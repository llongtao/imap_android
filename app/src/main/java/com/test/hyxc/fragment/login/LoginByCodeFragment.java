package com.test.hyxc.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.test.hyxc.R;
import com.test.hyxc.login.CodeWaitActivity;

import tools.patternUtils;

/**
 * Created by WWW on 2019/1/28.
 */

public class LoginByCodeFragment extends Fragment {
    private TextView tv_get_code;
    private EditText et_phone;
    private ImageView iv_phone;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imap_login_code_fragment, container, false);
        tv_get_code=view.findViewById(R.id.tv_get_code);
        et_phone=view.findViewById(R.id.et_phone);
        iv_phone=view.findViewById(R.id.iv_phone);
        initListeners();
        return view;
    }
    public void initListeners(){
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_phone.setText("");
            }
        });
        tv_get_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patternUtils.isPhone(et_phone.getText().toString())){
                     //跳转到等待验证码页面
                    Intent intent=new Intent();
                    intent.setClass(getActivity(),CodeWaitActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("phone",et_phone.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    Toast toast=Toast.makeText(getContext(),"手机号码无效~",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,360);
                    toast.show();
                }
            }
        });
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String phone=et_phone.getText().toString();
                if(patternUtils.isPhone(phone)){
                    tv_get_code.setBackgroundResource(R.drawable.imap_button);
                }else {
                    tv_get_code.setBackgroundResource(R.drawable.imap_button_cannot);
                }
            }
        });

    }
}
