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

package ocaml.toolWindow;

import com.intellij.execution.ExecutionException;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.Condition;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentManager;
import com.intellij.ui.content.ContentManagerAdapter;
import com.intellij.ui.content.ContentManagerEvent;
import ocaml.module.OCamlModuleType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Maxim.Manuylov
 *         Date: 03.04.2010
 */
public class OCamlToolWindowFactory implements ToolWindowFactory, Condition<Project>, PersistentStateComponent<String> {
    public void createToolWindowContent(@NotNull final Project project, @NotNull final ToolWindow toolWindow) {
        final ContentManager contentManager = toolWindow.getContentManager();
        contentManager.addContentManagerListener(new ContentManagerAdapter() {
            @Override
            public void contentRemoved(@NotNull final ContentManagerEvent event) {
                if (contentManager.getContentCount() == 0) {
                    addAndSelectStartContent(project, contentManager);
                }
            }
        });

        addAndSelectStartContent(project, contentManager);
    }

    public static void addAndSelectStartContent(@NotNull final Project project, @NotNull final ContentManager contentManager) {
        final OCamlToolWindowStartView view = new OCamlToolWindowStartView(project, contentManager);
        addAndSelectContent(contentManager, view, null, false);
    }

    public static void addAndSelectTopLevelConsoleContent(@NotNull final Project project,
                                                          @NotNull final ContentManager contentManager,
                                                          @NotNull final Sdk topLevelSdk) {
        final OCamlTopLevelConsoleView view;
        try {
            view = new OCamlTopLevelConsoleView(project, contentManager, topLevelSdk);
        } catch (final ExecutionException e) {
            Messages.showErrorDialog(e.getLocalizedMessage(), "Error");
            return;
        }
        addAndSelectContent(contentManager, view, "Top Level #" + view.getConsoleNumber(), true);
    }

    private static void addAndSelectContent(@NotNull final ContentManager contentManager,
                                            @NotNull final BaseOCamlToolWindowView view,
                                            @Nullable final String title,
                                            final boolean closable) {
        final Content content = contentManager.getFactory().createContent(view, title, false);
        content.setDisposer(view);
        content.setCloseable(closable);
        content.setPreferredFocusableComponent(view);
        contentManager.addContent(content);
        contentManager.setSelectedContent(content, true);
    }

    public boolean value(@NotNull final Project project) {
        final Module[] modules = ModuleManager.getInstance(project).getModules();
        for (final Module module : modules) {
            if (OCamlModuleType.ID.equals(module.getModuleType().getId())) {
                return true;
            }
        }
        return false;
    }

    public String getState() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void loadState(final String state) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
