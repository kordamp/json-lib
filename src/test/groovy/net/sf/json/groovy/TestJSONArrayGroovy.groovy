/*
 * Copyright 2002-2009 the original author or authors.
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
class TestJSONArrayGroovy extends GroovyTestCase {
    protected void setUp() throws Exception {
       GJson.enhanceClasses()
    }

    public void testEqualsOperator(){
       def json = new JSONArray().element(1)
       assertTrue json == json
    }

    public void testLessThanOperator(){
       def json1 = new JSONArray().element(1)
       def json2 = new JSONArray().element(1).element(2)
       assertTrue json1 < json2
    }

    public void testGreaterThanOperator(){
       def json1 = new JSONArray().element(1)
       def json2 = new JSONArray().element(1).element(2)
       assertTrue json2 > json1
    }

    public void testSpaceshipOperator(){
       def json1 = new JSONArray().element(1)
       def json2 = new JSONArray().element(1).element(2)
       assertTrue 0 != (json1 <=> json2)
    }
}