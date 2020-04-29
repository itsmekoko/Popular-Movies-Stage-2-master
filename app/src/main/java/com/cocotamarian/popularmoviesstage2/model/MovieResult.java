package com.cocotamarian.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResult {

    @SerializedName("results")
    private List<Movie> movies;

    public MovieResult(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
