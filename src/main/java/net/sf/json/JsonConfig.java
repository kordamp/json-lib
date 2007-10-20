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
import java.util.List;
import java.util.Map;

import net.sf.json.processors.DefaultDefaultValueProcessor;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.processors.DefaultValueProcessorMatcher;
import net.sf.json.processors.JsonBeanProcessor;
import net.sf.json.processors.JsonBeanProcessorMatcher;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JavaIdentifierTransformer;
import net.sf.json.util.JsonEventListener;
import net.sf.json.util.NewBeanInstanceStrategy;
import net.sf.json.util.PropertyFilter;
import net.sf.json.util.PropertySetStrategy;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;

/**
 * Utility class that helps configuring the serialization process.
 *
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class JsonConfig {
   public static final DefaultValueProcessorMatcher DEFAULT_DEFAULT_VALUE_PROCESSOR_MATCHER = DefaultValueProcessorMatcher.DEFAULT;
   public static final JsonBeanProcessorMatcher DEFAULT_JSON_BEAN_PROCESSOR_MATCHER = JsonBeanProcessorMatcher.DEFAULT;
   public static final NewBeanInstanceStrategy DEFAULT_NEW_BEAN_INSTANCE_STRATEGY = NewBeanInstanceStrategy.DEFAULT;
   public static final int MODE_LIST = 1;
   public static final int MODE_OBJECT_ARRAY = 2;
   private static final CycleDetectionStrategy DEFAULT_CYCLE_DETECTION_STRATEGY = CycleDetectionStrategy.STRICT;
   private static final String[] DEFAULT_EXCLUDES = new String[] { "class", "declaringClass",
         "metaClass" };
   private static final JavaIdentifierTransformer DEFAULT_JAVA_IDENTIFIER_TRANSFORMER = JavaIdentifierTransformer.NOOP;
   private static final PropertySetStrategy DEFAULT_PROPERTY_SET_STRATEGY = PropertySetStrategy.DEFAULT;
   private static final DefaultValueProcessor DEFAULT_VALUE_PROCESSOR = new DefaultDefaultValueProcessor();
   private static final String[] EMPTY_EXCLUDES = new String[0];

   /** Array conversion mode */
   private int arrayMode = MODE_LIST;
   private MultiKeyMap beanKeyMap = new MultiKeyMap();
   private MultiKeyMap beanTypeMap = new MultiKeyMap();
   /** Map of attribute/class */
   private Map classMap;
   private CycleDetectionStrategy cycleDetectionStrategy = DEFAULT_CYCLE_DETECTION_STRATEGY;
   private Map defaultValueMap = new HashMap();
   private DefaultValueProcessorMatcher defaultValueProcessorMatcher = DEFAULT_DEFAULT_VALUE_PROCESSOR_MATCHER;
   private List eventListeners = new ArrayList();
   private String[] excludes = EMPTY_EXCLUDES;
   private boolean handleJettisonEmptyElement;
   private boolean handleJettisonSingleElementArray;
   private boolean ignoreDefaultExcludes;
   private boolean ignoreTransientFields;
   private JavaIdentifierTransformer javaIdentifierTransformer = DEFAULT_JAVA_IDENTIFIER_TRANSFORMER;
   private PropertyFilter javaPropertyFilter;
   private JsonBeanProcessorMatcher jsonBeanProcessorMatcher = DEFAULT_JSON_BEAN_PROCESSOR_MATCHER;
   private PropertyFilter jsonPropertyFilter;
   private Map keyMap = new HashMap();
   private NewBeanInstanceStrategy newBeanInstanceStrategy = DEFAULT_NEW_BEAN_INSTANCE_STRATEGY;
   private Map processorMap = new HashMap();
   private PropertySetStrategy propertySetStrategy = DEFAULT_PROPERTY_SET_STRATEGY;
   /** Root class used when converting to an specific bean */
   private Class rootClass;
   private boolean skipJavaIdentifierTransformationInMapKeys;
   private boolean triggerEvents;
   private Map typeMap = new HashMap();

   public JsonConfig() {
   }

   /**
    * Registers a listener for Json events.<br>
    * The events will be triggered only when using the static builders and if
    * event triggering is enabled.
    *
    * @see #enableEventTriggering
    * @see #disableEventTriggering
    * @see #removeJsonEventListener(JsonEventListener)
    * @param listener a listener for events
    */
   public synchronized void addJsonEventListener( JsonEventListener listener ) {
      if( !eventListeners.contains( listener ) ){
         eventListeners.add( listener );
      }
   }

   /**
    * Removes all registered JsonBeanProcessors.
    */
   public void clearJsonBeanProcessors() {
      processorMap.clear();
   }

   /**
    * Removes all registered listener for Json Events.
    */
   public synchronized void clearJsonEventListeners() {
      eventListeners.clear();
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

   public JsonConfig copy() {
      JsonConfig jsc = new JsonConfig();
      jsc.beanKeyMap.putAll( beanKeyMap );
      jsc.beanTypeMap.putAll( beanTypeMap );
      jsc.classMap = new HashMap();
      if( classMap != null ){
         jsc.classMap.putAll( classMap );
      }
      jsc.cycleDetectionStrategy = cycleDetectionStrategy;
      if( eventListeners != null ){
         jsc.eventListeners.addAll( eventListeners );
      }
      if( excludes != null ){
         jsc.excludes = new String[excludes.length];
         System.arraycopy( excludes, 0, jsc.excludes, 0, excludes.length );
      }
      jsc.handleJettisonEmptyElement = handleJettisonEmptyElement;
      jsc.handleJettisonSingleElementArray = handleJettisonSingleElementArray;
      jsc.ignoreDefaultExcludes = ignoreDefaultExcludes;
      jsc.ignoreTransientFields = ignoreTransientFields;
      jsc.javaIdentifierTransformer = javaIdentifierTransformer;
      jsc.keyMap.putAll( keyMap );
      jsc.processorMap.putAll( processorMap );
      jsc.rootClass = rootClass;
      jsc.skipJavaIdentifierTransformationInMapKeys = skipJavaIdentifierTransformationInMapKeys;
      jsc.triggerEvents = triggerEvents;
      jsc.typeMap.putAll( typeMap );
      jsc.jsonPropertyFilter = jsonPropertyFilter;
      jsc.javaPropertyFilter = javaPropertyFilter;
      jsc.jsonBeanProcessorMatcher = jsonBeanProcessorMatcher;
      jsc.newBeanInstanceStrategy = newBeanInstanceStrategy;
      jsc.defaultValueProcessorMatcher = defaultValueProcessorMatcher;
      jsc.defaultValueMap.putAll( defaultValueMap );
      jsc.propertySetStrategy = propertySetStrategy;
      return jsc;
   }

   /**
    * Disables event triggering when building.
    */
   public void disableEventTriggering() {
      triggerEvents = false;
   }

   /**
    * Enables event triggering when building.
    */
   public void enableEventTriggering() {
      triggerEvents = true;
   }

   /**
    * Finds a DefaultValueProcessor registered to the target class.<br>
    * Returns null if none is registered. <br>
    * Used when transforming from Java to Json.
    *
    * @param target a class used for searching a DefaultValueProcessor.
    */
   public DefaultValueProcessor findDefaultValueProcessor( Class target ) {
      Object key = defaultValueProcessorMatcher.getMatch( target, defaultValueMap.keySet() );
      DefaultValueProcessor processor = (DefaultValueProcessor) defaultValueMap.get( key );
      if( processor != null ){
         return processor;
      }
      return DEFAULT_VALUE_PROCESSOR;
   }

   /**
    * Finds a JsonBeanProcessor registered to the target class.<br>
    * Returns null if none is registered. <br>
    * Used when transforming from Java to Json.
    *
    * @param target a class used for searching a JsonBeanProcessor.
    */
   public JsonBeanProcessor findJsonBeanProcessor( Class target ) {
      Object key = jsonBeanProcessorMatcher.getMatch( target, processorMap.keySet() );
      return (JsonBeanProcessor) processorMap.get( key );
   }

   /**
    * Finds a JsonValueProcessor registered to the target type.<br>
    * Returns null if none is registered. <br>
    * Used when tramsforming from Java to Json.
    *
    * @param propertyType a class used for searching a JsonValueProcessor.
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
    *
    * @param beanClass the class to which the property may belong
    * @param propertyType the type of the property
    * @param key the name of the property which may belong to the target class
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
    *
    * @param propertyType the type of the property
    * @param key the name of the property which may belong to the target class
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
    * Returns the current array mode conversion
    *
    * @return either MODE_OBJECT_ARRAY or MODE_LIST
    */
   public int getArrayMode() {
      return arrayMode;
   }

   /**
    * Returns the current attribute/class Map
    *
    * @return a Map of classes, every key identifies a property or a regexp
    */
   public Map getClassMap() {
      return classMap;
   }

   /**
    * Returns the configured CycleDetectionStrategy.<br>
    * Default value is CycleDetectionStrategy.STRICT
    */
   public CycleDetectionStrategy getCycleDetectionStrategy() {
      return cycleDetectionStrategy;
   }

   /**
    * Returns the configured DefaultValueProcessorMatcher.<br>
    * Default value is DefaultValueProcessorMatcher.DEFAULT
    */
   public DefaultValueProcessorMatcher getDefaultValueProcessorMatcher() {
      return defaultValueProcessorMatcher;
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
    * Used when transforming from Json to Java.<br>
    * Default value is JavaIdentifierTransformer.NOOP
    */
   public JavaIdentifierTransformer getJavaIdentifierTransformer() {
      return javaIdentifierTransformer;
   }

   /**
    * Returns the configured property filter when serializing to Java.
    */
   public PropertyFilter getJavaPropertyFilter() {
      return javaPropertyFilter;
   }

   /**
    * Returns the configured JsonBeanProcessorMatcher.<br>
    * Default value is JsonBeanProcessorMatcher.DEFAULT
    */
   public JsonBeanProcessorMatcher getJsonBeanProcessorMatcher() {
      return jsonBeanProcessorMatcher;
   }

   /**
    * Returns a list of registered listeners for Json events.
    */
   public synchronized List getJsonEventListeners() {
      return eventListeners;
   }

   /**
    * Returns the configured property filter when serializing to JSON.
    */
   public PropertyFilter getJsonPropertyFilter() {
      return jsonPropertyFilter;
   }

   /**
    * Returns a set of default excludes with user-defined excludes.
    */
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

   /**
    * Returns the configured NewBeanInstanceStrategy.<br>
    * Default value is NewBeanInstanceStrategy.DEFAULT
    */
   public NewBeanInstanceStrategy getNewBeanInstanceStrategy() {
      return newBeanInstanceStrategy;
   }

   /**
    * Returns the configured PropertySetStrategy.<br>
    * Default value is PropertySetStrategy.DEFAULT
    */
   public PropertySetStrategy getPropertySetStrategy() {
      return propertySetStrategy;
   }

   /**
    * Returns the current root Class.
    *
    * @return the target class for conversion
    */
   public Class getRootClass() {
      return rootClass;
   }

   /**
    * Returns true if event triggering is enabled during building.<br>
    * Default value is false
    */
   public boolean isEventTriggeringEnabled() {
      return triggerEvents;
   }

   /**
    * Returns true if this jettison convention will be handled when converting
    * to Java.<br>
    * Jettison assumes that "" (empty string) can be assigned to empty elements
    * (objects), which clearly violates the JSON spec.
    */
   public boolean isHandleJettisonEmptyElement() {
      return handleJettisonEmptyElement;
   }

   /**
    * Returns true if this jettison convention will be handled when converting
    * to Java.<br>
    * Jettison states the following JSON {'media':{'title':'hello'}} can be set
    * as a single element JSONArray (media is the array).
    */
   public boolean isHandleJettisonSingleElementArray() {
      return handleJettisonSingleElementArray;
   }

   /**
    * Returns true if default excludes will not be used.<br>
    * Default value is false
    */
   public boolean isIgnoreDefaultExcludes() {
      return ignoreDefaultExcludes;
   }

   /**
    * Returns true if transient fields of a bean will be ignored.<br>
    * Default value is false
    */
   public boolean isIgnoreTransientFields() {
      return ignoreTransientFields;
   }

   /**
    * Returns true if map keys will not be transformed.<br>
    * Default value is false
    */
   public boolean isSkipJavaIdentifierTransformationInMapKeys() {
      return skipJavaIdentifierTransformationInMapKeys;
   }

   /**
    * Registers a DefaultValueProcessor.<br>
    *
    * @param target the class to use as key
    * @param defaultValueProcessor the processor to register
    */
   public void registerDefaultValueProcessor( Class target,
         DefaultValueProcessor defaultValueProcessor ) {
      if( target != null && defaultValueProcessor != null ){
         defaultValueMap.put( target, defaultValueProcessor );
      }
   }

   /**
    * Registers a JsonBeanProcessor.<br>
    *
    * @param target the class to use as key
    * @param jsonBeanProcessor the processor to register
    */
   public void registerJsonBeanProcessor( Class target, JsonBeanProcessor jsonBeanProcessor ) {
      if( target != null && jsonBeanProcessor != null ){
         processorMap.put( target, jsonBeanProcessor );
      }
   }

   /**
    * Registers a JsonValueProcessor.<br>
    *
    * @param beanClass the class to use as key
    * @param propertyType the property type to use as key
    * @param jsonValueProcessor the processor to register
    */
   public void registerJsonValueProcessor( Class beanClass, Class propertyType,
         JsonValueProcessor jsonValueProcessor ) {
      if( beanClass != null && propertyType != null && jsonValueProcessor != null ){
         beanTypeMap.put( beanClass, propertyType, jsonValueProcessor );
      }
   }

   /**
    * Registers a JsonValueProcessor.<br>
    *
    * @param propertyType the property type to use as key
    * @param jsonValueProcessor the processor to register
    */
   public void registerJsonValueProcessor( Class propertyType, JsonValueProcessor jsonValueProcessor ) {
      if( propertyType != null && jsonValueProcessor != null ){
         typeMap.put( propertyType, jsonValueProcessor );
      }
   }

   /**
    * Registers a JsonValueProcessor.<br>
    *
    * @param beanClass the class to use as key
    * @param key the property name to use as key
    * @param jsonValueProcessor the processor to register
    */
   public void registerJsonValueProcessor( Class beanClass, String key,
         JsonValueProcessor jsonValueProcessor ) {
      if( beanClass != null && key != null && jsonValueProcessor != null ){
         beanKeyMap.put( beanClass, key, jsonValueProcessor );
      }
   }

   /**
    * Registers a JsonValueProcessor.<br>
    *
    * @param key the property name to use as key
    * @param jsonValueProcessor the processor to register
    */
   public void registerJsonValueProcessor( String key, JsonValueProcessor jsonValueProcessor ) {
      if( key != null && jsonValueProcessor != null ){
         keyMap.put( key, jsonValueProcessor );
      }
   }

   /**
    * Removes a listener for Json events.<br>
    *
    * @see #addJsonEventListener(JsonEventListener)
    * @param listener a listener for events
    */
   public synchronized void removeJsonEventListener( JsonEventListener listener ) {
      eventListeners.remove( listener );
   }

   /**
    * Resets all values to its default state.
    */
   public void reset() {
      excludes = EMPTY_EXCLUDES;
      ignoreDefaultExcludes = false;
      ignoreTransientFields = false;
      javaIdentifierTransformer = DEFAULT_JAVA_IDENTIFIER_TRANSFORMER;
      cycleDetectionStrategy = DEFAULT_CYCLE_DETECTION_STRATEGY;
      skipJavaIdentifierTransformationInMapKeys = false;
      triggerEvents = false;
      handleJettisonEmptyElement = false;
      handleJettisonSingleElementArray = false;
      arrayMode = MODE_LIST;
      rootClass = null;
      classMap = null;
      keyMap.clear();
      typeMap.clear();
      beanKeyMap.clear();
      beanTypeMap.clear();
      jsonPropertyFilter = null;
      javaPropertyFilter = null;
      jsonBeanProcessorMatcher = DEFAULT_JSON_BEAN_PROCESSOR_MATCHER;
      newBeanInstanceStrategy = DEFAULT_NEW_BEAN_INSTANCE_STRATEGY;
      defaultValueProcessorMatcher = DEFAULT_DEFAULT_VALUE_PROCESSOR_MATCHER;
      defaultValueMap.clear();
      propertySetStrategy = DEFAULT_PROPERTY_SET_STRATEGY;
   }

   /**
    * Sets the current array mode for conversion.<br>
    * If the value is not MODE_LIST neither MODE_OBJECT_ARRAY, then MODE_LIST
    * will be used.
    *
    * @param arrayMode array mode for conversion
    */
   public void setArrayMode( int arrayMode ) {
      if( arrayMode != MODE_LIST && arrayMode != MODE_OBJECT_ARRAY ){
         this.arrayMode = MODE_LIST;
      }else{
         this.arrayMode = arrayMode;
      }
   }

   /**
    * Sets the current attribute/Class Map
    *
    * @param classMap a Map of classes, every key identifies a property or a
    *        regexp
    */
   public void setClassMap( Map classMap ) {
      this.classMap = classMap;
   }

   /**
    * Sets a CycleDetectionStrategy to use.<br>
    * Will set default value (CycleDetectionStrategy.STRICT) if null.
    */
   public void setCycleDetectionStrategy( CycleDetectionStrategy cycleDetectionStrategy ) {
      this.cycleDetectionStrategy = cycleDetectionStrategy == null ? DEFAULT_CYCLE_DETECTION_STRATEGY
            : cycleDetectionStrategy;
   }

   /**
    * Sets a DefaultValueProcessorMatcher to use.<br>
    * Will set default value (DefaultValueProcessorMatcher.DEFAULT) if null.
    */
   public void setDefaultValueProcessorMatcher(
         DefaultValueProcessorMatcher defaultValueProcessorMatcher ) {
      this.defaultValueProcessorMatcher = defaultValueProcessorMatcher == null ? DEFAULT_DEFAULT_VALUE_PROCESSOR_MATCHER
            : defaultValueProcessorMatcher;
   }

   /**
    * Sets the excludes to use.<br>
    * Will set default value ([]) if null.
    */
   public void setExcludes( String[] excludes ) {
      this.excludes = excludes == null ? EMPTY_EXCLUDES : excludes;
   }

   /**
    * Activate/Deactive handling this jettison convention when converting to
    * Java.<br>
    * Jettison states that "" (empty string) can be assigned to empty elements
    * (objects), which clearly violates the JSON spec.
    */
   public void setHandleJettisonEmptyElement( boolean handleJettisonEmptyElement ) {
      this.handleJettisonEmptyElement = handleJettisonEmptyElement;
   }

   /**
    * Activate/Deactive handling this jettison convention when converting to
    * Java.<br> * Jettison states the following JSON
    * {'media':{'title':'hello'}} can be set as a single element JSONArray
    * (media is the array).
    */
   public void setHandleJettisonSingleElementArray( boolean handleJettisonSingleElementArray ) {
      this.handleJettisonSingleElementArray = handleJettisonSingleElementArray;
   }

   /**
    * Sets if default ecludes would be skipped when building.<br>
    */
   public void setIgnoreDefaultExcludes( boolean ignoreDefaultExcludes ) {
      this.ignoreDefaultExcludes = ignoreDefaultExcludes;
   }

   /**
    * Sets if transient fields would be skipped when building.<br>
    */
   public void setIgnoreTransientFields( boolean ignoreTransientFields ) {
      this.ignoreTransientFields = ignoreTransientFields;
   }

   /**
    * Sets the JavaIdentifierTransformer to use.<br>
    * Will set default value (JavaIdentifierTransformer.NOOP) if null.
    */
   public void setJavaIdentifierTransformer( JavaIdentifierTransformer javaIdentifierTransformer ) {
      this.javaIdentifierTransformer = javaIdentifierTransformer == null ? DEFAULT_JAVA_IDENTIFIER_TRANSFORMER
            : javaIdentifierTransformer;
   }

   /**
    * Sets a property filter used when serializing to Java.
    *
    * @param javaPropertyFilter the property filter
    */
   public void setJavaPropertyFilter( PropertyFilter javaPropertyFilter ) {
      this.javaPropertyFilter = javaPropertyFilter;
   }

   /**
    * Sets a JsonBeanProcessorMatcher to use.<br>
    * Will set default value (JsonBeanProcessorMatcher.DEFAULT) if null.
    */
   public void setJsonBeanProcessorMatcher( JsonBeanProcessorMatcher jsonBeanProcessorMatcher ) {
      this.jsonBeanProcessorMatcher = jsonBeanProcessorMatcher == null ? DEFAULT_JSON_BEAN_PROCESSOR_MATCHER
            : jsonBeanProcessorMatcher;
   }

   /**
    * Sets a property filter used when serializing to JSON.
    *
    * @param jsonPropertyFilter the property filter
    */
   public void setJsonPropertyFilter( PropertyFilter jsonPropertyFilter ) {
      this.jsonPropertyFilter = jsonPropertyFilter;
   }

   /**
    * Sets the NewBeanInstanceStrategy to use.<br>
    * Will set default value (NewBeanInstanceStrategy.DEFAULT) if null.
    */
   public void setNewBeanInstanceStrategy( NewBeanInstanceStrategy newBeanInstanceStrategy ) {
      this.newBeanInstanceStrategy = newBeanInstanceStrategy == null ? DEFAULT_NEW_BEAN_INSTANCE_STRATEGY
            : newBeanInstanceStrategy;
   }

   /**
    * Sets a PropertySetStrategy to use.<br>
    * Will set default value (PropertySetStrategy.DEFAULT) if null.
    */
   public void setPropertySetStrategy( PropertySetStrategy propertySetStrategy ) {
      this.propertySetStrategy = propertySetStrategy == null ? DEFAULT_PROPERTY_SET_STRATEGY
            : propertySetStrategy;
   }

   /**
    * Sets the current root Class
    *
    * @param rootClass the target class for conversion
    */
   public void setRootClass( Class rootClass ) {
      this.rootClass = rootClass;
   }

   /**
    * Sets if transient fields of beans would be skipped when building.<br>
    */
   public void setSkipJavaIdentifierTransformationInMapKeys(
         boolean skipJavaIdentifierTransformationInMapKeys ) {
      this.skipJavaIdentifierTransformationInMapKeys = skipJavaIdentifierTransformationInMapKeys;
   }

   /**
    * Removes a DefaultValueProcessor.
    *
    * @param target a class used for searching a DefaultValueProcessor.
    */
   public void unregisterDefaultValueProcessor( Class target ) {
      if( target != null ){
         defaultValueMap.remove( target );
      }
   }

   /**
    * Removes a JsonBeanProcessor.
    *
    * @param target a class used for searching a JsonBeanProcessor.
    */
   public void unregisterJsonBeanProcessor( Class target ) {
      if( target != null ){
         processorMap.remove( target );
      }
   }

   /**
    * Removes a JsonValueProcessor.
    *
    * @param propertyType a class used for searching a JsonValueProcessor.
    */
   public void unregisterJsonValueProcessor( Class propertyType ) {
      if( propertyType != null ){
         typeMap.remove( propertyType );
      }
   }

   /**
    * Removes a JsonValueProcessor.
    *
    * @param beanClass the class to which the property may belong
    * @param propertyType the type of the property
    */
   public void unregisterJsonValueProcessor( Class beanClass, Class propertyType ) {
      if( beanClass != null && propertyType != null ){
         beanTypeMap.remove( beanClass, propertyType );
      }
   }

   /**
    * Removes a JsonValueProcessor.
    *
    * @param beanClass the class to which the property may belong
    * @param key the name of the property which may belong to the target class
    */
   public void unregisterJsonValueProcessor( Class beanClass, String key ) {
      if( beanClass != null && key != null ){
         beanKeyMap.remove( beanClass, key );
      }
   }

   /**
    * Removes a JsonValueProcessor.
    *
    * @param key the name of the property which may belong to the target class
    */
   public void unregisterJsonValueProcessor( String key ) {
      if( key != null ){
         keyMap.remove( key );
      }
   }
}