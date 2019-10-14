/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2019 the original author or authors.
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
public class TestDefaultJsonBeanProcessorMatcher extends TestCase {
    public void testDefault() {
        JsonBeanProcessorMatcher matcher = JsonBeanProcessorMatcher.DEFAULT;

        assertNull(matcher.getMatch(null, null));
        assertNull(matcher.getMatch(Long.class, null));
        assertNull(matcher.getMatch(null, new HashSet()));
        assertNull(matcher.getMatch(Long.class, new HashSet()));
        Set set = new HashSet();
        set.add(Long.class);
        assertNotNull(matcher.getMatch(Long.class, set));
        assertEquals(Long.class, matcher.getMatch(Long.class, set));
    }
}
