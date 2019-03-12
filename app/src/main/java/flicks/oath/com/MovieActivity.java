package flicks.oath.com;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import flicks.oath.com.adapters.MovieArrayAdapter;
import flicks.oath.com.interfaces.flicksService;
import flicks.oath.com.models.Movie;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieActivity extends Activity {

    ArrayList<Movie> movies;
    MovieArrayAdapter movieAdapter;
    @BindView(R.id.rvMovies) RecyclerView rvMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        ButterKnife.bind(this);

        final String BASE_URL = "https://api.themoviedb.org/";
        final String apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .build();
        final flicksService apiService = retrofit.create(flicksService.class);

        movies = new ArrayList<>();
        movieAdapter = new MovieArrayAdapter(movies);
        rvMovies.setAdapter(movieAdapter);
        rvMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Call<ResponseBody> call = apiService.getNowPlaying(apiKey, 1);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                String nowPlayingJSONString;
                try {
                    String responseString = response.body().string();
                    JSONObject json = new JSONObject(responseString);
                    nowPlayingJSONString = json.getJSONArray("results").toString();
                    Type movieListType = new TypeToken<ArrayList<Movie>>() {}.getType();
                    ArrayList<Movie> moviesResult = new Gson().fromJson(nowPlayingJSONString, movieListType);
                    movies.addAll(moviesResult);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (JSONException ex) {
                    ex.printStackTrace();
                } catch (NullPointerException ex) {
                    ex.printStackTrace();
                }
                movieAdapter.notifyDataSetChanged();
                Log.d("DEBUG", movies.toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("ERROR", call.toString());
            }
        });
    }
}