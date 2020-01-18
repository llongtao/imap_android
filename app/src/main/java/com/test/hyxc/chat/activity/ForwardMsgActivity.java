package com.test.hyxc.chat.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.CustomContent;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.Message;
import cn.jpush.im.android.api.options.MessageSendingOptions;
import cn.jpush.im.api.BasicCallback;
import tools.AppContext;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.test.hyxc.R;
import com.test.hyxc.ccb.utils.GsonUtils;
import com.test.hyxc.chat.adapter.ForwardMsgAdapter;
import com.test.hyxc.chat.controller.ActivityController;
import com.test.hyxc.chat.utils.DialogCreator;
import com.test.hyxc.chat.utils.HandleResponseCode;
import com.test.hyxc.model.ActivityShow;
import com.test.hyxc.model.UserInfo;
import com.test.hyxc.model.WorkShow;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by ${chenyn} on 2017/7/16.
 */

public class ForwardMsgActivity extends BaseActivity {

    private LinearLayout mLl_groupAll;
    private LinearLayout mSearch_title;
    private ListView mListView;
    private ForwardMsgAdapter mAdapter;
    private Dialog mDialog;
    private List<Conversation> forwardList = new ArrayList<>();
    public String useType="";
    public WorkShow workShow;
    public ActivityShow activityShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forward_msg);
        if(getIntent().hasExtra("useType") && getIntent().hasExtra("workShow")) {
            useType = getIntent().getExtras().getString("useType");
            workShow = (WorkShow) getIntent().getSerializableExtra("workShow");
        }
        if(getIntent().hasExtra("useType") && getIntent().hasExtra("activityShow")){
            useType = getIntent().getExtras().getString("useType");
            activityShow = (ActivityShow) getIntent().getSerializableExtra("activityShow");
        }
        ActivityController.addActivity(this);
        initView();
        initData();
    }

    private void initView() {
        if (getIntent().getFlags() == 1) {
            initTitle(true, true, "发送名片", "", false, "");
        } else {
            initTitle(true, true, "转发", "", false, "");
        }

        mListView = (ListView) findViewById(R.id.forward_business_list);
        mLl_groupAll = (LinearLayout) findViewById(R.id.ll_groupAll);
        mSearch_title = (LinearLayout) findViewById(R.id.search_title);
    }

    private void initData() {
        List<Conversation> conversationList = JMessageClient.getConversationList();
        for (Conversation conv : conversationList) {
            if (!conv.getTargetId().equals("feedback_Android")) {
                forwardList.add(conv);
            }
        }
        mAdapter = new ForwardMsgAdapter(this, forwardList);
        mListView.setAdapter(mAdapter);
        //搜索栏
        mSearch_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForwardMsgActivity.this, SearchContactsActivity.class);
                setExtraIntent(intent);
            }
        });

        //群组
        mLl_groupAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForwardMsgActivity.this, GroupActivity.class);
                setExtraIntent(intent);
            }
        });

        //最近联系人
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(useType.equals("share")){ //转发自定义 作品消息
                    final Object itemAtPosition = parent.getItemAtPosition(position);
                    final Conversation conversation = (Conversation) itemAtPosition;
                    CustomContent customContent = new CustomContent();
                    String work_text= ""; //防止空指针
                    if(workShow.getWork_text()!=null && !"".equals(workShow.getWork_text()))
                        work_text = workShow.getWork_text();
                    customContent.setStringValue("share_type","work");
                    customContent.setStringValue("work_text",work_text.length()>60?work_text.substring(0,60):work_text);
                    customContent.setStringValue("work_id",String.valueOf(workShow.getWork_id()));
                    customContent.setStringValue("work_type",String.valueOf(workShow.getWork_type()));
                    customContent.setStringValue("work_author_id",String.valueOf(workShow.getWork_author()));
                    customContent.setStringValue("work_author_logo",workShow.getUser_headimg());
                    customContent.setStringValue("work_author_name",workShow.getUser_nickname());
                    customContent.setStringValue("work_island",String.valueOf(workShow.getWork_island()));
                    customContent.setStringValue("work_island_name",workShow.getWork_island_name());
                    customContent.setStringValue("work_island_logo",workShow.getWork_island_logo());
                    //customContent.setStringValue("workShow",GsonUtils.toJson(workShow)); //防止消息体过大 4k限制
                    if(conversation.getType()== ConversationType.single){ //单聊
                        Message customMsg = conversation.createSendMessage(customContent);
                        AppContext.forwardMsg.clear();
                        AppContext.forwardMsg.add(customMsg);
                    }else{ //群聊
                        Message customMsg = conversation.createSendMessage(customContent);
                         AppContext.forwardMsg.clear();
                         AppContext.forwardMsg.add(customMsg);
                    }
                    DialogCreator.createForwardMsg(ForwardMsgActivity.this, mWidth, true, conversation, null, null, null);
                }else if(useType.equals("shareActivity")){ //转发活动消息
                    final Object itemAtPosition = parent.getItemAtPosition(position);
                    final Conversation conversation = (Conversation) itemAtPosition;
                    CustomContent customContent = new CustomContent();
                    String act_title= ""; //防止空指针
                    if(activityShow.getAct_title()!=null && !"".equals(activityShow.getAct_title()))
                        act_title = activityShow.getAct_title();
                    customContent.setStringValue("share_type","activity");
                    customContent.setStringValue("act_title",act_title.length()>60?act_title.substring(0,60):act_title);
                    customContent.setStringValue("act_id",String.valueOf(activityShow.getAct_id()));
                    customContent.setStringValue("act_pubman_id",String.valueOf(activityShow.getAct_pubman()));
                    customContent.setStringValue("act_pubman_nick_name",activityShow.getAct_pubman_nickname());
                    customContent.setStringValue("act_island",String.valueOf(activityShow.getAct_island()));
                    customContent.setStringValue("act_island_name",activityShow.getAct_island_name());
                    customContent.setStringValue("act_island_logo",activityShow.getAct_island_logo());
                    customContent.setStringValue("act_uni_id",String.valueOf(activityShow.getAct_island_university()));
                    customContent.setStringValue("act_uni_name",activityShow.getAct_university_name());
                    //customContent.setStringValue("workShow",GsonUtils.toJson(workShow)); //防止消息体过大 4k限制
                    if(conversation.getType()== ConversationType.single){ //单聊
                        Message customMsg = conversation.createSendMessage(customContent);
                        AppContext.forwardMsg.clear();
                        AppContext.forwardMsg.add(customMsg);
                    }else{ //群聊
                        Message customMsg = conversation.createSendMessage(customContent);
                        AppContext.forwardMsg.clear();
                        AppContext.forwardMsg.add(customMsg);
                    }
                    DialogCreator.createForwardMsg(ForwardMsgActivity.this, mWidth, true, conversation, null, null, null);
                }else {
                    final Object itemAtPosition = parent.getItemAtPosition(position);
                    //发送名片
                    final Intent intent = getIntent();
                    final Conversation conversation = (Conversation) itemAtPosition;
                    if (intent.getFlags() == 1) { //发送名片
                        String toName = conversation.getTitle();
                        setBusinessCard(toName, intent, conversation);
                        //转发消息
                    } else {
                        DialogCreator.createForwardMsg(ForwardMsgActivity.this, mWidth, true, conversation, null, null, null);
                    }
                }
            }
        });
    }

    private void setExtraIntent(Intent intent) {
        //发送名片,ForwardMsgActivity跳转过来
        if (getIntent().getFlags() == 1) {
            intent.setFlags(2);
            intent.putExtra("userName", getIntent().getStringExtra("userName"));
            intent.putExtra("appKey", getIntent().getStringExtra("appKey"));
            intent.putExtra("avatar", getIntent().getStringExtra("avatar"));
        } else {
            //转发消息,启动群组界面,设置flag标识为1
            intent.setFlags(1);
            if(useType!=null && "share".equals(useType)) {
                intent.putExtra("useType", String.valueOf(useType));
                intent.putExtra("workShow", workShow);
            }
            if(useType!=null && "shareActivity".equals(useType)){
                intent.putExtra("useType",String.valueOf(useType));
                intent.putExtra("activityShow",activityShow);
            }
        }
        startActivity(intent);
    }

    private void setBusinessCard(String name, final Intent intent, final Conversation conversation) {
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_cancel:
                        mDialog.dismiss();
                        break;
                    case R.id.btn_sure:
                        mDialog.dismiss();
                        //把名片的userName和appKey通过extra发送给对方
                        TextContent content = new TextContent("推荐了一张名片");
                        content.setStringExtra("userName", intent.getStringExtra("userName"));
                        content.setStringExtra("appKey", intent.getStringExtra("appKey"));
                        content.setStringExtra("businessCard", "businessCard");

                        Message textMessage = conversation.createSendMessage(content);
                        MessageSendingOptions options = new MessageSendingOptions();
                        options.setNeedReadReceipt(true);
                        JMessageClient.sendMessage(textMessage, options);
                        textMessage.setOnSendCompleteCallback(new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i == 0) {
                                    Toast.makeText(ForwardMsgActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    HandleResponseCode.onHandle(ForwardMsgActivity.this, i, false);
                                }
                            }
                        });
                        break;
                }
            }
        };
        mDialog = DialogCreator.createBusinessCardDialog(ForwardMsgActivity.this, listener, name,
                intent.getStringExtra("userName"), intent.getStringExtra("avatar"));
        mDialog.getWindow().setLayout((int) (0.8 * mWidth), WindowManager.LayoutParams.WRAP_CONTENT);
        mDialog.show();
    }
}
