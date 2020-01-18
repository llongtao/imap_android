package com.test.hyxc.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.test.hyxc.R;
import com.test.hyxc.chat.activity.ChatActivity;
import com.test.hyxc.model.FriendDetail;
import com.test.hyxc.model.UserInfo;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.ui.GlideRoundTransform;

import java.util.ArrayList;
import java.util.List;

import tools.AppContext;
import tools.IMConfig;

public class UserInfoAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<UserInfo> listUserInfo=new ArrayList<>();
    Context context;
    String url="";
    public UserInfoAdapter(Context context, List<UserInfo> listUserInfo){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.listUserInfo=listUserInfo;
        url=((AppContext)context).getOssConfig().getHost()+"/";
    }
    @Override
    public int getCount() {
        return listUserInfo.size();
    }

    @Override
    public Object getItem(int position) {
        return listUserInfo.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.imap_user_info_item,parent,false);
            holder=new ViewHolder();
            holder.iv_logo=(ImageView) convertView.findViewById(R.id.iv_logo);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.iv_sex =(ImageView) convertView.findViewById(R.id.iv_sex);
            holder.ll_container =(LinearLayout) convertView.findViewById(R.id.ll_container);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
            holder.iv_logo=(ImageView) convertView.findViewById(R.id.iv_logo);
            holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
            holder.iv_sex =(ImageView) convertView.findViewById(R.id.iv_sex);
            holder.ll_container =(LinearLayout) convertView.findViewById(R.id.ll_container);
        }
        UserInfo userInfo=listUserInfo.get(position);
        String name=userInfo.getUser_nickname();
        String logo=url+userInfo.getUser_headimg();
        holder.tv_name.setText(name);
        Glide.with(context)
                .load(logo)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(holder.iv_logo);
        if(userInfo.getUser_gender().equals("1")){
            holder.iv_sex.setImageResource(R.mipmap.male_blue);
        }else{
            holder.iv_sex.setImageResource(R.mipmap.female_red);
        }
        holder.ll_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("user_id",String.valueOf(userInfo.getUser_id()));
                intent.setClass(context, MeActivity.class);
                context.startActivity(intent);
            }
        });
        return  convertView;
    }
    //设置view类
    private class  ViewHolder {
        ImageView iv_logo,iv_sex;
        TextView tv_name;
        LinearLayout ll_container;
    }
}
