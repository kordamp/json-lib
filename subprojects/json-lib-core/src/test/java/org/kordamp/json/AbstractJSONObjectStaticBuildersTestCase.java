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
package org.kordamp.json;

import junit.framework.TestCase;

/**
 * @author Andres Almiray
 */
public abstract class AbstractJSONObjectStaticBuildersTestCase extends TestCase {
    public AbstractJSONObjectStaticBuildersTestCase(String testName) {
        super(testName);
    }

    public void testFromObject() {
        JSONObject json = JSONObject.fromObject(getSource());
        assertJSONObject(json, getProperties());
        assertTrue(!json.has("class"));
    }

    public void testFromObject_excludes() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(getExclusions());
        JSONObject json = JSONObject.fromObject(getSource(), jsonConfig);
        assertJSONObject(json, getProperties());
        String[] excluded = getExclusions();
        for (int i = 0; i < excluded.length; i++) {
            assertTrue(!json.has(excluded[i]));
        }
        assertTrue(!json.has("class"));
        assertTrue(!json.has("pexcluded"));
    }

    public void testFromObject_excludes_ignoreDefaults() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(getExclusions());
        jsonConfig.setIgnoreDefaultExcludes(true);
        JSONObject json = JSONObject.fromObject(getSource(), jsonConfig);
        assertJSONObject(json, getProperties());
        assertTrue(json.has("class"));
        assertTrue(!json.has("pexcluded"));
    }

    protected String[] getExclusions() {
        return new String[]{"pexcluded"};
    }

    protected String[] getProperties() {
        return PropertyConstants.getProperties();
    }

    protected abstract Object getSource();

    private void assertJSONObject(JSONObject json, String[] properties) {
        assertNotNull(json);
        for (int i = 0; i < properties.length; i++) {
            assertTrue(json.has(properties[i]));
        }
    }
}