package com.anwarabdullahn.polibatamdigitalmading.Activity.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.anwarabdullahn.polibatamdigitalmading.Activity.LoginActivity;
import com.anwarabdullahn.polibatamdigitalmading.R;

/**
 * Created by anwarabdullahn on 1/28/18.
 */

public class PermissionSplash extends AppCompatActivity {

    PermissionHelper permissionHelper;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_splash);

        permissionHelper = new PermissionHelper(this);

        checkAndRequestPermissions();

    }

    private boolean checkAndRequestPermissions() {
        permissionHelper.permissionListener(new PermissionHelper.PermissionListener() {
            @Override
            public void onPermissionCheckDone() {
                intent = new Intent(PermissionSplash.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        permissionHelper.checkAndRequestPermissions();

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestCallBack(requestCode, permissions, grantResults);
    }
}
