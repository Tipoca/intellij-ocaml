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

package manuylov.maxim.ocaml.lang.parser.ast;

import com.intellij.lang.PsiBuilder;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import manuylov.maxim.ocaml.lang.Strings;
import manuylov.maxim.ocaml.lang.lexer.token.OCamlTokenTypes;
import manuylov.maxim.ocaml.lang.parser.ast.element.OCamlElementTypes;
import org.jetbrains.annotations.NotNull;

/**
 * @author Maxim.Manuylov
 *         Date: 12.02.2009
 */
class NameParsing extends Parsing {
    public static void parseModulePath(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker modulePathMarker = builder.mark();

        boolean dotParsed = false;

        do {
            parseModuleName(builder);

            if (builder.getTokenType() == OCamlTokenTypes.DOT) {
                dotParsed = true;
            }
        } while (ignore(builder, OCamlTokenTypes.DOT));

        if (dotParsed) {
            modulePathMarker.done(OCamlElementTypes.MODULE_PATH);
        }
        else {
            modulePathMarker.drop();
        }
    }

    public static void parseModuleName(@NotNull final PsiBuilder builder) {
        if (!tryParseModuleName(builder)) {
            builder.error(Strings.MODULE_NAME_EXPECTED);
        }
    }

    public static void parseConstructorPath(@NotNull final PsiBuilder builder, final boolean isDefinition) {
        if (!tryParseConstructorPath(builder, isDefinition)) {
            builder.error(Strings.CONSTRUCTOR_PATH_EXPECTED);
        }
    }

    public static void parseAnyIdentifier(@NotNull final PsiBuilder builder) {
        if (!tryParseAnyIdentifier(builder)) {
            builder.error(Strings.IDENTIFIER_EXPECTED);
        }
    }

    public static void parseTypeParameterName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        parseAnyIdentifier(builder);

        marker.done(OCamlElementTypes.TYPE_PARAMETER_NAME);
    }

    public static void parseForExpressionIndexVariableName(@NotNull final PsiBuilder builder) {
        if (!tryParseForExpressionIndexVariableName(builder)) {
            builder.error(Strings.INDEX_VARIABLE_NAME_EXPECTED);
        }
    }

    public static boolean tryParseForExpressionIndexVariableName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker marker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            marker.drop();
            return false;
        }

        marker.done(OCamlElementTypes.FOR_EXPRESSION_INDEX_VARIABLE_NAME);

        return true;
    }

    public static void parseClassPath(@NotNull final PsiBuilder builder) {
        if (!tryParseClassPath(builder)) {
            builder.error(Strings.CLASS_PATH_EXPECTED);
        }
    }

    public static void parseMethodName(@NotNull final PsiBuilder builder) {
        if (!tryParseMethodName(builder)) {
            builder.error(Strings.METHOD_NAME_EXPECTED);
        }
    }

    public static void parseLabelNameProbablyWithColon(@NotNull final PsiBuilder builder, @NotNull final Runnable afterColonProcessing, final boolean isDefinition) {
        doParseLabelNameWithColon(builder, afterColonProcessing, false, isDefinition);
    }

    private static void doParseLabelNameWithColon(final PsiBuilder builder, final Runnable afterColonProcessing, final boolean colonIsMandatory, final boolean isDefinition) {
        final PsiBuilder.Marker labelNameMarker = builder.mark();

        int offset = builder.getCurrentOffset();

        if (builder.getTokenType() == OCamlTokenTypes.LCFC_IDENTIFIER) {
            final String tokenText = builder.getTokenText();
            if (tokenText != null) {
                offset += tokenText.length();
                builder.advanceLexer();
            }
        }
        else {
            builder.error(Strings.LABEL_NAME_EXPECTED);
        }

        labelNameMarker.done(OCamlElementTypes.LABEL_NAME);
        if (isDefinition) {
            labelNameMarker.precede().done(OCamlElementTypes.LABEL_DEFINITION);
        }

        if (offset == builder.getCurrentOffset() && ignore(builder, OCamlTokenTypes.COLON)) {
            afterColonProcessing.run();
        }
        else if (colonIsMandatory) {
            builder.error(Strings.COLON_EXPECTED);
        }
    }

    public static void parseModuleTypeName(@NotNull final PsiBuilder builder) {
        if (!tryParseModuleTypeName(builder)) {
            builder.error(Strings.MODULE_TYPE_NAME_EXPECTED);
        }
    }

    public static void parseExtendedModulePath(@NotNull final PsiBuilder builder) {
        if (!tryParseExtendedModulePath(builder)) {
            builder.error(Strings.EXTENDED_MODULE_PATH_EXPECTED);
        }
    }

    private static boolean tryParseExtendedModulePath(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker extendedModulePathMarker = builder.mark();

        final boolean extendedModuleNameParsed = doTryParseExtendedModuleNames(builder);

        if (extendedModuleNameParsed) {
            if (!tryParseExtendedModuleName(builder, false)) {
                builder.error(Strings.EXTENDED_MODULE_NAME_EXPECTED);
            }

            extendedModulePathMarker.done(OCamlElementTypes.EXTENDED_MODULE_PATH);
        }
        else {
            extendedModulePathMarker.drop();

            return tryParseExtendedModuleName(builder, false);
        }

        return true;
    }

    public static void parseTypeConstructorPath(@NotNull final PsiBuilder builder) {
        if (!tryParseTypeConstructorPath(builder)) {
            builder.error(Strings.TYPE_CONSTRUCTOR_PATH_EXPECTED);
        }
    }

    public static void parseModuleTypePath(@NotNull final PsiBuilder builder) {
        if (!tryParseModuleTypePath(builder)) {
            builder.error(Strings.MODULE_TYPE_PATH_EXPECTED);
        }
    }

    private static boolean tryParseModuleTypePath(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleTypePathMarker = builder.mark();

        final boolean extendedModuleNameParsed = doTryParseExtendedModuleNames(builder);

        if (extendedModuleNameParsed) {
            if (!tryParseModuleTypeName(builder)) {
                builder.error(Strings.MODULE_TYPE_NAME_EXPECTED);
            }

            moduleTypePathMarker.done(OCamlElementTypes.MODULE_TYPE_PATH);
        }
        else {
            moduleTypePathMarker.drop();

            return tryParseModuleTypeName(builder);
        }

        return true;
    }

    public static void parseTypeConstructorName(@NotNull final PsiBuilder builder) {
        if (!tryParseTypeConstructorName(builder)) {
            builder.error(Strings.TYPE_CONSTRUCTOR_NAME_EXPECTED);
        }
    }

    public static void parseTagName(@NotNull final PsiBuilder builder) {
        if (!tryParseTagName(builder)) {
            builder.error(Strings.TAG_NAME_EXPECTED);
        }
    }

    public static void parseClassName(@NotNull final PsiBuilder builder) {
        if (!tryParseClassName(builder)) {
            builder.error(Strings.CLASS_NAME_EXPECTED);
        }
    }

    public static void parseConstructorName(@NotNull final PsiBuilder builder, final boolean isDefinition) {
        if (!tryParseConstructorName(builder, isDefinition)) {
            builder.error(Strings.CONSTRUCTOR_NAME_EXPECTED);
        }
    }

    public static void parseInstVarName(@NotNull final PsiBuilder builder, final boolean isDefinition) {
        if (!tryParseInstVarName(builder, isDefinition)) {
            builder.error(Strings.INSTANCE_VARIABLE_NAME_EXPECTED);
        }
    }

    public static void parseValueName(@NotNull final PsiBuilder builder, final boolean isPattern) {
        if (!tryParseValueName(builder, isPattern)) {
            builder.error(Strings.VALUE_NAME_EXPECTED);
        }
    }

    public static void parseFieldName(@NotNull final PsiBuilder builder) {
        if (!tryParseFieldName(builder)) {
            builder.error(Strings.FIELD_NAME_EXPECTED);
        }
    }

    public static void parseFieldPath(@NotNull final PsiBuilder builder) {
        if (!tryParseFieldPath(builder)) {
            builder.error(Strings.FIELD_PATH_EXPECTED);
        }
    }

    public static boolean tryParseValueName(@NotNull final PsiBuilder builder, final boolean isPattern) {
        final PsiBuilder.Marker valueNameMarker = builder.mark();

        if (ignore(builder, OCamlTokenTypes.LPAR)) {
            if (!tryParseOperatorName(builder) || !ignore(builder, OCamlTokenTypes.RPAR)) {
                valueNameMarker.rollbackTo();
                return false;
            }
        }
        else {
            if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
                valueNameMarker.drop();
                return false;
            }
        }

        valueNameMarker.done(isPattern ? OCamlElementTypes.VALUE_NAME_PATTERN : OCamlElementTypes.VALUE_NAME);

        return true;
    }

    private static boolean tryParseModuleName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.UCFC_IDENTIFIER)) {
            moduleNameMarker.drop();
            return false;
        }

        moduleNameMarker.done(OCamlElementTypes.MODULE_NAME);

        return true;
    }

    public static boolean tryParseConstructorPath(@NotNull final PsiBuilder builder, final boolean isDefinition) {
        return tryParseModulePathWithLastPart(builder, OCamlElementTypes.CONSTRUCTOR_PATH, isDefinition);
    }

    private static boolean tryParseConstructorName(@NotNull final PsiBuilder builder, final boolean isDefinition) {
        final PsiBuilder.Marker constructorNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.UCFC_IDENTIFIER)) {
            constructorNameMarker.drop();
            return false;
        }

        constructorNameMarker.done(isDefinition ? OCamlElementTypes.CONSTRUCTOR_NAME_DEFINITION : OCamlElementTypes.CONSTRUCTOR_NAME);

        return true;
    }

    private static boolean tryParseTypeConstructorName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker typeConstructorNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            typeConstructorNameMarker.drop();
            return false;
        }

        typeConstructorNameMarker.done(OCamlElementTypes.TYPE_CONSTRUCTOR_NAME);

        return true;
    }

    private static boolean tryParseAnyIdentifier(@NotNull final PsiBuilder builder) {
        return ignore(builder, OCamlTokenTypes.IDENTIFIERS);
    }

    private static boolean tryParseFieldName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker fieldNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            fieldNameMarker.drop();
            return false;
        }

        fieldNameMarker.done(OCamlElementTypes.FIELD_NAME);

        return true;
    }

    public static boolean tryParseClassPath(@NotNull final PsiBuilder builder) {
        return tryParseModulePathWithLastPart(builder, OCamlElementTypes.CLASS_PATH, false);
    }

    private static boolean tryParseClassName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker classNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            classNameMarker.drop();
            return false;
        }

        classNameMarker.done(OCamlElementTypes.CLASS_NAME);

        return true;
    }

    private static boolean tryParseMethodName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker methodNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            methodNameMarker.drop();
            return false;
        }

        methodNameMarker.done(OCamlElementTypes.METHOD_NAME);

        return true;
    }

    private static boolean tryParseInstVarName(@NotNull final PsiBuilder builder, final boolean isDefinition) {
        final PsiBuilder.Marker instVarNameMarker = builder.mark();

        if (!ignore(builder, OCamlTokenTypes.LCFC_IDENTIFIER)) {
            instVarNameMarker.drop();
            return false;
        }

        instVarNameMarker.done(isDefinition ? OCamlElementTypes.INST_VAR_NAME_DEFINITION : OCamlElementTypes.INST_VAR_NAME);

        return true;
    }

    private static boolean tryParseModuleTypeName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker moduleTypeNameMarker = builder.mark();

        if (!tryParseAnyIdentifier(builder)) {
            moduleTypeNameMarker.drop();
            return false;
        }

        moduleTypeNameMarker.done(OCamlElementTypes.MODULE_TYPE_NAME);

        return true;
    }

    public static boolean tryParseTypeConstructorPath(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker typeConstructorPathMarker = builder.mark();

        final boolean extendedModuleNameParsed = doTryParseExtendedModuleNames(builder);

        if (extendedModuleNameParsed) {
            if (!tryParseTypeConstructorName(builder)) {
                builder.error(Strings.TYPE_CONSTRUCTOR_NAME_EXPECTED);
            }

            typeConstructorPathMarker.done(OCamlElementTypes.TYPE_CONSTRUCTOR_PATH);
        }
        else {
            typeConstructorPathMarker.drop();

            return tryParseTypeConstructorName(builder);
        }

        return true;
    }

    public static boolean tryParseQuestAndLabel(@NotNull final PsiBuilder builder) {
        if (ignore(builder, OCamlTokenTypes.QUEST)) {
            doParseLabelNameWithMandatoryColon(builder, true);
            return true;
        }
        else if (getNextTokenType(builder) == OCamlTokenTypes.COLON) {
            doParseLabelNameWithMandatoryColon(builder, true);
            return true;
        }
        return false;
    }

    private static boolean tryParseTagName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker tagNameMarker = builder.mark();

        if (!tryParseAnyIdentifier(builder)) {
            tagNameMarker.drop();
            return false;
        }

        tagNameMarker.done(OCamlElementTypes.TAG_NAME);

        return true;
    }

    public static boolean tryParseValuePath(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker pathMarker = builder.mark();

        boolean dotParsed = false;

        while (tryParseModuleName(builder)) {
            checkMatches(builder, OCamlTokenTypes.DOT, Strings.DOT_EXPECTED);
            dotParsed = true;
        }

        if (!tryParseValueName(builder, false)) {
            pathMarker.rollbackTo();
            return false;
        }

        if (dotParsed) {
            pathMarker.done(OCamlElementTypes.VALUE_PATH);
        }
        else {
            pathMarker.drop();
        }

        return true;
    }

    private static boolean tryParseFieldPath(@NotNull final PsiBuilder builder) {
        return tryParseModulePathWithLastPart(builder, OCamlElementTypes.FIELD_PATH, false);
    }

    public static boolean tryParseConstant(@NotNull final PsiBuilder builder) {
        if (tryParseConstructorPath(builder, false)) {
            return true;
        }

        final PsiBuilder.Marker constantMarker = builder.mark();

        if (!ignore(builder, TokenSet.create(OCamlTokenTypes.INTEGER_LITERAL, OCamlTokenTypes.FLOAT_LITERAL,
                                             OCamlTokenTypes.CHAR_LITERAL, OCamlTokenTypes.STRING_LITERAL,
                                             OCamlTokenTypes.FALSE_KEYWORD, OCamlTokenTypes.TRUE_KEYWORD))) {
            constantMarker.drop();
            return false;
        }

        constantMarker.done(OCamlElementTypes.CONSTANT);

        return true;
    }

    private static void doParseLabelNameWithMandatoryColon(@NotNull final PsiBuilder builder, final boolean isDefinition) {
        doParseLabelNameWithColon(builder, new Runnable() {
            public void run() {
                //do nothing
            }
        }, true, isDefinition);
    }

    private static boolean tryParseModulePathWithLastPart(@NotNull final PsiBuilder builder, @NotNull final IElementType pathType, final boolean isDefinition) {
        final PsiBuilder.Marker pathMarker = builder.mark();

        boolean dotParsed = false;

        while (getNextTokenType(builder) == OCamlTokenTypes.DOT) {
            if (!tryParseModuleName(builder)) {
                pathMarker.rollbackTo();
                return false;
            }
            checkMatches(builder, OCamlTokenTypes.DOT, Strings.DOT_EXPECTED);
            dotParsed = true;
        }

        final boolean lastPartParseResult;

        if (pathType == OCamlElementTypes.CLASS_PATH) {
            lastPartParseResult = tryParseClassName(builder);
        }
        else if (pathType == OCamlElementTypes.FIELD_PATH) {
            lastPartParseResult = tryParseFieldName(builder);
        }
        else {
            //noinspection SimplifiableIfStatement
            if (pathType == OCamlElementTypes.CONSTRUCTOR_PATH) {
                lastPartParseResult = tryParseConstructorName(builder, isDefinition);
            }
            else {
                lastPartParseResult = false;
            }
        }

        if (!lastPartParseResult) {
            pathMarker.rollbackTo();
            return false;
        }

        if (dotParsed) {
            pathMarker.done(pathType);
        }
        else {
            pathMarker.drop();
        }

        return true;
    }

    private static boolean tryParseOperatorName(@NotNull final PsiBuilder builder) {
        final PsiBuilder.Marker operatorNameMarker = builder.mark();

        if (!ignore(builder, TokenSet.orSet(OCamlTokenTypes.PREFIX_OPERATORS, OCamlTokenTypes.INFIX_OPERATORS))) {
            operatorNameMarker.drop();
            return false;
        }

        operatorNameMarker.done(OCamlElementTypes.OPERATOR_NAME);

        return true;
    }

    private static boolean tryParseExtendedModuleName(@NotNull final PsiBuilder builder, final boolean parseDot) {
        final PsiBuilder.Marker extendedModuleNameMarker = builder.mark();

        if (!tryParseModuleName(builder)) {
            extendedModuleNameMarker.drop();
            return false;
        }

        boolean extendedModulePathParsed = false;

        PsiBuilder.Marker marker = builder.mark();

        while (ignore(builder, OCamlTokenTypes.LPAR)) {
            parseExtendedModulePath(builder);

            extendedModulePathParsed = true;

            checkMatches(builder, OCamlTokenTypes.RPAR, Strings.RPAR_EXPECTED);

            marker.done(OCamlElementTypes.PARENTHESES);
            marker = builder.mark();
        }

        marker.drop();

        if (parseDot) {
            if (builder.getTokenType() == OCamlTokenTypes.DOT) {
                if (extendedModulePathParsed) {
                    extendedModuleNameMarker.done(OCamlElementTypes.EXTENDED_MODULE_NAME);
                }
                else {
                    extendedModuleNameMarker.drop();
                }
                builder.advanceLexer();
            }
            else {
                extendedModuleNameMarker.rollbackTo();
                return false;
            }
        }
        else {
            if (extendedModulePathParsed) {
                extendedModuleNameMarker.done(OCamlElementTypes.EXTENDED_MODULE_NAME);
            }
            else {
                extendedModuleNameMarker.drop();
            }
        }

        return true;
    }

    private static boolean doTryParseExtendedModuleNames(@NotNull final PsiBuilder builder) {
        final boolean[] extendedModuleNameParsed = { false };
        final boolean[] shouldBreak = { false };

        final Runnable parsing = new Runnable() {
            public void run() {
                if (tryParseExtendedModuleName(builder, true)) {
                    extendedModuleNameParsed[0] = true;
                }
                else {
                    shouldBreak[0] = true;
                }
            }
        };

        while (!builder.eof() && !shouldBreak[0]) {
            advanceLexerIfNothingWasParsed(builder, shouldBreak, parsing);
        }

        return extendedModuleNameParsed[0];
    }
}
