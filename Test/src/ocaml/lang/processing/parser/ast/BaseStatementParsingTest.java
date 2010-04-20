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

package ocaml.lang.processing.parser.ast;

import ocaml.lang.processing.Strings;
import ocaml.lang.processing.parser.ast.testCase.ParsingTestCase;
import org.testng.annotations.Test;

import static com.intellij.psi.TokenType.ERROR_ELEMENT;
import static ocaml.lang.processing.lexer.token.OCamlTokenTypes.*;
import static ocaml.lang.processing.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 20.03.2009
 */
@Test
public abstract class BaseStatementParsingTest extends ParsingTestCase {
    public void testExternal() throws Exception {
        myTree.addNode(2, EXTERNAL_DEFINITION);
        myTree.addNode(3, EXTERNAL_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "myFunc");
        myTree.addNode(3, COLON);
        myTree.addNode(3, FUNCTION_TYPE_EXPRESSION);
        myTree.addNode(4, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "int");
        myTree.addNode(4, MINUS_GT);
        myTree.addNode(4, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "int");
        myTree.addNode(3, EQ);
        myTree.addNode(3, EXTERNAL_DECLARATION);
        myTree.addNode(4, STRING_LITERAL, "\"myFunc\"");

        doTest("external myFunc : int -> int = \"myFunc\"", myTree.getStringRepresentation());
    }

    public void testOpenDirective() throws Exception {
        myTree.addNode(2, OPEN_DIRECTIVE);
        myTree.addNode(3, OPEN_KEYWORD);
        myTree.addNode(3, MODULE_NAME);
        myTree.addNode(4, UCFC_IDENTIFIER, "Module");

        doTest("open Module", myTree.getStringRepresentation());
    }

    public void testSemicolonSemicolon() throws Exception {
        myTree.addNode(2, SEMICOLON_SEMICOLON);

        doTest(";;", myTree.getStringRepresentation());
    }

    public void testCommentBlock() throws Exception {
        myTree.addNode(2, COMMENT_BLOCK);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(3, COMMENT, " comment ");
        myTree.addNode(3, COMMENT_END, "*)");

        doTest("(* comment *)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, COMMENT_BLOCK);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(3, COMMENT_END, "*)");

        doTest("(**)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, COMMENT_BLOCK);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT_END, "*)");
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT_END, "*)");
        myTree.addNode(3, COMMENT_END, "*)");

        doTest("(*(**)(**)*)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, COMMENT_BLOCK);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(3, COMMENT, "c");
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(4, COMMENT_END, "*)");
        myTree.addNode(3, COMMENT, "c");
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(4, COMMENT_END, "*)");
        myTree.addNode(3, COMMENT, "c");
        myTree.addNode(3, COMMENT_END, "*)");

        doTest("(*c(*c*)c(*c*)c*)", myTree.getStringRepresentation());
    }

    public void testUnclosedComment() throws Exception {
        myTree.addNode(2, UNCLOSED_COMMENT);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(3, COMMENT, " comment");
        myTree.addNode(2, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(* comment", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, UNCLOSED_COMMENT);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(2, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, UNCLOSED_COMMENT);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(3, UNCLOSED_COMMENT);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(2, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*(*", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, UNCLOSED_COMMENT);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT_END, "*)");
        myTree.addNode(2, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*(**)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, UNCLOSED_COMMENT);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT_END, "*)");
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT_END, "*)");
        myTree.addNode(2, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*(**)(**)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, UNCLOSED_COMMENT);
        myTree.addNode(3, COMMENT_BEGIN, "(*");
        myTree.addNode(3, COMMENT, "c");
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(4, COMMENT_END, "*)");
        myTree.addNode(3, COMMENT, "c");
        myTree.addNode(3, COMMENT_BLOCK);
        myTree.addNode(4, COMMENT_BEGIN, "(*");
        myTree.addNode(4, COMMENT, "c");
        myTree.addNode(4, COMMENT_END, "*)");
        myTree.addNode(3, COMMENT, "c");
        myTree.addNode(2, ERROR_ELEMENT, Strings.UNCLOSED_COMMENT);

        doTest("(*c(*c*)c(*c*)c", myTree.getStringRepresentation());
    }
}
