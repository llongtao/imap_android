package com.test.hyxc.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;

import tools.DipPxUtils;

public class WrapContentHeightViewPager extends ViewPager {

    private int currentIndex;
    private int height=0;
    private  int width=0;
    //保存view对应的索引
    private HashMap<Integer, View> mChildrenViews=new LinkedHashMap<Integer, View>();


    public WrapContentHeightViewPager(Context context) {
        super(context);
    }

    public WrapContentHeightViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mChildrenViews.size() > currentIndex) {
            View child=mChildrenViews.get(currentIndex);
            child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            height=child.getMeasuredHeight();
            width=child.getMeasuredWidth();

        }
        if (mChildrenViews.size() != 0) {
            heightMeasureSpec=MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 重新设置高度
     *
     * @param current
     */
    public void resetHeight(int current) {
        currentIndex=current;
        if (mChildrenViews.size() > current) {
            FrameLayout.LayoutParams layoutParams=(FrameLayout.LayoutParams) getLayoutParams();
            if (layoutParams == null) {
                layoutParams=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
            } else {
                layoutParams.height=height;
               /* if(height< DipPxUtils.dip2px(getContext(),300));
                    layoutParams.height=DipPxUtils.dip2px(getContext(),300);*/

            }
            setLayoutParams(layoutParams);
        }
    }

    /**
     * 保存View对应的索引,需要自适应高度的一定要设置这个
     */
    public void setViewForPosition(View view, int position) {
        mChildrenViews.put(position, view);
    }
}
