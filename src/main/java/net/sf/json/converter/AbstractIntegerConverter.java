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

/**
 * Base class por primitive integer conversion.
 *
 * @author Andres Almiray
 */
public abstract class AbstractIntegerConverter extends AbstractPrimitiveConverter
{

   public AbstractIntegerConverter()
   {
      super();
   }

   public AbstractIntegerConverter( boolean useDefault )
   {
      super( useDefault );
   }

   /**
    * Trims the String from the begining to the first "."
    */
   protected String getIntegerValue( String str )
   {
      int index = str.indexOf( "." );
      if( index != -1 ){
         str = str.substring( 0, index );
      }
      return str;
   }
}