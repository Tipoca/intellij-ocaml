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

package ocaml.lang.processing.lexer;

import ocaml.lang.processing.Strings;
import ocaml.lang.processing.lexer.testCase.LexerTestCase;
import ocaml.lang.processing.lexer.token.OCamlTokenTypes;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 23.02.2009
 */
@Test
public abstract class BaseLexerTest extends LexerTestCase {
    public void testLineTerminator() {
        doTest("\r\n", token(OCamlTokenTypes.WHITE_SPACE, "\r\n"));
        doTest("\n", token(OCamlTokenTypes.WHITE_SPACE, "\n"));
        doTest("\r", token(OCamlTokenTypes.WHITE_SPACE, "\r"));
    }

    public void testWhiteSpaces() {
        doTest(" ", token(OCamlTokenTypes.WHITE_SPACE, " "));
        doTest("\t", token(OCamlTokenTypes.WHITE_SPACE, "\t"));
        doTest("\f", token(OCamlTokenTypes.WHITE_SPACE, "\f"));
    }

    public void testComments() {
        doTest("(*)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, ")"));
        doTest("(**)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(*\n*)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, "\n"), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(***)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, "*"), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(* *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(* aa *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " aa "), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("( * )", token(OCamlTokenTypes.LPAR, "("), token(OCamlTokenTypes.WHITE_SPACE, " "), token(OCamlTokenTypes.MULT, "*"), token(OCamlTokenTypes.WHITE_SPACE, " "), token(OCamlTokenTypes.RPAR, ")"));
        doTest("(* *) *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"), token(OCamlTokenTypes.WHITE_SPACE, " "), token(OCamlTokenTypes.MULT, "*"), token(OCamlTokenTypes.RPAR, ")"));
        doTest("(* (* *) *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(*(**)*)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT_END, "*)"), token(OCamlTokenTypes.COMMENT_END, "*)"));
        doTest("(* (* *)", token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_BEGIN, "(*"), token(OCamlTokenTypes.COMMENT, " "), token(OCamlTokenTypes.COMMENT_END, "*)"));
    }

    public void testIntegerLiteral() {
        doTest("2", token(OCamlTokenTypes.INTEGER_LITERAL, "2"));
        doTest("-2", token(OCamlTokenTypes.MINUS, Strings.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "2"));
        doTest("-2_300", token(OCamlTokenTypes.MINUS, Strings.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "2_300"));
        doTest("002_300", token(OCamlTokenTypes.INTEGER_LITERAL, "002_300"));
        doTest("-0x02_48_AB_CD_EF", token(OCamlTokenTypes.MINUS, Strings.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0x02_48_AB_CD_EF"));
        doTest("-0X02_48_AB_CD_EF", token(OCamlTokenTypes.MINUS, Strings.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0X02_48_AB_CD_EF"));
        doTest("-0o02_47", token(OCamlTokenTypes.MINUS, Strings.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0o02_47"));
        doTest("-0O02_47", token(OCamlTokenTypes.MINUS, Strings.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0O02_47"));
        doTest("-0b00_11", token(OCamlTokenTypes.MINUS, Strings.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0b00_11"));
        doTest("-0B11_00", token(OCamlTokenTypes.MINUS, Strings.MINUS), token(OCamlTokenTypes.INTEGER_LITERAL, "0B11_00"));
        doTest("12A", token(OCamlTokenTypes.INTEGER_LITERAL, "12"), token(OCamlTokenTypes.UCFC_IDENTIFIER, "A"));
        doTest("0x12T", token(OCamlTokenTypes.INTEGER_LITERAL, "0x12"), token(OCamlTokenTypes.UCFC_IDENTIFIER, "T"));
        doTest("0o128", token(OCamlTokenTypes.INTEGER_LITERAL, "0o12"), token(OCamlTokenTypes.INTEGER_LITERAL, "8"));
        doTest("0b12", token(OCamlTokenTypes.INTEGER_LITERAL, "0b1"), token(OCamlTokenTypes.INTEGER_LITERAL, "2"));
        doTest("2l", token(OCamlTokenTypes.INTEGER_LITERAL, "2l"));
        doTest("2L", token(OCamlTokenTypes.INTEGER_LITERAL, "2L"));
        doTest("2n", token(OCamlTokenTypes.INTEGER_LITERAL, "2n"));
        doTest("0b1l", token(OCamlTokenTypes.INTEGER_LITERAL, "0b1l"));
        doTest("0b1L", token(OCamlTokenTypes.INTEGER_LITERAL, "0b1L"));
        doTest("0b1n", token(OCamlTokenTypes.INTEGER_LITERAL, "0b1n"));
        doTest("0o1l", token(OCamlTokenTypes.INTEGER_LITERAL, "0o1l"));
        doTest("0o1L", token(OCamlTokenTypes.INTEGER_LITERAL, "0o1L"));
        doTest("0o1n", token(OCamlTokenTypes.INTEGER_LITERAL, "0o1n"));
        doTest("0x1l", token(OCamlTokenTypes.INTEGER_LITERAL, "0x1l"));
        doTest("0x1L", token(OCamlTokenTypes.INTEGER_LITERAL, "0x1L"));
        doTest("0x1n", token(OCamlTokenTypes.INTEGER_LITERAL, "0x1n"));
    }

    public void testFloatLiteral() {
        doTest("-6_4.", token(OCamlTokenTypes.MINUS, Strings.MINUS), token(OCamlTokenTypes.FLOAT_LITERAL, "6_4."));
        doTest("6._", token(OCamlTokenTypes.FLOAT_LITERAL, "6._"));
        doTest("6.2", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2"));
        doTest("6.2e2", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2e2"));
        doTest("6.2e3_", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2e3_"));
        doTest("6.2e+3", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2e+3"));
        doTest("6.2e-3", token(OCamlTokenTypes.FLOAT_LITERAL, "6.2e-3"));
        doTest("6.E-3", token(OCamlTokenTypes.FLOAT_LITERAL, "6.E-3"));
        doTest("6E3", token(OCamlTokenTypes.FLOAT_LITERAL, "6E3"));
    }

    public void testIdentifier() {
        doTest("pKH_B_wb'y2b8_87'3n_", token(OCamlTokenTypes.LCFC_IDENTIFIER, "pKH_B_wb'y2b8_87'3n_"));
        doTest("K'H_B_wby2b8_873n_", token(OCamlTokenTypes.UCFC_IDENTIFIER, "K'H_B_wby2b8_873n_"));
        doTest("_pKH_B_wby2b8_873n_'", token(OCamlTokenTypes.LCFC_IDENTIFIER, "_pKH_B_wby2b8_873n_'"));
        doTest("_PKH_B_wby2b8_873n_", token(OCamlTokenTypes.LCFC_IDENTIFIER, "_PKH_B_wby2b8_873n_"));
    }

    public void testInfixSymbol() {
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.INFIX_OPERATOR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.EQ));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LT));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.GT));
        assertFalse(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.VBAR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.AMP));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.PLUS));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MINUS));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MULT));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.DOLLAR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.AMP_AMP));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MINUS_DOT));
        assertFalse(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MINUS_GT));
        assertFalse(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LT_MINUS));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.VBAR_VBAR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LT_LT));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LT_COLON));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.GT_GT));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.DOLLAR_DOLLAR));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.DOLLAR_COLON));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.OR_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.COLON_EQ));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.MOD_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LAND_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LOR_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LXOR_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LSL_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.LSR_KEYWORD));
        assertTrue(OCamlTokenTypes.INFIX_OPERATORS.contains(OCamlTokenTypes.ASR_KEYWORD));

        doTest("=", token(OCamlTokenTypes.EQ, Strings.EQ));
        doTest("<", token(OCamlTokenTypes.LT, Strings.LT));
        doTest(">", token(OCamlTokenTypes.GT, Strings.GT));
        doTest("@", token(OCamlTokenTypes.INFIX_OPERATOR, "@"));
        doTest("^", token(OCamlTokenTypes.INFIX_OPERATOR, "^"));
        doTest("&", token(OCamlTokenTypes.AMP, Strings.AMP));
        doTest("+", token(OCamlTokenTypes.PLUS, Strings.PLUS));
        doTest("-", token(OCamlTokenTypes.MINUS, Strings.MINUS));
        doTest("*", token(OCamlTokenTypes.MULT, Strings.MULT));
        doTest("/", token(OCamlTokenTypes.INFIX_OPERATOR, "/"));
        doTest("$", token(OCamlTokenTypes.DOLLAR, Strings.DOLLAR));
        doTest("%", token(OCamlTokenTypes.INFIX_OPERATOR, "%"));

        doTest("=!", token(OCamlTokenTypes.INFIX_OPERATOR, "=!"));
        doTest("<!", token(OCamlTokenTypes.INFIX_OPERATOR, "<!"));
        doTest(">!", token(OCamlTokenTypes.INFIX_OPERATOR, ">!"));
        doTest("@!", token(OCamlTokenTypes.INFIX_OPERATOR, "@!"));
        doTest("^!", token(OCamlTokenTypes.INFIX_OPERATOR, "^!"));
        doTest("|!", token(OCamlTokenTypes.INFIX_OPERATOR, "|!"));
        doTest("&!", token(OCamlTokenTypes.INFIX_OPERATOR, "&!"));
        doTest("+!", token(OCamlTokenTypes.INFIX_OPERATOR, "+!"));
        doTest("-!", token(OCamlTokenTypes.INFIX_OPERATOR, "-!"));
        doTest("*!", token(OCamlTokenTypes.INFIX_OPERATOR, "*!"));
        doTest("/!", token(OCamlTokenTypes.INFIX_OPERATOR, "/!"));
        doTest("$!", token(OCamlTokenTypes.INFIX_OPERATOR, "$!"));
        doTest("%!", token(OCamlTokenTypes.INFIX_OPERATOR, "%!"));

        doTest("%!", token(OCamlTokenTypes.INFIX_OPERATOR, "%!"));
        doTest("%$", token(OCamlTokenTypes.INFIX_OPERATOR, "%$"));
        doTest("%%", token(OCamlTokenTypes.INFIX_OPERATOR, "%%"));
        doTest("%&", token(OCamlTokenTypes.INFIX_OPERATOR, "%&"));
        doTest("%*", token(OCamlTokenTypes.INFIX_OPERATOR, "%*"));
        doTest("%+", token(OCamlTokenTypes.INFIX_OPERATOR, "%+"));
        doTest("%-", token(OCamlTokenTypes.INFIX_OPERATOR, "%-"));
        doTest("%.", token(OCamlTokenTypes.INFIX_OPERATOR, "%."));
        doTest("%/", token(OCamlTokenTypes.INFIX_OPERATOR, "%/"));
        doTest("%:", token(OCamlTokenTypes.INFIX_OPERATOR, "%:"));
        doTest("%<", token(OCamlTokenTypes.INFIX_OPERATOR, "%<"));
        doTest("%=", token(OCamlTokenTypes.INFIX_OPERATOR, "%="));
        doTest("%>", token(OCamlTokenTypes.INFIX_OPERATOR, "%>"));
        doTest("%?", token(OCamlTokenTypes.INFIX_OPERATOR, "%?"));
        doTest("%@", token(OCamlTokenTypes.INFIX_OPERATOR, "%@"));
        doTest("%^", token(OCamlTokenTypes.INFIX_OPERATOR, "%^"));
        doTest("%|", token(OCamlTokenTypes.INFIX_OPERATOR, "%|"));
        doTest("%~", token(OCamlTokenTypes.INFIX_OPERATOR, "%~"));

        doTest("&&", token(OCamlTokenTypes.AMP_AMP, Strings.AMP_AMP));
        doTest("-.", token(OCamlTokenTypes.MINUS_DOT, Strings.MINUS_DOT));
        doTest("<-", token(OCamlTokenTypes.LT_MINUS, Strings.LT_MINUS));
        doTest("||", token(OCamlTokenTypes.VBAR_VBAR, Strings.VBAR_VBAR));
        doTest("<<", token(OCamlTokenTypes.LT_LT, Strings.LT_LT));
        doTest("<:", token(OCamlTokenTypes.LT_COLON, Strings.LT_COLON));
        doTest(">>", token(OCamlTokenTypes.GT_GT, Strings.GT_GT));
        doTest("$$", token(OCamlTokenTypes.DOLLAR_DOLLAR, Strings.DOLLAR_DOLLAR));
        doTest("$:", token(OCamlTokenTypes.DOLLAR_COLON, Strings.DOLLAR_COLON));
        doTest("or", token(OCamlTokenTypes.OR_KEYWORD, Strings.OR_KEYWORD));
        doTest(":=", token(OCamlTokenTypes.COLON_EQ, Strings.COLON_EQ));
        doTest("mod", token(OCamlTokenTypes.MOD_KEYWORD, Strings.MOD_KEYWORD));
        doTest("land", token(OCamlTokenTypes.LAND_KEYWORD, Strings.LAND_KEYWORD));
        doTest("lor", token(OCamlTokenTypes.LOR_KEYWORD, Strings.LOR_KEYWORD));
        doTest("lxor", token(OCamlTokenTypes.LXOR_KEYWORD, Strings.LXOR_KEYWORD));
        doTest("lsl", token(OCamlTokenTypes.LSL_KEYWORD, Strings.LSL_KEYWORD));
        doTest("lsr", token(OCamlTokenTypes.LSR_KEYWORD, Strings.LSR_KEYWORD));
        doTest("asr", token(OCamlTokenTypes.ASR_KEYWORD, Strings.ASR_KEYWORD));
    }

    public void testPrefixSymbol() {
        assertTrue(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.PREFIX_OPERATOR));
        assertFalse(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.QUEST));
        assertFalse(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.TILDE));
        assertTrue(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.NOT_EQ));
        assertTrue(OCamlTokenTypes.PREFIX_OPERATORS.contains(OCamlTokenTypes.QUEST_QUEST));

        doTest("!", token(OCamlTokenTypes.PREFIX_OPERATOR, "!"));
        doTest("?", token(OCamlTokenTypes.QUEST, Strings.QUEST));
        doTest("~", token(OCamlTokenTypes.TILDE, Strings.TILDE));

        doTest("!!", token(OCamlTokenTypes.PREFIX_OPERATOR, "!!"));
        doTest("?!", token(OCamlTokenTypes.PREFIX_OPERATOR, "?!"));
        doTest("~!", token(OCamlTokenTypes.PREFIX_OPERATOR, "~!"));

        doTest("~!", token(OCamlTokenTypes.PREFIX_OPERATOR, "~!"));
        doTest("~$", token(OCamlTokenTypes.PREFIX_OPERATOR, "~$"));
        doTest("~%", token(OCamlTokenTypes.PREFIX_OPERATOR, "~%"));
        doTest("~&", token(OCamlTokenTypes.PREFIX_OPERATOR, "~&"));
        doTest("~*", token(OCamlTokenTypes.PREFIX_OPERATOR, "~*"));
        doTest("~+", token(OCamlTokenTypes.PREFIX_OPERATOR, "~+"));
        doTest("~-", token(OCamlTokenTypes.PREFIX_OPERATOR, "~-"));
        doTest("~.", token(OCamlTokenTypes.PREFIX_OPERATOR, "~."));
        doTest("~/", token(OCamlTokenTypes.PREFIX_OPERATOR, "~/"));
        doTest("~:", token(OCamlTokenTypes.PREFIX_OPERATOR, "~:"));
        doTest("~<", token(OCamlTokenTypes.PREFIX_OPERATOR, "~<"));
        doTest("~=", token(OCamlTokenTypes.PREFIX_OPERATOR, "~="));
        doTest("~>", token(OCamlTokenTypes.PREFIX_OPERATOR, "~>"));
        doTest("~?", token(OCamlTokenTypes.PREFIX_OPERATOR, "~?"));
        doTest("~@", token(OCamlTokenTypes.PREFIX_OPERATOR, "~@"));
        doTest("~^", token(OCamlTokenTypes.PREFIX_OPERATOR, "~^"));
        doTest("~|", token(OCamlTokenTypes.PREFIX_OPERATOR, "~|"));
        doTest("~~", token(OCamlTokenTypes.PREFIX_OPERATOR, "~~"));

        doTest("!=", token(OCamlTokenTypes.NOT_EQ, Strings.NOT_EQ));
        doTest("??", token(OCamlTokenTypes.QUEST_QUEST, Strings.QUEST_QUEST));
    }

    public void testLineNumberDirective() {
        doTest("#2", token(OCamlTokenTypes.WHITE_SPACE, "#2"));
        doTest("# 2 ", token(OCamlTokenTypes.WHITE_SPACE, "# 2 "));
        doTest("# 2 \"file name\"", token(OCamlTokenTypes.WHITE_SPACE, "# 2 \"file name\""));
    }

    public void testKeyword() {
        doTest("and", token(OCamlTokenTypes.AND_KEYWORD, Strings.AND_KEYWORD));
        doTest("as", token(OCamlTokenTypes.AS_KEYWORD, Strings.AS_KEYWORD));
        doTest("assert", token(OCamlTokenTypes.ASSERT_KEYWORD, Strings.ASSERT_KEYWORD));
        doTest("asr", token(OCamlTokenTypes.ASR_KEYWORD, Strings.ASR_KEYWORD));
        doTest("begin", token(OCamlTokenTypes.BEGIN_KEYWORD, Strings.BEGIN_KEYWORD));
        doTest("class", token(OCamlTokenTypes.CLASS_KEYWORD, Strings.CLASS_KEYWORD));
        doTest("constraint", token(OCamlTokenTypes.CONSTRAINT_KEYWORD, Strings.CONSTRAINT_KEYWORD));
        doTest("do", token(OCamlTokenTypes.DO_KEYWORD, Strings.DO_KEYWORD));
        doTest("done", token(OCamlTokenTypes.DONE_KEYWORD, Strings.DONE_KEYWORD));
        doTest("downto", token(OCamlTokenTypes.DOWNTO_KEYWORD, Strings.DOWNTO_KEYWORD));
        doTest("else", token(OCamlTokenTypes.ELSE_KEYWORD, Strings.ELSE_KEYWORD));
        doTest("end", token(OCamlTokenTypes.END_KEYWORD, Strings.END_KEYWORD));
        doTest("exception", token(OCamlTokenTypes.EXCEPTION_KEYWORD, Strings.EXCEPTION_KEYWORD));
        doTest("external", token(OCamlTokenTypes.EXTERNAL_KEYWORD, Strings.EXTERNAL_KEYWORD));
        doTest("false", token(OCamlTokenTypes.FALSE_KEYWORD, Strings.FALSE_KEYWORD));
        doTest("for", token(OCamlTokenTypes.FOR_KEYWORD, Strings.FOR_KEYWORD));
        doTest("fun", token(OCamlTokenTypes.FUN_KEYWORD, Strings.FUN_KEYWORD));
        doTest("function", token(OCamlTokenTypes.FUNCTION_KEYWORD, Strings.FUNCTION_KEYWORD));
        doTest("functor", token(OCamlTokenTypes.FUNCTOR_KEYWORD, Strings.FUNCTOR_KEYWORD));
        doTest("if", token(OCamlTokenTypes.IF_KEYWORD, Strings.IF_KEYWORD));
        doTest("in", token(OCamlTokenTypes.IN_KEYWORD, Strings.IN_KEYWORD));
        doTest("include", token(OCamlTokenTypes.INCLUDE_KEYWORD, Strings.INCLUDE_KEYWORD));
        doTest("inherit", token(OCamlTokenTypes.INHERIT_KEYWORD, Strings.INHERIT_KEYWORD));
        doTest("initializer", token(OCamlTokenTypes.INITIALIZER_KEYWORD, Strings.INITIALIZER_KEYWORD));
        doTest("land", token(OCamlTokenTypes.LAND_KEYWORD, Strings.LAND_KEYWORD));
        doTest("lazy", token(OCamlTokenTypes.LAZY_KEYWORD, Strings.LAZY_KEYWORD));
        doTest("let", token(OCamlTokenTypes.LET_KEYWORD, Strings.LET_KEYWORD));
        doTest("lor", token(OCamlTokenTypes.LOR_KEYWORD, Strings.LOR_KEYWORD));
        doTest("lsl", token(OCamlTokenTypes.LSL_KEYWORD, Strings.LSL_KEYWORD));
        doTest("lsr", token(OCamlTokenTypes.LSR_KEYWORD, Strings.LSR_KEYWORD));
        doTest("lxor", token(OCamlTokenTypes.LXOR_KEYWORD, Strings.LXOR_KEYWORD));
        doTest("match", token(OCamlTokenTypes.MATCH_KEYWORD, Strings.MATCH_KEYWORD));
        doTest("method", token(OCamlTokenTypes.METHOD_KEYWORD, Strings.METHOD_KEYWORD));
        doTest("mod", token(OCamlTokenTypes.MOD_KEYWORD, Strings.MOD_KEYWORD));
        doTest("module", token(OCamlTokenTypes.MODULE_KEYWORD, Strings.MODULE_KEYWORD));
        doTest("mutable", token(OCamlTokenTypes.MUTABLE_KEYWORD, Strings.MUTABLE_KEYWORD));
        doTest("new", token(OCamlTokenTypes.NEW_KEYWORD, Strings.NEW_KEYWORD));
        doTest("object", token(OCamlTokenTypes.OBJECT_KEYWORD, Strings.OBJECT_KEYWORD));
        doTest("of", token(OCamlTokenTypes.OF_KEYWORD, Strings.OF_KEYWORD));
        doTest("open", token(OCamlTokenTypes.OPEN_KEYWORD, Strings.OPEN_KEYWORD));
        doTest("or", token(OCamlTokenTypes.OR_KEYWORD, Strings.OR_KEYWORD));
        doTest("private", token(OCamlTokenTypes.PRIVATE_KEYWORD, Strings.PRIVATE_KEYWORD));
        doTest("rec", token(OCamlTokenTypes.REC_KEYWORD, Strings.REC_KEYWORD));
        doTest("sig", token(OCamlTokenTypes.SIG_KEYWORD, Strings.SIG_KEYWORD));
        doTest("struct", token(OCamlTokenTypes.STRUCT_KEYWORD, Strings.STRUCT_KEYWORD));
        doTest("then", token(OCamlTokenTypes.THEN_KEYWORD, Strings.THEN_KEYWORD));
        doTest("to", token(OCamlTokenTypes.TO_KEYWORD, Strings.TO_KEYWORD));
        doTest("true", token(OCamlTokenTypes.TRUE_KEYWORD, Strings.TRUE_KEYWORD));
        doTest("try", token(OCamlTokenTypes.TRY_KEYWORD, Strings.TRY_KEYWORD));
        doTest("type", token(OCamlTokenTypes.TYPE_KEYWORD, Strings.TYPE_KEYWORD));
        doTest("val", token(OCamlTokenTypes.VAL_KEYWORD, Strings.VAL_KEYWORD));
        doTest("virtual", token(OCamlTokenTypes.VIRTUAL_KEYWORD, Strings.VIRTUAL_KEYWORD));
        doTest("when", token(OCamlTokenTypes.WHEN_KEYWORD, Strings.WHEN_KEYWORD));
        doTest("while", token(OCamlTokenTypes.WHILE_KEYWORD, Strings.WHILE_KEYWORD));
        doTest("with", token(OCamlTokenTypes.WITH_KEYWORD, Strings.WITH_KEYWORD));

        doTest("!=", token(OCamlTokenTypes.NOT_EQ, Strings.NOT_EQ));
        doTest("#", token(OCamlTokenTypes.HASH, Strings.HASH));
        doTest("&", token(OCamlTokenTypes.AMP, Strings.AMP));
        doTest("&&", token(OCamlTokenTypes.AMP_AMP, Strings.AMP_AMP));
        doTest("'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("(", token(OCamlTokenTypes.LPAR, Strings.LPAR));
        doTest(")", token(OCamlTokenTypes.RPAR, Strings.RPAR));
        doTest("*", token(OCamlTokenTypes.MULT, Strings.MULT));
        doTest("+", token(OCamlTokenTypes.PLUS, Strings.PLUS));
        doTest(",", token(OCamlTokenTypes.COMMA, Strings.COMMA));
        doTest("-", token(OCamlTokenTypes.MINUS, Strings.MINUS));
        doTest("-.", token(OCamlTokenTypes.MINUS_DOT, Strings.MINUS_DOT));
        doTest("->", token(OCamlTokenTypes.MINUS_GT, Strings.MINUS_GT));
        doTest(".", token(OCamlTokenTypes.DOT, Strings.DOT));
        doTest("..", token(OCamlTokenTypes.DOT_DOT, Strings.DOT_DOT));
        doTest(":", token(OCamlTokenTypes.COLON, Strings.COLON));
        doTest("::", token(OCamlTokenTypes.COLON_COLON, Strings.COLON_COLON));
        doTest(":=", token(OCamlTokenTypes.COLON_EQ, Strings.COLON_EQ));
        doTest(":>", token(OCamlTokenTypes.COLON_GT, Strings.COLON_GT));
        doTest(";", token(OCamlTokenTypes.SEMICOLON, Strings.SEMICOLON));
        doTest(";;", token(OCamlTokenTypes.SEMICOLON_SEMICOLON, Strings.SEMICOLON_SEMICOLON));
        doTest("<", token(OCamlTokenTypes.LT, Strings.LT));
        doTest("<-", token(OCamlTokenTypes.LT_MINUS, Strings.LT_MINUS));
        doTest("=", token(OCamlTokenTypes.EQ, Strings.EQ));
        doTest(">", token(OCamlTokenTypes.GT, Strings.GT));
        doTest(">]", token(OCamlTokenTypes.GT_RBRACKET, Strings.GT_RBRACKET));
        doTest(">}", token(OCamlTokenTypes.GT_RBRACE, Strings.GT_RBRACE));
        doTest("?", token(OCamlTokenTypes.QUEST, Strings.QUEST));
        doTest("??", token(OCamlTokenTypes.QUEST_QUEST, Strings.QUEST_QUEST));
        doTest("[", token(OCamlTokenTypes.LBRACKET, Strings.LBRACKET));
        doTest("[<", token(OCamlTokenTypes.LBRACKET_LT, Strings.LBRACKET_LT));
        doTest("[>", token(OCamlTokenTypes.LBRACKET_GT, Strings.LBRACKET_GT));
        doTest("[|", token(OCamlTokenTypes.LBRACKET_VBAR, Strings.LBRACKET_VBAR));
        doTest("]", token(OCamlTokenTypes.RBRACKET, Strings.RBRACKET));
        doTest("_", token(OCamlTokenTypes.UNDERSCORE, Strings.UNDERSCORE));
        doTest("`", token(OCamlTokenTypes.ACCENT, Strings.ACCENT));
        doTest("{", token(OCamlTokenTypes.LBRACE, Strings.LBRACE));
        doTest("{<", token(OCamlTokenTypes.LBRACE_LT, Strings.LBRACE_LT));
        doTest("|", token(OCamlTokenTypes.VBAR, Strings.VBAR));
        doTest("||", token(OCamlTokenTypes.VBAR_VBAR, Strings.VBAR_VBAR));
        doTest("|]", token(OCamlTokenTypes.VBAR_RBRACKET, Strings.VBAR_RBRACKET));
        doTest("}", token(OCamlTokenTypes.RBRACE, Strings.RBRACE));
        doTest("~", token(OCamlTokenTypes.TILDE, Strings.TILDE));

        /* Camlp4 extensions compatibility { */

        doTest("parser", token(OCamlTokenTypes.PARSER_KEYWORD, Strings.PARSER_KEYWORD));
        doTest("<<", token(OCamlTokenTypes.LT_LT, Strings.LT_LT));
        doTest("<:", token(OCamlTokenTypes.LT_COLON, Strings.LT_COLON));
        doTest(">>", token(OCamlTokenTypes.GT_GT, Strings.GT_GT));
        doTest("$", token(OCamlTokenTypes.DOLLAR, Strings.DOLLAR));
        doTest("$$", token(OCamlTokenTypes.DOLLAR_DOLLAR, Strings.DOLLAR_DOLLAR));
        doTest("$:", token(OCamlTokenTypes.DOLLAR_COLON, Strings.DOLLAR_COLON));

        /* } */
    }
}
