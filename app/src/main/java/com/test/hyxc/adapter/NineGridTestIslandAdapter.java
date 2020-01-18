package com.test.hyxc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.test.hyxc.R;
import com.test.hyxc.model.WorkResource;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.ui.GlideRoundTransform;
import com.test.hyxc.view.NineGridTestLayout;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import tools.AppContext;

public class NineGridTestIslandAdapter extends BaseAdapter implements View.OnClickListener {
    String url="";
    private Context mContext;
    private List<WorkShow> mList;
    protected LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;
    boolean tooLong=false;
    boolean spread =false;
    //那种页面
    String pageType="myWork"; //默认是我的作品页面 myWork  myFollow islandWork 三种
    //标志变量 表示当前浏览的用户 在海岛的关系 -1 ，0 无关 1：普通 2：管理员 3：岛主
    int relation=-1;
    int user_id = -1;
    public NineGridTestIslandAdapter(Context context, String pageType, int relation) {
        mContext = context;
        inflater = LayoutInflater.from(context);
        url=((AppContext)context.getApplicationContext()).getOssConfig().getHost()+"/";
        this.pageType = pageType;
        this.relation=relation;
        user_id=Integer.parseInt(((AppContext)mContext.getApplicationContext()).getUserId());
    }
    public void setList(List<WorkShow> list) {
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
            convertView = inflater.inflate(R.layout.item_bbs_nine_grid_island, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.layout.setIsShowAll(true);
        WorkShow workShow=mList.get(position);
        List<WorkResource> l=workShow.getResourcesList();
        holder.layout.setWorkShow(workShow);
        Glide.with(mContext)
                .load(url+workShow.getUser_headimg())
                .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 50))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(holder.iv_head1);
        holder.tv_nickname.setText(workShow.getUser_nickname());
        /*if(workShow.getWork_island()==-1){
            //头像
            Glide.with(mContext)
                    .load(url+workShow.getUser_headimg())
                    .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 50))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .crossFade()
                    .into(holder.iv_head1);
            holder.tv_nickname.setText(workShow.getUser_nickname());
        }else{
            Glide.with(mContext)
                    .load(url+workShow.getWork_island_logo())
                    .transform(new CenterCrop(mContext), new GlideRoundTransform(mContext, 50))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .crossFade()
                    .into(holder.iv_head1);
            holder.tv_nickname.setText(workShow.getWork_island_name());
        }*/
        Long t=workShow.getWork_time().getTime();
        String timestr=getTimeStr(t);
        holder.tv_time.setText(timestr);
        holder.tv_content.setText(workShow.getWork_text());
       /* String content=workShow.getWork_text();
        //内容处理
        if (content.length() > 156) {
            tooLong=true;
            Spannable strcontent=new SpannableString(content.substring(0, 156) + "..展开");
            strcontent.setSpan(new StyleSpan(Typeface.BOLD), 156, 160, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            strcontent.setSpan(new AbsoluteSizeSpan(46), 156, 160, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tv_content.setText(strcontent);
        } else {
            holder.tv_content.setText(content);
        }
        //内容动态改变多少
        ViewHolder finalHolder=holder;
        holder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tooLong) {
                    if (!spread) {
                        spread=!spread;
                        Spannable strcontent=new SpannableString(content + "..收起");
                        strcontent.setSpan(new StyleSpan(Typeface.BOLD), content.length(), content.length() + 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        strcontent.setSpan(new AbsoluteSizeSpan(46), content.length(), content.length() + 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        finalHolder.tv_content.setText(strcontent);
                    } else {
                        spread=!spread;
                        Spannable strcontent=new SpannableString(content.substring(0, 156) + "..展开");
                        strcontent.setSpan(new StyleSpan(Typeface.BOLD), 156, 160, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        strcontent.setSpan(new AbsoluteSizeSpan(46), 156, 160, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        finalHolder.tv_content.setText(strcontent);
                    }
                }
            }
        });
*/


        /*holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               *//* if(workShow.getWork_type()==0) {
                    Intent intent=new Intent(mContext, ImageShowActivity.class);
                    intent.putExtra("workshow", workShow);
                    mContext.startActivity(intent);
                }else if(workShow.getWork_type()==1){
                       *//**//* Intent intent=new Intent(getActivity(), VideoShowActivity.class);
                        intent.putExtra("workshow", workShow);
                        startActivity(intent);*//**//*
                    Intent intent=new Intent(mContext, Page2Activity.class);
                    intent.putExtra("workshow", workShow);
                    mContext.startActivity(intent);
                }*//*
            }
        });*/
        if(mList.get(position).getEnd()!=0){
            holder.tv_end.setVisibility(View.VISIBLE);
        }else{
            holder.tv_end.setVisibility(View.GONE);
        }
        //点击事件
        holder.iv_more.setTag(position);
        holder.iv_more.setOnClickListener(NineGridTestIslandAdapter.this);
        if(pageType.equals("myWork") && relation>=2){
            holder.iv_more.setVisibility(View.VISIBLE);
        }
        if(pageType.equals("islandWork")){
            if(relation==1){ //普通用户 只能移除自己的
                if(workShow.getWork_author()==user_id){
                    holder.iv_more.setVisibility(View.VISIBLE);
                }
            }
            if(relation==2){ //管理员 可以移除 普通用户和自己的
                if(workShow.getUser_island_privilege()<=1 || workShow.getWork_author()==user_id){
                     holder.iv_more.setVisibility(View.VISIBLE);
                }
            }
            if(relation==3){//岛主
                holder.iv_more.setVisibility(View.VISIBLE);
            }
        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_nickname,tv_time,tv_content,tv_end;
        NineGridTestLayout layout;
        ImageView iv_head1;
        ImageView iv_more;
        public ViewHolder(View view) {
            layout = (NineGridTestLayout) view.findViewById(R.id.layout_nine_grid);
            tv_nickname=view.findViewById(R.id.tv_nickname);
            tv_time=view.findViewById(R.id.tv_time);
            tv_content=view.findViewById(R.id.tv_content);
            iv_head1=view.findViewById(R.id.iv_head1);
            tv_end=view.findViewById(R.id.tv_end);
            iv_more =view.findViewById(R.id.iv_more);
        }
    }
    private int getListSize(List<WorkShow> list) {
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
                case R.id.iv_more:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                case R.id.tv_delete:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE,position);
                    break;
                default:
                    break;
            }
        }
    }
}
