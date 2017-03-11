package com.example.rent.movieapp.main;

import com.example.rent.movieapp.search.MovieResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by RENT on 2017-03-08.
 */


public interface SearchService {

    @GET("/")
    Observable<MovieResult> search(int i, @Query("s") String title, @Query("y") String year, @Query("t") String type);

}
