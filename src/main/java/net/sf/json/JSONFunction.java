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

import net.sf.json.util.JSONTokener;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * JSONFunction represents a javaScript function's text.
 * 
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 * @version 3
 */
public class JSONFunction implements Serializable
{
   /** constant array for empty parameters */
   private static final String[] EMPTY_PARAM_ARRAY = new String[0];

   private static final long serialVersionUID = 5550817154436696969L;

   /**
    * Constructs a JSONFunction from a text representation
    */
   public static JSONFunction parse( JSONTokener x )
   {
      Object v = x.nextValue();
      if( !JSONUtils.isFunctionHeader( v ) ){
         throw new JSONException( "String is not a function. " + v );
      }else{
         // read params if any
         String params = JSONUtils.getFunctionParams( (String) v );
         // read function text
         int i = 0;
         StringBuffer sb = new StringBuffer();
         for( ;; ){
            char ch = x.next();
            if( ch == 0 ){
               break;
            }
            if( ch == '{' ){
               i++;
            }
            if( ch == '}' ){
               i--;
            }
            sb.append( ch );
            if( i == 0 ){
               break;
            }
         }
         if( i != 0 ){
            throw x.syntaxError( "Unbalanced '{' or '}' on prop: " + v );
         }
         // trim '{' at start and '}' at end
         String text = sb.toString();
         text = text.substring( 1, text.length() - 1 )
               .trim();
         return new JSONFunction( (params != null) ? params.split( "," ) : null, text );
      }
   }

   /**
    * Constructs a JSONFunction from a text representation
    */
   public static JSONFunction parse( String str )
   {
      return parse( new JSONTokener( str ) );
   }

   /** the parameters of this function */
   private String[] params;

   /** the text of this function */
   private String text;

   /**
    * Constructs a JSONFunction with no parameters.
    * 
    * @param text The text of the function
    */
   public JSONFunction( String text )
   {
      this( null, text );
   }

   /**
    * Constructs a JSONFunction with parameters.
    * 
    * @param params The parameters of the function
    * @param text The text of the function
    */
   public JSONFunction( String[] params, String text )
   {
      this.text = (text != null) ? text.trim() : "";
      if( params != null ){
         if( params.length == 1 && params[0].trim()
               .equals( "" ) ){
            this.params = EMPTY_PARAM_ARRAY;
         }else{
            this.params = new String[params.length];
            System.arraycopy( params, 0, this.params, 0, params.length );
            // remove empty spaces
            for( int i = 0; i < params.length; i++ ){
               this.params[i] = this.params[i].trim();
            }
         }
      }else{
         this.params = EMPTY_PARAM_ARRAY;
      }
   }

   public boolean equals( Object obj )
   {
      if( this == obj ){
         return true;
      }
      if( obj == null ){
         return false;
      }
      if( !(obj instanceof JSONFunction) ){
         return false;
      }

      // if( obj instanceof String ){
      // return toString().compareTo( (String) obj ) == 0;
      // }

      JSONFunction other = (JSONFunction) obj;
      if( params.length != other.params.length ){
         return false;
      }
      EqualsBuilder builder = new EqualsBuilder();
      for( int i = 0; i < params.length; i++ ){
         builder.append( params[i], other.params[i] );
      }
      builder.append( text, other.text );
      return builder.isEquals();
   }

   /**
    * Returns the parameters of this function.
    */
   public String[] getParams()
   {
      return params;
   }

   /**
    * Reeturns the text of this function.
    */
   public String getText()
   {
      return text;
   }

   public int hashCode()
   {
      HashCodeBuilder builder = new HashCodeBuilder();
      for( int i = 0; i < params.length; i++ ){
         builder.append( params[i] );
      }
      builder.append( text );
      return builder.toHashCode();
   }

   /**
    * Returns the string representation of this function.
    */
   public String toString()
   {
      StringBuffer b = new StringBuffer( "function(" );
      if( params.length > 0 ){
         for( int i = 0; i < params.length - 1; i++ ){
            b.append( params[i] )
                  .append( "," );
         }
         b.append( params[params.length - 1] );
      }
      b.append( "){" );
      if( text.length() > 0 ){
         b.append( " " )
               .append( text )
               .append( " " );
      }
      b.append( "}" );
      return b.toString();
   }
}