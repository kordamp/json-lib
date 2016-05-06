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
package org.kordamp.json.processors;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Andres Almiray
 */
public class TestDefaultJsonValueProcessorMatcher extends TestCase {
    public void testDefault() {
        JsonValueProcessorMatcher matcher = JsonValueProcessorMatcher.DEFAULT;

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
