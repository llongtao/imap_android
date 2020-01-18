package com.test.hyxc.fragment.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.adapter.MyFriendAdapter;
import com.test.hyxc.adapter.UserInfoAdapter;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.FriendDetail;
import com.test.hyxc.model.UserInfo;
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

public class S_UserFragment extends Fragment {
    private List<UserInfo> listUserInfo=new ArrayList<>();
    private UserInfoAdapter adapter;
    ListView lv_list;
    String searchText="";
    boolean end = false;
    int pageNum =1;
    int pageSize =20;
    TextView tv_nothing;
    public void setSearchText(String searchText){this.searchText =searchText;}
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.imap_user_info_list, container, false);
        adapter=new UserInfoAdapter(getActivity().getApplicationContext(),listUserInfo);
        lv_list=view.findViewById(R.id.lv_list);
        tv_nothing =view.findViewById(R.id.tv_nothing);
        lv_list.setAdapter(adapter);
        lv_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });
        lv_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //判断滑动到底部
                   /* if(view.getLastVisiblePosition()==view.getCount()-1){
                    }*/
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        try {
            getData();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private void getData() throws Exception {
        if(!end && searchText!=null && !"".equals(searchText)) {
            NetUtils netUtils = NetUtils.getInstance();
            String token = ((AppContext) getActivity().getApplicationContext()).getToken();
            Map map = new HashMap();
            map.put("keyword", searchText);
            map.put("page_First", String.valueOf(pageNum));
            map.put("page_Size", String.valueOf(pageSize));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_SEARCH_BASE + "/searchUser", token, map, new NetUtils.MyNetCall() {
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
                    JSONArray userInfos = json.getJSONArray("list");
                    JsonParser parser = new JsonParser();
                    final JsonArray Jarray = parser.parse(userInfos.toString()).getAsJsonArray();
                    if (Jarray != null && !Jarray.equals("[]") && Jarray.size() > 0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (JsonElement obj : Jarray) {
                                    UserInfo userInfo = gson.fromJson(obj, UserInfo.class);
                                    listUserInfo.add(userInfo);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }else{
                        end = true;
                        if(pageNum ==1) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv_nothing.setVisibility(View.VISIBLE);
                                    lv_list.setVisibility(View.GONE);
                                }
                            });
                        }else{
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.shortToast(getActivity(),"没有更多海岛信息了~");
                                }
                            });
                        }
                    }
                    pageNum++;
                }

                @Override
                public void failed(Call call, IOException e) {
                }
            });
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listUserInfo.clear();
    }
}
