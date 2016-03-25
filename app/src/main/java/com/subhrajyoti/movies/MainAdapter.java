package com.subhrajyoti.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static class MainViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        MainViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.listImage);

        }
    }
    ArrayList<MovieModel> arrayList;
    Context context;

    public MainAdapter(Context context,ArrayList<MovieModel> arrayList){
        this.context=context;
        this.arrayList=arrayList;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Picasso.with(context).load(arrayList.get(position).getImgURL()).centerCrop().placeholder(R.drawable.placeholder).into(((MainViewHolder)holder).imageView);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
