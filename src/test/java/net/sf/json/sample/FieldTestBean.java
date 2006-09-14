package net.sf.json.sample;

public class FieldTestBean
{
   private String string;
   private transient String transientString;
   private volatile String volatileString;

   public String getString()
   {
      return string;
   }

   public String getTransientString()
   {
      return transientString;
   }

   public String getVolatileString()
   {
      return volatileString;
   }

   public void setString( String string )
   {
      this.string = string;
   }

   public void setTransientString( String transientString )
   {
      this.transientString = transientString;
   }

   public void setVolatileString( String volatileString )
   {
      this.volatileString = volatileString;
   }
}