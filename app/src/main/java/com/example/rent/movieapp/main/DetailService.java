package com.example.rent.movieapp.main;

import com.example.rent.movieapp.listing.MovieDetails;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RENT on 2017-03-13.
 */

public interface DetailService {

    @GET("/")
    Observable<MovieDetails> getDetailInfo(@Query("i") String id);

}
