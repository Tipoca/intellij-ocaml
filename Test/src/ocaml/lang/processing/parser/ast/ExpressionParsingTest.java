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

import ocaml.lang.processing.parser.ast.testCase.MLParsingTestCase;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static ocaml.lang.processing.lexer.token.OCamlTokenTypes.*;
import static ocaml.lang.processing.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 28.02.2009
 */
@Test
public class ExpressionParsingTest extends MLParsingTestCase {
    public void testConstant() throws Exception {
        myTree.addNode(2, CONSTRUCTOR_NAME);
        myTree.addNode(3, UCFC_IDENTIFIER, "Constr");

        doTest("Constr", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CONSTANT);
        myTree.addNode(3, LPAR);
        myTree.addNode(3, RPAR);

        doTest("()", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CONSTANT);
        myTree.addNode(3, ACCENT);
        myTree.addNode(3, TAG_NAME);
        myTree.addNode(4, UCFC_IDENTIFIER, "Tag");

        doTest("`Tag", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CONSTANT);
        myTree.addNode(3, ACCENT);
        myTree.addNode(3, TAG_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "tag");

        doTest("`tag", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CONSTANT);
        myTree.addNode(3, ACCENT);
        myTree.addNode(3, TAG_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "_tag");

        doTest("`_tag", myTree.getStringRepresentation());
    }

    public void testExpressionInParenthess() throws Exception {
        myTree.addNode(2, PARENTHESES);
        myTree.addNode(3, LPAR);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "5");
        myTree.addNode(3, RPAR);

        doTest("(5)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, PARENTHESES);
        myTree.addNode(3, BEGIN_KEYWORD);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "5");
        myTree.addNode(3, END_KEYWORD);

        doTest("begin 5 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, TYPE_CONSTRAINT_EXPRESSION);
        myTree.addNode(3, LPAR);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "5");
        myTree.addNode(3, COLON);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");
        myTree.addNode(3, RPAR);

        doTest("(5 : int)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CASTING_EXPRESSION);
        myTree.addNode(3, LPAR);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "5");
        myTree.addNode(3, COLON_GT);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");
        myTree.addNode(3, RPAR);

        doTest("(5 :> int)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CASTING_EXPRESSION);
        myTree.addNode(3, LPAR);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "5");
        myTree.addNode(3, COLON);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");
        myTree.addNode(3, COLON_GT);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");
        myTree.addNode(3, RPAR);

        doTest("(5 : int :> int)", myTree.getStringRepresentation());
    }

    public void testCommaExpression() throws Exception {
        myTree.addNode(2, COMMA_EXPRESSION);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "1");
        myTree.addNode(3, COMMA);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "2");
        myTree.addNode(3, COMMA);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "3");

        doTest("1, 2, 3", myTree.getStringRepresentation());
    }

    public void testConstructorApplication() throws Exception {
        myTree.addNode(2, CONSTRUCTOR_APPLICATION_EXPRESSION);
        myTree.addNode(3, CONSTRUCTOR_NAME);
        myTree.addNode(4, UCFC_IDENTIFIER, "Constr");
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "5");

        doTest("Constr 5", myTree.getStringRepresentation());
    }

    public void testTaggedExpression() throws Exception {
        myTree.addNode(2, TAGGED_EXPRESSION);
        myTree.addNode(3, ACCENT);
        myTree.addNode(3, TAG_NAME);
        myTree.addNode(4, UCFC_IDENTIFIER, "Tag");
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "2");

        doTest("`Tag 2", myTree.getStringRepresentation());
    }

    public void testHeadTailExpression() throws Exception {
        myTree.addNode(2, HEAD_TAIL_EXPRESSION);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "1");
        myTree.addNode(3, COLON_COLON);
        myTree.addNode(3, LIST_EXPRESSION);
        myTree.addNode(4, LBRACKET);
        myTree.addNode(4, RBRACKET);

        doTest("1 :: []", myTree.getStringRepresentation());
    }

    public void testListExpression() throws Exception {
        myTree.addNode(2, LIST_EXPRESSION);
        myTree.addNode(3, LBRACKET);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "1");
        myTree.addNode(3, SEMICOLON);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "2");
        myTree.addNode(3, RBRACKET);

        doTest("[1; 2]", myTree.getStringRepresentation());
    }

    public void testArrayExpression() throws Exception {
        myTree.addNode(2, ARRAY_EXPRESSION);
        myTree.addNode(3, LBRACKET_VBAR);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "1");
        myTree.addNode(3, SEMICOLON);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "2");
        myTree.addNode(3, VBAR_RBRACKET);

        doTest("[|1; 2|]", myTree.getStringRepresentation());
    }

    public void testRecordExpression() throws Exception {
        myTree.addNode(2, RECORD_EXPRESSION);
        myTree.addNode(3, LBRACE);
        myTree.addNode(3, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(4, FIELD_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "0");
        myTree.addNode(3, SEMICOLON);
        myTree.addNode(3, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(4, FIELD_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "1");
        myTree.addNode(3, RBRACE);

        doTest("{a = 0; b = 1}", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, INHERITED_RECORD_EXPRESSION);
        myTree.addNode(3, LBRACE);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "recordValue");
        myTree.addNode(3, WITH_KEYWORD);
        myTree.addNode(3, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(4, FIELD_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "0");
        myTree.addNode(3, SEMICOLON);
        myTree.addNode(3, RECORD_FIELD_INITIALIZATION_IN_EXPRESSION);
        myTree.addNode(4, FIELD_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "1");
        myTree.addNode(3, RBRACE);

        doTest("{recordValue with a = 0; b = 1}", myTree.getStringRepresentation());
    }

    public void testFunctionApplication() throws Exception {
        myTree.addNode(2, FUNCTION_APPLICATION_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "f");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "2");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "c");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, CONSTRUCTOR_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Constr");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, TRUE_KEYWORD);
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, FALSE_KEYWORD);
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, LIST_EXPRESSION);
        myTree.addNode(5, LBRACKET);
        myTree.addNode(5, RBRACKET);
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, RPAR);
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "tag");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, TILDE);
        myTree.addNode(4, LABEL_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "lbl1");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, TILDE);
        myTree.addNode(4, LABEL_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "lbl2");
        myTree.addNode(4, COLON);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "3");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, QUEST);
        myTree.addNode(4, LABEL_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "lbl3");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, QUEST);
        myTree.addNode(4, LABEL_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "lbl4");
        myTree.addNode(4, COLON);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "4");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "g");

        doTest("f a b 2 c Constr true false [] () `tag ~lbl1 ~lbl2: 3 ?lbl3 ?lbl4: 4 g", myTree.getStringRepresentation());
    }

    public void testPrefixOperator() throws Exception {
        myTree.addNode(2, UNARY_EXPRESSION);
        myTree.addNode(3, PREFIX_OPERATOR, "~=");
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
               
        doTest("~=a", myTree.getStringRepresentation());
    }

    public void testInfixOperator() throws Exception {
        myTree.addNode(2, BINARY_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, INFIX_OPERATOR, "$+");
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");

        doTest("a$+b", myTree.getStringRepresentation());
    }

    public void testFieldAccessing() throws Exception {
        myTree.addNode(2, RECORD_FIELD_ACCESSING_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, DOT);
        myTree.addNode(3, FIELD_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");

        doTest("a.b", myTree.getStringRepresentation());
    }

    public void testArrayElementAccessing() throws Exception {
        myTree.addNode(2, ARRAY_ELEMENT_ACCESSING_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, DOT);
        myTree.addNode(3, PARENTHESES);
        myTree.addNode(4, LPAR);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, RPAR);

        doTest("a.(b)", myTree.getStringRepresentation());
    }

    public void testStringCharAccessing() throws Exception {
        myTree.addNode(2, STRING_CHAR_ACCESSING_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, DOT);
        myTree.addNode(3, BRACKETS);
        myTree.addNode(4, LBRACKET);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, RBRACKET);

        doTest("a.[b]", myTree.getStringRepresentation());
    }

    public void testAssignment() throws Exception {
        myTree.addNode(2, ASSIGNMENT_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, LT_MINUS);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "new_val");

        doTest("a <- new_val", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, ASSIGNMENT_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, COLON_EQ);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "new_val");

        doTest("a := new_val", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, ASSIGNMENT_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, LT_MINUS);
        myTree.addNode(3, ASSIGNMENT_EXPRESSION);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, LT_MINUS);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "new_val");

        doTest("a <- b <- new_val", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, ASSIGNMENT_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, COLON_EQ);
        myTree.addNode(3, ASSIGNMENT_EXPRESSION);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, COLON_EQ);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "new_val");

        doTest("a := b := new_val", myTree.getStringRepresentation());
    }

    public void testIfExpression() throws Exception {
        myTree.addNode(2, IF_EXPRESSION);
        myTree.addNode(3, IF_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, THEN_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");

        doTest("if a then b", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, IF_EXPRESSION);
        myTree.addNode(3, IF_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, THEN_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");
        myTree.addNode(3, ELSE_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "c");

        doTest("if a then b else c", myTree.getStringRepresentation());
    }

    public void testWhileExpression() throws Exception {
        myTree.addNode(2, WHILE_EXPRESSION);
        myTree.addNode(3, WHILE_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, DO_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");
        myTree.addNode(3, DONE_KEYWORD);

        doTest("while a do b done", myTree.getStringRepresentation());
    }

    public void testForExpression() throws Exception {
        myTree.addNode(2, FOR_EXPRESSION);
        myTree.addNode(3, FOR_KEYWORD);
        myTree.addNode(3, FOR_EXPRESSION_BINDING);
        myTree.addNode(4, FOR_EXPRESSION_INDEX_VARIABLE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, EQ);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, TO_KEYWORD);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "c");
        myTree.addNode(3, DO_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "d");
        myTree.addNode(3, DONE_KEYWORD);

        doTest("for a = b to c do d done", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, FOR_EXPRESSION);
        myTree.addNode(3, FOR_KEYWORD);
        myTree.addNode(3, FOR_EXPRESSION_BINDING);
        myTree.addNode(4, FOR_EXPRESSION_INDEX_VARIABLE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, EQ);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, DOWNTO_KEYWORD);
        myTree.addNode(4, VALUE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "c");
        myTree.addNode(3, DO_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "d");
        myTree.addNode(3, DONE_KEYWORD);

        doTest("for a = b downto c do d done", myTree.getStringRepresentation());
    }

    public void testSemicolonExpression() throws Exception {
        myTree.addNode(2, SEMICOLON_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, SEMICOLON);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");
        myTree.addNode(3, SEMICOLON);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "c");
        
        doTest("a; b; c", myTree.getStringRepresentation());
    }

    public void testMatchExpression() throws Exception {
        myTree.addNode(2, MATCH_EXPRESSION);
        myTree.addNode(3, MATCH_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "d");
        myTree.addNode(3, WITH_KEYWORD);
        myTree.addNode(3, VBAR);
        myTree.addNode(3, PATTERN_MATCHING);
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, WHEN_KEYWORD);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, TRUE_KEYWORD);
        myTree.addNode(4, MINUS_GT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "1");
        myTree.addNode(3, VBAR);
        myTree.addNode(3, PATTERN_MATCHING);
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, WHEN_KEYWORD);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, FALSE_KEYWORD);
        myTree.addNode(4, MINUS_GT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "2");

        doTest("match d with | a when true -> 1 | b when false -> 2", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, MATCH_EXPRESSION);
        myTree.addNode(3, MATCH_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "d");
        myTree.addNode(3, WITH_KEYWORD);
        myTree.addNode(3, PATTERN_MATCHING);
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, MINUS_GT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "1");
        myTree.addNode(3, VBAR);
        myTree.addNode(3, PATTERN_MATCHING);
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, MINUS_GT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "2");

        doTest("match d with a -> 1 | b -> 2", myTree.getStringRepresentation());
    }

    public void testFunctionExpression() throws Exception {
        myTree.addNode(2, FUNCTION_EXPRESSION);
        myTree.addNode(3, FUNCTION_KEYWORD);
        myTree.addNode(3, PATTERN_MATCHING);
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "x");
        myTree.addNode(4, MINUS_GT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "2");

        doTest("function x -> 2", myTree.getStringRepresentation());
    }

    public void testFunExpression() throws Exception {
        myTree.addNode(2, FUN_EXPRESSION);
        myTree.addNode(3, FUN_KEYWORD);
        myTree.addNode(3, PARAMETER);
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "x");
        myTree.addNode(3, PARAMETER);
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "y");
        myTree.addNode(3, PARAMETER);
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "z");
        myTree.addNode(3, MINUS_GT);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "2");

        doTest("fun x y z -> 2", myTree.getStringRepresentation());
    }

    public void testTryExpression() throws Exception {
        myTree.addNode(2, TRY_EXPRESSION);
        myTree.addNode(3, TRY_KEYWORD);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, LPAR);
        myTree.addNode(4, RPAR);
        myTree.addNode(3, WITH_KEYWORD);
        myTree.addNode(3, VBAR);
        myTree.addNode(3, PATTERN_MATCHING);
        myTree.addNode(4, CONSTRUCTOR_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "A");
        myTree.addNode(4, WHEN_KEYWORD);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, TRUE_KEYWORD);
        myTree.addNode(4, MINUS_GT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, RPAR);
        myTree.addNode(3, VBAR);
        myTree.addNode(3, PATTERN_MATCHING);
        myTree.addNode(4, CONSTRUCTOR_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "B");
        myTree.addNode(4, WHEN_KEYWORD);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, FALSE_KEYWORD);
        myTree.addNode(4, MINUS_GT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, RPAR);

        doTest("try () with | A when true -> () | B when false -> ()", myTree.getStringRepresentation());
    }

    public void testNewInstanceExpression() throws Exception {
        myTree.addNode(2, NEW_INSTANCE_EXPRESSION);
        myTree.addNode(3, NEW_KEYWORD);
        myTree.addNode(3, CLASS_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "myClass");

        doTest("new myClass", myTree.getStringRepresentation());
    }

    public void testObjectClassBodyEndExpression() throws Exception {
        myTree.addNode(2, OBJECT_CLASS_BODY_END_EXPRESSION);
        myTree.addNode(3, OBJECT_KEYWORD);
        myTree.addNode(3, END_KEYWORD);

        doTest("object end", myTree.getStringRepresentation());

        recreateTree();
        
        myTree.addNode(2, OBJECT_CLASS_BODY_END_EXPRESSION);
        myTree.addNode(3, OBJECT_KEYWORD);
        myTree.addNode(3, VALUE_CLASS_FIELD_DEFINITION);
        myTree.addNode(4, VAL_KEYWORD);
        myTree.addNode(4, INST_VAR_NAME_DEFINITION);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "0");
        myTree.addNode(3, END_KEYWORD);

        doTest("object val a = 0 end", myTree.getStringRepresentation());
    }

    public void testClassMethodAccessingExpression() throws Exception {
        myTree.addNode(2, CLASS_METHOD_ACCESSING_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, HASH);
        myTree.addNode(3, METHOD_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");

        doTest("a#b", myTree.getStringRepresentation());
    }

    public void testInstanceDuplicatingExpression() throws Exception {
        myTree.addNode(2, INSTANCE_DUPLICATING_EXPRESSION);
        myTree.addNode(3, LBRACE_LT);
        myTree.addNode(3, INST_VAR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, EQ);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "0");
        myTree.addNode(3, SEMICOLON);
        myTree.addNode(3, INST_VAR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");
        myTree.addNode(3, EQ);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, CHAR_LITERAL, "'q'");
        myTree.addNode(3, GT_RBRACE);

        doTest("{< a = 0; b = 'q' >}", myTree.getStringRepresentation());
    }

    public void testAssertExpression() throws Exception {
        myTree.addNode(2, ASSERT_EXPRESSION);
        myTree.addNode(3, ASSERT_KEYWORD);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, TRUE_KEYWORD);

        doTest("assert true", myTree.getStringRepresentation());
    }

    public void testLazyExpression() throws Exception {
        myTree.addNode(2, LAZY_EXPRESSION);
        myTree.addNode(3, LAZY_KEYWORD);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "1");

        doTest("lazy 1", myTree.getStringRepresentation());
    }

    public void testParameter() throws Exception {
        myTree.addNode(2, LET_STATEMENT);
        myTree.addNode(3, LET_KEYWORD);
        myTree.addNode(3, LET_BINDING);
        myTree.addNode(4, LET_BINDING_PATTERN);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "b");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "d");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "e");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "f");
        myTree.addNode(5, COLON);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "q");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "g");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "h");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "i");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "j");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, LABEL_DEFINITION);
        myTree.addNode(7, LABEL_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "k");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "l");
        myTree.addNode(5, COLON);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "q");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(5, COLON);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "n");
        myTree.addNode(5, COLON);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "o");
        myTree.addNode(5, COLON);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, QUEST);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "p");
        myTree.addNode(5, COLON);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "q");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(6, RPAR);
        myTree.addNode(4, EQ);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "0");

        doTest("let a b ~c ~(d) ~(e: int) ~f: q ?g ?(h) ?(i: int) ?(j = 0) ?(k: int = 0) ?l: q ?m: (q) ?n: (q: int) ?o: (q = 0) ?p: (q: int = 0) = 0", myTree.getStringRepresentation());
    }

    public void testUnaryMinusAndFunctionApplication() throws Exception {
        myTree.addNode(2, BINARY_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, MINUS);
        myTree.addNode(3, CONSTANT);
        myTree.addNode(4, INTEGER_LITERAL, "2");

        doTest("a -2", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, BINARY_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, MINUS);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");

        doTest("a -b", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, FUNCTION_APPLICATION_EXPRESSION);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, ARGUMENT);
        myTree.addNode(4, PARENTHESES);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, UNARY_EXPRESSION);
        myTree.addNode(6, MINUS);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, RPAR);

        doTest("a (-b)", myTree.getStringRepresentation());
    }

    public void testPriorities() throws Exception {
        String expr1 = getTreeIgnoringParenthess("let a = let x = 0 in (); let y = 0 in y in (); let p = 0 in p");
        String expr2 = getTreeIgnoringParenthess("let a = (let x = 0 in ((); (let y = 0 in y))) in ((); (let p = 0 in p))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("match (); match 1 with _ -> 1 with _ -> (); match 1 with _ -> (); match 1 with _ -> 1");
        expr2 = getTreeIgnoringParenthess("match ((); (match 1 with _ -> 1)) with _ -> ((); (match 1 with _ -> ((); (match 1 with _ -> 1))))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("try (); try () with _ -> () with _ -> (); try () with _ -> ()");
        expr2 = getTreeIgnoringParenthess("try ((); (try () with _ -> ())) with _ -> ((); (try () with _ -> ()))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("fun a -> (); fun b -> 1");
        expr2 = getTreeIgnoringParenthess("fun a -> ((); (fun b -> 1))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("function a -> (); function b -> 1");
        expr2 = getTreeIgnoringParenthess("function a -> ((); (function b -> 1))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("if true then (); if true then () else (); if true then ()");
        expr2 = getTreeIgnoringParenthess("(if true then ()); (if true then () else ()); (if true then ())");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("for i = 0 to 1 do (); for i = 0 to 1 do () done done; for i = 0 to 1 do () done");
        expr2 = getTreeIgnoringParenthess("(for i = 0 to 1 do ((); (for i = 0 to 1 do () done)) done); (for i = 0 to 1 do () done)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("while true do (); while true do () done done; while true do () done");
        expr2 = getTreeIgnoringParenthess("(while true do ((); (while true do () done)) done); (while true do () done)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("if true then a <- 1;; if true then () else a <- 1;; if true then a := 1;; if true then () else a := 1");
        expr2 = getTreeIgnoringParenthess("(if true then (a <- 1));; (if true then () else (a <- 1));; (if true then (a := 1));; (if true then () else (a := 1))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("for i = 0 to 1 do a <- 1 done;; for i = 0 to 1 do a := 1 done");
        expr2 = getTreeIgnoringParenthess("(for i = 0 to 1 do (a <- 1) done);; (for i = 0 to 1 do (a := 1) done)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("while true do a <- 1 done;; while true do a := 1 done");
        expr2 = getTreeIgnoringParenthess("(while true do (a <- 1) done);; (while true do (a := 1) done)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a <- b, c;; a := b, c");
        expr2 = getTreeIgnoringParenthess("(a <- (b, c));; (a := (b, c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a, b or c;; a, b || c");
        expr2 = getTreeIgnoringParenthess("(a, (b or c));; (a, (b || c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a or b & c;; a or b && c;; a || b & c;; a || b && c");
        expr2 = getTreeIgnoringParenthess("(a or (b & c));; (a or (b && c));; (a || (b & c));; (a || (b && c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a & b = c;; a & b =@ c;; a & b < c;; a & b <@ c;; a & b > c;; a & b >@ c;; a & b |@ c;; a & b &@ c;; a & b $ c;; a & b $@ c");
        expr2 = getTreeIgnoringParenthess("(a & (b = c));; (a & (b =@ c));; (a & (b < c));; (a & (b <@ c));; (a & (b > c));; (a & (b >@ c));; (a & (b |@ c));; (a & (b &@ c));; (a & (b $ c));; (a & (b $@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a && b = c;; a && b =@ c;; a && b < c;; a && b <@ c;; a && b > c;; a && b >@ c;; a && b |@ c;; a && b &@ c;; a && b $ c;; a && b $@ c");
        expr2 = getTreeIgnoringParenthess("(a && (b = c));; (a && (b =@ c));; (a && (b < c));; (a && (b <@ c));; (a && (b > c));; (a && (b >@ c));; (a && (b |@ c));; (a && (b &@ c));; (a && (b $ c));; (a && (b $@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a = b @ c;; a =@ b @ c;; a < b @ c;; a <@ b @ c;; a > b @ c;; a >@ b @ c;; a |@ b @ c;; a &@ b @ c;; a $ b @ c;; a $@ b @ c");
        expr2 = getTreeIgnoringParenthess("(a = (b @ c));; (a =@ (b @ c));; (a < (b @ c));; (a <@ (b @ c));; (a > (b @ c));; (a >@ (b @ c));; (a |@ (b @ c));; (a &@ (b @ c));; (a $ (b @ c));; (a $@ (b @ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a = b @@ c;; a =@ b @@ c;; a < b @@ c;; a <@ b @@ c;; a > b @@ c;; a >@ b @@ c;; a |@ b @@ c;; a &@ b @@ c;; a $ b @@ c;; a $@ b @@ c");
        expr2 = getTreeIgnoringParenthess("(a = (b @@ c));; (a =@ (b @@ c));; (a < (b @@ c));; (a <@ (b @@ c));; (a > (b @@ c));; (a >@ (b @@ c));; (a |@ (b @@ c));; (a &@ (b @@ c));; (a $ (b @@ c));; (a $@ (b @@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a = b ^ c;; a =@ b ^ c;; a < b ^ c;; a <@ b ^ c;; a > b ^ c;; a >@ b ^ c;; a |@ b ^ c;; a &@ b ^ c;; a $ b ^ c;; a $@ b ^ c");
        expr2 = getTreeIgnoringParenthess("(a = (b ^ c));; (a =@ (b ^ c));; (a < (b ^ c));; (a <@ (b ^ c));; (a > (b ^ c));; (a >@ (b ^ c));; (a |@ (b ^ c));; (a &@ (b ^ c));; (a $ (b ^ c));; (a $@ (b ^ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a = b ^@ c;; a =@ b ^@ c;; a < b ^@ c;; a <@ b ^@ c;; a > b ^@ c;; a >@ b ^@ c;; a |@ b ^@ c;; a &@ b ^@ c;; a $ b ^@ c;; a $@ b ^@ c");
        expr2 = getTreeIgnoringParenthess("(a = (b ^@ c));; (a =@ (b ^@ c));; (a < (b ^@ c));; (a <@ (b ^@ c));; (a > (b ^@ c));; (a >@ (b ^@ c));; (a |@ (b ^@ c));; (a &@ (b ^@ c));; (a $ (b ^@ c));; (a $@ (b ^@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a @ b :: c;; a @@ b :: c;; a ^ b :: c;; a ^@ b :: c");
        expr2 = getTreeIgnoringParenthess("(a @ (b :: c));; (a @@ (b :: c));; (a ^ (b :: c));; (a ^@ (b :: c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a :: b + c;; a :: b +@ c;; a :: b - c;; a :: b -@ c");
        expr2 = getTreeIgnoringParenthess("(a :: (b + c));; (a :: (b +@ c));; (a :: (b - c));; (a :: (b -@ c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a + b * c;; a + b *@ c;; a + b / c;; a + b /@ c;; a + b % c;; a + b %@ c;; a + b mod c;; a + b land c;; a + b lor c;; a + b lxor c");
        expr2 = getTreeIgnoringParenthess("(a + (b * c));; (a + (b *@ c));; (a + (b / c));; (a + (b /@ c));; (a + (b % c));; (a + (b %@ c));; (a + (b mod c));; (a + (b land c));; (a + (b lor c));; (a + (b lxor c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a +@ b * c;; a +@ b *@ c;; a +@ b / c;; a +@ b /@ c;; a +@ b % c;; a +@ b %@ c;; a +@ b mod c;; a +@ b land c;; a +@ b lor c;; a +@ b lxor c");
        expr2 = getTreeIgnoringParenthess("(a +@ (b * c));; (a +@ (b *@ c));; (a +@ (b / c));; (a +@ (b /@ c));; (a +@ (b % c));; (a +@ (b %@ c));; (a +@ (b mod c));; (a +@ (b land c));; (a +@ (b lor c));; (a +@ (b lxor c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a - b * c;; a - b *@ c;; a - b / c;; a - b /@ c;; a - b % c;; a - b %@ c;; a - b mod c;; a - b land c;; a - b lor c;; a - b lxor c");
        expr2 = getTreeIgnoringParenthess("(a - (b * c));; (a - (b *@ c));; (a - (b / c));; (a - (b /@ c));; (a - (b % c));; (a - (b %@ c));; (a - (b mod c));; (a - (b land c));; (a - (b lor c));; (a - (b lxor c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a -@ b * c;; a -@ b *@ c;; a -@ b / c;; a -@ b /@ c;; a -@ b % c;; a -@ b %@ c;; a -@ b mod c;; a -@ b land c;; a -@ b lor c;; a -@ b lxor c");
        expr2 = getTreeIgnoringParenthess("(a -@ (b * c));; (a -@ (b *@ c));; (a -@ (b / c));; (a -@ (b /@ c));; (a -@ (b % c));; (a -@ (b %@ c));; (a -@ (b mod c));; (a -@ (b land c));; (a -@ (b lor c));; (a -@ (b lxor c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a * b ** c;; a * b **@ c;; a * b lsl c;; a * b lsr c;; a * b asr c;; a *@ b ** c;; a *@ b **@ c;; a *@ b lsl c;; a *@ b lsr c;; a *@ b asr c");
        expr2 = getTreeIgnoringParenthess("(a * (b ** c));; (a * (b **@ c));; (a * (b lsl c));; (a * (b lsr c));; (a * (b asr c));; (a *@ (b ** c));; (a *@ (b **@ c));; (a *@ (b lsl c));; (a *@ (b lsr c));; (a *@ (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a / b ** c;; a / b **@ c;; a / b lsl c;; a / b lsr c;; a / b asr c;; a /@ b ** c;; a /@ b **@ c;; a /@ b lsl c;; a /@ b lsr c;; a /@ b asr c");
        expr2 = getTreeIgnoringParenthess("(a / (b ** c));; (a / (b **@ c));; (a / (b lsl c));; (a / (b lsr c));; (a / (b asr c));; (a /@ (b ** c));; (a /@ (b **@ c));; (a /@ (b lsl c));; (a /@ (b lsr c));; (a /@ (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a % b ** c;; a % b **@ c;; a % b lsl c;; a % b lsr c;; a % b asr c;; a %@ b ** c;; a %@ b **@ c;; a %@ b lsl c;; a %@ b lsr c;; a %@ b asr c");
        expr2 = getTreeIgnoringParenthess("(a % (b ** c));; (a % (b **@ c));; (a % (b lsl c));; (a % (b lsr c));; (a % (b asr c));; (a %@ (b ** c));; (a %@ (b **@ c));; (a %@ (b lsl c));; (a %@ (b lsr c));; (a %@ (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a mod b ** c;; a mod b **@ c;; a mod b lsl c;; a mod b lsr c;; a mod b asr c");
        expr2 = getTreeIgnoringParenthess("(a mod (b ** c));; (a mod (b **@ c));; (a mod (b lsl c));; (a mod (b lsr c));; (a mod (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a land b ** c;; a land b **@ c;; a land b lsl c;; a land b lsr c;; a land b asr c");
        expr2 = getTreeIgnoringParenthess("(a land (b ** c));; (a land (b **@ c));; (a land (b lsl c));; (a land (b lsr c));; (a land (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a lor b ** c;; a lor b **@ c;; a lor b lsl c;; a lor b lsr c;; a lor b asr c");
        expr2 = getTreeIgnoringParenthess("(a lor (b ** c));; (a lor (b **@ c));; (a lor (b lsl c));; (a lor (b lsr c));; (a lor (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a lxor b ** c;; a lxor b **@ c;; a lxor b lsl c;; a lxor b lsr c;; a lxor b asr c");
        expr2 = getTreeIgnoringParenthess("(a lxor (b ** c));; (a lxor (b **@ c));; (a lxor (b lsl c));; (a lxor (b lsr c));; (a lxor (b asr c))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("-a ** b;; -.a ** b;; -a lsl b;; -.a lsl b;; -a lsr b;; -.a lsr b;; -a asr b;; -.a asr b");
        expr2 = getTreeIgnoringParenthess("((-a) ** b);; ((-.a) ** b);; ((-a) lsl b);; ((-.a) lsl b);; ((-a) lsr b);; ((-.a) lsr b);; ((-a) asr b);; ((-.a) asr b)");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("-a b;; -Constr d;; -assert a;; -lazy a");
        expr2 = getTreeIgnoringParenthess("(-(a b));; (-(Constr d));; (-(assert a));; (-(lazy a))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("a b.c;; a b.(c);; a b.[c];; A b.c;; A b.(c);; A b.[c];; assert b.c;; assert b.(c);; assert b.[c];; lazy b.c;; lazy b.(c);; lazy b.[c]");
        expr2 = getTreeIgnoringParenthess("(a (b.c));; (a (b.(c)));; (a (b.[c]));; (A (b.c));; (A (b.(c)));; (A (b.[c]));; (assert (b.c));; (assert (b.(c)));; (assert (b.[c]));; (lazy (b.c));; (lazy (b.(c)));; (lazy (b.[c]))");

        assertEquals(expr1, expr2);

        expr1 = getTreeIgnoringParenthess("!a.b;; !@a.b;; ?@a.b;; ~@a.b;; !a.(b);; !@a.(b);; ?@a.(b);; ~@a.(b);; !a.[b];; !@a.[b];; ?@a.[b];; ~@a.[b]");
        expr2 = getTreeIgnoringParenthess("((!a).b);; ((!@a).b);; ((?@a).b);; ((~@a).b);; ((!a).(b));; ((!@a).(b));; ((?@a).(b));; ((~@a).(b));; ((!a).[b]);; ((!@a).[b]);; ((?@a).[b]);; ((~@a).[b])");

        assertEquals(expr1, expr2);
    }

    public void testPossibilities() throws Exception {
        assertIsAllowed("(let x = 0 in x)");
        assertIsAllowed("(();1)");
        assertIsAllowed("begin let x = 0 in x end");
        assertIsAllowed("begin ();1 end");
        assertIsAllowed("(let x = 0 in x : int)");
        assertIsAllowed("(();1 : int)");
        assertIsAllowed("a, let x = 0 in x");
        assertIsAllowed("a, ();1");
        assertIsNotAllowed("a let x = 0 in x");
        assertIsNotAllowed("A let x = 0 in x");
        assertIsNotAllowed("`tag let x = 0 in x");
        assertIsAllowed("1 :: let x = 0 in []");
        assertIsAllowed("1 :: ();[]");
        assertIsAllowed("[7; let x = 0 in x]");
        assertIsAllowed("[|7; let x = 0 in x|]");
        assertIsAllowed("{a = let x = 0 in x}");
        assertIsNotAllowed("{a = ();1}");
        assertIsNotAllowed("{let x = 0 in x with a = 0}");
        assertIsNotAllowed("{();b with a = 0}");
        assertIsNotAllowed("a let x = 0 in x");
        assertIsNotAllowed("!! let x = 0 in x");
        assertIsAllowed("let x = 0 in x + let x = 0 in x");
        assertIsAllowed("a <- let x = 0 in x");
        assertIsAllowed("a <- ();1");
        assertIsAllowed("a.(let x = 0 in x)");
        assertIsAllowed("a.(();1)");
        assertIsAllowed("a.[let x = 0 in x]");
        assertIsAllowed("a.[();1]");
        assertIsAllowed("if let x = 0 in true then ()");
        assertIsAllowed("if ();true then ()");
        assertIsAllowed("if true then let x = 0 in x");
        assertIsAllowed("if true then () else let x = 0 in x");
        assertIsAllowed("while let x = 0 in true do () done");
        assertIsAllowed("while ();true do () done");
        assertIsAllowed("while true do let x = 0 in () done");
        assertIsAllowed("while true do ();() done");
        assertIsAllowed("for i = let x = 0 in x to let x = 0 in x do () done");
        assertIsAllowed("for i = ();0 to ();0 do () done");
        assertIsAllowed("for i = 0 to 0 do let x = 0 in () done");
        assertIsAllowed("for i = 0 to 0 do ();() done");
        assertIsAllowed("();let x = 0 in x");
        assertIsAllowed("match let x = 0 in x with _ -> 0");
        assertIsAllowed("match ();0 with _ -> 0");
        assertIsAllowed("match 0 with _ -> let x = 0 in x");
        assertIsAllowed("match 0 with _ -> ();0");
        assertIsAllowed("function a -> let x = 0 in x");
        assertIsAllowed("function a -> ();0");
        assertIsAllowed("fun a -> let x = 0 in x");
        assertIsAllowed("fun a -> ();0");
        assertIsAllowed("try let x = 0 in 0 with _ -> 0");
        assertIsAllowed("try ();0 with _ -> 0");
        assertIsAllowed("try 0 with _ -> let x = 0 in x");
        assertIsAllowed("try 0 with _ -> ();0");
        assertIsAllowed("let a = let x = 0 in x in a");
        assertIsAllowed("let a = ();0 in a");
        assertIsAllowed("let a = 0 in let x = 0 in x");
        assertIsAllowed("let a = 0 in ();a");
        assertIsAllowed("(let x = 0 in x :> int)");
        assertIsAllowed("(();0 :> int)");
        assertIsAllowed("(let x = 0 in x : int :> int)");
        assertIsAllowed("(();0 : int :> int)");
        assertIsAllowed("{< a = let x = 0 in x >}");
        assertIsNotAllowed("{< a = ();1 >}");
        assertIsNotAllowed("assert let x = 0 in true");
        assertIsNotAllowed("lazy let x = 0 in true");
    }

    public void testAssociativity() throws Exception {
        doTestRightAssociativity("or");
        doTestRightAssociativity("||");
        doTestRightAssociativity("&");
        doTestRightAssociativity("&&");
        doTestLeftAssociativity("=");
        doTestLeftAssociativity("=@");
        doTestLeftAssociativity("<");
        doTestLeftAssociativity("<@");
        doTestLeftAssociativity(">");
        doTestLeftAssociativity(">@");
        doTestLeftAssociativity("|@");
        doTestLeftAssociativity("&@");
        doTestLeftAssociativity("$");
        doTestLeftAssociativity("$@");
        doTestRightAssociativity("@");
        doTestRightAssociativity("@@");
        doTestRightAssociativity("^");
        doTestRightAssociativity("^@");
        doTestRightAssociativity("::");
        doTestLeftAssociativity("+");
        doTestLeftAssociativity("+@");
        doTestLeftAssociativity("-");
        doTestLeftAssociativity("-@");
        doTestLeftAssociativity("*");
        doTestLeftAssociativity("*@");
        doTestLeftAssociativity("/");
        doTestLeftAssociativity("/@");
        doTestLeftAssociativity("%");
        doTestLeftAssociativity("%@");
        doTestLeftAssociativity("mod");
        doTestLeftAssociativity("land");
        doTestLeftAssociativity("lor");
        doTestLeftAssociativity("lxor");
        doTestRightAssociativity("**");
        doTestRightAssociativity("**@");
        doTestRightAssociativity("lsl");
        doTestRightAssociativity("lsr");
        doTestRightAssociativity("asr");
        doTestRightAssociativity("asr");
        doTestRightAssociativity("<-");
        doTestRightAssociativity(":=");             
    }

    private void doTestLeftAssociativity(@NotNull final String operator) throws Exception {
        final String expr1 = getTreeIgnoringParenthess("a " + operator + " b " + operator + " c");
        final String expr2 = getTreeIgnoringParenthess("((a " + operator + " b) " + operator + " c)");

        assertEquals(expr1, expr2);
    }

    private void doTestRightAssociativity(@NotNull final String operator) throws Exception {
        final String expr1 = getTreeIgnoringParenthess("a " + operator + " b " + operator + " c");
        final String expr2 = getTreeIgnoringParenthess("(a " + operator + " (b " + operator + " c))");

        assertEquals(expr1, expr2);
    }
}
