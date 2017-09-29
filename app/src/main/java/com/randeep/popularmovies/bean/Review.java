package com.randeep.popularmovies.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Randeeppulp on 9/27/17.
 */

class Review {

    @SerializedName("id")
    String reviewId;

    @SerializedName("author")
    String author;

    @SerializedName("content")
    String content;

    public String getReviewId(){
        return reviewId;
    }

    public String getAuthor(){
        return author;
    }

    public String getContent(){
        return content;
    }
}
