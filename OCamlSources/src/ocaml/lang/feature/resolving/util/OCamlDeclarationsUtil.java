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

package ocaml.lang.feature.resolving.util;

import ocaml.lang.feature.resolving.ResolvingBuilder;
import ocaml.lang.processing.parser.psi.OCamlElement;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.OCamlBinding;
import ocaml.lang.processing.parser.psi.element.OCamlStructuredElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 14.04.2010
 */
public class OCamlDeclarationsUtil {
    public static boolean processDeclarationsInChildren(@NotNull final ResolvingBuilder builder,
                                                        @NotNull final OCamlElement parent,
                                                        @NotNull final Class<? extends OCamlElement>... childrenTypes) {
        final List<? extends OCamlElement> children = OCamlPsiUtil.getChildrenOfTypes(parent, childrenTypes);
        for (int i = children.size() - 1; i >= 0; i--) {
            final OCamlElement child = children.get(i);
            if (!builder.childWasAlreadyProcessed(child) && child.processDeclarations(builder)) {
                return true;
            }
        }
        return false;
    }

    public static boolean processDeclarationsInModuleBinding(@NotNull final ResolvingBuilder builder,
                                                             @NotNull final OCamlBinding<? extends OCamlStructuredElement> binding) {
        final OCamlStructuredElement expression = binding.getExpression();
        if (expression != null && builder.childWasAlreadyProcessed(expression)) return false;
        if (builder.getProcessor().process(binding)) return true;
        final String moduleName = binding.getCanonicalName();
        return moduleName != null && builder.tryProcessModule(moduleName, new ResolvingBuilder.ModuleProcessor() {
            public boolean process() {
                return processDeclarationsInStructuredElement(builder, binding.getExpression());
            }
        });
    }

    public static boolean processDeclarationsInStructuredElement(@NotNull final ResolvingBuilder builder, @Nullable final OCamlStructuredElement psiElement) {
        if (psiElement == null || builder.childWasAlreadyProcessed(psiElement)) return false;
        final OCamlStructuredElement actualDefinition = psiElement.findActualDefinition();
        return actualDefinition != null && actualDefinition.processDeclarations(builder);
    }
}
