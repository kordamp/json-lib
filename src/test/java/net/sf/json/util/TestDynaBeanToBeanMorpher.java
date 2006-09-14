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

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.ezmorph.MorphException;
import net.sf.ezmorph.MorphUtils;
import net.sf.ezmorph.MorpherRegistry;
import net.sf.json.Assertions;
import net.sf.json.sample.BeanA;
import net.sf.json.sample.BeanB;
import net.sf.json.sample.BeanC;

import org.apache.commons.beanutils.DynaBean;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class TestDynaBeanToBeanMorpher extends TestCase
{
   public static void main( String[] args )
   {
      junit.textui.TestRunner.run( TestDynaBeanToBeanMorpher.class );
   }

   private MorpherRegistry morpherRegistry;

   public TestDynaBeanToBeanMorpher( String name )
   {
      super( name );
   }

   public void testException_array_class()
   {
      try{
         new DynaBeanToBeanMorpher( Map[].class, morpherRegistry );
         fail( "Expected an IllegalArgumentException" );
      }
      catch( IllegalArgumentException expected ){
         // ok
      }
   }

   public void testException_DynaBean_class()
   {
      try{
         new DynaBeanToBeanMorpher( JSONDynaBean.class, morpherRegistry );
         fail( "Expected an IllegalArgumentException" );
      }
      catch( IllegalArgumentException expected ){
         // ok
      }
   }

   public void testException_interface_class()
   {
      try{
         new DynaBeanToBeanMorpher( Map.class, morpherRegistry );
         fail( "Expected an IllegalArgumentException" );
      }
      catch( IllegalArgumentException expected ){
         // ok
      }
   }

   public void testException_null_class()
   {
      try{
         new DynaBeanToBeanMorpher( null, morpherRegistry );
         fail( "Expected an IllegalArgumentException" );
      }
      catch( IllegalArgumentException expected ){
         // ok
      }
   }

   public void testException_null_morpherRegistry()
   {
      try{
         new DynaBeanToBeanMorpher( BeanA.class, null );
         fail( "Expected an IllegalArgumentException" );
      }
      catch( IllegalArgumentException expected ){
         // ok
      }
   }

   public void testException_primitive_class()
   {
      try{
         new DynaBeanToBeanMorpher( int.class, morpherRegistry );
         fail( "Expected an IllegalArgumentException" );
      }
      catch( IllegalArgumentException expected ){
         // ok
      }
   }

   public void testException_unssuported_value()
   {
      try{
         DynaBeanToBeanMorpher morpher = new DynaBeanToBeanMorpher( BeanA.class, morpherRegistry );
         morpher.morph( new BeanB() );
         fail( "Expected a MorphException" );
      }
      catch( MorphException expected ){
         // ok
      }
   }

   public void testMorph() throws Exception
   {
      Map properties = new HashMap();
      properties.put( "string", String.class );
      properties.put( "integer", Integer.class );
      properties.put( "bool", Boolean.class );
      JSONDynaClass dynaClass = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaBean dynaBean = (JSONDynaBean) dynaClass.newInstance();
      dynaBean.setDynamicFormClass( dynaClass );
      dynaBean.set( "string", "dyna json" );
      dynaBean.set( "integer", "24" );
      dynaBean.set( "bool", "false" );

      DynaBeanToBeanMorpher morpher = new DynaBeanToBeanMorpher( BeanA.class, morpherRegistry );
      BeanA beanA = (BeanA) morpher.morph( dynaBean );
      Assertions.assertNotNull( beanA );
      Assertions.assertEquals( false, beanA.isBool() );
      Assertions.assertEquals( 24, beanA.getInteger() );
      Assertions.assertEquals( "dyna json", beanA.getString() );
   }

   public void testMorph_nested__dynaBeans() throws Exception
   {
      Map properties = new HashMap();
      properties.put( "string", String.class );
      properties.put( "integer", Integer.class );
      properties.put( "bool", Boolean.class );
      JSONDynaClass dynaClass = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaBean dynaBeanA = (JSONDynaBean) dynaClass.newInstance();
      dynaBeanA.setDynamicFormClass( dynaClass );
      dynaBeanA.set( "string", "dyna json" );
      dynaBeanA.set( "integer", "24" );
      dynaBeanA.set( "bool", "false" );

      properties = new HashMap();
      properties.put( "string", String.class );
      properties.put( "integer", Integer.class );
      properties.put( "bool", Boolean.class );
      properties.put( "intarray", int[].class );
      dynaClass = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaBean dynaBeanB = (JSONDynaBean) dynaClass.newInstance();
      dynaBeanB.setDynamicFormClass( dynaClass );
      dynaBeanB.set( "string", "dyna json B" );
      dynaBeanB.set( "integer", "48" );
      dynaBeanB.set( "bool", "true" );
      dynaBeanB.set( "intarray", new int[] { 4, 5, 6 } );

      properties = new HashMap();
      properties.put( "beanA", DynaBean.class );
      properties.put( "beanB", DynaBean.class );
      dynaClass = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaBean dynaBeanC = (JSONDynaBean) dynaClass.newInstance();
      dynaBeanC.setDynamicFormClass( dynaClass );
      dynaBeanC.set( "beanA", dynaBeanA );
      dynaBeanC.set( "beanB", dynaBeanB );

      morpherRegistry.registerMorpher( new DynaBeanToBeanMorpher( BeanA.class, morpherRegistry ) );
      morpherRegistry.registerMorpher( new DynaBeanToBeanMorpher( BeanB.class, morpherRegistry ) );
      DynaBeanToBeanMorpher morpher = new DynaBeanToBeanMorpher( BeanC.class, morpherRegistry );
      BeanC beanC = (BeanC) morpher.morph( dynaBeanC );
      Assertions.assertNotNull( beanC );
      BeanA beanA = beanC.getBeanA();
      Assertions.assertEquals( false, beanA.isBool() );
      Assertions.assertEquals( 24, beanA.getInteger() );
      Assertions.assertEquals( "dyna json", beanA.getString() );
      BeanB beanB = beanC.getBeanB();
      Assertions.assertEquals( true, beanB.isBool() );
      Assertions.assertEquals( 48, beanB.getInteger() );
      Assertions.assertEquals( "dyna json B", beanB.getString() );
      Assertions.assertEquals( new int[] { 4, 5, 6 }, beanB.getIntarray() );
   }

   public void testMorph_nested__specific_classes() throws Exception
   {
      Map properties = new HashMap();
      properties.put( "beanA", BeanA.class );
      properties.put( "beanB", BeanB.class );
      JSONDynaClass dynaClass = new JSONDynaClass( "JSON", JSONDynaBean.class, properties );
      JSONDynaBean dynaBean = (JSONDynaBean) dynaClass.newInstance();
      dynaBean.setDynamicFormClass( dynaClass );
      dynaBean.set( "beanA", new BeanA() );
      dynaBean.set( "beanB", new BeanB() );

      DynaBeanToBeanMorpher morpher = new DynaBeanToBeanMorpher( BeanC.class, morpherRegistry );
      BeanC beanC = (BeanC) morpher.morph( dynaBean );
      Assertions.assertNotNull( beanC );
      BeanA beanA = beanC.getBeanA();
      Assertions.assertEquals( true, beanA.isBool() );
      Assertions.assertEquals( 42, beanA.getInteger() );
      Assertions.assertEquals( "json", beanA.getString() );
      BeanB beanB = beanC.getBeanB();
      Assertions.assertEquals( true, beanB.isBool() );
      Assertions.assertEquals( 42, beanB.getInteger() );
      Assertions.assertEquals( "json", beanB.getString() );
      Assertions.assertEquals( new int[] { 1, 2, 3 }, beanB.getIntarray() );
   }

   public void testMorph_null()
   {
      DynaBeanToBeanMorpher morpher = new DynaBeanToBeanMorpher( BeanA.class, morpherRegistry );
      BeanA beanA = (BeanA) morpher.morph( null );
      assertNull( beanA );
   }

   protected void setUp() throws Exception
   {
      morpherRegistry = new MorpherRegistry();
      MorphUtils.registerStandardMorphers( morpherRegistry );
   }
}