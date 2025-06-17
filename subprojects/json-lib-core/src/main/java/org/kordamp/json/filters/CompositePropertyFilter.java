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
package org.kordamp.json.filters;

import org.kordamp.json.util.PropertyFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andres Almiray
 */
public class CompositePropertyFilter implements PropertyFilter {
    private List filters = new ArrayList();

    public CompositePropertyFilter() {
        this(null);
    }

    public CompositePropertyFilter(List filters) {
        if (filters != null) {
            for (Iterator i = filters.iterator(); i.hasNext(); ) {
                Object filter = i.next();
                if (filter instanceof PropertyFilter && !this.filters.contains(filter)) {
                    this.filters.add(filter);
                }
            }
        }
    }

    public void addPropertyFilter(PropertyFilter filter) {
        if (filter != null && !this.filters.contains(filter)) {
            filters.add(filter);
        }
    }

    public boolean apply(Object source, String name, Object value) {
        for (Iterator i = filters.iterator(); i.hasNext(); ) {
            PropertyFilter filter = (PropertyFilter) i.next();
            if (filter.apply(source, name, value)) {
                return true;
            }
        }
        return false;
    }

    public void removePropertyFilter(PropertyFilter filter) {
        if (filter != null) {
            filters.remove(filter);
        }
    }
}