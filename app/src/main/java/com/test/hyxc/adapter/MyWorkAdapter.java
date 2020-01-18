package com.test.hyxc.adapter;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.test.hyxc.MainActivity;
import com.test.hyxc.R;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.ui.GlideRoundTransform;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tools.AppContext;
import tools.DipPxUtils;
public class MyWorkAdapter extends RecyclerView.Adapter<MyWorkAdapter.WorkViewHolder> implements View.OnClickListener, AdapterView.OnItemClickListener {
    List<WorkShow> data;
    Context context;
    String url="";
    int position;
    public MyWorkAdapter(ArrayList<WorkShow> data, Context context) {
        setHasStableIds(true);
        this.data = data;
        this.context = context;
        url=((AppContext)context.getApplicationContext()).getOssConfig().getHost()+"/";
    }
    @NonNull
    @Override
    public MyWorkAdapter.WorkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        position=i;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_workshow_item, viewGroup, false);
        return new WorkViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyWorkAdapter.WorkViewHolder holder, int position) {
         WorkShow workShow=data.get(position);
         if(workShow.getWork_island()==-1){
             //头像
             Glide.with(context)
                     .load(url+workShow.getUser_headimg())
                     .placeholder(R.mipmap.error)
                     .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                     .diskCacheStrategy(DiskCacheStrategy.ALL)
                     .dontAnimate()
                     .crossFade()
                     .into(holder.iv1);
             holder.tv1.setText(workShow.getUser_nickname());
         }else{
             Glide.with(context)
                     .load(url+workShow.getWork_island_logo())
                     .placeholder(R.mipmap.error)
                     .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                     .diskCacheStrategy(DiskCacheStrategy.ALL)
                     .dontAnimate()
                     .crossFade()
                     .into(holder.iv1);
             holder.tv1.setText(workShow.getWork_island_name());
         }
        Long t=workShow.getWork_time().getTime();
        String timestr=getTimeStr(t);
        holder.tv_time.setText(timestr);
        /////*****************************gridview
        Map<String,Object> map=new HashMap<>();
        if(workShow.getWork_type()==0){
            //图片
            map.put("type",0);
            List<String> listimgs=new ArrayList<>();
            for(int i=0;i<workShow.getResourcesList().size();i++){
                listimgs.add( url +workShow.getResourcesList().get(i).getWork_resource_url());
            }
            map.put("url",listimgs);
            holder.gridView.setNumColumns(3);
        }else{
            //视频
            map.put("type",1);
            List<String> listimgs=new ArrayList<>();
            for(int i=0;i<workShow.getResourcesList().size();i++){
                listimgs.add( url +workShow.getResourcesList().get(i).getWork_resource_url()+"?x-oss-process=video/snapshot,t_1000,f_jpg,w_0,h_0,m_fast");
            }
            map.put("url",listimgs);
            //视频的话 列数1
            holder.gridView.setNumColumns(1);
        }
        //设置列宽度和列边距
        holder.gridView.setAdapter(new ImageAdapter(context,map));
        holder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
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
    public List<WorkShow> getData() {
        return data;
    }

    public void setData(List<WorkShow> data) {
        this.data = data;
    }
    @Override
    public void onClick(View v) {}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    class WorkViewHolder extends RecyclerView.ViewHolder {
         GridView gridView;
         LinearLayout ll_top;
         ImageView iv1;
         TextView tv1;
         TextView tv_time;
        LinearLayout ll_end;
        public WorkViewHolder(View itemView) {
            super(itemView);
            gridView = itemView.findViewById(R.id.gridview);
            ll_top=itemView.findViewById(R.id.ll_top);
            iv1=itemView.findViewById(R.id.iv1);
            tv1=itemView.findViewById(R.id.tv1);
            tv_time=itemView.findViewById(R.id.tv_time);
        }
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
    ///九宫格图像适配
    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private Map<String,Object> map;
        int type=0;
        public ImageAdapter(Context context,Map<String,Object> map) {
            this.context = context;
            this.map = map;
        }
        @Override
        public int getCount() {
            return ((List)map.get("url")).size();
        }
        @Override
        public String getItem(int i) {
            return (String) ((List)map.get("url")).get(i);
        }
        @Override
        public long getItemId(int i) {
            return i;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder vh = null;
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.grid_item,null);
                vh = new ViewHolder();
                vh.imageView =view.findViewById(R.id.imageview);
                vh.ll_video_tip=view.findViewById(R.id.ll_video_tip);
                view.setTag(vh);
            }
            vh = (ViewHolder) view.getTag();
            if(Integer.parseInt(map.get("type").toString())==0){
                //图片的话 视频按钮不显示
                vh.ll_video_tip.setVisibility(View.GONE);
                type=0;
                ViewGroup.LayoutParams lp=vh.imageView.getLayoutParams();
                DisplayMetrics metric=new DisplayMetrics();
                ((AppContext) context.getApplicationContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
                lp.width=(metric.widthPixels-DipPxUtils.dip2px(context,85))/3;
                lp.height=lp.width;
                vh.imageView.setLayoutParams(lp);
            }else if(Integer.parseInt(map.get("type").toString())==1){
                vh.ll_video_tip.setVisibility(View.VISIBLE);
                type=1;
            }
            if(map.get("url")!=null && ((List)map.get("url")).size()>0) {
                ViewHolder finalVh=vh;
                if (type == 0) { //图片是九宫格的方形宽高
                    Glide.with(context).load(((String) ((List) map.get("url")).get(i)))
                            .thumbnail(0.1f)
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(finalVh.imageView);
                } else if (type == 1) {
                    Glide.with(context).load(((String) ((List) map.get("url")).get(i)))
                            .asBitmap()
                            .placeholder(R.mipmap.loading) //加载成功前显示的图片
                            .error(R.mipmap.error)
                          //  .thumbnail(0.1f)
                            .listener(new RequestListener<String, Bitmap>() {
                                @Override
                                public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                                    return false;
                                }
                                @Override
                                public boolean onResourceReady(Bitmap bitmap, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                    int width=bitmap.getWidth();
                                    int height=bitmap.getHeight();
                                    int pxWidth=0;
                                    DisplayMetrics metric=new DisplayMetrics();
                                    ((AppContext) context.getApplicationContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
                                    pxWidth=(metric.widthPixels - DipPxUtils.dip2px(context, 78)) / 2;
                                    float ratio=(float) pxWidth / (float) width;
                                    float imageHeight=height * ratio;
                                    ViewGroup.LayoutParams lp=finalVh.imageView.getLayoutParams();
                                    lp.width=pxWidth;
                                    lp.height=(int) imageHeight;
                                    if ((float) lp.height / (float) lp.width > 1.6f) {
                                        lp.height=(int) (lp.width * 1.6);
                                    }
                                    finalVh.imageView.setLayoutParams(lp);
                                    return false;
                                }
                            })
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(finalVh.imageView);
                }
            }
            return view;
        }
        class ViewHolder{
            ImageView imageView;
            LinearLayout ll_video_tip;
        }
    }

}
