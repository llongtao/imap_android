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
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.IslandDetail;
import com.test.hyxc.page.island.IslandShowActivity;
import com.test.hyxc.ui.GlideRoundTransform;
import java.util.ArrayList;
import java.util.List;
import tools.AppContext;
public class MyIslandAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<IslandDetail> listIsland=new ArrayList<>();
    Context context;
    String url="";
    public MyIslandAdapter(Context context, List<IslandDetail> listIsland){
        inflater=LayoutInflater.from(context);
        this.context=context;
        this.listIsland=listIsland;
        url=((AppContext)context).getOssConfig().getHost()+"/";
    }
    @Override
    public int getCount() {
        return listIsland.size();
    }

    @Override
    public Object getItem(int position) {
        return listIsland.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder= null;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.imap_my_island_item,parent,false);
            holder=new ViewHolder();
            holder.iv_island_logo=(ImageView) convertView.findViewById(R.id.iv_island_logo);
            holder.tv_island_name=(TextView)convertView.findViewById(R.id.tv_island_name);
            holder.tv_getin_island=(TextView) convertView.findViewById(R.id.tv_getin_island);
            convertView.setTag(holder);
        }else {
            holder=(ViewHolder) convertView.getTag();
            holder.iv_island_logo=(ImageView) convertView.findViewById(R.id.iv_island_logo);
            holder.tv_island_name=(TextView)convertView.findViewById(R.id.tv_island_name);
            holder.tv_getin_island=(TextView)convertView.findViewById(R.id.tv_getin_island);
        }
        IslandDetail islandDetail=listIsland.get(position);
        holder.tv_island_name.setText(islandDetail.getRes_island_name());
        //海岛logo
        Glide.with(context)
                .load(url+islandDetail.getIs_logo())
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(holder.iv_island_logo);
        //点击事件添加
        holder.tv_getin_island.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Island island = new Island();
                    island.setIs_id(islandDetail.getRes_island());
                    island.setIs_name(islandDetail.getRes_island_name());
                    island.setIs_category_id(islandDetail.getIs_category_id());
                    island.setIs_img(islandDetail.getIs_img());
                    island.setIs_logo(islandDetail.getIs_logo());
                    island.setIs_liked_count(islandDetail.getIs_liked_count());
                    island.setIs_work_count(islandDetail.getIs_work_count());
                    island.setIs_follow_count(islandDetail.getIs_follow_count());
                    island.setIs_parent_type(islandDetail.getIs_parent_type());
                    island.setIs_level(islandDetail.getIs_level());
                    island.setIs_people_current(islandDetail.getIs_people_current());
                    island.setIs_people_limit(islandDetail.getIs_people_limit());
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.putExtra("island", island);
                    intent.setClass(context, IslandShowActivity.class);
                    context.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                    ToastUtil.shortToast(context,e.getMessage());
                }
            }
        });
        return  convertView;
    }
    //设置view类
    private class  ViewHolder {
        ImageView iv_island_logo;
        TextView tv_island_name;
        TextView tv_getin_island;
    }
}
