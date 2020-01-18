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
import com.test.hyxc.model.Island;
import com.test.hyxc.model.IslandDetail;
import com.test.hyxc.model.MyFollow;
import com.test.hyxc.page.island.IslandShowActivity;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.ui.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import tools.AppContext;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

public class MyFollowAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<MyFollow> listFollow=new ArrayList<>();
    Context context;
    String url="";
    public MyFollowAdapter(Context context, List<MyFollow> listFollow){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.listFollow=listFollow;
        url=((AppContext)context).getOssConfig().getHost()+"/";
    }
    @Override
    public int getCount() {
        return listFollow.size();
    }

    @Override
    public Object getItem(int position) {
        return listFollow.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.imap_my_follow_item,parent,false);
            holder=new ViewHolder();
            holder.iv_logo=(ImageView) convertView.findViewById(R.id.iv_logo);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_getin=(TextView) convertView.findViewById(R.id.tv_getin);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
            holder.iv_logo=(ImageView) convertView.findViewById(R.id.iv_logo);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.tv_getin=(TextView)convertView.findViewById(R.id.tv_getin);
        }
        MyFollow myFollow=listFollow.get(position);
        String name="";
        String logo=url;
        String getIn="进入海岛";
        //0：海岛  1：用户
        if(myFollow.getFollowType()==0&&myFollow.getIsland()!=null){
          name=myFollow.getIsland().getIs_name();
          logo=url+myFollow.getIsland().getIs_logo();
        }else if(myFollow.getFollowType()==1&&myFollow.getFriendDetail()!=null){
            name=myFollow.getFriendDetail().getUser_nickname();
            logo=url+myFollow.getFriendDetail().getUser_headimg();
            getIn="个人主页";
        }
        holder.tv_name.setText(name);
        //海岛logo
        Glide.with(context)
                .load(logo)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(holder.iv_logo);
        holder.tv_getin.setText(getIn);
        //点击事件添加
        holder.tv_getin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyFollow follow=listFollow.get(position);
                if(follow.getFollowType()==0&&follow.getIsland()!=null){
                    Island island=follow.getIsland();
                    Intent intent=new Intent();
                    intent.putExtra("island",island);
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.setClass(context, IslandShowActivity.class);
                    context.startActivity(intent);
                }else if(follow.getFollowType()==1&&follow.getFriendDetail()!=null){
                    Intent intent=new Intent();
                    intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("user_id",String.valueOf(follow.getFriendDetail().getUser_id()));
                    intent.setClass(context, MeActivity.class);
                    context.startActivity(intent);
                }
            }
        });
        return  convertView;
    }
    //设置view类
    private class  ViewHolder {
        ImageView iv_logo;
        TextView tv_name;
        TextView tv_getin;
    }
}
