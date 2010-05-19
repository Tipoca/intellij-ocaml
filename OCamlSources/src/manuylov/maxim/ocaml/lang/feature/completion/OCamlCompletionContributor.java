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

package manuylov.maxim.ocaml.lang.feature.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionInitializationContext;
import com.intellij.codeInsight.completion.DummyIdentifierPatcher;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 09.05.2010
 */
public class OCamlCompletionContributor extends CompletionContributor {
    public OCamlCompletionContributor() {
/*
        extend(CompletionType.BASIC, psiElement(), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters, @NotNull final ProcessingContext context, @NotNull final CompletionResultSet result) {
                collectVariantsFor(result);
            }
        });
*/
        //todo
    }

/*
    private void collectVariantsFor(@NotNull final OCamlElementType type, @NotNull final CompletionResultSet result) {
        final ASTNode astNode = OCamlASTNodeUtil.createLeaf(type, "");
        final OCamlElement element = OCamlElementFactory.INSTANCE.createElement(astNode);
        
        final LookupElement[] variants = OCamlResolvingUtil.getVariants(new ResolvingContext(), );
        for (final LookupElement variant : variants) {
            result.addElement(variant);
        }
    }
*/

    @Override
    public void beforeCompletion(@NotNull final CompletionInitializationContext context) {
        context.setFileCopyPatcher(new DummyIdentifierPatcher("lowerCase"));
    }
}
