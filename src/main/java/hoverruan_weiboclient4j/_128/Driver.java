package hoverruan_weiboclient4j._128;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver for CoreParameters variants.
 * Provides full access to all static factory methods.
 * Returns Object to avoid type resolution issues with param classes.
 */
public class Driver {

    private final Class<?> targetClass;

    public Driver(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    // === Helper methods ===
    
    @SuppressWarnings("unchecked")
    private <T> T invokeStatic(String methodName, Class<?>[] paramTypes, Object... args) {
        try {
            Method method = targetClass.getMethod(methodName, paramTypes);
            return (T) method.invoke(null, args);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Method not found: " + methodName, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access method: " + methodName, e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException(cause);
        }
    }

    // === String parameter factory methods ===
    
    public Object actionUrl(String value) {
        return invokeStatic("actionUrl", new Class<?>[]{String.class}, value);
    }

    public Object address(String value) {
        return invokeStatic("address", new Class<?>[]{String.class}, value);
    }

    public Object addressCode(String value) {
        return invokeStatic("addressCode", new Class<?>[]{String.class}, value);
    }

    public Object capitalLetter(String value) {
        return invokeStatic("capitalLetter", new Class<?>[]{String.class}, value);
    }

    public Object category(String value) {
        return invokeStatic("category", new Class<?>[]{String.class}, value);
    }

    public Object city(String value) {
        return invokeStatic("city", new Class<?>[]{String.class}, value);
    }

    public Object commentParam(String value) {
        return invokeStatic("commentParam", new Class<?>[]{String.class}, value);
    }

    public Object content(String value) {
        return invokeStatic("content", new Class<?>[]{String.class}, value);
    }

    public Object country(String value) {
        return invokeStatic("country", new Class<?>[]{String.class}, value);
    }

    public Object domain(String value) {
        return invokeStatic("domain", new Class<?>[]{String.class}, value);
    }

    public Object extra(String value) {
        return invokeStatic("extra", new Class<?>[]{String.class}, value);
    }

    public Object keyword(String value) {
        return invokeStatic("keyword", new Class<?>[]{String.class}, value);
    }

    public Object listId(String value) {
        return invokeStatic("listId", new Class<?>[]{String.class}, value);
    }

    public Object mid(String value) {
        return invokeStatic("mid", new Class<?>[]{String.class}, value);
    }

    public Object nickname(String value) {
        return invokeStatic("nickname", new Class<?>[]{String.class}, value);
    }

    public Object phone(String value) {
        return invokeStatic("phone", new Class<?>[]{String.class}, value);
    }

    public Object pid(String value) {
        return invokeStatic("pid", new Class<?>[]{String.class}, value);
    }

    public Object postCode(String value) {
        return invokeStatic("postCode", new Class<?>[]{String.class}, value);
    }

    public Object province(String value) {
        return invokeStatic("province", new Class<?>[]{String.class}, value);
    }

    public Object query(String value) {
        return invokeStatic("query", new Class<?>[]{String.class}, value);
    }

    public Object remark(String value) {
        return invokeStatic("remark", new Class<?>[]{String.class}, value);
    }

    public Object screenName(String value) {
        return invokeStatic("screenName", new Class<?>[]{String.class}, value);
    }

    public Object sourceScreenName(String value) {
        return invokeStatic("sourceScreenName", new Class<?>[]{String.class}, value);
    }

    public Object tagName(String value) {
        return invokeStatic("tagName", new Class<?>[]{String.class}, value);
    }

    public Object targetScreenName(String value) {
        return invokeStatic("targetScreenName", new Class<?>[]{String.class}, value);
    }

    public Object title(String value) {
        return invokeStatic("title", new Class<?>[]{String.class}, value);
    }

    public Object trendName(String value) {
        return invokeStatic("trendName", new Class<?>[]{String.class}, value);
    }

    public Object urlLong(String value) {
        return invokeStatic("urlLong", new Class<?>[]{String.class}, value);
    }

    public Object urlShort(String value) {
        return invokeStatic("urlShort", new Class<?>[]{String.class}, value);
    }

    public Object font(String value) {
        return invokeStatic("font", new Class<?>[]{String.class}, value);
    }

    public Object format(String value) {
        return invokeStatic("format", new Class<?>[]{String.class}, value);
    }

    public Object lines(String value) {
        return invokeStatic("lines", new Class<?>[]{String.class}, value);
    }

    public Object offsetX(String value) {
        return invokeStatic("offsetX", new Class<?>[]{String.class}, value);
    }

    public Object offsetY(String value) {
        return invokeStatic("offsetY", new Class<?>[]{String.class}, value);
    }

    public Object polygons(String value) {
        return invokeStatic("polygons", new Class<?>[]{String.class}, value);
    }

    public Object ip(String value) {
        return invokeStatic("ip", new Class<?>[]{String.class}, value);
    }

    public Object pic(String value) {
        return invokeStatic("pic", new Class<?>[]{String.class}, value);
    }

    // === Cid methods (bug-related) ===
    
    public Object cid(long value) {
        return invokeStatic("cid", new Class<?>[]{long.class}, value);
    }

    public Object cid(String value) {
        return invokeStatic("cid", new Class<?>[]{String.class}, value);
    }

    // === Long parameter factory methods ===
    
    public Object endBirth(long value) {
        return invokeStatic("endBirth", new Class<?>[]{long.class}, value);
    }

    public Object endTime(long value) {
        return invokeStatic("endTime", new Class<?>[]{long.class}, value);
    }

    public Object id(long value) {
        return invokeStatic("id", new Class<?>[]{long.class}, value);
    }

    public Object idFromString(String value) {
        return invokeStatic("id", new Class<?>[]{String.class}, value);
    }

    public Object locRange(long value) {
        return invokeStatic("locRange", new Class<?>[]{long.class}, value);
    }

    public Object num(long value) {
        return invokeStatic("num", new Class<?>[]{long.class}, value);
    }

    public Object section(long value) {
        return invokeStatic("section", new Class<?>[]{long.class}, value);
    }

    public Object sourceUid(long value) {
        return invokeStatic("sourceUid", new Class<?>[]{long.class}, value);
    }

    public Object sourceUidFromString(String value) {
        return invokeStatic("sourceUid", new Class<?>[]{String.class}, value);
    }

    public Object startBirth(long value) {
        return invokeStatic("startBirth", new Class<?>[]{long.class}, value);
    }

    public Object startTime(long value) {
        return invokeStatic("startTime", new Class<?>[]{long.class}, value);
    }

    public Object suid(long value) {
        return invokeStatic("suid", new Class<?>[]{long.class}, value);
    }

    public Object suidFromString(String value) {
        return invokeStatic("suid", new Class<?>[]{String.class}, value);
    }

    public Object tagId(long value) {
        return invokeStatic("tagId", new Class<?>[]{long.class}, value);
    }

    public Object tagIdFromString(String value) {
        return invokeStatic("tagId", new Class<?>[]{String.class}, value);
    }

    public Object targetUid(long value) {
        return invokeStatic("targetUid", new Class<?>[]{long.class}, value);
    }

    public Object targetUidFromString(String value) {
        return invokeStatic("targetUid", new Class<?>[]{String.class}, value);
    }

    public Object templateId(long value) {
        return invokeStatic("templateId", new Class<?>[]{long.class}, value);
    }

    public Object tid(long value) {
        return invokeStatic("tid", new Class<?>[]{long.class}, value);
    }

    public Object trendId(long value) {
        return invokeStatic("trendId", new Class<?>[]{long.class}, value);
    }

    public Object uid(long value) {
        return invokeStatic("uid", new Class<?>[]{long.class}, value);
    }

    public Object uidFromString(String value) {
        return invokeStatic("uid", new Class<?>[]{String.class}, value);
    }

    public Object zoom(long value) {
        return invokeStatic("zoom", new Class<?>[]{long.class}, value);
    }

    // === Float parameter factory methods ===
    
    public Object latitude(float value) {
        return invokeStatic("latitude", new Class<?>[]{float.class}, value);
    }

    public Object longitude(float value) {
        return invokeStatic("longitude", new Class<?>[]{float.class}, value);
    }

    // === Boolean parameter factory methods ===
    
    public Object scale(boolean value) {
        return invokeStatic("scale", new Class<?>[]{boolean.class}, value);
    }

    public Object traffic(boolean value) {
        return invokeStatic("traffic", new Class<?>[]{boolean.class}, value);
    }

    // === Coordinate factory methods ===
    
    public Object centerCoordinate(String value) {
        return invokeStatic("centerCoordinate", new Class<?>[]{String.class}, value);
    }

    public Object centerCoordinateFromCoordinate(Object coordinate) {
        try {
            Class<?> coordinateClass = Class.forName("hoverruan_weiboclient4j._128.params.Coordinate");
            return invokeStatic("centerCoordinate", new Class<?>[]{coordinateClass}, coordinate);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Object centerCoordinateFromLongLat(Object longitude, Object latitude) {
        try {
            Class<?> longitudeClass = Class.forName("hoverruan_weiboclient4j._128.params.Longitude");
            Class<?> latitudeClass = Class.forName("hoverruan_weiboclient4j._128.params.Latitude");
            return invokeStatic("centerCoordinate", new Class<?>[]{longitudeClass, latitudeClass}, longitude, latitude);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Object coordinatesFromString(String value) {
        return invokeStatic("coordinates", new Class<?>[]{String.class}, value);
    }

    public Object coordinatesFromArray(Object... coordinatesParam) {
        try {
            Class<?> coordinateClass = Class.forName("hoverruan_weiboclient4j._128.params.Coordinate");
            Class<?> arrayClass = java.lang.reflect.Array.newInstance(coordinateClass, 0).getClass();
            return invokeStatic("coordinates", new Class<?>[]{arrayClass}, (Object) coordinatesParam);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // === Varargs factory methods ===
    
    public Object names(String... namesParam) {
        return invokeStatic("names", new Class<?>[]{String[].class}, (Object) namesParam);
    }

    public Object picId(String... picIdList) {
        return invokeStatic("picId", new Class<?>[]{String[].class}, (Object) picIdList);
    }

    // === Backward compatible methods ===
    
    public Object createCidFromString(String value) {
        return cid(value);
    }

    public Object createCidFromLong(long value) {
        return cid(value);
    }
}
