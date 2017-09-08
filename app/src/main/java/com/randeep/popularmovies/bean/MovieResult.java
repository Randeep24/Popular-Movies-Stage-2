package com.randeep.popularmovies.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randeeppulp on 9/2/17.
 */

public class MovieResult {

    @SerializedName("results")
    private List<Movie> results = new ArrayList<>();

    public List<Movie> getMovieDetails(){
        return results;
    }
}
