package com.randeep.popularmovies.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
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
import com.randeep.popularmovies.network.NetworkCall;
import com.randeep.popularmovies.utils.Constants;
import com.randeep.popularmovies.bean.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements NetworkCall.UpdateReviewAndTrailerList {

    private ImageView backgroundImage, posterImage;
    private TextView movieTitle, movieReleaseDate, movieVote, overview, noInternetText;
    private TextView shareIcon, favoriteIcon, reviewText, trailerText, noReviewText;

    private AppCompatRatingBar ratingBar;
    private Toolbar toolbar;
    private RecyclerView trailerRecyclerView, reviewRecyclerView;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private List<Trailer> trailerList;

    private Movie movie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent != null) {
            movie = intent.getParcelableExtra(Constants.MOVIE_DETAIL);
        }

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        backgroundImage = findViewById(R.id.background_image);
        posterImage = findViewById(R.id.movie_poster);

        movieTitle = findViewById(R.id.title);
        movieReleaseDate = findViewById(R.id.release_date);
        movieVote = findViewById(R.id.vote_average);
        overview = findViewById(R.id.overview);
        favoriteIcon = findViewById(R.id.favorite);
        shareIcon = findViewById(R.id.share);

        reviewText = findViewById(R.id.review_text);
        trailerText = findViewById(R.id.trailer_text);

        ratingBar = findViewById(R.id.rating_bar);

        noReviewText = findViewById(R.id.no_reviews);
        noInternetText = findViewById(R.id.no_internet);

        trailerRecyclerView = findViewById(R.id.trailer_list);
        reviewRecyclerView = findViewById(R.id.reviewList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        trailerRecyclerView.setHasFixedSize(true);
        trailerRecyclerView.setLayoutManager(linearLayoutManager);

        trailerAdapter = new TrailerAdapter();
        trailerRecyclerView.setAdapter(trailerAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setHasFixedSize(true);
        reviewRecyclerView.setLayoutManager(layoutManager);

        reviewAdapter = new ReviewAdapter();
        reviewRecyclerView.setAdapter(reviewAdapter);

        Picasso.with(this).load("http://image.tmdb.org/t/p/w780" + movie.getBackgroundPosterPath()).into(backgroundImage);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w185" + movie.getPosterPath()).into(posterImage);

        movieTitle.setText(movie.getMovieTitle());
        movieReleaseDate.setText(movie.getReleaseDate());
        movieVote.setText(String.format(getString(R.string.ratings), movie.getVoteAverage()));
        overview.setText(movie.getOverView());
        ratingBar.setRating((float) (movie.getVoteAverage() / 2));

        isMovieFavorite();

        new NetworkCall().fetchTrailerAndReviewList(this, movie.getMovieId());

        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveOrDeleteFromFavorite();
            }
        });

        shareIcon.setOnClickListener(new View.OnClickListener() {
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

            favoriteIcon.setText("\ue900");
            favoriteIcon.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

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

            favoriteIcon.setText("\ue901");
            favoriteIcon.setTextColor(Color.RED);

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
                favoriteIcon.setText("\ue901");
                favoriteIcon.setTextColor(Color.RED);
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
        reviewText.setVisibility(View.VISIBLE);
        if (reviewList.size() > 0) {
            reviewRecyclerView.setVisibility(View.VISIBLE);
            reviewAdapter.setReviewList(reviewList);
        } else {
            noReviewText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateTrailerList(List<Trailer> trailerList) {
        if (trailerList.size() > 0) {
            this.trailerList = trailerList;
            trailerText.setVisibility(View.VISIBLE);
            trailerRecyclerView.setVisibility(View.VISIBLE);
            trailerAdapter.setTrailerList(trailerList);
        }
    }


    @Override
    public void showError() {
        noInternetText.setVisibility(View.VISIBLE);
    }
}
