package com.algonquintimes.algonquintimes.Social;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.algonquintimes.algonquintimes.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstagramFragment extends Fragment {


    public InstagramFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        WebView myWebView = view.findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMinimumFontSize(16);
        myWebView.setWebChromeClient(new WebChromeClient());



        String myUrl = "https://www.powr.io/plugins/instagram-feed/view?unique_label=0f6fe7b9_1521304270&external_type=iframe";

        myWebView.loadUrl(myUrl);




        return view;
    }

}
