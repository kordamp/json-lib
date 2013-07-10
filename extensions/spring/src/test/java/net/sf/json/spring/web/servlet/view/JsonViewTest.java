/*
 * Copyright 2006-2013 the original author or authors.
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

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.jstester.JsTester;
import net.sf.json.JSONSerializer;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.StaticWebApplicationContext;

/**
 * @author Andres Almiray
 */
public class JsonViewTest extends TestCase {
   public static void main( String[] args ) {
      junit.textui.TestRunner.run( JsonViewTest.class );
   }

   private JsTester jsTester;
   private MockServletContext servletContext;
   private MockHttpServletRequest servletRequest;
   private MockHttpServletResponse servletResponse;

   public JsonViewTest( String testName ) {
      super( testName );
   }

   public void testRenderObjectGraph() throws Exception {
      JsonView view = new JsonView();
      Map model = new HashMap();
      model.put( "bool", Boolean.TRUE );
      model.put( "integer", new Integer( 1 ) );
      model.put( "str", "string" );
      Map bean = new HashMap();
      bean.put( "name", "mybean" );
      bean.put( "bools", new boolean[] { true, false } );
      model.put( "bean", bean );

      view.render( model, servletRequest, servletResponse );
      jsTester.eval( toJsScript( servletResponse ) );

      jsTester.assertNotNull( "json" );
      jsTester.assertIsObject( "json" );
      jsTester.assertEquals( "json.bool", "true" );
      jsTester.assertEquals( "json.integer", "1" );
      jsTester.assertEquals( "json.str", "'string'" );
      jsTester.assertIsObject( "json.bean" );
      jsTester.assertIsArray( "json.bean.bools" );
      jsTester.assertEquals( "2", "json.bean.bools.length" );
      jsTester.assertEquals( "'mybean'", "json.bean.name" );
   }

   public void testRenderObjectGraphWithPropertyExclusion() throws Exception {
      JsonView view = new JsonView();
      view.setExcludedProperties( new String[] { "bools" } );
      Map model = new HashMap();
      model.put( "bool", Boolean.TRUE );
      model.put( "integer", new Integer( 1 ) );
      model.put( "str", "string" );
      Map bean = new HashMap();
      bean.put( "name", "mybean" );
      bean.put( "bools", new boolean[] { true, false } );
      model.put( "bean", bean );

      view.render( model, servletRequest, servletResponse );
      jsTester.eval( toJsScript( servletResponse ) );

      jsTester.assertNotNull( "json" );
      jsTester.assertIsObject( "json" );
      jsTester.assertEquals( "json.bool", "true" );
      jsTester.assertEquals( "json.integer", "1" );
      jsTester.assertEquals( "json.str", "'string'" );
      jsTester.assertIsObject( "json.bean" );
      jsTester.assertIsUndefined( "json.bean.bools" );
      jsTester.assertEquals( "'mybean'", "json.bean.name" );
   }

   public void testRenderSimpleProperties() throws Exception {
      JsonView view = new JsonView();
      Map model = new HashMap();
      model.put( "bool", Boolean.TRUE );
      model.put( "integer", new Integer( 1 ) );
      model.put( "str", "string" );

      view.render( model, servletRequest, servletResponse );
      jsTester.eval( toJsScript( servletResponse ) );

      jsTester.assertNotNull( "json" );
      jsTester.assertIsObject( "json" );
      jsTester.assertEquals( "json.bool", "true" );
      jsTester.assertEquals( "json.integer", "1" );
      jsTester.assertEquals( "json.str", "'string'" );
   }
   
   public void testForceTopLevelArray() throws Exception {
      JsonView view = new JsonView();
      view.setForceTopLevelArray( true );
      Map model = new HashMap();
      model.put( "bool", Boolean.TRUE );
      model.put( "integer", new Integer( 1 ) );
      model.put( "str", "string" );

      view.render( model, servletRequest, servletResponse );
      jsTester.eval( toJsScript( servletResponse ) );

      jsTester.assertNotNull( "json" );
      jsTester.assertIsArray( "json" );
      jsTester.assertEquals( "json[0].bool", "true" );
      jsTester.assertEquals( "json[0].integer", "1" );
      jsTester.assertEquals( "json[0].str", "'string'" );
   }

   public void testCharEncoding() throws Exception {
      JsonView view = new JsonView();
      view.setForceTopLevelArray( true );
      Map model = new HashMap();
      model.put( "unicodestring", "\u5718\u9AD4\u6236\u53E3\u9A57\u8B49\u7A0B\u5E8F" );

      view.render( model, servletRequest, servletResponse );
      jsTester.eval( toJsScript( servletResponse ) );

      jsTester.assertNotNull( "json" );
      jsTester.assertIsArray( "json" );
      jsTester.assertEquals( "json[0].unicodestring", "'\u5718\u9AD4\u6236\u53E3\u9A57\u8B49\u7A0B\u5E8F'" );
   }

   protected void setUp() throws Exception {
      servletContext = new MockServletContext();
      StaticWebApplicationContext wac = new StaticWebApplicationContext();
      wac.setServletContext( servletContext );
      servletRequest = new MockHttpServletRequest( servletContext );
      servletResponse = new MockHttpServletResponse();
      servletResponse.setBufferSize(100);

      jsTester = new JsTester();
      jsTester.onSetUp();
   }

   protected void tearDown() throws Exception {
      jsTester.onTearDown();
   }

   private String toJsScript( MockHttpServletResponse response ) throws Exception {
      // looks like MockHttpServletResponse is broken
      String json = response.getContentAsString();
      if( json.startsWith("[{") && !json.endsWith("}]") ){ json += "}]"; }
      if( json.startsWith("{") && !json.endsWith("}") ){ json += "}"; }
      if( json.startsWith("[") && !json.endsWith("]") ){ json += "]"; }
      return "var json = eval('(" + json + ")');";
   }
}
