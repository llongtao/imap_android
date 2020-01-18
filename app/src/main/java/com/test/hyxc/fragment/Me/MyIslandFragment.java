package com.test.hyxc.fragment.Me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.adapter.MyIslandAdapter;
import com.test.hyxc.adapter.PoiAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.IslandDetail;
import com.test.hyxc.page.personal.MeActivity;

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
import tools.BaiduMap.service.LocationService;
import tools.LoadingDialog;
import tools.NetUtils;

public class MyIslandFragment extends Fragment {
    int user_id=-1;
    private List<IslandDetail> listIsland=new ArrayList<>();
    private MyIslandAdapter adapter;
    ListView lv_island_list;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.imap_my_island_list, container, false);
        user_id=((MeActivity)getActivity()).user_id;
        adapter=new MyIslandAdapter(getActivity().getApplicationContext(),listIsland);
        lv_island_list=view.findViewById(R.id.lv_island_list);
        lv_island_list.setAdapter(adapter);
        lv_island_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //adapter.notifyDataSetChanged();
            }
        });
        lv_island_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //判断滑动到底部
                   /* if(view.getLastVisiblePosition()==view.getCount()-1){

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }).start();
                    }*/
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        try {
            getIsland();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private void getIsland() throws Exception {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getActivity().getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("user_id", String.valueOf(user_id));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_RESIDENT_BASE + "/findIslandByUserId", token, map, new NetUtils.MyNetCall() {
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
                    if (json.has("listIR") && null != json.get("listIR")) {
                        JSONArray listIR = json.getJSONArray("listIR");
                        JsonParser parser = new JsonParser();
                        final JsonArray Jarray = parser.parse(listIR.toString()).getAsJsonArray();
                        if (Jarray != null && !Jarray.equals("[]") && Jarray.size() > 0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (JsonElement obj : Jarray) {
                                        IslandDetail islandDetail = gson.fromJson(obj, IslandDetail.class);
                                        islandDetail.setRes_island_name(islandDetail.getIs_name());
                                        listIsland.add(islandDetail);
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                    }
                }
                @Override
                public void failed(Call call, IOException e) {
                }
            });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listIsland.clear();
    }
}
