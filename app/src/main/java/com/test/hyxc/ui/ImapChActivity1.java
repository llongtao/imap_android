package com.test.hyxc.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.FrameLayout;

import com.test.hyxc.MainFragment;
import com.test.hyxc.R;
import com.zaaach.citypicker.CityPickerDialogFragment;

import java.util.ArrayList;

public class ImapChActivity1 extends FragmentActivity {

    private ChSlideMenu chSlideMenu;
    private FrameLayout fl_content,fl_city;
    private MainFragment mainFragment;
    private CityPickerDialogFragment cityPickerDialogFragment;
    private FragmentManager manager;
    private FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.imap_ch_main1);
        chSlideMenu = findViewById(R.id.slideMenu);
        fl_content = findViewById(R.id.fl_content);
        fl_city=findViewById(R.id.fl_city);
        manager = getSupportFragmentManager();
        mainFragment = new MainFragment();

        //cityPickerDialogFragment= CityPickerDialogFragment.newInstance(getChSlideMenu().LEFT_CONTAINER,true);
        transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_content, mainFragment, "mymainfrag");
        transaction.replace(R.id.fl_city,cityPickerDialogFragment,"cityfrag");
        transaction.commit();
        // 创建
    }

    @Override
    protected void onResume() {
       /* tv_1 = findViewById(R.id.menu1);
        tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = (String) ((TextView) view).getText();
                ((MainFragment) manager.findFragmentByTag("mymainfrag")).tv_city.setText(str);
            }
        });*/
        super.onResume();
    }

    public ChSlideMenu getChSlideMenu() {
        return chSlideMenu;
    }

    /**
     * 以下的几个方法用来，让fragment能够监听touch事件
     */
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
         boolean onTouch(MotionEvent ev);
    }
}
