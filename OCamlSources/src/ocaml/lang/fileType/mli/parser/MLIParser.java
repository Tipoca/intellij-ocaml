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

package ocaml.lang.fileType.mli.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import ocaml.lang.processing.parser.ast.StatementParsing;
import ocaml.lang.processing.parser.ast.element.OCamlElementTypes;
import ocaml.lang.processing.parser.ast.util.CommentsParserPsiBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 09.02.2009
 */
class MLIParser implements PsiParser {
    @NotNull
    public ASTNode parse(final IElementType root, final PsiBuilder builder) {
        final PsiBuilder builderWrapper = new CommentsParserPsiBuilder(builder);

        final PsiBuilder.Marker rootMarker = builderWrapper.mark();
        StatementParsing.parseSpecifications(builderWrapper, new StatementParsing.Condition() {
            public boolean test() {
                return builderWrapper.eof();
            }
        });
        rootMarker.done(OCamlElementTypes.FILE_MODULE_TYPE);
        rootMarker.precede().done(root);
        return builderWrapper.getTreeBuilt();
    }
}
