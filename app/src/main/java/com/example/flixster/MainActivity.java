package com.example.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixster.Adapters.MovieAdapter;
import com.example.flixster.Models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    //URL so we can make a get request on currently playing movies
    public static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    //So we can easily log data
    public static final String TAG = "MainActivity";

    //add this here so it is easire to display movies in the RecyclerView
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);
        //instantiate here, modify later
        movies = new ArrayList<>();

        //Create Adapter
        //Activity is an instance of the context
        final MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        //Set adapter on the RecyclerView
        rvMovies.setAdapter(movieAdapter);

        //Set a layout manager on the RecyclerView so the RecyclerView knows how to layout different
        // views on the screen
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        AsyncHttpClient client = new AsyncHttpClient();
        //PASS IN A CALLBACK - Use JSON HttpResponseHandler because the API returns JSON
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler()
        {
            @Override
            //data we want is in the JSON object
            public void onSuccess(int statusCode, Headers headers,JSON json)
            {
                Log.d(TAG, "onSuccess");
                //returns the JSON object
                JSONObject jsonObject = json.jsonObject;
                //gets the list of movies now playing (use array because the value for the key is an array)
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: " + results.toString());
                    //modify existing reference because Adapter points to this
                    movies.addAll(Movie.fromJsonArray(results));
                    //When the data behind the adapter changes, need to let the adapter know so it
                    // can re-render the Recycler View
                    movieAdapter.notifyDataSetChanged();

                    Log.i(TAG, "Movies: " + movies.size());
                } catch (JSONException e) {
                    Log.e(TAG,"Hit JSON exception", e);
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });


    }
}
