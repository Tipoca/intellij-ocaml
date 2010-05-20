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

package manuylov.maxim.ocaml.lang.parser.psi;

import com.intellij.lang.*;
import com.intellij.lang.impl.PsiBuilderImpl;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import com.intellij.psi.util.PsiTreeUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlModuleName;
import manuylov.maxim.ocaml.lang.parser.psi.element.OCamlPathElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
public class OCamlPsiUtil {
    @Nullable
    public static PsiElement getNonWhiteSpacePreviousLeaf(@NotNull final PsiElement element) {
        PsiElement parent = element;
        PsiElement prevElement = getNonWhiteSpacePrevSibling(parent);

        while (prevElement == null) {
            parent = getParent(parent);
            if (parent == null) break;
            prevElement = getNonWhiteSpacePrevSibling(parent);
        }

        return prevElement == null ? null : getNonWhiteSpaceLastLeaf(prevElement);
    }

    @NotNull
    public static PsiElement getNonWhiteSpaceLastLeaf(@NotNull final PsiElement psiElement) {
        PsiElement result = psiElement;
        PsiElement lastChild = getNonWhiteSpaceLastChild(result);

        while (lastChild != null) {
            result = lastChild;
            lastChild = getNonWhiteSpaceLastChild(result);
        }

        return result;
    }

    @Nullable
    private static PsiElement getNonWhiteSpaceLastChild(@NotNull final PsiElement element) {
        final PsiElement lastChild = element.getLastChild();
        if (lastChild == null || (!(lastChild instanceof PsiWhiteSpace) && !isEmptyErrorMarker(lastChild))) return lastChild;
        return getNonWhiteSpacePrevSibling(lastChild);
    }

    @Nullable
    public static OCamlElement getPrevSibling(@NotNull final PsiElement sibling) {
        PsiElement result = sibling;

        do {
            final PsiElement currentResult = result;
            result = ApplicationManager.getApplication().runReadAction(new Computable<PsiElement>() {
                public PsiElement compute() {
                    return currentResult.getPrevSibling();
                }
            });
        } while (result != null && !(result instanceof OCamlElement));

        return (OCamlElement) result;
    }

    @Nullable
    public static PsiElement getNonWhiteSpacePrevSibling(@NotNull final PsiElement sibling) {
        PsiElement result = sibling;

        do {
            final PsiElement currentResult = result;
            result = ApplicationManager.getApplication().runReadAction(new Computable<PsiElement>() {
                public PsiElement compute() {
                    return currentResult.getPrevSibling();
                }
            });
        } while (result != null && (result instanceof PsiWhiteSpace || isEmptyErrorMarker(result)));

        return result;
    }

    private static boolean isEmptyErrorMarker(@NotNull final PsiElement element) {
        return element instanceof PsiErrorElement && getChildren(element).isEmpty();
    }

    @Nullable
    public static OCamlElement getNextSibling(@NotNull final PsiElement sibling) {
        PsiElement result = sibling;

        do {
            final PsiElement currentResult = result;
            result = ApplicationManager.getApplication().runReadAction(new Computable<PsiElement>() {
                public PsiElement compute() {
                    return currentResult.getNextSibling();
                }
            });
        } while (result != null && !(result instanceof OCamlElement));

        return (OCamlElement) result;
    }

    @Nullable
    public static OCamlElement getParent(@NotNull final PsiElement node) {
        final PsiElement result = ApplicationManager.getApplication().runReadAction(new Computable<PsiElement>() {
            public PsiElement compute() {
                return node.getParent();
            }
        });
        if (!(result instanceof OCamlElement)) return null;
        return (OCamlElement) result;
    }

    @Nullable
    public static <T extends PsiElement> T getParentOfType(@NotNull final PsiElement node, @NotNull final Class<T> clazz) {
        OCamlElement parent = node instanceof OCamlElement ? (OCamlElement) node : getParent(node);

        while (parent != null && !clazz.isInstance(parent)) {
            parent = getParent(parent);
        }

        //noinspection unchecked
        return (T) parent;
    }

    public static boolean areSiblings(@NotNull final OCamlElement firstNode, @NotNull final OCamlElement secondNode) {
        return getParent(firstNode) == getParent(secondNode);
    }

    @NotNull
    public static <T extends OCamlElement, Q extends OCamlElement> List<T> getModulePath(@NotNull final OCamlElement psiElement,
                                                                                         @NotNull final Class<T> siblingsType) {
        final List<T> result = new ArrayList<T>();

        if (!(psiElement.getParent() instanceof OCamlPathElement)) {
            return result;
        }

        OCamlElement prevSibling = getPrevSibling(psiElement);

        while (prevSibling != null) {
            if (siblingsType.isInstance(prevSibling)) {
                //noinspection unchecked
                result.add(0, (T) prevSibling);
            }
            prevSibling = getPrevSibling(prevSibling);
        }

        return result;
    }

    @NotNull
    public static <T extends OCamlElement> List<T> getChildrenOfType(@NotNull final PsiElement parent, @NotNull final Class<T> type) {
        final List<T> result = new ArrayList<T>();

        final PsiElement[] children = ApplicationManager.getApplication().runReadAction(new Computable<PsiElement[]>() {
            public PsiElement[] compute() {
                return parent.getChildren();
            }
        });
        for (final PsiElement child : children) {
            if (type.isInstance(child)) {
                //noinspection unchecked
                result.add((T) child);
            }
        }

        return result;
    }

    @NotNull
    public static List<OCamlElement> getChildren(@NotNull final PsiElement parent) {
        return getChildrenOfType(parent, OCamlElement.class);
    }

    @Nullable
    public static <T extends OCamlElement> T getFirstChildOfType(@NotNull final PsiElement parent, @NotNull final Class<T> type) {
        final List<OCamlElement> children = getChildren(parent);
        for (final OCamlElement child : children) {
            if (type.isInstance(child)) {
                //noinspection unchecked
                return (T) child;
            }
        }
        return null;
    }

    @Nullable
    public static <T extends OCamlElement> T getLastChildOfType(@NotNull final OCamlElement parent, @NotNull final Class<T> type) {
        final List<OCamlElement> children = getChildren(parent);
        for (int i = children.size() - 1; i >= 0; i--) {
            final OCamlElement child = children.get(i);
            if (type.isInstance(child)) {
                //noinspection unchecked
                return (T) child;
            }
        }
        return null;
    }

    @NotNull
    public static List<? extends OCamlElement> getChildrenOfTypes(@NotNull final OCamlElement parent, @NotNull final Class<? extends OCamlElement>... types) {
        final List<OCamlElement> result = new ArrayList<OCamlElement>();

        final List<OCamlElement> children = getChildren(parent);
        for (final OCamlElement child : children) {
            for (final Class<? extends OCamlElement> type : types) {
                if (type.isInstance(child)) {
                    result.add(child);
                }
            }
        }

        return result;
    }

    public static List<OCamlModuleName> collectModuleReferences(@NotNull final OCamlElement psiElement) {
        final List<OCamlModuleName> moduleReferences = new ArrayList<OCamlModuleName>();
        visitRecursively(psiElement, new OCamlElementVisitorAdapter() {
            @Override
            public void visitModuleName(@NotNull final OCamlModuleName psiElement) {
                moduleReferences.add(psiElement);
            }
        });
        return moduleReferences;
    }

    public static void visitRecursively(@NotNull final OCamlElement psiElement, @NotNull final OCamlElementVisitor visitor) {
        final Stack<OCamlElement> stack = new Stack<OCamlElement>();
        stack.push(psiElement);
        while (!stack.isEmpty()) {
            final OCamlElement element = stack.pop();
            element.visit(visitor);
            stack.addAll(getChildren(element));
        }
    }

    @Nullable
    public static PsiElement findCommonParent(@NotNull final PsiElement element1, @NotNull final PsiElement element2) {
      if(element1 == element2) return element1;

      final ArrayList<PsiElement> parents1 = getParents(element1);
      final ArrayList<PsiElement> parents2 = getParents(element2);
      int size = Math.min(parents1.size(), parents2.size());
      for (int i = 1; i <= size; i++) {
        final PsiElement parent1 = parents1.get(parents1.size() - i);
        final PsiElement parent2 = parents2.get(parents2.size() - i);
        if (!parent1.equals(parent2)) {
            return parent1;
        }
      }
      return null;
    }

    @NotNull
    private static ArrayList<PsiElement> getParents(@NotNull final PsiElement element) {
      final ArrayList<PsiElement> parents = new ArrayList<PsiElement>();
      PsiElement parent = element;
      while (parent != null) {
        parents.add(parent);
        parent = getParent(parent);
      }
      return parents;
    }

    @Nullable
    public static PsiElement findElementOfTypeInRange(@NotNull final PsiElement root,
                                                      @NotNull final Class<? extends PsiElement> elementClass,
                                                      final int startOffset,
                                                      final int endOffset) {
        PsiElement element1 = root.findElementAt(startOffset);
        PsiElement element2 = root.findElementAt(endOffset - 1);
        while (element1 instanceof PsiWhiteSpace) {
          element1 = root.findElementAt(element1.getTextRange().getEndOffset());
        }
        while (element2 instanceof PsiWhiteSpace) {
          element2 = root.findElementAt(element2.getTextRange().getStartOffset() - 1);
        }
        if (element1 == null || element2 == null) {
          return null;
        }

        final PsiElement parent = PsiTreeUtil.getParentOfType(findCommonParent(element1, element2), elementClass, false);
        if (parent != null && new TextRange(startOffset, endOffset).contains(parent.getTextRange())) {
            return parent;
        }

        return null;
    }

    public static boolean isElementOfType(@NotNull final String text,
                                          @NotNull final TextRange range,
                                          @NotNull final Class<? extends PsiElement> clazz,
                                          @NotNull final Language language,
                                          @NotNull final Project project) {
        final ParserDefinition parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(language);
        if (parserDefinition == null) return false;

        final Lexer lexer = parserDefinition.createLexer(project);
        final PsiParser parser = parserDefinition.createParser(project);
        final PsiBuilderImpl builder = new PsiBuilderImpl(lexer, parserDefinition.getWhitespaceTokens(), parserDefinition.getCommentTokens(), text);
        final ASTNode astRoot = parser.parse(parserDefinition.getFileNodeType(), builder);
        final PsiElement psiRoot = parserDefinition.createElement(astRoot);

        final PsiElement element = findElementOfTypeInRange(psiRoot, clazz, range.getStartOffset(), range.getEndOffset());
        return element != null && endsCorrectlyIfOCamlElement(element);
    }

    public static boolean endsCorrectlyIfOCamlElement(@NotNull final PsiElement element) {
        return !(element instanceof OCamlElement) || ((OCamlElement) element).endsCorrectly();
    }

    public static boolean endsWith(@NotNull final OCamlElement element, @NotNull final IElementType type) {
        return getLastNodeType(element) == type;
    }

    public static boolean endsWith(@NotNull final OCamlElement element, @NotNull final TokenSet tokenSet) {
        return tokenSet.contains(getLastNodeType(element));
    }

    @Nullable
    private static IElementType getLastNodeType(@NotNull final OCamlElement element) {
        final PsiElement lastChild = getLastChild(element);
        if (lastChild == null) return null;
        final ASTNode node = lastChild.getNode();
        if (node == null) return null;
        return node.getElementType();
    }

    public static boolean endsCorrectlyWith(@NotNull final OCamlElement element, @NotNull final Class<? extends OCamlElement> clazz) {
        final PsiElement lastChild = getLastChild(element);
        return lastChild != null && clazz.isInstance(lastChild) && ((OCamlElement)lastChild).endsCorrectly();
    }

    @Nullable
    private static PsiElement getLastChild(@NotNull final OCamlElement element) {
        return ApplicationManager.getApplication().runReadAction(new Computable<PsiElement>() {
            public PsiElement compute() {
                return element.getLastChild();
            }
        });
    }
}
