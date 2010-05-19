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

package manuylov.maxim.ocaml.lang.parser.ast.testCase;

import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.psi.tree.IElementType;
import manuylov.maxim.ocaml.lang.parser.ast.util.TreeStringBuilder;
import manuylov.maxim.ocaml.lang.parser.util.ParserTestUtil;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author Maxim.Manuylov
 *         Date: 23.02.2009
 */
@Test
public abstract class ParsingTestCase extends Assert {
    protected TreeStringBuilder myTree;

    @BeforeMethod
    public void setUp() {
        recreateTree();
    }

    protected void recreateTree() {
        myTree = new TreeStringBuilder(false);
        myTree.addNode(0, getParserDefinition().getFileNodeType());
        myTree.addNode(1, getModuleBindingNodeType());
        myTree.addNode(2, getModuleExpressionNodeType());
    }

    protected void doTest(@NotNull final String text, @NotNull final String expectedTree) throws Exception {
        assertEquals(getTreeAsString(text, false, false), expectedTree);
    }

    @NotNull
    private String getTreeAsString(@NotNull final String text, final boolean ignoreParenthess, final boolean throwExceptionIfErrorElementOccured) throws Exception {
        return treeToString(ParserTestUtil.buildTree(text, getParserDefinition()), ignoreParenthess, throwExceptionIfErrorElementOccured);
    }

    @NotNull
    protected String getTreeIgnoringParenthess(@NotNull final String text) throws Exception {
        return getTreeAsString(text, true, true);
    }

    protected void assertIsAllowed(@NotNull final String text) throws Exception {
        getTreeAsString(text, false, true);
    }

    protected void assertIsNotAllowed(@NotNull final String text) throws Exception {
        try {
            getTreeAsString(text, false, true);
        }
        catch (Throwable ignored) {
            return;
        }
        fail();
    }

    @NotNull
    private String treeToString(@NotNull final ASTNode root, final boolean ignoreParenthess, final boolean throwExceptionIfErrorElementOccured) {
        return TreeStringBuilder.buildTreeString(root, true, ignoreParenthess, throwExceptionIfErrorElementOccured).getStringRepresentation();
    }

    @NotNull
    protected abstract ParserDefinition getParserDefinition();

    @NotNull
    protected abstract IElementType getModuleExpressionNodeType();

    @NotNull
    protected abstract IElementType getModuleBindingNodeType();
}
