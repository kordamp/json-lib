package net.sf.json;

import junit.framework.Assert;

public class Assertions extends Assert
{
   public static void assertArrayEquals( boolean[] a, boolean[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i], b[i] );
      }
   }

   public static void assertArrayEquals( boolean[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( new Boolean( a[i] ), b[i] );
      }
   }

   public static void assertArrayEquals( byte[] a, byte[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i], b[i] );
      }
   }

   public static void assertArrayEquals( byte[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( new Byte( a[i] ), b[i] );
      }
   }

   public static void assertArrayEquals( double[] a, double[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i], b[i], 0d );
      }
   }

   public static void assertArrayEquals( double[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( new Double( a[i] ), b[i] );
      }
   }

   public static void assertArrayEquals( float[] a, float[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i], b[i], 0f );
      }
   }

   public static void assertArrayEquals( float[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( new Float( a[i] ), b[i] );
      }
   }

   public static void assertArrayEquals( int[] a, int[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i], b[i] );
      }
   }

   public static void assertArrayEquals( int[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( new Integer( a[i] ), b[i] );
      }
   }

   public static void assertArrayEquals( JSONFunction[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i].toString(), b[i].toString() );
      }
   }

   public static void assertArrayEquals( long[] a, long[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i], b[i] );
      }
   }

   public static void assertArrayEquals( long[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( new Long( a[i] ), b[i] );
      }
   }

   public static void assertArrayEquals( Object[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i], b[i] );
      }
   }

   public static void assertArrayEquals( short[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( new Short( a[i] ), b[i] );
      }
   }

   public static void assertArrayEquals( short[] a, short[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i], b[i] );
      }
   }
}