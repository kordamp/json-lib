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

import java.util.List;

import net.sf.json.test.JSONAssert;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class Assertions extends JSONAssert
{
   public static void assertEquals( List expecteds, List actuals )
   {
      assertEquals( null, expecteds, actuals );
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
            assertEquals( header + "lists first differed at element " + i + ";", expected, actual );
         }else if( List.class.isAssignableFrom( o1.getClass() )
               && List.class.isAssignableFrom( o2.getClass() ) ){
            assertEquals( header + "lists first differed at element [" + i + "];", (List) o1,
                  (List) o2 );
         }else{
            if( o1 instanceof String && o2 instanceof JSONFunction ){
               assertEquals( header + "lists first differed at element [" + i + "];", (String) o1,
                     (JSONFunction) o2 );
            }else if( o1 instanceof JSONFunction && o2 instanceof String ){
               assertEquals( header + "lists first differed at element [" + i + "];",
                     (JSONFunction) o1, (String) o2 );
            }else if( o1 instanceof JSONObject && o2 instanceof JSONObject ){
               assertEquals( header + "lists first differed at element [" + i + "];",
                     (JSONObject) o1, (JSONObject) o2 );
            }else if( o1 instanceof JSONArray && o2 instanceof JSONArray ){
               assertEquals( header + "lists first differed at element [" + i + "];",
                     (JSONArray) o1, (JSONArray) o2 );
            }else if( o1 instanceof JSONFunction && o2 instanceof JSONFunction ){
               assertEquals( header + "lists first differed at element [" + i + "];",
                     (JSONFunction) o1, (JSONFunction) o2 );
            }else{
               assertEquals( header + "lists first differed at element [" + i + "];", o1, o2 );
            }
         }
      }
   }
}