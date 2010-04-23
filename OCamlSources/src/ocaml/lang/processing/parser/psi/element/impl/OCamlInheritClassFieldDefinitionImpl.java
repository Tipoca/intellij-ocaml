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
import ocaml.lang.feature.resolving.ResolvingBuilder;
import ocaml.lang.feature.resolving.impl.BaseOCamlResolvedReference;
import ocaml.lang.feature.resolving.util.OCamlDeclarationsUtil;
import ocaml.lang.processing.parser.ast.element.OCamlElementTypes;
import ocaml.lang.processing.parser.ast.util.OCamlASTTreeUtil;
import ocaml.lang.processing.parser.psi.OCamlElementVisitor;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.OCamlClassExpression;
import ocaml.lang.processing.parser.psi.element.OCamlInheritClassFieldDefinition;
import ocaml.lang.processing.parser.psi.element.OCamlInstVarName;
import ocaml.lang.processing.parser.psi.element.OCamlInstVarNamePattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 21.03.2009
 */
public class OCamlInheritClassFieldDefinitionImpl extends BaseOCamlElement implements OCamlInheritClassFieldDefinition {
    public OCamlInheritClassFieldDefinitionImpl(@NotNull final ASTNode node) {
        super(node);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitInheritClassFieldDefinition(this);
    }

    @Override
    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        if (OCamlDeclarationsUtil.processDeclarationsInChildren(builder, this, OCamlInstVarNamePattern.class)) return true;
        final OCamlClassExpression expression = OCamlPsiUtil.getFirstChildOfType(this, OCamlClassExpression.class);
        return OCamlDeclarationsUtil.processDeclarationsInStructuredElement(builder, expression);
    }
}
