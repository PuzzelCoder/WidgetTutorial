package com.example.tutorialspointwidget;

import android.app.Application;
import android.content.Context;

public class MyAppliactionClass extends Application {
    private static Context context;

    public MyAppliactionClass(){
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context= this;
    }

    public static Context getAppContext(){
        return context;
    }

}
