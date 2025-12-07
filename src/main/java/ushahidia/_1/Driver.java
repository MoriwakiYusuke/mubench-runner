package ushahidia._1;

import ushahidia._1.mocks.Cursor;
import ushahidia._1.mocks.SQLiteDatabase;
import ushahidia._1.requirements.IOpenGeoSmsDao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Reflection-based driver for OpenGeoSmsDao variants.
 * Exposes all public methods of the target class.
 */
public class Driver {

    private static final String BASE_PACKAGE = "ushahidia._1";

    private final Object instance;
    private final Class<?> targetClass;
    private final SQLiteDatabase mockDb;

    public Driver(String variant) throws Exception {
        this.mockDb = new SQLiteDatabase();
        String className = BASE_PACKAGE + "." + variant + ".OpenGeoSmsDao";
        this.targetClass = Class.forName(className);
        Constructor<?> ctor = targetClass.getConstructor(SQLiteDatabase.class);
        this.instance = ctor.newInstance(mockDb);
    }

    /**
     * Configure the mock database to return a specific query result.
     * @param count number of rows to return
     * @param value the int value to return from getInt(0)
     */
    public void setQueryResult(int count, int value) {
        mockDb.setQueryResult(count, value);
    }

    /**
     * Get the last Cursor returned by the mock database.
     * Used to verify if close() was called.
     */
    public Cursor getLastCursor() {
        return mockDb.getLastCursor();
    }

    // ========== IOpenGeoSmsDao methods ==========

    public int getReportState(long reportId) {
        try {
            return (int) targetClass.getMethod("getReportState", long.class)
                    .invoke(instance, reportId);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean setReportState(long reportId, int state) {
        try {
            return (boolean) targetClass.getMethod("setReportState", long.class, int.class)
                    .invoke(instance, reportId, state);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addReport(long reportId) {
        try {
            return (boolean) targetClass.getMethod("addReport", long.class)
                    .invoke(instance, reportId);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteReport(long reportId) {
        try {
            return (boolean) targetClass.getMethod("deleteReport", long.class)
                    .invoke(instance, reportId);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteReports() {
        try {
            return (boolean) targetClass.getMethod("deleteReports")
                    .invoke(instance);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private RuntimeException rethrow(Throwable t) {
        if (t instanceof RuntimeException runtime) {
            return runtime;
        }
        return new RuntimeException(t);
    }
}
