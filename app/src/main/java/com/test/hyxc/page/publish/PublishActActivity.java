package com.test.hyxc.page.publish;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.ActivityShow;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.location.SelectProvinceActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.LoadingDialog;
import tools.NetUtils;
import tools.OssConfig;

import static com.test.hyxc.ccb.utils.UiUtils.getContext;

public class PublishActActivity  extends BaseActivity{
    //oss
    private OssConfig ossConfig=null;
    private OSS oss;
    private String bucket="yourbucket";
    private final static String imgEndpoint = "yourImgEndpoint";
    String url="";
    //表征上传oss是否异常
    private boolean ossUploadWrong=false;
    //地区选择
    private static final int REQUEST_PRO_CITY_REGION = 110;
    //大学选择
    private static final int REQUEST_PRO_CITY_UNIVERSITY = 120;
    //图片选择
    private static final int PHOTO_REQUEST_GALLERY=130;
    public LoadingDialog loadingDialog;
    int defineIsland;
    String defineIslandName="";
    String defineIslandLogo="";
    int defineIslandCategoryId;
    String defineIslandCategoryName="";
    int defineIslandCategoryParentId;
    String defineIslandCategoryParentName="";
    int defineIslandUniId=-1;
    String defineIslandUniName="";
    ImageView iv_back;
    EditText et_title,et_act_text,et_address;
    TextView tv_start_time2,tv_end_time2,tv_island_name2,tv_location_text,tv_uni_text;
    LinearLayout ll_start_time,ll_end_time,ll_location,ll_uni,ll_cover_show;
    ImageView iv_cover_add,iv_select_cover;
    Button btn_publish;
    //时间选择器
    TimePickerView pvTime ;
    TimePickerView pvTimeEnd;
    Date date1,date2;
    String start_year="",start_month="",start_day="",start_hour="",start_minute="";
    String end_year="",end_month="",end_day="",end_hour="",end_minute="";
    String province="",city="",district="";
    String selectCover="";
    int university_id;
    String university;
    ActivityShow activityShow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_act_publish;
    }
    @Override
    protected void initListener() {}
    @Override
    protected void initView() throws Exception {
        this.ossConfig=((AppContext)getApplicationContext()).getOssConfig();
        this.url=ossConfig.getHost()+"/";
        //组件
        iv_back=f(R.id.iv_back);
        tv_island_name2 =f(R.id.tv_island_name2);
        et_title=f(R.id.et_title);
        et_act_text=f(R.id.et_act_text);
        tv_start_time2=f(R.id.tv_start_time2);
        tv_end_time2=f(R.id.tv_end_time2);
        ll_start_time=f(R.id.ll_start_time);
        ll_end_time=f(R.id.ll_end_time);
        ll_location=f(R.id.ll_location);
        ll_uni=f(R.id.ll_uni);
        tv_location_text=f(R.id.tv_location_text);
        et_address=f(R.id.et_address);
        tv_uni_text=f(R.id.tv_uni_text);
        iv_cover_add=f(R.id.iv_cover_add);
        ll_cover_show=f(R.id.ll_cover_show);
        iv_select_cover=f(R.id.iv_select_cover);
        btn_publish=f(R.id.btn_publish);
        //初始化oss
        initOSS(imgEndpoint,"LTAIxo60meawp0vN","gwCz2nvKCS9qU5FYdfZSW7xO1UgVLy");
        defineIsland=getIntent().getIntExtra("defineIsland",0);
        defineIslandName=getIntent().getStringExtra("defineIslandName");
        defineIslandLogo=getIntent().getStringExtra("defineIslandLogo");
        defineIslandCategoryId=getIntent().getIntExtra("defineIslandCategoryId",0);
        defineIslandCategoryName=getIntent().getStringExtra("defineIslandCategoryName");
        defineIslandCategoryParentId=getIntent().getIntExtra("defineIslandCategoryParentId",0);
        defineIslandCategoryParentName=getIntent().getStringExtra("defineIslandCategoryParentName");
        if(getIntent().hasExtra("defineIslandUniId"))
            defineIslandUniId=getIntent().getIntExtra("defineIslandUniId",0);
        if(getIntent().hasExtra("defineIslandUniName"))
            defineIslandUniName=getIntent().getStringExtra("defineIslandUniName");
        tv_island_name2.setText(defineIslandName);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //弹出时间选择器选择生日
        pvTime= new TimePickerView.Builder(PublishActActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                date1 = date;
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String startTime=sdf.format(date);
                start_year=startTime.substring(0,4);
                start_month=startTime.substring(5,7);
                start_day=startTime.substring(8,10);
                start_hour=startTime.substring(11,13);
                start_minute=startTime.substring(14,16);
                //修改textview
                tv_start_time2.setText(startTime);
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setOutSideCancelable(true)
                .isCyclic(true)
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setSubmitColor(Color.GRAY)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .isCenterLabel(false)
                .build();
        ll_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show();
            }
        });
        //弹出时间选择器选择生日
        pvTimeEnd= new TimePickerView.Builder(PublishActActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                date2 = date;
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time=sdf.format(date);
                end_year=time.substring(0,4);
                end_month=time.substring(5,7);
                end_day=time.substring(8,10);
                end_hour=time.substring(11,13);
                end_minute=time.substring(14,16);
                //修改textview
                tv_end_time2.setText(time);
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY_HOUR_MIN)
                .setCancelText("取消")
                .setSubmitText("确定")
                .setContentSize(20)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setOutSideCancelable(true)
                .isCyclic(true)
                .setTextColorCenter(Color.BLACK)//设置选中项的颜色
                .setSubmitColor(Color.GRAY)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .isCenterLabel(false)
                .build();
        ll_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show();
            }
        });
        ll_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTimeEnd.show();
            }
        });
        ll_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(PublishActActivity.this, SelectProvinceActivity.class);
                startActivityForResult(intent, REQUEST_PRO_CITY_REGION);
            }
        });
        ll_uni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(PublishActActivity.this, com.test.hyxc.page.university.SelectProvinceActivity.class);
                startActivityForResult(intent1, REQUEST_PRO_CITY_UNIVERSITY);
            }
        });
        iv_cover_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gallery();
            }
        });
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    tryPublish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void initData() {}
    /**
     * 填充常住地
     */
    public void tryFillLocation(String province,String city,String district){
        String text=province+" "+city;
        if(!"".equals(district)){
            text=text+" "+district;
        }
        tv_location_text.setText(text);
        tv_location_text.setTextColor(Color.parseColor("#8D8B8B"));
    }
    /**
     * 从相册获取2
     */
    public void gallery() {
            Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_PRO_CITY_REGION://选择省市区
                if(resultCode==RESULT_OK){
                    province=intent.getStringExtra("province");
                    city=intent.getStringExtra("city");
                    district=intent.getStringExtra("region");
                    if(district.equalsIgnoreCase("noRegion")){
                        district="";
                    }
                    tryFillLocation(province,city,district);
                }
                break;
            case  REQUEST_PRO_CITY_UNIVERSITY://选择大学
                if(resultCode==RESULT_OK){
                    String province=intent.getStringExtra("province");
                    String city=intent.getStringExtra("city");
                    university_id=intent.getIntExtra("selectUniversityId",0);
                    university=intent.getStringExtra("selectUniversity");
                    if(university.equalsIgnoreCase("noUniversity")){
                        university_id=0;
                        university="";
                        tv_uni_text.setText("");
                        Toast.makeText(getApplicationContext(),"所选城市下没有大学！",Toast.LENGTH_LONG).show();
                    }else {
                        tv_uni_text.setText(university);
                        tv_uni_text.setTextColor(Color.parseColor("#8D8B8B"));
                    }
                }
                break;
            //相册选择
            case  PHOTO_REQUEST_GALLERY:
                // 从相册返回的数据
                if (intent != null) {
                    // 得到图片的全路径
                    Uri uri = intent.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = this.managedQuery(uri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    selectCover = cursor.getString(column_index);
                    //显示图片
                    ll_cover_show.setVisibility(View.VISIBLE);
                    ContentResolver cr = this.getContentResolver();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                        iv_select_cover.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        Log.e("Exception", e.getMessage(),e);
                    }

                }
                break;
           default:
               break;
        }
    }

    //尝试发布
    public void  tryPublish() throws Exception {
        if(et_title.getText()==null||et_title.getText().toString().equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"请填写活动标题~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }else if(et_act_text.getText()==null||et_act_text.getText().toString().equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"请填写活动内容信息~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }else if(et_act_text.getText().toString().length()>200){
            Toast toast=Toast.makeText(getApplicationContext(),"活动内容信息最多200字~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }else if(tv_start_time2.getText()==null||tv_start_time2.getText().toString().equals("")||start_year.equals("")||start_month.equals("")||start_day.equals("")||start_hour.equals("")||start_minute.equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"请选择开始时间~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }else if(tv_end_time2.getText()==null||tv_end_time2.getText().toString().equals("")||end_year.equals("")||end_month.equals("")||end_day.equals("")||end_hour.equals("")||end_minute.equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"请选择结束时间~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }else if(date1.getTime() >= date2.getTime()){ //保证开始时间小于结束时间
            Toast toast=Toast.makeText(getApplicationContext(),"时间选择不合理~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }
        else if(tv_location_text.getText()==null||tv_location_text.getText().toString().equals("")||province.equals("")||city.equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"请选择举办地区~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }else if(et_address.getText()==null||et_address.getText().toString().equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"具体地址未填写~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }/*else if(tv_uni_text.getText()!=null&&!tv_uni_text.getText().toString().equals("")){ //
            if(defineIslandUniName!=null||!defineIslandUniName.equals("")&&defineIslandUniId!=-1){
            }
        }*/
        else if(selectCover==null||selectCover.equals("")){
            Toast toast=Toast.makeText(getApplicationContext(),"请选择活动封面图片~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        } else {
            //loading对话框
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog=new LoadingDialog(PublishActActivity.this);
                    loadingDialog.show();
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //尝试上传图片
                    String localUrl=selectCover;
                    String remoteUrl="activity/actis_"+defineIsland+"_"+ localUrl.substring(localUrl.lastIndexOf("/")+1,localUrl.lastIndexOf("."))+".jpg";
                    // 构造上传请求。
                    PutObjectRequest put = new PutObjectRequest(bucket, remoteUrl, localUrl);
                    // 文件元信息的设置是可选的。
                    // ObjectMetadata metadata = new ObjectMetadata();
                    // metadata.setContentType("application/octet-stream"); // 设置content-type。
                    // metadata.setContentMD5(BinaryUtil.calculateBase64Md5(uploadFilePath)); // 校验MD5。
                    // put.setMetadata(metadata);
                    try {
                        PutObjectResult putResult = oss.putObject(put);
                        Log.d("PutObject", "UploadSuccess");
                        Log.d("ETag", putResult.getETag());
                        Log.d("RequestId", putResult.getRequestId());
                    } catch (ClientException e) {
                        // 本地异常，如网络异常等。
                        e.printStackTrace();
                        ossUploadWrong=true;
                        if(loadingDialog!=null)
                            loadingDialog.dismiss();
                    } catch (ServiceException e) {
                        ossUploadWrong=true;
                        if(loadingDialog!=null)
                            loadingDialog.dismiss();
                        // 服务异常。
                        Log.e("RequestId", e.getRequestId());
                        Log.e("ErrorCode", e.getErrorCode());
                        Log.e("HostId", e.getHostId());
                        Log.e("RawMessage", e.getRawMessage());
                    }
                    //保证oss上传正常
                    if(ossUploadWrong){
                        Toast toast=Toast.makeText(getApplicationContext(),"图片上传异常",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,240);
                        toast.show();
                        overridePendingTransition(0,R.anim.imap_fade_out_right);
                        finish();
                    }else{
                        //异步请求
                        NetUtils netUtils=NetUtils.getInstance();
                        String token=((AppContext)getApplicationContext()).getToken();
                        Map map=new HashMap();
                        map.put("island_id",String.valueOf(defineIsland));
                        map.put("act_title",et_title.getText().toString());
                        map.put("act_text",et_act_text.getText().toString());
                        map.put("act_linkman",((AppContext)getApplicationContext()).getUserId());
                        if(university!=null&&!university.equals("")){
                            map.put("act_island_university",String.valueOf(university_id));
                        }
                        map.put("act_city",city);
                        map.put("act_district",district);
                        //map.put("act_people_count",0);
                        map.put("act_time",start_year+"-"+start_month+"-"+start_day+" "+start_hour+":"+start_minute+":00");
                        map.put("act_end_time",end_year+"-"+end_month+"-"+end_day+" "+end_hour+":"+end_minute+":00");
                        map.put("act_address",et_address.getText().toString());
                        map.put("act_longitude","0");
                        map.put("act_latitude","0");
                        map.put("act_people_limit","10000");
                        map.put("act_people_count","0");
                        map.put("acturls",remoteUrl);
                        try {
                            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_BASE + "/publishActivity",token, map, new NetUtils.MyNetCall() {
                                @Override
                                public void success(Call call, Response response) throws IOException,JSONException {
                                    //loading对话框关闭
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(loadingDialog!=null)
                                               loadingDialog.dismiss();
                                        }
                                    });
                                    String ret=response.body().string();
                                    JSONObject json = new JSONObject(ret);
                                    //如果token过期,就重新登陆
                                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                                        startActivity(new Intent(PublishActActivity.this, LoginActivity.class));
                                        overridePendingTransition(0,R.anim.imap_fade_out_right);
                                        finish();
                                    }else {
                                        if(json.has("addActState")&&json.get("addActState").equals("success")){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast toast=Toast.makeText(getApplicationContext(),"发布成功！",Toast.LENGTH_LONG);
                                                    toast.setGravity(Gravity.CENTER,0,400);
                                                    toast.show();
                                                    overridePendingTransition(0,R.anim.imap_fade_out_right);
                                                    finish();
                                                }
                                            });
                                        }
                                    }

                                }
                                @Override
                                public void failed(Call call, IOException e) {
                                    //loading对话框关闭
                                   runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(loadingDialog!=null)
                                               loadingDialog.dismiss();
                                        }
                                    });
                                    Toast toast=Toast.makeText(getApplicationContext(),"发布失败，发生未知异常..",Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER,0,400);
                                    toast.show();
                                    overridePendingTransition(0,R.anim.imap_fade_out_right);
                                    finish();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

    //初始化一个OSS用来上传下载
    public void  initOSS(String endpoint,String akid,String aksecret) {
        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(akid, aksecret);
        //使用自己的获取STSToken的类
        //OSSCredentialProvider credentialProvider = new STSGetter(stsServer);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(20 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getContext(), endpoint, credentialProvider, conf);
        this.oss=oss;
    }

    public void makeSureProvince(String cityName,String districtName){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("cityname", cityName);
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_PRO_CITY_AREA + "/findProByCityname", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(PublishActActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    final Gson gson=new Gson();
                    Integer province_id = json.getInt("province_id");
                    String province_name = json.getString("province_name");
                    province=province_name;
                    city= cityName;
                    district =districtName;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv_location_text.setText(province+" "+activityShow.getAct_city()+" "+ activityShow.getAct_district());
                        }
                    });
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
