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

import org.apache.commons.beanutils.PropertyUtils;
import org.kordamp.json.sample.ObjectBean;

/**
 * @author Andres Almiray
 */
public class TestJSONObjectStaticBuilders_ObjectBean extends
    AbstractJSONObjectStaticBuildersTestCase {
    public TestJSONObjectStaticBuilders_ObjectBean(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONObjectStaticBuilders_ObjectBean.class);
    }

    protected Object getSource() {
        ObjectBean bean = new ObjectBean();
        String[] props = getProperties();
        try {
            for (int i = 0; i < props.length; i++) {
                PropertyUtils.setProperty(bean, props[i],
                    PropertyConstants.getPropertyValue(props[i]));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bean;
    }
}