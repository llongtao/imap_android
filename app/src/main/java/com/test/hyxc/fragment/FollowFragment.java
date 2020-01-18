package com.test.hyxc.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.adapter.NineGridTestAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.personal.MeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.NetUtils;

/**
 * Created by WWW on 2019/1/13.
 */

public class FollowFragment extends Fragment {
    private NineGridTestAdapter mAdapter;
    private List<WorkShow> mList=new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;
    boolean work_end=false;
    private PopupWindow mMorePopupWindow;
    private int mShowMorePopupWindowWidth;
    private int mShowMorePopupWindowHeight;
    boolean addEndWarn=false;
    int user_id=-1;
    int page_Size=10;
    int page_Num=1;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_list_view_follow_works, container, false);
        user_id=Integer.parseInt(((AppContext)getActivity().getApplicationContext()).getUserId());
        try {
            findMyFollowWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView mListView = view.findViewById(R.id.lv_bbs);
        mListView.setDividerHeight(1);
        mAdapter = new NineGridTestAdapter(getActivity(),"myFollow",false);
        mAdapter.setList(mList);
        mAdapter.setOnItemClickListener(MyItemClickListener);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case SCROLL_STATE_IDLE:
                        if( isListViewReachBottomEdge(absListView)){
                            try {
                                findMyFollowWork();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }
            }
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {}
        });
        mSwipeRefreshLayout=view.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED,Color.BLUE,Color.GREEN);
        initPullRefresh();
        return view;
    }
    public boolean isListViewReachBottomEdge(final AbsListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        };
        return result;
    }

    private void findMyFollowWork() throws Exception {
        if (!work_end) {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getActivity().getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("page_First", String.valueOf(page_Num));
            map.put("page_Size", String.valueOf(page_Size));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/findWorkByMyFollow", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    page_Num++;
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        return;
                    }
                    final Gson gson=new Gson();
                    JSONArray listwork=json.getJSONArray("listwork");
                    JsonParser parser=new JsonParser();
                    final JsonArray Jarray=parser.parse(listwork.toString()).getAsJsonArray();
                    if (Jarray != null&&!Jarray.equals("[]")&&Jarray.size()>0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (JsonElement obj : Jarray) {
                                    WorkShow ws=gson.fromJson(obj, WorkShow.class);
                                    mList.add(ws);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        });
                    }else{
                        work_end=true;
                    }
                }
                @Override
                public void failed(Call call, IOException e) {
                }
            });
        }else{
            //如果到底了
            if(!addEndWarn&&mList.size()>0) {
                mList.get(mList.size() - 1).setEnd(1);
                mAdapter.notifyDataSetChanged();
                addEndWarn=true;
            }
        }
    }
    //下拉刷新
    private void initPullRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.clear();
                        work_end=false;
                        page_Num=1;
                        page_Size=10;
                        try {
                            findMyFollowWork();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                }, 200);
            }
        });
    }
    /**item＋item里的控件点击监听事件 */
    private NineGridTestAdapter.OnItemClickListener MyItemClickListener = new NineGridTestAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, NineGridTestAdapter.ViewName viewName, int position) {
            switch (v.getId()){
                case R.id.iv_more:
                    showMore(v,position);
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onItemLongClick(View v) {}
    };

    /**
     * 弹出点赞和评论框
     *
     * @param moreBtnView
     */
    private void showMore(View moreBtnView,int position) {
        WorkShow workShow=mList.get(position);
        if (mMorePopupWindow == null) {
            LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View content = li.inflate(R.layout.test_more, null, false);
            mMorePopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mMorePopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mMorePopupWindow.setOutsideTouchable(true);
            mMorePopupWindow.setTouchable(true);
            content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mShowMorePopupWindowWidth = content.getMeasuredWidth();
            mShowMorePopupWindowHeight = content.getMeasuredHeight();
            View parent = mMorePopupWindow.getContentView();
            TextView tv_delete = (TextView) parent.findViewById(R.id.tv_delete);
            LinearLayout ll_tv_delete = (LinearLayout) parent.findViewById(R.id.ll_tv_delete);
            // 点赞的监听器
            ll_tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDlg(position);
                }
            });
        }else{
            View parent = mMorePopupWindow.getContentView();
            TextView tv_delete = (TextView) parent.findViewById(R.id.tv_delete);
            LinearLayout ll_tv_delete = (LinearLayout) parent.findViewById(R.id.ll_tv_delete);
            // 点赞的监听器
            ll_tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDlg(position);
                }
            });
        }

        if (mMorePopupWindow.isShowing()) {
            mMorePopupWindow.dismiss();
        } else {
            int heightMoreBtnView = moreBtnView.getHeight();

            mMorePopupWindow.showAsDropDown(moreBtnView, -mShowMorePopupWindowWidth - 23,
                    -(mShowMorePopupWindowHeight + heightMoreBtnView) / 2);
        }
    }

    //退出登录前
    public void showDeleteDlg(int position) {
        AlertDialog normalDialog = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.warn)
                .setTitle("确定删除么？")
                //.setMessage("test")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        tryDeleteWork(position);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        // 显示
        normalDialog.show();
        normalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0b090e"));
        normalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(15);
        normalDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#0b090e"));
        normalDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(15);
    }

    public void tryDeleteWork(int position) {
        NetUtils netUtils = NetUtils.getInstance();
        String token = ((AppContext) getActivity().getApplicationContext()).getToken();
        Map map = new HashMap();
        map.put("work_id", String.valueOf(mList.get(position).getWork_id()));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/delWork", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret = response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        return;
                    }
                    final Gson gson = new Gson();
                    String delState = json.getString("delState");
                    if(delState.equals("success")){
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mList.remove(position);
                                mAdapter.notifyDataSetChanged();
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
