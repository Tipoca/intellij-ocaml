/*
 * OCaml Support For IntelliJ Platform.
 * Copyright (C) 2010 Maxim Manuylov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/gpl-2.0.html>.
 */

package ocaml.compile;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunManager;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.compiler.ClassInstrumentingCompiler;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompileScope;
import com.intellij.openapi.compiler.ValidityState;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import ocaml.entity.CyclicDependencyException;
import ocaml.entity.OCamlModule;
import ocaml.run.OCamlRunConfiguration;
import ocaml.settings.OCamlSettings;
import ocaml.util.OCamlSystemUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.intellij.openapi.compiler.CompilerMessageCategory.ERROR;
import static com.intellij.openapi.compiler.CompilerMessageCategory.INFORMATION;

/**
 * @author Maxim.Manuylov
 *         Date: 13.04.2010
 */
public class OCamlLinker extends BaseOCamlCompiler implements ClassInstrumentingCompiler {
    @NotNull
    public ProcessingItem[] getProcessingItems(@NotNull final CompileContext context) {
        final ProgressIndicator progressIndicator = context.getProgressIndicator();
        progressIndicator.setIndeterminate(true);
        progressIndicator.setText("Preparing files for linking...");

        final OCamlCompileContext ocamlContext = OCamlCompileContext.createOn(context);
        if (!ocamlContext.isStandaloneCompile()) {
            return new ProcessingItem[] { createProcessingItem(context, getMainOCamlModule(ocamlContext)) };
        }
        
        final ArrayList<ProcessingItem> items = new ArrayList<ProcessingItem>();
        final RunConfiguration[] configurations = RunManager.getInstance(context.getProject()).getAllConfigurations();
        for (final RunConfiguration configuration : configurations) {
            if (!(configuration instanceof OCamlRunConfiguration)) continue;
            final OCamlModule ocamlModule = ((OCamlRunConfiguration) configuration).getMainOCamlModule();
            if (ocamlModule == null) continue;
            items.add(createProcessingItem(context, ocamlModule));            
        }
        
        return items.toArray(new ProcessingItem[items.size()]);
    }

    @NotNull
    public ProcessingItem[] process(@NotNull final CompileContext context, @NotNull final ProcessingItem[] items) {
        final ProgressIndicator progressIndicator = context.getProgressIndicator();
        progressIndicator.setIndeterminate(false);
        progressIndicator.setText("Linking...");

        final double allCount = items.length;
        int processedCount = -1;

        final ArrayList<ProcessingItem> processedItems = new ArrayList<ProcessingItem>();
        final ProjectFileIndex fileIndex = ProjectRootManager.getInstance(context.getProject()).getFileIndex();
        final OCamlCompileContext ocamlContext = OCamlCompileContext.createOn(context);
        final boolean isDebugMode = ocamlContext.isDebugMode();

        for (final ProcessingItem item : items) {
            progressIndicator.setText2("");
            processedCount++;
            progressIndicator.setFraction(processedCount / allCount);

            if (!(item instanceof OCamlLinkerProcessingItem)) continue;
            final OCamlModule mainOCamlModule = ((OCamlLinkerProcessingItem) item).getOCamlModule();
            if (mainOCamlModule == null) continue;

            final String exeFilePath = mainOCamlModule.getCompiledExecutableFile().getAbsolutePath();
            progressIndicator.setText2(exeFilePath);

            try {
                link(mainOCamlModule, fileIndex, context, ocamlContext);
            } catch (final CyclicDependencyException e) {
                context.addMessage(ERROR, e.getMessage(), null, -1, -1);
                continue;
            }

            processedItems.add(item);
            final ValidityState state = item.getValidityState();
            assert state != null && state instanceof FlagBasedValidityState;
            OCamlSettings.getInstance().saveExeFileState(exeFilePath, (FlagBasedValidityState) state);
        }

        return processedItems.toArray(new ProcessingItem[processedItems.size()]);
    }

    private void link(@NotNull final OCamlModule ocamlModule,
                      @NotNull final ProjectFileIndex fileIndex,
                      @NotNull final CompileContext context,
                      final OCamlCompileContext ocamlContext) throws CyclicDependencyException {
        final GeneralCommandLine cmd = getBaseCompilerCommandLineForFile(ocamlModule.getSourcesDir(), fileIndex, context, ocamlContext.isDebugMode());
        if (cmd == null) return;

        cmd.addParameter("-o");
        cmd.addParameter(ocamlModule.getCompiledExecutableFile().getAbsolutePath());

        if (!ocamlContext.isStandaloneCompile()) {
            cmd.getParametersList().addParametersString(getRunConfiguration(ocamlContext).getLinkerOptions());
        }

        final List<OCamlModule> dependencies = ocamlModule.collectAllDependencies();
        Collections.reverse(dependencies);
        
        for (final OCamlModule dependency : dependencies) {
            cmd.addParameter(dependency.getCompiledImplementationFile().getAbsolutePath());
        }

        cmd.addParameter(ocamlModule.getCompiledImplementationFile().getAbsolutePath());

        try {
            final ProcessOutput processOutput = OCamlSystemUtil.execute(cmd);
            processLines(processOutput.getStdoutLines(), context, null, INFORMATION);

            final List<String> errLines = processOutput.getStderrLines();
            processLines(errLines, context, null, ERROR);
        }
        catch (final ExecutionException e) {
            context.addMessage(ERROR, e.getLocalizedMessage(), null, -1, -1);
        }


        //todo OCamlSystemUtil.addStdPaths(cmd, sdk);

                //todo libraries!!! maybe in OCamlModule
    }

    @NotNull
    public ValidityState createValidityState(@NotNull final DataInput in) throws IOException {
        return FlagBasedValidityState.load(in);
    }

    @NotNull
    private ProcessingItem createProcessingItem(@NotNull final CompileContext context, @NotNull final OCamlModule mainOCamlModule) {
        final File cmoFile = mainOCamlModule.getCompiledImplementationFile();
        VirtualFile file = LocalFileSystem.getInstance().findFileByIoFile(cmoFile);
        if (file == null) {
            context.addMessage(ERROR, "File \"" + FileUtil.toSystemDependentName(cmoFile.getAbsolutePath()) + "\" was not created. Rebuild the project.", null, -1, -1);
            return doCreateProcessingItem(null, mainOCamlModule.getSourcesDir(), null);
        }

        final File exeFile = mainOCamlModule.getCompiledExecutableFile();
        final FlagBasedValidityState oldState = OCamlSettings.getInstance().getExeFileState(exeFile.getAbsolutePath());
        boolean relinkingIsNeeded = false;
        final Boolean thereWasRecompilation = context.getUserData(THERE_WAS_RECOMPILATION);
        if ((thereWasRecompilation != null && thereWasRecompilation) || !exeFile.exists()) {
            relinkingIsNeeded = true;
        }

        return doCreateProcessingItem(mainOCamlModule, file, relinkingIsNeeded ? oldState.another() : oldState);
    }

    @NotNull
    private ProcessingItem doCreateProcessingItem(@Nullable final OCamlModule ocamlModule,
                                                  @NotNull final VirtualFile file,
                                                  @Nullable final FlagBasedValidityState state) {
        return new OCamlLinkerProcessingItem() {
            @Nullable
            public OCamlModule getOCamlModule() {
                return ocamlModule;
            }

            @NotNull
            public VirtualFile getFile() {
                return file;
            }

            @Nullable
            public ValidityState getValidityState() {
                return state;
            }
        };
    }

    @NotNull
    public String getDescription() {
        return "OCaml Linker";
    }

    public boolean validateConfiguration(@NotNull final CompileScope scope) {
        return true;
    }

    private static interface OCamlLinkerProcessingItem extends ProcessingItem {
        @Nullable
        OCamlModule getOCamlModule();
    }
}
