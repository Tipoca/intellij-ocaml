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

package ocaml.lang.processing.parser.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import ocaml.lang.processing.parser.ast.element.OCamlElementTypes;
import ocaml.lang.processing.parser.psi.element.OCamlUnknownElement;
import org.jetbrains.annotations.NotNull;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Maxim.Manuylov
 *         Date: 23.03.2009
 */
@Test
public class IntegrityTest extends Assert {
    public void testFactoryCreatesAllElementsProperly() throws IllegalAccessException {
        processElementTypes(new ElementTypeProcessor() {
            public void process(@NotNull final IElementType type, @NotNull final OCamlElement element) {
                if (element instanceof OCamlUnknownElement) {
                    fail("Type \"" + type.toString() + "\" was not created by factory.");
                }
            }
        });
    }

    public void testVisitorHasCorrectMethodsSet() throws IllegalAccessException {
        final Set<String> expectedVisitorMethodNames = new HashSet<String>();

        processElementTypes(new ElementTypeProcessor() {
            public void process(@NotNull final IElementType type, @NotNull final OCamlElement element) {
                final String name = element.getClass().getSimpleName();
                expectedVisitorMethodNames.add(name.substring("OCaml".length(), name.length() - "Impl".length()));
            }
        });

        expectedVisitorMethodNames.add("UnknownElement");

        final Method[] methods = OCamlElementVisitor.class.getDeclaredMethods();

        final Set<String> actualVisitorMethodNames = new HashSet<String>();

        for (final Method method : methods) {
            if (method.getName().startsWith("visit")) {
                actualVisitorMethodNames.add(method.getName().substring("visit".length()));
            }
        }

        final Set<String> expectedVisitorMethodNamesCopy = new HashSet<String>(expectedVisitorMethodNames);
        final Set<String> actualVisitorMethodNamesCopy = new HashSet<String>(actualVisitorMethodNames);

        expectedVisitorMethodNames.removeAll(actualVisitorMethodNamesCopy);
        actualVisitorMethodNames.removeAll(expectedVisitorMethodNamesCopy);

        if (expectedVisitorMethodNames.size() != 0 || actualVisitorMethodNames.size() != 0) {
            System.out.println("extra expected:");
    
            for (final String expectedVisitorMethodName : expectedVisitorMethodNames) {
                System.out.println(expectedVisitorMethodName);
            }

            System.out.println();

            System.out.println("extra actual:");

            for (final String actualVisitorMethodName : actualVisitorMethodNames) {
                System.out.println(actualVisitorMethodName);
            }

            System.out.println();

            fail();
        }
    }

    public void testVisitorMethodsHasCorrectParameters() throws IllegalAccessException, ClassNotFoundException {
        final String elementPackage = "ocaml.lang.processing.parser.psi.element.";
        final String elementImplPackage = "ocaml.lang.processing.parser.psi.element.impl.";

        final Method[] methods = OCamlElementVisitor.class.getDeclaredMethods();

        for (final Method method : methods) {
            final Class<?>[] parameterTypes = method.getParameterTypes();

            assertEquals(1, parameterTypes.length);
            final String name = parameterTypes[0].getSimpleName();
            assertTrue(name.startsWith("OCaml"), method.getName() + ", " + name);
            assertEquals(method.getName().substring("visit".length()), name.substring("OCaml".length(), name.length()));
            final Class<?> clazz = Class.forName(elementImplPackage + name + "Impl");
            if (!Modifier.isAbstract(clazz.getModifiers())) {
                Arrays.asList(clazz.getInterfaces()).contains(Class.forName(elementPackage + name));
            }
        }
    }

    private void processElementTypes(@NotNull final ElementTypeProcessor processor) throws IllegalAccessException {
        final Field[] fields = OCamlElementTypes.class.getFields();

        final TokenSet files = TokenSet.create(OCamlElementTypes.ML_FILE, OCamlElementTypes.MLI_FILE);

        for (final Field field : fields) {
            final IElementType type = (IElementType) field.get(null);

            if (files.contains(type)) continue;

            processor.process(type, OCamlElementFactory.INSTANCE.createElement(new MockASTNode(type)));
        }
    }

    private static interface ElementTypeProcessor {
        void process(@NotNull final IElementType type, @NotNull final OCamlElement element);
    }
}
