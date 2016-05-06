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
import org.kordamp.ezmorph.test.ArrayAssertions;
import org.kordamp.json.JSON;
import org.kordamp.json.JSONArray;
import org.kordamp.json.JSONFunction;
import org.kordamp.json.JSONNull;
import org.kordamp.json.JSONObject;

import java.util.Arrays;

/**
 * @author Andres Almiray
 */
public class TestXMLSerializer_writes extends XMLTestCase {
    private XMLSerializer xmlSerializer;

    public TestXMLSerializer_writes(String testName) {
        super(testName);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestXMLSerializer_writes.class);
    }

    public void testWrite_null() throws Exception {
        String expected = "<o null=\"true\"/>";
        String xml = xmlSerializer.write(null);
        assertXMLEqual(expected, xml);
    }

    public void testWriteBooleanArray() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("[true,false]");
        String expected = "<a><e type=\"boolean\">true</e><e type=\"boolean\">false</e></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteEmptyObject() throws Exception {
        JSONObject jsonObject = new JSONObject();
        String expected = "<o/>";
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteFunctionArray() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("[function(a){ return a; }]");
        String expected = "<a><e type=\"function\" params=\"a\"><![CDATA[return a;]]></e></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteFunctionArray_noTypeHintsCompatibility() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("[function(a){ return a; }]");
        xmlSerializer.setTypeHintsCompatibility(false);
        String expected = "<a><e json_type=\"function\" json_params=\"a\"><![CDATA[return a;]]></e></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteJSONArray_collapseProperties() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("duplicated", "json1");
        jsonObject.accumulate("duplicated", "json2");
        jsonObject.getJSONArray("duplicated").setExpandElements(true);
        JSONArray jsonArray = new JSONArray().element(jsonObject);
        String expected = "<a><e class=\"object\"><duplicated type=\"string\">json1</duplicated><duplicated type=\"string\">json2</duplicated></e></a>";
        xmlSerializer.setExpandableProperties(new String[]{"duplicated"});
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
        ArrayAssertions.assertEquals(new String[]{"duplicated"}, xmlSerializer.getExpandableProperties());

        jsonObject.getJSONArray("duplicated").setExpandElements(false);
        jsonArray = new JSONArray().element(jsonObject);
        expected = "<a><e class=\"object\"><duplicated class=\"array\"><e type=\"string\">json1</e><e type=\"string\">json2</e></duplicated></e></a>";
        xmlSerializer.setExpandableProperties(null);
        xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
        ArrayAssertions.assertEquals(new String[0], xmlSerializer.getExpandableProperties());
    }

    public void testWriteJSONNull() throws Exception {
        String expected = "<o null=\"true\"/>";
        String xml = xmlSerializer.write(JSONNull.getInstance());
        assertXMLEqual(expected, xml);
    }

    public void testWriteJSONNull_encoding() throws Exception {
        String expected = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
        String xml = xmlSerializer.write(JSONNull.getInstance(), "ISO-8859-1");
        assertTrue(xml.startsWith(expected));
    }

    public void testWriteJSONObject_collapseProperties() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.element("duplicated", "json1");
        jsonObject.accumulate("duplicated", "json2");
        jsonObject.getJSONArray("duplicated")
            .setExpandElements(true);
        String expected = "<o><duplicated type=\"string\">json1</duplicated><duplicated type=\"string\">json2</duplicated></o>";
        xmlSerializer.setExpandableProperties(new String[]{"duplicated"});
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteMultiBooleanArray() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("[true,false,[true,false]]");
        String expected = "<a><e type=\"boolean\">true</e><e type=\"boolean\">false</e><e class=\"array\"><e type=\"boolean\">true</e><e type=\"boolean\">false</e></e></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteMultiNumberArray() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("[1.1,2,[3,4.4]]");
        String expected = "<a><e type=\"number\">1.1</e><e type=\"number\">2</e><e class=\"array\"><e type=\"number\">3</e><e type=\"number\">4.4</e></e></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteMultiStringArray() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("['1.1','2',['3','4.4']]");
        String expected = "<a><e type=\"string\">1.1</e><e type=\"string\">2</e><e class=\"array\"><e type=\"string\">3</e><e type=\"string\">4.4</e></e></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteNestedNullObject() throws Exception {
        JSONObject jsonObject = JSONObject.fromObject("{\"nested\":null}");
        String expected = "<o><nested class=\"object\" null=\"true\"/></o>";
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteNullObject() throws Exception {
        JSONObject jsonObject = new JSONObject(true);
        String expected = "<o null=\"true\"/>";
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteNullObject_noTypeHintsCompatibility() throws Exception {
        JSONObject jsonObject = new JSONObject(true);
        String expected = "<o json_null=\"true\"/>";
        xmlSerializer.setTypeHintsCompatibility(false);
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteNullObjectArray() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("[null,null]");
        String expected = "<a><e class=\"object\" null=\"true\"/><e class=\"object\" null=\"true\"/></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteNullObjectArray_noTypeHintsCompatibility() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("[null,null]");
        String expected = "<a><e json_class=\"object\" json_null=\"true\"/><e json_class=\"object\" json_null=\"true\"/></a>";
        xmlSerializer.setTypeHintsCompatibility(false);
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteNumberArray() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("[1.1,2]");
        String expected = "<a><e type=\"number\">1.1</e><e type=\"number\">2</e></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteObject() throws Exception {
        JSONObject jsonObject = JSONObject.fromObject("{\"name\":\"json\"}");
        String expected = "<o><name type=\"string\">json</name></o>";
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteObject_full_types() throws Exception {
        JSONObject jsonObject = JSONObject.fromObject("{\"string\":\"json\",\"int\":1,\"bool\":true,\"array\":[1.1,2],\"nested_null\":null,\"nested\":{\"name\":\"json\"},\"func\":function(a){ return a; }}");
        String expected = "<o><string type=\"string\">json</string>" + "<int type=\"number\">1</int>"
            + "<bool type=\"boolean\">true</bool>"
            + "<array class=\"array\"><e type=\"number\">1.1</e><e type=\"number\">2</e></array>"
            + "<nested_null class=\"object\" null=\"true\"/>"
            + "<nested class=\"object\"><name type=\"string\">json</name></nested>"
            + "<func type=\"function\" params=\"a\"><![CDATA[return a;]]></func>" + "</o>";
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteObject_withAttributes() throws Exception {
        JSONObject jsonObject = new JSONObject().element("@name", "json")
            .element("string", "json");
        String expected = "<o name=\"json\"><string type=\"string\">json</string></o>";
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteObject_withNamespacePrefix() throws Exception {
        JSONObject jsonObject = JSONObject.fromObject("{\"ns:name\":\"json\"}");
        String expected = "<o><ns:name type=\"string\">json</ns:name></o>";
        xmlSerializer.setNamespaceLenient(true);
        String xml = xmlSerializer.write(jsonObject);
        assertTrue(xml.trim()
            .endsWith(expected));
    }

    public void testWriteObject_withNamespaces() throws Exception {
        JSONObject jsonObject = JSONObject.fromObject("{\"ns:name\":\"json\"}");
        String expected = "<o xmlns=\"http://json.org\" "
            + "xmlns:ns=\"http://json.org/ns-schema\"><ns:name type=\"string\" >json</ns:name></o>";
        xmlSerializer.setNamespace(null, "http://json.org");
        xmlSerializer.addNamespace("ns", "http://json.org/ns-schema");
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteObject_withNamespaces_element() throws Exception {
        JSONObject jsonObject = JSONObject.fromObject("{\"ns:name\":\"json\"}");
        String expected = "<o><ns:name xmlns=\"http://json.org\" "
            + "xmlns:ns=\"http://json.org/ns-schema\" type=\"string\" >json</ns:name></o>";
        xmlSerializer.setNamespace(null, "http://json.org", "ns:name");
        xmlSerializer.addNamespace("ns", "http://json.org/ns-schema", "ns:name");
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testNamespaces() throws Exception {
        assertTrue(xmlSerializer.getElementNamespace(null).isEmpty());

        assertTrue(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());

        xmlSerializer.addNamespace(null, null, "ns:name");
        assertTrue(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());

        xmlSerializer.addNamespace(null, "http://json.org", null);
        assertFalse(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());
        xmlSerializer.clearNamespaces();

        xmlSerializer.addNamespace(null, "http://json.org", "ns:name");
        assertTrue(xmlSerializer.getRootNamespace().isEmpty());
        assertFalse(xmlSerializer.getElementNamespace("ns:name").isEmpty());
        xmlSerializer.clearNamespaces();

        xmlSerializer.addNamespace("ns", "http://json.org/ns-schema", null);
        assertFalse(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());
        xmlSerializer.clearNamespaces("ns:name");
        assertFalse(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());
        xmlSerializer.clearNamespaces("");
        assertTrue(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());

        xmlSerializer.addNamespace("ns", "http://json.org/ns-schema");
        xmlSerializer.addNamespace("ns", "http://json.org/ns-schema", "ns:name");
        xmlSerializer.removeNamespace("");
        assertFalse(xmlSerializer.getRootNamespace().isEmpty());
        assertFalse(xmlSerializer.getElementNamespace("ns:name").isEmpty());

        xmlSerializer.removeNamespace("ns");
        assertTrue(xmlSerializer.getRootNamespace().isEmpty());
        assertFalse(xmlSerializer.getElementNamespace("ns:name").isEmpty());

        xmlSerializer.addNamespace("ns", "http://json.org/ns-schema");
        xmlSerializer.removeNamespace("ns", null);
        assertTrue(xmlSerializer.getRootNamespace().isEmpty());
        assertFalse(xmlSerializer.getElementNamespace("ns:name").isEmpty());

        xmlSerializer.addNamespace("ns", "http://json.org/ns-schema");
        xmlSerializer.removeNamespace("ns", "ns:name");
        assertFalse(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());

        xmlSerializer.clearNamespaces();

        xmlSerializer.setNamespace(null, null, "ns:name");
        assertTrue(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());

        xmlSerializer.setNamespace(null, "http://json.org", null);
        assertFalse(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());
        xmlSerializer.clearNamespaces();

        xmlSerializer.setNamespace(null, "http://json.org", "ns:name");
        assertTrue(xmlSerializer.getRootNamespace().isEmpty());
        assertFalse(xmlSerializer.getElementNamespace("ns:name").isEmpty());
        xmlSerializer.clearNamespaces();

        xmlSerializer.setNamespace("ns", "http://json.org/ns-schema", null);
        assertFalse(xmlSerializer.getRootNamespace().isEmpty());
        assertTrue(xmlSerializer.getElementNamespace("ns:name").isEmpty());
        xmlSerializer.clearNamespaces("ns:name");

        xmlSerializer.setNamespace("ns", "http://json.org/ns-schema", "ns:name");
        xmlSerializer.setNamespace("ns", "http://json.org/ns-schema", "ns:name");
        xmlSerializer.removeNamespace("");
        assertFalse(xmlSerializer.getRootNamespace().isEmpty());
        assertFalse(xmlSerializer.getElementNamespace("ns:name").isEmpty());
    }

    public void testWriteObject_withText() throws Exception {
        JSONObject jsonObject = new JSONObject().element("#text", "json")
            .element("string", "json");
        String expected = "<o>json<string type=\"string\">json</string></o>";
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteObject_withText_2() throws Exception {
        JSONObject jsonObject = new JSONObject().element("#text", JSONArray.fromObject("['json','json']"))
            .element("string", "json");
        String expected = "<o>jsonjson<string type=\"string\">json</string></o>";
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteObjectArray() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("[{\"name\":\"json\"}]");
        String expected = "<a><e class=\"object\"><name type=\"string\">json</name></e></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteStringArray() throws Exception {
        JSONArray jsonArray = JSONArray.fromObject("['1','2']");
        String expected = "<a><e type=\"string\">1</e><e type=\"string\">2</e></a>";
        String xml = xmlSerializer.write(jsonArray);
        assertXMLEqual(expected, xml);
    }

    public void testWriteWithNamespace() throws Exception {
        JSONObject jsonObject = new JSONObject().element("@xmlns", "http://json.org/json/1.0")
            .element("@xmlns:ns", "http://www.w3.org/2001/XMLSchema-instance")
            .element("ns:string", "json")
            .element("ns:number", "1");
        String expected = "<o xmlns=\"http://json.org/json/1.0\""
            + " xmlns:ns=\"http://www.w3.org/2001/XMLSchema-instance\">"
            + "<ns:number type=\"string\">1</ns:number><ns:string type=\"string\">json</ns:string></o>";
        String xml = xmlSerializer.write(jsonObject);
        assertXMLEqual(expected, xml);
    }

    public void testWriteWithCustomNames_1() throws Exception {
        JSONObject root = new JSONObject()
            .element("items", JSONArray.fromObject(Arrays.asList("one", JSONObject.fromObject("{\"name\":\"json\"}"))));
        String expected = "<root><items class=\"array\"><element type=\"string\">one</element><element class=\"object\"><name type=\"string\">json</name></element></items></root>";
        xmlSerializer.setRootName("root");
        xmlSerializer.setElementName("element");
        String xml = xmlSerializer.write(root);
        assertXMLEqual(expected, xml);

        expected = "<o><items class=\"array\"><e type=\"string\">one</e><e class=\"object\"><name type=\"string\">json</name></e></items></o>";
        xmlSerializer.setRootName("");
        xmlSerializer.setElementName("");
        xml = xmlSerializer.write(root);
        assertXMLEqual(expected, xml);
    }

    public void testWriteWithCustomNames_2() throws Exception {
        JSONObject root = new JSONObject()
            .element("items", JSONArray.fromObject(Arrays.asList("one", JSONObject.fromObject("{\"name\":\"json\"}"))));
        String expected = "<object><items class=\"array\"><element type=\"string\">one</element><element class=\"object\"><name type=\"string\">json</name></element></items></object>";
        xmlSerializer.setObjectName("object");
        xmlSerializer.setElementName("element");
        String xml = xmlSerializer.write(root);
        assertXMLEqual(expected, xml);

        expected = "<o><items class=\"array\"><e type=\"string\">one</e><e class=\"object\"><name type=\"string\">json</name></e></items></o>";
        xmlSerializer.setObjectName("");
        xmlSerializer.setElementName("");
        xml = xmlSerializer.write(root);
        assertXMLEqual(expected, xml);
    }

    public void testWriteWithCustomNames_3() throws Exception {
        JSONArray root = JSONArray.fromObject("['1','2']");
        String expected = "<array><element type=\"string\">1</element><element type=\"string\">2</element></array>";
        xmlSerializer.setArrayName("array");
        xmlSerializer.setElementName("element");
        String xml = xmlSerializer.write(root);
        assertXMLEqual(expected, xml);

        expected = "<a><e type=\"string\">1</e><e type=\"string\">2</e></a>";
        xmlSerializer.setArrayName("");
        xmlSerializer.setElementName("");
        xml = xmlSerializer.write(root);
        assertXMLEqual(expected, xml);
    }

    public void testTypeHintsEnabled() throws Exception {
        JSON json = new JSONObject()
            .element("boolean", true)
            .element("number", 1)
            .element("string", "string")
            .element("fun1", JSONFunction.parse("function() {}"))
            .element("fun2", "function() {}")
            .element("object", JSONObject.fromObject("{\"name\":\"json\"}"))
            .element("array", JSONArray.fromObject("['1','2']"))
            .element("nil", JSONNull.getInstance());

        String expected = "<o><boolean type=\"boolean\">true</boolean><number type=\"number\">1</number><string type=\"string\">string</string>" +
            "<fun1 type=\"function\" params=\"\"><![CDATA[]]></fun1><fun2 type=\"function\" params=\"\"><![CDATA[]]></fun2>" +
            "<object class=\"object\"><name type=\"string\">json</name></object>" +
            "<array class=\"array\"><e type=\"string\">1</e><e type=\"string\">2</e></array><nil class=\"object\" null=\"true\"/></o>";
        String xml = xmlSerializer.write(json);
        assertXMLEqual(expected, xml);

        xmlSerializer.setTypeHintsEnabled(false);
        expected = "<o><boolean>true</boolean><number>1</number><string>string</string>" +
            "<fun1 params=\"\"><![CDATA[]]></fun1><fun2 params=\"\"><![CDATA[]]></fun2>" +
            "<object><name>json</name></object>" +
            "<array><e>1</e><e>2</e></array><nil null=\"true\"/></o>";
        xml = xmlSerializer.write(json);
        assertXMLEqual(expected, xml);
    }

    protected void setUp() throws Exception {
        super.setUp();
        xmlSerializer = new XMLSerializer();
    }
}