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

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.json.sample.BeanA;
import net.sf.json.sample.BeanB;
import net.sf.json.sample.BeanC;
import net.sf.json.sample.BeanWithFunc;

/**
 * @author Andres Almiray
 */
public class TestJSONObject extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestJSONObject.class );
   }

   public TestJSONObject( String testName )
   {
      super( testName );
   }

   public void testBeanWithFunc()
   {
      try{
         JSONObject json = JSONObject.fromObject( new BeanWithFunc( "return a;" ) );
         assertNotNull( json.get( "function" ) );
         assertTrue( JSONUtils.isFunction( json.get( "function" ) ) );
         assertEquals( "function(){ return a; }", json.get( "function" )
               .toString() );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testExtendedBean()
   {
      try{
         JSONObject json = JSONObject.fromObject( new BeanB() );
         assertEquals( true, json.getBoolean( "bool" ) );
         assertEquals( 42, json.getInt( "integer" ) );
         assertEquals( "json", json.getString( "string" ) );
         assertNotNull( json.get( "intarray" ) );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testMap()
   {
      Map map = new HashMap();
      map.put( "bool", Boolean.TRUE );
      map.put( "integer", new Integer( 42 ) );
      map.put( "string", "json" );
      try{
         JSONObject json = JSONObject.fromObject( map );
         assertEquals( true, json.getBoolean( "bool" ) );
         assertEquals( 42, json.getInt( "integer" ) );
         assertEquals( "json", json.getString( "string" ) );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testNestedBean()
   {
      try{
         JSONObject json = JSONObject.fromObject( new BeanC() );
         assertNotNull( json.get( "beanA" ) );
         assertNotNull( json.get( "beanB" ) );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testNestedNullObject()
   {
      Map map = new HashMap();
      map.put( "nested", null );
      map.put( "string", "json" );
      try{
         JSONObject json = JSONObject.fromMap( map );
         assertEquals( "json", json.getString( "string" ) );
         Object nested = json.get( "nested" );
         assertTrue( JSONUtils.isNull( nested ) );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testNullBean()
   {
      try{
         JSONObject json = JSONObject.fromBean( null );
         assertTrue( json.isNullObject() );
         assertEquals( JSONNull.getInstance()
               .toString(), json.toString() );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testNullMap()
   {
      try{
         JSONObject json = JSONObject.fromMap( null );
         assertTrue( json.isNullObject() );
         assertEquals( JSONNull.getInstance()
               .toString(), json.toString() );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testNullString()
   {
      try{
         JSONObject json = JSONObject.fromString( null );
         assertTrue( json.isNullObject() );
         assertEquals( JSONNull.getInstance()
               .toString(), json.toString() );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testSimpleBean()
   {
      try{
         JSONObject json = JSONObject.fromObject( new BeanA() );
         assertEquals( true, json.getBoolean( "bool" ) );
         assertEquals( 42, json.getInt( "integer" ) );
         assertEquals( "json", json.getString( "string" ) );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }
}