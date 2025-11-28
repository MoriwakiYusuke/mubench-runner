package lnreadera._2.misuse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;

import lnreadera._2.mocks.SuppressLint;
import lnreadera._2.mocks.AlertDialog;
import lnreadera._2.mocks.ProgressDialog;
import lnreadera._2.mocks.DialogInterface;
import lnreadera._2.mocks.Intent;
import lnreadera._2.mocks.SharedPreferences;
import lnreadera._2.mocks.Uri;
import lnreadera._2.mocks.AsyncTask;
import lnreadera._2.mocks.Build;
import lnreadera._2.mocks.Bundle;
import lnreadera._2.mocks.Environment;
import lnreadera._2.mocks.Handler;
import lnreadera._2.mocks.PreferenceManager;
import lnreadera._2.mocks.Log;
import lnreadera._2.mocks.Gravity;
import lnreadera._2.mocks.KeyEvent;
import lnreadera._2.mocks.View;
import lnreadera._2.mocks.Button;
import lnreadera._2.mocks.EditText;
import lnreadera._2.mocks.ImageButton;
import lnreadera._2.mocks.RelativeLayout;
import lnreadera._2.mocks.TextView;
import lnreadera._2.mocks.Toast;

import lnreadera._2.mocks.SherlockActivity;
import lnreadera._2.mocks.Menu;
import lnreadera._2.mocks.MenuItem;
import lnreadera._2.mocks.Constants;
import lnreadera._2.mocks.LNReaderApplication;
import lnreadera._2.mocks.R;
import lnreadera._2.mocks.UIHelper;
import lnreadera._2.mocks.BookmarkModelAdapter;
import lnreadera._2.mocks.PageModelAdapter;
import lnreadera._2.mocks.ICallbackEventData;
import lnreadera._2.mocks.NovelsDao;
import lnreadera._2.mocks.AsyncTaskResult;
import lnreadera._2.mocks.BakaTsukiWebChromeClient;
import lnreadera._2.mocks.BakaTsukiWebViewClient;
import lnreadera._2.mocks.NonLeakingWebView;
import lnreadera._2.mocks.Util;
import lnreadera._2.mocks.BookModel;
import lnreadera._2.mocks.BookmarkModel;
import lnreadera._2.mocks.NovelCollectionModel;
import lnreadera._2.mocks.NovelContentModel;
import lnreadera._2.mocks.PageModel;
import lnreadera._2.mocks.IAsyncTaskOwner;
import lnreadera._2.mocks.LoadNovelContentTask;

public class DisplayLightNovelContentActivity extends SherlockActivity implements IAsyncTaskOwner{
	private static final String TAG = DisplayLightNovelContentActivity.class.toString();
	public NovelContentModel content;
	private NovelCollectionModel novelDetails;
	private LoadNovelContentTask task;
	private AlertDialog tocMenu = null;
	private PageModelAdapter jumpAdapter = null;
	private BookmarkModelAdapter bookmarkAdapter = null;
	private ProgressDialog dialog;
	private NonLeakingWebView webView;
	private ImageButton goTop;
	private ImageButton goBottom;
	private BakaTsukiWebViewClient client;
	private boolean restored;
	private AlertDialog bookmarkMenu = null;
	boolean isFullscreen;

	boolean dynamicButtonsEnabled;
	Runnable hideBottom;
	Runnable hideTop;
	Handler mHandler=new Handler();

	@Override
	protected void onDestroy() {
		// Missing: super.onDestroy();
		if(webView != null) webView.destroy();
	}

	@Override
	protected void onStart() {
		super.onStart();
		Log.d(TAG, "onStart Completed");
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		restored = true;
		Log.d(TAG, "onRestart Completed");
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void toggleProgressBar(boolean show) {}

	@Override
	public void setMessageTextRecursive(ICallbackEventData message) {}
}
