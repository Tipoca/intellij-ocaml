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

package ocaml.lang.fileType.mli;

import ocaml.lang.fileType.OCamlFileType;
import ocaml.lang.fileType.ml.MLFileType;
import ocaml.util.OCamlIconUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author Maxim.Manuylov
 *         Date: 05.02.2009
 */
public class MLIFileType extends OCamlFileType {
    @NotNull public static final MLIFileType INSTANCE = new MLIFileType();

    private MLIFileType() {
        super(MLIFileTypeLanguage.INSTANCE);
    }

    @NotNull
    public String getName() {
        return "OCaml Module Interface File";
    }

    @NotNull
    public String getDescription() {
        return "OCaml module interface files";
    }

    @NotNull
    public String getDefaultExtension() {
        return "mli";
    }

    @NotNull
    public Icon getIcon() {
        return OCamlIconUtil.getMLIFileIcon();
    }

    @NotNull
    @Override
    public OCamlFileType getAnotherFileType() {
        return MLFileType.INSTANCE;
    }
}