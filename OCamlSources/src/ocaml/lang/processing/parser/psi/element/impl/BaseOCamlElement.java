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

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import ocaml.lang.feature.resolving.ResolvingBuilder;
import ocaml.lang.processing.parser.psi.OCamlElement;
import ocaml.lang.processing.parser.psi.OCamlElementProcessor;
import ocaml.lang.processing.parser.psi.OCamlElementVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
public abstract class BaseOCamlElement extends ASTWrapperPsiElement implements OCamlElement {
    public BaseOCamlElement(@NotNull final ASTNode astNode) {
        super(astNode);
    }

    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        return false;
    }

    @Override
    public void accept(@NotNull final PsiElementVisitor psiElementVisitor) {
        if (psiElementVisitor instanceof OCamlElementVisitor) {
            visit((OCamlElementVisitor)psiElementVisitor);
        }
        else if (psiElementVisitor instanceof OCamlElementProcessor) {
            ((OCamlElementProcessor) psiElementVisitor).process(this);
        }
        else {
            super.accept(psiElementVisitor);
        }
    }
}
