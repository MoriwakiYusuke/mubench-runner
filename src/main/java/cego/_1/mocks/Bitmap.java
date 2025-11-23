package cego._1.mocks;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Simplified bitmap storing placeholder content.
 */
public class Bitmap {

    public enum CompressFormat {
        JPEG
    }

    private final String placeholder;

    public Bitmap() {
        this("bitmap");
    }

    public Bitmap(String placeholder) {
        this.placeholder = placeholder;
    }

    public boolean compress(CompressFormat format, int quality, OutputStream stream) {
        try {
            stream.write(placeholder.getBytes(StandardCharsets.UTF_8));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
