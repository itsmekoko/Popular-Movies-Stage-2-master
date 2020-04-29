package com.cocotamarian.popularmoviesstage2.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.cocotamarian.popularmoviesstage2.utils.DateUtils;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

@Entity(tableName = "favourites")
@TypeConverters(DateUtils.class)
public class FavouriteMovie {
    @PrimaryKey(autoGenerate = true)
    private int uId;
    @SerializedName("id")
    @ColumnInfo(name = "movie_id")
    private int movieId;
    @SerializedName("original_title")
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @SerializedName("release_date")
    @ColumnInfo(name = "release_date")
    private Date releaseDate;
    @SerializedName("poster_path")
    @ColumnInfo(name = "poster_path")
    private String posterUrl;
    @SerializedName("vote_average")
    @ColumnInfo(name = "vote_average")
    private float voteAverage;
    @SerializedName("overview")
    private String overview;
    @SerializedName("backdrop_path")
    @ColumnInfo(name = "backdrop_path")
    private String backdropPath;

    public FavouriteMovie(int uId, int movieId, String originalTitle, Date releaseDate, String posterUrl,
                          float voteAverage, String overview, String backdropPath) {
        this.uId = uId;
        this.movieId = movieId;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.posterUrl = posterUrl;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.backdropPath = backdropPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public String getOverview() {
        return overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getMovieId() {
        return movieId;
    }

    public int getUId() {
        return uId;
    }

}