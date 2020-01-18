package com.test.hyxc;
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.adapter.PagerMainAdapter;
import com.test.hyxc.chat.entity.Event;
import com.test.hyxc.fragment.ActivityFragment;
import com.test.hyxc.fragment.FollowFragment;
import com.test.hyxc.fragment.RecommendFragment;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Version;
import com.test.hyxc.page.island.IslandSearchActivity;
import com.test.hyxc.page.island.IslandShowActivity;
import com.test.hyxc.page.personal.InformationActivity;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.page.activity.PublishActivity;
import com.test.hyxc.page.search.SearchActivity;
import com.zaaach.citypicker.CityActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import q.rorbin.badgeview.QBadgeView;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.BaiduMap.service.LocationService;
import tools.NetUtils;

import static tools.AppContext.context;

public class MainActivity extends BaseActivity {
    private AppContext ctx;
    private static final int ACCESS_LOCATION_READ_WRITE = 1;  //位置
    private ViewPager f_vp;
    private ImageView img_cursor;
    private List<Fragment> mFragments;
    private  int bmpWidth,offset;
    private int currIndex = 0;//当前页面的编号
    private int one = 0; //移动条滑动一页的距离
    private int two = 0; //滑动条移动两页的距离
    private LinearLayout ll_toptitle;
    private TextView tv_recommend,tv_follow,tv_activity;
    private TextView[] top_tvs=null;
    private TextView tv_city;
    private LinearLayout ll_city;
    private  int[] top_title_tvs={R.id.tv_recommend,R.id.tv_follow,R.id.tv_activity};
    //底部菜单
    private TextView tv_shouye,tv_island,tv_news,tv_me;
    private ImageView iv_publish;
    private ImageView iv_search;
    private RelativeLayout container;
    public LocationService locationService;
    public  Boolean getLocation=false;
    QBadgeView qb=null;
    private static String tempDir = Environment.getExternalStorageDirectory() + "/gridview/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_main;
    }

    @Override
    protected void initView() {
        ctx = (AppContext)this.getApplicationContext();
        container=f(R.id.container);
        checkPermission();
        //对4.4以下的系统做没有沉浸式菜单显示布局的处理
        if(Build.VERSION.SDK_INT<=Build.VERSION_CODES.KITKAT){
            container.setPadding(0,0,0,0);
        }
        //版本更新有关 检查版本号
        String appVersion;
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            appVersion = info.versionName; //版本名
            //checkAppVersion(appVersion); //暂时不去进行更新
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        f_vp = f(R.id.f_vp);
        //三个toptitle
        ll_toptitle=f(R.id.ll_toptitle);
        tv_city=f(R.id.tv_city);
        ll_city=f(R.id.ll_city);
        ll_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(MainActivity.this,CityActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.imap_fade_in,0);
            }
        });
        tv_recommend=f(R.id.tv_recommend);
        tv_follow=f(R.id.tv_follow);
        tv_activity=f(R.id.tv_activity);
        top_tvs=new TextView[3];
        top_tvs[0]=tv_recommend;
        top_tvs[1]=tv_follow;
        top_tvs[2]=tv_activity;
        img_cursor=f(R.id.img_cursor);
        //底部菜单
        tv_news=f(R.id.tv_news);
        tv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                    Intent intent=new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("user_id", ctx.getUserId());
                    intent.setClass(MainActivity.this, com.test.hyxc.chat.activity.MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            }
        });
        tv_shouye=f(R.id.tv_shouye);
        iv_publish=f(R.id.iv_publish);
        tv_news=f(R.id.tv_news);
        tv_me=f(R.id.tv_me);
        tv_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                    Intent intent=new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("user_id", ctx.getUserId());
                    intent.setClass(MainActivity.this, MeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            }
        });
        tv_island=f(R.id.tv_island);
        tv_island.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                    Intent intent=new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, IslandSearchActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
            }
        });
        iv_search=f(R.id.iv_search);
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                    Intent intent=new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, SearchActivity.class);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
                /*Intent intent = new Intent();
                intent.setClass(MainActivity.this, TestActivity.class);
                startActivity(intent);*/
            }
        });
        ////****这里进行极光登陆
        //JiguangLogin();
        qb=new QBadgeView(this);
        //qb.bindTarget(tv_news).setBadgeNumber(news_unread);
        /*qb.setOnDragStateChangedListener(new Badge.OnDragStateChangedListener(){
            @Override
            public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                int STATE_START = 1;
                int STATE_DRAGGING = 2;
                int STATE_DRAGGING_OUT_OF_RANGE = 3;
                int STATE_CANCELED = 4;
                int STATE_SUCCEED = 5;
                 onDragStateChanged(dragState,  badge,  targetView);
            }
        });*/
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        bmpWidth = BitmapFactory.decodeResource(getResources(), R.mipmap.imap_line).getWidth();// 获取图片宽度
        int screenW = dm.widthPixels;
        offset=(screenW*2/4/3-bmpWidth)/2;
        one=screenW/6;
        two = one * 2;// 移动两页的偏移量,比如1直接跳3
        Matrix matrix = new Matrix();
        matrix.postTranslate(screenW*1/4+offset, 0);
        img_cursor.setImageMatrix(matrix);// 设置动画初始位置
        //获取地理位置信息
        getLocation();
    }
    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED||
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                ) {
            //没有权限的时候去申请
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                 Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                 Manifest.permission.READ_EXTERNAL_STORAGE,
                                 Manifest.permission.RECORD_AUDIO,
                                 Manifest.permission.CAMERA},
                    ACCESS_LOCATION_READ_WRITE);
        }
    }
    //权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case ACCESS_LOCATION_READ_WRITE :
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED){
                } else {
                    //申请失败
                    finish();
                }
                break;
            default:
                break;
        }
    }
    public void getLocation(){
        // -----------location config ------------
        locationService = ((AppContext)getApplicationContext()).locationService;
        //获取locationservice实例，建L议应用中只初始化1个location实例，然后使用，可以参考其他示例的activity，都是通过此种方式获取locationservice实例的
        locationService.registerListener(mListener);
        //注册监听m
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
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                if(location.getCity()!=null&&location.getDistrict()!=null&&!"".equals(location.getCity())&&!"".equals(location.getDistrict())){
                    double longitude=location.getLongitude();
                    double latitude =location.getLatitude();
                    String city = location.getCity();
                    ((AppContext)getApplicationContext()).setLongitude(longitude);
                    ((AppContext)getApplicationContext()).setLatitude(latitude);
                    ((AppContext)getApplicationContext()).setCity(city);
                    //将经纬度保存下来
                    getLocation=true;
                    //获取到就结束service
                    locationService.stop();
                }
            }
        }
    };
    @Override
    protected void initData() {
        mFragments=new ArrayList<>();
        RecommendFragment one=new RecommendFragment();
        FollowFragment two=new FollowFragment();
        ActivityFragment three=new ActivityFragment();
        mFragments.add(one);
        mFragments.add(two);
        mFragments.add(three);
        // 设置填充器
        f_vp.setAdapter(new PagerMainAdapter(getSupportFragmentManager(),mFragments));
        tv_recommend.setTextColor(Color.BLACK);
        tv_recommend.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        // 设置缓存页面数
        f_vp.setOffscreenPageLimit(2);
    }
   @Override
   protected void initListener() {
       //toptitle的点击事件
          for(int i=0;i<top_tvs.length;i++){
              final int finalI = i;
              top_tvs[i].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        f_vp.setCurrentItem(finalI);
                    }
                });
          }
       //ViewPager的改变事件 vp-rg互相监听：vp
       f_vp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
           @Override
           public void onPageSelected(int position) {
               Animation animation = null;
               switch (position) {
                   case 0:
                       if (currIndex == 1) {
                           animation = new TranslateAnimation(one, 0, 0, 0);
                           tv_follow.setTextColor(Color.rgb(99,99,99));
                           tv_follow.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                       } else if (currIndex == 2) {
                           animation = new TranslateAnimation(two, 0, 0, 0);
                           tv_activity.setTextColor(Color.rgb(99,99,99));
                           tv_activity.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                       }
                       tv_recommend.setTextColor(Color.BLACK);
                       tv_recommend.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                       break;
                   case 1:
                       if (currIndex == 0) {
                           animation = new TranslateAnimation(offset, one, 0, 0);
                           tv_recommend.setTextColor(Color.rgb(99,99,99));
                           tv_recommend.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                       } else if (currIndex == 2) {
                           animation = new TranslateAnimation(two, one, 0, 0);
                           tv_activity.setTextColor(Color.rgb(99,99,99));
                           tv_activity.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                       }
                       tv_follow.setTextColor(Color.BLACK);
                       tv_follow.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                       break;
                   case 2:
                       if (currIndex == 0) {
                           animation = new TranslateAnimation(offset, two, 0, 0);
                           tv_recommend.setTextColor(Color.rgb(99,99,99));
                           tv_recommend.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                       } else if (currIndex == 1) {
                           animation = new TranslateAnimation(one, two, 0, 0);
                           tv_follow.setTextColor(Color.rgb(99,99,99));
                           tv_follow.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                       }
                       tv_activity.setTextColor(Color.BLACK);
                       tv_activity.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                       break;
               }
               currIndex = position;
               animation.setFillAfter(true);// true表示图片停在动画结束位置
               animation.setDuration(300); //设置动画时间为300毫秒
               img_cursor.startAnimation(animation);//开始动画
           }
       });
       //底部菜单
       iv_publish.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
                               //判定用户是否已经补全用户信息
                               if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                                   Intent intent=new Intent(MainActivity.this, InformationActivity.class);
                                   startActivity(intent);
                                   overridePendingTransition(R.anim.imap_fade_in_from_right, R.anim.imap_fade_out_from_right);
                               }else {
                                   Intent intent = new Intent(MainActivity.this, PublishActivity.class);
                                   startActivity(intent);
                                   overridePendingTransition(R.anim.imap_fade_in_from_right, R.anim.imap_fade_out_from_right);
                               }
           }
       });
   }
   //处理消息数量
    public String dealUnread(Integer news_unread){
       if(news_unread>99){
           return "99+";
       }else {
           return news_unread+"";
       }
    }
    void checkAppVersion(String appVersion){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("client_type","0"); //0 ：安卓 1：ios
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_VERSION_BASE + "/getLatestVersion",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException {
                    String ret=response.body().string();
                    final Gson gson=new Gson();
                    JSONObject json = new JSONObject(ret);
                    // if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                    // startActivity(new Intent(MainActivity.this, LoginActivity.class));// finish();// return;// }
                    String versionStr=json.getString("version");
                    if (versionStr!=null&&!"".equals(versionStr)) {
                        Version version = gson.fromJson(versionStr,Version.class);
                        if(!appVersion.equals(version.getVersion_code())){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                     showNormalDialog(version);
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

    private void showNormalDialog(Version version) {
        AlertDialog normalDialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.warn)
                .setTitle("版本有更新，需要更新么？")
                //.setMessage("test")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            tryDownLoad(version);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        // 显示
        normalDialog.show();
        normalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#0b090e"));
        normalDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(15);
        normalDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#0b090e"));
        normalDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextSize(15);
    }

    public void tryDownLoad(Version version){
        //进度条
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("正在下载更新");
        pd.show();
        new Thread(){
            @Override
            public void run() {
                try {
                    File file = getFileFromServer(version.getDownload_path(), pd);
                    //安装APK
                    installApk(file);
                    pd.dismiss(); //结束掉进度条对话框
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}.start();

    }
    public static File getFileFromServer(String path,ProgressDialog pd) throws  Exception{
        //如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        //if(Environment.getExternalStorageDirectory().equals(Environment.MEDIA_MOUNTED)){
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            //获取文件大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(tempDir,"udpata.apk");
            //File file = new File(Environment.getExternalStorageDirectory(),"updata.apk");
            FileOutputStream fos =new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1){
                fos.write(buffer,0,len);
                total += len;
                //获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
       // }else {
       //     return  null;
       // }
    }

    protected  void installApk(File file){
//        Intent intent = new Intent();
//        //执行动作
//        intent.setAction(Intent.ACTION_VIEW);
//        //执行的数据类型
//        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
//        startActivity(intent);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.test.hyxc.fileProvider", file);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(intent);
    }

}