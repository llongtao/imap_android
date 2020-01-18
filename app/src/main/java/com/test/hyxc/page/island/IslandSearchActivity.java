package com.test.hyxc.page.island;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.MainActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.PagerMainAdapter;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.fragment.island.IslandSearchFragment;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.IslandCategory;
import com.test.hyxc.page.search.SearchActivity;

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

public class IslandSearchActivity extends BaseActivity{
    private ViewPager f_vp;
    private List<Fragment> mFragments;
    private List<Island> lis=new ArrayList<>();
    private List<View> mViewList = new ArrayList<>();
    private TabLayout mTabLayout;
    private List<String> mTitleList = new ArrayList<String>();
    TextView tv_search;
    LinearLayout ll_search_parent;
    List<IslandCategory> islandCategoryList=new ArrayList<>();
    int titleNumber = 20;
    boolean getCategory = false;
    @Override
    protected int getLayoutID() {
        return R.layout.imap_island_search;
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(false);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected void initListener() {}
    @Override
    protected void initView() throws Exception {
        tv_search = f(R.id.et_search);
        ll_search_parent =f(R.id.ll_search_parent);
        ll_search_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(IslandSearchActivity.this, SearchActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        //查询二级分类列表
        getCategory();
    }
    @Override
    protected void initData() {
    }
    public void initTabTitle(List<IslandCategory> islandCategoryList){
        mTitleList.add("附近");
        for(int i =0;i<islandCategoryList.size();i++){
            IslandCategory islandCategory = islandCategoryList.get(i);
            mTitleList.add(islandCategory.getCate_name());
        }
        mTabLayout =  findViewById(R.id.tabs);
        for(int i =0;i<mTitleList.size();i++){
            mViewList.add(tab_icon(mTitleList.get(i)));
        }
        for(View v:mViewList){
            mTabLayout.addTab(mTabLayout.newTab().setCustomView(v));
        }
        ((TextView)mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#000000"));
        ((TextView)mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTextSize(17);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for(int i =0;i<mViewList.size();i++){
                   View v= mTabLayout.getTabAt(i).getCustomView();
                   ((TextView)v.findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#767676"));
                   ((TextView)v.findViewById(R.id.tabtext)).setTextSize(15);
                }
                ((TextView)tab.getCustomView().findViewById(R.id.tabtext)).setTextSize(17);
                ((TextView)tab.getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#000000"));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //////
        f_vp=f(R.id.f_vp);
        mFragments=new ArrayList<>();
        for(int i=0;i<mTitleList.size();i++) {
            IslandSearchFragment fragment=new IslandSearchFragment();
            Bundle bundle = new Bundle();
            if(i==0) {
                bundle.putString("searchText","附近");
            }else{
                bundle.putString("searchText",mTitleList.get(i));
                bundle.putInt("secondCate",islandCategoryList.get(i-1).getCate_id());
                bundle.putInt("firstCate",islandCategoryList.get(i-1).getCate_parent());
            }
            fragment.setArguments(bundle);
            mFragments.add(fragment);
        }
        // 设置填充器
        f_vp.setAdapter(new PagerMainAdapter(getSupportFragmentManager(),mFragments));
        // 设置缓存页面数
        f_vp.setOffscreenPageLimit(2);
        //ViewPager的改变事件 vp-rg互相监听：vp
        //f_vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
        //@Override
        //public void onPageSelected(int position) {
        // mTabLayout.getTabAt(position).select();
        // }
        //});
        f_vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(f_vp));
    }
    //tabbar
    private View tab_icon(String name){
        View newtab =  LayoutInflater.from(this).inflate(R.layout.tab_item,null);
        TextView tv = newtab.findViewById(R.id.tabtext);
        tv.setText(name);
        return newtab;
    }
    public void getCategory(){
        if(!getCategory) {
            try {
                NetUtils netUtils = NetUtils.getInstance();
                String token = ((AppContext) getApplicationContext()).getToken();
                Map map = new HashMap();
                netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_CATEGORY + "/findCategory", token, map, new NetUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException, JSONException {
                        String ret = response.body().string();
                        JSONObject json = new JSONObject(ret);
                        if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                            return;
                        }
                        final Gson gson = new Gson();
                        JSONArray secondCategory = json.getJSONArray("second");
                        JsonParser parser = new JsonParser();
                        final JsonArray Jarray = parser.parse(secondCategory.toString()).getAsJsonArray();
                        if (Jarray != null && Jarray.size() > 0 && !"[]".equals(Jarray)) {
                            getCategory = true;// 防止重复请求
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < Jarray.size() && i < titleNumber; i++) {
                                        JsonElement obj = Jarray.get(i);
                                        IslandCategory category = gson.fromJson(obj, IslandCategory.class);
                                        islandCategoryList.add(category);
                                    }
                                    initTabTitle(islandCategoryList);
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
}
