package com.cocotamarian.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerResult {
    @SerializedName("results")
    private List<Trailer> trailers;

    public TrailerResult(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

}
