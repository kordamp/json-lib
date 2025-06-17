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
import org.kordamp.json.JSONArray;
import org.kordamp.json.JSONException;

import java.util.Collections;

/**
 * @author Andres Almiray
 */
public class TestJSONUtils extends TestCase {
    public TestJSONUtils(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONUtils.class);
    }

    public void testDoubleToString_infinite() {
        assertEquals("null", JSONUtils.doubleToString(Double.POSITIVE_INFINITY));
    }

    public void testDoubleToString_nan() {
        assertEquals("null", JSONUtils.doubleToString(Double.NaN));
    }

    public void testDoubleToString_trailingZeros() {
        assertEquals("200.0", JSONUtils.doubleToString(200.00000));
    }

    public void testGetFunctionParams() {
        assertEquals("", JSONUtils.getFunctionParams("function()"));
        assertEquals("a", JSONUtils.getFunctionParams("function(a)"));
        assertEquals("a,b", JSONUtils.getFunctionParams("function(a,b)"));
        assertEquals("", JSONUtils.getFunctionParams("notAFunction"));
    }

    public void testIsArray() {
        assertTrue(JSONUtils.isArray(new Object[0]));
        assertTrue(JSONUtils.isArray(new boolean[0]));
        assertTrue(JSONUtils.isArray(new byte[0]));
        assertTrue(JSONUtils.isArray(new char[0]));
        assertTrue(JSONUtils.isArray(new short[0]));
        assertTrue(JSONUtils.isArray(new int[0]));
        assertTrue(JSONUtils.isArray(new long[0]));
        assertTrue(JSONUtils.isArray(new float[0]));
        assertTrue(JSONUtils.isArray(new double[0]));

        // two dimensions
        assertTrue(JSONUtils.isArray(new Object[0][0]));
        assertTrue(JSONUtils.isArray(new boolean[0][0]));
        assertTrue(JSONUtils.isArray(new byte[0][0]));
        assertTrue(JSONUtils.isArray(new char[0][0]));
        assertTrue(JSONUtils.isArray(new short[0][0]));
        assertTrue(JSONUtils.isArray(new int[0][0]));
        assertTrue(JSONUtils.isArray(new long[0][0]));
        assertTrue(JSONUtils.isArray(new float[0][0]));
        assertTrue(JSONUtils.isArray(new double[0][0]));

        // collections
        assertTrue(JSONUtils.isArray(Collections.EMPTY_SET));
        assertTrue(JSONUtils.isArray(Collections.EMPTY_LIST));

        // jsonArray
        assertTrue(JSONUtils.isArray(new JSONArray()));
    }

    public void testIsFunction() {
        assertTrue(JSONUtils.isFunction("function(){ return a; }"));
        assertTrue(JSONUtils.isFunction("function (){ return a; }"));
        assertTrue(JSONUtils.isFunction("function() { return a; }"));
        assertTrue(JSONUtils.isFunction("function () { return a; }"));
        assertTrue(JSONUtils.isFunction("function(a){ return a; }"));
    }

    public void testNumberToString_null() {
        try {
            JSONUtils.numberToString(null);
            fail("Should have thrown a JSONException");
        } catch (JSONException expected) {
            // ok
        }
    }

    public void testNumberToString_double() {
        assertEquals("1.0", JSONUtils.numberToString(1.0d));
    }

    public void testNumberToString_float() {
        assertEquals("1.0", JSONUtils.numberToString(1.0f));
    }

    public void testQuote_emptyString() {
        assertEquals("\"\"", JSONUtils.quote(""));
    }

    public void testQuote_escapeChars() {
        assertEquals("\"\\b\\t\\n\\r\\f\"", JSONUtils.quote("\b\t\n\r\f"));
    }

    public void testQuote_nullString() {
        assertEquals("\"\"", JSONUtils.quote(null));
    }

    public void testStripQuotes_singleChar_doubleeQuote() {
        String quoted = "\"";
        String actual = JSONUtils.stripQuotes(quoted);
        assertEquals(quoted, actual);
    }

    public void testStripQuotes_singleChar_singleQuote() {
        String quoted = "'";
        String actual = JSONUtils.stripQuotes(quoted);
        assertEquals(quoted, actual);
    }

    public void testStripQuotes_twoChars_doubleeQuote() {
        String quoted = "\"\"";
        String actual = JSONUtils.stripQuotes(quoted);
        assertEquals("", actual);
    }

    public void testStripQuotes_twoChars_singleQuote() {
        String quoted = "''";
        String actual = JSONUtils.stripQuotes(quoted);
        assertEquals("", actual);
    }

    public void testValidity_infiniteDouble() {
        try {
            JSONUtils.testValidity(new Double(Double.POSITIVE_INFINITY));
            fail("Should have thrown a JSONException");
        } catch (JSONException expected) {
            // ok
        }
    }

    public void testValidity_infiniteFloat() {
        try {
            JSONUtils.testValidity(new Float(Float.POSITIVE_INFINITY));
            fail("Should have thrown a JSONException");
        } catch (JSONException expected) {
            // ok
        }
    }

    public void testValidity_nanDouble() {
        try {
            JSONUtils.testValidity(new Double(Double.NaN));
            fail("Should have thrown a JSONException");
        } catch (JSONException expected) {
            // ok
        }
    }

    public void testValidity_nanFloat() {
        try {
            JSONUtils.testValidity(new Float(Float.NaN));
            fail("Should have thrown a JSONException");
        } catch (JSONException expected) {
            // ok
        }
    }
}