package com.subhrajyoti.movies;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {

    @Bind(R.id.toolImage)
    ImageView toolImage;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Bind(R.id.imageView)
    ImageView imageView;
    @Bind(R.id.textView)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);

        MovieModel movieModel =  getIntent().getParcelableExtra("MovieModel");
        collapsingToolbarLayout.setTitle(" ");
        textView.setText(movieModel.getoriginal_title());
        Picasso.with(getApplicationContext()).load(BuildConfig.IMAGE_URL+"/w500" + movieModel.getBackdrop_path() + "?api_key?=" + BuildConfig.API_KEY).placeholder(R.drawable.placeholder).into(toolImage);

        Picasso.with(getApplicationContext()).load(BuildConfig.IMAGE_URL+"/w342" + movieModel.getposter_path() + "?api_key?=" + BuildConfig.API_KEY).placeholder(R.drawable.placeholder).into(imageView);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){

            case android.R.id.home:
                supportFinishAfterTransition();
                super.onBackPressed();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

}
