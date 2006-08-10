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
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.json.sample.BeanA;
import net.sf.json.sample.BeanB;
import net.sf.json.sample.BeanC;
import net.sf.json.sample.BeanWithFunc;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
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

   public void testFromBean_null_bean()
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

   public void testFromBean_use_wrappers()
   {
      JSONObject json = JSONObject.fromBean( Boolean.TRUE );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromBean( new Byte( Byte.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromBean( new Short( Short.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromBean( new Integer( Integer.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromBean( new Long( Long.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromBean( new Float( Float.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromBean( new Double( Double.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromBean( new Character( 'A' ) );
      assertTrue( json.isEmpty() );
   }

   public void testFromMap_nested_null_object()
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

   public void testFromMap_null_Map()
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

   public void testFromObject_BeanA()
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

   public void testFromObject_BeanWithFunc()
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

   public void testFromObject_ExtendedBean()
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

   public void testFromObject_Map()
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

   public void testFromObject_nested_bean()
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

   public void testFromObject_use_wrappers()
   {
      JSONObject json = JSONObject.fromObject( Boolean.TRUE );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromObject( new Byte( Byte.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromObject( new Short( Short.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromObject( new Integer( Integer.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromObject( new Long( Long.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromObject( new Float( Float.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromObject( new Double( Double.MIN_VALUE ) );
      assertTrue( json.isEmpty() );
      json = JSONObject.fromObject( new Character( 'A' ) );
      assertTrue( json.isEmpty() );
   }

   public void testFromString_null_String()
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

   public void testToBean() throws Exception
   {
      String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}";
      JSONObject jsonObject = new JSONObject( json );
      Object bean = JSONObject.toBean( jsonObject );
      assertEquals( jsonObject.get( "name" ), PropertyUtils.getProperty( bean, "name" ) );
      assertEquals( jsonObject.get( "bool" ), PropertyUtils.getProperty( bean, "bool" ) );
      assertEquals( jsonObject.get( "int" ), PropertyUtils.getProperty( bean, "int" ) );
      assertEquals( jsonObject.get( "double" ), PropertyUtils.getProperty( bean, "double" ) );
      assertEquals( jsonObject.get( "func" ), PropertyUtils.getProperty( bean, "func" ) );
      List expected = JSONArray.toList( jsonObject.getJSONArray( "array" ) );
      Assertions.assertListEquals( expected, (List) PropertyUtils.getProperty( bean, "array" ) );
   }

   public void testToBean_BeanA()
   {
      String json = "{bool:true,integer:1,string:\"json\"}";
      JSONObject jsonObject = new JSONObject( json );
      BeanA bean = (BeanA) JSONObject.toBean( jsonObject, BeanA.class );
      assertEquals( jsonObject.get( "bool" ), Boolean.valueOf( bean.isBool() ) );
      assertEquals( jsonObject.get( "integer" ), new Integer( bean.getInteger() ) );
      assertEquals( jsonObject.get( "string" ), bean.getString() );
   }

   public void testToBean_BeanB()
   {
      String json = "{bool:true,integer:1,string:\"json\",intarray:[4,5,6]}";
      JSONObject jsonObject = new JSONObject( json );
      BeanB bean = (BeanB) JSONObject.toBean( jsonObject, BeanB.class );
      assertEquals( jsonObject.get( "bool" ), Boolean.valueOf( bean.isBool() ) );
      assertEquals( jsonObject.get( "integer" ), new Integer( bean.getInteger() ) );
      assertEquals( jsonObject.get( "string" ), bean.getString() );
      Assertions.assertEquals( bean.getIntarray(),
            JSONArray.toArray( jsonObject.getJSONArray( "intarray" ) ) );
   }

   public void testToBean_nested() throws Exception
   {
      String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },nested:{nested:true}}";
      JSONObject jsonObject = new JSONObject( json );
      Object bean = JSONObject.toBean( jsonObject );
      assertEquals( jsonObject.get( "name" ), PropertyUtils.getProperty( bean, "name" ) );
      assertEquals( jsonObject.get( "bool" ), PropertyUtils.getProperty( bean, "bool" ) );
      assertEquals( jsonObject.get( "int" ), PropertyUtils.getProperty( bean, "int" ) );
      assertEquals( jsonObject.get( "double" ), PropertyUtils.getProperty( bean, "double" ) );
      assertEquals( jsonObject.get( "func" ), PropertyUtils.getProperty( bean, "func" ) );
      JSONObject nestedJson = jsonObject.getJSONObject( "nested" );
      Object nestedBean = PropertyUtils.getProperty( bean, "nested" );
      assertEquals( nestedJson.get( "nested" ), PropertyUtils.getProperty( nestedBean, "nested" ) );
   }

   public void testToBean_null()
   {
      JSONObject jsonObject = new JSONObject( true );
      BeanA bean = (BeanA) JSONObject.toBean( jsonObject, BeanA.class );
      assertNull( bean );
   }
}