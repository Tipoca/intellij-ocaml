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

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.IFileElementType;
import ocaml.lang.fileType.OCamlFileTypeLanguage;
import ocaml.lang.processing.parser.BaseParserDefinition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author Maxim.Manuylov
 *         Date: 27.03.2009
 */
public class OCamlASTNodeUtil {
    @NotNull
    public static ASTNode createASTNode(@NotNull final String token, @NotNull final IElementType elementType, @NotNull final Project project) {
//        return LanguageASTFactory.INSTANCE.forLanguage(elementType.getLanguage()).createLeaf(elementType, token); todo Does it work?

        final TempParserDefinition parserDefinition = new TempParserDefinition(elementType);

        final PsiFile file = PsiFileFactory.getInstance(project).createFileFromText("tempFile", parserDefinition.getFileType(), token);

        final PsiElement firstChild = file.getFirstChild();
        assert firstChild != null;

        final ASTNode node = firstChild.getNode();
        assert node != null;

        return node;
    }

    private static class TempParserDefinition extends BaseParserDefinition {
        @NotNull private final IElementType myElementType;

        @NotNull private final OCamlFileTypeLanguage myLanguage = new OCamlFileTypeLanguage("Temp") {
            @Override
            @NotNull
            public ParserDefinition getParserDefinition() {
                return TempParserDefinition.this;
            }
        };

        @NotNull private final LanguageFileType myFileType = new LanguageFileType(myLanguage) {
            @NotNull
            public String getName() {
                return "OCaml:TempFile";
            }

            @NotNull
            public String getDescription() {
                return "OCaml:TempFile";
            }

            @NotNull
            public String getDefaultExtension() {
                return "";
            }

            @Nullable
            public Icon getIcon() {
                return null;
            }
        };

        @NotNull private final PsiParser myParser = new PsiParser() {
            @NotNull
            public ASTNode parse(final IElementType root, final PsiBuilder builder) {
                PsiBuilder.Marker marker = builder.mark();
                builder.advanceLexer();
                marker.done(myElementType);

                marker = marker.precede();

                while (!builder.eof()) {
                    builder.advanceLexer();
                }

                marker.done(root);

                return builder.getTreeBuilt();
            }
        };

        @NotNull private IFileElementType myFileElementType = new IFileElementType("OCaml:TempFile", myLanguage);

        @NotNull
        public LanguageFileType getFileType() {
            return myFileType;
        }

        public TempParserDefinition(@NotNull final IElementType elementType) {
            myElementType = elementType;
        }

        @NotNull
        public PsiParser createParser(@NotNull final Project project) {
            return myParser;
        }

        @NotNull
        public IFileElementType getFileNodeType() {
            return myFileElementType;
        }

        @NotNull
        public PsiFile createFile(@NotNull final FileViewProvider viewProvider) {
           return new PsiFileBase(viewProvider, myLanguage) {
               @NotNull
               public FileType getFileType() {
                   return myFileType;
               }
           };
        }
    }
}
