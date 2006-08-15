package net.sf.json;

import java.util.List;

import net.sf.ezmorph.test.ArrayAssertions;

public class Assertions extends ArrayAssertions
{
   public static void assertEquals( JSONFunction[] a, Object[] b )
   {
      assertEquals( a.length, b.length );
      for( int i = 0; i < a.length; i++ ){
         assertEquals( a[i].toString(), b[i].toString() );
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

         // handle nulls
         if( o1 == null ){
            if( o2 == null ){
               return;
            }else{
               fail( header + "lists first differed at element [" + i + "];" );
            }
         }else{
            if( o2 == null ){
               fail( header + "lists first differed at element [" + i + "];" );
            }
         }

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