package com.test.hyxc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import com.gyf.barlibrary.ImmersionBar;
import tools.GlobalConfig;

/**
 * Created by WWW on 2019/1/13.
 */

public abstract class BaseActivity extends FragmentActivity {
    protected BaseActivity act;
    protected final String TAG=getClass().getSimpleName();
    public ImmersionBar mImmersionBar;
    MyReceiver receiver;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        act=this;
        setContentView(getLayoutID());
       /* mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();*/
        try {
            initView();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initData();
        initListener();
        receiver=new MyReceiver();
        IntentFilter intentFilter=new IntentFilter(GlobalConfig.exitApp);
        registerReceiver(receiver,intentFilter);
    }
    @LayoutRes
    protected abstract int getLayoutID();
    protected abstract void initListener();
    protected abstract void initView() throws Exception;
    protected abstract void initData();
    @SuppressWarnings("unchecked")
    protected <E> E f(int id){
        return (E)findViewById(id);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
        unregisterReceiver(receiver);
    }
    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
                finish();
        }
    }
}