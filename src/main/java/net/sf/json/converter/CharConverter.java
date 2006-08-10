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
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class CharConverter extends AbstractPrimitiveConverter
{
   private char defaultValue;

   public CharConverter()
   {
      super();
   }

   public CharConverter( char defaultValue )
   {
      super( true );
      this.defaultValue = defaultValue;
   }

   public char convert( Object value )
   {
      if( value == null ){
         if( isUseDefault() ){
            return defaultValue;
         }else{
            throw new ConversionException( "value is null" );
         }
      }

      if( value instanceof Character ){
         return ((Character) value).charValue();
      }else{
         String s = String.valueOf( value );
         if( s.length() > 0 ){
            return s.charAt( 0 );
         }else{
            if( isUseDefault() ){
               return defaultValue;
            }else{
               throw new ConversionException( "Can't convert value: " + value );
            }
         }
      }
   }
}