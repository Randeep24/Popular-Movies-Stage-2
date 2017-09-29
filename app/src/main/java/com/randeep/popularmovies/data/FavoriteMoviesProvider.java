package com.randeep.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Randeeppulp on 9/29/17.
 */

public class FavoriteMoviesProvider extends ContentProvider {

    private static final UriMatcher uriMatcher = buildUriMatcher();

    private MovieDbHelper movieDbHelper;

    static final int MOVIE = 100;
    static final int MOVIE_WITH_ID = 101;


    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavoriteMovieContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, FavoriteMovieContract.PATH_MOVIE, MOVIE);
        matcher.addURI(authority, FavoriteMovieContract.PATH_MOVIE + "/*", MOVIE_WITH_ID);

       return matcher;
    }

    @Override
    public boolean onCreate() {

        movieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (uriMatcher.match(uri)){
            case MOVIE:
                cursor = movieDbHelper.getReadableDatabase().query(
                        FavoriteMovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case MOVIE_WITH_ID:
                cursor = movieDbHelper.getReadableDatabase().query(
                        FavoriteMovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        FavoriteMovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[] {String.valueOf(ContentUris.parseId(uri))},
                        null,
                        null,
                        sortOrder
                );
                break;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);
        Uri returnUri;

        if (uriMatcher.match(uri) == MOVIE){
                long _id = sqLiteDatabase.insert(FavoriteMovieContract.MovieEntry.TABLE_NAME,
                        null, contentValues);
                if (_id > 0) {
                    returnUri = FavoriteMovieContract.MovieEntry.buildMoviesUri(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }

        }else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        final SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        int rowsDeleted;

        if ( null == selection ) selection = "1";

        switch (match){
            case MOVIE:
                rowsDeleted = sqLiteDatabase.delete(FavoriteMovieContract.MovieEntry.TABLE_NAME,selection,selectionArgs);
                break;
            case MOVIE_WITH_ID:
                rowsDeleted = sqLiteDatabase.delete(FavoriteMovieContract.MovieEntry.TABLE_NAME,
                        FavoriteMovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[]{FavoriteMovieContract.MovieEntry.getMovieIdFromUri(uri)});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        final SQLiteDatabase sqLiteDatabase = movieDbHelper.getWritableDatabase();
        final int match = uriMatcher.match(uri);

        int rowsUpdated;

        switch (match) {
            case MOVIE:
                rowsUpdated = sqLiteDatabase.update(FavoriteMovieContract.MovieEntry.TABLE_NAME, contentValues, selection,
                        selectionArgs);
                break;
            case MOVIE_WITH_ID:
                rowsUpdated = sqLiteDatabase.update(FavoriteMovieContract.MovieEntry.TABLE_NAME, contentValues,
                        FavoriteMovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                        new String[]{FavoriteMovieContract.MovieEntry.getMovieIdFromUri(uri)});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }
}
