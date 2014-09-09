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

import org.kordamp.json.JSON;
import org.custommonkey.xmlunit.XMLTestCase;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestXmlContainingTypeAttribute extends XMLTestCase {

    public void testXmlWithTypeAttribute() throws IOException, SAXException, ParserConfigurationException {
        final String xml = "<data type=\"someType\"><nested type=\"some_other_type\">value</nested></data>";

        final XMLSerializer tested = new XMLSerializer();
        tested.setTypeHintsEnabled(false);
        tested.setTypeHintsCompatibility(false);
        tested.setRootName("data");

        final JSON jsonRepresentation = tested.read(xml);

        final String result = tested.write(jsonRepresentation);

        assertXMLEqual(xml, result);

    }
}
