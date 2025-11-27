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
 */

package jmrtd._2.requirements;

import java.util.EventObject;

/**
 * Event for BAC (Basic Access Control) authentication.
 */
public class BACEvent extends EventObject {
   private SecureMessagingWrapper wrapper;
   private byte[] rndICC;
   private byte[] rndIFD;
   private byte[] kICC;
   private byte[] kIFD;
   private boolean success;

   public BACEvent(Object source, SecureMessagingWrapper wrapper,
                   byte[] rndICC, byte[] rndIFD, byte[] kICC, byte[] kIFD,
                   boolean success) {
      super(source);
      this.wrapper = wrapper;
      this.rndICC = rndICC;
      this.rndIFD = rndIFD;
      this.kICC = kICC;
      this.kIFD = kIFD;
      this.success = success;
   }

   public SecureMessagingWrapper getWrapper() { return wrapper; }
   public byte[] getRndICC() { return rndICC; }
   public byte[] getRndIFD() { return rndIFD; }
   public byte[] getKICC() { return kICC; }
   public byte[] getKIFD() { return kIFD; }
   public boolean isSuccess() { return success; }
}
