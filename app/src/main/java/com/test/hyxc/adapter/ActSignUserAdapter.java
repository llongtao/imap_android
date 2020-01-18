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
import com.test.hyxc.model.UserInfo;
import com.test.hyxc.model.UserInfo;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.ui.GlideRoundTransform;

import java.util.List;

import tools.AppContext;

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;

public class ActSignUserAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<UserInfo> listUserInfo;
    String url = "";
    Context context;

    public ActSignUserAdapter(Context context, List<UserInfo> listUserInfo) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        url = ((AppContext) context.getApplicationContext()).getOssConfig().getHost() + "/";
        this.listUserInfo = listUserInfo;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.imap_act_sign_user_item, parent, false);
            holder = new ViewHolder();
            holder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            holder.tv_sign = (TextView) convertView.findViewById(R.id.tv_sign);
            holder.iv_gender = (ImageView) convertView.findViewById(R.id.iv_gender);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.iv_head = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_nickname = (TextView) convertView.findViewById(R.id.tv_nickname);
            holder.tv_sign = (TextView) convertView.findViewById(R.id.tv_sign);
            holder.iv_gender = (ImageView) convertView.findViewById(R.id.iv_gender);
        }
        UserInfo userInfo = listUserInfo.get(position);
        holder.tv_nickname.setText(userInfo.getUser_nickname() == null ? "" : userInfo.getUser_nickname());
        holder.tv_sign.setText(userInfo.getUser_signature() == null ? "" : userInfo.getUser_signature());
        Glide.with(getApplicationContext())
                .load(url + userInfo.getUser_headimg())
                .transform(new CenterCrop(getApplicationContext()), new GlideRoundTransform(getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_head);
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("user_id", String.valueOf(userInfo.getUser_id()));
                intent.setClass(context, MeActivity.class);
                context.startActivity(intent);
            }
        });
        if(userInfo.getUser_gender().equals("1")){
           holder.iv_gender.setImageResource(R.mipmap.male);
        }else{
           holder.iv_gender.setImageResource(R.mipmap.female);
        }
        return convertView;
    }

    //设置view类
    private class ViewHolder {
        ImageView iv_head,iv_gender;
        TextView tv_nickname, tv_sign;
    }
}
