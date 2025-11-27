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
 * $Id: Hex.java 29 2006-07-05 21:29:53Z martijno $
 */

package jmrtd._2.requirements;

/**
 * Some static helper methods for dealing with hexadecimal notation.
 *
 * @author Martijn Oostdijk (martijno@cs.ru.nl)
 *
 * @version $Revision: 29 $
 */
public final class Hex {

   private static final String HEXCHARS = "0123456789abcdefABCDEF";

   private Hex() {
   }

   public static String byteToHexString(byte b) {
      int n = b & 0x000000FF;
      String result = (n < 0x00000010 ? "0" : "") + Integer.toHexString(n);
      return result.toUpperCase();
   }

   public static String bytesToHexString(byte[] text) {
      return bytesToHexString(text, 0, text.length);
   }

   public static String bytesToHexString(byte[] text, int offset, int length) {
      String result = "";
      for (int i = 0; i < length; i++) {
         result += byteToHexString(text[offset + i]);
      }
      return result;
   }

   public static byte[] hexStringToBytes(String text) throws NumberFormatException {
      if (text == null) {
         return null;
      }
      StringBuffer hexText = new StringBuffer();
      for (int i = 0; i < text.length(); i++) {
         char c = text.charAt(i);
         if (Character.isWhitespace(c)) {
            continue;
         } else if (HEXCHARS.indexOf(c) < 0) {
            throw new NumberFormatException();
         } else {
            hexText.append(c);
         }
      }
      if (hexText.length() % 2 != 0) {
         hexText.insert(0, "0");
      }
      byte[] result = new byte[hexText.length() / 2];
      for (int i = 0; i < hexText.length(); i += 2) {
         int hi = hexDigitToInt(hexText.charAt(i));
         int lo = hexDigitToInt(hexText.charAt(i + 1));
         result[i / 2] = (byte) (((hi & 0x000000FF) << 4) | (lo & 0x000000FF));
      }
      return result;
   }

   static int hexDigitToInt(char c) throws NumberFormatException {
      switch (c) {
         case '0': return 0;
         case '1': return 1;
         case '2': return 2;
         case '3': return 3;
         case '4': return 4;
         case '5': return 5;
         case '6': return 6;
         case '7': return 7;
         case '8': return 8;
         case '9': return 9;
         case 'a': case 'A': return 10;
         case 'b': case 'B': return 11;
         case 'c': case 'C': return 12;
         case 'd': case 'D': return 13;
         case 'e': case 'E': return 14;
         case 'f': case 'F': return 15;
         default: throw new NumberFormatException();
      }
   }
}
