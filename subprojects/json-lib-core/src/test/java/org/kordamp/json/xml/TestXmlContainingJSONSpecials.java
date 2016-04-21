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
package org.kordamp.json.xml;

import org.custommonkey.xmlunit.XMLTestCase;
import org.kordamp.json.JSON;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class TestXmlContainingJSONSpecials extends XMLTestCase {
    public void testXmlWithBracketArray() throws IOException, SAXException, ParserConfigurationException {
        final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<data><e>{}</e><e>{}</e></data>";

        final XMLSerializer tested = new XMLSerializer();
        tested.setParseJsonLiterals(false);
        tested.setTypeHintsEnabled(false);
        tested.setRootName("data");

        final JSON jsonRepresentation = tested.read(xml);
        final String result = tested.write(jsonRepresentation);

        assertXMLEqual(xml, result);

        final String jsonText = jsonRepresentation.toString();
        assertTrue(jsonText.contains("[\"{}\""));
    }

    public void testXmlWithSpaceArray() throws IOException, SAXException, ParserConfigurationException {
        String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<e>\n" +
                "   <command>\n" +
                "      <update>\n" +
                "            <chg>\n" +
                "                  <addr>\n" +
                "                      <street>   </street>" +
                "                      <street>   </street>" +
                "                  </addr>" +
                "            </chg>\n" +
                "      </update>\n" +
                "   </command>\n" +
                "</e>";

        final XMLSerializer tested = new XMLSerializer();
        //tested.setKeepCData(true);
        tested.setTypeHintsEnabled(false);
        tested.setRootName("data");

        final JSON jsonRepresentation = tested.read(xml);
        final String jsonText = jsonRepresentation.toString();
        assertTrue(jsonText.contains("[\"   \""));
    }
}
