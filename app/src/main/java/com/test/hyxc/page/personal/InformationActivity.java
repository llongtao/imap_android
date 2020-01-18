package com.test.hyxc.page.personal;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.BuildConfig;
import com.test.hyxc.MainActivity;
import com.test.hyxc.R;
import com.test.hyxc.chat.database.UserEntry;
import com.test.hyxc.chat.utils.SharePreferenceManager;
import com.test.hyxc.headimg.ClipImageActivity;
import com.test.hyxc.headimg.util.FileUtil;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Province;
import com.test.hyxc.model.UserInfo;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.location.SelectProvinceActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.InflaterOutputStream;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.ConstellationUtil;
import tools.LoadingDialog;
import tools.NetUtils;
import tools.OssConfig;

import static com.test.hyxc.headimg.util.FileUtil.getRealFilePathFromUri;
public class InformationActivity extends BaseActivity  implements  View.OnClickListener{
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //地区选择
    private static final int REQUEST_PRO_CITY_REGION = 110;
    private static final int REQUEST_PRO_CITY_UNIVERSITY = 120;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //oss
    private OssConfig ossConfig=null;
    private OSS oss;
    private String bucket="yourbucket";
    private final static String imgEndpoint = "yourImgEndpoint";
    //表征上传oss是否异常
    private boolean ossUploadWrong=false;
    public LoadingDialog loadingDialog;
    private String url="";
    /**********需要获取的数据***************/
    String cropImagePath="";
    String nickname;
    String birth_year,birth_month,birth_day;
    String sign;
    String province,city,district;
    String university;
    String constell; //星座
    int university_id;
    int gender=0;// 0为没有选择 1：男 2女


    //头像
    private ImageView iv_headimg,iv_back,iv_male,iv_female;
    EditText et_nickname,et_sign;

    //调用照相机返回图片文件
    private File tempFile;
    /////////////////
    LinearLayout ll_headimg,ll_birth,ll_location,ll_uni;
    LinearLayout ll_male,ll_female;
    TextView tv_birth_text,tv_location_text,tv_male,tv_female,tv_uni_text;
    Button btn_save;
    //时间选择器
    TimePickerView pvTime ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_personal_information;
    }

    @Override
    protected void initListener() {

    }
    @Override
    protected void initView() {
        //对4.4以下的系统做没有沉浸式菜单显示布局的处理
        /*if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.KITKAT){
            ll_container.setPadding(0,0,0, DipPxUtils.dip2px(this,3));
        }*/
        this.ossConfig=((AppContext)getApplicationContext()).getOssConfig();
        this.url=ossConfig.getHost()+"/";
        //如果不是第一次补充信息 应该请求一次 将现在的内容填充进去
        testFill();
        //初始化oss
        initOSS(imgEndpoint,"LTAIxo60meawp0vN","gwCz2nvKCS9qU5FYdfZSW7xO1UgVLy");
        tv_birth_text=f(R.id.tv_birth_text);
        ll_location=f(R.id.ll_location);
        tv_location_text=f(R.id.tv_location_text);
        btn_save=f(R.id.btn_save);

        //弹出时间选择器选择生日
        pvTime= new TimePickerView.Builder(InformationActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String birth=sdf.format(date);
                String [] s=birth.split("-");
                birth_year=s[0];
                birth_month=s[1];
                birth_day=s[2];
                //修改textview
                tv_birth_text.setText(birth);
            }
        })
                .setType(TimePickerView.Type.YEAR_MONTH_DAY)
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
        /*pvTime=new TimePickerBuilder(InformationActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                String birth=sdf.format(date);
                String [] s=birth.split("-");
                birth_year=s[0];
                birth_month=s[1];
                birth_day=s[2];
                //修改textview
                tv_birth_text.setText(birth);
            }
        }).build();*/
        iv_back=f(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_nickname=f(R.id.et_nickname);
        et_sign=f(R.id.et_sign);
        ll_headimg=f(R.id.ll_headimg);
        ll_birth=f(R.id.ll_birth);
        ll_headimg.setOnClickListener(InformationActivity.this);
        ll_birth.setOnClickListener(InformationActivity.this);
        ll_location.setOnClickListener(InformationActivity.this);
        iv_headimg=f(R.id.iv_headimg);
        //性别
        ll_male=f(R.id.ll_male);
        ll_female=f(R.id.ll_female);
        iv_male=f(R.id.iv_male);
        iv_female=f(R.id.iv_female);
        tv_male=f(R.id.tv_male);
        tv_female=f(R.id.tv_female);
        ll_male.setOnClickListener(InformationActivity.this);
        ll_female.setOnClickListener(InformationActivity.this);
        //学校
        ll_uni=f(R.id.ll_uni);
        ll_uni.setOnClickListener(InformationActivity.this);
        tv_uni_text=f(R.id.tv_uni_text);
        btn_save.setOnClickListener(InformationActivity.this);
        loadingDialog=new LoadingDialog(this);
    }

    @Override
    protected void initData() { }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //头像
            case R.id.ll_headimg:
                //需要让软键盘 隐藏
                InputMethodManager imm=(InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null)
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                uploadHeadImage();
                break;
            //性别
            case R.id.ll_male:
                 changeSex(1);
                break;
            case  R.id.ll_female:
                changeSex(2);
                break;
            //生日
            case R.id.ll_birth:
                 pvTime.show();
                 break;
            //地区
            case R.id.ll_location:
                Intent intent = new Intent();
                intent.setClass(this, SelectProvinceActivity.class);
                startActivityForResult(intent, REQUEST_PRO_CITY_REGION);
                break;
            //学校
            case R.id.ll_uni:
                Intent intent1 = new Intent();
                intent1.setClass(this, com.test.hyxc.page.university.SelectProvinceActivity.class);
                startActivityForResult(intent1, REQUEST_PRO_CITY_UNIVERSITY);
                break;
            case R.id.btn_save:
                trySaveInformation();
                break;
          default:
                break;
        }
    }
    /**
     * 修改性别
     */
    public void changeSex(int i){
        if(i==1){
            //男
            gender=1;
            iv_male.setImageResource(R.mipmap.male);
            tv_male.setTextColor(Color.parseColor("#6D8ED7"));
            iv_female.setImageResource(R.mipmap.female1);
            tv_female.setTextColor(Color.parseColor("#8D8B8B"));
        }else if(i==2){
            //女
            gender=2;
            iv_female.setImageResource(R.mipmap.female);
            tv_female.setTextColor(Color.parseColor("#6D8ED7"));
            iv_male.setImageResource(R.mipmap.male1);
            tv_male.setTextColor(Color.parseColor("#8D8B8B"));
        }
    }
    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.imap_personal_information, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.3f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });
        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(InformationActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(InformationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(InformationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(InformationActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }
    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    //注意cropImagePath就是要上传的头像路径
                    cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                     iv_headimg.setImageBitmap(bitMap);
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......
                }
                break;
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
        }
    }
    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);
    }
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
        OSS oss = new OSSClient(getApplicationContext(), endpoint, credentialProvider, conf);
        this.oss=oss;
    }
    /**
     * 保存信息
     */
    public void trySaveInformation(){
        /*if(cropImagePath==null||"".equals(cropImagePath)){
            Toast.makeText(getApplicationContext(),"请选择头像~",Toast.LENGTH_SHORT).show();
            return;
        }else */
        if(et_nickname.getText()==null||"".equals(et_nickname.getText().toString())){
            Toast.makeText(getApplicationContext(),"请设置昵称",Toast.LENGTH_SHORT).show();
            return;
        }else if(et_nickname.getText().length()>8){
            Toast.makeText(getApplicationContext(),"昵称最大8个字符哦",Toast.LENGTH_SHORT).show();
            return;
        }else  if(gender==0){
            Toast.makeText(getApplicationContext(),"性别还没选呢",Toast.LENGTH_SHORT).show();
            return;
        }else if(et_sign.getText()==null||"".equals(et_sign.getText().toString())){
            Toast.makeText(getApplicationContext(),"设置签名吧",Toast.LENGTH_SHORT).show();
            return;
        }else if(birth_year==null||"".equals(birth_year)||birth_month==null||"".equals(birth_month)||birth_day==null||"".equals(birth_day)){
            Toast.makeText(getApplicationContext(),"赶快填写生日吧",Toast.LENGTH_SHORT).show();
            return;
        }else if(province==null||"".equals(province)){
            Toast.makeText(getApplicationContext(),"请选择地区",Toast.LENGTH_SHORT).show();
            return;
        }else if(city==null||"".equals(city)){
            Toast.makeText(getApplicationContext(),"请选择地区",Toast.LENGTH_SHORT).show();
            return;
        }else if(tv_location_text.getText().toString()==null||"".equals(tv_location_text.getText().toString())){
            Toast.makeText(getApplicationContext(),"请选择地区",Toast.LENGTH_SHORT).show();
            return;
        }else if(university==null||"".equals(university)){
            Toast.makeText(getApplicationContext(),"请选择大学",Toast.LENGTH_SHORT).show();
            return;
        }else if(university_id==0||university_id==-1){
            Toast.makeText(getApplicationContext(),"请选择大学",Toast.LENGTH_SHORT).show();
            return;
        }else if (tv_uni_text.getText()==null||"".equals(tv_uni_text.getText().toString())){
            Toast.makeText(getApplicationContext(),"请选择大学",Toast.LENGTH_SHORT).show();
            return;
        }else if(!checkString(et_nickname.getText().toString())||!checkString(et_sign.getText().toString())||!checkString(tv_location_text.getText().toString())){
                Toast.makeText(getApplicationContext(),"所填信息包含非法字符，请修改",Toast.LENGTH_LONG).show();
                return;
        } else {

                //正式补全信息
               constell=ConstellationUtil.getConstell(Integer.parseInt(birth_month),Integer.parseInt(birth_day));
               /*String localUrl=cropImagePath;
               String remoteUrl="headImg/head_"+((AppContext)getApplicationContext()).getUserId()+"_"+ localUrl.substring(localUrl.lastIndexOf("/")+1,localUrl.lastIndexOf("."))+".jpg";
                // 构造上传请求。
                PutObjectRequest put = new PutObjectRequest(bucket, remoteUrl, localUrl);
                try {
                    PutObjectResult putResult = oss.putObject(put);
                    Log.d("PutObject", "UploadSuccess");
                    Log.d("ETag", putResult.getETag());
                    Log.d("RequestId", putResult.getRequestId());
                } catch (ClientException e) {
                    // 本地异常，如网络异常等。
                    e.printStackTrace();
                    ossUploadWrong=true;
                } catch (ServiceException e) {
                    ossUploadWrong=true;
                    // 服务异常。
                    Log.e("RequestId", e.getRequestId());
                    Log.e("ErrorCode", e.getErrorCode());
                    Log.e("HostId", e.getHostId());
                    Log.e("RawMessage", e.getRawMessage());
                }
                /*//*********补录数据库
                if(ossUploadWrong){
                    Toast.makeText(getApplicationContext(),"头像上传失败",Toast.LENGTH_LONG).show();
                    return;
                }else {*/
                        //loading对话框
                        InformationActivity.this.runOnUiThread(new Runnable() {
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
                                    map.put("user_nickname", et_nickname.getText().toString());
                                    map.put("user_gender", String.valueOf(gender));
                                    map.put("user_signature", et_sign.getText().toString());
                                    map.put("user_birth_year", birth_year);
                                    map.put("user_birth_month", birth_month);
                                    map.put("user_birth_day", birth_day);
                                    map.put("user_constell", constell);
                                    map.put("user_province", province);
                                    map.put("user_city", city);
                                    if (district != null || !"".equals(district)) {
                                        map.put("user_area", district);
                                    }
                                    if (university_id != 0 && university_id != -1) {
                                        map.put("user_uni", String.valueOf(university_id));
                                    }
                                    netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/changeUserInfo", token, map, new NetUtils.MyNetCall() {
                                        @Override
                                        public void success(Call call, Response response) throws IOException, JSONException {
                                            //loading对话框关闭
                                           InformationActivity.this.runOnUiThread(new Runnable() {
                                               @Override
                                               public void run() {
                                                   loadingDialog.hide();
                                               }
                                           });
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
                                                            if (json.has("changeState") && json.get("changeState").equals("success")) {
                                                                Gson gson=new Gson();
                                                                JSONObject obj=(JSONObject) json.get("userInfo");
                                                                UserInfo userInfo=gson.fromJson(obj.toString(), UserInfo.class);
                                                                ((AppContext)getApplicationContext()).setNickname(userInfo.getUser_nickname());
                                                                //((AppContext)getApplicationContext()).setHeadImg(userInfo.getUser_headimg());
                                                                //极光信息 如果是首次补充信息 就会录入极光系统，这时需要登录极光系统
                                                                if(json.has("JGUserInfoResult")&&!"".equals(json.get("JGUserInfoResult"))){
                                                                    loginJiguang();
                                                                }
                                                                Toast.makeText(getApplicationContext(), "信息补充成功~", Toast.LENGTH_LONG).show();
                                                                Intent intent = new Intent();
                                                                intent.putExtra("user_id", ((AppContext)getApplicationContext()).getUserId());
                                                                intent.setClass(InformationActivity.this, MeActivity.class);
                                                                startActivity(intent);
                                                                finish();
                                                            } else {
                                                                Toast.makeText(getApplicationContext(), "信息补充失败，请重试", Toast.LENGTH_LONG).show();
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
    //}
    /***检验非法字符*****************/
    public boolean checkString(String s){
        if(s.contains("|")||s.contains("_")||s.contains("!")||s.contains("\\")||s.contains("&")||s.contains("%")||s.contains("?")){
            return false;
        }
        return true;
    }
    //
    public void testFill(){
        if(((AppContext)getApplicationContext()).getNickname()!=null&&!"".equals(((AppContext)getApplicationContext()).getNickname())){
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext)getApplicationContext()).getToken();
            Map map=new HashMap();
            try {
                netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/findUserInfoById",token, map, new NetUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException,JSONException{
                        String ret=response.body().string();
                        JSONObject json = new JSONObject(ret);
                        //如果token过期,就重新登陆
                        if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                            startActivity(new Intent(InformationActivity.this, LoginActivity.class));
                            finish();
                            return;
                        }
                        final Gson gson = new Gson();
                        JSONObject user_info=json.getJSONObject("user_info");
                        if(user_info!=null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UserInfo ui = gson.fromJson(user_info.toString(), UserInfo.class);
                                    et_nickname.setText(ui.getUser_nickname());
                                    changeSex(Integer.parseInt(ui.getUser_gender()));
                                    et_sign.setText(ui.getUser_signature());
                                    tv_birth_text.setText(ui.getUser_birth_year()+"-"+ui.getUser_birth_month()+"-"+ui.getUser_birth_day());
                                    tv_location_text.setText(ui.getUser_province()+" "+ui.getUser_city()+" "+ui.getUser_area());
                                    tv_uni_text.setText(ui.getUni_name());
                                    //给校验的字段赋值
                                    gender=Integer.parseInt(ui.getUser_gender());
                                    birth_year=ui.getUser_birth_year();
                                    birth_month=ui.getUser_birth_month();
                                    birth_day=ui.getUser_birth_day();
                                    province=ui.getUser_province();
                                    city=ui.getUser_city();
                                    district=ui.getUser_area();
                                    university=ui.getUni_name();
                                    university_id=ui.getUser_uni();
                                }
                            });
                        }
                    }
                    @Override
                    public void failed(Call call, IOException e) {}
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public  void loginJiguang() {
        try {
            JMessageClient.login(((AppContext)getApplicationContext()).getUsername(), ((AppContext)getApplicationContext()).getPassword(), new BasicCallback() {
                @Override
                public void gotResult(int responseCode, String responseMessage) {
                    if (responseCode == 0) {
                        SharePreferenceManager.setCachedPsw(((AppContext)getApplicationContext()).getPassword());
                        cn.jpush.im.android.api.model.UserInfo myInfo = JMessageClient.getMyInfo();
                        File avatarFile = myInfo.getAvatarFile();
                        //登陆成功,如果用户有头像就把头像存起来,没有就设置null
                        if (avatarFile != null) {
                            SharePreferenceManager.setCachedAvatarPath(avatarFile.getAbsolutePath());
                        } else {
                            SharePreferenceManager.setCachedAvatarPath(null);
                        }
                        String username = myInfo.getUserName();
                        String appKey = myInfo.getAppKey();
                        /*UserEntry user = UserEntry.getUser(username, appKey);
                        if (null == user) {
                            user = new UserEntry(username, appKey);
                            user.save();
                        }*/
                    } else {
                        Log.i("极光登陆失败", "极光登陆失败");
                    }
                }
            });
        }catch (Exception e){
            Log.i("login Jiguang error", "login Jiguang error!");

        }
    }
}
