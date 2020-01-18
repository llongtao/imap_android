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
import com.test.hyxc.model.IslandResidentUserInfo;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.ui.GlideRoundTransform;

import java.util.List;

import tools.AppContext;

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;

public class IslandUsersAskingAdapter extends BaseAdapter implements View.OnClickListener  {
    private LayoutInflater inflater;
    private List<IslandResidentUserInfo> listUserInfo;
    String url="";
    Context context;

    private OnItemClickListener mOnItemClickListener;
    public IslandUsersAskingAdapter(Context context, List<IslandResidentUserInfo> listUserInfo){
        inflater=LayoutInflater.from(context);
        this.context=context;
        url=((AppContext)context.getApplicationContext()).getOssConfig().getHost()+"/";
        this.listUserInfo=listUserInfo;
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
            convertView=inflater.inflate(R.layout.imap_island_user_item_asking,parent,false);
            holder=new ViewHolder();
            holder.iv_head=(ImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_nickname=(TextView)convertView.findViewById(R.id.tv_nickname);
            holder.tv_sign=(TextView) convertView.findViewById(R.id.tv_sign);
            holder.tv_privilege=(TextView)convertView.findViewById(R.id.tv_privilege);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
            holder.iv_head=(ImageView) convertView.findViewById(R.id.iv_head);
            holder.tv_nickname=(TextView)convertView.findViewById(R.id.tv_nickname);
            holder.tv_sign=(TextView) convertView.findViewById(R.id.tv_sign);
            holder.tv_privilege=(TextView)convertView.findViewById(R.id.tv_privilege);
        }
        IslandResidentUserInfo userInfo=listUserInfo.get(position);
        holder.tv_nickname.setText(userInfo.getUser_nickname()==null?"":userInfo.getUser_nickname());
        holder.tv_sign.setText(userInfo.getUser_signature()==null?"":userInfo.getUser_signature());
        Glide.with(getApplicationContext())
                .load(url + userInfo.getUser_headimg())
                .transform(new CenterCrop(getApplicationContext()), new GlideRoundTransform(getApplicationContext()))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.iv_head);
        holder.tv_privilege.setText(userPrivilege(userInfo.getRes_privilege()));
        convertView.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.iv_more:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    break;
            }
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
    //设置view类
    private class  ViewHolder {
        ImageView iv_head;
        TextView tv_nickname,tv_sign,tv_privilege;
    }
    //根据用户privilege 显示不同信息
    public String userPrivilege(int privilege){
        String priStr="";
        switch (privilege){
            case 3:
                priStr="岛主";
                break;
            case 2:
                priStr="管家";
                break;
            case 1:
                priStr="平民";
                break;
            default:
                priStr="平民";
                break;
        }
        return priStr;
    }
}
