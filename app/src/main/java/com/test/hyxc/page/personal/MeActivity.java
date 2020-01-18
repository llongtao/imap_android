package com.test.hyxc.page.personal;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.DeleteObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.PagerMainAdapter;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.fragment.Me.MyActFragment;
import com.test.hyxc.fragment.Me.MyFollowFragment;
import com.test.hyxc.fragment.Me.MyFriendFragment;
import com.test.hyxc.fragment.Me.MyIslandFragment;
import com.test.hyxc.fragment.Me.MyWorkFragment;
import com.test.hyxc.headimg.ClipImageActivity;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.UserInfo;
import com.test.hyxc.page.island.IslandSelectCategoryActivity;
import com.test.hyxc.ui.GlideRoundTransform;
import com.test.hyxc.view.BigImgDlg;
import com.test.hyxc.view.SimpleDlg;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.test.hyxc.chat.activity.ChatActivity;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.DipPxUtils;
import tools.GlobalConfig;
import tools.IMConfig;
import tools.NetUtils;
import static com.test.hyxc.headimg.util.FileUtil.getRealFilePathFromUri;
import static com.test.hyxc.videoeditor.utils.UIUtils.getContext;
import static tools.AppContext.context;

public class MeActivity extends BaseActivity implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {
    LinearLayout ll_top, ll_personal_edit, ll_follow, ll_send_news;
    TextView tv_follow, tv_send_news;
    public int user_id=-1;
    UserInfo userInfo = null;
    ImageView iv_edit, iv_head, iv_sex;
    boolean self = false; //是否是自己看这个页面
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    String cropImagePath = "";
    SimpleDlg simpleDlg;
    ImageView iv_big_head;
    BigImgDlg bigImgDlg;
    TextView tv_change_cover, tv_nickname, tv_sign, tv_birth, tv_position, tv_school, tv_constell, tv_fans, tv_zan, tv_work;
    Window window;
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择 修改背景图
    private final int PHOTO_REQUEST_GALLERY_HEADIMG = 3;
    private String selectImg = "";
    String url = "";
    int winWidth = 0;
    //菜单
    TabLayout tabs;
    View view1, view2, view3, view4, view5;
    List<String> mTitleList = new ArrayList<String>();
    List<View> mViewList = new ArrayList<>();
    private List<Fragment> mFragments;
    ViewPager vp;
    AppContext ctx;
    //oss
    private OSS oss;
    private String bucket = "yourbucket";
    private boolean ossUploadWrong = false;
    private final static String imgEndpoint = "yourImgEndpoint";
    //表征 这个对该用户的关注状态
    public Integer follow = -1; //默认未关注
    ///极光系统传userName
    String userName="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarColor(R.color.color4);
        //.statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutID() {
        return R.layout.imap_me;
    }

    @Override
    protected void initListener() {
    }

    @Override
    protected void initView() throws Exception {
        ctx = ((AppContext) getApplicationContext());
        url = ((AppContext) getApplicationContext()).getOssConfig().getHost() + "/";
        initOSS(imgEndpoint, "LTAIxo60meawp0vN", "gwCz2nvKCS9qU5FYdfZSW7xO1UgVLy");
        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        winWidth = wm.getDefaultDisplay().getWidth();
        //通过传userid
        if(getIntent().hasExtra("user_id")) {
            user_id = Integer.parseInt(getIntent().getExtras().getString("user_id"));
            int userID = Integer.parseInt(((AppContext) getApplicationContext()).getUserId());
            self = userID == user_id ? true : false;
        }
        //通过传userName 获取极光系统信息
        if(getIntent().hasExtra("userName")){
            userName = getIntent().getStringExtra("userName");
            String username = ((AppContext)getApplicationContext()).getUsername();
            self = userName.equals(username);
        }
        ll_top = f(R.id.ll_top);
        ll_follow = f(R.id.ll_follow);
        tv_follow = f(R.id.tv_follow);
        ll_send_news = f(R.id.ll_send_news);
        tv_send_news = f(R.id.tv_send_news);
        tv_send_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(AppContext.CONV_TITLE, userInfo.getUser_nickname());
                String targetId = userInfo.getUser_name();
                intent.putExtra(AppContext.TARGET_ID, targetId);
                intent.putExtra(AppContext.TARGET_APP_KEY, IMConfig.appkey);
                intent.putExtra(AppContext.DRAFT, "");
                intent.setClass(context, ChatActivity.class);
                context.startActivity(intent);
            }
        });
        tv_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    //这里判断下如果是取消关注 要弹窗提示下 取消关注 就不再是好友
                    if (follow == -1 || follow == 1) { //如果未关注 就关注
                        tryFollow();
                    } else {
                        showNormalDialog();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ll_personal_edit = f(R.id.ll_personal_edit);
        //根据用户是否是自己判断显示编辑  还是关注按钮
        if (self) {
            ll_personal_edit.setVisibility(View.VISIBLE);
            ll_follow.setVisibility(View.INVISIBLE);
        } else {
            ll_personal_edit.setVisibility(View.INVISIBLE);
            ll_follow.setVisibility(View.INVISIBLE); //先不显示关注按钮
            //确定该用户和自己的关系 0:关注了 -1：未关注 1：取消了关注
            if(getIntent().hasExtra("user_id")) {
                makeSureFollow(user_id);
            }else if(getIntent().hasExtra("userName")){
                makeSureFollowByUserName(userName);
            }
        }
        ll_top.setBackgroundResource(R.mipmap.personal_back);
        ll_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更换封面
                if (self) {
                    simpleDlg = new SimpleDlg(MeActivity.this, R.style.SimpleDlg);
                    simpleDlg.show();
                    window = simpleDlg.getWindow();
                    tv_change_cover = window.findViewById(R.id.tv_change_cover);
                    tv_change_cover.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //从相册选择图片
                            gallery();
                        }
                    });
                }
            }
        });
        iv_edit = f(R.id.iv_edit);
        iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MeActivity.this, InformationActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        iv_head = f(R.id.iv_head);
        //头像点击事件
        iv_head.setOnClickListener(this);
        tv_nickname = f(R.id.tv_nickname);
        iv_sex = f(R.id.iv_sex);
        tv_sign = f(R.id.tv_sign);
        tv_birth = f(R.id.tv_birth);
        tv_school = f(R.id.tv_school);
        tv_position = f(R.id.tv_position);
        tv_constell = f(R.id.tv_constell);
        tv_fans = f(R.id.tv_fans);
        tv_zan = f(R.id.tv_zan);
        tv_work = f(R.id.tv_work);
        initTab();
        if(user_id!=-1) {
            requireMeMessage();
        }else {
            requireMeMessage1();
        }
        //查找我的粉丝数
    }

    @Override
    protected void initData() {
    }

    public void initTab() {
        tabs = f(R.id.tabs);
        vp = f(R.id.vp);
        //标题添加页卡标题
        if (self) {
            mTitleList.add("动态");
            mTitleList.add("好友");
            mTitleList.add("海岛");
            mTitleList.add("活动");
            mTitleList.add("关注");
            tabs = f(R.id.tabs);
            view1 = tab_icon(mTitleList.get(0));
            view2 = tab_icon(mTitleList.get(1));
            view3 = tab_icon(mTitleList.get(2));
            view4 = tab_icon(mTitleList.get(3));
            view5 = tab_icon(mTitleList.get(4));
            //添加页卡视图
            mViewList.add(view1);
            mViewList.add(view2);
            mViewList.add(view3);
            mViewList.add(view4);
            mViewList.add(view5);
            // mTabLayout.addTab( mTabLayout.newTab().setCustomView(view1), true);
            tabs.addTab(tabs.newTab().setCustomView(view1));
            tabs.addTab(tabs.newTab().setCustomView(view2));
            tabs.addTab(tabs.newTab().setCustomView(view3));
            tabs.addTab(tabs.newTab().setCustomView(view4));
            tabs.addTab(tabs.newTab().setCustomView(view5));
        } else {
            mTitleList.add("动态");
            mTitleList.add("海岛");
            tabs = f(R.id.tabs);
            view1 = tab_icon(mTitleList.get(0));
            view2 = tab_icon(mTitleList.get(1));
            //添加页卡视图
            mViewList.add(view1);
            mViewList.add(view2);
            // mTabLayout.addTab( mTabLayout.newTab().setCustomView(view1), true);
            tabs.addTab(tabs.newTab().setCustomView(view1));
            tabs.addTab(tabs.newTab().setCustomView(view2));
        }
        //Tablayout自定义view绑定ViewPager 自定义view时使用 tabLayout.setupWithViewPager(viewPager);方法关联无效，通过以下方法进行viewpager和tablayout的关联
        vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        tabs.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(vp));
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#FEE75F"));
                ((TextView) tab.getCustomView().findViewById(R.id.tabtext)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                ((TextView) tab.getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#878687"));
                ((TextView) tab.getCustomView().findViewById(R.id.tabtext)).setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //默认选中第一个
        tabs.getTabAt(0).select();
        ((TextView) tabs.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#FEE75F"));
        ((TextView) tabs.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
    }

    //tabbar
    private View tab_icon(String name) {
        View newtab = LayoutInflater.from(this).inflate(R.layout.tab_item, null);
        TextView tv = newtab.findViewById(R.id.tabtext);
        tv.setTextSize(16);
        tv.setText(name);
        tv.setTextColor(Color.parseColor("#878687"));
        return newtab;
    }

    public void initVP() {
        mFragments = new ArrayList<>();
        if (self) {
            MyWorkFragment one = new MyWorkFragment();
            MyFriendFragment two = new MyFriendFragment();
            MyIslandFragment three = new MyIslandFragment();
            MyActFragment four = new MyActFragment();
            MyFollowFragment five = new MyFollowFragment();
            mFragments.add(one);
            mFragments.add(two);
            mFragments.add(three);
            mFragments.add(four);
            mFragments.add(five);
        } else {
            MyWorkFragment one = new MyWorkFragment();
            MyIslandFragment two = new MyIslandFragment();
            mFragments.add(one);
            mFragments.add(two);
        }
        // 设置填充器
        vp = f(R.id.vp);
        vp.setAdapter(new PagerMainAdapter(getSupportFragmentManager(), mFragments));
        vp.setOffscreenPageLimit(2);
    }

    /* private void requireMyFans() throws Exception {
         NetUtils netUtils=NetUtils.getInstance();
         String token=((AppContext)getApplicationContext()).getToken();
         Map map=new HashMap();
         map.put("user_id2",String.valueOf(user_id));
         netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_FRIEND_BASE + "/findFollowMeCount",token, map, new NetUtils.MyNetCall() {
             @Override
             public void success(Call call, Response response) throws IOException, JSONException {
                 String ret=response.body().string();
                 JSONObject json = new JSONObject(ret);
                 //如果token过期,就重新登陆
                 if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                     startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                     finish();
                     return;
                 }
                 if (json.has("followMeCount") && json.get("followMeCount")!=null) {
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             int followmecount=-1;
                             try{
                                 followmecount=json.getInt("followMeCount");
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                             if(followmecount!=-1){
                                 tv_fans.setText("粉丝 "+followmecount);
                             }else {
                                 tv_fans.setText("粉丝 ");
                             }

                         }
                     });
                 }

             }
             @Override
             public void failed(Call call, IOException e) {}
         });
     }*/
    /// 1：请求个人信息 通过用户id查找
    private void requireMeMessage() throws Exception {
        NetUtils netUtils = NetUtils.getInstance();
        String token = ((AppContext) getApplicationContext()).getToken();
        Map map = new HashMap();
        map.put("user_id", String.valueOf(user_id));
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/findUserInfoById", token, map, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException, JSONException {
                String ret = response.body().string();
                JSONObject json = new JSONObject(ret);
                //如果token过期,就重新登陆
                if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    return;
                }
                if (json.has("user_info") && json.get("user_info") != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject obj = null;
                            try {
                                obj = json.getJSONObject("user_info");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new Gson();
                            userInfo = gson.fromJson(obj.toString(), UserInfo.class);
                            //跟userName同步
                            userName = userInfo.getUser_name();
                            //这里确定viewpager显示效果
                            initVP();
                            ///封面
                            String cover = userInfo.getUser_cover_img();
                            Glide.with(getApplicationContext()).load(url + cover).asBitmap()//签到整体 背景
                                    .into(new SimpleTarget<Bitmap>(winWidth, DipPxUtils.dip2px(getApplicationContext(), 250)) {        //设置宽高
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            Drawable drawable = new BitmapDrawable(resource);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                ll_top.setBackground(drawable);    //设置背景
                                            }
                                        }
                                    });
                            //头像
                            String headImg = url + GlobalConfig.defaultHeadImg;
                            if (userInfo.getUser_headimg() != null && !"".equals(userInfo.getUser_headimg())) {
                                headImg = url + userInfo.getUser_headimg();
                            }
                            Glide.with(getApplicationContext())
                                    .load(headImg)
                                    .transform(new CenterCrop(getApplicationContext()), new GlideRoundTransform(getApplicationContext(), 50))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .dontAnimate()
                                    .crossFade()
                                    .into(iv_head);
                            tv_nickname.setText(userInfo.getUser_nickname());
                            //性别
                            if (userInfo.getUser_gender().equals("男") || userInfo.getUser_gender().equals("1") || userInfo.getUser_gender() == null) {
                                iv_sex.setImageResource(R.mipmap.male_blue);
                            } else {
                                iv_sex.setImageResource(R.mipmap.female_red);
                            }
                            //签名
                            if (userInfo.getUser_signature() != null && !"".equals(userInfo.getUser_signature())) {
                                tv_sign.setText("签名: " + userInfo.getUser_signature());
                            } else {
                                tv_sign.setText("没个性，不签名！");
                            }
                            //生日
                            String birthStr = "";
                            if (userInfo.getUser_birth_year() != null && userInfo.getUser_birth_month() != null && userInfo.getUser_birth_day() != null)
                                birthStr = userInfo.getUser_birth_year() + "-" + userInfo.getUser_birth_month() + "-" + userInfo.getUser_birth_day();
                            tv_birth.setText(birthStr);
                            String position = "";
                            if (userInfo.getUser_province() != null && userInfo.getUser_city() != null && userInfo.getUser_area() != null)
                                position = userInfo.getUser_province() + userInfo.getUser_city() + userInfo.getUser_area();
                            //常住地
                            tv_position.setText(position);
                            //请求学校名称
                            tv_school.setText(userInfo.getUni_name());
                            //星座
                            tv_constell.setText(userInfo.getUser_constell());
                            //获赞
                            tv_zan.setText("获赞 " + userInfo.getUser_liked_count());
                        }
                    });
                }
                if (json.has("followMeCount") && json.get("followMeCount") != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int followmecount = -1;
                            try {
                                followmecount = json.getInt("followMeCount");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (followmecount != -1) {
                                tv_fans.setText("粉丝 " + followmecount);
                            } else {
                                tv_fans.setText("粉丝 ");
                            }
                        }
                    });
                }
                if (json.has("workCount") && json.get("workCount") != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int workCount = -1;
                            try {
                                workCount = json.getInt("workCount");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (workCount != -1) {
                                tv_work.setText("作品 " + workCount);
                            } else {
                                tv_work.setText("作品 ");
                            }
                        }
                    });
                }

            }

            @Override
            public void failed(Call call, IOException e) {
            }
        });
    }
    /// 2: 通过userName查找
    private void requireMeMessage1() throws Exception {
        NetUtils netUtils = NetUtils.getInstance();
        String token = ((AppContext) getApplicationContext()).getToken();
        Map map = new HashMap();
        map.put("userName", String.valueOf(userName));
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/findUserInfoByUserName", token, map, new NetUtils.MyNetCall() {
            @Override
            public void success(Call call, Response response) throws IOException, JSONException {
                String ret = response.body().string();
                JSONObject json = new JSONObject(ret);
                //如果token过期,就重新登陆
                if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                    return;
                }
                if (json.has("user_info") && json.get("user_info") != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            JSONObject obj = null;
                            try {
                                obj = json.getJSONObject("user_info");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Gson gson = new Gson();
                            userInfo = gson.fromJson(obj.toString(), UserInfo.class);
                            //
                            user_id = userInfo.getUser_id();
                            //这里确定viewpager显示效果
                            initVP();
                            ///封面
                            String cover = userInfo.getUser_cover_img();
                            Glide.with(getApplicationContext()).load(url + cover).asBitmap()//签到整体 背景
                                    .into(new SimpleTarget<Bitmap>(winWidth, DipPxUtils.dip2px(getApplicationContext(), 250)) {        //设置宽高
                                        @Override
                                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                            Drawable drawable = new BitmapDrawable(resource);
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                ll_top.setBackground(drawable);    //设置背景
                                            }
                                        }
                                    });
                            //头像
                            String headImg = url + GlobalConfig.defaultHeadImg;
                            if (userInfo.getUser_headimg() != null && !"".equals(userInfo.getUser_headimg())) {
                                headImg = url + userInfo.getUser_headimg();
                            }
                            Glide.with(getApplicationContext())
                                    .load(headImg)
                                    .transform(new CenterCrop(getApplicationContext()), new GlideRoundTransform(getApplicationContext(), 50))
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .dontAnimate()
                                    .crossFade()
                                    .into(iv_head);
                            tv_nickname.setText(userInfo.getUser_nickname());
                            //性别
                            if (userInfo.getUser_gender().equals("男") || userInfo.getUser_gender().equals("1") || userInfo.getUser_gender() == null) {
                                iv_sex.setImageResource(R.mipmap.male_blue);
                            } else {
                                iv_sex.setImageResource(R.mipmap.female_red);
                            }
                            //签名
                            if (userInfo.getUser_signature() != null && !"".equals(userInfo.getUser_signature())) {
                                tv_sign.setText("签名: " + userInfo.getUser_signature());
                            } else {
                                tv_sign.setText("没个性，不签名！");
                            }
                            //生日
                            String birthStr = "";
                            if (userInfo.getUser_birth_year() != null && userInfo.getUser_birth_month() != null && userInfo.getUser_birth_day() != null)
                                birthStr = userInfo.getUser_birth_year() + "-" + userInfo.getUser_birth_month() + "-" + userInfo.getUser_birth_day();
                            tv_birth.setText(birthStr);
                            String position = "";
                            if (userInfo.getUser_province() != null && userInfo.getUser_city() != null && userInfo.getUser_area() != null)
                                position = userInfo.getUser_province() + userInfo.getUser_city() + userInfo.getUser_area();
                            //常住地
                            tv_position.setText(position);
                            //请求学校名称
                            tv_school.setText(userInfo.getUni_name());
                            //星座
                            tv_constell.setText(userInfo.getUser_constell());
                            //获赞
                            tv_zan.setText("获赞 " + userInfo.getUser_liked_count());
                        }
                    });
                }
                if (json.has("followMeCount") && json.get("followMeCount") != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int followmecount = -1;
                            try {
                                followmecount = json.getInt("followMeCount");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (followmecount != -1) {
                                tv_fans.setText("粉丝 " + followmecount);
                            } else {
                                tv_fans.setText("粉丝 ");
                            }
                        }
                    });
                }
                if (json.has("workCount") && json.get("workCount") != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int workCount = -1;
                            try {
                                workCount = json.getInt("workCount");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (workCount != -1) {
                                tv_work.setText("作品 " + workCount);
                            } else {
                                tv_work.setText("作品 ");
                            }
                        }
                    });
                }

            }

            @Override
            public void failed(Call call, IOException e) {
            }
        });
    }

    ///头像的点击事件 //这里注意是否是自己 要做区分
    @Override
    public void onClick(View v) {
        //创建弹出式菜单对象（最低版本11）
        if (self) {
            PopupMenu popup = new PopupMenu(this, v);//第二个参数是绑定的那个view
            //获取菜单填充器
            MenuInflater inflater = popup.getMenuInflater();
            //填充菜单
            inflater.inflate(R.menu.menu_me, popup.getMenu());
            //绑定菜单项的点击事件
            popup.setOnMenuItemClickListener(this);
            //显示(这一行代码不要忘记了)
            popup.show();
        } else { //如果是其他人的 看头像放大图
            if (bigImgDlg == null)
                bigImgDlg = new BigImgDlg(MeActivity.this, R.style.SimpleDlg);
            bigImgDlg.show();
            window = bigImgDlg.getWindow();
            iv_big_head = window.findViewById(R.id.iv_big_head);
            //头像
            String headImg = url + GlobalConfig.defaultHeadImg;
            if (userInfo.getUser_headimg() != null && !"".equals(userInfo.getUser_headimg())) {
                headImg = url + userInfo.getUser_headimg();
            }
            Glide.with(getApplicationContext())
                    .load(headImg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .crossFade()
                    .into(iv_big_head);
            iv_big_head.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bigImgDlg != null)
                        bigImgDlg.dismiss();
                }
            });
        }
    }

    //初始化一个OSS用来上传下载
    public void initOSS(String endpoint, String akid, String aksecret) {
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
        this.oss = oss;
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        //相册选择 修改背景图
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (intent != null) {
                // 得到图片的全路径
                Uri uri = intent.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(uri, proj, null, null, null);
                //按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                //最后根据索引值获取图片路径
                selectImg = cursor.getString(column_index);
                if (selectImg != null && !"".equals(selectImg)) {
                    tryChangeCoverImg(selectImg);
                }
            }
        }
        //修改用户头像
        if (requestCode == PHOTO_REQUEST_GALLERY_HEADIMG) {
            // 从相册返回的数据
            if (intent != null) {
                // 得到图片的全路径
                Uri uri = intent.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(uri, proj, null, null, null);
                //按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                //最后根据索引值获取图片路径
                selectImg = cursor.getString(column_index);
                if (selectImg != null && !"".equals(selectImg)) {
                    tryChangeHeadImg(selectImg);
                }
            }
        }
        ///使用剪切头像的方法
        if (requestCode == REQUEST_PICK) {  //调用系统相册返回
            if (resultCode == RESULT_OK) {
                Uri uri = intent.getData();
                gotoClipActivity(uri);
            }
        }
        if (requestCode == REQUEST_CROP_PHOTO) {  //剪切图片返回
            if (resultCode == RESULT_OK) {
                final Uri uri = intent.getData();
                if (uri == null) {
                    return;
                }
                //注意cropImagePath就是要上传的头像路径
                cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                iv_head.setImageBitmap(bitMap);
                //此处后面可以将bitMap转为二进制上传后台网络
                //上传头像
                tryChangeHeadImg(cropImagePath);

            }
        }
    }

    public void tryChangeHeadImg(String img) {
        String remoteUrl = "headImg/head_" + user_id + "_" + img.substring(img.lastIndexOf("/") + 1, img.lastIndexOf(".")) + ".jpg";
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(bucket, remoteUrl, img);
        try {
            PutObjectResult putResult = oss.putObject(put);
            Log.d("PutObject", "UploadSuccess");
            Log.d("ETag", putResult.getETag());
            Log.d("RequestId", putResult.getRequestId());
        } catch (ClientException e) {
            // 本地异常，如网络异常等。
            e.printStackTrace();
            ossUploadWrong = true;
        } catch (ServiceException e) {
            ossUploadWrong = true;
            // 服务异常。
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        }
        //然后删除之前的图片
        if (userInfo != null && !ossUploadWrong) {
            if (!userInfo.getUser_headimg().contains("/default/head/")) { //防止删除默认图片
                DeleteObjectRequest delete = new DeleteObjectRequest(bucket, userInfo.getUser_headimg());
                try {
                    oss.deleteObject(delete);
                } catch (ClientException e) {
                    e.printStackTrace();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }
        //修改极光的头像
        JMessageClient.updateUserAvatar(new File(img), new BasicCallback() {
            @Override
            public void gotResult(int responseCode, String responseMessage) {
                if (responseCode == 0) {
                    ToastUtil.shortToast(ctx, "更新成功");
                }
            }
        });
        //如果正常上传了就修改数据库
        if (!ossUploadWrong) {
            changeDataBaseUserInfo(1, remoteUrl);
        }

    }

    public void tryChangeCoverImg(String img) {
        String remoteUrl = "background/cover_" + user_id + "_" + img.substring(img.lastIndexOf("/") + 1, img.lastIndexOf(".")) + ".jpg";
        // 构造上传请求。
        PutObjectRequest put = new PutObjectRequest(bucket, remoteUrl, img);
        try {
            PutObjectResult putResult = oss.putObject(put);
            Log.d("PutObject", "UploadSuccess");
            Log.d("ETag", putResult.getETag());
            Log.d("RequestId", putResult.getRequestId());
        } catch (ClientException e) {
            // 本地异常，如网络异常等。
            e.printStackTrace();
            ossUploadWrong = true;
        } catch (ServiceException e) {
            ossUploadWrong = true;
            // 服务异常。
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        }
        //然后删除之前的图片
        if (userInfo != null && !ossUploadWrong) {
            if (!userInfo.getUser_cover_img().contains("/default/cover/")) { //防止删除默认图片
                DeleteObjectRequest delete = new DeleteObjectRequest(bucket, userInfo.getUser_cover_img());
                try {
                    oss.deleteObject(delete);
                } catch (ClientException e) {
                    e.printStackTrace();
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }
        //如果正常上传了就修改数据库
        if (!ossUploadWrong) {
            changeDataBaseUserInfo(0, remoteUrl);
        }

    }

    public void changeDataBaseUserInfo(int type, String remoteUrl) {
        NetUtils netUtils = NetUtils.getInstance();
        String token = ((AppContext) getApplicationContext()).getToken();
        Map map = new HashMap();
        if (type == 1) {
            map.put("user_headimg", remoteUrl);
        } else if (type == 0) {
            map.put("user_cover_img", remoteUrl);
        }
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/changeUserImg", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret = response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(MeActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    String changeState = json.getString("changeState");
                    if (changeState != null && changeState.equals("success")) {
                        if (type == 0)
                            userInfo.setUser_cover_img(remoteUrl);
                        else if (type == 1)
                            userInfo.setUser_headimg(remoteUrl);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (simpleDlg != null)
                                    simpleDlg.dismiss();
                                if (type == 0) { //修改封面
                                   /* String cover = userInfo.getUser_cover_img();
                                    Glide.with(getApplicationContext()).load(url + cover).asBitmap()//签到整体 背景
                                            .into(new SimpleTarget<Bitmap>(winWidth, DipPxUtils.dip2px(getApplicationContext(), 250)) {        //设置宽高
                                                @Override
                                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                                    Drawable drawable = new BitmapDrawable(resource);
                                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                                        ll_top.setBackground(drawable);    //设置背景
                                                    }
                                                }
                                            });*/
                                    refresh();
                                } else if (type == 1) {//修改头像
                                    //更新appcontext的头像
                                    ctx.setHeadImg(remoteUrl);
                                    refresh();
                                    //头像
                                    /*Glide.with(getApplicationContext())
                                            .load(url+userInfo.getUser_headimg())
                                            .transform(new CenterCrop(getApplicationContext()), new GlideRoundTransform(getApplicationContext(), 50))
                                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                                            .dontAnimate()
                                            .crossFade()
                                            .into(iv_head);*/
                                }
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "图片更改失败", Toast.LENGTH_SHORT).show();
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

    public void makeSureFollowByUserName(String userName) {
        NetUtils netUtils = NetUtils.getInstance();
        String token = ((AppContext) getApplicationContext()).getToken();
        Map map = new HashMap();
        //map.put("user_id2", String.valueOf(user_id));
        map.put("user_name2",userName);
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_FRIEND_BASE + "/followed", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret = response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(MeActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    Integer followed = json.getInt("followed");
                    follow = followed; //记录下来
                    if (followed == -1 || followed == 1) { //未关注 或者 取消了关注
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_follow.setVisibility(View.VISIBLE);
                                tv_follow.setText("关注");
                                tv_follow.setTextSize(15);
                                ll_follow.setBackgroundResource(R.drawable.imap_border_getin_island);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_send_news.setVisibility(View.VISIBLE); //如果关注了就显示发送消息
                                ll_follow.setVisibility(View.VISIBLE);
                                tv_follow.setText("取消关注");
                                tv_follow.setTextSize(12);
                                ll_follow.setBackgroundResource(R.drawable.imap_border_getin_island_cancel);
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

    public void makeSureFollow(Integer user_id) {
        NetUtils netUtils = NetUtils.getInstance();
        String token = ((AppContext) getApplicationContext()).getToken();
        Map map = new HashMap();
        map.put("user_id2", String.valueOf(user_id));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_FRIEND_BASE + "/followed", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret = response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(MeActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    Integer followed = json.getInt("followed");
                    follow = followed; //记录下来
                    if (followed == -1 || followed == 1) { //未关注 或者 取消了关注
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_follow.setVisibility(View.VISIBLE);
                                tv_follow.setText("关注");
                                tv_follow.setTextSize(15);
                                ll_follow.setBackgroundResource(R.drawable.imap_border_getin_island);
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_send_news.setVisibility(View.VISIBLE); //如果关注了就显示发送消息
                                ll_follow.setVisibility(View.VISIBLE);
                                tv_follow.setText("取消关注");
                                tv_follow.setTextSize(12);
                                ll_follow.setBackgroundResource(R.drawable.imap_border_getin_island_cancel);
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

    //
    public void tryFollow() throws Exception {
        if (follow == -1 || follow == 1) { //如果未关注 就关注
            NetUtils netUtils = NetUtils.getInstance();
            String token = ((AppContext) getApplicationContext()).getToken();
            Map map = new HashMap();
            map.put("user_id1", ctx.getUserId());
            map.put("user_id2", String.valueOf(user_id));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_FRIEND_BASE + "/FollowUser", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret = response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(MeActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    String FollowUser = json.getString("FollowUser");
                    if (FollowUser != null && FollowUser.equals("success")) {
                        follow = 0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_send_news.setVisibility(View.VISIBLE); //如果关注了就显示发送消息
                                ll_follow.setVisibility(View.VISIBLE);
                                tv_follow.setText("取消关注");
                                tv_follow.setTextColor(Color.parseColor("#bb070609"));
                                tv_follow.setTextSize(12);
                                ll_follow.setBackgroundResource(R.drawable.imap_border_getin_island_cancel);
                            }
                        });
                    }
                }

                @Override
                public void failed(Call call, IOException e) {
                }
            });
        } else { //取消关注
            NetUtils netUtils = NetUtils.getInstance();
            String token = ((AppContext) getApplicationContext()).getToken();
            Map map = new HashMap();
            map.put("user_id1", ctx.getUserId());
            map.put("user_id2", String.valueOf(user_id));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_FRIEND_BASE + "/delFriend", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret = response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(MeActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    String delstate = json.getString("delstate");
                    if (delstate != null && delstate.equals("success")) {
                        follow = 1;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ll_send_news.setVisibility(View.INVISIBLE); //如果取消了关注就隐藏发送消息按钮
                                ll_follow.setVisibility(View.VISIBLE);
                                tv_follow.setText("关注");
                                tv_follow.setTextSize(15);
                                ll_follow.setBackgroundResource(R.drawable.imap_border_getin_island);
                            }
                        });
                    }
                }

                @Override
                public void failed(Call call, IOException e) {
                }
            });
        }
    }

    //菜单点击事件
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.change_head:
                //gallery1();
                uploadHeadImage();
                break;
            case R.id.create_island:
                try {
                    tryCreateIsland();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.settings:
                Toast.makeText(this, "设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.exit:
                showExitAlert();
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void refresh() {
        finish();
        Intent intent = new Intent();
        intent.putExtra("user_id", String.valueOf(user_id));
        intent.setClass(MeActivity.this, MeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void showNormalDialog() {
        AlertDialog normalDialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.warn)
                .setTitle("取关后,不再是好友")
                //.setMessage("test")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            tryFollow();
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

    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        //权限判断
        try {
            if (ContextCompat.checkSelfPermission(MeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                //申请WRITE_EXTERNAL_STORAGE权限
                ActivityCompat.requestPermissions(MeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            } else {
                //跳转到调用系统相机
                gotoPhoto();
            }
        }catch (Exception e){
            ToastUtil.shortToast(context,e.getMessage());
        }
    }

    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        try {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
        }catch (Exception e){
            ToastUtil.shortToast(context,e.getMessage());
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

    //退出登录前
    public void showExitAlert() {
        AlertDialog normalDialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.warn)
                .setTitle("确定退出么？")
                //.setMessage("test")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exit();
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
    //退出登录
    public void exit() {
        Intent intent =new Intent();
        intent.setAction(GlobalConfig.exitApp);
        sendBroadcast(intent);
        ctx.setUsername(null);
        ctx.setPassword(null);
        ctx.setToken(null);
        ctx.setUserId(null);
        ctx.setNickname(null);
        //极光退出
        JMessageClient.logout();
        startActivity(new Intent(MeActivity.this,LoginActivity.class));
        this.finish();
    }
    //尝试创建海岛
    public void tryCreateIsland() throws Exception {
         if(ctx.getUserIsland()!=null&&!"".equals(ctx.getUserIsland())){
             if(ctx.getUserIsland().split("\\|").length>= Integer.parseInt(ctx.getUserIslandLimit())){
                 ToastUtil.shortToast(ctx,"所在海岛数已达上限:"+Integer.parseInt(ctx.getUserIslandLimit()));
             }else{
                 startSelectCategory();
             }
         }else{
             startSelectCategory();
         }
    }
    //跳转
    public void startSelectCategory(){
        Intent intent=new Intent();
        intent.setClass(MeActivity.this, IslandSelectCategoryActivity.class);
        MeActivity.this.finish();
        startActivity(intent);
    }
}
