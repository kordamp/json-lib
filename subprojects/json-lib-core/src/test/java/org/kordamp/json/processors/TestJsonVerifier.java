package org.kordamp.json.processors;

import junit.framework.TestCase;
import org.kordamp.json.JSONArray;
import org.kordamp.json.JSONFunction;
import org.kordamp.json.JSONNull;
import org.kordamp.json.JSONObject;
import org.kordamp.json.sample.ObjectJSONStringBean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;

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
