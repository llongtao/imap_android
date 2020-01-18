package com.test.hyxc.page.island;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.model.Island;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetAvatarBitmapCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import tools.GlobalConfig;
public class IslandSettingsActivity extends BaseActivity   {
    ImageButton return_btn;
    TextView title;
    ImageView iv_group_logo;
    TextView tv_nickname;
    Island island;
    GroupInfo mGroupInfo;
    LinearLayout join_allow;
    LinearLayout ask_allow;
    ReceiveBroadCast receiveBroadCast;
    Button dismiss;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_island_settings;
    }

    @Override
    protected void initListener() {
    }
    @Override
    protected void initView() {
        island = (Island) getIntent().getSerializableExtra("island");
        iv_group_logo=f(R.id.iv_group_logo);
        tv_nickname = f(R.id.tv_nickname);
        title=f(R.id.title);
        return_btn=f(R.id.return_btn);
        join_allow =f(R.id.join_allow);
        ask_allow =f(R.id.ask_allow);
        dismiss = f(R.id.dismiss);
        //获得群组基本信
        try {
            Conversation conv = JMessageClient.getGroupConversation(island.getIs_im_id());
            mGroupInfo = (GroupInfo) conv.getTargetInfo();
            fillGroupLogo(mGroupInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        title.setText("海岛状态设置");
        join_allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent();
                intent.setClass(IslandSettingsActivity.this,IslandJoinAllowActivity.class);
                intent.putExtra("island",island);
                startActivity(intent);
            }
        });
        ask_allow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(IslandSettingsActivity.this,IslandAskAllowActivity.class);
                intent.putExtra("island",island);
                startActivity(intent);
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.shortToast(IslandSettingsActivity.this,"当前版本不支持解散海岛哦~");
            }
        });
        receiveBroadCast=new ReceiveBroadCast();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(GlobalConfig.islandState);
        registerReceiver(receiveBroadCast,intentFilter);

    }
    @Override
    protected void initData() {}
    //显示海岛头像 gridview 隐藏
    public void fillGroupLogo(GroupInfo groupInfo){
        tv_nickname.setText(groupInfo.getGroupName().length()>3?groupInfo.getGroupName().substring(0,3)+"...":groupInfo.getGroupName());
        if (!TextUtils.isEmpty(groupInfo.getAvatar())) {
            groupInfo.getAvatarBitmap(new GetAvatarBitmapCallback() {
                @Override
                public void gotResult(int status, String desc, Bitmap bitmap) {
                    if (status == 0) {
                        iv_group_logo.setImageBitmap(bitmap);
                    }
                }
            });
        }
    }

    class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //得到广播中得到的数据  
            Integer is_id = intent.getExtras().getInt("is_id");
            Integer is_join_allow=null,is_ask_allow=null;
            if(intent.hasExtra("is_join_allow"))
                is_join_allow=intent.getExtras().getInt("is_join_allow");
            if(intent.hasExtra("is_ask_allow"))
                is_ask_allow=intent.getExtras().getInt("is_ask_allow");
            if(island.getIs_id().equals(is_id)){
                if(is_join_allow!=null){
                    island.setIs_join_allow(is_join_allow);
                }
                if(is_ask_allow  !=null){
                    island.setIs_ask_allow(is_ask_allow);
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiveBroadCast);
    }



}
