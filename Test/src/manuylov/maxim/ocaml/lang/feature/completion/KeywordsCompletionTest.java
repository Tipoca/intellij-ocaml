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
        doTest(1, "let a = 0 }{", AND_KEYWORD);
        doTest(2, "let a = }{");
        doTest(3, "let a = b }{ c", AND_KEYWORD);
        doTest(4, "let a = }{ c");
        doTest(5, "let a = b and f = g }{", AND_KEYWORD);
        doTest(6, "let a = b and f = }{");
        doTest(7, "let a = b and f = g }{ c", AND_KEYWORD);
        doTest(8, "let a = b and f = }{ c");
        doTest(9, "let a = 1 }{ in a", AND_KEYWORD);
        doTest(10, "let a = }{ in a");
        doTest(11, "class s = object end }{", AND_KEYWORD);
        doTest(12, "class s = object }{");
        doTest(13, "class type s = object end }{", AND_KEYWORD);
        doTest(14, "class type s = object }{");
        doTest(15, "type s = One }{", AND_KEYWORD);
        doTest(16, "type s = }{");
        doTest(17, "module type S = sig end with module M = M }{", AND_KEYWORD);
        doTest(18, "module type S = sig end with module M = }{");
        doTest(19, "let a = Constr }{", AND_KEYWORD);
    }

    public void testAsKeyword() throws Exception {
        doTest(1, "type s = _ }{", AS_KEYWORD, AND_KEYWORD);
        doTest(2, "type s = }{");
        doTest(3, "match 1 with _ }{", AS_KEYWORD);
        doTest(4, "match 1 with Constr }{", AS_KEYWORD);
        doTest(5, "match 1 with }{");
        doTest(6, "class s = object inherit ss }{", AS_KEYWORD);
        doTest(7, "class s = object inherit }{");
        doTest(8, "class s = object inherit ss as v }{");
        doTest(9, "class s = object inherit ss as }{");
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
