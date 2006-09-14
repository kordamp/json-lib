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

package net.sf.json.util;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaClass;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class JSONDynaClass implements DynaClass, Serializable
{
   private static final Comparator DynaPropertyComparator = new Comparator(){
      public int compare( Object a, Object b )
      {
         if( a instanceof DynaProperty && b instanceof DynaProperty ){
            DynaProperty p1 = (DynaProperty) a;
            DynaProperty p2 = (DynaProperty) b;
            return p1.getName()
                  .compareTo( p2.getName() );
         }
         return -1;
      }
   };

   private static final long serialVersionUID = 3856810283982201386L;

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

   public boolean equals( Object obj )
   {
      if( this == obj ){
         return true;
      }

      if( obj == null ){
         return false;
      }

      if( !(obj instanceof JSONDynaClass) ){
         return false;
      }

      JSONDynaClass other = (JSONDynaClass) obj;
      EqualsBuilder builder = new EqualsBuilder().append( this.name, other.name )
            .append( this.type, other.type );
      for( int i = 0; i < dynaProperties.length; i++ ){
         DynaProperty a = this.dynaProperties[i];
         DynaProperty b = other.dynaProperties[i];
         builder.append( a.getName(), b.getName() );
         builder.append( a.getType(), b.getType() );
      }
      return builder.isEquals();
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

   public int hashCode()
   {
      HashCodeBuilder builder = new HashCodeBuilder().append( name )
            .append( type );
      for( int i = 0; i < dynaProperties.length; i++ ){
         builder.append( this.dynaProperties[i].getName() );
         builder.append( this.dynaProperties[i].getType() );
      }
      return builder.toHashCode();
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
            }else if( pclass instanceof Class ){
               dynaProperty = new DynaProperty( pname, (Class) pclass );
            }else{
               throw new IllegalArgumentException( "Type must be String or Class" );
            }
            properties.put( dynaProperty.getName(), dynaProperty );
            dynaProperties[i++] = dynaProperty;
         }
      }
      catch( ClassNotFoundException cnfe ){
         throw new RuntimeException( cnfe );
      }

      // keep properties sorted by name
      Arrays.sort( dynaProperties, 0, dynaProperties.length, DynaPropertyComparator );
   }
}