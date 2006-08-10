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
public class TestBooleanConverter extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestBooleanConverter.class );
   }

   public TestBooleanConverter( String name )
   {
      super( name );
   }

   public void testConvert_useDefault()
   {
      String expected = String.valueOf( "A" );
      boolean actual = new BooleanConverter( true ).convert( expected );
      assertEquals( true, actual );
   }

   public void testConvertStringValues_false()
   {
      assertFalse( new BooleanConverter().convert( "false" ) );
      assertFalse( new BooleanConverter().convert( "no" ) );
      assertFalse( new BooleanConverter().convert( "off" ) );
   }

   public void testConvertStringValues_true()
   {
      assertTrue( new BooleanConverter().convert( "true" ) );
      assertTrue( new BooleanConverter().convert( "yes" ) );
      assertTrue( new BooleanConverter().convert( "on" ) );
   }
}