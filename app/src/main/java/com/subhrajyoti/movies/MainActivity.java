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

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    MainAdapter secondAdapter;
    ArrayList<MovieModel> popularList;
    ArrayList<MovieModel> ratedList;
    GridLayoutManager gridLayoutManager;
    MaterialDialog dialog;
    boolean popular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        popularList = new ArrayList<>();
        ratedList = new ArrayList<>();
        popular=false;
        gridLayoutManager = new GridLayoutManager(this,2);

        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            gridLayoutManager.setSpanCount(2);
        else
            gridLayoutManager.setSpanCount(3);
        mainAdapter = new MainAdapter(this,popularList);
        secondAdapter = new MainAdapter(this,ratedList);
        recyclerView.setAdapter(mainAdapter);
        displayDialog();
        getMovies("popular");
        getMovies("top_rated");


        recyclerView.addOnItemTouchListener(new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                MovieModel movieModel;
                if (!popular){
                    movieModel = popularList.get(position);
                }
                else {
                    movieModel = ratedList.get(position);
                }
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("MovieModel", movieModel);
                startActivity(intent);

            }
        }));
    }

    private void getMovies(final String sort){


        App.getMovieClient().getMovieAPI().loadMovies(sort,BuildConfig.API_KEY).enqueue(new Callback<Movies>() {

            @Override
            public void onResponse(Response<Movies> response, Retrofit retrofit) {
                if (sort.equals("popular")) {
                    for (int i = 0; i < response.body().results.size(); i++) {
                        popularList.add(response.body().results.get(i));
                        Log.v(sort, response.body().results.get(i).getoriginal_title());
                    }
                    mainAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    for (int i = 0; i < response.body().results.size(); i++) {
                        ratedList.add(response.body().results.get(i));
                        Log.v(sort, response.body().results.get(i).getoriginal_title());
                    }
                    secondAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

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
                recyclerView.setAdapter(secondAdapter);
                popular=true;
                break;
            case R.id.action_sort_by_popularity:
                recyclerView.setAdapter(mainAdapter);
                popular=false;
                break;

        }
        item.setChecked(true);

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
           gridLayoutManager.setSpanCount(3);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            gridLayoutManager.setSpanCount(2);
        }
    }

    public void displayDialog(){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .content("Fetching Movies").progress(true, 0);

        dialog = builder.build();
        dialog.show();
    }
}
