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
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class TestNumberConverter extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestNumberConverter.class );
   }

   private NumberConverter converter;

   public TestNumberConverter( String name )
   {
      super( name );
   }

   public void testByteConversion_byte()
   {
      converter = new NumberConverter( Byte.class );
      Byte expected = new Byte( Byte.MIN_VALUE );
      Byte actual = (Byte) converter.convert( expected );
      assertEquals( expected, actual );

   }

   public void testByteConversion_Byte()
   {
      converter = new NumberConverter( Byte.class );
      Byte expected = new Byte( Byte.MIN_VALUE );
      Byte actual = (Byte) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testByteConversion_null()
   {
      converter = new NumberConverter( Byte.class );
      assertNull( converter.convert( null ) );
   }

   public void testByteConversion_String()
   {
      converter = new NumberConverter( Byte.class );
      String expected = String.valueOf( Byte.MIN_VALUE );
      Byte actual = (Byte) converter.convert( expected );
      assertEquals( expected, actual.toString() );
   }

   public void testByteConversion_useDefault()
   {
      converter = new NumberConverter( Byte.class );
      Byte expected = new Byte( Byte.MIN_VALUE );
      converter.setDefaultValue( expected );
      Byte actual = (Byte) converter.convert( new Object() );
      assertEquals( expected, actual );
   }

   public void testDoubleConversion_double()
   {
      converter = new NumberConverter( Double.class );
      Double expected = new Double( Double.MIN_VALUE );
      Double actual = (Double) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testDoubleConversion_Double()
   {
      converter = new NumberConverter( Double.class );
      Double expected = new Double( Double.MIN_VALUE );
      Double actual = (Double) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testDoubleConversion_null()
   {
      converter = new NumberConverter( Double.class );
      assertNull( converter.convert( null ) );
   }

   public void testDoubleConversion_String()
   {
      converter = new NumberConverter( Double.class );
      String expected = String.valueOf( Double.MIN_VALUE );
      Double actual = (Double) converter.convert( expected );
      assertEquals( expected, actual.toString() );
   }

   public void testDoubleConversion_useDefault()
   {
      converter = new NumberConverter( Double.class );
      Double expected = new Double( Double.MIN_VALUE );
      converter.setDefaultValue( expected );
      Double actual = (Double) converter.convert( new Object() );
      assertEquals( expected, actual );
   }

   public void testFloatConversion_float()
   {
      converter = new NumberConverter( Float.class );
      Float expected = new Float( Float.MIN_VALUE );
      Float actual = (Float) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testFloatConversion_Float()
   {
      converter = new NumberConverter( Float.class );
      Float expected = new Float( Float.MIN_VALUE );
      Float actual = (Float) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testFloatConversion_null()
   {
      converter = new NumberConverter( Float.class );
      assertNull( converter.convert( null ) );
   }

   public void testFloatConversion_String()
   {
      converter = new NumberConverter( Float.class );
      String expected = String.valueOf( Float.MIN_VALUE );
      Float actual = (Float) converter.convert( expected );
      assertEquals( expected, actual.toString() );
   }

   public void testFloatConversion_useDefault()
   {
      converter = new NumberConverter( Float.class );
      Float expected = new Float( Float.MIN_VALUE );
      converter.setDefaultValue( expected );
      Float actual = (Float) converter.convert( new Object() );
      assertEquals( expected, actual );
   }

   public void testIntegerConversion_int()
   {
      converter = new NumberConverter( Integer.class );
      Integer expected = new Integer( Integer.MIN_VALUE );
      Integer actual = (Integer) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testIntegerConversion_Integer()
   {
      converter = new NumberConverter( Integer.class );
      Integer expected = new Integer( Integer.MIN_VALUE );
      Integer actual = (Integer) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testIntegerConversion_null()
   {
      converter = new NumberConverter( Integer.class );
      assertNull( converter.convert( null ) );
   }

   public void testIntegerConversion_String()
   {
      converter = new NumberConverter( Integer.class );
      String expected = String.valueOf( Integer.MIN_VALUE );
      Integer actual = (Integer) converter.convert( expected );
      assertEquals( expected, actual.toString() );
   }

   public void testIntegerConversion_useDefault()
   {
      converter = new NumberConverter( Integer.class );
      Integer expected = new Integer( Integer.MIN_VALUE );
      converter.setDefaultValue( expected );
      Integer actual = (Integer) converter.convert( new Object() );
      assertEquals( expected, actual );
   }

   public void testlongConversion_long()
   {
      converter = new NumberConverter( Long.class );
      Long expected = new Long( Long.MIN_VALUE );
      Long actual = (Long) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testLongConversion_Long()
   {
      converter = new NumberConverter( Long.class );
      Long expected = new Long( Long.MIN_VALUE );
      Long actual = (Long) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testLongConversion_null()
   {
      converter = new NumberConverter( Long.class );
      assertNull( converter.convert( null ) );
   }

   public void testLongConversion_String()
   {
      converter = new NumberConverter( Long.class );
      String expected = String.valueOf( Long.MIN_VALUE );
      Long actual = (Long) converter.convert( expected );
      assertEquals( expected, actual.toString() );
   }

   public void testLongConversion_useDefault()
   {
      converter = new NumberConverter( Long.class );
      Long expected = new Long( Long.MIN_VALUE );
      converter.setDefaultValue( expected );
      Long actual = (Long) converter.convert( new Object() );
      assertEquals( expected, actual );
   }

   public void testShortConversion_null()
   {
      converter = new NumberConverter( Short.class );
      assertNull( converter.convert( null ) );
   }

   public void testShortConversion_short()
   {
      converter = new NumberConverter( Short.class );
      Short expected = new Short( Short.MIN_VALUE );
      Short actual = (Short) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testShortConversion_Short()
   {
      converter = new NumberConverter( Short.class );
      Short expected = new Short( Short.MIN_VALUE );
      Short actual = (Short) converter.convert( expected );
      assertEquals( expected, actual );
   }

   public void testShortConversion_String()
   {
      converter = new NumberConverter( Short.class );
      String expected = String.valueOf( Short.MIN_VALUE );
      Short actual = (Short) converter.convert( expected );
      assertEquals( expected, actual.toString() );
   }

   public void testShortConversion_useDefault()
   {
      converter = new NumberConverter( Short.class );
      Short expected = new Short( Short.MIN_VALUE );
      converter.setDefaultValue( expected );
      Short actual = (Short) converter.convert( new Object() );
      assertEquals( expected, actual );
   }
}