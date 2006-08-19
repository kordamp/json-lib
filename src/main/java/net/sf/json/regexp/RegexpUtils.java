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

package net.sf.json.regexp;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
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