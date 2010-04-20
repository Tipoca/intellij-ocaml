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

import com.intellij.lexer.Lexer;
import ocaml.lang.processing.Strings;
import ocaml.lang.processing.lexer.token.OCamlTokenTypes;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 23.02.2009
 */
@Test
public class OCamlHighlightingLexerTest extends BaseLexerTest {
    public void testCharLiteral() {
        doTest("'a", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.LCFC_IDENTIFIER, "a"));
        doTest("'a'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "a"), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\\\'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\\\"), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\\"'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\\""), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\''", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\'"), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\n'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\n"), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\t'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\t"), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\b'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\b"), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\r'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\r"), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\ '", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\ "), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\123'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\123"), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
        doTest("'\\xBD'", token(OCamlTokenTypes.QUOTE, Strings.QUOTE), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\xBD"), token(OCamlTokenTypes.QUOTE, Strings.QUOTE));
    }

    public void testStringLiteral() {
        doTest("\"a", token(OCamlTokenTypes.DOUBLE_QUOTE, Strings.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "a"));
        doTest("\"a\"", token(OCamlTokenTypes.DOUBLE_QUOTE, Strings.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "a"), token(OCamlTokenTypes.DOUBLE_QUOTE, Strings.DOUBLE_QUOTE));
        doTest("\"abcdefskdn3\"", token(OCamlTokenTypes.DOUBLE_QUOTE, Strings.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "abcdefskdn3"), token(OCamlTokenTypes.DOUBLE_QUOTE, Strings.DOUBLE_QUOTE));
        doTest("\"abc\\n\\t\\rdef\"", token(OCamlTokenTypes.DOUBLE_QUOTE, Strings.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "abc"), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\n\\t\\r"), token(OCamlTokenTypes.REGULAR_CHARS, "def"), token(OCamlTokenTypes.DOUBLE_QUOTE, Strings.DOUBLE_QUOTE));
        doTest("\"abc\\\n   \t \\rdef\"", token(OCamlTokenTypes.DOUBLE_QUOTE, Strings.DOUBLE_QUOTE), token(OCamlTokenTypes.REGULAR_CHARS, "abc"), token(OCamlTokenTypes.ESCAPE_SEQUENCES, "\\\n   \t \\r"), token(OCamlTokenTypes.REGULAR_CHARS, "def"), token(OCamlTokenTypes.DOUBLE_QUOTE, Strings.DOUBLE_QUOTE));
    }

    protected Lexer createLexer() {
        return new OCamlHighlightingLexer();
    }
}
