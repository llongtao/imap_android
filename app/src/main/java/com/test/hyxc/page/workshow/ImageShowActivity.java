package com.test.hyxc.page.workshow;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.ImageShowAdapter;
import com.test.hyxc.chat.activity.ForwardMsgActivity;
import com.test.hyxc.fragment.RecommendFragment;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.discussreplyshow.DiscussReplyActivity;
import com.test.hyxc.page.island.IslandShowActivity;
import com.test.hyxc.page.personal.MeActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.model.Message;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.GlobalConfig;
import tools.NetUtils;
public class ImageShowActivity extends BaseActivity {
    private ArrayList<WorkShow> lws = new ArrayList<>();
    LinearLayout ll_back;
    private RecyclerView  rv_workimg;
    private StaggeredGridLayoutManager recyclerViewLayoutManager;
    private ImageShowAdapter adapter;
    //广播 修改点赞数量
    public static final String BROADCAST_ACTION = "ImageShowActivity";
    private double longitude=120f,latitude=30.2f; //设置默认值
    //分页查询
    private  int page_Num=1,page_Size=3;
    private  double findR=20000;// 10公里 之内的
    //没有更多了
    private  boolean noMore=false;
    //前面页面传过来的workshow
    WorkShow workShow;
    //根据intent传过来的数据判断要请求图片还是视频
    int type=0;
    public AppContext ctx;
    //用户所关注的海岛
    public String userIslandFollow="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_image_show;
    }
    @Override
    protected void initListener() { }
    @Override
    protected void initView() {
        ctx=((AppContext)getApplicationContext());
        userIslandFollow=ctx.getUserIslandFollow();
        try {
            longitude=((AppContext) getApplicationContext()).getLongitude();
            latitude=((AppContext) getApplicationContext()).getLatitude();
        }catch (Exception e){
            e.printStackTrace();
        }
        ll_back=f(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rv_workimg=f(R.id.rv_workimg);
        adapter=new ImageShowAdapter(lws,this);
        //这里能防止位置错乱
        recyclerViewLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv_workimg.setLayoutManager(recyclerViewLayoutManager);
        rv_workimg.setAdapter(adapter);
        //取消动画
        rv_workimg.setItemAnimator(null);
        //设置点击事件
        adapter.setOnItemClickListener(MyItemClickListener);
        adapter.setOnPageChangeListener(pageChangeListener);
        //如果传过来的是workshow 序列化对象
        if(getIntent().hasExtra("workshow")) {
            workShow = (WorkShow) getIntent().getSerializableExtra("workshow");
            type=workShow.getWork_type();
            lws.add(workShow);
            adapter.notifyDataSetChanged();
            //滚动监听
            setGlideToBottom();
        }else if(getIntent().hasExtra("work_id")){
            requireWorkById(getIntent().getIntExtra("work_id",1));
        }

    }
    @Override
    protected void initData() {}
    /**item＋item里的控件点击监听事件 */
    private ImageShowAdapter.OnItemClickListener MyItemClickListener = new ImageShowAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, ImageShowAdapter.ViewName viewName, int position) {
            switch (v.getId()){
                case R.id.ll_iv1:
                    gotoUserPage(position);
                    break;
                case R.id.ll_iv2:
                    gotoIslandPage(position);
                    break;
                //关注按钮
                case  R.id.ll_follow_1:
                    try {
                        followPerson(position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case  R.id.ll_follow_2:
                    followIsland();
                    break;
                case R.id.tv_follow_2:
                    gotoIslandPage(position);
                    break;
                case R.id.ll_2:
                    gotoIslandPage(position);
                    break;
                case R.id.ll_zan:
                    zanWork(position);
                    break;
                case R.id.ll_dis:
                    Intent intent=new Intent (ImageShowActivity.this, DiscussReplyActivity.class);
                    intent.putExtra("workShow", lws.get(position));
                    startActivity(intent);
                    break;
                case R.id.ll_discuss:
                    Intent intent1=new Intent (ImageShowActivity.this, DiscussReplyActivity.class);
                    intent1.putExtra("workShow",lws.get(position));
                    startActivity(intent1);
                    break;
                case R.id.ll_share:
                    //创建自定义消息
                    Intent i = new Intent(ImageShowActivity.this, ForwardMsgActivity.class);
                    i.setFlags(2);
                    i.putExtra("useType","share");
                    i.putExtra("workShow",lws.get(position));
                    AppContext.forwardMsg.clear();
                    //AppContext.forwardMsg.add(msg);
                    startActivity(i);
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onItemLongClick(View v) {
        }
    };
    /**item＋item里的控件点击监听事件 */
    private ImageShowAdapter.OnPageChangeListener pageChangeListener = new ImageShowAdapter.OnPageChangeListener() {
        @Override
        public void onPageSelected( int vp_index,int i) {
            changeTips(vp_index,i);
            changeTextIndex(vp_index,i);
            View v= rv_workimg.getLayoutManager().findViewByPosition(vp_index);
            //((WrapContentHeightViewPager)v.findViewById(R.id.viewPager)).resetHeight(i);
        }
    };
   /* private ImageShowAdapter.ChangeHeight changeHeight = new ImageShowAdapter.ChangeHeight() {
        @Override
        public void change(int position, int height) {
            ViewGroup.LayoutParams layoutParams=rv_workimg.getLayoutManager().findViewByPosition(position).findViewById(R.id.viewPager).getLayoutParams();
            layoutParams.height=height;
            rv_workimg.getLayoutManager().findViewByPosition(position).findViewById(R.id.viewPager).setLayoutParams(layoutParams);
            System.out.println(position);
            System.out.println(height);
        }
    };
*/
    /*private ImageShowAdapter.ImageLoadingListener loadingListener=new ImageShowAdapter.ImageLoadingListener() {
        @Override
        public void LoadingComplete(int vp_index,String s, View view, Bitmap bitmap) {
            ViewGroup.LayoutParams layoutParams=rv_workimg.getLayoutManager().findViewByPosition(vp_index).findViewById(R.id.viewPager).getLayoutParams();
            layoutParams.height=200;
            rv_workimg.getLayoutManager().findViewByPosition(vp_index).findViewById(R.id.viewPager).setLayoutParams(layoutParams);
            System.out.println(1);
        }
    };*/
    public void changeTextIndex(int vp_index,int index){
        String s=(index+1)+"/"+lws.get(vp_index).getResourcesList().size();
        View v= rv_workimg.getLayoutManager().findViewByPosition(vp_index);
        TextView tv=v.findViewById(R.id.tv_index);
        tv.setText(s);
    }
    public void changeTips(int vp_index,int index) {
        ((LinearLayout) rv_workimg.getLayoutManager().findViewByPosition(vp_index).findViewById(R.id.viewGroup)).removeAllViews();
        for (int i=0; i < lws.get(vp_index).getResourcesList().size(); i++) {
            ImageView imageView=new ImageView(this);
            if (i == index) {
                imageView.setImageResource(R.drawable.page_indicator_focused);
            } else {
                imageView.setImageResource(R.drawable.page_indicator_unfocused);
            }
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin=5;
            layoutParams.rightMargin=5;
            ((LinearLayout) rv_workimg.getLayoutManager().findViewByPosition(vp_index).findViewById(R.id.viewGroup)).addView(imageView, layoutParams);
        }
    }
    //点赞
    public void zanWork(int position){
        int user_id=lws.get(position).getWork_author();
        int work_id=lws.get(position).getWork_id();
        int is_id=lws.get(position).getWork_island();
        /*int currentUserId=Integer.parseInt(((AppContext)getApplicationContext()).getUserId());
        int likeState=lws.get(position).getLikeState();
        if(currentUserId==user_id){   //不能给自己点赞
            return;
        }
        if(likeState==1){  //点赞过了
            return;
        }*/
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
                public void success(Call call, Response response) throws IOException,JSONException{
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
                                ((ImageView) rv_workimg.getLayoutManager().findViewByPosition(position).findViewById(R.id.iv_zan)).setImageResource(R.mipmap.zan1);
                                ((TextView) rv_workimg.getLayoutManager().findViewByPosition(position).findViewById(R.id.tv_zan)).setText(String.valueOf(lws.get(position).getWork_like_count()==0?"":lws.get(position).getWork_like_count()));
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
                                ((ImageView) rv_workimg.getLayoutManager().findViewByPosition(position).findViewById(R.id.iv_zan)).setImageResource(R.mipmap.zaned);
                                ((TextView) rv_workimg.getLayoutManager().findViewByPosition(position).findViewById(R.id.tv_zan)).setText(String.valueOf(lws.get(position).getWork_like_count()));
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
    /*****滑动到底部****/
    public  void setGlideToBottom(){
        rv_workimg.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewLayoutManager.invalidateSpanAssignments();
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    try {
                        //请求数据
                        requireImageShowMore();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    public void requireImageShowMore(){
       int is_id= workShow.getWork_island();
       if(is_id==-1){
           //请求广场数据  根据地理位置查找
           requireByLoc();
       }else{
           String work_category=workShow.getWork_island_category();
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
                                            adapter.notifyDataSetChanged();
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
                                            adapter.notifyDataSetChanged();
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
  /*  Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                adapter.notifyDataSetChanged();
            }
        }
    };*/
  public void gotoUserPage(Integer position) {
      Intent intent = new Intent();
      intent.putExtra("user_id", String.valueOf(lws.get(position).getWork_author()));
      intent.setClass(ImageShowActivity.this, MeActivity.class);
      startActivity(intent);
      overridePendingTransition(0,0);
  }
  //需要先请求海岛信息 然后跳转
  public void gotoIslandPage(Integer position){
      Integer is_id=lws.get(position).getWork_island();
      Intent intent =new Intent();
      intent.putExtra("is_id",is_id);
      intent.setClass(ImageShowActivity.this, IslandShowActivity.class);
      startActivity(intent);
  }
  public void followPerson(Integer position) throws Exception {
      Integer fstate=lws.get(position).getFriendState();
      if(fstate==null||fstate==-1||fstate==1){ //做的是关注操作 null:未关注 1：取消了关注 -1 默认未关注
          NetUtils netUtils=NetUtils.getInstance();
          String token=((AppContext)getApplicationContext()).getToken();
          Map map=new HashMap();
          map.put("user_id1",ctx.getUserId());
          map.put("user_id2",String.valueOf(lws.get(position).getWork_author()));
          netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_FRIEND_BASE + "/FollowUser",token, map, new NetUtils.MyNetCall() {
              @Override
              public void success(Call call, Response response) throws IOException,JSONException{
                  String ret=response.body().string();
                  JSONObject json = new JSONObject(ret);
                  //如果token过期,就重新登陆
                  if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                      startActivity(new Intent(ImageShowActivity.this, LoginActivity.class));
                      finish();
                      return;
                  }
                  String  FollowUser=json.getString("FollowUser");
                  if(FollowUser!=null&&FollowUser.equals("success")) {
                      runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              for(int i=0;i<lws.size();i++){
                                  if(lws.get(i).getWork_author().equals(lws.get(position).getWork_author()))
                                      lws.get(i).setFriendState(0);
                              }
                              adapter.notifyDataSetChanged();
                          }
                      });
                  }
              }
              @Override
              public void failed(Call call, IOException e) {}
          });
      }else{      //取消关注操作 0：说明是关注了且是正常状态
          showNormalDialog(position);
      }
  }
  public void followIsland(){
  }


    private void showNormalDialog(Integer position){
        AlertDialog normalDialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.warn)
                .setTitle("取关后,不再是好友")
                //.setMessage("test")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            NetUtils netUtils=NetUtils.getInstance();
                            String token=((AppContext)getApplicationContext()).getToken();
                            Map map=new HashMap();
                            map.put("user_id1",ctx.getUserId());
                            map.put("user_id2",String.valueOf(lws.get(position).getWork_author()));
                            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_FRIEND_BASE + "/delFriend",token, map, new NetUtils.MyNetCall() {
                                @Override
                                public void success(Call call, Response response) throws IOException,JSONException{
                                    String ret=response.body().string();
                                    JSONObject json = new JSONObject(ret);
                                    //如果token过期,就重新登陆
                                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                                        startActivity(new Intent(ImageShowActivity.this, LoginActivity.class));
                                        finish();
                                        return;
                                    }
                                    String  delstate=json.getString("delstate");
                                    if(delstate!=null&&delstate.equals("success")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                for(int i=0;i<lws.size();i++){
                                                    if(lws.get(i).getWork_author().equals(lws.get(position).getWork_author()))
                                                        lws.get(i).setFriendState(1);
                                                }
                                                adapter.notifyDataSetChanged();
                                            }
                                        });
                                    }
                                }
                                @Override
                                public void failed(Call call, IOException e) {}
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        // 显示
        normalDialog.show();
        normalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0b090e"));
        normalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(15);
        normalDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#0b090e"));
        normalDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(15);
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
                                adapter.notifyDataSetChanged();
                                //滚动监听
                                setGlideToBottom();
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
