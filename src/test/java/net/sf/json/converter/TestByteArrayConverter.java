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
import net.sf.json.Assertions;

/**
 * @author Andres Almiray
 */
public class TestByteArrayConverter extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestByteArrayConverter.class );
   }

   private ByteArrayConverter converter;

   public TestByteArrayConverter( String name )
   {
      super( name );
   }

   public void testConvert_byteArray()
   {
      byte[] expected = { 1, 2, 3 };
      byte[] actual = (byte[]) converter.convert( expected );
      Assertions.assertEquals( expected, actual );
   }

   public void testConvert_byteArray_threedims()
   {
      byte[][][] expected = { { { 1 }, { 2 } }, { { 3 }, { 4 } } };
      byte[][][] actual = (byte[][][]) converter.convert( expected );
      Assertions.assertEquals( expected, actual );
   }

   public void testConvert_byteArray_twodims()
   {
      byte[][] expected = { { 1, 2, 3 }, { 4, 5, 6 } };
      byte[][] actual = (byte[][]) converter.convert( expected );
      Assertions.assertEquals( expected, actual );
   }

   public void testConvert_null()
   {
      assertNull( converter.convert( null ) );
   }

   public void testConvert_strings()
   {
      String[] expected = { "1", "2", "3.3" };
      byte[] actual = (byte[]) converter.convert( expected );
      Assertions.assertEquals( new byte[] { 1, 2, 3 }, actual );
   }

   public void testConvert_strings_twodims()
   {
      String[][] expected = { { "1", "2", "3.3" }, { "4", "5", "6.6" } };
      byte[][] actual = (byte[][]) converter.convert( expected );
      Assertions.assertEquals( new byte[][] { { 1, 2, 3 }, { 4, 5, 6 } }, actual );
   }

   protected void setUp() throws Exception
   {
      converter = new ByteArrayConverter();
   }
}