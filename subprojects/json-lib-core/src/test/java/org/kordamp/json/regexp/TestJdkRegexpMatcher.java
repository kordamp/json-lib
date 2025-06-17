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
package org.kordamp.json.regexp;

/**
 * @author Andres Almiray
 */
public class TestJdkRegexpMatcher extends AbstractRegexpMatcherTestCase {
    public TestJdkRegexpMatcher(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJdkRegexpMatcher.class);
    }

    protected RegexpMatcher getRegexpMatcher(String pattern) {
        return new JdkRegexpMatcher(pattern);
    }
}