package logblock_logblock_2._15;

import logblock_logblock_2._15.requirements.entry.BlobEntry;
import logblock_logblock_2._15.requirements.entry.blob.PaintingBlob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;

/**
 * Driver for testing DataOutputStream flush/close behavior with BlobEntry.
 * Uses reflection to invoke the paintingTest method on different variants.
 */
public class Driver {

    private static final String BASE_PACKAGE = "logblock_logblock_2._15";

    private final Object blobTestInstance;
    private final Class<?> blobTestClass;

    public Driver(String variant) throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".BlobTest";
        this.blobTestClass = Class.forName(className);
        this.blobTestInstance = blobTestClass.getDeclaredConstructor().newInstance();
    }

    /**
     * Execute the paintingTest method which tests DataOutputStream flush/close behavior.
     */
    public void paintingTest() throws Exception {
        Method method = blobTestClass.getMethod("paintingTest");
        try {
            method.invoke(blobTestInstance);
        } catch (java.lang.reflect.InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw new RuntimeException(cause);
        }
    }

    /**
     * Utility method: Create a PaintingBlob instance.
     */
    public PaintingBlob createPaintingBlob(int id) throws Exception {
        return BlobEntry.create(id, PaintingBlob.class);
    }

    /**
     * Utility method: Write a PaintingBlob to a ByteArrayOutputStream and return the bytes.
     * This method properly flushes and closes the DataOutputStream.
     */
    public byte[] writeBlobToBytes(PaintingBlob blob) throws Exception {
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        DataOutputStream outStream = new DataOutputStream(byteOutput);
        blob.write(outStream);
        outStream.flush();
        outStream.close();
        return byteOutput.toByteArray();
    }

    /**
     * Utility method: Read a PaintingBlob from bytes.
     */
    public PaintingBlob readBlobFromBytes(byte[] data) throws Exception {
        DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(data));
        PaintingBlob blob = BlobEntry.create(1, PaintingBlob.class);
        blob.read(inputStream);
        return blob;
    }
}
