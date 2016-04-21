/*
 * Copyright 2006-2016 the original author or authors.
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
package org.kordamp.json.bugs;

import junit.framework.TestCase;
import org.kordamp.json.JSONObject;
import org.kordamp.json.JsonConfig;

import java.util.ArrayList;
import java.util.List;

public class TestBug2843623 extends TestCase {
    public void testSample() {
        Container orig = new Container();
        List<String> strings = new ArrayList<String>();
        strings.add("one");
        strings.add("two");
        strings.add("three");
        orig.setMyList(strings);

        JSONObject jobj = JSONObject.fromObject(orig);
        Container root = new Container();
        Container res = (Container) JSONObject.toBean(jobj, root, new JsonConfig());
        assertTrue(res.getMyList().size() > 0);
        assertTrue(root.getMyList().size() > 0);
    }

    public static class Container {
        private List<String> myList = new ArrayList<String>();

        public List<String> getMyList() {
            return myList;
        }

        public void setMyList(List<String> strings) {
            this.myList.addAll(strings);
        }
    }
}
