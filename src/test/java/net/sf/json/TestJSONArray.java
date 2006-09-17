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

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.json.sample.ArrayJSONStringBean;
import net.sf.json.sample.BeanA;
import net.sf.json.util.JSONDynaBean;
import net.sf.json.util.JSONDynaClass;
import net.sf.json.util.JSONTokener;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
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

   public void testConstructor_Collection()
   {
      List l = new ArrayList();
      l.add( Boolean.TRUE );
      l.add( new Integer( 1 ) );
      l.add( "string" );
      testJSONArray( l, "[true,1,\"string\"]" );
   }

   public void testConstructor_Collection_JSONArray()
   {
      List l = new ArrayList();
      l.add( new JSONArray( new int[] { 1, 2 } ) );
      testJSONArray( l, "[[1,2]]" );
   }

   public void testConstructor_Collection_JSONFunction()
   {
      List l = new ArrayList();
      l.add( new JSONFunction( new String[] { "a" }, "return a;" ) );
      testJSONArray( l, "[function(a){ return a; }]" );
   }

   public void testConstructor_Collection_JSONString()
   {
      ArrayJSONStringBean bean = new ArrayJSONStringBean();
      bean.setValue( "json,json" );
      List l = new ArrayList();
      l.add( bean );
      testJSONArray( l, "[[\"json\",\"json\"]]" );
   }

   public void testConstructor_Collection_nulls()
   {
      List l = new ArrayList();
      l.add( null );
      l.add( null );
      testJSONArray( l, "[null,null]" );
   }

   public void testConstructor_JSONArray()
   {
      JSONArray expected = new JSONArray( "[1,2]" );
      JSONArray actual = new JSONArray( new JSONArray( "[1,2]" ) );
      Assertions.assertEquals( expected, actual );
   }

   public void testConstructor_JSONTokener_functions()
   {
      testJSONArray( new JSONTokener( "[function(a){ return a; }]" ), "[function(a){ return a; }]" );
   }

   public void testConstructor_JSONTokener_nulls()
   {
      testJSONArray( new JSONTokener( "[,,]" ), "[null,null]" );
   }

   public void testConstructor_JSONTokener_syntax_errors()
   {
      try{
         new JSONArray( "" );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testConstructor_Object_Array()
   {
      JSONArray expected = new JSONArray( "[\"json\",1]" );
      JSONArray actual = new JSONArray( new Object[] { "json", new Integer( 1 ) } );
      Assertions.assertEquals( expected, actual );
   }

   public void testConstructor_Object_Array_Array()
   {
      JSONArray expected = new JSONArray( "[[1,2]]" );
      JSONArray actual = new JSONArray( new Object[] { new int[] { 1, 2 } } );
      Assertions.assertEquals( expected, actual );
   }

   public void testConstructor_Object_Array_functions()
   {
      JSONArray expected = new JSONArray( "[function(a){ return a; }]" );
      JSONArray actual = new JSONArray( new JSONFunction[] { new JSONFunction(
            new String[] { "a" }, "return a;" ) } );
      Assertions.assertEquals( expected, actual );
   }

   public void testConstructor_Object_Array_functions_2()
   {
      JSONArray expected = new JSONArray( new JSONFunction[] { new JSONFunction(
            new String[] { "a" }, "return a;" ) } );
      JSONArray actual = new JSONArray( "[function(a){ return a; }]" );
      Assertions.assertEquals( expected, actual );
   }

   public void testConstructor_Object_Array_functions_3()
   {
      JSONArray expected = new JSONArray( new JSONFunction[] { new JSONFunction(
            new String[] { "a" }, "return a;" ) } );
      JSONArray actual = new JSONArray( new String[] { "function(a){ return a; }" } );
      Assertions.assertEquals( expected, actual );
   }

   public void testConstructor_Object_Array_JSONArray()
   {
      JSONArray expected = new JSONArray( "[[1,2]]" );
      JSONArray actual = new JSONArray( new Object[] { new JSONArray( "[1,2]" ) } );
      Assertions.assertEquals( expected, actual );
   }

   public void testConstructor_Object_Array_JSONString()
   {
      ArrayJSONStringBean bean = new ArrayJSONStringBean();
      bean.setValue( "json,json" );
      JSONArray expected = new JSONArray( "[[\"json\",\"json\"]]" );
      JSONArray actual = new JSONArray( new Object[] { bean } );
      Assertions.assertEquals( expected, actual );
   }

   public void testConstructor_Object_Array_nulls()
   {
      JSONArray expected = new JSONArray( "[null,null]" );
      JSONArray actual = new JSONArray( new Object[] { null, null } );
      Assertions.assertEquals( expected, actual );
   }

   public void testConstructor_primitive_array_boolean()
   {
      testJSONArray( new boolean[] { true, false }, "[true,false]" );
   }

   public void testConstructor_primitive_array_byte()
   {
      testJSONArray( new byte[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testConstructor_primitive_array_char()
   {
      testJSONArray( new char[] { 'a', 'b', 'c' }, "[\"a\",\"b\",\"c\"]" );
   }

   public void testConstructor_primitive_array_double()
   {
      testJSONArray( new double[] { 1.1, 2.2, 3.3 }, "[1.1,2.2,3.3]" );
   }

   public void testConstructor_primitive_array_float()
   {
      testJSONArray( new float[] { 1.1f, 2.2f, 3.3f }, "[1.1,2.2,3.3]" );
   }

   public void testConstructor_primitive_array_int()
   {
      testJSONArray( new int[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testConstructor_primitive_array_long()
   {
      testJSONArray( new long[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testConstructor_primitive_array_short()
   {
      testJSONArray( new short[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testConstructor_String_functions()
   {
      testJSONArray( "[function(a){ return a; }]", "[function(a){ return a; }]" );
   }

   public void testConstructor_String_functions_multi()
   {
      testJSONArray( "[function(a){ return a; },[function(b){ return b; }]]",
            "[function(a){ return a; },[function(b){ return b; }]]" );
   }

   public void testFromObject_JSONArray()
   {
      JSONArray expected = new JSONArray( "[1,2]" );
      JSONArray actual = JSONArray.fromObject( new JSONArray( "[1,2]" ) );
      Assertions.assertEquals( expected, actual );
   }

   public void testFromObject_JSONString()
   {
      ArrayJSONStringBean bean = new ArrayJSONStringBean();
      bean.setValue( "json,json" );
      JSONArray actual = JSONArray.fromObject( bean );
      JSONArray expected = new JSONArray( "['json','json']" );
      Assertions.assertEquals( expected, actual );
   }

   public void testFromObject_unsupported_Object()
   {
      try{
         JSONArray.fromObject( new HashMap() );
         fail( "Expected an IllegalArgumentException" );
      }
      catch( IllegalArgumentException expected ){
         // OK
      }
   }

   public void testGet_exception()
   {
      try{
         JSONArray jsonArray = new JSONArray( "[]" );
         jsonArray.get( 0 );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testGetBoolean_exception()
   {
      try{
         JSONArray jsonArray = new JSONArray( "[[]]" );
         jsonArray.getBoolean( 0 );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testGetBoolean_false()
   {
      JSONArray jsonArray = new JSONArray( "[false]" );
      assertFalse( jsonArray.getBoolean( 0 ) );
   }

   public void testGetBoolean_true()
   {
      JSONArray jsonArray = new JSONArray( "[true]" );
      assertTrue( jsonArray.getBoolean( 0 ) );
   }

   public void testGetDimensions_empty_array()
   {
      int[] dims = JSONArray.getDimensions( new JSONArray() );
      assertEquals( 1, dims.length );
      assertEquals( 0, dims[0] );
   }

   public void testGetDimensions_null_array()
   {
      int[] dims = JSONArray.getDimensions( null );
      assertEquals( 1, dims.length );
      assertEquals( 0, dims[0] );
   }

   public void testGetDimensions_one_dimension()
   {
      int[] dims = JSONArray.getDimensions( new JSONArray( "[1,2,3]" ) );
      assertEquals( 1, dims.length );
      assertEquals( 3, dims[0] );
   }

   public void testGetDimensions_pyramid()
   {
      int[] dims = JSONArray.getDimensions( new JSONArray( "[1,[2,[3,[4]]]]" ) );
      assertEquals( 4, dims.length );
      assertEquals( 2, dims[0] );
      assertEquals( 2, dims[1] );
      assertEquals( 2, dims[2] );
      assertEquals( 1, dims[3] );

      dims = JSONArray.getDimensions( new JSONArray( "[[[[1],2],3],4]" ) );
      assertEquals( 4, dims.length );
      assertEquals( 2, dims[0] );
      assertEquals( 2, dims[1] );
      assertEquals( 2, dims[2] );
      assertEquals( 1, dims[3] );
   }

   public void testGetDimensions_two_dimension()
   {
      int[] dims = JSONArray.getDimensions( new JSONArray( "[[1,2,3],[4,5,6]]" ) );
      assertEquals( 2, dims.length );
      assertEquals( 2, dims[0] );
      assertEquals( 3, dims[1] );

      dims = JSONArray.getDimensions( new JSONArray( "[[1,2],[4,5,6]]" ) );
      assertEquals( 2, dims.length );
      assertEquals( 2, dims[0] );
      assertEquals( 3, dims[1] );

      dims = JSONArray.getDimensions( new JSONArray( "[[1,2,3],[4,5]]" ) );
      assertEquals( 2, dims.length );
      assertEquals( 2, dims[0] );
      assertEquals( 3, dims[1] );
   }

   public void testGetDouble_exception()
   {
      try{
         JSONArray jsonArray = new JSONArray( "[[]]" );
         jsonArray.getDouble( 0 );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testGetDouble_Number()
   {
      JSONArray jsonArray = new JSONArray( "[2.0]" );
      assertEquals( 2.0d, jsonArray.getDouble( 0 ), 0d );
   }

   public void testGetDouble_String()
   {
      JSONArray jsonArray = new JSONArray( "[\"2.0\"]" );
      assertEquals( 2.0d, jsonArray.getDouble( 0 ), 0d );
   }

   public void testGetInt_exception()
   {
      try{
         JSONArray jsonArray = new JSONArray( "[[]]" );
         jsonArray.getInt( 0 );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testGetInt_Number()
   {
      JSONArray jsonArray = new JSONArray( "[2.0]" );
      assertEquals( 2, jsonArray.getInt( 0 ) );
   }

   public void testGetInt_String()
   {
      JSONArray jsonArray = new JSONArray( "[\"2.0\"]" );
      assertEquals( 2, jsonArray.getInt( 0 ) );
   }

   public void testGetJSONArray()
   {
      JSONArray jsonArray = new JSONArray( "[[1,2]]" );
      Assertions.assertEquals( new JSONArray( "[1,2]" ).toString(), jsonArray.getJSONArray( 0 )
            .toString() );
   }

   public void testGetJSONArray_exception()
   {
      try{
         JSONArray jsonArray = new JSONArray( "[1]" );
         jsonArray.getJSONArray( 0 );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testGetJSONObject()
   {
      JSONArray jsonArray = new JSONArray( "[{\"name\":\"json\"}]" );
      Assertions.assertEquals( new JSONObject( "{\"name\":\"json\"}" ), jsonArray.getJSONObject( 0 ) );
   }

   public void testGetJSONObject_exception()
   {
      try{
         JSONArray jsonArray = new JSONArray( "[1]" );
         jsonArray.getJSONObject( 0 );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testGetLong_exception()
   {
      try{
         JSONArray jsonArray = new JSONArray( "[[]]" );
         jsonArray.getLong( 0 );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testGetLong_Number()
   {
      JSONArray jsonArray = new JSONArray( "[2.0]" );
      assertEquals( 2, jsonArray.getLong( 0 ) );
   }

   public void testGetLong_String()
   {
      JSONArray jsonArray = new JSONArray( "[\"2.0\"]" );
      assertEquals( 2, jsonArray.getLong( 0 ) );
   }

   public void testGetString()
   {
      JSONArray jsonArray = new JSONArray( "[\"2.0\"]" );
      assertEquals( "2.0", jsonArray.getString( 0 ) );
   }

   public void testGetString_exception()
   {
      try{
         JSONArray jsonArray = new JSONArray( "[]" );
         jsonArray.getString( 0 );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testOptionalGet()
   {
      Object[] params = new Object[] { new JSONArray(), new JSONObject( "{\"int\":1}" ) };
      JSONArray jsonArray = new JSONArray( params );
      assertFalse( jsonArray.optBoolean( 0 ) );
      assertTrue( jsonArray.optBoolean( 0, true ) );
      assertTrue( Double.isNaN( jsonArray.optDouble( 0 ) ) );
      assertEquals( 0d, jsonArray.optDouble( 0, 0d ), 0d );
      assertEquals( 0, jsonArray.optInt( 0 ) );
      assertEquals( 1, jsonArray.optInt( 0, 1 ) );
      assertEquals( null, jsonArray.optJSONArray( 1 ) );
      Assertions.assertEquals( (JSONArray) params[0], jsonArray.optJSONArray( 0 ) );
      assertEquals( null, jsonArray.optJSONObject( 0 ) );
      Assertions.assertEquals( (JSONObject) params[1], jsonArray.optJSONObject( 1 ) );
      assertEquals( 0, jsonArray.optLong( 0 ) );
      assertEquals( 1, jsonArray.optLong( 0, 1 ) );
      assertEquals( "", jsonArray.optString( 3 ) );
      assertEquals( "json", jsonArray.optString( 3, "json" ) );
   }

   public void testPun_index_1_Array()
   {
      JSONArray array = new JSONArray();
      int[] ints = { 1, 2 };
      array.put( 1, ints );
      Assertions.assertEquals( new JSONArray( ints ), array.getJSONArray( 1 ) );
   }

   public void testPun_index_1_boolean()
   {
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( 1, true );
      assertEquals( 2, jsonArray.length() );
      assertEquals( JSONNull.getInstance(), jsonArray.get( 0 ) );
      assertTrue( jsonArray.getBoolean( 1 ) );
   }

   public void testPun_index_1_Boolean()
   {
      JSONArray array = new JSONArray();
      array.put( 1, Boolean.TRUE );
      Assertions.assertTrue( array.getBoolean( 1 ) );
   }

   public void testPun_index_1_Collection()
   {
      List l = new ArrayList();
      l.add( Boolean.TRUE );
      l.add( new Integer( 1 ) );
      l.add( "string" );
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( 1, l );
      assertEquals( 2, jsonArray.length() );
      assertEquals( JSONNull.getInstance(), jsonArray.get( 0 ) );
      Assertions.assertEquals( new JSONArray( "[true,1,\"string\"]" ), jsonArray.getJSONArray( 1 ) );
   }

   public void testPun_index_1_double()
   {
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( 1, 2.0d );
      assertEquals( 2, jsonArray.length() );
      assertEquals( JSONNull.getInstance(), jsonArray.get( 0 ) );
      assertEquals( 2.0d, jsonArray.getDouble( 1 ), 0d );
   }

   public void testPun_index_1_int()
   {
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( 1, 1 );
      assertEquals( 2, jsonArray.length() );
      assertEquals( JSONNull.getInstance(), jsonArray.get( 0 ) );
      assertEquals( 1, jsonArray.getInt( 1 ) );
   }

   public void testPun_index_1_JSON()
   {
      JSONArray array = new JSONArray();
      array.put( 1, JSONNull.getInstance() );
      Assertions.assertEquals( JSONNull.getInstance(), array.get( 1 ) );
   }

   public void testPun_index_1_JSONFunction()
   {
      JSONArray array = new JSONArray();
      JSONFunction f = new JSONFunction( "return this;" );
      array.put( 1, f );
      Assertions.assertEquals( f, (JSONFunction) array.get( 1 ) );
   }

   public void testPun_index_1_JSONString()
   {
      JSONArray array = new JSONArray();
      ArrayJSONStringBean bean = new ArrayJSONStringBean();
      bean.setValue( "json,json" );
      array.put( 1, bean );
      Assertions.assertEquals( JSONArray.fromJSONString( bean ), array.getJSONArray( 1 ) );
   }

   public void testPun_index_1_JSONTokener()
   {
      JSONArray array = new JSONArray();
      JSONTokener tok = new JSONTokener( "[1,2]" );
      array.put( 1, tok );
      tok.reset();
      Assertions.assertEquals( new JSONArray( tok ), array.getJSONArray( 1 ) );
   }

   public void testPun_index_1_long()
   {
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( 1, 1L );
      assertEquals( 2, jsonArray.length() );
      assertEquals( JSONNull.getInstance(), jsonArray.get( 0 ) );
      assertEquals( 1L, jsonArray.getLong( 1 ) );
   }

   public void testPun_index_1_Map()
   {
      Map map = new HashMap();
      map.put( "name", "json" );
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( 1, map );
      assertEquals( 2, jsonArray.length() );
      assertEquals( JSONNull.getInstance(), jsonArray.get( 0 ) );
      Assertions.assertEquals( new JSONObject( map ), jsonArray.getJSONObject( 1 ) );
   }

   public void testPun_index_1_Number()
   {
      JSONArray array = new JSONArray();
      array.put( 1, new Double( 2 ) );
      Assertions.assertEquals( new Double( 2 ).doubleValue(), array.getDouble( 1 ), 1d );
   }

   public void testPun_index_1_Object()
   {
      JSONArray array = new JSONArray();
      array.put( 1, new BeanA() );
      Assertions.assertEquals( JSONObject.fromBean( new BeanA() ), array.getJSONObject( 1 ) );
   }

   public void testPun_index_1_String()
   {
      JSONArray array = new JSONArray();
      array.put( 1, "json" );
      Assertions.assertEquals( "json", array.getString( 1 ) );
   }

   public void testPun_index_1_String_JSON()
   {
      JSONArray array = new JSONArray();
      array.put( 1, "[]" );
      Assertions.assertEquals( new JSONArray().toString(), array.getString( 1 ) );
   }

   public void testPun_index_1_String_null()
   {
      JSONArray array = new JSONArray();
      array.put( 1, (String) null );
      Assertions.assertEquals( "", array.getString( 1 ) );
   }

   public void testPut_Array()
   {
      JSONArray array = new JSONArray();
      int[] ints = { 1, 2 };
      array.put( ints );
      Assertions.assertEquals( new JSONArray( ints ), array.getJSONArray( 0 ) );
   }

   public void testPut_boolean()
   {
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( true );
      assertEquals( 1, jsonArray.length() );
      assertTrue( jsonArray.getBoolean( 0 ) );
   }

   public void testPut_Boolean()
   {
      JSONArray array = new JSONArray();
      array.put( Boolean.TRUE );
      Assertions.assertTrue( array.getBoolean( 0 ) );
   }

   public void testPut_Collection()
   {
      List l = new ArrayList();
      l.add( Boolean.TRUE );
      l.add( new Integer( 1 ) );
      l.add( "string" );
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( l );
      assertEquals( 1, jsonArray.length() );
      Assertions.assertEquals( new JSONArray( "[true,1,\"string\"]" ), jsonArray.getJSONArray( 0 ) );
   }

   public void testPut_double()
   {
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( 2.0d );
      assertEquals( 1, jsonArray.length() );
      assertEquals( 2.0d, jsonArray.getDouble( 0 ), 0d );
   }

   public void testPut_index_0_Array()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      ;
      int[] ints = { 0, 2 };
      array.put( 0, ints );
      Assertions.assertEquals( new JSONArray( ints ), array.getJSONArray( 0 ) );
   }

   public void testPut_index_0_Boolean()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      ;
      array.put( 0, Boolean.TRUE );
      Assertions.assertTrue( array.getBoolean( 0 ) );
   }

   public void testPut_index_0_JSON()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      array.put( 0, JSONNull.getInstance() );
      Assertions.assertEquals( JSONNull.getInstance(), array.get( 0 ) );
   }

   public void testPut_index_0_JSONFunction()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      JSONFunction f = new JSONFunction( "return this;" );
      array.put( 0, f );
      Assertions.assertEquals( f, (JSONFunction) array.get( 0 ) );
   }

   public void testPut_index_0_JSONString()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      ArrayJSONStringBean bean = new ArrayJSONStringBean();
      bean.setValue( "json,json" );
      array.put( 0, bean );
      Assertions.assertEquals( JSONArray.fromJSONString( bean ), array.getJSONArray( 0 ) );
   }

   public void testPut_index_0_JSONTokener()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      JSONTokener tok = new JSONTokener( "[0,2]" );
      array.put( 0, tok );
      tok.reset();
      Assertions.assertEquals( new JSONArray( tok ), array.getJSONArray( 0 ) );
   }

   public void testPut_index_0_Number()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      array.put( 0, new Double( 2 ) );
      Assertions.assertEquals( new Double( 2 ).doubleValue(), array.getDouble( 0 ), 0d );
   }

   public void testPut_index_0_Object()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      array.put( 0, new BeanA() );
      Assertions.assertEquals( JSONObject.fromBean( new BeanA() ), array.getJSONObject( 0 ) );
   }

   public void testPut_index_0_String()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      array.put( 0, "json" );
      Assertions.assertEquals( "json", array.getString( 0 ) );
   }

   public void testPut_index_0_String_JSON()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      array.put( 0, "[]" );
      Assertions.assertEquals( new JSONArray().toString(), array.getString( 0 ) );
   }

   public void testPut_index_0_String_null()
   {
      JSONArray array = new JSONArray( "[null,null]" );
      array.put( 0, (String) null );
      Assertions.assertEquals( "", array.getString( 0 ) );
   }

   public void testPut_int()
   {
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( 1 );
      assertEquals( 1, jsonArray.length() );
      assertEquals( 1, jsonArray.getInt( 0 ) );
   }

   public void testPut_JSON()
   {
      JSONArray array = new JSONArray();
      array.put( JSONNull.getInstance() );
      Assertions.assertEquals( JSONNull.getInstance(), array.get( 0 ) );
   }

   public void testPut_JSONFunction()
   {
      JSONArray array = new JSONArray();
      JSONFunction f = new JSONFunction( "return this;" );
      array.put( f );
      Assertions.assertEquals( f, (JSONFunction) array.get( 0 ) );
   }

   public void testPut_JSONString()
   {
      JSONArray array = new JSONArray();
      ArrayJSONStringBean bean = new ArrayJSONStringBean();
      bean.setValue( "json,json" );
      array.put( bean );
      Assertions.assertEquals( JSONArray.fromJSONString( bean ), array.getJSONArray( 0 ) );
   }

   public void testPut_JSONTokener()
   {
      JSONArray array = new JSONArray();
      JSONTokener tok = new JSONTokener( "[1,2]" );
      array.put( tok );
      tok.reset();
      Assertions.assertEquals( new JSONArray( tok ), array.getJSONArray( 0 ) );
   }

   public void testPut_long()
   {
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( 1L );
      assertEquals( 1, jsonArray.length() );
      assertEquals( 1L, jsonArray.getLong( 0 ) );
   }

   public void testPut_Map()
   {
      Map map = new HashMap();
      map.put( "name", "json" );
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( map );
      assertEquals( 1, jsonArray.length() );
      Assertions.assertEquals( new JSONObject( map ), jsonArray.getJSONObject( 0 ) );
   }

   public void testPut_negativeIndex()
   {
      try{
         JSONArray jsonArray = new JSONArray();
         jsonArray.put( -1, JSONNull.getInstance() );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testPut_Number()
   {
      JSONArray array = new JSONArray();
      array.put( new Double( 2 ) );
      Assertions.assertEquals( new Double( 2 ).doubleValue(), array.getDouble( 0 ), 0d );
   }

   public void testPut_Object()
   {
      JSONArray array = new JSONArray();
      array.put( new BeanA() );
      Assertions.assertEquals( JSONObject.fromBean( new BeanA() ), array.getJSONObject( 0 ) );
   }

   public void testPut_replace()
   {
      JSONArray jsonArray = new JSONArray();
      jsonArray.put( true );
      assertEquals( 1, jsonArray.length() );
      assertTrue( jsonArray.getBoolean( 0 ) );
      jsonArray.put( 0, false );
      assertEquals( 1, jsonArray.length() );
      assertFalse( jsonArray.getBoolean( 0 ) );
   }

   public void testPut_String()
   {
      JSONArray array = new JSONArray();
      array.put( "json" );
      Assertions.assertEquals( "json", array.getString( 0 ) );
   }

   public void testPut_String_JSON()
   {
      JSONArray array = new JSONArray();
      array.put( "[]" );
      Assertions.assertEquals( new JSONArray().toString(), array.getString( 0 ) );
   }

   public void testPut_String_null()
   {
      JSONArray array = new JSONArray();
      array.put( (String) null );
      Assertions.assertEquals( "", array.getString( 0 ) );
   }

   public void testToArray_bean_element()
   {
      BeanA[] expected = new BeanA[] { new BeanA() };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray, BeanA.class );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_boolean()
   {
      boolean[] expected = new boolean[] { true, false };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_Boolean()
   {
      Boolean[] expected = new Boolean[] { Boolean.TRUE, Boolean.FALSE };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_boolean_multi()
   {
      boolean[][] expected = new boolean[][] { { true, false }, { false, true } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_byte()
   {
      byte[] expected = new byte[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_Byte()
   {
      Byte[] expected = new Byte[] { new Byte( (byte) 1 ), new Byte( (byte) 2 ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_byte_multi()
   {
      byte[][] expected = new byte[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_char()
   {
      char[] expected = new char[] { 'A', 'B' };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_char_multi()
   {
      char[][] expected = new char[][] { { 'a', 'b' }, { 'c', 'd' } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_Character()
   {
      Character[] expected = new Character[] { new Character( 'A' ), new Character( 'B' ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_double()
   {
      double[] expected = new double[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_Double()
   {
      Double[] expected = new Double[] { new Double( 1d ), new Double( 2d ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_double_multi()
   {
      double[][] expected = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_dynabean_element() throws Exception
   {
      JSONDynaBean[] expected = new JSONDynaBean[] { createDynaBean() };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_float()
   {
      float[] expected = new float[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_Float()
   {
      Float[] expected = new Float[] { new Float( 1f ), new Float( 2f ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_float_multi()
   {
      float[][] expected = new float[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_int()
   {
      int[] expected = new int[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_int_multi()
   {
      int[][] expected = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_Integer()
   {
      Integer[] expected = new Integer[] { new Integer( 1 ), new Integer( 2 ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_long()
   {
      long[] expected = new long[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_Long()
   {
      Long[] expected = new Long[] { new Long( 1 ), new Long( 2 ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_long_multi()
   {
      long[][] expected = new long[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_null_elements()
   {
      String[] expected = new String[] { null, null, null };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_short()
   {
      short[] expected = new short[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_Short()
   {
      Short[] expected = new Short[] { new Short( (short) 1 ), new Short( (short) 2 ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_short_multi()
   {
      short[][] expected = new short[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_String()
   {
      String[] expected = new String[] { "1", "2", "3", "4", "5", "6" };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToArray_String_multi()
   {
      String[][] expected = new String[][] { { "1", "2", "3" }, { "4", "5", "6" } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToJSONObject()
   {
      JSONArray jsonArray = new JSONArray( "[\"json\",1,true]" );
      JSONObject expected = new JSONObject( "{\"string\":\"json\",\"int\":1,\"bool\":true}" );
      JSONArray names = new JSONArray( "[string,int,bool]" );

      Assertions.assertEquals( expected, jsonArray.toJSONObject( names ) );
   }

   public void testToJSONObject_null_object()
   {
      JSONArray jsonArray = new JSONArray();
      assertNull( jsonArray.toJSONObject( null ) );
      assertNull( jsonArray.toJSONObject( new JSONArray() ) );
      assertNull( jsonArray.toJSONObject( new JSONArray( "[json]" ) ) );
   }

   public void testToList_bean_elements()
   {
      List expected = new ArrayList();
      expected.add( new BeanA() );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray, BeanA.class );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_Boolean()
   {
      List expected = new ArrayList();
      expected.add( Boolean.TRUE );
      expected.add( Boolean.FALSE );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_Byte()
   {
      List expected = new ArrayList();
      expected.add( new Byte( (byte) 1 ) );
      expected.add( new Byte( (byte) 2 ) );
      expected.add( new Byte( (byte) 3 ) );
      expected.add( new Byte( (byte) 4 ) );
      expected.add( new Byte( (byte) 5 ) );
      expected.add( new Byte( (byte) 6 ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_Character()
   {
      List expected = new ArrayList();
      expected.add( new Character( 'A' ) );
      expected.add( new Character( 'B' ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_Double()
   {
      List expected = new ArrayList();
      expected.add( new Double( 1d ) );
      expected.add( new Double( 2d ) );
      expected.add( new Double( 3d ) );
      expected.add( new Double( 4d ) );
      expected.add( new Double( 5d ) );
      expected.add( new Double( 6d ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_dynaBean_elements() throws Exception
   {
      List expected = new ArrayList();
      expected.add( createDynaBean() );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_Float()
   {
      List expected = new ArrayList();
      expected.add( new Float( 1f ) );
      expected.add( new Float( 2f ) );
      expected.add( new Float( 3f ) );
      expected.add( new Float( 4f ) );
      expected.add( new Float( 5f ) );
      expected.add( new Float( 6f ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_Integer()
   {
      List expected = new ArrayList();
      expected.add( new Integer( 1 ) );
      expected.add( new Integer( 2 ) );
      expected.add( new Integer( 3 ) );
      expected.add( new Integer( 4 ) );
      expected.add( new Integer( 5 ) );
      expected.add( new Integer( 6 ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_JSONFunction_elements()
   {
      List expected = new ArrayList();
      expected.add( new JSONFunction( new String[] { "a" }, "return a;" ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_JSONFunction_elements_2()
   {
      List expected = new ArrayList();
      expected.add( "function(a){ return a; }" );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_Long()
   {
      List expected = new ArrayList();
      expected.add( new Long( 1 ) );
      expected.add( new Long( 2 ) );
      expected.add( new Long( 3 ) );
      expected.add( new Long( 4 ) );
      expected.add( new Long( 5 ) );
      expected.add( new Long( 6 ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_null_elements()
   {
      List expected = new ArrayList();
      expected.add( null );
      expected.add( null );
      expected.add( null );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_Short()
   {
      List expected = new ArrayList();
      expected.add( new Short( (short) 1 ) );
      expected.add( new Short( (short) 2 ) );
      expected.add( new Short( (short) 3 ) );
      expected.add( new Short( (short) 4 ) );
      expected.add( new Short( (short) 5 ) );
      expected.add( new Short( (short) 6 ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_String()
   {
      List expected = new ArrayList();
      expected.add( "A" );
      expected.add( "B" );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToList_String_multi()
   {
      List a = new ArrayList();
      a.add( "a" );
      a.add( "b" );
      List b = new ArrayList();
      b.add( "1" );
      b.add( "2" );
      List expected = new ArrayList();
      expected.add( a );
      expected.add( b );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testWrite()
   {
      JSONArray jsonArray = new JSONArray( "[[],{},1,true,\"json\"]" );
      StringWriter sw = new StringWriter();
      jsonArray.write( sw );
      assertEquals( "[[],{},1,true,\"json\"]", sw.toString() );
   }

   private JSONDynaBean createDynaBean() throws Exception
   {
      Map properties = new HashMap();
      properties.put( "name", String.class );
      JSONDynaClass dynaClass = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaBean dynaBean = (JSONDynaBean) dynaClass.newInstance();
      dynaBean.setDynamicFormClass( dynaClass );
      dynaBean.set( "name", "json" );
      // JSON Strings can not be null, only empty
      return dynaBean;
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