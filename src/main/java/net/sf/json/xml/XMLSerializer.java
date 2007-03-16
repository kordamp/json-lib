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

package net.sf.json.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONFunction;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;
import nu.xom.Attribute;
import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
import nu.xom.Serializer;
import nu.xom.Text;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class for transforming JSON to XML an back.<br>
 * When transforming JSONObject and JSONArray instances to XML, this class will
 * add hints for converting back to JSON.<br>
 * Examples:<br>
 *
 * <pre>
 * JSONObject json = JSONObject.fromObject("{\"name\":\"json\",\"bool\":true,\"int\":1}");
 * String xml = new XMLSerializer().write( json );
 * <xmp><o class="object">
 <name type="string">json</name>
 <bool type="boolean">true</bool>
 <int type="number">1</int>
 </o></xmp>
 * </pre><pre>
 * JSONArray json = JSONArray.fromObject("[1,2,3]");
 * String xml = new XMLSerializer().write( json );
 * <xmp><a class="array">
 <e type="number">1</e>
 <e type="number">2</e>
 <e type="number">3</e>
 </a></xmp>
 * </pre>
 *
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class XMLSerializer
{
   private static final String[] EMPTY_ARRAY = new String[0];
   private static final Log log = LogFactory.getLog( XMLSerializer.class );

   /**
    * Creates a JSONArray from a XML string.
    *
    * @param xml A well-formed xml document in a String
    * @return a JSONArray
    * @throws JSONException if the conversion from XML to JSON can't be made for
    *         I/O or format reasons.
    */
   public static JSONArray readArray( String xml )
   {
      JSONArray jsonArray = null;
      try{
         Document doc = new Builder().build( new StringReader( xml ) );
         Element root = doc.getRootElement();
         String defaultType = getType( root, JSONTypes.STRING );
         jsonArray = processArrayElement( root, defaultType );
      }
      catch( Exception e ){
         throw new JSONException( e );
      }
      return jsonArray;
   }

   /**
    * Creates a JSONObject from a XML string.
    *
    * @param xml A well-formed xml document in a String
    * @return a JSONObject
    * @throws JSONException if the conversion from XML to JSON can't be made for
    *         I/O or format reasons.
    */
   public static JSONObject readObject( String xml )
   {
      JSONObject jsonObject = null;
      try{
         Document doc = new Builder().build( new StringReader( xml ) );
         Element root = doc.getRootElement();
         String defaultType = getType( root, JSONTypes.STRING );
         jsonObject = processObjectElement( root, defaultType );
      }
      catch( Exception e ){
         throw new JSONException( e );
      }
      return jsonObject;
   }

   private static String getClass( Element element )
   {
      Attribute attribute = element.getAttribute( "class" );
      String clazz = null;
      if( attribute != null ){
         String clazzText = attribute.getValue()
               .trim();
         if( JSONTypes.OBJECT.compareToIgnoreCase( clazzText ) == 0 ){
            clazz = JSONTypes.OBJECT;
         }else if( JSONTypes.ARRAY.compareToIgnoreCase( clazzText ) == 0 ){
            clazz = JSONTypes.ARRAY;
         }
      }
      return clazz;
   }

   private static String getType( Element element )
   {
      return getType( element, null );
   }

   private static String getType( Element element, String defaultType )
   {
      Attribute attribute = element.getAttribute( "type" );
      String type = null;
      if( attribute != null ){
         String typeText = attribute.getValue()
               .trim();
         if( JSONTypes.BOOLEAN.compareToIgnoreCase( typeText ) == 0 ){
            type = JSONTypes.BOOLEAN;
         }else if( JSONTypes.NUMBER.compareToIgnoreCase( typeText ) == 0 ){
            type = JSONTypes.NUMBER;
         }else if( JSONTypes.INTEGER.compareToIgnoreCase( typeText ) == 0 ){
            type = JSONTypes.INTEGER;
         }else if( JSONTypes.FLOAT.compareToIgnoreCase( typeText ) == 0 ){
            type = JSONTypes.FLOAT;
         }else if( JSONTypes.OBJECT.compareToIgnoreCase( typeText ) == 0 ){
            type = JSONTypes.OBJECT;
         }else if( JSONTypes.ARRAY.compareToIgnoreCase( typeText ) == 0 ){
            type = JSONTypes.ARRAY;
         }else if( JSONTypes.STRING.compareToIgnoreCase( typeText ) == 0 ){
            type = JSONTypes.STRING;
         }else if( JSONTypes.FUNCTION.compareToIgnoreCase( typeText ) == 0 ){
            type = JSONTypes.FUNCTION;
         }
      }else{
         if( defaultType != null ){
            log.info( "Using default type " + defaultType );
            type = defaultType;
         }
      }
      return type;
   }

   private static JSONArray processArrayElement( Element element, String defaultType )
   {
      JSONArray jsonArray = new JSONArray();
      Elements elements = element.getChildElements();
      int max = elements.size();
      for( int i = 0; i < max; i++ ){
         set( jsonArray, elements.get( i ), defaultType );
      }
      return jsonArray;
   }

   private static JSONObject processObjectElement( Element element, String defaultType )
   {
      Attribute nullAttribute = element.getAttribute( "null" );
      if( nullAttribute != null && nullAttribute.getValue()
            .compareToIgnoreCase( "true" ) == 0 ){
         return new JSONObject( true );
      }
      JSONObject jsonObject = new JSONObject();
      Elements elements = element.getChildElements();
      int max = elements.size();
      for( int i = 0; i < max; i++ ){
         set( jsonObject, elements.get( i ), defaultType );
      }
      return jsonObject;
   }

   private static void set( JSONArray jsonArray, Element element, String defaultType )
   {
      String clazz = getClass( element );
      String type = getType( element );
      type = (type == null) ? defaultType : type;

      boolean classProcessed = false;
      if( clazz != null ){
         if( clazz.compareToIgnoreCase( JSONTypes.ARRAY ) == 0 ){
            jsonArray.put( processArrayElement( element, type ) );
            classProcessed = true;
         }else if( clazz.compareToIgnoreCase( JSONTypes.OBJECT ) == 0 ){
            jsonArray.put( processObjectElement( element, type ) );
            classProcessed = true;
         }
      }
      if( !classProcessed ){
         if( type.compareToIgnoreCase( JSONTypes.BOOLEAN ) == 0 ){
            jsonArray.put( Boolean.valueOf( element.getValue() ) );
         }else if( type.compareToIgnoreCase( JSONTypes.NUMBER ) == 0 ){
            // try integer first
            try{
               jsonArray.put( Integer.valueOf( element.getValue() ) );
            }
            catch( NumberFormatException e ){
               jsonArray.put( Double.valueOf( element.getValue() ) );
            }
         }else if( type.compareToIgnoreCase( JSONTypes.INTEGER ) == 0 ){
            jsonArray.put( Integer.valueOf( element.getValue() ) );
         }else if( type.compareToIgnoreCase( JSONTypes.FLOAT ) == 0 ){
            jsonArray.put( Double.valueOf( element.getValue() ) );
         }else if( type.compareToIgnoreCase( JSONTypes.STRING ) == 0 ){
            jsonArray.put( element.getValue() );
         }else if( type.compareToIgnoreCase( JSONTypes.FUNCTION ) == 0 ){
            String[] params = null;
            String text = element.getValue();
            Attribute paramsAttribute = element.getAttribute( "params" );
            if( paramsAttribute != null ){
               params = StringUtils.split( paramsAttribute.getValue(), "," );
            }
            jsonArray.put( new JSONFunction( params, text ) );
         }
      }
   }

   private static void set( JSONObject jsonObject, Element element, String defaultType )
   {
      String clazz = getClass( element );
      String type = getType( element );
      type = (type == null) ? defaultType : type;

      boolean classProcessed = false;
      if( clazz != null ){
         if( clazz.compareToIgnoreCase( JSONTypes.ARRAY ) == 0 ){
            jsonObject.put( element.getLocalName(), processArrayElement( element, type ) );
            classProcessed = true;
         }else if( clazz.compareToIgnoreCase( JSONTypes.OBJECT ) == 0 ){
            jsonObject.put( element.getLocalName(), processObjectElement( element, type ) );
            classProcessed = true;
         }
      }
      if( !classProcessed ){
         if( type.compareToIgnoreCase( JSONTypes.BOOLEAN ) == 0 ){
            jsonObject.put( element.getLocalName(), Boolean.valueOf( element.getValue() ) );
         }else if( type.compareToIgnoreCase( JSONTypes.NUMBER ) == 0 ){
            // try integer first
            try{
               jsonObject.put( element.getLocalName(), Integer.valueOf( element.getValue() ) );
            }
            catch( NumberFormatException e ){
               jsonObject.put( element.getLocalName(), Double.valueOf( element.getValue() ) );
            }
         }else if( type.compareToIgnoreCase( JSONTypes.INTEGER ) == 0 ){
            jsonObject.put( element.getLocalName(), Integer.valueOf( element.getValue() ) );
         }else if( type.compareToIgnoreCase( JSONTypes.FLOAT ) == 0 ){
            jsonObject.put( element.getLocalName(), Double.valueOf( element.getValue() ) );
         }else if( type.compareToIgnoreCase( JSONTypes.FUNCTION ) == 0 ){
            String[] params = null;
            String text = element.getValue();
            Attribute paramsAttribute = element.getAttribute( "params" );
            if( paramsAttribute != null ){
               params = StringUtils.split( paramsAttribute.getValue(), "," );
            }
            jsonObject.put( element.getLocalName(), new JSONFunction( params, text ) );
         }else if( type.compareToIgnoreCase( JSONTypes.STRING ) == 0 ){
            // see if by any chance has a 'params' attribute
            Attribute paramsAttribute = element.getAttribute( "params" );
            if( paramsAttribute != null ){
               String[] params = null;
               String text = element.getValue();
               params = StringUtils.split( paramsAttribute.getValue(), "," );
               jsonObject.put( element.getLocalName(), new JSONFunction( params, text ) );
            }else{
               jsonObject.put( element.getLocalName(), element.getValue() );
            }
         }
      }
   }

   /** the name for an JSONArray Element */
   private String arrayName;
   /** the name for an JSONArray's element Element */
   private String elementName;
   /** list of properties to be expanded from child to parent */
   private String[] expandableProperties;
   /** flag to be tolerant for incomplete namespace prefixes */
   private boolean namespaceLenient;
   /** the name for an JSONObject Element */
   private String objectName;
   /** the name for the root Element */
   private String rootName;
   /** flag for adding JSON types hints as attributes */
   private boolean typeHintsEnabled;

   public XMLSerializer()
   {
      setObjectName( "o" );
      setArrayName( "a" );
      setElementName( "e" );
      setTypeHintsEnabled( true );
      setNamespaceLenient( false );
      setExpandableProperties( EMPTY_ARRAY );
   }

   /**
    * Returns the name used for JSONArray.
    */
   public String getArrayName()
   {
      return arrayName;
   }

   /**
    * Returns the name used for JSONArray elements.
    */
   public String getElementName()
   {
      return elementName;
   }

   /**
    * Returns a list of properties to be expanded from child to parent.
    */
   public String[] getExpandableProperties()
   {
      return expandableProperties;
   }

   /**
    * Returns the name used for JSONArray.
    */
   public String getObjectName()
   {
      return objectName;
   }

   /**
    * Returns the name used for the root element.
    */
   public String getRootName()
   {
      return rootName;
   }

   /**
    * Returns wether this serializer is tolerant to namespaces without URIs or
    * not.
    */
   public boolean isNamespaceLenient()
   {
      return namespaceLenient;
   }

   /**
    * Returns true if JSON types will be included as attributes.
    */
   public boolean isTypeHintsEnabled()
   {
      return typeHintsEnabled;
   }

   /**
    * Sets the name used for JSONArray.<br>
    * Default is 'a'.
    */
   public void setArrayName( String arrayName )
   {
      this.arrayName = StringUtils.isBlank( arrayName ) ? "a" : arrayName;
   }

   /**
    * Sets the name used for JSONArray elements.<br>
    * Default is 'e'.
    */
   public void setElementName( String elementName )
   {
      this.elementName = StringUtils.isBlank( elementName ) ? "e" : elementName;
   }

   /**
    * Sets the list of properties to be expanded from child to parent.
    */
   public void setExpandableProperties( String[] expandableProperties )
   {
      this.expandableProperties = expandableProperties == null ? EMPTY_ARRAY : expandableProperties;
   }

   /**
    * Sets wether this serializer is tolerant to namespaces without URIs or not.
    */
   public void setNamespaceLenient( boolean namespaceLenient )
   {
      this.namespaceLenient = namespaceLenient;
   }

   /**
    * Sets the name used for JSONObject.<br>
    * Default is 'o'.
    */
   public void setObjectName( String objectName )
   {
      this.objectName = StringUtils.isBlank( objectName ) ? "o" : objectName;
   }

   /**
    * Sets the name used for the root element.
    */
   public void setRootName( String rootName )
   {
      this.rootName = StringUtils.isBlank( rootName ) ? null : rootName;
   }

   /**
    * Sets wether JSON types will be included as attributes.
    */
   public void setTypeHintsEnabled( boolean typeHintsEnabled )
   {
      this.typeHintsEnabled = typeHintsEnabled;
   }

   /**
    * Writes a JSON value into a XML string with UTF-8 encoding.<br>
    *
    * @param json The JSON value to transform
    * @return a String representation of a well-formed xml document.
    * @throws JSONException if the conversion from JSON to XML can't be made for
    *         I/O reasons.
    */
   public String write( JSON json )
   {
      return write( json, null );
   }

   /**
    * Writes a JSON value into a XML string with an specific encoding.<br>
    * If the encoding string is null it will use UTF-8.
    *
    * @param json The JSON value to transform
    * @param encoding The xml encoding to use
    * @return a String representation of a well-formed xml document.
    * @throws JSONException if the conversion from JSON to XML can't be made for
    *         I/O reasons or the encoding is not supported.
    */
   public String write( JSON json, String encoding )
   {
      if( JSONNull.getInstance()
            .equals( json ) ){
         Element root = null;
         root = newElement( getRootName() == null ? getObjectName() : getRootName() );
         root.addAttribute( new Attribute( "null", "true" ) );
         Document doc = new Document( root );
         return writeDocument( doc, encoding );
      }else if( json instanceof JSONArray ){
         JSONArray jsonArray = (JSONArray) json;
         Element root = processJSONArray( jsonArray,
               newElement( getRootName() == null ? getArrayName() : getRootName() ),
               expandableProperties );
         Document doc = new Document( root );
         return writeDocument( doc, encoding );
      }else{
         JSONObject jsonObject = (JSONObject) json;
         Element root = null;
         if( jsonObject.isNullObject() ){
            root = newElement( getObjectName() );
            root.addAttribute( new Attribute( "null", "true" ) );
         }else{
            root = processJSONObject( jsonObject,
                  newElement( getRootName() == null ? getObjectName() : getRootName() ),
                  expandableProperties );
         }
         Document doc = new Document( root );
         return writeDocument( doc, encoding );
      }
   }

   private Element newElement( String name )
   {
      return namespaceLenient ? new CustomElement( name ) : new Element( name );
   }

   private Element processJSONArray( JSONArray array, Element root, String[] expandableProperties )
   {
      int l = array.length();
      for( int i = 0; i < l; i++ ){
         Object value = array.get( i );
         Element element = processJSONValue( value, root, null, expandableProperties );
         root.appendChild( element );
      }
      return root;
   }

   private Element processJSONObject( JSONObject jsonObject, Element root,
         String[] expandableProperties )
   {
      if( jsonObject.isNullObject() ){
         root.addAttribute( new Attribute( "null", "true" ) );
         return root;
      }else if( jsonObject.isEmpty() ){
         return root;
      }

      Object[] names = jsonObject.names()
            .toArray();
      Element element = null;
      for( int i = 0; i < names.length; i++ ){
         String name = (String) names[i];
         Object value = jsonObject.get( name );
         if( value instanceof JSONArray
               && (((JSONArray) value).isExpandElements() || ArrayUtils.contains(
                     expandableProperties, name )) ){
            JSONArray array = (JSONArray) value;
            int l = array.length();
            for( int j = 0; j < l; j++ ){
               Object item = array.get( j );
               element = newElement( name );
               if( item instanceof JSONObject ){
                  element = processJSONValue( (JSONObject) item, root, element,
                        expandableProperties );
               }else if( item instanceof JSONArray ){
                  element = processJSONValue( (JSONArray) item, root, element, expandableProperties );
               }else{
                  element = processJSONValue( item, root, element, expandableProperties );
               }
               root.appendChild( element );
            }
         }else{
            element = newElement( name );
            element = processJSONValue( value, root, element, expandableProperties );
            root.appendChild( element );
         }
      }
      return root;
   }

   private Element processJSONValue( Object value, Element root, Element target,
         String[] expandableProperties )
   {
      if( target == null ){
         target = newElement( getElementName() );
      }
      if( JSONUtils.isBoolean( value ) ){
         if( isTypeHintsEnabled() ){
            target.addAttribute( new Attribute( "type", JSONTypes.BOOLEAN ) );
         }
         target.appendChild( value.toString() );
      }else if( JSONUtils.isNumber( value ) ){
         if( isTypeHintsEnabled() ){
            target.addAttribute( new Attribute( "type", JSONTypes.NUMBER ) );
         }
         target.appendChild( value.toString() );
      }else if( JSONUtils.isFunction( value ) ){
         JSONFunction func = (JSONFunction) value;
         if( isTypeHintsEnabled() ){
            target.addAttribute( new Attribute( "type", JSONTypes.FUNCTION ) );
         }
         String params = ArrayUtils.toString( func.getParams() );
         params = params.substring( 1 );
         params = params.substring( 0, params.length() - 1 );
         target.addAttribute( new Attribute( "params", params ) );
         target.appendChild( new Text( "<![CDATA[" + func.getText() + "]]>" ) );
      }else if( JSONUtils.isString( value ) ){
         if( isTypeHintsEnabled() ){
            target.addAttribute( new Attribute( "type", JSONTypes.STRING ) );
         }
         target.appendChild( value.toString() );
      }else if( value instanceof JSONArray ){
         if( isTypeHintsEnabled() ){
            target.addAttribute( new Attribute( "class", JSONTypes.ARRAY ) );
         }
         target = processJSONArray( (JSONArray) value, target, expandableProperties );
      }else if( value instanceof JSONObject ){
         if( isTypeHintsEnabled() ){
            target.addAttribute( new Attribute( "class", JSONTypes.OBJECT ) );
         }
         target = processJSONObject( (JSONObject) value, target, expandableProperties );
      }else if( JSONUtils.isNull( value ) ){
         if( isTypeHintsEnabled() ){
            target.addAttribute( new Attribute( "class", JSONTypes.OBJECT ) );
         }
         target.addAttribute( new Attribute( "null", "true" ) );
      }
      return target;
   }

   private String writeDocument( Document doc, String encoding )
   {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try{
         XomSerializer serializer = (encoding == null) ? new XomSerializer( baos )
               : new XomSerializer( baos, encoding );
         serializer.write( doc );
      }
      catch( IOException ioe ){
         throw new JSONException( ioe );
      }
      return baos.toString();
   }

   private static class CustomElement extends Element
   {
      private static String getName( String name )
      {
         int colon = name.indexOf( ':' );
         if( colon != -1 ){
            return name.substring( colon + 1 );
         }
         return name;
      }

      private static String getPrefix( String name )
      {
         int colon = name.indexOf( ':' );
         if( colon != -1 ){
            return name.substring( 0, colon );
         }
         return "";
      }

      private String prefix;

      public CustomElement( String name )
      {
         super( CustomElement.getName( name ) );
         prefix = CustomElement.getPrefix( name );
      }

      public final String getQName()
      {
         if( prefix.length() == 0 ){
            return getLocalName();
         }else{
            return prefix + ":" + getLocalName();
         }
      }
   }

   private class XomSerializer extends Serializer
   {
      public XomSerializer( OutputStream out )
      {
         super( out );
      }

      public XomSerializer( OutputStream out, String encoding ) throws UnsupportedEncodingException
      {
         super( out, encoding );
      }

      protected void write( Text text ) throws IOException
      {
         String value = text.getValue();
         if( value.startsWith( "<![CDATA[" ) && value.endsWith( "]]>" ) ){
            value = value.substring( 9 );
            value = value.substring( 0, value.length() - 3 );
            writeRaw( "<![CDATA[" );
            writeRaw( value );
            writeRaw( "]]>" );
         }else{
            super.write( text );
         }
      }

      protected void writeEmptyElementTag( Element element ) throws IOException
      {
         if( element instanceof CustomElement && isNamespaceLenient() ){
            writeTagBeginning( (CustomElement) element );
            writeRaw( "/>" );
         }else{
            super.writeEmptyElementTag( element );
         }
      }

      protected void writeEndTag( Element element ) throws IOException
      {
         if( element instanceof CustomElement && isNamespaceLenient() ){
            writeRaw( "</" );
            writeRaw( ((CustomElement) element).getQName() );
            writeRaw( ">" );
         }else{
            super.writeEndTag( element );
         }
      }

      protected void writeStartTag( Element element ) throws IOException
      {
         if( element instanceof CustomElement && isNamespaceLenient() ){
            writeTagBeginning( (CustomElement) element );
            writeRaw( ">" );
         }else{
            super.writeStartTag( element );
         }
      }

      private void writeTagBeginning( CustomElement element ) throws IOException
      {
         writeRaw( "<" );
         writeRaw( element.getQName() );
         writeAttributes( element );
         writeNamespaceDeclarations( element );
      }
   }
}