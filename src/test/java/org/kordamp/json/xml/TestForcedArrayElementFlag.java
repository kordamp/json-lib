/*
 * Copyright 2006-2015 the original author or authors.
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

import com.google.common.collect.ImmutableList;
import junit.framework.TestCase;
import org.kordamp.json.JSONObject;
import uk.org.lidalia.slf4jext.Level;
import uk.org.lidalia.slf4jtest.LoggingEvent;
import uk.org.lidalia.slf4jtest.TestLogger;
import uk.org.lidalia.slf4jtest.TestLoggerFactory;

import static java.util.Arrays.asList;


/**
 * @author Michel Racic
 */
public class TestForcedArrayElementFlag extends TestCase {
    static {
        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
    }

    // Test logger which we can use to check if warnings get logged correctly
    TestLogger logger = TestLoggerFactory.getTestLogger(XMLSerializer.class);

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
        TestLoggerFactory.clear();
        logger.setEnabledLevels(Level.WARN);
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
        String expectedWarningMessage = "Child elements [GaijiRefMaps,ForcedArrayElement] of forced array element [Properties] are not from the same type";
        boolean expectedWarningFound = false;
        ImmutableList<LoggingEvent> loggingEvents = logger.getLoggingEvents();
        for (LoggingEvent le : loggingEvents)
            if (le.getLevel() == Level.WARN)
                expectedWarningFound = le.getMessage().equals(expectedWarningMessage);
        assertTrue("Expected warning message has been found to notify the caller that child the elements are not from the same type", expectedWarningFound);
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
