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
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.UserDataHolder;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PsiElementPattern;
import com.intellij.psi.PsiElement;
import com.intellij.psi.filters.ElementFilter;
import com.intellij.psi.filters.position.FilterPattern;
import com.intellij.util.ProcessingContext;
import manuylov.maxim.ocaml.fileType.ml.MLFileTypeLanguage;
import manuylov.maxim.ocaml.fileType.mli.MLIFileTypeLanguage;
import manuylov.maxim.ocaml.lang.Keywords;
import manuylov.maxim.ocaml.lang.parser.psi.OCamlPsiUtil;
import manuylov.maxim.ocaml.lang.parser.psi.element.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.patterns.PlatformPatterns.psiElement;
import static manuylov.maxim.ocaml.lang.Keywords.*;

/**
 * @author Maxim.Manuylov
 *         Date: 09.05.2010
 */
public class OCamlCompletionContributor extends CompletionContributor {
    @NotNull public static final String DUMMY_IDENTIFIER = "lowerCase";
    @NotNull private static final Key<CompletionParameters> PARAMETERS = Key.create("OCamlCompletionContributorCompletionParameters");

    @NotNull private static final PsiElementPattern.Capture<PsiElement> OCAML_ELEMENT =
        psiElement().andOr(
            psiElement().withLanguage(MLFileTypeLanguage.INSTANCE),
            psiElement().withLanguage(MLIFileTypeLanguage.INSTANCE)
        );

    @Override
    public void beforeCompletion(@NotNull final CompletionInitializationContext context) {
        context.setFileCopyPatcher(new DummyIdentifierPatcher(DUMMY_IDENTIFIER));
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


        /*

      constraint  do          done        downto      else        end

      functor               in               inherit     initializer
                      let                           
                     method                     mutable
               object      of                  or          private
      rec         sig         struct      then        to
               type        val         virtual     when
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
                after(OCamlClassBinding.class),
                after(OCamlClassSpecificationBinding.class),
                after(OCamlClassTypeBinding.class),
                after(OCamlLetBinding.class),
                after(OCamlModuleTypeConstraint.class),
                after(OCamlTypeBinding.class)
            ),
            createCompletionProvider(TailType.SPACE, AND_KEYWORD)
        );
    }

    private void registerAsKeywordCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.and(after(OCamlTypeExpression.class)),
            createCompletionProvider(new TailType() {
                @Override
                public int processTail(@NotNull final Editor editor, final int tailOffset) {
                    return insertChar(editor, TailType.SPACE.processTail(editor, tailOffset), toCharacter(Keywords.QUOTE));
                }
            }, AS_KEYWORD)
        );
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                after(OCamlPattern.class),
                OCAML_ELEMENT.and(after(OCamlInheritClassFieldDefinition.class)).and(after(OCamlClassExpression.class))
            ),
            createCompletionProvider(TailType.SPACE, AS_KEYWORD)
        );
    }

    private void registerExpressionStartKeywordsCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.and(firstChildOf(OCamlExpression.class)),
            createCompletionProvider(TailType.SPACE,
                BEGIN_KEYWORD, IF_KEYWORD, WHILE_KEYWORD, FOR_KEYWORD, MATCH_KEYWORD, FUNCTION_KEYWORD,
                FUN_KEYWORD, TRY_KEYWORD, NEW_KEYWORD, ASSERT_KEYWORD, LAZY_KEYWORD, FALSE_KEYWORD, TRUE_KEYWORD
            )
        );
    }

    private void registerStatementStartKeywordsCompletionProvider() {
        extend(CompletionType.BASIC,
            OCAML_ELEMENT.andOr(
                atFileStart(),
                OCAML_ELEMENT.afterLeaf(Keywords.STRUCT_KEYWORD),
                OCAML_ELEMENT.afterLeaf(Keywords.SIG_KEYWORD),
                OCAML_ELEMENT.afterLeaf(Keywords.SEMICOLON_SEMICOLON),
                after(OCamlStatement.class)
            ),
            createCompletionProvider(TailType.SPACE,
                EXTERNAL_KEYWORD, EXCEPTION_KEYWORD, CLASS_KEYWORD, MODULE_KEYWORD, OPEN_KEYWORD, INCLUDE_KEYWORD
            )
        );
    }

    @NotNull
    private ElementPattern atFileStart() {
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
    private ElementPattern firstChildOf(final Class<? extends PsiElement> clazz) {
        return new FilterPattern(new ElementFilter() {
            public boolean isAcceptable(@NotNull final Object element, @NotNull final PsiElement context) {
                final CompletionParameters parameters = getCompletionParameters(element);
                if (parameters == null) return false;

                PsiElement parent = parameters.getPosition();
                while (parent != null) {
                    if (clazz.isInstance(parent)) return true;
                    if (OCamlPsiUtil.getStrictPrevSibling(parent) != null) break;
                    parent = OCamlPsiUtil.getParent(parent);
                }

                return false;
            }

            public boolean isClassAcceptable(@NotNull final Class hintClass) {
                return true;
            }
        });
    }

    @NotNull
    private static ElementPattern after(@NotNull final Class<? extends PsiElement> clazz) {
        return new FilterPattern(new ElementFilter() {
            public boolean isAcceptable(@NotNull final Object element, @NotNull final PsiElement context) {
                final CompletionParameters parameters = getCompletionParameters(element);
                if (parameters == null) return false;

                final PsiElement originalElement = parameters.getOriginalPosition();

                final PsiElement previousLeaf = originalElement == null
                    ? OCamlPsiUtil.getNonWhiteSpaceLastLeaf(parameters.getOriginalFile())
                    : OCamlPsiUtil.getNonWhiteSpacePreviousLeaf(originalElement);
                if (previousLeaf == null) return false;

                final int positionStartOffset = parameters.getPosition().getTextRange().getStartOffset();

                PsiElement parent = previousLeaf;
                while (parent != null) {
                    if (parent.getTextRange().getEndOffset() > positionStartOffset) break;
                    if (clazz.isInstance(parent)) return OCamlPsiUtil.endsCorrectlyIfOCamlElement(parent);
                    parent = OCamlPsiUtil.getParent(parent);
                }

                final PsiElement notNullElement = originalElement == null ? previousLeaf : originalElement;
                
                final PsiElement clazzElement = OCamlPsiUtil.getParentOfType(notNullElement, clazz);
                if (clazzElement == null) return false;

                final PsiElement parentStatement = OCamlPsiUtil.getParentOfType(notNullElement, OCamlStatement.class);
                if (parentStatement == null) return false;

                final int parentStatementStartOffset = parentStatement.getTextRange().getStartOffset();
                final int positionStartOffsetInParent = positionStartOffset - parentStatementStartOffset;
                final int clazzElementStartOffsetInParent = clazzElement.getTextRange().getStartOffset() - parentStatementStartOffset;
                
                final String text = parentStatement.getText().substring(0, positionStartOffsetInParent);
                final TextRange textRange = new TextRange(clazzElementStartOffsetInParent, positionStartOffsetInParent);

                return OCamlPsiUtil.isElementOfType(text, textRange, clazz, clazzElement.getLanguage(), parameters.getOriginalFile().getProject());
            }

            public boolean isClassAcceptable(@NotNull final Class hintClass) {
                return true;
            }
        });
    }

    @Nullable
    private static CompletionParameters getCompletionParameters(@NotNull final Object object) {
        if (!(object instanceof UserDataHolder)) return null;
        return ((UserDataHolder) object).getUserData(PARAMETERS);
    }

    @NotNull
    private static CompletionProvider<CompletionParameters> createCompletionProvider(@NotNull final TailType tailType, @NotNull final String... keywords) {
        return new CompletionProvider<CompletionParameters>() {
            @Override
            protected void addCompletions(@NotNull final CompletionParameters parameters, final ProcessingContext context, @NotNull final CompletionResultSet result) {
                putKeywords(result, tailType, keywords);
            }
        };
    }

    private static void putKeywords(@NotNull final CompletionResultSet result, @NotNull final TailType tail, @NotNull final String... words) {
        for (final String word : words) {
            result.addElement(TailTypeDecorator.withTail(LookupElementBuilder.create(word).setBold(), tail));
        }
    }

    private char toCharacter(@NotNull final String str) {
        assert str.length() == 1;
        return str.charAt(0);
    }
}
