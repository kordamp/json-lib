/*
 * Copyright 2006-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This product includes software developed by Douglas Crockford
 * (http://www.crockford.com) and released under the Apache Software
 * License version 2.0 in 2006.
 */
import junit.framework.TestCase;
import org.kordamp.json.JSONObject;
import org.kordamp.json.JsonConfig;

/**
 * @author Kohsuke Kawaguchi
 */
public class FieldBindingTest extends TestCase {
    public final class Foo {
        public int x;
        public String y;
    }

    public void test1() throws Exception {
        Foo f = new Foo();
        f.x = 5;
        f.y = "test";
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setIgnorePublicFields(false);
        JSONObject o = JSONObject.fromObject(f, jsonConfig);
        assertEquals(o.getInt("x"), 5);
        assertEquals(o.getString("y"), "test");
        assertEquals(o.size(), 2);
    }

    private final class Bar {
        public int x;
        public String y;
    }

    public void test2() throws Exception {
        // Property introspection is not capable of reading properties
        // from a private member class, even if they are defined as
        // public fields, thus the returned JSONObject must be empty

        Bar f = new Bar();
        f.x = 5;
        f.y = "test";
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setIgnorePublicFields(false);
        JSONObject o = JSONObject.fromObject(f, jsonConfig);
        assertTrue(o.isEmpty());
        // assertEquals(o.getInt("x"), 5);
        // assertEquals(o.getString("y"), "test");
        // assertEquals(o.size(), 2);
    }
}
