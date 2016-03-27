package com.subhrajyoti.movies;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @Bind(R.id.toolImage)
    ImageView imageView;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        MovieModel movieModel = (MovieModel) getIntent().getSerializableExtra("MovieModel");
        collapsingToolbarLayout.setTitle(movieModel.getoriginal_title());
        Picasso.with(getApplicationContext()).load(BuildConfig.IMAGE_URL+"/w500" + movieModel.getBackdrop_path() + "?api_key?=" + BuildConfig.API_KEY).placeholder(R.drawable.placeholder).into(imageView);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case android.R.id.home:
                super.onBackPressed();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}
