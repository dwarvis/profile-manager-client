package edu.cis.clientapp.Controller;

import android.app.Application;


//got from: https://stackoverflow.com/questions/9445661/how-to-get-the-context-from-anywhere

public class MyApp extends Application {
    private static MyApp instance;

    public static MyApp getInstance() {
        return instance;
    }

    public static MyApp getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
