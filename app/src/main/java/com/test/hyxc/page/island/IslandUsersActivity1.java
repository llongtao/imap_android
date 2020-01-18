package com.test.hyxc.page.island;

import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.IslandUsersAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.IslandResidentUserInfo;

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
import tools.NetUtils;

/**
 * Created by WWW on 2019/2/8.
 */

public class IslandUsersActivity1 extends BaseActivity   {
    private List<IslandResidentUserInfo> userInfoList=null;
    Button btn_users_back;
    TextView tv_title;
    Island island;
    ListView lv_island_users;
    private IslandUsersAdapter adapter;
    int page_First=1;
    int page_Size=20;
    public int relation=-1; //-1 or 0：无关系 1：普通居民 2：管理员 3：岛主

    @Override
    protected int getLayoutID() {
        return R.layout.imap_island_users;
    }

    @Override
    protected void initListener() {}
    @Override
    protected void initView() {
        userInfoList = new ArrayList<>();
        island = (Island) getIntent().getSerializableExtra("island");
        if(island!=null)
             findIslandById(island.getIs_id());
        btn_users_back=f(R.id.btn_users_back);
        tv_title=f(R.id.tv_title);
        lv_island_users=f(R.id.lv_island_users);
        adapter=new IslandUsersAdapter(this,userInfoList);
        btn_users_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText(island.getIs_name()+" ("+island.getIs_people_current()+")");
        lv_island_users.setAdapter(adapter);
        lv_island_users.setDividerHeight(1);
        lv_island_users.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //判断滑动到底部
                    if(view.getLastVisiblePosition()==view.getCount()-1){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    findUsersByIsId(island.getIs_id());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        try {
            findUsersByIsId(island.getIs_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void initData() {}
    //根据海岛id 查找用户信息
    public void findUsersByIsId(int is_id) throws Exception {
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        map.put("page_First",String.valueOf(page_First));
        map.put("page_Size",String.valueOf(page_Size));
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_RESIDENT_BASE + "/findIRByIsId",token, map, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException,JSONException {
                String ret=response.body().string();
                JSONObject json = new JSONObject(ret);
                //如果token过期,就重新登陆
                if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                    startActivity(new Intent(IslandUsersActivity1.this, LoginActivity.class));
                    finish();
                    return;
                }
                final Gson gson = new Gson();
                JSONArray listIR=json.getJSONArray("listIR");
                JsonParser parser = new JsonParser();
                final JsonArray Jarray = parser.parse(listIR.toString()).getAsJsonArray();
                if(Jarray!=null&&Jarray.size()>0) {
                    page_First++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (JsonElement obj : Jarray) {
                                IslandResidentUserInfo  irui = gson.fromJson(obj, IslandResidentUserInfo.class);
                                userInfoList.add(irui);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
            @Override
            public void failed(Call call, IOException e) {}
        });
    }

    // 确定用户和海岛关系
    public void findIslandById(Integer is_id){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/findIslandById",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException{
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(IslandUsersActivity1.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    //关系 用户跟海岛的关系 0：无关 1：居民 2：管理员 3：岛主
                    relation=json.has("relation") ? json.getInt("relation") : 0;
                    if(relation == -1 || relation == 0 || relation == 1){
                        for(int i = 0;i<userInfoList.size();i++){
                            userInfoList.get(i).setShowMore(false);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
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


}
