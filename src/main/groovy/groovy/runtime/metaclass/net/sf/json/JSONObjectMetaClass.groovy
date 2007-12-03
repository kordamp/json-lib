/*
 * Copyright 2002-2007 the original author or authors.
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

package groovy.runtime.metaclass.net.sf.json

import net.sf.json.JSONObject

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
class JSONObjectMetaClass extends groovy.lang.DelegatingMetaClass {
   JSONObjectMetaClass( final Class clazz ){
      super( clazz )
      initialize()
   }

   JSONObjectMetaClass( MetaClassRegistry registry, final Class clazz ){
      super( clazz )
   }

   public Object invokeMethod(Object instance, String methodName, Object[] args )
   {
       if( methodName == "leftShift" && args?.length == 1 ){
          return leftShift( instance, args[0] )
       }else if( methodName == "get" && args?.length == 2 ){
          return getWithDefaultValue( instance, args[0], args[1] )
       }else{
          return super.invokeMethod( instance, methodName, args )
       }
   }

   private Object leftShift( instance, args ){
      if( args instanceof Map ){
         instance.putAll( args )
         instance
      }else if( args instanceof List && args.size() >= 2 ){
         def key = args.remove(0)
         if( args.size() == 1 ){
            instance.element( key, args.get(0) )
         }else{
            instance.element( key, args )
         }
      }
   }

   private Object getWithDefaultValue( instance, key, defaultValue ){
      def previousValue = instance.opt( key )
      if( !previousValue ){
         instance.put( key,  defaultValue )
         previousValue = instance.get( key )
      }
      previousValue
   }
}