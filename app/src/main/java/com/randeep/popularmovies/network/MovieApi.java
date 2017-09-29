package com.randeep.popularmovies.network;

import com.randeep.popularmovies.bean.MovieResult;
import com.randeep.popularmovies.bean.ReviewResult;
import com.randeep.popularmovies.bean.TrailerResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Randeeppulp on 9/2/17.
 */

public interface MovieApi {

    @GET("{type}")
    Call<MovieResult> getMoviesList(
            @Path("type") String sortingType,
            @Query("api_key") String apiKey
    );

    @GET("{movieId}/videos")
    Call<TrailerResult> getTrailerList(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey
    );

    @GET("{movieId}/reviews")
    Call<ReviewResult> getReviewList(
            @Path("movieId") int movieId,
            @Query("api_key") String apiKey
    );
}
