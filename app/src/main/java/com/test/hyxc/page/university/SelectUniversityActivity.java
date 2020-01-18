package com.test.hyxc.page.university;

import android.content.Intent;
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
import com.test.hyxc.model.University;

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

public class SelectUniversityActivity extends BaseActivity {
    List<University> listdata;
    int position=0;
    BaseAdapter adapter;
    ListView listView;
    TextView tv_item;
    String city_name;
    int province_id;
    //选取的地区
    String selectUniversity="noUniversity"; //默认
    int selectUniversityId=-1;
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
        province_id=getIntent().getExtras().getInt("province_id");
        city_name= getIntent().getExtras().getString("city_name");
        listdata=new ArrayList<>();
        listView=f(R.id.listview);
        initAdapter();
        requireUniversity(province_id, city_name);
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
                LayoutInflater inflater = SelectUniversityActivity.this.getLayoutInflater();
                View view;
                if (convertView==null) {
                    //因为getView()返回的对象，adapter会自动赋给ListView
                    view = inflater.inflate(R.layout.imap_select_item, null);
                }else{
                    view=convertView;
                }
                view.setTag(position);
                tv_item = (TextView) view.findViewById(R.id.tv_item);//找到Textviewname
                tv_item.setText(listdata.get(position).getUni_name());//设置参数
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       int p=(int) v.getTag();
                       selectUniversityId=listdata.get(p).getUni_id();
                       selectUniversity=listdata.get(p).getUni_name();
                       returnOk(selectUniversityId,selectUniversity);
                    }
                });
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
    public void  requireUniversity(int province_id,String city_name) {
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("city_name",city_name);
            map.put("province_id",String.valueOf(province_id));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_UNIVERSITY + "/findUniByProIdCityName", token, map, new NetUtils.MyNetCall() {
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
                        if(json.get("listUniversity")==null||((JSONArray)json.get("listUniversity")).length()==0||json.get("listUniversity").toString().equals("[]")||"".equals(json.get("listUniversity").toString())){
                            //这时候需要直接返回给 city页面
                           returnOk(selectUniversityId,selectUniversity);
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                  try {
                                      Gson gson=new Gson();
                                      JSONArray jsonArray=(JSONArray) json.get("listUniversity");
                                      for (int i=0; i < jsonArray.length(); i++) {
                                          University uni=gson.fromJson(jsonArray.get(i).toString(), University.class);
                                          listdata.add(uni);
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
    public  void returnOk( int uni_id,String uni_name ){
        Intent intent = new Intent();
        intent.putExtra("selectUniversityId",uni_id);
        intent.putExtra("selectUniversity",selectUniversity);
        setResult(RESULT_OK, intent);
        finish();
    }
}
