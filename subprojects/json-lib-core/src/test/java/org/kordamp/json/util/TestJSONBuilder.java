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
package org.kordamp.json.util;

import junit.framework.TestCase;
import org.kordamp.json.JSONFunction;
import org.kordamp.json.JSONObject;

import java.io.StringWriter;

/**
 * @author Andres Almiray
 */
public class TestJSONBuilder extends TestCase {
    public TestJSONBuilder(String testName) {
        super(testName);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONBuilder.class);
    }

    public void testCreateArray() {
        StringWriter w = new StringWriter();
        new JSONBuilder(w).array()
            .value(true)
            .value(1.1d)
            .value(2L)
            .value("text")
            .endArray();
        assertEquals("[true,1.1,2,\"text\"]", w.toString());
    }

    public void testCreateEmptyArray() {
        StringWriter w = new StringWriter();
        new JSONBuilder(w).array()
            .endArray();
        assertEquals("[]", w.toString());
    }

    public void testCreateEmptyArrayWithNullObjects() {
        StringWriter w = new StringWriter();
        new JSONBuilder(w).array()
            .value(null)
            .value(null)
            .endArray();
        assertEquals("[null,null]", w.toString());
    }

    public void testCreateEmptyObject() {
        StringWriter w = new StringWriter();
        new JSONBuilder(w).object()
            .endObject();
        assertEquals("{}", w.toString());
    }

    public void testCreateFunctionArray() {
        StringWriter w = new StringWriter();
        new JSONBuilder(w).array()
            .value(new JSONFunction("var a = 1;"))
            .value(new JSONFunction("var b = 2;"))
            .endArray();
        assertEquals("[function(){ var a = 1; },function(){ var b = 2; }]", w.toString());
    }

    public void testCreateSimpleObject() {
        StringWriter w = new StringWriter();
        new JSONBuilder(w).object()
            .key("bool")
            .value(true)
            .key("numDouble")
            .value(1.1d)
            .key("numInt")
            .value(2)
            .key("text")
            .value("text")
            .key("func")
            .value(new JSONFunction("var a = 1;"))
            .endObject();
        JSONObject jsonObj = JSONObject.fromObject(w.toString());
        assertEquals(Boolean.TRUE, jsonObj.get("bool"));
        assertEquals(new Double(1.1d), jsonObj.get("numDouble"));
        assertEquals(new Long(2).longValue(), ((Number) jsonObj.get("numInt")).longValue());
        assertEquals("text", jsonObj.get("text"));
        assertTrue(JSONUtils.isFunction(jsonObj.get("func")));
        assertEquals("function(){ var a = 1; }", jsonObj.get("func")
            .toString());
    }
}