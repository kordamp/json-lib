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

package net.sf.json.converter;

import junit.framework.TestCase;

/**
 * @author Andres Almiray
 */
public class TestLongConverter extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestLongConverter.class );
   }

   public TestLongConverter( String name )
   {
      super( name );
   }

   public void testConvert_useDefault()
   {
      String expected = String.valueOf( "A" );
      long actual = new LongConverter( 0 ).convert( expected );
      assertEquals( 0, actual );
   }

   public void testConvertDecimalValue_Number()
   {
      Double expected = new Double( 3.1416d );
      long actual = new LongConverter().convert( expected );
      assertEquals( 3, actual );
   }

   public void testConvertDecimalValue_String()
   {
      String expected = "3.1416";
      long actual = new LongConverter().convert( expected );
      assertEquals( 3, actual );
   }

   public void testConvertMaxValue_Number()
   {
      Long expected = new Long( Long.MAX_VALUE );
      long actual = new LongConverter().convert( expected );
      assertEquals( expected.longValue(), actual );
   }

   public void testConvertMaxValue_String()
   {
      String expected = String.valueOf( new Long( Long.MAX_VALUE ) );
      long actual = new LongConverter().convert( expected );
      assertEquals( expected, String.valueOf( actual ) );
   }

   public void testConvertMinValue_Number()
   {
      Long expected = new Long( Long.MIN_VALUE );
      long actual = new LongConverter().convert( expected );
      assertEquals( expected.longValue(), actual );
   }

   public void testConvertMinValue_String()
   {
      String expected = String.valueOf( new Long( Long.MIN_VALUE ) );
      long actual = new LongConverter().convert( expected );
      assertEquals( expected, String.valueOf( actual ) );
   }
}