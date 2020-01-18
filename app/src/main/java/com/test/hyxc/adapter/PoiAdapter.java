package com.test.hyxc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.mapapi.search.core.PoiInfo;
import com.test.hyxc.R;

import java.util.List;

public class PoiAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<PoiInfo> listPoi;
    private List<Boolean> isSelected;

    public PoiAdapter (Context context,List<PoiInfo> listPoi,List<Boolean> isSelected){
        inflater=LayoutInflater.from(context);
        this.listPoi=listPoi;
        this.isSelected=isSelected;
    }
    @Override
    public int getCount() {
        return listPoi.size();
    }

    @Override
    public Object getItem(int position) {
        return listPoi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.imap_poi_item,parent,false);
            holder=new ViewHolder();
            holder.tv_poi_name=(TextView) convertView.findViewById(R.id.tv_poi_name);
            holder.tv_poi_detail=(TextView)convertView.findViewById(R.id.tv_poi_detail);
            holder.iv_selected=(ImageView) convertView.findViewById(R.id.iv_selected);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
            holder.tv_poi_name=(TextView)convertView.findViewById(R.id.tv_poi_name);
            holder.tv_poi_detail=(TextView)convertView.findViewById(R.id.tv_poi_detail);
            holder.iv_selected=(ImageView)convertView.findViewById(R.id.iv_selected);
        }
        PoiInfo poiInfo=listPoi.get(position);
        holder.tv_poi_name.setText(poiInfo.getName());
        holder.tv_poi_detail.setText(poiInfo.getAddress());
        if(isSelected.get(position)==true){
            holder.iv_selected.setWillNotDraw(false);
            holder.iv_selected.setImageResource(R.mipmap.imap_poi_selected);
        }else{
            holder.iv_selected.setWillNotDraw(true);
        }
        return  convertView;
    }
    //设置view类
    private class  ViewHolder {
        TextView tv_poi_name;
        TextView tv_poi_detail;
        ImageView iv_selected;
    }
}
