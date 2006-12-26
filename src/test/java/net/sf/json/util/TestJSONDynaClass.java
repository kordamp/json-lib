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

package net.sf.json.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.json.JSONException;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class TestJSONDynaClass extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestJSONDynaClass.class );
   }

   public void testEquals()
   {
      Map properties = new HashMap();
      properties.put( "byte", Byte.class );
      JSONDynaClass class1 = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaClass class2 = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaClass class3 = new JSONDynaClass( "JSON", JSONDynaBean.class, new HashMap() );

      assertFalse( class1.equals( null ) );
      assertTrue( class1.equals( class1 ) );
      assertTrue( class1.equals( class2 ) );
      assertFalse( class1.equals( new Object() ) );
      assertFalse( class1.equals( class3 ) );
   }

   public void testGetDynaProperty_null()
   {
      try{
         Map properties = new HashMap();
         properties.put( "obj", Object.class.getName() );
         JSONDynaClass dynaClass = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
         dynaClass.getDynaProperty( null );
         fail( "Expected an JSONException" );
      }
      catch( JSONException exception ){
         // ok
      }
   }

   public void testHashcode()
   {
      Map properties = new HashMap();
      properties.put( "byte", Byte.class );
      JSONDynaClass class1 = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaClass class2 = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaClass class3 = new JSONDynaClass( "JSON", JSONDynaBean.class, new HashMap() );

      assertEquals( class1.hashCode(), class1.hashCode() );
      assertEquals( class1.hashCode(), class2.hashCode() );
      assertTrue( class1.hashCode() != class3.hashCode() );
   }

   public void testMiscelaneousClasses()
   {
      Map properties = new HashMap();
      properties.put( "byte", Byte.class );
      properties.put( "short", Short.class );
      properties.put( "int", Integer.class );
      properties.put( "long", Long.class );
      properties.put( "float", Float.class );
      properties.put( "double", Double.class );
      properties.put( "bi", BigInteger.class );
      properties.put( "bd", BigDecimal.class );
      properties.put( "boolean", Boolean.class );
      properties.put( "char", Character.class );
      properties.put( "map", Map.class );
      properties.put( "strs", String[].class );
      properties.put( "list", List.class );
      new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
   }

   public void testMultidimensionalArrayClass_Class()
   {
      try{
         Map properties = new HashMap();
         properties.put( "array", Object[][].class );
         new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
         fail( "Expected an JSONException" );
      }
      catch( JSONException exception ){
         // ok
      }
   }

   public void testMultidimensionalArrayClass_String()
   {
      try{
         Map properties = new HashMap();
         properties.put( "array", Object[][].class.getName() );
         new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
         fail( "Expected an JSONException" );
      }
      catch( JSONException exception ){
         // ok
      }
   }
}