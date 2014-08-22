/*
 * Copyright 2006-2014 the original author or authors.
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
 */
package org.kordamp.json.xml;

import junit.framework.TestCase;
import org.kordamp.json.JSONObject;

import static java.util.Arrays.asList;


/**
 * @author Michel Racic
 */
public class TestForcedArrayElementFlag extends TestCase {
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    }

    /**
     * Should get an array without the list in this case.
     * With the list it needs to have the same behaviour.
     */
    public void test_same_elements_should_be_forced_array() {
        final XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setKeepCData(true);
        JSONObject actual = (JSONObject) xmlSerializer.read("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document DOMVersion=\"8.0\" Self=\"d\">" +
            "<TinDocumentDataObject>\n" +
            "<Properties>\n" +
            "<GaijiRefMaps><![CDATA[/////wAAAAAAAAAA]]></GaijiRefMaps>\n" +
            "<GaijiRefMaps><![CDATA[/////wBBBBBBBBBB]]></GaijiRefMaps>\n" +
            "</Properties>\n" +
            "</TinDocumentDataObject>\n" +
            "</Document>\n");

        String expectedJsonString = "{@DOMVersion:\"8.0\", @Self:\"d\", TinDocumentDataObject:{" +
            "Properties:[\"/////wAAAAAAAAAA\",\"/////wBBBBBBBBBB\"]}}";
        final JSONObject expected = JSONObject.fromObject(expectedJsonString);

        assertEquals(expected, actual);
    }

    public void test_different_elements_should_be_forced_array_with_log_warning() {
        final XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setKeepCData(true);
        xmlSerializer.setForcedArrayElements(asList("Properties"));
        JSONObject actual = (JSONObject) xmlSerializer.read("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document DOMVersion=\"8.0\" Self=\"d\">" +
            "<TinDocumentDataObject>\n" +
            "<Properties>\n" +
            "<GaijiRefMaps><![CDATA[/////wAAAAAAAAAA]]></GaijiRefMaps>\n" +
            "<ForcedArrayElement><![CDATA[/////wBBBBBBBBBB]]></ForcedArrayElement>\n" +
            "</Properties>\n" +
            "</TinDocumentDataObject>\n" +
            "</Document>\n");

        final JSONObject expected = JSONObject.fromObject("{@DOMVersion:\"8.0\", @Self:\"d\", TinDocumentDataObject:{" +
            "Properties:[\"/////wAAAAAAAAAA\",\"/////wBBBBBBBBBB\"] } }");

        assertEquals(expected, actual);

        // Check if warning appears in log
        //TODO Check log, find how to do it?
    }

    public void test_single_element_should_be_forced_array() {
        final XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setKeepCData(true);
        xmlSerializer.setForcedArrayElements(asList("Properties"));
        JSONObject actual = (JSONObject) xmlSerializer.read("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document DOMVersion=\"8.0\" Self=\"d\">" +
            "<TinDocumentDataObject>\n" +
            "<Properties>\n" +
            "<GaijiRefMaps><![CDATA[/////wAAAAAAAAAA]]></GaijiRefMaps>\n" +
            "</Properties>\n" +
            "</TinDocumentDataObject>\n" +
            "</Document>\n");

        final JSONObject expected = JSONObject.fromObject("{@DOMVersion:\"8.0\", @Self:\"d\", TinDocumentDataObject:{" +
            "Properties:[\"/////wAAAAAAAAAA\"] } }");

        assertEquals(expected, actual);
    }

    public void test_no_child_element_should_be_forced_empty_array() {
        final XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setKeepCData(true);
        xmlSerializer.setForcedArrayElements(asList("Properties"));
        JSONObject actual = (JSONObject) xmlSerializer.read("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document DOMVersion=\"8.0\" Self=\"d\">" +
            "<TinDocumentDataObject>\n" +
            "<Properties>\n" +
            "</Properties>\n" +
            "</TinDocumentDataObject>\n" +
            "</Document>\n");

        final JSONObject expected = JSONObject.fromObject("{@DOMVersion:\"8.0\", @Self:\"d\", TinDocumentDataObject:{" +
            "Properties:[] } }");

        assertEquals(expected, actual);
    }

    public void test_single_empty_child_element_should_be_forced_empty_array() {
        final XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setKeepCData(true);
        xmlSerializer.setForcedArrayElements(asList("Properties"));
        JSONObject actual = (JSONObject) xmlSerializer.read("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document DOMVersion=\"8.0\" Self=\"d\">" +
            "<TinDocumentDataObject>\n" +
            "<Properties>\n" +
            "<GaijiRefMaps></GaijiRefMaps>\n" +
            "</Properties>\n" +
            "</TinDocumentDataObject>\n" +
            "</Document>\n");

        final JSONObject expected = JSONObject.fromObject("{@DOMVersion:\"8.0\", @Self:\"d\", TinDocumentDataObject:{" +
            "Properties:[[]] } }");

        assertEquals(expected, actual);
    }

    public void test_single_terminating_empty_child_element_should_be_forced_empty_array() {
        final XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setKeepCData(true);
        xmlSerializer.setForcedArrayElements(asList("Properties"));
        JSONObject actual = (JSONObject) xmlSerializer.read("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
            "<Document DOMVersion=\"8.0\" Self=\"d\">" +
            "<TinDocumentDataObject>\n" +
            "<Properties>\n" +
            "<GaijiRefMaps />\n" +
            "</Properties>\n" +
            "</TinDocumentDataObject>\n" +
            "</Document>\n");

        final JSONObject expected = JSONObject.fromObject("{@DOMVersion:\"8.0\", @Self:\"d\", TinDocumentDataObject:{" +
            "Properties:[[]] } }");

        assertEquals(expected, actual);
    }
}
