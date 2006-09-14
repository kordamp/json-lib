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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.json.sample.BeanA;
import net.sf.json.sample.BeanB;
import net.sf.json.sample.BeanC;
import net.sf.json.sample.BeanFoo;
import net.sf.json.sample.BeanWithFunc;
import net.sf.json.sample.MappingBean;
import net.sf.json.sample.FieldTestBean;
import net.sf.json.sample.ValueBean;
import net.sf.json.util.JSONDynaBean;
import net.sf.json.util.JSONDynaClass;
import net.sf.json.util.JSONTokener;

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

   public void testConstructor_Object_String_Array__bean()
   {
      BeanA beanA = new BeanA();
      JSONObject jsonObject = new JSONObject( beanA, new String[] { "bool", "integer" } );
      assertEquals( true, jsonObject.getBoolean( "bool" ) );
      assertEquals( 42, jsonObject.getInt( "integer" ) );
   }

   public void testConstructor_Object_String_Array__DynaBean() throws Exception
   {
      Map properties = new HashMap();
      properties.put( "bool", Boolean.class );
      properties.put( "integer", Integer.class );
      JSONDynaClass dynaClass = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaBean dynaBean = (JSONDynaBean) dynaClass.newInstance();
      dynaBean.setDynamicFormClass( dynaClass );
      dynaBean.set( "bool", Boolean.TRUE );
      dynaBean.set( "integer", new Integer( 42 ) );

      JSONObject jsonObject = new JSONObject( dynaBean, new String[] { "bool", "integer" } );
      assertEquals( true, jsonObject.getBoolean( "bool" ) );
      assertEquals( 42, jsonObject.getInt( "integer" ) );
   }

   public void testFromBean_array()
   {
      try{
         JSONObject.fromBean( new ArrayList() );
         fail( "Expected a JSONException" );
      }
      catch( IllegalArgumentException expected ){
         // OK
      }

      try{
         JSONObject.fromBean( new String[] { "json" } );
         fail( "Expected a JSONException" );
      }
      catch( IllegalArgumentException expected ){
         // OK
      }
   }

   public void testFromBean_DynaBean() throws Exception
   {
      JSONObject json = JSONObject.fromBean( createDynaBean() );
      assertEquals( "json", json.getString( "name" ) );
   }

   public void testFromBean_JSONObject()
   {
      JSONObject json = new JSONObject();
      json.put( "name", "json" );
      Assertions.assertEquals( json, JSONObject.fromBean( json ) );
   }

   public void testFromBean_JSONTokener()
   {
      try{
         JSONTokener jsonTokener = new JSONTokener( "{\"string\":\"json\"}" );
         JSONObject json = JSONObject.fromBean( jsonTokener );
         assertEquals( "json", json.getString( "string" ) );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testFromBean_Map()
   {
      Map map = new HashMap();
      map.put( "bool", Boolean.TRUE );
      map.put( "integer", new Integer( 42 ) );
      map.put( "string", "json" );
      try{
         JSONObject json = JSONObject.fromBean( map );
         assertEquals( true, json.getBoolean( "bool" ) );
         assertEquals( 42, json.getInt( "integer" ) );
         assertEquals( "json", json.getString( "string" ) );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testFromBean_null()
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

   public void testFromBean_String()
   {
      try{
         JSONObject json = JSONObject.fromBean( "{\"string\":\"json\"}" );
         assertEquals( "json", json.getString( "string" ) );
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

   public void testFromDynaBean_full() throws Exception
   {
      Map properties = new HashMap();
      properties.put( "string", String.class );
      properties.put( "number", Integer.class );
      properties.put( "array", Object[].class );
      properties.put( "list", List.class );
      properties.put( "func", JSONFunction.class );
      properties.put( "boolean", Boolean.class );
      properties.put( "bean", BeanA.class );
      JSONDynaClass dynaClass = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaBean dynaBean = (JSONDynaBean) dynaClass.newInstance();
      dynaBean.setDynamicFormClass( dynaClass );
      dynaBean.set( "string", "json" );
      dynaBean.set( "number", new Double( 2 ) );
      dynaBean.set( "array", new Integer[] { new Integer( 1 ), new Integer( 2 ) } );
      dynaBean.set( "list", new ArrayList() );
      dynaBean.set( "func", new JSONFunction( new String[] { "a" }, "return a;" ) );
      dynaBean.set( "boolean", Boolean.TRUE );
      dynaBean.set( "bean", new BeanA() );

      JSONObject jsonObject = JSONObject.fromDynaBean( dynaBean );
      assertEquals( "json", jsonObject.get( "string" ) );
      assertEquals( new Double( 2 ), jsonObject.get( "number" ) );
      assertEquals( Boolean.TRUE, jsonObject.get( "boolean" ) );
      Assertions.assertEquals( "function(a){ return a; }", (JSONFunction) jsonObject.get( "func" ) );
   }

   public void testFromDynaBean_null()
   {
      JSONObject jsonObject = JSONObject.fromDynaBean( null );
      assertTrue( jsonObject.isNullObject() );
   }

   public void testFromJSONTokener()
   {
      JSONTokener jsonTokener = new JSONTokener( "{\"string\":\"json\"}" );
      JSONObject json = new JSONObject( jsonTokener );
      assertEquals( "json", json.getString( "string" ) );
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

   public void testFromObject_array()
   {
      try{
         JSONObject.fromObject( new ArrayList() );
         fail( "Expected a JSONException" );
      }
      catch( IllegalArgumentException expected ){
         // OK
      }

      try{
         JSONObject.fromObject( new String[] { "json" } );
         fail( "Expected a JSONException" );
      }
      catch( IllegalArgumentException expected ){
         // OK
      }
   }

   public void testFromObject_Bean()
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

   public void testFromObject_Bean__transient_fields()
   {
      try{
         FieldTestBean bean = new FieldTestBean();
         bean.setString( "json" );
         bean.setTransientString( "transient" );
         bean.setVolatileString( "volatile" );
         JSONObject json = JSONObject.fromObject( bean );
         assertEquals( "json", json.getString( "string" ) );
         assertFalse(json.has( "transientString" ));
         assertFalse(json.has( "volatileString" ));
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

   public void testFromObject_DynaBean() throws Exception
   {
      JSONObject json = JSONObject.fromObject( createDynaBean() );
      assertEquals( "json", json.getString( "name" ) );
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

   public void testFromObject_JSONTokener()
   {
      try{
         JSONTokener jsonTokener = new JSONTokener( "{\"string\":\"json\"}" );
         JSONObject json = JSONObject.fromObject( jsonTokener );
         assertEquals( "json", json.getString( "string" ) );
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
      map.put( "array", new JSONArray( "[1]" ) );
      map.put( "object", new JSONObject( "{\"name\":\"json\"}" ) );
      try{
         JSONObject json = JSONObject.fromObject( map );
         assertEquals( true, json.getBoolean( "bool" ) );
         assertEquals( 42, json.getInt( "integer" ) );
         assertEquals( "json", json.getString( "string" ) );
         Assertions.assertEquals( new JSONArray( "[1]" ), json.getJSONArray( "array" ) );
         Assertions.assertEquals( new JSONObject( "{\"name\":\"json\"}" ),
               json.getJSONObject( "object" ) );
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

   public void testFromObject_null()
   {
      try{
         JSONObject json = JSONObject.fromObject( null );
         assertTrue( json.isNullObject() );
         assertEquals( JSONNull.getInstance()
               .toString(), json.toString() );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testFromObject_String()
   {
      try{
         JSONObject json = JSONObject.fromObject( "{\"string\":\"json\"}" );
         assertEquals( "json", json.getString( "string" ) );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }

   public void testFromObject_toBean_DynaBean()
   {
      // bug report 1540137

      String jsondata = "{\"person\":{\"phone\":[\"111-222-3333\",\"777-888-9999\"],"
            + "\"address\":{\"street\":\"123 somewhere place\",\"zip\":\"30005\",\"city\":\"Alpharetta\"},"
            + "\"email\":[\"allen@work.com\",\"allen@home.net\"],\"name\":\"Allen\"}}";

      JSONObject jsonobj = JSONObject.fromString( jsondata );
      Object bean = JSONObject.toBean( jsonobj );
      // bean is a DynaBean
      assertTrue( bean instanceof JSONDynaBean );
      // convert the DynaBean to a JSONObject again
      JSONObject jsonobj2 = JSONObject.fromBean( bean );

      assertNotNull( jsonobj.getJSONObject( "person" ) );
      assertFalse( JSONUtils.isNull( jsonobj.getJSONObject( "person" ) ) );
      assertNotNull( jsonobj2.getJSONObject( "person" ) );
      assertFalse( JSONUtils.isNull( jsonobj2.getJSONObject( "person" ) ) );

      JSONObject person1 = jsonobj.getJSONObject( "person" );
      JSONObject person2 = jsonobj2.getJSONObject( "person" );
      assertEquals( person1.get( "name" ), person2.get( "name" ) );
      assertEquals( person1.get( "phone" )
            .toString(), person2.get( "phone" )
            .toString() );
      assertEquals( person1.get( "email" )
            .toString(), person2.get( "email" )
            .toString() );
      JSONObject address1 = person1.getJSONObject( "address" );
      JSONObject address2 = person2.getJSONObject( "address" );
      assertEquals( address1.get( "street" ), address2.get( "street" ) );
      assertEquals( address1.get( "zip" ), address2.get( "zip" ) );
      assertEquals( address1.get( "city" ), address2.get( "city" ) );
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
      Assertions.assertEquals( expected, (List) PropertyUtils.getProperty( bean, "array" ) );
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

   public void testToBean_interface()
   {
      // BUG 1542104

      try{
         JSONObject.toBean( new JSONObject( "{\"int\":1}" ), Serializable.class );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testToBean_Map()
   {
      // BUG 1542104

      Map map = new HashMap();
      map.put( "name", "json" );
      Object obj = JSONObject.toBean( new JSONObject( map ), Map.class );
      assertTrue( obj instanceof Map );
      assertEquals( map.get( "name" ), ((Map) obj).get( "name" ) );
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

   public void testToBean_nested_beans__null_object() throws Exception
   {
      // BUG 1553617

      String json = "{\"beanA\":{bool:true,integer:1,string:\"jsonbean\"},\"beanB\":null}";
      JSONObject jsonObject = new JSONObject( json );
      BeanC bean = (BeanC) JSONObject.toBean( jsonObject, BeanC.class );
      assertNotNull( bean );
      BeanA beanA = bean.getBeanA();
      assertNotNull( beanA );
      assertEquals( true, beanA.isBool() );
      assertEquals( 1, beanA.getInteger() );
      assertEquals( "jsonbean", beanA.getString() );
      BeanB beanB = bean.getBeanB();
      assertNull( beanB );
   }

   public void testToBean_nested_beans_in_map__beans()
   {
      // BUG 1542092

      MappingBean mappingBean = new MappingBean();

      ValueBean beanA = new ValueBean();
      beanA.setValue( 90000 );
      ValueBean beanB = new ValueBean();
      beanB.setValue( 91000 );

      mappingBean.addAttribute( "beanA", beanA );
      mappingBean.addAttribute( "beanB", beanB );

      JSONObject jsonObject = JSONObject.fromObject( mappingBean );
      Map classMap = new HashMap();
      classMap.put( "bean.*", ValueBean.class );
      MappingBean mappingBean2 = (MappingBean) JSONObject.toBean( jsonObject, MappingBean.class,
            classMap );
      Object ba = mappingBean2.getAttributes()
            .get( "beanA" );
      Object bb = mappingBean2.getAttributes()
            .get( "beanB" );
      assertTrue( ba instanceof ValueBean );
      assertTrue( bb instanceof ValueBean );
      assertEquals( beanA.getValue(), ((ValueBean) ba).getValue() );
      assertEquals( beanB.getValue(), ((ValueBean) bb).getValue() );
   }

   public void testToBean_nested_beans_in_map__DynaBean()
   {
      // BUG 1542092

      MappingBean mappingBean = new MappingBean();

      ValueBean beanA = new ValueBean();
      beanA.setValue( 90000 );
      ValueBean beanB = new ValueBean();
      beanB.setValue( 91000 );

      mappingBean.addAttribute( "beanA", beanA );
      mappingBean.addAttribute( "beanB", beanB );

      JSONObject jsonObject = JSONObject.fromObject( mappingBean );
      MappingBean mappingBean2 = (MappingBean) JSONObject.toBean( jsonObject, MappingBean.class );
      Object ba = mappingBean2.getAttributes()
            .get( "beanA" );
      Object bb = mappingBean2.getAttributes()
            .get( "beanB" );
      assertTrue( ba instanceof JSONDynaBean );
      assertTrue( bb instanceof JSONDynaBean );
      assertEquals( new Integer( beanA.getValue() ), ((JSONDynaBean) ba).get( "value" ) );
      assertEquals( new Integer( beanB.getValue() ), ((JSONDynaBean) bb).get( "value" ) );
   }

   public void testToBean_nested_dynabeans__null_object() throws Exception
   {
      // BUG 1553617

      String json = "{\"beanA\":{bool:true,integer:1,string:\"jsonbean\"},\"beanB\":null}";
      JSONObject jsonObject = new JSONObject( json );
      Object bean = JSONObject.toBean( jsonObject );
      assertNotNull( bean );
      Object beanA = PropertyUtils.getProperty( bean, "beanA" );
      assertNotNull( beanA );
      assertEquals( Boolean.TRUE, PropertyUtils.getProperty( beanA, "bool" ) );
      assertEquals( new Integer( 1 ), PropertyUtils.getProperty( beanA, "integer" ) );
      assertEquals( "jsonbean", PropertyUtils.getProperty( beanA, "string" ) );
      Object beanB = PropertyUtils.getProperty( bean, "beanB" );
      assertNull( beanB );
   }

   public void testToBean_null()
   {
      assertNull( JSONObject.toBean( null ) );
   }

   public void testToBean_null_2()
   {
      assertNull( JSONObject.toBean( null, BeanA.class ) );
   }

   public void testToBean_null_object()
   {
      JSONObject jsonObject = new JSONObject( true );
      BeanA bean = (BeanA) JSONObject.toBean( jsonObject, BeanA.class );
      assertNull( bean );
   }

   public void testToBean_null_values()
   {
      // bug report 1540196

      String json = "{\"items\":[[\"000\"],[\"010\", \"011\"],[\"020\"]]}";
      JSONObject jsonObject = JSONObject.fromString( json );

      BeanFoo foo = (BeanFoo) JSONObject.toBean( jsonObject, BeanFoo.class );
      assertNotNull( foo );
      assertNotNull( foo.getItems() );
      String[][] items = foo.getItems();
      assertEquals( 3, items.length );
      assertEquals( "000", items[0][0] );
      assertEquals( "010", items[1][0] );
      assertEquals( "011", items[1][1] );
      assertEquals( "020", items[2][0] );
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
}