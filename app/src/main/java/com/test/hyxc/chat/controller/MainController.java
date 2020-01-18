package com.test.hyxc.chat.controller;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import com.test.hyxc.R;
import com.test.hyxc.page.island.IslandSearchActivity;
import com.test.hyxc.page.personal.InformationActivity;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.page.activity.PublishActivity;

import com.test.hyxc.chat.activity.MainActivity;
import com.test.hyxc.chat.activity.fragment.ContactsFragment;
import com.test.hyxc.chat.activity.fragment.ConversationListFragment;
import com.test.hyxc.chat.activity.fragment.MeFragment;
import com.test.hyxc.chat.adapter.ViewPagerAdapter;
import com.test.hyxc.chat.view.MainView;
import tools.AppContext;

/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class MainController implements View.OnClickListener, ViewPager.OnPageChangeListener {
    private MainView mMainView;
    private MainActivity mContext;
    private AppContext ctx;
    private ConversationListFragment mConvListFragment;
    private MeFragment mMeFragment;
    private ContactsFragment mContactsFragment;

    public MainController(MainView mMainView, MainActivity context) {
        this.mMainView = mMainView;
        this.mContext = context;
        ctx=((AppContext)mContext.getApplicationContext());
        setViewPager();
    }

    private void setViewPager() {
        final List<Fragment> fragments = new ArrayList<>();
        mConvListFragment = new ConversationListFragment();
        //mContactsFragment = new ContactsFragment();
        //mMeFragment = new MeFragment();
        fragments.add(mConvListFragment);
        //fragments.add(mContactsFragment);
        //fragments.add(mMeFragment);
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mContext.getSupportFragmentManger(),
                fragments);
        mMainView.setViewPagerAdapter(viewPagerAdapter);
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            /*case R.id.actionbar_msg_btn:
                mMainView.setCurrentItem(0, false);
                break;
            case R.id.actionbar_contact_btn:
                mMainView.setCurrentItem(1, false);
                break;
            case R.id.actionbar_me_btn:
                mMainView.setCurrentItem(2, false);
                break;*/
            case R.id.tv_shouye:
                if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                    Intent intent=new Intent(mContext, InformationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    ctx.startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.setClass(mContext, com.test.hyxc.MainActivity.class);
                    ctx.startActivity(intent);
                    mContext.overridePendingTransition(0,0);
                }
                break;
            case R.id.tv_island:
                if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                    Intent intent=new Intent(mContext, InformationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    ctx.startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.setClass(mContext, IslandSearchActivity.class);
                    ctx.startActivity(intent);
                    mContext.overridePendingTransition(0,0);
                }
                break;
            case R.id.iv_publish:
                if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                    Intent intent=new Intent(mContext, InformationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    ctx.startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.setClass(mContext, PublishActivity.class);
                    ctx.startActivity(intent);
                    mContext.overridePendingTransition(0,0);
                }
                break;
            case R.id.tv_me:
                if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                    Intent intent=new Intent(mContext, InformationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    ctx.startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK );
                    intent.putExtra("user_id", ctx.getUserId());
                    intent.setClass(mContext, MeActivity.class);
                    ctx.startActivity(intent);
                    mContext.overridePendingTransition(0,0);
                }
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mMainView.setButtonColor(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void sortConvList() {
        mConvListFragment.sortConvList();
    }
}