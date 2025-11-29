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
package com.vaguehope.onosendai.update;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;

import com.vaguehope.onosendai.C;
import com.vaguehope.onosendai.provider.SendOutboxService;
import com.vaguehope.onosendai.util.BatteryHelper;
import com.vaguehope.onosendai.util.LogWrapper;

public class AlarmReceiver extends BroadcastReceiver {

	private static final int TEMP_WAKELOCK_TIMEOUT_MILLIS = 3000;

	private static final int BASE_ALARM_ID = 11000;
	private static final String KEY_ACTION = "action";
	private static final int ACTION_UPDATE = 1;
	private static final int ACTION_CLEANUP = 2;

	private static final LogWrapper LOG = new LogWrapper("AR");

	@Override
	public void onReceive (final Context context, final Intent intent) {
		final int action = intent.getExtras().getInt(KEY_ACTION, -1);
		final float bl = BatteryHelper.level(context);
		LOG.i("AlarmReceiver invoked: action=%s bl=%s.", action, bl);
		switch (action) {
			case ACTION_UPDATE:
				final boolean doUpdate = (bl > C.MIN_BAT_UPDATE);
				final boolean doSend = (bl > C.MIN_BAT_SEND);
				if (doUpdate || doSend) {
					final PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
					final WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, C.TAG);
					wl.acquire(TEMP_WAKELOCK_TIMEOUT_MILLIS);
				}
				if (doUpdate) context.startService(new Intent(context, UpdateService.class));
				if (doSend) context.startService(new Intent(context, SendOutboxService.class));
				break;
			case ACTION_CLEANUP:
				if (bl > C.MIN_BAT_CLEANUP) context.startService(new Intent(context, CleanupService.class));
				break;
			default:
				LOG.e("Unknown action: '%s'.", action);
				break;
		}
	}

	public static void configureAlarms (final Context context) {
		final AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		scheduleUpdates(context, am);
		scheduleCleanups(context, am);
		LOG.i("Alarm service configured.");
	}

	private static void scheduleUpdates (final Context context, final AlarmManager am) {
		final PendingIntent i = PendingIntent.getBroadcast(context, BASE_ALARM_ID + ACTION_UPDATE,
				new Intent(context, AlarmReceiver.class).putExtra(KEY_ACTION, ACTION_UPDATE),
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.cancel(i);
		am.setInexactRepeating(
				AlarmManager.ELAPSED_REALTIME_WAKEUP,
				SystemClock.elapsedRealtime(),
				AlarmManager.INTERVAL_FIFTEEN_MINUTES,
				i);
	}

	private static void scheduleCleanups (final Context context, final AlarmManager am) {
		final PendingIntent i = PendingIntent.getBroadcast(context, BASE_ALARM_ID + ACTION_CLEANUP,
				new Intent(context, AlarmReceiver.class).putExtra(KEY_ACTION, ACTION_CLEANUP),
				PendingIntent.FLAG_CANCEL_CURRENT);
		am.cancel(i);
		am.setInexactRepeating(
				AlarmManager.ELAPSED_REALTIME,
				SystemClock.elapsedRealtime(),
				AlarmManager.INTERVAL_DAY,
				i);
	}

}
```

## Context

**Bug Location**: File `com/vaguehope/onosendai/update/AlarmReceiver.java`, Method `onReceive(Context, Intent)`
**Bug Type**: missing/condition/value_or_state - `AlarmReceiver.java` uses the broadcast `Context` directly without first calling `Context.getApplicationContext()` when registering services and filters, which can lead to the receiver operating on an arbitrary, short-lived context.

Can you identify and fix it?

## Output Indicator
Update the ### Input Code as per the latest API specification, making necessary modifications.
Ensure the structure and format remain as close as possible to the original, but deprecated code must be updated. Output the all revised code without additional explanations or comments.
