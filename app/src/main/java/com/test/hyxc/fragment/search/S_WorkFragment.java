package com.test.hyxc.fragment.search;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.adapter.WorkShowAdapter;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.fragment.RecommendFragment;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.personal.InformationActivity;
import com.test.hyxc.page.workshow.ImageShowActivity;
import com.test.hyxc.page.workshow.Page2Activity;

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

public class S_WorkFragment extends Fragment {
    private RecyclerView recycler_view;
    private StaggeredGridLayoutManager recyclerViewLayoutManager;
    private ArrayList<WorkShow> lws = new ArrayList<>();
    private WorkShowAdapter adapter;
    private int adapterPosition=0;
    String searchText;
    TextView tv_nothing;
    int pageNum=1;
    int pageSize = 20;
    boolean end =false;
    S_WorkFragment.ReceiveBroadCast receiveBroadCast;
    public void setSearchText(String searchText){
        this.searchText = searchText;
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imap_slide_search_work, container, false);
        recycler_view=view.findViewById(R.id.recycler_view);
        tv_nothing=view.findViewById(R.id.tv_nothing);
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
        //注册广播
        receiveBroadCast=new S_WorkFragment.ReceiveBroadCast();
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
        if(!end && searchText!=null && !"".equals(searchText)) {
            NetUtils netUtils = NetUtils.getInstance();
            String token = ((AppContext) getActivity().getApplicationContext()).getToken();
            Map map = new HashMap();
            map.put("keyword", searchText);
            map.put("page_First", String.valueOf(pageNum));
            map.put("page_Size", String.valueOf(pageSize));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_SEARCH_BASE + "/searchWork", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret = response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        return;
                    }
                    final Gson gson = new Gson();
                    JSONArray listworkshowdetail = json.getJSONArray("list");
                    JsonParser parser = new JsonParser();
                    final JsonArray Jarray = parser.parse(listworkshowdetail.toString()).getAsJsonArray();
                    if (Jarray != null && !Jarray.equals("[]") &&Jarray.size()>0) {
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
                    } else {
                        end =true; //没有数据了
                        if(pageNum ==1){ //说明没有符合条件的数据
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_nothing.setVisibility(View.VISIBLE);
                                    recycler_view.setVisibility(View.GONE);
                                }
                            });
                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.shortToast(getActivity(),"没有更多动态信息了~");
                                }
                            });
                        }
                    }
                    pageNum++;
                }
                @Override
                public void failed(Call call, IOException e) {
                }
            });
        }
    }
    public void setGlideToBottom() {
        recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
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
            }

        });
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    //下拉刷新
    /*private void initPullRefresh() {
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
    }*/
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
                    String likeState=json.getString("likeState");
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