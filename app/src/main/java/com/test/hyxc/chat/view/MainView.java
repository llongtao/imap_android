package com.test.hyxc.chat.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.test.hyxc.R;

/**
 * Created by ${chenyn} on 2017/2/20.
 */

public class MainView extends RelativeLayout {

    //private Button[] mBtnList;
    //private int[] mBtnListID;
    LinearLayout ll_bottom;
    TextView tv_shouye,tv_news,tv_island,tv_me;
    ImageView iv_publish;
    private ScrollControlViewPager mViewContainer;

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void initModule() {
        ll_bottom=(LinearLayout)findViewById(R.id.ll_bottom);
        tv_shouye=(TextView)findViewById(R.id.tv_shouye);
        tv_news=(TextView)findViewById(R.id.tv_news);
        tv_me=(TextView)findViewById(R.id.tv_me);
        tv_island=(TextView)findViewById(R.id.tv_island);
        iv_publish=(ImageView)findViewById(R.id.iv_publish);
        mViewContainer = (ScrollControlViewPager) findViewById(R.id.viewpager);
        tv_shouye.setTextColor(Color.parseColor("#8A000000"));
        tv_news.setTextColor(Color.parseColor("#8A000000"));
        tv_island.setTextColor(Color.parseColor("#8A000000"));
        tv_me.setTextColor(Color.parseColor("#8A000000"));

        /*mBtnListID = new int[] {
                R.id.actionbar_msg_btn, R.id.actionbar_contact_btn, R.id.actionbar_me_btn
        };
        mBtnList = new Button[3];
        for (int i = 0; i < 3; i++) {
            mBtnList[i] = (Button) findViewById(mBtnListID[i]);
        }
        mViewContainer = (ScrollControlViewPager) findViewById(R.id.viewpager);
        mBtnList[0].setTextColor(getResources().getColor(R.color.actionbar_pres_color));
        mBtnList[0].setSelected(true);*/
    }

    public void setOnClickListener(OnClickListener onclickListener) {
        /*for (int i = 0; i < mBtnListID.length; i++) {
            mBtnList[i].setOnClickListener(onclickListener);
        }*/
        tv_news.setOnClickListener(onclickListener);
        tv_shouye.setOnClickListener(onclickListener);
        tv_island.setOnClickListener(onclickListener);
        tv_me.setOnClickListener(onclickListener);
        iv_publish.setOnClickListener(onclickListener);
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mViewContainer.addOnPageChangeListener(onPageChangeListener);
    }

    public void setViewPagerAdapter(FragmentPagerAdapter adapter) {
        mViewContainer.setAdapter(adapter);
    }

    public void setCurrentItem(int index, boolean scroll) {
        mViewContainer.setCurrentItem(index, scroll);
    }

    public void setButtonColor(int index) {
       /* for (int i = 0; i < 3; i++) {
            if (index == i) {
                mBtnList[i].setSelected(true);
                mBtnList[i].setTextColor(getResources().getColor(R.color.actionbar_pres_color));
            } else {
                mBtnList[i].setSelected(false);
                mBtnList[i].setTextColor(getResources().getColor(R.color.action_bar_txt_color));
            }
        }*/
    }
}
