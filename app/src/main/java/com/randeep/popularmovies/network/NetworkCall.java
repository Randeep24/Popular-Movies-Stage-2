package com.randeep.popularmovies.network;

import com.randeep.popularmovies.BuildConfig;
import com.randeep.popularmovies.bean.Review;
import com.randeep.popularmovies.bean.Trailer;
import com.randeep.popularmovies.utils.Constants;
import com.randeep.popularmovies.bean.Movie;
import com.randeep.popularmovies.bean.MovieResult;
import com.randeep.popularmovies.bean.ReviewResult;
import com.randeep.popularmovies.bean.TrailerResult;

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

    private MovieApi movieApi;
    private UpdateMovieListView mUpdateMovieListView;
    private UpdateReviewAndTrailerList mUpdateReviewList;

    public NetworkCall() {

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieApi = retrofit.create(MovieApi.class);


    }


    public interface UpdateMovieListView {
        void updateList(List<Movie> movieList);

        void showError();
    }

    public interface UpdateReviewAndTrailerList {
        void updateReviewList(List<Review> reviewList);
        void updateTrailerList(List<Trailer> trailerList);
        void showError();
    }


    public void fetchMoviesList(UpdateMovieListView updateMovieListView, String sortingType) {

        mUpdateMovieListView = updateMovieListView;

        Call<MovieResult> movieResultCall = movieApi.getMoviesList(sortingType, BuildConfig.API_KEY);

        movieResultCall.enqueue(new Callback<MovieResult>() {
            @Override
            public void onResponse(Call<MovieResult> call, Response<MovieResult> response) {
                MovieResult movieResult = response.body();
                List<Movie> movieList = movieResult.getMovieDetails();

                if (movieList != null) {
                    mUpdateMovieListView.updateList(movieList);
                }else {
                    mUpdateMovieListView.showError();
                }
            }

            @Override
            public void onFailure(Call<MovieResult> call, Throwable t) {
                mUpdateMovieListView.showError();
            }


        });
    }

    public void fetchTrailerAndReviewList(UpdateReviewAndTrailerList updateReviewAndTrailerList, int movieId){
        mUpdateReviewList = updateReviewAndTrailerList;
        fetchTrailerList(movieId);
        fetchReviewList(movieId);
    }
    public void fetchTrailerList(int movieId) {



        Call<TrailerResult> trailerResultCall = movieApi.getTrailerList(movieId, BuildConfig.API_KEY);

        trailerResultCall.enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {

                TrailerResult trailerResult = response.body();
                List<Trailer> trailerList = trailerResult.getTrailerResult();

                if (trailerList != null){
                    mUpdateReviewList.updateTrailerList(trailerList);
                } else {
                    mUpdateReviewList.showError();
                }
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {
                mUpdateReviewList.showError();
            }
        });
    }


    public void fetchReviewList(int movieId) {

        Call<ReviewResult> reviewResultCall = movieApi.getReviewList(movieId, BuildConfig.API_KEY);

        reviewResultCall.enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {

                ReviewResult reviewResult = response.body();
                List<Review> reviewList = reviewResult.getReviewList();

                if (reviewList != null){
                    mUpdateReviewList.updateReviewList(reviewList);
                }else {
                    mUpdateReviewList.showError();
                }
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                mUpdateReviewList.showError();
            }
        });
    }
}
