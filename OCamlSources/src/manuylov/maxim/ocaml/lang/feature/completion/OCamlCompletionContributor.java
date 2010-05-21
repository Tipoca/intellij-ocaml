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

package manuylov.maxim.ocaml.lang.feature.completion;

import com.intellij.codeInsight.TailType;
import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.codeInsight.lookup.TailTypeDecorator;
import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.util.ProcessingContext;
import manuylov.maxim.ocaml.fileType.ml.MLFileTypeLanguage;
import manuylov.maxim.ocaml.fileType.mli.MLIFileTypeLanguage;
import manuylov.maxim.ocaml.lang.Keywords;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.*;
import manuylov.maxim.ocaml.util.OCamlStringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static manuylov.maxim.ocaml.lang.Keywords.*;

/**
 * @author Maxim.Manuylov
 *         Date: 09.05.2010
 */
public class OCamlCompletionContributor extends CompletionContributor {
    @NotNull public static final String LOWER_CASE_DUMMY_IDENTIFIER = "lowerCase"; // todo sometimes this string appeared in list (e.g "class }{") 
    @NotNull public static final String UPPER_CASE_DUMMY_IDENTIFIER = "UpperCase";

    @NotNull private static final Key<CompletionParameters> PARAMETERS = Key.create("OCamlCompletionContributorCompletionParameters");

    @NotNull private static final PsiElementPattern.Capture<PsiElement> OCAML_ELEMENT =
        psiElement().andOr(
            psiElement().withLanguage(MLFileTypeLanguage.INSTANCE),
            psiElement().withLanguage(MLIFileTypeLanguage.INSTANCE)
        );

    @NotNull private static final PsiElementPattern.Capture<PsiElement> EXPRESSION_START =
        OCAML_ELEMENT.and(firstChildOfWithLowerCase(OCamlExpression.class));

    @NotNull private static final PsiElementPattern.Capture<PsiElement> PATTERN_START =
        OCAML_ELEMENT.and(firstChildOfWithLowerCase(OCamlPattern.class));

    @NotNull private static final ElementPattern<? extends PsiElement> STATEMENT_START =
        OCAML_ELEMENT.andOr(
            atFileStart(),
            OCAML_ELEMENT.afterLeaf(Keywords.STRUCT_KEYWORD),
            OCAML_ELEMENT.afterLeaf(Keywords.SIG_KEYWORD),
            OCAML_ELEMENT.afterLeaf(Keywords.SEMICOLON_SEMICOLON),
            after(OCamlStatement.class, true)
        );

    @NotNull private static final ElementPattern<? extends PsiElement> SPECIFICATION_START =
        OCAML_ELEMENT.andOr(
            atFileStart(),
            OCAML_ELEMENT.afterLeaf(Keywords.SIG_KEYWORD),
            OCAML_ELEMENT.afterLeaf(Keywords.SEMICOLON_SEMICOLON),
            after(OCamlSpecification.class, true)
        );

    @NotNull private static final ElementPattern<? extends PsiElement> CLASS_EXPRESSION_START =
        OCAML_ELEMENT.and(firstChildOfWithLowerCase(OCamlClassExpression.class));

    @NotNull private static final ElementPattern<? extends PsiElement> CLASS_BODY_TYPE_START =
        OCAML_ELEMENT.and(firstChildOfWithLowerCase(OCamlClassBodyType.class));

    @NotNull private static final ElementPattern<? extends PsiElement> MODULE_EXPRESSION_START =
        OCAML_ELEMENT.and(firstChildOfWithUpperCase(OCamlModuleExpression.class));

    @NotNull private static final ElementPattern<? extends PsiElement> MODULE_TYPE_START =
        OCAML_ELEMENT.and(firstChildOfWithUpperCase(OCamlModuleType.class));

    @NotNull private static final ElementPattern<? extends PsiElement> CLASS_FIELD_DEFINITION_START =
        OCAML_ELEMENT
            .and(after(OCamlClassBinding.class, false))
            .andOr(
                OCAML_ELEMENT.afterLeaf(OBJECT_KEYWORD), //todo object (self)
                after(OCamlClassFieldDefinition.class, true)
            );

    @NotNull private static final ElementPattern<? extends PsiElement> CLASS_FIELD_SPECIFICATION_START =
        OCAML_ELEMENT
            .and(after(OCamlClassSpecificationBinding.class, false))
            .andOr(
                OCAML_ELEMENT.afterLeaf(OBJECT_KEYWORD), //todo object (self)
                after(OCamlClassFieldSpecification.class, true)
            );

    @Override
    public void beforeCompletion(@NotNull final CompletionInitializationContext context) {
        context.setFileCopyPatcher(new DummyIdentifierPatcher(LOWER_CASE_DUMMY_IDENTIFIER));
    }

    @Override
    public void fillCompletionVariants(@NotNull final CompletionParameters parameters, @NotNull final CompletionResultSet result) {
        final PsiElement element = parameters.getPosition();
        try {
            element.putUserData(PARAMETERS, parameters);
            super.fillCompletionVariants(parameters, result);
        }
        finally {
            element.putUserData(PARAMETERS, null);
        }
    }

    public OCamlCompletionContributor() {
        registerAndKeywordCompletionProvider();
        registerAsKeywordCompletionProvider();
        registerExpressionStartKeywordsCompletionProvider();
        registerStatementStartKeywordsCompletionProvider();
        registerLetKeywordCompletionProvider();
        registerRecKeywordCompletionProvider();
        registerStructKeywordCompletionProvider();
        registerSigKeywordCompletionProvider();
        registerFunctorKeywordCompletionProvider();
        registerLazyKeywordCompletionProvider();
        registerObjectKeywordCompletionProvider();
        registerInheritKeywordCompletionProvider();
        registerInitializerKeywordCompletionProvider();
        registerMethodKeywordCompletionProvider();
        registerValKeywordCompletionProvider();
        registerConstraintKeywordCompletionProvider();
        registerTypeKeywordCompletionProvider();
        registerPrivateKeywordCompletionProvider();
              //todo tests

        /*          
        do          done        downto      else        end
                     in
                                          mutable
                     of                  or
                              then        to
                                virtual     when
      with

->
;;
)
]
}
|]
>
>]
>}


    ::    :=    :>         ?
    <-      ??
  */








        /*extend(CompletionType.BASIC, psiElement(), new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters, @NotNull final ProcessingContext context, @NotNull final CompletionResultSet result) {
                collectVariantsFor(result);
            }
        }); */
        //todo UpperCase basic completion + ModuleName (ClassName?) completion + smart completion
    }

    private void registerAndKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                after(OCamlClassBinding.class, true),
                after(OCamlClassSpecificationBinding.class, true),
                after(OCamlClassTypeBinding.class, true),
                after(OCamlLetBinding.class, true),
                after(OCamlModuleTypeConstraint.class, true),
                after(OCamlTypeBinding.class, true)
            ),
            createCompletionProvider(TailType.SPACE, AND_KEYWORD)
        );
    }

    private void registerAsKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.and(after(OCamlTypeExpression.class, true)),
            createCompletionProvider(new TailType() {
                @Override
                public int processTail(@NotNull final Editor editor, final int tailOffset) {
                    return insertChar(editor, TailType.SPACE.processTail(editor, tailOffset), toCharacter(Keywords.QUOTE));
                }
            }, AS_KEYWORD)
        );
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                after(OCamlPattern.class, true),
                OCAML_ELEMENT.and(after(OCamlInheritClassFieldDefinition.class, true)).and(after(OCamlClassExpression.class, true))
            ),
            createCompletionProvider(TailType.SPACE, AS_KEYWORD)
        );
    }

    private void registerExpressionStartKeywordsCompletionProvider() {
        extend(CompletionType.BASIC,
            EXPRESSION_START,
            createCompletionProvider(TailType.SPACE,
                BEGIN_KEYWORD, IF_KEYWORD, WHILE_KEYWORD, FOR_KEYWORD, MATCH_KEYWORD, FUNCTION_KEYWORD,
                FUN_KEYWORD, TRY_KEYWORD, NEW_KEYWORD, ASSERT_KEYWORD, FALSE_KEYWORD, TRUE_KEYWORD
            )
        );
    }

    private void registerStatementStartKeywordsCompletionProvider() {
        extend(CompletionType.BASIC,
            STATEMENT_START,
            createCompletionProvider(TailType.SPACE,
                EXTERNAL_KEYWORD, EXCEPTION_KEYWORD, CLASS_KEYWORD, MODULE_KEYWORD, OPEN_KEYWORD, INCLUDE_KEYWORD
            )
        );
    }

    private void registerLetKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                STATEMENT_START,
                EXPRESSION_START,
                CLASS_EXPRESSION_START
            ),
            createCompletionProvider(TailType.SPACE, LET_KEYWORD)
        );
    }

    private void registerRecKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.afterLeaf(LET_KEYWORD),
            createCompletionProvider(TailType.SPACE, REC_KEYWORD)
        );
    }

    private void registerStructKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            MODULE_EXPRESSION_START,
            createCompletionProvider(TailType.SPACE, STRUCT_KEYWORD)
        );
    }

    private void registerSigKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            MODULE_TYPE_START,
            createCompletionProvider(TailType.SPACE, SIG_KEYWORD)
        );
    }

    private void registerFunctorKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                MODULE_EXPRESSION_START,
                MODULE_TYPE_START
            ),
            createCompletionProvider(TailType.SPACE, FUNCTOR_KEYWORD)
        );
    }

    private void registerLazyKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                EXPRESSION_START,
                PATTERN_START
            ),
            createCompletionProvider(TailType.SPACE, LAZY_KEYWORD)
        );
    }

    private void registerObjectKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                EXPRESSION_START,
                CLASS_EXPRESSION_START,
                CLASS_BODY_TYPE_START
            ),
            createCompletionProvider(TailType.SPACE, OBJECT_KEYWORD)
        );
    }

    private void registerInheritKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                CLASS_FIELD_DEFINITION_START,
                CLASS_FIELD_SPECIFICATION_START
            ),
            createCompletionProvider(TailType.SPACE, INHERIT_KEYWORD)
        );
    }

    private void registerInitializerKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                CLASS_FIELD_DEFINITION_START
            ),
            createCompletionProvider(TailType.SPACE, INITIALIZER_KEYWORD)
        );
    }

    private void registerMethodKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                CLASS_FIELD_DEFINITION_START,
                CLASS_FIELD_SPECIFICATION_START
            ),
            createCompletionProvider(TailType.SPACE, METHOD_KEYWORD)
        );
    }

    private void registerValKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                SPECIFICATION_START,
                CLASS_FIELD_DEFINITION_START,
                CLASS_FIELD_SPECIFICATION_START
            ),
            createCompletionProvider(TailType.SPACE, VAL_KEYWORD)
        );
    }

    private void registerConstraintKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                after(OCamlTypeBinding.class, true),
                CLASS_FIELD_DEFINITION_START,
                CLASS_FIELD_SPECIFICATION_START
            ),
            createCompletionProvider(TailType.SPACE, CONSTRAINT_KEYWORD)
        );
    }

    private void registerTypeKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                STATEMENT_START,
                OCAML_ELEMENT.afterLeaf(CLASS_KEYWORD),
                OCAML_ELEMENT.afterLeaf(MODULE_KEYWORD)
            ),
            createCompletionProvider(TailType.SPACE, TYPE_KEYWORD)
        );
    }

    private void registerPrivateKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.afterLeaf(METHOD_KEYWORD),
            createCompletionProvider(TailType.SPACE, PRIVATE_KEYWORD)
        );
    }

    @NotNull
    private static ElementPattern atFileStart() {
        return new FilterPattern(new ElementFilter() {
            public boolean isAcceptable(@NotNull final Object element, @NotNull final PsiElement context) {
                final CompletionParameters parameters = getCompletionParameters(element);
                return parameters != null && OCamlPsiUtil.getNonWhiteSpacePreviousLeaf(parameters.getPosition()) == null;
            }

            public boolean isClassAcceptable(@NotNull final Class hintClass) {
                return true;
            }
        });
    }

    @NotNull
    private static ElementPattern firstChildOfWithLowerCase(final Class<? extends PsiElement> clazz) {
        return new FilterPattern(new ElementFilter() {
            public boolean isAcceptable(@NotNull final Object element, @NotNull final PsiElement context) {
                final CompletionParameters parameters = getCompletionParameters(element);
                return parameters != null && isFirstChildOf(parameters.getPosition(), clazz);
            }

            public boolean isClassAcceptable(@NotNull final Class hintClass) {
                return true;
            }
        });
    }

    @NotNull
    private static ElementPattern firstChildOfWithUpperCase(final Class<? extends PsiElement> clazz) {
        return new FilterPattern(new ElementFilter() {
            public boolean isAcceptable(@NotNull final Object element, @NotNull final PsiElement context) {
                final CompletionParameters parameters = getCompletionParameters(element);
                if (parameters == null) return false;

                final PsiElement upperCaseDummyElement = createUpperCaseDummyElement(parameters);
                return upperCaseDummyElement != null && isFirstChildOf(upperCaseDummyElement, clazz);
            }

            public boolean isClassAcceptable(@NotNull final Class hintClass) {
                return true;
            }
        });
    }

    @NotNull
    private static ElementPattern after(@NotNull final Class<? extends PsiElement> clazz, final boolean shouldEndCorrectly) {
        return after(ofType(clazz), shouldEndCorrectly);
    }

    @NotNull
    private static ElementPattern after(@NotNull final ElementPattern pattern, final boolean shouldEndCorrectly) {
        return new FilterPattern(new ElementFilter() {
            public boolean isAcceptable(@NotNull final Object element, @NotNull final PsiElement context) {
                final CompletionParameters parameters = getCompletionParameters(element);
                if (parameters == null) return false;

                final PsiElement originalElement = parameters.getOriginalPosition();

                final PsiElement previousLeaf = getPreviousLeaf(originalElement, parameters.getOriginalFile());
                if (previousLeaf == null) return false;

                final int positionStartOffset = parameters.getPosition().getTextRange().getStartOffset();

                PsiElement parent = previousLeaf;
                while (parent != null) {
                    if (parent.getTextRange().getEndOffset() > positionStartOffset) break;
                    if (pattern.accepts(parent)) return endsCorrectly(parent, shouldEndCorrectly);
                    parent = OCamlPsiUtil.getParent(parent);
                }

                final PsiElement notNullElement = originalElement == null ? previousLeaf : originalElement;

                final PsiElement patternElement = getParentOfPattern(notNullElement, pattern);
                if (patternElement == null) return false;

                final OCamlStatement parentStatement = OCamlPsiUtil.getStatementOf(previousLeaf);
                if (parentStatement == null) return false;

                final int parentStatementStartOffset = parentStatement.getTextRange().getStartOffset();
                final int positionStartOffsetInParent = positionStartOffset - parentStatementStartOffset;
                final int clazzElementStartOffsetInParent = patternElement.getTextRange().getStartOffset() - parentStatementStartOffset;

                final String text = parentStatement.getText().substring(0, positionStartOffsetInParent);
                final TextRange textRange = new TextRange(clazzElementStartOffsetInParent, positionStartOffsetInParent);

                final PsiElement elementInRange = OCamlPsiUtil.findElementInRange(
                    text, textRange, patternElement.getLanguage(), parameters.getOriginalFile().getProject()
                );

                return elementInRange != null && endsCorrectly(elementInRange, shouldEndCorrectly);
            }

            public boolean isClassAcceptable(@NotNull final Class hintClass) {
                return true;
            }
        });
    }

    private static boolean endsCorrectly(@NotNull final PsiElement element, final boolean shouldEndCorrectly) {
        return !shouldEndCorrectly || OCamlPsiUtil.endsCorrectlyIfOCamlElement(element);
    }

    @NotNull
    private static FilterPattern ofType(@NotNull final Class<? extends PsiElement> clazz) {
        return new FilterPattern(new ElementFilter() {
            public boolean isAcceptable(@NotNull final Object element, @NotNull final PsiElement context) {
                return clazz.isInstance(element);
            }

            public boolean isClassAcceptable(@NotNull final Class hintClass) {
                return false;
            }
        });
    }

    @Nullable
    private static CompletionParameters getCompletionParameters(@NotNull final Object object) {
        if (!(object instanceof UserDataHolder)) return null;
        return ((UserDataHolder) object).getUserData(PARAMETERS);
    }

    @Nullable
    private static PsiElement getParentOfPattern(@NotNull final PsiElement element, @NotNull final ElementPattern pattern) {
        PsiElement parent = element;
        while (parent != null) {
            if (pattern.accepts(parent)) return parent;
            parent = OCamlPsiUtil.getParent(parent);
        }

        return null;
    }

    @Nullable
    private static PsiElement createUpperCaseDummyElement(@NotNull final CompletionParameters parameters) {
        final PsiElement previousLeaf = getPreviousLeaf(parameters.getOriginalPosition(), parameters.getOriginalFile());
        if (previousLeaf == null) return null;

        final OCamlStatement statement = OCamlPsiUtil.getStatementOf(previousLeaf);
        if (statement == null) return null;

        final ASTNode statementNode = statement.getNode();
        if (statementNode == null) return null;

        final int offsetInStatement = parameters.getOffset() - statement.getTextRange().getStartOffset();

        final String textWithDummyIdentifier = OCamlStringUtil.insert(
            statementNode.getText(),
            offsetInStatement,
            UPPER_CASE_DUMMY_IDENTIFIER
        );

        final ParserDefinition parserDefinition = LanguageParserDefinitions.INSTANCE.forLanguage(statement.getLanguage());
        if (parserDefinition == null) return null;

        return OCamlPsiUtil.parse(
            textWithDummyIdentifier,
            parserDefinition,
            parameters.getOriginalFile().getProject()
        ).findElementAt(offsetInStatement);
    }

    @Nullable
    private static PsiElement getPreviousLeaf(@Nullable final PsiElement element, @NotNull final PsiFile file) {
        return element == null
            ? OCamlPsiUtil.getNonWhiteSpaceLastLeaf(file)
            : OCamlPsiUtil.getNonWhiteSpacePreviousLeaf(element);
    }

    private static boolean isFirstChildOf(@NotNull final PsiElement element, @NotNull final Class<? extends PsiElement> clazz) {
        PsiElement parent = element;
        while (parent != null) {
            if (clazz.isInstance(parent)) return true;
            if (OCamlPsiUtil.getStrictPrevSibling(parent) != null) break;
            parent = OCamlPsiUtil.getParent(parent);
        }

        return false;
    }

    @NotNull
    private static CompletionProvider<CompletionParameters> createCompletionProvider(@NotNull final TailType tailType, @NotNull final String... keywords) {
        return new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters, @NotNull final ProcessingContext context, @NotNull final CompletionResultSet result) {
                putKeywords(result, tailType, keywords);
            }
        };
    }

    private static void putKeywords(@NotNull final CompletionResultSet result, @NotNull final TailType tail, @NotNull final String... words) {
        for (final String word : words) {
            result.addElement(TailTypeDecorator.withTail(LookupElementBuilder.create(word).setBold(), tail));
        }
    }

    private static char toCharacter(@NotNull final String str) {
        assert str.length() == 1;
        return str.charAt(0);
    }
}
