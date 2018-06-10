package com.algonquintimes.algonquintimes.Articles;

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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.VolleyLog.TAG;


public class EntertainModel extends AndroidViewModel {

    @Nullable
    private JsonLiveData postsList;
    private int index;
    private MutableLiveData<Integer> refresh = new MutableLiveData<>();


    public EntertainModel(@NonNull Application application) {
        super(application);
        if (postsList == null)
            postsList = new JsonLiveData(application);

    }

    public MutableLiveData getRefresh() {
        return refresh;
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
        private final Context context;
        private List<Posts> mPosts = new ArrayList<Posts>();
        private int page = 1;

        public JsonLiveData(Context context) {
            this.context = context;
            LoadData();
        }

        private void LoadData() {

            final RequestQueue requestQueue = Volley.newRequestQueue(context);
            String baseUrl = Config.base_url;
            JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, baseUrl, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    // display response


                    for (int i = 0; i < response.length(); i++) {
                        final Posts post = new Posts();
                        try {

                            JSONObject obj = response.getJSONObject(i);
                            post.setId(obj.getInt("id"));
                            post.setCreatedAt(obj.getString("date"));
                            post.setPostURL(obj.getString("link"));
                            JSONObject titleObj = obj.getJSONObject("title");
                            post.setTitle(titleObj.getString("rendered"));
                            //Get excerpt
                            JSONObject exerptObj = obj.getJSONObject("excerpt");

                            String s = obj.getString("date");
                            s = s.replaceAll("T[0-9][0-9]:[0-9][0-9]:[0-9][0-9]", " ");

                            post.setExcerpt(s);
                            // Get content
                            JSONObject contentObj = obj.getJSONObject("content");
                            post.setContent(contentObj.getString("rendered"));


                            // getting URL of the Post fetured Image

                            int featureImageCheck = obj.getInt("featured_media");
                            String fturl = "http://algonquintimes.com/wp-content/themes/new-lotus/images/sample/sample_60x60.gif";
                            String fiurl = "http://algonquintimes.com/wp-content/themes/new-lotus/images/sample/sample_350x281.gif";
                            JSONObject featureImage = obj.getJSONObject("_embedded");
                            JSONArray featureAuthor = featureImage.getJSONArray("author");
                            JSONObject postAuthorObj = featureAuthor.getJSONObject(0);
                            String authorname = postAuthorObj.getString("name");
                            post.setPostAuthor(authorname);
                            if (featureImageCheck > 0) {

                                JSONArray featureImageUrl = featureImage.getJSONArray("wp:featuredmedia");
                                JSONObject featureImageObj = featureImageUrl.getJSONObject(0);
                                JSONObject featureMediaObj = featureImageObj.getJSONObject("media_details");
                                JSONObject featureSizeObj = featureMediaObj.getJSONObject("sizes");
                                JSONObject featurePostImageObj = featureSizeObj.getJSONObject("new-lotus-size-400-326");
                                fiurl = featurePostImageObj.getString("source_url");
                                JSONObject featureThumbnailObj = featureSizeObj.getJSONObject("new-lotus-size-60-60");
                                fturl = featureThumbnailObj.getString("source_url");

                            }
                            if (fiurl != null) {
                                post.setPostThumb(fturl);
                                post.setPostImg(fiurl);

                            }


                            mPosts.add(post);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    setValue(mPosts);
                    refresh.postValue(1);

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
