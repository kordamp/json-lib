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
import org.kordamp.json.JSONException;

/**
 * @author Andres Almiray
 */
public class TestJSONTokener extends TestCase {
    public TestJSONTokener(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONTokener.class);
    }

    public void testDehexchar() {
        assertEquals(0, JSONTokener.dehexchar('0'));
        assertEquals(1, JSONTokener.dehexchar('1'));
        assertEquals(2, JSONTokener.dehexchar('2'));
        assertEquals(3, JSONTokener.dehexchar('3'));
        assertEquals(4, JSONTokener.dehexchar('4'));
        assertEquals(5, JSONTokener.dehexchar('5'));
        assertEquals(6, JSONTokener.dehexchar('6'));
        assertEquals(7, JSONTokener.dehexchar('7'));
        assertEquals(8, JSONTokener.dehexchar('8'));
        assertEquals(9, JSONTokener.dehexchar('9'));

        assertEquals(10, JSONTokener.dehexchar('a'));
        assertEquals(10, JSONTokener.dehexchar('A'));
        assertEquals(11, JSONTokener.dehexchar('b'));
        assertEquals(11, JSONTokener.dehexchar('B'));
        assertEquals(12, JSONTokener.dehexchar('c'));
        assertEquals(12, JSONTokener.dehexchar('C'));
        assertEquals(13, JSONTokener.dehexchar('d'));
        assertEquals(13, JSONTokener.dehexchar('D'));
        assertEquals(14, JSONTokener.dehexchar('e'));
        assertEquals(14, JSONTokener.dehexchar('E'));
        assertEquals(15, JSONTokener.dehexchar('f'));
        assertEquals(15, JSONTokener.dehexchar('F'));
    }

    public void testLength() {
        assertEquals(0, new JSONTokener(null).length());
        assertEquals(0, new JSONTokener("").length());
        assertEquals(2, new JSONTokener("[]").length());
    }

    public void testNextChar() {
        JSONTokener tok = new JSONTokener("abc");
        assertEquals('a', tok.next('a'));
        try {
            assertEquals('e', tok.next('e'));
            fail("Expectd a JSONException");
        } catch (JSONException expected) {
            // ok
        }
    }

    public void testStartsWith() {
        assertFalse(new JSONTokener("").startsWith("null"));
        assertFalse(new JSONTokener("n").startsWith("null"));
        assertFalse(new JSONTokener("nu").startsWith("null"));
        assertFalse(new JSONTokener("nul").startsWith("null"));
        assertTrue(new JSONTokener("null").startsWith("null"));
        assertTrue(new JSONTokener("nulll").startsWith("null"));
        assertFalse(new JSONTokener("nn").startsWith("null"));
        assertFalse(new JSONTokener("nnu").startsWith("null"));
        assertFalse(new JSONTokener("nnul").startsWith("null"));
        assertFalse(new JSONTokener("nnull").startsWith("null"));
        assertFalse(new JSONTokener("nnulll").startsWith("null"));
    }

    public void testReset() {
        JSONTokener tok = new JSONTokener("abc");
        tok.next();
        tok.next();
        assertEquals('c', tok.next());
        tok.reset();
        assertEquals('a', tok.next());
    }
}