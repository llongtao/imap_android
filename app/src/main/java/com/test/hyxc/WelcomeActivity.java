package com.test.hyxc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.test.hyxc.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import tools.*;

public class WelcomeActivity extends BaseActivity {
    public AppContext ctx;
    @Override
    protected int getLayoutID() {
        return R.layout.activity_welcome;
    }
    @Override
    protected void initView() {
        ctx = (AppContext)this.getApplicationContext();
        //关联布局   需要让整个页面实现 动画效果 和 跳转 功能  所以先要得到一个视图view，对视图进行操作
        LayoutInflater inflater = LayoutInflater.from(this);
        View root = inflater.inflate(getLayoutID(), null);
        setContentView(root);
        Animation aa = AnimationUtils.loadAnimation(this, R.anim.alpha);
        root.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationEnd(Animation arg0) {
                doJump();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationStart(Animation animation) {}
        });
    }
    @Override
    protected void initListener() {}
    @Override
    protected void initData() { }
    private void doJump(){
        //如果是第一次运行程序，
        boolean first = this.ctx.sysSharedPreferences.getBoolean("first", true);
        if(first){
            startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
            this.ctx.sysSharedPreferences
                    .edit()
                    .putBoolean("first", false)
                    .commit();
            WelcomeActivity.this.finish();
        }else{//FIXME 检查网络
            if(-1 == SystemHelper.getNetWorkType(WelcomeActivity.this)){
                //使用隐式Intent打开网络设置程序
                Toast.makeText(WelcomeActivity.this, "网络暂时不能用吖~~", Toast.LENGTH_LONG).show();
                return ;
            }
            //判断是否已经登陆过 token
            //1：如果用户名和密码都不存在 就到登录页
            if(TextUtils.isEmpty(ctx.getUsername())&&TextUtils.isEmpty(ctx.getPassword())){
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                WelcomeActivity.this.finish();
            }
            else{
                //2：否则如果存在用户名和密码 ，检查token 如果token过期就自动登陆
                if(TextUtils.isEmpty(ctx.getToken())){
                    try {
                        autoLogin();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else{
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    WelcomeActivity.this.finish();
                }
            }
        }
    }
    @Override //禁用按钮
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
    private void autoLogin() throws Exception {
        final String username = ctx.getUsername();
        final String password = ctx.getPassword();
        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            NetUtils netUtils=NetUtils.getInstance();
            String token=ctx.getToken();
            Map map=new HashMap();
            map.put("phone",username);
            map.put("password",password);
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/applogin_android_pwd",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    if(json.getString("loginState").equals("phoneNotExist")||json.getString("loginState").equals("passwordWrong")){
                        startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                        WelcomeActivity.this.finish();
                    }else{
                        if(json.getString("loginState").equals("success")){
                             ctx.setUserId(json.getString("userId"));
                             ctx.setUsername(json.getString("username"));
                             ctx.setPassword(json.getString("password"));
                             ctx.setToken(json.getString("token"));
                             ctx.setNickname(json.getString("nickname")==null?"":json.getString("nickname"));
                             ctx.setHeadImg(json.getString("headimg")==null?"":json.getString("headimg"));
                            if(json.has("userIslandFollow")) {
                                ((AppContext) getApplicationContext()).setUserIslandFollow(json.getString("userIslandFollow") == null ? "" : json.getString("userIslandFollow"));
                            }
                            if(json.has("userIsland")) {
                                ((AppContext) getApplicationContext()).setUserIsland(json.getString("userIsland") == null ? "" : json.getString("userIsland"));
                            }
                            if(json.has("userIslandLimit")){
                                ((AppContext)getApplicationContext()).setUserIslandLimit(String.valueOf(json.getInt("userIslandLimit")));
                            }
                            //记录上次登陆时间，用于判断如果大于7天就自动重新登陆
                            ctx.setLastLoginTime(String.valueOf(System.currentTimeMillis()));
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            WelcomeActivity.this.finish();
                        }
                    }
                }
                @Override
                public void failed(Call call, IOException e) {}
            });
        }else{
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        }
    }
}
