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

package manuylov.maxim.ocaml.module;

import com.intellij.ide.util.projectWizard.ModuleBuilder;
import com.intellij.ide.util.projectWizard.SourcePathsBuilder;
import com.intellij.openapi.module.ModuleType;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModifiableRootModel;
import com.intellij.openapi.util.Pair;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
class OCamlModuleBuilder extends ModuleBuilder implements SourcePathsBuilder {
    @Nullable private List<Pair<String, String>> mySourcePaths;
    @Nullable private String myContentRootPath;
    @Nullable private Sdk mySdk;

    public void setupRootModel(@NotNull final ModifiableRootModel rootModel) throws ConfigurationException {
        if (mySdk != null) {
          rootModel.setSdk(mySdk);
        }
        else {
          rootModel.inheritSdk();
        }
        String moduleRootPath = getContentEntryPath();
        if (moduleRootPath != null) {
          LocalFileSystem lfs = LocalFileSystem.getInstance();
          VirtualFile moduleContentRoot = lfs.refreshAndFindFileByPath(FileUtil.toSystemIndependentName(moduleRootPath));
          if (moduleContentRoot != null) {
            rootModel.addContentEntry(moduleContentRoot);
          }
        }
    }

    @NotNull
    public ModuleType getModuleType() {
        return OCamlModuleType.getInstance();
    }

    public String getContentEntryPath() {
      return myContentRootPath;
    }

    public void setContentEntryPath(final String moduleRootPath) {
      myContentRootPath = moduleRootPath;
    }

    @Nullable
    public List<Pair<String, String>> getSourcePaths() {
      return mySourcePaths;
    }

    public void setSourcePaths(@Nullable final List<Pair<String, String>> sourcePaths) {
      mySourcePaths = sourcePaths;
    }

    public void addSourcePath(@NotNull final Pair<String, String> sourcePathInfo) {
      if (mySourcePaths == null) {
        mySourcePaths = new ArrayList<Pair<String, String>>();
      }
      mySourcePaths.add(sourcePathInfo);
    }

    public void setSdk(@Nullable final Sdk sdk) {
      mySdk = sdk;
    }
}
