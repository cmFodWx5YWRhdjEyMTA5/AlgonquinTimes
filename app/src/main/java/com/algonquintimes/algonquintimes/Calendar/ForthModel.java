package com.algonquintimes.algonquintimes.Calendar;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;


public class ForthModel extends AndroidViewModel {

    @Nullable
    private JsonLiveData postsList;
    private int index;

    public MutableLiveData getRefresh() {
        return refresh;
    }


    private MutableLiveData<Integer> refresh = new MutableLiveData<>();

    public ForthModel(@NonNull Application application) {
        super(application);
        if (postsList == null)
            postsList = new JsonLiveData(application);

    }

    public MutableLiveData<List<Posts>> getPostsList() {
        return postsList;
    }

    public int getChangeIndex() {
        return index;
    }

    public void RefreshData() {
        refresh.setValue(0);
        postsList = new JsonLiveData(this.getApplication());
    }


    public class JsonLiveData extends MutableLiveData<List<Posts>> {
        private List<Posts> mPosts = new ArrayList<Posts>();
        private final Context context;
        private int page = 1;

        public JsonLiveData(Context context) {
            this.context = context;
            LoadData();
        }

        private void LoadData() {

            final RequestQueue requestQueue = Volley.newRequestQueue(context);
            String baseUrl = Config.base_url;
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, baseUrl, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("events");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final Posts post = new Posts();
                            try {

                                JSONObject obj = jsonArray.getJSONObject(i);
                                post.setId(obj.getInt("id"));
                                post.setCreatedAt(obj.getString("date"));
                                post.setPostURL(obj.getString("url"));
                                //JSONObject titleObj = obj.getJSONObject("title");
                                post.setTitle(obj.getString("title"));
                                //Get excerpt
                                //JSONObject exerptObj = obj.getJSONObject("excerpt");
                                //post.setExcerpt(exerptObj.getString("rendered"));
                                //post.setExcerpt(obj.getString("date"));
                                String s = obj.getString("start_date");
                                s = s.replaceAll("[0-9][0-9]:[0-9][0-9]:[0-9][0-9]", " ");
                                post.setExcerpt(s);

                                //post.setStartDate(obj.getString("start_date"));
                                String sd = obj.getString("start_date");
                                sd = sd.replaceAll(":[0-9][0-9]$", " ");
                                post.setStartDate(sd);
                                //post.setEndDate(obj.getString("end_date"));
                                String ed = obj.getString("end_date");
                                ed = ed.replaceAll(":[0-9][0-9]$", " ");
                                post.setEndDate(ed);

                                // Get content
                                // JSONObject contentObj = obj.getJSONObject("content");
                                post.setContent(obj.getString("description"));
                                post.setPostURL(obj.getString("url"));
                                String venue = "";
                                try { // getting the venue details
                                    JSONObject venueDetails = obj.getJSONObject("venue");
                                    venue = venueDetails.getString("venue");
                                    venue += System.getProperty("line.separator");
                                    venue += venueDetails.getString("address");
                                    venue += System.getProperty("line.separator");
                                    venue += venueDetails.getString("city");
                                    venue += ", " + venueDetails.getString("province");
                                    venue += " " + venueDetails.getString("zip");
                                } catch (JSONException e) {
                                }
                                post.setVenue(venue);
                                // getting URL of the Post fetured Image

                                Log.d(TAG, "onResponse loop number: " + i);
                                if (obj.getString("description").isEmpty()) {
                                    Log.d(TAG, "onResponse: Description is empty");
                                } else {
                                    try {
                                        JSONObject featureImage = obj.getJSONObject("image");

                                        JSONObject featureImageObj = featureImage.getJSONObject("sizes");
                                        JSONObject featureThumbObj = featureImageObj.getJSONObject("thumbnail");
                                        JSONObject featureImgObj = featureImageObj.getJSONObject("medium");
                                        String fiurl = featureImgObj.getString("url");
                                        String fturl = featureThumbObj.getString("url");


                                        if (fiurl != null) {
                                            post.setPostThumb(fturl);
                                            post.setPostImg(fiurl);

                                        }
                                        mPosts.add(post);
                                    } catch (JSONException e) {

                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                        setValue(mPosts);
                        refresh.postValue(1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, error.toString());
                        }
                    }

            );


            requestQueue.add(getRequest);

        }
    }


}
