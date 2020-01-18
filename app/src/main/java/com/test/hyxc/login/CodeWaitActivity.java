package com.test.hyxc.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.test.hyxc.BaseActivity;
import com.test.hyxc.MainActivity;
import com.test.hyxc.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import cn.jpush.sms.SMSSDK;
import cn.jpush.sms.listener.SmscheckListener;
import cn.jpush.sms.listener.SmscodeListener;
import com.test.hyxc.chat.database.UserEntry;
import com.test.hyxc.chat.utils.SharePreferenceManager;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.NetUtils;

/**
 * Created by WWW on 2019/1/28.
 */

public class CodeWaitActivity  extends BaseActivity {
    private ImageView iv_back;
    private String phone="";
    private Button btn_ask;
    private TimerTask timerTask;
    private Timer timer;
    private int timess;
    private Button btn_next;
    private EditText et_code;
    private boolean getCode=false;
    //是否处于重发状态
    private boolean chongfa=false;

    @Override
    protected int getLayoutID() {
        return R.layout.imap_code_wait;
    }

    @Override
    protected void initListener() {}

    @Override
    protected void initView() {
        Bundle bundle=getIntent().getExtras();
        final String phone=bundle.getString("phone");
        this.phone=phone;
        iv_back=f(R.id.iv_back);
        btn_ask=f(R.id.btn_ask);
        btn_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!chongfa){
                  return;
                }else {
                    chongfa=!chongfa;
                    getUUID();
                    startTimer();
                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,R.anim.imap_fade_out_right);
            }
        });
        btn_next=f(R.id.btn_next);
        et_code=f(R.id.et_code);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=et_code.getText().toString();
                if(code.length()!=6){
                    Toast.makeText(CodeWaitActivity.this,"验证码格式不对,请检查~",Toast.LENGTH_LONG).show();
                }else {
                    if(getCode){
                        SMSSDK.getInstance().checkSmsCodeAsyn(phone, code, new SmscheckListener() {
                            @Override
                            public void checkCodeSuccess(final String code) {
                                // 验证码验证成功，code 为验证码信息。
                                try {
                                    tryLogin();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            @Override
                            public void checkCodeFail(int errCode, final String errMsg) {
                                Toast.makeText(CodeWaitActivity.this,"验证码输入有误,登录失败",Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        Toast.makeText(CodeWaitActivity.this,"还未发送验证码哦",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //极光短信
        SMSSDK.getInstance().initSdk(this);
        SMSSDK.getInstance().setIntervalTime(60*1000);
        getUUID();
        startTimer();
    }
    //验证码通过后进行登陆
    public void tryLogin() throws Exception {
        NetUtils netUtils=NetUtils.getInstance();
        String token="";
        Map map=new HashMap();
        map.put("phone",phone);
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/applogin_android_code",token, map, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException,JSONException {
                String ret=response.body().string();
                JSONObject json=new JSONObject(ret);
                if(json.getString("loginState").equals("phoneNotExist")){
                    Looper.prepare();
                    Toast toast=Toast.makeText(CodeWaitActivity.this,"手机号未注册",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,440);
                    toast.show();
                    Looper.loop();
                    finish();
                    return;
                }else{
                    if(json.getString("loginState").equals("success")){
                        ((AppContext)getApplicationContext()).setUserId(json.getString("userId"));
                        ((AppContext)getApplicationContext()).setUsername(json.getString("username"));
                        ((AppContext)getApplicationContext()).setPassword(json.getString("password"));
                        ((AppContext)getApplicationContext()).setToken(json.getString("token"));
                        ((AppContext)getApplicationContext()).setNickname(json.has("nickname") ? json.getString("nickname") : "");
                        ((AppContext)getApplicationContext()).setHeadImg(json.has("headimg") ? json.getString("headimg") :"");
                        ((AppContext)getApplicationContext()).setUserIslandFollow(json.has("userIslandFollow")?json.getString("userIslandFollow") : "");
                        ((AppContext)getApplicationContext()).setUserIsland(json.has("userIsland") ? json.getString("userIsland") : "");
                        if(json.has("userIslandLimit")){
                            ((AppContext)getApplicationContext()).setUserIslandLimit(String.valueOf(json.getInt("userIslandLimit")));
                        }
                        //记录下最后一次成功登陆的时间
                        ((AppContext)getApplicationContext()).setLastLoginTime(String.valueOf(System.currentTimeMillis()));
                        loginJiguang();
                        startActivity(new Intent(CodeWaitActivity.this, MainActivity.class));
                        finish();
                    }
                }
            }
            @Override
            public void failed(Call call, IOException e) {}
        });
    }
    //获取验证码的方法
    public void getUUID(){
            SMSSDK.getInstance().getSmsCodeAsyn(phone,1+"", new SmscodeListener() {
                @Override
                    public void getCodeSuccess(final String uuid) {
                        getCode=true;
                        Toast.makeText(CodeWaitActivity.this,"验证码发送成功，请填写",Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void getCodeFail(int errCode, final String errmsg) {
                        //失败后停止计时
                        stopTimer();
                        if(errCode==2996){
                            Toast.makeText(CodeWaitActivity.this,"获取验证码间隔须大于60秒",Toast.LENGTH_LONG).show();
                        }else {
                            Toast.makeText(CodeWaitActivity.this, "获取短信验证码异常~", Toast.LENGTH_LONG).show();
                        }
                        finish();
                    }
                });
    }
    private void startTimer(){
        timess = (int) (SMSSDK.getInstance().getIntervalTime()/1000);
        btn_ask.setText(timess+"秒");
        btn_ask.setClickable(false);
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
                            btn_ask.setText(timess+"秒");
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
        btn_ask.setText("重发");
        btn_ask.setBackgroundColor(Color.parseColor("#CA80E7"));
        btn_ask.setClickable(true);
        chongfa=true;
    }
    @Override
    protected void initData() {}


    public  void loginJiguang() {
        try {
            JMessageClient.login(((AppContext) getApplicationContext()).getUsername(), ((AppContext) getApplicationContext()).getPassword(), new BasicCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage) {
                    if (responseCode == 0) {
                        SharePreferenceManager.setCachedPsw(((AppContext) getApplicationContext()).getPassword());
                        UserInfo myInfo = JMessageClient.getMyInfo();
                        File avatarFile = myInfo.getAvatarFile();
                        //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                        if (avatarFile != null) {
                            SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                        } else {
                            SharePreferenceManager.setCachedAvatarPath(null);
                        }
                        String username = myInfo.getUserName();
                        String appKey = myInfo.getAppKey();
                        /*UserEntry user = UserEntry.getUser(username, appKey);
                        if (null == user) {
                            user = new UserEntry(username, appKey);
                            user.save();
                        }*/
                    } else {
                        Log.i("极光登陆失败", "极光登陆失败");
                    }
                }
            });
        }catch (Exception e){
            Log.i("login Jiguang error", "login Jiguang error!");

        }
    }
}
