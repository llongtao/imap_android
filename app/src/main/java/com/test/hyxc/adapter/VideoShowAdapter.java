package com.test.hyxc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.test.hyxc.R;
import com.test.hyxc.model.UserWorkDiscussShow;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.workshow.ImageShowActivity;
import com.test.hyxc.ui.GlideRoundTransform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.DipPxUtils;
import tools.NetUtils;
import tools.OssConfig;

public class VideoShowAdapter extends RecyclerView.Adapter<VideoShowAdapter.VideoShowViewHolder> implements View.OnClickListener {
    private Context context;
    private List<WorkShow> data;
    private WorkShow workShow;
    private String url;
    private OnItemClickListener mOnItemClickListener;//声明自定义的接口
    private int position;
    MediaController mediaController;
    private VideoShowViewHolder videoShowViewHolder;
    private DisplayMetrics metric=new DisplayMetrics();
    // ChangeHeight changeHeight;
    public VideoShowAdapter(ArrayList<WorkShow> data, Context context) {
        setHasStableIds(true);
        this.data=data;
        this.context=context;
        OssConfig ossConfig=((AppContext) this.context.getApplicationContext()).getOssConfig();
        this.url=ossConfig.getHost() + "/";
        ((AppContext) context.getApplicationContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
    }

    @Override
    public VideoShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.imap_video_show_item, parent, false);
        videoShowViewHolder=new VideoShowViewHolder(view);
        return videoShowViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoShowViewHolder holder, int position) {
        this.position=position;
        workShow=data.get(position);
        Long t=workShow.getWork_time().getTime();
        holder.vv.setTag(position);
        holder.itemView.setTag(position);
        mediaController=new MediaController(context);
        mediaController.setVisibility(View.GONE);
        holder.vv.setMediaController(mediaController);
        //播放完成回调
        holder.vv.setOnCompletionListener( new MyPlayerOnCompletionListener(holder));
        //设置视频路径
        //videoView.setVideoURI(uri);
        holder.vv.setVideoPath(url+workShow.getWork_show_image());
        //开始播放视频
        holder.vv.start();
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {
        public VideoShowViewHolder holder;
        public MyPlayerOnCompletionListener(VideoShowViewHolder holder){
            this.holder=holder;
        }
        @Override
        public void onCompletion(MediaPlayer mp) {
            holder.vv.start();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    //这里能防止位置错乱
    @Override
    public long getItemId(int position) {
        return position;
    }

    public class VideoShowViewHolder extends RecyclerView.ViewHolder {
        VideoView vv;
        public VideoShowViewHolder(View itemView) {
            super(itemView);
            vv=itemView.findViewById(R.id.vv);
            vv.setOnClickListener(VideoShowAdapter.this);
            itemView.setOnClickListener(VideoShowAdapter.this);
        }
    }
    public List<WorkShow> getData() {
        return data;
    }

    public void setData(List<WorkShow> data) {
        this.data=data;
    }

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v, ViewName viewName, int position);

        void onItemLongClick(View v);
    }
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener=listener;
    }

    @Override
    public void onClick(View v) {
        int position=(int) v.getTag();
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }
}
