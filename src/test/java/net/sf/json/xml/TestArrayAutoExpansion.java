/*
 * Copyright 2002-2009 the original author or authors.
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

import net.sf.json.JSON;
import org.custommonkey.xmlunit.XMLTestCase;


/**
 * @author Tobias Sodergren <tobias.sodergren@infomaker.se>
 */
public class TestArrayAutoExpansion extends XMLTestCase {

   private static final String FIXTURE = "<Document DOMVersion=\"8.0\" Self=\"d\">" +
         "<x:xmpmeta xmlns:x=\"adobe:ns:meta/\" x:xmptk=\"Adobe XMP Core 5.3-c011 66.145661, 2012/02/06-14:56:27\">" +
         "<rdf:RDF xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\">" +
         "<rdf:Description rdf:about=\"\" xmlns:dc=\"http://purl.org/dc/elements/1.1/\">" +
         "<dc:format>application/x-indesign</dc:format>" +
         "</rdf:Description>" +
         "<rdf:Description rdf:about=\"\" xmlns:xmp=\"http://ns.adobe.com/xap/1.0/\">" +
         "<xmp:CreatorTool>Adobe InDesign CS6 (Macintosh)</xmp:CreatorTool>" +
         "</rdf:Description>" +
         "<rdf:Description rdf:about=\"\" xmlns:xmpMM=\"http://ns.adobe.com/xap/1.0/mm/\">" +
         "<xmpMM:InstanceID>xmp.iid:D093CC710A2068118083AFA2F0AAE3ED</xmpMM:InstanceID>" +
         "</rdf:Description>" +
         "</rdf:RDF>" +
         "</x:xmpmeta>" +
         "</Document>";

   private static final String FIXTURE_2 = "<Properties>" +
         "<PathGeometry>" +
         "<GeometryPathType PathOpen=\"false\">" +
         "<PathPointArray>" +
         "<PathPointType Anchor=\"-140.31496000000004 -29.999999448818926\" LeftDirection=\"-140.31496000000004 -29.999999448818926\" RightDirection=\"-140.31496000000004 -29.999999448818926\"/>" +
         "<PathPointType Anchor=\"-140.31496000000004 20\" LeftDirection=\"-140.31496000000004 20\" RightDirection=\"-140.31496000000004 20\"/>" +
         "<PathPointType Anchor=\"140.31495999999999 20\" LeftDirection=\"140.31495999999999 20\" RightDirection=\"140.31495999999999 20\"/>" +
         "<PathPointType Anchor=\"140.31495999999999 -29.999999448818926\" LeftDirection=\"140.31495999999999 -29.999999448818926\" RightDirection=\"140.31495999999999 -29.999999448818926\"/>" +
         "</PathPointArray>" +
         "</GeometryPathType>" +
         "</PathGeometry>" +
         "</Properties>";


   public static void main( String[] args ) {
      junit.textui.TestRunner.run( TestArrayAutoExpansion.class );
   }

   public TestArrayAutoExpansion( String name ) {
      super( name );
   }

   public void testShouldCreateAutomaticExpansionWhenContainingArrayOfObjects() throws Exception {

      XMLSerializer serializer = new XMLSerializer();
      serializer.setTypeHintsEnabled( false );
      serializer.setPerformAutoExpansion( true );
      serializer.setRootName( "Document" );

      JSON jsonRepresentation = serializer.read( FIXTURE );

      final String writtenBack = serializer.write( jsonRepresentation );

      assertXMLEqual( FIXTURE, writtenBack );
   }

   public void test_array_is_being_correctly_constructed() throws Exception {
      XMLSerializer serializer = new XMLSerializer();
      serializer.setTypeHintsEnabled( false );
      serializer.setPerformAutoExpansion( true );
      serializer.setRootName( "Properties" );

      JSON jsonRepresentation = serializer.read( FIXTURE_2 );

      System.out.println( "jsonRepresentation = " + jsonRepresentation );

      final String writtenBack = serializer.write( jsonRepresentation );

      assertXMLEqual( FIXTURE_2, writtenBack );
   }
}