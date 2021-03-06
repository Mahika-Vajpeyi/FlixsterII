package com.example.flixster.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.Models.Movie;
import com.example.flixster.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    //needed to implement the functions in the generic abstract RecyclerView Adapter class
    Context context;
    //List of movies the Adapter holds on to
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    //inflates a layout (item movie) and returns it inside a ViewHolder
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        //inflates item_movie.xml
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    //Populates data in the view through the ViewHolder
    //takes the data at position and puts it in the view contained inside the ViewHolder
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        //Get the movie at the position
        Movie movie = movies.get(position);
        //Bind the movie data into the ViewHolder (populate elements of the ViewHolder with the data of the movie)
        holder.bind(movie);
    }

    @Override
    //Returns count of items in the list
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //member variables for each view in the ViewHolder
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //define where the textViews and ImageViews are coming from
            //R stands for resources
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            //Use getter methods on movie and populate each view
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            //loads image into the image view
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
        }
    }
}
