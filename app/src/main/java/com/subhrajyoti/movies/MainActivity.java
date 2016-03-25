package com.subhrajyoti.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import butterknife.Bind;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recyclerView)
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    private GridLayoutManager gridLayoutManager;
    private ArrayList<MovieModel> arrayList;
    private final String API_Key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        mainAdapter = new MainAdapter(this,arrayList);
        recyclerView.setAdapter(mainAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerClickListener(this, new RecyclerClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {

                MovieModel movieModel = arrayList.get(position);
                Intent intent = new Intent(MainActivity.this,DetailsActivity.class);
                intent.putExtra("MovieModel",movieModel);
                startActivity(intent);

            }
        }));




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
}
