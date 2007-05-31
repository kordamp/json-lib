package net.sf.json.groovy

import net.sf.json.*
import net.sf.json.test.JSONAssert

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
          [1,2,3]
       )
       def expected = new JSONArray()
          .element( new JSONObject().element("key","first") )
          .element( new JSONObject().element("key","second") )
          .element( new JSONObject().element("key","third") )
          .element( JSONArray.fromObject([1,2,3]) )
       JSONAssert.assertEquals( expected, actual )
    }

    public void testBuildObjectWithMaps(){
       def actual = builder.books {
          book = [title: "The Definitive Guide to Grails", author: "Graeme Rocher"]
          book = [title: "Groovy in Action", author: "Dierk Konig"]
       }
       def expected = new JSONObject()
          .element( "books", new JSONObject() )
       expected["books"].element( "book", new JSONObject()
          .element("title", "The Definitive Guide to Grails")
          .element("author", "Graeme Rocher") )
       expected["books"].element( "book", new JSONObject()
          .element("title", "Groovy in Action")
          .element("author", "Dierk Konig") )

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
             .element( "book", new JSONObject()
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
             .element( "book", new JSONObject()
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