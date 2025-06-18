/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kordamp.json;

/**
 * @author Andres Almiray
 */
public class TestJSONObjectStaticBuilders_JSONObject extends
    AbstractJSONObjectStaticBuildersTestCase {
    public TestJSONObjectStaticBuilders_JSONObject(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONObjectStaticBuilders_JSONObject.class);
    }

    protected Object getSource() {
        String[] props = getProperties();
        JSONObject jsonObject = new JSONObject();
        try {
            for (int i = 0; i < props.length; i++) {
                jsonObject.element(props[i], PropertyConstants.getPropertyValue(props[i]));
            }
            //jsonObject.element("class", Object.class);
            jsonObject.element("pexcluded", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return jsonObject;
    }
}