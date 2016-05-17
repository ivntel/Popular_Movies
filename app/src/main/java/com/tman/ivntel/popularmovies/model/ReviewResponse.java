package com.tman.ivntel.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Ivan Telisman on 27/09/2015.
 */
public class ReviewResponse {
    public static final String RESULT_COUNT = "total_results";
    public static final String RESULTS = "results";

    @SerializedName(RESULT_COUNT)
    private int resultCount;

    @SerializedName(RESULTS)
    private List<Review> reviews;

    public ReviewResponse() {
    }

    public List<Review> getReviewsList() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public int getResultCount() {
        return resultCount;
    }

    public void setResultCount(int resultCount) {
        this.resultCount = resultCount;
    }
}
