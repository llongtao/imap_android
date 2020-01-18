package com.test.hyxc.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.loopj.android.http.RequestParams;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.MainActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.WorkShowAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.personal.InformationActivity;
import com.test.hyxc.page.workshow.ImageShowActivity;
import com.test.hyxc.page.workshow.Page2Activity;
import com.test.hyxc.page.workshow.VideoShowActivity;
import com.test.hyxc.page.workshow.VideoSurfaceDemo;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.GlobalConfig;
import tools.NetUtils;

public class RecommendFragment extends Fragment {
    private RecyclerView recycler_view;
    private StaggeredGridLayoutManager recyclerViewLayoutManager;
    private ArrayList<WorkShow> lws = new ArrayList<>();
    private WorkShowAdapter adapter;
    private int adapterPosition=0;
    private View view1, view2, view3, view4,view5,view6,view7,view8,view9;
    private List<String> mTitleList = new ArrayList<String>();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private List<View> mViewList = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    ReceiveBroadCast receiveBroadCast;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imap_slide_recommend, container, false);
        recycler_view=view.findViewById(R.id.recycler_view);
        recycler_view.setAnimation(null);
        try {
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter=new WorkShowAdapter(lws,getActivity());
        //这里能防止位置错乱
        recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recycler_view.setPadding(0,0,0,0);
        recycler_view.setLayoutManager(recyclerViewLayoutManager);
        recycler_view.setAdapter(adapter);
        //取消动画
        recycler_view.setItemAnimator(null);
        setGlideToBottom();
        //设置点击事件
        adapter.setOnItemClickListener(MyItemClickListener);
        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
       // ViewGroup.LayoutParams lp=mSwipeRefreshLayout.getLayoutParams();
        //mSwipeRefreshLayout.setLayoutParams(mSwipeRefreshLayout.getLayoutParams().);
        initPullRefresh();
        initTabTitle(inflater,view);
        //注册广播
        receiveBroadCast=new ReceiveBroadCast();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(GlobalConfig.likeCountChange);
        getActivity().registerReceiver(receiveBroadCast,intentFilter);
        return view;
    }
    /**item＋item里的控件点击监听事件 */
    private WorkShowAdapter.OnItemClickListener MyItemClickListener = new WorkShowAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, WorkShowAdapter.ViewName viewName, int position) {
            switch (v.getId()){
                case R.id.ll_zan:
                        zanWork(position);
                    break;
                default:
                    if(((AppContext)getActivity().getApplicationContext()).getNickname()==null||"".equals(((AppContext)getActivity().getApplicationContext()).getNickname())){
                        Intent intent=new Intent(getActivity(), InformationActivity.class);
                        startActivity(intent);
                    }else {
                        WorkShow workShow = lws.get(position);
                        if (workShow.getWork_type() == 0) {
                            Intent intent = new Intent(getActivity(), ImageShowActivity.class);
                            intent.putExtra("workshow", workShow);
                            startActivity(intent);
                        } else if (workShow.getWork_type() == 1) {
                       /* Intent intent=new Intent(getActivity(), VideoShowActivity.class);
                        intent.putExtra("workshow", workShow);
                        startActivity(intent);*/
                            Intent intent = new Intent(getActivity(), Page2Activity.class);
                            intent.putExtra("workshow", workShow);
                            startActivity(intent);
                        }
                    }
                    break;
            }
        }
        @Override
        public void onItemLongClick(View v) {}
    };
    private void initData() throws Exception {
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getActivity().getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("workNum","20");
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/getWorkGoodAndRand",token, map, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException,JSONException{
                String ret=response.body().string();
                JSONObject json = new JSONObject(ret);
                //如果token过期,就重新登陆
                if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                    return;
                }
                final Gson gson = new Gson();
                JSONArray listworkshowdetail=json.getJSONArray("listworkshowdetail");
                JsonParser parser = new JsonParser();
                final JsonArray Jarray = parser.parse(listworkshowdetail.toString()).getAsJsonArray();
                if(Jarray!=null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        for (JsonElement obj : Jarray) {
                            WorkShow ws = gson.fromJson(obj, WorkShow.class);
                            lws.add(ws);
                            adapter.notifyItemInserted(adapterPosition++);
                        }
                        }
                    });
                }
            }
            @Override
            public void failed(Call call, IOException e) {
               // System.out.println("1111");
            }
        });
    }
    public void setGlideToBottom() {
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //防止第一行留白问题
                recyclerViewLayoutManager.invalidateSpanAssignments();
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isSlideToBottom(recyclerView)) {
                    //滑动到底部的业务逻辑
                    try {
                        initData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
                //快速到顶部空白问题
                /*StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                int[] firstVisibleItem = null;
                firstVisibleItem = layoutManager.findFirstVisibleItemPositions(firstVisibleItem);
                if(firstVisibleItem!=null&&firstVisibleItem[0]!=0){
                    //如果第一桢已经隐藏了
                    rv_first_show=false;
                }
                if (firstVisibleItem != null && firstVisibleItem[0] == 0) {
                    if(rv_first_show==false) {
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                            *//*for(int i=0;i<10;i++){
                                adapter.notifyItemChanged(i);
                            }*//*
                            rv_first_show=true;
                        }
                    }
                }*/
            }

        });
    }
    protected boolean isSlideToTop(RecyclerView recyclerView){
        if (recyclerView == null) return false;
        if ( recyclerView.computeVerticalScrollOffset() <= 0)
            return true;
        return false;
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    //tabbar
    private View tab_icon(String name){
        View newtab =  LayoutInflater.from(getActivity()).inflate(R.layout.tab_item,null);
        TextView tv = newtab.findViewById(R.id.tabtext);
        tv.setText(name);
        return newtab;
    }
    public void initTabTitle(LayoutInflater inflater,View view){
        //标题添加页卡标题
        mTitleList.add("篮球");
        mTitleList.add("动漫");
        mTitleList.add("街舞");
        mTitleList.add("文学");
        mTitleList.add("篮球");
        mTitleList.add("动漫");
        mTitleList.add("街舞");
        mTitleList.add("文学");
        mTitleList.add("篮球");
        mTitleList.add("动漫");
        mTitleList.add("街舞");
        mTitleList.add("文学");
        mViewPager = view.findViewById(R.id.vp_view);
        mViewPager.canScrollHorizontally(1);
        mTabLayout =  view.findViewById(R.id.tabs);
        view1 = tab_icon(mTitleList.get(0));
        view2 = tab_icon(mTitleList.get(1));
        view3 = tab_icon(mTitleList.get(2));
        view4 = tab_icon(mTitleList.get(3));
        view5 = tab_icon(mTitleList.get(4));
        view6 = tab_icon(mTitleList.get(5));
        view7 = tab_icon(mTitleList.get(6));
        view8 = tab_icon(mTitleList.get(7));
        view9 = tab_icon(mTitleList.get(8));
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        mViewList.add(view5);
        mViewList.add(view6);
        mViewList.add(view7);
        mViewList.add(view8);
        mViewList.add(view9);
        //添加tab选项卡，默认第一个选中
       // mTabLayout.addTab( mTabLayout.newTab().setCustomView(view1), true);
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view1));
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view2));
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view3));
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view4));
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view5));
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view6));
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view7));
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view8));
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view9));

        //Tablayout自定义view绑定ViewPager 自定义view时使用 tabLayout.setupWithViewPager(viewPager);方法关联无效，通过以下方法进行viewpager和tablayout的关联
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Toast.makeText(getContext(), "选中的"+((TextView)tab.getCustomView().findViewById(R.id.tabtext)).getText(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    //下拉刷新
    private void initPullRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lws.clear();
                        adapter.map.clear();
                        adapterPosition=0;
                        adapter.notifyDataSetChanged();
                        try {
                            initData();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 200);
            }
        });
    }
    //作品点赞
    public void zanWork(int position){
        int user_id=lws.get(position).getWork_author();
        int work_id=lws.get(position).getWork_id();
        int likeState=lws.get(position).getLikeState();
        int is_id=lws.get(position).getWork_island();
        /*int currentUserId=Integer.parseInt(((AppContext)getActivity().getApplicationContext()).getUserId());
        if(currentUserId==user_id){   //不能给自己点赞
            return;
        }*/
        /*if(likeState==1){  //点赞过了
            return;
        }*/
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getActivity().getApplicationContext()).getToken();
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
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        return;
                    }
                    String likeState=json.has("likeState") ? json.getString("likeState") :"success" ;
                    if(likeState.equals("ever_done")){ //如果点赞过了 就改成取消状态
                        lws.get(position).setLikeState(0);
                        lws.get(position).setWork_like_count(lws.get(position).getWork_like_count()-1<0?0:lws.get(position).getWork_like_count()-1);
                        //发送广播修改点赞数量 和点赞状态
                        Intent intent =new Intent();
                        intent.setAction(GlobalConfig.likeCountChange);
                        intent.putExtra("work_id",work_id);
                        intent.putExtra("likeCount",lws.get(position).getWork_like_count());
                        intent.putExtra("likeState",0);
                        getActivity().sendBroadcast(intent);
                    }else if(likeState.equals("success")){
                        lws.get(position).setLikeState(1);
                        lws.get(position).setWork_like_count(lws.get(position).getWork_like_count()+1);
                        /*getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView) recycler_view.getLayoutManager().findViewByPosition(position).findViewById(R.id.iv_zan)).setImageResource(R.mipmap.zaned);
                                ((TextView) recycler_view.getLayoutManager().findViewByPosition(position).findViewById(R.id.tv_zan)).setText(String.valueOf(lws.get(position).getWork_like_count()));
                            }
                        });*/
                        //发送广播修改点赞数量 和点赞状态
                        Intent intent =new Intent();
                        intent.setAction(GlobalConfig.likeCountChange);
                        intent.putExtra("work_id",work_id);
                        intent.putExtra("likeCount",lws.get(position).getWork_like_count());
                        intent.putExtra("likeState",1);
                        getActivity().sendBroadcast(intent);
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
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter.map.clear();
        lws.clear();
        //注销广播
        getActivity().unregisterReceiver(receiveBroadCast);
    }
    class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //得到广播中得到的数据  
            //Integer  index = intent.getExtras().getInt("index");
            Integer work_id = intent.getExtras().getInt("work_id");
            Integer likeCount=intent.getExtras().getInt("likeCount");
            Integer likeState=intent.getExtras().getInt("likeState");
            for(int i =0;i<lws.size();i++){
                if(lws.get(i).getWork_id().equals(work_id)){
                    lws.get(i).setLikeState(likeState);
                    lws.get(i).setWork_like_count(likeCount);
                    int finalI = i;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyItemChanged(finalI);
                        }
                    });
                }
            }


        }
    }
}