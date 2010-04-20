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

package ocaml.lang.fileType.mli.parser.psi.element.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import ocaml.lang.feature.resolving.NameType;
import ocaml.lang.feature.resolving.ResolvingBuilder;
import ocaml.lang.feature.resolving.util.OCamlDeclarationsUtil;
import ocaml.lang.fileType.mli.MLIFileType;
import ocaml.lang.fileType.mli.MLIFileTypeLanguage;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.OCamlFileModuleType;
import ocaml.lang.processing.parser.psi.element.OCamlModuleSpecificationBinding;
import ocaml.lang.processing.parser.psi.element.OCamlModuleType;
import ocaml.lang.processing.parser.psi.element.impl.BaseOCamlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 22.02.2009
 */
public class MLIFile extends BaseOCamlFile implements OCamlModuleSpecificationBinding {
    public MLIFile(@NotNull final FileViewProvider fileViewProvider) {
        super(fileViewProvider, MLIFileTypeLanguage.INSTANCE);
    }

    @NotNull
    public FileType getFileType() {
        return MLIFileType.INSTANCE;
    }

    @NotNull
    public String getCanonicalPath() {
        return getName();    //todo see this method in MLFile
    }

    @Nullable
    public ASTNode getNameElement() {
        return null;
    }

    @NotNull
    public NameType getNameType() {
        return NameType.AnyCase;
    }

    @NotNull
    public String getDescription() {
        return "module type";
    }

    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        return OCamlDeclarationsUtil.processDeclarationsInModuleBinding(builder, this);
    }

    @Nullable
    public OCamlModuleType getExpression() {
        return OCamlPsiUtil.getLastChildOfType(this, OCamlFileModuleType.class);
    }
}