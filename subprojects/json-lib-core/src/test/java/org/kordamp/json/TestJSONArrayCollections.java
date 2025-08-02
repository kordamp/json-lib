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
import org.kordamp.ezmorph.MorphUtils;
import org.kordamp.ezmorph.bean.MorphDynaBean;
import org.kordamp.ezmorph.bean.MorphDynaClass;
import org.kordamp.json.sample.BeanA;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Andres Almiray
 */
public class TestJSONArrayCollections extends TestCase {
    public TestJSONArrayCollections(String testName) {
        super(testName);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(TestJSONArrayCollections.class);
    }

    public void testToList_bean_elements() {
        List expected = new ArrayList();
        expected.add(new BeanA());
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray, BeanA.class);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_BigDecimal() {
        List expected = new ArrayList();
        expected.add(MorphUtils.BIGDECIMAL_ZERO);
        expected.add(MorphUtils.BIGDECIMAL_ONE);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_BigInteger() {
        List expected = new ArrayList();
        expected.add(BigInteger.ZERO);
        expected.add(BigInteger.ONE);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_Boolean() {
        List expected = new ArrayList();
        expected.add(Boolean.TRUE);
        expected.add(Boolean.FALSE);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_Byte() {
        List expected = new ArrayList();
        expected.add(Integer.valueOf(1));
        expected.add(Integer.valueOf(2));
        List bytes = new ArrayList();
        bytes.add(Byte.valueOf((byte) 1));
        bytes.add(Byte.valueOf((byte) 2));
        JSONArray jsonArray = JSONArray.fromObject(bytes);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_Character() {
        List expected = new ArrayList();
        expected.add("A");
        expected.add("B");
        List chars = new ArrayList();
        chars.add(Character.valueOf('A'));
        chars.add(Character.valueOf('B'));
        JSONArray jsonArray = JSONArray.fromObject(chars);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_Double() {
        List expected = new ArrayList();
        expected.add(Double.valueOf(1d));
        expected.add(Double.valueOf(2d));
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_dynaBean_elements() throws Exception {
        List expected = new ArrayList();
        expected.add(createDynaBean());
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_Float() {
        List expected = new ArrayList();
        expected.add(Double.valueOf(1d));
        expected.add(Double.valueOf(2d));
        List floats = new ArrayList();
        floats.add(Float.valueOf(1f));
        floats.add(Float.valueOf(2f));
        JSONArray jsonArray = JSONArray.fromObject(floats);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_Integer() {
        List expected = new ArrayList();
        expected.add(Integer.valueOf(1));
        expected.add(Integer.valueOf(2));
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_JSONFunction_elements() {
        List expected = new ArrayList();
        expected.add(new JSONFunction(new String[]{"a"}, "return a;"));
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_JSONFunction_elements_2() {
        List expected = new ArrayList();
        expected.add("function(a){ return a; }");
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_Long() {
        List expected = new ArrayList();
        expected.add(Integer.valueOf(1));
        expected.add(Integer.valueOf(2));
        List longs = new ArrayList();
        longs.add(Long.valueOf(1L));
        longs.add(Long.valueOf(2L));
        JSONArray jsonArray = JSONArray.fromObject(longs);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_Long2() {
        List expected = new ArrayList();
        expected.add(Long.valueOf(Integer.MAX_VALUE + 1L));
        expected.add(Long.valueOf(Integer.MAX_VALUE + 2L));
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_null_elements() {
        List expected = new ArrayList();
        expected.add(null);
        expected.add(null);
        expected.add(null);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_Short() {
        List expected = new ArrayList();
        expected.add(Integer.valueOf(1));
        expected.add(Integer.valueOf(2));
        List shorts = new ArrayList();
        shorts.add(Short.valueOf((short) 1));
        shorts.add(Short.valueOf((short) 2));
        JSONArray jsonArray = JSONArray.fromObject(shorts);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_String() {
        List expected = new ArrayList();
        expected.add("A");
        expected.add("B");
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToList_String_multi() {
        List a = new ArrayList();
        a.add("a");
        a.add("b");
        List b = new ArrayList();
        b.add("1");
        b.add("2");
        List expected = new ArrayList();
        expected.add(a);
        expected.add(b);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        List actual = (List) JSONArray.toCollection(jsonArray);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_bean_elements() {
        Set expected = new HashSet();
        expected.add(new BeanA());
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        jsonConfig.setRootClass(BeanA.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_BigDecimal() {
        Set expected = new HashSet();
        expected.add(MorphUtils.BIGDECIMAL_ZERO);
        expected.add(MorphUtils.BIGDECIMAL_ONE);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_BigInteger() {
        Set expected = new HashSet();
        expected.add(BigInteger.ZERO);
        expected.add(BigInteger.ONE);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_Boolean() {
        Set expected = new HashSet();
        expected.add(Boolean.TRUE);
        expected.add(Boolean.FALSE);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_Byte() {
        Set expected = new HashSet();
        expected.add(Integer.valueOf(1));
        expected.add(Integer.valueOf(2));
        Set bytes = new HashSet();
        bytes.add(Byte.valueOf((byte) 1));
        bytes.add(Byte.valueOf((byte) 2));
        JSONArray jsonArray = JSONArray.fromObject(bytes);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_Character() {
        Set expected = new HashSet();
        expected.add("A");
        expected.add("B");
        Set chars = new HashSet();
        chars.add(Character.valueOf('A'));
        chars.add(Character.valueOf('B'));
        JSONArray jsonArray = JSONArray.fromObject(chars);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_Double() {
        Set expected = new HashSet();
        expected.add(Double.valueOf(1d));
        expected.add(Double.valueOf(2d));
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_dynaBean_elements() throws Exception {
        Set expected = new HashSet();
        expected.add(createDynaBean());
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_Float() {
        Set expected = new HashSet();
        expected.add(Double.valueOf(1d));
        expected.add(Double.valueOf(2d));
        Set floats = new HashSet();
        floats.add(Float.valueOf(1f));
        floats.add(Float.valueOf(2f));
        JSONArray jsonArray = JSONArray.fromObject(floats);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_Integer() {
        Set expected = new HashSet();
        expected.add(Integer.valueOf(1));
        expected.add(Integer.valueOf(2));
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_JSONFunction_elements() {
        Set expected = new HashSet();
        expected.add(new JSONFunction(new String[]{"a"}, "return a;"));
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

   /*
   public void testToSet_JSONFunction_elements_2() {
      Set expected = new HashSet();
      expected.add( "function(a){ return a; }" );
      JSONArray jsonArray = JSONArray.fromObject( expected );
      JsonConfig jsonConfig = new JsonConfig();
      jsonConfig.setCollectionType( Set.class );
      Set actual = (Set) JSONArray.toCollection( jsonArray, jsonConfig );
      Assertions.assertEquals( expected, actual );
   }
   */

    public void testToSet_Long() {
        Set expected = new HashSet();
        expected.add(Integer.valueOf(1));
        expected.add(Integer.valueOf(2));
        Set longs = new HashSet();
        longs.add(Long.valueOf(1L));
        longs.add(Long.valueOf(2L));
        JSONArray jsonArray = JSONArray.fromObject(longs);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_Long2() {
        Set expected = new HashSet();
        expected.add(Long.valueOf(Integer.MAX_VALUE + 1L));
        expected.add(Long.valueOf(Integer.MAX_VALUE + 2L));
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_null_elements() {
        Set expected = new HashSet();
        expected.add(null);
        expected.add(null);
        expected.add(null);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_Short() {
        Set expected = new HashSet();
        expected.add(Integer.valueOf(1));
        expected.add(Integer.valueOf(2));
        Set shorts = new HashSet();
        shorts.add(Short.valueOf((short) 1));
        shorts.add(Short.valueOf((short) 2));
        JSONArray jsonArray = JSONArray.fromObject(shorts);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_String() {
        Set expected = new HashSet();
        expected.add("A");
        expected.add("B");
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    public void testToSet_String_multi() {
        Set a = new HashSet();
        a.add("a");
        a.add("b");
        Set b = new HashSet();
        b.add("1");
        b.add("2");
        Set expected = new HashSet();
        expected.add(a);
        expected.add(b);
        JSONArray jsonArray = JSONArray.fromObject(expected);
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCollectionType(Set.class);
        Set actual = (Set) JSONArray.toCollection(jsonArray, jsonConfig);
        Assertions.assertEquals(expected, actual);
    }

    private MorphDynaBean createDynaBean() throws Exception {
        Map properties = new HashMap();
        properties.put("name", String.class);
        MorphDynaClass dynaClass = new MorphDynaClass(properties);
        MorphDynaBean dynaBean = (MorphDynaBean) dynaClass.newInstance();
        dynaBean.setDynaBeanClass(dynaClass);
        dynaBean.set("name", "json");
        // JSON Strings can not be null, only empty
        return dynaBean;
    }
}