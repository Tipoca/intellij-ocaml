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

import com.intellij.lang.ParserDefinition;
import com.intellij.psi.tree.IElementType;
import ocaml.lang.fileType.ml.MLFileTypeLanguage;
import ocaml.lang.processing.parser.ast.element.OCamlElementTypes;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static ocaml.lang.processing.lexer.token.OCamlTokenTypes.*;
import static ocaml.lang.processing.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 19.03.2009
 */
@Test
public class ModuleMLParsingTest extends BaseModuleParsingTest {
    public void testModuleDefinition() throws Exception {
        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, EQ);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule = Module", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, COLON);
        myTree.addNode(4, MODULE_TYPE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "ModuleTypeName");
        myTree.addNode(4, EQ);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule : ModuleTypeName = Module", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, PARENTHESES);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, MODULE_PARAMETER);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(6, COLON);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleTypeName1");
        myTree.addNode(5, RPAR);
        myTree.addNode(4, PARENTHESES);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, MODULE_PARAMETER);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(6, COLON);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleTypeName2");
        myTree.addNode(5, RPAR);
        myTree.addNode(4, EQ);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule (Module1 : ModuleTypeName1) (Module2 : ModuleTypeName2) = Module", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, PARENTHESES);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, MODULE_PARAMETER);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(6, COLON);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleTypeName1");
        myTree.addNode(5, RPAR);
        myTree.addNode(4, PARENTHESES);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, MODULE_PARAMETER);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(6, COLON);
        myTree.addNode(6, MODULE_TYPE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleTypeName2");
        myTree.addNode(5, RPAR);
        myTree.addNode(4, COLON);
        myTree.addNode(4, MODULE_TYPE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "ModuleTypeName");
        myTree.addNode(4, EQ);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule (Module1 : ModuleTypeName1) (Module2 : ModuleTypeName2) : ModuleTypeName = Module", myTree.getStringRepresentation());
    }

    public void testModulePathExpression() throws Exception {
        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, EQ);
        myTree.addNode(4, MODULE_PATH);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module");

        doTest("module MyModule = Module1.Module", myTree.getStringRepresentation());
    }

    public void testStructEndExpression() throws Exception {
        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, EQ);
        myTree.addNode(4, STRUCT_END_MODULE_EXPRESSION);
        myTree.addNode(5, STRUCT_KEYWORD);
        myTree.addNode(5, EXCEPTION_DEFINITION);
        myTree.addNode(6, EXCEPTION_KEYWORD);
        myTree.addNode(6, CONSTRUCTOR_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Error");
        myTree.addNode(5, END_KEYWORD);

        doTest("module MyModule = struct exception Error end", myTree.getStringRepresentation());
    }

    public void testFunctorExpression() throws Exception {
        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, EQ);
        myTree.addNode(4, FUNCTOR_MODULE_EXPRESSION);
        myTree.addNode(5, FUNCTOR_KEYWORD);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, MODULE_PARAMETER);
        myTree.addNode(7, MODULE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(7, COLON);
        myTree.addNode(7, MODULE_TYPE_NAME);
        myTree.addNode(8, UCFC_IDENTIFIER, "ModuleTypeName");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, MINUS_GT);
        myTree.addNode(5, STRUCT_END_MODULE_EXPRESSION);
        myTree.addNode(6, STRUCT_KEYWORD);
        myTree.addNode(6, END_KEYWORD);

        doTest("module MyModule = functor (ModuleName : ModuleTypeName) -> struct end", myTree.getStringRepresentation());
    }

    public void testFunctorApplicationExpression() throws Exception {
        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, EQ);
        myTree.addNode(4, FUNCTOR_APPLICATION_MODULE_EXPRESSION);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "ModuleName2");
        myTree.addNode(6, RPAR);

        doTest("module MyModule = ModuleName(ModuleName2)", myTree.getStringRepresentation());
    }

    public void testParenthessedExpression() throws Exception {
        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, EQ);
        myTree.addNode(4, PARENTHESES);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(5, RPAR);

        doTest("module MyModule = (ModuleName)", myTree.getStringRepresentation());
    }

    public void testModuleTypeConstraintExpression() throws Exception {
        myTree.addNode(2, MODULE_DEFINITION);
        myTree.addNode(3, MODULE_KEYWORD);
        myTree.addNode(3, MODULE_DEFINITION_BINDING);
        myTree.addNode(4, MODULE_NAME);
        myTree.addNode(5, UCFC_IDENTIFIER, "MyModule");
        myTree.addNode(4, EQ);
        myTree.addNode(4, MODULE_TYPE_CONSTRAINT_MODULE_EXPRESSION);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "ModuleName");
        myTree.addNode(5, COLON);
        myTree.addNode(5, MODULE_TYPE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "ModuleTypeName");
        myTree.addNode(5, RPAR);

        doTest("module MyModule = (ModuleName : ModuleTypeName)", myTree.getStringRepresentation());
    }

    @NotNull
    protected IElementType getMainElement() {
        return MODULE_TYPE_DEFINITION;
    }

    @NotNull
    @Override
    protected IElementType getTypeBindingElement() {
        return MODULE_TYPE_DEFINITION_BINDING;
    }

    @NotNull
    protected ParserDefinition getParserDefinition() {
        return MLFileTypeLanguage.INSTANCE.getParserDefinition();
    }

    @NotNull
    protected IElementType getModuleNodeType() {
        return OCamlElementTypes.FILE_MODULE_EXPRESSION;
    }
}
