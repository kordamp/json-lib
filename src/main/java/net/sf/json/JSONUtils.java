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

/*
 Copyright (c) 2002 JSON.org

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 The Software shall be used for Good, not Evil.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

import java.util.Collection;

import org.apache.commons.lang.ArrayUtils;

/**
 * Provides useful methods on java objects.
 * 
 * @author Andres Almiray
 * @version 1
 */
public class JSONUtils
{
   /**
    * Tests if obj is an array or Collection.
    */
   public static boolean isArray( Object obj )
   {
      if( obj != null && obj.getClass()
            .isArray() ){
         return true;
      }
      if( obj instanceof Collection ){
         return true;
      }
      return false;
   }

   /**
    * Tests if obj is a Boolean or primitive boolean
    */
   public static boolean isBoolean( Object obj )
   {
      if( obj instanceof Boolean ){
         return true;
      }
      if( obj != null && obj.getClass() == Boolean.TYPE ){
         return true;
      }
      return false;
   }

   /**
    * Tests if obj is a primitive number or wrapper.<br>
    */
   public static boolean isNumber( Object obj )
   {
      if( (obj != null && obj.getClass() == Byte.TYPE)
            || (obj != null && obj.getClass() == Short.TYPE)
            || (obj != null && obj.getClass() == Integer.TYPE)
            || (obj != null && obj.getClass() == Long.TYPE)
            || (obj != null && obj.getClass() == Float.TYPE)
            || (obj != null && obj.getClass() == Double.TYPE) ){
         return true;
      }
      if( (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer)
            || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Double) ){
         return true;
      }
      return false;
   }

   /**
    * Tests if obj is not a boolean, number, string or array.
    */
   public static boolean isObject( Object obj )
   {
      return !isNumber( obj ) && !isString( obj ) && !isBoolean( obj ) && !isArray( obj );
   }

   /**
    * Tests if obj is a String or a char
    */
   public static boolean isString( Object obj )
   {
      if( obj instanceof String ){
         return true;
      }
      if( obj instanceof Character ){
         return true;
      }
      if( obj != null && obj.getClass() == Character.TYPE ){
         return true;
      }
      return false;
   }

   /**
    * Converts an array of primitive chars to objects.<br>
    * <p>
    * <strong>This is method is not in ArrayUtils.</strong>
    * </p>
    * <p>
    * This method returns <code>null</code> for a <code>null</code> input
    * array.
    * </p>
    * 
    * @param array a <code>char</code> array
    * @return a <code>Character</code> array, <code>null</code> if null
    *         array input
    */
   public static Object[] toObject( char[] array )
   {
      if( array == null ){
         return null;
      }else if( array.length == 0 ){
         return ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
      }
      final Character[] result = new Character[array.length];
      for( int i = 0; i < array.length; i++ ){
         result[i] = new Character( array[i] );
      }
      return result;
   }

   private JSONUtils()
   {
      super();
   }
}