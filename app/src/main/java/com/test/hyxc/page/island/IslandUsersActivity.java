package com.test.hyxc.page.island;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
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
import com.test.hyxc.adapter.PagerMainAdapter;
import com.test.hyxc.fragment.TestFragment;
import com.test.hyxc.fragment.island.IslandUsersAskingFragment;
import com.test.hyxc.fragment.island.IslandUsersFragment;
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

public class IslandUsersActivity extends BaseActivity   {
    Button btn_users_back;
    TextView tv_title;
    Island island;
    private List<String> mTitleList = new ArrayList<String>();
    private TabLayout mTabLayout;
    private View view1, view2;
    private List<View> mViewList = new ArrayList<>();
    ViewPager f_vp;
    public Integer current_page=0;//默认作品列表
    private List<Fragment> mFragments=new ArrayList<>();
    public int relation=-1; //-1 or 0：无关系 1：普通居民 2：管理员 3：岛主

    @Override
    protected int getLayoutID() {
        return R.layout.imap_island_users;
    }

    @Override
    protected void initListener() {}
    @Override
    protected void initView() {
        island = (Island) getIntent().getSerializableExtra("island");
        findIslandById(island.getIs_id());
        btn_users_back=f(R.id.btn_users_back);
        tv_title=f(R.id.tv_title);
        btn_users_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText(island.getIs_name()+" ("+island.getIs_people_current()+")");
    }
    @Override
    protected void initData() {}
    //根据海岛id 查找用户信息
//    public void findUsersByIsId(int is_id) throws Exception {
//        NetUtils netUtils=NetUtils.getInstance();
//        String token=((AppContext)getApplicationContext()).getToken();
//        Map map=new HashMap();
//        map.put("is_id",String.valueOf(is_id));
//        map.put("page_First",String.valueOf(page_First));
//        map.put("page_Size",String.valueOf(page_Size));
//        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_RESIDENT_BASE + "/findIRByIsId",token, map, new NetUtils.MyNetCall() {
//            @Override
//            public void success(Call call, Response response) throws IOException,JSONException {
//                String ret=response.body().string();
//                JSONObject json = new JSONObject(ret);
//                //如果token过期,就重新登陆
//                if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
//                    startActivity(new Intent(IslandUsersActivity.this, LoginActivity.class));
//                    finish();
//                    return;
//                }
//                final Gson gson = new Gson();
//                JSONArray listIR=json.getJSONArray("listIR");
//                JsonParser parser = new JsonParser();
//                final JsonArray Jarray = parser.parse(listIR.toString()).getAsJsonArray();
//                if(Jarray!=null&&Jarray.size()>0) {
//                    page_First++;
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            for (JsonElement obj : Jarray) {
//                                IslandResidentUserInfo  irui = gson.fromJson(obj, IslandResidentUserInfo.class);
//                                userInfoList.add(irui);
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//                    });
//                }
//            }
//            @Override
//            public void failed(Call call, IOException e) {}
//        });
//    }

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
                        startActivity(new Intent(IslandUsersActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    //关系 用户跟海岛的关系 0：无关 1：居民 2：管理员 3：岛主
                    relation=json.has("relation") ? json.getInt("relation") : 0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initTabTitle();
                        }
                    });
                }
                @Override
                public void failed(Call call, IOException e) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initTabTitle(){
        mTitleList.add("当前成员");
        mTitleList.add("申请中");
        mTabLayout =  findViewById(R.id.tabs);
        view1 = tab_icon(mTitleList.get(0));
        view2 = tab_icon(mTitleList.get(1));
        mViewList.add(view1);
        mViewList.add(view2);
        //添加tab选项卡，默认第一个选中
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view1),true);
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view2));
        ((TextView)mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#c0a8ff"));
        ((TextView)mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTextSize(18);
        ((TextView)mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#73747a"));
        ((TextView)mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tabtext)).setTextSize(16);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for(int i =0;i<mViewList.size();i++){
                    View v= mTabLayout.getTabAt(i).getCustomView();
                    ((TextView)v.findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#73747a"));
                    ((TextView)v.findViewById(R.id.tabtext)).setTextSize(16);
                }
                ((TextView)tab.getCustomView().findViewById(R.id.tabtext)).setTextSize(18);
                ((TextView)tab.getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#c0a8ff"));
                //联动
                f_vp.setCurrentItem(tab.getPosition(),true);
                current_page=tab.getPosition();//记录当前的页面是作品列表还是活动列表
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {  }
        });
        f_vp=f(R.id.f_vp);
        IslandUsersFragment fragment=new IslandUsersFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("island",island);
        bundle.putInt("relation",relation);
        fragment.setArguments(bundle);
        IslandUsersAskingFragment fragment1=new IslandUsersAskingFragment();
        fragment1.setArguments(bundle);
        mFragments.add(fragment);
        mFragments.add(fragment1);
        // 设置填充器
        f_vp.setAdapter(new PagerMainAdapter(getSupportFragmentManager(),mFragments));
        // 设置缓存页面数
        f_vp.setOffscreenPageLimit(2);
        //ViewPager的改变事件 vp-rg互相监听：vp
        f_vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }
    //tabbar
    private View tab_icon(String name){
        View newtab =  LayoutInflater.from(this).inflate(R.layout.tab_item,null);
        TextView tv = newtab.findViewById(R.id.tabtext);
        tv.setText(name);
        return newtab;
    }
}
