package net.sf.json.regexp;

public class RegexpUtils
{
   private static String javaVersion = "1.3.1";
   static{
      javaVersion = System.getProperty( "java.version" );
   }

   public static RegexpMatcher getMatcher( String pattern )
   {
      if( isJDK13() ){
         return new Perl5RegexpMatcher( pattern );
      }else{
         return new JdkRegexpMatcher( pattern );
      }
   }

   public static boolean isJDK13()
   {
      return javaVersion.indexOf( "1.3" ) != -1;
   }

   private RegexpUtils()
   {

   }
}