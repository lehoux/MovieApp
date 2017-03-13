package com.example.rent.movieapp.main;

import com.example.rent.movieapp.search.MovieResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchService {

    @GET("/")
    Observable<MovieResult> search(@Query("page") int page, @Query("s") String title, @Query("y") String year, @Query("type") String type);

}
