package ushahidia._1.mocks;

/**
 * Mock implementation of android.database.sqlite.SQLiteDatabase for testing.
 * Returns a trackable Cursor from query().
 */
public class SQLiteDatabase {
    private Cursor lastCursor;
    private int queryResultCount = 1;
    private int queryResultValue = 0;

    public void setQueryResult(int count, int value) {
        this.queryResultCount = count;
        this.queryResultValue = value;
    }

    public Cursor query(String table, String[] columns, String selection,
                        String[] selectionArgs, String groupBy, String having, String orderBy) {
        lastCursor = new Cursor(queryResultCount, queryResultValue);
        return lastCursor;
    }

    public long insert(String table, String nullColumnHack, ContentValues values) {
        return 1L;
    }

    public int update(String table, ContentValues values, String whereClause, String[] whereArgs) {
        return 1;
    }

    public int delete(String table, String whereClause, String[] whereArgs) {
        return 1;
    }

    public Cursor getLastCursor() {
        return lastCursor;
    }
}
