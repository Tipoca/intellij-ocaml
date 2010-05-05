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

import com.intellij.lang.ASTNode;
import ocaml.lang.feature.resolving.NameType;
import ocaml.lang.processing.parser.psi.OCamlElementVisitor;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.OCamlFileModuleSpecificationBinding;
import ocaml.lang.processing.parser.psi.element.OCamlFileModuleType;
import ocaml.lang.processing.parser.psi.element.OCamlModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 01.05.2010
 */
public class OCamlFileModuleSpecificationBindingImpl extends BaseOCamlFileModuleBinding<OCamlModuleType> implements OCamlFileModuleSpecificationBinding {
    public OCamlFileModuleSpecificationBindingImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @NotNull
    public NameType getNameType() {
        return NameType.AnyCase;
    }

    @NotNull
    public String getDescription() {
        return "module type";
    }

    @Nullable
    public OCamlModuleType getExpression() {
        return OCamlPsiUtil.getLastChildOfType(this, OCamlFileModuleType.class);
    }

    @Nullable
    public OCamlModuleType getTypeExpression() {
        return null;
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitFileModuleSpecificationBinding(this);
    }
}