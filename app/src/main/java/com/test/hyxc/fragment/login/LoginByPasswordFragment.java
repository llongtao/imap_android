package com.test.hyxc.fragment.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.test.hyxc.MainActivity;
import com.test.hyxc.R;
import com.test.hyxc.login.RegisterActivity;

import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import com.test.hyxc.chat.database.UserEntry;
import com.test.hyxc.chat.utils.SharePreferenceManager;

import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.LoadingDialog;
import tools.NetUtils;
import tools.patternUtils;

public class LoginByPasswordFragment extends Fragment {
    private EditText et_phone,et_password;
    private ImageView iv_phone,iv_password;
    private LinearLayout ll_login;
    private TextView tv_register;
    private LoadingDialog loadingDialog;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imap_login_password_fragment, container, false);
        initViews(view);
        return view;
    }
    public void initViews(View view ){
        et_phone=view.findViewById(R.id.et_phone);
        et_password=view.findViewById(R.id.et_password);
        iv_phone=view.findViewById(R.id.iv_phone);
        iv_password=view.findViewById(R.id.iv_password);
        ll_login=view.findViewById(R.id.ll_login);
        tv_register=view.findViewById(R.id.tv_register);
        //手机号
        iv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_phone.setText("");
            }
        });
        //密码
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
        //登陆建
        ll_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryLogin();
            }
        });
        //注册建
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RegisterActivity.class));
                getActivity().overridePendingTransition(0,R.anim.imap_fade_out);
            }
        });
    }
    public void tryLogin(){
          String phone=et_phone.getText().toString();
          String password=et_password.getText().toString();
          if(phone==null||"".equals(phone)){
              Toast toast=Toast.makeText(getContext(),"手机号不能为空哦~",Toast.LENGTH_SHORT);
              toast.setGravity(Gravity.TOP|Gravity.CENTER,0,260);
              toast.show();
          }else if(password==null||"".equals(password)){
              Toast toast=Toast.makeText(getContext(),"密码未输入哦",Toast.LENGTH_SHORT);
              toast.setGravity(Gravity.TOP|Gravity.CENTER,0,260);
              toast.show();
          } else if(!patternUtils.isPhone(phone)){
              Toast toast=Toast.makeText(getContext(),"手机号输入有误",Toast.LENGTH_SHORT);
              toast.setGravity(Gravity.TOP|Gravity.CENTER,0,260);
              toast.show();
          }else {
              //登陆
              try {
                  doLogin(phone, password);
              } catch (Exception e) {
                  e.printStackTrace();
              }
          }

    }
    //发送网络请求
    public void doLogin(String phone,String password) throws Exception {
          //网络加载框
          getActivity().runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  loadingDialog=new LoadingDialog(getContext());
                  loadingDialog.show();
              }
          });
          //登陆 token="login"
          NetUtils netUtils=NetUtils.getInstance();
          //初始化token 未登陆前的token“”
          String token=((AppContext)getActivity().getApplicationContext()).getToken();
          Map map =new HashMap();
          map.put("phone",phone);
          map.put("password",password);
          netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/applogin_android_pwd", "", map, new NetUtils.MyNetCall() {
              @Override
              public void success(Call call, Response response) throws IOException,JSONException {
                  getActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          loadingDialog.dismiss();
                      }
                  });
                  String ret=response.body().string();
                  JSONObject json=new JSONObject(ret);
                  if(json.getString("loginState").equals("phoneNotExist")||json.getString("loginState").equals("passwordWrong")){
                      Looper.prepare();
                      Toast toast=Toast.makeText(getContext(),"用户名或密码错误~~",Toast.LENGTH_LONG);
                      toast.setGravity(Gravity.TOP|Gravity.CENTER,0,440);
                      toast.show();
                      Looper.loop();
                  }else{
                      if(json.getString("loginState").equals("success")){
                          ((AppContext)getActivity().getApplicationContext()).setUserId(json.getString("userId"));
                          ((AppContext)getActivity().getApplicationContext()).setUsername(json.getString("username"));
                          ((AppContext)getActivity().getApplicationContext()).setPassword(json.getString("password"));
                          ((AppContext)getActivity().getApplicationContext()).setToken(json.getString("token"));
                          //这里有可能第一次注册登录 是没有nickname的
                          if(json.has("nickname")){
                              ((AppContext)getActivity().getApplicationContext()).setNickname(json.getString("nickname")==null?"":json.getString("nickname"));
                          }
                          ((AppContext)getActivity().getApplicationContext()).setHeadImg(json.getString("headimg")==null?"":json.getString("headimg"));
                          if(json.has("userIslandFollow")) {
                              ((AppContext) getActivity().getApplicationContext()).setUserIslandFollow(json.getString("userIslandFollow") == null ? "" : json.getString("userIslandFollow"));
                          }
                          if(json.has("userIsland")) {
                              ((AppContext) getActivity().getApplicationContext()).setUserIsland(json.getString("userIsland") == null ? "" : json.getString("userIsland"));
                          }
                          if(json.has("userIslandLimit")){
                              ((AppContext)getActivity().getApplicationContext()).setUserIslandLimit(String.valueOf(json.getInt("userIslandLimit")));
                          }
                          //记录下最后一次成功登陆的时间
                          ((AppContext)getActivity().getApplicationContext()).setLastLoginTime(String.valueOf(System.currentTimeMillis()));
                          //这是要登陆极光系统
                          startActivity(new Intent(getActivity(), MainActivity.class));
                          loginJiguang();
                          getActivity().finish();
                      }
                  }
              }
              @Override
              public void failed(Call call, IOException e) {
                  getActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          loadingDialog.dismiss();
                      }
                  });
                  Looper.prepare();
                  Toast toast=Toast.makeText(getContext(),"发生未知错误..",Toast.LENGTH_LONG);
                  toast.setGravity(Gravity.TOP|Gravity.CENTER,0,440);
                  toast.show();
                  Looper.loop();
              }
          });
    }
    public  void loginJiguang() {
        try {
            JMessageClient.login(((AppContext) getActivity().getApplicationContext()).getUsername(), ((AppContext) getActivity().getApplicationContext()).getPassword(), new BasicCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage) {
                    if (responseCode == 0) {
                        SharePreferenceManager.setCachedPsw(((AppContext) getActivity().getApplicationContext()).getPassword());
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