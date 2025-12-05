package tap_apps._1.mocks;

import java.util.Calendar;
import java.util.Date;

/**
 * Mock for org.jSyncManager.Conduit.NSMobileMessenger.DataTypes.MessageRecord
 */
public class MessageRecord extends DLPRecord {
    
    public static final int READ_FLAG = 1;
    public static final int DELETED_FLAG = 2;
    
    private Date messageDate;
    private int senderID;
    private char flags;
    private int[] recipients;
    private byte[] messageSubject;
    private byte[] messageData;
    private int recordID;
    
    public MessageRecord() {
    }
    
    public MessageRecord(byte[] data) throws ParseException {
        // Parse from byte array (mock)
        this.messageDate = new Date();
        this.senderID = 0;
        this.flags = 0;
        this.recipients = new int[0];
        this.messageSubject = new byte[0];
        this.messageData = new byte[0];
    }
    
    public MessageRecord(Date date, int senderID, char flags, int[] recipients, byte[] subject, byte[] data) {
        this.messageDate = date;
        this.senderID = senderID;
        this.flags = flags;
        this.recipients = recipients;
        this.messageSubject = subject;
        this.messageData = data;
    }
    
    public Calendar getMessageDateAsCalendar() {
        Calendar cal = Calendar.getInstance();
        if (messageDate != null) {
            cal.setTime(messageDate);
        }
        return cal;
    }
    
    public int getRecipientCount() {
        return recipients != null ? recipients.length : 0;
    }
    
    public int getRecipientAtIndex(int index) {
        return recipients[index];
    }
    
    public byte[] getMessageSubject() {
        return messageSubject;
    }
    
    public byte[] getMessageData() {
        return messageData;
    }
    
    public boolean testFlag(int flag) {
        return (flags & flag) != 0;
    }
    
    public int getRecordID() {
        return recordID;
    }
    
    public void setRecordID(int id) {
        this.recordID = id;
    }
}
