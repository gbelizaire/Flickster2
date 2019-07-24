package ht.bunexe.menfp.flickster;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import ht.bunexe.menfp.flickster.models.Movie;

public class DetailActivity extends YouTubeBaseActivity {
    //YouTubePlayerView youTubePlayerView;
    public static final String YOUTUBE_API_KEY = "AIzaSyDg8M_QFC8dGaSYFZLwx1Qun7pBh4REYB8";
    private static final String TRAILER_API = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    /* TextView tvTitle;
 TextView tvOverview;
 RatingBar ratingBar;
*/
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    ;
    @BindView(R.id.tvOverview)
    TextView tvOverview;
    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.player)
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

       /* tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        youTubePlayerView =
                (YouTubePlayerView) findViewById(R.id.player);
                */

        final Movie movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getVote_average());

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(TRAILER_API, movie.getMovieId()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray results = response.getJSONArray("results");
                    if (results.length() == 0) {
                        return;
                    }
                    JSONObject movieTrailer = results.getJSONObject(0);
                    String youtube_key = movieTrailer.getString("key");
                    if (movieTrailer.getString("site").equalsIgnoreCase("youtube")) {

                        initializeYoutube(youtube_key, movie);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });


    }

    private void initializeYoutube(final String youtube_key, final Movie movie) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("smile", "On init succeded");
                youTubePlayer.cueVideo(youtube_key);
                boolean autoplayed = movie.getVote_average() > 5;
                if (autoplayed) {
                    youTubePlayer.loadVideo(youtube_key);
                }


            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("smile", "Oninit Failed");
            }
        });
    }
}
