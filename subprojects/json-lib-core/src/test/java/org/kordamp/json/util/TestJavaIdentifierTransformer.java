/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kordamp.json.util;

import junit.framework.TestCase;

/**
 * @author Andres Almiray
 */
public class TestJavaIdentifierTransformer extends TestCase {
    public TestJavaIdentifierTransformer(String testName) {
        super(testName);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJavaIdentifierTransformer.class);
    }

    public void testCamelCase() {
        JavaIdentifierTransformer jit = JavaIdentifierTransformer.CAMEL_CASE;
        assertEquals("camelCase", jit.transformToJavaIdentifier("camel case"));
        assertEquals("camelCase", jit.transformToJavaIdentifier("@camel case"));
        assertEquals("$camelCase", jit.transformToJavaIdentifier("$camel case"));
        assertEquals("camelCase", jit.transformToJavaIdentifier("camel@case"));
        assertEquals("camelCase", jit.transformToJavaIdentifier("camel @case"));
        assertEquals("camelCase", jit.transformToJavaIdentifier("camel@@case"));
        assertEquals("camelCase", jit.transformToJavaIdentifier("camel@ @case"));
    }

    public void testUnderscore() {
        JavaIdentifierTransformer jit = JavaIdentifierTransformer.UNDERSCORE;
        assertEquals("under_score", jit.transformToJavaIdentifier("under score"));
        assertEquals("under_score", jit.transformToJavaIdentifier("@under score"));
        assertEquals("$under_score", jit.transformToJavaIdentifier("$under score"));
        assertEquals("under_score", jit.transformToJavaIdentifier("under@score"));
        assertEquals("under_score", jit.transformToJavaIdentifier("under score"));
        assertEquals("under_score", jit.transformToJavaIdentifier("under@@score"));
        assertEquals("under_score", jit.transformToJavaIdentifier("under@ @score"));
        assertEquals("under_score", jit.transformToJavaIdentifier("under score "));
    }

    public void testWhitespace() {
        JavaIdentifierTransformer jit = JavaIdentifierTransformer.WHITESPACE;
        assertEquals("whitespace", jit.transformToJavaIdentifier("white space"));
        assertEquals("whitespace", jit.transformToJavaIdentifier("@white space"));
        assertEquals("$whitespace", jit.transformToJavaIdentifier("$white space"));
        assertEquals("whitespace", jit.transformToJavaIdentifier("white@space"));
        assertEquals("whitespace", jit.transformToJavaIdentifier("white@@space"));
        assertEquals("whitespace", jit.transformToJavaIdentifier("white@ @space"));
        assertEquals("whitespace", jit.transformToJavaIdentifier("white space "));
    }
}