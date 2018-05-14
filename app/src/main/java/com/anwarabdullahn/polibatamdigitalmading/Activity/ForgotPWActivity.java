package com.anwarabdullahn.polibatamdigitalmading.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class ForgotPWActivity extends AppCompatActivity {

    Button forgotBtn;
    EditText emailTxt;
    TextView masukBtn;
    private Toolbar mToolbar;
    ImageView bgLogin;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pw);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "Lupa Password", this.getClass().getSimpleName());

        forgotBtn = (Button) findViewById(R.id.forgetBtn);
        masukBtn = (TextView) findViewById(R.id.masukbtn);
        emailTxt = (EditText) findViewById(R.id.registTxt);

        bgLogin = findViewById(R.id.bgLogin);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loginbg);
        Glide.with(this).load("https://media.giphy.com/media/26DN2YNl9k6VQqB2w/giphy.gif").apply(requestOptions).into(bgLogin);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbar.setTitle(mTitle.getText());
        mTitle.setText("LUPA PASSWORD");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        masukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgotPWActivity.this, LoginActivity.class));
                finish();
            }
        });

        forgotBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ("".equals(emailTxt.getText().toString())) {
                    Toast.makeText(ForgotPWActivity.this, "Mohon periksa kembali email kamu", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", emailTxt.getText().toString())
                        .build();


                LoadingHelper.loadingShow(ForgotPWActivity.this);
                API.service().forgot(body).enqueue(new APICallback<APIResponse>() {
                    @Override
                    protected void onSuccess(APIResponse apiResponse) {
                        LoadingHelper.loadingDismiss();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPWActivity.this, R.style.AlertDialog);
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setTitle("Forgot Password");
                        builder.setMessage("Silahkan periksa email anda untuk mengatur password baru");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ForgotPWActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.show();
                    }

                    @Override
                    protected void onError(APIError error) {
                        LoadingHelper.loadingDismiss();
                        Toast.makeText(ForgotPWActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

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
