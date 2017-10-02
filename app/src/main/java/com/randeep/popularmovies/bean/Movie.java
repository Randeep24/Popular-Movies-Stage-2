package com.randeep.popularmovies.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Randeeppulp on 9/2/17.
 */

public class Movie implements Parcelable {

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

    public Movie() {

    }

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

    public  void setMovieId(int movieId){
        this.movieId = movieId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setVoteAverage(double voteAverage){
        this.voteAverage = voteAverage;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setMovieTitle(String movieTitle){
        this.movieTitle = movieTitle;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setPosterPath(String posterPath){
        this.posterPath = posterPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setBackgroundPosterPath(String backgroundPosterPath){
        this.backgroundPosterPath = backgroundPosterPath;
    }

    public String getBackgroundPosterPath() {
        return backgroundPosterPath;
    }

    public void setReleaseDate(String releaseDate){
        this.releaseDate = releaseDate;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setOverView(String overView){
        this.overView = overView;
    }

    public String getOverView() {
        return overView;
    }


}
