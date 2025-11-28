package logblock_logblock_2._15;

import logblock_logblock_2._15.requirements.entry.BlobEntry;
import logblock_logblock_2._15.requirements.entry.blob.PaintingBlob;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Driver for testing DataOutputStream flush/close behavior with BlobEntry.
 * Uses source code analysis to verify flush/close patterns.
 */
public class Driver {

    private static final String BASE_PACKAGE = "logblock_logblock_2._15";

    private final String variant;
    private final Object blobTestInstance;
    private final Class<?> blobTestClass;
    private final String sourceFilePath;

    public Driver(String variant) throws Exception {
        this.variant = variant;
        String className = BASE_PACKAGE + "." + variant + ".BlobTest";
        this.blobTestClass = Class.forName(className);
        this.blobTestInstance = blobTestClass.getDeclaredConstructor().newInstance();
        this.sourceFilePath = "src/main/java/" + className.replace('.', '/') + ".java";
    }

    /**
     * Read the source code of the BlobTest class.
     */
    public String readSourceCode() throws IOException {
        return Files.readString(Paths.get(sourceFilePath));
    }

    /**
     * Check if the source code properly closes the DataOutputStream before toByteArray().
     * The bug pattern is: calling toByteArray() without first calling close() or flush() on outStream.
     */
    public boolean hasProperStreamClose() throws IOException {
        String source = readSourceCode();
        
        // Check if outStream.close() or outStream.flush() is called before toByteArray()
        // The pattern we're looking for: outStream.close() OR outStream.flush() before byteOutput.toByteArray()
        int toByteArrayIndex = source.indexOf("toByteArray()");
        if (toByteArrayIndex == -1) {
            return false;
        }
        
        // Get the code before toByteArray()
        String codeBefore = source.substring(0, toByteArrayIndex);
        
        // Check if close() or flush() is called on outStream after write() and before toByteArray()
        int writeIndex = codeBefore.lastIndexOf(".write(");
        if (writeIndex == -1) {
            return false;
        }
        
        String codeAfterWrite = codeBefore.substring(writeIndex);
        return codeAfterWrite.contains("outStream.close()") || codeAfterWrite.contains("outStream.flush()");
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
