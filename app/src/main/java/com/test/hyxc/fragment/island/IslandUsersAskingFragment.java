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
import com.test.hyxc.adapter.IslandUsersAskingAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.IslandResidentUserInfo;
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

public class IslandUsersAskingFragment extends Fragment {
    String url="";
    private List<IslandResidentUserInfo> userInfoList=new ArrayList<>();
    ListView lv_island_users;
    private IslandUsersAskingAdapter adapter;
    Island island;
    public int relation=-1; //-1 or 0：无关系 1：普通居民 2：管理员 3：岛主
    int page_First=1;
    int page_Size=20;
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
        adapter = new IslandUsersAskingAdapter(getActivity(),userInfoList);
        adapter.setOnItemClickListener(MyItemClickListener);
        lv_island_users = view.findViewById(R.id.lv_island_users);
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
                                    findUsersAskingByIsId(island.getIs_id());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { }
        });
        try {
            findUsersAskingByIsId(island.getIs_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**item＋item里的控件点击监听事件 */
    private IslandUsersAskingAdapter.OnItemClickListener MyItemClickListener = new IslandUsersAskingAdapter.OnItemClickListener() {

        @Override
        public void onItemClick(View v, IslandUsersAskingAdapter.ViewName viewName, int position) {
            switch (v.getId()){
                case R.id.iv_more:
                    break;
                default:
                    break;
            }
        }
        @Override
        public void onItemLongClick(View v) {}
    };


    //根据海岛id 查找申请中的用户信息
    public void findUsersAskingByIsId(int is_id) throws Exception {
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        map.put("page_First",String.valueOf(page_First));
        map.put("page_Size",String.valueOf(page_Size));
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_RESIDENT_BASE + "/findIRAskingByIsId",token, map, new NetUtils.MyNetCall() {
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
