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
 * Base class for array conversions.
 *
 * @author Andres Almiray
 */
public abstract class AbstractArrayConverter implements Converter
{
   private boolean useDefault = false;

   public AbstractArrayConverter()
   {
   }

   public AbstractArrayConverter( boolean useDefault )
   {
      this.useDefault = useDefault;
   }

   public abstract Object convert( Object array );

   public boolean isUseDefault()
   {
      return useDefault;
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

   /**
    * Returns the number of dimensions in an array class.
    */
   protected int getDimensions( Class arrayClass )
   {
      if( arrayClass == null || !arrayClass.isArray() ){
         return 0;
      }

      return 1 + getDimensions( arrayClass.getComponentType() );
   }
}