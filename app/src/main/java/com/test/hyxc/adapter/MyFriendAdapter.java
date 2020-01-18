package com.test.hyxc.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.test.hyxc.model.FriendDetail;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.ui.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import com.test.hyxc.chat.activity.ChatActivity;
import tools.AppContext;
import tools.IMConfig;

public class MyFriendAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<FriendDetail> listFriendDetail=new ArrayList<>();
    Context context;
    String url="";
    public MyFriendAdapter(Context context, List<FriendDetail> listFriendDetail){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.listFriendDetail=listFriendDetail;
        url=((AppContext)context).getOssConfig().getHost()+"/";
    }
    @Override
    public int getCount() {
        return listFriendDetail.size();
    }

    @Override
    public Object getItem(int position) {
        return listFriendDetail.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.imap_my_friend_item,parent,false);
            holder=new ViewHolder();
            holder.iv_logo=(ImageView) convertView.findViewById(R.id.iv_logo);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_news=(TextView) convertView.findViewById(R.id.tv_news);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
            holder.iv_logo=(ImageView) convertView.findViewById(R.id.iv_logo);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_news=(TextView)convertView.findViewById(R.id.tv_news);
        }
        FriendDetail friendDetail=listFriendDetail.get(position);
        String name=friendDetail.getUser_nickname();
        String logo=url+friendDetail.getUser_headimg();
        holder.tv_name.setText(name);
        //海岛logo
        Glide.with(context)
                .load(logo)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(holder.iv_logo);
        holder.iv_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("user_id",String.valueOf(friendDetail.getUser_id()));
                intent.setClass(context, MeActivity.class);
                context.startActivity(intent);
            }
        });
        //点击事件添加
        holder.tv_news.setText("发消息");
        holder.tv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(AppContext.CONV_TITLE, listFriendDetail.get(position).getUser_nickname());
                String targetId = listFriendDetail.get(position).getUser_name();
                intent.putExtra(AppContext.TARGET_ID, targetId);
                intent.putExtra(AppContext.TARGET_APP_KEY, IMConfig.appkey);
                intent.putExtra(AppContext.DRAFT, "");
                intent.setClass(context, ChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                context.startActivity(intent);
            }
        });
        return  convertView;
    }
    //设置view类
    private class  ViewHolder {
        ImageView iv_logo;
        TextView tv_name;
        TextView tv_news;
    }
}
