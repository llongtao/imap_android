package com.test.hyxc.page.island;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.google.gson.JsonParser;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.PagerMainAdapter;
import com.test.hyxc.chat.activity.ChatActivity;
import com.test.hyxc.chat.activity.ForwardMsgActivity;
import com.test.hyxc.chat.activity.ForwardMsgActivity1;
import com.test.hyxc.chat.entity.Event;
import com.test.hyxc.chat.entity.EventType;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.fragment.RecommendFragment;
import com.test.hyxc.fragment.island.IslandShowActFragment;
import com.test.hyxc.fragment.island.IslandShowWorkFragment;
import com.test.hyxc.headimg.ClipImageActivity;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.Island;
import com.test.hyxc.page.activity.ActivityDetailShow;
import com.test.hyxc.page.personal.InformationActivity;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.page.publish.PublishActActivity;
import com.test.hyxc.page.activity.PublishActivity;
import com.test.hyxc.page.workshow.ImageShowActivity;
import com.test.hyxc.ui.GlideRoundTransform;
import com.test.hyxc.view.BigImgDlg;
import com.test.hyxc.view.IslandDescDlg;
import com.test.hyxc.view.SimpleDlg;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetGroupInfoCallback;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.GroupInfo;
import cn.jpush.im.android.eventbus.EventBus;
import cn.jpush.im.api.BasicCallback;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.GlobalConfig;
import tools.LoadingDialog;
import tools.NetUtils;

import static com.test.hyxc.headimg.util.FileUtil.getRealFilePathFromUri;
import static com.test.hyxc.videoeditor.utils.UIUtils.getContext;
import static tools.AppContext.context;

public class IslandShowActivity extends BaseActivity implements View.OnClickListener,PopupMenu.OnMenuItemClickListener {
    public Island island;
    public Integer is_id;
    public Long groupId;
    int winWidth;
    ImageView iv_island;
    IslandDescDlg islandDescDlg;
    LoadingDialog loadingDialog;
    BigImgDlg bigImgDlg;
    ImageView iv_big_head;
    String cropImagePath = "";
    ReceiveBroadCast receiveBroadCast;
    //oss
    private OSS oss;
    private String bucket="yourbucket";
    private  boolean ossUploadWrong=false;
    private final static String imgEndpoint = "yourImgEndpoint";
    Window window;
    TextView tv_island_name,tv_island_people_current,tv_message;
    private final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择 修改背景图
    String url="";
    private List<View> mViewList = new ArrayList<>();
    private TabLayout mTabLayout;
    private View view1, view2;
    private  FloatingActionButton btn_publish;
    private List<String> mTitleList = new ArrayList<String>();
    LinearLayout ll_top,ll_people_current;
    ViewPager f_vp;
    public Integer current_page=0;//默认作品列表
    private List<Fragment> mFragments=new ArrayList<>();
    //用户角色 0=参观者 1：成员 2：管理员 3：群主
    private int role=0;
    int userId=-1;
    private AppContext ctx;
    SimpleDlg simpleDlg;
    TextView tv_change_cover;
    TextView tv_news;
    ///简介弹框的view
    ImageView iv_dlg_head;
    TextView tv_dlg_title,tv_dlg_desc,tv_dlg_people,tv_dlg_category;
    ////////////
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //用户和海岛的关系
    public int relation=-1; //-1 or 0：无关系 1：普通居民 2：管理员 3：岛主
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
        return R.layout.imap_island;
    }
    @Override
    protected void initListener() { }
    @Override
    protected void initView() throws Exception {
        ctx=(AppContext)getApplicationContext();
        loadingDialog=new LoadingDialog(IslandShowActivity.this);
        if(getIntent().hasExtra("island")) {
            island = (Island) getIntent().getSerializableExtra("island");
            is_id=island.getIs_id();
            findIslandById(is_id);
        }else if(getIntent().hasExtra("is_id")) {
            is_id = (Integer) getIntent().getSerializableExtra("is_id");
            //然后请求获取island信息
            findIslandById(is_id);
        }else if(getIntent().hasExtra("groupId")){ //聊天页面传过来
            groupId=(Long)getIntent().getSerializableExtra("groupId");
            //根据groupid找island信息
            findIslandByGroupId(groupId);
        }
        initOSS(imgEndpoint,"LTAIxo60meawp0vN","gwCz2nvKCS9qU5FYdfZSW7xO1UgVLy");
        userId=Integer.parseInt(((AppContext)getApplicationContext()).getUserId());
        url=((AppContext)getApplicationContext()).getOssConfig().getHost()+"/";
        WindowManager wm = (WindowManager) getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        ll_top=f(R.id.ll_top);
        ll_top.setOnClickListener(this);
        winWidth=wm.getDefaultDisplay().getWidth();
        iv_island=f(R.id.iv_island);
        iv_island.setOnClickListener(this);
        tv_island_name=f(R.id.tv_island_name);
        tv_island_people_current=f(R.id.tv_island_people_current);
        tv_message=f(R.id.tv_message);
        btn_publish=f(R.id.btn_publish);
        btn_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tryPublishWorkOrAct();
            }
        });
        //填充界面
        fillUI(island);
        ll_people_current=f(R.id.ll_people_current);
        ll_people_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("island",island);
                intent.setClass(IslandShowActivity.this,IslandUsersActivity.class);
                startActivity(intent);
            }
        });
        //进入聊天
        tv_news=f(R.id.tv_news);
        tv_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Conversation groupConversation = JMessageClient.getGroupConversation(island.getIs_im_id().longValue());
                if (groupConversation == null) {
                    groupConversation = Conversation.createGroupConversation(island.getIs_im_id().longValue());
                    EventBus.getDefault().post(new Event.Builder()
                            .setType(EventType.createConversation)
                            .setConversation(groupConversation)
                            .build());
                }
                Intent intent = new Intent(IslandShowActivity.this, ChatActivity.class);
                intent.putExtra(AppContext.CONV_TITLE, island.getIs_name());
                intent.putExtra(AppContext.GROUP_ID,island.getIs_im_id().longValue());
                startActivity(intent);
                finish();
            }
        });
        //注册广播
        receiveBroadCast=new ReceiveBroadCast();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(GlobalConfig.islandState);
        registerReceiver(receiveBroadCast,intentFilter);
        //initTabTitle();
    }

    @Override
    protected void initData() {}
    ///请求海岛信息
    private void requireIslandMess() throws Exception {
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/findUserInfoById",token, map, new NetUtils.MyNetCall() {
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
                if (json.has("user_info") && json.get("user_info")!=null) {
                     runOnUiThread(new Runnable() {
                         @Override
                         public void run() {
                             JSONObject obj=null;
                             try{
                                 obj=json.getJSONObject("user_info");
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }
                         }
                     });
                }
                if(json.has("followMeCount")&&json.get("followMeCount")!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int followmecount=-1;
                            try{
                                followmecount=json.getInt("followMeCount");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
                if(json.has("workCount")&&json.get("workCount")!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int workCount=-1;

                        }
                    });
                }

            }
            @Override
            public void failed(Call call, IOException e) {}
        });
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
                String selectImg= cursor.getString(column_index);
                if(selectImg!=null&&!"".equals(selectImg)){
                    tryChangeCoverImg(selectImg);
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
                //此处后面可以将bitMap转为二进制上传后台网络
                //上传头像
                tryChangeHeadImg(cropImagePath);

            }
        }
    }
    public void initTabTitle(){
        mTitleList.add("动态");
        mTitleList.add("活动");
        mTabLayout =  findViewById(R.id.tabs);
        view1 = tab_icon(mTitleList.get(0));
        view2 = tab_icon(mTitleList.get(1));
        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        //添加tab选项卡，默认第一个选中
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view1),true);
        mTabLayout.addTab( mTabLayout.newTab().setCustomView(view2));
        ((TextView)mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#c0a8ff"));
        ((TextView)mTabLayout.getTabAt(0).getCustomView().findViewById(R.id.tabtext)).setTextSize(18);
        ((TextView)mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#73747a"));
        ((TextView)mTabLayout.getTabAt(1).getCustomView().findViewById(R.id.tabtext)).setTextSize(16);
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                for(int i =0;i<mViewList.size();i++){
                    View v= mTabLayout.getTabAt(i).getCustomView();
                    ((TextView)v.findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#73747a"));
                    ((TextView)v.findViewById(R.id.tabtext)).setTextSize(16);
                }
                ((TextView)tab.getCustomView().findViewById(R.id.tabtext)).setTextSize(18);
                ((TextView)tab.getCustomView().findViewById(R.id.tabtext)).setTextColor(Color.parseColor("#c0a8ff"));
                //联动
                f_vp.setCurrentItem(tab.getPosition(),true);
                current_page=tab.getPosition();//记录当前的页面是作品列表还是活动列表
                //发布按钮是否显示
                showOrNotActionButton();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        //////
        f_vp=f(R.id.f_vp);
            IslandShowWorkFragment fragment=new IslandShowWorkFragment();
            IslandShowActFragment fragment1=new IslandShowActFragment();
            mFragments.add(fragment);
            mFragments.add(fragment1);

        // 设置填充器
        f_vp.setAdapter(new PagerMainAdapter(getSupportFragmentManager(),mFragments));
        // 设置缓存页面数
        f_vp.setOffscreenPageLimit(2);
        //ViewPager的改变事件 vp-rg互相监听：vp
        f_vp.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

    }
    //tabbar
    private View tab_icon(String name){
        View newtab =  LayoutInflater.from(this).inflate(R.layout.tab_item,null);
        TextView tv = newtab.findViewById(R.id.tabtext);
        tv.setText(name);
        return newtab;
    }
    //数量处理
    public String dealNumber(Integer number){
        if(number==null)
            return "";
        if(number>1000)
            return number/1000+"k";
        else if(number>10000)
            return number/10000+"万";
        return number+"";
    }
     //发布作品或者活动
    public void tryPublishWorkOrAct(){
         switch (current_page){
             case 0: //作品
                 if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                     Intent intent=new Intent(IslandShowActivity.this, InformationActivity.class);
                     startActivity(intent);
                 }else {
                     Intent intent = new Intent();
                     intent.putExtra("user_id", ctx.getUserId());
                     intent.putExtra("defineIsland",is_id);
                     intent.putExtra("defineIslandName",island.getIs_name());
                     intent.putExtra("defineIslandLogo",island.getIs_logo());
                     intent.putExtra("defineIslandCategoryId",island.getIs_category_id());
                     intent.putExtra("defineIslandCategoryName",island.getIs_category());
                     intent.putExtra("defineIslandCategoryParentId",island.getIs_parent_id());
                     intent.putExtra("defineIslandCategoryParentName",island.getIs_category_parent());
                     intent.setClass(IslandShowActivity.this, PublishActivity.class);
                     startActivity(intent);
                     overridePendingTransition(0,0);
                 }
                 break;
             case 1: //活动
                 if(ctx.getNickname()==null||"".equals(ctx.getNickname())){
                     Intent intent=new Intent(IslandShowActivity.this, InformationActivity.class);
                     startActivity(intent);
                 }else {
                     Intent intent = new Intent();
                     intent.putExtra("user_id", ctx.getUserId());
                     intent.putExtra("defineIsland",is_id);
                     intent.putExtra("defineIslandName",island.getIs_name());
                     intent.putExtra("defineIslandLogo",island.getIs_logo());
                     intent.putExtra("defineIslandCategoryId",island.getIs_category_id());
                     intent.putExtra("defineIslandCategoryName",island.getIs_category());
                     intent.putExtra("defineIslandCategoryParentId",island.getIs_parent_id());
                     intent.putExtra("defineIslandCategoryParentName",island.getIs_category_parent());
                     intent.putExtra("defineIslandUniId",island.getIs_parent_id());
                     intent.putExtra("defineIslandUniName",island.getIs_parent_name());
                     intent.setClass(IslandShowActivity.this, PublishActActivity.class);
                     startActivity(intent);
                     overridePendingTransition(0,0);
                 }
                 break;
             default:
                 break;
         }
    }
    //查找海岛信息
    public void findIslandById(Integer is_id){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/findIslandById",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException{
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(IslandShowActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    JSONObject  jsonObject=json.getJSONObject("island");
                    final Gson gson = new Gson();
                    JsonParser parser = new JsonParser();
                    if(jsonObject!=null) {
                        island = gson.fromJson(jsonObject.toString(), Island.class);
                        //填充界面信息
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fillUI(island);
                            }
                        });
                    }
                    //关系 用户跟海岛的关系 0：无关 1：居民 2：管理员 3：岛主
                    Integer i=json.getInt("relation");
                    relation=i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initTabTitle();
                        }
                    });
                }
                @Override
                public void failed(Call call, IOException e) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //////////
    //查找海岛信息
    public void findIslandByGroupId(Long  groupId){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("groupId",String.valueOf(groupId));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/findIslandById",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException{
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(IslandShowActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    JSONObject  jsonObject=json.getJSONObject("island");
                    final Gson gson = new Gson();
                    JsonParser parser = new JsonParser();
                    if(jsonObject!=null) {
                        island = gson.fromJson(jsonObject.toString(), Island.class);
                        is_id=island.getIs_id(); ///这里一定要调用一下
                        //填充界面信息
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                fillUI(island);
                            }
                        });
                    }
                    //关系 用户跟海岛的关系 0：无关 1：居民 2：管理员 3：岛主
                    Integer i=json.getInt("relation");
                    relation=i;
                    //填充fragment
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initTabTitle();
                        }
                    });
                }
                @Override
                public void failed(Call call, IOException e) {}
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillUI(Island island){
        if(island!=null) {
            Glide.with(this).load(url + island.getIs_img()).asBitmap()//签到整体 背景
                    .into(new SimpleTarget<Bitmap>() {        //设置宽高
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            Drawable drawable = new BitmapDrawable(resource);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                ll_top.setBackground(drawable);    //设置背景
                            }
                        }
                    });
        }
        if(island!=null) {
            Glide.with(getApplicationContext())
                    .load(url + island.getIs_logo())
                    .transform(new CenterCrop(getApplicationContext()), new GlideRoundTransform(getApplicationContext()))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(iv_island);
            tv_island_name.setText(island.getIs_name());
            tv_island_people_current.setText(String.valueOf(island.getIs_people_current() == null ? 0 : island.getIs_people_current()));
            String mess = "粉丝：" + dealNumber(island.getIs_follow_count()) + " ";
            mess = mess + "作品：" + dealNumber(island.getIs_work_count()) + " ";
            mess = mess + "点赞：" + dealNumber(island.getIs_liked_count());
            tv_message.setText(mess);
        }
        //发布按钮是否显示
        showOrNotActionButton();
    }

    public void tryShowDescDlg(){
        if(islandDescDlg==null)
            islandDescDlg=new IslandDescDlg(IslandShowActivity.this,R.style.SimpleDlg);
        islandDescDlg.show();
        window = islandDescDlg.getWindow();
        iv_dlg_head = window.findViewById(R.id.iv_dlg_head);
        tv_dlg_title=window.findViewById(R.id.tv_dlg_title);
        tv_dlg_category=window.findViewById(R.id.tv_dlg_category);
        tv_dlg_desc=window.findViewById(R.id.tv_dlg_desc);
        tv_dlg_people=window.findViewById(R.id.tv_dlg_people);
        //头像
        String headImg=url+ GlobalConfig.defaultHeadImg;
        if(island!=null) {
            headImg = url + island.getIs_logo();
            Glide.with(getApplicationContext())
                    .load(headImg)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate()
                    .crossFade()
                    .into(iv_dlg_head);
            tv_dlg_title.setText(island.getIs_name()==null?"":island.getIs_name());
            tv_dlg_desc.setText(island.getIs_describe()==null?"":island.getIs_describe());
            tv_dlg_category.setText(island.getIs_category()==null?"":island.getIs_category());
            tv_dlg_people.setText(island.getIs_people_current()==null?"":""+island.getIs_people_current());
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
    public void tryChangeCoverImg(String img){
        String remoteUrl="background/cover_island_"+is_id+"_"+ img.substring(img.lastIndexOf("/")+1,img.lastIndexOf("."))+".jpg";
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
            ossUploadWrong=true;
        } catch (ServiceException e) {
            ossUploadWrong=true;
            // 服务异常。
            Log.e("RequestId", e.getRequestId());
            Log.e("ErrorCode", e.getErrorCode());
            Log.e("HostId", e.getHostId());
            Log.e("RawMessage", e.getRawMessage());
        }
        //然后删除之前的图片
        if(island!=null&&!ossUploadWrong){
            if(!island.getIs_img().contains("/default/cover/")) { //防止删除默认图片
                DeleteObjectRequest delete = new DeleteObjectRequest(bucket, island.getIs_img());
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
        if(!ossUploadWrong){
            changeDataBaseIsland(0,remoteUrl);
        }
    }
    public void changeDataBaseIsland(int type,String remoteUrl){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        if(type==1){
            map.put("is_img",remoteUrl);
        }else if(type==0){
            map.put("is_img",remoteUrl);
        }
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/changIsByCondition",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException{
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(IslandShowActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    String changeState=json.getString("changIsByCondition");
                    if (changeState!=null&&changeState.equals("success")) {
                        if(type==0)
                            island.setIs_img(remoteUrl);
                        else if(type==1)
                            island.setIs_img(remoteUrl);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(simpleDlg!=null)
                                    simpleDlg.dismiss();
                                if(type==0) { //修改封面
                                    refresh();
                                }else if(type==1){//修改头像
                                    //更新appcontext的头像
                                    ctx.setHeadImg(remoteUrl);
                                    refresh();
                                }
                            }
                        });
                    }else {
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
    ///****头像的点击事件 //这里注意是否是自己 要做区分
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.iv_island:
                if(relation==3){ //岛主
                    PopupMenu popup = new PopupMenu(this, v);//第二个参数是绑定的那个view
                    //获取菜单填充器
                    MenuInflater inflater = popup.getMenuInflater();
                    //填充菜单
                    inflater.inflate(R.menu.menu_island_3, popup.getMenu());
                    //绑定菜单项的点击事件
                    popup.setOnMenuItemClickListener(this);
                    //显示(这一行代码不要忘记了)
                    popup.show();
                }else if (relation==2) {  //管理员 可以显示设置 和更换头像
                    PopupMenu popup = new PopupMenu(this, v);
                    //获取菜单填充器
                    MenuInflater inflater = popup.getMenuInflater();
                    //填充菜单
                    inflater.inflate(R.menu.menu_island_2, popup.getMenu());
                    //绑定菜单项的点击事件
                    popup.setOnMenuItemClickListener(this);
                    //显示(这一行代码不要忘记了)
                    popup.show();
                } else if(relation==1){ //普通用户
                    PopupMenu popup = new PopupMenu(this, v);
                    //获取菜单填充器
                    MenuInflater inflater = popup.getMenuInflater();
                    //填充菜单
                    inflater.inflate(R.menu.menu_island_1, popup.getMenu());
                    //动态添加按钮选项@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2
                    if(island.getIs_ask_allow()==0 || island.getIs_ask_allow()==2) {
                        //popup.getMenu().add(Menu.NONE, Menu.FIRST, 111111, "邀请好友1");
                        popup.getMenu().add(Menu.NONE,-111111,111111,"邀请好友");
                    }
                    popup.setOnMenuItemClickListener(this);
                    popup.show();
                }else if(relation ==0 || relation ==-1) { //游客
                    PopupMenu popup = new PopupMenu(this, v);
                    //获取菜单填充器
                    MenuInflater inflater = popup.getMenuInflater();
                    //填充菜单
                    inflater.inflate(R.menu.menu_island, popup.getMenu());
                    //动态添加按钮选项@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2
                    if(island.getIs_join_allow() != null && island.getIs_join_allow()==0 || island.getIs_join_allow()==2) {
                        popup.getMenu().add(Menu.NONE,-222222,222222,"申请加入");
                    }
                    popup.setOnMenuItemClickListener(this);
                    popup.show();
                }
                break;
            case R.id.ll_top:
                //更换封面 //只有岛主能改
                if(relation==3){
                    simpleDlg=new SimpleDlg(IslandShowActivity.this,R.style.SimpleDlg);
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
                break;
            default:
                break;
        }

    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.change_head: //修改头像
                uploadHeadImage();
                break;
            case R.id.change_island: //修改简介
                Intent intent =new Intent();
                intent.setClass(IslandShowActivity.this,IslandChangeActivity.class);
                intent.putExtra("island",island);
                startActivity(intent);
                //tryShowDescDlg();
                break;
            case R.id.settings:
                Intent intent1 = new Intent();
                intent1.setClass(IslandShowActivity.this,IslandSettingsActivity.class);
                intent1.putExtra("island",island);
                startActivity(intent1);
                break;
            case R.id.ask :
                askFriend();
                break;
            case R.id.see_head: //查看头像
                seeIslandLogoBig();
                break;
            case R.id.see_desc:// 查看简介
                tryShowDescDlg();
                break;
            case -111111: //自定义的邀请好友
                askFriend();
                break;
            case -222222: //自定义 申请加入按钮
                joinInIsland();
                break;
            case R.id.leave: //离开海岛
                showNormalDialog();
                break;
            default:
                break;
        }
        return false;
    }
    //申请加入
    public void joinInIsland(){
        if(island.getIs_join_allow()==0){ //直接加入
            joinStraight();
        }else if(island.getIs_join_allow()==1){ //不允许加入
           ToastUtil.shortToast(IslandShowActivity.this,"当前海岛不允许加入~~");
        }else if(island.getIs_join_allow()==2){ //需要验证
            joinWaitVerify();
        }
    }

    public void askFriend(){
        Intent i = new Intent(IslandShowActivity.this, ForwardMsgActivity1.class);
        i.setFlags(2);
        i.putExtra("useType","shareIsland");
        i.putExtra("island",island);
        AppContext.forwardMsg.clear();
        //AppContext.forwardMsg.add(msg);
        startActivity(i);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void refresh() {
        finish();
        Intent intent = new Intent();
        intent.putExtra("island", island);
        intent.setClass(IslandShowActivity.this, IslandShowActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }
    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        //权限判断
        if (ContextCompat.checkSelfPermission(IslandShowActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(IslandShowActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        } else {
            //跳转到调用系统相机
            gotoPhoto();
        }
    }
    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
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
    public void tryChangeHeadImg(String img) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingDialog.show();
            }
        });
        String remoteUrl = "headImgIsland/head_island_" + island.getIs_id() + "_" + img.substring(img.lastIndexOf("/") + 1, img.lastIndexOf(".")) + ".jpg";
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
        //修改极光的头像
        if(!ossUploadWrong) {
            JMessageClient.getGroupInfo(island.getIs_im_id().longValue(), new GetGroupInfoCallback() {
                @Override
                public void gotResult(int i, String s, GroupInfo groupInfo) {
                    if (i == 0) {
                        groupInfo.updateAvatar(new File(img), "jpg", new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                if (i == 0) {
                                    //修改logo图片
                                    Bitmap bitMap = BitmapFactory.decodeFile(img);
                                    iv_island.setImageBitmap(bitMap);
                                    //然后修改数据库
                                    tryChangeDatabase(remoteUrl,island.getIs_id());
                                }else{
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(loadingDialog!=null)
                                                loadingDialog.dismiss();
                                            ToastUtil.shortToast(IslandShowActivity.this,"更改头像失败~");
                                        }
                                    });
                                }
                            }
                        });
                    } else {
                         runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 if(loadingDialog!=null)
                                     loadingDialog.dismiss();
                                 ToastUtil.shortToast(IslandShowActivity.this,"通信系统异常~");
                                 //然后删除刚刚上传的图片
                                 DeleteObjectRequest delete = new DeleteObjectRequest(bucket,remoteUrl);
                                 try {
                                     oss.deleteObject(delete);
                                 } catch (ClientException e) {
                                     e.printStackTrace();
                                 } catch (ServiceException e) {
                                     e.printStackTrace();
                                 }
                             }
                         });
                    }
                }
            });
        }else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(loadingDialog!=null)
                        loadingDialog.dismiss();
                    ToastUtil.shortToast(IslandShowActivity.this, "上传头像异常~");
                }
            });
        }
    }
    public void tryChangeDatabase(String remoteUrl,Integer is_id){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        map.put("is_logo",remoteUrl);
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/changIsByCondition",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException{
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(IslandShowActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    String changeState=json.getString("changIsByCondition");
                    if (changeState!=null&&changeState.equals("success")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //然后删除之前的图片信息
                                if(loadingDialog!=null)
                                    loadingDialog.dismiss();
                            }
                        });
                        if (!island.getIs_logo().contains("default/island")&&!island.getIs_logo().contains("default/head")) { //防止删除默认图片
                            DeleteObjectRequest delete = new DeleteObjectRequest(bucket,island.getIs_logo());
                            try {
                                oss.deleteObject(delete);
                            } catch (ClientException e) {
                                e.printStackTrace();
                            } catch (ServiceException e) {
                                e.printStackTrace();
                            }
                        }
                        refresh();
                    }else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(loadingDialog!=null)
                                    loadingDialog.dismiss();
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
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(loadingDialog!=null)
                        loadingDialog.dismiss();
                }
            });
            e.printStackTrace();
        }
    }

    public void showOrNotActionButton(){
        //发布按钮是否显示
        if(current_page==0){ //动态页面 如果是岛内成员 就要显示 可以发布内容
            if(relation==1||relation == 2 || relation ==3)
                btn_publish.setVisibility(View.VISIBLE);
            else
                btn_publish.setVisibility(View.GONE);

        }else if (current_page ==1){ //活动页面
            if(relation ==2 || relation ==3 )
                btn_publish.setVisibility(View.VISIBLE);
            else
                btn_publish.setVisibility(View.GONE);
        }
    }

    class ReceiveBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //得到广播中得到的数据  
            Integer is_id = intent.getExtras().getInt("is_id");
            Integer is_join_allow=null,is_ask_allow=null;
            if(intent.hasExtra("is_join_allow"))
                is_join_allow=intent.getExtras().getInt("is_join_allow");
            if(intent.hasExtra("is_ask_allow"))
                is_ask_allow=intent.getExtras().getInt("is_ask_allow");
            if(island.getIs_id().equals(is_id)){
               if(is_join_allow!=null){
                   island.setIs_join_allow(is_join_allow);
               }
               if(is_ask_allow  !=null){
                   island.setIs_ask_allow(is_ask_allow);
               }
            }
        }
    }

    public void seeIslandLogoBig(){
        if (bigImgDlg == null)
            bigImgDlg = new BigImgDlg(IslandShowActivity.this, R.style.SimpleDlg);
        bigImgDlg.show();
        window = bigImgDlg.getWindow();
        iv_big_head = window.findViewById(R.id.iv_big_head);
        //头像
        String headImg = url + GlobalConfig.defaultHeadImg;
        if (island.getIs_logo()!=null && !"".equals(island.getIs_logo())) {
            headImg = url + island.getIs_logo();
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

    //直接加入
    public void joinStraight(){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(island.getIs_id()));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/joinIslandStraight",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException{
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(IslandShowActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    String joinIslandStraight=json.getString("joinIslandStraight");
                    String state = json.getString("state");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(joinIslandStraight!=null && joinIslandStraight.equals("success")){
                                String userIsland =((AppContext)getApplicationContext()).getUserIsland();
                                if(userIsland==null)
                                    userIsland="";
                                if("".equals(userIsland)){
                                    userIsland=""+island.getIs_id();
                                }else{
                                    userIsland = userIsland + "|" + island.getIs_id();
                                }
                                ((AppContext)getApplicationContext()).setUserIsland(userIsland); //本地缓存要更新
                                ToastUtil.shortToast(IslandShowActivity.this,"恭喜成功加入海岛~");
                            }else if(joinIslandStraight!=null && joinIslandStraight.equals("fail")){
                                if(state!=null && state.equals("alreadyIn")){
                                    ToastUtil.shortToast(IslandShowActivity.this,"您已经是海岛成员了，无需再次加入~");
                                }else if(state!=null && state.equals("applying")){
                                    ToastUtil.shortToast(IslandShowActivity.this,"您在申请中，请耐心等待审核结果~");
                                }else if(state!=null && state.equals("removed")){
                                    ToastUtil.shortToast(IslandShowActivity.this,"您已被移除海岛，无法再次加入~");
                                }else {
                                    ToastUtil.shortToast(IslandShowActivity.this, "抱歉，加入海岛出现异常");
                                }
                            }
                        }
                    });
                }
                @Override
                public void failed(Call call, IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.shortToast(IslandShowActivity.this,"抱歉，网络异常~~");
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //申请 等待审核
    public void joinWaitVerify(){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(island.getIs_id()));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/joinIslandStraight",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException{
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(IslandShowActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    String joinIslandStraight=json.getString("joinIslandStraight");
                    String state = json.getString("state");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(joinIslandStraight!=null && joinIslandStraight.equals("success")){
                                String userIsland =((AppContext)getApplicationContext()).getUserIsland();
                                if(userIsland==null)
                                    userIsland="";
                                if("".equals(userIsland)){
                                    userIsland=""+island.getIs_id();
                                }else{
                                    userIsland = userIsland + "|" + island.getIs_id();
                                }
                                ((AppContext)getApplicationContext()).setUserIsland(userIsland); //本地缓存要更新
                                ToastUtil.shortToast(IslandShowActivity.this,"恭喜成功加入海岛~");
                            }else if(joinIslandStraight!=null && joinIslandStraight.equals("fail")){
                                if(state!=null && state.equals("alreadyIn")){
                                    ToastUtil.shortToast(IslandShowActivity.this,"您已经是海岛成员了，无需再次加入~");
                                }else if(state!=null && state.equals("applying")){
                                    ToastUtil.shortToast(IslandShowActivity.this,"您在申请中，请耐心等待审核结果~");
                                }else if(state!=null && state.equals("removed")){
                                    ToastUtil.shortToast(IslandShowActivity.this,"您已被移除海岛，无法再次加入~");
                                }else {
                                    ToastUtil.shortToast(IslandShowActivity.this, "抱歉，加入海岛出现异常");
                                }
                            }
                        }
                    });
                }
                @Override
                public void failed(Call call, IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.shortToast(IslandShowActivity.this,"抱歉，网络异常~~");
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showNormalDialog(){
        AlertDialog normalDialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.warn)
                .setTitle("确定要退出海岛么？")
                //.setMessage("test")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        leaveIsland();
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
    //退出海岛
    public void leaveIsland(){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(island.getIs_id()));
        map.put("user_id",String.valueOf(userId));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/leaveIsland",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret = response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(IslandShowActivity.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    if (json.has("leaveIsland") && json.get("leaveIsland").equals("success")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.shortToast(getApplicationContext(), "成功离开海岛!");
                                //本地缓存 userIsland 修改
                                String userIsland = ((AppContext)getApplicationContext()).getUserIsland();
                                String uiss[] = userIsland.split("\\|");
                                String userIslandNew = "";
                                for(int i =0;i<uiss.length;i++){
                                    if(uiss[i]!=null && !"".equals(uiss[i])){
                                        if(!island.getIs_id().equals(Integer.parseInt(uiss[i]))){
                                            userIslandNew  = userIslandNew + uiss[i] + '|';
                                        }
                                    }
                                }
                                if(userIslandNew.endsWith("\\|")){
                                    userIslandNew =userIslandNew.substring(0,userIslandNew.length()-1);
                                }
                                ((AppContext)getApplicationContext()).setUserIsland(userIslandNew);
                                finish();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ToastUtil.shortToast(getApplicationContext(),"后台异常~");
                                finish();
                            }
                        });
                    }
                }
                @Override
                public void failed(Call call, IOException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.shortToast(IslandShowActivity.this,"抱歉，网络异常~~");
                        }
                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiveBroadCast);
    }

}
