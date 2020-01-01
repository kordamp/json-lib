/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2020 the original author or authors.
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
package org.kordamp.json.processors;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Andres Almiray
 */
public class TestJsonBeanProcessorMatcher extends TestCase {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJsonBeanProcessorMatcher.class);
    }

    public void testGetMatchUsingStartsWith() {
        Set set = new HashSet();
        set.add(JsonBeanProcessorMatcher.class);
        set.add(JsonBeanProcessorMatcher.DEFAULT.getClass());

        JsonBeanProcessorMatcher matcher = new StartsWithJsonBeanProcessorMatcher(
            "org.kordamp.json.processors.JsonBeanProcessorMatcher$");
        assertEquals(JsonBeanProcessorMatcher.DEFAULT.getClass(), matcher.getMatch(
            JsonBeanProcessorMatcher.DEFAULT.getClass(), set));
    }
}