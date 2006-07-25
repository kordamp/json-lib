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

import java.lang.reflect.Array;

/**
 * Converts an array to a Character[].
 *
 * @author Andres Almiray
 */
public class CharObjectArrayConverter extends AbstractArrayConverter
{
   private static final Class CHAR_ARRAY_CLASS = Character[].class;
   private char defaultValue;

   public CharObjectArrayConverter()
   {
      super( false );
   }

   public CharObjectArrayConverter( char defaultValue )
   {
      super( true );
      this.defaultValue = defaultValue;
   }

   public Object convert( Object array )
   {
      if( array == null ){
         return null;
      }

      if( CHAR_ARRAY_CLASS.isAssignableFrom( array.getClass() ) ){
         // no conversion needed
         return (Character[]) array;
      }

      if( array.getClass()
            .isArray() ){
         int length = Array.getLength( array );
         int dims = getDimensions( array.getClass() );
         int[] dimensions = createDimensions( dims, length );
         Object result = Array.newInstance( Character.class, dimensions );
         CharConverter converter = isUseDefault() ? new CharConverter( defaultValue )
               : new CharConverter();
         if( dims == 1 ){
            for( int index = 0; index < length; index++ ){
               Array.set( result, index,
                     new Character( converter.convert( Array.get( array, index ) ) ) );
            }
         }else{
            for( int index = 0; index < length; index++ ){
               Array.set( result, index, convert( Array.get( array, index ) ) );
            }
         }
         return result;
      }else{
         throw new ConversionException( "argument is not an array: " + array.getClass() );
      }
   }

   public char getDefaultValue()
   {
      return defaultValue;
   }

   public void setDefaultValue( char defaultValue )
   {
      this.defaultValue = defaultValue;
      setUseDefault( true );
   }
}