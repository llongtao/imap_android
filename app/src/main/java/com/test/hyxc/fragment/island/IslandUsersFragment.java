package com.test.hyxc.fragment.island;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import com.test.hyxc.adapter.IslandShowAdapter;
import com.test.hyxc.adapter.IslandUsersAdapter;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.IslandResidentUserInfo;
import com.test.hyxc.page.island.IslandShowActivity;
import com.test.hyxc.page.island.IslandUsersActivity;

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

import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;

public class IslandUsersFragment extends Fragment {
    String url="";
    private List<IslandResidentUserInfo> userInfoList=new ArrayList<>();
    ListView lv_island_users;
    private IslandUsersAdapter adapter;
    Island island;
    public int relation=-1; //-1 or 0：无关系 1：普通居民 2：管理员 3：岛主
    int page_First=1;
    int page_Size=20;
    private PopupWindow mMorePopupWindow;
    private int mShowMorePopupWindowWidth;
    private int mShowMorePopupWindowHeight;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imap_island_users_fragment, container, false);
        url=((AppContext)getActivity().getApplicationContext()).getOssConfig().getHost()+"/";
        initView(view);
        return view;
    }
    public void initView(View view){
        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        if(bundle!=null){
            island = (Island) bundle.get("island");
            relation = bundle.getInt("relation");
        }
        lv_island_users = view.findViewById(R.id.lv_island_users);
        adapter = new IslandUsersAdapter(getActivity(),userInfoList);
        adapter.setOnItemClickListener(MyItemClickListener);
        lv_island_users.setAdapter(adapter);
        lv_island_users.setDividerHeight(1);
        lv_island_users.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //判断滑动到底部
                    if(view.getLastVisiblePosition()==view.getCount()-1){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    findUsersByIsId(island.getIs_id());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        try {
            findUsersByIsId(island.getIs_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**item＋item里的控件点击监听事件 */
    private IslandUsersAdapter.OnItemClickListener MyItemClickListener = new IslandUsersAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, IslandUsersAdapter.ViewName viewName, int position) {
            switch (v.getId()){
                case R.id.iv_more:
                    if(relation == -1 || relation == 0 || relation == 1){
                        //什么也不做
                    }else {
                        showMore(v, position);
                    }
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onItemLongClick(View v) {}
    };
    private void showMore(View moreBtnView,int position) {
        IslandResidentUserInfo ir = userInfoList.get(position);
        int privilege = ir.getRes_privilege();
        int resource = R.layout.test_more_island_users_1; //移除海岛 一个按钮
        if(relation == 2){ //只能对 普通用户操作
           if(privilege <=1){
               resource =R.layout.test_more_island_users_1;
               getPop(moreBtnView,resource,position);
           }
        }else if(relation ==3){
           if(privilege ==2) { //对管理员
               resource = R.layout.test_more_island_users_2; //移除 + 取消管理员两个按钮
               getPop(moreBtnView,resource,position);
           }else if(privilege <=1){ //对普通用户
              resource = R.layout.test_more_island_users; //移除 + 设为管理员两个按钮
              getPop(moreBtnView,resource,position);
           }
        }
    }

    void getPop(View moreBtnView,int resource,int position){
        View parent = null;
        if (mMorePopupWindow == null) {
            LayoutInflater li = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View content = li.inflate(resource, null, false);
            mMorePopupWindow = new PopupWindow(content, ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            mMorePopupWindow.setBackgroundDrawable(new BitmapDrawable());
            mMorePopupWindow.setOutsideTouchable(true);
            mMorePopupWindow.setTouchable(true);
            content.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            mShowMorePopupWindowWidth = content.getMeasuredWidth();
            mShowMorePopupWindowHeight = content.getMeasuredHeight();
             parent = mMorePopupWindow.getContentView();
        }else {
             parent = mMorePopupWindow.getContentView();
        }
            TextView tv_delete = (TextView) parent.findViewById(R.id.tv_delete);
            //移出海岛
            LinearLayout ll_tv_delete = (LinearLayout) parent.findViewById(R.id.ll_tv_delete);
            ll_tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        removeFromIsland(island.getIs_id(), userInfoList.get(position).getUser_id(),position);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            if(relation ==3 && userInfoList.get(position).getRes_privilege()==1) {
                //设为管理员
                LinearLayout ll_tv_set_manager = (LinearLayout) parent.findViewById(R.id.ll_tv_set_manager);
                ll_tv_set_manager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            setManager(island.getIs_id(),userInfoList.get(position).getUser_id(),position,2);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            if(relation ==3 && userInfoList.get(position).getRes_privilege()==2) {
                // 取消管理员
                LinearLayout ll_tv_cancel_manager = (LinearLayout) parent.findViewById(R.id.ll_tv_cancel_manager);
                ll_tv_cancel_manager.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            setManager(island.getIs_id(), userInfoList.get(position).getUser_id(), position, 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
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
    //移出海岛
    public void removeFromIsland(int is_id,int user_id,int position) throws  Exception{
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        map.put("user_id",String.valueOf(user_id));
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/removeUser",token, map, new NetUtils.MyNetCall() {
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
                if (json.has("removeUser") && json.getString("removeUser").equals("success")) {
//                    if(String.valueOf(userInfoList.get(position).getUser_id()) == ((AppContext)getActivity().getApplicationContext()).getUserId()){reSetUserIsland(); //如果是移除自己}
                    userInfoList.remove(position);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            if (mMorePopupWindow != null && mMorePopupWindow.isShowing()) {
                                mMorePopupWindow.dismiss();
                            }

                        }
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.shortToast(getActivity(),"抱歉，后台发生异常~");
                            if (mMorePopupWindow != null && mMorePopupWindow.isShowing()) {
                                mMorePopupWindow.dismiss();
                            }
                        }
                    });
                    getActivity().finish();
                }
            }
            @Override
            public void failed(Call call, IOException e) {}
        });
    }
    //设为管理员 和 取消管理员
    void setManager(int is_id,int user_id,int position,int privilege) throws  Exception{
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        map.put("user_id",String.valueOf(user_id));
        map.put("res_privilege",String.valueOf(privilege));
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/updateIslandResident",token, map, new NetUtils.MyNetCall() {
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
                if (json.has("updateIslandResident") && json.getString("updateIslandResident").equals("success")) {
                    IslandResidentUserInfo ir = userInfoList.get(position);
                    ir.setRes_privilege(privilege);
                    userInfoList.remove(position);
                    userInfoList.add(position,ir);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            ToastUtil.shortToast(getActivity(),"修改成功！");
                            if (mMorePopupWindow != null && mMorePopupWindow.isShowing()) {
                                mMorePopupWindow.dismiss();
                            }
                            getActivity().finish();
                        }
                    });
                }else{
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.shortToast(getActivity(),"抱歉，后台发生异常~");
                            if (mMorePopupWindow != null && mMorePopupWindow.isShowing()) {
                                mMorePopupWindow.dismiss();
                            }
                        }
                    });
                    getActivity().finish();
                }
            }

            @Override
            public void failed(Call call, IOException e) {}
        });
    }

    //根据海岛id 查找用户信息
    public void findUsersByIsId(int is_id) throws Exception {
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        map.put("page_First",String.valueOf(page_First));
        map.put("page_Size",String.valueOf(page_Size));
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_RESIDENT_BASE + "/findIRByIsId",token, map, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException,JSONException {
                String ret=response.body().string();
                JSONObject json = new JSONObject(ret);
                //如果token过期,就重新登陆
                if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    getActivity().finish();
                    return;
                }
                final Gson gson = new Gson();
                JSONArray listIR=json.getJSONArray("listIR");
                JsonParser parser = new JsonParser();
                final JsonArray Jarray = parser.parse(listIR.toString()).getAsJsonArray();
                if(Jarray!=null&&Jarray.size()>0) {
                    page_First++;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (JsonElement obj : Jarray) {
                                IslandResidentUserInfo  irui = gson.fromJson(obj, IslandResidentUserInfo.class);
                                if(relation == -1 || relation == 0 || relation == 1){
                                    irui.setShowMore( false);
                                }
                                userInfoList.add(irui);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
            @Override
            public void failed(Call call, IOException e) {}
        });
    }


}
