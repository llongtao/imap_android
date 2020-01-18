package com.test.hyxc.adapter;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.test.hyxc.R;
import com.test.hyxc.model.ActivityResource;
import com.test.hyxc.model.ActivityShow;
import com.test.hyxc.transformations.MyRoundCornersTransformation;
import com.test.hyxc.ui.GlideRoundTransform;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tools.AppContext;
import tools.OssConfig;
import tools.SystemHelper;

;

/**
 * Created by WWW on 2019/1/26.
 * 本校活动轮播适配器
 */

public class ActivityUniAdapter extends RecyclerView.Adapter<ActivityUniAdapter.ActivityUniViewHolder> {
    /**上下文*/
    private Context mContext;
    /**数据集合*/
    private List<ActivityShow> data;
    private OnItemClickListener listener;
    public ActivityUniAdapter(ArrayList<ActivityShow> data, Context context) {
        this.data = data;
        this.mContext = context;
    }
    @Override
    public ActivityUniViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imap_item_uni_activity, parent, false);
        return new ActivityUniViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ActivityUniViewHolder holder, final int position) {
        OssConfig ossConfig=((AppContext)mContext.getApplicationContext()).getOssConfig();
        String url=ossConfig.getHost()+"/";
        if(data.size()>0) {
            List<ActivityResource> activityResourceList = data.get(position % data.size()).getResourcesList();
            if(activityResourceList!=null && activityResourceList.size()>0) {
                String lunboImg = activityResourceList.get(0).getAct_resource_url();
                //该方案不合适，因为图片尺寸有大有小
                Glide.with(mContext)
                        .load(url + lunboImg)
                        .placeholder(R.mipmap.error)
                        .error(R.mipmap.error)
                        .centerCrop()
                        //默认淡入淡出动画
                        .crossFade()
                        //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                        .skipMemoryCache(false)
                        //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        //设置图片加载的优先级
                        .priority(Priority.HIGH)
                        .transform(new MyRoundCornersTransformation(mContext, SystemHelper.dip2px(mContext, 4),
                                MyRoundCornersTransformation.CornerType.TOP))
                        .into(holder.item_image);
            }
        }
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
        String title = data.get(position).getAct_title();
        if(title.length()>8){
            title= title.substring(0,8)+"..";
        }
        holder.tv_activity_name.setText(title);
        //时间显示
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
        Date date = new Date(data.get(position).getAct_time().getTime());
        String startStr = sdf.format(date);
        Date date1 = new Date(data.get(position).getAct_end_time().getTime());
        String endStr = sdf.format(date1);
        holder.tv_activity_time.setText(startStr + "-" + endStr);
        //海岛头像
        Glide.with(mContext)
                .load(url + data.get(position).getAct_island_logo())
                .placeholder(R.mipmap.error)
                .error(R.mipmap.error)
                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 17))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(holder.iv_activity_island);
        holder.tv_island_name.setText(data.get(position).getAct_island_name());
        holder.tv_activity_sign_num.setText(data.get(position).getAct_people_count()+"");
        holder.tv_uni_name.setText(data.get(position).getAct_university_name());
    }
    public List<ActivityShow> getData() {
        return data;
    }
    public void setData(List<ActivityShow> data) {
        this.data = data;
    }
    @Override
    public int getItemCount() {
       // return  Integer.MAX_VALUE;
        return  data.size();
    }

    public class ActivityUniViewHolder extends RecyclerView.ViewHolder {
        private ImageView item_image;
        private TextView tv_activity_name,tv_activity_time,tv_island_name,tv_activity_sign_num,tv_uni_name;
        ImageView iv_activity_island;
        public ActivityUniViewHolder(View itemView) {
            super(itemView);
            item_image=itemView.findViewById(R.id.item_image);
            tv_activity_name= itemView.findViewById(R.id.tv_activity_name);
            tv_activity_time = itemView.findViewById(R.id.tv_activity_time);
            iv_activity_island = itemView.findViewById(R.id.iv_activity_island);
            tv_island_name = itemView.findViewById(R.id.tv_island_name);
            tv_activity_sign_num = itemView.findViewById(R.id.tv_activity_sign_num);
            tv_uni_name = itemView.findViewById(R.id.tv_uni_name);
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