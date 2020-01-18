package com.test.hyxc.page.discussreplyshow;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.DiscussAdapter;
import com.test.hyxc.adapter.ImageShowAdapter;
import com.test.hyxc.login.LoginActivity;
import com.test.hyxc.model.UserWorkDiscussShow;
import com.test.hyxc.model.WorkDiscussReply;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.personal.InformationActivity;
import com.test.hyxc.page.workshow.ImageShowActivity;

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
import tools.LoadingDialog;
import tools.NetUtils;
public class DiscussReplyActivity extends BaseActivity {
    private WorkShow workShow;
    ImageView iv_back;
    int adapterposition=0;
    RecyclerView rv_discuss;
    int work_id;
    public EditText et_input;
    private int user_id;
    public Button btn_send;
    //评论页码，评论下面的回复数
    public LoadingDialog loadingDialog;
    private int page_Num=1,page_Size=4, req_reply_num=2;
    private StaggeredGridLayoutManager recyclerViewLayoutManager;
    private DiscussAdapter adapter;
    private List<UserWorkDiscussShow> luwd=new ArrayList<>();
    //中间变量 保存 评论回复的需要的信息
    public static Map reply_map=new HashMap();
    ///回复的模式
    //1:评论(对作品的回复）  2：对评论回复  3：对回复回复  2和3 可以看成一种
    private  static int   reply_mode=1;
    @Override
    protected int getLayoutID() { return R.layout.imap_discuss_reply_show; }
    @Override
    protected void initListener() {}
    @Override
    protected void initData() { }
    @Override
    protected void initView() {
        workShow=(WorkShow) getIntent().getSerializableExtra("workShow");
        work_id=workShow.getWork_id();
        loadingDialog=new LoadingDialog(this);
        user_id=Integer.parseInt(((AppContext)getApplicationContext()).getUserId());
        iv_back=f(R.id.iv_back);
        et_input=f(R.id.et_input);
        btn_send=f(R.id.btn_send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_input.getText()==null||"".equals(et_input.getText().toString())){
                    Toast.makeText(getApplicationContext(),"请输入内容~",Toast.LENGTH_SHORT).show();
                    return;
                }
                String text=et_input.getText().toString();
                //增加评论
                if(reply_mode==1){
                    addDiscuss(work_id,text);
                }else if(reply_mode==2){
                    //给评论回复
                    int discuss_id=Integer.parseInt(reply_map.get("discuss_id").toString());
                    int reply_from_id=Integer.parseInt(reply_map.get("reply_from_id").toString());
                    int reply_to_id=Integer.parseInt(reply_map.get("reply_to_id").toString());
                    int dis_index=Integer.parseInt(reply_map.get("dis_index").toString());
                    addReply(work_id,discuss_id,reply_from_id,reply_to_id,text,dis_index);
                }else  if(reply_mode==3){
                    //给回复回复
                    int discuss_id=Integer.parseInt(reply_map.get("discuss_id").toString());
                    int reply_from_id=Integer.parseInt(reply_map.get("reply_from_id").toString());
                    int reply_to_id=Integer.parseInt(reply_map.get("reply_to_id").toString());
                    int dis_index=Integer.parseInt(reply_map.get("dis_index").toString());
                    int rep_index=Integer.parseInt(reply_map.get("rep_index").toString());
                    addReplyReply(work_id,discuss_id,reply_from_id,reply_to_id,text,dis_index,rep_index);

                }
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //网络请求
        try{
           requireDiscussReplyPage();
        }catch (Exception e){
            e.printStackTrace();
        }
        rv_discuss=f(R.id.rv_discuss);
        adapter=new DiscussAdapter(luwd,this);
        //这里能防止位置错乱
        recyclerViewLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerViewLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        rv_discuss.setLayoutManager(recyclerViewLayoutManager);
        rv_discuss.setAdapter(adapter);
        rv_discuss.setItemAnimator(null);
        adapter.setOnItemClickListener(MyItemClickListener);
        setGlideToBottom(); //滑到底部事件

    }
    /*****滑动到底部****/
    public  void setGlideToBottom(){
        rv_discuss.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewLayoutManager.invalidateSpanAssignments();
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (isSlideToBottom(recyclerView)) {
                    try {
                        requireDiscussReplyPage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                super.onScrolled(recyclerView, dx, dy);
            }

        });
    }
    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    public  void requireDiscussReplyPage(){
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext) getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("work_id", String.valueOf(work_id));
            map.put("page_Num", String.valueOf(page_Num));
            map.put("page_Size",String.valueOf(page_Size));
            map.put("replyNum", String.valueOf(req_reply_num));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/findUWDSByWorkId", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    page_Num++;
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    Gson gson=new Gson();
                    JSONArray userworkdiscussshow=json.getJSONArray("list");
                    JsonParser parser=new JsonParser();
                    final JsonArray Jarray=parser.parse(userworkdiscussshow.toString()).getAsJsonArray();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (JsonElement obj : Jarray) {
                                UserWorkDiscussShow uwd=gson.fromJson(obj, UserWorkDiscussShow.class);
                                luwd.add(uwd);
                                adapterposition++;
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
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
    /**item＋item里的控件点击监听事件 */
    private DiscussAdapter.OnItemClickListener MyItemClickListener = new DiscussAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, DiscussAdapter.ViewName viewName, Object tag) {
            InputMethodManager imm;
            int dis_index=0;
            String  reply_to="" ;
            switch (v.getId()){
                //评论的回复
                case R.id.tv_reply:
                    //要保证用户已经补全信息（昵称必填项不为空才行)
                    if(((AppContext)getApplicationContext()).getNickname()==null||"".equals(((AppContext)getApplicationContext()).getNickname())){
                        Intent intent=new Intent(DiscussReplyActivity.this, InformationActivity.class);
                        startActivity(intent);
                    }else {
                        reply_mode=2;
                        dis_index=(int) v.getTag();
                        reply_map.put("work_id", work_id);
                        reply_map.put("discuss_id", luwd.get(dis_index).getDiscuss_id());
                        reply_map.put("reply_from_id", user_id);
                        reply_map.put("reply_to_id", luwd.get(dis_index).getUser_id());
                        reply_map.put("dis_index", dis_index);
                        reply_to=luwd.get(dis_index).getUser_nickname();
                        et_input.setFocusable(true);
                        et_input.setFocusableInTouchMode(true);
                        et_input.requestFocus();
                        et_input.setHint("@--" + reply_to);
                        imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(et_input, 0);
                    }
                    break;
                //回复的回复
                case R.id.tv_reply_reply:
                    //要保证用户已经补全信息（昵称必填项不为空才行)
                    if(((AppContext)getApplicationContext()).getNickname()==null||"".equals(((AppContext)getApplicationContext()).getNickname())){
                        Intent intent=new Intent(DiscussReplyActivity.this, InformationActivity.class);
                        startActivity(intent);
                    }else {
                        reply_mode=3;
                        String dis_rep=(String) v.getTag();
                        dis_index=Integer.parseInt(dis_rep.split("_")[0]);
                        int rep_index=Integer.parseInt(dis_rep.split("_")[1]);
                        reply_to=luwd.get(dis_index).getWorkDiscussReplyList().get(rep_index).getFrom_nickname();
                        int reply_to_id=luwd.get(dis_index).getWorkDiscussReplyList().get(rep_index).getReply_from_id();
                        reply_map.put("work_id", work_id);
                        reply_map.put("discuss_id", luwd.get(dis_index).getDiscuss_id());
                        reply_map.put("reply_from_id", user_id);
                        reply_map.put("reply_to_id", reply_to_id);
                        reply_map.put("dis_index", dis_index);
                        reply_map.put("rep_index",rep_index);
                        et_input.setFocusable(true);
                        et_input.setFocusableInTouchMode(true);
                        et_input.requestFocus();
                        et_input.setHint("@--" + reply_to);
                        imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.showSoftInput(et_input, 0);
                    }
                    break;
                default:
                    //恢复默认值
                    reply_mode=1;
                    et_input.setHint("我有话说~");
                    break;
            }
        }
        @Override
        public void onItemLongClick(View v) {
        }
    };
    //增加评论
    public void  addDiscuss(int work_id,String discuss_text){
        loadingDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    NetUtils netUtils=NetUtils.getInstance();
                    String token=((AppContext) getApplicationContext()).getToken();
                    Map map=new HashMap();
                    map.put("work_id", String.valueOf(work_id));
                    map.put("discuss_text", discuss_text);
                    netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/addDiscuss", token, map, new NetUtils.MyNetCall() {
                        @Override
                        public void success(Call call, Response response) throws IOException, JSONException {
                            String ret=response.body().string();
                            JSONObject json=new JSONObject(ret);
                            if (json.has("addDiscuss") && json.get("addDiscuss").equals("success")) {
                                JSONObject obj=json.getJSONObject("discuss");
                                //将评论插入
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(loadingDialog!=null)
                                            loadingDialog.hide();
                                        Gson gson=new Gson();
                                        JsonParser parser=new JsonParser();
                                        UserWorkDiscussShow uwds=gson.fromJson(obj.toString(), UserWorkDiscussShow.class);
                                        uwds.setDiscuss_reply_count(0);
                                        //将这条评论的用户信息赋值 用于点击跳转到个人主页
                                        uwds.setUser_nickname(((AppContext)getApplicationContext()).getNickname());
                                        uwds.setUser_headimg(((AppContext)getApplicationContext()).getHeadImg());
                                        uwds.setUser_id(Integer.parseInt(((AppContext)getApplicationContext()).getUserId()));
                                        workShow.setWork_discuss_count(workShow.getWork_discuss_count()==0?1:workShow.getWork_discuss_count()+1);
                                        luwd.add(0, uwds);
                                        adapter.notifyDataSetChanged();
                                        //然后要将 mode至1
                                        reply_mode=1;
                                        et_input.setFocusable(true);
                                        et_input.setFocusableInTouchMode(true);
                                        et_input.requestFocus();
                                        et_input.setText("");
                                        et_input.setHint("我有话说~");
                                        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
                                    }
                                });
                            }
                        }

                        @Override
                        public void failed(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(loadingDialog!=null)
                                        loadingDialog.hide();
                                }
                            });
                            reply_mode=1;
                            et_input.setFocusable(true);
                            et_input.setFocusableInTouchMode(true);
                            et_input.requestFocus();
                            et_input.setText("");
                            et_input.setHint("我有话说~");
                            InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(loadingDialog!=null)
                                loadingDialog.hide();
                        }
                    });
                    e.printStackTrace();
                }
            }}.start();
    }
    //增加评论的回复  index保存评论修改的位置
    public void  addReply(int work_id,int discuss_id,int reply_from_id,int reply_to_id,String reply_text,int dis_index){
        loadingDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    NetUtils netUtils=NetUtils.getInstance();
                    String token=((AppContext) getApplicationContext()).getToken();
                    Map map=new HashMap();
                    map.put("work_id", String.valueOf(work_id));
                    map.put("discuss_id", String.valueOf(discuss_id));
                    map.put("reply_from_id", String.valueOf(reply_from_id));
                    map.put("reply_to_id", String.valueOf(reply_to_id));
                    map.put("reply_text", reply_text);
                    netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/AddReply", token, map, new NetUtils.MyNetCall() {
                        @Override
                        public void success(Call call, Response response) throws IOException, JSONException {
                            String ret=response.body().string();
                            JSONObject json=new JSONObject(ret);
                            //如果token过期,就重新登陆
                            if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                                return;
                            }
                            if (json.has("AddReply") && json.get("AddReply").equals("success")) {
                                runOnUiThread(new Runnable() {
                                    JSONObject obj=json.getJSONObject("reply");
                                    Gson gson=new Gson();
                                    @Override
                                    public void run() {
                                        if(loadingDialog!=null)
                                            loadingDialog.hide();
                                        WorkDiscussReply wdr=gson.fromJson(obj.toString(), WorkDiscussReply.class);
                                        //将这条回复的用户信息赋值 用于点击跳转到个人主页
                                        wdr.setFrom_nickname(((AppContext)getApplicationContext()).getNickname());
                                        wdr.setFrom_headimg(((AppContext)getApplicationContext()).getHeadImg());
                                        wdr.setReply_from_id(Integer.parseInt(((AppContext)getApplicationContext()).getUserId()));
                                        wdr.setReply_to_id(luwd.get(dis_index).getUser_id());
                                        wdr.setTo_nickname(luwd.get(dis_index).getUser_nickname());
                                        //评论的回复数量+1
                                        luwd.get(dis_index).setDiscuss_reply_count(luwd.get(dis_index).getDiscuss_reply_count()+1);
                                        workShow.setWork_discuss_count(workShow.getWork_discuss_count()==0?1:workShow.getWork_discuss_count()+1);
                                        //如果是空就创建
                                        if(luwd.get(dis_index).getWorkDiscussReplyList()!=null&&luwd.get(dis_index).getWorkDiscussReplyList().size()>0) {
                                            luwd.get(dis_index).getWorkDiscussReplyList().add(0, wdr);
                                        }else{
                                            List<WorkDiscussReply> list=new ArrayList<>();
                                            luwd.get(dis_index).setWorkDiscussReplyList(list);
                                            luwd.get(dis_index).getWorkDiscussReplyList().add(0,wdr);
                                        }
                                        adapter.notifyItemChanged(dis_index);
                                        //然后要将 mode至1
                                        reply_mode=1;
                                        et_input.setFocusable(true);
                                        et_input.setFocusableInTouchMode(true);
                                        et_input.requestFocus();
                                        et_input.setText("");
                                        et_input.setHint("我有话说~");
                                        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);

                                    }
                                });
                            }
                        }

                        @Override
                        public void failed(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(loadingDialog!=null)
                                        loadingDialog.hide();
                                }
                            });
                            reply_mode=1;
                            et_input.setFocusable(true);
                            et_input.setFocusableInTouchMode(true);
                            et_input.requestFocus();
                            et_input.setText("");
                            et_input.setHint("我有话说~");
                            InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}.start();
    }
    //增加回复的回复
    public void  addReplyReply(int work_id,int discuss_id,int reply_from_id,int reply_to_id,String reply_text,int dis_index,int rep_index){
        loadingDialog.show();
        new Thread() {
            @Override
            public void run() {
                try {
                    NetUtils netUtils=NetUtils.getInstance();
                    String token=((AppContext) getApplicationContext()).getToken();
                    Map map=new HashMap();
                    map.put("work_id", String.valueOf(work_id));
                    map.put("discuss_id", String.valueOf(discuss_id));
                    map.put("reply_from_id", String.valueOf(reply_from_id));
                    map.put("reply_to_id", String.valueOf(reply_to_id));
                    map.put("reply_text", reply_text);
                    netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/AddReply", token, map, new NetUtils.MyNetCall() {
                        @Override
                        public void success(Call call, Response response) throws IOException, JSONException {
                            String ret=response.body().string();
                            JSONObject json=new JSONObject(ret);
                            //如果token过期,就重新登陆
                            if (json.has("tokenState") && json.getString("tokenState").equals("tokenTimeout")) {
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                finish();
                                return;
                            }
                            if (json.has("AddReply") && json.get("AddReply").equals("success")) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(loadingDialog!=null)
                                            loadingDialog.hide();
                                        JSONObject obj=null;
                                        try {
                                            obj=json.getJSONObject("reply");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Gson gson=new Gson();
                                        WorkDiscussReply wdr=gson.fromJson(obj.toString(), WorkDiscussReply.class);
                                        //将这条回复的用户信息赋值 用于点击跳转到个人主页
                                        wdr.setFrom_nickname(((AppContext)getApplicationContext()).getNickname());
                                        wdr.setFrom_headimg(((AppContext)getApplicationContext()).getHeadImg());
                                        wdr.setReply_from_id(Integer.parseInt(((AppContext)getApplicationContext()).getUserId()));
                                        wdr.setReply_to_id(luwd.get(dis_index).getUser_id());
                                        wdr.setTo_nickname(luwd.get(dis_index).getUser_nickname());
                                        //评论的回复数量+1
                                        luwd.get(dis_index).setDiscuss_reply_count(luwd.get(dis_index).getDiscuss_reply_count()+1);
                                        workShow.setWork_discuss_count(workShow.getWork_discuss_count()==0?1:workShow.getWork_discuss_count()+1);
                                        //如果是空就创建
                                        if(luwd.get(dis_index).getWorkDiscussReplyList()!=null&&luwd.get(dis_index).getWorkDiscussReplyList().size()>0) {
                                            luwd.get(dis_index).getWorkDiscussReplyList().add(0, wdr);
                                        }else{
                                            List<WorkDiscussReply> list=new ArrayList<>();
                                            luwd.get(dis_index).setWorkDiscussReplyList(list);
                                            luwd.get(dis_index).getWorkDiscussReplyList().add(0,wdr);
                                        }
                                        adapter.notifyItemChanged(dis_index);
                                        //然后要将 mode至1
                                        reply_mode=1;
                                        et_input.setFocusable(true);
                                        et_input.setFocusableInTouchMode(true);
                                        et_input.requestFocus();
                                        et_input.setText("");
                                        et_input.setHint("我有话说~");
                                        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                        imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
                                    }
                                });
                            }
                        }
                        @Override
                        public void failed(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(loadingDialog!=null)
                                        loadingDialog.hide();
                                }
                            });
                            reply_mode=1;
                            et_input.setFocusable(true);
                            et_input.setFocusableInTouchMode(true);
                            et_input.requestFocus();
                            et_input.setText("");
                            et_input.setHint("我有话说~");
                            InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(et_input.getWindowToken(), 0);
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }}.start();
    }

}
