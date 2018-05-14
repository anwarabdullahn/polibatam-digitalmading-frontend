package com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.anwarabdullahn.polibatamdigitalmading.Model.Banner;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.anwarabdullahn.polibatamdigitalmading.R;

import java.util.ArrayList;

/**
 * Created by anwarabdullahn on 2/1/18.
 */

public class BannerViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Banner> bannerList;

    public BannerViewPagerAdapter(Context context,ArrayList<Banner> bannerList) {
        this.context = context;
        this.bannerList = bannerList;
    }

    @Override
    public int getCount() {
        return bannerList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        Banner banner = bannerList.get(position);

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.banner_layout, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Glide.with(context).load(banner.getImage()).apply(options).into(imageView);

        ViewPager vp = (ViewPager) container;
        vp.addView(view ,0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);
    }
}
