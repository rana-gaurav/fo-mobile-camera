package com.faceopen.camerabenchmark;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String APP_SETTINGS = "APP_SETTINGS";

    // properties
    private static final String SOME_STRING_VALUE1 = "SOME_STRING_VALUE1";
    private static final String SOME_STRING_VALUE2 = "SOME_STRING_VALUE2";
    private static final String SOME_STRING_VALUE3 = "SOME_STRING_VALUE3";
    private static final String SOME_STRING_VALUE4 = "SOME_STRING_VALUE4";
    private static final String SOME_STRING_VALUE5 = "SOME_STRING_VALUE5";
    // other properties...


    private SharedPreferenceManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_SETTINGS, Context.MODE_PRIVATE);
    }

    //----1 ----

    public static boolean getCategory1(Context context) {
        return getSharedPreferences(context).getBoolean(SOME_STRING_VALUE1 , false);
    }

    public static void setCategory1(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SOME_STRING_VALUE1 , newValue);
        editor.commit();
    }

    //----1 ----
    public static boolean getCategory2(Context context) {
        return getSharedPreferences(context).getBoolean(SOME_STRING_VALUE2 , false);
    }

    public static void setCategory2(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SOME_STRING_VALUE2 , newValue);
        editor.commit();
    }

    //----3 ----
    public static boolean getCategory3(Context context) {
        return getSharedPreferences(context).getBoolean(SOME_STRING_VALUE3 , false);
    }

    public static void setCategory3(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SOME_STRING_VALUE3 , newValue);
        editor.commit();
    }

    //----4 ----
    public static boolean getCategory4(Context context) {
        return getSharedPreferences(context).getBoolean(SOME_STRING_VALUE4 , false);
    }

    public static void setCategory4(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SOME_STRING_VALUE4 , newValue);
        editor.commit();
    }


    //----5 ----
    public static boolean getCategory5(Context context) {
        return getSharedPreferences(context).getBoolean(SOME_STRING_VALUE5 , false);
    }

    public static void setCategory5(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(SOME_STRING_VALUE5 , newValue);
        editor.commit();
    }


    public static void setCategory12(Context context, String newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putString("SOME_STRING_VALUE" , newValue);
        editor.commit();
    }


    public static String getCategory12(Context context) {
        return getSharedPreferences(context).getString("SOME_STRING_VALUE" , "null");
    }


    // other getters/setters
}
