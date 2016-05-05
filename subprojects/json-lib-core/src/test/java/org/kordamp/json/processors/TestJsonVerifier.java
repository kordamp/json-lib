/*
 * Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This product includes software developed by Douglas Crockford
 * (http://www.crockford.com) and released under the Apache Software
 * License version 2.0 in 2006.
 */
package org.kordamp.json.processors;

import junit.framework.TestCase;
import org.kordamp.json.JSONArray;
import org.kordamp.json.JSONFunction;
import org.kordamp.json.JSONNull;
import org.kordamp.json.JSONObject;
import org.kordamp.json.sample.ObjectJSONStringBean;

import java.math.BigDecimal;
import java.math.BigInteger;

import static java.util.Collections.emptyList;

/**
 * @author Andres Almiray
 */
public class TestJsonVerifier extends TestCase {
    public void testBoolean() {
        assertTrue(JsonVerifier.isValidJsonValue(true));
    }

    public void testByte() {
        assertTrue(JsonVerifier.isValidJsonValue((byte) 0));
    }

    public void testShort() {
        assertTrue(JsonVerifier.isValidJsonValue((short) 0));
    }

    public void testInteger() {
        assertTrue(JsonVerifier.isValidJsonValue( 0));
    }

    public void testLong() {
        assertTrue(JsonVerifier.isValidJsonValue(0L));
    }

    public void testFloat() {
        assertFalse(JsonVerifier.isValidJsonValue(0f));
    }

    public void testDouble() {
        assertTrue(JsonVerifier.isValidJsonValue(0d));
    }

    public void testString() {
        assertTrue(JsonVerifier.isValidJsonValue(""));
    }

    public void testBigInteger() {
        assertTrue(JsonVerifier.isValidJsonValue(BigInteger.ONE));
    }

    public void testBigDecimal() {
        assertTrue(JsonVerifier.isValidJsonValue(BigDecimal.ONE));
    }

    public void testJSONNull() {
        assertTrue(JsonVerifier.isValidJsonValue(JSONNull.getInstance()));
    }

    public void testJSONObject() {
        assertTrue(JsonVerifier.isValidJsonValue(JSONObject.fromObject(null)));
    }

    public void testJSONArray() {
        assertTrue(JsonVerifier.isValidJsonValue(JSONArray.fromObject(emptyList())));
    }

    public void testJSONFunction() {
        assertTrue(JsonVerifier.isValidJsonValue(JSONFunction.parse("function() {}")));
    }

    public void testJSONString() {
        assertTrue(JsonVerifier.isValidJsonValue(new ObjectJSONStringBean()));
    }
}
