package com.test.hyxc.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.widget.TextView;

import com.test.hyxc.R;

public class SimpleDlg extends Dialog {
    private TextView tv;
    public SimpleDlg(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imap_change_cover_dlg);
        tv=findViewById(R.id.tv_change_cover);
    }
}
