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

import ocaml.lang.processing.parser.ast.testCase.ParsingTestCase;
import org.testng.annotations.Test;

import static ocaml.lang.processing.lexer.token.OCamlTokenTypes.*;
import static ocaml.lang.processing.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 19.03.2009
 */
@Test
public abstract class BaseClassParsingTest extends ParsingTestCase {
    public void testSeveralClassTypeBindings() throws Exception {
        addCommonNodes();
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_TYPE_BINDING);
        myTree.addNode(4, VIRTUAL_KEYWORD);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class2");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_TYPE_BINDING);
        myTree.addNode(4, BRACKETS);
        myTree.addNode(5, LBRACKET);
        myTree.addNode(5, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, COMMA);
        myTree.addNode(5, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, COMMA);
        myTree.addNode(5, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");
        myTree.addNode(5, RBRACKET);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class3");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_TYPE_BINDING);
        myTree.addNode(4, VIRTUAL_KEYWORD);
        myTree.addNode(4, BRACKETS);
        myTree.addNode(5, LBRACKET);
        myTree.addNode(5, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, COMMA);
        myTree.addNode(5, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, COMMA);
        myTree.addNode(5, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(6, QUOTE);
        myTree.addNode(6, TYPE_PARAMETER_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");
        myTree.addNode(5, RBRACKET);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class3");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        
        doTest("class type class1 = class0 and virtual class2 = class0 and ['a, 'b, 'c] class3 = class0 and virtual ['a, 'b, 'c] class3 = class0", myTree.getStringRepresentation());
    }

    public void testClassPathClassBodyType() throws Exception {
        addCommonNodes();
        myTree.addNode(4, CLASS_PATH);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(5, DOT);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class0");

        doTest("class type class1 = Module1.Module2.class0", myTree.getStringRepresentation());
    }

    public void testClassPathApplicationClassBodyType() throws Exception {
        addCommonNodes();
        myTree.addNode(4, CLASS_PATH_APPLICATION);
        myTree.addNode(5, BRACKETS);
        myTree.addNode(6, LBRACKET);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, COMMA);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RBRACKET);
        myTree.addNode(5, CLASS_PATH);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(6, DOT);
        myTree.addNode(6, MODULE_NAME);
        myTree.addNode(7, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(6, DOT);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "class0");

        doTest("class type class1 = [int, int, int] Module1.Module2.class0", myTree.getStringRepresentation());
    }

    public void testObjectEndClassBodyType() throws Exception {
        addCommonNodes();
        myTree.addNode(4, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, END_KEYWORD);

        doTest("class type class1 = object end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(4, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, END_KEYWORD);

        doTest("class type class1 = object (int) end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(4, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(6, INHERIT_KEYWORD);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "class0");
        myTree.addNode(5, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, MUTABLE_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, MUTABLE_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, POLY_TYPE_EXPRESSION);
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, PRIVATE_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, POLY_TYPE_EXPRESSION);
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, POLY_TYPE_EXPRESSION);
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, PRIVATE_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, POLY_TYPE_EXPRESSION);
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, CONSTRAINT_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, CONSTRAINT_KEYWORD);
        myTree.addNode(6, TYPE_PARAMETER);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, END_KEYWORD);

        doTest("class type class1 = object " +
               "inherit class0 " +
               "val x : int " +
               "val mutable x : int " +
               "val virtual x : int " +
               "val mutable virtual x : int " +
               "method m : 'a . int " +
               "method private m : 'a . int " +
               "method virtual m : 'a . int " +
               "method private virtual m : 'a . int " +
               "constraint 'a = int " +
               "end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(4, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, PARENTHESES);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(6, INHERIT_KEYWORD);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "class0");
        myTree.addNode(5, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, MUTABLE_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, VALUE_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, MUTABLE_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, POLY_TYPE_EXPRESSION);
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, PRIVATE_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, POLY_TYPE_EXPRESSION);
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, POLY_TYPE_EXPRESSION);
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, METHOD_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, PRIVATE_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, POLY_TYPE_EXPRESSION);
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, CONSTRAINT_CLASS_FIELD_SPECIFICATION);
        myTree.addNode(6, CONSTRAINT_KEYWORD);
        myTree.addNode(6, TYPE_PARAMETER);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, END_KEYWORD);

        doTest("class type class1 = object (int) " +
               "inherit class0 " +
               "val x : int " +
               "val mutable x : int " +
               "val virtual x : int " +
               "val mutable virtual x : int " +
               "method m : 'a . int " +
               "method private m : 'a . int " +
               "method virtual m : 'a . int " +
               "method private virtual m : 'a . int " +
               "constraint 'a = int " +
               "end", myTree.getStringRepresentation());
    }

    public void testClassType() throws Exception {
        addCommonNodes();
        myTree.addNode(4, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(6, INHERIT_KEYWORD);
        myTree.addNode(6, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(7, OBJECT_KEYWORD);
        myTree.addNode(7, END_KEYWORD);
        myTree.addNode(5, END_KEYWORD);

        doTest("class type class1 = object inherit object end end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(4, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(6, INHERIT_KEYWORD);
        myTree.addNode(6, FUNCTION_CLASS_TYPE);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, MINUS_GT);
        myTree.addNode(7, FUNCTION_CLASS_TYPE);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(8, MINUS_GT);
        myTree.addNode(8, CLASS_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "myClass");
        myTree.addNode(5, END_KEYWORD);

        doTest("class type class1 = object inherit int -> int -> myClass end", myTree.getStringRepresentation());

        recreateTree();

        addCommonNodes();
        myTree.addNode(4, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, INHERIT_CLASS_FILED_SPECIFICATION);
        myTree.addNode(6, INHERIT_KEYWORD);
        myTree.addNode(6, FUNCTION_CLASS_TYPE);
        myTree.addNode(7, LABEL_DEFINITION);
        myTree.addNode(8, LABEL_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "label");
        myTree.addNode(7, COLON);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(7, MINUS_GT);
        myTree.addNode(7, FUNCTION_CLASS_TYPE);
        myTree.addNode(8, QUEST);
        myTree.addNode(8, LABEL_DEFINITION);
        myTree.addNode(9, LABEL_NAME);
        myTree.addNode(10, LCFC_IDENTIFIER, "label");
        myTree.addNode(8, COLON);
        myTree.addNode(8, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "int");
        myTree.addNode(8, MINUS_GT);
        myTree.addNode(8, CLASS_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "myClass");
        myTree.addNode(5, END_KEYWORD);

        doTest("class type class1 = object inherit label: int -> ?label: int -> myClass end", myTree.getStringRepresentation());
    }

    private void addCommonNodes() {
        myTree.addNode(2, CLASS_TYPE_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, TYPE_KEYWORD);
        myTree.addNode(3, CLASS_TYPE_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class1");
        myTree.addNode(4, EQ);
    }
}
