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

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.json.sample.JSONTestBean;

import org.apache.commons.beanutils.DynaBean;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class TestUserSubmitted extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestUserSubmitted.class );
   }

   public TestUserSubmitted( String name )
   {
      super( name );
   }

   public void testBug_1635890() throws NoSuchMethodException, IllegalAccessException,
         InvocationTargetException
   {
      // submited by arco.vandenheuvel[at]points[dot].com

      String TEST_JSON_STRING = "{\"rateType\":\"HOTRATE\",\"rateBreakdown\":{\"rate\":[{\"amount\":\"109.74\",\"date\":{\"month\":\"01\",\"day\":\"15\",\"year\":\"2007\"}},{\"amount\":\"109.74\",\"date\":{\"month\":\"1\",\"day\":\"16\",\"year\":\"2007\"}}]}}";

      DynaBean jsonBean = (DynaBean) JSONObject.toBean( JSONObject.fromString( TEST_JSON_STRING ) );
      assertNotNull( jsonBean );
      assertEquals( "wrong rate Type", "HOTRATE", jsonBean.get( "rateType" ) );
      assertNotNull( "null rate breakdown", jsonBean.get( "rateBreakdown" ) );
      DynaBean jsonRateBreakdownBean = (DynaBean) jsonBean.get( "rateBreakdown" );
      assertNotNull( "null rate breakdown ", jsonRateBreakdownBean );
      Object jsonRateBean = jsonRateBreakdownBean.get( "rate" );
      assertNotNull( "null rate ", jsonRateBean );
      assertTrue( "list", jsonRateBean instanceof ArrayList );
      assertNotNull( "null rate ", jsonRateBreakdownBean.get( "rate", 0 ) );
   }

   public void testBug_1650535_builders()
   {
      // submitted by Paul Field <paulfield[at]users[dot]sourceforge[dot]net>

      String json = "{\"obj\":\"{}\",\"array\":\"[]\"}";
      JSONObject object = JSONObject.fromString( json );
      assertNotNull( object );
      assertTrue( object.has( "obj" ) );
      assertTrue( object.has( "array" ) );
      Object obj = object.get( "obj" );
      assertTrue( obj instanceof String );
      Object array = object.get( "array" );
      assertTrue( array instanceof String );

      json = "{'obj':'{}','array':'[]'}";
      object = JSONObject.fromString( json );
      assertNotNull( object );
      assertTrue( object.has( "obj" ) );
      assertTrue( object.has( "array" ) );
      obj = object.get( "obj" );
      assertTrue( obj instanceof String );
      array = object.get( "array" );
      assertTrue( array instanceof String );

      json = "[\"{}\",\"[]\"]";
      JSONArray jarray = JSONArray.fromString( json );
      assertNotNull( jarray );
      obj = jarray.get( 0 );
      assertTrue( obj instanceof String );
      array = jarray.get( 1 );
      assertTrue( array instanceof String );

      json = "['{}','[]']";
      jarray = JSONArray.fromString( json );
      assertNotNull( jarray );
      obj = jarray.get( 0 );
      assertTrue( obj instanceof String );
      array = jarray.get( 1 );
      assertTrue( array instanceof String );

      // submitted by Elizabeth Keogh <ekeogh[at]thoughtworks[dot]com>

      Map map = new HashMap();
      map.put( "address", "1 The flats [Upper floor]" );
      map.put( "phoneNumber", "[+44] 582 401923" );
      map.put( "info1", "[Likes coffee]" );
      map.put( "info2", "[Likes coffee] [Likes tea]" );
      map.put( "info3", "[Likes coffee [but not with sugar]]" );
      object = JSONObject.fromObject( map );
      assertNotNull( object );
      assertTrue( object.has( "address" ) );
      assertTrue( object.has( "phoneNumber" ) );
      assertTrue( object.has( "info1" ) );
      assertTrue( object.has( "info2" ) );
      assertTrue( object.has( "info3" ) );
      assertTrue( object.get( "address" ) instanceof String );
      assertTrue( object.get( "phoneNumber" ) instanceof String );
      assertTrue( object.get( "info1" ) instanceof String );
      assertTrue( object.get( "info2" ) instanceof String );
      assertTrue( object.get( "info3" ) instanceof String );
   }

   public void testBug_1650535_setters()
   {
      JSONObject object = new JSONObject();
      object.put( "obj", "{}" );
      object.put( "notobj", "{string}" );
      object.put( "array", "[]" );
      object.put( "notarray", "[string]" );
      assertTrue( object.get( "obj" ) instanceof JSONObject );
      assertTrue( object.get( "array" ) instanceof JSONArray );
      assertTrue( object.get( "notobj" ) instanceof String );
      assertTrue( object.get( "notarray" ) instanceof String );

      object.put( "str", "json,json" );
      assertTrue( object.get( "str" ) instanceof String );
   }

   public void testDynaBeanAttributeMap() throws NoSuchMethodException, IllegalAccessException,
         InvocationTargetException
   {
      // submited by arco.vandenheuvel[at]points[dot].com

      JSONObject jsonObject = JSONObject.fromObject( new JSONTestBean() );
      String jsonString = jsonObject.toString();
      DynaBean jsonBean = (DynaBean) JSONObject.toBean( JSONObject.fromString( jsonString ) );
      assertNotNull( jsonBean );
      assertEquals( "wrong inventoryID", "", jsonBean.get( "inventoryID" ) );
   }

   public void testJsonWithNamespaceToDynaBean()
   {
      // submited by Girish Ipadi

      String str = "{'version':'1.0'," + "'sid':'AmazonDocStyle',    'svcVersion':'0.1',"
            + "'oid':'ItemLookup',    'params':[{            'ns:ItemLookup': {"
            + "'ns:SubscriptionId':'0525E2PQ81DD7ZTWTK82'," + "'ns:Validate':'False',"
            + "'ns:Request':{" + "'ns:ItemId':'SDGKJSHDGAJSGL'," + "'ns:IdType':'ASIN',"
            + "'ns:ResponseGroup':'Large'" + "}," + "'ns:Request':{" + "'ns:ItemId':'XXXXXXXXXX',"
            + "'ns:IdType':'ASIN'," + "'ns:ResponseGroup':'Large'" + "}" + "}" + "}]" + "} ";
      JSONObject json = JSONObject.fromString( str );
      Object bean = JSONObject.toBean( (JSONObject) json );
      // TODO add assertions
   }
}