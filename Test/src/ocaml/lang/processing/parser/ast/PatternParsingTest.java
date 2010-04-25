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
import org.testng.annotations.Test;

import static ocaml.lang.processing.lexer.token.OCamlTokenTypes.*;
import static ocaml.lang.processing.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 19.03.2009
 */
@Test
public class PatternParsingTest extends MLParsingTestCase {
    public void testValueName() throws Exception {
        addStartNodes();
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LCFC_IDENTIFIER, "a");
        addEndNodes();

        doTest("match a with a -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(4, VALUE_NAME_PATTERN);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, OPERATOR_NAME);
        myTree.addNode(6, PREFIX_OPERATOR, "!!");
        myTree.addNode(5, RPAR);
        addEndNodes();

        doTest("match a with (!!) -> 0", myTree.getStringRepresentation());
    }

    public void testUnderscore() throws Exception {
        addStartNodes();
        myTree.addNode(4, UNDERSCORE);
        addEndNodes();

        doTest("match a with _ -> 0", myTree.getStringRepresentation());
    }

    public void testCharRange() throws Exception {
        addStartNodes();
        myTree.addNode(4, CHAR_RANGE_PATTERN);
        myTree.addNode(5, CHAR_LITERAL, "'a'");
        myTree.addNode(5, DOT_DOT);
        myTree.addNode(5, CHAR_LITERAL, "'z'");
        addEndNodes();

        doTest("match a with 'a' .. 'z' -> 0", myTree.getStringRepresentation());
    }

    public void testEmptyListPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, LIST_PATTERN);
        myTree.addNode(5, LBRACKET);
        myTree.addNode(5, RBRACKET);
        addEndNodes();

        doTest("match a with [] -> 0", myTree.getStringRepresentation());
    }

    public void testEmptyArrayPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, ARRAY_PATTERN);
        myTree.addNode(5, LBRACKET_VBAR);
        myTree.addNode(5, VBAR_RBRACKET);
        addEndNodes();

        doTest("match a with [||] -> 0", myTree.getStringRepresentation());
    }

    public void testConstant() throws Exception {
        addStartNodes();
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "1");
        addEndNodes();

        doTest("match a with 1 -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, RPAR);
        addEndNodes();

        doTest("match a with () -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(4, CONSTRUCTOR_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Constr");
        addEndNodes();

        doTest("match a with Constr -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Tag");
        addEndNodes();

        doTest("match a with `Tag -> 0", myTree.getStringRepresentation());

        recreateTree();

        addStartNodes();
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "tag");
        addEndNodes();

        doTest("match a with `tag -> 0", myTree.getStringRepresentation());
    }

    public void testAsPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, AS_PATTERN);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, AS_KEYWORD);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "a");
        addEndNodes();

        doTest("match a with 1 as a -> 0", myTree.getStringRepresentation());
    }

    public void testPatternInParenthess() throws Exception {
        addStartNodes();
        myTree.addNode(4, PARENTHESES);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, RPAR);
        addEndNodes();

        doTest("match a with (1) -> 0", myTree.getStringRepresentation());
    }

    public void testTypeConstraintPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, TYPE_CONSTRAINT_PATTERN);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, COLON);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, RPAR);
        addEndNodes();

        doTest("match a with (1 : int) -> 0", myTree.getStringRepresentation());
    }

    public void testOrPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, OR_PATTERN);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, VBAR);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "2");
        addEndNodes();

        doTest("match a with 1 | 2 -> 0", myTree.getStringRepresentation());
    }

    public void testConstructorApplicationPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, CONSTRUCTOR_APPLICATION_PATTERN);
        myTree.addNode(5, CONSTRUCTOR_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Constr");
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        addEndNodes();

        doTest("match a with Constr 1 -> 0", myTree.getStringRepresentation());
    }

    public void testTaggedPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, TAGGED_PATTERN);
        myTree.addNode(5, ACCENT);
        myTree.addNode(5, TAG_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "tag");
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        addEndNodes();

        doTest("match a with `tag 1 -> 0", myTree.getStringRepresentation());
    }

    public void testTypeConstructorPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, TYPE_CONSTRUCTOR_PATTERN);
        myTree.addNode(5, HASH);
        myTree.addNode(5, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "ttt");
        addEndNodes();

        doTest("match a with #ttt -> 0", myTree.getStringRepresentation());
    }

    public void testCommaPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, COMMA_PATTERN);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, COMMA);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "2");
        addEndNodes();

        doTest("match a with 1, 2 -> 0", myTree.getStringRepresentation());
    }

    public void testRecordPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, RECORD_PATTERN);
        myTree.addNode(5, LBRACE);
        myTree.addNode(5, RECORD_FIELD_INITIALIZATION_IN_PATTERN);
        myTree.addNode(6, FIELD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, RECORD_FIELD_INITIALIZATION_IN_PATTERN);
        myTree.addNode(6, FIELD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "1");
        myTree.addNode(5, RBRACE);
        addEndNodes();

        doTest("match a with {a = 0; b = 1} -> 0", myTree.getStringRepresentation());
    }

    public void testListPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, LIST_PATTERN);
        myTree.addNode(5, LBRACKET);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "2");
        myTree.addNode(5, RBRACKET);
        addEndNodes();

        doTest("match a with [1; 2] -> 0", myTree.getStringRepresentation());
    }

    public void testHeadTailPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, HEAD_TAIL_PATTERN);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, COLON_COLON);
        myTree.addNode(5, LIST_PATTERN);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "2");
        myTree.addNode(6, RBRACKET);
        addEndNodes();

        doTest("match a with 1 :: [2] -> 0", myTree.getStringRepresentation());
    }

    public void testArrayPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, ARRAY_PATTERN);
        myTree.addNode(5, LBRACKET_VBAR);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        myTree.addNode(5, SEMICOLON);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "2");
        myTree.addNode(5, VBAR_RBRACKET);
        addEndNodes();

        doTest("match a with [|1; 2|] -> 0", myTree.getStringRepresentation());
    }

    public void testLazyPattern() throws Exception {
        addStartNodes();
        myTree.addNode(4, LAZY_PATTERN);
        myTree.addNode(5, LAZY_KEYWORD);
        myTree.addNode(5, CONSTANT);
        myTree.addNode(6, INTEGER_LITERAL, "1");
        addEndNodes();

        doTest("match a with lazy 1 -> 0", myTree.getStringRepresentation());
    }

    public void testPriorities() throws Exception {
        String text1 = getTreeIgnoringParenthess("match a with a | b as c -> 0");
        String text2 = getTreeIgnoringParenthess("match a with ((a | b) as c) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParenthess("match a with a | b, c -> 0");
        text2 = getTreeIgnoringParenthess("match a with (a | (b, c)) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParenthess("match a with a, b | c -> 0");
        text2 = getTreeIgnoringParenthess("match a with ((a, b) | c) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParenthess("match a with a, b :: c -> 0");
        text2 = getTreeIgnoringParenthess("match a with (a, (b :: c)) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParenthess("match a with a :: b, c -> 0");
        text2 = getTreeIgnoringParenthess("match a with ((a :: b), c) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParenthess("match a with a :: Constr b -> 0");
        text2 = getTreeIgnoringParenthess("match a with (a :: (Constr b)) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParenthess("match a with Constr a :: b -> 0");
        text2 = getTreeIgnoringParenthess("match a with ((Constr a) :: b) -> 0");

        assertEquals(text1, text2);
    }

    public void testAssociativity() throws Exception {
        String text1 = getTreeIgnoringParenthess("match a with a | b | c -> 0");
        String text2 = getTreeIgnoringParenthess("match a with ((a | b) | c) -> 0");

        assertEquals(text1, text2);

        text1 = getTreeIgnoringParenthess("match a with a :: b :: c -> 0");
        text2 = getTreeIgnoringParenthess("match a with (a :: (b :: c)) -> 0");

        assertEquals(text1, text2);
    }

    private void addStartNodes() {
        myTree.addNode(2, MATCH_EXPRESSION);
        myTree.addNode(3, MATCH_KEYWORD);
        myTree.addNode(3, VALUE_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, WITH_KEYWORD);
        myTree.addNode(3, PATTERN_MATCHING);
    }

    private void addEndNodes() {
        myTree.addNode(4, MINUS_GT);
        myTree.addNode(4, CONSTANT);
        myTree.addNode(5, INTEGER_LITERAL, "0");
    }
}
