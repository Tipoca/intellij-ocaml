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

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.compiler.CompileContext;
import com.intellij.openapi.compiler.CompilerMessageCategory;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.vfs.VirtualFile;
import ocaml.entity.OCamlModule;
import ocaml.run.OCamlRunConfiguration;
import ocaml.sdk.OCamlSdkType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.openapi.compiler.CompilerMessageCategory.ERROR;

abstract class BaseOCamlCompiler {
    @NotNull protected static final Key<Boolean> THERE_WAS_RECOMPILATION = new Key<Boolean>("THERE_WAS_RECOMPILATION");

    @NotNull
    protected OCamlModule getMainOCamlModule(@NotNull final OCamlCompileContext ocamlContext) {
        final OCamlModule ocamlModule = getRunConfiguration(ocamlContext).getMainOCamlModule();
        assert ocamlModule != null;
        return ocamlModule;
    }

    @NotNull
    protected OCamlRunConfiguration getRunConfiguration(@NotNull final OCamlCompileContext ocamlContext) {
        final OCamlRunConfiguration runConfiguration = ocamlContext.getRunConfiguration();
        assert runConfiguration != null;
        return runConfiguration;
    }

    @Nullable
    protected GeneralCommandLine getBaseCompilerCommandLineForFile(@NotNull final VirtualFile file,
                                                                   @NotNull final ProjectFileIndex fileIndex,
                                                                   @NotNull final CompileContext context,
                                                                   final boolean isDebugMode) {
        final Module module = fileIndex.getModuleForFile(file);
        if (module == null) {
            context.addMessage(ERROR, "Cannot determine module for \"" + file.getPath() + "\" file.", file.getUrl(), -1, -1);
            return null;
        }

        final Sdk sdk = ModuleRootManager.getInstance(module).getSdk();
        if (sdk == null || !(sdk.getSdkType() instanceof OCamlSdkType)) {
            context.addMessage(ERROR, "Sdk of module \"" + module.getName() + "\" is invalid.", file.getUrl(), -1, -1);
            return null;
        }

        final String sdkHomePath = sdk.getHomePath();
        final String compilerExePath = OCamlSdkType.getByteCodeCompilerExecutable(sdkHomePath).getAbsolutePath();

        final GeneralCommandLine cmd = new GeneralCommandLine();
        cmd.setWorkDirectory(sdkHomePath);
        cmd.setExePath(compilerExePath);
        if (isDebugMode) {
            cmd.addParameter("-g");
        }

        return cmd;
    }

    protected void processLines(@NotNull final List<String> lines, @NotNull final CompileContext context, @Nullable final VirtualFile file, @NotNull final CompilerMessageCategory category) {
        for (final String line : lines) {
            final String url = file == null ? null : file.getUrl();
            context.addMessage(category, line, url, -1, -1);
        }
    }
}