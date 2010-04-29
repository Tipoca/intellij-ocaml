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

package ocaml.entity;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.util.containers.MultiMap;
import ocaml.lang.feature.resolving.OCamlResolvedReference;
import ocaml.lang.fileType.ml.MLFileType;
import ocaml.lang.fileType.mli.MLIFileType;
import ocaml.lang.processing.parser.psi.OCamlPsiUtil;
import ocaml.lang.processing.parser.psi.element.OCamlFile;
import ocaml.lang.processing.parser.psi.element.OCamlModuleName;
import ocaml.util.OCamlFileUtil;
import ocaml.util.OCamlStringUtil;
import ocaml.util.TreeNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.*;

/**
 * @author Maxim.Manuylov
 *         Date: 07.04.2010
 */
public class OCamlModule {
    @NotNull private final Project myProject;
    @NotNull private final VirtualFile mySourcesDir;
    @NotNull private final String myName;

    public OCamlModule(@NotNull final Project project, @NotNull final VirtualFile sourcesDir, @NotNull final String name) {
        myProject = project;
        mySourcesDir = sourcesDir;
        myName = name;
    }

    @Nullable
    public static OCamlModule getBySourceFile(@NotNull final VirtualFile file, @NotNull final Project project) {
        if (!OCamlFileUtil.isOCamlSourceFile(file)) return null;
        final ProjectFileIndex fileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        if (!fileIndex.isInSourceContent(file)) return null;
        return new OCamlModule(project, file.getParent(), file.getNameWithoutExtension());
    }

    @NotNull
    public List<OCamlModule> collectAllDependencies() throws CyclicDependencyException {        
        final List<OCamlModule> result = new ArrayList<OCamlModule>();
        final Set<OCamlModule> processedModules = new HashSet<OCamlModule>();
        final Queue<TreeNode<OCamlModule>> queue = new LinkedList<TreeNode<OCamlModule>>();
        addDependencies(queue, new TreeNode<OCamlModule>(this), collectExactDependencies());
        processedModules.add(this);
        while (!queue.isEmpty()) {
            final TreeNode<OCamlModule> node = queue.remove();
            final OCamlModule module = node.getData();
            if (processedModules.contains(module)) {
                throw new CyclicDependencyException(node);
            }
            result.add(module);
            processedModules.add(module);
            addDependencies(queue, node, module.collectExactDependencies());
        }
        return result;
    }

    private static void addDependencies(@NotNull final Queue<TreeNode<OCamlModule>> queue,
                                 @NotNull final TreeNode<OCamlModule> parent,
                                 @NotNull final Collection<OCamlModule> modules) {
        for (final OCamlModule module : modules) {
            final TreeNode<OCamlModule> child = new TreeNode<OCamlModule>(module);
            queue.add(child);
            parent.addChild(child);
        }
    }

    @NotNull
    public Collection<OCamlModule> collectExactDependencies() {
        final Set<OCamlModule> dependencies = new HashSet<OCamlModule>();
        final LocalFileSystem fileSystem = LocalFileSystem.getInstance();
        collectExactDependenciesFor(fileSystem.findFileByIoFile(getImplementationFile()), dependencies);
        collectExactDependenciesFor(fileSystem.findFileByIoFile(getInterfaceFile()), dependencies);
        return dependencies;
        //todo libraries (see DependnecyValidatorManager)
    }

    private void collectExactDependenciesFor(@Nullable final VirtualFile file, @NotNull final Set<OCamlModule> dependencies) {
        if (file == null) return;
        final PsiFile psiFile = ApplicationManager.getApplication().runReadAction(new Computable<PsiFile>() {
            @Nullable
            public PsiFile compute() {
                return PsiManager.getInstance(myProject).findFile(file);
            }
        });
        if (psiFile == null || !(psiFile instanceof OCamlFile)) return;
        final List<OCamlModuleName> moduleReferences = OCamlPsiUtil.collectModuleReferences((OCamlFile) psiFile);
        for (final OCamlModuleName moduleReference : moduleReferences) {
            final OCamlResolvedReference resolvedReference = moduleReference.resolve();
            if (resolvedReference == null) continue;
            final VirtualFile dependencyFile = resolvedReference.getContainingFile().getVirtualFile();
            if (dependencyFile != null) {
                final OCamlModule ocamlModule = getBySourceFile(dependencyFile, myProject);
                if (ocamlModule != null && !this.equals(ocamlModule)) {
                    dependencies.add(ocamlModule);
                }
            }
        }
    }

    @NotNull
    public static List<OCamlModule> sortAccordingToDependencies(@NotNull final Collection<OCamlModule> ocamlModules) throws CyclicDependencyException {
        final MultiMap<OCamlModule, OCamlModule> dependsOn = new MultiMap<OCamlModule, OCamlModule>();
        for (final OCamlModule ocamlModule : ocamlModules) {
            dependsOn.put(ocamlModule, ocamlModule.collectExactDependencies());
        }
        final List<OCamlModule> result = new ArrayList<OCamlModule>();
        while (!dependsOn.keySet().isEmpty()) {
            OCamlModule currentModule = null;
            for (final OCamlModule module : dependsOn.keySet()) {
                if (dependsOn.get(module).isEmpty()) {
                    currentModule = module;
                    break;
                }
            }
            if (currentModule == null) {
                throw new CyclicDependencyException(findCycle(dependsOn));
            }
            result.add(currentModule);
            dependsOn.remove(currentModule);
            for (final OCamlModule module : dependsOn.keySet()) {
                dependsOn.get(module).remove(currentModule);
            }
        }
        return result;
    }

    @NotNull
    private static TreeNode<OCamlModule> findCycle(@NotNull final MultiMap<OCamlModule, OCamlModule> dependsOn) {
        for (final OCamlModule startModule : dependsOn.keySet()) {
            final Set<OCamlModule> processedModules = new HashSet<OCamlModule>();
            final Queue<TreeNode<OCamlModule>> queue = new LinkedList<TreeNode<OCamlModule>>();
            addDependencies(queue, new TreeNode<OCamlModule>(startModule), dependsOn.get(startModule));
            processedModules.add(startModule);
            while (!queue.isEmpty()) {
                final TreeNode<OCamlModule> node = queue.remove();
                final OCamlModule module = node.getData();
                if (processedModules.contains(module)) {
                    return node;
                }
                processedModules.add(module);
                addDependencies(queue, node, dependsOn.get(module));
            }
        }
        assert false;
        return null;
    }

    @NotNull
    public VirtualFile getSourcesDir() {
        return mySourcesDir;
    }

    @NotNull
    public String getName() {
        return myName.length() > 0 ? OCamlStringUtil.capitalize(myName) : "";
    }

    @NotNull
    public File getImplementationFile() {
        return new File(mySourcesDir.getPath(), OCamlFileUtil.getFileName(myName, MLFileType.INSTANCE));
    }

    @NotNull
    public File getInterfaceFile() {
        return new File(mySourcesDir.getPath(), OCamlFileUtil.getFileName(myName, MLIFileType.INSTANCE));
    }

    @NotNull
    public File getCompiledExecutableFile() {
        return new File(getCompiledDir(), myName + (SystemInfo.isWindows ? ".exe" : ""));
    }

    @NotNull
    public File getCompiledImplementationFile() {
        return new File(getCompiledDir(), myName + ".cmo"); //todo CMOFileType
    }

    @NotNull
    public File getCompiledInterfaceFile() {
        return new File(getCompiledDir(), myName + ".cmi"); //todo CMIFileType
    }

    private File getCompiledDir() {
        final ProjectFileIndex fileIndex = ProjectRootManager.getInstance(myProject).getFileIndex();
        return OCamlFileUtil.getCompiledDir(fileIndex, mySourcesDir);
    }

    @Override
    public String toString() {
        return "OCamlModule{Name='" + myName + '\'' + ", SourcesDir='" + OCamlFileUtil.getPathToDisplay(mySourcesDir) + "'}";
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof OCamlModule)) return false;

        final OCamlModule that = (OCamlModule) o;

        if (!myName.equals(that.myName)) return false;
        //noinspection RedundantIfStatement
        if (!mySourcesDir.getPath().equals(that.mySourcesDir.getPath())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mySourcesDir.getPath().hashCode();
        result = 31 * result + myName.hashCode();
        return result;
    }
}
