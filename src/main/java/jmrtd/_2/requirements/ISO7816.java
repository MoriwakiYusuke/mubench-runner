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
 * $Id: ISO7816.java 29 2006-07-05 21:29:53Z martijno $
 */

package jmrtd._2.requirements;

/**
 * Constants interface for ISO7816-4.
 *
 * @author Martijn Oostdijk (martijno@cs.ru.nl)
 *
 * @version $Revision: 29 $
 */
public interface ISO7816 {
   byte OFFSET_CLA = 0;
   byte OFFSET_INS = 1;
   byte OFFSET_P1 = 2;
   byte OFFSET_P2 = 3;
   byte OFFSET_LC = 4;
   byte OFFSET_CDATA = 5;

   byte CLA_ISO7816 = 0x00;
   byte INS_ERASE_BINARY = 0x0E;
   byte INS_VERIFY = 0x20;
   byte INS_MANAGE_CHANNEL = 0x70;
   byte INS_EXTERNAL_AUTHENTICATE = (byte) 0x82;
   byte INS_GET_CHALLENGE = (byte) 0x84;
   byte INS_INTERNAL_AUTHENTICATE = (byte) 0x88;
   byte INS_SELECT_FILE = (byte) 0xA4;
   byte INS_READ_BINARY = (byte) 0xB0;
   byte INS_READ_RECORD = (byte) 0xB2;
   byte INS_GET_RESPONSE = (byte) 0xC0;
   byte INS_ENVELOPE = (byte) 0xC2;
   byte INS_GET_DATA = (byte) 0xCA;
   byte INS_WRITE_BINARY = (byte) 0xD0;
   byte INS_WRITE_RECORD = (byte) 0xD2;
   byte INS_UPDATE_BINARY = (byte) 0xD6;
   byte INS_PUT_DATA = (byte) 0xDA;
   byte INS_UPDATE_RECORD = (byte) 0xDC;
   byte INS_APPEND_RECORD = (byte) 0xE2;
   byte INS_CREATE_FILE = (byte) 0xE0;

   short SW_BYTES_REMAINING_00 = 0x6100;
   short SW_END_OF_FILE = 0x6282;
   short SW_WRONG_LENGTH = 0x6700;
   short SW_SECURITY_STATUS_NOT_SATISFIED = 0x6982;
   short SW_FILE_INVALID = 0x6983;
   short SW_DATA_INVALID = 0x6984;
   short SW_CONDITIONS_NOT_SATISFIED = 0x6985;
   short SW_COMMAND_NOT_ALLOWED = 0x6986;
   short SW_APPLET_SELECT_FAILED = 0x6999;
   short SW_WRONG_DATA = 0x6A80;
   short SW_FUNC_NOT_SUPPORTED = 0x6A81;
   short SW_FILE_NOT_FOUND = 0x6A82;
   short SW_RECORD_NOT_FOUND = 0x6A83;
   short SW_INCORRECT_P1P2 = 0x6A86;
   short SW_WRONG_P1P2 = 0x6B00;
   short SW_CORRECT_LENGTH_00 = 0x6C00;
   short SW_INS_NOT_SUPPORTED = 0x6D00;
   short SW_CLA_NOT_SUPPORTED = 0x6E00;
   short SW_UNKNOWN = 0x6F00;
   short SW_NO_ERROR = (short) 0x9000;
}
