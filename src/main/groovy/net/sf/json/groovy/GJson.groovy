/*
 * Copyright 2002-2008 the original author or authors.
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

package net.sf.json.groovy

import net.sf.json.JSON
import net.sf.json.JSONArray
import net.sf.json.JSONObject
import net.sf.json.JSONFunction
import net.sf.json.JSONSerializer

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
class GJson {
   public static void enhanceClasses(){
      def s = enhanceString()
      def l = enhanceCollection()
      def m = enhanceMap()
      def j = enhanceJSONObject()
      if( s || l || m || j ){
         ExpandoMetaClass.enableGlobally()
      }
   }

   private static boolean enhanceString(){
      if( !String.metaClass.methods.find{ it.name == "isJsonEnhanced" } ){
         def asType = String.metaClass.&asType
         String.metaClass.asType = { Class clazz ->
            switch( clazz ){
               case JSON:
                  return JSONSerializer.toJSON( delegate )
               case JSONArray:
                  return JSONArray.fromObject( delegate )
               case JSONObject:
                  return JSONObject.fromObject( delegate )
               case JSONFunction:
                  return JSONFunction.parse( delegate )
               default:
                  return asType(clazz)
            }
         }
         String.metaClass.isJsonEnhanced = { true }
         return true
      }
      return false
   }

   private static boolean enhanceCollection(){
      if( !Collection.metaClass.methods.find{ it.name == "isJsonEnhanced" } ){
         def asType = Collection.metaClass.&asType
         def typeConverter = { Class clazz ->
            switch( clazz ){
               case JSONArray:
                  return JSONArray.fromObject( delegate )
               default:
                  return asType(clazz)
            }
         }
         // FIXME why won't Collection.metaClass.asType work ?
         ArrayList.metaClass.asType = typeConverter
         HashSet.metaClass.asType = typeConverter
         Collection.metaClass.isJsonEnhanced = { true }
         return true
      }
      return false
   }

   private static boolean enhanceMap(){
      if( !Map.metaClass.methods.find{ it.name == "isJsonEnhanced" } ){
         def asType = Map.metaClass.&asType
         Map.metaClass.asType = { Class clazz ->
            switch( clazz ){
               case JSONObject:
                  return JSONObject.fromObject( delegate )
               default:
                  return asType(clazz)
            }
         }
         Map.metaClass.isJsonEnhanced = { true }
         return true
      }
      return false
   }

   private static boolean enhanceJSONObject(){
      if( !JSONObject.metaClass.methods.find{ it.name == "isJsonEnhanced" } ){
         JSONObject.metaClass.leftShift = { args ->
            if( args instanceof Map ){
               delegate.putAll( args )
               delegate
            }else if( args instanceof List && args.size() >= 2 ){
               def key = args.remove(0)
               if( args.size() == 1 ){
                  delegate.element( key, args.get(0) )
               }else{
                  delegate.element( key, args )
               }
            }
         }
         JSONObject.metaClass.get = { String key, defaultValue ->
            def previousValue = delegate.opt( key )
            if( !previousValue ){
               delegate.element( key, defaultValue )
               previousValue = delegate.get( key )
            }
            return previousValue
         }
         JSONObject.metaClass.isJsonEnhanced = { true }
         return true
      }
      return false
   }
}