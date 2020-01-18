package com.test.hyxc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.test.hyxc.R;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.ui.GlideRoundTransform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.AppContext;
import tools.DipPxUtils;
import tools.OssConfig;

public class WorkShowAdapter extends RecyclerView.Adapter<WorkShowAdapter.WorkShowViewHolder> implements View.OnClickListener {
    private Context mContext;
    /**数据集合*/
    private List<WorkShow> data;
    //记录高度
    public  Map <Integer,Integer> map=new HashMap<>();
    private OnItemClickListener mOnItemClickListener;
    private int position;
    public WorkShowAdapter(ArrayList<WorkShow> data, Context context) {
        setHasStableIds(true);
        this.data = data;
        this.mContext = context;
    }
    @Override
    public WorkShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.workshow_item, parent, false);
        return new WorkShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WorkShowViewHolder holder, final int position) {
        this.position=position;
        WorkShow workShow= data.get(position);
        holder.itemView.setTag(position);
        holder.ll_zan.setTag(position);
        OssConfig ossConfig=((AppContext)mContext.getApplicationContext()).getOssConfig();
        String url=ossConfig.getHost()+"/";
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                //.showImageOnLoading(R.drawable.imap_blank)
                //.showImageForEmptyUri(R.drawable.imap_blank)
                //.showImageOnFail(R.drawable.imap_blank)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(50))// 设置成圆角图片
                .build();
        DisplayImageOptions options1 = new DisplayImageOptions.Builder()
               // .showImageOnLoading(R.drawable.imap_blank)
                //.showImageForEmptyUri(R.drawable.imap_blank)
                //.showImageOnFail(R.drawable.imap_blank)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .displayer(new RoundedBitmapDisplayer(0))
                .build();
        Glide.with(mContext)
                .load(url+workShow.getUser_headimg())
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.error)
                .dontAnimate()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_user);
        //获取图片显示在ImageView后的宽高
        String workshowurl="";
        if(workShow.getWork_type()==0) {
            workshowurl=workShow.getWork_show_image().trim();
        }else if(workShow.getWork_type()==1){
            //视频
           workshowurl=workShow.getWork_show_image()+"?x-oss-process=video/snapshot,t_1000,f_jpg,w_0,h_0,m_fast";
        }
        Integer imgHeight=map.get(position);
        if(imgHeight!=null&&imgHeight!=0){
            DisplayMetrics metric=new DisplayMetrics();
            ((AppContext) mContext.getApplicationContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
            int pxWidth=metric.widthPixels / 2- DipPxUtils.dip2px(mContext,6.7f); //剪掉左右两边居
            ViewGroup.LayoutParams lp=holder.WorkShowImage.getLayoutParams();
            lp.height=imgHeight;
            lp.width=pxWidth;
            holder.WorkShowImage.setLayoutParams(lp);
            ViewGroup.LayoutParams lp1=holder.ll_all.getLayoutParams();
            lp1.height=imgHeight+280;
            holder.ll_all.setLayoutParams(lp1);
            Glide.with(mContext).load(url + workshowurl)
                    .asBitmap()
                    .error(R.mipmap.error)
                    .placeholder( R.mipmap.loading) //加载成功前显示的图片
                    .thumbnail(0.1f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.WorkShowImage);
        } else {
            Glide.with(mContext).load(url + workshowurl)
                    .asBitmap()
                    .placeholder( R.mipmap.loading) //加载成功前显示的图片
                    .error(R.mipmap.error)
                    .thumbnail(0.1f)
                    .listener(new RequestListener<String, Bitmap>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }
                        @Override
                        public boolean onResourceReady(Bitmap bitmap, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            int width=bitmap.getWidth();
                            int height=bitmap.getHeight();
                            DisplayMetrics metric=new DisplayMetrics();
                            ((AppContext) mContext.getApplicationContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
                            int pxWidth=metric.widthPixels / 2- DipPxUtils.dip2px(mContext,6.7f);
                            float ratio=(float) pxWidth / (float) width;
                            float imageHeight=height * ratio;
                            LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams) holder.WorkShowImage.getLayoutParams();
                            lp.width=pxWidth;
                            lp.height=(int) imageHeight;
                            if ((float) lp.height / (float) lp.width > 1.5f) {
                                lp.height=(int) (lp.width * 1.5);
                            }
                            //////////
                            map.put(position, lp.height);
                            holder.WorkShowImage.setLayoutParams(lp);
                            ViewGroup.LayoutParams lp1=holder.ll_all.getLayoutParams();
                            lp1.height=lp.height+280;
                            holder.ll_all.setLayoutParams(lp1);
                            return false;
                        }
                    })
                    // .override(400, 700)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.WorkShowImage);
        }

        if(workShow.getWork_island_name()!=null&&!"".equals(workShow.getWork_island_name())){
            holder.tv_island.setText(workShow.getWork_island_name());
        }else {
            holder.tv_island.setVisibility(View.GONE);
        }
        holder.tv_user.setText(workShow.getUser_nickname());
        String worktext="";
        if(workShow.getWork_text().length()>24){
            worktext=workShow.getWork_text().substring(0,24)+"...";
        }else{
            worktext=workShow.getWork_text();
        }
        holder.tv_content.setText(worktext);
        //视频作品
        if(workShow.getWork_type()==1){
            holder.iv_vedio.setVisibility(View.VISIBLE);
            holder.iv_vedio.setImageResource(R.mipmap.video);
            holder.iv_vedio.setAlpha((float) 0.9);
        }else{
            holder.iv_vedio.setVisibility(View.GONE);
        }
        //点赞数
        String like_count="";
        if(workShow.getWork_like_count()==0){
            like_count="";
        } else if(workShow.getWork_like_count()>10000){
            like_count=workShow.getWork_like_count()/10000+"万";
        }
        else
            like_count=String.valueOf(workShow.getWork_like_count());
            holder.tv_zan.setText(like_count);
        //点赞状态
        if(workShow.getLikeState()==null||workShow.getLikeState()==0){
            holder.iv_zan.setImageResource(R.mipmap.zan);
        }else if(workShow.getLikeState()==1){
            holder.iv_zan.setImageResource(R.mipmap.zaned);
        }
        //分享
    }
    public  void AddHeaderItem(List<WorkShow> items){
        data.addAll(0,items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(List<WorkShow> items){
        data.addAll(items);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    //这里能防止位置错乱
    @Override
    public long getItemId(int position){
        return position;
    }

    class WorkShowViewHolder extends RecyclerView.ViewHolder {
        ImageView WorkShowImage;
        ImageView iv_user;
        TextView tv_user;
        TextView tv_content;
        TextView tv_island;
        ImageView iv_vedio;
        LinearLayout ll_zan;
        ImageView iv_zan;
        TextView tv_zan;
        RelativeLayout rl_zan_t;
        LinearLayout ll_all;
        public WorkShowViewHolder(View itemView) {
            super(itemView);
            WorkShowImage = itemView.findViewById(R.id.WorkShowImg);
            ll_zan=itemView.findViewById(R.id.ll_zan);
            iv_user=itemView.findViewById(R.id.iv_user);
            tv_user=itemView.findViewById(R.id.tv_user);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_island= itemView.findViewById(R.id.tv_island);
            iv_vedio=itemView.findViewById(R.id.iv_vedio);
            iv_zan=itemView.findViewById(R.id.iv_zan);
            tv_zan=itemView.findViewById(R.id.tv_zan);
            ll_all=itemView.findViewById(R.id.ll_item);
            itemView.setOnClickListener(WorkShowAdapter.this);
            tv_zan.setOnClickListener(WorkShowAdapter.this);
            ll_zan.setOnClickListener(WorkShowAdapter.this);
        }
    }

    public List<WorkShow> getData() {
        return data;
    }

    public void setData(List<WorkShow> data) {
        this.data = data;
    }
    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener  {
        void onItemClick(View v, ViewName viewName, int position);
        void onItemLongClick(View v);
    }
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener  listener) {
        this.mOnItemClickListener  = listener;
    }
    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.ll_zan:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }






}