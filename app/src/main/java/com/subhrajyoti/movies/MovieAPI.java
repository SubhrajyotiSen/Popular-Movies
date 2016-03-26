package com.subhrajyoti.movies;


import java.util.ArrayList;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;


public interface MovieAPI {


    @GET("/3/movie/popular")
    Call<Movies> loadMovies(@Query("api_key") String api_key);
}
