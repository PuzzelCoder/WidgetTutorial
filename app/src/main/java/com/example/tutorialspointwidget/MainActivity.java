package com.example.tutorialspointwidget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MainActivity extends Activity {
    public static final String TRIGGER = "TRIGGER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void StartTimer(View view) {
        SystemClock.sleep(3000);
        Log.d("TAG", "StartTimer: ");
//        Toast.makeText(this,"Clicked",Toast.LENGTH_SHORT).show();
//        WidgetConfig.startNewTimer();

        sendBroadcast(new Intent(this,WidgetConfig.class).setAction("UPDATETO12"));
    }
    public void startAnim(View view) {
        SystemClock.sleep(3000);
        WidgetConfig.startAnim();
    }

    public void stopAnim(View view) {
//        SystemClock.sleep(3000);
//        WidgetConfig.updateUSer();
    }
}
