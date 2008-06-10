/*
 * Copyright 2006-2008 the original author or authors.
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

package net.sf.json.spring.web.servlet.view;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;
import net.sf.json.filters.OrPropertyFilter;

import org.springframework.web.servlet.view.AbstractView;

/**
 * A View that renders its model as a JSON object.
 *
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class JsonView extends AbstractView {
   /** Default content type. Overridable as bean property. */
   private static final String DEFAULT_JSON_CONTENT_TYPE = "application/json";
   private boolean forceTopLevelArray = false;
   private boolean skipBindingResult = true;
   /** Json confiiguration */
   private JsonConfig jsonConfig = new JsonConfig();
   
   public JsonView() {
      super();
      setContentType( DEFAULT_JSON_CONTENT_TYPE );
   }

   public JsonConfig getJsonConfig(){
      return jsonConfig;
   }

   public boolean isForceTopLevelArray() {
      return forceTopLevelArray;
   }

   /**
    * Returns whether the JSONSerializer will ignore or not its internal property
    * exclusions.
    */
   public boolean isIgnoreDefaultExcludes() {
      return jsonConfig.isIgnoreDefaultExcludes();
   }

   /**
    * Returns whether the JSONSerializer will skip or not any BindingResult related keys on the model.<p>
    * Models in Spring >= 2.5 will cause an exception as they contain a BindingResult that cycles back.
    */
   public boolean isSkipBindingResult() {
      return skipBindingResult;
   }

   /**
    * Sets the group of properties to be excluded.
    */
   public void setExcludedProperties( String[] excludedProperties ) {
      jsonConfig.setExcludes( excludedProperties );
   }
   
   public void setForceTopLevelArray( boolean forceTopLevelArray ) {
      this.forceTopLevelArray = forceTopLevelArray;
   }

   /**
    * Sets whether the JSONSerializer will ignore or not its internal property
    * exclusions.
    */
   public void setIgnoreDefaultExcludes( boolean ignoreDefaultExcludes ) {
      jsonConfig.setIgnoreDefaultExcludes( ignoreDefaultExcludes );
   }

   public void setJsonConfig( JsonConfig jsonConfig ) {
     this.jsonConfig = jsonConfig != null ? jsonConfig : new JsonConfig();
     if( skipBindingResult ){
        PropertyFilter jsonPropertyFilter = this.jsonConfig.getJsonPropertyFilter();
        if( jsonPropertyFilter == null ) {
           this.jsonConfig.setJsonPropertyFilter( new BindingResultPropertyFilter() );
        }else{
           this.jsonConfig.setJsonPropertyFilter( new OrPropertyFilter(new BindingResultPropertyFilter(), jsonPropertyFilter) );
        }
     }
   }

   /**
    * Sets whether the JSONSerializer will skip or not any BindingResult related keys on the model.<p>
    * Models in Spring >= 2.5 will cause an exception as they contain a BindingResult that cycles back.
    */
   public void setSkipBindingResult( boolean kipBindingResult ) {
      this.skipBindingResult = skipBindingResult;
   }

   /**
    * Creates a JSON [JSONObject,JSONArray,JSONNUll] from the model values.
    */
   protected JSON createJSON( Map model, HttpServletRequest request, HttpServletResponse response ) {
      return defaultCreateJSON( model );
   }

   /**
    * Creates a JSON [JSONObject,JSONArray,JSONNUll] from the model values.
    */
   protected final JSON defaultCreateJSON( Map model ) {
      if( skipBindingResult && jsonConfig.getJsonPropertyFilter() == null ){
         this.jsonConfig.setJsonPropertyFilter( new BindingResultPropertyFilter() );
      }
      return JSONSerializer.toJSON( model, jsonConfig );
   }

   /**
    * Returns the group of properties to be excluded.
    */
   protected String[] getExcludedProperties() {
      return jsonConfig.getExcludes();
   }

   protected void renderMergedOutputModel( Map model, HttpServletRequest request,
         HttpServletResponse response ) throws Exception {
      response.setContentType( getContentType() );
      writeJSON( model, request, response );
   }

   protected void writeJSON( Map model, HttpServletRequest request,
         HttpServletResponse response ) throws Exception {
      JSON json = createJSON( model, request, response );
      if( forceTopLevelArray ){
         json = new JSONArray().element(json);
      }
      json.write( response.getWriter() );
   }

   private static class BindingResultPropertyFilter implements PropertyFilter {
      public boolean apply( Object source, String name, Object value ) {
         return name.startsWith("org.springframework.validation.BindingResult.");
      }
   }
}
