package com.subhrajyoti.movies;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

public class MovieClient
{
    private MovieAPI movieAPI;

    public MovieClient()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        movieAPI = retrofit.create(MovieAPI.class);
    }

    public MovieAPI getMovieAPI()
    {
        return movieAPI;
    }
}
