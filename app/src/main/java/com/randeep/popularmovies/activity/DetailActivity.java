package com.randeep.popularmovies.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.randeep.popularmovies.R;
import com.randeep.popularmovies.Utils.Constants;
import com.randeep.popularmovies.bean.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView backgroundImage, posterImage;
    private TextView movieTitle, movieReleaseDate, movieVote, overview;
    private AppCompatRatingBar ratingBar;
    private Toolbar toolbar;
    Movie movie;

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

        ratingBar = findViewById(R.id.rating_bar);


        Picasso.with(this).load(movie.getBackgroundPosterPath()).into(backgroundImage);
        Picasso.with(this).load(movie.getPosterPath()).into(posterImage);

        movieTitle.setText(movie.getMovieTitle());
        movieReleaseDate.setText(movie.getReleaseDate());
        movieVote.setText(String.format(getString(R.string.ratings),movie.getVoteAverage()));
        overview.setText(movie.getOverView());
        ratingBar.setRating((float) (movie.getVoteAverage() / 2));
    }


}
