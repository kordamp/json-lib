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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class JSONDynaClass implements DynaClass, Serializable
{
   private static final long serialVersionUID = 5573622587350265375L;
   protected Map attributes;
   protected DynaProperty dynaProperties[];
   protected transient Class jsonBeanClass;
   protected String name;
   protected Map properties = new HashMap();
   protected Class type;

   public JSONDynaClass( String name, Class type, Map attributes )
   {
      this.name = name;
      this.type = type;
      this.attributes = attributes;
      process();
   }

   public DynaProperty[] getDynaProperties()
   {
      return dynaProperties;
   }

   public DynaProperty getDynaProperty( String propertyName )
   {
      if( propertyName == null ){
         throw new IllegalArgumentException( "Unnespecified bean property name" );

      }
      return (DynaProperty) properties.get( propertyName );
   }

   public String getName()
   {
      return this.name;
   }

   public DynaBean newInstance() throws IllegalAccessException, InstantiationException
   {
      JSONDynaBean dynamicForm = (JSONDynaBean) getJsonBeanClass().newInstance();
      dynamicForm.setDynamicFormClass( this );
      Iterator keys = attributes.keySet()
            .iterator();
      while( keys.hasNext() ){
         String key = (String) keys.next();
         dynamicForm.set( key, null );
      }
      return dynamicForm;
   }

   public String toString()
   {
      return new ToStringBuilder( this ).append( "name", this.name )
            .append( "type", this.type )
            .append( "attributes", this.attributes )
            .toString();
   }

   protected Class getJsonBeanClass()
   {
      if( this.jsonBeanClass == null ){
         process();
      }
      return this.jsonBeanClass;
   }

   private void process()
   {
      this.jsonBeanClass = this.type;

      if( !JSONDynaBean.class.isAssignableFrom( this.jsonBeanClass ) ){
         throw new IllegalArgumentException( "Unnasignable dynaClass " + jsonBeanClass );
      }

      try{
         Iterator entries = attributes.entrySet()
               .iterator();
         dynaProperties = new DynaProperty[attributes.size()];
         int i = 0;
         while( entries.hasNext() ){
            Map.Entry entry = (Map.Entry) entries.next();
            String pname = (String) entry.getKey();
            Object pclass = entry.getValue();
            DynaProperty dynaProperty = null;
            if( pclass instanceof String ){
               dynaProperty = new DynaProperty( pname, Class.forName( (String) pclass ) );
            }else if( pclass instanceof Class){
               dynaProperty = new DynaProperty( pname, (Class)pclass );
            }else{
               throw new IllegalArgumentException("Type must be String or Class");
            }
            properties.put( dynaProperty.getName(), dynaProperty );
            dynaProperties[i++] = dynaProperty;
         }
      }
      catch( ClassNotFoundException cnfe ){
         throw new RuntimeException( cnfe );
      }
   }
}