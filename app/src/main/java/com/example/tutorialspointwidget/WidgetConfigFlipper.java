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
import android.widget.RemoteViews;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WidgetConfigFlipper extends AppWidgetProvider {
    private static final String TAG ="WidgetConfig";
    private static int layIndex=0;
    private Context mContext;
    private static  Context scontext;
    private static CountDownTimer cTimer = null;
    private static RemoteViews views;
    private static AppWidgetManager mAppWidgetManager;
    private static int[] mAppWidgetIds;
    private int layoutIndex=0;
    private static int demoLayouts[]={R.layout.invisible0,R.layout.invisible24};
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

    public enum ACTIONS {
        BACK(R.id.back),
        REFRESH(R.id.refresh),
        NEXT(R.id.next);

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
        mContext = context;
        scontext=context;
        mAppWidgetManager = appWidgetManager;
        mAppWidgetIds = appWidgetIds;

        for (int id : appWidgetIds) {

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.adapterviewflipper);

            // Specify the service to provide data for the collection widget.
            // Note that we need to
            // embed the appWidgetId via the data otherwise it will be ignored.
            Intent intent = new Intent(context, WidgetServiceFlipper.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
//            rv.setRemoteAdapter(R.id.page_flipper, intent);

            for (ACTIONS a : ACTIONS.values()) {
                Intent wIntent = new Intent(context, WidgetConfigFlipper.class);
                wIntent.setAction(a.name());
                wIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id);
                PendingIntent pendingIntent = PendingIntent
                        .getBroadcast(context, 0, wIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                rv.setOnClickPendingIntent(a.resid, pendingIntent);
            }

            appWidgetManager.updateAppWidget(id, rv);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);

////         MainActivity.sedContext(context);
////        for(int i=0; i<appWidgetIds.length; i++){
////            int currentWidgetId = appWidgetIds[i];
////            String url = "http://www.tutorialspoint.com";
////
////            Intent intent = new Intent(Intent.ACTION_DEFAULT);
////            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////            intent.setData(Uri.parse(url));
////
////            PendingIntent pending = PendingIntent.getActivity(context, 0,intent, 0);
////            views.setOnClickPendingIntent(R.id.imageView, pending);
////             views.setBoolean(R.id.vf_slot_0, "myStartFlipping", true);
////            appWidgetManager.updateAppWidget(currentWidgetId,views);
////            Toast.makeText(context, "widget added", Toast.LENGTH_SHORT).show();
////        }
//
    }




    @Override
    public void onReceive(final Context context, final Intent intent) {
        String action = intent.getAction();
        if (action == null) {
            Log.e(TAG, "onReceive: action is null");
            return;
        }

        ACTIONS a = ACTIONS.fromNames.get(action);

        if (a != null)
            switch (a) {
                case REFRESH:
                    AppWidgetManager mgr = AppWidgetManager.getInstance(context);
                    ComponentName cn = new ComponentName(context,
                            WidgetConfigFlipper.class);
                    mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn),
                            R.id.page_flipper);
                    break;
                case NEXT:
                case BACK:
                    final RemoteViews rv = new RemoteViews(context.getPackageName(),
                            R.layout.adapterviewflipper);
                    if (a == ACTIONS.NEXT) {


                        rv.showNext(R.id.page_flipper);


                    } else {
                        rv.showPrevious(R.id.page_flipper);

                    }
                    AppWidgetManager.getInstance(context).updateAppWidget(
                            intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                                    AppWidgetManager.INVALID_APPWIDGET_ID), rv);
                        automateOnlick(rv,context,intent);
                    break;
            }

        super.onReceive(context, intent);
    }

    private void automateOnlick(RemoteViews rv, Context context, Intent intent) {
        rv.showNext(R.id.page_flipper);
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
                updateUSer();//direct to the polygon
//                startTimer();//slowly to the polygon
//                cTimer.start();
            }
        });
    }

    private static void addText() {
        RemoteViews update = new RemoteViews(scontext.getPackageName(), R.layout.demo_layout);

            RemoteViews upper_part = new RemoteViews(scontext.getPackageName() ,R.layout.upper_part);
//            textView.setTextViewText(R.id.textView1, "TextView number" + String.valueOf(i));
            update.addView(R.id.upper_layout, upper_part);


        mAppWidgetManager.updateAppWidget(mAppWidgetIds, update);

        SystemClock.sleep(2000);
        update.removeAllViews(R.id.upper_layout);
        mAppWidgetManager.updateAppWidget(mAppWidgetIds, update);

    }

    private static void startTimer() {

        if (cTimer != null) {
            cTimer.cancel();
        }
        cTimer = new CountDownTimer(3675/*seconds in a week*/, 175) {
            public void onTick(long millisUntilFinished) {
//                    updateFlower(millisUntilFinished);
                        updateUSer();
            }

            public void onFinish() {
                if (cTimer != null) {
                    cTimer.cancel();
                }
            }
        };
    }

    static void updateUSer() {
        Log.d( "updateUSer: ",""+layIndex);
        layIndex=15;
        views = new RemoteViews(scontext.getPackageName(), myLayouts[layIndex]);
        // Instruct the widget manager to update the widget
        mAppWidgetManager.updateAppWidget(mAppWidgetIds, views);
    }

    private static void updateFlower(long millisUntilFinished) {
        String s = String.valueOf(new Random().nextInt(5));

        views.setTextViewText(R.id.textView1, s);
        int index = (int) (millisUntilFinished % 4);
        views.setImageViewResource(R.id.myImage, myImages[index]);
        Log.d("updateAppWidget: ", s);
        Log.d("mili: ", "" + millisUntilFinished);
        Log.d("img: ", "" + index);

        // Instruct the widget manager to update the widget
        mAppWidgetManager.updateAppWidget(mAppWidgetIds, views);
    }

    //cancel timer
    void cancelTimer() {
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

    private RemoteViews buildUpdate(Context ctxt, int[] appWidgetIds, String rs) {
        RemoteViews updateViews = new RemoteViews(ctxt.getPackageName(),
                R.layout.widget);

        Intent i = new Intent(ctxt, WidgetConfigFlipper.class);

        i.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        i.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

//        PendingIntent pi=PendingIntent.getBroadcast(ctxt, 0 , i,
//                PendingIntent.FLAG_UPDATE_CURRENT);
        //  CharSequence c = (CharSequence)((int)Math.random()*6);

        CharSequence cs = rs.subSequence(0, 1);


        updateViews.setTextViewText(R.id.textView1, cs);
//        updateViews.setOnClickPendingIntent(R.id.textView1, pi);

//        updateViews.setOnClickPendingIntent(R.id.background, pi);

        return (updateViews);
    }

    public static void startAnim() {
//       views.setBoolean(R.id.vf_slot_0, "se", false)  ;
//     mAppWidgetManager.updateAppWidget(mAppWidgetIds,views);
    }
//
//    public static void stopAnim() {
////        views.(R.id.vf_slot_0, "setFlipInterval", 1000);
//    }
}