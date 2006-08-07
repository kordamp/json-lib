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

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

/**
 * Provides useful methods on java objects.
 *
 * @author Andres Almiray
 * @version 3
 */
public final class JSONUtils
{
   private static RegexpMatcher FUNCTION_HEADER_MATCHER = null;
   private static final String FUNCTION_HEADER_PATTERN = "^function[ ]?\\(.*\\)$";
   private static RegexpMatcher FUNCTION_MACTHER = null;
   private static RegexpMatcher FUNCTION_PARAMS_MATCHER = null;
   private static final String FUNCTION_PARAMS_PATTERN = "^function[ ]?\\((.*?)\\)$";
   private static final String FUNCTION_PATTERN = "^function[ ]?\\(.*\\)[ ]?\\{.*\\}$";

   private static String javaVersion = "1.3.1";

   static{
      javaVersion = System.getProperty( "java.version" );

      if( isJDK13() ){
         FUNCTION_HEADER_MATCHER = new Perl5RegexpMatcher( FUNCTION_HEADER_PATTERN );
         FUNCTION_PARAMS_MATCHER = new Perl5RegexpMatcher( FUNCTION_PARAMS_PATTERN );
         FUNCTION_MACTHER = new Perl5RegexpMatcher( FUNCTION_PATTERN );
      }else{
         FUNCTION_HEADER_MATCHER = new JdkRegexpMatcher( FUNCTION_HEADER_PATTERN );
         FUNCTION_PARAMS_MATCHER = new JdkRegexpMatcher( FUNCTION_PARAMS_PATTERN );
         FUNCTION_MACTHER = new JdkRegexpMatcher( FUNCTION_PATTERN );
      }
   }

   /**
    * Produce a string from a double. The string "null" will be returned if the
    * number is not finite.
    *
    * @param d A double.
    * @return A String.
    */
   public static String doubleToString( double d )
   {
      if( Double.isInfinite( d ) || Double.isNaN( d ) ){
         return "null";
      }

      // Shave off trailing zeros and decimal point, if possible.

      String s = Double.toString( d );
      if( s.indexOf( '.' ) > 0 && s.indexOf( 'e' ) < 0 && s.indexOf( 'E' ) < 0 ){
         while( s.endsWith( "0" ) ){
            s = s.substring( 0, s.length() - 1 );
         }
         if( s.endsWith( "." ) ){
            s = s.substring( 0, s.length() - 1 );
         }
      }
      return s;
   }

   public static int getDimensions( Class arrayClass )
   {
      if( arrayClass == null || !arrayClass.isArray() ){
         return 0;
      }

      return 1 + getDimensions( arrayClass.getComponentType() );
   }

   /**
    * Returns the params of a function literal.
    */
   public static String getFunctionParams( String function )
   {
      return FUNCTION_PARAMS_MATCHER.getGroupIfMatches( function, 1 );
   }

   public static Class getInnerComponentType( Class type )
   {
      if( !type.isArray() ){
         return type;
      }
      return getInnerComponentType( type.getComponentType() );
   }

   /**
    * Creates a Map with all the properties of the JSONObject.
    */
   public static Map getJSONProperties( JSONObject jsonObject )
   {
      Map properties = new HashMap();
      for( Iterator keys = jsonObject.keys(); keys.hasNext(); ){
         String key = (String) keys.next();
         properties.put( key, getJSONType( jsonObject.get( key ) ) );
      }
      return properties;
   }

   /**
    * Returns the JSON type.
    */
   public static Object getJSONType( Object obj )
   {
      if( isNull( obj ) ){
         return JSONTypes.OBJECT;
      }else if( isArray( obj ) ){
         return JSONTypes.ARRAY;
      }else if( isFunction( obj ) ){
         return JSONTypes.FUNCTION;
      }else if( isBoolean( obj ) ){
         return JSONTypes.BOOLEAN;
      }else if( isNumber( obj ) ){
         return JSONTypes.NUMBER;
      }else if( isString( obj ) ){
         return JSONTypes.STRING;
      }else if( isObject( obj ) ){
         return JSONTypes.OBJECT;
      }else{
         throw new JSONException( "Unsupported type" );
      }
   }

   /**
    * Creates a Map with all the properties of the JSONObject.
    */
   public static Map getProperties( JSONObject jsonObject )
   {
      Map properties = new HashMap();
      for( Iterator keys = jsonObject.keys(); keys.hasNext(); ){
         String key = (String) keys.next();
         properties.put( key, getTypeClass( jsonObject.get( key ) ) );
      }
      return properties;
   }

   /**
    * Returns the JSON type.
    */
   public static Class getTypeClass( Object obj )
   {
      if( isNull( obj ) ){
         return Object.class;
      }else if( isArray( obj ) ){
         return List.class;
      }else if( isFunction( obj ) ){
         return JSONFunction.class;
      }else if( isBoolean( obj ) ){
         return Boolean.class;
      }else if( isNumber( obj ) ){
         return Double.class;
      }else if( isString( obj ) ){
         return String.class;
      }else if( isObject( obj ) ){
         return Object.class;
      }else{
         throw new JSONException( "Unsupported type" );
      }
   }

   /**
    * Tests if obj is an array or Collection.
    */
   public static boolean isArray( Object obj )
   {
      if( obj != null && obj.getClass()
            .isArray() ){
         return true;
      }
      if( obj instanceof Collection ){
         return true;
      }
      return false;
   }

   /**
    * Tests if obj is a Boolean or primitive boolean
    */
   public static boolean isBoolean( Object obj )
   {
      if( obj instanceof Boolean ){
         return true;
      }
      if( obj != null && obj.getClass() == Boolean.TYPE ){
         return true;
      }
      return false;
   }

   /**
    * Tests if obj is javaScript function.<br>
    * Obj must ba a non-null String and match <nowrap>"^function[ ]?\\(.*\\)[
    * ]?\\{.*\\}$"</nowrap>
    */
   public static boolean isFunction( Object obj )
   {
      if( obj instanceof String && obj != null ){
         String str = (String) obj;
         return FUNCTION_MACTHER.matches( str );
      }
      if( obj instanceof JSONFunction && obj != null ){
         return true;
      }
      return false;
   }

   /**
    * Tests if obj is javaScript function header.<br>
    * Obj must ba a non-null String and match "^function[ ]?\\(.*\\)$"
    */
   public static boolean isFunctionHeader( Object obj )
   {
      if( obj instanceof String && obj != null ){
         String str = (String) obj;
         return FUNCTION_HEADER_MATCHER.matches( str );
      }
      return false;
   }

   public static boolean isJDK13()
   {
      return javaVersion.indexOf( "1.3" ) != -1;
   }

   /**
    * Tests if the obj is a javaScript null.
    */
   public static boolean isNull( Object obj )
   {
      if( obj instanceof JSONObject ){
         return ((JSONObject) obj).isNullObject();
      }
      return JSONNull.getInstance()
            .equals( obj );
   }

   /**
    * Tests if obj is a primitive number or wrapper.<br>
    */
   public static boolean isNumber( Object obj )
   {
      if( (obj != null && obj.getClass() == Byte.TYPE)
            || (obj != null && obj.getClass() == Short.TYPE)
            || (obj != null && obj.getClass() == Integer.TYPE)
            || (obj != null && obj.getClass() == Long.TYPE)
            || (obj != null && obj.getClass() == Float.TYPE)
            || (obj != null && obj.getClass() == Double.TYPE) ){
         return true;
      }
      if( (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer)
            || (obj instanceof Long) || (obj instanceof Float) || (obj instanceof Double) ){
         return true;
      }
      return false;
   }

   /**
    * Tests if obj is not a boolean, number, string or array.
    */
   public static boolean isObject( Object obj )
   {
      return !isNumber( obj ) && !isString( obj ) && !isBoolean( obj ) && !isArray( obj )
            || isNull( obj );
   }

   /**
    * Tests if obj is a String or a char
    */
   public static boolean isString( Object obj )
   {
      if( obj instanceof String ){
         return true;
      }
      if( obj instanceof Character ){
         return true;
      }
      if( obj != null && obj.getClass() == Character.TYPE ){
         return true;
      }
      return false;
   }

   /**
    * Produce a string from a Number.
    *
    * @param n A Number
    * @return A String.
    * @throws JSONException If n is a non-finite number.
    */
   public static String numberToString( Number n )
   {
      if( n == null ){
         throw new JSONException( "Null pointer" );
      }
      JSONUtils.testValidity( n );

      // Shave off trailing zeros and decimal point, if possible.

      String s = n.toString();
      if( s.indexOf( '.' ) > 0 && s.indexOf( 'e' ) < 0 && s.indexOf( 'E' ) < 0 ){
         while( s.endsWith( "0" ) ){
            s = s.substring( 0, s.length() - 1 );
         }
         if( s.endsWith( "." ) ){
            s = s.substring( 0, s.length() - 1 );
         }
      }
      return s;
   }

   /**
    * Produce a string in double quotes with backslash sequences in all the
    * right places. A backslash will be inserted within </, allowing JSON text
    * to be delivered in HTML. In JSON text, a string cannot contain a control
    * character or an unescaped quote or backslash.<br>
    * <strong>CAUTION:</strong> if <code>string</code> represents a
    * javascript function, translation of characters will not take place. This
    * will produce a non-conformant JSON text.
    *
    * @param string A String
    * @return A String correctly formatted for insertion in a JSON text.
    */
   public static String quote( String string )
   {
      if( JSONUtils.isFunction( string ) ){
         return string;
      }
      if( string == null || string.length() == 0 ){
         return "\"\"";
      }

      char b;
      char c = 0;
      int i;
      int len = string.length();
      StringBuffer sb = new StringBuffer( len + 4 );
      String t;

      sb.append( '"' );
      for( i = 0; i < len; i += 1 ){
         b = c;
         c = string.charAt( i );
         switch( c )
         {
            case '\\':
            case '"':
               sb.append( '\\' );
               sb.append( c );
               break;
            case '/':
               if( b == '<' ){
                  sb.append( '\\' );
               }
               sb.append( c );
               break;
            case '\b':
               sb.append( "\\b" );
               break;
            case '\t':
               sb.append( "\\t" );
               break;
            case '\n':
               sb.append( "\\n" );
               break;
            case '\f':
               sb.append( "\\f" );
               break;
            case '\r':
               sb.append( "\\r" );
               break;
            default:
               if( c < ' ' ){
                  t = "000" + Integer.toHexString( c );
                  sb.append( "\\u" + t.substring( t.length() - 4 ) );
               }else{
                  sb.append( c );
               }
         }
      }
      sb.append( '"' );
      return sb.toString();
   }

   /**
    * Throw an exception if the object is an NaN or infinite number.
    *
    * @param o The object to test.
    * @throws JSONException If o is a non-finite number.
    */
   public static void testValidity( Object o )
   {
      if( o != null ){
         if( o instanceof Double ){
            if( ((Double) o).isInfinite() || ((Double) o).isNaN() ){
               throw new JSONException( "JSON does not allow non-finite numbers" );
            }
         }else if( o instanceof Float ){
            if( ((Float) o).isInfinite() || ((Float) o).isNaN() ){
               throw new JSONException( "JSON does not allow non-finite numbers." );
            }
         }
      }
   }

   /**
    * Converts an array of primitive chars to objects.<br>
    * <p>
    * <strong>This method is not in ArrayUtils. (commons-lang 2.1)</strong>
    * </p>
    * <p>
    * This method returns <code>null</code> for a <code>null</code> input
    * array.
    * </p>
    *
    * @param array a <code>char</code> array
    * @return a <code>Character</code> array, <code>null</code> if null
    *         array input
    */
   public static Object[] toObject( char[] array )
   {
      if( array == null ){
         return null;
      }else if( array.length == 0 ){
         return ArrayUtils.EMPTY_CHARACTER_OBJECT_ARRAY;
      }
      final Character[] result = new Character[array.length];
      for( int i = 0; i < array.length; i++ ){
         result[i] = new Character( array[i] );
      }
      return result;
   }

   /**
    * Make a JSON text of an Object value. If the object has an
    * value.toJSONString() method, then that method will be used to produce the
    * JSON text. The method is required to produce a strictly conforming text.
    * If the object does not contain a toJSONString method (which is the most
    * common case), then a text will be produced by the rules.
    * <p>
    * Warning: This method assumes that the data structure is acyclical.
    *
    * @param value The value to be serialized.
    * @return a printable, displayable, transmittable representation of the
    *         object, beginning with <code>{</code>&nbsp;<small>(left brace)</small>
    *         and ending with <code>}</code>&nbsp;<small>(right brace)</small>.
    * @throws JSONException If the value is or contains an invalid number.
    */
   public static String valueToString( Object value )
   {
      if( value == null || value.equals( null ) ){
         return "null";
      }
      if( value instanceof JSONFunction ){
         return ((JSONFunction) value).toString();
      }
      if( value instanceof JSONString ){
         Object o;
         try{
            o = ((JSONString) value).toJSONString();
         }
         catch( Exception e ){
            throw new JSONException( e );
         }
         if( o instanceof String ){
            return (String) o;
         }
         throw new JSONException( "Bad value from toJSONString: " + o );
      }
      if( value instanceof Number ){
         return numberToString( (Number) value );
      }
      if( value instanceof Boolean || value instanceof JSONObject || value instanceof JSONArray ){
         return value.toString();
      }
      return quote( value.toString() );
   }

   /**
    * Make a prettyprinted JSON text of an object value.
    * <p>
    * Warning: This method assumes that the data structure is acyclical.
    *
    * @param value The value to be serialized.
    * @param indentFactor The number of spaces to add to each level of
    *        indentation.
    * @param indent The indentation of the top level.
    * @return a printable, displayable, transmittable representation of the
    *         object, beginning with <code>{</code>&nbsp;<small>(left brace)</small>
    *         and ending with <code>}</code>&nbsp;<small>(right brace)</small>.
    * @throws JSONException If the object contains an invalid number.
    */
   public static String valueToString( Object value, int indentFactor, int indent )
   {
      if( value == null || value.equals( null ) ){
         return "null";
      }
      if( value instanceof JSONFunction ){
         return ((JSONFunction) value).toString();
      }
      try{
         if( value instanceof JSONString ){
            Object o = ((JSONString) value).toJSONString();
            if( o instanceof String ){
               return (String) o;
            }
         }
      }
      catch( Exception e ){
         /* forget about it */
      }
      if( value instanceof Number ){
         return numberToString( (Number) value );
      }
      if( value instanceof Boolean ){
         return value.toString();
      }
      if( value instanceof JSONObject ){
         return ((JSONObject) value).toString( indentFactor, indent );
      }
      if( value instanceof JSONArray ){
         return ((JSONArray) value).toString( indentFactor, indent );
      }
      return quote( value.toString() );
   }

   private JSONUtils()
   {
      super();
   }
}