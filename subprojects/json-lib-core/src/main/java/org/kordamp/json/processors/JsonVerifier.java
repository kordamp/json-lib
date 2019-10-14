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

import org.kordamp.json.JSON;
import org.kordamp.json.JSONFunction;
import org.kordamp.json.JSONNull;
import org.kordamp.json.JSONString;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Verifies if a value is a valid JSON value.
 *
 * @author Andres Almiray
 */
public final class JsonVerifier {

    /**
     * Verifies if value is a valid JSON value.
     *
     * @param value the value to verify
     */
    public static boolean isValidJsonValue(Object value) {
        if (JSONNull.getInstance()
            .equals(value) || value instanceof JSON || value instanceof Boolean
            || value instanceof Byte || value instanceof Short
            || value instanceof Integer || value instanceof Long || value instanceof Double
            || value instanceof BigInteger || value instanceof BigDecimal
            || value instanceof JSONFunction || value instanceof JSONString
            || value instanceof String) {
            return true;
        }
        return false;
    }
}