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

import java.util.Iterator;

import junit.framework.Assert;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONFunction;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

/**
 * Provides assertions on equality for JSON strings and JSON types.
 * 
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class JSONAssert extends Assert
{
   /**
    * Asserts that two JSON values are equal.
    */
   public static void assertEquals( JSON expected, JSON actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONArrays are equal.
    */
   public static void assertEquals( JSONArray expected, JSONArray actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONArrays are equal.
    */
   public static void assertEquals( JSONArray expected, String actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONFunctions are equal.
    */
   public static void assertEquals( JSONFunction expected, String actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONNulls are equal.
    */
   public static void assertEquals( JSONNull expected, String actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONObjects are equal.
    */
   public static void assertEquals( JSONObject expected, JSONObject actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONObjects are equal.
    */
   public static void assertEquals( JSONObject expected, String actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSON values are equal.
    */
   public static void assertEquals( String message, JSON expected, JSON actual )
   {
      String header = message == null ? "" : message + ": ";
      if( expected == null ){
         fail( header + "expected was null" );
      }
      if( actual == null ){
         fail( header + "actual was null" );
      }
      if( expected == actual || expected.equals( actual ) ){
         return;
      }

      if( expected instanceof JSONArray ){
         if( actual instanceof JSONArray ){
            assertEquals( header, (JSONArray) expected, (JSONArray) actual );
         }else{
            fail( header + "actual is not a JSONArray" );
         }
      }else if( expected instanceof JSONObject ){
         if( actual instanceof JSONObject ){
            assertEquals( header, (JSONObject) expected, (JSONObject) actual );
         }else{
            fail( header + "actual is not a JSONObject" );
         }
      }else if( expected instanceof JSONNull ){
         if( actual instanceof JSONNull ){
            return;
         }else{
            fail( header + "actual is not a JSONNull" );
         }
      }
   }

   /**
    * Asserts that two JSONArrays are equal.
    */
   public static void assertEquals( String expected, JSONArray actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONArrays are equal.
    */
   public static void assertEquals( String message, JSONArray expected, JSONArray actual )
   {
      String header = message == null ? "" : message + ": ";
      if( expected == null ){
         fail( header + "expected array was null" );
      }
      if( actual == null ){
         fail( header + "actual array was null" );
      }
      if( expected == actual || expected.equals( actual ) ){
         return;
      }
      if( actual.length() != expected.length() ){
         fail( header + "arrays sizes differed, expected.length()=" + expected.length()
               + " actual.length()=" + actual.length() );
      }

      int max = expected.length();
      for( int i = 0; i < max; i++ ){
         Object o1 = expected.get( i );
         Object o2 = actual.get( i );

         // handle nulls
         if( JSONNull.getInstance()
               .equals( o1 ) ){
            if( JSONNull.getInstance()
                  .equals( o2 ) ){
               return;
            }else{
               fail( header + "arrays first differed at element [" + i + "];" );
            }
         }else{
            if( JSONNull.getInstance()
                  .equals( o2 ) ){
               fail( header + "arrays first differed at element [" + i + "];" );
            }
         }

         if( o1 instanceof JSONArray && o2 instanceof JSONArray ){
            JSONArray e = (JSONArray) o1;
            JSONArray a = (JSONArray) o2;
            assertEquals( header + "arrays first differed at element " + i + ";", e, a );
         }else{
            if( o1 instanceof String && o2 instanceof JSONFunction ){
               assertEquals( header + "arrays first differed at element [" + i + "];", (String) o1,
                     (JSONFunction) o2 );
            }else if( o1 instanceof JSONFunction && o2 instanceof String ){
               assertEquals( header + "arrays first differed at element [" + i + "];",
                     (JSONFunction) o1, (String) o2 );
            }else if( o1 instanceof JSONObject && o2 instanceof JSONObject ){
               assertEquals( header + "arrays first differed at element [" + i + "];",
                     (JSONObject) o1, (JSONObject) o2 );
            }else if( o1 instanceof JSONArray && o2 instanceof JSONArray ){
               assertEquals( header + "arrays first differed at element [" + i + "];",
                     (JSONArray) o1, (JSONArray) o2 );
            }else if( o1 instanceof JSONFunction && o2 instanceof JSONFunction ){
               assertEquals( header + "arrays first differed at element [" + i + "];",
                     (JSONFunction) o1, (JSONFunction) o2 );
            }else{
               assertEquals( header + "arrays first differed at element [" + i + "];", o1, o2 );
            }
         }
      }
   }

   /**
    * Asserts that two JSONArrays are equal.
    */
   public static void assertEquals( String message, JSONArray expected, String actual )
   {
      try{
         assertEquals( message, expected, JSONArray.fromString( actual ) );
      }
      catch( JSONException e ){
         String header = message == null ? "" : message + ": ";
         fail( header + "actual is not a JSONArray" );
      }
   }

   /**
    * Asserts that two JSONFunctions are equal.
    */
   public static void assertEquals( String expected, JSONFunction actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONFunctions are equal.
    */
   public static void assertEquals( String message, JSONFunction expected, String actual )
   {
      String header = message == null ? "" : message + ": ";
      if( expected == null ){
         fail( header + "expected function was null" );
      }
      if( actual == null ){
         fail( header + "actual string was null" );
      }

      try{
         assertEquals( header, expected, JSONFunction.parse( actual ) );
      }
      catch( JSONException jsone ){
         fail( header + "'" + actual + "' is not a function" );
      }
   }

   /**
    * Asserts that two JSONNulls are equal.
    */
   public static void assertEquals( String expected, JSONNull actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONNulls are equal.
    */
   public static void assertEquals( String message, JSONNull expected, String actual )
   {
      String header = message == null ? "" : message + ": ";
      if( actual == null ){
         fail( header + "actual string was null" );
      }else if( expected == null ){
         assertEquals( header, "null".toString(), actual );
      }else{
         assertEquals( header, expected.toString(), actual );
      }
   }

   /**
    * Asserts that two JSONObjects are equal.
    */
   public static void assertEquals( String expected, JSONObject actual )
   {
      assertEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSONObjects are equal.
    */
   public static void assertEquals( String message, JSONObject expected, JSONObject actual )
   {
      String header = message == null ? "" : message + ": ";
      if( expected == null ){
         fail( header + "expected object was null" );
      }
      if( actual == null ){
         fail( header + "actual object was null" );
      }
      if( expected == actual /* || expected.equals( actual ) */){
         return;
      }

      if( expected.isNullObject() ){
         if( actual.isNullObject() ){
            return;
         }else{
            fail( header + "actual is not a null JSONObject" );
         }
      }else{
         if( actual.isNullObject() ){
            fail( header + "actual is a null JSONObject" );
         }
      }

      assertEquals( header + "names sizes differed, expected.names().length()=" + expected.names()
            .length() + " actual.names().length()=" + actual.names()
            .length(), expected.names()
            .length(), actual.names()
            .length() );
      for( Iterator keys = expected.keys(); keys.hasNext(); ){
         String key = (String) keys.next();
         Object o1 = expected.get( key );
         Object o2 = actual.get( key );

         if( JSONNull.getInstance()
               .equals( o1 ) ){
            if( JSONNull.getInstance()
                  .equals( o2 ) ){
               return;
            }else{
               fail( header + "objects differed at key [" + key + "];" );
            }
         }else{
            if( JSONNull.getInstance()
                  .equals( o2 ) ){
               fail( header + "objects differed at key [" + key + "];" );
            }
         }

         if( o1 instanceof String && o2 instanceof JSONFunction ){
            assertEquals( header + "objects differed at key [" + key + "];", (String) o1,
                  (JSONFunction) o2 );
         }else if( o1 instanceof JSONFunction && o2 instanceof String ){
            assertEquals( header + "objects differed at key [" + key + "];", (JSONFunction) o1,
                  (String) o2 );
         }else if( o1 instanceof JSONObject && o2 instanceof JSONObject ){
            assertEquals( header + "objects differed at key [" + key + "];", (JSONObject) o1,
                  (JSONObject) o2 );
         }else if( o1 instanceof JSONArray && o2 instanceof JSONArray ){
            assertEquals( header + "objects differed at key [" + key + "];", (JSONArray) o1,
                  (JSONArray) o2 );
         }else if( o1 instanceof JSONFunction && o2 instanceof JSONFunction ){
            assertEquals( header + "objects differed at key [" + key + "];", (JSONFunction) o1,
                  (JSONFunction) o2 );
         }else{
            assertEquals( header + "objects differed at key [" + key + "];", o1, o2 );
         }
      }
   }

   /**
    * Asserts that two JSONObjects are equal.
    */
   public static void assertEquals( String message, JSONObject expected, String actual )
   {
      try{
         assertEquals( message, expected, JSONObject.fromString( actual ) );
      }
      catch( JSONException e ){
         String header = message == null ? "" : message + ": ";
         fail( header + "actual is not a JSONObject" );
      }
   }

   /**
    * Asserts that two JSONArrays are equal.
    */
   public static void assertEquals( String message, String expected, JSONArray actual )
   {
      try{
         assertEquals( message, JSONArray.fromString( expected ), actual );
      }
      catch( JSONException e ){
         String header = message == null ? "" : message + ": ";
         fail( header + "expected is not a JSONArray" );
      }
   }

   /**
    * Asserts that two JSONFunctions are equal.
    */
   public static void assertEquals( String message, String expected, JSONFunction actual )
   {
      String header = message == null ? "" : message + ": ";
      if( expected == null ){
         fail( header + "expected string was null" );
      }
      if( actual == null ){
         fail( header + "actual function was null" );
      }

      try{
         assertEquals( header, JSONFunction.parse( expected ), actual );
      }
      catch( JSONException jsone ){
         fail( header + "'" + expected + "' is not a function" );
      }
   }

   /**
    * Asserts that two JSONNulls are equal.
    */
   public static void assertEquals( String message, String expected, JSONNull actual )
   {
      String header = message == null ? "" : message + ": ";
      if( expected == null ){
         fail( header + "expected was null" );
      }else if( actual == null ){
         assertEquals( header, expected, "null" );
      }else{
         assertEquals( header, expected, actual.toString() );
      }
   }

   /**
    * Asserts that two JSONObjects are equal.
    */
   public static void assertEquals( String message, String expected, JSONObject actual )
   {
      try{
         assertEquals( message, JSONObject.fromString( expected ), actual );
      }
      catch( JSONException e ){
         String header = message == null ? "" : message + ": ";
         fail( header + "expected is not a JSONObject" );
      }
   }

   /**
    * Asserts that two JSON strings are equal.
    */
   public static void assertJsonEquals( String expected, String actual )
   {
      assertJsonEquals( null, expected, actual );
   }

   /**
    * Asserts that two JSON strings are equal.
    */
   public static void assertJsonEquals( String message, String expected, String actual )
   {
      String header = message == null ? "" : message + ": ";
      if( expected == null ){
         fail( header + "expected was null" );
      }
      if( actual == null ){
         fail( header + "actual was null" );
      }

      JSON json1 = null;
      JSON json2 = null;
      try{
         json1 = JSONSerializer.toJSON( expected );
      }
      catch( JSONException jsone ){
         fail( header + "expected is not a valid JSON string" );
      }
      try{
         json2 = JSONSerializer.toJSON( actual );
      }
      catch( JSONException jsone ){
         fail( header + "actual is not a valid JSON string" );
      }
      assertEquals( header, json1, json2 );
   }
}