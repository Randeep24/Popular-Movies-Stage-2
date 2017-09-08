package com.randeep.popularmovies.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ShapeDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.randeep.popularmovies.R;
import com.randeep.popularmovies.Utils.Constants;
import com.randeep.popularmovies.adapter.MoviesListAdapter;
import com.randeep.popularmovies.bean.Movie;
import com.randeep.popularmovies.network.NetworkCall;
import com.randeep.popularmovies.Utils.ToolbarShapeBackground;

import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NetworkCall.UpdateMovieListView, MoviesListAdapter.MovieListItemClickListener {

    private Toolbar toolbar;
    private RecyclerView moviesListView;
    private ProgressBar progressBar;
    private TextView errorText, titleTextView;

    private MoviesListAdapter moviesListAdapter;
    private List<Movie> movieList;
    private View view;
    private int spanCount = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        moviesListView = findViewById(R.id.movies_list);
        view = findViewById(R.id.rectangle_shape);
        progressBar = findViewById(R.id.progress_bar);
        errorText = findViewById(R.id.error_message);
        titleTextView = findViewById(R.id.title);

        view.setBackground(new ShapeDrawable(new ToolbarShapeBackground()));

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            spanCount = 3;
        }

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, spanCount);
        moviesListView.setLayoutManager(gridLayoutManager);

        moviesListAdapter = new MoviesListAdapter(this);
        moviesListView.setAdapter(moviesListAdapter);

       
        new NetworkCall(this).fetchMoviesList(Constants.POPULAR_MOVIES);

    }


    @Override
    public void updateList(List<Movie> movieList) {
        progressBar.setVisibility(View.GONE);
        moviesListView.setVisibility(View.VISIBLE);
        this.movieList = movieList;
        moviesListAdapter.setMovieList(movieList);
    }

    @Override
    public void showError() {
        progressBar.setVisibility(View.GONE);
        errorText.setVisibility(View.VISIBLE);
    }

    @Override
    public void movieListItemClicked(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(Constants.MOVIE_DETAIL, movieList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        moviesListView.setVisibility(View.GONE);
        errorText.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        switch (id) {
            case R.id.action_popular:
                titleTextView.setText(getString(R.string.popular));
                new NetworkCall(this).fetchMoviesList(Constants.POPULAR_MOVIES);
                return true;
            case R.id.action_top_rated:
                titleTextView.setText(getString(R.string.top_rated));
                new NetworkCall(this).fetchMoviesList(Constants.HIGHEST_RATED);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
