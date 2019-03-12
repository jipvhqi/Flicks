package flicks.oath.com.models;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class Video {

    String id;
    @SerializedName("iso_639_1")
    String iso6391;
    @SerializedName("iso31661")
    String iso_3166_1;
    String key;
    String name;
    String site;
    Integer size;
    String type;

    public String getId() {
        return id;
    }

    public String getIso6391() {
        return iso6391;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public Integer getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    // empty constructor needed by the Parceler library
    public Video() {
    }
}