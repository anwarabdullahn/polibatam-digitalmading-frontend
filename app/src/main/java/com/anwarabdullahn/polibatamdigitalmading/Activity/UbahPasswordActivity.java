package com.anwarabdullahn.polibatamdigitalmading.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.API.API;
import com.anwarabdullahn.polibatamdigitalmading.API.APICallback;
import com.anwarabdullahn.polibatamdigitalmading.API.APIError;
import com.anwarabdullahn.polibatamdigitalmading.API.APIResponse;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.LoadingHelper;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class UbahPasswordActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Button ubahBtn;
    String oldPass, newPass, reNewPass;
    ImageView bgLogin;

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "Ubah Password", this.getClass().getSimpleName());

        bgLogin = findViewById(R.id.bgLogin);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loginbg);
        Glide.with(this).load("https://media.giphy.com/media/26DN2YNl9k6VQqB2w/giphy.gif").apply(requestOptions).into(bgLogin);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbar.setTitle(mTitle.getText());
        mTitle.setText("UBAH PASSWORD");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        ubahBtn = (Button) findViewById(R.id.ubahBtn);
        ubahBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldPass = ((EditText) findViewById(R.id.passOld)).getText().toString();
                newPass = ((EditText) findViewById(R.id.passNew)).getText().toString();
                reNewPass = ((EditText) findViewById(R.id.rePassNew)).getText().toString();

                if ("".equals(oldPass)) {
                    Toast.makeText(UbahPasswordActivity.this, "Password Lama Wajib diisi.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(newPass)) {
                    Toast.makeText(UbahPasswordActivity.this, "Password Baru Wajib diisi.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(reNewPass)) {
                    Toast.makeText(UbahPasswordActivity.this, "Password Baru Wajib diisi.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPass.equals(reNewPass)){
                    Toast.makeText(UbahPasswordActivity.this, "Password Baru Tidak Cocok.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Log.d("oldPass ",oldPass);
                Log.d("newPass ",newPass);
                Log.d("reNewPass ",reNewPass);

                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("oldpassword", oldPass)
                        .addFormDataPart("password", newPass)
                        .addFormDataPart("repassword", reNewPass)
                        .build();
                LoadingHelper.loadingShow(UbahPasswordActivity.this);
                API.service().setPassword(body).enqueue(new APICallback<APIResponse>() {
                    @Override
                    protected void onSuccess(APIResponse apiResponse) {
                        LoadingHelper.loadingDismiss();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(UbahPasswordActivity.this, R.style.AlertDialog);
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setTitle("Ubah Password");
                        builder.setMessage("Password Berhasil di ubah");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(UbahPasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.show();
                    }

                    @Override
                    protected void onError(APIError error) {
                        Toast.makeText(UbahPasswordActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }

}
