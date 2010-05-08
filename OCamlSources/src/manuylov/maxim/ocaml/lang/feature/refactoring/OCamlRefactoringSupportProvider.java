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

package manuylov.maxim.ocaml.lang.feature.refactoring;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import com.intellij.refactoring.RefactoringActionHandler;

/**
 * @author Maxim.Manuylov
 *         Date: 26.04.2010
 */
public class OCamlRefactoringSupportProvider implements RefactoringSupportProvider { // todo
    public boolean isSafeDeleteAvailable(final PsiElement element) {
        return true;
    }

    public RefactoringActionHandler getIntroduceVariableHandler() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RefactoringActionHandler getExtractMethodHandler() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RefactoringActionHandler getIntroduceConstantHandler() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RefactoringActionHandler getIntroduceFieldHandler() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RefactoringActionHandler getIntroduceParameterHandler() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RefactoringActionHandler getPullUpHandler() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RefactoringActionHandler getPushDownHandler() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RefactoringActionHandler getExtractModuleHandler() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public RefactoringActionHandler getExtractSuperClassHandler() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean doInplaceRenameFor(final PsiElement element, final PsiElement context) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
