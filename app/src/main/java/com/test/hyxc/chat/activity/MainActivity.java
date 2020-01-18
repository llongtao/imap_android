package com.test.hyxc.chat.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import cn.jiguang.api.JCoreInterface;
import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupIDListCallback;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import tools.AppContext;

import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.R;

import com.test.hyxc.chat.controller.MainController;
import com.test.hyxc.chat.utils.ThreadUtil;
import com.test.hyxc.chat.view.MainView;

import java.util.List;

public class MainActivity extends FragmentActivity {
    private MainController mMainController;
    private MainView mMainView;
    ImmersionBar mImmersionBar;
    public String useType = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //嵌入
        mImmersionBar = ImmersionBar.with(this);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_jiguang);
        if(getIntent().hasExtra("useType"))
            useType = getIntent().getExtras().getString("useType");
        mMainView = (MainView) findViewById(R.id.main_view);
        mMainView.initModule();
        mMainController = new MainController(mMainView, this);
        mMainView.setOnClickListener(mMainController);
        mMainView.setOnPageChangeListener(mMainController);

    }

    public FragmentManager getSupportFragmentManger() {
        return getSupportFragmentManager();
    }

    @Override
    protected void onPause() {
        JCoreInterface.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        JCoreInterface.onResume(this);
        mMainController.sortConvList();
        //如果放到数据库做.能提高效率和网络状态不好的情况,但是不能实时获取在其他终端修改后的搜索匹配.
        //为搜索群组做准备
        ////////////*******用于搜索********///////////
        ////////////***************//////////
        AppContext.mGroupInfoList.clear();
        ThreadUtil.runInThread(new Runnable() {
            @Override
            public void run() {
                JMessageClient.getGroupIDList(new GetGroupIDListCallback() {
                    @Override
                    public void gotResult(int responseCode, String responseMessage, List<Long> groupIDList) {
                        if (responseCode == 0) {
                            for (final Long groupID : groupIDList) {
                                JMessageClient.getGroupInfo(groupID, new GetGroupInfoCallback() {
                                    @Override
                                    public void gotResult(int responseCode, String responseMessage, GroupInfo groupInfo) {
                                        if (responseCode == 0) {
                                            AppContext.mGroupInfoList.add(groupInfo);
                                        }
                                    }
                                });
                            }
                        }
                    }
                });
            }
        });
        //为搜索好友做准备
        if (AppContext.mFriendInfoList != null)
            AppContext.mFriendInfoList.clear();
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {
                if (i == 0) {
                    AppContext.mFriendInfoList = list;
                }
            }
        });
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //return keyCode == KeyEvent.KEYCODE_BACK;
    }*/
}
