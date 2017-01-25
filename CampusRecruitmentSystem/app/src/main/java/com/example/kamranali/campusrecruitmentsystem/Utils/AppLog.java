package com.example.kamranali.campusrecruitmentsystem.utils;

import android.util.Log;

/**
 * Created by kamranali on 25/01/2017.
 */

public class AppLog {

    private static String TAG = "CampusRecuritmentSystem";

    public static void logd(String msg) {
        Log.d(TAG, msg);
    }

    public static void loge(String msg) {
        Log.e(TAG, msg);
    }


    public static void logi(String msg) {
        Log.i(TAG, msg);
    }


    public static int v(String tag, String msg) {
        return Log.v(tag, msg);
    }

    public static int d(String tag, String msg) {
        return Log.d(tag, msg);
    }

    public static int i(String tag, String msg) {
        return Log.i(tag, msg);
    }

    public static int w(String tag, String msg) {
        return Log.w(tag, msg);
    }

    public static int e(String tag, String msg) {
        return Log.e(tag, msg);
    }

}
