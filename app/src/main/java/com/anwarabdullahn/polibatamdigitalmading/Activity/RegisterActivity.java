package com.anwarabdullahn.polibatamdigitalmading.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.API.API;
import com.anwarabdullahn.polibatamdigitalmading.API.APICallback;
import com.anwarabdullahn.polibatamdigitalmading.API.APIError;
import com.anwarabdullahn.polibatamdigitalmading.API.APIResponse;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.LoadingHelper;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.regex.Pattern;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class RegisterActivity extends AppCompatActivity {

    Button daftarBtn;
    String nameText,nimTxt,emailTxt,passwordTxt,repasswordTxt;
    TextView masukBtn;
    ImageView bgLogin;
    private Toolbar mToolbar;

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "Daftar", this.getClass().getSimpleName());

        masukBtn = findViewById(R.id.masukbtn);
        daftarBtn = findViewById(R.id.daftarbtn);
        bgLogin = findViewById(R.id.bgLogin);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.loginbg);
        Glide.with(this).load("https://media.giphy.com/media/26DN2YNl9k6VQqB2w/giphy.gif").apply(requestOptions).into(bgLogin);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbar.setTitle(mTitle.getText());
        mTitle.setText("DAFTAR AKUN");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        masukBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        daftarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameText        = ((EditText) findViewById(R.id.nameTxt)).getText().toString();
                Log.d("name : ",nameText);
                nimTxt          = ((EditText) findViewById(R.id.nimTxt)).getText().toString();
                emailTxt        = ((EditText) findViewById(R.id.registTxt)).getText().toString().trim();
                passwordTxt     = ((EditText) findViewById(R.id.passwordTxt)).getText().toString();
                repasswordTxt   = ((EditText) findViewById(R.id.repasswordTxt)).getText().toString();

                CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);

                if ("".equals(nameText)) {
                    Toast.makeText(RegisterActivity.this, "Mohon periksa kembali nama kamu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(nimTxt)) {
                    Toast.makeText(RegisterActivity.this, "Mohon periksa kembali nim kamu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isValidEmaillId(emailTxt)) {
                    Toast.makeText(RegisterActivity.this, "Email kamu tidak sah", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(passwordTxt)) {
                    Toast.makeText(RegisterActivity.this, "Mohon periksa kembali password kamu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ("".equals(repasswordTxt)) {
                    Toast.makeText(RegisterActivity.this, "Mohon periksa kembali re-password kamu", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!repasswordTxt.equals(passwordTxt)){
                    Toast.makeText(RegisterActivity.this, "Password tidak cocok", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (checkBox.isChecked()){

                }else {
                    Toast.makeText(RegisterActivity.this, "Kamu belum menyetujui Syarat & Ketenteuan", Toast.LENGTH_SHORT).show();
                    return;
                }

                RequestBody body = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("email", emailTxt)
                        .addFormDataPart("password", passwordTxt)
                        .addFormDataPart("name", nameText)
                        .addFormDataPart("nim", nimTxt)
                        .build();

                LoadingHelper.loadingShow(RegisterActivity.this);
                API.service().setRegister(body).enqueue(new APICallback<APIResponse>() {
                    @Override
                    protected void onSuccess(APIResponse APIResponse) {
                        LoadingHelper.loadingDismiss();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this, R.style.AlertDialog);
                        builder.setIcon(R.mipmap.ic_launcher);
                        builder.setTitle("Register");
                        builder.setMessage(APIResponse.getMessage());
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.show();
                    }

                    @Override
                    protected void onError(APIError error) {
                        LoadingHelper.loadingDismiss();
                        Toast.makeText(RegisterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onBackPressed();
        return super.onOptionsItemSelected(item);

    }
}
