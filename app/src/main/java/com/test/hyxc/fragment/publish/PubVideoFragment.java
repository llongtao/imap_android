package com.test.hyxc.fragment.publish;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.test.hyxc.R;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.page.publish.PoiChooseActivity;
import com.test.hyxc.page.activity.PublishActivity;
import com.test.hyxc.ui.GlideRoundTransform;
import com.test.hyxc.videoeditor.ui.activity.TrimVideoActivity;
import com.test.hyxc.videoeditor.ui.activity.VideoCameraActivity;
//import com.vincent.videocompressor.VideoCompress;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nereo.multi_image_selector.MultiImageSelectorActivity;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.BaiduMap.service.LocationService;
import tools.LoadingDialog;
import tools.NetUtils;
import tools.OssConfig;

import static me.yokeyword.fragmentation.SupportFragment.RESULT_OK;

public class PubVideoFragment extends Fragment {
    private GridView gw;
    //权限
    private static  String[] PERMISSIONS_STORAGE = {Manifest.permission.CAMERA};
    private List<Map<String, Object>> datas;
    //上传oss后的图片列表
    private List<String> listOssdatas=new ArrayList<>();
    private GridViewAddVideoAdpter gridViewAddVideoAdpter;
    private Dialog dialog,dialog1;
    private final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    //多图选择
    private final int sum=1;//摄像总数
    private final  int VIDEO_SELECT=3;
    private final int POI_SELECT=4;
    private ArrayList<String> tempdata =new ArrayList<>();
    private LinearLayout ll_publish_to;
    //数量显示
    private TextView tv_num;
    private File tempFile;
    private final String VIDEO_DIR = Environment.getExternalStorageDirectory().getPath() +File.separator
            + "videoeditor"+File.separator+"small_video";
    /* 临时图片名称 */
    private final String VIDEO_FILE_NAME = "temp_video.mp4";
    //发布到的海岛
    private List<Island> listisland=new ArrayList<>();
    //是否请求到了数据
    private  boolean gotIsData=false;
    //选中的
    public   TextView tv_selected_island,tv_district;
    private ImageView iv_selected_island;
    private OssConfig ossConfig=null;
    private String url="";
    private int publish_index=-1;//广场
    private Button btn_publish;
    private EditText et_content;
    //oss
    private OSS oss;
    private String bucket="yourbucket";
    private final static String imgEndpoint = "yourImgEndpoint";
    //
    private  String  user_id;
    //表征上传oss是否异常
    private boolean ossUploadWrong=false;
    //位置信息
    public String country,city,district,province,street,coorType;
    public float radius;
    public double longitude,latitude;
    public  LocationService locationService;
    public  Boolean getLocation=false;
    //是否选择具体位置信息position
    public  boolean getDetailPosition=false;
    //上传中提示
    public LoadingDialog loadingDialog;
    //是否确定了海岛
    public int defineIsland;
    public String defineIslandName;
    public String defineIslandLogo;
    //分类
    public int defineIslandCategoryId=0;
    public String defineIslandCategoryName="";
    public int defineIslandCategoryParentId=0;
    public String defineIslandCategoryParentName="";
    private RxPermissions mRxPermissions;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.imap_pubvideo, container, false);
        iv_selected_island=view.findViewById(R.id.iv_selected_island);
        tv_selected_island=view.findViewById(R.id.tv_selected_island);
        this.ossConfig=((AppContext)getActivity().getApplicationContext()).getOssConfig();
        this.url=ossConfig.getHost()+"/";
        defineIsland=((PublishActivity)getActivity()).defineIsland;
        defineIslandName=((PublishActivity)getActivity()).defineIslandName;
        defineIslandLogo=((PublishActivity)getActivity()).defineIslandLogo;
        defineIslandCategoryId=((PublishActivity)getActivity()).defineIslandCategoryId;
        defineIslandCategoryName=((PublishActivity)getActivity()).defineIslandCategoryName;
        defineIslandCategoryParentId=((PublishActivity)getActivity()).defineIslandCategoryParentId;
        defineIslandCategoryParentName=((PublishActivity)getActivity()).defineIslandCategoryParentName;
        mRxPermissions = new RxPermissions(getActivity());
        if(defineIsland==0){
            //设置默认的海岛  广场
            Island is = new Island();
            is.setIs_id(-1);
            is.setIs_name("广场");
            is.setIs_logo("/default/island/square.png");
            listisland.add(is);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(getContext())
                            .load(url+is.getIs_logo())
                            .transform(new CenterCrop(getContext()),new GlideRoundTransform(getContext()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .crossFade()
                            .into(iv_selected_island);
                }
            });
            tv_selected_island.setText(is.getIs_name());
        }else {
            Island is =new Island();
            is.setIs_id(defineIsland);
            is.setIs_name(defineIslandName);
            is.setIs_logo(defineIslandLogo);
            listisland.add(is);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(getContext())
                            .load(url+is.getIs_logo())
                            .transform(new CenterCrop(getContext()),new GlideRoundTransform(getContext()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .crossFade()
                            .into(iv_selected_island);
                }
            });
            tv_selected_island.setText(is.getIs_name());
        }
        tv_num=view.findViewById(R.id.tv_num);
        ll_publish_to=view.findViewById(R.id.ll_publish_to);
        et_content=view.findViewById(R.id.et_content);
        //地区显示
        tv_district=view.findViewById(R.id.tv_district);
        tv_district.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), PoiChooseActivity.class);
                startActivityForResult(intent,POI_SELECT);
                getActivity().overridePendingTransition(R.anim.imap_fade_in_from_right,R.anim.imap_fade_out_to_left);
            }
        });
        //发布
        btn_publish=view.findViewById(R.id.btn_publish);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    tryPublish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        gw = view.findViewById(R.id.gw);
        datas = new ArrayList<>();
        gridViewAddVideoAdpter = new GridViewAddVideoAdpter(datas, getActivity());
        gridViewAddVideoAdpter.setOnItemClickListener(MyItemClickListener);
        gw.setAdapter(gridViewAddVideoAdpter);
        gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                showdialog();
            }
        });
        ll_publish_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(defineIsland == 0) {
                    try {
                        showChooseIslandDlg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        user_id=((AppContext)getActivity().getApplicationContext()).getUserId();
        //初始化oss
        initOSS(imgEndpoint,"LTAIxo60meawp0vN","gwCz2nvKCS9qU5FYdfZSW7xO1UgVLy");
        //获取地理信息
        getLocation();
        return view;
    }

    public void getLocation(){
        // -----------location config ------------
        locationService = ((AppContext)getActivity().getApplicationContext()).locationService;
        //获取locationservice实例，建议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听
        //int type = getIntent().getIntExtra("from", 0);
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
    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if(location.getCity()!=null&&location.getDistrict()!=null&&!"".equals(location.getCity())&&!"".equals(location.getDistrict())){
                    longitude=location.getLongitude();
                    latitude=location.getLatitude();
                    country=location.getCountry();
                    province=location.getProvince();
                    city=location.getCity();
                    district=location.getDistrict();
                    street=location.getStreet();
                    radius=location.getRadius();
                    coorType=location.getCoorType();
                    getLocation=true;
                    //获取到就结束service
                    locationService.stop();
                    tv_district.setText(district);
                }
            }
        }
    };
    //初始化一个OSS用来上传下载
    public void  initOSS(String endpoint,String akid,String aksecret) {
        //如果希望直接使用accessKey来访问的时候，可以直接使用OSSPlainTextAKSKCredentialProvider来鉴权。
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider(akid, aksecret);
        //使用自己的获取STSToken的类
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(20 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        OSS oss = new OSSClient(getContext(), endpoint, credentialProvider, conf);
        this.oss=oss;
    }
    /**
     * 选择海岛对话框
     */
    public void showChooseIslandDlg() throws Exception {
        if(defineIsland!=0){  //确定了海岛的
            tryFill(listisland);
        }else {
            //如果请求过数据无需再请求
            if (!gotIsData) {
                //先进行获取用户信息
                NetUtils netUtils = NetUtils.getInstance();
                String token = ((AppContext) getActivity().getApplicationContext()).getToken();
                String userId = user_id;
                Map map = new HashMap();
                map.put("user_id", userId);
                netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_RESIDENT_BASE + "/findIslandByUserId", token, map, new NetUtils.MyNetCall() {
                    @Override
                    public void success(Call call, Response response) throws IOException, JSONException {
                        String ret = response.body().string();
                        JSONObject json = new JSONObject(ret);
                        //如果token过期,就重新登陆
                        if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();
                            return;
                        } else {
                            gotIsData = true;
                            listisland.clear();//先清空
                            //设置默认的海岛  广场
                            Island is = new Island();
                            is.setIs_id(-1);
                            is.setIs_name("广场");
                            is.setIs_logo("/default/island/square.png");
                            listisland.add(is);
                            final Gson gson = new Gson();
                            JSONArray jsonArray = json.getJSONArray("listIR");
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject obj = jsonArray.getJSONObject(i);
                                    Island island = new Island();
                                    island.setIs_id((int) obj.get("res_island"));
                                    island.setIs_logo(obj.getString("is_logo"));
                                    island.setIs_name(obj.getString("res_island_name"));
                                    island.setIs_category_id(obj.getInt("is_category_id"));
                                    island.setIs_category(obj.getString("is_category"));
                                    island.setIs_category_parent_id(obj.getInt("is_category_parent_id"));
                                    island.setIs_category_parent(obj.getString("is_category_parent"));
                                    listisland.add(island);
                                }
                            }
                            tryFill(listisland);
                        }
                    }

                    @Override
                    public void failed(Call call, IOException e) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast toast = Toast.makeText(getActivity(), "获取海岛列表失败", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 440);
                                toast.show();
                            }
                        });
                    }
                });
            } else {
                //直接填充
                tryFill(listisland);
            }
        }
    }
    /**
     * 根据获取的海岛列表数据填充
     */
    public void tryFill(List<Island> listisland){
        final View localView1 =LayoutInflater.from(getActivity()).inflate(R.layout.imap_publish_dialog_choose_island,null);
        LinearLayout ll_container=localView1.findViewById(R.id.ll_container);
        int index=-1;
        for(Island island:listisland){
            index++;
            int is_id=island.getIs_id();
            String is_name=island.getIs_name();
            final String is_logo=island.getIs_logo();
            //外层linear
            LinearLayout linearLayout=new LinearLayout(getActivity());
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            linearLayout.setLayoutParams(layoutParams);
            linearLayout.setBackgroundResource(R.drawable.imap_listmenu_border);
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            final ImageView imageView =new ImageView(getActivity());
            //imageView.setImageResource(R.mipmap.imap_square);
            //这里加载网络图片
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(island.getIs_name().equals("广场")) {
                        imageView.setImageResource(R.mipmap.imap_square);
                    }else {
                        Glide.with(getContext())
                                .load(url + is_logo)
                                .transform(new CenterCrop(getContext()), new GlideRoundTransform(getContext()))
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                                .crossFade()
                                .into(imageView);
                    }
                }
            });
            LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(dp2px(getContext(),36f),dp2px(getContext(),36f));
            lp.gravity=Gravity.CENTER;
            lp.setMargins(dp2px(getContext(),10f),dp2px(getContext(),3f),dp2px(getContext(),10f),dp2px(getContext(),3f));
            imageView.setLayoutParams(lp);
            TextView textView=new TextView(getActivity());
            LinearLayout.LayoutParams lp1=new LinearLayout.LayoutParams(0,120);
            lp1.weight=1;
            textView.setGravity(Gravity.LEFT|Gravity.CENTER_VERTICAL);
            textView.setLayoutParams(lp1);
            textView.setPadding(dp2px(getContext(),15f),2,0,0);
            textView.setText(is_name);
            textView.setTextColor(Color.parseColor("#666666"));
            textView.setTextSize(17);
            try{
                linearLayout.addView(imageView);
                linearLayout.addView(textView);
                ll_container.addView(linearLayout,index);
                final int finalIndex = index;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position= finalIndex;
                        //真正选择了海岛
                        getChooseIsland(position);
                    }
                });
            }catch (Exception e){
                Log.e("PubImgFragment",e.getMessage());
                e.printStackTrace();
            }
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog1=new Dialog(getContext(),R.style.custom_dialog);
                dialog1.setContentView(localView1);
                dialog1.getWindow().setGravity(Gravity.BOTTOM);
                // 设置全屏
                WindowManager windowManager = getActivity().getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = dialog1.getWindow().getAttributes();
                lp.width = display.getWidth(); // 设置宽度
                dialog1.getWindow().setAttributes(lp);
                dialog1.show();
            }
        });
    }
    /**
     * 将px转换为与之相等的dp
     */
    public static int px2dp(Context context, float pxValue) {
        final float scale =  context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 选中海岛
     */
    public void getChooseIsland(int position){
        publish_index=position;
        if(listisland.size()>0&&listisland.size()>position) {
            final Island island = listisland.get(position);
            //  Toast.makeText(getContext(), "您选择了" + island.getIs_name() + "!", Toast.LENGTH_LONG).show();
            //修改
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(getContext())
                            .load(url+island.getIs_logo())
                            .transform(new CenterCrop(getContext()),new GlideRoundTransform(getContext()))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .crossFade()
                            .into(iv_selected_island);
                }
            });
            tv_selected_island.setText(island.getIs_name());
            if(dialog1!=null)
              dialog1.dismiss();
        }
    }
    /**
     * 选择图片对话框
     */
    public void showdialog() {
        View localView = LayoutInflater.from(getActivity()).inflate(
                R.layout.imap_publish_dialog_add_video, null);
        TextView tv_camera = (TextView) localView.findViewById(R.id.tv_camera);
        TextView tv_gallery = (TextView) localView.findViewById(R.id.tv_gallery);
        TextView tv_cancel = (TextView) localView.findViewById(R.id.tv_cancel);
        dialog = new Dialog(getContext(), R.style.custom_dialog);
        dialog.setContentView(localView);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        // 设置全屏
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = display.getWidth(); // 设置宽度
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });

        tv_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 摄像
                camera();
            }
        });

        tv_gallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                // 从系统相册选取照片
                gallery();
            }
        });
    }

    /**
     * 摄像
     */
    public void camera() {
        //权限检查及提醒
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_STORAGE, 1);
            }
        }
         if (hasSdcard()) {
        } else {
            Toast.makeText(getActivity(), "未找到存储卡，无法录像！", Toast.LENGTH_SHORT).show();
        }
        if(hasSdcard()){
            File dir=new File(VIDEO_DIR);
            if(!dir.exists()){
                dir.mkdir();
            }
            mRxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.CAMERA)
                    .subscribe(granted -> {
                        if (granted) { //已获取权限
                            Intent intent = new Intent(getActivity(), VideoCameraActivity.class);
                            startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
                        } else {
                            Toast.makeText(getActivity(), "请打开响应权限~", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    /**
     * 判断sdcard是否被挂载
     */
    public boolean hasSdcard() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    /**
     * 从相册获取2
     */
    public void gallery() {
          Intent intent = new Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode==VIDEO_SELECT){
                if(resultCode == RESULT_OK){
                    // Get the result list of select image paths
                    List<String> path = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    for(String s:path){
                        uploadImage(s);
                    }
                }
            }
            //从相册选择 并截切之后的视频
            if(requestCode==10){
                if (hasSdcard()) {
                    Bundle bundle=data.getExtras();
                    String tempFile=bundle.getString("outputPath");
                    if (tempFile != null && !"".equals(tempFile)) {
                        uploadImage(tempFile);
                    } else {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "获取剪切后的视频失败！", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    Log.i("images", "拿到照片path=" + tempFile);
                }else {
                    Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                }

            }
            //相册选择
            if (requestCode == PHOTO_REQUEST_GALLERY) {
                // 从相册返回的数据
                if (data != null) {
                    // 得到图片的全路径
                    Uri uri = data.getData();
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = getActivity().managedQuery(uri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                    //跳转到视频播放页面
                    Intent intent=new Intent(getActivity(), TrimVideoActivity.class);
                    intent.putExtra("videoPath",path);
                    startActivityForResult(intent,10);
                    //uploadImage(path);
                }

            } else if (requestCode == PHOTO_REQUEST_CAREMA) {
                    // 从相机返回的数据
                    if (hasSdcard()) {
                        Bundle bundle=data.getExtras();
                        String tempFile=bundle.getString("outputPath");
                        if (tempFile != null&&!"".equals(tempFile)) {
                            uploadImage(tempFile);
                        } else {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getActivity(), "相机异常请稍后再试！", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        Log.i("images", "拿到照片path=" + tempFile);
                    } else {
                        Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
                    }
                }

        }
        //poi选择结果
        if(requestCode==POI_SELECT){
            //操作
            if(data!=null&&data.getExtras()!=null&&data.getExtras().containsKey("selectPoi")&&data.getExtras().getInt("selectPoi")==1){
                getDetailPosition=true;
                String name = data.getExtras().getString("name");
                String address = data.getExtras().getString("address");
                double lat = data.getExtras().getDouble("lat");
                double lng = data.getExtras().getDouble("lng");
                tv_district.setText(name);
            }
        }
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0xAAAAAAAA) {
                photoPath(msg.obj.toString());
            }

        }
    };
    /**
     * 上传视频
     *
     * @param path
     */
    private void uploadImage(final String path) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Message message = new Message();
                    message.what = 0xAAAAAAAA;
                    message.obj = path;
                    handler.sendMessage(message);
                   /* VideoCompress.compressVideoLow(path, file.getAbsolutePath(), new VideoCompress.CompressListener() {
                        @Override
                        public void onStart() {
                          getActivity().runOnUiThread(new Runnable() {
                              @Override
                              public void run() {
                                  compressingDialog=new CompressingDialog(getContext());
                                  compressingDialog.show();
                              }
                          });
                        }

                        @Override
                        public void onSuccess() {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    compressingDialog.dismiss();
                                }
                            });
                            Message message = new Message();
                            message.what = 0xAAAAAAAA;
                            message.obj = file.getAbsolutePath();
                            handler.sendMessage(message);
                        }

                        @Override
                        public void onFail() {
                           getActivity().runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   if(compressingDialog!=null){
                                       compressingDialog.dismiss();
                                   }
                                   Toast.makeText(getContext(),"压缩视频出现异常！",Toast.LENGTH_LONG);
                                   getActivity().finish();
                               }
                           });

                        }

                        @Override
                        public void onProgress(final float percent) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    int pint=(int)percent;
                                    if(percent>=100f){
                                        String text = "压缩完成!";
                                        compressingDialog.mTxtMsg.setText(text);
                                    }
                                    if(pint%5==0) {
                                        String text = "压缩进度:";
                                        text = text + String.valueOf(pint) + "%";
                                        compressingDialog.mTxtMsg.setText(text);
                                    }

                                }
                            });
                        }
                    });*/
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public void photoPath(String path) {
        Map<String,Object> map=new HashMap<>();
        map.put("path",path);
        datas.add(map);
        gridViewAddVideoAdpter.notifyDataSetChanged();
        //修改图片显示数量
        tv_num.setText(datas.size()+"/"+sum);
    }
    /**item＋item里的控件点击监听事件 */
    private GridViewAddVideoAdpter.OnItemClickListener MyItemClickListener = new GridViewAddVideoAdpter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int position) {
            switch (v.getId()) {
                case R.id.bt_del:
                    //改变图片显示数量
                    tv_num.setText(datas.size()+"/"+sum);
                    break;
                default:
                    break;
            }
        }
    };
    //尝试发布
    public void  tryPublish() throws Exception {
        if(datas.size()==0||datas==null){
            Toast toast=Toast.makeText(getContext(),"视频为空~",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }else if(!getLocation){
            Toast toast=Toast.makeText(getContext(),"正在定位,请等待",Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,240);
            toast.show();
        }else {
            //loading对话框
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadingDialog=new LoadingDialog(getContext());
                    loadingDialog.show();
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //尝试上传图片
                    for(Map m:datas) {
                        String localUrl=m.get("path").toString();
                        String remoteUrl="work/work_video_"+user_id+"_"+ localUrl.substring(localUrl.lastIndexOf("/")+1,localUrl.lastIndexOf("."))+".mp4";
                        // 构造上传请求。
                        PutObjectRequest put = new PutObjectRequest(bucket, remoteUrl, localUrl);
                        // 文件元信息的设置是可选的。
                        // ObjectMetadata metadata = new ObjectMetadata();
                        // metadata.setContentType("application/octet-stream"); // 设置content-type。
                        // metadata.setContentMD5(BinaryUtil.calculateBase64Md5(uploadFilePath)); // 校验MD5。
                        // put.setMetadata(metadata);
                        try {
                            PutObjectResult putResult = oss.putObject(put);
                            listOssdatas.add(remoteUrl);
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
                    }

                    //然后入库
                    //保证oss上传正常
                    if(ossUploadWrong){
                        Toast toast=Toast.makeText(getContext(),"图片上传异常",Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,240);
                        toast.show();
                        getActivity().overridePendingTransition(0,R.anim.imap_fade_out_right);
                        getActivity().finish();
                    }else{
                        String work_text=et_content.getText().toString();
                        String work_author=user_id;
                        String work_island="";
                        if(defineIsland==0) { //没有确定海岛的情况
                            if (publish_index == -1 || publish_index == 0) {
                                work_island = "-1";
                            } else {
                                work_island = listisland.get(publish_index).getIs_id().toString();
                            }
                        }else{ //确定的情况
                            work_island=String.valueOf(defineIsland);
                        }
                        String workUrl="";
                        for(String s:listOssdatas){
                            workUrl=workUrl+s+"|";
                        }
                        if(workUrl.endsWith("|")){
                            workUrl=workUrl.substring(0,workUrl.lastIndexOf("|"));
                        }
                        //异步请求
                        NetUtils netUtils=NetUtils.getInstance();
                        String token=((AppContext)getActivity().getApplicationContext()).getToken();
                        Map map=new HashMap();
                        map.put("work_text",work_text);
                        map.put("work_author",work_author);
                        map.put("work_island",work_island);
                        map.put("work_city",city);
                        map.put("work_district",district);
                        //如果选择了具体的位置信息 Position字段
                        if(getDetailPosition){
                            map.put("work_position",tv_district.getText().toString());
                        }
                        map.put("work_longitude",String.valueOf(longitude));
                        map.put("work_latitude",String.valueOf(latitude));
                        map.put("workurl",workUrl);
                        if(defineIsland==0) {
                            //判断是否是广场
                            if (Integer.parseInt(work_island) != -1) {
                                //不是广场就添加海岛信息
                                map.put("work_island_category_id", listisland.get(publish_index).getIs_category_id().toString());
                                map.put("work_island_category", listisland.get(publish_index).getIs_category());
                                map.put("work_island_category_parent_id", listisland.get(publish_index).getIs_category_parent_id().toString());
                                map.put("work_island_category_parent", listisland.get(publish_index).getIs_category_parent());
                            }
                        }else{
                            map.put("work_island_category_id", String.valueOf(defineIslandCategoryId));
                            map.put("work_island_category", defineIslandCategoryName);
                            map.put("work_island_category_parent_id", String.valueOf(defineIslandCategoryParentId));
                            map.put("work_island_category_parent", defineIslandCategoryParentName);

                        }
                        try {
                            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/AddWorkVedio",token, map, new NetUtils.MyNetCall() {
                                @Override
                                public void success(Call call, Response response) throws IOException,JSONException{
                                    //loading对话框关闭
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingDialog.hide();
                                        }
                                    });
                                    String ret=response.body().string();
                                    JSONObject json = new JSONObject(ret);
                                    //如果token过期,就重新登陆
                                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                                        startActivity(new Intent(getActivity(), LoginActivity.class));
                                        getActivity().overridePendingTransition(0,R.anim.imap_fade_out_right);
                                        getActivity().finish();
                                    }else {
                                        if(json.has("uploadState")&&json.get("uploadState").equals("success")){
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast toast=Toast.makeText(getContext(),"发布成功！",Toast.LENGTH_LONG);
                                                    toast.setGravity(Gravity.CENTER,0,400);
                                                    toast.show();
                                                    getActivity().overridePendingTransition(0,R.anim.imap_fade_out_right);
                                                    getActivity().finish();
                                                }
                                            });
                                        }
                                    }

                                }
                                @Override
                                public void failed(Call call, IOException e) {
                                    //loading对话框关闭
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            loadingDialog.hide();
                                        }
                                    });
                                    Toast toast=Toast.makeText(getContext(),"发布失败，发生未知异常..",Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER,0,400);
                                    toast.show();
                                    getActivity().overridePendingTransition(0,R.anim.imap_fade_out_right);
                                    getActivity().finish();
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


}
