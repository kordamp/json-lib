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

   public class BeanA
   {
      private boolean bool = true;
      private int integer = 42;
      private String string = "json";

      public int getInteger()
      {
         return integer;
      }

      public String getString()
      {
         return string;
      }

      public boolean isBool()
      {
         return bool;
      }

      public void setBool( boolean bool )
      {
         this.bool = bool;
      }

      public void setInteger( int integer )
      {
         this.integer = integer;
      }

      public void setString( String string )
      {
         this.string = string;
      }
   }

   public class BeanB extends BeanA
   {
      private int[] intarray = new int[] { 1, 2, 3 };

      public int[] getIntarray()
      {
         return intarray;
      }

      public void setIntarray( int[] intarray )
      {
         this.intarray = intarray;
      }
   }

   public class BeanC
   {
      private BeanA beanA = new BeanA();
      private BeanB beanB = new BeanB();

      public BeanA getBeanA()
      {
         return beanA;
      }

      public BeanB getBeanB()
      {
         return beanB;
      }

      public void setBeanA( BeanA beanA )
      {
         this.beanA = beanA;
      }

      public void setBeanB( BeanB beanB )
      {
         this.beanB = beanB;
      }
   }
}