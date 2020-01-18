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
import com.test.hyxc.model.Island;
import com.test.hyxc.ui.GlideRoundTransform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tools.AppContext;
import tools.DipPxUtils;
import tools.LocationUtil;
import tools.OssConfig;

public class IslandShowAdapter extends RecyclerView.Adapter<IslandShowAdapter.IslandViewHolder> implements View.OnClickListener {
    private Context mContext;
    /**数据集合*/
    private List<Island> data;
    private OnItemClickListener mOnItemClickListener;
    private int position;
    String url="";
    private  double lng,lat;
    public IslandShowAdapter(ArrayList<Island> data, Context context) {
        setHasStableIds(true);
        this.data = data;
        this.mContext = context;
        url=((AppContext)context.getApplicationContext()).getOssConfig().getHost()+"/";
        lng=((AppContext)context.getApplicationContext()).getLongitude();
        lat=((AppContext) context.getApplicationContext()).getLatitude();
    }
    @Override
    public IslandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.island_item, parent, false);
        return new IslandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final IslandViewHolder holder, final int position) {
        this.position=position;
        Island island= data.get(position);
        holder.itemView.setTag(position);
        String desc =island.getIs_describe();
        if(desc.length()>38){
            desc=desc.substring(0,38)+"...";
        }
        holder.tv_desc.setText(desc);
        holder.tv_people.setText(island.getIs_people_current()+"");
        holder.tv_name.setText(island.getIs_name());
        Glide.with(mContext)
                .load(url+island.getIs_logo())
                .transform(new CenterCrop(mContext),new GlideRoundTransform(mContext))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_head);
        //人数
        holder.tv_people.setText(String.valueOf(island.getIs_people_current()));
        //距离
        double distance=LocationUtil.getDistance(Double.parseDouble(island.getIs_longitude()),Double.parseDouble(island.getIs_latitude()),lng,lat);
        holder.tv_location.setText(Math.round(distance)*1/1000+"Km");


    }
    public  void AddHeaderItem(List<Island> items){
        data.addAll(0,items);
        notifyDataSetChanged();
    }

    public void AddFooterItem(List<Island> items){
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

    class IslandViewHolder extends RecyclerView.ViewHolder {
         ImageView iv_head,iv_people,iv_location;
         TextView tv_people,tv_location,tv_desc,tv_name;
        public IslandViewHolder(View itemView) {
            super(itemView);
            iv_head=itemView.findViewById(R.id.iv_head);
            iv_people=itemView.findViewById(R.id.iv_people);
            iv_location=itemView.findViewById(R.id.iv_location);
            tv_people=itemView.findViewById(R.id.tv_people);
            tv_location=itemView.findViewById(R.id.tv_location);
            tv_desc=itemView.findViewById(R.id.tv_desc);
            tv_name=itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(IslandShowAdapter.this);
        }
    }

    public List<Island> getData() {
        return data;
    }

    public void setData(List<Island> data) {
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
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }

}
