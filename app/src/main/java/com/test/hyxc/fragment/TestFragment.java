package com.test.hyxc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.test.hyxc.R;

/**
 * Created by Mac on 2019/7/27.
 */

public class TestFragment extends Fragment {
    private final static String TAG = TestFragment.class.getSimpleName();
    private String test;
    public View mContentView;

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate:  test = "+test);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.layout_fg, null);
        Log.e(TAG, "onCreateView: test = "+test);
        return mContentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.e(TAG, "onViewCreated: test = "+test);
        TextView testText = mContentView.findViewById(R.id.tv_test);
        testText.setText(test);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e(TAG, "onActivityCreated: test = "+test);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy:  test = "+test);
    }
}
