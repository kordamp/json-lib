/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2019 the original author or authors.
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
package org.kordamp.json.bugs;

import junit.framework.TestCase;
import org.kordamp.json.Assertions;
import org.kordamp.json.JSONArray;
import org.kordamp.json.JSONNull;
import org.kordamp.json.JSONObject;

public class TestNullString extends TestCase {
    public void test_object_from_null_string() {
        JSONObject object = JSONObject.fromObject("{key:\"null\"}");
        Assertions.assertEquals("{\"key\":\"null\"}", object.toString());
        Assertions.assertTrue(object.get("key") instanceof String);

        object = JSONObject.fromObject("{key:null}");
        Assertions.assertEquals("{\"key\":null}", object.toString());
        Assertions.assertTrue(object.get("key") instanceof JSONNull);
    }

    public void test_object_element_null_string() {
        JSONObject object = new JSONObject();
        object.element("key", "null");
        Assertions.assertTrue(object.get("key") instanceof String);
    }

    public void test_array_from_null_string() {
        JSONArray array = JSONArray.fromObject("[\"null\"]");
        Assertions.assertEquals("[\"null\"]", array.toString());
        Assertions.assertTrue(array.get(0) instanceof String);

        array = JSONArray.fromObject("[null]");
        Assertions.assertEquals("[null]", array.toString());
        Assertions.assertTrue(array.get(0) instanceof JSONNull);
    }
}
