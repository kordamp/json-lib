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

import junit.framework.TestCase;
import org.kordamp.json.processors.JsDateJsonBeanProcessor;
import org.kordamp.json.processors.JsDateJsonValueProcessor;
import org.kordamp.json.sample.DateBean;
import org.kordamp.json.sample.IdentityJsonValueProcessor;
import org.kordamp.json.test.JSONAssert;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andres Almiray
 */
public class TestJSONObjectWithProcessors extends TestCase {
    private Date date;
    private JsonConfig jsonConfig;
    public TestJSONObjectWithProcessors(String name) {
        super(name);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONObjectWithProcessors.class);
    }

    public void testBeanWithDateProperty_fromObject_withJsonBeanProcessor() {
        DateBean bean = new DateBean();
        bean.setDate(date);
        bean.setValue(42);
        jsonConfig.registerJsonBeanProcessor(Date.class, new JsDateJsonBeanProcessor());
        JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);
        assertNotNull(jsonObject);
        assertEquals(42, jsonObject.getInt("value"));
        JSONObject jsDate = jsonObject.getJSONObject("date");
        assertJsDate(jsDate);
    }

    public void testBeanWithDateProperty_fromObject_withJsonValueProcessor_1() {
        DateBean bean = new DateBean();
        bean.setDate(date);
        bean.setValue(42);
        jsonConfig.registerJsonValueProcessor(DateBean.class, Date.class,
            new JsDateJsonValueProcessor());
        JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);
        assertNotNull(jsonObject);
        assertEquals(42, jsonObject.getInt("value"));
        JSONObject jsDate = jsonObject.getJSONObject("date");
        assertJsDate(jsDate);
    }

    public void testBeanWithDateProperty_fromObject_withJsonValueProcessor_2() {
        DateBean bean = new DateBean();
        bean.setDate(date);
        bean.setValue(42);
        jsonConfig.registerJsonValueProcessor(DateBean.class, "date", new JsDateJsonValueProcessor());
        JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);
        assertNotNull(jsonObject);
        assertEquals(42, jsonObject.getInt("value"));
        JSONObject jsDate = jsonObject.getJSONObject("date");
        assertJsDate(jsDate);
    }

    public void testBeanWithDateProperty_fromObject_withJsonValueProcessor_3() {
        DateBean bean = new DateBean();
        bean.setDate(date);
        bean.setValue(42);
        jsonConfig.registerJsonValueProcessor(Date.class, new JsDateJsonValueProcessor());
        JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);
        assertNotNull(jsonObject);
        assertEquals(42, jsonObject.getInt("value"));
        JSONObject jsDate = jsonObject.getJSONObject("date");
        assertJsDate(jsDate);
    }

    public void testBeanWithDateProperty_fromObject_withJsonValueProcessor_4() {
        DateBean bean = new DateBean();
        bean.setDate(date);
        bean.setValue(42);
        jsonConfig.registerJsonValueProcessor("date", new JsDateJsonValueProcessor());
        JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);
        assertNotNull(jsonObject);
        assertEquals(42, jsonObject.getInt("value"));
        JSONObject jsDate = jsonObject.getJSONObject("date");
        assertJsDate(jsDate);
    }

    public void testBeanWithDateProperty_put() {
        DateBean bean = new DateBean();
        bean.setValue(42);
        jsonConfig.registerJsonBeanProcessor(Date.class, new JsDateJsonBeanProcessor());
        JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);
        assertNotNull(jsonObject);
        assertEquals(42, jsonObject.getInt("value"));
        JSONObject jsDate = jsonObject.getJSONObject("date");
        JSONAssert.assertNull(jsDate);
        jsonObject.element("date", date, jsonConfig);
        jsDate = jsonObject.getJSONObject("date");
        assertJsDate(jsDate);
    }

    public void testDateAsBean_fromObject() {
        jsonConfig.registerJsonBeanProcessor(Date.class, new JsDateJsonBeanProcessor());
        JSONObject jsDate = JSONObject.fromObject(date, jsonConfig);
        assertJsDate(jsDate);
    }

    public void testNumericValueWithProcessor_Byte() {
        Map bean = new HashMap();
        bean.put("value", Byte.valueOf(Byte.MAX_VALUE));
        jsonConfig.registerJsonValueProcessor("value", new IdentityJsonValueProcessor());
        JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);
        assertNotNull(jsonObject);
        assertEquals(Integer.valueOf(Byte.MAX_VALUE), jsonObject.get("value"));
    }

    public void testNumericValueWithProcessor_Short() {
        Map bean = new HashMap();
        bean.put("value", Short.valueOf(Short.MAX_VALUE));
        jsonConfig.registerJsonValueProcessor("value", new IdentityJsonValueProcessor());
        JSONObject jsonObject = JSONObject.fromObject(bean, jsonConfig);
        assertNotNull(jsonObject);
        assertEquals(Integer.valueOf(Short.MAX_VALUE), jsonObject.get("value"));
    }

    protected void setUp() throws Exception {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, 2007);
        c.set(Calendar.MONTH, 5);
        c.set(Calendar.DAY_OF_MONTH, 17);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 13);
        c.set(Calendar.SECOND, 14);
        c.set(Calendar.MILLISECOND, 150);
        date = c.getTime();
        jsonConfig = new JsonConfig();
    }

    private void assertJsDate(JSONObject jsDate) {
        JSONAssert.assertNotNull(jsDate);
        int year = jsDate.getInt("year");
        // bug ?
        assertTrue(year == 2007 || year == 107);
        assertEquals(5, jsDate.getInt("month"));
        assertEquals(17, jsDate.getInt("day"));
        assertEquals(12, jsDate.getInt("hours"));
        assertEquals(13, jsDate.getInt("minutes"));
        assertEquals(14, jsDate.getInt("seconds"));
        assertEquals(150, jsDate.getInt("milliseconds"));
    }
}