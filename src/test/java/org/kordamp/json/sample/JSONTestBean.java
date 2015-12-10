/*
 * Copyright 2006-2015 the original author or authors.
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
package org.kordamp.json.sample;

import java.util.HashMap;
import java.util.Map;

public class JSONTestBean {
    private String email = "";
    private String inventoryID = "";
    private String notes = "";
    private Map options = new HashMap();
    private String rateID = "";

    public String getEmail() {
        return email;
    }

    public String getInventoryID() {
        return inventoryID;
    }

    public String getNotes() {
        return notes;
    }

    public Map getOptions() {
        return options;
    }

    public String getRateID() {
        return rateID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setInventoryID(String inventoryID) {
        this.inventoryID = inventoryID;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setOptions(Map options) {
        this.options = options;
    }

    public void setRateID(String rateID) {
        this.rateID = rateID;
    }
}