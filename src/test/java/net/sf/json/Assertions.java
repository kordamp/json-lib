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

import java.util.Iterator;
import java.util.List;

import net.sf.ezmorph.test.ArrayAssertions;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class Assertions extends ArrayAssertions
{
   public static void assertEquals( JSONArray expecteds, JSONArray actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( JSONObject expected, JSONObject actual )
   {
      assertEquals( null, expected, actual );
   }

   public static void assertEquals( List expecteds, List actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( String message, JSONArray expecteds, JSONArray actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( expecteds == actuals || expecteds.equals( actuals ) ){
         return;
      }
      if( actuals.length() != expecteds.length() ){
         fail( header + "arrays sizes differed, expected.length()=" + expecteds.length()
               + " actual.length()=" + actuals.length() );
      }

      int max = expecteds.length();
      for( int i = 0; i < max; i++ ){
         Object o1 = expecteds.get( i );
         Object o2 = actuals.get( i );

         // handle nulls
         if( o1 == null ){
            if( o2 == null ){
               return;
            }else{
               fail( header + "arrays first differed at element [" + i + "];" );
            }
         }else{
            if( o2 == null ){
               fail( header + "arrays first differed at element [" + i + "];" );
            }
         }

         if( o1 instanceof JSONArray && o2 instanceof JSONArray ){
            JSONArray expected = (JSONArray) o1;
            JSONArray actual = (JSONArray) o2;
            assertEquals( header + "arrays first differed at element " + i + ";", expected, actual );
         }else{
            if( o1 instanceof String && o2 instanceof JSONFunction ){
               assertEquals( header + "lists first differed at element [" + i + "];", (String) o1,
                     (JSONFunction) o2 );
            }else{
               assertEquals( header + "arrays first differed at element [" + i + "];", o1, o2 );
            }
         }
      }
   }

   public static void assertEquals( String expected, JSONFunction actual )
   {
      assertEquals( null, expected, actual );
   }

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
      assertEquals( header + "names sizes differed, expected.names().length()=" + expected.names()
            .length() + " actual.names().length()=" + actual.names()
            .length(), expected.names()
            .length(), actual.names()
            .length() );
      for( Iterator keys = expected.keys(); keys.hasNext(); ){
         String key = (String) keys.next();
         assertEquals( header, expected.get( key ), actual.get( key ) );
      }
   }

   public static void assertEquals( String message, List expecteds, List actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( expecteds == actuals || expecteds.equals( actuals ) ){
         return;
      }
      if( actuals.size() != expecteds.size() ){
         fail( header + "list sizes differed, expected.size()=" + expecteds.size()
               + " actual.size()=" + actuals.size() );
      }

      int max = expecteds.size();
      for( int i = 0; i < max; i++ ){
         Object o1 = expecteds.get( i );
         Object o2 = actuals.get( i );

         // handle nulls
         if( o1 == null ){
            if( o2 == null ){
               return;
            }else{
               fail( header + "lists first differed at element [" + i + "];" );
            }
         }else{
            if( o2 == null ){
               fail( header + "lists first differed at element [" + i + "];" );
            }
         }

         if( o1.getClass()
               .isArray() && o2.getClass()
               .isArray() ){
            Object[] expected = (Object[]) o1;
            Object[] actual = (Object[]) o2;
            assertEquals( header + "arrays first differed at element " + i + ";", expected, actual );
         }else if( List.class.isAssignableFrom( o1.getClass() )
               && List.class.isAssignableFrom( o2.getClass() ) ){
            assertEquals( header + "lists first differed at element [" + i + "];", (List) o1,
                  (List) o2 );
         }else{
            if( o1 instanceof String && o2 instanceof JSONFunction ){
               assertEquals( header + "lists first differed at element [" + i + "];", (String) o1,
                     (JSONFunction) o2 );
            }else{
               assertEquals( header + "lists first differed at element [" + i + "];", o1, o2 );
            }
         }
      }
   }

   public static void assertEquals( String message, String expected, JSONFunction actual )
   {
      String header = message == null ? "" : message + ": ";
      if( expected == null ){
         fail( header + "expected string was null" );
      }
      if( actual == null ){
         fail( header + "actual function was null" );
      }
      assertEquals( header, expected, actual.toString() );
   }
}