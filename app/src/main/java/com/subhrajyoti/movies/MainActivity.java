package com.subhrajyoti.movies;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    MainAdapter secondAdapter;
    ArrayList<MovieModel> popularList;
    ArrayList<MovieModel> ratedList;
    GridLayoutManager portrait;
    GridLayoutManager landscape;
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
        popular=true;
        portrait = new GridLayoutManager(this,2);
        landscape = new GridLayoutManager(this,3);
        if (getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
            recyclerView.setLayoutManager(portrait);
        else
            recyclerView.setLayoutManager(landscape);
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

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        Call<Movies> call = movieAPI.loadMovies(sort,BuildConfig.API_KEY);


        call.enqueue(new Callback<Movies>() {
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

    });*/
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
                item.setChecked(true);
                //getMovies();
                recyclerView.setAdapter(secondAdapter);
                popular=true;
                break;
            case R.id.action_sort_by_popularity:
                item.setChecked(true);
                recyclerView.setAdapter(mainAdapter);
                popular=false;
                break;

        }


        return super.onOptionsItemSelected(item);
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

    public void displayDialog(){
        MaterialDialog.Builder builder = new MaterialDialog.Builder(this)
                .content("Fetching Movies").progress(true, 0);

        dialog = builder.build();
        dialog.show();
    }
}
