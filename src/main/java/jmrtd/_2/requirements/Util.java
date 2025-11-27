/*
 * JMRTD - A Java API for accessing machine readable travel documents.
 *
 * Copyright (C) 2006  SoS group, Radboud University
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *
 * $Id: Util.java 50 2006-07-12 13:26:07Z martijno $
 */

package jmrtd._2.requirements;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * Some static helper functions. Mostly dealing with low-level crypto.
 */
public class Util {
   public static final int ENC_MODE = 1;
   public static final int MAC_MODE = 2;

   public static SecretKey deriveKey(byte[] keySeed, int mode)
   throws GeneralSecurityException {
      MessageDigest shaDigest = MessageDigest.getInstance("SHA1");
      shaDigest.update(keySeed);
      byte[] c = { 0x00, 0x00, 0x00, (byte)mode };
      shaDigest.update(c);
      byte[] hash = shaDigest.digest();
      byte[] key = new byte[24];
      System.arraycopy(hash, 0, key, 0, 8);
      System.arraycopy(hash, 8, key, 8, 8);
      System.arraycopy(hash, 0, key, 16, 8);
      SecretKeyFactory desKeyFactory = SecretKeyFactory.getInstance("DESede");
      return desKeyFactory.generateSecret(new DESedeKeySpec(key));
   }

   public static byte[] computeKeySeed(String docNrStr,
                                        String dateOfBirthStr,
                                        String dateOfExpiryStr)
   throws UnsupportedEncodingException, GeneralSecurityException {
      byte[] docNr = docNrStr.getBytes("UTF-8");
      byte[] dateOfBirth = dateOfBirthStr.getBytes("UTF-8");
      byte[] dateOfExpiry = dateOfExpiryStr.getBytes("UTF-8");
      if (docNr.length != 9
          || dateOfBirth.length != 6
          || dateOfExpiry.length != 6) {
         throw new UnsupportedEncodingException("Wrong length MRZ input");
      }
      byte[] cd1 = (Integer.toString(checkDigit(docNr))).getBytes("UTF-8");
      byte[] cd2 = (Integer.toString(checkDigit(dateOfBirth))).getBytes("UTF-8");
      byte[] cd3 = (Integer.toString(checkDigit(dateOfExpiry))).getBytes("UTF-8");
      MessageDigest shaDigest = MessageDigest.getInstance("SHA1");
      shaDigest.update(docNr);
      shaDigest.update(cd1);
      shaDigest.update(dateOfBirth);
      shaDigest.update(cd2);
      shaDigest.update(dateOfExpiry);
      shaDigest.update(cd3);
      byte[] hash = shaDigest.digest();
      byte[] keySeed = new byte[16];
      System.arraycopy(hash, 0, keySeed, 0, 16);
      return keySeed;
   }

   public static long computeSendSequenceCounter(byte[] rndICC, byte[] rndIFD) {
      if (rndICC == null || rndICC.length != 8
          || rndIFD == null || rndIFD.length != 8) {
         throw new IllegalStateException("Wrong length input");
      }
      long ssc = 0;
      for (int i = 4; i < 8; i++) {
         ssc <<= 8;
         ssc += (long)(rndICC[i] & 0x000000FF);
      }
      for (int i = 4; i < 8; i++) {
         ssc <<= 8;
         ssc += (long)(rndIFD[i] & 0x000000FF);
      }
      return ssc;
   }

   public static byte[] pad(byte[] in) {
      return pad(in, 0, in.length);
   }

   public static byte[] pad(byte[] in, int offset, int length) {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      out.write(in, offset, length);
      out.write((byte)0x80);
      while (out.size() % 8 != 0) {
        out.write((byte)0x00);
      }
      return out.toByteArray();
   }

   public static byte[] unpad(byte[] in) {
      int i = in.length - 1;
      while (i >= 0 && in[i] == 0x00) {
        i--;
      }
      if ((in[i] & 0x000000FF) != 0x00000080) {
         throw new IllegalStateException("unpad expected constant 0x80");
      }
      byte[] out = new byte[i];
      System.arraycopy(in, 0, out, 0, i);
      return out;
   }

   private static int checkDigit(byte[] chars) {
      try {
         int[] weights = { 7, 3, 1 };
         int result = 0;
         for (int i = 0; i < chars.length; i++) {
            result = (result + weights[i % 3] * decodeMRZDigit(chars[i])) % 10;
         }
         return result;
      } catch (Exception e) {
         throw new IllegalArgumentException(e.toString());
      }
   }

   private static int decodeMRZDigit(byte ch) throws NumberFormatException {
      switch (ch) {
         case '<':
         case '0': return 0; case '1': return 1; case '2': return 2;
         case '3': return 3; case '4': return 4; case '5': return 5;
         case '6': return 6; case '7': return 7; case '8': return 8;
         case '9': return 9;
         case 'a': case 'A': return 10; case 'b': case 'B': return 11;
         case 'c': case 'C': return 12; case 'd': case 'D': return 13;
         case 'e': case 'E': return 14; case 'f': case 'F': return 15;
         case 'g': case 'G': return 16; case 'h': case 'H': return 17;
         case 'i': case 'I': return 18; case 'j': case 'J': return 19;
         case 'k': case 'K': return 20; case 'l': case 'L': return 21;
         case 'm': case 'M': return 22; case 'n': case 'N': return 23;
         case 'o': case 'O': return 24; case 'p': case 'P': return 25;
         case 'q': case 'Q': return 26; case 'r': case 'R': return 27;
         case 's': case 'S': return 28; case 't': case 'T': return 29;
         case 'u': case 'U': return 30; case 'v': case 'V': return 31;
         case 'w': case 'W': return 32; case 'x': case 'X': return 33;
         case 'y': case 'Y': return 34; case 'z': case 'Z': return 35;
         default:
            throw new NumberFormatException("Could not decode MRZ character " + ch);
      }
   }

   public static byte[] recoverMessage(int digestLength, byte[] plaintext) {
      if (((plaintext[0] & 0xC0) ^ 0x40) != 0) {
         throw new NumberFormatException("Could not get M1");
      }
      if (((plaintext[plaintext.length - 1] & 0xF) ^ 0xC) != 0) {
         throw new NumberFormatException("Could not get M1");
      }
      int delta = 0;
      if (((plaintext[plaintext.length - 1] & 0xFF) ^ 0xBC) == 0) {
         delta = 1;
      } else {
         throw new NumberFormatException("Could not get M1");
      }
      int mStart = 0;
      for (mStart = 0; mStart < plaintext.length; mStart++) {
         if (((plaintext[mStart] & 0x0F) ^ 0x0A) == 0) {
            break;
         }
      }
      mStart++;
      int off = plaintext.length - delta - digestLength;
      if ((off - mStart) <= 0) {
         throw new NumberFormatException("Could not get M1");
      }
      if ((plaintext[0] & 0x20) == 0) {
         throw new NumberFormatException("Could not get M1");
      } else {
         byte[] recoveredMessage = new byte[off - mStart];
         System.arraycopy(plaintext, mStart, recoveredMessage, 0, recoveredMessage.length);
         return recoveredMessage;
      }
   }
}
