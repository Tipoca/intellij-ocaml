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

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ContentIterator;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import ocaml.entity.OCamlModule;
import ocaml.lang.feature.resolving.*;
import ocaml.lang.fileType.ml.MLFileType;
import ocaml.lang.fileType.mli.MLIFileType;
import ocaml.lang.processing.parser.psi.OCamlElement;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.*;
import ocaml.module.OCamlModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
public class OCamlResolvingUtil {
    @Nullable
    public static OCamlStructuredElement findActualDefinitionOfStructuredElement(@NotNull final OCamlReference reference) {
        final OCamlResolvedReference resolvedReference = reference.resolve();
        if (resolvedReference == null || !(resolvedReference instanceof OCamlStructuredBinding)) return null;
        final OCamlStructuredElement expression = ((OCamlStructuredBinding) resolvedReference).getExpression();
        return expression == null ? null : expression.findActualDefinition();
    }

    @NotNull
    public static OCamlResolvedReference[] getVariants(@NotNull final ResolvingContext context, @NotNull final List<Class<? extends OCamlResolvedReference>> types) {
        final VariantsCollectorProcessor processor = new VariantsCollectorProcessor(types);
        treeWalkUp(new ResolvingBuilder(processor, context));
        return processor.getCollectedVariants();
    }

    @Nullable
    public static OCamlResolvedReference resolve(@NotNull final ResolvingContext context, @NotNull final List<Class<? extends OCamlResolvedReference>> types) {
        final ResolvingProcessor processor = new ResolvingProcessor(types);
        treeWalkUp(new ResolvingBuilder(processor, context));
        return processor.getResolvedReference();
    }

    private static void treeWalkUp(@NotNull final ResolvingBuilder builder) {
        OCamlElement parent = builder.getContext().getSourceElement();
        builder.setLastParent(parent);

        while (parent != null) {
            if (processParent(parent, builder)) return;
            OCamlElement sibling = OCamlPsiUtil.getPrevSibling(parent);

            while (sibling != null) {
                if (processSibling(sibling, builder)) return;
                sibling = OCamlPsiUtil.getPrevSibling(sibling);
            }

            builder.setLastParent(parent);
            parent = OCamlPsiUtil.getParent(parent);
        }

        if (builder.getModulePathOffset() == 0 && builder.getContext().getModulePath().size() > 0) {
            String moduleName = builder.getCurrentModuleName();
            if (moduleName == null && builder.getContext().getSourceElement() instanceof OCamlModuleName) {
                moduleName = builder.getContext().getSourceElement().getName();
            }
            if (moduleName != null) {
                final PsiFile sourceFile = builder.getContext().getSourceElement().getContainingFile();
                final OCamlElement targetFile = findFileModule(sourceFile, moduleName);
                if (targetFile != null) {
                    processSibling(targetFile, builder);
                }
            }
        }
    }

    private static boolean processParent(@NotNull final OCamlElement parent, @NotNull final ResolvingBuilder builder) {
        builder.setLastParentPosition(ElementPosition.Child);
        return parent.processDeclarations(builder);
    }

    private static boolean processSibling(@NotNull final OCamlElement sibling, @NotNull final ResolvingBuilder builder) {
        builder.setLastParentPosition(ElementPosition.Sibling);
        return sibling.processDeclarations(builder);
    }

    @Nullable
    private static OCamlElement findFileModule(@NotNull final PsiFile sourceFile, @NotNull final String moduleName) {
        if (sourceFile.getFileType() == MLFileType.INSTANCE) {
            final OCamlElement targetFile = findFileModuleDefinition(sourceFile, moduleName);
            if (targetFile != null) {
                return targetFile;
            }
        }

        return findFileModuleSpecification(sourceFile, moduleName);
    }

    @Nullable
    public static OCamlModuleDefinitionBinding findFileModuleDefinition(@NotNull final PsiFile sourceFile,
                                                                        @NotNull final String moduleName) {
        return doFindFile(sourceFile, moduleName, MLFileType.INSTANCE, OCamlModuleDefinitionBinding.class);
    }

    @Nullable
    public static OCamlModuleSpecificationBinding findFileModuleSpecification(@NotNull final PsiFile sourceFile,
                                                                              @NotNull final String moduleName) {
        return doFindFile(sourceFile, moduleName, MLIFileType.INSTANCE, OCamlModuleSpecificationBinding.class);
    }

    @SuppressWarnings({"unchecked"})
    @Nullable
    private static <T extends OCamlElement> T doFindFile(@NotNull final PsiFile sourceFile,
                                                         @NotNull final String moduleName,
                                                         @NotNull final FileType fileType,
                                                         @NotNull final Class<T> type) {
        final Object[] foundFile = new Object[] { null };

        final Project project = sourceFile.getProject();
        final ContentIterator contentIterator = new ContentIterator() {
            public boolean processFile(@NotNull final VirtualFile fileOrDir) {
                if (fileOrDir.getFileType() != fileType) return true;
                final OCamlModule ocamlModule = OCamlModule.getBySourceFile(fileOrDir, project);
                if (ocamlModule != null && ocamlModule.getName().equals(moduleName)) {
                    final PsiFile psiFile = ApplicationManager.getApplication().runReadAction(new Computable<PsiFile>() {
                        @Nullable
                        public PsiFile compute() {
                            return PsiManager.getInstance(project).findFile(fileOrDir);
                        }
                    });
                    if (psiFile != null && type.isInstance(psiFile)) {
                        foundFile[0] = psiFile;
                        return false;
                    }
                }
                return true;
            }
        };

        final Module module = ModuleUtil.findModuleForPsiElement(sourceFile);
        if (module == null) return null;

        final PsiDirectory sourceDir = sourceFile.getParent();
        if (sourceDir == null) return null;

        final ModuleRootManager rootManager = ModuleRootManager.getInstance(module);

        rootManager.getFileIndex().iterateContentUnderDirectory(sourceDir.getVirtualFile(), contentIterator);
        if (foundFile[0] != null) {
            return (T) foundFile[0];
        }

        rootManager.getFileIndex().iterateContent(contentIterator);
        if (foundFile[0] != null) {
            return (T) foundFile[0];
        }

        for (final Module dependency : rootManager.getDependencies()) {
            if (!(dependency.getModuleType() instanceof OCamlModuleType)) continue;
            ModuleRootManager.getInstance(dependency).getFileIndex().iterateContent(contentIterator);
            if (foundFile[0] != null) {
                return (T) foundFile[0];
            }
        }

        return null;
    }
}
