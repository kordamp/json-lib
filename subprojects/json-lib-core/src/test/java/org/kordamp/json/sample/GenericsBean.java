/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2019 Andres Almiray.
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Matt Small <msmall@wavemaker.com>
 */
public class GenericsBean {
    private List<GenericsInternalBean> genericsInternalBeanList;
    private List noTypeList;
    private ArrayList<String> stringArrayList;
    private HashSet<String> stringHashSet;
    private List<String> stringList;
    private Set<String> stringSet;

    public List<GenericsInternalBean> getGenericsInternalBeanList() {
        return genericsInternalBeanList;
    }

    public void setGenericsInternalBeanList(List<GenericsInternalBean> genericsInternalBeanList) {
        this.genericsInternalBeanList = genericsInternalBeanList;
    }

    public List getNoTypeList() {
        return noTypeList;
    }

    public void setNoTypeList(List noTypeList) {
        this.noTypeList = noTypeList;
    }

    public ArrayList<String> getStringArrayList() {
        return stringArrayList;
    }

    public void setStringArrayList(ArrayList<String> stringArrayList) {
        this.stringArrayList = stringArrayList;
    }

    public HashSet<String> getStringHashSet() {
        return stringHashSet;
    }

    public void setStringHashSet(HashSet<String> stringHashSet) {
        this.stringHashSet = stringHashSet;
    }

    public List<String> getStringList() {
        return stringList;
    }

    public void setStringList(List<String> stringList) {
        this.stringList = stringList;
    }

    public Set<String> getStringSet() {
        return stringSet;
    }

    public void setStringSet(Set<String> stringSet) {
        this.stringSet = stringSet;
    }

    public static class GenericsInternalBean {

        private String string;

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }
}