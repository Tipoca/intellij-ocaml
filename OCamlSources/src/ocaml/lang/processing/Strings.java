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
 *         Date: 28.02.2009
 */
public interface Strings {
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

    String CLASS_EXPECTED = "'class' expected";
    String TYPE_EXPECTED = "'type' expected";
    String INITIALIZER_KEYWORD_EXPECTED = "'initializer' expected";
    String METHOD_KEYWORD_EXPECTED = "'method' expected";
    String OBJECT_KEYWORD_EXPECTED = "'object' expected";
    String INHERIT_KEYWORD_EXPECTED = "'inherit' expected";
    String CLASS_KEYWORD_EXPECTED = "'class' expected";
    String LBRACE_LT_EXPECTED = "'{<' expected";
    String GT_RBRACE_EXPECTED = "'>}' expected";
    String LBRACKET_VBAR_EXPECTED = "'[|' expected";
    String LBRACKET_EXPECTED = "'[' expected";
    String WHILE_KEYWORD_EXPECTED = "'while' expected";
    String FOR_KEYWORD_EXPECTED = "'for' expected";
    String DO_KEYWORD_EXPECTED = "'do' expected";
    String DONE_KEYWORD_EXPECTED = "'done' expected";
    String IF_KEYWORD_EXPECTED = "'if' expected";
    String THEN_KEYWORD_EXPECTED = "'then' expected";
    String TRY_KEYWORD_EXPECTED = "'try' expected";
    String FUNCTION_KEYWORD_EXPECTED = "'function' expected";
    String FUN_KEYWORD_EXPECTED = "'fun' expected";
    String MATCH_KEYWORD_EXPECTED = "'match' expected";
    String WITH_KEYWORD_EXPECTED = "'with' expected";
    String ASSERT_KEYWORD_EXPECTED = "'assert' expected";
    String LAZY_KEYWORD_EXPECTED = "'lazy' expected";
    String END_KEYWORD_EXPECTED = "'end' expected";
    String LET_KEYWORD_EXPECTED = "'let' expected";
    String IN_KEYWORD_EXPECTED = "'in' expected";
    String FUNCTOR_KEYWORD_EXPECTED = "'functor' expected";
    String SIG_KEYWORD_EXPECTED = "'sig' expected";
    String MINUS_GT_EXPECTED = "'->' expected";
    String LPAR_EXPECTED = "'(' expected";
    String MODULE_KEYWORD_EXPECTED = "'module' expected";
    String VBAR_RBRACKET_EXPECTED = "'|]' expected";
    String VAL_KEYWORD_EXPECTED = "'val' expected";
    String INCLUDE_KEYWORD_EXPECTED = "'include' expected";
    String OPEN_KEYWORD_EXPECTED = "'open' expected";
    String EXTERNAL_KEYWORD_EXPECTED = "'external' expected";
    String EXCEPTION_KEYWORD_EXPECTED = "'exception' expected";
    String TYPE_KEYWORD_EXPECTED = "'type' expected";
    String CONSTRAINT_KEYWORD_EXPECTED = "'constraint' expected";
    String QUOTE_EXPECTED = "''' expected";
    String EQ_EXPECTED = "'=' expected";
    String LBRACE_EXPECTED = "'{' expected";
    String RBRACE_EXPECTED = "'}' expected";
    String COLON_EXPECTED = "':' expected";
    String RPAR_EXPECTED = "')' expected";
    String ACCENT_EXPECTED = "'`' expected";
    String RBRACKET_EXPECTED = "']' expected";
    String GT_EXPECTED = "'>' expected";
    String CHAR_LITERAL_EXPECTED = "character literal expected";
    String DOT_EXPECTED = "'.' expected";
    String SEMICOLON_SEMICOLON_EXPECTED = "';;' expected";
    String INHERIT_OR_VAL_OR_METHOD_OR_CONSTRAINT_OR_INITIALIZER_EXPECTED = "'inherit', 'val', 'method', 'constraint' or 'initializer' expected";
    String EXPRESSION_EXPECTED = "Expression expected";
    String RPAR_OR_COLON_GT_EXPECTED = "')' or ':>' expected";
    String RPAR_OR_COLON_OR_COLON_GT_EXPECTED = "')', ':' or ':>' expected";
    String BEGIN_OR_LPAR_EXPECTED = "'begin' or '(' expected";
    String TO_OR_DOWNTO_EXPECTED = "'to' or 'downto' expected";
    String TYPE_OR_MODULE_EXPECTED = "'type' or 'module' expected";
    String MODULE_NAME_EXPECTED = "Module name expected";
    String CONSTRUCTOR_PATH_EXPECTED = "Constructor path expected";
    String IDENTIFIER_EXPECTED = "Identifier expected";
    String CLASS_PATH_EXPECTED = "Class path expected";
    String METHOD_NAME_EXPECTED = "Method name expected";
    String LABEL_NAME_EXPECTED = "Label name expected";
    String MODULE_TYPE_NAME_EXPECTED = "Module type name expected";
    String EXTENDED_MODULE_PATH_EXPECTED = "Extended module path expected";
    String EXTENDED_MODULE_NAME_EXPECTED = "Extended module name expected";
    String TYPE_CONSTRUCTOR_PATH_EXPECTED = "Type constructor path expected";
    String MODULE_TYPE_PATH_EXPECTED = "Module type path expected";
    String TYPE_CONSTRUCTOR_NAME_EXPECTED = "Type constructor name expected";
    String TAG_NAME_EXPECTED = "Tag name expected";
    String CLASS_NAME_EXPECTED = "Class name expected";
    String CONSTRUCTOR_NAME_EXPECTED = "Constructor name expected";
    String INSTANCE_VARIABLE_NAME_EXPECTED = "Instance variable name expected";
    String VALUE_NAME_EXPECTED = "Value name expected";
    String FIELD_NAME_EXPECTED = "Field name expected";
    String FIELD_PATH_EXPECTED = "Field path expected";
    String PATTERN_EXPECTED = "Pattern expected";
    String RPAR_OR_COLON_EXPECTED = "')' or ':' expected";
    String SPECIFICATION_EXPECTED = "Specification expected";
    String TYPE_EXPRESSION_EXPECTED = "Type expression expected";
    String POLYMORPHIC_TYPE_EXPRESSION_EXPECTED = "Polymorphic type expression expected";
    String TYPE_CONSTRUCTOR_OR_HASH_EXPECTED = "Type constructor or '#' expected";
    String STRUCT_KEYWORD_EXPECTED = "'struct' expected";
    String UNEXPECTED_TOKEN = "Unexpected token";
    String DEFINITION_OR_EXPRESSION_EXPECTED = "Definition or expression expected";
    String UNCLOSED_COMMENT = "Unclosed comment";
    String UNEXPECTED_END_OF_FILE = "Unexpected end of file";
    String CLASS_EXPRESSION_EXPECTED = "Class expression expected";
    String INDEX_VARIABLE_NAME_EXPECTED = "Index variable name expected";
}
