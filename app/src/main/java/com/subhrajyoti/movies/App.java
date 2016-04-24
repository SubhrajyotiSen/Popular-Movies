package com.subhrajyoti.movies;

import android.app.Application;

public class App extends Application {
    private static MovieAPI.MovieClient movieClient;

    @Override
    public void onCreate() {
        super.onCreate();

        movieClient = new MovieAPI.MovieClient();
    }

    public static MovieAPI.MovieClient getMovieClient() {
        return movieClient;
    }
}
