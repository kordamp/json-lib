package org.kordamp.json.xml;

import org.custommonkey.xmlunit.XMLTestCase;
import org.kordamp.json.JSON;
import org.kordamp.json.JSONArray;
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
