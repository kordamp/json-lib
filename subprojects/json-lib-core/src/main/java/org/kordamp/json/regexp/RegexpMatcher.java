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
 * Abstraction for regexp handling.
 *
 * @author Andres Almiray
 */
public interface RegexpMatcher {
    /**
     * Returns the specified group if the string matches the Pattern.<br>
     * The Pattern will be managed internally by the RegexpMatcher
     * implementation.
     */
    String getGroupIfMatches(String str, int group);

    /**
     * Returns true is the string matches the Pattern.<br>
     * The Pattern will be managed internally by the RegexpMatcher
     * implementation.
     */
    boolean matches(String str);
}