package com.tman.ivntel.popularmovies.model;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


/**
 * Created by Ivan Telisman on 9/1/15.
 */
public class Movie implements Serializable {



    public static final String POSTER_PATH = "poster_path";
    public static final String TITLE = "title";
    public static final String OVERVIEW = "overview";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String ID = "id";
    public static final String REVIEWS = "content";

    @SerializedName(POSTER_PATH)
    private String moviePoster;

    @SerializedName(TITLE)
    private String title;

    @SerializedName(OVERVIEW)
    private String overview;

    @SerializedName(VOTE_AVERAGE)
    private String average;

    @SerializedName(RELEASE_DATE)
    private String date;

    @SerializedName(ID)
    private int iD;

    @SerializedName(REVIEWS)
    private String reviews;

    public Movie() {
    }

    public String getReviews() {return reviews;}

    public void setReviews(String reviews) {this.reviews = reviews;}

    public static String getREVIEWS() {return REVIEWS;}

    public String getDate() { return date; }

    public int getIDnum() { return iD; }

    public void setIDnum(int iD){ this.iD = iD; }

    public void setDate(String date) {
        this.date = date;
    }

    public static String getReleaseDate() {
        return RELEASE_DATE;
    }

    public static String getTITLE() {
        return TITLE;
    }

    public static String getOVERVIEW() {
        return OVERVIEW;
    }

    public static String getVoteAverage() {
        return VOTE_AVERAGE;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getAverage() {
        return average;
    }

    public void setAverage(String average) {
        this.average = average;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) { this.moviePoster = moviePoster; }
}

