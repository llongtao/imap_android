package com.test.hyxc.chat.controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;

import com.test.hyxc.R;
import com.test.hyxc.chat.activity.CommonScanActivity;
import com.test.hyxc.chat.activity.CreateGroupActivity;
import com.test.hyxc.chat.activity.SearchForAddFriendActivity;
import com.test.hyxc.chat.activity.fragment.ConversationListFragment;
import com.test.hyxc.chat.model.Constant;

/**
 * Created by ${chenyn} on 2017/4/9.
 */

public class MenuItemController implements View.OnClickListener {
    private ConversationListFragment mFragment;

    public MenuItemController(ConversationListFragment fragment) {
        this.mFragment = fragment;
    }

    //会话界面的加号
    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.create_group_ll:
                mFragment.dismissPopWindow();
                intent = new Intent(mFragment.getContext(), CreateGroupActivity.class);
                mFragment.getContext().startActivity(intent);
                break;
            case R.id.add_friend_with_confirm_ll:
                mFragment.dismissPopWindow();
                intent = new Intent(mFragment.getContext(), SearchForAddFriendActivity.class);
                intent.setFlags(1);
                mFragment.startActivity(intent);
                break;
            case R.id.send_message_ll:
                mFragment.dismissPopWindow();
                intent = new Intent(mFragment.getContext(), SearchForAddFriendActivity.class);
                intent.setFlags(2);
                mFragment.startActivity(intent);
                break;
            //扫描二维码
            case R.id.ll_saoYiSao:
                intent = new Intent(mFragment.getContext(), CommonScanActivity.class);
                intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_QRCODE_MODE);
                mFragment.getContext().startActivity(intent);
                break;
            default:
                break;
        }

    }
}
