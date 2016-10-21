package com.treats.treats.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by david.uzan on 10/19/2016.
 */

public class SharedPrefsHelper {

    private static String KEY_USER_ID = "key_user_id";

    private static SharedPrefsHelper sInstance;
    private SharedPreferences mSharedPref;
    private Gson mGson;

    private static final Object sLockObject = new Object();

    public static SharedPrefsHelper getInstance(Context context) {
        if (sInstance == null) {
            synchronized (sLockObject) {
                if (sInstance == null) {
                    sInstance = new SharedPrefsHelper(context);
                }
            }
        }
        return sInstance;
    }

    private SharedPrefsHelper(){}

    private SharedPrefsHelper(Context context){
        mSharedPref = context.getSharedPreferences("com.treats.treats", Context.MODE_PRIVATE);
        mGson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void clearSharedPrefs(){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public void setUserId(String userId) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putString(KEY_USER_ID, userId);
        editor.apply();
    }

    public String getUserId() {
        return mSharedPref.getString(KEY_USER_ID, "");
    }
}
