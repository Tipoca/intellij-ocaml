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

package manuylov.maxim.ocaml.lang.feature.refactoring.rename;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNamedElement;
import com.intellij.psi.PsiReference;
import com.intellij.refactoring.rename.RenamePsiElementProcessor;
import com.intellij.util.IncorrectOperationException;
import com.intellij.util.containers.MultiMap;
import manuylov.maxim.ocaml.lang.feature.resolving.OCamlNamedElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * @author Maxim.Manuylov
 *         Date: 03.06.2010
 */
public class OCamlRenamePsiElementProcessor extends RenamePsiElementProcessor {
    @Override
    public boolean canProcessElement(@NotNull final PsiElement element) {
        return element instanceof OCamlElement;
    }

    @Override
    public void findExistingNameConflicts(@NotNull final PsiElement element, @NotNull final String newName, @NotNull final MultiMap<PsiElement, String> conflicts) {
        super.findExistingNameConflicts(element, newName, conflicts);

        //noinspection UnnecessaryLocalVariable
        final PsiElement elementBefore = element;
        final Collection<PsiReference> referencesBefore = collectReferences(elementBefore);

        for (final PsiReference referenceBefore : referencesBefore) {
            final PsiElement referenceElementBefore = referenceBefore.getElement();
            if (referenceElementBefore == null) continue;
            final PsiElement referenceElementAfter = referenceElementBefore.copy();
            final PsiReference referenceAfter = referenceElementAfter.getReference();
            if (referenceAfter == null) continue;
            final PsiElement definitionAfter = referenceAfter.resolve(); //todo it can be in another file
            if (definitionAfter == null) continue; //todo now is null - why?
            if (!rename(referenceElementAfter, newName)) continue;
            if (!rename(definitionAfter, newName)) continue;
            final PsiElement actualDefinitionAfter = referenceAfter.resolve();
            if (actualDefinitionAfter == null) continue;
            if (definitionAfter != actualDefinitionAfter) {
                addConflictIfNeeded(conflicts, actualDefinitionAfter, "Warning: %s is already defined.");
            }
        }

        final OCamlNamedElement elementAfter = (OCamlNamedElement) element.copy();
        if (!rename(elementAfter, newName)) return;

        final Collection<PsiReference> newReferencesAfter = collectReferences(elementAfter);
        final PsiFile originalFile = elementBefore.getContainingFile();
        final PsiFile fakeFile = elementAfter.getContainingFile();
        for (final PsiReference reference : newReferencesAfter) {
            final PsiElement originalElement = getOriginal(reference.getElement(), fakeFile, originalFile);
            if (originalElement != null) {
                addConflictIfNeeded(conflicts, originalElement, "Warning: there is a usage of %s.");
            }
        }
    }

    private boolean rename(@NotNull final PsiElement element, @NotNull final String newName) {
        try {
            if (element instanceof PsiNamedElement) {
                ((PsiNamedElement) element).setName(newName);
                return true;
            }
        }
        catch (final IncorrectOperationException e) {
            return false;
        }
        return false;
    }

    @Nullable
    private PsiElement getOriginal(@NotNull final PsiElement element, @NotNull final PsiElement fakeFile, final PsiFile originalFile) {
        if (element.getContainingFile() != fakeFile) {
            return element;
        }
        return originalFile.findElementAt(element.getTextRange().getStartOffset());
    }

    private void addConflictIfNeeded(@NotNull final MultiMap<PsiElement, String> conflicts,
                                     @NotNull final PsiElement conflictingElement,
                                     @NotNull final String text) {
        if (!conflicts.containsKey(conflictingElement)) {
            conflicts.putValue(conflictingElement, String.format(text, conflictingElement.toString()));
        }
    }

    @NotNull
    private Collection<PsiReference> collectReferences(@NotNull final PsiElement element) {
        final Collection<PsiReference> references = findReferences(element);
        //noinspection SuspiciousMethodCalls
        references.remove(element);
        return references;
    }
}
