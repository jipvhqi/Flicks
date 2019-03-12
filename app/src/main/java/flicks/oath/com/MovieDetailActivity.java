package flicks.oath.com;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import flicks.oath.com.models.Movie;
import flicks.oath.com.models.Video;

public class MovieDetailActivity extends YouTubeBaseActivity {
    @BindView(R.id.videoView) YouTubePlayerView youTubePlayerView;
    @Nullable @BindView(R.id.tvTitle) TextView tvTitle;
    @Nullable @BindView(R.id.tvOverview) TextView tvOverview;
    @Nullable @BindView(R.id.tvReleaseDate) TextView tvReleaseDate;
    @Nullable @BindView(R.id.ratingBar) RatingBar rbRatings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Movie movie = Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        final ArrayList<Video> videos = Parcels.unwrap(getIntent().getParcelableExtra("videos"));

        if (movie.isPopular()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            setContentView(R.layout.activity_movie_play_trailer);
        } else {
            setContentView(R.layout.activity_movie_detail);
        }

        ButterKnife.bind(this);

        if (!movie.isPopular()) {
            tvTitle.setText(movie.getOriginalTitle());
            tvOverview.setText(movie.getOverview());
            tvReleaseDate.setText(getText(R.string.release_date).toString().concat(": ").concat(movie.getReleaseDate()));
            rbRatings.setRating(movie.getVoteAverage().floatValue());
            youTubePlayerView.initialize("AIzaSyDw34RUGcaHD4PsNmtKM2igHTFojVTFekg",
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {

                            if (videos != null && videos.size() > 0) {
                                youTubePlayer.cueVideo(videos.get(0).getKey());
                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    });
        } else {
            youTubePlayerView.initialize("AIzaSyDw34RUGcaHD4PsNmtKM2igHTFojVTFekg",
                    new YouTubePlayer.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                            YouTubePlayer youTubePlayer, boolean b) {

                            if (videos != null && videos.size() > 0) {
                                youTubePlayer.loadVideo(videos.get(0).getKey());
                            }
                        }

                        @Override
                        public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                            YouTubeInitializationResult youTubeInitializationResult) {

                        }
                    });
        }
    }
}