package com.cocotamarian.popularmoviesstage2.model;


import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("author")
    private String author;
    @SerializedName("content")
    private String reviewMessage;

    public Review(String author, String reviewMessage) {
        this.author = author;
        this.reviewMessage = reviewMessage;
    }

    public String getAuthor() {
        return author;
    }

    public String getReviewMessage() {
        return reviewMessage;
    }

}
