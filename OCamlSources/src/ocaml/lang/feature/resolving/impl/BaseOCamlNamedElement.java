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

package ocaml.lang.feature.resolving.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import ocaml.lang.feature.refactoring.rename.OCamlNamesValidator;
import ocaml.lang.feature.resolving.OCamlNamedElement;
import ocaml.lang.feature.resolving.util.OCamlASTNodeUtil;
import ocaml.lang.processing.parser.psi.OCamlElement;
import ocaml.lang.processing.parser.psi.OCamlElementProcessorAdapter;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.OCamlModuleDefinitionBinding;
import ocaml.lang.processing.parser.psi.element.OCamlModuleSpecificationBinding;
import ocaml.lang.processing.parser.psi.element.impl.BaseOCamlElement;
import ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 28.03.2009
 */
public abstract class BaseOCamlNamedElement extends BaseOCamlElement implements OCamlNamedElement {
    protected BaseOCamlNamedElement(@NotNull final ASTNode node) {
        super(node);
    }

    @Nullable
    public String getCanonicalName() {
        return getName();
    }

    @Nullable
    public String getCanonicalPath() {
        final StringBuilder sb = new StringBuilder(OCamlStringUtil.getNotNull(getCanonicalName()));

        final OCamlElementProcessorAdapter processor = new OCamlElementProcessorAdapter() {
            public void process(@NotNull final OCamlElement psiElement) {
                if (psiElement instanceof OCamlModuleDefinitionBinding || psiElement instanceof OCamlModuleSpecificationBinding) {
                    sb.insert(0, ".");
                    sb.insert(0, OCamlStringUtil.getNotNull(((OCamlNamedElement) psiElement).getCanonicalName()));
                }
            }
        };

        OCamlElement parent = OCamlPsiUtil.getParent(this);

        while (parent != null) {
            parent.accept(processor);
            parent = OCamlPsiUtil.getParent(parent);
        }

        return sb.toString();
    }

    @Override
    @Nullable
    public String getName() {
        final ASTNode nameElement = getNameElement();
        return nameElement == null ? null : nameElement.getText();
    }

    @NotNull
    public PsiElement setName(@NotNull final String name) throws IncorrectOperationException {
        final ASTNode nameElement = getNameElement();
        if (nameElement == null) {
            throw new IncorrectOperationException("Incorrect " + getDescription() + " name");
        }

        checkNameIsNotAKeyword(name);
        getNameType().checkNameIsCorrect(this, name);

        OCamlASTNodeUtil.replaceLeafText(nameElement.getFirstChildNode(), name);

        return this;
    }

    private void checkNameIsNotAKeyword(@NotNull final String name) throws IncorrectOperationException {
        if (OCamlNamesValidator.isKeyword(name)) {
            throw new IncorrectOperationException("It is not allowed to use a keyword as a " + getDescription() + " name.");
        }
    }

    @Override
    public int getTextOffset() {
        final ASTNode nameElement = getNameElement();
        return nameElement == null ? 0 : nameElement.getStartOffset();
    }

    @Override
    public String toString() {
        return getDescription();
    }
}
