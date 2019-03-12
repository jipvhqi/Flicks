package flicks.oath.com.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import flicks.oath.com.MovieDetailActivity;
import flicks.oath.com.R;
import flicks.oath.com.interfaces.flicksService;
import flicks.oath.com.models.Movie;
import flicks.oath.com.models.Video;
import flicks.oath.com.modules.GlideApp;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MovieArrayAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    class ViewHolderGeneralMovie extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle) TextView tvTitle;
        @BindView(R.id.tvOverview) TextView tvOverview;
        @Nullable @BindView(R.id.ivMovieImage) ImageView ivImage;
        @Nullable @BindView(R.id.ivMovieBackdrop) ImageView ivBackdrop;

        public ViewHolderGeneralMovie(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ViewHolderPopularMovie extends RecyclerView.ViewHolder {
        @Nullable @BindView(R.id.tvTitle) TextView tvTitle;
        @Nullable @BindView(R.id.tvOverview) TextView tvOverview;
        @Nullable @BindView(R.id.ivMovieImage) ImageView ivImage;
        @Nullable @BindView(R.id.ivMovieBackdrop) ImageView ivBackdrop;
        @BindView(R.id.ivPlayIconOverlay) ImageView ivPlayIconOverlay;

        public ViewHolderPopularMovie(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private ArrayList<Movie> mMovies;
    private final int GENERAL = 0, POPULAR = 1;

    public MovieArrayAdapter(ArrayList<Movie> movies) {
        super();
        mMovies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case GENERAL:
                View v1 = inflater.inflate(R.layout.item_movie, viewGroup, false);
                viewHolder = new ViewHolderGeneralMovie(v1);
                break;
            case POPULAR:
            default:
                View v2 = inflater.inflate(R.layout.item_movie_popular, viewGroup, false);
                viewHolder = new ViewHolderPopularMovie(v2);
                break;

        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case GENERAL:
                ViewHolderGeneralMovie vh1 = (ViewHolderGeneralMovie) viewHolder;
                configureViewHolderGeneralMovie(vh1, position);
                break;
            case POPULAR:
                default:
                ViewHolderPopularMovie vh2 = (ViewHolderPopularMovie) viewHolder;
                configureViewHolderPopularMovie(vh2, position);
                break;
        }

        final String BASE_URL = "https://api.themoviedb.org/";
        final String apiKey = "a07e22bc18f5cb106bfe4cc1f83ad8ed";

        OkHttpClient client = new OkHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .build();
        final flicksService apiService = retrofit.create(flicksService.class);

        viewHolder.itemView.setOnClickListener( v -> {
            final Movie movie = mMovies.get(position);
            Call<ResponseBody> call = apiService.getVideos(movie.getId().toString(), apiKey);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    String videosJSONString;
                    try {
                        String responseString = response.body().string();
                        JSONObject json = new JSONObject(responseString);
                        videosJSONString = json.getJSONArray("results").toString();
                        Type videoListType = new TypeToken<ArrayList<Video>>() {}.getType();
                        ArrayList<Video> videos = new Gson().fromJson(videosJSONString, videoListType);
                        Log.d("DEBUG", videos.toString());

                        Intent intent = new Intent(viewHolder.itemView.getContext(), MovieDetailActivity.class);
                        intent.putExtra("movie", Parcels.wrap(movie));
                        intent.putExtra("videos", Parcels.wrap(videos));
                        viewHolder.itemView.getContext().startActivity(intent);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (JSONException ex) {
                        ex.printStackTrace();
                    } catch (NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("ERROR", call.toString());
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return this.mMovies.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mMovies.get(position).isPopular() ? POPULAR: GENERAL;
    }

    private void configureViewHolderGeneralMovie(ViewHolderGeneralMovie vh, int position) {
        Movie movie = mMovies.get(position);
        String imageLoadPath;
        ImageView ivTarget;
        int width;
        int height;

        if (movie != null) {
            vh.tvTitle.setText(movie.getOriginalTitle());
            vh.tvOverview.setText(movie.getOverview());

            if (vh.ivImage != null) {
                imageLoadPath = movie.getPosterPath();
                ivTarget = vh.ivImage;
                width = vh.itemView.getResources().getDisplayMetrics().widthPixels / 2;
                height = Target.SIZE_ORIGINAL;
            } else {
                imageLoadPath = movie.getBackdropPath();
                ivTarget = vh.ivBackdrop;
                width = Target.SIZE_ORIGINAL;
                height = vh.itemView.getResources().getDisplayMetrics().heightPixels / 2;
            }

            int radius = 20; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

            GlideApp.with(vh.itemView.getContext())
                    .load(imageLoadPath)
                    .override(width, height)
                    .placeholder(R.drawable.ic_file_download_white)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivTarget);
        }
    }

    private void configureViewHolderPopularMovie(ViewHolderPopularMovie vh, int position) {
        Movie movie = mMovies.get(position);
        String imageLoadPath;
        ImageView ivTarget;
        int width;
        int height;

        if (movie != null) {
            if (vh.tvTitle != null) {
                vh.tvTitle.setText(movie.getOriginalTitle());
            }

            if (vh.tvOverview != null) {
                vh.tvOverview.setText(movie.getOverview());
            }

            if (vh.ivImage != null) {
                imageLoadPath = movie.getPosterPath();
                ivTarget = vh.ivImage;
                width = vh.itemView.getResources().getDisplayMetrics().widthPixels;
                height = Target.SIZE_ORIGINAL;
            } else {
                imageLoadPath = movie.getBackdropPath();
                ivTarget = vh.ivBackdrop;
                width = Target.SIZE_ORIGINAL;
                height = vh.itemView.getResources().getDisplayMetrics().heightPixels / 2;
            }

            int radius = 20; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop

            GlideApp.with(vh.itemView.getContext())
                    .load(imageLoadPath)
                    .override(width, height)
                    .placeholder(R.drawable.ic_file_download_white)
                    .transform(new RoundedCornersTransformation(radius, margin))
                    .into(ivTarget);

            vh.ivPlayIconOverlay.setImageResource(R.drawable.ic_play_circle_outline_white);
        }
    }
}