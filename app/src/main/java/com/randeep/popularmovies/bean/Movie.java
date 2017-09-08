package com.randeep.popularmovies.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Randeeppulp on 9/2/17.
 */

public class Movie implements Parcelable{

    @SerializedName("id")
    private int movieId;

    @SerializedName("vote_average")
    private double voteAverage;

    @SerializedName("title")
    private String movieTitle;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("backdrop_path")
    private String backgroundPosterPath;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String overView;

    protected Movie(Parcel in) {
        movieId = in.readInt();
        voteAverage = in.readDouble();
        movieTitle = in.readString();
        posterPath = in.readString();
        backgroundPosterPath = in.readString();
        releaseDate = in.readString();
        overView = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(movieId);
        dest.writeDouble(voteAverage);
        dest.writeString(movieTitle);
        dest.writeString(posterPath);
        dest.writeString(backgroundPosterPath);
        dest.writeString(releaseDate);
        dest.writeString(overView);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getMovieId(){
        return movieId;
    }

    public double getVoteAverage(){
        return voteAverage;
    }

    public String getMovieTitle(){
        return movieTitle;
    }

    public String getPosterPath(){
        return "http://image.tmdb.org/t/p/w185"+posterPath;
    }

    public String getBackgroundPosterPath(){
        return "http://image.tmdb.org/t/p/w780"+backgroundPosterPath;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public String getOverView(){
        return overView;
    }


}
