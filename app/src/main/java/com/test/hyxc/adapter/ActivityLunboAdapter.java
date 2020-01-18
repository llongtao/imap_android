package com.test.hyxc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.hyxc.R;
import com.test.hyxc.model.ActivityResource;
import com.test.hyxc.model.ActivityShow;

import java.util.ArrayList;
import java.util.List;

import tools.AppContext;
import tools.OssConfig;

/**
 * 广告效果的轮播
 */
public class ActivityLunboAdapter extends RecyclerView.Adapter<ActivityLunboAdapter.ActivityLunboViewHolder> {
    /**上下文*/
    private Context mContext;
    /**数据集合*/
    private List<ActivityShow> data;
    private OnItemClickListener listener;
    public ActivityLunboAdapter(ArrayList<ActivityShow> data, Context context) {
        this.data = data;
        this.mContext = context;
    }
    @Override
    public ActivityLunboViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imap_item_lunbo, parent, false);
        return new ActivityLunboViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ActivityLunboViewHolder holder, final int position) {
        OssConfig ossConfig=((AppContext)mContext.getApplicationContext()).getOssConfig();
        String url=ossConfig.getHost()+"/";
        if(data !=null && data.size()>0) {
            List<ActivityResource> activityResourceList = data.get(position % data.size()).getResourcesList();
            if(activityResourceList!=null && activityResourceList.size()>0) {
                String lunboImg = activityResourceList.get(0).getAct_resource_url();
                Glide.with(mContext)
                        .load(url  + lunboImg)
                        // .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext))//圆角图片
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        //.placeholder(R.drawable.shenpi_jiazai)
                        .dontAnimate()
                        .crossFade()
                        .into(holder.item_image);
            }
        }
        //
        holder.item_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

        holder.item_image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(position);
                }
                return true;
            }
        });

    }
    public List<ActivityShow> getData() {
        return data;
    }
    public void setData(List<ActivityShow> data) {
        this.data = data;
    }
    @Override
    public int getItemCount() {
        return  Integer.MAX_VALUE;
    }

    public class ActivityLunboViewHolder extends RecyclerView.ViewHolder {
        private  ImageView item_image;

        public ActivityLunboViewHolder(View itemView) {
            super(itemView);
            item_image=itemView.findViewById(R.id.item_image);
        }
    }

    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }
    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
    public interface OnItemLongClickListener {
        void onClick(int position);
    }
    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }


}