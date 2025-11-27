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

package jmrtd._1.requirements;

/**
 * Some static helper methods for dealing with hexadecimal notation.
 *
 * @author Martijn Oostdijk (martijno@cs.ru.nl)
 *
 * @version $Revision: 29 $
 */
public final class Hex {

   /** Hex characters. */
   private static final String HEXCHARS = "0123456789abcdefABCDEF";

   /** Printable characters. */
   private static final String PRINTABLE = " .,:;'`\"<>()[]{}?/\\!@#$%^&*_-=+|~0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

   private static final boolean LEFT = true;
   private static final boolean RIGHT = false;

   /**
    * This private constructor makes it impossible for clients to create
    * instances of this class.
    */
   private Hex() {
   }

   /**
    * Converts the byte <code>b</code> to capitalized hexadecimal text.
    */
   public static String byteToHexString(byte b) {
      int n = b & 0x000000FF;
      String result = (n < 0x00000010 ? "0" : "") + Integer.toHexString(n);
      return result.toUpperCase();
   }

   /**
    * Converts the short <code>s</code> to capitalized hexadecimal text.
    */
   public static String shortToHexString(short s) {
      int n = s & 0x0000FFFF;
      String result = ((n < 0x00001000) ? "0" : "")
                    + ((n < 0x00000100) ? "0" : "")
                    + ((n < 0x00000010) ? "0" : "")
                    + Integer.toHexString(s);
      return result.toUpperCase();
   }

   /**
    * Converts the integer <code>n</code> to capitalized hexadecimal text.
    */
   public static String intToHexString(int n) {
      String result = ((n < 0x10000000) ? "0" : "")
                    + ((n < 0x01000000) ? "0" : "")
                    + ((n < 0x00100000) ? "0" : "")
                    + ((n < 0x00010000) ? "0" : "")
                    + ((n < 0x00001000) ? "0" : "")
                    + ((n < 0x00000100) ? "0" : "")
                    + ((n < 0x00000010) ? "0" : "")
                    + Integer.toHexString(n);
      return result.toUpperCase();
   }

   /**
    * Converts a byte array to capitalized hexadecimal text.
    */
   public static String bytesToHexString(byte[] text) {
      return bytesToHexString(text,0,text.length);
   }

   /**
    * Converts a byte array to capitalized hexadecimal text.
    */
   public static String toHexString(byte[] text) {
      return bytesToHexString(text,0,text.length);
   }

   /**
    * Converts part of a byte array to capitalized hexadecimal text.
    */
   public static String bytesToHexString(byte[] text, int offset, int length) {
      String result = "";
      for (int i = 0; i < length; i++) {
         result += byteToHexString(text[offset + i]);
      }
      return result;
   }

   /**
    * Converts the hexadecimal string in <code>text</code> to a byte.
    */
   public static byte hexStringToByte(String text)
   throws NumberFormatException {
      byte[] bytes = hexStringToBytes(text);
      if (bytes.length != 1) {
         throw new NumberFormatException();
      }
      return bytes[0];
   }

   /**
    * Converts the hexadecimal string in <code>text</code> to a short.
    */
   public static short hexStringToShort(String text)
   throws NumberFormatException {
      byte[] bytes = hexStringToBytes(text);
      if (bytes.length != 2) {
         throw new NumberFormatException();
      }
      return (short)(((bytes[0] & 0x000000FF) << 8)
                    | (bytes[1] & 0x000000FF));
   }

   /**
    * Converts the hexadecimal string in <code>text</code> to an integer.
    */
   public static int hexStringToInt(String text)
   throws NumberFormatException {
      byte[] bytes = hexStringToBytes(text);
      if (bytes.length != 4) {
         throw new NumberFormatException();
      }
      return (int)(((bytes[0] & 0x000000FF) << 24)
                 | ((bytes[1] & 0x000000FF) << 16)
                 | ((bytes[2] & 0x000000FF) << 8)
                 | (bytes[3] & 0x000000FF));
   }

   /**
    * Converts the hexadecimal string in <code>text</code> to a byte array.
    */
   public static byte[] hexStringToBytes(String text)
   throws NumberFormatException {
      if (text==null) {
         return null;
      }
      StringBuffer hexText = new StringBuffer();
      for (int i=0; i < text.length(); i++) {
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
         hexText.insert(0,"0");
      }
      byte[] result = new byte[hexText.length() / 2];
      for (int i = 0; i < hexText.length(); i += 2) {
         int hi = hexDigitToInt(hexText.charAt(i));
         int lo = hexDigitToInt(hexText.charAt(i + 1));
         result[i / 2] = (byte)(((hi & 0x000000FF) << 4) | (lo & 0x000000FF));
      }
      return result;
   }

   /**
    * Interprets the character <code>c</code> as hexadecimal digit.
    */
   static int hexDigitToInt(char c)
   throws NumberFormatException {
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

   private static String pad(String txt, int width, char padChar, boolean left) {
      String result = new String(txt);
      String padString = Character.toString(padChar);
      for (int i = txt.length(); i < width; i++) {
         if (left) {
            result = padString + result;
         } else {
            result = result + padString;
         }
      }
      return result;
   }

   public static String bytesToSpacedHexString(byte[] data) {
      String result = "";
      for (int i = 0; i < data.length; i++) {
         result += byteToHexString(data[i]);
         result += (i < data.length - 1) ? " " : "";
      }
      result = result.toUpperCase();
      return result;
   }

   private static String[] bytesToSpacedHexStrings(byte[] data, int columns,
                                           int padWidth) {
      byte[][] src = split(data,columns);
      String[] result = new String[src.length];
      for (int j = 0; j < src.length; j++) {
         result[j] = bytesToSpacedHexString(src[j]);
         result[j] = pad(result[j],padWidth,' ',RIGHT);
      }
      return result;
   }

   public static String bytesToASCIIString(byte[] data) {
      String result = "";
      for (int i = 0; i < data.length; i++) {
         char c = (char)data[i];
         result += Character.toString(PRINTABLE.indexOf(c) >= 0 ? c : '.');
      }
      return result;
   }

   static String[] bytesToASCIIStrings(byte[] data, int columns,
                                       int padWidth) {
      byte[][] src = split(data,columns);
      String[] result = new String[src.length];
      for (int j = 0; j < src.length; j++) {
         result[j] = bytesToASCIIString(src[j]);
      }
      return result;
   }

   public static byte[][] split(byte[] src, int width) {
      int rows = src.length / width;
      int rest = src.length % width;
      byte[][] dest = new byte[rows + (rest > 0 ? 1 : 0)][];
      int k = 0;
      for (int j = 0; j < rows; j++) {
         dest[j] = new byte[width];
         System.arraycopy(src,k,dest[j],0,width);
         k += width;
      }
      if (rest > 0) {
         dest[rows] = new byte[rest];
         System.arraycopy(src,k,dest[rows],0,rest);
      }
      return dest;
   }

   public static String bytesToPrettyString(byte[] data) {
      return bytesToPrettyString(data,16,true,4,null,true);
   }

   public static String bytesToPrettyString(byte[] data, int columns,
                          boolean useIndex, int indexPadWidth, String altIndex,
                          boolean useASCII) {
      String result = "";
      String[] hexStrings = bytesToSpacedHexStrings(data,columns,3 * columns);
      String[] asciiStrings = bytesToASCIIStrings(data,columns,columns);
      for (int j = 0; j < hexStrings.length; j++) {
         if (useIndex) {
            String prefix = Integer.toHexString(j * columns).toUpperCase();
            result += pad(prefix,indexPadWidth,'0',LEFT) + ": ";
         } else {
            String prefix = j == 0 ? altIndex : "";
            result += pad(prefix,indexPadWidth,' ',LEFT) + " ";
         }
         result += hexStrings[j];
         if (useASCII) {
            result += " " + asciiStrings[j];
         }
         result += "\n";
      }
      return result;
   }
}
