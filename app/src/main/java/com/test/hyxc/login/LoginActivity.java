package com.test.hyxc.login;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.PagerMainAdapter;
import com.test.hyxc.fragment.login.LoginByCodeFragment;
import com.test.hyxc.fragment.login.LoginByPasswordFragment;

import java.util.ArrayList;
import java.util.List;

import tools.AppContext;

public class LoginActivity extends BaseActivity {
    private AppContext ctx;
    private ImageView iv_back;
    private TextView tv_login_by_password,tv_login_by_code;
    private TextView[] top_tvs=null;
    private ViewPager f_vp_login;
    private List<Fragment> mFragments;
    private LoginByPasswordFragment loginByPasswordFragment;
    private LoginByCodeFragment loginByCodeFragment;
    private int currIndex=0;
    @Override
    protected int getLayoutID() {
        return R.layout.imap_login;
    }

    @Override
    protected void initView() {
        ctx = (AppContext)this.getApplicationContext();
        f_vp_login=f(R.id.f_vp_login);
        tv_login_by_password=f(R.id.tv_login_by_password);
        tv_login_by_code=f(R.id.tv_login_by_code);
        top_tvs=new TextView[2];
        top_tvs[0]=tv_login_by_password;
        top_tvs[1]=tv_login_by_code;
        iv_back=f(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0,R.anim.imap_fade_out_right);
                finish();
            }
        });

    }
    @Override
    protected void initListener() {
        //toptitle的点击事件
        for(int i=0;i<top_tvs.length;i++){
            final int finalI = i;
            top_tvs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    f_vp_login.setCurrentItem(finalI);
                }
            });
        }
        //ViewPager的改变事件 vp-rg互相监听：vp
        f_vp_login.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        tv_login_by_password.setTextColor(Color.parseColor("#D0BBFD"));
                        tv_login_by_password.setBackgroundResource(R.drawable.imap_login_selector);
                        tv_login_by_code.setTextColor(Color.parseColor("#CCCCCC"));
                        tv_login_by_code.setBackgroundResource(0);
                        break;
                    case 1:
                        tv_login_by_code.setTextColor(Color.parseColor("#D0BBFD"));
                        tv_login_by_code.setBackgroundResource(R.drawable.imap_login_selector);
                        tv_login_by_password.setTextColor(Color.parseColor("#cccccc"));
                        tv_login_by_password.setBackgroundResource(0);
                        break;
                }
                currIndex = position;
            }
        });
    }
    @Override
    protected void initData() {
        mFragments=new ArrayList<>();
        LoginByPasswordFragment one=new LoginByPasswordFragment();
        LoginByCodeFragment two=new LoginByCodeFragment();
        mFragments.add(one);
        mFragments.add(two);
        // 设置填充器
        f_vp_login.setAdapter(new PagerMainAdapter(getSupportFragmentManager(),mFragments));
        // 设置缓存页面数
        f_vp_login.setOffscreenPageLimit(2);
    }
}
