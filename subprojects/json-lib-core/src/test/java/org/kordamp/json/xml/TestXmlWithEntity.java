/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2022 the original author or authors.
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

import org.custommonkey.xmlunit.XMLTestCase;
import org.kordamp.json.JSON;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestXmlWithEntity extends XMLTestCase {
    public void test_convert_xml_with_entity() throws IOException, SAXException, ParserConfigurationException {
        String fixture = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<Description>" +
            "<Thumbnails>" +
            "<image>/&#xA;9j/4AAQSkZJRgABAgEASABIAAD/7QAsUGhvdG9zaG9wIDMuMAA4QklNA+0AAAAAABAASAAAAAEA&#xA;AQBIAAAAAQAB/</image>" +
            "</Thumbnails>" +
            "</Description>";

        XMLSerializer xmlSerializer = new XMLSerializer();
        xmlSerializer.setEscapeLowerChars(true);
        xmlSerializer.setRootName("Description");
        xmlSerializer.setTypeHintsEnabled(false);
        final JSON json = xmlSerializer.read(fixture);

        final String result = xmlSerializer.write(json);

        assertXMLEqual(fixture, result);
    }
}
