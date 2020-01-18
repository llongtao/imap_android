package com.test.hyxc.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.test.hyxc.R;
import com.test.hyxc.model.ActivityResource;
import com.test.hyxc.model.ActivityShow;
import com.test.hyxc.page.activity.ActivityDetailShow;
import com.test.hyxc.ui.GlideRoundTransform1;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import tools.AppContext;
import tools.DateUtils;

public class ActivityShowAdapter extends BaseAdapter {
    String url="";
    private Context mContext;
    private List<ActivityShow> mList;
    protected LayoutInflater inflater;
    public ActivityShowAdapter(Context context) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        url=((AppContext)context.getApplicationContext()).getOssConfig().getHost()+"/";
    }
    public void setList(List<ActivityShow> list) {
        mList = list;
    }
    @Override
    public int getCount() {
        return getListSize(mList);
    }
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            convertView = inflater.inflate(R.layout.item_bbs_activity_show, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ActivityShow activityShow=mList.get(position);
        List<ActivityResource> l=activityShow.getResourcesList();
        String coverImg=url;
        if(l!=null&&l.size()>0)
            coverImg=coverImg+l.get(0).getAct_resource_url();
        Glide.with(mContext)
                .load(coverImg)
                .transform(new GlideRoundTransform1(mContext,3))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(holder.iv_activity);
        Long actTime=activityShow.getAct_time()==null?0L:activityShow.getAct_time().getTime();
        String timestr=new SimpleDateFormat("MM/dd").format(actTime);
        timestr=timestr+"  "+DateUtils.dateToWeek(new SimpleDateFormat("yyyy-MM-dd").format(actTime));
        holder.tv_time.setText(timestr);
        String title=activityShow.getAct_title().length()>27?activityShow.getAct_title().substring(0,27)+"..":activityShow.getAct_title();
        holder.tv_title.setText(title);
        String address = "";
        if(activityShow.getAct_address()!=null && !"".equals(activityShow.getAct_address())){
            address = activityShow.getAct_address();
            if(address.length()>5)
                address  = address.substring(5)+"..";

        }
        holder.tv_address.setText(activityShow.getAct_address());
        //判断时间
        long start = activityShow.getAct_time().getTime();
        long end =activityShow.getAct_end_time().getTime();
        //Integer act_sign = activityShow.getSign();
        if(System.currentTimeMillis()<start){
            holder.tv_sign.setText("未开始");
            holder.tv_sign.setTextColor(Color.parseColor("#88A254C6"));
            holder.ll_sign.setBackgroundResource(R.drawable.imap_border_dark_radius);
            /*if(act_sign == 0 || act_sign==null || act_sign.equals(0)){ //未开始且未报名
                holder.tv_sign.setText("未开始");
                holder.ll_sign.setBackgroundResource(R.drawable.imap_border_dark_radius);
            }else{
                holder.tv_sign.setText("取消报名");
                holder.tv_sign.setTextSize(11);
            }*/
        }else if(System.currentTimeMillis()>=start&&System.currentTimeMillis()<end){
            holder.tv_sign.setText("进行中");
            holder.tv_sign.setTextColor(Color.parseColor("#ffffff"));
            holder.ll_sign.setBackgroundResource(R.drawable.imap_publish_button_1);
        }else if(System.currentTimeMillis()>=end){
            holder.tv_sign.setText("已结束");
            holder.tv_sign.setTextColor(Color.parseColor("#87889b"));
            //holder.tv_sign.setBackgroundResource(R.drawable.imap_publish_button_1);
            holder.ll_sign.setBackgroundResource(R.drawable.imap_border_dark_radius_cancel);
        }
        if(mList.get(position).getEnd()!=0){
            holder.ll_end.setVisibility(View.VISIBLE);
        }else{
            holder.ll_end.setVisibility(View.GONE);
        }
        holder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer act_id=mList.get(position).getAct_id();
                Intent intent =new Intent();
                intent.putExtra("act",activityShow);
                intent.setClass(mContext,ActivityDetailShow.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }
    private class ViewHolder {
        ImageView iv_activity;
        LinearLayout ll_end,ll_item,ll_sign;
        TextView tv_title,tv_time,tv_address,tv_sign,tv_end;
        public ViewHolder(View view) {
            ll_item=view.findViewById(R.id.ll_item);
            iv_activity=view.findViewById(R.id.iv_activity);
            tv_time=view.findViewById(R.id.tv_time);
            tv_title=view.findViewById(R.id.tv_title);
            ll_end=view.findViewById(R.id.ll_end);
            tv_sign=view.findViewById(R.id.tv_sign);
            ll_sign=view.findViewById(R.id.ll_sign);
            tv_address=view.findViewById(R.id.tv_address);
            tv_end=view.findViewById(R.id.tv_end);
        }
    }
    private int getListSize(List<ActivityShow> list) {
        if (list == null || list.size() == 0) {
            return 0;
        }
        return list.size();
    }
    public String getTimeStr(Long t) {
        Long ct=System.currentTimeMillis();
        Long t1=ct - t; //时间差
        //3个月前的直接显示时间
        if (t1 >= 90 * 24 * 60 * 60 * 1000L) {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date(t));
            /*return workShow.getWork_time().getYear()+"-"+
                    workShow.getWork_time().getMonth()+"-"+
                    workShow.getWork_time().getDay();*/
        } else if (t1 >= 30 * 24 * 60 * 60 * 1000L) {
            return t1 / (30 * 24 * 60 * 60 * 1000L) + "月前";
        } else if (t1 > 24 * 60 * 60 * 1000L) {
            return t1 / (24 * 60 * 60 * 1000L) + "天前";
        } else if (t1 > 60 * 60 * 1000L) {
            return t1 / (60 * 60 * 1000L) + "小时前";
        } else if (t1 > 60 * 1000L) {
            return t1 / (60 * 1000L) + "分钟前";
        } else if (t1 > 1000L) {
            return t1 / 1000L + "秒前";
        } else {
            return "刚刚";
        }
    }
}
