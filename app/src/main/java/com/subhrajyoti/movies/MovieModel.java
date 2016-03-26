package com.subhrajyoti.movies;


import java.io.Serializable;

public class MovieModel implements Serializable {

    public String getoriginal_title() {
        return original_title;
    }

    public void setoriginal_title(String original_title) {
        this.original_title = original_title;
    }

    private String original_title;

    public String getposter_path() {
        return poster_path;
    }

    public void setposter_path(String poster_path) {
        this.poster_path = poster_path;
    }

    private String poster_path;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    private String overview;

    public String getvote_average() {
        return vote_average;
    }

    public void setvote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    private String vote_average;

    public String getrelease_date() {
        return release_date;
    }

    public void setrelease_date(String release_date) {
        this.release_date = release_date;
    }

    private String release_date;

    public MovieModel(String original_title,String poster_path,String overview, String vote_average,String release_date){

        this.original_title=original_title;
        this.poster_path=poster_path;
        this.overview=overview;
        this.vote_average=vote_average;
        this.release_date=release_date;


    }

}
