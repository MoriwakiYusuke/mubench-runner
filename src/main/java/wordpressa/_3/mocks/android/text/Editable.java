package wordpressa._3.mocks.android.text;

public interface Editable extends Spannable {
    Editable replace(int st, int en, CharSequence source);
    Editable replace(int st, int en, CharSequence source, int start, int end);
    Editable insert(int where, CharSequence text);
    Editable insert(int where, CharSequence text, int start, int end);
    Editable delete(int st, int en);
    Editable append(CharSequence text);
    Editable append(CharSequence text, int start, int end);
    Editable append(char text);
    void clear();
}
