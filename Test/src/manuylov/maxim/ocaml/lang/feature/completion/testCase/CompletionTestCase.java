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

package manuylov.maxim.ocaml.lang.feature.completion.testCase;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.mock.MockPsiManager;
import com.intellij.openapi.command.impl.DummyProject;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.psi.DummyHolderViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.PsiFileImpl;
import manuylov.maxim.ocaml.fileType.ml.MLFileTypeLanguage;
import manuylov.maxim.ocaml.lang.BaseOCamlTestCase;
import manuylov.maxim.ocaml.lang.feature.completion.OCamlCompletionContributor;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElement;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlElementFactory;
import manuylov.maxim.ocaml.lang.parser.util.ParserTestUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.testng.annotations.BeforeMethod;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maxim.Manuylov
 *         Date: 20.05.2010
 */
public abstract class CompletionTestCase extends BaseOCamlTestCase {
    @NotNull private static final String COMPLETION_POSITION = "}{";

    @NotNull private static final ParserDefinition ourParserDefinition = MLFileTypeLanguage.INSTANCE.getParserDefinition();
    @NotNull private static final CompletionContributor ourContributor = new OCamlCompletionContributor();

    private static int testNumber;

    @BeforeMethod
    public void setUp() {
        super.setUp();
        testNumber = 0;
    }

    @NotNull
    protected abstract CompletionType getCompletionType();

    protected abstract int getInvocationCount();

    protected void doTest(final int n, @NotNull final String text, @NotNull final String... variants) throws Exception {
        if (n <= testNumber) {
            throw new IllegalArgumentException("n = " + n + ", testNumber = " + testNumber);
        }
        testNumber = n;

        final String errorText = "Test " + n;

        int completionPosition = text.indexOf(COMPLETION_POSITION);
        assertTrue(completionPosition != -1, errorText);
        final String actualText = remove(text, completionPosition, COMPLETION_POSITION.length());
        assertEquals(actualText.indexOf(COMPLETION_POSITION), -1, errorText);

        doTest(actualText, completionPosition, variants, errorText);
    }

    @NotNull
    private String remove(@NotNull final String text, final int start, final int length) {
        return text.substring(0, start) + text.substring(start + length);
    }

    private void doTest(@NotNull final String actualText,
                        final int completionPosition,
                        @Nullable final String[] variants,
                        @NotNull final String errorText) throws Exception {
        final ASTNode originalRoot = ParserTestUtil.buildTree(actualText, ourParserDefinition);
        final OCamlElement originalPsiRoot = OCamlElementFactory.INSTANCE.createElement(originalRoot);

        final ASTNode dummyRoot = ParserTestUtil.buildTree(insertDummyIdentifier(actualText, completionPosition), ourParserDefinition);
        final OCamlElement dummyPsiRoot = OCamlElementFactory.INSTANCE.createElement(dummyRoot);

        final Set<String> result = new HashSet<String>();
        ourContributor.fillCompletionVariants(
            createCompletionParameters(originalPsiRoot, dummyPsiRoot, completionPosition),
            createResultSet(result)
        );

        final HashSet<String> variantsSet = new HashSet<String>(Arrays.asList(variants));
        assertEquals(result, variantsSet, errorText + "\nactual: " + result.toString() + "\nexpected: " + variantsSet.toString() + "\n");
    }

    @NotNull
    private String insertDummyIdentifier(@NotNull final String text, final int position) {
        final StringBuilder sb = new StringBuilder(text);
        sb.insert(position, OCamlCompletionContributor.DUMMY_IDENTIFIER);
        return sb.toString();
    }

    @NotNull
    private CompletionParameters createCompletionParameters(@NotNull final OCamlElement originalPsiRoot,
                                                            @NotNull final OCamlElement dummyPsiRoot,
                                                            final int completionPosition) {
        //noinspection ConstantConditions
        return new CompletionParameters(
            dummyPsiRoot.findElementAt(completionPosition),
            createFakeFile(originalPsiRoot),
            getCompletionType(),
            completionPosition,
            getInvocationCount()) {

            @Override
            public PsiElement getOriginalPosition() {
                return originalPsiRoot.findElementAt(completionPosition);
            }
        };
    }

    @NotNull
    private CompletionResultSet createResultSet(@NotNull final Set<String> result) {
        return new CompletionResultSet(null, null, ourContributor) {
            @Override
            public void addElement(@NotNull final LookupElement element) {
                result.add(element.getLookupString());
            }

            @NotNull
            @Override
            public CompletionResultSet withPrefixMatcher(@NotNull final PrefixMatcher matcher) {
                return this;
            }

            @NotNull
            @Override
            public CompletionResultSet withPrefixMatcher(@NotNull final String prefix) {
                return this;
            }

            @NotNull
            @Override
            public CompletionResultSet caseInsensitive() {
                return this;
            }
        };
    }

    @NotNull
    private PsiFile createFakeFile(@NotNull final OCamlElement originalPsiRoot) {
        return new PsiFileImpl(new DummyHolderViewProvider(new MockPsiManager())) {
            @NotNull
            public FileType getFileType() {
                //noinspection ConstantConditions
                return null;
            }

            @NotNull
            @Override
            public Project getProject() {
                return DummyProject.getInstance();
            }

            @Override
            @Nullable
            public PsiElement getLastChild() {
                return originalPsiRoot.getLastChild();
            }

            public void accept(@NotNull final PsiElementVisitor visitor) {}
        };
    }
}
