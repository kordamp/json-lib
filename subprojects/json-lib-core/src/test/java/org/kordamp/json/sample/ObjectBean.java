/*
 * SPDX-License-Identifier: Apache-2.0
 *
 * Copyright 2006-2019 the original author or authors.
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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Andres Almiray
 */
public class ObjectBean {
    private Object parray;
    private Object pbean;
    private Object pboolean;
    private Object pbyte;
    private Object pchar;
    private Object pclass;
    private Object pdouble;
    private Object pexcluded;
    private Object pfloat;
    private Object pfunction;
    private Object pint;
    private Object plist;
    private Object plong;
    private Object pmap;
    private Object pshort;
    private Object pstring;

    public Object getParray() {
        return parray;
    }

    public void setParray(Object parray) {
        this.parray = parray;
    }

    public Object getPbean() {
        return pbean;
    }

    public void setPbean(Object bean) {
        this.pbean = bean;
    }

    public Object getPboolean() {
        return pboolean;
    }

    public void setPboolean(Object pboolean) {
        this.pboolean = pboolean;
    }

    public Object getPbyte() {
        return pbyte;
    }

    public void setPbyte(Object pbyte) {
        this.pbyte = pbyte;
    }

    public Object getPchar() {
        return pchar;
    }

    public void setPchar(Object pchar) {
        this.pchar = pchar;
    }

    public Object getPclass() {
        return pclass;
    }

    public void setPclass(Object pclass) {
        this.pclass = pclass;
    }

    public Object getPdouble() {
        return pdouble;
    }

    public void setPdouble(Object pdouble) {
        this.pdouble = pdouble;
    }

    public Object getPexcluded() {
        return pexcluded;
    }

    public void setPexcluded(Object pexcluded) {
        this.pexcluded = pexcluded;
    }

    public Object getPfloat() {
        return pfloat;
    }

    public void setPfloat(Object pfloat) {
        this.pfloat = pfloat;
    }

    public Object getPfunction() {
        return pfunction;
    }

    public void setPfunction(Object pfunction) {
        this.pfunction = pfunction;
    }

    public Object getPint() {
        return pint;
    }

    public void setPint(Object pint) {
        this.pint = pint;
    }

    public Object getPlist() {
        return plist;
    }

    public void setPlist(Object plist) {
        this.plist = plist;
    }

    public Object getPlong() {
        return plong;
    }

    public void setPlong(Object plong) {
        this.plong = plong;
    }

    public Object getPmap() {
        return pmap;
    }

    public void setPmap(Object pmap) {
        this.pmap = pmap;
    }

    public Object getPshort() {
        return pshort;
    }

    public void setPshort(Object pshort) {
        this.pshort = pshort;
    }

    public Object getPstring() {
        return pstring;
    }

    public void setPstring(Object pstring) {
        this.pstring = pstring;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}