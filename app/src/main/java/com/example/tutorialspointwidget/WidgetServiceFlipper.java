package com.example.tutorialspointwidget;


import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.Date;


public class WidgetServiceFlipper extends RemoteViewsService {

	private static final String TAG = WidgetServiceFlipper.class.getSimpleName();

//	final static int[] mLayoutIds = { R.layout.invisible0, R.layout.invisible1, R.layout.invisible2 ,R.layout.invisible3};
private static int myLayouts[] = {
		R.layout.invisible0, /*R.layout.invisible1, R.layout.invisible2, R.layout.invisible3,
            R.layout.invisible4, R.layout.invisible5, R.layout.invisible6, R.layout.invisible7,
            R.layout.invisible8, R.layout.invisible9, R.layout.invisible10, R.layout.invisible11,*/
		R.layout.invisible12, /*R.layout.invisible13, R.layout.invisible14, R.layout.invisible15,
            R.layout.invisible16, R.layout.invisible17, R.layout.invisible18, R.layout.invisible19,
            R.layout.invisible20, R.layout.invisible21, R.layout.invisible22, R.layout.invisible23,*/
		R.layout.invisible24
};
	@Override
	public RemoteViewsFactory onGetViewFactory(Intent intent) {
		Log.d(TAG, "onGetViewFactory()");

		return new ViewFactory(intent);
	}

	private class ViewFactory implements RemoteViewsFactory {
		
		private final int mInstanceId;
		private Date mUpdateDate = new Date();
		
		public ViewFactory(Intent intent) {
			mInstanceId = intent.getIntExtra(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID );
		}

		@Override
		public void onCreate() {
			Log.d(TAG, "onCreate()");

		}

		@Override
		public void onDataSetChanged() {
			Log.d(TAG, "onDataSetChanged()");

			mUpdateDate = new Date();
		}

		@Override
		public void onDestroy() {
			Log.d(TAG, "onDestroy()");
		}

		@Override
		public int getCount() {
			Log.d(TAG, "getCount() " + myLayouts.length);

			return myLayouts.length;
		}

		@Override
		public RemoteViews getViewAt(int position) {
			Log.d(TAG, "getViewAt()" + position);

			RemoteViews page = new RemoteViews(getPackageName(), myLayouts[position]);
//			SimpleDateFormat sdf = (SimpleDateFormat) SimpleDateFormat
//					.getDateTimeInstance();
//			page.setTextViewText(R.id.update_date, sdf.format(mUpdateDate));

			return page;
		}

		@Override
		public RemoteViews getLoadingView() {
			Log.d(TAG, "getLoadingView()");

			return new RemoteViews(getPackageName(), R.layout.loading);
		}

		@Override
		public int getViewTypeCount() {
			Log.d(TAG, "getViewTypeCount()");

			return myLayouts.length;
		}

		@Override
		public long getItemId(int position) {
			Log.d(TAG, "getItemId()");

			return position;
		}

		@Override
		public boolean hasStableIds() {
			Log.d(TAG, "hasStableIds()");

			return true;
		}

	}

}
