package com.test.hyxc.adapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.test.hyxc.R;
import com.test.hyxc.model.UserWorkDiscussShow;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.discussreplyshow.DiscussReplyActivity;
import com.test.hyxc.page.workshow.ImageShowActivity;
import com.test.hyxc.ui.GlideRoundTransform;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.microedition.khronos.opengles.GL;

import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.DipPxUtils;
import tools.GlobalConfig;
import tools.NetUtils;
import tools.OssConfig;
public class ImageShowAdapter  extends RecyclerView.Adapter<ImageShowAdapter.ImageShowViewHolder> implements View.OnClickListener,ViewPager.OnPageChangeListener {
    private Context context;
    private List<WorkShow> data;
    ArrayAdapter arrayAdapter;
    private String url;
    private OnItemClickListener mOnItemClickListener;//声明自定义的接口
    private OnPageChangeListener pageChangeListener;
    private ImageShowViewHolder imageShowViewHolder;
    private ImageLoadingListener loadingListener;
    private int position;
    private int vp_index;
    private DisplayMetrics metric=new DisplayMetrics();
    MyAdapter adapter;
    List<String> list;
    private boolean tooLong=false;
    private boolean spread=false;
    int currentIndex=0;
    //评论请求数
    private int page_Num=1,page_Size=2, req_reply_num=2;

    // ChangeHeight changeHeight;
    public ImageShowAdapter(ArrayList<WorkShow> data, Context context) {
        setHasStableIds(true);
        this.data=data;
        this.context=context;
        OssConfig ossConfig=((AppContext) this.context.getApplicationContext()).getOssConfig();
        this.url=ossConfig.getHost() + "/";
        ((AppContext) context.getApplicationContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
    }
    public ImageShowAdapter(ArrayList<WorkShow> data, Context context,int currentIndex) {
        this.currentIndex=currentIndex;
        setHasStableIds(true);
        this.data=data;
        this.context=context;
        OssConfig ossConfig=((AppContext) this.context.getApplicationContext()).getOssConfig();
        this.url=ossConfig.getHost() + "/";
        ((AppContext) context.getApplicationContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
    }

    @Override
    public ImageShowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.imap_image_show_item, parent, false);
        imageShowViewHolder=new ImageShowViewHolder(view);
        return imageShowViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageShowViewHolder holder, final int position) {
        this.position=position;
        Long t=data.get(position).getWork_time().getTime();
        holder.itemView.setTag(position);
        holder.ll_iv1.setTag(position);
        holder.ll_iv2.setTag(position);
        holder.ll_2.setTag(position);
        holder.ll_follow_2.setTag(position);
        holder.tv_follow_2.setTag(position);
        holder.ll_follow_1.setTag(position);
        holder.group.setTag(position);
        holder.ll_zan.setTag(position);
        holder.ll_dis.setTag(position);
        holder.ll_discuss.setTag(position);
        holder.ll_share.setTag(position);
        holder.viewPager.setTag(position);
        holder.tv_index.setText(1 + "/" + data.get(position).getResourcesList().size());
        holder.viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //反射方法解决滑动卡顿问题，
                Field mFirstLayout=null;
                try {
                    //mFirstLayout=v.getClass().getSuperclass().getDeclaredField("mFirstLayout");
                    mFirstLayout=v.getClass().getDeclaredField("mFirstLayout");
                    mFirstLayout.setAccessible(true);
                    mFirstLayout.set(v, false);
                    // adapter.notifyDataSetChanged();
                    // ((ViewPager)v).setCurrentItem(((ViewPager)v).getCurrentItem());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                vp_index=(int) v.getTag();
                return false;
            }
        });
        //initSpinner(holder,data.get(position));
        String timestr=getTimeStr(t);
        String userHead=url + data.get(position).getUser_headimg();
        String usernickname=data.get(position).getUser_nickname();
        String islandname="";
        String isHead="";
        String content=data.get(position).getWork_text();
        if (data.get(position).getWork_island() == -1 || data.get(position).getWork_island() == null) {
            isHead=url + GlobalConfig.squareLogo;
            islandname= GlobalConfig.squareName;
            holder.ll_2.setVisibility(View.GONE);
        } else {
            isHead=url + data.get(position).getWork_island_logo();
            islandname=data.get(position).getWork_island_name();
        }
        holder.tv_time.setText(timestr);
        //内容处理
        if (content.length() > 96) {
            tooLong=true;
            Spannable strcontent=new SpannableString(content.substring(0, 96) + "..展开");
            strcontent.setSpan(new StyleSpan(Typeface.BOLD), 96, 100, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            strcontent.setSpan(new AbsoluteSizeSpan(46), 96, 100, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
            holder.tv_content.setText(strcontent);
        } else {
            holder.tv_content.setText(content);
        }
        //内容动态改变多少
        holder.tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tooLong) {
                    if (!spread) {
                        spread=!spread;
                        Spannable strcontent=new SpannableString(content + "..收起");
                        strcontent.setSpan(new StyleSpan(Typeface.BOLD), content.length(), content.length() + 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        strcontent.setSpan(new AbsoluteSizeSpan(46), content.length(), content.length() + 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        holder.tv_content.setText(strcontent);
                    } else {
                        spread=!spread;
                        Spannable strcontent=new SpannableString(content.substring(0, 96) + "..展开");
                        strcontent.setSpan(new StyleSpan(Typeface.BOLD), 96, 100, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        strcontent.setSpan(new AbsoluteSizeSpan(46), 96, 100, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                        holder.tv_content.setText(strcontent);
                    }
                }
            }
        });
        //如果是自己 将关注按钮隐藏
        if(data.get(position).getWork_author()==Integer.parseInt(((AppContext)context.getApplicationContext()).getUserId())){
            holder.ll_follow_1.setVisibility(View.INVISIBLE);
        }else {
            if (data.get(position).getFriendState() == null || -1 == data.get(position).getFriendState() || 1 == data.get(position).getFriendState()) {
                holder.tv_follow_1.setText("关注");
                holder.tv_follow_1.setTextColor(Color.parseColor("#A254C6"));
                holder.ll_follow_1.setBackgroundResource(R.drawable.imap_border_dark_radius);
            } else {
                holder.tv_follow_1.setText("取消关注");
                holder.tv_follow_1.setTextColor(Color.parseColor("#45464d"));
                holder.ll_follow_1.setBackgroundResource(R.drawable.imap_border_dark_radius_cancel);
            }
        }
        /////////****************/////////
        holder.tv1.setText(usernickname);
        holder.tv2.setText(islandname);
        Glide.with(context)
                .load(userHead)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(holder.iv1);
        //第二个头像  海岛
        Glide.with(context)
                .load(isHead)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 10))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                //.placeholder(R.drawable.shenpi_jiazai)
                .dontAnimate()
                .crossFade()
                .into(holder.iv2);
        if(data.get(position).getResourcesList()!=null&& data.get(position).getResourcesList().size()>0 && data.get(position).getResourcesList().get(0)!=null) {
            Glide.with(context).load(url + "/" + data.get(position).getResourcesList().get(0).getWork_resource_url()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    float scale = (float) resource.getHeight() / resource.getWidth();
                    int defaultHeight = (int) (scale * metric.widthPixels);
                    if (defaultHeight > DipPxUtils.dip2px(context, 475)) {
                        defaultHeight = DipPxUtils.dip2px(context, 475);
                    }
                    initImgVp(holder, data.get(position), defaultHeight);
                }

                @Override
                public void onLoadFailed(Exception e, Drawable errorDrawable) {
                    int defaultHeight = DipPxUtils.dip2px(context, 475);
                    initImgVp(holder, data.get(position), defaultHeight);
                }
            });
        }
        ///点赞状态
        if (data.get(position).getLikeState() == 1) {
            holder.iv_zan.setImageResource(R.mipmap.zaned);
        } else {
            holder.iv_zan.setImageResource(R.mipmap.zan1);
        }
        holder.tv_zan.setText(data.get(position).getWork_like_count() == 0 ? "" : String.valueOf(data.get(position).getWork_like_count()));
        //分享
        holder.tv_share.setText(data.get(position).getWork_share_count() == 0 ? "" : String.valueOf(data.get(position).getWork_share_count()));
        //评论
        holder.tv_discuss.setText(data.get(position).getWork_discuss_count() == 0 ? "" : String.valueOf(data.get(position).getWork_discuss_count()));
        // 评论框
        tryFillDiscuss(holder,position);
        //*****评论网络请求
        requireDiscuss(holder,position);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }

    //这里能防止位置错乱
    @Override
    public long getItemId(int position) {
        return position;
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
    //使用数组形式操作
    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {}
        public void onNothingSelected(AdapterView<?> arg0) {}
    }

    public void initImgVp(ImageShowViewHolder holder, WorkShow workShow, int defaultHeight) {
        holder.group.removeAllViews();
        for (int i=0; i < workShow.getResourcesList().size(); i++) {
            ImageView imageView=new ImageView(context);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(7, 7));
            if (i == 0) {
                imageView.setImageResource(R.drawable.page_indicator_focused);
            } else {
                imageView.setImageResource(R.drawable.page_indicator_unfocused);
            }
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin=5;
            layoutParams.rightMargin=5;
            holder.group.addView(imageView, layoutParams);
        }
        //将图片装载到数组中
        list=new ArrayList<>();
        for (int i=0; i < workShow.getResourcesList().size(); i++) {
            list.add(url + "/" + workShow.getResourcesList().get(i).getWork_resource_url());
        }
        adapter=new MyAdapter(context, list, holder);
        //设置Adapter
        holder.viewPager.setAdapter(adapter);
        ViewGroup.LayoutParams layoutParams=holder.viewPager.getLayoutParams();
        layoutParams.height=defaultHeight;
        holder.viewPager.setLayoutParams(layoutParams);
       /* if(changeHeight!=null){
            changeHeight.change((int)holder.viewPager.getTag(),defaultHeight);
        }*/
        //设置监听，主要是设置点点的背景
        holder.viewPager.setOnPageChangeListener(ImageShowAdapter.this);
        //设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
        //viewPager.setCurrentItem((mImageViews.length) * 100);
        holder.viewPager.setCurrentItem(currentIndex);
    }

    public class ImageShowViewHolder extends RecyclerView.ViewHolder {
        ImageView iv1;
        ImageView iv2;
        LinearLayout ll_iv1,ll_iv2;
        TextView tv_content;
        LinearLayout ll_follow_1,ll_follow_2;
        TextView tv_follow_1,tv_follow_2;
        LinearLayout ll_2;
        TextView tv1, tv2, tv_time;
        LinearLayout ll_spinner;
        Spinner spinner;
        ViewPager viewPager;
        ViewGroup group;
        TextView tv_index;
        ImageView iv_zan, iv_discuss, iv_share;
        TextView tv_zan, tv_discuss, tv_share;
        LinearLayout ll_zan, ll_dis, ll_share;
        LinearLayout ll_discuss;

        public ImageShowViewHolder(View itemView) {
            super(itemView);
            iv1=itemView.findViewById(R.id.iv1);
            iv2=itemView.findViewById(R.id.iv2);
            ll_iv1=itemView.findViewById(R.id.ll_iv1);
            ll_iv2=itemView.findViewById(R.id.ll_iv2);
            ll_follow_1=itemView.findViewById(R.id.ll_follow_1);
            ll_follow_2=itemView.findViewById(R.id.ll_follow_2);
            tv_follow_1=itemView.findViewById(R.id.tv_follow_1);
            tv_follow_2=itemView.findViewById(R.id.tv_follow_2);
            ll_2=itemView.findViewById(R.id.ll_2);
            tv1=itemView.findViewById(R.id.tv1);
            tv2=itemView.findViewById(R.id.tv2);
            tv_time=itemView.findViewById(R.id.tv_time);
            ll_spinner=itemView.findViewById(R.id.ll_spinner);
            spinner=itemView.findViewById(R.id.spinner);
            viewPager=itemView.findViewById(R.id.viewPager);
            group=itemView.findViewById(R.id.viewGroup);
            tv_index=itemView.findViewById(R.id.tv_index);
            tv_content=itemView.findViewById(R.id.tv_content);
            ////点赞 分享 评论
            iv_zan=itemView.findViewById(R.id.iv_zan);
            iv_discuss=itemView.findViewById(R.id.iv_discuss);
            iv_share=itemView.findViewById(R.id.iv_share);
            tv_zan=itemView.findViewById(R.id.tv_zan);
            tv_discuss=itemView.findViewById(R.id.tv_discuss);
            tv_share=itemView.findViewById(R.id.tv_share);
            ll_zan=itemView.findViewById(R.id.ll_zan);
            ll_dis=itemView.findViewById(R.id.ll_dis);
            ll_share=itemView.findViewById(R.id.ll_share);
            ll_discuss=itemView.findViewById(R.id.ll_discuss);
            //点击事件设置
            ll_iv1.setOnClickListener(ImageShowAdapter.this);
            ll_iv2.setOnClickListener(ImageShowAdapter.this);
            //海岛点击事件
            ll_2.setOnClickListener(ImageShowAdapter.this);
            ll_follow_1.setOnClickListener(ImageShowAdapter.this);
            ll_follow_2.setOnClickListener(ImageShowAdapter.this);
            tv_follow_2.setOnClickListener(ImageShowAdapter.this);
            ll_zan.setOnClickListener(ImageShowAdapter.this);
            ll_share.setOnClickListener(ImageShowAdapter.this);
            ll_dis.setOnClickListener(ImageShowAdapter.this);
            ll_discuss.setOnClickListener(ImageShowAdapter.this);
            itemView.setOnClickListener(ImageShowAdapter.this);
        }
    }

    public class MyAdapter extends PagerAdapter {
        Context context;
        List<String> list;
        ImageShowViewHolder holder;
        public MyAdapter(Context context, List<String> list, ImageShowViewHolder holder) {
            this.context=context;
            this.list=list;
            this.holder=holder;
            ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));
        }
        @Override
        public int getCount() {
            return list.size();
        }
        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
        @Override
        public void destroyItem(View container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }
        /**
         * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
         */
        @Override
        public Object instantiateItem(View container, int position) {
            ImageView imageView=new ImageView(context);
            DisplayImageOptions options=new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .build();
            // ImageLoader.getInstance().displayImage(list.get(position), imageView, options,ImageShowAdapter.this);
            //((WrapContentHeightViewPager)holder.viewPager).setViewForPosition(imageView,position);
            ImageLoader.getInstance().displayImage(list.get(position), imageView, options, new com.nostra13.universalimageloader.core.listener.ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {

                }

                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                    int width=bitmap.getWidth();
                    int height=bitmap.getHeight();
                    if (width >= height) {
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    } else {
                        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    }
                }

                @Override
                public void onLoadingCancelled(String s, View view) {
                }
            });
            ViewGroup parent=(ViewGroup) imageView.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            ((ViewPager) container).addView(imageView, 0);
            return imageView;
        }
    }

    public List<WorkShow> getData() {
        return data;
    }

    public void setData(List<WorkShow> data) {
        this.data=data;
    }

    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v, ViewName viewName, int position);

        void onItemLongClick(View v);
    }
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener=listener;
    }

    @Override
    public void onClick(View v) {
        int position=(int) v.getTag();
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                //用户头像点击事件
                case R.id.ll_iv1:
                    mOnItemClickListener.onItemClick(v,ViewName.PRACTISE, position);
                    break;
                case R.id.ll_iv2:
                    mOnItemClickListener.onItemClick(v,ViewName.PRACTISE,position);
                    break;
                //关注按钮点击事件
                case R.id.ll_follow_1:
                    mOnItemClickListener.onItemClick(v,ViewName.PRACTISE,position);
                    break;
                case R.id.ll_follow_2:
                    mOnItemClickListener.onItemClick(v,ViewName.PRACTISE,position);
                    break;
                case R.id.tv_follow_2:
                    mOnItemClickListener.onItemClick(v,ViewName.PRACTISE,position);
                    break;
                case R.id.ll_2:
                    mOnItemClickListener.onItemClick(v,ViewName.PRACTISE,position);
                    break;
                case R.id.ll_zan:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                case R.id.ll_dis:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                case R.id.ll_discuss:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                case R.id.ll_share:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, position);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, position);
                    break;
            }
        }
    }

    public interface OnPageChangeListener {
        void onPageSelected(int vp_index, int i);
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.pageChangeListener=listener;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {
        //计算ViewPager现在应该的高度,heights[]表示页面高度的数组。
        //为ViewPager设置高度
        ViewGroup.LayoutParams params=imageShowViewHolder.viewPager.getLayoutParams();

        // params.height = 200;
        imageShowViewHolder.viewPager.setLayoutParams(params);
    }

    @Override
    public void onPageSelected(int i) {
        if (pageChangeListener != null) {
            pageChangeListener.onPageSelected(vp_index, i);
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    /**
     * ImageLoder 开始加载的监听
     *
     * @author Administrator
     */
    private class MySimpleLoading extends SimpleImageLoadingListener {
        public ImageShowViewHolder holder;

        public MySimpleLoading(ImageShowViewHolder holder) {
            this.holder=holder;
        }

        //开始加载
        @Override
        public void onLoadingStarted(String imageUri, View view) {
            // TODO Auto-generated method stub
            super.onLoadingStarted(imageUri, view);
        }

        @Override
        public void onLoadingFailed(String imageUri, View view,
                                    FailReason failReason) {
        }

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            // TODO Auto-generated method stub
            super.onLoadingComplete(imageUri, view, loadedImage);
            int height=loadedImage.getHeight();
            ((ImageView) view).setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (height <= DipPxUtils.dip2px(context, 300)) {
                ((ImageView) view).setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
            ViewGroup.LayoutParams layoutParams=holder.viewPager.getLayoutParams();
            layoutParams.height=200;
            holder.viewPager.setLayoutParams(layoutParams);
        }
    }
    public interface ImageLoadingListener {
        void LoadingComplete(int vp_index, String s, View view, Bitmap bitmap);
    }

    public void setImageLoadingListener(ImageLoadingListener listener) {
        this.loadingListener=listener;
    }

    /**
     * 网络请求 评论
     */
    public void requireDiscuss(ImageShowViewHolder holder,int position) {
        WorkShow workShow=data.get(position);
        if (workShow.getUserWorkDiscussShowList() == null || workShow.getUserWorkDiscussShowList().size() == 0) {
            try {
                NetUtils netUtils=NetUtils.getInstance();
                String token=((AppContext) context.getApplicationContext()).getToken();
                Map map=new HashMap();
                map.put("work_id", String.valueOf(workShow.getWork_id()));
                map.put("page_Num",String.valueOf(page_Num));
                map.put("page_Size", String.valueOf(page_Size));
                map.put("replyNum", String.valueOf(req_reply_num));
                int finalI=position;
                netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/findUWDSByWorkId", token, map, new NetUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException, JSONException {
                        String ret=response.body().string();
                        JSONObject json=new JSONObject(ret);
                        Gson gson=new Gson();
                        JSONArray userworkdiscussshow=json.getJSONArray("list");
                        JsonParser parser=new JsonParser();
                        final JsonArray Jarray=parser.parse(userworkdiscussshow.toString()).getAsJsonArray();
                        List<UserWorkDiscussShow> listuserworkdiscussshow=new ArrayList<>();
                        for (JsonElement obj : Jarray) {
                            UserWorkDiscussShow uwd=gson.fromJson(obj, UserWorkDiscussShow.class);
                            listuserworkdiscussshow.add(uwd);
                        }
                        data.get(finalI).setUserWorkDiscussShowList(listuserworkdiscussshow);
                        ((ImageShowActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tryFillDiscuss(holder,finalI);
                            }
                        });
                        //notifyItemInserted(finalI);//刷新评论回复
                    }
                    @Override
                    public void failed(Call call, IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void tryFillDiscuss(ImageShowViewHolder holder,int position){
        WorkShow workShow=data.get(position);
        if (workShow.getUserWorkDiscussShowList() == null || workShow.getUserWorkDiscussShowList().size() == 0) {
            holder.ll_discuss.removeAllViews();
            TextView textView=new TextView(context);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setTextColor(Color.parseColor("#8B8989"));
            textView.setText("期待你的评论哦~");
            holder.ll_discuss.addView(textView);
        } else {
            holder.ll_discuss.removeAllViews();
            List<UserWorkDiscussShow> list=workShow.getUserWorkDiscussShowList();
            for (int i=0; i < list.size(); i++) {
                View view = LayoutInflater.from(context).inflate(R.layout.imap_discuss_item,null);
                String nickname=list.get(i).getUser_nickname().length()>7?list.get(i).getUser_nickname().substring(0,6)+"..:":list.get(i).getUser_nickname()+":";
                String discuss_text=list.get(i).getDiscuss_text().length()>24?list.get(i).getDiscuss_text().substring(0,24)+"...":list.get(i).getDiscuss_text();
                ((TextView)view.findViewById(R.id.tv_nickname)).setText(nickname);
                ((TextView)view.findViewById(R.id.tv_discuss_text)).setText(discuss_text);
                holder.ll_discuss.addView(view);
            }
            TextView textView=new TextView(context);
            textView.setText("共"+workShow.getWork_discuss_count()+"条评论");
            holder.ll_discuss.addView(textView);
        }
    }

    public void initSpinner(ImageShowViewHolder holder,WorkShow workShow) {
        int relation=userIslandRelation(workShow.getWork_island());
        String[] dropDown=null;
        if(relation==0) {
             dropDown = new String[]{"关注", "加入"};
        }else if(relation==1){
             dropDown = new String[]{"取消关注","加入"};
        }else if(relation==2){
            dropDown=new String[]{"退出"};
        }
        arrayAdapter=new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, dropDown);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.spinner.setAdapter(arrayAdapter);
        //添加事件Spinner事件监听
        holder.spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
    }
    //确定海岛和用户的关系 0：毫无关系 1：关注 2：所在
    public int userIslandRelation(int is_id){
        AppContext ctx=((AppContext) this.context.getApplicationContext());
        String[] userIslandFollows=ctx.getUserIslandFollow().split("\\|");
        String[] userIslands=ctx.getUserIsland().split("\\|");
        if(userIslandFollows!=null&&userIslandFollows.length>0) {
            for (int x = 0; x < userIslandFollows.length; x++) {
                if (Integer.parseInt(userIslandFollows[x]) == is_id)
                    return 1;
            }
        }
        if(userIslands!=null&&userIslands.length>0) {
            for (int y = 0; y < userIslands.length; y++) {
                if (Integer.parseInt(userIslands[y]) == is_id)
                    return 2;
            }
        }
        return 0;
    }
}
