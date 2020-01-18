package com.test.hyxc.page.activity;

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
import com.test.hyxc.adapter.ActSignUserAdapter;
import com.test.hyxc.adapter.IslandUsersAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.ActivityShow;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.IslandResidentUserInfo;
import com.test.hyxc.model.UserInfo;

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

public class ActSignUsersActivity extends BaseActivity   {
    private List<UserInfo> userInfoList=null;
    Button btn_users_back;
    TextView tv_title;
    ActivityShow  activityShow;
    ListView lv_act_sign_users;
    private ActSignUserAdapter adapter;
    int page_First=1;
    int page_Size=20;
    @Override
    protected int getLayoutID() {
        return R.layout.imap_act_sign_users;
    }

    @Override
    protected void initListener() {
    }
    @Override
    protected void initView() {
        userInfoList = new ArrayList<>();
        activityShow = (ActivityShow) getIntent().getSerializableExtra("activityShow");
        btn_users_back=f(R.id.btn_users_back);
        tv_title=f(R.id.tv_title);
        lv_act_sign_users=f(R.id.lv_act_sign_users);
        adapter=new ActSignUserAdapter(this,userInfoList);
        btn_users_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText("报名人员列表");
        lv_act_sign_users.setAdapter(adapter);
        lv_act_sign_users.setDividerHeight(1);
        lv_act_sign_users.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //判断滑动到底部
                    if(view.getLastVisiblePosition()==view.getCount()-1){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    findSignUsersByActId(activityShow.getAct_id());
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
            findSignUsersByActId(activityShow.getAct_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void initData() {}
    //根据活动id 查找用户列表
    public void findSignUsersByActId(int act_id) throws Exception {
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("act_id",String.valueOf(act_id));
        map.put("page_First",String.valueOf(page_First));
        map.put("page_Size",String.valueOf(page_Size));
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_USER_BASE + "/findSignUsersByActId",token, map, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException,JSONException {
                String ret=response.body().string();
                JSONObject json = new JSONObject(ret);
                //如果token过期,就重新登陆
                if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                    startActivity(new Intent(ActSignUsersActivity.this, LoginActivity.class));
                    finish();
                    return;
                }
                final Gson gson = new Gson();
                JSONArray listdata=json.getJSONArray("listdata");
                JsonParser parser = new JsonParser();
                final JsonArray Jarray = parser.parse(listdata.toString()).getAsJsonArray();
                if(Jarray!=null&&Jarray.size()>0) {
                    page_First++;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (JsonElement obj : Jarray) {
                                UserInfo  ui = gson.fromJson(obj, UserInfo.class);
                                userInfoList.add(ui);
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


}
