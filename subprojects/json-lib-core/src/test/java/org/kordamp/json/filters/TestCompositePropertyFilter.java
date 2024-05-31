/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2024 the original author or authors.
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

import junit.framework.TestCase;
import org.kordamp.json.util.PropertyFilter;

import java.util.Arrays;
import java.util.List;

/**
 * @author Andres Almiray
 */
public class TestCompositePropertyFilter extends TestCase {
    private CompositePropertyFilter filter;

    public TestCompositePropertyFilter(String testName) {
        super(testName);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestCompositePropertyFilter.class);
    }

    public void testApply_addFilter() {
        filter.addPropertyFilter(new FalsePropertyFilter());
        assertFalse(filter.apply(null, null, null));
        filter.addPropertyFilter(new TruePropertyFilter());
        assertTrue(filter.apply(null, null, null));
    }

    public void testConstructor() {
        PropertyFilter filter1 = new FalsePropertyFilter();
        PropertyFilter filter2 = new FalsePropertyFilter();
        PropertyFilter filter3 = new TruePropertyFilter();
        List filters = Arrays.asList(filter1, filter2, filter3, filter3, new Object());

        filter = new CompositePropertyFilter(filters);
        assertTrue(filter.apply(null, null, null));

        filter.addPropertyFilter(filter1);
        assertTrue(filter.apply(null, null, null));

        filter.addPropertyFilter(null);
        assertTrue(filter.apply(null, null, null));

        filter.removePropertyFilter(filter3);
        assertFalse(filter.apply(null, null, null));

        filter.removePropertyFilter(null);
        assertFalse(filter.apply(null, null, null));
    }

    protected void setUp() throws Exception {
        filter = new CompositePropertyFilter();
    }
}