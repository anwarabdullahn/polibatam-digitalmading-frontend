package com.anwarabdullahn.polibatamdigitalmading;

import android.app.Application;
import android.content.Intent;

import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.Firebase.App.Config;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.Services;
import com.google.firebase.messaging.FirebaseMessaging;
import com.orhanobut.hawk.Hawk;

/**
 * Created by anwarabdullahn on 2/4/18.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Hawk.init(this).build();
        startService(new Intent(this, Services.class));
        FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
    }
}
