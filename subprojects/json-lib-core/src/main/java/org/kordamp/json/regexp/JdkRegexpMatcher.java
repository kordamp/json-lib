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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JDK 1.4+ RegexpMatcher implementation.
 *
 * @author Andres Almiray
 */
public class JdkRegexpMatcher implements RegexpMatcher {
    private final Pattern pattern;

    public JdkRegexpMatcher(String pattern) {
        this(pattern, false);
    }

    public JdkRegexpMatcher(String pattern, boolean multiline) {
        if (multiline) {
            this.pattern = Pattern.compile(pattern, Pattern.MULTILINE);
        } else {
            this.pattern = Pattern.compile(pattern);
        }
    }

    public String getGroupIfMatches(String str, int group) {
        Matcher matcher = pattern.matcher(str);
        if (matcher.matches()) {
            return matcher.group(group);
        }
        return "";
    }

    public boolean matches(String str) {
        return pattern.matcher(str).matches();
    }
}