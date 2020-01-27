package com.tenserflow.therapist.model.Detail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoArray {


    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

}

