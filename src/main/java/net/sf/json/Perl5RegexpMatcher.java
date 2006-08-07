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

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

/**
 * @author Andres Almiray
 * @version 1
 */
public class Perl5RegexpMatcher implements RegexpMatcher
{
   private static final Perl5Compiler compiler = new Perl5Compiler();
   private final PatternMatcher matcher = new Perl5Matcher();
   private Pattern pattern;

   public Perl5RegexpMatcher( String pattern )
   {
      try{
         this.pattern = compiler.compile( pattern, Perl5Compiler.READ_ONLY_MASK );
      }
      catch( MalformedPatternException mpe ){
         throw new RuntimeException( mpe );
      }
   }

   public String getGroupIfMatches( String str, int group )
   {
      if( matches( str ) ){
         matcher.getMatch()
               .group( 1 );
      }
      return "";
   }

   public boolean matches( String str )
   {
      return matcher.matches( str, pattern );
   }
}