package com.cocotamarian.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

public class Cast {
    @SerializedName("character")
    private String movieCharacter;
    @SerializedName("name")
    private String originalName;
    @SerializedName("profile_path")
    private String profilePath;

    public Cast(String movieCharacter, String originalName, String profilePath) {
        this.movieCharacter = movieCharacter;
        this.originalName = originalName;
        this.profilePath = profilePath;
    }

    public String getMovieCharacter() {
        return movieCharacter;
    }

    public String getOriginalName() {
        return originalName;
    }

    public String getProfilePath() {
        return profilePath;
    }

}
