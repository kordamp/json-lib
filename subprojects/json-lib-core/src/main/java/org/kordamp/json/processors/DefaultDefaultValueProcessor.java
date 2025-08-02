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
package org.kordamp.json.processors;

import org.kordamp.json.JSONArray;
import org.kordamp.json.JSONNull;
import org.kordamp.json.util.JSONUtils;

/**
 * Base implementation for DefaultDefaultValueProcessor.
 *
 * @author Andres Almiray
 */
public class DefaultDefaultValueProcessor implements DefaultValueProcessor {
    public Object getDefaultValue(Class type) {
        if (JSONUtils.isArray(type)) {
            return new JSONArray();
        } else if (JSONUtils.isNumber(type)) {
            if (JSONUtils.isDouble(type)) {
                return Double.valueOf(0);
            } else {
                return Integer.valueOf(0);
            }
        } else if (JSONUtils.isBoolean(type)) {
            return Boolean.FALSE;
        } else if (JSONUtils.isString(type)) {
            return "";
        }
        return JSONNull.getInstance();
    }
}