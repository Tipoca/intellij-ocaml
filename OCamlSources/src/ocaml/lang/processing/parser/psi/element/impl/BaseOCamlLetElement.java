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
import ocaml.lang.processing.lexer.token.OCamlTokenTypes;
import ocaml.lang.processing.parser.ast.util.OCamlASTTreeUtil;
import ocaml.lang.processing.parser.psi.element.OCamlLetElement;
import org.jetbrains.annotations.NotNull;

abstract class BaseOCamlLetElement extends BaseOCamlElement implements OCamlLetElement {
    public BaseOCamlLetElement(@NotNull final ASTNode astNode) {
        super(astNode);
    }

    public boolean isRecursive() {
        return OCamlASTTreeUtil.findChildOfType(getNode(), OCamlTokenTypes.REC_KEYWORD, false) != null;
    }
}
