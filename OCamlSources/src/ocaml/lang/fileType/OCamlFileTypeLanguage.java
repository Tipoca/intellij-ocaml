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

package ocaml.lang.fileType;

import com.intellij.lang.Commenter;
import com.intellij.lang.Language;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.folding.FoldingBuilder;
import ocaml.lang.OCamlLanguage;
import ocaml.lang.feature.braceMatching.OCamlBraceMatcher;
import ocaml.lang.feature.commenting.OCamlCommenter;
import ocaml.lang.feature.folding.OCamlFoldingBuilder;
import ocaml.lang.feature.highlighting.OCamlAnnotatingVisitor;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 28.03.2009
 */
public abstract class OCamlFileTypeLanguage extends Language {
    protected OCamlFileTypeLanguage(@NotNull final String id) {
        super(OCamlLanguage.INSTANCE, id);
    }

    @NotNull
    public abstract ParserDefinition getParserDefinition();

    @NotNull
    public Annotator getAnnotator() {
        return new OCamlAnnotatingVisitor();
    }

    @NotNull
    public PairedBraceMatcher getBraceMatcher() {
        return new OCamlBraceMatcher();
    }

    @NotNull
    public FoldingBuilder getFoldingBuilder() {
        return new OCamlFoldingBuilder();
    }

    @NotNull
    public Commenter getCommenter() {
        return new OCamlCommenter();
    }

    @Override
    public boolean isCaseSensitive() {
        return true;
    }
}
