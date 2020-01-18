package com.test.hyxc.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.adapter.ActivityLunboAdapter;
import com.test.hyxc.adapter.ActivityCityAdapter;
import com.test.hyxc.adapter.ActivityUniAdapter;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.manager.SmoothLinearLayoutManager;
import com.test.hyxc.model.ActivityShow;
import com.test.hyxc.page.activity.ActivityDetailShow;
import com.test.hyxc.view.BannerIndicator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.NetUtils;

public class ActivityFragment extends Fragment {
    private List<String> mTitleList = new ArrayList<String>();
    private LinearLayout ll_tabtitle;
    private LinearLayout ll_lunbo;
    private RecyclerView rv_recommend;
    private List<ActivityShow> las=new ArrayList<>();
    private ActivityLunboAdapter adapter;
    private LinearLayoutManager recyclerViewLayoutManager;
    private int lunboPosition=0;
    private ActivityLunboAdapter.OnItemClickListener MyOnItemClickListener;
    //本校活动
    private LinearLayout ll_uni;
    private LinearLayout ll_uniactivity_rv;
    private List<ActivityShow> lasUni = new ArrayList<>();
    private RecyclerView rv_uniactivity;
    private ActivityUniAdapter adapter1;
    private LinearLayoutManager recyclerViewLayoutManager1;
    int pageNumUni = 1,pageSizeUni =10;
    boolean uniEnd = false;
    //附近活动
    private LinearLayout ll_near;
    private LinearLayout ll_nearby_activity_rv;
    private List<ActivityShow> lasCity = new ArrayList<>();
    private RecyclerView rv_nearby_activity;
    private ActivityCityAdapter adapter2;
    private LinearLayoutManager recyclerViewLayoutManager2;
    int pageNumCity = 1,pageSizeCity = 10;
    boolean cityEnd = false;
    BannerIndicator bannerIndicator;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imap_slide_activity, container, false);
        initLunboData();
        //本校活动
        initUniData();
        //附近活动
        initCityData();
        //推荐轮播
        ll_uni = view.findViewById(R.id.ll_uni);
        ll_lunbo = view.findViewById(R.id.ll_lunbo);
        rv_recommend = view.findViewById(R.id.rv_recommend);
        adapter = new ActivityLunboAdapter((ArrayList<ActivityShow>) las, getContext());
        recyclerViewLayoutManager = new SmoothLinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_recommend.setLayoutManager(recyclerViewLayoutManager);
        rv_recommend.setHasFixedSize(true);
        rv_recommend.setAdapter(adapter);
        rv_recommend.scrollToPosition(las.size() * 10);
        //循环轮播
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv_recommend);
        //轮播点
        bannerIndicator = view.findViewById(R.id.indicator);
        //本校活动
        ll_uniactivity_rv=view.findViewById(R.id.ll_uniactivity_rv);
        rv_uniactivity=view.findViewById(R.id.rv_university_activity);
        adapter1 = new ActivityUniAdapter((ArrayList<ActivityShow>) lasUni, getContext());
        recyclerViewLayoutManager1= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_uniactivity.setLayoutManager(recyclerViewLayoutManager1);
        rv_uniactivity.setHasFixedSize(true);
        rv_uniactivity.setAdapter(adapter1);
        rv_uniactivity.scrollToPosition(0);
        rv_uniactivity.setAnimation(null);
        rv_uniactivity.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isSlideToBottom(recyclerView)) {
                    //滑动到底部的业务逻辑
                    try {
                        initUniData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        setUniAdapterListener(adapter1);
        //附近活动
        ll_nearby_activity_rv=view.findViewById(R.id.ll_nearby_activity_rv);
        rv_nearby_activity=view.findViewById(R.id.rv_nearby_activity);
        adapter2 = new ActivityCityAdapter((ArrayList<ActivityShow>) lasCity, getContext());
        recyclerViewLayoutManager2= new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        rv_nearby_activity.setLayoutManager(recyclerViewLayoutManager2);
        rv_nearby_activity.setHasFixedSize(true);
        rv_nearby_activity.setAdapter(adapter2);
        rv_nearby_activity.scrollToPosition(0);
        rv_nearby_activity.setAnimation(null);
        rv_nearby_activity.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isSlideToBottom(recyclerView)) {
                    //滑动到底部的业务逻辑
                    try {
                        initCityData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        setCityAdapterListener(adapter2);
        return view;
    }
    //轮播图片
    public  void initLunboData(){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getActivity().getApplicationContext()).getToken();
        Map map=new HashMap();
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_BASE + "/recommendActByUserId",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException {
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        return;
                    }
                    final Gson gson = new Gson();
                    JSONArray activityDetailList=json.getJSONArray("activityDetailList");
                    JsonParser parser = new JsonParser();
                    final JsonArray Jarray = parser.parse(activityDetailList.toString()).getAsJsonArray();
                    if(Jarray!=null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (JsonElement obj : Jarray) {
                                    ActivityShow activityShow = gson.fromJson(obj, ActivityShow.class);
                                    las.add(activityShow);
                                }
                                adapter.notifyDataSetChanged();
                                bannerIndicator.setNumber(las.size());
                                //自动轮播
                                ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
                                scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                                    @Override
                                    public void run() {
                                        rv_recommend.smoothScrollToPosition(recyclerViewLayoutManager.findFirstVisibleItemPosition() + 1);
                                    }
                                }, 5000, 5000, TimeUnit.MILLISECONDS);
                                rv_recommend.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                            if(las.size()!=0) {
                                                int i = recyclerViewLayoutManager.findFirstVisibleItemPosition() % las.size();
                                                //得到指示器红点的位置
                                                bannerIndicator.setPosition(i);
                                                lunboPosition = i;
                                            }
                                        }
                                    }
                                });
                                //为轮播图添加点击事件监听
                                setLunboAdapterListener(adapter);
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
    public void setLunboAdapterListener(ActivityLunboAdapter adapter){
        adapter.setOnItemClickListener(new ActivityLunboAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                lunboPosition=position%las.size();
                Intent intent =new Intent();
                intent.putExtra("act",las.get(lunboPosition));
                intent.setClass(getActivity(),ActivityDetailShow.class);
                getActivity().startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new ActivityLunboAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
               // lunboPosition=position%las.size();
            }
        });
    }
    //本校活动点击监听事件
    public void setCityAdapterListener(ActivityCityAdapter adapter2){
        adapter2.setOnItemClickListener(new ActivityCityAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                int p =position%lasCity.size();
                Intent intent =new Intent();
                intent.putExtra("act",lasCity.get(p));
                intent.setClass(getActivity(),ActivityDetailShow.class);
                getActivity().startActivity(intent);
            }
        });
        adapter2.setOnItemLongClickListener(new ActivityCityAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
            }
        });
    }
    //本市活动点击监听事件
    public void setUniAdapterListener(ActivityUniAdapter adapter1){
        adapter1.setOnItemClickListener(new ActivityUniAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                int p =position%lasUni.size();
                Intent intent =new Intent();
                intent.putExtra("act",lasUni.get(p));
                intent.setClass(getActivity(),ActivityDetailShow.class);
                getActivity().startActivity(intent);
            }
        });
        adapter1.setOnItemLongClickListener(new ActivityUniAdapter.OnItemLongClickListener() {
            @Override
            public void onClick(int position) {
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        las.clear();
        lasUni.clear();
        lasCity.clear();
    }
    //本校活动
    public void initUniData(){
        if(!uniEnd) { //如果未到底 就发送请求
            NetUtils netUtils = NetUtils.getInstance();
            String token = ((AppContext) getActivity().getApplicationContext()).getToken();
            Map map = new HashMap();
            map.put("page_First", String.valueOf(pageNumUni));
            map.put("page_Size", String.valueOf(pageSizeUni));
            map.put("orderby", "time");
            try {
                netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_BASE + "/getMyUniActivity", token, map, new NetUtils.MyNetCall() {
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
                        JSONArray listact = json.getJSONArray("listact");
                        JsonParser parser = new JsonParser();
                        final JsonArray Jarray = parser.parse(listact.toString()).getAsJsonArray();
                        if (Jarray != null && Jarray.size() > 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ll_uni.setVisibility(View.VISIBLE);
                                    ll_uniactivity_rv.setVisibility(View.VISIBLE);
                                    pageNumUni++;
                                    for (JsonElement obj : Jarray) {
                                        ActivityShow activityShow = gson.fromJson(obj, ActivityShow.class);
                                        lasUni.add(activityShow);
                                    }
                                    adapter1.notifyDataSetChanged();
                                }
                            });
                        } else {
                            uniEnd = true; //到底了
//                            getActivity().runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast toast=Toast.makeText(getContext(),"没有更多了~",Toast.LENGTH_SHORT);
//                                    toast.setGravity(Gravity.TOP| Gravity.CENTER,0,260);
//                                    toast.show();
//                                }
//                            });
                        }
                    }
                    @Override
                    public void failed(Call call, IOException e) {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    //本市活动
    public void initCityData(){
        if(!cityEnd) {
            String city = ((AppContext) getActivity().getApplicationContext()).getCity();
            NetUtils netUtils = NetUtils.getInstance();
            String token = ((AppContext) getActivity().getApplicationContext()).getToken();
            Map map = new HashMap();
            map.put("act_city", city);
            map.put("page_First", String.valueOf(pageNumCity));
            map.put("page_Size", String.valueOf(pageSizeCity));
            map.put("orderby", "time");
            try {
                netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_BASE + "/findActByConditionDescPage", token, map, new NetUtils.MyNetCall() {
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
                        JSONArray listact = json.getJSONArray("listact");
                        JsonParser parser = new JsonParser();
                        final JsonArray Jarray = parser.parse(listact.toString()).getAsJsonArray();
                        if (Jarray != null && Jarray.size() > 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    pageNumCity++;
                                    for (JsonElement obj : Jarray) {
                                        ActivityShow activityShow = gson.fromJson(obj, ActivityShow.class);
                                        lasCity.add(activityShow);
                                    }
                                    adapter2.notifyDataSetChanged();
                                }
                            });
                        } else {
                            cityEnd=true;
                            /*getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast = Toast.makeText(getContext(), "没有更多了~", Toast.LENGTH_SHORT);
                                    toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 260);
                                    toast.show();
                                }
                            });*/
                        }
                    }

                    @Override
                    public void failed(Call call, IOException e) {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }


}