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

import org.apache.commons.beanutils.MethodUtils;

/**
 * Converts an array to an array of subclass of Number.
 *
 * @author Andres Almiray
 */
public class NumberArrayConverter implements Converter
{
   private Number defaultValue;
   private boolean useDefault = false;
   private Class type;

   public NumberArrayConverter( Class type )
   {
      this( type, null );
   }

   public NumberArrayConverter( Class type, Number defaultValue )
   {
      if( !Number.class.isAssignableFrom( type ) ){
         throw new ConversionException( "type is not a subclass of Number: " + type );
      }
      this.type = type;
      setDefaultValue( defaultValue );
   }

   public Object convert( Object array )
   {
      if( array == null ){
         return null;
      }

      if( array.getClass()
            .isArray() ){
         int length = Array.getLength( array );
         int dims = getDimensions( array.getClass() );
         int[] dimensions = createDimensions( dims, length );
         Object result = Array.newInstance( type, dimensions );
         AbstractPrimitiveConverter converter = null;
         if( Byte.class.isAssignableFrom( type ) ){
            converter = defaultValue != null && isUseDefault() ? new ByteConverter(
                  defaultValue.byteValue() ) : new ByteConverter();
         }else if( Short.class.isAssignableFrom( type ) ){
            converter = defaultValue != null && isUseDefault() ? new ShortConverter(
                  defaultValue.shortValue() ) : new ShortConverter();
         }else if( Integer.class.isAssignableFrom( type ) ){
            converter = defaultValue != null && isUseDefault() ? new IntConverter(
                  defaultValue.intValue() ) : new IntConverter();
         }else if( Long.class.isAssignableFrom( type ) ){
            converter = defaultValue != null && isUseDefault() ? new LongConverter(
                  defaultValue.longValue() ) : new LongConverter();
         }else if( Float.class.isAssignableFrom( type ) ){
            converter = defaultValue != null && isUseDefault() ? new FloatConverter(
                  defaultValue.floatValue() ) : new FloatConverter();
         }else if( Double.class.isAssignableFrom( type ) ){
            converter = defaultValue != null && isUseDefault() ? new DoubleConverter(
                  defaultValue.doubleValue() ) : new DoubleConverter();
         }

         if( dims == 1 ){
            for( int index = 0; index < length; index++ ){
               try{
                  Object value = Array.get( array, index );
                  Object converted = MethodUtils.invokeMethod( converter, "convert", value );
                  Array.set( result, index, converted );
               }
               catch( Exception e ){
                  throw new ConversionException( e );
               }
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

   public boolean isUseDefault()
   {
      return useDefault;
   }

   public void setDefaultValue( Number defaultValue )
   {
      this.defaultValue = defaultValue;
      setUseDefault( true );
   }

   public void setUseDefault( boolean useDefault )
   {
      this.useDefault = useDefault;
   }

   protected int[] createDimensions( int length, int initial )
   {
      Object dims = Array.newInstance( int.class, length );
      Array.set( dims, 0, new Integer( initial ) );
      return (int[]) dims;
   }

   protected int getDimensions( Class arrayClass )
   {
      if( arrayClass == null || !arrayClass.isArray() ){
         return 0;
      }

      return 1 + getDimensions( arrayClass.getComponentType() );
   }
}