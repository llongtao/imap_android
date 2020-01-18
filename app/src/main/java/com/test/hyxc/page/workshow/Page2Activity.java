package com.test.hyxc.page.workshow;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.chat.activity.ForwardMsgActivity;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.discussreplyshow.DiscussReplyActivity;
import com.test.hyxc.page.island.IslandShowActivity;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.page.workshow.base.BaseRecAdapter;
import com.test.hyxc.page.workshow.base.BaseRecViewHolder;
import com.test.hyxc.page.workshow.widget.MyVideoPlayer;
import com.test.hyxc.ui.GlideRoundTransform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.GlobalConfig;
import tools.NetUtils;

public class Page2Activity extends BaseActivity {
    RecyclerView rvPage2;
    private ListVideoAdapter videoAdapter;
    private PagerSnapHelper snapHelper;
    private LinearLayoutManager layoutManager;
    double longitude=0,latitude=0;
    WorkShow workShow;
    int type=1;
    String url="";
    //没有更多了
    private  boolean noMore=false;
    List<WorkShow> lws=new ArrayList<>();
    int page_Num =1,page_Size=5;
    double findR = 1000000;
    AppContext ctx;
    @Override
    protected void initData() {}
    @Override
    protected int getLayoutID() { return R.layout.activity_page2; }
    @Override
    protected void initListener() {}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarColor(R.color.colorTransparent)
                .statusBarDarkFont(false);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    public  void initView() {
        ctx=(AppContext)getApplicationContext();
        longitude=ctx.getLongitude();
        latitude=ctx.getLatitude();
        rvPage2=findViewById(R.id.rv_page2);
        snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rvPage2);
        url=((AppContext)getApplicationContext()).getOssConfig().getHost()+"/";
        videoAdapter = new ListVideoAdapter(lws);
        layoutManager = new LinearLayoutManager(Page2Activity.this, LinearLayoutManager.VERTICAL, false);
        rvPage2.setLayoutManager(layoutManager);
        rvPage2.setAdapter(videoAdapter);
        if(getIntent().hasExtra("workshow")) {
            workShow = (WorkShow) getIntent().getSerializableExtra("workshow");
            type=workShow.getWork_type();
            lws.add(workShow);
            videoAdapter.notifyDataSetChanged();
            addListener();
        }else if (getIntent().hasExtra("work_id")){
            requireWorkById(getIntent().getIntExtra("work_id",1));
        }



    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    private void addListener() {
        rvPage2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    try {//滑动到底部的时候 请求新的数据
                        requireVideoShowMore();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE://停止滚动
                        View view = snapHelper.findSnapView(layoutManager);
                        JZVideoPlayer.releaseAllVideos();
                        RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
                        if (viewHolder != null && viewHolder instanceof VideoViewHolder) {
                            ((VideoViewHolder) viewHolder).mp_video.startVideo();
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING://拖动
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING://惯性滑动
                        break;
                }
            }
        });
    }
    @Override
    public void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
    class ListVideoAdapter extends BaseRecAdapter<WorkShow, VideoViewHolder> {
        public ListVideoAdapter(List<WorkShow> list) {
            super(list);
        }
        @Override
        public void onHolder(VideoViewHolder holder, WorkShow bean, int position) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
            holder.mp_video.setUp(url+lws.get(position).getWork_show_image(), JZVideoPlayerStandard.CURRENT_STATE_NORMAL);
            if (position == 0) {
                holder.mp_video.startVideo();
            }
            holder.iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            Glide.with(context).load(url+lws.get(position).getWork_show_image()).into(holder.mp_video.thumbImageView);
            //holder.tv_title.setText("第" + position + "个视频");
            holder.tv_content.setText(lws.get(position).getWork_text());
            //////////头像
            Glide.with(context)
                    .load(url+lws.get(position).getUser_headimg())
                    .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .crossFade()
                    .into(holder.iv_head);
            ////////////
            holder.iv_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoUserPage(position);
                }
            });
            holder.tv_zan.setText(trans2String(lws.get(position).getWork_like_count()));
            holder.tv_discuss.setText(trans2String(lws.get(position).getWork_discuss_count()));
            holder.tv_share.setText(trans2String(lws.get(position).getWork_share_count()));
            ///海岛信息
            if(lws.get(position).getWork_island()==-1){
                //广场
                Glide.with(context)
                        .load(url+GlobalConfig.squareLogo)
                        .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .crossFade()
                        .into(holder.iv_island);
                holder.tv_island.setText("广场");
            }else {
                Glide.with(context)
                        .load(url+lws.get(position).getWork_island_logo())
                        .transform(new CenterCrop(context), new GlideRoundTransform(context, 50))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .crossFade()
                        .into(holder.iv_island);
                holder.tv_island.setText(lws.get(position).getWork_island_name());
            }
            holder.iv_island.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    gotoIslandPage(position);
                }
            });
            /////////////点击事件
            holder.ll_dis_to.setTag(position);
            holder.ll_dis_to.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int po=(int) v.getTag();
                    Intent intent1=new Intent (Page2Activity.this, DiscussReplyActivity.class);
                    intent1.putExtra("workShow",lws.get(po));
                    startActivity(intent1);
                }
            });
            //点赞
            holder.iv_zan.setTag(position);
            ///点赞状态
            if (lws.get(position).getLikeState() == 1) {
                holder.iv_zan.setImageResource(R.mipmap.zaned);
            } else {
                holder.iv_zan.setImageResource(R.mipmap.zan_white);
            }
            holder.iv_zan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    zanWork(position,v,holder.tv_zan);
                }
            });
            //评论
            holder.iv_discuss.setTag(position);
            holder.iv_discuss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int po=(int) v.getTag();
                    Intent intent1=new Intent (Page2Activity.this, DiscussReplyActivity.class);
                    intent1.putExtra("workShow",lws.get(po));
                    startActivity(intent1);
                }
            });
            //分享
            holder.iv_share.setTag(position);
            holder.iv_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /////////////////
                    //创建自定义消息
                    Intent i = new Intent(Page2Activity.this, ForwardMsgActivity.class);
                    i.setFlags(2);
                    i.putExtra("useType","share");
                    i.putExtra("workShow",lws.get(position));
                    AppContext.forwardMsg.clear();
                    startActivity(i);
                }
            });

        }
        @Override
        public VideoViewHolder onCreateHolder() { return new VideoViewHolder(getViewByRes(R.layout.item_page2)); }
    }
    public class VideoViewHolder extends BaseRecViewHolder {
        public View rootView;
        public MyVideoPlayer mp_video;
        public TextView tv_title;
        //////
        public ImageView iv_back;
        public TextView tv_content;
        public ImageView iv_head;
        ////
        public ImageView iv_zan,iv_discuss,iv_share;
        public TextView tv_zan,tv_discuss,tv_share;
        ////底部写评论
        public LinearLayout ll_dis_to;
        /////////海岛信息
        public ImageView iv_island;
        public TextView tv_island;

        public VideoViewHolder(View rootView) {
            super(rootView);
            this.rootView = rootView;
            this.mp_video = rootView.findViewById(R.id.mp_video);
            this.tv_title = rootView.findViewById(R.id.tv_title);
            iv_back=rootView.findViewById(R.id.iv_back);
            tv_content=rootView.findViewById(R.id.tv_content);
            iv_head=rootView.findViewById(R.id.iv_head);
            tv_zan=rootView.findViewById(R.id.tv_zan);
            tv_discuss=rootView.findViewById(R.id.tv_discuss);
            tv_share=rootView.findViewById(R.id.tv_share);
            ll_dis_to=rootView.findViewById(R.id.ll_dis_to);
            iv_zan=rootView.findViewById(R.id.iv_zan);
            iv_discuss=rootView.findViewById(R.id.iv_discuss);
            iv_share=rootView.findViewById(R.id.iv_share);
            iv_island=rootView.findViewById(R.id.iv_island);
            tv_island=rootView.findViewById(R.id.tv_island);
        }
    }
    /////////////////数量转换
    public String trans2String(int count){
        String result="";
        if(count>10000){
            result= count/10000+"万";
        }else if(count>100000){
            result=count/100000+"十万";
        }else if(count>1000000){
            result=count/1000000+"百万";
        }else if(count>10000000){
            result=count/10000000+"千万";
        }else if(count<10000){
            result=String.valueOf(count);
        }
        return result;
    }
    public void gotoUserPage(Integer position) {
        Intent intent = new Intent();
        intent.putExtra("user_id", String.valueOf(lws.get(position).getWork_author()));
        intent.setClass(Page2Activity.this, MeActivity.class);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
    //需要先请求海岛信息 然后跳转
    public void gotoIslandPage(Integer position){
        int is_id=lws.get(position).getWork_island();
        if(is_id != -1) {
            Intent intent = new Intent();
            intent.putExtra("is_id", is_id);
            intent.setClass(Page2Activity.this, IslandShowActivity.class);
            startActivity(intent);
        }
    }
    //点赞
    public void zanWork(int position,View imageView,TextView tvZanView){
        int user_id=lws.get(position).getWork_author();
        int work_id=lws.get(position).getWork_id();
        int is_id=lws.get(position).getWork_island();
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("work_author",String.valueOf(user_id));
        map.put("work_id",String.valueOf(work_id));
        if(is_id!=-1)
            map.put("is_id",String.valueOf(is_id));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/likeWork",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException {
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        return;
                    }
                    String likeState=json.getString("likeState");
                    if(likeState.equals("ever_done")){  //点赞过了 就是取消点赞状态
                        lws.get(position).setLikeState(0);
                        lws.get(position).setWork_like_count(lws.get(position).getWork_like_count()-1<0?0:lws.get(position).getWork_like_count()-1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView) imageView).setImageResource(R.mipmap.zan_white);
                                ((TextView) tvZanView).setText(String.valueOf(lws.get(position).getWork_like_count()==0?"":lws.get(position).getWork_like_count()));
                            }
                        });
                        //发送广播修改点赞数量 和点赞状态
                        Intent intent =new Intent();
                        intent.setAction(GlobalConfig.likeCountChange);
                        intent.putExtra("index",position);
                        intent.putExtra("work_id",work_id);
                        intent.putExtra("likeCount",lws.get(position).getWork_like_count());
                        intent.putExtra("likeState",0);
                        sendBroadcast(intent);
                    }else if(likeState.equals("success")){
                        lws.get(position).setLikeState(1);
                        lws.get(position).setWork_like_count(lws.get(position).getWork_like_count()+1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //((ImageView) rv_workimg.getLayoutManager().findViewByPosition(position).findViewById(R.id.iv_zan)).setImageResource(R.mipmap.zaned);
                                ((ImageView)imageView).setImageResource(R.mipmap.zaned);
                                ((TextView) tvZanView).setText(String.valueOf(lws.get(position).getWork_like_count()));
                            }
                        });
                        //发送广播修改点赞数量 和点赞状态
                        Intent intent =new Intent();
                        intent.setAction(GlobalConfig.likeCountChange);
                        intent.putExtra("index",position);
                        intent.putExtra("work_id",work_id);
                        intent.putExtra("likeCount",lws.get(position).getWork_like_count());
                        intent.putExtra("likeState",1);
                        sendBroadcast(intent);
                    }
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
    public void requireVideoShowMore(){
        int is_id= workShow.getWork_island();
        if(is_id==-1){
            //请求广场数据  根据地理位置查找
            requireByLoc();
        }else{
            //根据相同类别的去找
            requireByCategory();
        }
    }
    public void requireByLoc(){
        if(noMore){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"没有更多了",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            try {
                NetUtils netUtils=NetUtils.getInstance();
                String token=((AppContext) getApplicationContext()).getToken();
                Map map=new HashMap();
                map.put("longitude", String.valueOf(longitude));
                map.put("latitude", String.valueOf(latitude));
                map.put("page_First", String.valueOf(page_Num));
                map.put("page_Size", String.valueOf(page_Size));
                map.put("findR", String.valueOf(findR));
                map.put("work_type", String.valueOf(type));
                netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/findByLocDescByTimePage", token, map, new NetUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException, JSONException {
                        String ret=response.body().string();
                        JSONObject json=new JSONObject(ret);
                        //如果token过期,就重新登陆
                        if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                            return;
                        }
                        //如果找到了 要注意将中间变量修改
                        final Gson gson=new Gson();
                        JSONArray listworkshowdetail=json.getJSONArray("listworkshowdetail");
                        if (listworkshowdetail.length() == 0 || listworkshowdetail == null) {
                            //没有更多了
                            noMore=true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"没有更多了",Toast.LENGTH_SHORT).show();
                                }
                            });

                        } else {
                            page_Num++;
                            JsonParser parser=new JsonParser();
                            final JsonArray Jarray=parser.parse(listworkshowdetail.toString()).getAsJsonArray();
                            if (Jarray != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (JsonElement obj : Jarray) {
                                            WorkShow ws=gson.fromJson(obj, WorkShow.class);
                                            lws.add(ws);
                                            videoAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        }
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
    public void  requireByCategory(){
        if(noMore){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(),"没有更多了",Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            try {
                NetUtils netUtils=NetUtils.getInstance();
                String token=((AppContext) getApplicationContext()).getToken();
                Map map=new HashMap();
                map.put("page_First", String.valueOf(page_Num));
                map.put("page_Size", String.valueOf(page_Size));
                map.put("work_island_category", workShow.getWork_island_category());
                map.put("work_type", String.valueOf(type));
                netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/findWorkDetailByConditionDescPage", token, map, new NetUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException, JSONException {
                        String ret=response.body().string();
                        JSONObject json=new JSONObject(ret);
                        //如果token过期,就重新登陆
                        if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                            return;
                        }
                        //如果找到了 要注意将中间变量修改
                        final Gson gson=new Gson();
                        JSONArray listworkshowdetail=json.getJSONArray("listworkshowdetail");
                        if (listworkshowdetail.length() == 0 || listworkshowdetail == null) {
                            //没有更多了
                            noMore=true;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"没有更多了",Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            page_Num++;
                            JsonParser parser=new JsonParser();
                            final JsonArray Jarray=parser.parse(listworkshowdetail.toString()).getAsJsonArray();
                            if (Jarray != null) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        for (JsonElement obj : Jarray) {
                                            WorkShow ws=gson.fromJson(obj, WorkShow.class);
                                            lws.add(ws);
                                            videoAdapter.notifyDataSetChanged();
                                        }
                                    }
                                });
                            }
                        }
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
    public void requireWorkById( int work_id){
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("work_id", String.valueOf(work_id));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/getWorkById", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        return;
                    }
                    //如果找到了 要注意将中间变量修改
                    final Gson gson=new Gson();
                    JSONObject  object=json.getJSONObject("workShowDetail");
                    if (object != null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                workShow =gson.fromJson(object.toString(), WorkShow.class);
                                type = workShow.getWork_type();
                                lws.add(workShow);
                                videoAdapter.notifyDataSetChanged();
                                //滚动监听
                                addListener();
                            }
                        });
                    }
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
