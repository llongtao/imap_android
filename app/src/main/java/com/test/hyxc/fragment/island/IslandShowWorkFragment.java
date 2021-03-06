package com.test.hyxc.fragment.island;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.test.hyxc.adapter.NineGridTestIslandAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.island.IslandShowActivity;
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

public class IslandShowWorkFragment extends Fragment {
    private NineGridTestIslandAdapter mAdapter;
    private List<WorkShow> mList=new ArrayList<>();
    boolean work_end=false;
    boolean addEndWarn=false;
    private PopupWindow mMorePopupWindow;
    private int mShowMorePopupWindowWidth;
    private int mShowMorePopupWindowHeight;
    int user_id=-1;
    int page_Size=10;
    int page_Num=1;
    Integer is_id;
    int relation=-1; //-1 or 0：无关系 1：普通居民 2：管理员 3：岛主
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_list_view_example, container, false);
        user_id=Integer.parseInt(((AppContext)getActivity().getApplicationContext()).getUserId());
        is_id=((IslandShowActivity)getActivity()).is_id;
        relation = ((IslandShowActivity)getActivity()).relation;
        try {
            initMyWork();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView mListView = view.findViewById(R.id.lv_bbs);
        mListView.setDividerHeight(1);
        mAdapter = new NineGridTestIslandAdapter(getActivity(),"islandWork",relation);
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
                                initMyWork();
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

    private void initMyWork() throws Exception {
        if (!work_end) {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getActivity().getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("work_island", String.valueOf(is_id));
            map.put("orderby", "time");
            map.put("page_First", String.valueOf(page_Num));
            map.put("page_Size", String.valueOf(page_Size));
            map.put("excludeMe",String.valueOf(2));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/findWorkDetailByConditionDescPage", token, map, new NetUtils.MyNetCall() {
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
                    JSONArray listworkshowdetail=json.getJSONArray("listworkshowdetail");
                    JsonParser parser=new JsonParser();
                    final JsonArray Jarray=parser.parse(listworkshowdetail.toString()).getAsJsonArray();
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
            if(!addEndWarn) {
                mList.get(mList.size() - 1).setEnd(1);
                mAdapter.notifyDataSetChanged();
                addEndWarn=true;
            }
        }
    }
    /**item＋item里的控件点击监听事件 */
    private NineGridTestIslandAdapter.OnItemClickListener MyItemClickListener = new NineGridTestIslandAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, NineGridTestIslandAdapter.ViewName viewName, int position) {
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
    /***
     * @param moreBtnView
     */
    private void showMore(View moreBtnView,int position) {
        WorkShow workShow=mList.get(position);
        if (mMorePopupWindow == null) {
            LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View content = li.inflate(R.layout.test_more_island_work, null, false);
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
        if(mMorePopupWindow!=null && mMorePopupWindow.isShowing()){
            mMorePopupWindow.dismiss();
        }
        AlertDialog normalDialog = new AlertDialog.Builder(getActivity())
                .setIcon(R.drawable.warn)
                .setTitle("移出海岛不可恢复哦~")
                //.setMessage("test")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        tryMoveWorkFromIsland(position);
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

    public void tryMoveWorkFromIsland(int position) {
        NetUtils netUtils = NetUtils.getInstance();
        String token = ((AppContext) getActivity().getApplicationContext()).getToken();
        Map map = new HashMap();
        map.put("work_id", String.valueOf(mList.get(position).getWork_id()));
        map.put("work_island","-1"); //-1 代表广场
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/changeWorkByCondition", token, map, new NetUtils.MyNetCall() {
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
                    String updateState = json.getString("updateState");
                    if(updateState.equals("success")){
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
