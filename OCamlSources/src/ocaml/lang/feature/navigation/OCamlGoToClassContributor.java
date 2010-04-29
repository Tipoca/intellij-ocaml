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

package ocaml.lang.feature.navigation;

import com.intellij.navigation.ChooseByNameContributor;
import com.intellij.navigation.NavigationItem;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 27.04.2010
 */
public class OCamlGoToClassContributor implements ChooseByNameContributor { //todo
    public String[] getNames(@NotNull final Project project, final boolean includeNonProjectItems) {
//        com.intellij.psi.search.searches.ReferencesSearch.search((PsiElement) null).forEach()

        //ModuleScope.
//        FilenameIndex.
//        GlobalSearchScope.projectScope(project).
        //ProjectAndLibrariesScope.

        return new String[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    public NavigationItem[] getItemsByName(@NotNull final String name, @NotNull final String pattern, @NotNull final Project project, final boolean includeNonProjectItems) {
        return new NavigationItem[0];  //To change body of implemented methods use File | Settings | File Templates.
    }
}
