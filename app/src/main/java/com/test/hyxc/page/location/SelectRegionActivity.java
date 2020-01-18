package com.test.hyxc.page.location;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.CCRegion;
import com.test.hyxc.model.City;

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

public class SelectRegionActivity extends BaseActivity {
    List<CCRegion> listdata;
    int position=0;
    BaseAdapter adapter;
    ListView listView;
    TextView tv_item;
    String city_name;
    //选取的地区
    String selectRegion="";
    @Override
    protected void initData() { }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_select;
    }
    @Override
    protected void initListener() { }
    @Override
    protected void initView() {
        city_name= getIntent().getExtras().getString("city_name");
        listdata=new ArrayList<>();
        listView=f(R.id.listview);
        initAdapter();
        requireRegion( city_name);
    }
    public void initAdapter(){
        adapter=new BaseAdapter() {
            @Override
            public int getCount() {
                return listdata.size();
            }
            @Override
            public Object getItem(int position) {
                return null;
            }
            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = SelectRegionActivity.this.getLayoutInflater();
                View view;
                if (convertView==null) {
                    //因为getView()返回的对象，adapter会自动赋给ListView
                    view = inflater.inflate(R.layout.imap_select_item, null);
                }else{
                    view=convertView;
                    Log.i("info","有缓存，不需要重新生成"+position);
                }
                view.setTag(position);
                tv_item = (TextView) view.findViewById(R.id.tv_item);//找到Textviewname
                tv_item.setText(listdata.get(position).getName());//设置参数
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       returnOk(listdata.get(position).getName());
                    }
                });
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
    public void  requireRegion(String city_name) {
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("city_name",city_name);
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_CCREGION + "/findRegionByCityName", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                        return;
                    } else {
                         //对于一些城市下面是没有地区划分的 返回 "noRegion"
                        if(json.get("list")==null||((JSONArray)json.get("list")).length()==0||json.get("list").toString().equals("[]")||"".equals(json.get("list").toString())){
                            //这时候需要直接返回给 city页面
                           selectRegion="noRegion";
                           returnOk(selectRegion);
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                  try {
                                      Gson gson=new Gson();
                                      JSONArray jsonArray=(JSONArray) json.get("list");
                                      for (int i=0; i < jsonArray.length(); i++) {
                                          CCRegion region=gson.fromJson(jsonArray.get(i).toString(), CCRegion.class);
                                          listdata.add(region);
                                          position++;
                                      }
                                      adapter.notifyDataSetChanged();
                                  }catch (Exception e){
                                      e.printStackTrace();
                                  }
                                }
                            });
                        }
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
    //得到地区开始返回 result
    public  void returnOk( String region){
        Intent intent = new Intent();
        intent.putExtra("region",region);
        setResult(RESULT_OK, intent);
        finish();
    }
}
