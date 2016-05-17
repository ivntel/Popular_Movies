package com.tman.ivntel.popularmovies.retrofit;
import com.tman.ivntel.popularmovies.BuildConfig;

import retrofit.RestAdapter;

/**
 * Created by Ivan Telisman on 9/1/15.
 */
public final class RestClient {

    public static final String ENDPOINT = "http://api.themoviedb.org";
    public static final boolean ENABLE_LOGGING = true;
    private static MovieApi movieApi;

    private RestClient() {
        // Hidden constructor
    }

    static {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(BuildConfig.DEBUG && ENABLE_LOGGING ? RestAdapter.LogLevel.BASIC : RestAdapter.LogLevel.NONE)
                .setEndpoint(ENDPOINT)
                .build();
        movieApi = restAdapter.create(MovieApi.class);
    }

    public static MovieApi getMovieApi() {
        return movieApi;
    }
}