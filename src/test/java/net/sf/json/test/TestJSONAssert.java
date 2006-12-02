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

package net.sf.json.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.AssertionFailedError;
import junit.framework.TestCase;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONFunction;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class TestJSONAssert extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestJSONAssert.class );
   }

   public TestJSONAssert( String testName )
   {
      super( testName );
   }

   public void testAssertEquals_JSON_JSON__actual_null()
   {
      try{
         JSON expected = new JSONArray( "[1,2,3]" );
         JSONAssert.assertEquals( expected, null );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual was null" );
      }
   }

   public void testAssertEquals_JSON_JSON__expected_null()
   {
      try{
         JSON actual = new JSONObject( "{\"str\":\"json\"}" );
         JSONAssert.assertEquals( null, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "expected was null" );
      }
   }

   public void testAssertEquals_JSON_JSON__JSONArray_JSONArray()
   {
      try{
         JSON expected = new JSONArray( "[1,2,3]" );
         JSON actual = new JSONArray( "[1,2,3]" );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Arrays should be equal" );
      }
   }

   public void testAssertEquals_JSON_JSON__JSONArray_JSONObject()
   {
      try{
         JSON expected = new JSONArray( "[1,2,3]" );
         JSON actual = new JSONObject( "{\"str\":\"json\"}" );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual is not a JSONArray" );
      }
   }

   public void testAssertEquals_JSON_JSON__JSONNull_JSONArray()
   {
      try{
         JSON expected = JSONNull.getInstance();
         JSON actual = new JSONArray( "[1,2,3]" );

         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual is not a JSONNull" );
      }
   }

   public void testAssertEquals_JSON_JSON__JSONNull_JSONNull()
   {
      try{
         JSON expected = JSONNull.getInstance();
         JSON actual = JSONNull.getInstance();
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "JSONNull should be equal to itself" );
      }
   }

   public void testAssertEquals_JSON_JSON__JSONObject_JSONArray()
   {
      try{
         JSON expected = new JSONObject( "{\"str\":\"json\"}" );
         JSON actual = new JSONArray( "[1,2,3]" );

         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual is not a JSONObject" );
      }
   }

   public void testAssertEquals_JSON_JSON__JSONObject_JSONObject()
   {
      try{
         JSON expected = new JSONObject( "{\"str\":\"json\"}" );
         JSON actual = new JSONObject( "{\"str\":\"json\"}" );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Objects should be equal" );
      }
   }

   public void testAssertEquals_JSONArray_JSONArray()
   {
      Object[] values = new Object[] { Boolean.valueOf( true ), new Integer( Integer.MAX_VALUE ),
            new Long( Long.MAX_VALUE ), new Float( Float.MAX_VALUE ),
            new Double( Double.MAX_VALUE ), "json", new JSONArray(), new JSONObject( true ),
            new JSONObject(), new JSONObject().put( "str", "json" ), "function(){ return this; }",
            new JSONFunction( "return that;" ), new int[] { 1, 2 } };
      JSONArray expected = JSONArray.fromObject( values );
      JSONArray actual = JSONArray.fromObject( values );

      try{
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Arrays should be equal" );
      }
   }

   public void testAssertEquals_JSONArray_JSONArray__actual_null()
   {
      try{
         JSONArray expected = new JSONArray( "[1,2,3]" );
         JSONAssert.assertEquals( expected, (JSONArray) null );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual array was null" );
      }
   }

   public void testAssertEquals_JSONArray_JSONArray__different_length()
   {
      try{
         JSONArray expected = new JSONArray( "[1]" );
         JSONArray actual = new JSONArray();
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(),
               "arrays sizes differed, expected.length()=1 actual.length()=0" );
      }
   }

   public void testAssertEquals_JSONArray_JSONArray__expected_null()
   {
      try{
         JSONArray actual = new JSONArray( "[1,2,3]" );
         JSONAssert.assertEquals( (JSONArray) null, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "expected array was null" );
      }
   }

   public void testAssertEquals_JSONArray_JSONArray__nulls()
   {
      try{
         JSONArray expected = new JSONArray( "[1]" );
         JSONArray actual = new JSONArray().put( JSONNull.getInstance() );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "arrays first differed at element [0];" );
      }

      try{
         JSONArray expected = new JSONArray().put( JSONNull.getInstance() );
         JSONArray actual = new JSONArray( "[1]" );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "arrays first differed at element [0];" );
      }
   }

   public void testAssertEquals_JSONArray_String()
   {
      try{
         JSONArray expected = new JSONArray( "[1,2,3]" );
         String actual = "[1,2,3]";
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Arrays should be equal" );
      }
   }

   public void testAssertEquals_JSONArray_String_fail()
   {
      try{
         JSONArray expected = new JSONArray( "[1,2,3]" );
         String actual = "{1,2,3}";
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual is not a JSONArray" );
      }
   }

   public void testAssertEquals_JSONFunction_String()
   {
      try{
         JSONFunction expected = new JSONFunction( "return this;" );
         String actual = "function(){ return this; }";
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Functions should be equal" );
      }
   }

   public void testAssertEquals_JSONFunction_String__actual_null()
   {
      try{
         JSONFunction expected = new JSONFunction( ";" );
         JSONAssert.assertEquals( expected, (String) null );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual string was null" );
      }
   }

   public void testAssertEquals_JSONFunction_String__expected_null()
   {
      try{
         JSONAssert.assertEquals( (JSONFunction) null, ";" );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "expected function was null" );
      }
   }

   public void testAssertEquals_JSONFunction_String_fail()
   {
      try{
         JSONFunction expected = new JSONFunction( "return this;" );
         String actual = "function(){ return this;";
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "'function(){ return this;' is not a function" );
      }
   }

   public void testAssertEquals_JSONNull_String()
   {
      try{
         JSONNull expected = JSONNull.getInstance();
         String actual = "null";
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Should be equal" );
      }
   }

   public void testAssertEquals_JSONNull_String__actual_null()
   {
      try{
         JSONNull expected = JSONNull.getInstance();
         JSONAssert.assertEquals( expected, (String) null );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual string was null" );
      }
   }

   public void testAssertEquals_JSONNull_String__expected_null()
   {
      try{
         JSONAssert.assertEquals( (JSONNull) null, "null" );
      }
      catch( AssertionFailedError e ){
         fail( "Should be equal" );
      }
   }

   public void testAssertEquals_JSONObject_JSONObject_()
   {
      try{
         String[] names = new String[] { "b", "i", "l", "f", "d", "s", "a1", "o1", "o2", "o3",
               "u1", "u2" };
         Object[] values = new Object[] { Boolean.valueOf( true ),
               new Integer( Integer.MAX_VALUE ), new Long( Long.MAX_VALUE ),
               new Float( Float.MAX_VALUE ), new Double( Double.MAX_VALUE ), "json",
               new JSONArray(), new JSONObject( true ), new JSONObject(),
               new JSONObject().put( "str", "json" ), "function(){ return this; }",
               new JSONFunction( "return that;" ) };
         Map map = new HashMap();
         for( int i = 0; i < names.length; i++ ){
            map.put( names[i], values[i] );
         }
         JSONObject expected = new JSONObject( map );
         JSONObject actual = new JSONObject( map );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Objects should be equal" );
      }
   }

   public void testAssertEquals_JSONObject_JSONObject__actual_null()
   {
      try{
         JSONObject expected = new JSONObject( "{}" );
         JSONAssert.assertEquals( expected, (JSONObject) null );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual object was null" );
      }
   }

   public void testAssertEquals_JSONObject_JSONObject__expected_null()
   {
      try{
         JSONObject actual = new JSONObject( "{}" );
         JSONAssert.assertEquals( (JSONObject) null, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "expected object was null" );
      }
   }

   public void testAssertEquals_JSONObject_JSONObject_names()
   {
      try{
         JSONObject expected = new JSONObject().put( "str", "json" );
         JSONObject actual = new JSONObject();
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals(
               e.getMessage(),
               "names sizes differed, expected.names().length()=1 actual.names().length()=0 expected:<1> but was:<0>" );
      }
   }

   public void testAssertEquals_JSONObject_JSONObject_nullObjects()
   {
      try{
         JSONObject expected = new JSONObject( true );
         JSONObject actual = new JSONObject( true );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Objects should be equal" );
      }
   }

   public void testAssertEquals_JSONObject_JSONObject_nullObjects_fail1()
   {
      try{
         JSONObject expected = new JSONObject();
         JSONObject actual = new JSONObject( true );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual is a null JSONObject" );
      }
   }

   public void testAssertEquals_JSONObject_JSONObject_nullObjects_fail2()
   {
      try{
         JSONObject expected = new JSONObject( true );
         JSONObject actual = new JSONObject();
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual is not a null JSONObject" );
      }
   }

   public void testAssertEquals_JSONObject_String()
   {
      try{
         JSONObject expected = new JSONObject( "{\"str\":\"json\"}" );
         String actual = "{\"str\":\"json\"}";
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Objects should be equal" );
      }
   }

   public void testAssertEquals_JSONObject_String_fail()
   {
      try{
         JSONObject expected = new JSONObject( "{\"str\":\"json\"}" );
         String actual = "[1,2,3]";
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual is not a JSONObject" );
      }
   }

   public void testAssertEquals_String_JSONArray()
   {
      try{
         String expected = "[1,2,3]";
         JSONArray actual = new JSONArray( "[1,2,3]" );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Arrays should be equal" );
      }
   }

   public void testAssertEquals_String_JSONArray_fail()
   {
      try{
         String expected = "{1,2,3}";
         JSONArray actual = new JSONArray( "[1,2,3]" );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "expected is not a JSONArray" );
      }
   }

   public void testAssertEquals_String_JSONFunction()
   {
      try{
         String expected = "function(){ return this; }";
         JSONFunction actual = new JSONFunction( "return this;" );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Functions should be equal" );
      }
   }

   public void testAssertEquals_String_JSONFunction_fail()
   {
      try{
         JSONFunction actual = new JSONFunction( "return this;" );
         String expected = "function(){ return this;";
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "'function(){ return this;' is not a function" );
      }
   }

   public void testAssertEquals_String_JSONNull()
   {
      try{
         String expected = "null";
         JSONNull actual = JSONNull.getInstance();
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Should be equal" );
      }
   }

   public void testAssertEquals_String_JSONObject()
   {
      try{
         String expected = "{\"str\":\"json\"}";
         JSONObject actual = new JSONObject( "{\"str\":\"json\"}" );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Objects should be equal" );
      }
   }

   public void testAssertEquals_String_JSONObject_fail()
   {
      try{
         String expected = "[1,2,3]";
         JSONObject actual = new JSONObject( "{\"str\":\"json\"}" );
         JSONAssert.assertEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "expected is not a JSONObject" );
      }
   }

   public void testAssertJsonEquals_garbage_json()
   {
      try{
         String expected = "garbage";
         String actual = "null";
         JSONAssert.assertJsonEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "expected is not a valid JSON string" );
      }
   }

   public void testAssertJsonEquals_json_garbage()
   {
      try{
         String expected = "null";
         String actual = "garbage";
         JSONAssert.assertJsonEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         assertEquals( e.getMessage(), "actual is not a valid JSON string" );
      }
   }

   public void testAssertJsonEquals_jsonArray_jsonArray()
   {
      try{
         String expected = "[1,2,3]";
         String actual = "[1,2,3]";
         JSONAssert.assertJsonEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Strings should be valid JSON and equal" );
      }
   }

   public void testAssertJsonEquals_jsonNull_jsonNull()
   {
      try{
         String expected = "null";
         String actual = "null";
         JSONAssert.assertJsonEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Strings should be valid JSON and equal" );
      }
   }

   public void testAssertJsonEquals_jsonObject_jsonObject()
   {
      try{
         String expected = "{\"str\":\"json\"}";
         String actual = "{\"str\":\"json\"}";
         JSONAssert.assertJsonEquals( expected, actual );
      }
      catch( AssertionFailedError e ){
         fail( "Strings should be valid JSON and equal" );
      }
   }
}