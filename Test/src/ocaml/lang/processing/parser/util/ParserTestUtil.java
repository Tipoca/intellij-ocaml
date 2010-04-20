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

package ocaml.lang.processing.parser.util;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.openapi.command.impl.DummyProject;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 18.06.2009
 */
public class ParserTestUtil {
    @NotNull
    public static ASTNode buildTree(@NotNull final String text, @NotNull final ParserDefinition parserDefinition) throws Exception {
        final Project someProject = DummyProject.getInstance();
        final PsiBuilder builder = new PsiBuilderImpl(parserDefinition.createLexer(someProject), parserDefinition.getWhitespaceTokens(), parserDefinition.getCommentTokens(), text);
        builder.setDebugMode(true);
        final PsiParser parser = parserDefinition.createParser(someProject);
        final ASTNode root;
        try {
            root = parser.parse(parserDefinition.getFileNodeType(), builder);
        }
        catch (RuntimeException e) {
            throw new Exception(e);
        }
        return root;
    }
}
