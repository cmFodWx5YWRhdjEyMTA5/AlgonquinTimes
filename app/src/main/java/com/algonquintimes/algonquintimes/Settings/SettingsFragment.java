package com.algonquintimes.algonquintimes.Settings;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.algonquintimes.algonquintimes.R;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.login.LoginManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {
    private Button socialButtonLogout;
    AccessToken accessToken = AccountKit.getCurrentAccessToken();
    com.facebook.AccessToken loginToken = com.facebook.AccessToken.getCurrentAccessToken();
    public SettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        RelativeLayout relLayoutTerm = view.findViewById(R.id.relLayoutTerm);
        relLayoutTerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentTerms;
                intentTerms = new Intent(getActivity(), TermsActivity.class);
                startActivity(intentTerms);

            }
        });


        RelativeLayout relLayoutContact = view.findViewById(R.id.relLayoutContact);
        relLayoutContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentContact;
                intentContact = new Intent(getActivity(), ContactActivity.class);
                startActivity(intentContact);

            }
        });

        RelativeLayout relLayoutAbout = view.findViewById(R.id.relLayoutAbout);
        relLayoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentAbout;
                intentAbout = new Intent(getActivity(), AboutActivity.class);
                startActivity(intentAbout);

            }
        });
        socialButtonLogout = view.findViewById(R.id.socialButtonLogout);

        socialButtonLogout.setVisibility(View.GONE);

        if (accessToken != null || loginToken != null) {
            // if previously logged in, proceed to the account activity
            //launchAccountActivity();
            Log.d("token not null", "onCreateView: ");
            socialButtonLogout.setVisibility(View.VISIBLE);
        }

        socialButtonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                socialButtonLogout.setVisibility(View.GONE);
               onLogout();


            }
        });



        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

         accessToken = AccountKit.getCurrentAccessToken();
         loginToken = com.facebook.AccessToken.getCurrentAccessToken();
        if (accessToken != null || loginToken != null) {
            // if previously logged in, proceed to the account activity
            //launchAccountActivity();
            Log.d("token not null", "onCreateView: ");
            socialButtonLogout.setVisibility(View.VISIBLE);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    public void onLogout() {
        // logout of Account Kit
        AccountKit.logOut();
        // logout of Login Button
        LoginManager.getInstance().logOut();

        //launchLoginActivity();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        accessToken = AccountKit.getCurrentAccessToken();
        loginToken = com.facebook.AccessToken.getCurrentAccessToken();
        if (accessToken != null || loginToken != null) {
            // if previously logged in, proceed to the account activity
            //launchAccountActivity();
            Log.d("token not null", "onCreateView: ");
            socialButtonLogout.setVisibility(View.VISIBLE);
        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onResume() {

       // socialButtonLogout.setVisibility(View.GONE);
        accessToken = AccountKit.getCurrentAccessToken();
        loginToken = com.facebook.AccessToken.getCurrentAccessToken();
        if (accessToken != null || loginToken != null) {
            // if previously logged in, proceed to the account activity
            //launchAccountActivity();

            Log.d("token not null", "onCreateView: ");
            socialButtonLogout.setVisibility(View.VISIBLE);
        }
        Log.d("resumed", "onResume: ");
        super.onResume();
    }
}
