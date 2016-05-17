package com.tman.ivntel.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.tman.ivntel.popularmovies.R;
import com.tman.ivntel.popularmovies.model.Movie;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by Ivan Telisman on 9/1/15.
 */

public class MovieAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private List<Movie> mMovies;
    private Context mContext;

    public MovieAdapter(Context context, List<Movie> movies) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mMovies = movies;
    }

    public void updateData(List<Movie> movies) {
        mMovies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mMovies.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Movie getItem(int position) {
        return mMovies.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;

        final Movie movie = mMovies.get(position);

        if (convertView == null){
            view = mInflater.inflate(R.layout.row_movie, parent, false);
            holder = new ViewHolder();

            holder.rowImage = (ImageView)view.findViewById(R.id.row_image);
            holder.rowLayout = (RelativeLayout)view.findViewById(R.id.row_layout);
            view.setTag(holder);
        }
        else{
            view = convertView;
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load("http://image.tmdb.org/t/p/w185"+movie.getMoviePoster()).into(holder.rowImage);

        /*holder.rowLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);
                intent.putExtra(DetailActivity.ARG_MOVIE, movie);
                mContext.startActivity(intent);
            }
        });*/


        return view;
    }

    public static class ViewHolder {

        public RelativeLayout rowLayout;

        public ImageView rowImage;

    }
}