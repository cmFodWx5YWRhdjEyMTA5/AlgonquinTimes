package com.algonquintimes.algonquintimes.Calendar;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.algonquintimes.algonquintimes.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForthFragment extends Fragment {
    public static String TAG = "postFrag";
    public List<Posts> mPosts;
    public Button btnGetPost;
    public RecyclerView recyclerView;
    public PostAdapter postAdapter;
    public int page = 1;
    public SwipeRefreshLayout swipeRefreshLayout;
    public File file;
    private ForthModel postModel;
    private Observer<List<Posts>> postsObserver;

    public ForthFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_events4, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        super.onActivityCreated(savedInstanceState);
        setUrl();
        //Config.base_url= "http://www.algonquinsa.com/wp-json/tribe/events/v1/events/?per_page=30";
        recyclerView = getActivity().findViewById(R.id.recyclerEvent4);
        postAdapter = new PostAdapter(mPosts, getContext(), false, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent_green, R.color.md_red_800, R.color.md_blue_500, R.color.purple);
        mPosts = new ArrayList<Posts>();
        recyclerView.setAdapter(postAdapter);
        postModel = ViewModelProviders.of(getActivity()).get(ForthModel.class);
        swipeRefreshLayout.setEnabled(true);
        postModel.getPostsList().observe(this, new Observer<List<Posts>>() {
            @Override
            public void onChanged(@Nullable List<Posts> posts) {
                postAdapter.setData(posts);
                postAdapter.notifyItemChanged(postModel.getChangeIndex());
                Log.d(TAG, "On Changed method called");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        postModel.getRefresh().observe(this, new Observer<Integer>() {

            @Override
            public void onChanged(@Nullable Integer integer) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                postModel.RefreshData();


            }
        });


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUrl();
        //  Config.base_url= "http://www.algonquinsa.com/wp-json/tribe/events/v1/events/?per_page=30";
        recyclerView = getActivity().findViewById(R.id.recyclerEvent4);
        postAdapter = new PostAdapter(mPosts, getContext(), false, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swipeRefreshLayout = getActivity().findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent_green, R.color.md_red_800, R.color.md_blue_500, R.color.purple);
        mPosts = new ArrayList<Posts>();
        recyclerView.setAdapter(postAdapter);
        postModel = ViewModelProviders.of(getActivity()).get(ForthModel.class);
        swipeRefreshLayout.setEnabled(true);
        postModel.getPostsList().observe(this, new Observer<List<Posts>>() {
            @Override
            public void onChanged(@Nullable List<Posts> posts) {
                postAdapter.setData(posts);
                postAdapter.notifyItemChanged(postModel.getChangeIndex());
                Log.d(TAG, "On Changed method called");
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        postModel.getRefresh().observe(this, new Observer<Integer>() {

            @Override
            public void onChanged(@Nullable Integer integer) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                postModel.RefreshData();


            }
        });


    }


    public void setData(List<Posts> posts) {
        recyclerView.setAdapter(postAdapter);
        postAdapter.setData(mPosts);
        swipeRefreshLayout.setRefreshing(false);
        postAdapter.notifyDataSetChanged();
    }

    public void setUrl() {
        // setting the api url
        Calendar calendar = Calendar.getInstance();

        int thisYear = calendar.get(Calendar.YEAR);
        int thisMonth = calendar.get(Calendar.MONTH);

        int yearCounter = thisYear;
        int monthCounter = thisMonth + 3;

        if (monthCounter > 12) {
            monthCounter = monthCounter - 12;
            yearCounter++;
        }
        String dateString = "&start_date=" + String.valueOf(yearCounter) + "-" + String.valueOf(monthCounter) + "-01&end_date=" + String.valueOf(yearCounter) + "-" + String.valueOf(monthCounter) + "-31";

        Config.base_url = "http://www.algonquinsa.com/wp-json/tribe/events/v1/events/?per_page=30" + dateString;
        Log.d(TAG, "config url: " + Config.base_url);
    }


}
