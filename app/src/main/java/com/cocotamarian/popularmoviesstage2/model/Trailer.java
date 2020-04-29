package com.cocotamarian.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

public class Trailer {
    @SerializedName("key")
    private String videoKey;

    public Trailer(String videoKey) {
        this.videoKey = videoKey;
    }

    public String getVideoKey() {
        return videoKey;
    }

}