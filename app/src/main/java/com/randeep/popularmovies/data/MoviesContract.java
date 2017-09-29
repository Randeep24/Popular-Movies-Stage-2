package com.randeep.popularmovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Randeeppulp on 9/24/17.
 */


public class MoviesContract {

    public static final String CONTENT_AUTHORITY = "com.randeep.popularmovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIE = "movie";
    public static final String PATH_MOVIE_TRAILER = "trailer";
    public static final String PATH_MOVIE_REVIEW = "review";

    public static final class MovieEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE)
                .build();

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACKGROUND_POSTER_PATH = "background_poster_path";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_OVERVIEW = "overview";

    }

    public static final class TrailerEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE_TRAILER)
                .build();

        public static final String TABLE_NAME = "trailers";

        public static final String COLUMN_TRAILER_ID = "trailer_id";
        public static final String COLUMN_MOVIE_ID = "trailer_movie_id";
        public static final String COLUMN_TRAILER_URL = "trailer_url";
        public static final String COLUMN_TRAILER_NAME = "trailer_name";
        public static final String COLUMN_TRAILER_TYPE = "trailer_type";
    }


    public static final class ReviewEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE_REVIEW)
                .build();

        public static final String TABLE_NAME = "reviews";

        public static final String COLUMN_REVIEW_ID = "review_id";
        public static final String COLUMN_MOVIE_ID = "review_movie_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_CONTENT = "content";

    }
}
