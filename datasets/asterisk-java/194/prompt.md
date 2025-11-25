## Instruction
You are a software engineer specializing in REST API.
Use the guidelines below to make any necessary modifications.

### Modification Procedure
0. First, familiarise yourself with the following steps and ### Notes.
1. Check the technical specifications of the Java API that you have studied or in the official documentation. If you don't know, output the ### Input Code as it is.
2. Based on the technical specifications of the Java API you have reviewed in step 1, identify the code according to the deprecated specifications contained in the ### Input Code. In this case, the deprecated specifications are the Java API calls that have been deprecated. If no code according to the deprecated specification is found, identify code that is not based on best practice. If you are not sure, output the ### Input Code as it is.
3. If you find code according to the deprecated specification or not based on best practice in step 2, check the technical specifications in the Java API that you have studied or in the official documentation. If you are not sure, output the ### Input Code as it is.
4. With attention to the points listed in ### Notes below, modify the code identified in step 2 to follow the recommended specification analysed in step 3.
5. Verify again that the modified code works correctly.
6. If you determine that it works correctly, output the modified code.
7. If it is judged to fail, output the ### Input Code as it is.
8. If you are not sure, output the ### Input Code as it is.

### Notes.
- You must follow the ## Context.

## Input Code
```java
/*
 *  Copyright 2004-2006 Stefan Reuter
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package org.asteriskjava.manager.event;

import java.net.InetAddress;

/**
 * An RTCPReceivedEvent is triggered when Asterisk receives an RTCP message.<p>
 * Available since Asterisk 1.6<p>
 * It is implemented in <code>main/rtp.c</code>
 *
 * @author srt
 * @version $Id$
 * @since 1.0.0
 */
public class RtcpReceivedEvent extends AbstractRtcpEvent
{
    private static final long serialVersionUID = 2L;

    /**
     * Payload identifier for a sender report.
     */
    public static final int PT_SENDER_REPORT = 200;

    /**
     * Payload identifier for a receiver report.
     */
    public static final int PT_RECEIVER_REPORT = 201;
    public static final int PT_H261_FUR = 192;

    private InetAddress fromAddress;
    private Integer fromPort;
    private Long pt;
    private Long receptionReports;
    private Long senderSsrc;
    private Long packetsLost;
    private Long highestSequence;
    private Long sequenceNumberCycles;
    private Double lastSr;
    private Double rtt;

    private String channel;
    private String language;    
    private String report0SequenceNumberCycles;
    private String ssrc;
    private String linkedId;
    private String report0lsr;
    private Long sentOctets;
    private String report0Sourcessrc;
    private Double report0dlsr;
    private String uniqueid;
    private Integer reportCount;
    private Integer report0CumulativeLost;
    private Integer report0FractionLost;
    private Integer report0iaJitter;
    private Integer report0HighestSequence;
    private InetAddress toAddress;
    private Integer toPort;
    private String sentntp;
    private Integer sentPackets;
    private Long sentrtp;
    private String accountCode;


    public RtcpReceivedEvent(Object source)
    {
        super(source);
    }

    /**
     * Returns the IP address the RTCP message has been received from.
     *
     * @return the IP address the RTCP message has been received from.
     */
    public InetAddress getFromAddress()
    {
        return fromAddress;
    }

    /**
     * Returns the port of the RTCP message has been received from.
     *
     * @return the port of the RTCP message has been received from.
     */
    public Integer getFromPort()
    {
        return fromPort;
    }

    public void setFrom(String from)
    {
        // Format is "%s:%d"
        this.fromAddress = stringToAddress(from);
        this.fromPort = stringToPort(from);
    }

    /**
     * Returns the port the RTCP message has been sent to.
     *
     * @return the port the RTCP message has been sent to.
     */
    public Integer getToPort()
    {
        return toPort;
    }

    public void setTo(String to)
    {
        // Format is "%s:%d"
        this.toAddress = stringToAddress(to);
        this.toPort = stringToPort(to);
    }

    /**
     * Indicates the format of the payload, typical values are 200 for sender reports and
     * 201 for receiver reports.
     *
     * @return the format of the payload.
     * @see #PT_SENDER_REPORT
     * @see #PT_RECEIVER_REPORT
     */
    public Long getPt()
    {
        return pt;
    }

    public void setPt(String ptString)
    {
        // Format is "PT: %d(%s)"
        if (ptString == null || ptString.length() == 0)
        {
            this.pt = null;
            return;
        }

        if (ptString.indexOf('(') > 0)
        {
            this.pt = Long.parseLong(ptString.substring(0, ptString.indexOf('(')));
        }
        else
        {
            this.pt = Long.parseLong(ptString);
        }
    }

    public Long getReceptionReports()
    {
        return receptionReports;
    }

    public void setReceptionReports(Long receptionReports)
    {
        this.receptionReports = receptionReports;
    }

    /**
     * Returns the synchronization source identifier of the sender.
     *
     * @return the synchronization source identifier of the sender.
     */
    public Long getSenderSsrc()
    {
        return senderSsrc;
    }

    public void setSenderSsrc(Long senderSsrc)
    {
        this.senderSsrc = senderSsrc;
    }

    /**
     * Returns the number of packets lost so far.
     *
     * @return the number of packets lost.
     */
    public Long getPacketsLost()
    {
        return packetsLost;
    }

    public void setPacketsLost(Long packetsLost)
    {
        this.packetsLost = packetsLost;
    }

    public Long getHighestSequence()
    {
        return highestSequence;
    }

    public void setHighestSequence(Long highestSequence)
    {
        this.highestSequence = highestSequence;
    }

    public Long getSequenceNumberCycles()
    {
        return sequenceNumberCycles;
    }

    public void setSequenceNumberCycles(Long sequenceNumberCycles)
    {
        this.sequenceNumberCycles = sequenceNumberCycles;
    }

    public Double getLastSr()
    {
        return lastSr;
    }

    public void setLastSr(Double lastSr)
    {
        this.lastSr = lastSr;
    }

    /**
     * Returns the round trip time.
     *
     * @return the round trip time in seconds, may be <code>null</code>.
     */
    public Double getRtt()
    {
        return rtt;
    }

    public void setRtt(String rttString)
    {
        this.rtt = secStringToDouble(rttString);
    }

    public Long getRttAsMillseconds()
    {
    	return (long) (rtt * 1000);
    }

    public String getChannel()
    {
        return channel;
    }

    public void setChannel(String channel)
    {
        this.channel = channel;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getReport0SequenceNumberCycles()
    {
        return report0SequenceNumberCycles;
    }

    public void setReport0SequenceNumberCycles(String report0SequenceNumberCycles)
    {
        this.report0SequenceNumberCycles = report0SequenceNumberCycles;
    }

    public String getSsrc()
    {
        return ssrc;
    }

    public void setSsrc(String ssrc)
    {
        this.ssrc = ssrc;
    }

    public String getReport0lsr()
    {
        return report0lsr;
    }

    public void setReport0lsr(String report0lsr)
    {
        this.report0lsr = report0lsr;
    }

    public Long getSentOctets()
    {
        return sentOctets;
    }

    public void setSentOctets(Long sentOctets)
    {
        this.sentOctets = sentOctets;
    }

    public String getReport0Sourcessrc()
    {
        return report0Sourcessrc;
    }

    public void setReport0Sourcessrc(String report0Sourcessrc)
    {
        this.report0Sourcessrc = report0Sourcessrc;
    }

    public Double getReport0dlsr()
    {
        return report0dlsr;
    }

    public void setReport0dlsr(Double report0dlsr)
    {
        this.report0dlsr = report0dlsr;
    }

    public String getUniqueid()
    {
        return uniqueid;
    }

    public void setUniqueid(String uniqueid)
    {
        this.uniqueid = uniqueid;
    }

    public Integer getReport0CumulativeLost()
    {
        return report0CumulativeLost;
    }

    public void setReport0CumulativeLost(Integer report0CumulativeLost)
    {
        this.report0CumulativeLost = report0CumulativeLost;
    }

    public Integer getReport0FractionLost()
    {
        return report0FractionLost;
    }

    public void setReport0FractionLost(Integer report0FractionLost)
    {
        this.report0FractionLost = report0FractionLost;
    }

    public Integer getReport0iaJitter()
    {
        return report0iaJitter;
    }

    public void setReport0iaJitter(Integer report0iaJitter)
    {
        this.report0iaJitter = report0iaJitter;
    }

    public InetAddress getToAddress()
    {
        return toAddress;
    }

    public String getSentntp()
    {
        return sentntp;
    }

    public void setSentntp(String sentntp)
    {
        this.sentntp = sentntp;
    }

    public Long getSentrtp()
    {
        return sentrtp;
    }

    public void setSentrtp(Long sentrtp)
    {
        this.sentrtp = sentrtp;
    }

    public Integer getReportCount()
    {
        return reportCount;
    }

    public void setReportCount(Integer reportCount)
    {
        this.reportCount = reportCount;
    }

    public Integer getReport0HighestSequence()
    {
        return report0HighestSequence;
    }

    public void setReport0HighestSequence(Integer report0HighestSequence)
    {
        this.report0HighestSequence = report0HighestSequence;
    }

    public String getLinkedId()
    {
        return linkedId;
    }

    public void setLinkedId(String linkedId)
    {
        this.linkedId = linkedId;
    }

    public Integer getSentPackets()
    {
        return sentPackets;
    }

    public void setSentPackets(Integer sentPackets)
    {
        this.sentPackets = sentPackets;
    }

    public String getAccountCode()
    {
        return accountCode;
    }

    public void setAccountCode(String accountCode)
    {
        this.accountCode = accountCode;
    }
}
```

## Context
In this class, the method 'public void setPt(String ptString)' has the following issue '`RtcpReceivedEvent.java` calls `java.lang.long.parseLong` without first checking whether the argument parses. This lead to an uncaught `NumberFormatException`: [Oracle Java 7 API specification](http://docs.oracle.com/javase/7/docs/api/java/lang/Long.html#parseLong%28java.lang.String,%20int%29). This pull request adds a check with a more helpful exception message. Kindly let me know if you want me to change the message'.
Can you idenfity and /fix it?

## Output Indicator
Update the ### Input Code as per the latest API specification, making necessary modifications.
Ensure the structure and format remain as close as possible to the original, but deprecated code must be updated. Output the all revised code without additional explanations or comments.