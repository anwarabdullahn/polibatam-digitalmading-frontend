package com.anwarabdullahn.polibatamdigitalmading.Activity.Utility;

import android.app.ProgressDialog;
import android.content.Context;

import com.anwarabdullahn.polibatamdigitalmading.R;

/**
 * Created by anwarabdullahn on 1/30/18.
 */

public class LoadingHelper {
    private static ProgressDialog loading;

    public static void loadingShow(Context context) {
        loading = ProgressDialog.show(context, null, context.getString(R.string.please_wait));
    }

    public static void loadingDismiss() {
        loading.dismiss();
    }
}
