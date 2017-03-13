package com.example.rent.movieapp.search;

import com.example.rent.movieapp.listing.MovieItem;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by RENT on 2017-03-07.
 */

public class MovieResult {

    @SerializedName("Search")
    private List<MovieItem> items;
    private String totalResults;

    @SerializedName("Response")
    private String response;

    public List<MovieItem> getItems() {
        return items;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setItems(List<MovieItem> items) {
        this.items = items;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
