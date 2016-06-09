package com.tman.ivntel.popularmovies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.tman.ivntel.popularmovies.model.Movie;
import com.tman.ivntel.popularmovies.model.Review;
import com.tman.ivntel.popularmovies.model.ReviewResponse;
import com.tman.ivntel.popularmovies.model.Trailer;
import com.tman.ivntel.popularmovies.model.TrailerResponse;
import com.tman.ivntel.popularmovies.retrofit.RestClient;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Ivan Telisman on 21/10/2015.
 */
public class DetailActivityFragment extends Fragment {

    public static final String TAG = DetailActivityFragment.class.getSimpleName();

    static final String DETAIL_MOVIE = "DETAIL_MOVIE";

    private Movie mMovie;
    private List<Review> mReviews = new ArrayList<Review>();
    private List<Trailer> mTrailers = new ArrayList<Trailer>();

    private ImageView mImageView;
    private TextView mTitleView;
    private TextView mOverviewView;
    private TextView mDateView;
    private TextView mVoteAverageView;
    private Button mFavorites;

    private LinearLayout mTrailersLayout;
    private LinearLayout mReviewsLayout;
    private ScrollView mDetailLayout;
    private LayoutInflater mInflater;
    private ReviewResponse mReviewResponse;
    private TrailerResponse mTrailerResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mInflater = inflater;
        Log.d("debug", "oncreateview check");
        Bundle arguments = getArguments();

        if (arguments != null) {
            mMovie = (Movie) arguments.getSerializable(DetailActivityFragment.DETAIL_MOVIE);
            Log.d("debug", "mMovie: " + mMovie.getTitle());
        }

        View rootView = mInflater.inflate(R.layout.fragment_detail, container, false);

        mDetailLayout = (ScrollView) rootView.findViewById(R.id.detail_layout);

        if (mMovie != null) {
            mDetailLayout.setVisibility(View.VISIBLE);
        } else {
            mDetailLayout.setVisibility(View.INVISIBLE);
        }

        mImageView = (ImageView) rootView.findViewById(R.id.detail_image);
        mTitleView = (TextView) rootView.findViewById(R.id.detail_title);
        mOverviewView = (TextView) rootView.findViewById(R.id.detail_overview);
        mDateView = (TextView) rootView.findViewById(R.id.detail_date);
        mVoteAverageView = (TextView) rootView.findViewById(R.id.detail_vote_average);
        mTrailersLayout = (LinearLayout) rootView.findViewById(R.id.detail_trailers);
        mReviewsLayout = (LinearLayout) rootView.findViewById(R.id.detail_reviews);
        mFavorites = (Button) rootView.findViewById(R.id.favorite);


        if (mMovie != null) {

            Log.d("debug", "mMovie!null");
            Glide.with(this).load("http://image.tmdb.org/t/p/w185" + mMovie.getMoviePoster()).into(mImageView);
            mTitleView.setText("" + mMovie.getTitle());
            mOverviewView.setText("Synopsis: " + mMovie.getOverview());
            mDateView.setText("" + mMovie.getDate());
            mVoteAverageView.setText("" + mMovie.getAverage() + "/10");
            //mDateView.setText("Date released: " + mMovie.getDate());
            //mVoteAverageView.setText("Rating: " + mMovie.getAverage() + "/10");

            mFavorites.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    if(isAlreadyFavorite(mMovie) == true){
                        Toast.makeText(getActivity(), "Already saved in favorites", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //MainActivityFragment.movieList.remove(mMovie);*/
                        MainActivityFragment.movieList.add(mMovie);
                        Toast.makeText(getActivity(), "Saved in favorites", Toast.LENGTH_SHORT).show();
                        saveObject(MainActivityFragment.movieList);
                    }
                }
            });
            getReviews();
            getTrailers();
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_detail, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void getReviews() {
        RestClient.getMovieApi().getReviews(mMovie.getIDnum(), new Callback<ReviewResponse>() {

            @Override
            public void success(ReviewResponse reviewResponse, Response response) {
                Log.d(TAG, response.getBody().toString());
                mReviewResponse = reviewResponse;
                mReviews = mReviewResponse.getReviewsList();
                Log.d("review count", String.valueOf(mReviews.size()));

                for (final Review review : mReviews) {
                    View row = mInflater.inflate(R.layout.row_review, mReviewsLayout, false);
                    TextView authorTextView = (TextView) row.findViewById(R.id.author);
                    TextView contentTextView = (TextView) row.findViewById(R.id.row_review);
                    authorTextView.setText(review.getAuthor());
                    contentTextView.setText(review.getContent());
                    mReviewsLayout.addView(row);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("review count", String.valueOf(mReviews.size()));
                Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getTrailers() {
        RestClient.getMovieApi().getTrailers(mMovie.getIDnum(), new Callback<TrailerResponse>() {

            @Override
            public void success(TrailerResponse trailerResponse, Response response) {
                Log.d(TAG, response.getBody().toString());
                mTrailerResponse = trailerResponse;
                mTrailers = mTrailerResponse.getTrailerList();
                Log.d("trailer count", String.valueOf(mTrailers.size()));

                for (final Trailer trailer : mTrailers) {
                    View row = mInflater.inflate(R.layout.row_trailer, mTrailersLayout, false);
                    TextView trailerNameTextView = (TextView) row.findViewById(R.id.name);
                    trailerNameTextView.setText(trailer.getName());
                    mTrailersLayout.addView(row);
                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            watchYoutubeVideo(trailer.getKey());
                        }
                    });
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("trailer count", String.valueOf(mTrailers.size()));
                Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void saveObject(List<Movie> myobject) {
        Gson gson = new Gson();
        String jsonList = gson.toJson(myobject);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("MyObjectList", jsonList);
        editor.apply();
    }
    public void watchYoutubeVideo(String videoKey) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + videoKey));
            startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoKey));
            startActivity(intent);
        }
    }
    public static boolean isAlreadyFavorite(Movie movie) {
        List<Movie> favoriteMovies = MainActivityFragment.movieList;
        for (Movie favoriteMovie : favoriteMovies) {
            if (favoriteMovie.getIDnum() == movie.getIDnum()) {
                return true;
            }
        }
        return false;
    }
}
