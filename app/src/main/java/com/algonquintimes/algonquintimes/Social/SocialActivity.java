package com.algonquintimes.algonquintimes.Social;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toolbar;


import com.algonquintimes.algonquintimes.R;
import com.algonquintimes.algonquintimes.Utils.BottomNavigationViewHelper;
import com.algonquintimes.algonquintimes.Utils.SectionsPagerAdapter;

public class SocialActivity extends AppCompatActivity {
    private static final String TAG = "SocialActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mContext = SocialActivity.this;
    //widgets
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        Log.d(TAG, "onCreate: starting");
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.my_toolbar));


        setupBottomNavigationView();
        setupViewPager();
    }


    /**
     * Responsible for adding the 3 Facebook: Twitter, Instagram
     */
    private void setupViewPager(){
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FacebookFragment()); //index 0
        adapter.addFragment(new TwitterFragment()); //index 1
        adapter.addFragment(new InstagramFragment()); //index 2
        //mViewPager.setAdapter(adapter);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setText("Facebook");
        tabLayout.getTabAt(1).setText("Twitter");
        tabLayout.getTabAt(2).setText("Instagram");
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


}
