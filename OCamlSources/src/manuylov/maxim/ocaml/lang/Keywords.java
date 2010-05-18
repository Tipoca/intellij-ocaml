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

package manuylov.maxim.ocaml.lang;

import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 26.04.2010
 */
public interface Keywords { 
    @NotNull String AND_KEYWORD = "and";
    @NotNull String AS_KEYWORD = "as";
    @NotNull String ASSERT_KEYWORD = "assert";
    @NotNull String ASR_KEYWORD = "asr";
    @NotNull String BEGIN_KEYWORD = "begin";
    @NotNull String CLASS_KEYWORD = "class";
    @NotNull String CONSTRAINT_KEYWORD = "constraint";
    @NotNull String DO_KEYWORD = "do";
    @NotNull String DONE_KEYWORD = "done";
    @NotNull String DOWNTO_KEYWORD = "downto";
    @NotNull String ELSE_KEYWORD = "else";
    @NotNull String END_KEYWORD = "end";
    @NotNull String EXCEPTION_KEYWORD = "exception";
    @NotNull String EXTERNAL_KEYWORD = "external";
    @NotNull String FALSE_KEYWORD = "false";
    @NotNull String FOR_KEYWORD = "for";
    @NotNull String FUN_KEYWORD = "fun";
    @NotNull String FUNCTION_KEYWORD = "function";
    @NotNull String FUNCTOR_KEYWORD = "functor";
    @NotNull String IF_KEYWORD = "if";
    @NotNull String IN_KEYWORD = "in";
    @NotNull String INCLUDE_KEYWORD = "include";
    @NotNull String INHERIT_KEYWORD = "inherit";
    @NotNull String INITIALIZER_KEYWORD = "initializer";
    @NotNull String LAND_KEYWORD = "land";
    @NotNull String LAZY_KEYWORD = "lazy";
    @NotNull String LET_KEYWORD = "let";
    @NotNull String LOR_KEYWORD = "lor";
    @NotNull String LSR_KEYWORD = "lsr";
    @NotNull String LSL_KEYWORD = "lsl";
    @NotNull String LXOR_KEYWORD = "lxor";
    @NotNull String MATCH_KEYWORD = "match";
    @NotNull String METHOD_KEYWORD = "method";
    @NotNull String MOD_KEYWORD = "mod";
    @NotNull String MODULE_KEYWORD = "module";
    @NotNull String MUTABLE_KEYWORD = "mutable";
    @NotNull String NEW_KEYWORD = "new";
    @NotNull String OBJECT_KEYWORD = "object";
    @NotNull String OF_KEYWORD = "of";
    @NotNull String OPEN_KEYWORD = "open";
    @NotNull String OR_KEYWORD = "or";
    @NotNull String PARSER_KEYWORD = "parser";
    @NotNull String PRIVATE_KEYWORD = "private";
    @NotNull String REC_KEYWORD = "rec";
    @NotNull String SIG_KEYWORD = "sig";
    @NotNull String STRUCT_KEYWORD = "struct";
    @NotNull String THEN_KEYWORD = "then";
    @NotNull String TO_KEYWORD = "to";
    @NotNull String TRUE_KEYWORD = "true";
    @NotNull String TRY_KEYWORD = "try";
    @NotNull String TYPE_KEYWORD = "type";
    @NotNull String VAL_KEYWORD = "val";
    @NotNull String VIRTUAL_KEYWORD = "virtual";
    @NotNull String WHEN_KEYWORD = "when";
    @NotNull String WHILE_KEYWORD = "while";
    @NotNull String WITH_KEYWORD = "with";

    @NotNull String NOT_EQ = "!=";
    @NotNull String HASH = "#";
    @NotNull String AMP = "&";
    @NotNull String AMP_AMP = "&&";
    @NotNull String QUOTE = "'";
    @NotNull String LPAR = "(";
    @NotNull String RPAR = ")";
    @NotNull String MULT = "*";
    @NotNull String PLUS = "+";
    @NotNull String COMMA = ",";
    @NotNull String MINUS = "-";
    @NotNull String MINUS_DOT = "-.";
    @NotNull String MINUS_GT = "->";
    @NotNull String DOT = ".";
    @NotNull String DOT_DOT = "..";
    @NotNull String COLON = ":";
    @NotNull String COLON_COLON = "::";
    @NotNull String COLON_EQ = ":=";
    @NotNull String COLON_GT = ":>";
    @NotNull String SEMICOLON = ";";
    @NotNull String SEMICOLON_SEMICOLON = ";;";
    @NotNull String LT = "<";
    @NotNull String LT_MINUS = "<-";
    @NotNull String EQ = "=";
    @NotNull String GT = ">";
    @NotNull String GT_RBRACKET = ">]";
    @NotNull String GT_RBRACE = ">}";
    @NotNull String QUEST = "?";
    @NotNull String QUEST_QUEST = "??";
    @NotNull String LBRACKET = "[";
    @NotNull String LBRACKET_LT = "[<";
    @NotNull String LBRACKET_GT = "[>";
    @NotNull String LBRACKET_VBAR = "[|";
    @NotNull String RBRACKET = "]";
    @NotNull String UNDERSCORE = "_";
    @NotNull String ACCENT = "`";
    @NotNull String LBRACE = "{";
    @NotNull String LBRACE_LT = "{<";
    @NotNull String VBAR = "|";
    @NotNull String VBAR_VBAR = "||";
    @NotNull String VBAR_RBRACKET = "|]";
    @NotNull String RBRACE = "}";
    @NotNull String TILDE = "~";
    @NotNull String LT_LT = "<<";
    @NotNull String LT_COLON = "<:";
    @NotNull String GT_GT = ">>";
    @NotNull String DOLLAR = "$";
    @NotNull String DOLLAR_DOLLAR = "$$";
    @NotNull String DOLLAR_COLON = "$:";
    @NotNull String DOUBLE_QUOTE = "\"";
    @NotNull String AT = "@";
    @NotNull String XOR = "^";
    @NotNull String DIV = "/";
    @NotNull String PERCENT = "%";
    @NotNull String POWER = "**";
}
