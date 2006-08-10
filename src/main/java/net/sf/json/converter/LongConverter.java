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
public class LongConverter extends AbstractIntegerConverter
{
   private long defaultValue;

   public LongConverter()
   {
      super();
   }

   public LongConverter( long defaultValue )
   {
      super( true );
      this.defaultValue = defaultValue;
   }

   public long convert( Object value )
   {
      if( value == null ){
         if( isUseDefault() ){
            return defaultValue;
         }else{
            throw new ConversionException( "value is null" );
         }
      }

      if( value instanceof Number ){
         return ((Number) value).longValue();
      }else{
         long i = 0;
         try{
            i = Long.parseLong( getIntegerValue( String.valueOf( value ) ) );
            return i;
         }
         catch( NumberFormatException nfe ){
            if( isUseDefault() ){
               return defaultValue;
            }else{
               throw new ConversionException( nfe );
            }
         }
      }
   }
}