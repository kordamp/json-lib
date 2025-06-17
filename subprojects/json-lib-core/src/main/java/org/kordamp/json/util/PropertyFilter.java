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
package org.kordamp.json.util;

/**
 * @author Andres Almiray
 */
public interface PropertyFilter {
    /**
     * @param source the owner of the property
     * @param name   the name of the property
     * @param value  the value of the property
     *
     * @return true if the property will be filtered out, false otherwise
     */
    boolean apply(Object source, String name, Object value);
}