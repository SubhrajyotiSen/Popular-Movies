package com.subhrajyoti.movies;


import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {

    private String release_date;
    private String original_title;
    private String poster_path;
    private String overview;
    private String vote_average;
    private String backdrop_path;
    public MovieModel(String original_title,String poster_path,String overview, String vote_average,String release_date,String backdrop_path){

        this.original_title=original_title;
        this.poster_path=poster_path;
        this.overview=overview;
        this.vote_average=vote_average;
        this.release_date=release_date;
        this.backdrop_path=backdrop_path;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(original_title);
        dest.writeString(poster_path);
        dest.writeString(vote_average);
        dest.writeString(backdrop_path);
        dest.writeString(overview);
        dest.writeString(release_date);

    }

    protected MovieModel(Parcel in) {
        original_title = in.readString();
        poster_path = in.readString();
        vote_average = in.readString();
        backdrop_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getoriginal_title() {
        return original_title;
    }

    public void setoriginal_title(String original_title) {
        this.original_title = original_title;
    }


    public String getposter_path() {
        return poster_path;
    }

    public void setposter_path(String poster_path) {
        this.poster_path = poster_path;
    }


    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }


    public String getvote_average() {
        return vote_average;
    }

    public void setvote_average(String vote_average) {
        this.vote_average = vote_average;
    }


    public String getrelease_date() {
        return release_date;
    }

    public void setrelease_date(String release_date) {
        this.release_date = release_date;
    }



    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }



}
