package com.test.hyxc.slide;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.test.hyxc.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WWW on 2019/1/13.
 */


public class MainActivity extends BaseActivity {
    private ViewPager vp;
    private RadioGroup rg;
    private int[] rbs = {R.id.rb_home, R.id.rb_bar, R.id.rb_me};
    private List<Fragment> mFragments;

    //简化后的方法
    @Override
    protected int getLayoutID() {
        return R.layout.slide_main;
    }

    @Override
    protected void initView() {
        vp = f(R.id.vp);
        rg = f(R.id.rg);
    }
    @Override
    protected void initData() {

        mFragments=new ArrayList<>();
  /*      MainHomeFragment one=new MainHomeFragment();
        MainBarFragment two=new MainBarFragment();
        MainMeFragment three=new MainMeFragment();
        mFragments.add(one);
        mFragments.add(two);
        mFragments.add(three);*/
        // 设置填充器
        vp.setAdapter(new PagerMainAdapter(getSupportFragmentManager(),mFragments));
        // 设置缓存页面数
        vp.setOffscreenPageLimit(2);

    }

    @Override
    protected void initListener() {
        //radioGroup的点击事件
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                for (int i = 0; i < rbs.length; i++) {
                    if (rbs[i] != checkedId) continue;
                    //加载滑动
                    vp.setCurrentItem(i);
                }
            }
        });
        //ViewPager的点击事件 vp-rg互相监听：vp
        vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                rg.check(rbs[position]);
            }
        });
        //设置一个默认页
        rg.check(rbs[0]);
    }
}