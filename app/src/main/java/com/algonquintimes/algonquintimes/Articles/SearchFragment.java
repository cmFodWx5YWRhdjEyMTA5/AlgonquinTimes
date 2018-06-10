package com.algonquintimes.algonquintimes.Articles;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {
    public static String TAG = "postFrag";
    public List<Posts> mPosts;
    public Button btnGetPost;
    public RecyclerView recyclerView;
    public PostAdapter postAdapter;
    public int page = 1;
    // public SwipeRefreshLayout swipeRefreshLayout;
    public File file;
    private SearchModel postModel;
    private Observer<List<Posts>> postsObserver;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);


        return view;
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerSearch);
        postAdapter = new PostAdapter(mPosts, getContext(), false, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mPosts = new ArrayList<Posts>();
        recyclerView.setAdapter(postAdapter);
        postModel = ViewModelProviders.of(getActivity()).get(SearchModel.class);

        postModel.getPostsList().observe(this, new Observer<List<Posts>>() {
            @Override
            public void onChanged(@Nullable List<Posts> posts) {
                postAdapter.setData(posts);
                postAdapter.notifyItemChanged(postModel.getChangeIndex());



            }
        });
        postModel.getRefresh().observe(this, new Observer<Integer>() {

            @Override
            public void onChanged(@Nullable Integer integer) {

            }
        });




    }


    public void setData(List<Posts> posts) {
        recyclerView.setAdapter(postAdapter);
        postAdapter.setData(mPosts);
        // swipeRefreshLayout.setRefreshing(false);
        postAdapter.notifyDataSetChanged();
    }



}
