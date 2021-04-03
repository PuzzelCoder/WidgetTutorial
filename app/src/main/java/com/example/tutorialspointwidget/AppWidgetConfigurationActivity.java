package com.example.tutorialspointwidget;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class AppWidgetConfigurationActivity extends Activity {
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private static final String TAG = AppWidgetConfigurationActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "---MYAPPWIDGET---");
//        setResult(RESULT_CANCELED);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        ComponentName name = new ComponentName(MyAppliactionClass.getAppContext(), WidgetConfig.class);
        int[] appWidgetIDs = appWidgetManager.getAppWidgetIds(name);
        Log.d(TAG, "---INSTALLED WIDGETS---" + appWidgetIDs.length);
        if (appWidgetIDs.length == 1) {
            RemoteViews views = new RemoteViews(MyAppliactionClass.getAppContext().getPackageName(), R.layout.adapterview);
            appWidgetManager.updateAppWidget(appWidgetIDs, views);
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
        } else {
            /*Remove rest of the widgets using AppWidgetHost*/
//            int hostID = 22; // Itc oould be any value
//            AppWidgetHost host = new AppWidgetHost(MyAppliactionClass.getAppContext(), hostID);
//            host.deleteAppWidgetId(appWidgetIDs[0]);
//            AppWidgetProviderInfo info = appWidgetManager.getAppWidgetInfo(appWidgetIDs[1]);

            Log.d(TAG, "onCreate: widgets alredy present");
            Toast.makeText(MyAppliactionClass.getAppContext(), "Widget Already Present", Toast.LENGTH_LONG).show();

        }
        finish();
    }
}
