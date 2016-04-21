/*
 * Copyright 2006-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This product includes software developed by Douglas Crockford
 * (http://www.crockford.com) and released under the Apache Software
 * License version 2.0 in 2006.
 */
package org.kordamp.json;

import org.kordamp.ezmorph.bean.MorphDynaBean;
import org.kordamp.ezmorph.bean.MorphDynaClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Andres Almiray
 */
public class TestJSONArrayStaticBuilders_Collection_DynaBean extends
    AbstractJSONArrayStaticBuildersTestCase {
    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONArrayStaticBuilders_Collection_DynaBean.class);
    }

    public TestJSONArrayStaticBuilders_Collection_DynaBean(String name) {
        super(name);
    }

    protected Object getSource() {
        Map map = new HashMap();
        String[] props = getProperties();
        for (int i = 0; i < props.length; i++) {
            map.put(props[i], PropertyConstants.getPropertyClass(props[i]));
        }
        map.put("class", Class.class);
        map.put("pexcluded", String.class);
        MorphDynaClass dynaClass = new MorphDynaClass(map);
        MorphDynaBean dynaBean = null;
        try {
            dynaBean = (MorphDynaBean) dynaClass.newInstance();
            for (int i = 0; i < props.length; i++) {
                dynaBean.set(props[i], PropertyConstants.getPropertyValue(props[i]));
            }
            dynaBean.set("class", Object.class);
            dynaBean.set("pexcluded", "");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        List list = new ArrayList();
        list.add(dynaBean);
        return list;
    }
}