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
 * The JSONException is thrown when things are amiss.
 *
 * @author JSON.org
 * @version 4
 */
public class JSONException extends RuntimeException {
    private static final long serialVersionUID = -359505426467944084L;

    public JSONException() {
        super();
    }

    public JSONException(String msg) {
        super(msg, null);
    }

    public JSONException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JSONException(Throwable cause) {
        super((cause == null ? null : cause.toString()), cause);
    }
}