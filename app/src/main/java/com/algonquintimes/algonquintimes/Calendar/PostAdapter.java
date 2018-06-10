package com.algonquintimes.algonquintimes.Calendar;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.algonquintimes.algonquintimes.Articles.GlideApp;
import com.algonquintimes.algonquintimes.R;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import static com.bumptech.glide.request.RequestOptions.fitCenterTransform;


public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int FADE_DURATION = 1000;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;
    Bundle bundle = new Bundle();
    private String TAG = "LoadImage";
    private Context context;
    private List<Posts> questionList;

    private boolean mWithHeader;
    private boolean mWithFooter;
    private View.OnClickListener mOnClickListener;

    PostAdapter(List<Posts> posts, Context context, boolean withHeader, boolean withFooter) {
        this.questionList = posts;
        this.context = context;
        this.mWithHeader = withHeader;
        this.mWithFooter = withFooter;

    }

    @Override
    public int getItemViewType(int position) {

        if (mWithHeader && isPositionHeader(position))
            return TYPE_HEADER;
        if (mWithFooter && isPositionFooter(position))
            return TYPE_FOOTER;
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Log.d("debugMode3", "in Recycleview");
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.postitem, viewGroup, false);
        //   itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
        //          RecyclerView.LayoutParams.WRAP_CONTENT));

        VideoViewHolder holder = new VideoViewHolder(itemView);
        itemView.setTag(holder);

        //  itemView.setOnClickListener(mOnClickListener);

        return holder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof header) {
            //((header) holder).vName.setText(album_name);
        } else if (holder instanceof footer) {
            ((footer) holder).context = context;
        } else {
            Posts post = getItem(position);

            ((VideoViewHolder) holder).vTitle.setText(Html.fromHtml(post.getTitle()));
            ((VideoViewHolder) holder).vTitle.setClickable(true);
            String excerpt = post.getExcerpt();
            if (excerpt != null) {
                if (excerpt.length() >= 254) {
                    ((VideoViewHolder) holder).vExcerpt.setText(Html.fromHtml(excerpt.substring(0, 254) + " "));
                } else {
                    ((VideoViewHolder) holder).vExcerpt.setText(Html.fromHtml(post.getExcerpt() + " "));
                }
            }
            ((VideoViewHolder) holder).vExcerpt.setClickable(true);
            ((VideoViewHolder) holder).context = context;
            ((VideoViewHolder) holder).content = post.getContent();
            ((VideoViewHolder) holder).imageLink = post.postImg;
            ((VideoViewHolder) holder).title = post.Title;
            ((VideoViewHolder) holder).startdate = post.getStartDate();
            ((VideoViewHolder) holder).enddate = post.getEndDate();
            ((VideoViewHolder) holder).link = post.getPostURL();
            ((VideoViewHolder) holder).venue = post.getVenue();
            GlideApp.with(context)
                    .load(post.getPostThumb())
                    .thumbnail(1.0f)
                    .apply(fitCenterTransform())
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(false)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {

                            Log.e(TAG, "Load failed", e);

                            // You can also log the individual causes:
                            for (Throwable t : e.getRootCauses()) {
                                Log.e(TAG, "Caused by", t);
                            }
                            // Or, to log all root causes locally, you can use the built in helper method:
                            e.logRootCauses(TAG);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(((VideoViewHolder) holder).vImage);

        }
    }

    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        int itemCount = 0;
        if (questionList != null) {


            itemCount = questionList.size();
            if (mWithHeader)
                itemCount = itemCount + 1;
            if (mWithFooter)
                itemCount = itemCount + 1;
            // return itemCount;
        }
        return itemCount;
    }


    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private boolean isPositionFooter(int position) {
        return position == getItemCount() - 1;
    }

    public void setOnClickListener(View.OnClickListener lis) {
        mOnClickListener = lis;
    }

    protected Posts getItem(int position) {
        return mWithHeader ? questionList.get(position - 1) : questionList.get(position);
    }

    private int getItemPosition(int position) {
        return mWithHeader ? position - 1 : position;
    }

    public void setData(List<Posts> questionList) {
        this.questionList = questionList;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        protected ImageView vImage;
        protected TextView vName;
        protected TextView vDetails, vTitle, vExcerpt;
        protected Context context;
        String content;
        String imageLink;
        String title;
        String startdate;
        String enddate;
        String link;
        String venue;

        public VideoViewHolder(View v) {
            super(v);
            vImage = v.findViewById(R.id.blog_image);
            vTitle = v.findViewById(R.id.title);
            vExcerpt = v.findViewById(R.id.excerpt);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Item Clicked:" + vTitle.getText().toString());
                    Intent fullScreenIntent = new Intent(context.getApplicationContext(), EventFullScreen.class);
                    fullScreenIntent.putExtra("content", content);
                    fullScreenIntent.putExtra("image", imageLink);
                    fullScreenIntent.putExtra("title", title);
                    fullScreenIntent.putExtra("startdate", startdate);
                    fullScreenIntent.putExtra("enddate", enddate);
                    fullScreenIntent.putExtra("link", link);
                    fullScreenIntent.putExtra("venue", venue);
                    context.startActivity(fullScreenIntent);
                }
            });
        }

        public void clearAnimation() {
            this.clearAnimation();
        }


    }

    public class header extends RecyclerView.ViewHolder {


        protected Context context;
        protected int position;

        public header(View v) {
            super(v);


        }


    }


    public class footer extends RecyclerView.ViewHolder {


        protected Context context;
        protected int position;

        public footer(View v) {
            super(v);


        }


    }

}
