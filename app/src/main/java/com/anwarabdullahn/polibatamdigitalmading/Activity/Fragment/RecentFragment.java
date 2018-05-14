package com.anwarabdullahn.polibatamdigitalmading.Activity.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.anwarabdullahn.polibatamdigitalmading.API.API;
import com.anwarabdullahn.polibatamdigitalmading.API.APICallback;
import com.anwarabdullahn.polibatamdigitalmading.API.APIError;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter.BannerViewPagerAdapter;
import com.anwarabdullahn.polibatamdigitalmading.Activity.CategoryActivity;
import com.anwarabdullahn.polibatamdigitalmading.Activity.Utility.LoadingHelper;
import com.anwarabdullahn.polibatamdigitalmading.Model.Banner;
import com.anwarabdullahn.polibatamdigitalmading.Model.ListBanner;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecentFragment extends Fragment {

    public static final String TITLE = "Terkini";

    ViewPager viewPager;
    LinearLayout sliderDots;
    private int dotsCount;
    private ImageView [] dots;
    int numberHolder;
    Button cariBtn;

    FirebaseAnalytics mFirebaseAnalytics;

    public static RecentFragment newInstance() {

        return new RecentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View contentView = inflater.inflate(R.layout.fragment_recent, container, false);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this.getContext());
        mFirebaseAnalytics.setCurrentScreen(this.getActivity(), "Banner Home", this.getClass().getSimpleName());

        viewPager = (ViewPager) contentView.findViewById(R.id.viewPager);
        sliderDots = (LinearLayout) contentView.findViewById(R.id.sliderDots);

        cariBtn = (Button) contentView.findViewById(R.id.cariBtn);
        cariBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoryActivity.class);
                startActivity(intent);
            }
        });

        LoadingHelper.loadingShow(contentView.getContext());
        API.service().bannerList().enqueue(new APICallback<ListBanner>() {
            @Override
            protected void onSuccess(ListBanner listBanner) {
                LoadingHelper.loadingDismiss();
                ArrayList<Banner> bannerList = listBanner.getData();

                numberHolder =  listBanner.getData().size();

                BannerViewPagerAdapter bannerViewPagerAdapter = new BannerViewPagerAdapter(contentView.getContext(),bannerList);

                viewPager.setAdapter(bannerViewPagerAdapter);

                dotsCount = bannerViewPagerAdapter.getCount();

                dots = new ImageView[dotsCount];
                for (int i = 0; i < dotsCount; i++) {
                    dots[i] = new ImageView(contentView.getContext());
                    dots[i].setImageDrawable(ContextCompat.getDrawable(contentView.getContext(),R.drawable.nonactivedots));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(8, 0, 9, 0);
                    sliderDots.addView(dots[i],params);
                }
                dots[0].setImageDrawable(ContextCompat.getDrawable(contentView.getContext(),R.drawable.activedots));
                viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        
                    }

                    @Override
                    public void onPageSelected(int position) {
                        for (int i = 0; i < dotsCount; i++) {
                            dots[i].setImageDrawable(ContextCompat.getDrawable(contentView.getContext(),R.drawable.nonactivedots));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(contentView.getContext(),R.drawable.activedots));
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

                Timer timer = new Timer();
                timer.scheduleAtFixedRate(new MyTimerTask(),2000,4000);
            }

            @Override
            protected void onError(APIError error) {
                LoadingHelper.loadingDismiss();
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



        return contentView;
    }

    public class MyTimerTask extends TimerTask{

        @Override
        public void run() {

            if(getActivity() == null)
                return;

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (numberHolder == '5'){
                        if (viewPager.getCurrentItem() == 0){
                            viewPager.setCurrentItem(1);
                        }else if (viewPager.getCurrentItem() == 1){
                            viewPager.setCurrentItem(2);
                        }else if (viewPager.getCurrentItem() == 2){
                            viewPager.setCurrentItem(3);
                        }else  if (viewPager.getCurrentItem() == 3){
                            viewPager.setCurrentItem(4);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }else {
                        if (viewPager.getCurrentItem() == 0){
                            viewPager.setCurrentItem(1);
                        }else if (viewPager.getCurrentItem() == 1){
                            viewPager.setCurrentItem(2);
                        }else {
                            viewPager.setCurrentItem(0);
                        }
                    }

                }
            });
        }
    }
}