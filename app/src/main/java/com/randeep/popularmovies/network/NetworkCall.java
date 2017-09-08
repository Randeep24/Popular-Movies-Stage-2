package com.randeep.popularmovies.network;

import android.content.Context;
import android.util.Log;

import com.randeep.popularmovies.BuildConfig;
import com.randeep.popularmovies.Utils.Constants;
import com.randeep.popularmovies.bean.Movie;
import com.randeep.popularmovies.bean.MovieResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Randeeppulp on 9/2/17.
 */

public class NetworkCall {

    private static final String LOG_TAG = NetworkCall.class.getSimpleName();

    private Retrofit retrofit;
    private MovieApi movieApi;
    private final UpdateMovieListView mUpdateMovieListView;

    public NetworkCall(UpdateMovieListView updateMovieListView){

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(MovieApi.class);

        mUpdateMovieListView = updateMovieListView;
    }


    public interface UpdateMovieListView{
        void updateList(List<Movie> movieList);
        void showError();
    }


    public void fetchMoviesList(String sortingType){

        Call<MovieResult> movieResultCall = movieApi.getMoviesList(sortingType, BuildConfig.API_KEY);

        movieResultCall.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                List<Movie> movieList = movieResult.getMovieDetails();

                if (movieList != null){
                    mUpdateMovieListView.updateList(movieList);
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                mUpdateMovieListView.showError();
            }


        });
    }
}
