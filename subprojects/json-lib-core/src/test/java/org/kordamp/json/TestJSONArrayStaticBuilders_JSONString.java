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
public class TestJSONArrayStaticBuilders_JSONString extends AbstractJSONArrayStaticBuildersTestCase {
    public TestJSONArrayStaticBuilders_JSONString(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONArrayStaticBuilders_JSONString.class);
    }

    protected Object getSource() {
        return new JsonBean();
    }

    public static class JsonBean implements JSONString {
        public String toJSONString() {
            return "[{\"parray\":[1,2],\"plong\":9223372036854775807,\"pchar\":\"J\",\"pboolean\":true,\"pfloat\":3.4028234663852886E38,\"pbean\":{\"parray\":null,\"plong\":null,\"pchar\":null,\"pboolean\":null,\"pfloat\":null,\"pbean\":null,\"pshort\":null,\"pdouble\":null,\"pclass\":null,\"pstring\":null,\"pint\":null,\"plist\":null,\"pfunction\":null,\"pmap\":null,\"pbyte\":null},\"pshort\":32767,\"pdouble\":1.7976931348623157E308,\"pclass\":\"java.lang.Object\",\"pstring\":\"json\",\"pint\":2147483647,\"plist\":[\"a\",\"b\"],\"pfunction\":function(){ this; },\"pmap\":null,\"pbyte\":127,\"class\":\"java.lang.Object\",\"pexcluded\":\"\"}]";
        }
    }
}