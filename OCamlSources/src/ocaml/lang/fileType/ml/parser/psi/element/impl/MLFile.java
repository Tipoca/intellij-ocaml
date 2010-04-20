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

package ocaml.lang.fileType.ml.parser.psi.element.impl;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import ocaml.lang.feature.resolving.NameType;
import ocaml.lang.feature.resolving.ResolvingBuilder;
import ocaml.lang.feature.resolving.util.OCamlDeclarationsUtil;
import ocaml.lang.fileType.ml.MLFileType;
import ocaml.lang.fileType.ml.MLFileTypeLanguage;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.OCamlFileModuleExpression;
import ocaml.lang.processing.parser.psi.element.OCamlModuleDefinitionBinding;
import ocaml.lang.processing.parser.psi.element.OCamlModuleExpression;
import ocaml.lang.processing.parser.psi.element.impl.BaseOCamlFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 22.02.2009
 */
public class MLFile extends BaseOCamlFile implements OCamlModuleDefinitionBinding {
    public MLFile(@NotNull final FileViewProvider fileViewProvider) {
        super(fileViewProvider, MLFileTypeLanguage.INSTANCE);
    }

    @NotNull
    public FileType getFileType() {
        return MLFileType.INSTANCE;
    }

    @NotNull
    public String getCanonicalPath() {
        return getCanonicalName();   // todo ??? + MLIFile
    }

    @Nullable
    public ASTNode getNameElement() {
        return null;
    }

    @NotNull
    public NameType getNameType() {
        return NameType.UpperCase;
    }

    @NotNull
    public String getDescription() {
        return "module";
    }

    public boolean processDeclarations(@NotNull final ResolvingBuilder builder) {
        return OCamlDeclarationsUtil.processDeclarationsInModuleBinding(builder, this);
    }

    @Nullable
    public OCamlModuleExpression getExpression() {
        return OCamlPsiUtil.getLastChildOfType(this, OCamlFileModuleExpression.class);
    }
}
