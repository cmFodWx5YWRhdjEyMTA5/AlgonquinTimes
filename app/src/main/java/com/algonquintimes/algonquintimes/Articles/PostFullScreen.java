package com.algonquintimes.algonquintimes.Articles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.algonquintimes.algonquintimes.R;

public class PostFullScreen extends AppCompatActivity {

    // Variables
    private Activity mActivity;
    private Context mContext;

    // init views

    private ImageButton imgBtnShare;
    private Toolbar mToolbar;
    private TextView postTitle, postAuthor, postDate;
    private String postLink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_full_screen);
        // TextView textView=findViewById(R.id.fullpost);
        Intent intent = getIntent();
        Bundle bundle;
        bundle = intent.getExtras();
        WebView webView = findViewById(R.id.fullpost);
        WebView imageView = findViewById(R.id.post_img);
        postTitle = findViewById(R.id.title_text);
        postAuthor = findViewById(R.id.post_author);
        postDate = findViewById(R.id.date_text);
        imgBtnShare = findViewById(R.id.imgBtnShare);
        initToolbar();
        enableBackButton();


        if (bundle.isEmpty()) {


            webView.loadData("Data is empty", "text/html; charset=utf-8", "UTF-8");

        } else {

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setMinimumFontSize(16);
            webView.setWebChromeClient(new WebChromeClient());
            //webView.loadData(bundle.getString("content"), "text/html; charset=utf-8", "UTF-8");
            webView.loadData(getHtmlData(bundle.getString("content")), "text/html; charset=utf-8", "UTF-8");



            imageView.loadUrl(bundle.getString("image"));

            postTitle.setText(Html.fromHtml(bundle.getString("title")));
            postDate.setText(bundle.getString("date"));
            postLink = bundle.getString("link");
            postAuthor.setText(bundle.getString("author"));


            // textView.setText(Html.fromHtml());
        }

        initListener();

    }
    public String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    public void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    public void enableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public void initListener() {


        imgBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, postLink);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
