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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

/**
 * @author Andres Almiray
 */
public class TestJSONArray extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestJSONArray.class );
   }

   public TestJSONArray( String testName )
   {
      super( testName );
   }

   public void testArray_nullObjects()
   {
      testJSONArray( new Object[] { null, null }, "[null,null]" );
   }

   public void testCollection()
   {
      List l = new ArrayList();
      l.add( Boolean.TRUE );
      l.add( new Integer( 1 ) );
      l.add( "string" );
      testJSONArray( l, "[true,1,\"string\"]" );
   }

   public void testCollection_nullObjects()
   {
      List l = new ArrayList();
      l.add( null );
      l.add( null );
      testJSONArray( l, "[null,null]" );
   }

   public void testFunctionArray_JSONTokener()
   {
      testJSONArray( new JSONTokener( "[function(a){ return a; }]" ), "[function(a){ return a; }]" );
   }

   public void testFunctionArray_ObjectArray()
   {
      testJSONArray( new JSONFunction[] { new JSONFunction( new String[] { "a" }, "return a;" ) },
            "[function(a){ return a; }]" );
   }

   public void testFunctionArray_String()
   {
      testJSONArray( "[function(a){ return a; }]", "[function(a){ return a; }]" );
   }

   public void testMultiFunctionArray_String()
   {
      testJSONArray( "[function(a){ return a; },[function(b){ return b; }]]",
            "[function(a){ return a; },[function(b){ return b; }]]" );
   }

   public void testPrimitiveBooleanArray()
   {
      testJSONArray( new boolean[] { true, false }, "[true,false]" );
   }

   public void testPrimitiveByteArray()
   {
      testJSONArray( new byte[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testPrimitiveCharArray()
   {
      testJSONArray( new char[] { 'a', 'b', 'c' }, "[\"a\",\"b\",\"c\"]" );
   }

   public void testPrimitiveDoubleArray()
   {
      testJSONArray( new double[] { 1.1, 2.2, 3.3 }, "[1.1,2.2,3.3]" );
   }

   public void testPrimitiveFloatArray()
   {
      testJSONArray( new float[] { 1.1f, 2.2f, 3.3f }, "[1.1,2.2,3.3]" );
   }

   public void testPrimitiveIntArray()
   {
      testJSONArray( new int[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testPrimitiveLongArray()
   {
      testJSONArray( new long[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testPrimitiveShortArray()
   {
      testJSONArray( new short[] { 1, 2, 3 }, "[1,2,3]" );
   }

   public void testToBooleanArray()
   {
      boolean[] expected = new boolean[] { true, false };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToBooleanArray_object()
   {
      Boolean[] expected = new Boolean[] { Boolean.TRUE, Boolean.FALSE };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToBooleanList()
   {
      List expected = new ArrayList();
      expected.add( Boolean.TRUE );
      expected.add( Boolean.FALSE );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToBooleanMultiArray()
   {
      boolean[][] expected = new boolean[][] { { true, false }, { false, true } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToByteArray()
   {
      byte[] expected = new byte[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToByteArray_object()
   {
      Byte[] expected = new Byte[] { new Byte( (byte) 1 ), new Byte( (byte) 2 ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToByteList()
   {
      List expected = new ArrayList();
      expected.add( new Byte( (byte) 1 ) );
      expected.add( new Byte( (byte) 2 ) );
      expected.add( new Byte( (byte) 3 ) );
      expected.add( new Byte( (byte) 4 ) );
      expected.add( new Byte( (byte) 5 ) );
      expected.add( new Byte( (byte) 6 ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToByteMultiArray()
   {
      byte[][] expected = new byte[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToCharacterArray()
   {
      Character[] expected = new Character[] { new Character( 'A' ), new Character( 'B' ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToCharacterList()
   {
      List expected = new ArrayList();
      expected.add( new Character( 'A' ) );
      expected.add( new Character( 'B' ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToCharArray()
   {
      char[] expected = new char[] { 'A', 'B' };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToCharMultiArray()
   {
      char[][] expected = new char[][] { { 'a', 'b' }, { 'c', 'd' } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToDoubleArray()
   {
      double[] expected = new double[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToDoubleArray_object()
   {
      Double[] expected = new Double[] { new Double( 1d ), new Double( 2d ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToDoubleList()
   {
      List expected = new ArrayList();
      expected.add( new Double( 1d ) );
      expected.add( new Double( 2d ) );
      expected.add( new Double( 3d ) );
      expected.add( new Double( 4d ) );
      expected.add( new Double( 5d ) );
      expected.add( new Double( 6d ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToDoubleMultiArray()
   {
      double[][] expected = new double[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToFloatArray()
   {
      float[] expected = new float[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToFloatArray_object()
   {
      Float[] expected = new Float[] { new Float( 1f ), new Float( 2f ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToFloatList()
   {
      List expected = new ArrayList();
      expected.add( new Float( 1f ) );
      expected.add( new Float( 2f ) );
      expected.add( new Float( 3f ) );
      expected.add( new Float( 4f ) );
      expected.add( new Float( 5f ) );
      expected.add( new Float( 6f ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToFloatMultiArray()
   {
      float[][] expected = new float[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToIntArray()
   {
      int[] expected = new int[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToIntegerArray()
   {
      Integer[] expected = new Integer[] { new Integer( 1 ), new Integer( 2 ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToIntegerList()
   {
      List expected = new ArrayList();
      expected.add( new Integer( 1 ) );
      expected.add( new Integer( 2 ) );
      expected.add( new Integer( 3 ) );
      expected.add( new Integer( 4 ) );
      expected.add( new Integer( 5 ) );
      expected.add( new Integer( 6 ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToIntMultiArray()
   {
      int[][] expected = new int[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToLongArray()
   {
      long[] expected = new long[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToLongArray_object()
   {
      Long[] expected = new Long[] { new Long( 1 ), new Long( 2 ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToLongList()
   {
      List expected = new ArrayList();
      expected.add( new Long( 1 ) );
      expected.add( new Long( 2 ) );
      expected.add( new Long( 3 ) );
      expected.add( new Long( 4 ) );
      expected.add( new Long( 5 ) );
      expected.add( new Long( 6 ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToLongMultiArray()
   {
      long[][] expected = new long[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToShortArray()
   {
      short[] expected = new short[] { 1, 2, 3, 4, 5, 6 };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToShortArray_object()
   {
      Short[] expected = new Short[] { new Short( (short) 1 ), new Short( (short) 2 ) };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToShortList()
   {
      List expected = new ArrayList();
      expected.add( new Short( (short) 1 ) );
      expected.add( new Short( (short) 2 ) );
      expected.add( new Short( (short) 3 ) );
      expected.add( new Short( (short) 4 ) );
      expected.add( new Short( (short) 5 ) );
      expected.add( new Short( (short) 6 ) );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToShortMultiArray()
   {
      short[][] expected = new short[][] { { 1, 2, 3 }, { 4, 5, 6 } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToStringArray()
   {
      String[] expected = new String[] { "1", "2", "3", "4", "5", "6" };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToStringList()
   {
      List expected = new ArrayList();
      expected.add( "A" );
      expected.add( "B" );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToStringMultiArray()
   {
      String[][] expected = new String[][] { { "1", "2", "3" }, { "4", "5", "6" } };
      JSONArray jsonArray = JSONArray.fromObject( expected );
      Object[] actual = JSONArray.toArray( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   public void testToStringMultiList()
   {
      List a = new ArrayList();
      a.add( "a" );
      a.add( "b" );
      List b = new ArrayList();
      b.add( "1" );
      b.add( "2" );
      List expected = new ArrayList();
      expected.add( a );
      expected.add( b );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      List actual = JSONArray.toList( jsonArray );
      Assertions.assertEquals( expected, actual );
   }

   private void testJSONArray( Object array, String expected )
   {
      try{
         JSONArray jsonArray = JSONArray.fromObject( array );
         assertEquals( expected, jsonArray.toString() );
      }
      catch( JSONException jsone ){
         fail( jsone.getMessage() );
      }
   }
}