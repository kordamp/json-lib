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

import java.util.HashMap;
import java.util.List;

/**
 * @author Andres Almiray
 */
public class MappedBean extends HashMap {
    private static final long serialVersionUID = -3372716996620968313L;

    public List getList() {
        return (List) get("list");
    }

    public void setList(List list) {
        put("list", list);
    }

    public String getName() {
        return (String) get("name");
    }

    public void setName(String name) {
        put("name", name);
    }
}