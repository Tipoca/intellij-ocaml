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

package ocaml.lang.processing.parser.psi.resolving;

import ocaml.lang.processing.parser.psi.resolving.testCase.ResolvingTestCase;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 19.06.2009
 */
@Test
public class ValueResolvingTest extends ResolvingTestCase {
    public void testValueResolving() throws Exception {
        doTest(1, "" +
            "let {{a}} = 0;; let f = }{a;;");

        doTest(2, "" +
            "let a = }{b;;");

        doTest(3, "" +
            "let {{}{a}} = 9;;");

        doTest(4, "" +
            "let a = }{b;;");

        doTest(5, "" +
            "let a = }{b and b = 5;;");

        doTest(6, "" +
            "let rec a = }{b and {{b}} = 5;;");

        doTest(7, "" +
            "let rec {{b}} = 5 and a = }{b;;");

        doTest(8, "" +
            "let {{a}} = 5 in }{a;;");

        doTest(9, "" +
            "let a = 5 in a;;" +
            "let f = }{a;;");

        doTest(10, "" +
            "let a n = }{a (n - 1);;");

        doTest(11, "" +
            "let rec {{a}} n = }{a (n - 1);;");

        doTest(12, "" +
            "module M = " +
            "struct " +
            "  let {{a}} = 0;; " +
            "end;; " +
            "let f = M.}{a;;");

        doTest(13, "" +
            "module M = " +
            "struct " +
            "  let a = 0;; " +
            "end;; " +
            "let f = }{a;;");

        doTest(14, "" +
            "module M = " +
            "struct " +
            "  let {{a}} = 0;; " +
            "end;; " +
            "open M;;" +
            "let f = }{a;;");

        doTest(15, "" +
            "module M = " +
            "struct " +
            "  let {{a}} = 0;; " +
            "end;; " +
            "open M;;" +
            "let f = M.}{a;;");

        doTest(16, "" +
            "module M = " +
            "struct " +
            "  let {{a}} = 0;; " +
            "end;; " +
            "include M;;" +
            "let f = }{a;;");

        doTest(17, "" +
            "module M = " +
            "struct " +
            "  let {{a}} = 0;; " +
            "end;; " +
            "include M;;" +
            "let f = M.}{a;;");

        doTest(18, "" +
            "let {{a}} = 9;; " +
            "module M = " +
            "struct " +
            "  let a = 0;; " +
            "end;; " +
            "let f = }{a;;");

        doTest(19, "" +
            "module M = " +
            "struct " +
            "  let {{a}} = 0;; " +
            "end;; " +
            "let a = 9;; " +
            "open M;; " +
            "let f = }{a;;");

        doTest(20, "" +
            "module M = " +
            "struct " +
            "  let {{a}} = 0;; " +
            "end;; " +
            "let a = 9;; " +
            "include M;; " +
            "let f = }{a;;");

        doTest(21, "" +
            "for {{i}} = 1 to 5 do print_int }{i done;;");

        doTest(22, "" +
            "match 1 with {{a}} -> }{a");

        doTest(23, "" +
            "match 1 with _ as {{a}} -> }{a");

        doTest(24, "" +
            "match 1 with ({{a}}) -> }{a");

        doTest(25, "" +
            "match 1 with ({{a}} : int) -> }{a");

        doTest(26, "" +
            "match 1 with 2 | {{a}} -> }{a");

        doTest(27, "" +
            "match 1 with {{a}} | 2 -> }{a");

        doTest(28, "" +
            "match 1 with Constr {{a}} -> }{a");

        doTest(29, "" +
            "match 1 with `tag {{a}} -> }{a");

        doTest(30, "" +
            "match 1 with {{a}}, b -> }{a");

        doTest(31, "" +
            "match 1 with b, {{a}} -> }{a");

        doTest(32, "" +
            "match 1 with {f1 = {{a}}; f2 = b} -> }{a");

        doTest(33, "" +
            "match 1 with {f1 = b; f2 = {{a}}} -> }{a");

        doTest(34, "" +
            "match 1 with [{{a}}; b] -> }{a");

        doTest(35, "" +
            "match 1 with [b; {{a}}] -> }{a");

        doTest(36, "" +
            "match 1 with {{a}} :: [] -> }{a");

        doTest(37, "" +
            "match 1 with _ :: {{a}} -> }{a");

        doTest(38, "" +
            "match 1 with [|{{a}}; b|] -> }{a");

        doTest(39, "" +
            "match 1 with [|b; {{a}}|] -> }{a");

        doTest(40, "" +
            "match 1 with lazy {{a}} -> }{a");

        doTest(41, "" +
            "match 1 with {{a}} as b -> }{a");


        
                                                     /*
module-expr	::=	module-path
 	?	 struct { definition  [;;]
 	?	 expr ;; } end
 	?	 functor ( module-name :  module-type ) ->  module-expr
 	?	 module-expr (  module-expr )
 	?	 ( module-expr )
 	?	 ( module-expr :  module-type )
        + module find actual definition + module type
                              *
                              *
                              * let <pattern> = ... ??
                              * */



        //todo {< inst-var-name =  expr  { ; inst-var-name =  expr } >} + classes + module types
    }
}