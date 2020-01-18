package com.test.hyxc.fragment.island;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.adapter.ActivityShowAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.ActivityShow;
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

public class IslandShowActFragment extends Fragment {
    private ActivityShowAdapter mAdapter;
    private List<ActivityShow> mList=new ArrayList<>();
    boolean work_end=false;
    boolean addEndWarn=false;
    int user_id=-1;
    int page_Size=10;
    int page_Num=1;
    Integer is_id;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_list_view_example, container, false);
        user_id=Integer.parseInt(((AppContext)getActivity().getApplicationContext()).getUserId());
        is_id=((IslandShowActivity)getActivity()).is_id;
        try {
            initMyActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ListView mListView = view.findViewById(R.id.lv_bbs);
        mListView.setDividerHeight(1);
        mAdapter = new ActivityShowAdapter(getActivity());
        mAdapter.setList(mList);
        mListView.setAdapter(mAdapter);
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
                switch (i) {
                    case SCROLL_STATE_IDLE:
                        if( isListViewReachBottomEdge(absListView)){
                            try {
                                initMyActivity();
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

    private void initMyActivity() throws Exception {
        if (!work_end) {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getActivity().getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("act_island", String.valueOf(is_id));
            map.put("orderby", "time");
            map.put("page_First", String.valueOf(page_Num));
            map.put("page_Size", String.valueOf(page_Size));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_BASE + "/findActByConditionDescPage", token, map, new NetUtils.MyNetCall() {
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
                    JSONArray listact=json.getJSONArray("listact");
                    JsonParser parser=new JsonParser();
                    final JsonArray Jarray=parser.parse(listact.toString()).getAsJsonArray();
                    if (Jarray != null&&!Jarray.equals("[]")&&Jarray.size()>0) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (JsonElement obj : Jarray) {
                                    ActivityShow activityShow=gson.fromJson(obj, ActivityShow.class);
                                    mList.add(activityShow);
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
}
