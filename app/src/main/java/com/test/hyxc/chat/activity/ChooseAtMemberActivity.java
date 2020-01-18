package com.test.hyxc.chat.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.api.model.UserInfo;
import tools.AppContext;

import com.test.hyxc.R;
import com.test.hyxc.chat.adapter.AtMemberAdapter;
import com.test.hyxc.chat.model.ParentLinkedHolder;
import com.test.hyxc.chat.utils.keyboard.widget.EmoticonsEditText;
import com.test.hyxc.chat.utils.pinyin.UserComparator;
import com.test.hyxc.chat.utils.sidebar.SideBar;
import com.test.hyxc.chat.view.listview.StickyListHeadersListView;


/**
 * @ 功能, 选择群组中成员
 */

public class ChooseAtMemberActivity extends BaseActivity {

    private List<UserInfo> mList;
    private SideBar mSideBar;
    private TextView mLetterHintTv;
    private AtMemberAdapter mAdapter;
    private StickyListHeadersListView mListView;
    private LinearLayout mLl_groupAll;
    private LinearLayout mSearch_title;
    private static ParentLinkedHolder<EmoticonsEditText> textParentLinkedHolder;
    private List<UserInfo> forDel = new ArrayList<>();

    public static void show(ChatActivity textWatcher, EmoticonsEditText editText, String targetId) {
        synchronized (ChooseAtMemberActivity.class) {
            ParentLinkedHolder<EmoticonsEditText> holder = new ParentLinkedHolder<>(editText);
            textParentLinkedHolder = holder.addParent(textParentLinkedHolder);
        }

        Intent intent = new Intent(textWatcher, ChooseAtMemberActivity.class);
        intent.putExtra(AppContext.GROUP_ID, Long.parseLong(targetId));
        textWatcher.startActivityForResult(intent, AppContext
                .REQUEST_CODE_AT_MEMBER);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_at_member);
        mListView = (StickyListHeadersListView) findViewById(R.id.at_member_list_view);
        mLl_groupAll = (LinearLayout) findViewById(R.id.ll_groupAll);
        mSearch_title = (LinearLayout) findViewById(R.id.search_title);
        mSideBar = (SideBar) findViewById(R.id.sidebar);
        mLetterHintTv = (TextView) findViewById(R.id.letter_hint_tv);
        mSideBar.setTextView(mLetterHintTv);

        initTitle(true, true, "选择成员", "", false, "");

        long groupId = getIntent().getLongExtra(AppContext.GROUP_ID, 0);
        if (0 != groupId) {
            Conversation conv = JMessageClient.getGroupConversation(groupId);
            GroupInfo groupInfo = (GroupInfo) conv.getTargetInfo();
            mList = groupInfo.getGroupMembers();
            for (UserInfo info : mList) {
                if (info.getUserName().equals(JMessageClient.getMyInfo().getUserName())) {
                    forDel.clear();
                    forDel.add(info);
                }
            }
            mList.removeAll(forDel);
            Collections.sort(mList, new UserComparator());

            mAdapter = new AtMemberAdapter(this, mList);
            mListView.setAdapter(mAdapter);
        }

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                int position = mAdapter.getSectionForLetter(s);
                if (position != -1 && position < mAdapter.getCount()) {
                    mListView.setSelection(position - 1);
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                UserInfo userInfo = mList.get(position);
                Intent intent = new Intent();
                String atName = userInfo.getDisplayName();

                synchronized (ChooseAtMemberActivity.class) {
                    if (textParentLinkedHolder != null) {
                        EmoticonsEditText editText = textParentLinkedHolder.item;
                        if (editText != null)
                            intent.putExtra(AppContext.NAME, atName);
                    }
                }

                intent.putExtra(AppContext.TARGET_ID, userInfo.getUserName());
                intent.putExtra(AppContext.TARGET_APP_KEY, userInfo.getAppKey());
                setResult(AppContext.RESULT_CODE_AT_MEMBER, intent);
                finish();
            }
        });

        mLl_groupAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //@all
                Intent intent = new Intent();
                intent.putExtra(AppContext.ATALL, true);
                setResult(AppContext.RESULT_CODE_AT_ALL, intent);
                finish();
            }
        });

        mSearch_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseAtMemberActivity.this, SearchAtMemberActivity.class);
                AppContext.mSearchAtMember = mList;
                startActivityForResult(intent, AppContext.SEARCH_AT_MEMBER_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 33 && data != null) {
            Intent intent = new Intent();
            intent.putExtra(AppContext.NAME, data.getStringExtra(AppContext.SEARCH_AT_MEMBER_NAME));
            intent.putExtra(AppContext.TARGET_ID, data.getStringExtra(AppContext.SEARCH_AT_MEMBER_USERNAME));
            intent.putExtra(AppContext.TARGET_APP_KEY, data.getStringExtra(AppContext.SEARCH_AT_APPKEY));
            setResult(AppContext.RESULT_CODE_AT_MEMBER, intent);
            finish();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        synchronized (ChooseAtMemberActivity.class) {
            if (textParentLinkedHolder != null) {
                textParentLinkedHolder = textParentLinkedHolder.putParent();
            }
        }
    }


}
