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

package ocaml.settings;

import com.intellij.execution.RunManager;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.ProjectJdkTable;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.util.io.FileUtil;
import ocaml.compile.FlagBasedValidityState;
import ocaml.entity.OCamlModule;
import ocaml.run.OCamlRunConfiguration;
import ocaml.sdk.OCamlSdkType;
import ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author Maxim.Manuylov
 *         Date: 04.04.2010
 */
@State(
    name = "OCamlSettings",
    storages = {
        @Storage(id = "default", file = "$PROJECT_FILE$"),
        @Storage(id = "dir", file = "$PROJECT_CONFIG_DIR$/ocaml_settings.xml", scheme = StorageScheme.DIRECTORY_BASED)
    })
public class OCamlSettings implements ProjectComponent, PersistentStateComponent<OCamlState> {
    @NotNull private final Project myProject;
    @NotNull private final Map<String, FlagBasedValidityState> myExeFileStates = new HashMap<String, FlagBasedValidityState>();
    @Nullable private Sdk myTopLevelSdk = null;
    @NotNull private String myTopLevelCmdParams;
    @NotNull private String myTopLevelCmdWorkingDir;

    @NotNull private static OCamlSettings ourInstance;

    @NotNull
    public static OCamlSettings getInstance() {
        return ourInstance;
    }

    public OCamlSettings(@NotNull final Project project) {
        myProject = project;
        ourInstance = this;
    }

    @NotNull
    public OCamlState getState() {
        final OCamlState state = new OCamlState();
        if (myTopLevelSdk != null) {
            state.setTopLevelSdkHomePath(FileUtil.toSystemIndependentName(myTopLevelSdk.getHomePath()));
        }
        filterExeFileStates();
        for (final Map.Entry<String, FlagBasedValidityState> entry : myExeFileStates.entrySet()) {
            state.putExeFileState(entry.getKey(), entry.getValue().getFlag());
        }
        state.setTopLevelCmdOptions(myTopLevelCmdParams);
        state.setTopLevelCmdWorkingDir(myTopLevelCmdWorkingDir);
        return state;
    }

    public void loadState(@NotNull final OCamlState state) {
        final String systemIndependentHomePath = state.getTopLevelSdkHomePath();
        if (systemIndependentHomePath == null) {
            myTopLevelSdk = null;
        }
        else {
            final List<Sdk> ocamlSdks = ProjectJdkTable.getInstance().getSdksOfType(OCamlSdkType.getInstance());
            for (final Sdk ocamlSdk : ocamlSdks) {
                if (systemIndependentHomePath.equals(FileUtil.toSystemIndependentName(ocamlSdk.getHomePath()))) {
                    myTopLevelSdk = ocamlSdk;
                    break;
                }
            }
        }
        for (final Map.Entry<String, Boolean> entry : state.getExeFileStates().entrySet()) {
            myExeFileStates.put(entry.getKey(), FlagBasedValidityState.fromFlag(entry.getValue()));
        }
        myTopLevelCmdParams = OCamlStringUtil.getNotNull(state.getTopLevelCmdOptions());
        myTopLevelCmdWorkingDir = OCamlStringUtil.getNotNull(state.getTopLevelCmdWorkingDir());
    }

    @Nullable
    public Sdk getTopLevelSdk() {
        return myTopLevelSdk;
    }

    public void setTopLevelSdk(@Nullable final Sdk topLevelSdk) {
        myTopLevelSdk = topLevelSdk;
    }

    @NotNull
    public FlagBasedValidityState getExeFileState(@NotNull final String exeFilePath) {
        final FlagBasedValidityState state = myExeFileStates.get(FileUtil.toSystemIndependentName(exeFilePath));
        return state == null ? FlagBasedValidityState.TRUE_STATE : state;
    }

    public void saveExeFileState(@NotNull final String exeFilePath, @NotNull final FlagBasedValidityState state) {
        myExeFileStates.put(FileUtil.toSystemIndependentName(exeFilePath), state);
    }

    private void filterExeFileStates() {
        final Set<String> exeFilePaths = new HashSet<String>();
        final RunConfiguration[] configurations = RunManager.getInstance(myProject).getAllConfigurations();
        for (final RunConfiguration configuration : configurations) {
            if (!(configuration instanceof OCamlRunConfiguration)) continue;
            final OCamlModule ocamlModule = ((OCamlRunConfiguration) configuration).getMainOCamlModule();
            if (ocamlModule == null) continue;
            exeFilePaths.add(FileUtil.toSystemIndependentName(ocamlModule.getCompiledExecutableFile().getAbsolutePath()));
        }
        for (final Iterator<Map.Entry<String, FlagBasedValidityState>> iterator = myExeFileStates.entrySet().iterator(); iterator.hasNext();) {
            final Map.Entry<String, FlagBasedValidityState> entry = iterator.next();
            if (!exeFilePaths.contains(entry.getKey())) {
                iterator.remove();
            }
        }
    }

    public void setTopLevelCmdOptions(@NotNull final String cmdParams) {
        myTopLevelCmdParams = cmdParams;
    }

    @NotNull
    public String getTopLevelCmdOptions() {
        return myTopLevelCmdParams;
    }

    public void setTopLevelCmdWorkingDir(@NotNull final String dir) {
        myTopLevelCmdWorkingDir = dir;
    }

    @NotNull
    public String getTopLevelCmdWorkingDir() {
        return myTopLevelCmdWorkingDir;
    }

    @NotNull
    public String getComponentName() {
        return "OCamlSettings";
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    public void projectOpened() {
    }

    public void projectClosed() {
    }
}
