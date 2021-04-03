package com.example.tutorialspointwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WidgetConfig extends AppWidgetProvider {

    private static final String TAG = "WidgetConfig";
    public static final String TOAST_ACTION = "com.example.android.stackwidget.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.stackwidget.EXTRA_ITEM";
    private static int layIndex = 0;
//    private Context mContext;
    //    private static Context scontext;
    private static CountDownTimer cTimer = null;
    private static RemoteViews views;
//    private static AppWidgetManager mAppWidgetManager;
    private static ArrayList mAppWidgetIds = new ArrayList();
    private int layoutIndex = 0;
    private static int demoLayouts[] = {R.layout.invisible0, R.layout.invisible24};
    private static int myImages[] = {R.mipmap.flower, R.mipmap.ic_fuel/*, R.mipmap.ic_kmph, R.mipmap.ic_launcher*/};
    private static int myLayouts[] = {
            R.layout.invisible0, /*R.layout.invisible1, R.layout.invisible2, R.layout.invisible3,
            R.layout.invisible4, R.layout.invisible5, R.layout.invisible6, R.layout.invisible7,
            R.layout.invisible8, R.layout.invisible9, R.layout.invisible10, R.layout.invisible11,*/
            R.layout.invisible12, /*R.layout.invisible13, R.layout.invisible14, R.layout.invisible15,
            R.layout.invisible16, R.layout.invisible17, R.layout.invisible18, R.layout.invisible19,
            R.layout.invisible20, R.layout.invisible21, R.layout.invisible22, R.layout.invisible23,*/
            R.layout.invisible24
    };
    private RemoteViews rv;

    public enum ACTIONS {
        BACK(R.id.back),
        REFRESH(R.id.refresh),
        NEXT(R.id.next);
//        TOAST();

        public final int resid;

        ACTIONS(int resid) {
            this.resid = resid;
        }

        public static final Map<String, ACTIONS> fromNames = new HashMap<String, ACTIONS>() {{
            for (ACTIONS a : ACTIONS.values()) put(a.name(), a);

        }};
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
//        mContext = context;
//        scontext = context;
//        mAppWidgetManager = appWidgetManager;

//        mAppWidgetIds.clear();
        for (int id : appWidgetIds) {
            mAppWidgetIds.add(id);
        }
        Log.d(TAG, "onUpdate size: " + mAppWidgetIds.size());
        for(int id=0;id< appWidgetIds.length;id++){
            Log.d(TAG, "onUpdate: ["+id+"]="+appWidgetIds[id]);
        }

        rv = new RemoteViews(context.getPackageName(), R.layout.adapterview);
        for (int id = 0; id < appWidgetIds.length; id++) {
            //cp
            // Here we setup the intent which points to the WidgetService which will
            // p/ / rovide the views for this collection.

            Intent intent = new Intent(context, ContactsWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[id]);

            // When intents are compared, the extras are ignored, so we need to embed the extras
            // into the data so that the extras will not be ignored.

            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            rv.setRemoteAdapter(R.id.contacts_listview, intent);

            // The empty view is displayed when the collection has no items. It should be a sibling
            // of the collection view.

            rv.setEmptyView(R.id.contacts_listview, R.id.empty_view);

            // Here we setup the a pending intent template. Individuals items of a collection
            // cannot setup their own pending intents, instead, the collection as a whole can
            // setup a pending intent template, and the individual items can set a fillInIntent
            // to create unique before on an item to item basis.

            Intent toastIntent = new Intent(context, WidgetConfig.class);
            toastIntent.setAction(WidgetConfig.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[id]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, appWidgetIds[id], toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.contacts_listview, toastPendingIntent);
            //cp

            for (ACTIONS a : ACTIONS.values()) {
                Intent wIntent = new Intent(context, WidgetConfig.class);
                wIntent.setAction(a.name());
                wIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[id]);
                PendingIntent pendingIntent = PendingIntent
                        .getBroadcast(context, appWidgetIds[id], wIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                rv.setOnClickPendingIntent(a.resid, pendingIntent);
                appWidgetManager.updateAppWidget(appWidgetIds[id], rv);
            }
            appWidgetManager.updateAppWidget(id, rv);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }


    @Override
    public void onReceive(final Context context, final Intent intent) {
        String action = intent.getAction();
       int widget_id=-1;
        Log.d(TAG, "onReceive: action=" + action);
        mAppWidgetIds.clear();

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName name = new ComponentName(MyAppliactionClass.getAppContext(), WidgetConfig.class);
        int ids[] = appWidgetManager.getAppWidgetIds(name);
        Log.d(TAG, "onReceive size=: "+ids.length);
        for (int id = 0; id < ids.length; id++) {
            Log.d(TAG, "onReceive: [" + id + "]=" + ids[id]);
            mAppWidgetIds.add(ids[id]);
        }
//        if (mAppWidgetIds.size() > 1) {
//            for (int id = 0; id < ids.length-1; id++) {
//                Log.d(TAG, "deletedId: " + id + "=" + ids[id]);
//                mAppWidgetIds.remove(id);
//            }
//        }
        if (action == null) {
            Log.e(TAG, "onReceive: action is null");
            return;
        } else if (intent.getAction().equals(TOAST_ACTION)) {
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, -1);
            widget_id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            Toast.makeText(context, "Touched view " + viewIndex+"From ID="+widget_id, Toast.LENGTH_SHORT).show();
        } else if (intent.getAction().equals("UPDATETO24")) {

             widget_id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            update24(context,widget_id);
            Log.d(TAG, "onReceive UPDATETO24"+widget_id);
        } else if (intent.getAction().equals("UPDATETO12")) {
            widget_id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
            Log.d(TAG, "onReceive: UPDATETO12");
            update12(context,widget_id);
        }
//        else if(action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)){
//            update0(context);
//        }
        else {
            Log.d(TAG, "onReceive: Seconnf" + action+mAppWidgetIds.size());
            ACTIONS a = ACTIONS.fromNames.get(action);
            if (a != null)
                switch (a) {
                    case REFRESH:
                        widget_id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
                        Log.d(TAG, "onReceive: UPDATETO0"+widget_id);
                        update0(context,widget_id);
//                        update24(context);
//                        refreshLayout(context,intent);
                        break;
                    case NEXT:
                        widget_id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
                        Log.d(TAG, "onReceive: UPDATETOUSER"+widget_id);
                        updateUSer(context, widget_id);
//                    startNewTimer();
//                    final RemoteViews rv = new RemoteViews(context.getPackageName(),
//                            R.layout.adapterview);
//
//                    rv.removeAllViews(R.id.flipper_layout);
//                    final RemoteViews invisible_layout = new RemoteViews(context.getPackageName(),
//                            R.layout.invisible15);
//                    rv.addView(R.id.flipper_layout,invisible_layout);
//                    if (a == ACTIONS.NEXT) {
//                        rv.showNext(R.id.page_flipper);
////
////
//                    } else {
//                        rv.showPrevious(R.id.page_flipper);
//
//                    }
//                    AppWidgetManager.getInstance(context).updateAppWidget(
//                            intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
//                                    AppWidgetManager.INVALID_APPWIDGET_ID), rv);
                        break;
                }

        }

        super.onReceive(context, intent);
    }

    private void update0(Context context, int widget_id) {
        rv = new RemoteViews(context.getPackageName(),
                R.layout.adapterview);
        rv.removeAllViews(R.id.flipper_layout);
        RemoteViews invisible_layout0 = new RemoteViews(context.getPackageName(),
                R.layout.invisible0);
        rv.addView(R.id.flipper_layout, invisible_layout0);

        Intent intent1 = new Intent(context, ContactsWidgetService.class);
//        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,(mAppWidgetIds.toArray()));
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widget_id);
//
        intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
        rv.setRemoteAdapter(R.id.contacts_listview, intent1);

        // The empty view is displayed when the collection has no items. It should be a sibling
        // of the collection view.

        rv.setEmptyView(R.id.contacts_listview, R.id.empty_view);
        Intent toastIntent1 = new Intent(context, WidgetConfig.class);
        toastIntent1.setAction(WidgetConfig.TOAST_ACTION);
//        toastIntent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetIds.toArray());
        toastIntent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widget_id);
        intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent1 = PendingIntent.getBroadcast(context, widget_id, toastIntent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rv.setPendingIntentTemplate(R.id.contacts_listview, toastPendingIntent1);
        rv.setViewVisibility(R.id.refresh, View.GONE);
        rv.setViewVisibility(R.id.next, View.VISIBLE);
//        ComponentName component = new ComponentName(context, WidgetConfig.class);
//        mAppWidgetManager.updateAppWidget(component, rv);
        AppWidgetManager.getInstance(context).updateAppWidget(
                widget_id, rv);
    }

    private void refreshLayout(Context context, Intent intent) {
        rv = new RemoteViews(context.getPackageName(),
                R.layout.adapterview);
        rv.removeAllViews(R.id.flipper_layout);
        final RemoteViews invisible_layout12 = new RemoteViews(context.getPackageName(),
                R.layout.invisible12);
        rv.addView(R.id.flipper_layout, invisible_layout12);
        AppWidgetManager.getInstance(context).updateAppWidget(
                intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID), rv);

        SystemClock.sleep(500);
        rv.removeAllViews(R.id.flipper_layout);
        final RemoteViews invisible_layout0 = new RemoteViews(context.getPackageName(),
                R.layout.invisible0);
        rv.addView(R.id.flipper_layout, invisible_layout0);
        rv.setViewVisibility(R.id.refresh, View.GONE);
        rv.setViewVisibility(R.id.next, View.VISIBLE);
        AppWidgetManager.getInstance(context).updateAppWidget(
                intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                        AppWidgetManager.INVALID_APPWIDGET_ID), rv);
    }

    public static void startNewTimer() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
//                addText();//add/remove view
//                updateUSer();//direct to the polygon
                startTimer();//slowly to the polygon
                cTimer.start();
            }
        });
    }

//    private static void addText() {
//        RemoteViews update = new RemoteViews(context.getPackageName(), R.layout.demo_layout);
//
//        RemoteViews upper_part = new RemoteViews(context.getPackageName(), R.layout.upper_part);
////            textView.setTextViewText(R.id.textView1, "TextView number" + String.valueOf(i));
//        update.addView(R.id.upper_layout, upper_part);
//
//
//        mAppWidgetManager.updateAppWidget(mAppWidgetIds, update);
//
//        SystemClock.sleep(2000);
//        update.removeAllViews(R.id.upper_layout);
//        mAppWidgetManager.updateAppWidget(mAppWidgetIds, update);
//
//    }

    private static void startTimer() {

        cancelTimer();
        cTimer = new CountDownTimer(3675/*seconds in a week*/, 175) {
            public void onTick(long millisUntilFinished) {
//                    updateFlower(millisUntilFinished);
//                        updateUSer();
            }

            public void onFinish() {
                cancelTimer();
            }
        };
    }

    void updateUSer(Context context, int widget_id) {
        Log.d(TAG, "updateUSer: ");
//        layIndex=14;
//        views = new RemoteViews(scontext.getPackageName(), myLayouts[layIndex++]);
//        // Instruct the widget manager to update the widget
//        mAppWidgetManager.updateAppWidget(mAppWidgetIds, views);

        update12(context, widget_id);
//        update24(context, widget_id);

    }

    private void update12(Context context, int widget_id) {
        RemoteViews rv1 = new RemoteViews(context.getPackageName(),
                R.layout.adapterview);
        rv1.removeAllViews(R.id.flipper_layout);
        RemoteViews invisible_layout1 = new RemoteViews(context.getPackageName(),
                R.layout.invisible12);
        rv1.addView(R.id.flipper_layout, invisible_layout1);

        Intent intent1 = new Intent(context, ContactsWidgetService.class);
//        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,mAppWidgetIds.toArray());
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widget_id);
//
        intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
        rv1.setRemoteAdapter(R.id.contacts_listview, intent1);


        // The empty view is displayed when the collection has no items. It should be a sibling
        // of the collection view.

        rv1.setEmptyView(R.id.contacts_listview, R.id.empty_view);
//        Intent toastIntent1 = new Intent(context, WidgetConfig.class);
//        toastIntent1.setAction(WidgetConfig.TOAST_ACTION);
//        toastIntent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetIds[0]);
        intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
//        PendingIntent toastPendingIntent1 = PendingIntent.getBroadcast(context, 1, toastIntent1,
//                PendingIntent.FLAG_UPDATE_CURRENT);

//        rv1.setPendingIntentTemplate(R.id.contacts_listview, toastPendingIntent1);
//        AppWidgetManager.getInstance(context).updateAppWidget(
//                (int)(mAppWidgetIds.toArray())[0], rv1);
        AppWidgetManager.getInstance(context).updateAppWidget(
                widget_id, rv1);

        SystemClock.sleep(750);
        update24(context, widget_id);

    }

    private void update24(Context context, int widget_id) {

        RemoteViews rv1 = new RemoteViews(context.getPackageName(),
                R.layout.adapterview);
        rv1.removeAllViews(R.id.flipper_layout);
        RemoteViews invisible_layout1 = new RemoteViews(context.getPackageName(),
                R.layout.invisible24);
        rv1.addView(R.id.flipper_layout, invisible_layout1);

        Intent intent1 = new Intent(context, ContactsWidgetService.class);
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widget_id);
//
        intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
        rv1.setRemoteAdapter(R.id.contacts_listview, intent1);

        // The empty view is displayed when the collection has no items. It should be a sibling
        // of the collection view.

        rv1.setEmptyView(R.id.contacts_listview, R.id.empty_view);
        Intent toastIntent1 = new Intent(context, WidgetConfig.class);
        toastIntent1.setAction(WidgetConfig.TOAST_ACTION);
        toastIntent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,widget_id);
        intent1.setData(Uri.parse(intent1.toUri(Intent.URI_INTENT_SCHEME)));
        PendingIntent toastPendingIntent1 = PendingIntent.getBroadcast(context, widget_id, toastIntent1,
                PendingIntent.FLAG_UPDATE_CURRENT);
        rv1.setPendingIntentTemplate(R.id.contacts_listview, toastPendingIntent1);
        rv1.setViewVisibility(R.id.next, View.GONE);
        rv1.setViewVisibility(R.id.refresh, View.VISIBLE);
        AppWidgetManager.getInstance(context).updateAppWidget(
               widget_id, rv1);
    }

//    private void createClickIntents() {
//        rv = new RemoteViews(mContext.getPackageName(), R.layout.adapterview);
//        for (int id = 0; id < mAppWidgetIds.length; id++) {
//            //cp
//            // Here we setup the intent which points to the WidgetService which will
//            // provide the views for this collection.
//
//            Intent intent = new Intent(mContext, ContactsWidgetService.class);
//            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetIds[id]);
//
//            // When intents are compared, the extras are ignored, so we need to embed the extras
//            // into the data so that the extras will not be ignored.
//
//            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//            rv.setRemoteAdapter(R.id.contacts_listview, intent);
//
//            // The empty view is displayed when the collection has no items. It should be a sibling
//            // of the collection view.
//
//            rv.setEmptyView(R.id.contacts_listview, R.id.empty_view);
//
//            // Here we setup the a pending intent template. Individuals items of a collection
//            // cannot setup their own pending intents, instead, the collection as a whole can
//            // setup a pending intent template, and the individual items can set a fillInIntent
//            // to create unique before on an item to item basis.
//
//            Intent toastIntent = new Intent(mContext, WidgetConfig.class);
//            toastIntent.setAction(WidgetConfig.TOAST_ACTION);
//            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetIds[id]);
//            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(mContext, 0, toastIntent,
//                    PendingIntent.FLAG_UPDATE_CURRENT);
//            rv.setPendingIntentTemplate(R.id.contacts_listview, toastPendingIntent);
//            //cp
//
//            for (ACTIONS a : ACTIONS.values()) {
//                Intent wIntent = new Intent(mContext, WidgetConfig.class);
//                wIntent.setAction(a.name());
//                wIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
//                PendingIntent pendingIntent = PendingIntent
//                        .getBroadcast(mContext, 0, wIntent,
//                                PendingIntent.FLAG_UPDATE_CURRENT);
//                rv.setOnClickPendingIntent(a.resid, pendingIntent);
//                mAppWidgetManager.updateAppWidget(mAppWidgetIds[id], rv);
//            }
//            mAppWidgetManager.updateAppWidget(id, rv);
//        }
//    }

//    private static void updateFlower(long millisUntilFinished) {
//        String s = String.valueOf(new Random().nextInt(5));
//
//        views.setTextViewText(R.id.textView1, s);
//        int index = (int) (millisUntilFinished % 4);
//        views.setImageViewResource(R.id.myImage, myImages[index]);
//        Log.d("updateAppWidget: ", s);
//        Log.d("mili: ", "" + millisUntilFinished);
//        Log.d("img: ", "" + index);
//
//        // Instruct the widget manager to update the widget
//        mAppWidgetManager.updateAppWidget(mAppWidgetIds[0], views);
//    }

    //cancel timer
    static void cancelTimer() {
        if (cTimer != null)
            cTimer.cancel();
    }
//    @Override
//    public void onUpdate(Context ctxt, AppWidgetManager mgr,
//                         int[] appWidgetIds) {
//        ComponentName me=new ComponentName(ctxt, WidgetConfig.class);
//        int v = (int)(Math.random()*6);
//        String rs = Integer.toString(v);
//        mgr.updateAppWidget(me, buildUpdate(ctxt, appWidgetIds,rs));
//    }

//    private RemoteViews buildUpdate(Context ctxt, int[] appWidgetIds, String rs) {
//        RemoteViews updateViews = new RemoteViews(ctxt.getPackageName(),
//                R.layout.widget);
//
//        Intent i = new Intent(ctxt, WidgetConfig.class);
//
//        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
//        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
//
////        PendingIntent pi=PendingIntent.getBroadcast(ctxt, 0 , i,
////                PendingIntent.FLAG_UPDATE_CURRENT);
//        //  CharSequence c = (CharSequence)((int)Math.random()*6);
//
//        CharSequence cs = rs.subSequence(0, 1)  ;
//
//
//        updateViews.setTextViewText(R.id.textView1, cs);
////        updateViews.setOnClickPendingIntent(R.id.textView1, pi);
//
////        updateViews.setOnClickPendingIntent(R.id.background, pi);
//
//        return (updateViews);
//    }

    public static void startAnim() {
//       views.setBoolean(R.id.vf_slot_0, "se", false)  ;
//     mAppWidgetManager.updateAppWidget(mAppWidgetIds,views);
    }
//
//    public static void stopAnim() {
////        views.(R.id.vf_slot_0, "setFlipInterval", 1000);
//    }
}