package com.randeep.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.randeep.popularmovies.R;
import com.randeep.popularmovies.activity.DetailActivity;
import com.randeep.popularmovies.activity.MainActivity;
import com.randeep.popularmovies.bean.Movie;
import com.squareup.picasso.Picasso;

import static com.randeep.popularmovies.utils.Constants.COLUMN_BACKGROUND_POSTER_PATH;
import static com.randeep.popularmovies.utils.Constants.COLUMN_MOVIE_ID;
import static com.randeep.popularmovies.utils.Constants.COLUMN_OVERVIEW;
import static com.randeep.popularmovies.utils.Constants.COLUMN_POSTER_PATH;
import static com.randeep.popularmovies.utils.Constants.COLUMN_RELEASE_DATE;
import static com.randeep.popularmovies.utils.Constants.COLUMN_TITLE;
import static com.randeep.popularmovies.utils.Constants.COLUMN_VOTE_AVERAGE;
import static com.randeep.popularmovies.utils.Constants.MOVIE_DETAIL;

/**
 * Created by Randeeppulp on 10/2/17.
 */

public class FavoriteMovieListAdapter extends RecyclerView.Adapter<FavoriteMovieListAdapter.FavoriteMovieViewHolder> {


    private Cursor mCursor;
    private Context mContext;

    public FavoriteMovieListAdapter(Context context){

        mContext = context;
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView movieImageView;

        public FavoriteMovieViewHolder(View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

            mCursor.moveToPosition(getAdapterPosition());
            Movie movie = new Movie();
            movie.setMovieId(mCursor.getInt(COLUMN_MOVIE_ID));
            movie.setMovieTitle(mCursor.getString(COLUMN_TITLE));
            movie.setVoteAverage(mCursor.getDouble(COLUMN_VOTE_AVERAGE));
            movie.setPosterPath(mCursor.getString(COLUMN_POSTER_PATH));
            movie.setBackgroundPosterPath(mCursor.getString(COLUMN_BACKGROUND_POSTER_PATH));
            movie.setReleaseDate(mCursor.getString(COLUMN_RELEASE_DATE));
            movie.setOverView(mCursor.getString(COLUMN_OVERVIEW));

            Intent intent = new Intent(mContext, DetailActivity.class);
            intent.putExtra(MOVIE_DETAIL, movie);
            mContext.startActivity(intent);
        }
    }

    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.movie_poster_item, parent, false);
        FavoriteMovieViewHolder movieViewHolder = new FavoriteMovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(FavoriteMovieViewHolder holder, int position) {

        mCursor.moveToPosition(position);

        String imageUrl = "http://image.tmdb.org/t/p/w185" + mCursor.getString(COLUMN_POSTER_PATH);
        Picasso.with(mContext)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error_placeholder)
                .into(holder.movieImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }
}
