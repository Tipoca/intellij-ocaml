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

package ocaml.lang.processing.parser.ast.util;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import ocaml.lang.processing.Strings;
import ocaml.lang.processing.lexer.token.OCamlTokenTypes;
import ocaml.lang.processing.parser.ast.element.OCamlElementTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * @author Maxim.Manuylov
 *         Date: 25.02.2009
 */
public class TreeStringBuilder {
    private final StringBuilder myStringBuilder = new StringBuilder("");
    private final boolean myThrowExceptionIfErrorElementOccured;
    private int ignoredLeftParCount = 0;
    private int ignoredRightParCount = 0;

    public TreeStringBuilder(final boolean throwExceptionIfErrorElementOccured) {
        myThrowExceptionIfErrorElementOccured = throwExceptionIfErrorElementOccured;
    }

    public static TreeStringBuilder buildTreeString(@NotNull final ASTNode root, final boolean ignoreWhiteSpaces, final boolean ignoreParenthess,
                                              final boolean throwExceptionIfErrorElementOccured) {
        final TreeStringBuilder builder = new TreeStringBuilder(throwExceptionIfErrorElementOccured);
        processNode(builder, 0, root, ignoreWhiteSpaces, ignoreParenthess);
        if (builder.ignoredLeftParCount != builder.ignoredRightParCount) {
            throw new RuntimeException("Unbalanced parenthess.");
        }
        return builder;
    }

    private static void processNode(final TreeStringBuilder builder, final int level, final ASTNode node, final boolean ignoreWhiteSpaces,
                                    final boolean ignoreParenthess) {
        doProcessNode(builder, level, node, ignoreWhiteSpaces, ignoreParenthess);

        final ASTNode[] children = node.getChildren(null);
        for (final ASTNode child : children) {
            processNode(builder, ignoreParenthess && node.getElementType() == OCamlElementTypes.PARENTHESES ? level : level + 1, child, ignoreWhiteSpaces, ignoreParenthess);
        }
    }

    private static void doProcessNode(final TreeStringBuilder builder, final int level, final ASTNode node, final boolean ignoreWhiteSpaces,
                                      final boolean ignoreParenthess) {
        final IElementType elementType = node.getElementType();

        if (ignoreWhiteSpaces && elementType == TokenType.WHITE_SPACE) return;

        if (ignoreParenthess) {
            if (elementType == OCamlElementTypes.PARENTHESES) return;

            if (elementType == OCamlTokenTypes.LPAR) {
                final ASTNode next = node.getTreeNext();
                if (next != null && next.getElementType() != OCamlTokenTypes.RPAR) {
                    builder.ignoredLeftParCount++;
                    return;
                }
            }
            else if (elementType == OCamlTokenTypes.RPAR) {
                final ASTNode prev = node.getTreePrev();
                if (prev != null && prev.getElementType() != OCamlTokenTypes.LPAR) {
                    builder.ignoredRightParCount++;
                    return;
                }
            }
        }

        if (node.getChildren(null).length == 0 || elementType == TokenType.ERROR_ELEMENT) {
            final String text;

            if (elementType == TokenType.ERROR_ELEMENT) {
                assert node instanceof PsiErrorElement;
                text = ((PsiErrorElement)node).getErrorDescription();
                if (builder.myThrowExceptionIfErrorElementOccured) {
                    throw new RuntimeException("Error element: " + text + ".\n\nTree:\n\n" + buildTreeString(getRoot(node), ignoreWhiteSpaces, ignoreParenthess, false).getStringRepresentation());
                }
            }
            else {
                text = node.getText();
            }
            
            builder.addNode(level, elementType, text);
        }
        else {
            builder.addNode(level, elementType);
        }
    }

    public void addNode(final int level, @NotNull final IElementType type) {
        final String text = getElementText(type);

        if (text != null) {
            addNode(level, type, text);
            return;
        }

        addIndent(level);
        myStringBuilder.append(type.toString());
        myStringBuilder.append("\n");
    }

    public void addNode(final int level, @NotNull final IElementType type, @NotNull final String text) {
        addIndent(level);
        myStringBuilder.append(type.toString());
        myStringBuilder.append("('");
        myStringBuilder.append(text);
        myStringBuilder.append("')");
        myStringBuilder.append("\n");
    }

    @NotNull
    public String getStringRepresentation() {
        return myStringBuilder.toString();
    }

    @NotNull
    private static ASTNode getRoot(@NotNull final ASTNode node) {
        ASTNode root = node;

        while (root.getTreeParent() != null) {
            root = root.getTreeParent();
        }

        return root;
    }

    private void addIndent(final int level) {
        for (int i = 0; i < level; i++) {
            myStringBuilder.append("  ");
        }
    }

    @Nullable
    private String getElementText(@NotNull final IElementType type) {
        final Field[] fields = OCamlTokenTypes.class.getFields();

        for (final Field field : fields) {
            try {
                if (field.get(null) == type) {
                    final Field[] stringFields = Strings.class.getFields();
                    for (final Field stringField : stringFields) {
                        if (stringField.getName().equals(field.getName())) {
                            return String.valueOf(stringField.get(null));
                        }
                    }
                }
            }
            catch (IllegalAccessException ignored) {
            }
        }

        return null;
    }
}
