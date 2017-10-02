package com.randeep.popularmovies.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.drawable.ShapeDrawable;
import android.os.PersistableBundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.randeep.popularmovies.R;
import com.randeep.popularmovies.adapter.FavoriteMovieListAdapter;
import com.randeep.popularmovies.data.FavoriteMovieContract;
import com.randeep.popularmovies.utils.Constants;
import com.randeep.popularmovies.adapter.MoviesListAdapter;
import com.randeep.popularmovies.bean.Movie;
import com.randeep.popularmovies.network.NetworkCall;
import com.randeep.popularmovies.utils.ToolbarShapeBackground;

import java.util.List;

import static com.randeep.popularmovies.utils.Constants.FAVORITE;
import static com.randeep.popularmovies.utils.Constants.HIGHEST_RATED;
import static com.randeep.popularmovies.utils.Constants.HIGHEST_RATED_MOVIES;
import static com.randeep.popularmovies.utils.Constants.MOVIE_DETAIL_PROJECTION;
import static com.randeep.popularmovies.utils.Constants.POPULAR;
import static com.randeep.popularmovies.utils.Constants.SORT_TYPE;

public class MainActivity extends AppCompatActivity implements
        NetworkCall.UpdateMovieListView, MoviesListAdapter.MovieListItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    private Toolbar toolbar;
    private RecyclerView moviesListView;
    private ProgressBar progressBar;
    private TextView errorText, titleTextView;

    private MoviesListAdapter moviesListAdapter;
    private FavoriteMovieListAdapter favoriteMovieListAdapter;
    private List<Movie> movieList;
    private View view;
    private int spanCount = 2;

    private static final int FAVORITE_LOADER_ID = 24;
    private int mPosition = RecyclerView.NO_POSITION;
    private int sortingType = POPULAR;





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

        favoriteMovieListAdapter = new FavoriteMovieListAdapter(this);

        if (savedInstanceState != null){
            sortingType = savedInstanceState.getInt(SORT_TYPE);
        }

        switch (sortingType){
            case POPULAR:
                new NetworkCall().fetchMoviesList(this, Constants.POPULAR_MOVIES);
                break;
            case HIGHEST_RATED_MOVIES:
                titleTextView.setText(getString(R.string.top_rated));
                new NetworkCall().fetchMoviesList(this, Constants.HIGHEST_RATED);
                break;
            case FAVORITE:
                titleTextView.setText(getString(R.string.favorite));
                getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);
                break;
        }


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
                checkPreviousSorting();
                sortingType = POPULAR;
                titleTextView.setText(getString(R.string.popular));
                new NetworkCall().fetchMoviesList(this, Constants.POPULAR_MOVIES);
                return true;
            case R.id.action_top_rated:
                checkPreviousSorting();
                sortingType = HIGHEST_RATED_MOVIES;
                titleTextView.setText(getString(R.string.top_rated));
                new NetworkCall().fetchMoviesList(this, Constants.HIGHEST_RATED);
                return true;
            case R.id.action_favorite:
                sortingType = FAVORITE;
                titleTextView.setText(getString(R.string.favorite));
                getSupportLoaderManager().initLoader(FAVORITE_LOADER_ID, null, this);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void checkPreviousSorting() {
        if (sortingType == FAVORITE){
            getSupportLoaderManager().destroyLoader(FAVORITE_LOADER_ID);
            moviesListView.setAdapter(moviesListAdapter);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {
            case FAVORITE_LOADER_ID:
                return new CursorLoader(this,
                        FavoriteMovieContract.MovieEntry.CONTENT_URI,
                        MOVIE_DETAIL_PROJECTION,
                        null,
                        null,
                        null);
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (sortingType == FAVORITE) {
            progressBar.setVisibility(View.GONE);
            moviesListView.setVisibility(View.VISIBLE);
            if (!(moviesListView.getAdapter() instanceof FavoriteMovieListAdapter)) {
                moviesListView.setAdapter(favoriteMovieListAdapter);
            }
            favoriteMovieListAdapter.swapCursor(data);
            if (mPosition == RecyclerView.NO_POSITION) mPosition = 0;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (sortingType == FAVORITE) {
            favoriteMovieListAdapter.swapCursor(null);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SORT_TYPE,sortingType);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sortingType == FAVORITE){
            getSupportLoaderManager().restartLoader(FAVORITE_LOADER_ID,null,this);
        }
    }
}
