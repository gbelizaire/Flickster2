package ht.bunexe.menfp.flickster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    int movieId;
    String title;
    String poster_path;
    double vote_average;
    String backdrop_path;
    String overview;

    public  Movie(JSONObject jsonObject) throws JSONException {
        this.movieId = jsonObject.getInt("id");
        this.vote_average = jsonObject.getDouble("vote_average");
        this.title = jsonObject.getString("title");
        this.poster_path = jsonObject.getString("poster_path");
        this.backdrop_path = jsonObject.getString("backdrop_path");
        this.overview = jsonObject.getString("overview");
    }

    public static List<Movie> fromMovieJsonArray(JSONArray movieJsonArray) throws JSONException{
        List<Movie> ListMovies = new ArrayList<>();
        for (int i = 0; i < movieJsonArray.length(); i++) {
            ListMovies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }

        return ListMovies;
    }

    public Movie(String title, String poster_path, String overview) {
        this.title = title;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public String getPoster_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",poster_path);
    }

    public String getBackdrop_path() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",backdrop_path);
    }
    public String getOverview() {
        return overview;
    }
}
