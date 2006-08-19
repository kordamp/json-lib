/*
 * Copyright 2002-2006 the original author or authors.
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

package net.sf.json.xml;

import junit.framework.TestCase;
import net.sf.json.Assertions;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class TestXMLSerializer_reads extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestXMLSerializer_reads.class );
   }

   public TestXMLSerializer_reads( String testName )
   {
      super( testName );
   }

   public void testNullObjectArray()
   {
      String xml = "<a><e class=\"object\" null=\"true\"/><e class=\"object\" null=\"true\"/></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[null,null]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadBooleanArray_withDefaultType()
   {
      String xml = "<a type=\"boolean\"><e>true</e><e>false</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[true,false]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadBooleanArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"boolean\">true</e><e type=\"boolean\">false</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[true,false]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadEmptyObject()
   {
      String xml = "<o/>";
      JSONObject xmlObject = XMLSerializer.readObject( xml );
      assertTrue( xmlObject.isEmpty() );
   }

   public void testReadFloatArray_withDefaultType()
   {
      String xml = "<a type=\"float\"><e>1.1</e><e>2.2</e><e>3.3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1.1,2.2,3.3]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadFloatArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"float\">1.1</e><e type=\"float\">2.2</e><e type=\"float\">3.3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1.1,2.2,3.3]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadFunctionArray_withDefaultType()
   {
      String xml = "<a type=\"function\"><e params=\"a\"><![CDATA[return a;]]></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[function(a){ return a; }]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadFunctionArray_withoutDefaultType()
   {
      String xml = "<a><e params=\"a\" type=\"function\"><![CDATA[return a;]]></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[function(a){ return a; }]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadFunctionObject()
   {
      String xml = "<o><func params=\"a\" ><![CDATA[return a;]]></func></o>";
      JSONObject xmlObject = XMLSerializer.readObject( xml );
      JSONObject expected = new JSONObject( "{func:function(a){ return a; }}" );
      assertEquals( expected.toString(), xmlObject.toString() );
   }

   public void testReadIntegerArray_withDefaultType()
   {
      String xml = "<a type=\"integer\"><e>1</e><e>2</e><e>3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1,2,3]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadIntegerArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"integer\">1</e><e type=\"integer\">2</e><e type=\"integer\">3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1,2,3]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMixedArray_withDefaultType()
   {
      String xml = "<a type=\"string\"><e type=\"boolean\">true</e><e type=\"number\">2.2</e><e>3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[true,2.2,\"3\"]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMixedArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"boolean\">true</e><e type=\"number\">2.2</e><e type=\"string\">3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[true,2.2,\"3\"]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiBooleanArray_withDefaultType()
   {
      String xml = "<a type=\"boolean\"><e>true</e><e>false</e><e class=\"array\"><e>false</e><e>true</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[true,false,[false,true]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiBooleanArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"boolean\">true</e><e type=\"boolean\">false</e><e class=\"array\"><e type=\"boolean\">false</e><e type=\"boolean\">true</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[true,false,[false,true]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiFloatArray_withDefaultType()
   {
      String xml = "<a type=\"float\"><e>1.1</e><e>2.2</e><e>3.3</e><e class=\"array\"><e>1.1</e><e>2.2</e><e>3.3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1.1,2.2,3.3,[1.1,2.2,3.3]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiFloatArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"float\">1.1</e><e type=\"float\">2.2</e><e type=\"float\">3.3</e><e class=\"array\"><e type=\"float\">1.1</e><e type=\"float\">2.2</e><e type=\"float\">3.3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1.1,2.2,3.3,[1.1,2.2,3.3]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiIntegerArray_withDefaultType()
   {
      String xml = "<a type=\"integer\"><e>1</e><e>2</e><e>3</e><e class=\"array\"><e>1</e><e>2</e><e>3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1,2,3,[1,2,3]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiIntegerArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"integer\">1</e><e type=\"integer\">2</e><e type=\"integer\">3</e><e class=\"array\"><e type=\"integer\">1</e><e type=\"integer\">2</e><e type=\"integer\">3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1,2,3,[1,2,3]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiMixedArray_withDefaultType()
   {
      String xml = "<a type=\"string\"><e type=\"boolean\">true</e><e type=\"number\">2.2</e><e>3</e><e class=\"array\"><e type=\"boolean\">true</e><e type=\"number\">2.2</e><e>3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[true,2.2,\"3\",[true,2.2,\"3\"]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiMixedArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"boolean\">true</e><e type=\"number\">2.2</e><e type=\"string\">3</e><e class=\"array\"><e type=\"boolean\">true</e><e type=\"number\">2.2</e><e type=\"string\">3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[true,2.2,\"3\",[true,2.2,\"3\"]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiNumberArray_withDefaultType()
   {
      String xml = "<a type=\"number\"><e>1.1</e><e>2.2</e><e>3</e><e class=\"array\"><e>1.1</e><e>2.2</e><e>3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1.1,2.2,3,[1.1,2.2,3]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiNumberArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"number\">1.1</e><e type=\"number\">2.2</e><e type=\"number\">3</e><e class=\"array\"><e type=\"number\">1.1</e><e type=\"number\">2.2</e><e type=\"number\">3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1.1,2.2,3,[1.1,2.2,3]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiStringArray_withDefaultType()
   {
      String xml = "<a type=\"string\"><e>1.1</e><e>2.2</e><e>3</e><e class=\"array\"><e>1.1</e><e>2.2</e><e>3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[\"1.1\",\"2.2\",\"3\",[\"1.1\",\"2.2\",\"3\"]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadMultiStringArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"string\">1.1</e><e type=\"string\">2.2</e><e type=\"string\">3</e><e class=\"array\"><e type=\"string\">1.1</e><e type=\"string\">2.2</e><e type=\"string\">3</e></e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[\"1.1\",\"2.2\",\"3\",[\"1.1\",\"2.2\",\"3\"]]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadNestedObject()
   {
      String xml = "<o><name>json</name><nested class=\"object\"><id type=\"number\">1</id></nested></o>";
      JSONObject xmlObject = XMLSerializer.readObject( xml );
      JSONObject expected = JSONObject.fromString( "{name:\"json\",nested:{id:1}}" );
      assertEquals( expected.get( "name" ), xmlObject.get( "name" ) );
      assertEquals( expected.get( "nested" )
            .toString(), xmlObject.get( "nested" )
            .toString() );
   }

   public void testReadNullObject()
   {
      String xml = "<o null=\"true\"/>";
      JSONObject xmlObject = XMLSerializer.readObject( xml );
      assertTrue( xmlObject.isNullObject() );
   }

   public void testReadNumberArray_withDefaultType()
   {
      String xml = "<a type=\"number\"><e>1.1</e><e>2.2</e><e>3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1.1,2.2,3]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadNumberArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"number\">1.1</e><e type=\"number\">2.2</e><e type=\"number\">3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[1.1,2.2,3]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadObjectFullTypes()
   {
      String xml = "<o><int type=\"integer\">1</int>" + "<decimal type=\"float\">2.0</decimal>"
            + "<number type=\"number\">3.1416</number>" + "<bool type=\"boolean\">true</bool>"
            + "<string>json</string>"
            + "<func type=\"function\" params=\"a\" ><![CDATA[return a;]]></func></o>";
      JSONObject xmlObject = XMLSerializer.readObject( xml );
      JSONObject expected = new JSONObject( "{func:function(a){ return a; }}" );
      expected.put( "int", new Integer( 1 ) );
      expected.put( "decimal", new Double( 2.0 ) );
      expected.put( "number", new Double( 3.1416 ) );
      expected.put( "bool", Boolean.TRUE );
      expected.put( "string", "json" );
      Assertions.assertEquals( expected, xmlObject );
   }

   public void testReadSimpleObject_withDefaultType()
   {
      String xml = "<o type=\"number\"><bool type=\"boolean\">true</bool><int>1</int><double>2.2</double><string type=\"string\">json</string><numbers class=\"array\"><e>4.44</e><e>5</e></numbers></o>";
      JSONObject xmlObject = XMLSerializer.readObject( xml );
      JSONObject expected = JSONObject.fromString( "{bool:true,int:1,double:2.2,string:'json',numbers:[4.44,5]}" );
      assertEquals( expected.get( "bool" ), xmlObject.get( "bool" ) );
      assertEquals( expected.get( "int" ), xmlObject.get( "int" ) );
      assertEquals( expected.get( "double" ), xmlObject.get( "double" ) );
      assertEquals( expected.get( "string" ), xmlObject.get( "string" ) );
      assertEquals( expected.get( "numbers" )
            .toString(), xmlObject.get( "numbers" )
            .toString() );
   }

   public void testReadStringArray_withDefaultType()
   {
      String xml = "<a type=\"string\"><e>1.1</e><e>2.2</e><e>3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[\"1.1\",\"2.2\",\"3\"]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }

   public void testReadStringArray_withoutDefaultType()
   {
      String xml = "<a><e type=\"string\">1.1</e><e type=\"string\">2.2</e><e type=\"string\">3</e></a>";
      JSONArray xmlArray = XMLSerializer.readArray( xml );
      JSONArray expected = new JSONArray( "[\"1.1\",\"2.2\",\"3\"]" );
      assertEquals( expected.toString(), xmlArray.toString() );
   }
}