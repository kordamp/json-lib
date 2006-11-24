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

import java.io.IOException;
import java.io.Writer;

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

/**
 * JSONNull is equivalent to the value that JavaScript calls null, whilst Java's
 * null is equivalent to the value that JavaScript calls undefined.
 * 
 * @author JSON.org
 * @version 2
 */
public final class JSONNull implements JSON
{
   /** singleton instance */
   private static JSONNull instance;

   static{
      instance = new JSONNull();
   }

   /**
    * Returns the singleton instance of JSONNull
    */
   public static JSONNull getInstance()
   {
      return instance;
   }

   private JSONNull()
   {

   }

   /**
    * A Null object is equal to the null value and to itself.
    * 
    * @param object An object to test for nullness.
    * @return true if the object parameter is the JSONObject.NULL object or
    *         null.
    */
   public boolean equals( Object object )
   {
      return object == null || object == this || object == instance;
   }

   public int hashCode()
   {
      return 42 + "null".hashCode();
   }

   public boolean isArray()
   {
      return false;
   }

   /**
    * Get the "null" string value.
    * 
    * @return The string "null".
    */
   public String toString()
   {
      return "null";
   }

   public String toString( int indentFactor )
   {
      return toString();
   }

   public String toString( int indentFactor, int indent )
   {
      return toString();
   }

   public Writer write( Writer writer )
   {
      try{
         writer.write( toString() );
         return writer;
      }
      catch( IOException e ){
         throw new JSONException( e );
      }
   }
}