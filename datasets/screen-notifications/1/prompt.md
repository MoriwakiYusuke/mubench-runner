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
 * Copyright 2012 Luke Korth <korth.luke@gmail.com>
 * 
 * This file is part of Screen Notifications.
 * 
 * Screen Notifications is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * Screen Notifications is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with Screen Notifications.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.lukekorth.screennotifications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ProgressDialog;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.lukekorth.ez_loaders.EzLoader;
import com.lukekorth.ez_loaders.EzLoaderInterface;

public class AppsActivity extends FragmentActivity implements EzLoaderInterface<Data> {
	
	private ProgressDialog mLoadingDialog;
	private AppAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.apps);

        mLoadingDialog = ProgressDialog.show(AppsActivity.this, "", "Loading. Please wait...", true);
        getSupportLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.apps_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        boolean check;

        if(mAdapter != null) {
	        switch (itemId) {
	            case R.id.uncheck_all_apps:
	            	mAdapter.uncheckAll();
	            	break;
	            case R.id.inverse_apps:
	            	mAdapter.invertSelection();
	            	break;	            	
	        }
        }

        return true;
    }

	@Override
	public Loader<Data> onCreateLoader(int arg0, Bundle arg1) {
		return new EzLoader<Data>(this, "android.intent.action.PACKAGE_ADDED", this);
	}
	
	@Override
	public Data loadInBackground(int id) {
		final PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Collections.sort(packages, new ApplicationInfo.DisplayNameComparator(pm));
        
        Data data = new Data();
        data.sections = new ArrayList<Section>();
        data.apps = new App[packages.size()];
        
        String lastSection = "";
        String currentSection;
        for(int i = 0; i < packages.size(); i++) {
        	ApplicationInfo appInfo = packages.get(i);
        	
        	data.apps[i] = new App();
        	data.apps[i].name = (String) appInfo.loadLabel(pm);
        	data.apps[i].packageName = appInfo.packageName;
        	data.apps[i].icon = appInfo.loadIcon(pm);
        	
        	if(data.apps[i].name != null && data.apps[i].name.length() > 0) {
	        	currentSection = data.apps[i].name.substring(0, 1).toUpperCase();
	        	if(!lastSection.equals(currentSection)) {        		
	        		data.sections.add(new Section(i, currentSection));
	        		lastSection = currentSection;
	        	}
        	}
        }
        
        return data;
	}

	@Override
	public void onLoadFinished(Loader<Data> arg0, Data data) {
		mAdapter = new AppAdapter(this, data);
		((ListView) findViewById(R.id.appsList)).setAdapter(mAdapter);
		
		if(mLoadingDialog.isShowing())
			mLoadingDialog.cancel();         
	}
	
	@Override
	public void onLoaderReset(Loader<Data> arg0) {		
	}

	@Override
	public void onReleaseResources(Data t) {		
	}

}

class Data {
	
	ArrayList<Section> sections;
	App[] apps;
	
}

class App {
	
	String name;
	String packageName;
	Drawable icon;
	
}

class Section {
	
	int startingIndex;
	String section;
	
	public Section(int startingIndex, String section) {
		this.startingIndex = startingIndex;
		this.section = section;
	}
	
	public String toString() {
		return section;
	}
}
```

## Context

**Bug Location**: File `com/lukekorth/screennotifications/AppsActivity.java`, Method `loadInBackground(int)`
**Bug Type**: missing/exception_handling - `AppsActivity.java` calls `ApplicationInfo.loadIcon(PackageManager)` without handling potential `OutOfMemoryError`. On some platforms AppInfo.loadIcon() may throw an OutOfMemoryError.

Can you identify and fix it?

## Output Indicator
Update the ### Input Code as per the latest API specification, making necessary modifications.
Ensure the structure and format remain as close as possible to the original, but deprecated code must be updated. Output the all revised code without additional explanations or comments.
