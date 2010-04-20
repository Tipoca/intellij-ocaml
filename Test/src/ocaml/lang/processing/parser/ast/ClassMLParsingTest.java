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
public class ClassMLParsingTest extends BaseClassParsingTest {
    public void testSeveralClassBindings() throws Exception {
        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class1");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, VIRTUAL_KEYWORD);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class1");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
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
        myTree.addNode(5, LCFC_IDENTIFIER, "class1");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
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
        myTree.addNode(5, LCFC_IDENTIFIER, "class1");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class1");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "label1");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "label2");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class1");
        myTree.addNode(4, COLON);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class2");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class1");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "label1");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "label2");
        myTree.addNode(4, COLON);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class2");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(3, AND_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class1");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "label1");
        myTree.addNode(4, PARAMETER);
        myTree.addNode(5, TILDE);
        myTree.addNode(5, LABEL_DEFINITION);
        myTree.addNode(6, LABEL_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "label2");
        myTree.addNode(5, COLON);
        myTree.addNode(5, VALUE_NAME_PATTERN);
        myTree.addNode(6, LCFC_IDENTIFIER, "x");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");

        doTest("class class1 = class0 and virtual class1 = class0 and ['a, 'b, 'c] class1 = class0 and virtual ['a, 'b, 'c] class1 = class0 and class1 ~label1 ~label2 = class0 and class1 : class2 = class0 and class1 ~label1 ~label2 : class2 = class0 and class1 ~label1 ~label2: x = class0", myTree.getStringRepresentation());
    }

    public void testClassPathExpression() throws Exception {
        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_PATH);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module1");
        myTree.addNode(5, DOT);
        myTree.addNode(5, MODULE_NAME);
        myTree.addNode(6, UCFC_IDENTIFIER, "Module2");
        myTree.addNode(5, DOT);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");

        doTest("class class0 = Module1.Module2.class1", myTree.getStringRepresentation());
    }

    public void testClassPathApplicationExpression() throws Exception {
        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
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
        myTree.addNode(7, LCFC_IDENTIFIER, "class1");

        doTest("class class0 = [int, int, int] Module1.Module2.class1", myTree.getStringRepresentation());
    }

    public void testParenthessedExpression() throws Exception {
        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, PARENTHESES);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");
        myTree.addNode(5, RPAR);

        doTest("class class0 = (class1)", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, CLASS_TYPE_CONSTRAINT);
        myTree.addNode(5, LPAR);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");
        myTree.addNode(5, COLON);
        myTree.addNode(5, OBJECT_END_CLASS_BODY_TYPE);
        myTree.addNode(6, OBJECT_KEYWORD);
        myTree.addNode(6, END_KEYWORD);
        myTree.addNode(5, RPAR);

        doTest("class class0 = (class1 : object end)", myTree.getStringRepresentation());
    }

    public void testFunctionApplicationExpression() throws Exception {
        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, FUNCTION_APPLICATION_CLASS_EXPRESSION);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, ARGUMENT);
        myTree.addNode(6, VALUE_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");

        doTest("class class0 = class1 a b c", myTree.getStringRepresentation());
    }

    public void testFunctionExpression() throws Exception {
        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, FUNCTION_CLASS_EXPRESSION);
        myTree.addNode(5, FUN_KEYWORD);
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "a");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "b");
        myTree.addNode(5, PARAMETER);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "c");
        myTree.addNode(5, MINUS_GT);
        myTree.addNode(5, CLASS_NAME);
        myTree.addNode(6, LCFC_IDENTIFIER, "class1");

        doTest("class class0 = fun a b c -> class1", myTree.getStringRepresentation());
    }

    public void testObjectEndExpression() throws Exception {
        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, OBJECT_SELF_DEFINIFTION);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "self");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object (self) end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, OBJECT_SELF_DEFINIFTION);
        myTree.addNode(6, LPAR);
        myTree.addNode(6, VALUE_NAME_PATTERN);
        myTree.addNode(7, LCFC_IDENTIFIER, "self");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, RPAR);
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object (self : int) end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, INHERIT_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, INHERIT_KEYWORD);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "class1");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object inherit class1 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, INHERIT_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, INHERIT_KEYWORD);
        myTree.addNode(6, CLASS_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "class1");
        myTree.addNode(6, AS_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "super");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object inherit class1 as super end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, VALUE_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object val x = 0 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, VALUE_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, MUTABLE_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object val mutable x = 0 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, VALUE_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object val x : int = 0 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, VALUE_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, MUTABLE_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object val mutable x : int = 0 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, VALUE_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object val virtual x : int end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, VALUE_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, VAL_KEYWORD);
        myTree.addNode(6, MUTABLE_KEYWORD);
        myTree.addNode(6, VIRTUAL_KEYWORD);
        myTree.addNode(6, INST_VAR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "x");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object val mutable virtual x : int end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, METHOD_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object method m = 0 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, METHOD_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, PRIVATE_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object method private m = 0 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, METHOD_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, PARAMETER);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, PARAMETER);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, PARAMETER);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "c");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object method m a b c = 0 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, METHOD_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, PARAMETER);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, PARAMETER);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "b");
        myTree.addNode(6, PARAMETER);
        myTree.addNode(7, VALUE_NAME_PATTERN);
        myTree.addNode(8, LCFC_IDENTIFIER, "c");
        myTree.addNode(6, COLON);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object method m a b c : int = 0 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, METHOD_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, METHOD_KEYWORD);
        myTree.addNode(6, METHOD_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "m");
        myTree.addNode(6, COLON);
        myTree.addNode(6, POLY_TYPE_EXPRESSION);
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "a");
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "b");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(6, EQ);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, INTEGER_LITERAL, "0");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object method m : 'a 'b . int = 0 end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, METHOD_CLASS_FIELD_DEFINITION);
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
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "b");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object method virtual m : 'a 'b . int end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, METHOD_CLASS_FIELD_DEFINITION);
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
        myTree.addNode(7, TYPE_PARAMETER_DEFINITION);
        myTree.addNode(8, QUOTE);
        myTree.addNode(8, TYPE_PARAMETER_NAME);
        myTree.addNode(9, LCFC_IDENTIFIER, "b");
        myTree.addNode(7, DOT);
        myTree.addNode(7, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object method private virtual m : 'a 'b . int end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, CONSTRAINT_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, CONSTRAINT_KEYWORD);
        myTree.addNode(6, TYPE_PARAMETER);
        myTree.addNode(7, QUOTE);
        myTree.addNode(7, TYPE_PARAMETER_NAME);
        myTree.addNode(8, LCFC_IDENTIFIER, "a");
        myTree.addNode(6, EQ);
        myTree.addNode(6, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(7, LCFC_IDENTIFIER, "int");
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object constraint 'a = int end", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, CLASS_DEFINITION);
        myTree.addNode(3, CLASS_KEYWORD);
        myTree.addNode(3, CLASS_BINDING);
        myTree.addNode(4, CLASS_NAME);
        myTree.addNode(5, LCFC_IDENTIFIER, "class0");
        myTree.addNode(4, EQ);
        myTree.addNode(4, OBJECT_END_CLASS_EXPRESSION);
        myTree.addNode(5, OBJECT_KEYWORD);
        myTree.addNode(5, INITIALIZER_CLASS_FIELD_DEFINITION);
        myTree.addNode(6, INITIALIZER_KEYWORD);
        myTree.addNode(6, CONSTANT);
        myTree.addNode(7, LPAR);
        myTree.addNode(7, RPAR);
        myTree.addNode(5, END_KEYWORD);

        doTest("class class0 = object initializer () end", myTree.getStringRepresentation());
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
