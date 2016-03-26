package com.subhrajyoti.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<MovieModel> data = new ArrayList<>();
    String API_KEY;

    public MainAdapter(Context context, ArrayList<MovieModel> data,String API_KEY) {
        this.context = context;
        this.data = data;
        this.API_KEY = API_KEY;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_item, parent, false);
        viewHolder = new MyItemHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Picasso.with(context).load("http://image.tmdb.org/t/p/w500" + data.get(position).getposter_path() + "?api_key?=" + API_KEY).placeholder(R.drawable.placeholder).into(((MyItemHolder) holder).imageView);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MyItemHolder extends RecyclerView.ViewHolder {
        ImageView imageView;


        public MyItemHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.listImage);
        }

    }


}