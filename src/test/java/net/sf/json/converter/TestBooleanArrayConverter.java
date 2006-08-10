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
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class TestBooleanArrayConverter extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestBooleanArrayConverter.class );
   }

   private BooleanArrayConverter converter;

   public TestBooleanArrayConverter( String name )
   {
      super( name );
   }

   public void testConvert_booleanArray()
   {
      boolean[] expected = { true, false };
      boolean[] actual = (boolean[]) converter.convert( expected );
      Assertions.assertEquals( expected, actual );
   }

   public void testConvert_booleanArray_threedims()
   {
      boolean[][][] expected = { { { true }, { false } }, { { true }, { false } } };
      boolean[][][] actual = (boolean[][][]) converter.convert( expected );
      Assertions.assertEquals( expected, actual );
   }

   public void testConvert_booleanArray_twodims()
   {
      boolean[][] expected = { { true, false }, { true, false } };
      boolean[][] actual = (boolean[][]) converter.convert( expected );
      Assertions.assertEquals( expected, actual );
   }

   public void testConvert_null()
   {
      assertNull( converter.convert( null ) );
   }

   public void testConvert_strings()
   {
      String[] expected = { "true", "yes", "on", "false", "no", "off" };
      boolean[] actual = (boolean[]) converter.convert( expected );
      Assertions.assertEquals( new boolean[] { true, true, true, false, false, false }, actual );
   }

   public void testConvert_strings_twodims()
   {
      String[][] expected = { { "true", "yes", "on" }, { "false", "no", "off" } };
      boolean[][] actual = (boolean[][]) converter.convert( expected );
      Assertions.assertEquals( new boolean[][] { { true, true, true }, { false, false, false } },
            actual );
   }

   protected void setUp() throws Exception
   {
      converter = new BooleanArrayConverter();
   }
}