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

package ocaml.lang.processing.parser.psi.element.impl;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElementVisitor;
import ocaml.lang.processing.parser.psi.OCamlElementVisitor;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.OCamlFile;
import ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 22.03.2009
 */
public abstract class BaseOCamlFile extends PsiFileBase implements OCamlFile {
    protected BaseOCamlFile(@NotNull final FileViewProvider fileViewProvider, @NotNull final Language language) {
        super(fileViewProvider, language);
    }

    @NotNull
    public String getCanonicalName() {
        final VirtualFile file = getVirtualFile();
        final String fileNameWithoutExtension = file == null
            ? OCamlStringUtil.dropFromLastDot(getName())
            : file.getNameWithoutExtension();
        return OCamlStringUtil.capitalize(fileNameWithoutExtension);
    }

    @Override
    public void accept(@NotNull final PsiElementVisitor psiElementVisitor) {
        if (!OCamlPsiUtil.acceptOCamlElement(this, psiElementVisitor)) {
            super.accept(psiElementVisitor);
        }
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitFile(this);
    }
}
