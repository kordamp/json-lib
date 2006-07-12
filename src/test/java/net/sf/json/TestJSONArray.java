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
package net.sf.json;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author Andres Almiray
 */
public class TestJSONArray extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestJSONArray.class );
   }

   public TestJSONArray( String testName )
   {
      super( testName );
   }

   public void testArray_nullObjects()
   {
      testJSONArray( new Object[] { null, null }, "[null,null]" );
   }

   public void testCollection()
   {
      List l = new ArrayList();
      l.add( Boolean.TRUE );
      l.add( new Integer( 1 ) );
      l.add( "string" );
      testJSONArray( l, "[true,1,\"string\"]" );
   }

   public void testCollection_nullObjects()
   {
      List l = new ArrayList();
      l.add( null );
      l.add( null );
      testJSONArray( l, "[null,null]" );
   }

   public void testFunctionArray_JSONTokener()
   {
      testJSONArray( new JSONTokener( "[function(a){ return a; }]" ), "[function(a){ return a; }]" );
   }

   public void testFunctionArray_ObjectArray()
   {
      testJSONArray( new JSONFunction[] { new JSONFunction( new String[] { "a" }, "return a;" ) },
            "[function(a){ return a; }]" );
   }

   public void testFunctionArray_String()
   {
      testJSONArray( "[function(a){ return a; }]", "[function(a){ return a; }]" );
   }

   public void testMultiFunctionArray_String()
   {
      testJSONArray( "[function(a){ return a; },[function(b){ return b; }]]",
            "[function(a){ return a; },[function(b){ return b; }]]" );
   }

   public void testPrimitiveBooleanArray()
   {
      testJSONArray( new boolean[] { true, false }, "[true,false]" );
   }

   public void testPrimitiveByteArray()
   {
      testJSONArray( new byte[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testPrimitiveCharArray()
   {
      testJSONArray( new char[] { 'a', 'b', 'c' }, "[\"a\",\"b\",\"c\"]" );
   }

   public void testPrimitiveDoubleArray()
   {
      testJSONArray( new double[] { 1.1, 2.2, 3.3 }, "[1.1,2.2,3.3]" );
   }

   public void testPrimitiveFloatArray()
   {
      testJSONArray( new float[] { 1.1f, 2.2f, 3.3f }, "[1.1,2.2,3.3]" );
   }

   public void testPrimitiveIntArray()
   {
      testJSONArray( new int[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testPrimitiveLongArray()
   {
      testJSONArray( new long[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testPrimitiveShortArray()
   {
      testJSONArray( new short[] { 1, 2, 3 }, "[1,2,3]" );
   }

   private void testJSONArray( Object array, String expected )
   {
      try{
         JSONArray jsonArray = JSONArray.fromObject( array );
         assertEquals( expected, jsonArray.toString() );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }
}