package com.tman.ivntel.popularmovies.retrofit;

import com.tman.ivntel.popularmovies.model.MovieResponse;
import com.tman.ivntel.popularmovies.model.ReviewResponse;
import com.tman.ivntel.popularmovies.model.TrailerResponse;


import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Ivan Telisman on 9/1/15.
 */


public interface MovieApi {

    String API_KEY = "";

    @GET("/3/discover/movie?sort_by=popularity.desc&api_key=" + API_KEY)
    void getPopularMovieResults(Callback<MovieResponse> callback);

    @GET("/3/discover/movie?sort_by=vote_average.desc&api_key=" + API_KEY)
    void getHighRatedMovieResults(Callback<MovieResponse> callback);

    @GET("/3/movie/{id}/reviews?api_key=" + API_KEY)
    void getReviews(@Path("id") int id, Callback<ReviewResponse> reviewResponseCallback);

    @GET("/3/movie/{id}/videos?api_key=" + API_KEY)
    void getTrailers(@Path("id") int id, Callback<TrailerResponse> trailerResponseCallback);

}