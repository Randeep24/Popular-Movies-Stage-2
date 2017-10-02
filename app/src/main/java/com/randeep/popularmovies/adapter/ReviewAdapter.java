package com.randeep.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.randeep.popularmovies.R;
import com.randeep.popularmovies.bean.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randeeppulp on 10/1/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private List<Review> reviewList;

    public ReviewAdapter() {

        reviewList = new ArrayList<>();
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.review_item, parent, false);
        ReviewViewHolder trailerViewHolder = new ReviewViewHolder(view);
        return trailerViewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.authorTextView.setText(reviewList.get(position).getAuthor());
        holder.content.setText(reviewList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView authorTextView;
        TextView content;

        public ReviewViewHolder(View itemView) {
            super(itemView);

            authorTextView = itemView.findViewById(R.id.author_name);
            content = itemView.findViewById(R.id.content);

        }

    }

    public void setReviewList(List<Review> reviews){
        reviewList = reviews;
        notifyDataSetChanged();
    }
}
