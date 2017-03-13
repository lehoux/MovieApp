package com.example.rent.movieapp.listing;

import com.annimon.stream.Objects;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RENT on 2017-03-13.
 */

public class MovieDetails {

    @SerializedName("Title")
    private String title;

    @SerializedName("Year")
    private String year;

    @SerializedName("Rated")
    private String rated;

    @SerializedName("Runtime")
    private String runtime;

    @SerializedName("Director")
    private String director;

    @SerializedName("Actors")
    private String actors;

    @SerializedName("Plot")
    private String plot;

    @SerializedName("Awards")
    private String awards;

    @SerializedName("Poster")
    private String poster;

    private String imdbRating;

    @SerializedName("Type")
    private String type;

    @SerializedName("Response")
    private String response;

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRated() {
        return rated;
    }

    public String getRuntime() {
        return runtime;
    }

    public String getDirector() {
        return director;
    }

    public String getActors() {
        return actors;
    }

    public String getPlot() {
        return plot;
    }

    public String getAwards() {
        return awards;
    }

    public String getPoster() {
        return poster;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public String getType() {
        return type;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDetails movieDetails = (MovieDetails) o;
        return Objects.equals(title, movieDetails.title) &&
                Objects.equals(year, movieDetails.year) &&
                Objects.equals(rated, movieDetails.rated) &&
                Objects.equals(runtime, movieDetails.runtime) &&
                Objects.equals(director, movieDetails.director) &&
                Objects.equals(actors, movieDetails.actors) &&
                Objects.equals(plot, movieDetails.plot) &&
                Objects.equals(awards, movieDetails.awards) &&
                Objects.equals(poster, movieDetails.poster) &&
                Objects.equals(imdbRating, movieDetails.imdbRating) &&
                Objects.equals(type, movieDetails.type) &&
                Objects.equals(response, movieDetails.response);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, rated, runtime, director, actors, plot, awards, poster, imdbRating, type, response);
    }
}
