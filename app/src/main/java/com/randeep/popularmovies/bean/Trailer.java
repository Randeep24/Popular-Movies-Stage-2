package com.randeep.popularmovies.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Randeeppulp on 9/27/17.
 */

class Trailer {

    @SerializedName("id")
    int trailerId;

    @SerializedName("key")
    String youtubeLink;

    @SerializedName("name")
    String trailerName;

    @SerializedName("type")
    String trailerType;

    public int getTrailerId(){
        return trailerId;
    }

    public String getYoutubeLink(){
        return "https://youtu.be/" + youtubeLink;
    }

    public String getTrailerName(){
        return trailerName;
    }

    public String getTrailerType(){
        return trailerType;
    }
}
