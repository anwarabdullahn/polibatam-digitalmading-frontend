package com.anwarabdullahn.polibatamdigitalmading.Activity.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anwarabdullahn.polibatamdigitalmading.Activity.DetailActivity;
import com.anwarabdullahn.polibatamdigitalmading.Model.Event;
import com.anwarabdullahn.polibatamdigitalmading.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by anwarabdullahn on 2/6/18.
 */

public class byJadwalEventRecyclerVAdapter extends RecyclerView.Adapter<byJadwalEventRecyclerVAdapter.ViewHolder> {

    List<Event> eventList;

    public byJadwalEventRecyclerVAdapter(List<Event> eventList) {
        this.eventList = eventList;
    }

    @Override
    public byJadwalEventRecyclerVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_onitem,parent,false);

        return new byJadwalEventRecyclerVAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final byJadwalEventRecyclerVAdapter.ViewHolder holder, int position) {

        final Event event = eventList.get(position);

        holder.getEventName().setText(event.getTitle());
        holder.getAuthorName().setText(event.getAuthor().getName());
        holder.getEventDate().setText(event.getDate());

        RequestOptions options = new RequestOptions();
        options.centerCrop();

        Glide.with(holder.itemView).load(event.getImage()).apply(options).into(holder.eventImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
                intent.putExtra("Title", event.getTitle());
                intent.putExtra("Detail", event.getDate());
                intent.putExtra("Image", event.getImage());
                intent.putExtra("Description", event.getDescription());
                intent.putExtra("AuthorName", event.getAuthor().getName());
                intent.putExtra("AuthorImage", event.getAuthor().getAvatar());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView eventName, eventDate, authorName;
        private ImageView eventImage;

        public ViewHolder(View itemView) {
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.titleTxt);
            authorName = (TextView) itemView.findViewById(R.id.authorNameTxt);
            eventDate = (TextView) itemView.findViewById(R.id.categoryTxt);
            eventImage = (ImageView) itemView.findViewById(R.id.imageView);
        }

        public ImageView getEventImage() {
            return eventImage;
        }

        public TextView getAuthorName() {
            return authorName;
        }

        public TextView getEventDate() {
            return eventDate;
        }


        public TextView getEventName() {
            return eventName;
        }
    }
}
