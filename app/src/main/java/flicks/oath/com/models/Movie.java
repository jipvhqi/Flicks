package flicks.oath.com.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Movie {

    public Integer getId() {
        return id;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public Double getPopularity() {
        return popularity;
    }

    public boolean isPopular() { return voteAverage >= 5.0; }

    Integer id;
    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("backdrop_path")
    String backdropPath;
    @SerializedName("original_title")
    String originalTitle;
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("vote_average")
    Double voteAverage;
    Double popularity;

    // empty constructor needed by the Parceler library
    public Movie() {
    }
}