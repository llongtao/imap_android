package com.test.hyxc.page.location;
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
import com.test.hyxc.model.City;
import com.test.hyxc.model.Province;
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
public class SelectCityActivity extends BaseActivity {
    List<City> listdata;
    int position=0;
    BaseAdapter adapter;
    private static final int REQUEST_PRO_CITY_REGION = 110;
    ListView listView;
    TextView tv_item;
    int province_id;
    String city_name;
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
        province_id= getIntent().getExtras().getInt("province_id");
        listdata=new ArrayList<>();
        listView=f(R.id.listview);
        initAdapter();
        requireCity( province_id);
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
                LayoutInflater inflater = SelectCityActivity.this.getLayoutInflater();
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
                tv_item.setText(listdata.get(position).getCity_name());//设置参数
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       int p=(int) v.getTag();
                       int city_id= listdata.get(p).getCity_id();
                        city_name=listdata.get(p).getCity_name();
                       findRegion(city_name);
                    }
                });
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
    public void  requireCity(int province_id) {
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("pro_id",String.valueOf(province_id));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_PRO_CITY_AREA + "/findCityByProId", token, map, new NetUtils.MyNetCall() {
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
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               try {
                                   Gson gson=new Gson();
                                   JSONArray jsonArray=(JSONArray) json.get("listcity");
                                   for (int i=0; i < jsonArray.length(); i++) {
                                       City c=gson.fromJson(jsonArray.get(i).toString(), City.class);
                                       listdata.add(c);
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
                @Override
                public void failed(Call call, IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void findRegion(String city_name){
        Intent intent = new Intent();
        intent.putExtra("city_name",city_name);
        intent.setClass(this, SelectRegionActivity.class);
        startActivityForResult(intent, REQUEST_PRO_CITY_REGION);
    }
    ///返回数据
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_PRO_CITY_REGION:
                if (resultCode == RESULT_OK) {
                     String region=data.getStringExtra("region");
                     String city=city_name;
                     returnOk(city,region);
                }
                break;
        }
    }

    //得到地区开始返回 result
    public  void returnOk(String city, String region){
        Intent intent = new Intent();
        intent.putExtra("city",city);
        intent.putExtra("region",region);
        setResult(RESULT_OK, intent);
        finish();
    }
}
