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

package manuylov.maxim.ocaml.lang.parser.psi.resolving;

import manuylov.maxim.ocaml.lang.parser.psi.resolving.testCase.ResolvingTestCase;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 19.06.2009
 */
@Test
public class ModuleNameResolvingTest extends ResolvingTestCase {
    public void testModuleNameResolving() throws Exception {
        doTest(1, "" +
            "module {{M = M1}};;" +
            "module M2 = }{M;;");

        doTest(2, "module M = }{M1;;");

        doTest(3, "" +
            "module M = " +
            "struct " +
            "  module {{M1 = struct end}};; " +
            "end;; " +
            "module M2 = M.}{M1;;");

        doTest(4, "" +
            "module {{M = " +
            "struct " +
            "  module M1 = struct end;; " +
            "end}};; " +
            "module M2 = }{M.M1;;");

        doTest(5, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module {{M3 = struct end}};;" +
            "  end;; " +
            "end;; " +
            "module M2 = M.M1.}{M3;;");

        doTest(6, "" +
            "module M = " +
            "struct " +
            "  module {{M1 = " +
            "  struct " +
            "    module M3 = struct end;;" +
            "  end}};; " +
            "end;; " +
            "module M2 = M.}{M1.M3;;");

        doTest(7, "" +
            "module {{M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module M3 = struct end;;" +
            "  end;; " +
            "end}};; " +
            "module M2 = }{M.M1.M3;;");

        doTest(8, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module M3 = struct end;;" +
            "  end;; " +
            "end;; " +
            "module M2 = }{M1;;");

        doTest(9, "" +
            "module M = " +
            "struct " +
            "  module {{M1 = " +
            "  struct " +
            "    module M3 = struct end;;" +
            "  end}};; " +
            "end;; " +
            "open M;;" +
            "module M2 = }{M1;;");

        doTest(10, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module {{M3 = struct end}};;" +
            "  end;; " +
            "end;; " +
            "open M;;" +
            "module M2 = M1.}{M3;;");

        doTest(11, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module {{M3 = struct end}};;" +
            "  end;; " +
            "end;; " +
            "open M.M1;;" +
            "module M2 = }{M3;;");

        doTest(12, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module {{M3 = struct end}};;" +
            "  end;; " +
            "end;; " +
            "open M;;" +
            "open M1;;" +
            "module M2 = }{M3;;");

        doTest(13, "" +
            "module M = " +
            "struct " +
            "  module M1 = }{M;;" +
            "end;; ");

        doTest(14, "" +
            "module M = " +
            "struct " +
            "  module {{M1 = " +
            "  struct " +
            "    module M3 = struct end;;" +
            "  end}};; " +
            "end;; " +
            "include M;;" +
            "module M2 = }{M1;;");

        doTest(15, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module {{M3 = struct end}};;" +
            "  end;; " +
            "end;; " +
            "include M;;" +
            "module M2 = M1.}{M3;;");

        doTest(16, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module {{M3 = struct end}};;" +
            "  end;; " +
            "end;; " +
            "include M.M1;;" +
            "module M2 = }{M3;;");

        doTest(17, "" +
            "module M = " +
            "struct " +
            "  module M1 = " +
            "  struct " +
            "    module {{M3 = struct end}};;" +
            "  end;; " +
            "end;; " +
            "include M;;" +
            "include M1;;" +
            "module M2 = }{M3;;");

        doTest(18, "" +
            "module {{}{M = struct end}};;");
    }
}
