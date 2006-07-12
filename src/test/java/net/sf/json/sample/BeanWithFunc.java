package net.sf.json.sample;

import net.sf.json.JSONFunction;

public class BeanWithFunc
{
   private JSONFunction function;

   public BeanWithFunc( JSONFunction function )
   {
      this.function = function;
   }

   public BeanWithFunc( String function )
   {
      this.function = new JSONFunction( function );
   }

   public JSONFunction getFunction()
   {
      return function;
   }

   public void setFunction( JSONFunction function )
   {
      this.function = function;
   }
}