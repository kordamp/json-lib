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
import org.kordamp.json.JSONArray;
import org.kordamp.json.test.JSONAssert;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Andres Almiray
 */
public class TestDefaultValueProcessorMatcher extends TestCase {
    public void testDefault() {
        DefaultValueProcessorMatcher matcher = DefaultValueProcessorMatcher.DEFAULT;

        assertNull(matcher.getMatch(null, null));
        assertNull(matcher.getMatch(Long.class, null));
        assertNull(matcher.getMatch(null, new HashSet()));
        assertNull(matcher.getMatch(Long.class, new HashSet()));
        Set set = new HashSet();
        set.add(Long.class);
        assertNotNull(matcher.getMatch(Long.class, set));
        assertEquals(Long.class, matcher.getMatch(Long.class, set));
    }

    public void testDefaultValues() {
        DefaultDefaultValueProcessor processor = new DefaultDefaultValueProcessor();
        JSONAssert.assertEquals(new JSONArray(), processor.getDefaultValue(List.class));
        JSONAssert.assertEquals(new JSONArray(), processor.getDefaultValue(Set.class));
        JSONAssert.assertEquals(new JSONArray(), processor.getDefaultValue(Collection.class));
        assertEquals(0d, (Double) processor.getDefaultValue(Double.TYPE), 0d);
        assertEquals(0d, (Double) processor.getDefaultValue(Double.class), 0d);
        assertEquals(0, processor.getDefaultValue(Float.class));
        assertEquals(0, processor.getDefaultValue(Float.TYPE));
        assertEquals(0, processor.getDefaultValue(Byte.class));
        assertEquals(0, processor.getDefaultValue(Byte.TYPE));
        assertEquals(0, processor.getDefaultValue(Short.class));
        assertEquals(0, processor.getDefaultValue(Short.TYPE));
        assertEquals(0, processor.getDefaultValue(Integer.class));
        assertEquals(0, processor.getDefaultValue(Integer.TYPE));
        assertEquals(0, processor.getDefaultValue(Long.class));
        assertEquals(0, processor.getDefaultValue(Long.TYPE));
        assertEquals(false, processor.getDefaultValue(Boolean.class));
        assertEquals(false, processor.getDefaultValue(Boolean.TYPE));
    }
}
