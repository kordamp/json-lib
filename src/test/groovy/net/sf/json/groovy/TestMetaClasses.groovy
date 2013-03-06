/*
 * Copyright 2002-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sf.json.groovy

import net.sf.json.JSON
import net.sf.json.JSONArray
import net.sf.json.JSONFunction
import net.sf.json.JSONObject
import net.sf.json.test.JSONAssert

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
class TestMetaClasses extends GroovyTestCase {
    protected void setUp() throws Exception {
        GJson.enhanceClasses()
    }

    void testJONObjectGet_withDefaultValue() {
        def json = new JSONObject();
        assertNull json.opt("key")
        json.get("key", "value")
        assertNotNull json.get("key")
        assertEquals "value", json.get("key")
    }

    void testTypeConversion_Map_to_JSONObject() {
        def map = [key: 'value']
        def expected = new JSONObject().element("key", "value")
        def actual = map as JSONObject
        JSONAssert.assertEquals expected, actual
    }

    void testTypeConversion_List_to_JSONArray() {
        def list = [1, "2", true]
        def expected = new JSONArray()
            .element(1)
            .element("2")
            .element(true)
        def actual = list as JSONArray
        JSONAssert.assertEquals expected, actual
    }

    void testTypeConversion_String_to_JSONObject() {
        def expected = new JSONObject().element("key", "value")
        def actual = "{'key':'value'}" as JSONObject
        JSONAssert.assertEquals expected, actual
    }

    void testTypeConversion_String_to_JSONArray() {
        def expected = new JSONArray()
            .element(1)
            .element("2")
            .element(true)
        def actual = "[1,'2',true]" as JSONArray
        JSONAssert.assertEquals expected, actual
    }

    void testTypeConversion_String_to_JSONFunction() {
        def expected = new JSONFunction("return this;")
        def actual = "function(){return this;}" as JSONFunction
        assertEquals expected, actual
    }

    void testTypeConversion_String_to_JSON_object() {
        def expected = new JSONObject().element("key", "value")
        def actual = "{'key':'value'}" as JSON
        JSONAssert.assertEquals expected, actual
    }

    void testTypeConversion_String_to_JSON_array() {
        def expected = new JSONArray()
            .element(1)
            .element("2")
            .element(true)
        def actual = "[1,'2',true]" as JSON
        JSONAssert.assertEquals expected, actual
    }
}