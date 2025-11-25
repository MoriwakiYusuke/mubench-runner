## Instruction
You are a software engineer specializing in REST API.
Use the guidelines below to make any necessary modifications.

### Modification Procedure
0. First, familiarise yourself with the following steps and ### Notes.
1. Check the technical specifications of the Java API that you have studied or in the official documentation. If you don't know, output the ### Input Code as it is.
2. Based on the technical specifications of the Java API you have reviewed in step 1, identify the code according to the deprecated specifications contained in the ### Input Code. In this case, the deprecated specifications are the Java API calls that have been deprecated. If no code according to the deprecated specification is found, identify code that is not based on best practice. If you are not sure, output the ### Input Code as it is.
3. If you find code according to the deprecated specification or not based on best practice in step 2, check the technical specifications in the Java API that you have studied or in the official documentation. If you are not sure, output the ### Input Code as it is.
4. With attention to the points listed in ### Notes below, modify the code identified in step 2 to follow the recommended specification analysed in step 3.
5. Verify again that the modified code works correctly.
6. If you determine that it works correctly, output the modified code.
7. If it is judged to fail, output the ### Input Code as it is.
8. If you are not sure, output the ### Input Code as it is.

### Notes.
- You must follow the ## Context.

## Input Code
```java
/*
 * Copyright (c) 2014 Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gnucash.android.ui.passcode;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import org.gnucash.android.R;
import org.gnucash.android.app.GnuCashApplication;
import org.gnucash.android.ui.UxArgument;

/**
 * Activity for displaying and managing the passcode lock screen.
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 */
public class PasscodeLockScreenActivity extends SherlockFragmentActivity
        implements KeyboardFragment.OnPasscodeEnteredListener {

    private static final String TAG = "PasscodeLockScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.passcode_lockscreen);
    }

    @Override
    public void onPasscodeEntered(String pass) {
        String passcode = PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .getString(UxArgument.PASSCODE, "");
        Log.d(TAG, "Passcode: " + passcode);

        if (passcode.equals(pass)) {
            GnuCashApplication.PASSCODE_SESSION_INIT_TIME = System.currentTimeMillis();
            startActivity(new Intent()
                    .setClassName(this, getIntent().getStringExtra(UxArgument.PASSCODE_CLASS_CALLER))
                    .setAction(getIntent().getAction())
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .putExtra(UxArgument.SELECTED_ACCOUNT_UID, getIntent().getLongExtra(UxArgument.SELECTED_ACCOUNT_UID, 0L))
            );
        } else {
            Toast.makeText(this, R.string.toast_wrong_passcode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        GnuCashApplication.PASSCODE_SESSION_INIT_TIME = System.currentTimeMillis() - GnuCashApplication.SESSION_TIMEOUT;
        startActivity(new Intent(Intent.ACTION_MAIN)
                .addCategory(Intent.CATEGORY_HOME)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

}
```

## Context
In this class, the method 'public void onPasscodeEntered(String pass)' has the following issue 'Call Intent.getLongExtra() with UxArgument.ORIGIN_ACCOUNT_UID parameter, which fails because the UUID is a string.'.
Can you idenfity and /fix it?

## Output Indicator
Update the ### Input Code as per the latest API specification, making necessary modifications.
Ensure the structure and format remain as close as possible to the original, but deprecated code must be updated. Output the all revised code without additional explanations or comments.