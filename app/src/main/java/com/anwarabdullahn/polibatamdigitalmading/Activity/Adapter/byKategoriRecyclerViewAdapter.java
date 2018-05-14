package com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anwarabdullahn.polibatamdigitalmading.Activity.ByCategoryActivity;
import com.anwarabdullahn.polibatamdigitalmading.Model.Kategori;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;

/**
 * Created by anwarabdullahn on 2/2/18.
 */

public class byKategoriRecyclerViewAdapter extends RecyclerView.Adapter<byKategoriRecyclerViewAdapter.ViewHolder> {

    private List<Kategori> kategoriList;
    FirebaseAnalytics mFirebaseAnalytics;

    public byKategoriRecyclerViewAdapter(List<Kategori> kategoriList) {
        this.kategoriList = kategoriList;
    }

    @Override
    public byKategoriRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kategori,parent,false);
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(v.getContext());
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(byKategoriRecyclerViewAdapter.ViewHolder holder, int position) {

        final Kategori kategori = kategoriList.get(position);

        holder.getKategoriName().setText(kategori.getName());
        holder.getKategoriId().setText(kategori.getId());
        holder.getKategoriAmount().setText(kategori.getAmount());

        RequestOptions options = new RequestOptions();
        options.centerCrop();




        Glide.with(holder.itemView.getContext()).load(kategori.getImage()).apply(options).into(holder.kategoriImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ByCategoryActivity.class);
                intent.putExtra("categoryId", kategori.getId());
                intent.putExtra("categoryName", kategori.getName());
                intent.putExtra("categoryBg", kategori.getImage());

                Bundle params = new Bundle();
                params.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, kategori.getName());
                params.putString(FirebaseAnalytics.Param.ITEM_NAME, kategori.getName());
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, params);

                v.getContext().startActivity(intent);
            }
        });


    }



    @Override
    public int getItemCount() {
        return kategoriList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView kategoriName, kategoriId, kategoriAmount;
    private ImageView kategoriImage;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            kategoriId = (TextView) itemView.findViewById(R.id.kategoriId);
            kategoriName = (TextView) itemView.findViewById(R.id.kategoriName);
            kategoriAmount = (TextView) itemView.findViewById(R.id.kategoriAmount);
            kategoriImage = (ImageView) itemView.findViewById(R.id.kategoriImage);


        }

        public TextView getKategoriAmount() {
            return kategoriAmount;
        }
        public TextView getKategoriId() {
            return kategoriId;
        }
        public TextView getKategoriName() {
            return kategoriName;
        }


    }
}
