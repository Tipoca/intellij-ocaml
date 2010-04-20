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

import ocaml.lang.feature.resolving.OCamlResolvedReference;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 27.03.2009
 */
class VariantsCollectorProcessor extends BaseOCamlResolvedReferencesProcessor {
    @NotNull private final ArrayList<OCamlResolvedReference> myVariants = new ArrayList<OCamlResolvedReference>();

    public VariantsCollectorProcessor(@NotNull final List<Class<? extends OCamlResolvedReference>> types) {
        super(types);
    }

    public boolean doProcess(@NotNull final OCamlResolvedReference psiElement) {
        myVariants.add(psiElement);
        return false;
    }

    @NotNull
    public OCamlResolvedReference[] getCollectedVariants() {
        return myVariants.toArray(new OCamlResolvedReference[myVariants.size()]);
    }
}
