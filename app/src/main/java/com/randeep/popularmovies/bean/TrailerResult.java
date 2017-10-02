package com.randeep.popularmovies.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randeeppulp on 9/27/17.
 */

public class TrailerResult {

    @SerializedName("id")
    int movieId;

    @SerializedName("results")
    private List<Trailer> results = new ArrayList<>();

    public List<Trailer> getTrailerResult(){
        return results;
    }
}
