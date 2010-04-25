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

import com.intellij.lang.ParserDefinition;
import com.intellij.psi.tree.IElementType;
import ocaml.lang.fileType.ml.MLFileTypeLanguage;
import ocaml.lang.processing.Strings;
import ocaml.lang.processing.parser.ast.element.OCamlElementTypes;
import ocaml.lang.processing.parser.ast.util.TreeStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static com.intellij.psi.TokenType.ERROR_ELEMENT;
import static ocaml.lang.processing.lexer.token.OCamlTokenTypes.*;
import static ocaml.lang.processing.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 28.02.2009
 */
@Test
public class DefinitionParsingTest extends BaseStatementParsingTest {
    public void testDoubleSemicolon() throws Exception {
        myTree.addNode(2, LET_STATEMENT);
        myTree.addNode(3, LET_KEYWORD);
        myTree.addNode(3, LET_BINDING);
        myTree.addNode(4, LET_BINDING_PATTERN);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "7");
        myTree.addNode(2, LET_STATEMENT);
        myTree.addNode(3, LET_KEYWORD);
        myTree.addNode(3, LET_BINDING);
        myTree.addNode(4, LET_BINDING_PATTERN);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "c");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "0");
        myTree.addNode(2, SEMICOLON_SEMICOLON);
        myTree.addNode(2, CONSTANT);
        myTree.addNode(3, INTEGER_LITERAL, "4");

        doTest("let a = 7 let c = 0;; 4", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, LET_STATEMENT);
        myTree.addNode(3, LET_KEYWORD);
        myTree.addNode(3, LET_BINDING);
        myTree.addNode(4, LET_BINDING_PATTERN);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "c");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "0");
        myTree.addNode(2, ERROR_ELEMENT, Strings.SEMICOLON_SEMICOLON_EXPECTED);
        myTree.addNode(2, LET_EXPRESSION);
        myTree.addNode(3, LET_KEYWORD);
        myTree.addNode(3, LET_BINDING);
        myTree.addNode(4, LET_BINDING_PATTERN);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "s");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "2");
        myTree.addNode(3, IN_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "s");

        doTest("let c = 0 let s = 2 in s", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, WHILE_EXPRESSION);
        myTree.addNode(3, WHILE_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, DO_KEYWORD);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, LPAR);
        myTree.addNode(4, RPAR);
        myTree.addNode(3, DONE_KEYWORD);
        myTree.addNode(2, ERROR_ELEMENT, Strings.SEMICOLON_SEMICOLON_EXPECTED);
        myTree.addNode(2, CONSTANT);
        myTree.addNode(3, INTEGER_LITERAL, "4");

        doTest("while a do () done 4", myTree.getStringRepresentation());
    }

    public void testIncludeDefinition() throws Exception {
        myTree.addNode(2, INCLUDE_DIRECTIVE_DEFINITION);
        myTree.addNode(3, INCLUDE_KEYWORD);
        myTree.addNode(3, MODULE_NAME);
        myTree.addNode(4, UCFC_IDENTIFIER, "Module");

        doTest("include Module", myTree.getStringRepresentation());
    }

    public void testExceptionDefinition() throws Exception {
        myTree.addNode(2, EXCEPTION_DEFINITION);
        myTree.addNode(3, EXCEPTION_KEYWORD);
        myTree.addNode(3, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(4, UCFC_IDENTIFIER, "Error");

        doTest("exception Error", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, EXCEPTION_DEFINITION);
        myTree.addNode(3, EXCEPTION_KEYWORD);
        myTree.addNode(3, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(4, UCFC_IDENTIFIER, "Error1");
        myTree.addNode(3, EQ);
        myTree.addNode(3, CONSTRUCTOR_NAME);
        myTree.addNode(4, UCFC_IDENTIFIER, "Error");

        doTest("exception Error1 = Error", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, EXCEPTION_DEFINITION);
        myTree.addNode(3, EXCEPTION_KEYWORD);
        myTree.addNode(3, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(4, UCFC_IDENTIFIER, "Error");
        myTree.addNode(3, OF_KEYWORD);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");

        doTest("exception Error of int", myTree.getStringRepresentation());
    }

    public void testEmptyFile() throws Exception {
        myTree = new TreeStringBuilder(false);
        myTree.addNode(0, ML_FILE);
        myTree.addNode(1, FILE_MODULE_EXPRESSION, "");             

        doTest("", myTree.getStringRepresentation());
    }

    @NotNull
    protected ParserDefinition getParserDefinition() {
        return MLFileTypeLanguage.INSTANCE.getParserDefinition();
    }

    @NotNull
    protected IElementType getModuleNodeType() {
        return OCamlElementTypes.FILE_MODULE_EXPRESSION;
    }
}
