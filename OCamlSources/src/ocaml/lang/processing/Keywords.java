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

package ocaml.lang.processing;

/**
 * @author Maxim.Manuylov
 *         Date: 26.04.2010
 */
public interface Keywords { 
    String AND_KEYWORD = "and";
    String AS_KEYWORD = "as";
    String ASSERT_KEYWORD = "assert";
    String ASR_KEYWORD = "asr";
    String BEGIN_KEYWORD = "begin";
    String CLASS_KEYWORD = "class";
    String CONSTRAINT_KEYWORD = "constraint";
    String DO_KEYWORD = "do";
    String DONE_KEYWORD = "done";
    String DOWNTO_KEYWORD = "downto";
    String ELSE_KEYWORD = "else";
    String END_KEYWORD = "end";
    String EXCEPTION_KEYWORD = "exception";
    String EXTERNAL_KEYWORD = "external";
    String FALSE_KEYWORD = "false";
    String FOR_KEYWORD = "for";
    String FUN_KEYWORD = "fun";
    String FUNCTION_KEYWORD = "function";
    String FUNCTOR_KEYWORD = "functor";
    String IF_KEYWORD = "if";
    String IN_KEYWORD = "in";
    String INCLUDE_KEYWORD = "include";
    String INHERIT_KEYWORD = "inherit";
    String INITIALIZER_KEYWORD = "initializer";
    String LAND_KEYWORD = "land";
    String LAZY_KEYWORD = "lazy";
    String LET_KEYWORD = "let";
    String LOR_KEYWORD = "lor";
    String LSR_KEYWORD = "lsr";
    String LSL_KEYWORD = "lsl";
    String LXOR_KEYWORD = "lxor";
    String MATCH_KEYWORD = "match";
    String METHOD_KEYWORD = "method";
    String MOD_KEYWORD = "mod";
    String MODULE_KEYWORD = "module";
    String MUTABLE_KEYWORD = "mutable";
    String NEW_KEYWORD = "new";
    String OBJECT_KEYWORD = "object";
    String OF_KEYWORD = "of";
    String OPEN_KEYWORD = "open";
    String OR_KEYWORD = "or";
    String PARSER_KEYWORD = "parser";
    String PRIVATE_KEYWORD = "private";
    String REC_KEYWORD = "rec";
    String SIG_KEYWORD = "sig";
    String STRUCT_KEYWORD = "struct";
    String THEN_KEYWORD = "then";
    String TO_KEYWORD = "to";
    String TRUE_KEYWORD = "true";
    String TRY_KEYWORD = "try";
    String TYPE_KEYWORD = "type";
    String VAL_KEYWORD = "val";
    String VIRTUAL_KEYWORD = "virtual";
    String WHEN_KEYWORD = "when";
    String WHILE_KEYWORD = "while";
    String WITH_KEYWORD = "with";

    String NOT_EQ = "!=";
    String HASH = "#";
    String AMP = "&";
    String AMP_AMP = "&&";
    String QUOTE = "'";
    String LPAR = "(";
    String RPAR = ")";
    String MULT = "*";
    String PLUS = "+";
    String COMMA = ",";
    String MINUS = "-";
    String MINUS_DOT = "-.";
    String MINUS_GT = "->";
    String DOT = ".";
    String DOT_DOT = "..";
    String COLON = ":";
    String COLON_COLON = "::";
    String COLON_EQ = ":=";
    String COLON_GT = ":>";
    String SEMICOLON = ";";
    String SEMICOLON_SEMICOLON = ";;";
    String LT = "<";
    String LT_MINUS = "<-";
    String EQ = "=";
    String GT = ">";
    String GT_RBRACKET = ">]";
    String GT_RBRACE = ">}";
    String QUEST = "?";
    String QUEST_QUEST = "??";
    String LBRACKET = "[";
    String LBRACKET_LT = "[<";
    String LBRACKET_GT = "[>";
    String LBRACKET_VBAR = "[|";
    String RBRACKET = "]";
    String UNDERSCORE = "_";
    String ACCENT = "`";
    String LBRACE = "{";
    String LBRACE_LT = "{<";
    String VBAR = "|";
    String VBAR_VBAR = "||";
    String VBAR_RBRACKET = "|]";
    String RBRACE = "}";
    String TILDE = "~";
    String LT_LT = "<<";
    String LT_COLON = "<:";
    String GT_GT = ">>";
    String DOLLAR = "$";
    String DOLLAR_DOLLAR = "$$";
    String DOLLAR_COLON = "$:";
    String DOUBLE_QUOTE = "\"";
    String AT = "@";
    String XOR = "^";
    String DIV = "/";
    String PERCENT = "%";
    String POWER = "**";
}
