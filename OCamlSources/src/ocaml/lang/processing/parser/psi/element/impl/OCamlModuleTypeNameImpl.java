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
import ocaml.lang.feature.resolving.OCamlResolvedReference;
import ocaml.lang.feature.resolving.impl.BaseOCamlReference;
import ocaml.lang.feature.resolving.util.OCamlResolvingUtil;
import ocaml.lang.processing.parser.psi.OCamlElementVisitor;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
public class OCamlModuleTypeNameImpl extends BaseOCamlReference implements OCamlModuleTypeName {
    public OCamlModuleTypeNameImpl(@NotNull final ASTNode node) {
        super(node);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitModuleTypeName(this);
    }

    @Nullable
    public ASTNode getNameElement() {
        return getNode();
    }

    @NotNull
    public NameType getNameType() {
        return NameType.AnyCase;
    }

    @NotNull
    public String getDescription() {
        return "module type";
    }

    @NotNull
    public List<Class<? extends OCamlResolvedReference>> getPossibleResolvedTypes() {
        return Arrays.<Class<? extends OCamlResolvedReference>>asList(OCamlModuleTypeDefinitionBinding.class, OCamlModuleTypeSpecificationBinding.class);
    }

    @NotNull
    public List<OCamlExtendedModuleName> getModulePath() {
        return OCamlPsiUtil.getModulePath(this, OCamlModuleTypeName.class, OCamlExtendedModuleName.class);
    }

    @NotNull
    public OCamlModuleTypeName getModuleTypeName() {
        return this;
    }

    @Nullable
    public OCamlStructuredElement findActualDefinition() {
        return OCamlResolvingUtil.findActualDefinitionOfStructuredElement(this);
    }
}
