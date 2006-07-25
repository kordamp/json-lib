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

import junit.framework.TestSuite;

/**
 * @author Andres Almiray
 */
public class AllTests extends TestSuite
{
   public static TestSuite suite() throws Exception
   {
      TestSuite suite = new TestSuite();
      suite.setName( "converter" );

      suite.addTest( new TestSuite( TestBooleanConverter.class ) );
      suite.addTest( new TestSuite( TestCharConverter.class ) );
      suite.addTest( new TestSuite( TestByteConverter.class ) );
      suite.addTest( new TestSuite( TestShortConverter.class ) );
      suite.addTest( new TestSuite( TestIntConverter.class ) );
      suite.addTest( new TestSuite( TestLongConverter.class ) );
      suite.addTest( new TestSuite( TestFloatConverter.class ) );
      suite.addTest( new TestSuite( TestDoubleConverter.class ) );
      suite.addTest( new TestSuite( TestNumberConverter.class ) );

      suite.addTest( new TestSuite( TestBooleanArrayConverter.class ) );
      suite.addTest( new TestSuite( TestCharArrayConverter.class ) );
      suite.addTest( new TestSuite( TestByteArrayConverter.class ) );
      suite.addTest( new TestSuite( TestShortArrayConverter.class ) );
      suite.addTest( new TestSuite( TestIntArrayConverter.class ) );
      suite.addTest( new TestSuite( TestLongArrayConverter.class ) );
      suite.addTest( new TestSuite( TestFloatArrayConverter.class ) );
      suite.addTest( new TestSuite( TestDoubleArrayConverter.class ) );

      suite.addTest( new TestSuite( TestBooleanObjectArrayConverter.class ) );
      suite.addTest( new TestSuite( TestCharObjectArrayConverter.class ) );

      return suite;
   }
}