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

package net.sf.json.groovy

import net.sf.json.*
import net.sf.json.test.JSONAssert

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public class TestJsonGroovyBuilder extends GroovyTestCase {
    JsonGroovyBuilder builder

    public void testBuildDefaultRootEmptyObject(){
       def actual = builder.json {}
       JSONAssert.assertEquals( new JSONObject(), actual )
    }

    public void testBuildDefaultRootEmptyArray(){
       builder.json = []
       def actual = builder.json
       JSONAssert.assertEquals( new JSONArray(), actual )
    }

    public void testBuildDefaultRootObjectWithClosure(){
       def actual = builder.json {
          string = "json"
          integer = 1
          bool = true
       }
       def expected = new JSONObject()
           .element("string","json")
           .element("integer",1)
           .element("bool",true)
       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildDefaultRootObjectWithMap(){
       def actual = builder.json([
          'string': "json",
          'integer': 1,
          'bool': true
       ])
       def expected = new JSONObject()
           .element("string","json")
           .element("integer",1)
           .element("bool",true)
       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildDefaultRootArrayWithList(){
       def actual = builder.json(["json", 1, true])
       def expected = new JSONArray()
           .element("json")
           .element(1)
           .element(true)
       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildDefaultRootNestedObjects(){
       def actual = builder.json {
          first = {
             integer = 42
          }
          second = {
             integer = 48
          }
       }
       def expected = new JSONObject()
          .element( "first", new JSONObject().element("integer",42) )
          .element( "second", new JSONObject().element("integer",48) )
       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildDefaultRootArrayWithMultipleArgs(){
       def actual = builder.json( {
             key = "first"
          }, {
             key = "second"
          },
          [key:'third'],
          [1,2,3],
          new Book(title: "Groovy in Action", author: "Dierk Konig"),
          "json"
       )
       def expected = new JSONArray()
          .element( new JSONObject().element("key","first") )
          .element( new JSONObject().element("key","second") )
          .element( new JSONObject().element("key","third") )
          .element( JSONArray.fromObject([1,2,3]) )
          .element( new JSONObject()
             .element("title", "Groovy in Action")
             .element("author", "Dierk Konig")
          )
          .element( "json")
       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildDefaultRootArrayWithMultipleArgs2(){
       def actual = builder.json([key:'first'],[1,2,3]) {
          key = "third"
       }
       def expected = new JSONArray()
          .element( new JSONObject().element("key","first") )
          .element( JSONArray.fromObject([1,2,3]) )
          .element( new JSONObject().element("key","third") )
       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildObjectWithMaps(){
       def actual = builder.books {
          book = [title: "The Definitive Guide to Grails", author: "Graeme Rocher"]
          book = [title: "Groovy in Action", author: "Dierk Konig"]
       }
       def expected = new JSONObject()
          .element( "books", new JSONObject()
             .element( "book", new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
             .accumulate( "book", new JSONObject()
                .element("title", "Groovy in Action")
                .element("author", "Dierk Konig") )
          )

       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildObjectWithBeans(){
       def actual = builder.books {
          book = new Book(title: "The Definitive Guide to Grails", author: "Graeme Rocher")
          book = new Book(title: "Groovy in Action", author: "Dierk Konig")
       }
       def expected = new JSONObject()
          .element( "books", new JSONObject()
             .element( "book", new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
             .accumulate( "book", new JSONObject()
                .element("title", "Groovy in Action")
                .element("author", "Dierk Konig") )
          )

       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildObjectWithClosures(){
       def actual = builder.books {
          book = {
             title = "The Definitive Guide to Grails"
             author= "Graeme Rocher"
          }
          book = {
             title = "Groovy in Action"
             author = "Dierk Konig"
          }
       }
       def expected = new JSONObject()
          .element( "books", new JSONObject()
             .element( "book", new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
             .accumulate( "book", new JSONObject()
                .element("title", "Groovy in Action")
                .element("author", "Dierk Konig") )
          )

       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildObjectWithClosures2(){
       def actual = builder.books {
          2.times {
             book = {
                title = "The Definitive Guide to Grails"
                author= "Graeme Rocher"
             }
          }
       }
       def expected = new JSONObject()
          .element( "books", new JSONObject()
             .element( "book", new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
             .accumulate( "book", new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
          )

       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildObjectWithClosures3(){
       def actual = builder.books {
          2.times {
             book {
                title = "The Definitive Guide to Grails"
                author= "Graeme Rocher"
             }
          }
       }
       def expected = new JSONObject()
          .element( "books", new JSONObject()
             .element( "book", new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
             .accumulate( "book", new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
          )
       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildObjectWithClosures4(){
       def actual = builder.books {
          book {
             title = "The Definitive Guide to Grails"
             author= "Graeme Rocher"
          }
          book {
             title = "The Definitive Guide to Grails"
             author= "Graeme Rocher"
          }
       }
       def expected = new JSONObject()
          .element( "books", new JSONObject()
             .element( "book", new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
             .accumulate( "book", new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
          )
       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildObjectWithMultipleClosures(){
       def actual = builder.books( {
             title = "The Definitive Guide to Grails"
             author= "Graeme Rocher"
          }, {
             title = "Groovy in Action"
             author = "Dierk Konig"
          }
       )
       def expected = new JSONObject()
          .element( "books", new JSONArray()
             .element( new JSONObject()
                .element("title", "The Definitive Guide to Grails")
                .element("author", "Graeme Rocher") )
             .element( new JSONObject()
                .element("title", "Groovy in Action")
                .element("author", "Dierk Konig") )
          )

       JSONAssert.assertEquals( expected, actual )
    }

	protected void setUp(){
	   builder = new JsonGroovyBuilder()
	}
}