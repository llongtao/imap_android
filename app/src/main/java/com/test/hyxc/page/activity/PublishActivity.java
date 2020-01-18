package com.test.hyxc.page.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.PagerMainAdapter;
import com.test.hyxc.fragment.publish.PubImgFragment;
import com.test.hyxc.fragment.publish.PubVideoFragment;
import java.util.ArrayList;
import java.util.List;

import tools.DipPxUtils;

public class PublishActivity extends BaseActivity {
    private List<Fragment> fragments;
    private ImageView iv_back;
    private ViewPager f_vp;
    private TextView[] top_tvs=null;
    private TextView tv_img,tv_video;
    private int currIndex=0;
    private LinearLayout ll_container;
    public  int defineIsland=0;//是否确定发布到哪个海岛
    public String defineIslandName="";
    public String defineIslandLogo ="";
    public int defineIslandCategoryId=0;
    public String defineIslandCategoryName="";
    public int defineIslandCategoryParentId=0;
    public String defineIslandCategoryParentName="";
    //fragment
    public  PubImgFragment one;
    public  PubVideoFragment two;
    private static  String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this);
                //.statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_publish;
    }

    @Override
    protected void initListener() {
        //toptitle的点击事件
        for(int i=0;i<top_tvs.length;i++){
            final int finalI = i;
            top_tvs[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    f_vp.setCurrentItem(finalI);
                }
            });
        }
        //ViewPager的改变事件 vp-rg互相监听：vp
        f_vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        if (currIndex == 1) {
                            tv_img.setTextColor(Color.parseColor("#ffffff"));
                            tv_img.setTextSize(22);
                            tv_img.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                            tv_video.setTextColor(Color.parseColor("#ffffff"));
                            tv_video.setTextSize(20);
                            tv_video.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        }
                        break;
                    case 1:
                        if (currIndex == 0) {
                            tv_video.setTextColor(Color.parseColor("#ffffff"));
                            tv_video.setTextSize(22);
                            tv_video.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                            tv_img.setTextColor(Color.parseColor("#ffffff"));
                            tv_img.setTextSize(20);
                            tv_img.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        }
                        break;
                }
                currIndex = position;
            }
        });
    }

    @Override
    protected void initView() {
        //表示特定的海岛
        if(getIntent().hasExtra("defineIsland"))
            defineIsland=getIntent().getIntExtra("defineIsland",0);
        if(getIntent().hasExtra("defineIslandName"))
            defineIslandName=getIntent().getStringExtra("defineIslandName");
        if(getIntent().hasExtra("defineIslandLogo"))
            defineIslandLogo=getIntent().getStringExtra("defineIslandLogo");
        if(getIntent().hasExtra("defineIslandCategoryId"))
            defineIslandCategoryId=getIntent().getIntExtra("defineIslandCategoryId",0);
        if(getIntent().hasExtra("defineIslandCategoryName"))
            defineIslandCategoryName=getIntent().getStringExtra("defineIslandCategoryName");
        if(getIntent().hasExtra("defineIslandCategoryParentId"))
            defineIslandCategoryParentId=getIntent().getIntExtra("defineIslandCategoryParentId",0);
        if(getIntent().hasExtra("defineIslandCategoryParentName"))
            defineIslandCategoryParentName=getIntent().getStringExtra("defineIslandCategoryParentName");
        ll_container=f(R.id.ll_container);
        //对4.4以下的系统做没有沉浸式菜单显示布局的处理
        if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.KITKAT){
            ll_container.setPadding(0,0,0, DipPxUtils.dip2px(this,3));
        }
        f_vp=f(R.id.f_vp);
        tv_img=f(R.id.tv_img);
        tv_video=f(R.id.tv_video);
        top_tvs=new TextView[2];
        top_tvs[0]=tv_img;
        top_tvs[1]=tv_video;
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
    protected void initData() {
        fragments=new ArrayList<>();
        one=new PubImgFragment();
        two=new PubVideoFragment();
        fragments.add(one);
        fragments.add(two);
        // 设置填充器
        f_vp.setAdapter(new PagerMainAdapter(getSupportFragmentManager(),fragments));
        // 设置缓存页面数
        f_vp.setOffscreenPageLimit(2);
        //权限检查及提醒
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 2);
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 3);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            //写权限
            for (int i = 0; i < permissions.length; i++) {
                if(!(grantResults[i]==PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(PublishActivity.this,"需要相关权限才能继续哦~",Toast.LENGTH_LONG).show();
                    overridePendingTransition(0,R.anim.imap_fade_out_right);
                    finish();
                }
            }
        }
        if (requestCode == 2) {
            //相机
            for (int i = 0; i < permissions.length; i++) {
                if(!(grantResults[i]==PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(PublishActivity.this,"需要相关权限才能继续哦~",Toast.LENGTH_LONG).show();
                    overridePendingTransition(0,R.anim.imap_fade_out_right);
                    finish();
                }
            }
        }
        if (requestCode == 3) {
            //位置
            for (int i = 0; i < permissions.length; i++) {
                if(!(grantResults[i]==PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(PublishActivity.this,"需要相关权限才能继续哦~",Toast.LENGTH_LONG).show();
                    overridePendingTransition(0,R.anim.imap_fade_out_right);
                    finish();
                }
            }
        }
    }
}
