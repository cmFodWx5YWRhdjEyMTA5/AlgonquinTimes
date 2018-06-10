package com.algonquintimes.algonquintimes.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.algonquintimes.algonquintimes.Articles.ArticlesActivity;
import com.algonquintimes.algonquintimes.Calendar.CalendarActivity;
import com.algonquintimes.algonquintimes.Login.AccountActivity;
import com.algonquintimes.algonquintimes.R;
import com.algonquintimes.algonquintimes.Settings.SettingsActivity;
import com.algonquintimes.algonquintimes.Social.SocialActivity;

/**
 * Created by Marjan on 3/25/2018.
 */

public class BottomNavigationViewHelper {
    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationView bottomNavigationView){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");

    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationView view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.action_articles:
                        Intent intent1 = new Intent(context, ArticlesActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.action_calendar:
                        Intent intent2  = new Intent(context, CalendarActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.action_social:
                        Intent intent3 = new Intent(context, SocialActivity.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.action_settings:
                        Intent intent4 = new Intent(context, SettingsActivity.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);
                        /*Intent intent4 = new Intent(context, AccountActivity.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);*/
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;


                }


                return false;
            }
        });
    }

}
