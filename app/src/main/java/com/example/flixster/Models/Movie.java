package com.example.flixster.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

//encapsulates the idea of a movie
public class Movie {
    String posterPath;
    String title;
    String overview;

    //Constructor takes a JSON object and constructs a Movie object
    //caller is responsible for handling the exception
    public Movie(JSONObject jsonObject) throws JSONException {
        //keys in the JSON object
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++)
        {
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    //allow us to gte data out of Movie objects
    public String getPosterPath() {
        //poster path is a relative path - need to get the absolute path which is
        //baseURL/image_width/relative path
        //hardcode image size here (w342) but better to get all possible image sizes
        //and then append the relative path to get absolute paths
        return String.format("https://image.tmdb.org/t/p/w342/%s",posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
