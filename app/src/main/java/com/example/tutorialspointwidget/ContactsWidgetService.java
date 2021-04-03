package com.example.tutorialspointwidget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

public class ContactsWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetRemoteViewsFactory(this.getApplicationContext(), intent));
    }

    private class WidgetRemoteViewsFactory implements RemoteViewsFactory {
        private List<User> widgetList = new ArrayList<User>();
        private Context mContext;
//        private int mAppWidgetId;



        public WidgetRemoteViewsFactory(Context context, Intent intent) {
            this.mContext = context;
//            mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                    AppWidgetManager.INVALID_APPWIDGET_ID);
//            Log.d("AppWidgetId", String.valueOf(mAppWidgetId));
        }

        @Override
        public void onCreate() {
//            get your contact List Here
           widgetList.addAll(new ArrayList<User>() {
                {
                    add(new User("Yogesh", "0123456789", false, R.drawable.profile_icon));
                    add(new User("Ava", "3842343394", true, R.drawable.profile_icon));
                    add(new User("Harrison", "4543478624", false, R.drawable.profile_icon));
                    add(new User("Hemant", "2384829283", true, R.drawable.profile_icon));
                    add(new User("Santosh", "29504739404", false, R.drawable.profile_icon));
                    add(new User("Kartik", "52044849433", true, R.drawable.profile_icon));
                    add(new User("Yogesh", "0123456789", false, R.drawable.profile_icon));
                    add(new User("Ava", "3842343394", true, R.drawable.profile_icon));
                    add(new User("Harrison", "4543478624", false, R.drawable.profile_icon));
                    add(new User("Hemant", "2384829283", true, R.drawable.profile_icon));
                    add(new User("Santosh", "29504739404", false, R.drawable.profile_icon));
                    add(new User("Kartik", "52044849433", true, R.drawable.profile_icon));
                }
            });
            // In onCreate() you setup any connections / cursors to your data source. Heavy lifting,
            // for example downloading or creating content etc, should be deferred to onDataSetChanged()
            // or getViewAt(). Taking more than 20 seconds in this call will result in an ANR.
//            for (int i = 0; i < widgetList.size(); i++) {
//                widgetList.add(new User(i + "!"));
//            }
            // We sleep for 3 seconds here to show how the empty view appears in the interim.
            // The empty view is set in the StackWidgetProvider and should be a sibling of the
            // collection view.
//            try {
//                Thread.sleep(3000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void onDestroy() {
            // TODO Auto-generated method stub
            widgetList.clear();
        }

        @Override
        public int getCount() {
            return widgetList.size();
        }

        private void updateWidgetListView(List<User> convertedToList) {
            this.widgetList = convertedToList;
        }

        public RemoteViews getViewAt(int position) {
            // position will always range from 0 to getCount() - 1.
            // We construct a remote views item based on our widget item xml file, and set the
            // text based on the position.
            RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.listview_row_item);
            rv.setTextViewText(R.id.name, widgetList.get(position).getName());
            rv.setTextViewText(R.id.number, widgetList.get(position).getNumber());
            rv.setImageViewResource(R.id.profile_icon, widgetList.get(position).getProfileIcon());
            if(widgetList.get(position).isFav()){
//                Log.d("WidgetService", "getViewAt: "+position);
                rv.setImageViewResource(R.id.favIcon,R.drawable.fav_icon_true);
            }

            // Next, we set a fill-intent which will be used to fill-in the pending intent template
            // which is set on the collection view in StackWidgetProvider.
            Bundle extras = new Bundle();
            extras.putInt(WidgetConfig.EXTRA_ITEM, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            rv.setOnClickFillInIntent(R.id.name, fillInIntent);
            rv.setOnClickFillInIntent(R.id.number, fillInIntent);
            rv.setOnClickFillInIntent(R.id.profile_icon, fillInIntent);
            rv.setOnClickFillInIntent(R.id.profile_icon, fillInIntent);
            // You can do heaving lifting in here, synchronously. For example, if you need to
            // process an image, fetch something from the network, etc., it is ok to do it here,
            // synchronously. A loading view will show up in lieu of the actual contents in the
            // interim.
//            try {
//                System.out.println("Loading view " + position);
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            // Return the remote views object.
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public int getViewTypeCount() {
            // TODO Auto-generated method stub
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            // TODO Auto-generated method stub
            return true;
        }

        @Override
        public void onDataSetChanged() {
            // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
            // on the collection view corresponding to this factory. You can do heaving lifting in
            // here, synchronously. For example, if you need to process an image, fetch something
            // from the network, etc., it is ok to do it here, synchronously. The widget will remain
            // in its current state while work is being done here, so you don't need to worry about
            // locking up the widget.
        }

    }
}
