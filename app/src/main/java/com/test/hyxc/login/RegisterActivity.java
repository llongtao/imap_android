package com.test.hyxc.login;

import android.content.Intent;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.model.UserInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.LoadingDialog;
import tools.NetUtils;
import tools.patternUtils;

/**
 * Created by WWW on 2019/1/29.
 */

public class RegisterActivity  extends BaseActivity{
    private ImageView iv_back;
    private EditText et_phone,et_code,et_password;
    private Button btn_code;
    private ImageView iv_phone,iv_password;
    private LinearLayout ll_service_text;
    private TextView tv_register;
    private String phone="";
    private TimerTask timerTask;
    private Timer timer;
    private int timess;
    private boolean getCode=false;
    private boolean chongfa=false;
    private Handler handler = new Handler();
    //
    private LoadingDialog loadingDialog;
    @Override
    protected int getLayoutID() {
        return R.layout.imap_register;
    }
    @Override
    protected void initListener() {
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0,R.anim.imap_fade_out_right);
                finish();
            }
        });
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_phone.setText("");
            }
        });
        iv_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD == et_password.getInputType()) {
                    et_password.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    iv_password.setImageResource(R.mipmap.imap_invisible);
                } else {
                    et_password.setInputType(EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    iv_password.setImageResource(R.mipmap.imap_visible);
                }
                //执行上面的代码后光标会处于输入框的最前方,所以把光标位置挪到文字的最后面
                et_password.setSelection(et_password.getText().toString().length());
            }
        });
        btn_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!patternUtils.isPhone(et_phone.getText().toString())){
                   Toast.makeText(RegisterActivity.this,"手机号输入有误，请检查",Toast.LENGTH_LONG).show();
                   return;
               }else {
                   phone=et_phone.getText().toString();
                   getUUID();
                   btn_code.setBackgroundResource(R.drawable.imap_code_get_cannot);
                   startTimer();
               }
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_phone.getText().toString()==null||"".equals(et_phone.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"手机号未输入！",Toast.LENGTH_LONG).show();
                    return;
                }
                if(!patternUtils.isPhone(et_phone.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"手机号输入异常",Toast.LENGTH_LONG).show();
                     return;
                }
                if(et_password.getText()==null||"".equals(et_password.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"密码未设置，请检查",Toast.LENGTH_LONG).show();
                    return;
                }
                if(et_password.getText()!=null&&et_password.getText().toString().length()<6){
                    Toast.makeText(RegisterActivity.this,"密码最少6位",Toast.LENGTH_LONG).show();
                    return;
                }
                if(et_code.getText()==null||"".equals(et_code.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"验证码未输入",Toast.LENGTH_LONG).show();
                    return;
                }
                if(et_code.getText()!=null&&et_code.getText().toString().length()!=6){
                    Toast.makeText(RegisterActivity.this,"验证码应该6位，请检查",Toast.LENGTH_LONG).show();
                    return;
                }
                //输入正确后 1验证验证码 2：注册
                final String phone=et_phone.getText().toString();
                String code=et_code.getText().toString();
                final String password=et_password.getText().toString();
                if(getCode){
                    SMSSDK.getInstance().checkSmsCodeAsyn(phone, code, new SmscheckListener() {
                        @Override
                        public void checkCodeSuccess(final String code) {
                            // 验证码验证成功，code 为验证码信息。
                            try {
                                tryRegister(phone,password);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        @Override
                        public void checkCodeFail(int errCode, final String errMsg) {
                            Toast.makeText(RegisterActivity.this,"验证码输入有误,注册失败",Toast.LENGTH_LONG).show();
                            return;
                        }
                    });
                }else {
                    Toast.makeText(RegisterActivity.this,"请先发送验证码！",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //正式注册
    public void  tryRegister(String phone,String password) throws Exception {
        NetUtils netUtils=NetUtils.getInstance();
        String token="";
        Map map=new HashMap();
        map.put("phone",phone);
        map.put("password",password);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog=new LoadingDialog(RegisterActivity.this);
                loadingDialog.show();
            }
        });
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/appregister",token, map, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException,JSONException {
                String ret=response.body().string();
                JSONObject json = new JSONObject(ret);
                if (json.has("regState")&&json.getString("regState").equals("reg_ever")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"手机号已经注册,请直接登录",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if(json.has("regState")&&json.getString("regState").equals("database_error")){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(RegisterActivity.this,"注册发生异常..请稍后再试",Toast.LENGTH_LONG).show();
                        }
                    });
                }
                if(json.has("regState")&&json.getString("regState").equals("success")){
                    final Gson gson = new Gson();
                    JSONObject userInfo=json.getJSONObject("user_info");
                    JsonParser parser = new JsonParser();
                    final JsonObject jsonObject = parser.parse(userInfo.toString()).getAsJsonObject();
                    //注册完成后 设置默认的头像
                    ((AppContext)getApplicationContext()).setHeadImg(userInfo.getString("user_headimg"));
                   //注册完成后可以登陆跳转到登陆页面
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,"恭喜注册成功！！",Toast.LENGTH_LONG).show();
                        }
                    });
                    handler.postDelayed(new Runnable() {
                        @Override
                         public void run() {
                                       Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                                       startActivity(intent);
                                       finish();
                                    }
                         }, 1000);
              }
            }
            @Override
            public void failed(Call call, IOException e) {
                Toast.makeText(RegisterActivity.this,"网络出错了~",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void initView() {
        iv_back=f(R.id.iv_back);
        et_phone=f(R.id.et_phone);
        et_code=f(R.id.et_code);
        et_password=f(R.id.et_password);
        btn_code=f(R.id.btn_code);
        iv_phone=f(R.id.iv_phone);
        iv_password=f(R.id.iv_password);
        ll_service_text=f(R.id.ll_service_text);
        tv_register=f(R.id.tv_register);
        //极光短信
        SMSSDK.getInstance().initSdk(this);
        SMSSDK.getInstance().setIntervalTime(60*1000);
    }

    @Override
    protected void initData() {}
    //获取验证码的方法
    public void getUUID(){
        SMSSDK.getInstance().getSmsCodeAsyn(phone,1+"", new SmscodeListener() {
            @Override
            public void getCodeSuccess(final String uuid) {
                getCode=true;
            }
            @Override
            public void getCodeFail(int errCode, final String errmsg) {
                //失败后停止计时
                stopTimer();
                if(errCode==2996){
                    Toast.makeText(RegisterActivity.this,"获取验证码间隔须大于60秒",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "获取短信验证码异常~", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private void startTimer(){
        timess = (int) (SMSSDK.getInstance().getIntervalTime()/1000);
        btn_code.setText(timess+"秒");
        btn_code.setClickable(false);
        this.chongfa=false;
        if(timerTask==null){
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timess--;
                            if(timess<=0){
                                stopTimer();
                                return;
                            }
                            btn_code.setText(timess+"秒");
                        }
                    });
                }
            };
        }
        if(timer==null){
            timer = new Timer();
        }
        timer.schedule(timerTask, 1000, 1000);
    }
    private void stopTimer(){
        if(timer!=null){
            timer.cancel();
            timer=null;
        }
        if(timerTask!=null){
            timerTask.cancel();
            timerTask=null;
        }
        btn_code.setText("获取");
        btn_code.setBackgroundResource(R.drawable.imap_code_get);
        btn_code.setClickable(true);
        chongfa=true;
    }
}
