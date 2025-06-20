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

import java.io.StringWriter;

/**
 * JSONStringer provides a quick and convenient way of producing JSON text. The
 * texts produced strictly conform to JSON syntax rules. No whitespace is added,
 * so the results are ready for transmission or storage. Each instance of
 * JSONStringer can produce one JSON text.
 * <p/>
 * A JSONStringer instance provides a <code>value</code> method for appending
 * values to the text, and a <code>key</code> method for adding keys before
 * values in objects. There are <code>array</code> and <code>endArray</code>
 * methods that make and bound array values, and <code>object</code> and
 * <code>endObject</code> methods which make and bound object values. All of
 * these methods return the JSONWriter instance, permitting cascade style. For
 * example,
 * <p/>
 * <pre>
 * myString = new JSONStringer()
 *     .object()
 *         .key("JSON")
 *         .value("Hello, World!")
 *     .endObject()
 *     .toString();</pre>
 * <p/>
 * which produces the string
 * <p/>
 * <pre>
 * {"JSON":"Hello, World!"}</pre>
 * <p/>
 * <p/>
 * The first method called must be <code>array</code> or <code>object</code>.
 * There are no methods for adding commas or colons. JSONStringer adds them for
 * you. Objects and arrays can be nested up to 20 levels deep.
 * <p/>
 * This can sometimes be easier than using a JSONObject to build a string.
 *
 * @author JSON.org
 * @version 2
 */
public class JSONStringer extends JSONBuilder {
    /**
     * Make a fresh JSONStringer. It can be used to build one JSON text.
     */
    public JSONStringer() {
        super(new StringWriter());
    }

    /**
     * Return the JSON text. This method is used to obtain the product of the
     * JSONStringer instance. It will return <code>null</code> if there was a
     * problem in the construction of the JSON text (such as the calls to
     * <code>array</code> were not properly balanced with calls to
     * <code>endArray</code>).
     *
     * @return The JSON text.
     */
    public String toString() {
        return this.mode == 'd' ? this.writer.toString() : null;
    }
}
