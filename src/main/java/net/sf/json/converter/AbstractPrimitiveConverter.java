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
 * Base class for primitive value conversion.<br>
 * 
 * @author Andres Almiray
 */
public abstract class AbstractPrimitiveConverter implements Converter
{
   private boolean useDefault = false;

   public AbstractPrimitiveConverter()
   {

   }

   public AbstractPrimitiveConverter( boolean useDefault )
   {
      this.useDefault = useDefault;
   }

   /**
    * If the class <code>type</code> represents a decimal.
    */
   public boolean isDecimal( Class type )
   {
      return (Double.class.isAssignableFrom( type ) || Float.class.isAssignableFrom( type )
            || Double.TYPE == type || Float.TYPE == type);
   }

   /**
    * If the class <code>type</code> represents an integer.
    */
   public boolean isInteger( Class type )
   {
      return (Byte.class.isAssignableFrom( type ) || Short.class.isAssignableFrom( type )
            || Integer.class.isAssignableFrom( type ) || Long.class.isAssignableFrom( type )
            || Byte.TYPE == type || Short.TYPE == type || Integer.TYPE == type || Long.TYPE == type);
   }

   public boolean isUseDefault()
   {
      return useDefault;
   }
}