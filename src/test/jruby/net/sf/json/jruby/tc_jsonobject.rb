require 'test/unit'
require 'java'
require 'net/sf/json/jsonobject'

class TC_JsonObject < Test::Unit::TestCase
   def test_empty?
      json = JSONObject.new
      assert json.empty?
   end

   def test_simple_values
      json = JSONObject.new
      json.element("string","json").element("bool",true)
      json["integer"] = 1
      assert_equal "json", json["string"]  		
      assert_equal true, json["bool"]  		
      assert_equal 1, json["integer"]  		
   end

   def test_leftshift_array
     json = JSONObject.new
     json << ["key","value"]
     assert !json.empty?
     assert_equal "value", json["key"]
   end

   def test_leftshift_hash
     json = JSONObject.new
     json << {"key1" => "value1", "key2" => "value2" }
     assert !json.empty?
     assert_equal 2, json.size()
     assert_equal "value1", json["key1"]
     assert_equal "value2", json["key2"]
   end
end
