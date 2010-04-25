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

package ocaml.lang.processing.parser.ast;

import com.intellij.psi.tree.IElementType;
import ocaml.lang.processing.parser.ast.testCase.ParsingTestCase;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static ocaml.lang.processing.lexer.token.OCamlTokenTypes.*;
import static ocaml.lang.processing.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 19.03.2009
 */
@Test
public abstract class BaseModuleParsingTest extends ParsingTestCase {
    public void testModuleTypePathModuleType() throws Exception {
        myTree.addNode(2, getMainElement());
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, TYPE_KEYWORD);
        myTree.addNode(3, getTypeBindingElement());
        myTree.addNode(4, MODULE_TYPE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "m");
        myTree.addNode(4, EQ);
        myTree.addNode(4, MODULE_TYPE_PATH);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "M1");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "M2");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "m");

        doTest("module type m = M1.M2.m", myTree.getStringRepresentation());
    }

    public void testModuleTypeInParenthess() throws Exception {
        myTree.addNode(2, getMainElement());
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, TYPE_KEYWORD);
        myTree.addNode(3, getTypeBindingElement());
        myTree.addNode(4, MODULE_TYPE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "m");
        myTree.addNode(4, EQ);
        myTree.addNode(4, PARENTHESES);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "M");
        myTree.addNode(5, RPAR);

        doTest("module type m = (M)", myTree.getStringRepresentation());
    }

    public void testModuleTypeWithConstraints() throws Exception {
        myTree.addNode(2, getMainElement());
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, TYPE_KEYWORD);
        myTree.addNode(3, getTypeBindingElement());
        myTree.addNode(4, MODULE_TYPE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "m");
        myTree.addNode(4, EQ);
        myTree.addNode(4, MODULE_TYPE_WITH_CONSTRAINTS);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "M");
        myTree.addNode(5, WITH_KEYWORD);
        myTree.addNode(5, MODULE_TYPE_MODULE_CONSTRAINT);
        myTree.addNode(6, MODULE_KEYWORD);
        myTree.addNode(6, MODULE_PATH);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "M1");
        myTree.addNode(7, DOT);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "M2");
        myTree.addNode(7, DOT);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "Mod");
        myTree.addNode(6, EQ);
        myTree.addNode(6, EXTENDED_MODULE_NAME);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "M4");
        myTree.addNode(7, PARENTHESES);
        myTree.addNode(8, LPAR);
        myTree.addNode(8, MODULE_NAME);
        myTree.addNode(9, UCFC_IDENTIFIER, "M5");
        myTree.addNode(8, RPAR);
        myTree.addNode(5, AND_KEYWORD);
        myTree.addNode(5, MODULE_TYPE_TYPE_CONSTRAINT);
        myTree.addNode(6, TYPE_KEYWORD);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "t");
        myTree.addNode(6, EQ);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, AND_KEYWORD);
        myTree.addNode(5, MODULE_TYPE_TYPE_CONSTRAINT);
        myTree.addNode(6, TYPE_KEYWORD);
        myTree.addNode(6, TYPE_PARAMETER);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_PARAMETER);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "c");
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "tt");
        myTree.addNode(6, EQ);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");

        doTest("module type m = M with module M1.M2.Mod = M4(M5) and type t = int and type 'a, 'b, 'c tt = int", myTree.getStringRepresentation());
    }

    public void testFunctorModuleType() throws Exception {
        myTree.addNode(2, getMainElement());
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, TYPE_KEYWORD);
        myTree.addNode(3, getTypeBindingElement());
        myTree.addNode(4, MODULE_TYPE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "m");
        myTree.addNode(4, EQ);
        myTree.addNode(4, FUNCTOR_MODULE_TYPE);
        myTree.addNode(5, FUNCTOR_KEYWORD);
        myTree.addNode(5, MODULE_PARAMETER);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(6, COLON);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleTypeName");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, MINUS_GT);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "ModuleTypeName");

        doTest("module type m = functor (ModuleName : ModuleTypeName) -> ModuleTypeName", myTree.getStringRepresentation());
    }

    public void testSigEndModuleType() throws Exception {
        myTree.addNode(2, getMainElement());
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, TYPE_KEYWORD);
        myTree.addNode(3, getTypeBindingElement());
        myTree.addNode(4, MODULE_TYPE_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "m");
        myTree.addNode(4, EQ);
        myTree.addNode(4, SIG_END_MODULE_TYPE);
        myTree.addNode(5, SIG_KEYWORD);
        myTree.addNode(5, VALUE_SPECIFICATION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, END_KEYWORD);

        doTest("module type m = sig val a : int end", myTree.getStringRepresentation());
    }

    @NotNull
    protected abstract IElementType getMainElement();

    @NotNull
    protected abstract IElementType getTypeBindingElement();
}
