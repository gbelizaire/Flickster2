package ht.bunexe.menfp.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import ht.bunexe.menfp.flickster.adapters.MoviesAdapter;
import ht.bunexe.menfp.flickster.models.Movie;

public class MainActivity extends AppCompatActivity {

    // declaration d'une variable pointant vers le url
    private static final String MOVIES_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    @BindView(R.id.rvMovies)
    RecyclerView rvMovies;
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //RecyclerView rvMovies = findViewById(R.id.rvMovies);
        movies = new ArrayList<>();
        final MoviesAdapter adapter= new MoviesAdapter(this,movies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        rvMovies.setAdapter(adapter);

        AsyncHttpClient client= new AsyncHttpClient();
        client.get(MOVIES_URL,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray movieJSONArray = response.getJSONArray("results");
                    //movies = Movie.fromMovieJsonArray(movieJSONArray);
                    movies.addAll(Movie.fromMovieJsonArray(movieJSONArray));
                    //adapter.notifyDataSetChanged();
                    adapter.notifyDataSetChanged();
                    Log.d("smile",movies.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                //super.onFailure(statusCode, headers, responseString, throwable);
                Log.d("Erreur Belizaire",String.format("Code Erreur : %d  ; Message Erreur: %s",statusCode,responseString));
            }
        });
    }
}
