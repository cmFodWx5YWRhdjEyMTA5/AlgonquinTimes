package com.algonquintimes.algonquintimes.Calendar;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.algonquintimes.algonquintimes.R;
import com.algonquintimes.algonquintimes.Utils.BottomNavigationViewHelper;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mContext = CalendarActivity.this;

    //widgets
    // private ViewPager mViewPager =(ViewPager) findViewById(R.id.viewpager_container);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        setupBottomNavigationView();

        setupViewPager();
        ActionBar actionbar = getSupportActionBar();
        actionbar.setTitle("Calendar of Events");

    }

    /**
     * Responsible for adding the 3 Facebook: Twitter, Instagram
     */
    private void setupViewPager() {

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FirstFragment()); //index 0
        adapter.addFragment(new SecondFragment()); //index 1
        adapter.addFragment(new ThirdFragment()); //index 2
        adapter.addFragment(new ForthFragment()); //index 3


        // mViewPager.setAdapter(adapter);
        ViewPager viewPager = findViewById(R.id.viewpager_container);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(0);
        tabLayout.getTabAt(0).setText("All Events");


        Calendar calendar = Calendar.getInstance();

        int thisYear = calendar.get(Calendar.YEAR);



        int thisMonth = calendar.get(Calendar.MONTH);



        int thisDay = calendar.get(Calendar.DAY_OF_MONTH);


        int monthCounter = thisMonth;
        int yearCounter = thisYear;
        String yearString = "";
        for (int l = 1; l <= 3; l++) {
            yearString = String.valueOf(yearCounter);


            switch (monthCounter) {

                case 0:

                    tabLayout.getTabAt(l).setText("January " + yearString);

                    break;
                case 1:

                    tabLayout.getTabAt(l).setText("February " + yearString);
                    break;
                case 2:

                    tabLayout.getTabAt(l).setText("March " + yearString);
                    break;
                case 3:

                    tabLayout.getTabAt(l).setText("April " + yearString);
                    break;
                case 4:

                    tabLayout.getTabAt(l).setText("May " + yearString);
                    break;
                case 5:

                    tabLayout.getTabAt(l).setText("June " + yearString);
                    break;
                case 6:

                    tabLayout.getTabAt(l).setText("July " + yearString);
                    break;
                case 7:

                    tabLayout.getTabAt(l).setText("August " + yearString);
                    break;
                case 8:

                    tabLayout.getTabAt(l).setText("September " + yearString);
                    break;
                case 9:

                    tabLayout.getTabAt(l).setText("October " + yearString);
                    break;
                case 10:

                    tabLayout.getTabAt(l).setText("November " + yearString);
                    break;
                case 11:

                    tabLayout.getTabAt(l).setText("December " + yearString);
                    break;
            }
            monthCounter++;
            if (monthCounter > 11) {
                monthCounter = 0;
                yearCounter++;
            }
        }


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
