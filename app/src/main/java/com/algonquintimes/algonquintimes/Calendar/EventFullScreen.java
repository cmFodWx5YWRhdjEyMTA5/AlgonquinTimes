package com.algonquintimes.algonquintimes.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EventFullScreen extends AppCompatActivity {

    // Variables
    private Activity mActivity;
    private Context mContext;

    // init views

    private ImageButton imgBtnShare, imgCalEvent;
    private Toolbar mToolbar;
    private TextView postTitle, postVenue, postStartDate, postEndDate;
    private String postLink;
    private String eventTitle;
    private String eventStartDate;
    private String eventEndDate;
    private String eventVenue;
    private String eventDesc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_full_screen);
        // TextView textView=findViewById(R.id.fullpost);
        Intent intent = getIntent();
        Bundle bundle;
        bundle = intent.getExtras();
        WebView webView = findViewById(R.id.fullpost);
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        WebView imageView = findViewById(R.id.post_img);
        postTitle = findViewById(R.id.title_text);
        postVenue = findViewById(R.id.event_venue);
        postStartDate = findViewById(R.id.event_start);
        postEndDate = findViewById(R.id.event_end);
        imgBtnShare = findViewById(R.id.imgBtnShare);
        imgCalEvent = findViewById(R.id.date_image);
        initToolbar();
        enableBackButton();

        if (bundle.isEmpty()) {


            webView.loadData("Data is empty", "text/html; charset=utf-8", "UTF-8");
        } else {


            //webView.loadData(bundle.getString("content"), "text/html; charset=utf-8", "UTF-8");

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setMinimumFontSize(16);
            webView.setWebChromeClient(new WebChromeClient());
            webView.loadData(getHtmlData(bundle.getString("content")), "text/html; charset=utf-8", "UTF-8");


            imageView.loadUrl(bundle.getString("image"));
            postTitle.setText(Html.fromHtml(bundle.getString("title")));
            postStartDate.setText(bundle.getString("startdate"));
            postEndDate.setText(bundle.getString("enddate"));
            postLink = bundle.getString("link");
            postVenue.setText(bundle.getString("venue"));


            //NOTE: setting variables to add to calendar
            eventTitle =  postTitle.getText().toString();
            eventVenue = bundle.getString("venue");
            eventStartDate = bundle.getString("startdate");
            eventEndDate = bundle.getString("enddate");

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


        /* NOTE: New Code */
        imgCalEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA);
                try {
                    Date start  = formatter.parse(eventStartDate);
                    Date finish = formatter.parse(eventEndDate);

                    Calendar beginTime = Calendar.getInstance();
                    beginTime.setTime(start);

                    Calendar endTime = Calendar.getInstance();
                    endTime.setTime(finish);

                    Intent intent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, eventTitle)
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, eventVenue)
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);
                    startActivity(intent);
                    finish();
                }catch (ParseException e){
                    e.printStackTrace();
                }
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
