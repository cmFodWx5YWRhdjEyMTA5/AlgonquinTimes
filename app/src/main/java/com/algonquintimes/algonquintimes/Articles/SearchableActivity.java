package com.algonquintimes.algonquintimes.Articles;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.algonquintimes.algonquintimes.R;

public class SearchableActivity extends AppCompatActivity {
    public static final String TAG = "Searchable Activity";


    private static final int ACTIVITY_NUM = 5;
    private Context mContext = SearchableActivity.this;
    //widgets
    private ViewPager mViewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: starting");

        handleIntent(this.getIntent());

        setContentView(R.layout.activity_searchable);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Log.d(TAG, "onCreate: starting");
        //setupViewPager();
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Search Results");

        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }





    @Override
    public void onBackPressed() {

        super.onBackPressed();

    }



    

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(
                    SearchManager.QUERY);


            Config.base_url = "http://algonquintimes.com/wp-json/wp/v2/posts?_embed=true&page=1&per_page=10&search=" + query;


        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(this.getIntent());
    }

}
