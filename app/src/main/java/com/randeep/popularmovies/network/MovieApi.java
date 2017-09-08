package com.randeep.popularmovies.network;

import com.randeep.popularmovies.bean.MovieResult;

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
}
