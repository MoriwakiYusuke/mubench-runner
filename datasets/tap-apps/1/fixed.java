// --------------------------------------------------------------------------
// TAP APPS Project -- Source File
// Copyright (c) 2004 Brad BARCLAY <bbarclay@jsyncmanager.org>
// --------------------------------------------------------------------------
// OSI Certified Open Source Software
// --------------------------------------------------------------------------
//
// This program is free software; you can redistribute it and/or modify it 
// under the terms of the GNU General Public License as published by the Free 
// Software Foundation; either version 2 of the License, or (at your option) 
// any later version.
//
// This program is distributed in the hope that it will be useful, but WITHOUT
// ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
// FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
// more details.
//
// You should have received a copy of the GNU General Public License along 
// with this program; if not, write to the Free Software Foundation, Inc., 
// 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
//
// --------------------------------------------------------------------------
// $Id$
// --------------------------------------------------------------------------

package org.jSyncManager.Conduit.NSMobileMessenger;

import org.jSyncManager.API.Conduit.*;
import org.jSyncManager.API.Protocol.NotConnectedException;
import org.jSyncManager.API.Protocol.Util.*;
import org.jSyncManager.API.Protocol.Util.StdApps.*;
import org.jSyncManager.API.Tools.*;
import org.jSyncManager.API.Threads.Synchronizer;
import org.jSyncManager.SecurityImpl.NSMobileSecurityHandler;
import org.jSyncManager.Conduit.NSMobileMessenger.DataTypes.*;
import org.jSyncManager.Conduit.NSMobileUsersDBConduit.NSMobileUsersDBConduit;
import org.jSyncManager.Conduit.NSMobileOwnerDBConduit.NSMobileOwnerDBConduit;
import org.jSyncManager.Conduit.NSMobileCalendar.*;
import org.jSyncManager.API.Protocol.DLP_Packet;
import org.jSyncManager.API.Protocol.Util.StdApps.CategoryInfo;

import java.util.*;
import javax.swing.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.text.SimpleDateFormat;

import org.apache.commons.codec.binary.Base64;

// ==========================================================================

/** NSMobile Messaging jConduit.
  * 
  * @author Brad BARCLAY &lt;bbarclay@jsyncmanager.org&gt;
  * <br>Last modified by: $Author$.
  * @version $Revision$
  */

public final class NSMobileMessenger extends AbstractConduit {

   private static final String MESSAGE_DB_TYPE = "Msg_";
   private static final String CREATOR_ID = "TAPS";

   /** A constant denoting the user directory for storing calendar-specific data.   
      */
   protected static final String MESSAGE_USERS_DIR = "NSMobileMessenger";
   
   public static final SimpleDateFormat dateParser = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
   public static final SimpleDateFormat idDateFormat = new SimpleDateFormat("yyyy-MM-dd.HH_mm_ss");
   
   private transient AuthenticatorInterface authenticator = new NSMobileAuthenticator();

   static final long serialVersionUID = -8738310911778110686L;
   
   // ..........................................................................
   
   private class MessageHeader {
      String messageID = null;
      boolean acknowledged = false;
      boolean archived = false;
      Date lastModified = null;
      int[] recipients;
      
      MessageHeader(Node messageNode) {
         if (!messageNode.getNodeName().equals("message")) throw new RuntimeException("NSMobileMessenger> Not a message node!");
         
         messageID = messageNode.getAttributes().getNamedItem("id").getNodeValue();
         
         NodeList childNodes = messageNode.getChildNodes();
         for(int i=0;i<childNodes.getLength();i++) {
            Node next = childNodes.item(i);
            if (next.getNodeName().equals("acknowledged")) {
               // Parse the acknowledged value
               acknowledged = !next.getFirstChild().getNodeValue().equals("0");
               continue;
            } // end-if
            
            if (next.getNodeName().equals("archived")) {
               // Parse the acknowledged value
               archived = !next.getFirstChild().getNodeValue().equals("0");
               continue;
            } // end-if
                    
            if (next.getNodeName().equals("lastModified")) {
               String modTime = next.getFirstChild().getNodeValue();
               try {
                  lastModified = dateParser.parse(modTime);
               } catch(java.text.ParseException e) {
                  System.err.println("We were unable to parse a header date \""+modTime+"\":\n");
                  System.err.println(e.toString());
               } // end-catch
               continue;
            } // end-if
            
            if (next.getNodeName().equals("recipients")) {
               // Parse out the recipients list
               next.normalize();
               NodeList recipientsList = next.getChildNodes();
               recipients = new int[recipientsList.getLength()/2];
               int k=0;
               for(int j=0;j<recipientsList.getLength();j++) {
                  Node temp=recipientsList.item(j).getFirstChild();
                  if (temp!=null) recipients[k++]=temp.getNodeValue().trim().hashCode();
                  else continue;
               } // end-for
            } // end-if
         } // end-for
      } // end-constructor
      
      public String toString() {
         StringBuffer sb = new StringBuffer();
         sb.append("-----------------------------------------------------------\n");
         sb.append("Message ID:     ").append(messageID).append('\n');
         sb.append("Acknowledged:   ").append(acknowledged?"TRUE":"FALSE").append('\n');
         sb.append("Archived:       ").append(archived?"TRUE":"FALSE").append('\n');
         sb.append("Last Modified:  ").append(lastModified).append('\n');
         sb.append("Recipients:     ");
         for(int i=0;i<recipients.length;i++) sb.append(recipients[i]).append(' ');
         sb.append('\n');
         
         return sb.toString();
      } // end-method
      
   } // end-inner-class
   
// ==========================================================================

   public NSMobileMessenger() {
      // No construction necessary
   } // end-constructor
   
// ==========================================================================
   
   /** Retrieves the name of the class to use as the Conduits Resource Bundle.
   * @return the fully-qualified classname of the class to use as this jConduits resource bundle.
   */
   protected String getResourceBundleName() {
      return "org.jSyncManager.Conduit.Resources.Text.NSMobileMessenger";
   } // end-method
   
// --------------------------------------------------------------------------
      
   /** This method returns the jConduits priority byte.
     * @return the jConduits priority ordering byte.
     */
   public byte getPriority() {
      return (byte) 0;
   } // end-method
   
// --------------------------------------------------------------------------
   
   /** This method is called when this jConduit is given the opportunity to synchronize.
      * @param ch the handle to the active ConduitHandler to use for synchronization.
      * @param user the user information object for the handhelds owner.
      * @exception NotConnectedException thrown if the connection to the Palm is lost during sync.
      */
   public void startSync(ConduitHandler ch, DLPUserInfo user) throws NotConnectedException {
      // Open the messsage database
      int recID;
      MessageUserRecord[] users;
      MessageOwnerRecord owner;
      HashMap usersMap;
      Hashtable existingMsgsTable = getUserIDMap(user);
      
      try {
         users = (MessageUserRecord[])ch.getProperty(NSMobileUsersDBConduit.USERS_ARRAY_KEY);
      } catch (ClassCastException e) {
         ch.postToLog("The data in key \""+NSMobileUsersDBConduit.USERS_ARRAY_KEY+"\" is not a users array!");
         return;
      } // end-catch

      try {
         owner = (MessageOwnerRecord)ch.getProperty(NSMobileOwnerDBConduit.OWNER_OBJECT_KEY);
      } catch (ClassCastException e) {
         ch.postToLog("The data in key \""+NSMobileOwnerDBConduit.OWNER_OBJECT_KEY+"\" is not an owner object.");
         return;
      } // end-catch

      try {
         usersMap = (HashMap)ch.getProperty(NSMobileUsersDBConduit.USER_IDS_MAP_KEY);
      } catch (ClassCastException e) {
         ch.postToLog("The data in key \""+NSMobileUsersDBConduit.USER_IDS_MAP_KEY+"\" is not a HashMap object.");
         return;
      } // end-catch

// .............................................................................................      
      
      // First, read this users login properties
      File userProperties=new File(NSMobileSecurityHandler.getUserPermissionFile(user.getUserName()));
      if (!userProperties.exists()) {
         ch.postToLog("User login info file \""+userProperties.toString()+"\" not found!");
         return;
      } // end-if
      
      String ploneName, plonePass, messageHdrURL, messageDataURL, messageWriteURL, messageName, authURL;
      
      try {
         FileInputStream fis = new FileInputStream(userProperties);
         
         Properties userProps = new Properties();
         userProps.load(fis);
         fis.close();      // Don't keep open file handles laying around
         
         messageHdrURL = userProps.getProperty(NSMobileSecurityHandler.MESSAGE_HEADER_URL);
         messageDataURL = userProps.getProperty(NSMobileSecurityHandler.MESSAGE_DATA_URL);
         messageWriteURL = userProps.getProperty(NSMobileSecurityHandler.MESSAGE_WRITE_URL);
         messageName =  userProps.getProperty(NSMobileSecurityHandler.MESSAGE_USER_NAME);
         authURL = userProps.getProperty(NSMobileSecurityHandler.MESSAGE_AUTH_URL);
         ploneName = userProps.getProperty(NSMobileSecurityHandler.PLONE_USERNAME);
         plonePass = userProps.getProperty(NSMobileSecurityHandler.PLONE_PASSWORD);
         
         if (!testProperty(messageHdrURL) || !testProperty(messageDataURL) || 
             !testProperty(messageWriteURL) || !testProperty(ploneName) || 
             !testProperty(plonePass) || !testProperty(messageName) || !testProperty(authURL)) {
            // Oops -- the user properties are invalid!
            ch.postToLog("This users Plone properties are not set.  Please repair and try again.");
            return;
         } // end-if
      } catch (Exception e) {
         ch.postToLog("NSMobileMessenger caught a fatal exception at (1):");
         ch.postToLog(e.toString());
         return;
      } // end-catch
            
      // .............................................................................................      

      // Authenticate with the server
      URL auth;
      try {
         auth = new URL(authURL);
      } catch (MalformedURLException e) {
         ch.postToLog("The authentication URL is invalid.  Aborting.");
         return;
      } // end-catch
      
      HashMap cookieMap = new HashMap();
      try {
         authenticator.authenticateAndGetReader(auth, ploneName, plonePass, cookieMap);
      } catch (Exception e) {
         ch.postToLog("An exception was caught attempting to authenticate with the server:");
         ch.postToLog(e.toString());
      } // end-catch
      
      // Build a string out of the cookie map
      StringBuffer sb = new StringBuffer();
      for (Iterator keys = cookieMap.keySet().iterator();keys.hasNext();) {
         String nextKey = (String)keys.next();
         String nextValue = (String)cookieMap.get(nextKey);
         sb.append(nextKey).append('=').append(nextValue).append("; ");
      } // end-for
      
      String cookieString = sb.toString();
      
      // .............................................................................................      
      
      // Connect to the server and get the incoming messages
      MessageHeader[] incomingMsgHdrs;
      try {
         ch.postToLog("  -- Reading message headers...");
         incomingMsgHdrs = getMessageHeaders(ploneName, plonePass, messageName, messageHdrURL, cookieString);
         ch.postToLog("  -- Read "+incomingMsgHdrs.length+" headers.");
      } catch (Exception e) {
         ch.postToLog("NSMobileMessenger caught an exception trying to get the users message headers:");
         ch.postToLog(e.toString());
         return;
      } // end-catch
      
      // Get the users ID map
      Hashtable idMap = getUserIDMap(user);
      
      // Open the handheld database
      byte dbID = (byte)0;
      MessageAppBlock appBlock = null;
      try {
         DLPFindDBResponse findReslt = ch.findDatabaseByName((byte)0, (byte)0, MessageAppBlock.getDatabaseName());
         dbID = ch.openDatabase(MessageAppBlock.getDatabaseName(), (byte)(DLPDatabase.READ_MODE | DLPDatabase.WRITE_MODE));
      } catch (ConduitHandlerException e) {
         if (e.getDlpException().getErrorCode()==DLP_Packet.ERR_NOT_FOUND) {
            // The database wasn't found -- create it.
            try {
               dbID = ch.createDatabase(CREATOR_ID, MESSAGE_DB_TYPE, MessageAppBlock.getDatabaseName(), (char)0, (char)1);
               
               // Now create a basic app block.
               appBlock = generateNewAppBlock();
            } catch (ConduitHandlerException e1) {
               ch.postToLog("NSMobileMessenger caught an exception trying to create the handheld message database:");
               ch.postToLog(e1.toString());
               return;
            } // end-catch
         } else {
            ch.postToLog("NSMobileMessenger caught an exception trying to open the handheld message database:");
            ch.postToLog(e.toString());
            e.printStackTrace(System.err);
            return;
         } // end-if
      } // end-catch

      // .............................................................................................      
      
      if (appBlock==null) {
         try {
            appBlock = new MessageAppBlock(ch.getApplicationBlock(dbID));
         } catch (ConduitHandlerException e) {
            if (e.getDlpException().getErrorCode()==DLP_Packet.ERR_NOT_FOUND) {
               appBlock = generateNewAppBlock();
            } else {
               ch.postToLog("We were unable to read the databases application block due to:\n"+e.toString());
               ch.postToLog("Aborting...");
               return;
            } // end-if
         } catch (ParseException pe) {
            ch.postToLog("We were unable to parse the application block.  Aborting...");
            if (dbID!=0) {
               // Close the database
               try {
                  ch.closeDatabase(dbID);
               } catch (ConduitHandlerException che0) {
                  ch.postToLog("Couldn't close the messaging database.  Skipping...");
               } finally {
                  return;
               } // end-finally
            } // end-if
         } catch (ArrayIndexOutOfBoundsException e) {
            ch.postToLog("The application application block is the wrong size.  Creating a new application block...");
            appBlock = generateNewAppBlock();
         } // end-catch
      } // end-if
      
      Date lastSyncDate = DLP_Date.seconds2Calendar(appBlock.getLastSyncDateTime()).getTime();
      
      // .............................................................................................      
      
      // Write the incoming messages to the handheld if they aren't already present
      // For each incoming message...
      for(int i=0;i<incomingMsgHdrs.length;i++) {
         // Check if the user already has it
         if (!incomingMsgHdrs[i].archived && existingMsgsTable.get(incomingMsgHdrs[i].messageID)==null && incomingMsgHdrs[i].lastModified.after(lastSyncDate)) {
            try {
               // If so, get the message content for writing
               ch.postToLog("-----------------------------------------------");
               ch.postToLog("  -- Getting message with ID: "+incomingMsgHdrs[i].messageID);
               MessageRecord msg = getMessage(ploneName, plonePass, messageName, messageDataURL, incomingMsgHdrs[i], cookieString, owner);
               ch.postToLog("  -- Writing to handheld...");
               int recordID = ch.writeRecord(dbID, (byte)0, msg);
               existingMsgsTable.put(incomingMsgHdrs[i].messageID, new Integer(recordID));
               ch.postToLog("  -- Done.");
            } catch (Exception e) {
               ch.postToLog("NSMobileMessenger caught an exception trying to write a message to the handheld message database:");
               ch.postToLog(e.toString());
               e.printStackTrace(System.err);
            } // end-catch
         } // end-if
      } // end-for
      
      // .............................................................................................      
      
      // Read all of the outgoing messages from the handheld's OUTBOX category
      try {
         ch.resetRecordIndex(dbID);
      } catch (ConduitHandlerException e) {
         ch.postToLog("We were unable to reset the record index for the message database.");
         ch.postToLog("We'll continue, but some messages may not be sent until the next sync.");
      } // end-catch

      // .............................................................................................      
      
      while(true) {
         try {
            // For each outgoing message
            MessageRecord nextOutgoing = new MessageRecord(ch.readNextRecordInCategory(dbID, MessageAppBlock.CATEGORY_OUT));

            // Write it to the server
            ch.postToLog("  -- Writing message to server with record ID "+nextOutgoing.getRecordID());
            writeMessageToServer(ploneName, plonePass, messageName, nextOutgoing, usersMap, messageWriteURL, owner, cookieString);
            
            ch.postToLog("  -- Deleting message from handheld...");

            // Delete it from the handheld
            ch.deleteRecord(dbID, (byte)0, nextOutgoing.getRecordID());
            ch.postToLog("  -- Done!");
         } catch (ConduitHandlerException e) {
            if (e.getDlpException().getErrorCode()!=DLP_Packet.ERR_NOT_FOUND) {
               ch.postToLog("NSMobileMessenger caught an exception trying to write a message to the server:");
               ch.postToLog(e.toString());
            } // end-if
            break;
         } catch (ParseException e1) {
            ch.postToLog("NSMobileMessenger was unable to parse a message from the handheld due to:");
            ch.postToLog(e1.toString());
         } catch (Exception e2) {
            ch.postToLog("NSMobileMessenger was unable to write a message to the server due to:");
            ch.postToLog(e2.toString());
         } // end-method
      } // end-while
      
      // .............................................................................................      
      
      try {
         // Do all clean-up and return.
         ch.cleanupDatabase(dbID);
         ch.resetSyncFlags(dbID);
         ch.deleteRecord(dbID, (byte)DLPRecord.DELETE_ALL_IN_CATEGORY, MessageAppBlock.CATEGORY_DELETED);

         writeUsersIDMap(existingMsgsTable, user);
         // Update the app block
         try {
            ch.postToLog("Writing the app block...");
            DLP_Date date = new DLP_Date();
            long time = date.convertToSeconds();
            ch.postToLog("Setting last sync date/time to: "+date.toString()+" ("+time+")");
            appBlock.setLastSyncDateTime(time);
            ch.writeApplicationBlock(dbID, appBlock);
         } catch (ConduitHandlerException e1) {
            ch.postToLog("We were unable to write the message application block due to exception:");
            ch.postToLog(e1.toString());
         } // end-try
         
         ch.closeDatabase(dbID);
      } catch (ConduitHandlerException e) {
         ch.postToLog("NSMobileMessenger caught an exception trying to clean-up:");
         ch.postToLog(e.toString());
      } catch (Exception e1) {
         ch.postToLog("NSMobileMessanger was unable to write the users record map to disk due to:");
         ch.postToLog(e1.toString());
         return;
      } // end-catch
      
      // Done!
      ch.postToLog("Message sync complete.");
   } // end-method
   
// --------------------------------------------------------------------------
   
   /** Constructs a configuration panel for this jConduit.
      * @return a JPanel containing whatever configuration widgets your jConduit requires.
      */
   
   protected JPanel constructConfigPanel() {
      return null;
   } // end-method
   
// --------------------------------------------------------------------------

   /** Perform this jconduit's initialization tasks.
      */
   protected void doInitialization() {
      if (authenticator == null) authenticator = new NSMobileAuthenticator();
   } // end-method
   
// --------------------------------------------------------------------------
   
   /** Writes a message to the server.
     * @param message the message to be written.
     * @param userTable the user ID matching table.
     */
   protected void writeMessageToServer(String userName, String password, String messageName, MessageRecord message, HashMap userTable, String writeUrl, MessageOwnerRecord owner, String cookieString) throws Exception {
      StringBuffer sb = new StringBuffer();
      sb.append("<?xml version=\"1.0\"?>\n");
      sb.append("<messages xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Messages.xsd\">\n");
      sb.append("<message id=\"from_").append(messageName).append('.');
      sb.append(idDateFormat.format(message.getMessageDateAsCalendar().getTime())).append("\" sender=\"").append((String)userTable.get(new Integer(owner.getUserID()))).append("\">\n");
      sb.append("<lastModified>").append(dateParser.format(message.getMessageDateAsCalendar().getTime())).append("</lastModified>\n");
      
      // Cycle through the recipients
      sb.append("<recipients>\n");
      for(int i=0;i<message.getRecipientCount();i++) {
         sb.append("<recipient>").append((String)userTable.get(new Integer(message.getRecipientAtIndex(i)))).append("</recipient>\n");
      } // end-for
      sb.append("</recipients>\n");

      sb.append("<textFormat>text/plain</textFormat>\n");
      
      // Decrypt the subject first
      SecretKeySpec sks = new SecretKeySpec(owner.getEncryptionKey(), "Blowfish");
      
      Cipher c = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
      c.init(Cipher.DECRYPT_MODE, sks);
      byte[] decryptedMsgData = c.doFinal(message.getMessageData());
      byte[] decryptedSubject = c.doFinal(message.getMessageSubject());
      
      byte[] encodedSubject = Base64.encodeBase64Chunked(decryptedSubject);
      sb.append("<subject>\n").append(new String(encodedSubject)).append("</subject>\n");
      
      byte[] encodedBody = Base64.encodeBase64Chunked(decryptedMsgData);
      sb.append("<text>\n").append(new String(encodedBody)).append("</text>\n");
      
      sb.append("<acknowledged>").append(message.testFlag(MessageRecord.READ_FLAG)?1:0).append("</acknowledged>\n");
      sb.append("<archived>").append(message.testFlag(MessageRecord.DELETED_FLAG)?1:0).append("</archived>\n");
      sb.append("<draft>1</draft>\n");
      sb.append("</message></messages>\n");

      // The XML is now built.  Log in and write it to the server.
      URL url = new URL(writeUrl+"?userId="+messageName+"&msgs="+URLEncoder.encode(sb.toString(), "UTF-8"));
      URLConnection conn = url.openConnection();
      conn.setRequestProperty("Cookie", cookieString);
      conn.connect();
      
      InputStream is = conn.getInputStream();
      BufferedReader data = new BufferedReader(new InputStreamReader(is));
   } // end-method
   
// --------------------------------------------------------------------------
   
   /**  Retrieve the message headers for this user.
      * @param userName the Plone name for the synchronizing user.
      * @param password the Plone password for the synchronizing user.
      * @param hdrUrl the URL from which to retrieve the header information.
      * @return an array of MessageHeader objects.
      * @throws Exception if any processing exceptions are encountered.
      */
   protected MessageHeader[] getMessageHeaders(String userName, String password, String messageName, String hdrUrl, String cookieString) throws Exception {
      URL url = new URL(hdrUrl+"?userId="+messageName);
      
      // BufferedInputStream bis = new BufferedInputStream(authenticator.authenticateAndGetReader(url, userName, password));
      URLConnection conn = url.openConnection();
      conn.setRequestProperty("Cookie", cookieString);
      conn.connect();
      BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
      
      // Read in the XML data
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bis);
      NodeList nodes = doc.getElementsByTagName("message");
      
      MessageHeader[] ret = new MessageHeader[nodes.getLength()];
      
      for(int i=0;i<nodes.getLength();i++) {
         ret[i]=new MessageHeader(nodes.item(i));
      } // end-for
      
      return ret;
   } // end-method
   
// --------------------------------------------------------------------------
   
   /** Tests to see if the specified property is valid.
      * This method tests to see that the provided string isn't null, isn't equal to "", and 
      * isn't set the NSMobileSecurityHandler.UNSET_VALUE.
      * @return <B>true</B> if this value is valid, <B>false</B> otherwise.
      */
   protected boolean testProperty(String p) {
      return p!=null && !p.equals("") && !p.equals(NSMobileSecurityHandler.UNSET_VALUE);
   } // end-method
   
// --------------------------------------------------------------------------
   
   /** Creates a filename based on the DLPUserInfo object provided.
      * This method is used by the ID Map reading and writing methods to ensure a consistant, unique filename.
      * @param user the DLPUserInfo for the user to compute a filename for.
      * @return a String containing the unique filename for the user.
      */
   
   protected String getUserFileName(DLPUserInfo user) {
      StringBuffer sb = new StringBuffer();
      sb.append(Synchronizer.getHomeDirectory()).append(System.getProperty("file.separator")).append(MESSAGE_USERS_DIR).append(System.getProperty("file.separator"));
      sb.append("NSMobileMessenger.");
      sb.append(StringManipulator.generateValidFilename(user.getUserName().replace(' ','_'))).append(".");
      sb.append(user.getUserID());
      
      return sb.toString();
   } // end-method
   
// --------------------------------------------------------------------------
   
   /** Retrieves the hashtable mapping Zope and Handheld record IDs for the specified user.
      * @param user the DLPUserInfo for the user to retreive the ID map for.
      * @return a Hashtable containing the ID map for the specified user.
      */
   protected Hashtable getUserIDMap(DLPUserInfo user) {
      // Try to read and deserialize the record ID map for this user.
      
      try {
         FileInputStream fis = new FileInputStream(getUserFileName(user));
         ObjectInputStream ois = new ObjectInputStream(fis);
         return (Hashtable)ois.readObject();
      } catch (Exception e) {
         // No hashmap for this user, so create a new one.
         return new Hashtable();
      } // end-catch
   } // end-method
   
// --------------------------------------------------------------------------
   
   /** Serializes this users record ID map to disk for later retrieval.
      * @param hash the Hashtable to be written.
      * @param user the DLPUserInfo object for the user to write the hashtable for.
      */
   protected void writeUsersIDMap(Hashtable hash, DLPUserInfo user) throws FileNotFoundException, IOException {
      FileOutputStream fos = new FileOutputStream(getUserFileName(user));
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(hash);
      oos.close();
      fos.close();
   } // end-method
   
// --------------------------------------------------------------------------
   
   /** Gets the specified message content from the server.
     * @param username the username to log in as.
     * @param password the user's password.
     * @param msgUrl the URL to retrieve the message from.
     * @param hdr the header of the message to retrieve.
     */
   protected MessageRecord getMessage(String username, String password, String messageName, String msgUrl, MessageHeader hdr, String cookieString, MessageOwnerRecord owner) throws Exception {
      URL url = new URL(msgUrl+"?userId="+messageName+"&msgId="+hdr.messageID);
      
      //BufferedInputStream bis = new BufferedInputStream(authenticator.authenticateAndGetReader(url, username, password));
      URLConnection conn = url.openConnection();
      conn.setRequestProperty("Cookie", cookieString);
      conn.connect();
      BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
      
      // Read in the XML data
      Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(bis);
      NodeList nodes = doc.getElementsByTagName("message");

      // Convert the message node into a MessageRecord and return.
      
      return nodeAsMessageRecord(nodes.item(0), hdr, username, owner);
   } // end-method
   
// --------------------------------------------------------------------------

   protected MessageRecord nodeAsMessageRecord(Node n, MessageHeader hdr, String username, MessageOwnerRecord owner) throws Exception {
      // Get the id and sender attributes
      NamedNodeMap attributes = n.getAttributes();

      String id = attributes.getNamedItem("id").getNodeValue();
      
      String sender = attributes.getNamedItem("sender").getNodeValue();
      int senderID = sender.hashCode();
      
      // Get the sub-nodes
      byte[] descData = null;
      byte[] data = null;
      
      NodeList nodes = n.getChildNodes();
      for(int i=0;i<nodes.getLength();i++) {
         Node nextNode = nodes.item(i);
         if (nextNode.getNodeName().equals("text")) {
            // Get its child node
            data = nextNode.getFirstChild().getNodeValue().getBytes();
         } // end-if
         if (nextNode.getNodeName().equals("subject")) {
            // Get its child node
            descData = nextNode.getFirstChild().getNodeValue().getBytes();
         } // end-if
      } // end-for

      byte[] tmpSubj = Base64.decodeBase64(descData);
      byte[] tmpData = Base64.decodeBase64(data);

      String msgData = new String(tmpData).replaceAll("\r\n", "\n");
      
      // Encrypt the data using the users key
      SecretKeySpec sks = new SecretKeySpec(owner.getEncryptionKey(), "Blowfish");
      
      byte[] encryptedMsgData;
      byte[] encryptedSubject;
      Cipher c = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
      c.init(Cipher.ENCRYPT_MODE, sks);
      encryptedMsgData = c.doFinal(padArray8(msgData.getBytes()));
      encryptedSubject = c.doFinal(padArray8(tmpSubj));

      return new MessageRecord(hdr.lastModified, senderID, (char)0, hdr.recipients, encryptedSubject, encryptedMsgData);
   } // end-method
   
   // --------------------------------------------------------------------------
   
   private MessageAppBlock generateNewAppBlock() {
      MessageAppBlock appBlock = new MessageAppBlock();
      
      CategoryInfo inbox = new CategoryInfo();
      inbox.setCategoryID(MessageAppBlock.CATEGORY_IN);
      inbox.setCategoryName("Inbox");
      inbox.setModifiedFlag(false);
      
      CategoryInfo outbox = new CategoryInfo();
      outbox.setCategoryID(MessageAppBlock.CATEGORY_OUT);
      outbox.setCategoryName("Outbox");
      outbox.setModifiedFlag(false);
      
      CategoryInfo deleted = new CategoryInfo();
      deleted.setCategoryID(MessageAppBlock.CATEGORY_DELETED);
      deleted.setCategoryName("Deleted");
      deleted.setModifiedFlag(false);
      
      appBlock.setCategoryInfo(inbox, 0);
      appBlock.setCategoryInfo(outbox, 1);
      appBlock.setCategoryInfo(deleted, 2);
      
      for(int i=3;i<16;i++) appBlock.setCategoryInfo(new CategoryInfo(), i);
      
      appBlock.setLastSyncDateTime(0);
      
      return appBlock;
   } // end-method      
   
// --------------------------------------------------------------------------

   protected byte[] padArray8(byte[] inData) {
      if (inData.length % 8 == 0) return inData;
      byte[] ret = new byte[((inData.length/8)+1)*8];
      System.arraycopy(inData, 0, ret, 0, inData.length);
      for(int i=inData.length;i<ret.length;i++) ret[i]=(byte)0;
      return ret;
   } // end-method
   
// ==========================================================================
      
} // end-class
