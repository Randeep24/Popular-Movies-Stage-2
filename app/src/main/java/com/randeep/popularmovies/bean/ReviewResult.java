package com.randeep.popularmovies.bean;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randeeppulp on 9/27/17.
 */

public class ReviewResult {

    @SerializedName("results")
    private List<Review> results = new ArrayList<>();

    public List<Review> getReviewList(){
        return results;
    }
}
