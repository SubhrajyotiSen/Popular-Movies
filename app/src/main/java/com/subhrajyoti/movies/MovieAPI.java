package com.subhrajyoti.movies;


import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface MovieAPI {


    @GET("/3/movie/{sort}")
    Call<Movies> loadMovies(@Path("sort") String sort,@Query("api_key") String api_key);


}
