package net.sf.json;

import java.util.List;

import junit.framework.Assert;

public class Assertions extends Assert
{
   public static void assertEquals( boolean[] expecteds, boolean[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( boolean[] expecteds, Object[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( byte[] expecteds, byte[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( byte[] expecteds, Object[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( char[] expecteds, char[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( char[] expecteds, Object[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( double[] expecteds, double[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( double[] expecteds, Object[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( float[] expecteds, float[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( float[] expecteds, Object[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( int[] expecteds, int[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( int[] expecteds, Object[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( JSONFunction[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i].toString(), b[i].toString() );
      }
   }

   public static void assertEquals( long[] expecteds, long[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( long[] expecteds, Object[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( Object[] expecteds, Object[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( short[] expecteds, Object[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( short[] expecteds, short[] actuals )
   {
      assertEquals( null, expecteds, actuals );
   }

   public static void assertEquals( String message, boolean[] expecteds, boolean[] actuals )
   {
      if( expecteds == actuals ){
         return;
      }
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", expecteds[i],
               actuals[i] );
      }
   }

   public static void assertEquals( String message, boolean[] expecteds, Object[] actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", Boolean.valueOf(
               expecteds[i] ), actuals[i] );
      }
   }

   public static void assertEquals( String message, byte[] expecteds, byte[] actuals )
   {
      if( expecteds == actuals ){
         return;
      }
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", expecteds[i],
               actuals[i] );
      }
   }

   public static void assertEquals( String message, byte[] expecteds, Object[] actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", new Byte(
               expecteds[i] ), actuals[i] );
      }
   }

   public static void assertEquals( String message, char[] expecteds, char[] actuals )
   {
      if( expecteds == actuals ){
         return;
      }
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", expecteds[i],
               actuals[i] );
      }
   }

   public static void assertEquals( String message, char[] expecteds, Object[] actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", new Character(
               expecteds[i] ), actuals[i] );
      }
   }

   public static void assertEquals( String message, double[] expecteds, double[] actuals )
   {
      if( expecteds == actuals ){
         return;
      }
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", expecteds[i],
               actuals[i], 0d );
      }
   }

   public static void assertEquals( String message, double[] expecteds, Object[] actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", new Double(
               expecteds[i] ), actuals[i] );
      }
   }

   public static void assertEquals( String message, float[] expecteds, float[] actuals )
   {
      if( expecteds == actuals ){
         return;
      }
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", expecteds[i],
               actuals[i], 0f );
      }
   }

   public static void assertEquals( String message, float[] expecteds, Object[] actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", new Float(
               expecteds[i] ), actuals[i] );
      }
   }

   public static void assertEquals( String message, int[] expecteds, int[] actuals )
   {
      if( expecteds == actuals ){
         return;
      }
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", expecteds[i],
               actuals[i] );
      }
   }

   public static void assertEquals( String message, int[] expecteds, Object[] actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", new Integer(
               expecteds[i] ), actuals[i] );
      }
   }

   public static void assertEquals( String message, long[] expecteds, long[] actuals )
   {
      if( expecteds == actuals ){
         return;
      }
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", expecteds[i],
               actuals[i] );
      }
   }

   public static void assertEquals( String message, long[] expecteds, Object[] actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", new Long(
               expecteds[i] ), actuals[i] );
      }
   }

   public static void assertEquals( String message, Object[] expecteds, Object[] actuals )
   {
      if( expecteds == actuals ){
         return;
      }
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         Object o1 = expecteds[i];
         Object o2 = actuals[i];
         if( o1.getClass()
               .isArray() && o2.getClass()
               .isArray() ){
            Class type1 = o1.getClass()
                  .getComponentType();
            Class type2 = o2.getClass()
                  .getComponentType();
            if( type1.isPrimitive() ){
               if( type1 == Boolean.TYPE ){
                  if( type2 == Boolean.TYPE ){
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (boolean[]) o1, (boolean[]) o2 );
                  }else{
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (boolean[]) o1, (Object[]) o2 );
                  }
               }else if( type1 == Byte.TYPE ){
                  if( type2 == Byte.TYPE ){
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (byte[]) o1, (byte[]) o2 );
                  }else{
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (byte[]) o1, (Object[]) o2 );
                  }
               }else if( type1 == Short.TYPE ){
                  if( type2 == Short.TYPE ){
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (short[]) o1, (short[]) o2 );
                  }else{
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (short[]) o1, (Object[]) o2 );
                  }
               }else if( type1 == Integer.TYPE ){
                  if( type2 == Integer.TYPE ){
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (int[]) o1, (int[]) o2 );
                  }else{
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (int[]) o1, (Object[]) o2 );
                  }
               }else if( type1 == Long.TYPE ){
                  if( type2 == Long.TYPE ){
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (long[]) o1, (long[]) o2 );
                  }else{
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (long[]) o1, (Object[]) o2 );
                  }
               }else if( type1 == Float.TYPE ){
                  if( type2 == Float.TYPE ){
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (float[]) o1, (float[]) o2 );
                  }else{
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (float[]) o1, (Object[]) o2 );
                  }
               }else if( type1 == Double.TYPE ){
                  if( type2 == Double.TYPE ){
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (double[]) o1, (double[]) o2 );
                  }else{
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (double[]) o1, (Object[]) o2 );
                  }
               }else if( type1 == Character.TYPE ){
                  if( type2 == Character.TYPE ){
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (char[]) o1, (char[]) o2 );
                  }else{
                     assertEquals( header + "arrays first differed at element " + i + ";",
                           (char[]) o1, (Object[]) o2 );
                  }
               }
            }else{
               Object[] expected = (Object[]) o1;
               Object[] actual = (Object[]) o2;
               assertEquals( header + "arrays first differed at element " + i + ";", expected,
                     actual );
            }
         }else{
            assertEquals( header + "arrays first differed at element [" + i + "];", o1, o2 );
         }
      }
   }

   public static void assertEquals( String message, short[] expecteds, Object[] actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", new Short(
               expecteds[i] ), actuals[i] );
      }
   }

   public static void assertEquals( String message, short[] expecteds, short[] actuals )
   {
      if( expecteds == actuals ){
         return;
      }
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( actuals.length != expecteds.length ){
         fail( header + "array lengths differed, expected.length=" + expecteds.length
               + " actual.length=" + actuals.length );
      }

      for( int i = 0; i < expecteds.length; i++ ){
         assertEquals( header + "arrays first differed at element [" + i + "];", expecteds[i],
               actuals[i] );
      }
   }

   public static void assertListEquals( List expecteds, List actuals )
   {
      assertListEquals( null, expecteds, actuals );
   }

   public static void assertListEquals( String message, List expecteds, List actuals )
   {
      String header = message == null ? "" : message + ": ";
      if( expecteds == null ){
         fail( header + "expected array was null" );
      }
      if( actuals == null ){
         fail( header + "actual array was null" );
      }
      if( expecteds.equals( actuals ) ){
         return;
      }
      if( actuals.size() != expecteds.size() ){
         fail( header + "list sizes differed, expected.size()=" + expecteds.size()
               + " actual.size()=" + actuals.size() );
      }

      int max = expecteds.size();
      for( int i = 0; i < max; i++ ){
         Object o1 = expecteds.get( i );
         Object o2 = actuals.get( i );
         if( o1.getClass()
               .isArray() && o2.getClass()
               .isArray() ){
            Object[] expected = (Object[]) o1;
            Object[] actual = (Object[]) o2;
            assertEquals( header + "arrays first differed at element " + i + ";", expected, actual );
         }else if( List.class.isAssignableFrom( o1.getClass() )
               && List.class.isAssignableFrom( o2.getClass() ) ){
            assertEquals( header + "lists first differed at element [" + i + "];", (List) o1,
                  (List) o2 );
         }else{
            assertEquals( header + "lists first differed at element [" + i + "];", o1, o2 );
         }
      }
   }
}