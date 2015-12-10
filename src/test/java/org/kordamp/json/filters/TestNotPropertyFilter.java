/*
 * Copyright 2006-2015 the original author or authors.
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
package org.kordamp.json.filters;

import junit.framework.TestCase;

/**
 * @author Andres Almiray
 */
public class TestNotPropertyFilter extends TestCase {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestNotPropertyFilter.class);
    }

    public TestNotPropertyFilter(String testName) {
        super(testName);
    }

    public void testApply_true() {
        NotPropertyFilter filter = new NotPropertyFilter(new TruePropertyFilter());
        assertFalse(filter.apply(null, null, null));
    }

    public void testApply_false() {
        NotPropertyFilter filter = new NotPropertyFilter(new FalsePropertyFilter());
        assertTrue(filter.apply(null, null, null));
    }
}