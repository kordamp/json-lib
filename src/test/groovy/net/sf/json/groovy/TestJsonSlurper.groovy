/*
 * Copyright 2002-2013 the original author or authors.
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

import net.sf.json.JSON
import net.sf.json.JSONObject
import net.sf.json.test.JSONAssert
import org.apache.commons.httpclient.HttpClient
import org.apache.commons.httpclient.methods.GetMethod

/**
 * @author Andres Almiray
 */
class TestJsonSlurper extends GroovyTestCase {

    private JSONObject expected

    protected void setUp() throws Exception {
        GJson.enhanceClasses()
        expected = new JSONObject().element('books', [])
        expected.books << [name: 'Groovy in Action', author: 'Dierk Koenig']
        expected.books << [name: 'The Definitive Guide to Grails', author: 'Graeme Rocher']
        expected.books << [name: 'Groov Recipes, greasing the wheels of Java', author: 'Scott Davis']
    }

    void testParseFile() {
        File file = new File(new File(".").absolutePath, "src/test/resources/net/sf/json/groovy/sample.json")
        JSON actual = new JsonSlurper().parse(file)
        JSONAssert.assertEquals(expected, actual)
    }

    void testParseURL() {
        File file = new File(new File(".").absolutePath, "src/test/resources/net/sf/json/groovy/sample.json")
        JSON actual = new JsonSlurper().parse(file.toURL())
        JSONAssert.assertEquals(expected, actual)
    }

    void testParseInputStream() {
        File file = new File(new File(".").absolutePath, "src/test/resources/net/sf/json/groovy/sample.json")
        FileInputStream stream = new FileInputStream(file)
        JSON actual = new JsonSlurper().parse(stream)
        JSONAssert.assertEquals(expected, actual)
    }

    void testParseUri() {
        File file = new File(new File(".").absolutePath, "src/test/resources/net/sf/json/groovy/sample.json")
        JSON actual = new JsonSlurper().parse("file://" + file.absolutePath)
        JSONAssert.assertEquals(expected, actual)
    }

    void testParseReader() {
        File file = new File(new File(".").absolutePath, "src/test/resources/net/sf/json/groovy/sample.json")
        JSON actual = new JsonSlurper().parse(new FileReader(file))
        JSONAssert.assertEquals(expected, actual)
    }

    void testParsetext() {
        File file = new File(new File(".").absolutePath, "src/test/resources/net/sf/json/groovy/sample.json")
        JSON actual = new JsonSlurper().parseText(file.text)
        JSONAssert.assertEquals(expected, actual)
    }

    void testParseReader_liveUrl() {
        HttpClient http = new HttpClient()
        GetMethod get = new GetMethod("http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=Calvin%20and%20Hobbes")
        int resultCode = http.executeMethod(get)
        if (resultCode != 200) {
            fail("Http GET http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=Calvin%20and%20Hobbes returned with code $resultCode")
        }
        Reader reader = new InputStreamReader(get.responseStream, "utf-8")
        JSON actual = new JsonSlurper().parse(reader)
        assertTrue(actual.has("responseData"))
    }
}