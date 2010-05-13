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

package manuylov.maxim.ocaml.lang.parser.ast.element;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import manuylov.maxim.ocaml.lang.OCamlElementType;
import manuylov.maxim.ocaml.fileType.ml.MLFileTypeLanguage;
import manuylov.maxim.ocaml.fileType.mli.MLIFileTypeLanguage;

/**
 * @author Maxim.Manuylov
 *         Date: 05.02.2009
 */
public interface OCamlElementTypes {
    IFileElementType ML_FILE = new IFileElementType("ML_FILE", MLFileTypeLanguage.INSTANCE);
    IFileElementType MLI_FILE = new IFileElementType("MLI_FILE", MLIFileTypeLanguage.INSTANCE);

    /* Let parsing { */

    IElementType LET_BINDING = new OCamlElementType("LET_BINDING");
    IElementType LET_BINDING_PATTERN = new OCamlElementType("LET_BINDING_PATTERN");
    IElementType LET_STATEMENT = new OCamlElementType("LET_STATEMENT");
    IElementType LET_EXPRESSION = new OCamlElementType("LET_EXPRESSION");
    IElementType LET_CLASS_EXPRESSION = new OCamlElementType("LET_CLASS_EXPRESSION");

    /* } */

    /* Statement parsing { */

    IElementType EXTERNAL_DEFINITION = new OCamlElementType("EXTERNAL_DEFINITION");
    IElementType EXCEPTION_DEFINITION = new OCamlElementType("EXCEPTION_DEFINITION");
    IElementType OPEN_DIRECTIVE = new OCamlElementType("OPEN_DIRECTIVE");
    IElementType INCLUDE_DIRECTIVE_DEFINITION = new OCamlElementType("INCLUDE_DIRECTIVE_DEFINITION");
    IElementType EXCEPTION_SPECIFICATION = new OCamlElementType("EXCEPTION_SPECIFICATION");
    IElementType INCLUDE_DIRECTIVE_SPECIFICATION = new OCamlElementType("INCLUDE_DIRECTIVE_SPECIFICATION");
    IElementType VALUE_SPECIFICATION = new OCamlElementType("VALUE_SPECIFICATION");
    IElementType EXTERNAL_DECLARATION = new OCamlElementType("EXTERNAL_DECLARATION");

    /* } */

    /* Type parsing { */

    IElementType TYPE_DEFINITION = new OCamlElementType("TYPE_DEFINITION");
    IElementType TYPE_PARAMETERIZED_BINDING = new OCamlElementType("TYPE_PARAMETERIZED_BINDING");
    IElementType TYPE_BINDING = new OCamlElementType("TYPE_BINDING");
    IElementType TYPE_PARAMETER = new OCamlElementType("TYPE_PARAMETER");
    IElementType TYPE_PARAMETER_DEFINITION = new OCamlElementType("TYPE_PARAMETER_DEFINITION");
    IElementType PLUS_MINUS_TYPE_PARAMETER = new OCamlElementType("PLUS_MINUS_TYPE_PARAMETER");
    IElementType RECORD_TYPE_DEFINITION = new OCamlElementType("RECORD_TYPE_DEFINITION");
    IElementType RECORD_FIELD_DEFINITION = new OCamlElementType("RECORD_FIELD_DEFINITION");
    IElementType VARIANT_TYPE_DEFINITION = new OCamlElementType("VARIANT_TYPE_DEFINITION");
    IElementType CONSTRUCTOR_DEFINITION = new OCamlElementType("CONSTRUCTOR_DEFINITION");
    IElementType TYPE_DEFINITION_CONSTRAINT = new OCamlElementType("TYPE_DEFINITION_CONSTRAINT");
    IElementType AS_TYPE_EXPRESSION = new OCamlElementType("AS_TYPE_EXPRESSION");
    IElementType FUNCTION_TYPE_EXPRESSION = new OCamlElementType("FUNCTION_TYPE_EXPRESSION");
    IElementType TUPLE_TYPE_EXPRESSION = new OCamlElementType("TUPLE_TYPE_EXPRESSION");
    IElementType SUPER_CLASS_TYPE_EXPRESSION = new OCamlElementType("SUPER_CLASS_TYPE_EXPRESSION");
    IElementType TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION = new OCamlElementType("TYPE_CONSTRUCTOR_APPLICATION_TYPE_EXPRESSION");
    IElementType OBJECT_INTERFACE_TYPE_EXPRESSION = new OCamlElementType("OBJECT_INTERFACE_TYPE_EXPRESSION");
    IElementType METHOD_TYPE = new OCamlElementType("METHOD_TYPE");
    IElementType VARIANT_TYPE_TYPE_EXPRESSION = new OCamlElementType("VARIANT_TYPE_TYPE_EXPRESSION");
    IElementType TAG_SPEC = new OCamlElementType("TAG_SPEC");
    IElementType TAG_SPEC_FULL = new OCamlElementType("TAG_SPEC_FULL");
    IElementType POLY_TYPE_EXPRESSION = new OCamlElementType("POLY_TYPE_EXPRESSION");
    IElementType PARENTHESES_TYPE_EXPRESSION = new OCamlElementType("PARENTHESES_TYPE_EXPRESSION");
    IElementType PARENTHESES_TYPE_PARAMETERS = new OCamlElementType("PARENTHESES_TYPE_PARAMETERS");

    /* } */

    /* Class parsing { */

    IElementType CLASS_DEFINITION = new OCamlElementType("CLASS_DEFINITION");
    IElementType CLASS_BINDING = new OCamlElementType("CLASS_BINDING");
    IElementType CLASS_TYPE_DEFINITION = new OCamlElementType("CLASS_TYPE_DEFINITION");
    IElementType CLASS_TYPE_BINDING = new OCamlElementType("CLASS_TYPE_BINDING");
    IElementType FUNCTION_CLASS_TYPE = new OCamlElementType("FUNCTION_CLASS_TYPE");
    IElementType CLASS_PATH_APPLICATION = new OCamlElementType("CLASS_PATH_APPLICATION");
    IElementType OBJECT_END_CLASS_BODY_TYPE = new OCamlElementType("OBJECT_END_CLASS_BODY_TYPE");
    IElementType INHERIT_CLASS_FILED_SPECIFICATION = new OCamlElementType("INHERIT_CLASS_FILED_SPECIFICATION");
    IElementType VALUE_CLASS_FIELD_SPECIFICATION = new OCamlElementType("VALUE_CLASS_FIELD_SPECIFICATION");
    IElementType METHOD_CLASS_FIELD_SPECIFICATION = new OCamlElementType("METHOD_CLASS_FIELD_SPECIFICATION");
    IElementType CONSTRAINT_CLASS_FIELD_SPECIFICATION = new OCamlElementType("CONSTRAINT_CLASS_FIELD_SPECIFICATION");
    IElementType FUNCTION_APPLICATION_CLASS_EXPRESSION = new OCamlElementType("FUNCTION_APPLICATION_CLASS_EXPRESSION");
    IElementType OBJECT_END_CLASS_EXPRESSION = new OCamlElementType("OBJECT_END_CLASS_EXPRESSION");
    IElementType OBJECT_SELF_DEFINITION = new OCamlElementType("OBJECT_SELF_DEFINITION");
    IElementType INHERIT_CLASS_FIELD_DEFINITION = new OCamlElementType("INHERIT_CLASS_FIELD_DEFINITION");
    IElementType INITIALIZER_CLASS_FIELD_DEFINITION = new OCamlElementType("INITIALIZER_CLASS_FIELD_DEFINITION");
    IElementType CONSTRAINT_CLASS_FIELD_DEFINITION = new OCamlElementType("CONSTRAINT_CLASS_FIELD_DEFINITION");
    IElementType VALUE_CLASS_FIELD_DEFINITION = new OCamlElementType("VALUE_CLASS_FIELD_DEFINITION");
    IElementType METHOD_CLASS_FIELD_DEFINITION = new OCamlElementType("METHOD_CLASS_FIELD_DEFINITION");
    IElementType FUNCTION_CLASS_EXPRESSION = new OCamlElementType("FUNCTION_CLASS_EXPRESSION");
    IElementType CLASS_SPECIFICATION = new OCamlElementType("CLASS_SPECIFICATION");
    IElementType CLASS_SPECIFICATION_BINDING = new OCamlElementType("CLASS_SPECIFICATION_BINDING");
    IElementType CLASS_TYPE_CONSTRAINT = new OCamlElementType("CLASS_TYPE_CONSTRAINT");
    IElementType PARENTHESES_CLASS_EXPRESSION = new OCamlElementType("PARENTHESES_CLASS_EXPRESSION");

    /* } */

    /* Module parsing { */

    IElementType MODULE_DEFINITION = new OCamlElementType("MODULE_DEFINITION");
    IElementType MODULE_TYPE_DEFINITION = new OCamlElementType("MODULE_TYPE_DEFINITION");
    IElementType FUNCTOR_MODULE_TYPE = new OCamlElementType("FUNCTOR_MODULE_TYPE");
    IElementType SIG_END_MODULE_TYPE = new OCamlElementType("SIG_END_MODULE_TYPE");
    IElementType MODULE_TYPE_TYPE_CONSTRAINT = new OCamlElementType("MODULE_TYPE_TYPE_CONSTRAINT");
    IElementType MODULE_TYPE_MODULE_CONSTRAINT = new OCamlElementType("MODULE_TYPE_MODULE_CONSTRAINT");
    IElementType FUNCTOR_APPLICATION_MODULE_EXPRESSION = new OCamlElementType("FUNCTOR_APPLICATION_MODULE_EXPRESSION");
    IElementType STRUCT_END_MODULE_EXPRESSION = new OCamlElementType("STRUCT_END_MODULE_EXPRESSION");
    IElementType FUNCTOR_MODULE_EXPRESSION = new OCamlElementType("FUNCTOR_MODULE_EXPRESSION");
    IElementType MODULE_TYPE_CONSTRAINT_MODULE_EXPRESSION = new OCamlElementType("MODULE_TYPE_CONSTRAINT_MODULE_EXPRESSION");
    IElementType MODULE_TYPE_WITH_CONSTRAINTS = new OCamlElementType("MODULE_TYPE_WITH_CONSTRAINTS");
    IElementType MODULE_TYPE_SPECIFICATION = new OCamlElementType("MODULE_TYPE_SPECIFICATION");
    IElementType MODULE_SPECIFICATION = new OCamlElementType("MODULE_SPECIFICATION");
    IElementType MODULE_TYPE_DEFINITION_BINDING = new OCamlElementType("MODULE_TYPE_DEFINITION_BINDING");
    IElementType MODULE_TYPE_SPECIFICATION_BINDING = new OCamlElementType("MODULE_TYPE_SPECIFICATION_BINDING");
    IElementType MODULE_DEFINITION_BINDING = new OCamlElementType("MODULE_DEFINITION_BINDING");
    IElementType MODULE_SPECIFICATION_BINDING = new OCamlElementType("MODULE_SPECIFICATION_BINDING");
    IElementType MODULE_PARAMETER = new OCamlElementType("MODULE_PARAMETER");
    IElementType FILE_MODULE_DEFINITION_BINDING = new OCamlElementType("FILE_MODULE_DEFINITION_BINDING");
    IElementType FILE_MODULE_EXPRESSION = new OCamlElementType("FILE_MODULE_EXPRESSION");
    IElementType FILE_MODULE_SPECIFICATION_BINDING = new OCamlElementType("FILE_MODULE_SPECIFICATION_BINDING");
    IElementType FILE_MODULE_TYPE = new OCamlElementType("FILE_MODULE_TYPE");
    IElementType PARENTHESES_MODULE_EXPRESSION = new OCamlElementType("PARENTHESES_MODULE_EXPRESSION");
    IElementType PARENTHESES_MODULE_TYPE = new OCamlElementType("PARENTHESES_MODULE_TYPE");

    /* } */

    /* Name parsing { */

    IElementType VALUE_NAME = new OCamlElementType("VALUE_NAME");
    IElementType OPERATOR_NAME = new OCamlElementType("OPERATOR_NAME");
    IElementType MODULE_PATH = new OCamlElementType("MODULE_PATH");
    IElementType MODULE_NAME = new OCamlElementType("MODULE_NAME");
    IElementType CONSTRUCTOR_PATH = new OCamlElementType("CONSTRUCTOR_PATH");
    IElementType CONSTRUCTOR_NAME = new OCamlElementType("CONSTRUCTOR_NAME");
    IElementType TYPE_CONSTRUCTOR_NAME = new OCamlElementType("TYPE_CONSTRUCTOR_NAME");
    IElementType INST_VAR_NAME_DEFINITION = new OCamlElementType("INST_VAR_NAME_DEFINITION");
    IElementType CONSTRUCTOR_NAME_DEFINITION = new OCamlElementType("CONSTRUCTOR_NAME_DEFINITION");
    IElementType TYPE_PARAMETER_NAME = new OCamlElementType("TYPE_PARAMETER_NAME");
    IElementType FIELD_NAME = new OCamlElementType("FIELD_NAME");
    IElementType CLASS_PATH = new OCamlElementType("CLASS_PATH");
    IElementType CLASS_NAME = new OCamlElementType("CLASS_NAME");
    IElementType METHOD_NAME = new OCamlElementType("METHOD_NAME");
    IElementType INST_VAR_NAME = new OCamlElementType("INST_VAR_NAME");
    IElementType LABEL_NAME = new OCamlElementType("LABEL_NAME");
    IElementType MODULE_TYPE_NAME = new OCamlElementType("MODULE_TYPE_NAME");
    IElementType EXTENDED_MODULE_PATH = new OCamlElementType("EXTENDED_MODULE_PATH");
    IElementType EXTENDED_MODULE_NAME = new OCamlElementType("EXTENDED_MODULE_NAME");
    IElementType TYPE_CONSTRUCTOR_PATH = new OCamlElementType("TYPE_CONSTRUCTOR_PATH");
    IElementType MODULE_TYPE_PATH = new OCamlElementType("MODULE_TYPE_PATH");
    IElementType TAG_NAME = new OCamlElementType("TAG_NAME");
    IElementType VALUE_PATH = new OCamlElementType("VALUE_PATH");
    IElementType FIELD_PATH = new OCamlElementType("FIELD_PATH");
    IElementType CONSTANT = new OCamlElementType("CONSTANT");
    IElementType FOR_EXPRESSION_INDEX_VARIABLE_NAME = new OCamlElementType("FOR_EXPRESSION_INDEX_VARIABLE_NAME");

    /* } */

    /* Expression parsing { */

    IElementType MATCH_EXPRESSION = new OCamlElementType("MATCH_EXPRESSION");
    IElementType FUN_EXPRESSION = new OCamlElementType("FUN_EXPRESSION");
    IElementType FUNCTION_EXPRESSION = new OCamlElementType("FUNCTION_EXPRESSION");
    IElementType TRY_EXPRESSION = new OCamlElementType("TRY_EXPRESSION");
    IElementType PATTERN_MATCHING = new OCamlElementType("PATTERN_MATCHING");
    IElementType SEMICOLON_EXPRESSION = new OCamlElementType("SEMICOLON_EXPRESSION");
    IElementType IF_EXPRESSION = new OCamlElementType("IF_EXPRESSION");
    IElementType FOR_EXPRESSION = new OCamlElementType("FOR_EXPRESSION");
    IElementType FOR_EXPRESSION_BINDING = new OCamlElementType("FOR_EXPRESSION_BINDING");
    IElementType WHILE_EXPRESSION = new OCamlElementType("WHILE_EXPRESSION");
    IElementType ASSIGNMENT_EXPRESSION = new OCamlElementType("ASSIGNMENT_EXPRESSION");
    IElementType COMMA_EXPRESSION = new OCamlElementType("COMMA_EXPRESSION");
    IElementType BINARY_EXPRESSION = new OCamlElementType("BINARY_EXPRESSION");
    IElementType HEAD_TAIL_EXPRESSION = new OCamlElementType("HEAD_TAIL_EXPRESSION");
    IElementType ASSERT_EXPRESSION = new OCamlElementType("ASSERT_EXPRESSION");
    IElementType LAZY_EXPRESSION = new OCamlElementType("LAZY_EXPRESSION");
    IElementType CONSTRUCTOR_APPLICATION_EXPRESSION = new OCamlElementType("CONSTRUCTOR_APPLICATION_EXPRESSION");
    IElementType FUNCTION_APPLICATION_EXPRESSION = new OCamlElementType("FUNCTION_APPLICATION_EXPRESSION");
    IElementType ARRAY_ELEMENT_ACCESSING_EXPRESSION = new OCamlElementType("ARRAY_ELEMENT_ACCESSING_EXPRESSION");
    IElementType STRING_CHAR_ACCESSING_EXPRESSION = new OCamlElementType("STRING_CHAR_ACCESSING_EXPRESSION");
    IElementType RECORD_FIELD_ACCESSING_EXPRESSION = new OCamlElementType("RECORD_FIELD_ACCESSING_EXPRESSION");
    IElementType RECORD_FIELD_INITIALIZATION_IN_EXPRESSION = new OCamlElementType("RECORD_FIELD_INITIALIZATION_IN_EXPRESSION");
    IElementType RECORD_FIELD_INITIALIZATION_IN_PATTERN = new OCamlElementType("RECORD_FIELD_INITIALIZATION_IN_PATTERN");
    IElementType UNARY_EXPRESSION = new OCamlElementType("UNARY_EXPRESSION");
    IElementType CLASS_METHOD_ACCESSING_EXPRESSION = new OCamlElementType("CLASS_METHOD_ACCESSING_EXPRESSION");
    IElementType TAGGED_EXPRESSION = new OCamlElementType("TAGGED_EXPRESSION");
    IElementType TYPE_CONSTRAINT_EXPRESSION = new OCamlElementType("TYPE_CONSTRAINT_EXPRESSION");
    IElementType CASTING_EXPRESSION = new OCamlElementType("CASTING_EXPRESSION");
    IElementType LIST_EXPRESSION = new OCamlElementType("LIST_EXPRESSION");
    IElementType ARRAY_EXPRESSION = new OCamlElementType("ARRAY_EXPRESSION");
    IElementType RECORD_EXPRESSION = new OCamlElementType("RECORD_EXPRESSION");
    IElementType INHERITED_RECORD_EXPRESSION = new OCamlElementType("INHERITED_RECORD_EXPRESSION");
    IElementType INSTANCE_DUPLICATING_EXPRESSION = new OCamlElementType("INSTANCE_DUPLICATING_EXPRESSION");
    IElementType OBJECT_CLASS_BODY_END_EXPRESSION = new OCamlElementType("OBJECT_CLASS_BODY_END_EXPRESSION");
    IElementType NEW_INSTANCE_EXPRESSION = new OCamlElementType("NEW_INSTANCE_EXPRESSION");
    IElementType ARGUMENT = new OCamlElementType("ARGUMENT");
    IElementType PARAMETER = new OCamlElementType("PARAMETER");
    IElementType LABEL_DEFINITION = new OCamlElementType("LABEL_DEFINITION");
    IElementType PARENTHESES_EXPRESSION = new OCamlElementType("PARENTHESES_EXPRESSION");

    /* } */

    /* Pattern parsing { */

    IElementType VALUE_NAME_PATTERN = new OCamlElementType("VALUE_NAME_PATTERN");
    IElementType AS_PATTERN = new OCamlElementType("AS_PATTERN");
    IElementType OR_PATTERN = new OCamlElementType("OR_PATTERN");
    IElementType COMMA_PATTERN = new OCamlElementType("COMMA_PATTERN");
    IElementType HEAD_TAIL_PATTERN = new OCamlElementType("HEAD_TAIL_PATTERN");
    IElementType CONSTRUCTOR_APPLICATION_PATTERN = new OCamlElementType("CONSTRUCTOR_APPLICATION_PATTERN");
    IElementType TAGGED_PATTERN = new OCamlElementType("TAGGED_PATTERN");
    IElementType TYPE_CONSTRUCTOR_PATTERN = new OCamlElementType("TYPE_CONSTRUCTOR_PATTERN");
    IElementType LAZY_PATTERN = new OCamlElementType("LAZY_PATTERN");
    IElementType LIST_PATTERN = new OCamlElementType("LIST_PATTERN");
    IElementType ARRAY_PATTERN = new OCamlElementType("ARRAY_PATTERN");
    IElementType RECORD_PATTERN = new OCamlElementType("RECORD_PATTERN");
    IElementType TYPE_CONSTRAINT_PATTERN = new OCamlElementType("TYPE_CONSTRAINT_PATTERN");
    IElementType CHAR_RANGE_PATTERN = new OCamlElementType("CHAR_RANGE_PATTERN");
    IElementType PARENTHESES_PATTERN = new OCamlElementType("PARENTHESES_PATTERN");

    /* } */

    /* Comments { */

    IElementType COMMENT_BLOCK = new OCamlElementType("COMMENT_BLOCK");
    IElementType UNCLOSED_COMMENT = new OCamlElementType("UNCLOSED_COMMENT");

    /* } */

    IElementType PARENTHESES = new OCamlElementType("PARENTHESES");
    IElementType BRACKETS = new OCamlElementType("BRACKETS");
}

