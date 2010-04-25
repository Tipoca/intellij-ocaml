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
import ocaml.lang.fileType.mli.MLIFileTypeLanguage;
import ocaml.lang.processing.parser.ast.element.OCamlElementTypes;
import ocaml.lang.processing.parser.ast.util.TreeStringBuilder;
import org.jetbrains.annotations.NotNull;
import org.testng.annotations.Test;

import static ocaml.lang.processing.lexer.token.OCamlTokenTypes.*;
import static ocaml.lang.processing.parser.ast.element.OCamlElementTypes.*;

/**
 * @author Maxim.Manuylov
 *         Date: 13.03.2009
 */
@Test
public class SpecificationParsingTest extends BaseStatementParsingTest {
    public void testDoubleSemicolon() throws Exception {
        myTree.addNode(2, VALUE_SPECIFICATION);
        myTree.addNode(3, VAL_KEYWORD);
        myTree.addNode(3, VALUE_NAME_PATTERN);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, COLON);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");
        myTree.addNode(2, VALUE_SPECIFICATION);
        myTree.addNode(3, VAL_KEYWORD);
        myTree.addNode(3, VALUE_NAME_PATTERN);
        myTree.addNode(4, LCFC_IDENTIFIER, "b");
        myTree.addNode(3, COLON);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");
        myTree.addNode(2, SEMICOLON_SEMICOLON);
        myTree.addNode(2, VALUE_SPECIFICATION);
        myTree.addNode(3, VAL_KEYWORD);
        myTree.addNode(3, VALUE_NAME_PATTERN);
        myTree.addNode(4, LCFC_IDENTIFIER, "c");
        myTree.addNode(3, COLON);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");

        doTest("val a : int val b : int;; val c : int", myTree.getStringRepresentation());
    }

    public void testValueSpecification() throws Exception {
        myTree.addNode(2, VALUE_SPECIFICATION);
        myTree.addNode(3, VAL_KEYWORD);
        myTree.addNode(3, VALUE_NAME_PATTERN);
        myTree.addNode(4, LCFC_IDENTIFIER, "a");
        myTree.addNode(3, COLON);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");

        doTest("val a : int", myTree.getStringRepresentation());
    }

    public void testExceptionSpecification() throws Exception {
        myTree.addNode(2, EXCEPTION_SPECIFICATION);
        myTree.addNode(3, EXCEPTION_KEYWORD);
        myTree.addNode(3, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(4, UCFC_IDENTIFIER, "Error");

        doTest("exception Error", myTree.getStringRepresentation());

        recreateTree();

        myTree.addNode(2, EXCEPTION_SPECIFICATION);
        myTree.addNode(3, EXCEPTION_KEYWORD);
        myTree.addNode(3, CONSTRUCTOR_NAME_DEFINITION);
        myTree.addNode(4, UCFC_IDENTIFIER, "Error");
        myTree.addNode(3, OF_KEYWORD);
        myTree.addNode(3, TYPE_CONSTRUCTOR_NAME);
        myTree.addNode(4, LCFC_IDENTIFIER, "int");

        doTest("exception Error of int", myTree.getStringRepresentation());
    }

    public void testIncludeSpecification() throws Exception {
        myTree.addNode(2, INCLUDE_DIRECTIVE_SPECIFICATION);
        myTree.addNode(3, INCLUDE_KEYWORD);
        myTree.addNode(3, MODULE_TYPE_NAME);
        myTree.addNode(4, UCFC_IDENTIFIER, "ModuleType");

        doTest("include ModuleType", myTree.getStringRepresentation());
    }

    public void testEmptyFile() throws Exception {
        myTree = new TreeStringBuilder(false);
        myTree.addNode(0, MLI_FILE);
        myTree.addNode(1, FILE_MODULE_TYPE, "");             

        doTest("", myTree.getStringRepresentation());
    }

    @NotNull
    protected ParserDefinition getParserDefinition() {
        return MLIFileTypeLanguage.INSTANCE.getParserDefinition();
    }

    @NotNull
    protected IElementType getModuleNodeType() {
        return OCamlElementTypes.FILE_MODULE_TYPE;
    }
}
