package com.test.hyxc.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.adapter.IslandShowAdapter;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.page.island.IslandSearchActivity;
import com.test.hyxc.page.island.IslandShowActivity;
import com.test.hyxc.page.search.SearchActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.NetUtils;

public class S_IslandFragment extends Fragment {
    RecyclerView recycler_view;
    String url="";
    private StaggeredGridLayoutManager recyclerViewLayoutManager;
    private ArrayList<Island> lis = new ArrayList<>();
    private IslandShowAdapter adapter;
    private int adapterPosition=0;
    String searchText="";
    int pageNum =1;
    int pageSize =20;
    boolean end =false;
    TextView tv_nothing;
    public void setSearchText(String searchText){
        this.searchText = searchText;
    }
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imap_slide_island_search_keyword, container, false);
        url=((AppContext)getActivity().getApplicationContext()).getOssConfig().getHost()+"/";
        initView(view);
        return view;
    }
    public void initView(View view){
        recycler_view=view.findViewById(R.id.recycler_view);
        tv_nothing = view.findViewById(R.id.tv_nothing);
        recycler_view.setAnimation(null);
        try {
            getData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        adapter=new IslandShowAdapter(lis,getActivity());
        //这里能防止位置错乱
        recyclerViewLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recycler_view.setPadding(0,0,0,0);
        recycler_view.setLayoutManager(recyclerViewLayoutManager);
        recycler_view.setAdapter(adapter);
        recycler_view.setItemAnimator(null);
        setGlideToBottom();
        //设置点击事件
        adapter.setOnItemClickListener(MyItemClickListener);
    }
    /**item＋item里的控件点击监听事件 */
    private IslandShowAdapter.OnItemClickListener MyItemClickListener = new IslandShowAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, IslandShowAdapter.ViewName viewName, int position) {
            switch (v.getId()){
                default:
                    Island island=lis.get(position);
                    Intent intent =new Intent();
                    intent.putExtra("island",island);
                    intent.setClass(getActivity(), IslandShowActivity.class);
                    startActivity(intent);
                    break;
            }
        }
        @Override
        public void onItemLongClick(View v) {}
    };


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
                        getData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
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

    public void getData() throws Exception {
        if(!end && searchText!=null && !"".equals(searchText)) {
            NetUtils netUtils = NetUtils.getInstance();
            String token = ((AppContext) getActivity().getApplicationContext()).getToken();
            Map map = new HashMap();
            map.put("keyword", searchText);
            map.put("page_First", String.valueOf(pageNum));
            map.put("page_Size", String.valueOf(pageSize));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_SEARCH_BASE + "/searchIsland", token, map, new NetUtils.MyNetCall() {
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
                    JSONArray listislandshowdetail = json.getJSONArray("list");
                    JsonParser parser = new JsonParser();
                    final JsonArray Jarray = parser.parse(listislandshowdetail.toString()).getAsJsonArray();
                    if (Jarray != null && !Jarray.equals("[]") && Jarray.size() > 0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (JsonElement obj : Jarray) {
                                    Island island = gson.fromJson(obj, Island.class);
                                    lis.add(island);
                                    adapter.notifyItemInserted(adapterPosition++);
                                }
                            }
                        });
                    }else{
                        end = true;
                        if(pageNum ==1) {
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
                                    ToastUtil.shortToast(getActivity(),"没有更多海岛信息了~");
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
}
