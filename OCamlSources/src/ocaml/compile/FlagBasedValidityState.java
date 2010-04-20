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

package ocaml.compile;

import com.intellij.openapi.compiler.ValidityState;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Maxim.Manuylov
 *         Date: 13.04.2010
 */
public class FlagBasedValidityState implements ValidityState {
    @NotNull public static final FlagBasedValidityState TRUE_STATE = new FlagBasedValidityState(true);
    @NotNull public static final FlagBasedValidityState FALSE_STATE = new FlagBasedValidityState(false);

    private final boolean myFlag;

    private FlagBasedValidityState(final boolean flag) {
        myFlag = flag;
    }

    public boolean getFlag() {
        return myFlag;
    }

    @NotNull
    public FlagBasedValidityState another() {
        return fromFlag(!myFlag);
    }

    public boolean equalsTo(@NotNull final ValidityState otherState) {
        return this == otherState;
    }

    public void save(@NotNull final DataOutput out) throws IOException {
        out.writeBoolean(myFlag);
    }

    @NotNull
    public static FlagBasedValidityState fromFlag(final boolean flag) {
        return flag ? TRUE_STATE : FALSE_STATE;
    }

    @NotNull
    public static FlagBasedValidityState load(@NotNull final DataInput in) throws IOException {
        return fromFlag(in.readBoolean());
    }
}
