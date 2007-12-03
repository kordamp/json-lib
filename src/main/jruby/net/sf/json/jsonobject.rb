#
# Copyright 2002-2007 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# @author Andres Almiray <aalmiray@users.sourceforge.net>
#

include_class 'net.sf.json.JSONObject'

class JSONObject
   def empty?
      return isEmpty()
   end

   def <<(arg)
      if( !arg.nil? )
         if( (arg.instance_of? Array) && (arg.length > 1) )
            key = arg.shift
            if( arg.length == 1 )
               element( key, arg[0] )
            else
               element( key, arg )
            end
         elsif( (arg.instance_of? Hash) && !(arg.empty?) )
            putAll( arg )
         end
      end
      return self
   end
end
