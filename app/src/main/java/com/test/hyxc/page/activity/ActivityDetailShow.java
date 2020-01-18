package com.test.hyxc.page.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.chat.activity.ForwardMsgActivity;
import com.test.hyxc.chat.utils.ToastUtil;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.ActivityShow;
import com.test.hyxc.model.Island;
import com.test.hyxc.model.IslandResident;
import com.test.hyxc.model.UserInfo;
import com.test.hyxc.page.island.IslandShowActivity;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.page.publish.PublishActActivity;
import com.test.hyxc.page.workshow.ImageShowActivity;
import com.test.hyxc.transformations.MyRoundCornersTransformation;
import com.test.hyxc.ui.GlideRoundTransform1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.NetUtils;
import tools.SystemHelper;

public class ActivityDetailShow extends BaseActivity implements PopupMenu.OnMenuItemClickListener {
    Integer act_sign=0;
    private ActivityShow activityShow;
    String url="";
    LinearLayout ll_back,ll_sign_count;
    ImageView iv_cover;
    TextView tv_act_title,tv_act_time,tv_island_name,tv_linkman,tv_detail,tv_act_address,tv_sign_count;
    ImageView iv_island_logo,iv_linkman;
    Button btn_sign;TextView tv_more;
    Island is=new Island();
    UserInfo linkman = new UserInfo();
    public int relation=-1; //-1 or 0：无关系 1：普通居民 2：管理员 3：岛主
    int act_id = -1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_activity_show;
    }

    @Override
    protected void initListener() {}

    @Override
    protected void initView() throws Exception {
        url = ((AppContext)getApplicationContext()).getOssConfig().getHost()+"/";
        ////控件
        ll_back= f(R.id.ll_back);
        iv_cover=f(R.id.iv_cover);
        tv_act_title=f(R.id.tv_act_title);
        tv_linkman=f(R.id.tv_linkman);
        iv_linkman=f(R.id.iv_linkman);
        tv_act_time = f(R.id.tv_act_time);
        tv_island_name = f(R.id.tv_island_name);
        iv_island_logo = f(R.id.iv_island_logo);
        tv_detail=f(R.id.tv_detail);
        tv_act_address=f(R.id.tv_act_address);
        tv_sign_count = f(R.id.tv_sign_count);
        ll_sign_count=f(R.id.ll_sign_count);
        btn_sign = f(R.id.btn_sign);
        tv_more = f(R.id.tv_more);
        tv_more.setVisibility(View.GONE);
        if(getIntent().hasExtra("act")) {
            activityShow = (ActivityShow) getIntent().getSerializableExtra("act");
            act_sign=activityShow.getSign();
            fillUI(activityShow);
        }else if(getIntent().hasExtra("act_id")){
            act_id = getIntent().getIntExtra("act_id",0);
            findActById(act_id);
        }
    }

    public void fillUI(ActivityShow activityShow){
        if(activityShow.getResourcesList()!=null && activityShow.getResourcesList().size()>0) {
            Glide.with(getApplicationContext())
                    .load(url + activityShow.getResourcesList().get(0).getAct_resource_url())
                    .placeholder(R.mipmap.error)
                    .error(R.mipmap.error)
                    .centerCrop()
                    //默认淡入淡出动画
                    .crossFade()
                    //缓存策略,跳过内存缓存【此处应该设置为false，否则列表刷新时会闪一下】
                    .skipMemoryCache(false)
                    //缓存策略,硬盘缓存-仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                    .diskCacheStrategy(DiskCacheStrategy.RESULT)
                    //设置图片加载的优先级
                    .priority(Priority.HIGH)
                    .transform(new MyRoundCornersTransformation(getApplicationContext(), SystemHelper.dip2px(getApplicationContext(), 4),
                            MyRoundCornersTransformation.CornerType.TOP))
                    .into(iv_cover);
        }
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_island_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("island",is);
                intent.setClass(ActivityDetailShow.this, IslandShowActivity.class);
                startActivity(intent);
                finish();
            }
        });
        tv_island_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("island",is);
                intent.setClass(ActivityDetailShow.this, IslandShowActivity.class);
                startActivity(intent);
            }
        });
        tv_linkman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("user_id", String.valueOf(linkman.getUser_id()));
                intent.setClass(ActivityDetailShow.this, MeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        iv_linkman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("user_id", String.valueOf(linkman.getUser_id()));
                intent.setClass(ActivityDetailShow.this, MeActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
        tv_act_title.setText(String.valueOf(activityShow.getAct_title()));
        tv_act_address.setText(activityShow.getAct_city()+"/"+activityShow.getAct_district()+"/"+activityShow.getAct_address());
        //时间显示
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd HH:mm");
        Date date = new Date(activityShow.getAct_time().getTime());
        String startStr = sdf.format(date);
        Date date1 = new Date(activityShow.getAct_end_time().getTime());
        String endStr = sdf.format(date1);
        tv_act_time.setText(startStr + "   --   " + endStr);
        //海岛信息
        tryGetIslandMess(activityShow.getAct_island());
        //查询联系人信息
        tryGetLinkman(activityShow.getAct_linkman());
        tv_detail.setText(activityShow.getAct_text());
        tv_sign_count.setText((activityShow.getAct_people_count()==null?0:activityShow.getAct_people_count())+"人");
        ll_sign_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.putExtra("activityShow",activityShow);
                intent.setClass(ActivityDetailShow.this,ActSignUsersActivity.class);
                startActivity(intent);
            }
        });
        //判断时间
        long start = activityShow.getAct_time().getTime();
        long end =activityShow.getAct_end_time().getTime();
        if(System.currentTimeMillis()<start){
            if(act_sign == 0 || act_sign==null || act_sign.equals(0)){ //未开始且未报名
                btn_sign.setText("报名");
            }else{
                btn_sign.setText("取消报名");
            }
        }else if(System.currentTimeMillis()>=start&&System.currentTimeMillis()<end){
            btn_sign.setText("进行中");
            btn_sign.setBackgroundResource(R.drawable.imap_publish_button_1);
        }else if(System.currentTimeMillis()>=end){
            btn_sign.setText("已结束");
            btn_sign.setBackgroundResource(R.drawable.imap_publish_button_1);
        }
        //报名接口
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_sign.getText().toString().equals("报名")) {
                    try {
                        trySignAct(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }else if(btn_sign.getText().toString().equals("取消报名")) {
                    try {
                        trySignAct(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        makeSureRelation(activityShow);
    }

    @Override
    protected void initData() {}
    public void tryGetIslandMess(int is_id){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("is_id",String.valueOf(is_id));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/findIslandById",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException {
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(ActivityDetailShow.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    final Gson gson = new Gson();
                    JSONObject island=json.getJSONObject("island");
                    if(island!=null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                is = gson.fromJson(island.toString(),Island.class);
                                tv_island_name.setText(is.getIs_name());
                                //补充activityshow的信息
                                activityShow.setAct_island_name(is.getIs_name());
                                activityShow.setAct_island_logo(is.getIs_logo());
                                Glide.with(getApplicationContext())
                                        .load(url+is.getIs_logo())
                                        .placeholder(R.mipmap.error)
                                        .transform(new GlideRoundTransform1(getApplicationContext(),30))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .dontAnimate()
                                        .crossFade()
                                        .into(iv_island_logo);
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
    public void tryGetLinkman(int linkmanId){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("user_id",String.valueOf(linkmanId));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_USER_INFO + "/findUserInfoById",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException {
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(ActivityDetailShow.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    final Gson gson = new Gson();
                    JSONObject user_info=json.getJSONObject("user_info");
                    if(user_info!=null) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                linkman = gson.fromJson(user_info.toString(),UserInfo.class);
                                tv_linkman.setText(linkman.getUser_nickname());
                                Glide.with(getApplicationContext())
                                        .load(url+linkman.getUser_headimg())
                                        .placeholder(R.mipmap.error)
                                        .transform(new GlideRoundTransform1(getApplicationContext(),30))
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                                        .dontAnimate()
                                        .crossFade()
                                        .into(iv_linkman);
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
    //尝试报名
    public void trySignAct(int type) throws Exception {
        if(type ==0) { //报名
            NetUtils netUtils = NetUtils.getInstance();
            String token = ((AppContext) getApplicationContext()).getToken();
            Map map = new HashMap();
            map.put("act_id", String.valueOf(activityShow.getAct_id()));
            map.put("user_id", ((AppContext) getApplicationContext()).getUserId());
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_USER_BASE + "/addOne", token, map, new NetUtils.MyNetCall() {
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
                    final Gson gson = new Gson();
                    String state = json.getString("addActUserState");
                    if (state.equals("success")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                 btn_sign.setText("取消报名");
                                activityShow.setAct_people_count(activityShow.getAct_people_count()+1);
                                //已报名人数+1
                                tv_sign_count.setText(activityShow.getAct_people_count()+"人");
                            }
                        });
                    }else{ //报名失败
                        if(json.has("reason")&& json.getString("reason").equals("signed")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast toast=Toast.makeText(getApplicationContext(),"已经报名过了~",Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.TOP|Gravity.CENTER,0,360);
                                    toast.show();
                                }
                            });
                        }
                    }
                }
                @Override
                public void failed(Call call, IOException e) {
                }
            });
        }else if (type ==1){ //取消报名
            NetUtils netUtils = NetUtils.getInstance();
            String token = ((AppContext) getApplicationContext()).getToken();
            Map map = new HashMap();
            map.put("act_id", String.valueOf(activityShow.getAct_id()));
            map.put("user_id", ((AppContext) getApplicationContext()).getUserId());
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_USER_BASE + "/delOne", token, map, new NetUtils.MyNetCall() {
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
                    final Gson gson = new Gson();
                    String state = json.getString("delActUserState");
                    if (state.equals("success")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                btn_sign.setText("报名");
                                activityShow.setAct_people_count(activityShow.getAct_people_count()-1);
                                //已报名人数-1
                                tv_sign_count.setText(activityShow.getAct_people_count()+"人");
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
    public void makeSureRelation(ActivityShow activityShow){
        int is_id = activityShow.getAct_island();
        int user_id = Integer.parseInt(((AppContext)getApplicationContext()).getUserId());
        NetUtils netUtils = NetUtils.getInstance();
        String token = ((AppContext) getApplicationContext()).getToken();
        Map map = new HashMap();
        map.put("is_id", String.valueOf(is_id));
        map.put("user_id", String.valueOf(user_id));
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_BASE + "/findIRByUserIS", token, map, new NetUtils.MyNetCall() {
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
                    final Gson gson = new Gson();
                    JSONObject jsonObject = json.getJSONObject("ir");
                    if(jsonObject!=null){
                        IslandResident ir = gson.fromJson(jsonObject.toString(),IslandResident.class);
                        relation =ir.getRes_privilege();
                    }else {
                        relation = 0;
                    }
                    //然后显示更多按钮
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //更多操作
                            tv_more.setVisibility(View.VISIBLE);
                            tv_more.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    PopupMenu popup = new PopupMenu(ActivityDetailShow.this, view);//第二个参数是绑定的那个view
                                    //获取菜单填充器
                                    MenuInflater inflater = popup.getMenuInflater();
                                    //填充菜单
                                    if(relation == 2 || relation == 3) { //可以修改活动信息的权限
                                        inflater.inflate(R.menu.menu_activity_more, popup.getMenu());
                                    }else{
                                        inflater.inflate(R.menu.menu_activity_more_normal,popup.getMenu());
                                    }
                                    //绑定菜单项的点击事件
                                    popup.setOnMenuItemClickListener(ActivityDetailShow.this);
                                    //显示(这一行代码不要忘记了)
                                    popup.show();
                                }
                            });
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
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case R.id.share:
                Intent i = new Intent(ActivityDetailShow.this, ForwardMsgActivity.class);
                i.setFlags(2);
                i.putExtra("useType","shareActivity");
                i.putExtra("activityShow",activityShow);
                AppContext.forwardMsg.clear();
                //AppContext.forwardMsg.add(msg);
                startActivity(i);
                break;
            case R.id.change:
                if(activityShow.getAct_time().getTime()<=System.currentTimeMillis()){
                    ToastUtil.shortToast(ActivityDetailShow.this,"活动开始前才可以修改内容哦~~");
                }else {
                    Intent intent = new Intent(ActivityDetailShow.this, ChangeActActivity.class);
                    intent.putExtra("type", "change");
                    intent.putExtra("activityShow", activityShow);
                    startActivity(intent);
                }
                break;
            case R.id.delete:
                showNormalDialog();
                break;
            default:
                break;
        }
        return false;
    }

    public void findActById(int act_id){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getApplicationContext()).getToken();
        Map map=new HashMap();
        map.put("act_id", String.valueOf(act_id));
        map.put("page_First","1");
        map.put("page_Size","1");
        map.put("orderby","time");
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_BASE + "/findActByConditionDescPage", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                        startActivity(new Intent(ActivityDetailShow.this, LoginActivity.class));
                        finish();
                        return;
                    }
                    final Gson gson=new Gson();
                    JSONArray listact=json.getJSONArray("listact");
                    JsonParser parser=new JsonParser();
                    final JsonArray Jarray=parser.parse(listact.toString()).getAsJsonArray();
                    if (Jarray != null&&!Jarray.equals("[]")&&Jarray.size()>0) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                activityShow = gson.fromJson(Jarray.get(0).toString(),ActivityShow.class);
                                act_sign =activityShow.getSign();
                                fillUI(activityShow);
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

    private void showNormalDialog(){
        AlertDialog normalDialog = new AlertDialog.Builder(this)
                .setIcon(R.drawable.warn)
                .setTitle("删除不可恢复，确定删除么？")
                //.setMessage("test")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        try {
                            NetUtils netUtils=NetUtils.getInstance();
                            String token=((AppContext)getApplicationContext()).getToken();
                            Map map=new HashMap();
                            map.put("act_id",String.valueOf(activityShow.getAct_id()));
                            map.put("act_island",String.valueOf(activityShow.getAct_island()));
                            map.put("current_state","1");
                            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ACTIVITY_BASE + "/changeActivity",token, map, new NetUtils.MyNetCall() {
                                @Override
                                public void success(Call call, Response response) throws IOException,JSONException{
                                    String ret=response.body().string();
                                    JSONObject json = new JSONObject(ret);
                                    //如果token过期,就重新登陆
                                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                                        startActivity(new Intent(ActivityDetailShow.this, LoginActivity.class));
                                        finish();
                                        return;
                                    }
                                    String  delstate=json.getString("changeState");
                                    if(delstate!=null&&delstate.equals("success")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtil.shortToast(ActivityDetailShow.this,"删除活动信息成功~~");
                                                finish();
                                            }
                                        });
                                    }
                                }
                                @Override
                                public void failed(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtil.shortToast(ActivityDetailShow.this,"删除活动信息失败，发生未知异常~");
                                        }
                                    });
                                }
                            });
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

}
