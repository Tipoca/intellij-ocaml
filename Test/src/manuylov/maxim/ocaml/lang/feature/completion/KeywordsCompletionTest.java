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

package manuylov.maxim.ocaml.lang.feature.completion;

import com.intellij.codeInsight.completion.CompletionType;
import manuylov.maxim.ocaml.lang.feature.completion.testCase.CompletionTestCase;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static manuylov.maxim.ocaml.lang.Keywords.*;

/**
 * @author Maxim.Manuylov
 *         Date: 20.05.2010
 */
@Test
public class KeywordsCompletionTest extends CompletionTestCase {
    public void testAndKeyword() throws Exception {
        setKeywords(AND_KEYWORD);

        doTest(1, "let a = 0 }{", true);
        doTest(2, "let a = }{", false);
        doTest(3, "let a = b }{ c", true);
        doTest(4, "let a = }{ c", false);
        doTest(5, "let a = b and f = g }{", true);
        doTest(6, "let a = b and f = }{", false);
        doTest(7, "let a = b and f = g }{ c", true);
        doTest(8, "let a = b and f = }{ c", false);
        doTest(9, "let a = 1 }{ in a", true);
        doTest(10, "let a = }{ in a", false);
        doTest(11, "class s = object end }{", true);
        doTest(12, "class s = object }{", false);
        doTest(13, "class type s = object end }{", true);
        doTest(14, "class type s = object }{", false);
        doTest(15, "type s = One }{", true);
        doTest(16, "type s = }{", false);
        doTest(17, "module type S = sig end with module M = M }{", true);
        doTest(18, "module type S = sig end with module M = }{", false);
        doTest(19, "let a = Constr }{", true);
    }

    public void testAsKeyword() throws Exception {
        setKeywords(AS_KEYWORD);

        doTest(1, "type s = _ }{", true);
        doTest(2, "type s = }{", false);
        doTest(3, "match 1 with _ }{", true);
        doTest(4, "match 1 with Constr }{", true);
        doTest(5, "match 1 with }{", false);
        doTest(6, "class s = object inherit ss }{", true);
        doTest(7, "class s = object inherit }{", false);
        doTest(8, "class s = object inherit ss as v }{", false);
        doTest(9, "class s = object inherit ss as }{", false);
    }
    
    public void testExpressionStart() throws Exception {
        setKeywords(BEGIN_KEYWORD, IF_KEYWORD, WHILE_KEYWORD, FOR_KEYWORD, MATCH_KEYWORD, FUNCTION_KEYWORD,
            FUN_KEYWORD, TRY_KEYWORD, NEW_KEYWORD, ASSERT_KEYWORD, LAZY_KEYWORD, TRUE_KEYWORD, FALSE_KEYWORD);

        doTest(1, "let a = }{", true);
        doTest(2, "}{", true);
        doTest(3, "let }{", false);
        doTest(4, "type t = }{", false);
    }

    public void testStatementStart() throws Exception {
        setKeywords(EXTERNAL_KEYWORD, EXCEPTION_KEYWORD, CLASS_KEYWORD, MODULE_KEYWORD, OPEN_KEYWORD, INCLUDE_KEYWORD);

        doTest(1, "let }{", false);
        doTest(2, "}{", true);
        doTest(3, ";; }{", true);
        doTest(4, "let a = 0;; }{", true);
        doTest(5, "module M = struct }{", true);
        doTest(6, "module M = struct ;; }{", true);
        doTest(7, "module M = struct let a = 0;; }{", true);
        doTest(8, "module type M = sig }{", true);
        doTest(9, "module type M = sig ;; }{", true);
        doTest(10, "module type M = sig val a : int;; }{", true);
    }

    @NotNull
    @Override
    protected CompletionType getCompletionType() {
        return CompletionType.BASIC;
    }

    @Override
    protected int getInvocationCount() {
        return 1;
    }
}
