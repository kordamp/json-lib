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

import org.kordamp.json.JSONException;

import java.io.IOException;
import java.io.Writer;


/**
 * JSONBuilder provides a quick and convenient way of producing JSON text. The
 * texts produced strictly conform to JSON syntax rules. No whitespace is added,
 * so the results are ready for transmission or storage. Each instance of
 * JSONWriter can produce one JSON text.
 * <p/>
 * A JSONBuilder instance provides a <code>value</code> method for appending
 * values to the text, and a <code>key</code> method for adding keys before
 * values in objects. There are <code>array</code> and <code>endArray</code>
 * methods that make and bound array values, and <code>object</code> and
 * <code>endObject</code> methods which make and bound object values. All of
 * these methods return the JSONBuilder instance, permitting a cascade style.
 * For example,
 * <p/>
 * <pre>
 * new JSONBuilder(myWriter)
 *     .object()
 *         .key("JSON")
 *         .value("Hello, World!")
 *     .endObject();</pre>
 * <p/>
 * which writes
 * <p/>
 * <pre>
 * {"JSON":"Hello, World!"}</pre>
 * <p/>
 * <p/>
 * The first method called must be <code>array</code> or <code>object</code>.
 * There are no methods for adding commas or colons. JSONBuilder adds them for
 * you. Objects and arrays can be nested up to 20 levels deep.
 * <p/>
 * This can sometimes be easier than using a JSONObject to build a string.
 *
 * @author JSON.org
 * @version 1
 */
public class JSONBuilder {
    private static final int MAXDEPTH = 20;
    /**
     * The current mode. Values: 'a' (array), 'd' (done), 'i' (initial), 'k'
     * (key), 'o' (object).
     */
    protected char mode;
    /**
     * The writer that will receive the output.
     */
    protected Writer writer;
    /**
     * The comma flag determines if a comma should be output before the next
     * value.
     */
    private boolean comma;
    /**
     * The object/array stack.
     */
    private char stack[];
    /**
     * The stack top index. A value of 0 indicates that the stack is empty.
     */
    private int top;

    /**
     * Make a fresh JSONBuilder. It can be used to build one JSON text.
     */
    public JSONBuilder(Writer w) {
        this.comma = false;
        this.mode = 'i';
        this.stack = new char[MAXDEPTH];
        this.top = 0;
        this.writer = w;
    }

    /**
     * Append a value.
     *
     * @param s A string value.
     *
     * @return this
     *
     * @throws JSONException If the value is out of sequence.
     */
    private JSONBuilder append(String s) {
        if (s == null) {
            throw new JSONException("Null pointer");
        }
        if (this.mode == 'o' || this.mode == 'a') {
            try {
                if (this.comma && this.mode == 'a') {
                    this.writer.write(',');
                }
                this.writer.write(s);
            } catch (IOException e) {
                throw new JSONException(e);
            }
            if (this.mode == 'o') {
                this.mode = 'k';
            }
            this.comma = true;
            return this;
        }
        throw new JSONException("Value out of sequence.");
    }

    /**
     * Begin appending a new array. All values until the balancing
     * <code>endArray</code> will be appended to this array. The
     * <code>endArray</code> method must be called to mark the array's end.
     *
     * @return this
     *
     * @throws JSONException If the nesting is too deep, or if the object is
     *                       started in the wrong place (for example as a key or after the end
     *                       of the outermost array or object).
     */
    public JSONBuilder array() {
        if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
            this.push('a');
            this.append("[");
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }

    /**
     * End something.
     *
     * @param m Mode
     * @param c Closing character
     *
     * @return this
     *
     * @throws JSONException If unbalanced.
     */
    private JSONBuilder end(char m, char c) {
        if (this.mode != m) {
            throw new JSONException(m == 'o' ? "Misplaced endObject." : "Misplaced endArray.");
        }
        this.pop(m);
        try {
            this.writer.write(c);
        } catch (IOException e) {
            throw new JSONException(e);
        }
        this.comma = true;
        return this;
    }

    /**
     * End an array. This method most be called to balance calls to
     * <code>array</code>.
     *
     * @return this
     *
     * @throws JSONException If incorrectly nested.
     */
    public JSONBuilder endArray() {
        return this.end('a', ']');
    }

    /**
     * End an object. This method most be called to balance calls to
     * <code>object</code>.
     *
     * @return this
     *
     * @throws JSONException If incorrectly nested.
     */
    public JSONBuilder endObject() {
        return this.end('k', '}');
    }

    /**
     * Append a key. The key will be associated with the next value. In an
     * object, every value must be preceded by a key.
     *
     * @param s A key string.
     *
     * @return this
     *
     * @throws JSONException If the key is out of place. For example, keys do not
     *                       belong in arrays or if the key is null.
     */
    public JSONBuilder key(String s) {
        if (s == null) {
            throw new JSONException("Null key.");
        }
        if (this.mode == 'k') {
            try {
                if (this.comma) {
                    this.writer.write(',');
                }
                this.writer.write(JSONUtils.quote(s));
                this.writer.write(':');
                this.comma = false;
                this.mode = 'o';
                return this;
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        throw new JSONException("Misplaced key.");
    }

    /**
     * Begin appending a new object. All keys and values until the balancing
     * <code>endObject</code> will be appended to this object. The
     * <code>endObject</code> method must be called to mark the object's end.
     *
     * @return this
     *
     * @throws JSONException If the nesting is too deep, or if the object is
     *                       started in the wrong place (for example as a key or after the end
     *                       of the outermost array or object).
     */
    public JSONBuilder object() {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        if (this.mode == 'o' || this.mode == 'a') {
            this.append("{");
            this.push('k');
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced object.");

    }

    /**
     * Pop an array or object scope.
     *
     * @param c The scope to close.
     *
     * @throws JSONException If nesting is wrong.
     */
    private void pop(char c) {
        if (this.top <= 0 || this.stack[this.top - 1] != c) {
            throw new JSONException("Nesting error.");
        }
        this.top -= 1;
        this.mode = this.top == 0 ? 'd' : this.stack[this.top - 1];
    }

    /**
     * Push an array or object scope.
     *
     * @param c The scope to open.
     *
     * @throws JSONException If nesting is too deep.
     */
    private void push(char c) {
        if (this.top >= MAXDEPTH) {
            throw new JSONException("Nesting too deep.");
        }
        this.stack[this.top] = c;
        this.mode = c;
        this.top += 1;
    }

    /**
     * Append either the value <code>true</code> or the value
     * <code>false</code>.
     *
     * @param b A boolean.
     *
     * @return this
     *
     * @throws JSONException
     */
    public JSONBuilder value(boolean b) {
        return this.append(b ? "true" : "false");
    }

    /**
     * Append a double value.
     *
     * @param d A double.
     *
     * @return this
     *
     * @throws JSONException If the number is not finite.
     */
    public JSONBuilder value(double d) {
        return this.value(new Double(d));
    }

    /**
     * Append a long value.
     *
     * @param l A long.
     *
     * @return this
     *
     * @throws JSONException
     */
    public JSONBuilder value(long l) {
        return this.append(Long.toString(l));
    }

    /**
     * Append an object value.
     *
     * @param o The object to append. It can be null, or a Boolean, Number,
     *          String, JSONObject, or JSONArray, or an object with a
     *          toJSONString() method.
     *
     * @return this
     *
     * @throws JSONException If the value is out of sequence.
     */
    public JSONBuilder value(Object o) {
        return this.append(JSONUtils.valueToString(o));
    }
}
