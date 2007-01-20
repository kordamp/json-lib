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

import junit.framework.TestCase;

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
}