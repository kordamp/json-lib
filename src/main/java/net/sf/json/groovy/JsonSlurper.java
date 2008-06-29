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

package net.sf.json.groovy;

import groovy.lang.GroovyObjectSupport;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

/**
 * A Helper class modeled after XmlSlurper
 * 
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class JsonSlurper extends GroovyObjectSupport {

   private JsonConfig jsonConfig;

   public JsonSlurper() {
      this( new JsonConfig() );
   }

   public JsonSlurper( JsonConfig jsonConfig ) {
      this.jsonConfig = jsonConfig != null ? jsonConfig : new JsonConfig();
   }

   public JSON parse( File file ) throws IOException {
      return parse( new FileReader( file ) );
   }

   public JSON parse( URL url ) throws IOException {
      return parse( url.openConnection().getInputStream() );
   }

   public JSON parse( InputStream input ) throws IOException {
      return parse( new InputStreamReader( input ) );
   }

   public JSON parse( String uri ) throws IOException {
      return parse( new URL( uri ) );
   }

   public JSON parse( Reader reader ) throws IOException {
      // unfortunately JSONSerializer can't process the text as a stream
      // so we must read the full content first and then parse it
      char[] chunk = new char[1024];
      StringBuffer buffer = new StringBuffer();
      while( reader.read( chunk ) != -1 ) {
         buffer.append( chunk );
      }
      return parseText( buffer.toString() );
   }

   public JSON parseText( String text ) {
      return JSONSerializer.toJSON( text, jsonConfig );
   }
}