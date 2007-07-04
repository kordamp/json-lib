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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.sf.json.util.JsonEventListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
abstract class AbstractJSON {
   private static Set cycleSet = new HashSet();

   private static final Log log = LogFactory.getLog( AbstractJSON.class );

   protected static boolean addInstance( Object instance ) {
      return cycleSet.add( instance );
   }

   protected static void fireArrayEndEvent() {
      JsonConfig jsonConfig = JsonConfig.getInstance();
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onArrayEnd();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   protected static void fireArrayStartEvent() {
      JsonConfig jsonConfig = JsonConfig.getInstance();
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onArrayStart();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   protected static void fireElementAddedEvent( int index, Object element ) {
      JsonConfig jsonConfig = JsonConfig.getInstance();
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onElementAdded( index, element );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   protected static void fireErrorEvent( JSONException jsone ) {
      JsonConfig jsonConfig = JsonConfig.getInstance();
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onError( jsone );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   protected static void fireObjectEndEvent() {
      JsonConfig jsonConfig = JsonConfig.getInstance();
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onObjectEnd();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   protected static void fireObjectStartEvent() {
      JsonConfig jsonConfig = JsonConfig.getInstance();
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onObjectStart();
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   protected static void firePropertySetEvent( String key, Object value, boolean accumulated ) {
      JsonConfig jsonConfig = JsonConfig.getInstance();
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onPropertySet( key, value, accumulated );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   protected static void fireWarnEvent( String warning ) {
      JsonConfig jsonConfig = JsonConfig.getInstance();
      if( jsonConfig.isEventTriggeringEnabled() ){
         for( Iterator listeners = jsonConfig.getJsonEventListeners()
               .iterator(); listeners.hasNext(); ){
            JsonEventListener listener = (JsonEventListener) listeners.next();
            try{
               listener.onWarning( warning );
            }catch( RuntimeException e ){
               log.warn( e );
            }
         }
      }
   }

   protected static void removeInstance( Object instance ) {
      cycleSet.remove( instance );
   }
}