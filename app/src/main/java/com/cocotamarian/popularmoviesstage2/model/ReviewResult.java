package com.cocotamarian.popularmoviesstage2.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResult {
    @SerializedName("results")
    private List<Review> reviews;

    public ReviewResult(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Review> getReviews() {
        return reviews;
    }

}
