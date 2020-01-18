package com.test.hyxc.page.island;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.page.activity.ChangeActActivity;
import com.test.hyxc.page.location.SelectProvinceActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.CreateGroupCallback;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.BaiduMap.service.LocationService;
import tools.GlobalConfig;
import tools.LoadingDialog;
import tools.NetUtils;

/**
 * Created by Mac on 2019/5/30.
 */

public class IslandChangeActivity extends BaseActivity {
    LinearLayout container;
    ImageView iv_back;
    TextView tv_title;
    TextView tv_category,tv_category_text;
    TextView tv_island_name;
    EditText et_island_name;
    EditText et_desc;
    TextView tv_location_text;
    public LoadingDialog loadingDialog;
    TextView tv_uni_text;
    //选择地区
    private static final int REQUEST_PRO_CITY_REGION = 110;
    private static final int REQUEST_PRO_CITY_UNIVERSITY = 120;
    private final static String imgEndpoint = "yourImgEndpoint";
    //
    Integer firstId=-1;
    String  firstName="";
    Integer secondId=-1;
    String  secondName="";
    //获取的数据
    Island islandMessage;
    String  islandName;
    String  islandDesc;
    String  provinceName="";
    String  cityName="";
    String  areaName="";
    Integer cityId;
    Integer areaId;
    Integer uniId=0;
    String  uniName;
    double longitude=0.0;
    double latitude =0.0;
    public LocationService locationService;
    public  Boolean getLocation=false;
    Button btn_save;
    //简介个数限制
    public int limit=350;
    String url="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_island_change;
    }
    @Override
    protected void initListener() {}
    @Override
    protected void initView() throws Exception {
        url=((AppContext)getApplicationContext()).getOssConfig().getHost()+"/";
        findView();
        getLocation();
        islandMessage= (Island) getIntent().getSerializableExtra("island");
        loadingDialog = new LoadingDialog(this);
        try {
            firstId =islandMessage.getIs_category_parent_id();
            secondId =islandMessage.getIs_category_id();
            firstName =islandMessage.getIs_category_parent();
            secondName =islandMessage.getIs_category();
            cityId=islandMessage.getIs_city_id();
            cityName=islandMessage.getIs_city_name();
            areaId=islandMessage.getIs_area_id();
            tv_category_text.setText(firstName + "  " + secondName);
            et_island_name.setText(islandMessage.getIs_name());
            et_desc.setText(islandMessage.getIs_describe());
            makeSureLocation(islandMessage.getIs_city_name(),islandMessage.getIs_area_id());
            if(islandMessage.getIs_parent_name()!=null && islandMessage.getIs_parent_type()!=null && islandMessage.getIs_parent_type()==0){
                //代表大学
                tv_uni_text.setText(islandMessage.getIs_parent_name());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_location_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(IslandChangeActivity.this, SelectProvinceActivity.class);
                startActivityForResult(intent, REQUEST_PRO_CITY_REGION);
            }
        });
        tv_uni_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(IslandChangeActivity.this, com.test.hyxc.page.university.SelectProvinceActivity.class);
                startActivityForResult(intent1, REQUEST_PRO_CITY_UNIVERSITY);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trySaveInformation();
            }
        });

    }

    @Override
    protected void initData() {
    }
    public void findView(){
        container=f(R.id.container);
        iv_back=f(R.id.iv_back);
        tv_title=f(R.id.tv_title);
        tv_category=f(R.id.tv_category);
        tv_category_text=f(R.id.tv_category_text);
        tv_island_name=f(R.id.tv_island_name);
        et_island_name=f(R.id.et_island_name);
        et_desc=f(R.id.et_desc);
        tv_location_text=f(R.id.tv_location_text);
        tv_uni_text=f(R.id.tv_uni_text);
        btn_save=f(R.id.btn_save);
    }
    /* 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改*/
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if(location.getCity()!=null&&location.getDistrict()!=null&&!"".equals(location.getCity())&&!"".equals(location.getDistrict())){
                    longitude=location.getLongitude();
                    latitude =location.getLatitude();
                    //将经纬度保存下来
                    getLocation=true;
                    //获取到就结束service
                    locationService.stop();
                }
            }
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_PRO_CITY_REGION:
                if(resultCode==RESULT_OK){
                    provinceName=intent.getStringExtra("province");
                    cityName=intent.getStringExtra("city");
                    areaName=intent.getStringExtra("region");
                    if(areaName.equalsIgnoreCase("noRegion")){
                        areaName="";
                    }
                    tv_location_text.setText(provinceName+" "+cityName+" "+areaName);
                    tv_location_text.setTextColor(Color.parseColor("#8D8B8B"));
                }
                break;
            case  REQUEST_PRO_CITY_UNIVERSITY://选择大学
                if(resultCode==RESULT_OK){
                    String province=intent.getStringExtra("province");
                    String city=intent.getStringExtra("city");
                    uniId=intent.getIntExtra("selectUniversityId",0);
                    uniName=intent.getStringExtra("selectUniversity");
                    if(uniName.equalsIgnoreCase("noUniversity")){
                        uniId=0;
                        uniName="";
                        tv_uni_text.setText("");
                        Toast.makeText(getApplicationContext(),"所选城市下没有大学！",Toast.LENGTH_LONG).show();
                    }else {
                        tv_uni_text.setText(uniName);
                        tv_uni_text.setTextColor(Color.parseColor("#8D8B8B"));
                    }
                }
                break;
        }
    }

    /**
     * 保存信息
     */
    public void trySaveInformation(){
        //名称
        if(et_island_name.getText()==null||"".equals(et_island_name.getText().toString())){
            Toast.makeText(getApplicationContext(),"海岛名未填写~",Toast.LENGTH_SHORT).show();
            return;
        }else if(et_island_name.getText().toString().length()>10){
            Toast.makeText(getApplicationContext(),"名称最大10个字~",Toast.LENGTH_SHORT).show();
            return;
        }else{
            islandName=et_island_name.getText().toString();
        }
        //简介
        if(et_desc.getText()==null||"".equals(et_desc.getText().toString())){
            Toast.makeText(getApplicationContext(),"简介未填写~",Toast.LENGTH_SHORT).show();
            return;
        }else{
            islandDesc=et_desc.getText().toString();
        }
        //简介是否超过个数
        if(et_desc.getText().toString().length()>this.limit){
            Toast.makeText(getApplicationContext(),"简介不能超过:"+limit,Toast.LENGTH_SHORT).show();
            return;
        }else{
            islandDesc=et_desc.getText().toString();
        }
        if(!checkString(et_island_name.getText().toString())||!checkString(et_desc.getText().toString())||!checkString(tv_location_text.getText().toString())||!checkString(tv_uni_text.getText().toString())){
            Toast.makeText(getApplicationContext(),"所填信息包含非法字符，请修改",Toast.LENGTH_LONG).show();
            return;
        } else {
            IslandChangeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog.show();
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        NetUtils netUtils=NetUtils.getInstance();
                        String token=((AppContext) getApplicationContext()).getToken();
                        Map map=new HashMap();
                        map.put("is_id",""+islandMessage.getIs_id());
                        map.put("is_name", islandName);
                        map.put("is_describe", String.valueOf(islandDesc));
                        map.put("is_city_id", String.valueOf(cityId));
                        if(areaName!=null && !"".equals(areaName)){ //如果是重新选择了地区 后台要改变 update
                            map.put("is_city_name", cityName);
                            map.put("is_area_name",areaName);
                        }/*else {
                            map.put("is_city_id", String.valueOf(cityId));
                            map.put("is_city_name", cityName);
                            map.put("is_area_id", String.valueOf(areaId));
                        }*/
                        if(uniName!=null&&!"".equals(uniName)){
                            map.put("is_parent_id",String.valueOf(uniId));
                            map.put("is_parent_name",uniName);
                            map.put("is_parent_type",String.valueOf(0));
                        }
                        map.put("is_longitude",String.valueOf(longitude));
                        map.put("is_latitude",String.valueOf(latitude));
                        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/changIsByCondition", token, map, new NetUtils.MyNetCall() {
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
                                                if (json.has("changIsByCondition") && json.get("changIsByCondition").equals("success")) {
                                                    IslandChangeActivity.this.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            ToastUtil.shortToast(IslandChangeActivity.this,"修改海岛信息成功~");
                                                            finish();
                                                        }
                                                    });
                                                } else {
                                                    //loading对话框关闭
                                                    IslandChangeActivity.this.runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            loadingDialog.dismiss();
                                                            Toast.makeText(getApplicationContext(), "修改海岛信息异常~", Toast.LENGTH_LONG).show();
                                                        }
                                                    });
                                                }
                                            } catch (Exception e) {
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
            }).start();
        }
    }
    /***检验非法字符*****************/
    public boolean checkString(String s){
        if(s.contains("|")||s.contains("_")||s.contains("!")||s.contains("\\")||s.contains("&")||s.contains("%")||s.contains("?")){
            return false;
        }
        return true;
    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }
    //成功创建海岛信息 修改本地库的信息
    public void updateIslandMessageSuccess(Integer is_id,Long groupId){
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("is_id", String.valueOf(is_id));
            map.put("is_im_id", String.valueOf(groupId));
            map.put("current_status",String.valueOf(0));//改为正常
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/changIsByCondition", token, map, new NetUtils.MyNetCall() {
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
                                //loading对话框关闭
                                IslandChangeActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingDialog.dismiss();
                                    }
                                });
                                try {
                                    if(json.has("changIsByCondition")&&json.get("changIsByCondition").equals("success")){
                                        ToastUtil.shortToast(IslandChangeActivity.this,"恭喜，创建海岛成功~");
                                        finish();
                                    }else{
                                        ToastUtil.shortToast(IslandChangeActivity.this,"创建海岛发生异常~");
                                    }
                                } catch (JSONException e) {
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
    //发生异常废弃掉海岛
    public void updateIslandMessageFail(Integer is_id,Long groupId){
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("is_id", String.valueOf(is_id));
            map.put("is_im_id", String.valueOf(groupId));
            map.put("current_status",String.valueOf(2)); //废弃
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/changIsByCondition", token, map, new NetUtils.MyNetCall() {
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
                        if(json.get("changIsByCondition").equals("fial")){
                            System.out.println("修改海岛废弃信息失败");
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

    static boolean saveBitmap2file(Bitmap bmp,String filename){
        Bitmap.CompressFormat format= Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream("/sdcard/"+GlobalConfig.packageName+"/temp/" + filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bmp.compress(format, quality, stream);
    }
    public void getLocation(){
        locationService = ((AppContext)getApplicationContext()).locationService;
        locationService.registerListener(mListener);
        int type=0;
        if (type == 0) {
            locationService.setLocationOption(locationService.getDefaultLocationClientOption());
        } else if (type == 1) {
            locationService.setLocationOption(locationService.getOption());
        }
        if(!getLocation){
            locationService.start();
        }
    }

    public void makeSureLocation(String cityName,int area_id){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("cityname", cityName);
        map.put("area_id",String.valueOf(area_id));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_PRO_CITY_AREA + "/findProByCityname", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(IslandChangeActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    final Gson gson=new Gson();
                    if(json!=null) {
                        if(json.has("province_id") && json.has("province_name")) {
                           // Integer province_id = json.getInt("province_id");
                            provinceName = json.getString("province_name");
                        }
                        if(json.has("area_id") && json.has("area_name")){
                           areaName = json.getString("area_name");
                            areaId= json.getInt("area_id");
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_location_text.setText(provinceName + " " + cityName + " " + areaName);
                            }
                        });
                    }
                }
                @Override
                public void failed(Call call, IOException e) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
