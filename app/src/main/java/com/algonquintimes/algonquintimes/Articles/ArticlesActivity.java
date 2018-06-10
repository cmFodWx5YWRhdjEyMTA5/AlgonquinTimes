package com.algonquintimes.algonquintimes.Articles;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.algonquintimes.algonquintimes.Email.SendTipActivity;
import com.algonquintimes.algonquintimes.Email.StoryIdeaActivity;
import com.algonquintimes.algonquintimes.Login.LoginActivity;
import com.algonquintimes.algonquintimes.R;
import com.algonquintimes.algonquintimes.Utils.BottomNavigationViewHelper;

public class ArticlesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "ArticlesActivity";
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = ArticlesActivity.this;
    //widgets
    private ViewPager mViewPager;

    private LinearLayout story_layout;
    private LinearLayout tip_layout;
    private FloatingActionButton fab_main;
    private FloatingActionButton fab_tip;
    private FloatingActionButton fab_story;
    private Animation fab_open;
    private Animation fab_close;
    private Animation rotate_cw;
    private Animation rotate_acw;
    private Boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupViewPager();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        // change the hamburger Icon to tune
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_tune_black_24dp);
        setupBottomNavigationView();
        setFab();

    }


    private void setFab() {
        story_layout = findViewById(R.id.storyLayout);
        tip_layout = findViewById(R.id.tipLayout);
        fab_main = findViewById(R.id.fab);
        fab_tip = findViewById(R.id.fabSendTip);
        fab_story = findViewById(R.id.fabStory);
        fab_open = AnimationUtils.loadAnimation(this, R.anim.open_fab);
        fab_close = AnimationUtils.loadAnimation(this, R.anim.close_fab);
        rotate_cw = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
        rotate_acw = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise);
        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOpen) {
                    fab_main.setImageResource(R.drawable.ic_record_voice_over_white_24dp);
                    fab_story.startAnimation(fab_close);
                    fab_tip.startAnimation(fab_close);
                    tip_layout.startAnimation(fab_close);
                    story_layout.startAnimation(fab_close);
                    fab_main.startAnimation(rotate_acw);
                    story_layout.setVisibility(View.GONE);
                    tip_layout.setVisibility(View.GONE);
                    fab_story.setVisibility(View.GONE);
                    fab_tip.setVisibility(View.GONE);
                    isOpen = false;
                } else {
                    fab_story.startAnimation(fab_open);
                    fab_tip.startAnimation(fab_open);
                    tip_layout.startAnimation(fab_open);
                    story_layout.startAnimation(fab_open);
                    fab_main.startAnimation(rotate_cw);
                    story_layout.setVisibility(View.VISIBLE);
                    tip_layout.setVisibility(View.VISIBLE);
                    fab_story.setVisibility(View.VISIBLE);
                    fab_tip.setVisibility(View.VISIBLE);
                    fab_story.setClickable(true);
                    fab_tip.setClickable(true);
                    isOpen = true;
                    fab_main.setImageResource(R.drawable.ic_add_white_24dp);
                }
            }
        });

        fab_story.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent storyIdea = new Intent(getApplicationContext(), StoryIdeaActivity.class);//ACTIVITY_NUM = 2
                startActivity(storyIdea);*/
                Config.action="STORY";
                Intent storyIdea = new Intent(getApplicationContext(), LoginActivity.class);//ACTIVITY_NUM = 2
                startActivity(storyIdea);
//                Toast.makeText(applicationContext, "Story Idea Layout Appears", Toast.LENGTH_LONG).show()
            }
        });
        fab_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent sendATip = new Intent(getApplicationContext(), SendTipActivity.class);//ACTIVITY_NUM = 2
                startActivity(sendATip);*/
                Config.action="TIP";
                Intent sendATip = new Intent(getApplicationContext(), LoginActivity.class);//ACTIVITY_NUM = 2
                startActivity(sendATip);
//                Toast.makeText(applicationContext, "Send a Tip Layout Appears", Toast.LENGTH_LONG).show()
            }
        });
    }
    /**
     * Responsible for adding the Articles Fragment
     */
    private void setupViewPager() {
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.


            // Create a new Fragment to be placed in the activity layout
            ArticlesFragment firstFragment = new ArticlesFragment();

            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(getIntent().getExtras());

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }

    }



    @Override
    public void onBackPressed() {
        if (findViewById(R.id.fragment_container) != null) {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.articles, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();


        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                        ComponentName componentName = new ComponentName(mContext, SearchableActivity.class);
                        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                        ComponentName componentName = new ComponentName(mContext, SearchableActivity.class);
                        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName));
                        return false;

                    }
                }
        );


        // Configure the search info and add any event listeners...

        // return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_featured) {
            //Show the list of Features to the user
            FeaturedFragment secondFragment = new FeaturedFragment();
            secondFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, secondFragment).commit();

        } else if (id == R.id.action_news) {
            //Show the list of News to the user
            NewsFragment thirdFragment = new NewsFragment();
            thirdFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, thirdFragment).commit();

        } else if (id == R.id.action_entertain) {
            //Show the list of Entertainment to the user
            EntertainFragment forthFragment = new EntertainFragment();
            forthFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, forthFragment).commit();

        } else if (id == R.id.action_sports) {
            //Show the list of Sports to the user
            SportsFragment fifthFragment = new SportsFragment();
            fifthFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fifthFragment).commit();
        } else if (id == R.id.action_activities) {
            //Show the list of Activities to the user
            ActivitiesFragment sixthFragment = new ActivitiesFragment();
            sixthFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, sixthFragment).commit();
        } else if (id == R.id.action_lifestyles) {
            //Show the list of Lifestyles to the user
            LifeFragment seventhFragment = new LifeFragment();
            seventhFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, seventhFragment).commit();

        } else if (id == R.id.action_innovations) {
            //Show the list of Innovations to the user
            InnovationsFragment eighthFragment = new InnovationsFragment();
            eighthFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, eighthFragment).commit();

        } else if (id == R.id.action_opinions) {
            //Show the list of Opinions to the user
            OpinionsFragment ninthFragment = new OpinionsFragment();
            ninthFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, ninthFragment).commit();

        } else if (id == R.id.action_focus) {
            //Show the list of Focus Articles to the user
            FocusFragment tenthFragment = new FocusFragment();
            tenthFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, tenthFragment).commit();

        } else if (id == R.id.action_all) {
            //Show the list of Focus Articles to the user
            ArticlesFragment lastFragment = new ArticlesFragment();


            lastFragment.setArguments(getIntent().getExtras());
            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, lastFragment).commit();

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * BottomNavigationView setup
     */
    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationView);
        BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
    }


}
