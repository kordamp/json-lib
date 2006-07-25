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
 * Converts an array to a long[].
 *
 * @author Andres Almiray
 */
public class LongArrayConverter extends AbstractArrayConverter
{
   private static final Class LONG_ARRAY_CLASS = long[].class;
   private long defaultValue;

   public LongArrayConverter()
   {
      super( false );
   }

   public LongArrayConverter( long defaultValue )
   {
      super( true );
      this.defaultValue = defaultValue;
   }

   public Object convert( Object array )
   {
      if( array == null ){
         return null;
      }

      if( LONG_ARRAY_CLASS.isAssignableFrom( array.getClass() ) ){
         // no conversion needed
         return (long[]) array;
      }

      if( array.getClass()
            .isArray() ){
         int length = Array.getLength( array );
         int dims = getDimensions( array.getClass() );
         int[] dimensions = createDimensions( dims, length );
         Object result = Array.newInstance( long.class, dimensions );
         LongConverter converter = isUseDefault() ? new LongConverter( defaultValue )
               : new LongConverter();
         if( dims == 1 ){
            for( int index = 0; index < length; index++ ){
               Array.set( result, index,
                     new Long( converter.convert( Array.get( array, index ) ) ) );
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

   public long getDefaultValue()
   {
      return defaultValue;
   }

   public void setDefaultValue( long defaultValue )
   {
      this.defaultValue = defaultValue;
      setUseDefault( true );
   }
}