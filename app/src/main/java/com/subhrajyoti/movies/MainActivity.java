package com.subhrajyoti.movies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements Callback<Movies> {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    MainAdapter secondAdapter;
    ArrayList<MovieModel> popularList;
    ArrayList<MovieModel> ratedList;
    GridLayoutManager portrait;
    GridLayoutManager landscape;
    boolean popular;
    String API_Key = "954a72d39f9d0a7f59b3c738fa9d6e7f";
    final String ROOT_URL = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        popularList = new ArrayList<>();
        ratedList = new ArrayList<>();
        popular=true;
        portrait = new GridLayoutManager(this,2);
        landscape = new GridLayoutManager(this,3);
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(portrait);
        else
            recyclerView.setLayoutManager(landscape);
        mainAdapter = new MainAdapter(this,popularList,API_Key);
        secondAdapter = new MainAdapter(this,ratedList,API_Key);
        recyclerView.setAdapter(mainAdapter);
        Log.v("Test", "Adapter");
        getMovies();

        recyclerView.addOnItemTouchListener(new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                MovieModel movieModel = popularList.get(position);
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("MovieModel", movieModel);
                startActivity(intent);

            }
        }));
    }

    private void getMovies(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<Movies> call = movieAPI.loadPopularMovies(API_Key);
        Call<Movies> call2 = movieAPI.loadratedMovies(API_Key);
        if (popular)
            call.enqueue(this);
        else
            call2.enqueue(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item =  menu.findItem(R.id.action_sort_by_popularity);
        item.setChecked(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case R.id.action_sort_by_rating:
                item.setChecked(true);
                //getMovies();
                recyclerView.setAdapter(secondAdapter);
                break;
            case R.id.action_sort_by_popularity:
                item.setChecked(true);
                recyclerView.setAdapter(mainAdapter);
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Response<Movies> moviesResponse, Retrofit retrofit) {

        if (popular) {

            for (int i = 0; i < moviesResponse.body().results.size(); i++) {
                popularList.add(moviesResponse.body().results.get(i));
                Log.v("popular", moviesResponse.body().results.get(i).getoriginal_title());
            }
            popular=false;
            getMovies();
        }
        else
            for (int i=0;i<moviesResponse.body().results.size();i++)
            {
                ratedList.add(moviesResponse.body().results.get(i));
                Log.v("rated", moviesResponse.body().results.get(i).getoriginal_title());
            }
        mainAdapter.notifyDataSetChanged();
        secondAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.v("fail", t.getLocalizedMessage());

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(landscape);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(portrait);
        }
    }
}
