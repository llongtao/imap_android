package com.zaaach.citypicker;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by WWW on 2019/1/20.
 */

public class CityActivity extends FragmentActivity {
    private FrameLayout fl_city;
    private CityPickerDialogFragment cityPickerDialogFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private float x1,x2,y1,y2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.imap_city);
        fl_city=findViewById(R.id.fl_city);
        manager = getSupportFragmentManager();
        cityPickerDialogFragment= CityPickerDialogFragment.newInstance(100,true);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_city,cityPickerDialogFragment,"cityfrag");
        transaction.commit();
        // 创建
    }

    private ArrayList<MyOnTouchListener> onTouchListeners = new ArrayList<MyOnTouchListener>(
            10);

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        for (MyOnTouchListener listener : onTouchListeners) {
            listener.onTouch(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void registerMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.add(myOnTouchListener);
    }

    public void unregisterMyOnTouchListener(MyOnTouchListener myOnTouchListener) {
        onTouchListeners.remove(myOnTouchListener);
    }

    public interface MyOnTouchListener {
        public boolean onTouch(MotionEvent ev);
    }

}
