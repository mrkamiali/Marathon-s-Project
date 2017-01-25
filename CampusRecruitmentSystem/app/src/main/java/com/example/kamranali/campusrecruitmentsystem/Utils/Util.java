package com.example.kamranali.campusrecruitmentsystem.utils;

import android.content.Context;

/**
 * Created by kamranali on 25/01/2017.
 */

public class Util {
    public static void successToast(Context context,String message){
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
    }



    public static void failureToast(Context context,String message){
        android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show();
    }

}
