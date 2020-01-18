package com.test.hyxc.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.widget.ImageView;

import com.test.hyxc.R;

public class IslandDescDlg extends Dialog {
    private ImageView iv;
    public IslandDescDlg(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imap_island_desc);
        iv=findViewById(R.id.iv_big_head);
    }
}
