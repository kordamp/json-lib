/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2019 Andres Almiray.
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
package org.kordamp.json.filters;

import junit.framework.TestSuite;

/**
 * @author Andres Almiray
 */
public class AllTests extends TestSuite {
    public static TestSuite suite() throws Exception {
        TestSuite suite = new TestSuite();
        suite.setName("filters");

        suite.addTest(new TestSuite(TestAndPropertyFilter.class));
        suite.addTest(new TestSuite(TestCompositePropertyFilter.class));
        suite.addTest(new TestSuite(TestNotPropertyFilter.class));
        suite.addTest(new TestSuite(TestOrPropertyFilter.class));
        suite.addTest(new TestSuite(TestMappingPropertyFilter.class));

        return suite;
    }
}