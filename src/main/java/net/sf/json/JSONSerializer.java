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

import java.util.Map;

import net.sf.json.util.JSONTokener;
import net.sf.json.util.JSONUtils;

/**
 * Transforms java objects into JSON and back.<br>
 * Transformation from java to JSON is pretty straightforward, but the other way
 * around needs certain configuration, otherwise the java objects produced will
 * be DynaBeans and Lists, because the JSON notation does not carry any
 * information on java classes.<br>
 * Use the provided property setters before calling <code>toJava()</code>.
 *
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class JSONSerializer
{
   public static final int MODE_LIST = 1;
   public static final int MODE_OBJECT_ARRAY = 2;

   /**
    * Creates a JSONObject, JSONArray or a JSONNull from object.
    *
    * @param object
    * @throws JSONException if the object can not be converted
    */
   public static JSON toJSON( Object object )
   {
      return toJSON( object, null, false );
   }

   /**
    * Creates a JSONObject, JSONArray or a JSONNull from object.
    *
    * @param object
    * @param excludes A group of property names to be excluded
    * @throws JSONException if the object can not be converted
    */
   public static JSON toJSON( Object object, String[] excludes )
   {
      return toJSON( object, excludes, false );
   }

   /**
    * Creates a JSONObject, JSONArray or a JSONNull from object.
    *
    * @param object
    * @param excludes A group of property names to be excluded
    * @param ignoreDefaultExcludes A flag for ignoring the default exclusions of
    *        property names
    * @throws JSONException if the object can not be converted
    */
   public static JSON toJSON( Object object, String[] excludes, boolean ignoreDefaultExcludes )
   {
      JSON json = null;
      if( object == null ){
         json = JSONNull.getInstance();
      }else if( object instanceof JSONString ){
         json = toJSON( (JSONString) object, excludes, ignoreDefaultExcludes );
      }else if( object instanceof String ){
         json = toJSON( (String) object, excludes, ignoreDefaultExcludes );
      }else if( JSONUtils.isArray( object ) ){
         json = JSONArray.fromObject( object, excludes, ignoreDefaultExcludes );
      }else{
         try{
            json = JSONObject.fromObject( object, excludes, ignoreDefaultExcludes );
         }
         catch( JSONException e ){
            if( object instanceof JSONTokener ){
               ((JSONTokener) object).reset();
            }
            json = JSONArray.fromObject( object, excludes, ignoreDefaultExcludes );
         }
      }

      return json;
   }

   /**
    * Creates a JSONObject, JSONArray or a JSONNull from a JSONString.
    *
    * @throws JSONException if the string is not a valid JSON string
    */
   private static JSON toJSON( JSONString string, String[] excludes, boolean ignoreDefaultExcludes )
   {
      return toJSON( string.toJSONString(), excludes, ignoreDefaultExcludes );
   }

   /**
    * Creates a JSONObject, JSONArray or a JSONNull from a JSONString.
    *
    * @throws JSONException if the string is not a valid JSON string
    */
   private static JSON toJSON( String string, String[] excludes, boolean ignoreDefaultExcludes )
   {
      JSON json = null;
      if( string.startsWith( "[" ) ){
         json = JSONArray.fromString( string, excludes, ignoreDefaultExcludes );
      }else if( string.startsWith( "{" ) ){
         json = JSONObject.fromString( string, excludes, ignoreDefaultExcludes );
      }else if( "null".equalsIgnoreCase( string ) ){
         json = JSONNull.getInstance();
      }else{
         throw new JSONException( "Invalid JSON String" );
      }

      return json;
   }

   /** Array conversion mode */
   private int arrayMode = MODE_LIST;
   /** Map of attribute/class */
   private Map classMap;
   /** Root class used when converting to an specific bean */
   private Class rootClass;

   /**
    * Default constructor
    */
   public JSONSerializer()
   {
   }

   /**
    * Constructs a new JSONSerializer with specific values for conversion.
    *
    * @param rootClass
    * @param classMap
    * @param arrayMode
    */
   public JSONSerializer( Class rootClass, Map classMap, int arrayMode )
   {
      super();
      this.rootClass = rootClass;
      this.classMap = classMap;
      this.arrayMode = arrayMode;
   }

   /**
    * Returns the current array mode conversion
    *
    * @return either MODE_OBJECT_ARRAY or MODE_LIST
    */
   public synchronized int getArrayMode()
   {
      return arrayMode;
   }

   /**
    * Returns the current attribute/class Map
    *
    * @return
    */
   public synchronized Map getClassMap()
   {
      return classMap;
   }

   /**
    * Returns the current root Class.
    *
    * @return
    */
   public synchronized Class getRootClass()
   {
      return rootClass;
   }

   /**
    * Resets this serializer to default values.<br>
    * rootClass = null<br>
    * classMap = null<br>
    * arrayMode = MODE_LIST<br>
    */
   public synchronized void reset()
   {
      arrayMode = MODE_LIST;
      rootClass = null;
      classMap = null;
   }

   /**
    * Sets the current array mode for conversion.<br>
    * If the value is not MODE_LIST neither MODE_OBJECT_ARRAY, then MODE_LIST
    * will be used.
    *
    * @param arrayMode
    */
   public synchronized void setArrayMode( int arrayMode )
   {
      if( arrayMode != MODE_LIST && arrayMode != MODE_OBJECT_ARRAY ){
         this.arrayMode = MODE_LIST;
      }else{
         this.arrayMode = arrayMode;
      }
   }

   /**
    * Sets the current attribute/Class Map
    *
    * @param classMap
    */
   public synchronized void setClassMap( Map classMap )
   {
      this.classMap = classMap;
   }

   /**
    * Sets the current root Class
    *
    * @param rootClass
    */
   public synchronized void setRootClass( Class rootClass )
   {
      this.rootClass = rootClass;
   }

   /**
    * Transform a JSON value to a java object.<br>
    * Depending on the configured values for conversion this will return a
    * DynaBean, a bean, a List, or and array.
    *
    * @param json
    * @return
    */
   public synchronized Object toJava( JSON json )
   {
      if( JSONUtils.isNull( json ) ){
         return null;
      }

      Object object = null;

      if( json instanceof JSONArray ){
         if( arrayMode == MODE_OBJECT_ARRAY ){
            object = JSONArray.toArray( (JSONArray) json, rootClass, classMap );
         }else{
            object = JSONArray.toList( (JSONArray) json, rootClass, classMap );
         }
      }else{
         object = JSONObject.toBean( (JSONObject) json, rootClass, classMap );
      }

      return object;
   }
}