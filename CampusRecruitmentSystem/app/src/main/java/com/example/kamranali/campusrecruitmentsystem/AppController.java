package com.example.kamranali.campusrecruitmentsystem;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kamranali on 25/01/2017.
 */

public class AppController extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


    }
}
