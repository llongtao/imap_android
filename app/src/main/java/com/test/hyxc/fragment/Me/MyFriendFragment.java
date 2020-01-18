package com.test.hyxc.fragment.Me;

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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.adapter.MyFollowAdapter;
import com.test.hyxc.adapter.MyFriendAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.FriendDetail;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.MyFollow;
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

public class MyFriendFragment extends Fragment {
    int user_id=-1;
    private List<FriendDetail> listFriendDetail=new ArrayList<>();
    private MyFriendAdapter adapter;
    ListView lv_follow_list;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.imap_my_follow_list, container, false);
        user_id=((MeActivity)getActivity()).user_id;
        adapter=new MyFriendAdapter(getActivity().getApplicationContext(),listFriendDetail);
        lv_follow_list=view.findViewById(R.id.lv_follow_list);
        lv_follow_list.setAdapter(adapter);
        lv_follow_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //adapter.notifyDataSetChanged();
            }
        });
        lv_follow_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //判断滑动到底部
                   /* if(view.getLastVisiblePosition()==view.getCount()-1){

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }).start();
                    }*/
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
        try {
            getMyFriend();
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    private void getMyFriend() throws Exception {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getActivity().getApplicationContext()).getToken();
            Map map=new HashMap();
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_FRIEND_BASE + "/findMyFriend", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        return;
                    }
                    final Gson gson=new Gson();
                    JSONArray myFriends=json.getJSONArray("listf");
                    JsonParser parser=new JsonParser();
                    final JsonArray Jarray=parser.parse(myFriends.toString()).getAsJsonArray();
                    if (Jarray!= null&&!Jarray.equals("[]")&&Jarray.size()>0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (JsonElement obj : Jarray) {
                                    FriendDetail friendDetail =gson.fromJson(obj,FriendDetail.class);
                                    listFriendDetail.add(friendDetail);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                }
                @Override
                public void failed(Call call, IOException e) {
                }
            });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        listFriendDetail.clear();
    }
}
