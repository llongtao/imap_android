package com.test.hyxc.page.island;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.GlobalConfig;
import tools.NetUtils;

/**
 * Created by Mac on 8/19/19.
 */

public class IslandJoinAllowActivity extends BaseActivity {
    RadioGroup rg_select;
    RadioButton can,cannot,verify;
    int join_allow = 0; //0直接加入 1：不允许  2：验证加入
    Button save,cancel,btn_location_back;
    Island island;
    @Override
    protected int getLayoutID() {
        return R.layout.imap_island_join_allow_privilege_list;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initView() throws Exception {
        island = (Island) getIntent().getSerializableExtra("island");
        rg_select=f(R.id.rg_select);
        btn_location_back=f(R.id.btn_location_back);
        verify=(RadioButton)this.findViewById(R.id.verify);
        can=(RadioButton)this.findViewById(R.id.can);
        cannot=(RadioButton)this.findViewById(R.id.cannot);
        save =f(R.id.save);
        cancel = f(R.id.cancel);
        switch (island.getIs_join_allow()){
            case 0:
                can.setChecked(true);
                break;
            case 1:
                cannot.setChecked(true);
                break;
            case 2:
                verify.setChecked(true);
                break;
            default:
                can.setChecked(true);
                break;
        }
        btn_location_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rg_select.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //如果‘海南’这个单选按钮被选中了
                if(can.getId()==checkedId){
                    //弹出吐司通知
                    join_allow=0;
                }else if(cannot.getId() == checkedId){
                    join_allow = 1;
                }else{
                    join_allow = 2;
                }

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 updateAskAllow();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
    }

    public void updateAskAllow(){
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("is_id",""+island.getIs_id());
            map.put("is_join_allow",String.valueOf(join_allow));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/changIsByCondition", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        return;
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (json.has("changIsByCondition") && json.get("changIsByCondition").equals("success")) {
                                        IslandJoinAllowActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                //发送广播修改点赞数量 和点赞状态
                                                Intent intent =new Intent();
                                                intent.setAction(GlobalConfig.islandState);
                                                intent.putExtra("is_id",island.getIs_id());
                                                intent.putExtra("is_join_allow",join_allow);
                                                sendBroadcast(intent);
                                                ToastUtil.shortToast(IslandJoinAllowActivity.this,"修改状态成功~");
                                                finish();
                                            }
                                        });
                                    } else {
                                        //loading对话框关闭
                                        IslandJoinAllowActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtil.shortToast(IslandJoinAllowActivity.this,"修改状态发生异常~");
                                                finish();
                                            }
                                        });
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
                @Override
                public void failed(Call call, IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
