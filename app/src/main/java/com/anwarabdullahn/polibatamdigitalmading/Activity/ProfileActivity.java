package com.anwarabdullahn.polibatamdigitalmading.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.API.API;
import com.anwarabdullahn.polibatamdigitalmading.API.APICallback;
import com.anwarabdullahn.polibatamdigitalmading.API.APIError;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.CircleTransform;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.LoadingHelper;
import com.anwarabdullahn.polibatamdigitalmading.Model.Profile;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.squareup.picasso.Picasso;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    String name;
    EditText profileName;
    ImageView mProfileImage;
    TextView nimTxt, emailTxt, nameHolder, registTxt;

    public static final int REQUEST_PROFILE = 1;
    private CharSequence[] items = {"Galeri"};
    private File mImage;

    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "Profile", this.getClass().getSimpleName());

        profileName = (EditText) findViewById(R.id.profileName);
        mProfileImage = (ImageView) findViewById(R.id.authorImage);
//        nameHolder  = (TextView) findViewById(R.id.holder);
        mProfileImage = (ImageView) findViewById(R.id.authorImage);

        EasyImage.configuration(this)
                .setImagesFolderName("Sample app images")
                .saveInAppExternalFilesDir()
                .setCopyExistingPicturesToPublicLocation(true);

        LoadingHelper.loadingShow(this);
        API.service().profile().enqueue(new APICallback<Profile>() {
            @Override
            protected void onSuccess(Profile profile) {
                LoadingHelper.loadingDismiss();

                mToolbar = (Toolbar) findViewById(R.id.toolbar);
                TextView mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
                mToolbar.setTitle(mTitle.getText());
                mTitle.setText("PROFILE");
                setSupportActionBar(mToolbar);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);

                nimTxt = (TextView) findViewById(R.id.nimTxt);
                emailTxt = (TextView) findViewById(R.id.emailTxt);
                registTxt = (TextView) findViewById(R.id.registTxt);

                name = profile.getData().getName();

                profileName.setText(name);

                nimTxt.setText(profile.getData().getNim());
                emailTxt.setText(profile.getData().getEmail());
                registTxt.setText(profile.getData().getRegistered());
                Picasso.with(getBaseContext()).load(profile.getData().getAvatar()).centerCrop().resize(200, 200).placeholder(R.drawable.ic_profile).transform(new CircleTransform()).into(mProfileImage);
            }

            @Override
            protected void onError(APIError error) {
                LoadingHelper.loadingDismiss();
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

        if (!(profileName.getText().toString()).equals(name)) {
            profileName.getText().toString();
        }

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this, R.style.AlertDialog);
                builder.setIcon(R.mipmap.ic_launcher);
                builder.setTitle("Change Picture");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                EasyImage.openGallery(ProfileActivity.this, 0);
                                break;
                        }
                    }
                });
                builder.setNegativeButton("Batal", null);
                builder.show();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, UbahPasswordActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void OnTakePhoto() {
        /**Permission check only required if saving pictures to root of sdcard*/
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            EasyImage.openCamera(this, 0);
        } else {
            Nammu.askForPermission(this, Manifest.permission.CAMERA, new PermissionCallback() {

                @Override
                public void permissionGranted() {
                    EasyImage.openCamera(ProfileActivity.this, 0);
                }

                @Override
                public void permissionRefused() {

                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PROFILE && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        } else {
            EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
                @Override
                public void onImagePickerError(Exception e, EasyImage.ImageSource source, int type) {
                    //Some error handling
                }

                @Override
                public void onImagePicked(File imageFile, EasyImage.ImageSource source, int type) {
                    //Handle the image
                    mImage = imageFile;
                    onPhotoReturned(imageFile);
                }

                @Override
                public void onCanceled(EasyImage.ImageSource source, int type) {
                    //Cancel handling, you might wanna remove taken photo if it was canceled
                    if (source == EasyImage.ImageSource.CAMERA) {
                        File photoFile = EasyImage.lastlyTakenButCanceledPhoto(ProfileActivity.this);
                        if (photoFile != null) photoFile.delete();
                    }
                }
            });
        }


    }

    private void onPhotoReturned(File photoFile) {
        Picasso.with(this)
                .load(photoFile)
                .fit()
                .centerCrop()
                .transform(new CircleTransform())
                .into(mProfileImage);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.simpan:
                if (mImage != null) {
                    Log.d("Test", mImage.getName().toString());
                    RequestBody body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("avatar", mImage.getName(), RequestBody.create(MediaType.parse("image/jpeg"), mImage))
                            .build();
                    LoadingHelper.loadingShow(this);
                    API.service().profileImage(body).enqueue(new APICallback<Profile>() {
                        @Override
                        protected void onSuccess(Profile profile) {
                            LoadingHelper.loadingDismiss();
                            final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this, R.style.AlertDialog);
                            builder.setTitle("Berhasil");
                            builder.setMessage("Perubahan berhasil disimpan");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                                    startActivityForResult(intent, REQUEST_PROFILE);
                                }
                            });
                            builder.show();
                        }

                        @Override
                        protected void onError(APIError error) {
                            LoadingHelper.loadingDismiss();
                            Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                } else if (mImage == null) {
//                    Toast.makeText(ProfileActivity.this,"Null",Toast.LENGTH_LONG);
                    String nameHolder = profileName.getText().toString();

                    RequestBody body = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("name", nameHolder)
                            .build();
                    LoadingHelper.loadingShow(this);
                    API.service().profileUpdate(body).enqueue(new APICallback<Profile>() {
                        @Override
                        protected void onSuccess(Profile profile) {
                            LoadingHelper.loadingDismiss();
                            final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this, R.style.AlertDialog);
                            builder.setTitle("Berhasil");
                            builder.setMessage("Perubahan berhasil disimpan");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                                    startActivityForResult(intent, REQUEST_PROFILE);
                                }
                            });
                            builder.show();
                        }

                        @Override
                        protected void onError(APIError error) {
                            LoadingHelper.loadingDismiss();
                            Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                return true;
            default:
                super.onBackPressed();
                return super.onOptionsItemSelected(item);
        }

    }
}
