package asterisk_java._194.mocks;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Provides helper methods used by the RTCP events.
 */
public abstract class AbstractRtcpEvent extends ManagerEvent {

    protected AbstractRtcpEvent(Object source) {
        super(source);
    }

    protected InetAddress stringToAddress(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        String host = value;
        int colon = value.indexOf(':');
        if (colon >= 0) {
            host = value.substring(0, colon);
        }
        if (host.isEmpty()) {
            return null;
        }
        try {
            return InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            return null;
        }
    }

    protected Integer stringToPort(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        int colon = value.lastIndexOf(':');
        if (colon < 0 || colon + 1 >= value.length()) {
            return null;
        }
        String number = value.substring(colon + 1).trim();
        if (number.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected Double secStringToDouble(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        int space = trimmed.indexOf(' ');
        if (space >= 0) {
            trimmed = trimmed.substring(0, space);
        }
        int paren = trimmed.indexOf('(');
        if (paren >= 0) {
            trimmed = trimmed.substring(0, paren);
        }
        trimmed = trimmed.trim();
        if (trimmed.isEmpty()) {
            return null;
        }
        StringBuilder digits = new StringBuilder();
        for (int i = 0; i < trimmed.length(); i++) {
            char c = trimmed.charAt(i);
            if ((c >= '0' && c <= '9') || c == '+' || c == '-' || c == '.' || c == 'e' || c == 'E') {
                digits.append(c);
            } else {
                break;
            }
        }
        if (digits.length() == 0) {
            return null;
        }
        try {
            return Double.valueOf(digits.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid seconds value: \"" + value + "\"", e);
        }
    }
}
