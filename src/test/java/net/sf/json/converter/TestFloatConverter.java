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
public class TestFloatConverter extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestFloatConverter.class );
   }

   public TestFloatConverter( String name )
   {
      super( name );
   }

   public void testConvert_useDefault()
   {
      String expected = String.valueOf( "A" );
      float actual = new FloatConverter( 0 ).convert( expected );
      assertEquals( 0f, actual, 0f );
   }

   public void testConvertDecimalValue_Number()
   {
      Double expected = new Double( 3.1416d );
      float actual = new FloatConverter().convert( expected );
      assertEquals( 3.1416f, actual, 0f );
   }

   public void testConvertDecimalValue_String()
   {
      String expected = "3.1416";
      float actual = new FloatConverter().convert( expected );
      assertEquals( 3.1416f, actual, 0f );
   }

   public void testConvertMaxValue_Number()
   {
      Float expected = new Float( Float.MAX_VALUE );
      float actual = new FloatConverter().convert( expected );
      assertEquals( expected.floatValue(), actual, 0f );
   }

   public void testConvertMaxValue_String()
   {
      String expected = String.valueOf( new Float( Float.MAX_VALUE ) );
      float actual = new FloatConverter().convert( expected );
      assertEquals( expected, String.valueOf( actual ) );
   }

   public void testConvertMinValue_Number()
   {
      Float expected = new Float( Float.MIN_VALUE );
      float actual = new FloatConverter().convert( expected );
      assertEquals( expected.floatValue(), actual, 0f );
   }

   public void testConvertMinValue_String()
   {
      String expected = String.valueOf( new Float( Float.MIN_VALUE ) );
      float actual = new FloatConverter().convert( expected );
      assertEquals( expected, String.valueOf( actual ) );
   }
}