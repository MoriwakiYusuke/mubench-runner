package calligraphy._2;

import calligraphy._2.mocks.AttributeSet;
import calligraphy._2.mocks.CalligraphyConfig;
import calligraphy._2.mocks.Context;
import calligraphy._2.mocks.Resources;
import calligraphy._2.mocks.TextView;
import calligraphy._2.mocks.Typeface;
import calligraphy._2.mocks.TypedArray;
import calligraphy._2.mocks.TypedValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver to exercise CalligraphyUtils variants without
 * modifying the original sources.
 */
public class Driver {

    private final Class<?> targetClass;

    public Driver(String targetClassName) {
        this.targetClass = loadTargetClass(targetClassName);
    }

    private static Class<?> loadTargetClass(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Target class not found: " + name, e);
        }
    }

    public boolean applyFont(TextView textView, Typeface typeface) {
        return (Boolean) invoke("applyFontToTextView",
                new Class<?>[]{TextView.class, Typeface.class},
                textView, typeface);
    }

    public boolean applyFont(Context context, TextView textView, String fontPath) {
        return (Boolean) invoke("applyFontToTextView",
                new Class<?>[]{Context.class, TextView.class, String.class},
                context, textView, fontPath);
    }

    public void applyFont(Context context, TextView textView, CalligraphyConfig config) {
        invoke("applyFontToTextView",
                new Class<?>[]{Context.class, TextView.class, CalligraphyConfig.class},
                context, textView, config);
    }

    public void applyFont(Context context, TextView textView, CalligraphyConfig config, String textViewFont) {
        invoke("applyFontToTextView",
                new Class<?>[]{Context.class, TextView.class, CalligraphyConfig.class, String.class},
                context, textView, config, textViewFont);
    }

    public String pullFontPath(Context context, AttributeSet attrs, int attributeId) {
        return (String) invoke("pullFontPath",
                new Class<?>[]{Context.class, AttributeSet.class, int.class},
                context, attrs, attributeId);
    }

    public InvocationResult pullFontPathFromStyleWithFallback(String fallbackText) {
        TypedArray typedArray = new ThrowingGetStringTypedArray(fallbackText);
        Context context = new StyledContext(typedArray);
        AttributeSet attrs = new AttributeSet();
        return invokeSafely("pullFontPathFromStyle",
                new Class<?>[]{Context.class, AttributeSet.class, int.class},
                context, attrs, 1);
    }

    public InvocationResult pullFontPathFromThemeWithFallback(String fallbackText) {
        Resources.Theme theme = new ConfigurableTheme(100,
                new ThrowingGetStringTypedArray(fallbackText),
                null);
        Context context = new ThemeContext(theme);
        return invokeSafely("pullFontPathFromTheme",
                new Class<?>[]{Context.class, int.class, int.class},
                context, 10, 11);
    }

    public InvocationResult pullFontPathFromThemeWithMissingStyle() {
        Resources.NotFoundException exception = new Resources.NotFoundException("missing style");
        Resources.Theme theme = new ConfigurableTheme(0,
                new StaticTypedArray(null),
                exception);
        Context context = new ThemeContext(theme);
        return invokeSafely("pullFontPathFromTheme",
                new Class<?>[]{Context.class, int.class, int.class},
                context, 10, 11);
    }

    private Object invoke(String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = targetClass.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(null, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw new RuntimeException("Invocation failed", cause);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to invoke method: " + methodName, e);
        }
    }

    private InvocationResult invokeSafely(String methodName, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = targetClass.getDeclaredMethod(methodName, parameterTypes);
            method.setAccessible(true);
            Object result = method.invoke(null, args);
            return InvocationResult.success(result);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            return InvocationResult.failure(cause);
        } catch (ReflectiveOperationException e) {
            return InvocationResult.failure(e);
        }
    }

    public static final class InvocationResult {
        private final Object value;
        private final Throwable error;

        private InvocationResult(Object value, Throwable error) {
            this.value = value;
            this.error = error;
        }

        public static InvocationResult success(Object value) {
            return new InvocationResult(value, null);
        }

        public static InvocationResult failure(Throwable error) {
            return new InvocationResult(null, error);
        }

        public boolean isSuccess() {
            return error == null;
        }

        public Object getValue() {
            return value;
        }

        public Throwable getError() {
            return error;
        }
    }

    private static final class StaticTypedArray extends TypedArray {
        private final String value;

        StaticTypedArray(String value) {
            this.value = value;
        }

        @Override
        public String getString(int index) {
            return value;
        }

        @Override
        public CharSequence getText(int index) {
            return value;
        }

        @Override
        public int getIndexCount() {
            return value == null ? 0 : 1;
        }
    }

    private static final class ThrowingGetStringTypedArray extends TypedArray {
        private final String fallback;

        ThrowingGetStringTypedArray(String fallback) {
            this.fallback = fallback;
        }

        @Override
        public String getString(int index) {
            throw new RuntimeException("getString failure");
        }

        @Override
        public CharSequence getText(int index) {
            return fallback;
        }

        @Override
        public int getIndexCount() {
            return fallback == null ? 0 : 1;
        }
    }

    private static final class StyledContext extends Context {
        private final TypedArray typedArray;

        StyledContext(TypedArray typedArray) {
            this.typedArray = typedArray;
        }

        @Override
        public TypedArray obtainStyledAttributes(AttributeSet attrs, int[] attributeIds) {
            return typedArray;
        }
    }

    private static final class ThemeContext extends Context {
        private final Resources.Theme theme;

        ThemeContext(Resources.Theme theme) {
            this.theme = theme;
        }

        @Override
        public Resources.Theme getTheme() {
            return theme;
        }
    }

    private static final class ConfigurableTheme extends Resources.Theme {
        private final int resolvedResourceId;
        private final TypedArray typedArray;
        private final RuntimeException obtainException;

        ConfigurableTheme(int resolvedResourceId, TypedArray typedArray, RuntimeException obtainException) {
            this.resolvedResourceId = resolvedResourceId;
            this.typedArray = typedArray;
            this.obtainException = obtainException;
        }

        @Override
        public void resolveAttribute(int styleId, TypedValue outValue, boolean resolveRefs) {
            outValue.resourceId = resolvedResourceId;
        }

        @Override
        public TypedArray obtainStyledAttributes(int resId, int[] attrs) {
            if (obtainException != null) {
                throw obtainException;
            }
            return typedArray;
        }
    }
}
