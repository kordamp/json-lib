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

package net.sf.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.JavaIdentifierTransformer;
import net.sf.json.util.JsonEventListener;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class JsonConfig {
   private static MultiKeyMap beanTypeMap = new MultiKeyMap();
   private static final String[] DEFAULT_EXCLUDES = new String[] { "class", "declaringClass",
         "metaClass" };
   private static final JavaIdentifierTransformer DEFAULT_JAVA_IDENTIFIER_TRANSFORMER = JavaIdentifierTransformer.NOOP;
   private static final String[] EMPTY_EXCLUDES = new String[0];

   /*
    * private static ThreadLocal instance = new ThreadLocal(){ protected
    * synchronized Object initialValue() { return new JsonConfig(); } };
    */

   private static JsonConfig instance = new JsonConfig();
   private static final Log log = LogFactory.getLog( JsonConfig.class );

   public static JsonConfig getInstance() {
      // return (JsonConfig) instance.get();
      return instance;
   }

   private MultiKeyMap beanKeyMap = new MultiKeyMap();
   private List eventListeners = new ArrayList();
   private String[] excludes = EMPTY_EXCLUDES;
   private boolean ignoreDefaultExcludes;
   private boolean ignoreTransientFields;
   private JavaIdentifierTransformer javaIdentifierTransformer = DEFAULT_JAVA_IDENTIFIER_TRANSFORMER;
   private Map keyMap = new HashMap();
   private Map processorMap = new HashMap();
   private boolean skipJavaIdentifierTransformationInMapKeys;
   private boolean triggerEvents;
   private Map typeMap = new HashMap();

   private JsonConfig() {
   }

   public synchronized void addJsonEventListener( JsonEventListener listener ) {
      if( !eventListeners.contains( listener ) ){
         eventListeners.add( listener );
      }
   }

   public synchronized void clearEventListeners() {
      eventListeners.clear();
   }

   /**
    * Removes all registered JsonBeanProcessors.
    */
   public void clearJsonBeanProcessors() {
      processorMap.clear();
   }

   /**
    * Removes all registered JsonValueProcessors.
    */
   public void clearJsonValueProcessors() {
      beanKeyMap.clear();
      beanTypeMap.clear();
      keyMap.clear();
      typeMap.clear();
   }

   public void disableEventTriggering() {
      triggerEvents = false;
   }

   public void enableEventTriggering() {
      triggerEvents = true;
   }

   /**
    * Finds a JsonBeanProcessor registered to the target class.<br>
    * Returns null if none is registered. <br>
    * Used when tramsforming from Java to Json.
    */
   public JsonBeanProcessor findJsonBeanProcessor( Class target ) {
      if( target != null ){
         return (JsonBeanProcessor) processorMap.get( target );
      }else{
         return null;
      }
   }

   /**
    * Finds a JsonValueProcessor registered to the target type.<br>
    * Returns null if none is registered. <br>
    * Used when tramsforming from Java to Json.
    */
   public JsonValueProcessor findJsonValueProcessor( Class propertyType ) {
      JsonValueProcessor jsonValueProcessor = null;

      jsonValueProcessor = (JsonValueProcessor) typeMap.get( propertyType );
      if( jsonValueProcessor != null ){
         return jsonValueProcessor;
      }

      return null;
   }

   /**
    * Finds a JsonValueProcessor.<br>
    * It will search the registered JsonValueProcessors in the following order:
    * <ol>
    * <li>beanClass, key</li>
    * <li>beanClass, type</li>
    * <li>key</li>
    * <li>type</li>
    * </ol>
    * Returns null if none is registered. <br>
    * Used when tramsforming from Java to Json.
    */
   public JsonValueProcessor findJsonValueProcessor( Class beanClass, Class propertyType, String key ) {
      JsonValueProcessor jsonValueProcessor = null;
      jsonValueProcessor = (JsonValueProcessor) beanKeyMap.get( beanClass, key );
      if( jsonValueProcessor != null ){
         return jsonValueProcessor;
      }

      jsonValueProcessor = (JsonValueProcessor) beanTypeMap.get( beanClass, propertyType );
      if( jsonValueProcessor != null ){
         return jsonValueProcessor;
      }

      jsonValueProcessor = (JsonValueProcessor) keyMap.get( key );
      if( jsonValueProcessor != null ){
         return jsonValueProcessor;
      }

      jsonValueProcessor = (JsonValueProcessor) typeMap.get( propertyType );
      if( jsonValueProcessor != null ){
         return jsonValueProcessor;
      }

      return null;
   }

   /**
    * Finds a JsonValueProcessor.<br>
    * It will search the registered JsonValueProcessors in the following order:
    * <ol>
    * <li>key</li>
    * <li>type</li>
    * </ol>
    * Returns null if none is registered. <br>
    * Used when tramsforming from Java to Json.
    */
   public JsonValueProcessor findJsonValueProcessor( Class propertyType, String key ) {
      JsonValueProcessor jsonValueProcessor = null;
      jsonValueProcessor = (JsonValueProcessor) keyMap.get( key );
      if( jsonValueProcessor != null ){
         return jsonValueProcessor;
      }

      jsonValueProcessor = (JsonValueProcessor) typeMap.get( propertyType );
      if( jsonValueProcessor != null ){
         return jsonValueProcessor;
      }

      return null;
   }

   /**
    * Returns the configured properties for exclusion. <br>
    * Used when tramsforming from Java to Json.
    */
   public String[] getExcludes() {
      return excludes;
   }

   /**
    * Returns the configured JavaIdentifierTransformer. <br>
    * Used when tramsforming from Json to Java.
    */
   public JavaIdentifierTransformer getJavaIdentifierTransformer() {
      return javaIdentifierTransformer;
   }

   public Collection getMergedExcludes() {
      Collection exclusions = new HashSet();
      for( int i = 0; i < excludes.length; i++ ){
         String exclusion = excludes[i];
         if( !StringUtils.isBlank( excludes[i] ) ){
            exclusions.add( exclusion.trim() );
         }
      }

      if( !ignoreDefaultExcludes ){
         for( int i = 0; i < DEFAULT_EXCLUDES.length; i++ ){
            if( !exclusions.contains( DEFAULT_EXCLUDES[i] ) ){
               exclusions.add( DEFAULT_EXCLUDES[i] );
            }
         }
      }

      return exclusions;
   }

   public boolean isEventTriggeringEnabled() {
      return triggerEvents;
   }

   public boolean isIgnoreDefaultExcludes() {
      return ignoreDefaultExcludes;
   }

   public boolean isIgnoreTransientFields() {
      return ignoreTransientFields;
   }

   public boolean isSkipJavaIdentifierTransformationInMapKeys() {
      return skipJavaIdentifierTransformationInMapKeys;
   }

   public void registerJsonBeanProcessor( Class target, JsonBeanProcessor jsonBeanProcessor ) {
      if( target != null && jsonBeanProcessor != null ){
         processorMap.put( target, jsonBeanProcessor );
      }
   }

   public void registerJsonValueProcessor( Class beanClass, Class propertyType,
         JsonValueProcessor jsonValueProcessor ) {
      if( beanClass != null && propertyType != null && jsonValueProcessor != null ){
         beanTypeMap.put( beanClass, propertyType, jsonValueProcessor );
      }
   }

   public void registerJsonValueProcessor( Class propertyType, JsonValueProcessor jsonValueProcessor ) {
      if( propertyType != null && jsonValueProcessor != null ){
         typeMap.put( propertyType, jsonValueProcessor );
      }
   }

   public void registerJsonValueProcessor( Class beanClass, String key,
         JsonValueProcessor jsonValueProcessor ) {
      if( beanClass != null && key != null && jsonValueProcessor != null ){
         beanKeyMap.put( beanClass, key, jsonValueProcessor );
      }
   }

   public void registerJsonValueProcessor( String key, JsonValueProcessor jsonValueProcessor ) {
      if( key != null && jsonValueProcessor != null ){
         keyMap.put( key, jsonValueProcessor );
      }
   }

   public synchronized void removeJsonEventListener( JsonEventListener listener ) {
      eventListeners.remove( listener );
   }

   public void reset() {
      excludes = EMPTY_EXCLUDES;
      ignoreDefaultExcludes = false;
      ignoreTransientFields = false;
      javaIdentifierTransformer = DEFAULT_JAVA_IDENTIFIER_TRANSFORMER;
      skipJavaIdentifierTransformationInMapKeys = false;
      triggerEvents = false;
   }

   public void setExcludes( String[] excludes ) {
      this.excludes = excludes == null ? EMPTY_EXCLUDES : excludes;
   }

   public void setIgnoreDefaultExcludes( boolean ignoreDefaultExcludes ) {
      this.ignoreDefaultExcludes = ignoreDefaultExcludes;
   }

   public void setIgnoreTransientFields( boolean ignoreTransientFields ) {
      this.ignoreTransientFields = ignoreTransientFields;
   }

   public void setJavaIdentifierTransformer( JavaIdentifierTransformer javaIdentifierTransformer ) {
      this.javaIdentifierTransformer = javaIdentifierTransformer == null ? DEFAULT_JAVA_IDENTIFIER_TRANSFORMER
            : javaIdentifierTransformer;
   }

   public void setSkipJavaIdentifierTransformationInMapKeys(
         boolean skipJavaIdentifierTransformationInMapKeys ) {
      this.skipJavaIdentifierTransformationInMapKeys = skipJavaIdentifierTransformationInMapKeys;
   }

   public void unregisterJsonBeanProcessor( Class target ) {
      if( target != null ){
         processorMap.remove( target );
      }
   }

   public void unregisterJsonValueProcessor( Class beanClass, Class propertyType,
         JsonValueProcessor jsonValueProcessor ) {
      if( beanClass != null && propertyType != null ){
         beanTypeMap.remove( beanClass, propertyType );
      }
   }

   public void unregisterJsonValueProcessor( Class propertyType,
         JsonValueProcessor jsonValueProcessor ) {
      if( propertyType != null ){
         typeMap.remove( propertyType );
      }
   }

   public void unregisterJsonValueProcessor( Class beanClass, String key,
         JsonValueProcessor jsonValueProcessor ) {
      if( beanClass != null && key != null ){
         beanKeyMap.remove( beanClass, key );
      }
   }

   public void unregisterJsonValueProcessor( String key, JsonValueProcessor jsonValueProcessor ) {
      if( key != null ){
         keyMap.remove( key );
      }
   }

   void fireArrayEndEvent() {
      if( triggerEvents ){
         for( Iterator listeners = eventListeners.iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onArrayEnd();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   void fireArrayStartEvent() {
      if( triggerEvents ){
         for( Iterator listeners = eventListeners.iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onArrayStart();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   void fireElementAddedEvent( int index, Object element ) {
      if( triggerEvents ){
         for( Iterator listeners = eventListeners.iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onElementAdded( index, element );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   void fireErrorEvent( JSONException jsone ) {
      if( triggerEvents ){
         for( Iterator listeners = eventListeners.iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onError( jsone );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   void fireObjectEndEvent() {
      if( triggerEvents ){
         for( Iterator listeners = eventListeners.iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onObjectEnd();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   void fireObjectStartEvent() {
      if( triggerEvents ){
         for( Iterator listeners = eventListeners.iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onObjectStart();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   void firePropertySetEvent( String key, Object value, boolean accumulated ) {
      if( triggerEvents ){
         for( Iterator listeners = eventListeners.iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onPropertySet( key, value, accumulated );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   void fireWarnEvent( String warning ) {
      if( triggerEvents ){
         for( Iterator listeners = eventListeners.iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onWarning( warning );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }
}