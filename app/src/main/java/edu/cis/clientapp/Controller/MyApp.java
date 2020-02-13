package edu.cis.clientapp.Controller;

import android.app.Application;
import android.content.Context;


//got from: https://stackoverflow.com/questions/9445661/how-to-get-the-context-from-anywhere

public class MyApp extends Application {
    private static MyApp instance;
    private static Context mContext;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        //  return instance.getApplicationContext();
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }
}
