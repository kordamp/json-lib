/*
 * Copyright 2002-2007 the original author or authors.
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

package net.sf.json.groovy

import net.sf.json.*
import net.sf.json.test.JSONAssert

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
class TestJSONObjectGroovy extends GroovyTestCase {
   public void testLeftShift_with_map(){
      def actual = new JSONObject()
      actual << [key:1]
      def expected = new JSONObject()
         .element( "key", 1 )
      JSONAssert.assertEquals( expected, actual )
   }

   public void testLeftShift_with_map2(){
      def actual = new JSONObject()
      actual << [key:1,key2:2]
      def expected = new JSONObject()
         .element( "key", 1 )
         .element( "key2", 2 )
      JSONAssert.assertEquals( expected, actual )
   }

   public void testLeftShift_with_list(){
      def actual = new JSONObject()
      actual << ['key',1]
      def expected = new JSONObject()
         .element( "key", 1 )
      JSONAssert.assertEquals( expected, actual )
   }

   public void testLeftShift_with_list2(){
      def actual = new JSONObject()
      actual << ['key',1,2,3]
      def expected = new JSONObject()
         .element( "key", 1 )
         .accumulate( "key", 2 )
         .accumulate( "key", 3 )
      JSONAssert.assertEquals( expected, actual )
   }

   public void testEqualsOperator(){
      def json = new JSONObject().element("key",1)
      assertTrue json == json
   }

   public void testLessThanOperator(){
      def json1 = new JSONObject().element("key",1)
      def json2 = new JSONObject().element("key",1).element("key2",2)
      assertTrue json1 < json2
   }

   public void testGreaterThanOperator(){
      def json1 = new JSONObject().element("key",1)
      def json2 = new JSONObject().element("key",1).element("key2",2)
      assertTrue json2 > json1
   }

   public void testSpaceshipOperator(){
      def json1 = new JSONObject().element("key",1)
      def json2 = new JSONObject().element("key",1).element("key2",2)
      assertTrue 0 != (json1 <=> json2)
   }
}