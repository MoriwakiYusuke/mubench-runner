package wordpressa._3.mocks.org.wordpress.android.util;

public class WPMobileStatsUtil {
    public static final String StatsPropertyPostLocalDraft = "local_draft";
    public static final String StatsEventPostUpdatedPost = "updated_post";
    public static final String StatsPropertyPostDetailClickedKeyboardToolbarBoldButton = "bold_button";
    public static final String StatsPropertyPostDetailClickedKeyboardToolbarItalicButton = "italic_button";
    public static final String StatsPropertyPostDetailClickedKeyboardToolbarUnderlineButton = "underline_button";
    public static final String StatsPropertyPostDetailClickedKeyboardToolbarDelButton = "del_button";
    public static final String StatsPropertyPostDetailClickedKeyboardToolbarBlockquoteButton = "blockquote_button";
    public static final String StatsPropertyPostDetailClickedKeyboardToolbarMoreButton = "more_button";
    public static final String StatsPropertyPostDetailClickedKeyboardToolbarLinkButton = "link_button";
    public static final String StatsPropertyPostDetailClickedKeyboardToolbarPictureButton = "picture_button";
    
    public static void flagProperty(String property, String value) {}
    public static void trackEvent(String event) {}
    public static void trackEventForWPCom(String event) {}
    public static void trackEventForSelfHosted(String event) {}
}
