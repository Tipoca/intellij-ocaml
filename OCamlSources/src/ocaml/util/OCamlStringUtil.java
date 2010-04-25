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

package ocaml.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 20.04.2010
 */
public class OCamlStringUtil {
    @NotNull
    public static String getNotNull(@Nullable final String string) {
        return string == null ? "" : string;
    }

    @NotNull
    public static String capitalize(@NotNull final String string) {
        if (string.length() == 0) return string;
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }

    @NotNull
    public static String changeFirstLetterCase(final String string) {
        if (string.length() == 0) return string;
        return changeLetterCase(string.charAt(0)) + string.substring(1);
    }

    private static char changeLetterCase(final char letter) {
        final char upperCaseLetter = Character.toUpperCase(letter);
        return letter == upperCaseLetter ? Character.toLowerCase(letter) : upperCaseLetter;
    }
}
