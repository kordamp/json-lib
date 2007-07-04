/*
 * Copyright 2002-2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.sf.json.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * @author Andres Almiray <aalmiray@users.sourceforge.net>
 */
public abstract class CycleDetectionStrategy {
   /** Returns empty array and null object */
   public static final CycleDetectionStrategy LENIENT = new LenientCycleDetectionStrategy();
   /** Throws a JSONException */
   public static final CycleDetectionStrategy STRICT = new StrictCycleDetectionStrategy();

   public abstract JSONArray handleRepeatedReferenceAsArray( Object reference );

   public abstract JSONObject handleRepeatedReferenceAsObject( Object reference );

   private static final class LenientCycleDetectionStrategy extends CycleDetectionStrategy {
      public JSONArray handleRepeatedReferenceAsArray( Object reference ) {
         return new JSONArray();
      }

      public JSONObject handleRepeatedReferenceAsObject( Object reference ) {
         return new JSONObject( true );
      }
   }

   private static final class StrictCycleDetectionStrategy extends CycleDetectionStrategy {
      public JSONArray handleRepeatedReferenceAsArray( Object reference ) {
         throw new JSONException( "There is a cycle in the hierarchy!" );
      }

      public JSONObject handleRepeatedReferenceAsObject( Object reference ) {
         throw new JSONException( "There is a cycle in the hierarchy!" );
      }
   }
}