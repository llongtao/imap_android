package com.test.hyxc.page.search;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.BaseFragmentPagerAdapter;
import com.test.hyxc.fragment.search.S_ActFragment;
import com.test.hyxc.fragment.search.S_IslandFragment;
import com.test.hyxc.fragment.search.S_UserFragment;
import com.test.hyxc.fragment.search.S_WorkFragment;
import java.util.ArrayList;
import java.util.List;
public class SearchActivity  extends BaseActivity {
    private ViewPager f_vp;
    private List<Fragment> mFragments;
    private List<String> mTitleList = new ArrayList<String>();
    private TabLayout mTabLayout;
    private View view1, view2, view3, view4;
    private List<View> mViewList = new ArrayList<>();
    public  String searchText="";
    LinearLayout ll_alert,ll_content;
    EditText et_search;TextView tv_search;
    BaseFragmentPagerAdapter adapter;
    boolean changed = false;
    S_WorkFragment one;
    S_IslandFragment two;
    S_UserFragment three;
    S_ActFragment four;
    FragmentManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true);
        mImmersionBar.init();
    }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_search;
    }
    @Override
    protected void initListener() {}

    @Override
    protected void initView() throws Exception {
        ll_alert=f(R.id.ll_alert);
        ll_content = f(R.id.ll_content);
        et_search=f(R.id.et_search);
        tv_search = f(R.id.tv_search);
        mTabLayout = findViewById(R.id.tabs);
        f_vp=f(R.id.f_vp);
        listenSearch();
        initTabTitle(searchText);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 trySearch(searchText);
            }
        });
    }

    @Override
    protected void initData() {

    }
    //edit 修改监听
    public void listenSearch(){
        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                searchText= s.toString();
                changed =true;
                if(searchText==null || "".equals(searchText)){
                    ll_alert.setVisibility(View.VISIBLE);
                    ll_content.setVisibility(View.GONE);
                }
            }
        };
        et_search.addTextChangedListener(watcher);
    }
    public void initTabTitle(String searchText) {
        //标题添加页卡标题
        mTitleList.add("动态");
        mTitleList.add("海岛");
        mTitleList.add("用户");
        mTitleList.add("活动");
        view1 = tab_icon(mTitleList.get(0));
        view2 = tab_icon(mTitleList.get(1));
        view3 = tab_icon(mTitleList.get(2));
        view4 = tab_icon(mTitleList.get(3));
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        //添加tab选项卡，默认第一个选中
        // mTabLayout.addTab( mTabLayout.newTab().setCustomView(view1), true);
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(view1));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(view2));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(view3));
        mTabLayout.addTab(mTabLayout.newTab().setCustomView(view4));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(f_vp));
        ((TextView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#000000"));
        ((TextView) mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTextSize(17);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for (int i = 0; i < mViewList.size(); i++) {
                    View v = mTabLayout.getTabAt(i).getCustomView();
                    ((TextView) v.findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#767676"));
                    ((TextView) v.findViewById(R.id.tabtext)).setTextSize(16);
                }
                ((TextView) tab.getCustomView().findViewById(R.id.tabtext)).setTextSize(17);
                ((TextView) tab.getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#000000"));
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        //////
        mFragments=new ArrayList<>();
        /*Bundle bundle = new Bundle();
        bundle.putString("searchText",searchText);*/
        one = new S_WorkFragment();
        one.setSearchText(searchText);
        two = new S_IslandFragment();
        two.setSearchText(searchText);
        three  = new S_UserFragment();
        three.setSearchText(searchText);
        four = new S_ActFragment();
        four.setSearchText(searchText);
        manager = getSupportFragmentManager();
        mFragments.add(one);
        mFragments.add(two);
        mFragments.add(three);
        mFragments.add(four);
        // 设置填充器
        adapter = new BaseFragmentPagerAdapter(manager,mFragments);
        f_vp.setAdapter(adapter);
        // 设置缓存页面数
        f_vp.setOffscreenPageLimit(4);
        //ViewPager的改变事件 vp-rg互相监听：vp
        f_vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                //searchText=mTitleList.get(position);
                mTabLayout.getTabAt(position).select();
            }
        });
    }

        //tabbar
    private View tab_icon(String name){
        View newtab =  LayoutInflater.from(this).inflate(R.layout.tab_item,null);
        TextView tv = newtab.findViewById(R.id.tabtext);
        tv.setText(name);
        return newtab;
    }
    @SuppressLint("RestrictedApi")
    public void trySearch(String searchText){
        if(searchText!=null && !"".equals(searchText) && changed){
            ll_alert.setVisibility(View.GONE);
            ll_content.setVisibility(View.VISIBLE);
            ////
            one = new S_WorkFragment();
            one.setSearchText(searchText);
            two = new S_IslandFragment();
            two.setSearchText(searchText);
            three = new S_UserFragment();
            three.setSearchText(searchText);
            four = new S_ActFragment();
            four.setSearchText(searchText);
            adapter.replaceFragment(0,one);
            adapter.replaceFragment(1,two);
            adapter.replaceFragment(2,three);
            adapter.replaceFragment(3,four);
            changed =false;
        }
    }

}
