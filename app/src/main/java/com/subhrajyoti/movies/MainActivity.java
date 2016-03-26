package com.subhrajyoti.movies;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

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
    ArrayList<MovieModel> arrayList;
    String API_Key = "954a72d39f9d0a7f59b3c738fa9d6e7f";
    final String ROOT_URL = "http://api.themoviedb.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        arrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mainAdapter = new MainAdapter(this,arrayList,API_Key);
        recyclerView.setAdapter(mainAdapter);
        Log.v("Test", "Adapter");
        getMovies();

        recyclerView.addOnItemTouchListener(new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                MovieModel movieModel = arrayList.get(position);
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

        Call<Movies> call = movieAPI.loadMovies(API_Key);
        call.enqueue(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponse(Response<Movies> moviesResponse, Retrofit retrofit) {
        Log.v("fail","Recieved");
        if (moviesResponse.body().results==null)
        {
            Log.v("Null","response is null");
        }
        else
        for (int i=0;i<moviesResponse.body().results.size();i++)
        {
            arrayList.add(moviesResponse.body().results.get(i));
            Log.v("fail", moviesResponse.body().results.get(i).getposter_path());
        }
        mainAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailure(Throwable t) {
        Log.v("fail",t.getLocalizedMessage());

    }
}
