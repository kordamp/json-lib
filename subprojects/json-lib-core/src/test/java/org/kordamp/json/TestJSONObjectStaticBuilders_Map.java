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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Andres Almiray
 */
public class TestJSONObjectStaticBuilders_Map extends AbstractJSONObjectStaticBuildersTestCase {
    public TestJSONObjectStaticBuilders_Map(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONObjectStaticBuilders_Map.class);
    }

    protected Object getSource() {
        Map map = new HashMap();
        String[] props = getProperties();
        for (int i = 0; i < props.length; i++) {
            map.put(props[i], PropertyConstants.getPropertyValue(props[i]));
        }
        //map.put("class", "");
        map.put("pexcluded", "");
        return map;
    }
}