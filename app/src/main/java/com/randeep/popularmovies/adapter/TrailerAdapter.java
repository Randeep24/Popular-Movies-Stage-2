package com.randeep.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.randeep.popularmovies.R;
import com.randeep.popularmovies.activity.YoutubeVideoActivity;
import com.randeep.popularmovies.bean.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randeeppulp on 9/30/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private List<Trailer> trailerList;

    public TrailerAdapter() {

        trailerList = new ArrayList<>();
    }


    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView trailerImage;
        TextView title, playIcon;

        public TrailerViewHolder(View itemView) {
            super(itemView);

            trailerImage = itemView.findViewById(R.id.trailer_image);
            title = itemView.findViewById(R.id.trailer_name);
            playIcon = itemView.findViewById(R.id.play_icon);

            Typeface customFont = Typeface.createFromAsset(itemView.getContext().getAssets(), "icomoon.ttf");
            playIcon.setTypeface(customFont);
            itemView.setOnClickListener(this);
        }

        void setMovieImage() {
            int position = getAdapterPosition();
            String urlSource = trailerList.get(position).getYoutubeLink();
            final String BASE_URL = "http://img.youtube.com/vi/";
            String imageUrl = BASE_URL + urlSource + "/0.jpg";
            Picasso.with(itemView.getContext())
                    .load(imageUrl)
                    .fit()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error_placeholder)
                    .into(trailerImage);
        }

        @Override
        public void onClick(View view) {

            Intent intent = new Intent(view.getContext(), YoutubeVideoActivity.class);
            intent.putExtra("MEDIA_URL", "https://youtu.be/" +
                    trailerList.get(getAdapterPosition()).getYoutubeLink());
            view.getContext().startActivity(intent);
        }
    }


    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.trailer_item, parent, false);
        TrailerViewHolder trailerViewHolder = new TrailerViewHolder(view);
        return trailerViewHolder;

    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.setMovieImage();
        ;
        holder.title.setText(trailerList.get(position).getTrailerName());

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }


}
