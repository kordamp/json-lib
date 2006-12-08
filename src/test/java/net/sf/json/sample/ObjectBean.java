package net.sf.json.sample;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ObjectBean
{
   private Object bean;
   private Object parray;
   private Object pboolean;
   private Object pbyte;
   private Object pchar;
   private Object pdouble;
   private Object pfloat;
   private Object pfunction;
   private Object pint;
   private Object plist;
   private Object plong;
   private Object pmap;
   private Object pshort;

   private Object pstring;

   public Object getBean()
   {
      return bean;
   }

   public Object getParray()
   {
      return parray;
   }

   public Object getPboolean()
   {
      return pboolean;
   }

   public Object getPbyte()
   {
      return pbyte;
   }

   public Object getPchar()
   {
      return pchar;
   }

   public Object getPdouble()
   {
      return pdouble;
   }

   public Object getPfloat()
   {
      return pfloat;
   }

   public Object getPfunction()
   {
      return pfunction;
   }

   public Object getPint()
   {
      return pint;
   }

   public Object getPlist()
   {
      return plist;
   }

   public Object getPlong()
   {
      return plong;
   }

   public Object getPmap()
   {
      return pmap;
   }

   public Object getPshort()
   {
      return pshort;
   }

   public Object getPstring()
   {
      return pstring;
   }

   public void setBean( Object bean )
   {
      this.bean = bean;
   }

   public void setParray( Object parray )
   {
      this.parray = parray;
   }

   public void setPboolean( Object pboolean )
   {
      this.pboolean = pboolean;
   }

   public void setPbyte( Object pbyte )
   {
      this.pbyte = pbyte;
   }

   public void setPchar( Object pchar )
   {
      this.pchar = pchar;
   }

   public void setPdouble( Object pdouble )
   {
      this.pdouble = pdouble;
   }

   public void setPfloat( Object pfloat )
   {
      this.pfloat = pfloat;
   }

   public void setPfunction( Object pfunction )
   {
      this.pfunction = pfunction;
   }

   public void setPint( Object pint )
   {
      this.pint = pint;
   }

   public void setPlist( Object plist )
   {
      this.plist = plist;
   }

   public void setPlong( Object plong )
   {
      this.plong = plong;
   }

   public void setPmap( Object pmap )
   {
      this.pmap = pmap;
   }

   public void setPshort( Object pshort )
   {
      this.pshort = pshort;
   }

   public void setPstring( Object pstring )
   {
      this.pstring = pstring;
   }

   public String toString()
   {
      return ToStringBuilder.reflectionToString( this, ToStringStyle.MULTI_LINE_STYLE );
   }
}