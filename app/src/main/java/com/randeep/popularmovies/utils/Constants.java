package com.randeep.popularmovies.utils;

import com.randeep.popularmovies.data.FavoriteMovieContract;

/**
 * Created by Randeeppulp on 9/2/17.
 */

public interface Constants {

    String BASE_URL = "http://api.themoviedb.org/3/movie/";

    String POPULAR_MOVIES = "popular";
    String HIGHEST_RATED = "top_rated";

    String MOVIE_DETAIL = "movie_detail";

    int CONNECTION_TIMEOUT = 5;

    String SORT_TYPE = "sort_type";
    int POPULAR = 1;
    int HIGHEST_RATED_MOVIES = 2;
    int FAVORITE = 3;


    public static final String[] MOVIE_DETAIL_PROJECTION = {
            FavoriteMovieContract.MovieEntry.COLUMN_MOVIE_ID,
            FavoriteMovieContract.MovieEntry.COLUMN_TITLE,
            FavoriteMovieContract.MovieEntry.COLUMN_VOTE_AVERAGE,
            FavoriteMovieContract.MovieEntry.COLUMN_POSTER_PATH,
            FavoriteMovieContract.MovieEntry.COLUMN_BACKGROUND_POSTER_PATH,
            FavoriteMovieContract.MovieEntry.COLUMN_RELEASE_DATE,
            FavoriteMovieContract.MovieEntry.COLUMN_OVERVIEW
    };


    public static final int COLUMN_MOVIE_ID = 0;
    public static final int COLUMN_TITLE = 1;
    public static final int COLUMN_VOTE_AVERAGE = 2;
    public static final int COLUMN_POSTER_PATH = 3;
    public static final int COLUMN_BACKGROUND_POSTER_PATH = 4;
    public static final int COLUMN_RELEASE_DATE = 5;
    public static final int COLUMN_OVERVIEW = 6;
}
