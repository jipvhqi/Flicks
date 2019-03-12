package flicks.oath.com.interfaces;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface flicksService {
    @GET("/3/movie/now_playing")
    Call<ResponseBody> getNowPlaying(@Query("api_key") String apiKey, @Query("page") int page);

    @GET("/3/movie/{movie_id}/videos")
    Call<ResponseBody> getVideos(@Path("movie_id") String movieID, @Query("api_key") String apiKey);
}