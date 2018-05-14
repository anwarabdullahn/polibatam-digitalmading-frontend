package com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anwarabdullahn.polibatamdigitalmading.Activity.DetailActivity;
import com.anwarabdullahn.polibatamdigitalmading.Model.Berita;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by anwarabdullahn on 2/2/18.
 */

public class BeritaRecyclerViewAdapter extends RecyclerView.Adapter<BeritaRecyclerViewAdapter.ViewHolder> {

    List<Berita> beritaList;

    public BeritaRecyclerViewAdapter(List<Berita> beritaList) {
        this.beritaList = beritaList;
    }

    @Override
    public BeritaRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_onitem,parent,false);
        return new BeritaRecyclerViewAdapter.ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView beritaNameTxt, authorNameTxt, categoryTxt;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);

            beritaNameTxt = (TextView) itemView.findViewById(R.id.titleTxt);
            authorNameTxt = (TextView) itemView.findViewById(R.id.authorNameTxt);
            categoryTxt = (TextView) itemView.findViewById(R.id.categoryTxt);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public TextView getAuthorNameTxt() {
            return authorNameTxt;
        }

        public TextView getBeritaNameTxt() {
            return beritaNameTxt;
        }

        public TextView getCategoryTxt() {
            return categoryTxt;
        }

    }

    @Override
    public void onBindViewHolder(final BeritaRecyclerViewAdapter.ViewHolder holder, int position) {

        final Berita berita = beritaList.get(position);

        holder.getAuthorNameTxt().setText(berita.getAuthor().getName());
        holder.getBeritaNameTxt().setText(berita.getTitle());
        holder.getCategoryTxt().setText(berita.getCategory());

        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Glide.with(holder.getImageView()).load(berita.getImage()).apply(options).into(holder.imageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);

                String urlFile = "http://digitalmading.xyz/file";
                String holder = berita.getFile().trim();

                intent.putExtra("Title", berita.getTitle());
                intent.putExtra("Detail", berita.getCategory());
                intent.putExtra("Image", berita.getImage());

                if (holder.equals(urlFile)){
                    intent.putExtra("File", "null");
                }else {
                    intent.putExtra("File", berita.getFile());
                }

                if ((berita.getDescription())!=null){
                    intent.putExtra("Description", berita.getDescription());
                }else if ((berita.getDescription())==null){
                    intent.putExtra("Description", "Tidak Ada Deskripsi");
                }
                intent.putExtra("AuthorName", berita.getAuthor().getName());
                intent.putExtra("AuthorImage", berita.getAuthor().getAvatar());
                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return beritaList.size();
    }


}
