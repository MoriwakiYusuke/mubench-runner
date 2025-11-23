package gnucrasha._1a.fixed;

import gnucrasha._1a.mocks.Intent;
import gnucrasha._1a.mocks.PasscodeLockScreenActivity;
import gnucrasha._1a.mocks.PreferenceManager;
import gnucrasha._1a.mocks.SharedPreferences;
import gnucrasha._1a.mocks.SherlockFragmentActivity;
import gnucrasha._1a.mocks.GnuCashApplication;
import gnucrasha._1a.mocks.UxArgument;

/**
 * This activity used as the parent class for enabling passcode lock
 *
 * @author Oleksandr Tyshkovets <olexandr.tyshkovets@gmail.com>
 * @see org.gnucash.android.ui.account.AccountsActivity
 * @see org.gnucash.android.ui.transaction.TransactionsActivity
 */
public class PassLockActivity extends SherlockFragmentActivity {

    private static final String TAG = "PassLockActivity";

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.getBoolean(UxArgument.ENABLED_PASSCODE, false) && !isSessionActive()) {
            startActivity(new Intent(this, PasscodeLockScreenActivity.class)
                    .setAction(getIntent().getAction())
                    .putExtra(UxArgument.PASSCODE_CLASS_CALLER, this.getClass().getName())
                    .putExtra(UxArgument.SELECTED_ACCOUNT_UID,
                            getIntent().getStringExtra(UxArgument.SELECTED_ACCOUNT_UID))
            );
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        GnuCashApplication.PASSCODE_SESSION_INIT_TIME = System.currentTimeMillis();
    }

    /**
     * @return {@code true} if passcode session is active, and {@code false} otherwise
     */
    private boolean isSessionActive() {
        return System.currentTimeMillis() - GnuCashApplication.PASSCODE_SESSION_INIT_TIME
                < GnuCashApplication.SESSION_TIMEOUT;
    }

}
