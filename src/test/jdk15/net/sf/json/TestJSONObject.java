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
import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.ezmorph.MorphUtils;
import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.ezmorph.bean.MorphDynaClass;
import net.sf.ezmorph.test.ArrayAssertions;
import net.sf.json.sample.AnnotationBean;
import net.sf.json.sample.BeanA;
import net.sf.json.sample.BeanB;
import net.sf.json.sample.BeanC;
import net.sf.json.sample.BeanFoo;
import net.sf.json.sample.BeanWithFunc;
import net.sf.json.sample.ClassBean;
import net.sf.json.sample.EmptyBean;
import net.sf.json.sample.JavaIdentifierBean;
import net.sf.json.sample.EnumBean;
import net.sf.json.sample.JsonEnum;
import net.sf.json.sample.ListingBean;
import net.sf.json.sample.MappingBean;
import net.sf.json.sample.NumberBean;
import net.sf.json.sample.ObjectBean;
import net.sf.json.sample.ObjectJSONStringBean;
import net.sf.json.sample.PropertyBean;
import net.sf.json.sample.ValueBean;
import net.sf.json.util.EnumMorpher;
import net.sf.json.util.JSONTokener;
import net.sf.json.util.JSONUtils;
import net.sf.json.util.JavaIdentifierTransformer;

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

   public void testAccumulate()
   {
      JSONObject json = new JSONObject();
      json.accumulate( "key", "1" );
      Assertions.assertEquals( 1, json.getInt( "key" ) );
      json.accumulate( "key", "2" );
      Assertions.assertEquals( JSONArray.fromObject( "['1','2']" ), json.getJSONArray( "key" ) );
      json.accumulate( "key", "3" );
      Assertions.assertEquals( JSONArray.fromObject( "['1','2','3']" ), json.getJSONArray( "key" ) );
   }

   public void testAccumulate__nullObject()
   {
      try{
         new JSONObject( true ).accumulate( "key", "value" );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testConstructor_Object__nullJSONObject()
   {
      JSONObject jsonObject = JSONObject.fromJSONObject( (JSONObject) null );
      assertTrue( jsonObject.isNullObject() );
   }

   public void testConstructor_Object_String_Array__nullObject()
   {
      JSONObject jsonObject = JSONObject.fromObject( (Object) null, new String[] { "bool",
            "integer" } );
      assertTrue( jsonObject.isNullObject() );
   }

   public void testFromBean_AnnotationBean()
   {
      AnnotationBean bean = new AnnotationBean();
      Annotation[] annotations = bean.getClass()
            .getAnnotations();
      try{
         JSONObject.fromBean( annotations[0] );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testFromBean_array()
   {
      try{
         JSONObject.fromBean( new ArrayList() );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }

      try{
         JSONObject.fromBean( new String[] { "json" } );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testFromBean_ClassBean()
   {
      ClassBean classBean = new ClassBean();
      classBean.setKlass( Object.class );
      JSONObject json = JSONObject.fromBean( classBean );
      assertEquals( "java.lang.Object", json.get( "klass" ) );
   }

   public void testFromBean_DynaBean() throws Exception
   {
      JSONObject json = JSONObject.fromBean( createDynaBean() );
      assertEquals( "json", json.getString( "name" ) );
      Assertions.assertEquals( JSONArray.fromObject( "[1,2]" ), json.getJSONArray( "str" ) );
      Assertions.assertEquals( JSONObject.fromObject( "{'id':'1'}" ), json.getJSONObject( "json" ) );
      Assertions.assertEquals( JSONObject.fromObject( "{'name':''}" ),
            json.getJSONObject( "jsonstr" ) );
      Assertions.assertEquals( "function(){ return this; }", (JSONFunction) json.get( "func" ) );
   }

   public void testFromBean_Enum()
   {
      try{
         JSONObject.fromBean( JsonEnum.OBJECT );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testFromBean_EnumBean()
   {
      EnumBean bean = new EnumBean();
      bean.setJsonEnum( JsonEnum.OBJECT );
      bean.setString( "string" );
      JSONObject json = JSONObject.fromBean( bean );
      assertNotNull( json );
      assertEquals( JsonEnum.OBJECT.toString(), json.get( "jsonEnum" ) );
      assertEquals( "string", json.get( "string" ) );
   }

   public void testFromBean_JSONObject()
   {
      JSONObject json = new JSONObject();
      json.put( "name", "json" );
      Assertions.assertEquals( json, JSONObject.fromBean( json ) );
   }

   public void testFromBean_JSONString()
   {
      ObjectJSONStringBean bean = new ObjectJSONStringBean();
      bean.setId( 1 );
      bean.setName( "json" );
      JSONObject json = JSONObject.fromBean( bean );
      assertEquals( "json", json.getString( "name" ) );
      assertTrue( !json.has( "id" ) );
   }

   public void testFromBean_JSONTokener()
   {
      JSONTokener jsonTokener = new JSONTokener( "{\"string\":\"json\"}" );
      JSONObject json = JSONObject.fromBean( jsonTokener );
      assertEquals( "json", json.getString( "string" ) );
   }

   public void testFromBean_Map()
   {
      Map map = new HashMap();
      map.put( "bool", Boolean.TRUE );
      map.put( "integer", new Integer( 42 ) );
      map.put( "string", "json" );

      JSONObject json = JSONObject.fromBean( map );
      assertEquals( true, json.getBoolean( "bool" ) );
      assertEquals( 42, json.getInt( "integer" ) );
      assertEquals( "json", json.getString( "string" ) );
   }

   public void testFromBean_noReadMethod()
   {
      JSONObject json = JSONObject.fromBean( new PropertyBean() );
      assertTrue( json.has( "propertyWithNoWriteMethod" ) );
      assertTrue( !json.has( "propertyWithNoReadMethod" ) );
   }

   public void testFromBean_null()
   {
      JSONObject json = JSONObject.fromBean( null );
      assertTrue( json.isNullObject() );
      assertEquals( JSONNull.getInstance()
            .toString(), json.toString() );
   }

   public void testFromBean_String()
   {
      JSONObject json = JSONObject.fromBean( "{\"string\":\"json\"}" );
      assertEquals( "json", json.getString( "string" ) );
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
      MorphDynaClass dynaClass = new MorphDynaClass( "JSON", MorphDynaBean.class, properties );
      MorphDynaBean dynaBean = (MorphDynaBean) dynaClass.newInstance();
      dynaBean.setDynaBeanClass( dynaClass );
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
      JSONObject json = JSONObject.fromObject( jsonTokener );
      assertEquals( "json", json.getString( "string" ) );
   }

   public void testFromMap_nested_null_object()
   {
      Map map = new HashMap();
      map.put( "nested", null );
      map.put( "string", "json" );

      JSONObject json = JSONObject.fromMap( map );
      assertEquals( "json", json.getString( "string" ) );
      Object nested = json.get( "nested" );
      assertTrue( JSONUtils.isNull( nested ) );
   }

   public void testFromMap_null_Map()
   {
      JSONObject json = JSONObject.fromMap( null );
      assertTrue( json.isNullObject() );
      assertEquals( JSONNull.getInstance()
            .toString(), json.toString() );
   }

   public void testFromObject_AnnotationBean()
   {
      AnnotationBean bean = new AnnotationBean();
      Annotation[] annotations = bean.getClass()
            .getAnnotations();
      try{
         JSONObject.fromObject( annotations[0] );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testFromObject_array()
   {
      try{
         JSONObject.fromObject( new ArrayList() );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }

      try{
         JSONObject.fromObject( new String[] { "json" } );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // OK
      }
   }

   public void testFromObject_Bean()
   {
      JSONObject json = JSONObject.fromObject( new BeanA() );
      assertEquals( true, json.getBoolean( "bool" ) );
      assertEquals( 42, json.getInt( "integer" ) );
      assertEquals( "json", json.getString( "string" ) );
   }

   public void testFromObject_BeanWithFunc()
   {
      JSONObject json = JSONObject.fromObject( new BeanWithFunc( "return a;" ) );
      assertNotNull( json.get( "function" ) );
      assertTrue( JSONUtils.isFunction( json.get( "function" ) ) );
      assertEquals( "function(){ return a; }", json.get( "function" )
            .toString() );
   }

   public void testFromObject_DynaBean() throws Exception
   {
      JSONObject json = JSONObject.fromObject( createDynaBean() );
      assertEquals( "json", json.getString( "name" ) );
   }

   public void testFromObject_DynaBean__Enum() throws Exception
   {
      Map properties = new HashMap();
      properties.put( "jsonEnum", JsonEnum.class );
      MorphDynaClass dynaClass = new MorphDynaClass( properties );
      MorphDynaBean dynaBean = (MorphDynaBean) dynaClass.newInstance();
      dynaBean.setDynaBeanClass( dynaClass );
      dynaBean.set( "jsonEnum", JsonEnum.OBJECT );
      JSONObject json = JSONObject.fromObject( dynaBean );
      assertNotNull( json );
      assertEquals( JsonEnum.OBJECT.toString(), json.get( "jsonEnum" ) );
   }

   public void testFromObject_emptyBean()
   {
      EmptyBean bean = new EmptyBean();
      JSONObject json = JSONObject.fromObject( bean );
      JSONObject expected = new JSONObject();
      expected.put( "arrayp", new JSONArray() );
      expected.put( "listp", new JSONArray() );
      expected.put( "bytep", new Integer( 0 ) );
      expected.put( "shortp", new Integer( 0 ) );
      expected.put( "intp", new Integer( 0 ) );
      expected.put( "longp", new Integer( 0 ) );
      expected.put( "floatp", new Integer( 0 ) );
      expected.put( "doublep", new Double( 0 ) );
      expected.put( "charp", "" );
      expected.put( "stringp", "" );

      Assertions.assertEquals( expected, json );
   }

   public void testFromObject_Enum()
   {
      try{
         JSONObject.fromObject( JsonEnum.OBJECT );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testFromObject_EnumBean()
   {
      EnumBean bean = new EnumBean();
      bean.setJsonEnum( JsonEnum.OBJECT );
      bean.setString( "string" );
      JSONObject json = JSONObject.fromObject( bean );
      assertNotNull( json );
      assertEquals( JsonEnum.OBJECT.toString(), json.get( "jsonEnum" ) );
      assertEquals( "string", json.get( "string" ) );
   }

   public void testFromObject_ExtendedBean()
   {
      JSONObject json = JSONObject.fromObject( new BeanB() );
      assertEquals( true, json.getBoolean( "bool" ) );
      assertEquals( 42, json.getInt( "integer" ) );
      assertEquals( "json", json.getString( "string" ) );
      assertNotNull( json.get( "intarray" ) );
   }

   public void testFromObject_JSONObject()
   {
      JSONObject expected = new JSONObject().put( "id", "1" )
            .put( "name", "json" );
      JSONObject actual = JSONObject.fromObject( expected );
      Assertions.assertEquals( expected, actual );
   }

   public void testFromObject_JSONString()
   {
      ObjectJSONStringBean bean = new ObjectJSONStringBean();
      bean.setId( 1 );
      bean.setName( "json" );
      JSONObject json = JSONObject.fromObject( bean );
      assertEquals( "json", json.getString( "name" ) );
      assertTrue( !json.has( "id" ) );
   }

   public void testFromObject_JSONTokener()
   {
      JSONTokener jsonTokener = new JSONTokener( "{\"string\":\"json\"}" );
      JSONObject json = JSONObject.fromObject( jsonTokener );
      assertEquals( "json", json.getString( "string" ) );
   }

   public void testFromObject_Map()
   {
      Map map = new HashMap();
      map.put( "bool", Boolean.TRUE );
      map.put( "integer", new Integer( 42 ) );
      map.put( "string", "json" );
      map.put( "array", JSONArray.fromObject( "[1]" ) );
      map.put( "object", JSONObject.fromObject( "{\"name\":\"json\"}" ) );

      JSONObject json = JSONObject.fromObject( map );
      assertEquals( true, json.getBoolean( "bool" ) );
      assertEquals( 42, json.getInt( "integer" ) );
      assertEquals( "json", json.getString( "string" ) );
      Assertions.assertEquals( JSONArray.fromObject( "[1]" ), json.getJSONArray( "array" ) );
      Assertions.assertEquals( JSONObject.fromObject( "{\"name\":\"json\"}" ),
            json.getJSONObject( "object" ) );
   }

   public void testFromObject_Map__Enum()
   {
      Map properties = new HashMap();
      properties.put( "jsonEnum", JsonEnum.OBJECT );
      JSONObject json = JSONObject.fromObject( properties );
      assertNotNull( json );
      assertEquals( JsonEnum.OBJECT.toString(), json.get( "jsonEnum" ) );
   }

   public void testFromObject_nested_bean()
   {
      JSONObject json = JSONObject.fromObject( new BeanC() );
      assertNotNull( json.get( "beanA" ) );
      assertNotNull( json.get( "beanB" ) );
   }

   public void testFromObject_null()
   {
      JSONObject json = JSONObject.fromObject( null );
      assertTrue( json.isNullObject() );
      assertEquals( JSONNull.getInstance()
            .toString(), json.toString() );
   }

   public void testFromObject_ObjectBean()
   {
      // FR 1611204
      ObjectBean bean = new ObjectBean();
      bean.setPbyte( Byte.valueOf( "1" ) );
      bean.setPshort( Short.valueOf( "1" ) );
      bean.setPint( Integer.valueOf( "1" ) );
      bean.setPlong( Long.valueOf( "1" ) );
      bean.setPfloat( Float.valueOf( "1" ) );
      bean.setPdouble( Double.valueOf( "1" ) );
      bean.setPchar( new Character( '1' ) );
      bean.setPboolean( Boolean.TRUE );
      bean.setPstring( "json" );
      bean.setParray( new String[] { "a", "b" } );
      bean.setPbean( new BeanA() );
      List list = new ArrayList();
      list.add( "1" );
      list.add( "2" );
      bean.setPlist( list );
      Map map = new HashMap();
      map.put( "string", "json" );
      bean.setPmap( map );
      bean.setPfunction( new JSONFunction( "this;" ) );

      JSONObject json = JSONObject.fromObject( bean );
      assertEquals( 1, json.getInt( "pbyte" ) );
      assertEquals( 1, json.getInt( "pshort" ) );
      assertEquals( 1, json.getInt( "pint" ) );
      assertEquals( 1, json.getInt( "plong" ) );
      assertEquals( 1d, json.getDouble( "pfloat" ), 0d );
      assertEquals( 1d, json.getDouble( "pdouble" ), 0d );
      assertTrue( json.getBoolean( "pboolean" ) );
      assertEquals( "json", json.get( "pstring" ) );
      Assertions.assertEquals( JSONArray.fromObject( "['a','b']" ), json.getJSONArray( "parray" ) );
      Assertions.assertEquals( JSONArray.fromObject( "['1','2']" ), json.getJSONArray( "plist" ) );
      assertEquals( "1", json.getString( "pchar" ) );
      JSONObject b = new JSONObject().put( "string", "json" )
            .put( "integer", "42" )
            .put( "bool", "true" );
      Assertions.assertEquals( b, json.getJSONObject( "pbean" ) );
      b = new JSONObject().put( "string", "json" );
      Assertions.assertEquals( b, json.getJSONObject( "pmap" ) );
   }

   public void testFromObject_ObjectBean_empty()
   {
      // FR 1611204
      ObjectBean bean = new ObjectBean();
      JSONObject json = JSONObject.fromObject( bean );

      String[] keys = { "pbyte", "pshort", "pint", "plong", "pfloat", "pdouble", "pboolean",
            "pchar", "pstring", "parray", "plist", "pmap", "pbean" };
      for( int i = 0; i < keys.length; i++ ){
         assertTrue( JSONNull.getInstance()
               .equals( json.get( keys[i] ) ) );
      }
   }

   public void testFromObject_String()
   {
      JSONObject json = JSONObject.fromObject( "{\"string\":\"json\"}" );
      assertEquals( "json", json.getString( "string" ) );
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
      assertTrue( bean instanceof MorphDynaBean );
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
      JSONObject json = JSONObject.fromString( null );
      assertTrue( json.isNullObject() );
      assertEquals( JSONNull.getInstance()
            .toString(), json.toString() );
   }

   public void testHas()
   {
      assertFalse( new JSONObject().has( "any" ) );
      assertTrue( new JSONObject().put( "any", "value" )
            .has( "any" ) );
   }

   public void testLength()
   {
      assertEquals( 0, new JSONObject().length() );
   }

   public void testLength_nullObject()
   {
      try{
         new JSONObject( true ).length();
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testOptBoolean()
   {
      assertFalse( new JSONObject().optBoolean( "any" ) );
   }

   public void testOptBoolean_defaultValue()
   {
      assertTrue( new JSONObject().optBoolean( "any", true ) );
   }

   public void testOptDouble()
   {
      assertTrue( Double.isNaN( new JSONObject().optDouble( "any" ) ) );
   }

   public void testOptDouble_defaultValue()
   {
      assertEquals( 2d, new JSONObject().optDouble( "any", 2d ), 0d );
   }

   public void testOptInt()
   {
      assertEquals( 0, new JSONObject().optInt( "any" ) );
   }

   public void testOptInt_defaultValue()
   {
      assertEquals( 1, new JSONObject().optInt( "any", 1 ) );
   }

   public void testOptJSONArray()
   {
      JSONObject json = new JSONObject();
      assertNull( json.optJSONArray( "a" ) );
      json.put( "a", "[]" );
      Assertions.assertEquals( new JSONArray(), json.optJSONArray( "a" ) );
   }

   public void testOptJSONObject()
   {
      JSONObject json = new JSONObject();
      assertNull( json.optJSONObject( "a" ) );
      json.put( "a", "{}" );
      Assertions.assertEquals( new JSONObject(), json.optJSONObject( "a" ) );
   }

   public void testOptLong()
   {
      assertEquals( 0L, new JSONObject().optLong( "any" ) );
   }

   public void testOptLong_defaultValue()
   {
      assertEquals( 1L, new JSONObject().optLong( "any", 1L ) );
   }

   public void testOptString()
   {
      assertEquals( "", new JSONObject().optString( "any" ) );
   }

   public void testOptString_defaultValue()
   {
      assertEquals( "json", new JSONObject().optString( "any", "json" ) );
   }

   public void testPut_Annotation()
   {
      AnnotationBean bean = new AnnotationBean();
      Annotation[] annotations = bean.getClass()
            .getAnnotations();
      try{
         JSONObject jsonObject = new JSONObject();
         jsonObject.put( "annotation", annotations[0] );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testPut__duplicateProperty()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "duplicated", "json1" );
      jsonObject.put( "duplicated", "json2" );
      Object o = jsonObject.get( "duplicated" );
      assertTrue( o instanceof JSONArray );
      JSONArray jsonArray = (JSONArray) o;
      assertEquals( "json1", jsonArray.get( 0 ) );
      assertEquals( "json2", jsonArray.get( 1 ) );
   }

   public void testPut__duplicateProperty_2()
   {
      JSONObject jsonObject = JSONObject.fromObject( "{'duplicated':'json1','duplicated':'json2'}" );
      Object o = jsonObject.get( "duplicated" );
      assertTrue( o instanceof JSONArray );
      JSONArray jsonArray = (JSONArray) o;
      assertEquals( "json1", jsonArray.get( 0 ) );
      assertEquals( "json2", jsonArray.get( 1 ) );
   }

   public void testPut_Bean()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "bean", new ObjectBean() );
      JSONObject actual = jsonObject.getJSONObject( "bean" );
      Assertions.assertTrue( !actual.has( "class" ) );
   }

   public void testPut_Bean_exclusions()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "bean", new ObjectBean(), new String[] { "pexcluded" } );
      JSONObject actual = jsonObject.getJSONObject( "bean" );
      Assertions.assertTrue( !actual.has( "class" ) );
      Assertions.assertTrue( !actual.has( "pexcluded" ) );
   }

   public void testPut_Bean_exclusions_ignoreDefault()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "bean", new ObjectBean(), new String[] { "pexcluded" }, true );
      JSONObject actual = jsonObject.getJSONObject( "bean" );
      Assertions.assertTrue( actual.has( "class" ) );
      Assertions.assertTrue( !actual.has( "pexcluded" ) );
   }

   public void testPut_boolean()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "bool", true );
      assertTrue( jsonObject.getBoolean( "bool" ) );
   }

   public void testPut_Boolean()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "bool", Boolean.TRUE );
      Assertions.assertTrue( jsonObject.getBoolean( "bool" ) );
   }

   public void testPut_Class()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "class", Object.class );
      assertEquals( "java.lang.Object", jsonObject.get( "class" ) );
   }

   public void testPut_Collection()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "list", Collections.EMPTY_LIST );
      Assertions.assertEquals( new JSONArray(), jsonObject.getJSONArray( "list" ) );
   }

   public void testPut_Collection2()
   {
      List list = new ArrayList();
      list.add( new ObjectBean() );
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "list", list );
      JSONObject actual = jsonObject.getJSONArray( "list" )
            .getJSONObject( 0 );
      Assertions.assertTrue( !actual.has( "class" ) );
   }

   public void testPut_Collection2_exclusions()
   {
      List list = new ArrayList();
      list.add( new ObjectBean() );
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "list", list, new String[] { "pexcluded" } );
      JSONObject actual = jsonObject.getJSONArray( "list" )
            .getJSONObject( 0 );
      Assertions.assertTrue( !actual.has( "class" ) );
      Assertions.assertTrue( !actual.has( "pexcluded" ) );
   }

   public void testPut_Collection2_exclusions_ignoreDefault()
   {
      List list = new ArrayList();
      list.add( new ObjectBean() );
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "list", list, new String[] { "pexcluded" }, true );
      JSONObject actual = jsonObject.getJSONArray( "list" )
            .getJSONObject( 0 );
      Assertions.assertTrue( actual.has( "class" ) );
      Assertions.assertTrue( !actual.has( "pexcluded" ) );
   }

   public void testPut_double()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "double", 1d );
      assertEquals( 1d, jsonObject.getDouble( "double" ), 0d );
   }

   public void testPut_Enum()
   {
      JSONObject json = new JSONObject();
      json.put( "jsonEnum", JsonEnum.OBJECT );
      assertEquals( JsonEnum.OBJECT.toString(), json.get( "jsonEnum" ) );
   }

   public void testPut_int()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "int", 1 );
      assertEquals( 1, jsonObject.getInt( "int" ) );
   }

   public void testPut_JSON()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "null", JSONNull.getInstance() );
      Assertions.assertEquals( JSONNull.getInstance(), jsonObject.get( "null" ) );
   }

   public void testPut_JSONFunction()
   {
      JSONObject jsonObject = new JSONObject();
      JSONFunction f = new JSONFunction( "return this;" );
      jsonObject.put( "func", f );
      Assertions.assertEquals( f, (JSONFunction) jsonObject.get( "func" ) );
   }

   public void testPut_JSONString()
   {
      JSONObject jsonObject = new JSONObject();
      ObjectJSONStringBean bean = new ObjectJSONStringBean();
      bean.setName( "json" );
      jsonObject.put( "bean", bean );
      Assertions.assertEquals( JSONObject.fromJSONString( bean ), jsonObject.getJSONObject( "bean" ) );
   }

   public void testPut_JSONTokener()
   {
      JSONObject jsonObject = new JSONObject();
      JSONTokener tok = new JSONTokener( "{'name':'json'}" );
      jsonObject.put( "obj", tok );
      tok.reset();
      Assertions.assertEquals( JSONObject.fromObject( tok ), jsonObject.getJSONObject( "obj" ) );
   }

   public void testPut_long()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "long", 1L );
      assertEquals( 1L, jsonObject.getLong( "long" ) );
   }

   public void testPut_Map()
   {
      Map map = new HashMap();
      map.put( "name", "json" );
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "map", map );
      Assertions.assertEquals( JSONObject.fromObject( map ), jsonObject.getJSONObject( "map" ) );
   }

   public void testPut_Map2()
   {
      Map map = new HashMap();
      map.put( "name", "json" );
      map.put( "class", "java.lang.Object" );
      map.put( "excluded", "excluded" );
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "map", map );
      JSONObject actual = jsonObject.getJSONObject( "map" );
      Assertions.assertTrue( !actual.has( "class" ) );
   }

   public void testPut_Map2_exclusions()
   {
      Map map = new HashMap();
      map.put( "name", "json" );
      map.put( "class", "java.lang.Object" );
      map.put( "pexcluded", "excluded" );
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "map", map, new String[] { "pexcluded" } );
      JSONObject actual = jsonObject.getJSONObject( "map" );
      Assertions.assertTrue( !actual.has( "class" ) );
      Assertions.assertTrue( !actual.has( "pexcluded" ) );
   }

   public void testPut_Map2_exclusions_ignoreDefault()
   {
      Map map = new HashMap();
      map.put( "name", "json" );
      map.put( "class", "java.lang.Object" );
      map.put( "pexcluded", "excluded" );
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "map", map, new String[] { "pexcluded" }, true );
      JSONObject actual = jsonObject.getJSONObject( "map" );
      Assertions.assertTrue( actual.has( "class" ) );
      Assertions.assertTrue( !actual.has( "pexcluded" ) );
   }

   public void testPut_null_key()
   {
      try{
         new JSONObject().put( null, "value" );
         fail( "Expected a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testPut_Number()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "num", new Double( 2 ) );
      Assertions.assertEquals( new Double( 2 ).doubleValue(), jsonObject.getDouble( "num" ), 0d );
   }

   public void testPut_Object()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "bean", new BeanA() );
      Assertions.assertEquals( JSONObject.fromBean( new BeanA() ),
            jsonObject.getJSONObject( "bean" ) );
   }

   public void testPut_String()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "str", "json" );
      Assertions.assertEquals( "json", jsonObject.getString( "str" ) );
   }

   public void testPut_String_JSON()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "str", "[]" );
      Assertions.assertEquals( new JSONArray().toString(), jsonObject.getString( "str" ) );
   }

   public void testPut_String_null()
   {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put( "str", (String) null );
      // special case, if value null, there is no value associated to key
      try{
         jsonObject.getString( "str" );
         fail( "Should have thrown a JSONException" );
      }
      catch( JSONException expected ){
         // ok
      }
   }

   public void testToBean() throws Exception
   {
      String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },array:[1,2]}";
      JSONObject jsonObject = JSONObject.fromObject( json );
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
      JSONObject jsonObject = JSONObject.fromObject( json );
      BeanA bean = (BeanA) JSONObject.toBean( jsonObject, BeanA.class );
      assertEquals( jsonObject.get( "bool" ), Boolean.valueOf( bean.isBool() ) );
      assertEquals( jsonObject.get( "integer" ), new Integer( bean.getInteger() ) );
      assertEquals( jsonObject.get( "string" ), bean.getString() );
   }

   public void testToBean_BeanB()
   {
      String json = "{bool:true,integer:1,string:\"json\",intarray:[4,5,6]}";
      JSONObject jsonObject = JSONObject.fromObject( json );
      BeanB bean = (BeanB) JSONObject.toBean( jsonObject, BeanB.class );
      assertEquals( jsonObject.get( "bool" ), Boolean.valueOf( bean.isBool() ) );
      assertEquals( jsonObject.get( "integer" ), new Integer( bean.getInteger() ) );
      assertEquals( jsonObject.get( "string" ), bean.getString() );
      ArrayAssertions.assertEquals( bean.getIntarray(),
            JSONArray.toArray( jsonObject.getJSONArray( "intarray" ) ) );
   }

   public void testToBean_ClassBean()
   {
      JSONObject json = new JSONObject();
      json.put( "klass", "java.lang.Object" );

      ClassBean bean = (ClassBean) JSONObject.toBean( json, ClassBean.class );
      assertEquals( Object.class, bean.getKlass() );
   }

   public void testToBean_DynaBean__BigInteger_BigDecimal()
   {
      BigInteger l = new BigDecimal( "1.7976931348623157E308" ).toBigInteger();
      BigDecimal m = new BigDecimal( "1.7976931348623157E307" ).add( new BigDecimal( "0.0001" ) );
      JSONObject json = new JSONObject().put( "i", BigInteger.ZERO )
            .put( "d", MorphUtils.BIGDECIMAL_ONE )
            .put( "bi", l )
            .put( "bd", m );
      Object bean = JSONObject.toBean( json );
      Object i = ((MorphDynaBean) bean).get( "i" );
      Object d = ((MorphDynaBean) bean).get( "d" );
      assertTrue( i instanceof Integer );
      assertTrue( d instanceof Integer );

      Object bi = ((MorphDynaBean) bean).get( "bi" );
      Object bd = ((MorphDynaBean) bean).get( "bd" );
      assertTrue( bi instanceof BigInteger );
      assertTrue( bd instanceof BigDecimal );
   }

   public void testToBean_emptyBean()
   {
      EmptyBean bean = new EmptyBean();
      JSONObject json = JSONObject.fromObject( bean );
      JSONObject expected = new JSONObject();
      expected.put( "arrayp", new JSONArray() );
      expected.put( "listp", new JSONArray() );
      expected.put( "bytep", new Integer( 0 ) );
      expected.put( "shortp", new Integer( 0 ) );
      expected.put( "intp", new Integer( 0 ) );
      expected.put( "longp", new Integer( 0 ) );
      expected.put( "floatp", new Integer( 0 ) );
      expected.put( "doublep", new Double( 0 ) );
      expected.put( "charp", "" );
      expected.put( "stringp", "" );

      Assertions.assertEquals( expected, json );

      EmptyBean bean2 = (EmptyBean) JSONObject.toBean( json, EmptyBean.class );

      ArrayAssertions.assertEquals( new Object[0], bean2.getArrayp() );
      Assertions.assertEquals( new ArrayList(), bean2.getListp() );
      Assertions.assertEquals( new Byte( (byte) 0 ), bean2.getBytep() );
      Assertions.assertEquals( new Short( (short) 0 ), bean2.getShortp() );
      Assertions.assertEquals( new Integer( 0 ), bean2.getIntp() );
      Assertions.assertEquals( new Long( 0 ), bean2.getLongp() );
      Assertions.assertEquals( new Float( 0 ), bean2.getFloatp() );
      Assertions.assertEquals( new Double( 0 ), bean2.getDoublep() );
      Assertions.assertEquals( new Character( '\0' ), bean2.getCharp() );
      Assertions.assertEquals( "", bean2.getStringp() );
   }

   public void testToBean_EnumBean()
   {
      JSONUtils.getMorpherRegistry()
            .registerMorpher( new EnumMorpher( JsonEnum.class ) );
      JSONObject json = new JSONObject();
      json.put( "jsonEnum", "OBJECT" );
      EnumBean bean = (EnumBean) JSONObject.toBean( json, EnumBean.class );
      assertNotNull( bean );
      assertEquals( bean.getJsonEnum(), JsonEnum.OBJECT );
   }

   public void testToBean_interface()
   {
      // BUG 1542104

      try{
         JSONObject.toBean( JSONObject.fromObject( "{\"int\":1}" ), Serializable.class );
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
      Object obj = JSONObject.toBean( JSONObject.fromObject( map ), Map.class );
      assertTrue( obj instanceof Map );
      assertEquals( map.get( "name" ), ((Map) obj).get( "name" ) );
   }

   public void testToBean_nested() throws Exception
   {
      String json = "{name=\"json\",bool:true,int:1,double:2.2,func:function(a){ return a; },nested:{nested:true}}";
      JSONObject jsonObject = JSONObject.fromObject( json );
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
      JSONObject jsonObject = JSONObject.fromObject( json );
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

   public void testToBean_nested_beans_in_list__beans()
   {
      // BUG 1592799

      ListingBean listingBean = new ListingBean();

      ValueBean beanA1 = new ValueBean();
      beanA1.setValue( 90000 );
      ValueBean beanA2 = new ValueBean();
      beanA2.setValue( 91000 );

      listingBean.addAttribute( beanA1 );
      listingBean.addAttribute( beanA2 );

      JSONObject jsonObject = JSONObject.fromObject( listingBean );
      Map classMap = new HashMap();
      classMap.put( "attributes", ValueBean.class );
      ListingBean listingBean2 = (ListingBean) JSONObject.toBean( jsonObject, ListingBean.class,
            classMap );
      List attributes = listingBean2.getAttributes();
      Object ba = attributes.get( 0 );
      Object bb = attributes.get( 1 );

      assertTrue( ba instanceof ValueBean );
      assertTrue( bb instanceof ValueBean );
      assertEquals( beanA1.getValue(), ((ValueBean) ba).getValue() );
      assertEquals( beanA2.getValue(), ((ValueBean) bb).getValue() );
   }

   public void testToBean_nested_beans_in_list__DynaBean()
   {
      // BUG 1592799

      ListingBean listingBean = new ListingBean();

      ValueBean beanA1 = new ValueBean();
      beanA1.setValue( 90000 );
      ValueBean beanA2 = new ValueBean();
      beanA2.setValue( 91000 );

      listingBean.addAttribute( beanA1 );
      listingBean.addAttribute( beanA2 );

      JSONObject jsonObject = JSONObject.fromObject( listingBean );
      ListingBean listingBean2 = (ListingBean) JSONObject.toBean( jsonObject, ListingBean.class );
      List attributes = listingBean2.getAttributes();
      Object ba = attributes.get( 0 );
      Object bb = attributes.get( 1 );

      assertTrue( ba instanceof MorphDynaBean );
      assertTrue( bb instanceof MorphDynaBean );
      assertEquals( new Integer( beanA1.getValue() ), ((MorphDynaBean) ba).get( "value" ) );
      assertEquals( new Integer( beanA2.getValue() ), ((MorphDynaBean) bb).get( "value" ) );
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
      assertTrue( ba instanceof MorphDynaBean );
      assertTrue( bb instanceof MorphDynaBean );
      assertEquals( new Integer( beanA.getValue() ), ((MorphDynaBean) ba).get( "value" ) );
      assertEquals( new Integer( beanB.getValue() ), ((MorphDynaBean) bb).get( "value" ) );
   }

   public void testToBean_nested_dynabeans__null_object() throws Exception
   {
      // BUG 1553617

      String json = "{\"beanA\":{bool:true,integer:1,string:\"jsonbean\"},\"beanB\":null}";
      JSONObject jsonObject = JSONObject.fromObject( json );
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

   public void testtoBean_noWriteMethod()
   {
      JSONObject json = new JSONObject();
      json.put( "propertyWithNoReadMethod", "json" );
      json.put( "propertyWithNoWriteMethod", "value" );
      PropertyBean bean = (PropertyBean) JSONObject.toBean( json, PropertyBean.class );
      assertNotNull( bean );
      assertEquals( "json", bean.valueOfPropertyWithNoReadMethod() );
      assertEquals( "json", bean.getPropertyWithNoWriteMethod() );
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

   public void testToBean_NumberBean()
   {
      JSONObject json = new JSONObject();
      json.put( "pbyte", new Byte( (byte) 2 ) );
      json.put( "pshort", new Short( (short) 2 ) );
      json.put( "pint", new Integer( 2 ) );
      json.put( "plong", new Long( 2 ) );
      json.put( "pfloat", new Float( 2 ) );
      json.put( "pdouble", new Double( 2 ) );
      json.put( "pbigint", new BigInteger( "2" ) );
      json.put( "pbigdec", new BigDecimal( "2" ) );

      NumberBean bean = (NumberBean) JSONObject.toBean( json, NumberBean.class );
      assertEquals( (byte) 2, bean.getPbyte() );
      assertEquals( (short) 2, bean.getPshort() );
      assertEquals( 2, bean.getPint() );
      assertEquals( 2L, bean.getPlong() );
      assertEquals( 2f, bean.getPfloat(), 0f );
      assertEquals( 2d, bean.getPdouble(), 0d );
      assertEquals( new BigInteger( "2" ), bean.getPbigint() );
      assertEquals( new BigDecimal( "2" ), bean.getPbigdec() );
   }

   public void testToBean_NumberBean_2()
   {
      JSONObject json = new JSONObject();
      json.put( "pbyte", new Integer( 2 ) );
      json.put( "pshort", new Integer( 2 ) );
      json.put( "pint", new Integer( 2 ) );
      json.put( "plong", new Integer( 2 ) );
      json.put( "pfloat", new Integer( 2 ) );
      json.put( "pdouble", new Integer( 2 ) );
      json.put( "pbigint", new Integer( 2 ) );
      json.put( "pbigdec", new Integer( 2 ) );

      NumberBean bean = (NumberBean) JSONObject.toBean( json, NumberBean.class );
      assertEquals( (byte) 2, bean.getPbyte() );
      assertEquals( (short) 2, bean.getPshort() );
      assertEquals( 2, bean.getPint() );
      assertEquals( 2L, bean.getPlong() );
      assertEquals( 2f, bean.getPfloat(), 0f );
      assertEquals( 2d, bean.getPdouble(), 0d );
      assertEquals( new BigInteger( "2" ), bean.getPbigint() );
      assertEquals( new BigDecimal( "2" ), bean.getPbigdec() );
   }

   public void testToBean_ObjectBean()
   {
      // FR 1611204
      ObjectBean bean = new ObjectBean();
      bean.setPbyte( Byte.valueOf( "1" ) );
      bean.setPshort( Short.valueOf( "1" ) );
      bean.setPint( Integer.valueOf( "1" ) );
      bean.setPlong( Long.valueOf( "1" ) );
      bean.setPfloat( Float.valueOf( "1" ) );
      bean.setPdouble( Double.valueOf( "1" ) );
      bean.setPchar( new Character( '1' ) );
      bean.setPboolean( Boolean.TRUE );
      bean.setPstring( "json" );
      bean.setParray( new String[] { "a", "b" } );
      bean.setPbean( new BeanA() );
      List list = new ArrayList();
      list.add( "1" );
      list.add( "2" );
      bean.setPlist( list );
      Map map = new HashMap();
      map.put( "string", "json" );
      bean.setPmap( map );
      bean.setPfunction( new JSONFunction( "this;" ) );
      JSONObject json = JSONObject.fromObject( bean );
      Map classMap = new HashMap();
      classMap.put( "pbean", BeanA.class );
      ObjectBean obj = (ObjectBean) JSONObject.toBean( json, ObjectBean.class, classMap );
      assertEquals( Integer.valueOf( "1" ), obj.getPbyte() );
      assertEquals( Integer.valueOf( "1" ), obj.getPshort() );
      assertEquals( Integer.valueOf( "1" ), obj.getPint() );
      assertEquals( Integer.valueOf( "1" ), obj.getPlong() );
      assertEquals( Double.valueOf( "1" ), obj.getPfloat() );
      assertEquals( Double.valueOf( "1" ), obj.getPdouble() );
      assertEquals( "1", obj.getPchar() );
      assertEquals( "json", obj.getPstring() );
      List l = new ArrayList();
      l.add( "a" );
      l.add( "b" );
      ArrayAssertions.assertEquals( l.toArray(), (Object[]) obj.getParray() );
      l = new ArrayList();
      l.add( "1" );
      l.add( "2" );
      ArrayAssertions.assertEquals( l.toArray(), (Object[]) obj.getPlist() );
      assertEquals( new BeanA(), obj.getPbean() );
      assertTrue( obj.getPmap() instanceof MorphDynaBean );
   }

   public void testToBean_ObjectBean_empty() throws Exception
   {
      // FR 1611204
      ObjectBean bean = new ObjectBean();
      JSONObject json = JSONObject.fromObject( bean );
      Map classMap = new HashMap();
      classMap.put( "bean", BeanA.class );
      ObjectBean obj = (ObjectBean) JSONObject.toBean( json, ObjectBean.class, classMap );

      String[] keys = { "pbyte", "pshort", "pint", "plong", "pfloat", "pdouble", "pboolean",
            "pchar", "pstring", "parray", "plist", "pmap", "pbean" };
      for( int i = 0; i < keys.length; i++ ){
         assertNull( PropertyUtils.getProperty( obj, keys[i] ) );
      }
   }

   public void testToBean_withNonJavaIdentifier_camelCase_Strategy()
   {
      JSONObject json = new JSONObject().put( "camel case", "json" );
      JSONUtils.setJavaIdentifierTransformer( JavaIdentifierTransformer.CAMEL_CASE );
      JavaIdentifierBean bean = (JavaIdentifierBean) JSONObject.toBean( json,
            JavaIdentifierBean.class );
      assertNotNull( bean );
      assertEquals( "json", bean.getCamelCase() );
   }

   public void testToBean_withNonJavaIdentifier_underScore_Strategy()
   {
      JSONObject json = new JSONObject().put( "under score", "json" );
      JSONUtils.setJavaIdentifierTransformer( JavaIdentifierTransformer.UNDERSCORE );
      JavaIdentifierBean bean = (JavaIdentifierBean) JSONObject.toBean( json,
            JavaIdentifierBean.class );
      assertNotNull( bean );
      assertEquals( "json", bean.getUnder_score() );
   }

   public void testToBean_withNonJavaIdentifier_whitespace_Strategy()
   {
      JSONObject json = new JSONObject().put( " white space ", "json" );
      JSONUtils.setJavaIdentifierTransformer( JavaIdentifierTransformer.WHITESPACE );
      JavaIdentifierBean bean = (JavaIdentifierBean) JSONObject.toBean( json,
            JavaIdentifierBean.class );
      assertNotNull( bean );
      assertEquals( "json", bean.getWhitespace() );
   }

   public void testToJSONArray()
   {
      String json = "{bool:true,integer:1,string:\"json\"}";
      JSONArray names = JSONArray.fromObject( "['string','integer','bool']" );
      JSONObject jsonObject = JSONObject.fromObject( json );
      JSONArray jsonArray = jsonObject.toJSONArray( names );
      assertEquals( "json", jsonArray.getString( 0 ) );
      assertEquals( 1, jsonArray.getInt( 1 ) );
      assertTrue( jsonArray.getBoolean( 2 ) );
   }

   private MorphDynaBean createDynaBean() throws Exception
   {
      Map properties = new HashMap();
      properties.put( "name", String.class );
      properties.put( "func", JSONFunction.class );
      properties.put( "jsonstr", JSONString.class );
      properties.put( "json", JSON.class );
      properties.put( "str", String.class );
      MorphDynaClass dynaClass = new MorphDynaClass( properties );
      MorphDynaBean dynaBean = (MorphDynaBean) dynaClass.newInstance();
      dynaBean.setDynaBeanClass( dynaClass );
      dynaBean.set( "name", "json" );
      dynaBean.set( "func", new JSONFunction( "return this;" ) );
      dynaBean.set( "jsonstr", new ObjectJSONStringBean() );
      dynaBean.set( "json", new JSONObject().put( "id", "1" ) );
      dynaBean.set( "str", "[1,2]" );
      // JSON Strings can not be null, only empty
      return dynaBean;
   }
}
