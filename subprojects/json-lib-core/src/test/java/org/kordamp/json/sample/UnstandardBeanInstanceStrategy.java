/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2020 the original author or authors.
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
package org.kordamp.json.sample;

import org.kordamp.json.JSONObject;
import org.kordamp.json.util.NewBeanInstanceStrategy;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Andres Almiray
 */
public class UnstandardBeanInstanceStrategy extends NewBeanInstanceStrategy {
    public Object newInstance(Class target, JSONObject source) throws InstantiationException,
        IllegalAccessException, SecurityException, NoSuchMethodException,
        InvocationTargetException {
        return new UnstandardBean(source.getInt("id"));
    }
}