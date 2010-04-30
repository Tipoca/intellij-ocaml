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

package ocaml.lang.processing.parser.psi;

import com.intellij.navigation.ItemPresentation;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.util.Computable;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import ocaml.lang.feature.resolving.OCamlNamedElement;
import ocaml.lang.processing.parser.psi.element.OCamlModuleName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
public class OCamlPsiUtil {
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

    public static boolean areSiblings(@NotNull final OCamlElement firstNode, @NotNull final OCamlElement secondNode) {
        return getParent(firstNode) == getParent(secondNode);
    }

    public static boolean acceptOCamlElement(@NotNull final OCamlElement psiElement, @NotNull final PsiElementVisitor psiElementVisitor) {
        boolean accepted = false;

        if (psiElementVisitor instanceof OCamlElementVisitor) {
            psiElement.visit((OCamlElementVisitor)psiElementVisitor);
            accepted = true;
        }
        
        if (psiElementVisitor instanceof OCamlElementProcessor) {
            ((OCamlElementProcessor) psiElementVisitor).process(psiElement);
            accepted = true;
        }

        return accepted;
    }

    @NotNull
    public static <T extends OCamlElement, Q extends OCamlElement> List<T> getModulePath(@NotNull final OCamlElement psiElement, 
                                                                                         @NotNull final Class<Q> parentType,
                                                                                         @NotNull final Class<T> siblingsType) {
        final List<T> result = new ArrayList<T>();

        if (!parentType.isInstance(psiElement.getParent())) {
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
    public static <T extends OCamlElement> List<T> getChildrenOfType(@NotNull final OCamlElement parent, @NotNull final Class<T> type) {
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
    public static List<OCamlElement> getChildren(@NotNull final OCamlElement parent) {
        return getChildrenOfType(parent, OCamlElement.class);
    }

    @Nullable
    public static <T extends OCamlElement> T getFirstChildOfType(@NotNull final OCamlElement parent, @NotNull final Class<T> type) {
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
                    result.add((OCamlElement) child);
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

    public static ItemPresentation getPresentation(@NotNull final OCamlNamedElement element) {
        return new ItemPresentation() {
            @NotNull
            public String getPresentableText() {
                return element.getDescription() + ' ' + element.getCanonicalName();
            }

            @NotNull
            public String getLocationString() {
                return '(' + element.getContainingFile().getName() + ')';
            }

            @Nullable
            public Icon getIcon(final boolean open) {
                return null;
            }

            @Nullable
            public TextAttributesKey getTextAttributesKey() {
                return null;
            }
        };
    }
}
