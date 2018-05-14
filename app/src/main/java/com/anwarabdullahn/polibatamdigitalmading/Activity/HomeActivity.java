package com.anwarabdullahn.polibatamdigitalmading.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.API.API;
import com.anwarabdullahn.polibatamdigitalmading.API.APICallback;
import com.anwarabdullahn.polibatamdigitalmading.API.APIError;
import com.anwarabdullahn.polibatamdigitalmading.API.APIResponse;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter.ViewPagerAdapter;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.Firebase.App.Config;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.Firebase.Utils.NotificationUtils;
import com.anwarabdullahn.polibatamdigitalmading.Model.Profile;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.messaging.FirebaseMessaging;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    private ViewPager mViewPager;
    private Toolbar mToolbar;
    private ViewPagerAdapter mViewPagerAdapter;
    private TabLayout mTabLayout;
    private DrawerLayout myDrawer;
    private ActionBarDrawerToggle myToggle;
    FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setCurrentScreen(this, "Home", this.getClass().getSimpleName());

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        mToolbar.setTitle(mTitle.getText());
        mTitle.setText("DIGITAL MADING");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setViewPager();

        myDrawer = (DrawerLayout) findViewById(R.id.myDrawer);
        myToggle = new ActionBarDrawerToggle(this, myDrawer, R.string.open, R.string.close);

        myDrawer.addDrawerListener(myToggle);
        myToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        API.service().profile().enqueue(new APICallback<Profile>() {
            @Override
            protected void onSuccess(Profile profile) {
                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                Menu itemView = (Menu) navigationView.getMenu();


//                TextView profileTxt = ((TextView) headerView.findViewById(R.id.profileTxt));
//                ImageView profileImg = (ImageView) headerView.findViewById(R.id.profileImg);

                MenuItem profileText = (itemView.findItem(R.id.profile));
                profileText.setTitle((CharSequence) profile.getData().getName());


//                profileTxt.setText((CharSequence) profile.getData().getName());
//                Picasso.with(headerView.getContext()).load(profile.getData().getAvatar()).resize(200,200).centerCrop().placeholder(R.drawable.ic_profile).transform(new CircleTransform()).into(profileImg);

            }

            @Override
            protected void onError(APIError error) {

            }
        });

        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
//                    FirebaseMessaging.getInstance().subscribeToTopic("news");
                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received

                    String message = intent.getStringExtra("message");

                    Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

//                    txtMessage.setText(message);
                }
            }
        };

        displayFirebaseRegId();
    }

    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.e(TAG, "Firebase reg id: " + regId);

//        if (!TextUtils.isEmpty(regId))
//            txtRegId.setText("Firebase Reg Id: " + regId);
//        else
//            txtRegId.setText("Firebase Reg Id is not received yet!");
//    }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    private void setViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mViewPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab);
        mTabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (myToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void keluar(MenuItem item) {
        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialog).create();
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Apakah Anda Yakin Ingin Keluar ?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "IYA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        API.service().logout().enqueue(new APICallback<APIResponse>() {
                            @Override
                            protected void onSuccess(APIResponse apiMessage) {
                                Intent logout = new Intent(HomeActivity.this, LoginActivity.class);
                                startActivity(logout);
                                finish();
                                API.logOut();
                            }

                            @Override
                            protected void onError(APIError error) {

                            }
                        });
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "BATAL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        alertDialog.show();
    }

    public void profile(MenuItem item) {
        Intent profile = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(profile);
    }

    public void kategori(MenuItem item) {
        Intent profile = new Intent(HomeActivity.this, CategoryActivity.class);
        startActivity(profile);
    }

    public void jadwal(MenuItem item) {
        Intent jadwal = new Intent(HomeActivity.this, JadwalActivity.class);
        startActivity(jadwal);
    }


}
