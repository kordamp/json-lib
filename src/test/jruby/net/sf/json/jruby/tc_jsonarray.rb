require 'test/unit'
require 'java'
require 'net/sf/json/jsonarray'

class TC_JsonArray < Test::Unit::TestCase
   def test_empty?
      json = JSONArray.new
      assert json.empty?
   end
end
