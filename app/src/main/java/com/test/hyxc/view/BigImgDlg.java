package com.test.hyxc.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.hyxc.R;

public class BigImgDlg extends Dialog {
    private ImageView iv;
    public BigImgDlg(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imap_headimg_bigshow);
        iv=findViewById(R.id.iv_big_head);
    }
}
