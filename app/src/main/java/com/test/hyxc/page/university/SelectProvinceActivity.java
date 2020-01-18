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

public class SelectProvinceActivity extends BaseActivity {
    List<Province> listdata;
    int position=0;
    BaseAdapter adapter;
    private static final int REQUEST_PRO_CITY_UNIVERSITY = 120;
    ListView listView;
    TextView tv_item;
    String province;
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
        listdata=new ArrayList<>();
        listView=f(R.id.listview);
        initAdapter();
        requireProvince();
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
                LayoutInflater inflater = SelectProvinceActivity.this.getLayoutInflater();
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
                tv_item.setText(listdata.get(position).getProvince_name());//设置参数
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       int p=(int) v.getTag();
                       int pro_id= listdata.get(p).getProvince_id();
                       province=listdata.get(p).getProvince_name();
                       findCity(pro_id);
                    }
                });
                return view;
            }
        };
        listView.setAdapter(adapter);
    }
    public void  requireProvince() {
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getApplicationContext()).getToken();
            Map map=new HashMap();
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_PRO_CITY_AREA + "/findProvinceList", token, map, new NetUtils.MyNetCall() {
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
                                //将评论插入
                                try {
                                    Gson gson=new Gson();
                                    JSONArray jsonArray=(JSONArray) json.get("list");
                                    for (int i=0; i < jsonArray.length(); i++) {
                                        Province p=gson.fromJson(jsonArray.get(i).toString(), Province.class);
                                        listdata.add(p);
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
    public void findCity(int province_id){
        Intent intent = new Intent();
        intent.putExtra("province_id",province_id);
        intent.setClass(this,SelectCityActivity.class);
        startActivityForResult(intent, REQUEST_PRO_CITY_UNIVERSITY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_PRO_CITY_UNIVERSITY:
                if (resultCode == RESULT_OK) {
                    String city=intent.getStringExtra("city");
                    int selectUniversityId=intent.getIntExtra("selectUniversityId",0);
                    String selectUniversity=intent.getStringExtra("selectUniversity");
                    returnOk(province,city,selectUniversityId,selectUniversity);
                }
                break;
        }
    }
    //得到地区开始返回 result
    public  void returnOk(String province,String city, int  selectUniversityId,String selectUniversity){
        Intent intent = new Intent();
        intent.putExtra("province",province);
        intent.putExtra("city",city);
        intent.putExtra("selectUniversityId",selectUniversityId);
        intent.putExtra("selectUniversity",selectUniversity);
        setResult(RESULT_OK, intent);
        finish();
    }
}
