package com.tman.ivntel.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ivan Telisman on 9/1/15.
 */

public class MovieResponse implements Serializable {
    public static final String TAG = MovieResponse.class.getSimpleName();

    public static final String PROPERTY_RESULT_COUNT = "total_results";
    public static final String PROPERTY_RESULTS = "results";

    @SerializedName(PROPERTY_RESULT_COUNT)
    private int resultCount;

    @SerializedName(PROPERTY_RESULTS)
    private List<Movie> movies;

    public MovieResponse() {
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
}