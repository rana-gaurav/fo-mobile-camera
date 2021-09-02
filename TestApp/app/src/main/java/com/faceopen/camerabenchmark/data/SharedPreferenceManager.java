package com.faceopen.camerabenchmark.data;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String APP_PREFS = "APP_PREFS";
    private static final String CATEGORY_A = "CATEGORY_A";
    private static final String CATEGORY_B = "CATEGORY_B";
    private static final String CATEGORY_C = "CATEGORY_C";
    private static final String CATEGORY_D = "CATEGORY_D";
    private static final String CATEGORY_E = "CATEGORY_E";
    private SharedPreferenceManager() {}

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    //----A ----

    public static boolean getCategoryA(Context context) {
        return getSharedPreferences(context).getBoolean(CATEGORY_A , false);
    }

    public static void setCategoryA(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(CATEGORY_A , newValue);
        editor.commit();
    }

    //----B ----
    public static boolean getCategoryB(Context context) {
        return getSharedPreferences(context).getBoolean(CATEGORY_B , false);
    }

    public static void setCategoryB(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(CATEGORY_B , newValue);
        editor.commit();
    }

    //----C ----
    public static boolean getCategoryC(Context context) {
        return getSharedPreferences(context).getBoolean(CATEGORY_C , false);
    }

    public static void setCategoryC(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(CATEGORY_C , newValue);
        editor.commit();
    }

    //----D ----
    public static boolean getCategoryD(Context context) {
        return getSharedPreferences(context).getBoolean(CATEGORY_D , false);
    }

    public static void setCategoryD(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(CATEGORY_D , newValue);
        editor.commit();
    }

    //----E ----
    public static boolean getCategoryE(Context context) {
        return getSharedPreferences(context).getBoolean(CATEGORY_E , false);
    }


    public static void setCategoryE(Context context, boolean newValue) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(CATEGORY_E , newValue);
        editor.commit();
    }
}
