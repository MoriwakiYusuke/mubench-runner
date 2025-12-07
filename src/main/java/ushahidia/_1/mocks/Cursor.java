package ushahidia._1.mocks;

/**
 * Mock implementation of android.database.Cursor for testing.
 * Tracks whether close() was called.
 */
public class Cursor {
    private int count;
    private int currentPosition = -1;
    private int[] intValues;
    private boolean closed = false;

    public Cursor(int count, int... values) {
        this.count = count;
        this.intValues = values;
    }

    public int getCount() {
        return count;
    }

    public boolean moveToFirst() {
        if (count > 0) {
            currentPosition = 0;
            return true;
        }
        return false;
    }

    public int getInt(int columnIndex) {
        if (currentPosition >= 0 && intValues != null && intValues.length > 0) {
            return intValues[0];
        }
        return 0;
    }

    public void close() {
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }
}
