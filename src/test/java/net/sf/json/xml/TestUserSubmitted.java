/*
 * Copyright 2002-2007 the original author or authors.
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
import net.sf.json.JSONObject;
import net.sf.json.test.JSONAssert;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class TestUserSubmitted extends TestCase {
   public static void main( String[] args ) {
      junit.textui.TestRunner.run( TestUserSubmitted.class );
   }

   public TestUserSubmitted( String name ) {
      super( name );
   }

   public void testReadFromXMLToJSON_1735732() throws Exception {
      // bug 1735732

      XMLSerializer xmlSerializer = new XMLSerializer();

      JSONObject actual = (JSONObject) xmlSerializer.readFromFile( "net/sf/json/xml/1735732.xml" );
      JSONObject expected = new JSONObject().element( "@xmlns:ns2",
            "http://schemas.foo.com/HelloWorld" )
            .element( "item", new JSONObject().element( "age", "2 5" )
                  .element( "name", "emp1" ) )
            .accumulate( "item", new JSONObject().element( "age", "2" )
                  .element( "name", "emp2" ) );
      JSONAssert.assertEquals( expected, actual );
   }

   public void testReadFromXMLToJSON_1739066() throws Exception {
      // bug 1739066

      XMLSerializer xmlSerializer = new XMLSerializer();
      xmlSerializer.setTrimSpaces( true );

      JSONObject actual = (JSONObject) xmlSerializer.readFromFile( "net/sf/json/xml/1739066.xml" );
      JSONObject expected = new JSONObject().element( "Address", "http://localhost:0/te stToString" )
            .element( "@xmlns", "http://www.w3.org/2005/08/addressing" )
            .element(
                  "Metadata",
                  new JSONObject().element( "@xmlns:wsa-wsdl",
                        "http://www.w3.org/2006/01/wsdl-instance" )
                        .element( "@wsa-wsdl:wsdlLocation", "file:///b.wsdl" )
                        .element(
                              "ns3:InterfaceName",
                              new JSONObject().element( "@xmlns:tns", "http://com.iona.cxf/GreetMe" )
                                    .element( "@xmlns:ns3",
                                          "http://www.w3.org/2005/02/addressing/wsdl" )
                                    .element( "#text", "tns:GreetMePortType" ) )
                        .element(
                              "ns3:ServiceName",
                              new JSONObject().element( "@EndpointName", "GreetMePort" )
                                    .element( "@xmlns:tns", "http://com.iona.cxf/GreetMe" )
                                    .element( "@xmlns:ns3",
                                          "http://www.w3.org/2005/02/addressing/wsdl" )
                                    .element( "#text", "tns:GreetMeService" ) ) );
      JSONAssert.assertEquals( expected, actual );
   }
}