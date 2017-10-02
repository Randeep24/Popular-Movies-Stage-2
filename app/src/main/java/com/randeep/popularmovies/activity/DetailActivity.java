package com.randeep.popularmovies.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.randeep.popularmovies.R;
import com.randeep.popularmovies.adapter.ReviewAdapter;
import com.randeep.popularmovies.adapter.TrailerAdapter;
import com.randeep.popularmovies.bean.Review;
import com.randeep.popularmovies.bean.Trailer;
import com.randeep.popularmovies.data.FavoriteMovieContract;
import com.randeep.popularmovies.databinding.ActivityDetailBinding;
import com.randeep.popularmovies.network.NetworkCall;
import com.randeep.popularmovies.utils.Constants;
import com.randeep.popularmovies.bean.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements NetworkCall.UpdateReviewAndTrailerList {

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private List<Trailer> trailerList;

    private Movie movie;

    ActivityDetailBinding mBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null) {
            movie = intent.getParcelableExtra(Constants.MOVIE_DETAIL);
        }

        mBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mBinding.trailerList.setHasFixedSize(true);
        mBinding.trailerList.setLayoutManager(linearLayoutManager);

        trailerAdapter = new TrailerAdapter();
        mBinding.trailerList.setAdapter(trailerAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.reviewList.setHasFixedSize(true);
        mBinding.reviewList.setLayoutManager(layoutManager);

        reviewAdapter = new ReviewAdapter();
        mBinding.reviewList.setAdapter(reviewAdapter);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w780" + movie.getBackgroundPosterPath()).into(mBinding.backgroundImage);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185" + movie.getPosterPath()).into(mBinding.moviePoster);

        mBinding.title.setText(movie.getMovieTitle());
        mBinding.releaseDate.setText(movie.getReleaseDate());
        mBinding.voteAverage.setText(String.format(getString(R.string.ratings), movie.getVoteAverage()));
        mBinding.overview.setText(movie.getOverView());
        mBinding.ratingBar.setRating((float) (movie.getVoteAverage() / 2));

        isMovieFavorite();

        new NetworkCall().fetchTrailerAndReviewList(this, movie.getMovieId());

        mBinding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOrDeleteFromFavorite();
            }
        });

        mBinding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != trailerList && trailerList.size() > 0){
                    String trailerUrl = "https://www.youtube.com/watch?v=" + trailerList.get(0).getYoutubeLink();
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, trailerUrl);
                    sendIntent.setType("text/plain");
                    startActivity(Intent.createChooser(sendIntent, getString(R.string.share)));
                }
            }
        });
    }

    private void saveOrDeleteFromFavorite() {

        if (isMovieFavorite()) {

            getContentResolver().delete(FavoriteMovieContract.MovieEntry.CONTENT_URI,
                    FavoriteMovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{movie.getMovieId()+""});

            mBinding.favorite.setText("\ue900");
            mBinding.favorite.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        } else {

            ContentValues contentValues = new ContentValues();
            contentValues.put(FavoriteMovieContract.MovieEntry.COLUMN_MOVIE_ID, movie.getMovieId());
            contentValues.put(FavoriteMovieContract.MovieEntry.COLUMN_TITLE, movie.getMovieTitle());
            contentValues.put(FavoriteMovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
            contentValues.put(FavoriteMovieContract.MovieEntry.COLUMN_POSTER_PATH, movie.getPosterPath());
            contentValues.put(FavoriteMovieContract.MovieEntry.COLUMN_BACKGROUND_POSTER_PATH, movie.getBackgroundPosterPath());
            contentValues.put(FavoriteMovieContract.MovieEntry.COLUMN_RELEASE_DATE, movie.getReleaseDate());
            contentValues.put(FavoriteMovieContract.MovieEntry.COLUMN_OVERVIEW, movie.getOverView());

            getContentResolver().insert(FavoriteMovieContract.MovieEntry.CONTENT_URI, contentValues);

            mBinding.favorite.setText("\ue901");
            mBinding.favorite.setTextColor(Color.RED);

        }

    }

    private boolean isMovieFavorite() {

        Cursor cursor = null;
        try {

            cursor = getContentResolver().query(FavoriteMovieContract.MovieEntry.CONTENT_URI,
                    new String[]{FavoriteMovieContract.MovieEntry.COLUMN_MOVIE_ID},
                    FavoriteMovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{movie.getMovieId() + ""}, null);

            if (cursor != null && cursor.moveToFirst()) {
                mBinding.favorite.setText("\ue901");
                mBinding.favorite.setTextColor(Color.RED);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
//            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void updateReviewList(List<Review> reviewList) {
        mBinding.reviewText.setVisibility(View.VISIBLE);
        if (reviewList.size() > 0) {
            mBinding.reviewList.setVisibility(View.VISIBLE);
            reviewAdapter.setReviewList(reviewList);
        } else {
            mBinding.noReviews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateTrailerList(List<Trailer> trailerList) {
        if (trailerList.size() > 0) {
            this.trailerList = trailerList;
            mBinding.trailerText.setVisibility(View.VISIBLE);
            mBinding.trailerList.setVisibility(View.VISIBLE);
            trailerAdapter.setTrailerList(trailerList);
        }
    }


    @Override
    public void showError() {
        mBinding.noInternet.setVisibility(View.VISIBLE);
    }
}
