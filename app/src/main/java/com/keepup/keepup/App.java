package com.keepup.keepup;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by david.uzan on 9/3/2016.
 */
public class App extends Application {

    private static Context sContext = null;

    public static Context getAppContext() {
        return sContext;
    }

    public static void log(String message) {
        if (BuildConfig.DEBUG) {
            Log.i(getAppContext().getResources().getString(R.string.app_name), message);
        }
    }

    public static void logError(String message) {
        if (BuildConfig.DEBUG) {
            Log.e(getAppContext().getResources().getString(R.string.app_name), message);
        }
    }

    public static void toast(String msg) {
        Toast.makeText(getAppContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

    }
}
