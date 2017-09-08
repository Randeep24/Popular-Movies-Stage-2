package com.randeep.popularmovies.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.randeep.popularmovies.R;
import com.randeep.popularmovies.bean.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Randeeppulp on 9/2/17.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MovieViewHolder> {

    private List<Movie> movieList;
    private MovieListItemClickListener mMovieListItemClickListener;

    public MoviesListAdapter(MovieListItemClickListener movieListItemClickListener){
        movieList = new ArrayList<>();
        mMovieListItemClickListener = movieListItemClickListener;
    }


    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView movieImageView;
        public MovieViewHolder(View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);

        }

        void setMovieImage(){
            int position = getAdapterPosition();
            Picasso.with(itemView.getContext()).load(movieList.get(position).getPosterPath()).into(movieImageView);
        }

        @Override
        public void onClick(View view) {
            mMovieListItemClickListener.movieListItemClicked(getAdapterPosition());
        }
    }


    public interface MovieListItemClickListener{
        void movieListItemClicked(int position);
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View view = layoutInflater.inflate(R.layout.movie_poster_item, parent, false);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.setMovieImage();
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void setMovieList(List<Movie> movies){
        movieList = movies;
        notifyDataSetChanged();
    }

}
