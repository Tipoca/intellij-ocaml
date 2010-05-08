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

package manuylov.maxim.ocaml.lang.lexer.token;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import manuylov.maxim.ocaml.lang.OCamlElementType;

/**
 * @author Maxim.Manuylov
 *         Date: 05.02.2009
 */
public interface OCamlTokenTypes {
    IElementType WHITE_SPACE = TokenType.WHITE_SPACE;
    IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;

    IElementType COMMENT = new OCamlElementType("COMMENT");
    IElementType COMMENT_BEGIN = new OCamlElementType("COMMENT_BEGIN");
    IElementType COMMENT_END = new OCamlElementType("COMMENT_END");

    IElementType AND_KEYWORD = new OCamlElementType("AND_KEYWORD");
    IElementType AS_KEYWORD = new OCamlElementType("AS_KEYWORD");
    IElementType ASSERT_KEYWORD = new OCamlElementType("ASSERT_KEYWORD");
    IElementType ASR_KEYWORD = new OCamlElementType("ASR_KEYWORD");
    IElementType BEGIN_KEYWORD = new OCamlElementType("BEGIN_KEYWORD");
    IElementType CLASS_KEYWORD = new OCamlElementType("CLASS_KEYWORD");
    IElementType CONSTRAINT_KEYWORD = new OCamlElementType("CONSTRAINT_KEYWORD");
    IElementType DO_KEYWORD = new OCamlElementType("DO_KEYWORD");
    IElementType DONE_KEYWORD = new OCamlElementType("DONE_KEYWORD");
    IElementType DOWNTO_KEYWORD = new OCamlElementType("DOWNTO_KEYWORD");
    IElementType ELSE_KEYWORD = new OCamlElementType("ELSE_KEYWORD");
    IElementType END_KEYWORD = new OCamlElementType("END_KEYWORD");
    IElementType EXCEPTION_KEYWORD = new OCamlElementType("EXCEPTION_KEYWORD");
    IElementType EXTERNAL_KEYWORD = new OCamlElementType("EXTERNAL_KEYWORD");
    IElementType FALSE_KEYWORD = new OCamlElementType("FALSE_KEYWORD");
    IElementType FOR_KEYWORD = new OCamlElementType("FOR_KEYWORD");
    IElementType FUN_KEYWORD = new OCamlElementType("FUN_KEYWORD");
    IElementType FUNCTION_KEYWORD = new OCamlElementType("FUNCTION_KEYWORD");
    IElementType FUNCTOR_KEYWORD = new OCamlElementType("FUNCTOR_KEYWORD");
    IElementType IF_KEYWORD = new OCamlElementType("IF_KEYWORD");
    IElementType IN_KEYWORD = new OCamlElementType("IN_KEYWORD");
    IElementType INCLUDE_KEYWORD = new OCamlElementType("INCLUDE_KEYWORD");
    IElementType INHERIT_KEYWORD = new OCamlElementType("INHERIT_KEYWORD");
    IElementType INITIALIZER_KEYWORD = new OCamlElementType("INITIALIZER_KEYWORD");
    IElementType LAND_KEYWORD = new OCamlElementType("LAND_KEYWORD");
    IElementType LAZY_KEYWORD = new OCamlElementType("LAZY_KEYWORD");
    IElementType LET_KEYWORD = new OCamlElementType("LET_KEYWORD");
    IElementType LOR_KEYWORD = new OCamlElementType("LOR_KEYWORD");
    IElementType LSR_KEYWORD = new OCamlElementType("LSR_KEYWORD");
    IElementType LSL_KEYWORD = new OCamlElementType("LSL_KEYWORD");
    IElementType LXOR_KEYWORD = new OCamlElementType("LXOR_KEYWORD");
    IElementType MATCH_KEYWORD = new OCamlElementType("MATCH_KEYWORD");
    IElementType METHOD_KEYWORD = new OCamlElementType("METHOD_KEYWORD");
    IElementType MOD_KEYWORD = new OCamlElementType("MOD_KEYWORD");
    IElementType MODULE_KEYWORD = new OCamlElementType("MODULE_KEYWORD");
    IElementType MUTABLE_KEYWORD = new OCamlElementType("MUTABLE_KEYWORD");
    IElementType NEW_KEYWORD = new OCamlElementType("NEW_KEYWORD");
    IElementType OBJECT_KEYWORD = new OCamlElementType("OBJECT_KEYWORD");
    IElementType OF_KEYWORD = new OCamlElementType("OF_KEYWORD");
    IElementType OPEN_KEYWORD = new OCamlElementType("OPEN_KEYWORD");
    IElementType OR_KEYWORD = new OCamlElementType("OR_KEYWORD");
    IElementType PRIVATE_KEYWORD = new OCamlElementType("PRIVATE_KEYWORD");
    IElementType REC_KEYWORD = new OCamlElementType("REC_KEYWORD");
    IElementType SIG_KEYWORD = new OCamlElementType("SIG_KEYWORD");
    IElementType STRUCT_KEYWORD = new OCamlElementType("STRUCT_KEYWORD");
    IElementType THEN_KEYWORD = new OCamlElementType("THEN_KEYWORD");
    IElementType TO_KEYWORD = new OCamlElementType("TO_KEYWORD");
    IElementType TRUE_KEYWORD = new OCamlElementType("TRUE_KEYWORD");
    IElementType TRY_KEYWORD = new OCamlElementType("TRY_KEYWORD");
    IElementType TYPE_KEYWORD = new OCamlElementType("TYPE_KEYWORD");
    IElementType VAL_KEYWORD = new OCamlElementType("VAL_KEYWORD");
    IElementType VIRTUAL_KEYWORD = new OCamlElementType("VIRTUAL_KEYWORD");
    IElementType WHEN_KEYWORD = new OCamlElementType("WHEN_KEYWORD");
    IElementType WHILE_KEYWORD = new OCamlElementType("WHILE_KEYWORD");
    IElementType WITH_KEYWORD = new OCamlElementType("WITH_KEYWORD");

    IElementType NOT_EQ = new OCamlElementType("NOT_EQ"); // !=
    IElementType HASH = new OCamlElementType("HASH"); // #
    IElementType AMP = new OCamlElementType("AMP"); // &
    IElementType AMP_AMP = new OCamlElementType("AMP_AMP"); // &&
    IElementType QUOTE = new OCamlElementType("QUOTE"); // '
    IElementType LPAR = new OCamlElementType("LPAR"); // (
    IElementType RPAR = new OCamlElementType("RPAR"); // )
    IElementType MULT = new OCamlElementType("MULT"); // *
    IElementType PLUS = new OCamlElementType("PLUS"); // +
    IElementType COMMA = new OCamlElementType("COMMA"); // ,
    IElementType MINUS = new OCamlElementType("MINUS"); // -
    IElementType MINUS_DOT = new OCamlElementType("MINUS_DOT"); // -.
    IElementType MINUS_GT = new OCamlElementType("MINUS_GT"); // ->
    IElementType DOT = new OCamlElementType("DOT"); // .
    IElementType DOT_DOT = new OCamlElementType("DOT_DOT"); // ..
    IElementType COLON = new OCamlElementType("COLON"); // :
    IElementType COLON_COLON = new OCamlElementType("COLON_COLON"); // ::
    IElementType COLON_EQ = new OCamlElementType("COLON_EQ"); // :=
    IElementType COLON_GT = new OCamlElementType("COLON_GT"); // :>
    IElementType SEMICOLON = new OCamlElementType("SEMICOLON"); // ;
    IElementType SEMICOLON_SEMICOLON = new OCamlElementType("SEMICOLON_SEMICOLON"); // ;;
    IElementType LT = new OCamlElementType("LT"); // <
    IElementType LT_MINUS = new OCamlElementType("LT_MINUS"); // <-
    IElementType EQ = new OCamlElementType("EQ"); // =
    IElementType GT = new OCamlElementType("GT"); // >
    IElementType GT_RBRACKET = new OCamlElementType("GT_RBRACKET"); // >]
    IElementType GT_RBRACE = new OCamlElementType("GT_RBRACE"); // >}
    IElementType QUEST = new OCamlElementType("QUEST"); // ?
    IElementType QUEST_QUEST = new OCamlElementType("QUEST_QUEST"); // ??
    IElementType LBRACKET = new OCamlElementType("LBRACKET"); // [
    IElementType LBRACKET_LT = new OCamlElementType("LBRACKET_LT"); // [<
    IElementType LBRACKET_GT = new OCamlElementType("LBRACKET_GT"); // [>
    IElementType LBRACKET_VBAR = new OCamlElementType("LBRACKET_VBAR"); // [|
    IElementType RBRACKET = new OCamlElementType("RBRACKET"); // ]
    IElementType UNDERSCORE = new OCamlElementType("UNDERSCORE"); // _
    IElementType ACCENT = new OCamlElementType("ACCENT"); // `
    IElementType LBRACE = new OCamlElementType("LBRACE"); // {
    IElementType LBRACE_LT = new OCamlElementType("LBRACE_LT"); // {<
    IElementType VBAR = new OCamlElementType("VBAR"); // |
    IElementType VBAR_VBAR = new OCamlElementType("VBAR_VBAR"); // ||
    IElementType VBAR_RBRACKET = new OCamlElementType("VBAR_RBRACKET"); // |]
    IElementType RBRACE = new OCamlElementType("RBRACE"); // }
    IElementType TILDE = new OCamlElementType("TILDE"); // ~

    /* Camlp4 extensions compatibility { */

    IElementType PARSER_KEYWORD = new OCamlElementType("PARSER_KEYWORD");
    IElementType LT_LT = new OCamlElementType("LT_LT"); // <<
    IElementType LT_COLON = new OCamlElementType("LT_COLON"); // <:
    IElementType GT_GT = new OCamlElementType("GT_GT"); // >>
    IElementType DOLLAR = new OCamlElementType("DOLLAR"); // $
    IElementType DOLLAR_DOLLAR = new OCamlElementType("DOLLAR_DOLLAR"); // $$
    IElementType DOLLAR_COLON = new OCamlElementType("DOLLAR_COLON"); // $:

    /* } */

    IElementType LCFC_IDENTIFIER = new OCamlElementType("LCFC_IDENTIFIER");
    IElementType UCFC_IDENTIFIER = new OCamlElementType("UCFC_IDENTIFIER");

    IElementType INTEGER_LITERAL = new OCamlElementType("INTEGER_LITERAL");
    IElementType FLOAT_LITERAL = new OCamlElementType("FLOAT_LITERAL");
    IElementType CHAR_LITERAL = new OCamlElementType("CHAR_LITERAL");
    IElementType STRING_LITERAL = new OCamlElementType("STRING_LITERAL");

    IElementType INFIX_OPERATOR = new OCamlElementType("INFIX_OPERATOR");
    IElementType PREFIX_OPERATOR = new OCamlElementType("PREFIX_OPERATOR");

    /* Highlighting only tokens { */

    IElementType REGULAR_CHARS = new OCamlElementType("REGULAR_CHARS");
    IElementType ESCAPE_SEQUENCES = new OCamlElementType("ESCAPE_SEQUENCES");
    IElementType DOUBLE_QUOTE = new OCamlElementType("DOUBLE_QUOTE");

    /* } */

    TokenSet IDENTIFIERS = TokenSet.create( LCFC_IDENTIFIER, UCFC_IDENTIFIER );

    TokenSet KEYWORDS = TokenSet
        .create(AND_KEYWORD, AS_KEYWORD, ASSERT_KEYWORD, BEGIN_KEYWORD, CLASS_KEYWORD, CONSTRAINT_KEYWORD, DO_KEYWORD, DONE_KEYWORD, DOWNTO_KEYWORD, ELSE_KEYWORD, END_KEYWORD,
                EXCEPTION_KEYWORD, EXTERNAL_KEYWORD, FALSE_KEYWORD, FOR_KEYWORD, FUN_KEYWORD, FUNCTION_KEYWORD, FUNCTOR_KEYWORD, IF_KEYWORD, IN_KEYWORD, INCLUDE_KEYWORD,
                INHERIT_KEYWORD, INITIALIZER_KEYWORD, LAZY_KEYWORD, LET_KEYWORD, MATCH_KEYWORD, METHOD_KEYWORD, MODULE_KEYWORD, MUTABLE_KEYWORD, NEW_KEYWORD, OBJECT_KEYWORD,
                OF_KEYWORD, OPEN_KEYWORD, OR_KEYWORD, PARSER_KEYWORD, PRIVATE_KEYWORD, REC_KEYWORD, SIG_KEYWORD, STRUCT_KEYWORD, THEN_KEYWORD, TO_KEYWORD, TRUE_KEYWORD,
                TRY_KEYWORD, TYPE_KEYWORD, VAL_KEYWORD, VIRTUAL_KEYWORD, WHEN_KEYWORD, WHILE_KEYWORD, WITH_KEYWORD);

    TokenSet PREFIX_OPERATORS = TokenSet.create( PREFIX_OPERATOR, NOT_EQ, QUEST_QUEST );

    TokenSet INFIX_OPERATORS = TokenSet.create(INFIX_OPERATOR, EQ, LT, GT, AMP, PLUS, MINUS, MULT, DOLLAR, AMP_AMP, MINUS_DOT, VBAR_VBAR, LT_LT, LT_COLON, GT_GT, DOLLAR_DOLLAR, 
                                               DOLLAR_COLON, OR_KEYWORD, COLON_EQ, MOD_KEYWORD, LAND_KEYWORD, LOR_KEYWORD, LXOR_KEYWORD, LSL_KEYWORD, LSR_KEYWORD, ASR_KEYWORD);

    TokenSet WHITE_SPACES = TokenSet.create( WHITE_SPACE );

    TokenSet STRING_LITERALS = TokenSet.create( STRING_LITERAL );
    TokenSet COMMENTS = TokenSet.create( COMMENT, COMMENT_BEGIN, COMMENT_END );

    /* TokenSets for DefaultWordsScanner { */

    TokenSet DWS_IDENTIFIERS = IDENTIFIERS;
    TokenSet DWS_COMMENTS = TokenSet.create( COMMENT );
    TokenSet DWS_LITERALS = TokenSet.create( INTEGER_LITERAL, FLOAT_LITERAL, REGULAR_CHARS, ESCAPE_SEQUENCES );

    /* } */
}

