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
public class TestCharConverter extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestCharConverter.class );
   }

   public TestCharConverter( String name )
   {
      super( name );
   }

   public void testConvert()
   {
      String expected = String.valueOf( "A" );
      char actual = new CharConverter().convert( expected );
      assertEquals( 'A', actual );
   }

   public void testConvert_useDefault()
   {
      String expected = String.valueOf( "" );
      char actual = new CharConverter( 'A' ).convert( expected );
      assertEquals( 'A', actual );
   }
}