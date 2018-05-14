package com.anwarabdullahn.polibatamdigitalmading.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.API.API;
import com.anwarabdullahn.polibatamdigitalmading.API.APICallback;
import com.anwarabdullahn.polibatamdigitalmading.API.APIError;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.LoadingHelper;
import com.anwarabdullahn.polibatamdigitalmading.Model.Login;
import com.anwarabdullahn.polibatamdigitalmading.Request.LoginForm;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.anwarabdullahn.polibatamdigitalmading.R;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.orhanobut.hawk.Hawk;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    FirebaseAnalytics mFirebaseAnalytics;

    Button masukBtn;
    String emailText,passwordText;
    TextView daftarBtn;
    TextView forgetBtn;
    ImageView loginBg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Hawk.init(this).build();
        daftarBtn = findViewById(R.id.daftarbtn);
        masukBtn = findViewById(R.id.masukbtn);
        forgetBtn = findViewById(R.id.forgetBtn);
        loginBg = findViewById(R.id.bgLogin);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "Login", this.getClass().getSimpleName());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loginbg);
        Glide.with(this).load("https://media.giphy.com/media/26DN2YNl9k6VQqB2w/giphy.gif").apply(requestOptions).into(loginBg);

        forgetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPWActivity.class));
            }
        });

        if (API.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        masukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                emailText = ((EditText) findViewById(R.id.registTxt)).getText().toString().trim();
                passwordText = ((EditText) findViewById(R.id.passwordTxt)).getText().toString();

                if (isValidEmaillId(emailText)) {
                    Toast.makeText(LoginActivity.this, "Email kamu tidak sah", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(passwordText)) {
                    Toast.makeText(LoginActivity.this, "Mohon periksa kembali password kamu", Toast.LENGTH_SHORT).show();
                    return;
                }

                LoginForm body = new LoginForm();
                body.setEmail(emailText);
                body.setPassword(passwordText);
                body.setPlatfom(0);

                LoadingHelper.loadingShow(LoginActivity.this);
                API.service().login(body).enqueue(new APICallback<Login>() {
                    @Override
                    protected void onSuccess(Login login) {
                        LoadingHelper.loadingDismiss();
                        API.setCurrentUser(login.getData());
                        API.setToken(login.getMeta().getToken());

                        Intent intent = new Intent(v.getContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    protected void onError(APIError error) {
                        LoadingHelper.loadingDismiss();
                        Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        daftarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmaillId(String email) {
        if (TextUtils.isEmpty(email)) {
            return true;
        } else {
            boolean pattern = Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                    + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                    + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                    + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                    + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
            return !pattern;
        }
    }
}
