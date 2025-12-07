package wordpressa._3.mocks.android.text;

import wordpressa._3.mocks.android.view.View;

public interface TextWatcher {
    void beforeTextChanged(CharSequence s, int start, int count, int after);
    void onTextChanged(CharSequence s, int start, int before, int count);
    void afterTextChanged(Editable s);
}
