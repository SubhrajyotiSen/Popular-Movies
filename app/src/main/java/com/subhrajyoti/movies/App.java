package com.subhrajyoti.movies;

import android.app.Application;

public class App extends Application {
    private static MovieClient movieClient;

    @Override
    public void onCreate() {
        super.onCreate();

        movieClient = new MovieClient();
    }

    public static MovieClient getMovieClient() {
        return movieClient;
    }
}
