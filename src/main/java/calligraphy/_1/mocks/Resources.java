package calligraphy._1.mocks;

/**
 * Simplified Resources implementation.
 */
public class Resources {

    public String getResourceEntryName(int resId) {
        if (resId <= 0) {
            throw new NotFoundException("Invalid resource id: " + resId);
        }
        return "res" + resId;
    }

    public String getString(int resId) {
        return "res-" + resId;
    }

    public Theme getTheme() {
        return new Theme();
    }

    public static class Theme {
        public void resolveAttribute(int styleId, TypedValue outValue, boolean resolveRefs) {
            if (outValue != null) {
                outValue.resourceId = styleId;
            }
        }

        public TypedArray obtainStyledAttributes(int resId, int[] attrs) {
            return new TypedArray();
        }
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {
            super(message);
        }
    }
}
