package com.tman.ivntel.popularmovies;

import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.tman.ivntel.popularmovies.adapters.MovieAdapter;
import com.tman.ivntel.popularmovies.model.Movie;
import com.tman.ivntel.popularmovies.model.MovieResponse;
import com.tman.ivntel.popularmovies.retrofit.RestClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivityFragment extends Fragment {

    public static final String KEY_MOVIES_RESPONSE = "KEY_MOVIES_RESPONSE";
    public static final String TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.gridview_movies)
    GridView mGridView;

    private MovieAdapter mMovieGridAdapter;

    private List<Movie> mMovies = new ArrayList<Movie>();
    private MovieResponse mMovieResponse;
    public static List<Movie> movieList = new ArrayList<Movie>();

    public interface Callback {
        void onItemSelected(Movie movie);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mGridView = (GridView) view.findViewById(R.id.gridview_movies);

        mMovieGridAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        mGridView.setAdapter(mMovieGridAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMovieGridAdapter.getItem(position);
                ((Callback) getActivity()).onItemSelected(movie);
            }
        });

        if (savedInstanceState != null) {
            mMovies = (List<Movie>) savedInstanceState.getSerializable(KEY_MOVIES_RESPONSE);
            mMovieGridAdapter.updateData(mMovies);
        }
                handleResults();
                return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        switch(id){

            case R.id.highest_rated:
                Toast.makeText(getActivity(), "Highest Rated", Toast.LENGTH_SHORT).show();
                handleResults2();
                break;

            case R.id.popular:
                Toast.makeText(getActivity(), "Most Popular", Toast.LENGTH_SHORT).show();
                handleResults();
                break;

            case R.id.favorites:
                //load sharedpref
                Gson gson = new Gson();
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
                String savedList = preferences.getString("MyObjectList", "");
                Type type = new TypeToken<List<Movie>>(){}.getType();
                List<Movie> objects = gson.fromJson(savedList, type);

                if(objects == null){
                    Toast.makeText(getActivity(), "Nothing saved in favorites", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), "favorites", Toast.LENGTH_SHORT).show();
                    mMovieGridAdapter.updateData(objects);
                    break;
                }
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_MOVIES_RESPONSE, (Serializable) mMovies);
    }
    public void handleResults() {
        RestClient.getMovieApi().getPopularMovieResults(new retrofit.Callback<MovieResponse>() {

            @Override
            public void success(MovieResponse movieResponse, Response response) {
                Log.d(TAG, response.getBody().toString());
                mMovieResponse = movieResponse;
                mMovies = mMovieResponse.getMovies();
                mMovieGridAdapter.updateData(mMovies);
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void handleResults2() {
        RestClient.getMovieApi().getHighRatedMovieResults(new retrofit.Callback<MovieResponse>() {

            @Override
            public void success(MovieResponse movieResponse, Response response) {
                Log.d(TAG, response.getBody().toString());
                mMovieResponse = movieResponse;
                mMovies = mMovieResponse.getMovies();
                mMovieGridAdapter.updateData(mMovies);
            }
            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), "No results found", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
