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
public class TwitterFragment extends Fragment {


    public TwitterFragment() {
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



        String myUrl = "https://www.powr.io/plugins/facebook-feed/view?unique_label=b4bb604e_1522789433&external_type=iframe";

        myWebView.loadUrl(myUrl);

        return view;
    }

}
