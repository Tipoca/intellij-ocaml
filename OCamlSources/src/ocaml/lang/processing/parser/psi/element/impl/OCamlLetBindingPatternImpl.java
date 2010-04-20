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
import ocaml.lang.feature.resolving.ResolvingBuilder;
import ocaml.lang.feature.resolving.util.OCamlDeclarationsUtil;
import ocaml.lang.processing.parser.psi.OCamlElement;
import ocaml.lang.processing.parser.psi.OCamlElementVisitor;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.OCamlLetBindingPattern;
import ocaml.lang.processing.parser.psi.element.OCamlPattern;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 20.04.2010
 */
public class OCamlLetBindingPatternImpl extends BaseOCamlElement implements OCamlLetBindingPattern {
    public OCamlLetBindingPatternImpl(@NotNull final ASTNode node) {
        super(node);
    }

    public void visit(@NotNull final OCamlElementVisitor visitor) {
        visitor.visitLetBindingPattern(this);
    }

    @Override
    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        final OCamlElement lastParent = builder.getLastParent(), lastParentParent = lastParent == null ? null : OCamlPsiUtil.getParent(lastParent);
        final boolean lastParentParentIsSibling = lastParentParent != null && OCamlPsiUtil.areSiblings(this, lastParentParent);
        return !lastParentParentIsSibling && OCamlDeclarationsUtil.processDeclarationsInChildren(builder, this, OCamlPattern.class);
    }
}
