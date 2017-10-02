package com.randeep.popularmovies.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Randeeppulp on 9/27/17.
 */

public class Trailer {

    @SerializedName("id")
    String trailerId;

    @SerializedName("key")
    String youtubeLink;

    @SerializedName("name")
    String trailerName;

    @SerializedName("type")
    String trailerType;

    public String getTrailerId(){
        return trailerId;
    }

    public String getYoutubeLink(){
        return youtubeLink;
    }

    public String getTrailerName(){
        return trailerName;
    }

    public String getTrailerType(){
        return trailerType;
    }
}
