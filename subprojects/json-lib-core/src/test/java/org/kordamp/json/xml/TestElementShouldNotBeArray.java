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
package org.kordamp.json.xml;

import junit.framework.TestCase;
import org.kordamp.json.JSONObject;

public class TestElementShouldNotBeArray extends TestCase {

    public void test_element_should_not_be_mistaken_as_array() {
        final XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setKeepCData(true);
        JSONObject actual = (JSONObject) xmlSerializer.read("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document DOMVersion=\"8.0\" Self=\"d\">" +
            "<TinDocumentDataObject>\n" +
            "<Properties>\n" +
            "<GaijiRefMaps><![CDATA[/////wAAAAAAAAAA]]></GaijiRefMaps>\n" +
            "</Properties>\n" +
            "</TinDocumentDataObject>\n" +
            "</Document>\n");

        final JSONObject expected = JSONObject.fromObject("{@DOMVersion:\"8.0\", @Self:\"d\", TinDocumentDataObject:{" +
            "Properties:{" +
            "GaijiRefMaps:\"<![CDATA[/////wAAAAAAAAAA]]>\"} } }");

        assertEquals(expected, actual);
    }
}
