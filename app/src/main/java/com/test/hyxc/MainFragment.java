package com.test.hyxc;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test.hyxc.adapter.PagerMainAdapter;
import com.test.hyxc.fragment.ActivityFragment;
import com.test.hyxc.fragment.FollowFragment;
import com.test.hyxc.fragment.RecommendFragment;
import com.test.hyxc.ui.ImapChActivity1;

import java.util.ArrayList;
import java.util.List;

import q.rorbin.badgeview.QBadgeView;
import tools.AppContext;

;

public class MainFragment extends Fragment {
    private AppContext ctx;
    private ViewPager f_vp;
    private ImageView img_cursor;
    private List<Fragment> mFragments;
    private int bmpWidth, offset;
    private int currIndex = 0;//当前页面的编号
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离
    private LinearLayout ll_toptitle;
    private TextView tv_recommend, tv_follow, tv_activity;
    private TextView[] top_tvs = null;
    private TextView tv_shouye, tv_news;
    public LinearLayout  ll_city;
    public TextView tv_city;
    private  View view;
    private int news_unread = 10;
    private int[] top_title_tvs = {R.id.tv_recommend, R.id.tv_follow, R.id.tv_activity};
    //屏幕监听
    private GestureDetector mGestureDetector;
    ImapChActivity1.MyOnTouchListener myOnTouchListener;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imap_main, container, false);
        this.view=view;
        /**屏幕监听事件**/
        mGestureDetector = new GestureDetector(getActivity(),
                new GestureDetector.SimpleOnGestureListener());
        myOnTouchListener = new ImapChActivity1.MyOnTouchListener() {
            @Override
            public boolean onTouch(MotionEvent ev) {
                boolean result = mGestureDetector.onTouchEvent(ev);
                int action = ev.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        boolean isSlideout=((ImapChActivity1)getActivity()).getChSlideMenu().GetIsSlideOut();
                        int LEFT_CONTAINER=((ImapChActivity1)getActivity()).getChSlideMenu().LEFT_CONTAINER;
                        //隐藏在左边宽度
                        if(isSlideout) {
                            if(ev.getX()>LEFT_CONTAINER+5){
                                //如果点击的是城市选择区域
                                    ((ImapChActivity1) getActivity()).getChSlideMenu().slideInMenuJust();
                            }
                        }else{
                            if(isTouchPointInView(ll_city,(int)ev.getX(),(int)ev.getY())){
                                ((ImapChActivity1) getActivity()).getChSlideMenu().slideOutMenuJust();
                            }
                        }
                    }
                return  true;//不向下传递
            }
        };
        ((ImapChActivity1) getActivity())
                .registerMyOnTouchListener(myOnTouchListener);
        /**屏幕监听**/

        ctx = (AppContext) getActivity().getApplicationContext();
        f_vp = f(R.id.f_vp);
        //三个toptitle
        ll_toptitle = f(R.id.ll_toptitle);
        ll_city = f(R.id.ll_city);
        tv_city=f(R.id.tv_city);
      /*  ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                      ((ImapChActivity1) getActivity()).getChSlideMenu().switchMenu();
            }
        });*/
      //  tvCityOnclick();
        tv_recommend = f(R.id.tv_recommend);
        tv_follow = f(R.id.tv_follow);
        tv_activity = f(R.id.tv_activity);
        top_tvs = new TextView[3];
        top_tvs[0] = tv_recommend;
        top_tvs[1] = tv_follow;
        top_tvs[2] = tv_activity;
        img_cursor = f(R.id.img_cursor);
        //底部菜单
        tv_shouye = f(R.id.tv_shouye);
        tv_shouye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(getActivity()..this,MainFragment.class);
                startActivity(intent);*/
                getActivity().overridePendingTransition(0, 0);

            }
        });
        tv_news = f(R.id.tv_news);
        QBadgeView qb = new QBadgeView(getActivity());
        qb.bindTarget(tv_news).setBadgeNumber(news_unread);
        /*qb.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener(){
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                int STATE_START = 1;
                int STATE_DRAGGING = 2;
                int STATE_DRAGGING_OUT_OF_RANGE = 3;
                int STATE_CANCELED = 4;
                int STATE_SUCCEED = 5;
                 onDragStateChanged(dragState,  badge,  targetView);
            }
        });*/

        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        bmpWidth = BitmapFactory.decodeResource(getResources(), R.mipmap.imap_line).getWidth();// 获取图片宽度
        int screenW = dm.widthPixels;
        offset = (screenW * 2 / 4 / 3 - bmpWidth) / 2;
        one = screenW / 6;
        two = one * 2;// 移动两页的偏移量,比如1直接跳3
        Matrix matrix = new Matrix();
        matrix.postTranslate(screenW * 1 / 4 + offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置
        initData();
        initListener();
        return view;
    }
    protected void initData() {
        mFragments=new ArrayList<>();
        RecommendFragment one=new RecommendFragment();
        FollowFragment two=new FollowFragment();
        ActivityFragment three=new ActivityFragment();
        mFragments.add(one);
        mFragments.add(two);
        mFragments.add(three);
        // 设置填充器
        f_vp.setAdapter(new PagerMainAdapter(getActivity().getSupportFragmentManager(),mFragments));
        // 设置缓存页面数
        f_vp.setOffscreenPageLimit(2);
    }
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
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
               // This space for rent
           }
           @Override
           public void onPageSelected(int position) {
               Animation animation = null;
               switch (position) {
                   case 0:
                       if (currIndex == 1) {
                           animation = new TranslateAnimation(one, 0, 0, 0);
                           tv_follow.setTextColor(Color.rgb(99,99,99));
                       } else if (currIndex == 2) {
                           animation = new TranslateAnimation(two, 0, 0, 0);
                           tv_activity.setTextColor(Color.rgb(99,99,99));
                       }
                       tv_recommend.setTextColor(Color.BLACK);
                       break;
                   case 1:
                       if (currIndex == 0) {
                           animation = new TranslateAnimation(offset, one, 0, 0);
                           tv_recommend.setTextColor(Color.rgb(99,99,99));
                       } else if (currIndex == 2) {
                           animation = new TranslateAnimation(two, one, 0, 0);
                           tv_activity.setTextColor(Color.rgb(99,99,99));
                       }
                       tv_follow.setTextColor(Color.BLACK);
                       break;
                   case 2:
                       if (currIndex == 0) {
                           animation = new TranslateAnimation(offset, two, 0, 0);
                           tv_recommend.setTextColor(Color.rgb(99,99,99));
                       } else if (currIndex == 1) {
                           animation = new TranslateAnimation(one, two, 0, 0);
                           tv_follow.setTextColor(Color.rgb(99,99,99));
                       }
                       tv_activity.setTextColor(Color.BLACK);
                       break;
               }
               currIndex = position;
               animation.setFillAfter(true);// true表示图片停在动画结束位置
               animation.setDuration(300); //设置动画时间为300毫秒
               img_cursor.startAnimation(animation);//开始动画
           }
       });
   }
    //(x,y)是否在view的区域内
    private boolean isTouchPointInView(View view, int x, int y) {
        if (view == null) {
            return false;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        int left = location[0];
        int top = location[1];
        int right = left + view.getMeasuredWidth();
        int bottom = top + view.getMeasuredHeight();
        //view.isClickable() &&
        if (y >= top && y <= bottom && x >= left
                && x <= right) {
            return true;
        }
        return false;
    }
    protected <E> E f(int id){
        return (E)view.findViewById(id);
    }
}