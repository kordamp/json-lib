package net.sf.json.sample;

public class EnumBean
{
   private JsonEnum jsonEnum;
   private String string;

   public JsonEnum getJsonEnum()
   {
      return jsonEnum;
   }

   public String getString()
   {
      return string;
   }

   public void setJsonEnum( JsonEnum jsonEnum )
   {
      this.jsonEnum = jsonEnum;
   }

   public void setString( String string )
   {
      this.string = string;
   }
}