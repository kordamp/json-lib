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

/**
 * @author Andres Almiray
 */
public class TestJSONNullAsJSON extends AbstractJSONTest {
    public TestJSONNullAsJSON(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONNullAsJSON.class);
    }

    protected int getIndent() {
        return 2;
    }

    protected int getIndentFactor() {
        return 2;
    }

    protected Object[] getIsArrayExpectations() {
        return new Object[]{Boolean.FALSE, JSONNull.getInstance()};
    }

    protected Object[] getToStringExpectations1() {
        return new Object[]{"null", JSONNull.getInstance()};
    }

    protected Object[] getToStringExpectations2() {
        return new Object[]{"null", JSONNull.getInstance()};
    }

    protected Object[] getToStringExpectations3() {
        return new Object[]{"  null", JSONNull.getInstance()};
    }

    protected Object[] getWriteExpectations() {
        return new Object[]{"null", JSONNull.getInstance()};
    }
}