package com.example.rent.movieapp.listing;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RENT on 2017-03-07.
 */

public class MovieItem {

    @SerializedName("Title")
    private String title;
    @SerializedName("Year")
    private String year;
    @SerializedName("imdbID")
    private String imdbID;
    @SerializedName("Type")
    private String type;
    @SerializedName("Poster")
    private String poster;

    public MovieItem(String title, String year, String imdbID, String type, String poster) {
        this.title = title;
        this.year = year;
        this.imdbID = imdbID;
        this.type = type;
        this.poster = poster;
    }


    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getType() {
        return type;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbID() {
        return imdbID;
    }

}
