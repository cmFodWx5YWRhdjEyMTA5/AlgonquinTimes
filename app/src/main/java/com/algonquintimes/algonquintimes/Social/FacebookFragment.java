package com.algonquintimes.algonquintimes.Social;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.algonquintimes.algonquintimes.R;

import static com.android.volley.VolleyLog.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FacebookFragment extends Fragment {


    public FacebookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_facebook, container, false);
        WebView myWebView = view.findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setMinimumFontSize(16);
        myWebView.setWebChromeClient(new WebChromeClient());


        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);


        int pageWidth = (int) metrics.xdpi - 15;

        Log.d(TAG, "width:  ");
        Log.d(TAG, String.valueOf(pageWidth));
        int pageHeight = metrics.heightPixels;
        String myUrl = "https://www.powr.io/plugins/social-feed/view?unique_label=c7e88f1e_1522791460&external_type=iframe";
        myWebView.loadUrl(myUrl);




        return view;
    }

}
