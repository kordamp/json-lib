package net.sf.json.sample;

import java.util.HashMap;
import java.util.Map;

public class MappingBean
{
   private Map attributes = new HashMap();

   public void addAttribute( Object key, Object value )
   {
      this.attributes.put( key, value );
   }

   public Map getAttributes()
   {
      return attributes;
   }

   public void setAttributes( Map attributes )
   {
      this.attributes = attributes;
   }
}