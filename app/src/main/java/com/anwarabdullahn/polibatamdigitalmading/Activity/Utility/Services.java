package com.anwarabdullahn.polibatamdigitalmading.Activity.Utility;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by anwarabdullahn on 2/5/18.
 */

public class Services extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // do your jobs here
        onTaskRemoved(intent);
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent intent = new Intent(getApplicationContext(),this.getClass());
        intent.setPackage(getPackageName());
        startService(intent);
        super.onTaskRemoved(rootIntent);
    }
}