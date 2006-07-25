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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andres Almiray
 */
public class ConverterRegistry
{
   private static Map converters = new HashMap();

   static{
      converters.put( Boolean.TYPE, new BooleanConverter( false ) );
      converters.put( boolean.class, new BooleanConverter( false ) );
      converters.put( boolean[].class, new BooleanArrayConverter( false ) );
      converters.put( Boolean[].class, new BooleanObjectArrayConverter( false ) );

      converters.put( Character.TYPE, new CharConverter( '\0' ) );
      converters.put( char.class, new CharConverter( '\0' ) );
      converters.put( char[].class, new CharArrayConverter( '\0' ) );
      converters.put( Character[].class, new CharObjectArrayConverter( '\0' ) );

      converters.put( Byte.TYPE, new ByteConverter( (byte) 0 ) );
      converters.put( byte.class, new ByteConverter( (byte) 0 ) );
      converters.put( byte[].class, new ByteArrayConverter( (byte) 0 ) );
      converters.put( Byte[].class, new NumberConverter( Byte.class, new Byte( (byte) 0 ) ) );

      converters.put( Short.TYPE, new ShortConverter( (short) 0 ) );
      converters.put( short.class, new ShortConverter( (short) 0 ) );
      converters.put( short[].class, new ShortArrayConverter( (short) 0 ) );
      converters.put( Short[].class, new NumberConverter( Short.class, new Short( (short) 0 ) ) );

      converters.put( Integer.TYPE, new IntConverter( 0 ) );
      converters.put( int.class, new IntConverter( 0 ) );
      converters.put( int[].class, new IntArrayConverter( 0 ) );
      converters.put( Integer[].class, new NumberConverter( Integer.class, new Integer( 0 ) ) );

      converters.put( Long.TYPE, new LongConverter( 0 ) );
      converters.put( long.class, new LongConverter( 0 ) );
      converters.put( long[].class, new LongArrayConverter( 0 ) );
      converters.put( Long[].class, new NumberConverter( Long.class, new Long( 0 ) ) );

      converters.put( Float.TYPE, new FloatConverter( 0 ) );
      converters.put( float.class, new FloatConverter( 0 ) );
      converters.put( float[].class, new FloatArrayConverter( 0 ) );
      converters.put( Float[].class, new NumberConverter( Float.class, new Float( 0 ) ) );

      converters.put( Double.TYPE, new DoubleConverter( 0 ) );
      converters.put( double.class, new DoubleConverter( 0 ) );
      converters.put( double[].class, new DoubleArrayConverter( 0 ) );
      converters.put( Double[].class, new NumberConverter( Double.class, new Double( 0 ) ) );
   }

   public static Converter getConverterFor( Class type )
   {
      return (Converter) converters.get( type );
   }

   public static Class getInnerComponentType( Class type )
   {
      if( !type.isArray() ){
         return type;
      }
      return getInnerComponentType( type.getComponentType() );
   }

   public static void removeConverterFor( Class type )
   {
      converters.remove( type );
   }

   public static void setConverterFor( Class type, Converter converter )
   {
      converters.put( type, converter );
   }

   private ConverterRegistry()
   {

   }
}