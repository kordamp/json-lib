package net.sf.json.sample;

import java.math.BigDecimal;
import java.math.BigInteger;

public class NumberBean
{
   private BigDecimal pbigdec;
   private BigInteger pbigint;
   private byte pbyte;
   private double pdouble;
   private float pfloat;
   private int pint;
   private long plong;
   private short pshort;

   public BigDecimal getPbigdec()
   {
      return pbigdec;
   }

   public BigInteger getPbigint()
   {
      return pbigint;
   }

   public byte getPbyte()
   {
      return pbyte;
   }

   public double getPdouble()
   {
      return pdouble;
   }

   public float getPfloat()
   {
      return pfloat;
   }

   public int getPint()
   {
      return pint;
   }

   public long getPlong()
   {
      return plong;
   }

   public short getPshort()
   {
      return pshort;
   }

   public void setPbigdec( BigDecimal pbigdec )
   {
      this.pbigdec = pbigdec;
   }

   public void setPbigint( BigInteger pbigint )
   {
      this.pbigint = pbigint;
   }

   public void setPbyte( byte pbyte )
   {
      this.pbyte = pbyte;
   }

   public void setPdouble( double pdouble )
   {
      this.pdouble = pdouble;
   }

   public void setPfloat( float pfloat )
   {
      this.pfloat = pfloat;
   }

   public void setPint( int pint )
   {
      this.pint = pint;
   }

   public void setPlong( long plong )
   {
      this.plong = plong;
   }

   public void setPshort( short pshort )
   {
      this.pshort = pshort;
   }
}