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

import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import ocaml.lang.feature.resolving.*;
import ocaml.lang.fileType.ml.MLFileType;
import ocaml.lang.fileType.mli.MLIFileType;
import ocaml.lang.processing.parser.psi.OCamlElement;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.*;
import ocaml.util.OCamlFileUtil;
import ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.psi.search.GlobalSearchScope.getScopeRestrictedByFileTypes;
import static com.intellij.psi.search.GlobalSearchScope.moduleWithDependenciesAndLibrariesScope;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
public class OCamlResolvingUtil {
    @NotNull public static final String PERVASIVES = "Pervasives";

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

        if (builder.getModulePathOffset() == 0) {
            final PsiFile sourceFile = builder.getContext().getSourceElement().getContainingFile();
            if (tryProcessPervasives(builder, sourceFile)) return;

            String moduleName = null;
            if (builder.getContext().getModulePath().size() > 0) {
                moduleName = builder.getCurrentModuleName();
            }
            else if (builder.getContext().getSourceElement() instanceof OCamlModuleName) {
                moduleName = builder.getContext().getSourceElement().getName();
            }
            if (moduleName != null) {
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

    private static boolean tryProcessPervasives(@NotNull final ResolvingBuilder builder, @NotNull final PsiFile sourceFile) {
        final OCamlElement pervasivesFile = findFileModule(sourceFile, PERVASIVES);
        return pervasivesFile != null && processSibling(pervasivesFile, builder);
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
        final Project project = sourceFile.getProject();

        final Module module = ModuleUtil.findModuleForPsiElement(sourceFile);
        if (module == null) return null;

        final String fileName = OCamlFileUtil.getFileName(moduleName, fileType);
        final GlobalSearchScope scope = getScopeRestrictedByFileTypes(moduleWithDependenciesAndLibrariesScope(module), fileType);
        final T file = findFileByName(project, scope, type, fileName);
        return file == null ? findFileByName(project, scope, type, OCamlStringUtil.changeFirstLetterCase(fileName)) : file;
    }

    @Nullable
    private static <T extends OCamlElement> T findFileByName(@NotNull final Project project,
                                                             @NotNull final GlobalSearchScope scope,
                                                             @NotNull final Class<T> type,
                                                             @NotNull final String fileName) {
        final PsiFile[] files = FilenameIndex.getFilesByName(project, fileName, scope);

        for (final PsiFile file : files) {
            if (type.isInstance(file)) {
                //noinspection unchecked
                return (T) file;
            }
        }

        return null;
    }
}
