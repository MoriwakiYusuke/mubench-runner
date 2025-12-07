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
package com.ushahidi.android.app.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class OpenGeoSmsDao implements IOpenGeoSmsDao, IOpenGeoSmsSchema{

	private SQLiteDatabase mDb;

	public OpenGeoSmsDao(SQLiteDatabase db){
		mDb = db;
	}
	private static String[] array(String...strs){
		return strs;
	}
	private static String[] array(long i){
		return new String[]{ String.valueOf(i) };
	}

	@Override
	public int getReportState(long reportId) {
		Cursor c = mDb.query(
			TABLE,
			array(STATE),
			WHERE,
			array(reportId),
			null,
			null,
			null
		);
		if ( c.getCount() < 1 ){
			return STATE_NOT_OPENGEOSMS;
		}
		c.moveToFirst();
		return c.getInt(0);
	}
	private static final String WHERE=REPORT_ID+"=?";
	@Override
	public boolean addReport(long reportId) {
		ContentValues cv = new ContentValues();
		cv.put(REPORT_ID, reportId);
		cv.put(STATE, STATE_PENDING);
		return mDb.insert(TABLE, null, cv) != -1;
	}

	@Override
	public boolean setReportState(long reportId, int state) {
		switch(state){
		case STATE_PENDING:
		case STATE_SENT:
			ContentValues cv = new ContentValues();
			cv.put(STATE, state);
			return mDb.update(TABLE, cv, WHERE, array(reportId)) > 0;
		default:
			return false;
		}

	}

	@Override
	public boolean deleteReport(long reportId) {
		return mDb.delete(TABLE, WHERE, array(reportId)) > 0;
	}
	@Override
	public boolean deleteReports() {
		mDb.delete(TABLE, null, null);
		return true;
	}

}
```

## Context

**Bug Location**: File `com/ushahidi/android/app/database/OpenGeoSmsDao.java`, Method `getReportState(long)`
**Bug Type**: missing/call - `OpenGeoSmsDao.java` uses `android.database.Cursor` without calling `Cursor.close()` after use. This leads to a resource leak where the cursor is never released.

Can you identify and fix it?

## Output Indicator
Update the ### Input Code as per the latest API specification, making necessary modifications.
Ensure the structure and format remain as close as possible to the original, but deprecated code must be updated. Output the all revised code without additional explanations or comments.
