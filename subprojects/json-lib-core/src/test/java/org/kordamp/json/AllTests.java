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

import junit.framework.TestSuite;

/**
 * @author Andres Almiray
 */
public class AllTests extends TestSuite {
    public static TestSuite suite() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setName("core");

        suite.addTest(new TestSuite(TestJSONObjectStaticBuilders_ObjectBean.class));
        suite.addTest(new TestSuite(TestJSONObjectStaticBuilders_PrimitiveBean.class));
        suite.addTest(new TestSuite(TestJSONObjectStaticBuilders_DynaBean.class));
        suite.addTest(new TestSuite(TestJSONObjectStaticBuilders_JSONObject.class));
        suite.addTest(new TestSuite(TestJSONObjectStaticBuilders_JSONString.class));
        suite.addTest(new TestSuite(TestJSONObjectStaticBuilders_JSONTokener.class));
        suite.addTest(new TestSuite(TestJSONObjectStaticBuilders_Map.class));
        suite.addTest(new TestSuite(TestJSONObjectStaticBuilders_String.class));

        suite.addTest(new TestSuite(TestJSONArrayStaticBuilders_Array_DynaBean.class));
        suite.addTest(new TestSuite(TestJSONArrayStaticBuilders_Collection_DynaBean.class));
        suite.addTest(new TestSuite(TestJSONArrayStaticBuilders_JSONString.class));
        suite.addTest(new TestSuite(TestJSONArrayStaticBuilders_String.class));

        suite.addTest(new TestSuite(TestJSONArray.class));
        suite.addTest(new TestSuite(TestJSONArrayAsJSON.class));
        suite.addTest(new TestSuite(TestJSONArrayAsList.class));
        suite.addTest(new TestSuite(TestJSONArrayCollections.class));
        suite.addTest(new TestSuite(TestJSONArrayEqualsHashCodeCompareTo.class));
        suite.addTest(new TestSuite(TestJSONArrayEvents.class));
        suite.addTest(new TestSuite(TestJSONArrayJdk15.class));
        suite.addTest(new TestSuite(TestJSONFunction.class));
        suite.addTest(new TestSuite(TestJSONNullAsJSON.class));
        suite.addTest(new TestSuite(TestJSONObject.class));
        suite.addTest(new TestSuite(TestJSONObjectAsMap.class));
        suite.addTest(new TestSuite(TestJSONObjectEqualsHashCodeCompareTo.class));
        suite.addTest(new TestSuite(TestJSONObjectEvents.class));
        suite.addTest(new TestSuite(TestJSONObjectJdk15.class));
        suite.addTest(new TestSuite(TestJSONObjectWithProcessors.class));
        suite.addTest(new TestSuite(TestJSONSerializer.class));

        suite.addTest(new TestSuite(TestUserSubmitted.class));

        return suite;
    }
}
