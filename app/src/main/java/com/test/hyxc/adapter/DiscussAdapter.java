package com.test.hyxc.adapter;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.renderscript.ScriptC;
import android.support.annotation.NonNull;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.model.UserWorkDiscussShow;
import com.test.hyxc.model.WorkDiscussReply;
import com.test.hyxc.page.discussreplyshow.DiscussReplyActivity;
import com.test.hyxc.ui.GlideRoundTransform;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.DipPxUtils;
import tools.NetUtils;
import tools.OssConfig;
public class DiscussAdapter extends RecyclerView.Adapter<DiscussAdapter.DiscussViewHolder> implements View.OnClickListener {
    private List<UserWorkDiscussShow> luwd=new ArrayList<>();
    private Context context;
    private OnItemClickListener mOnItemClickListener;//声明自定义的接口
    private DiscussViewHolder discussViewHolder;
    private DisplayMetrics metric=new DisplayMetrics();
    private String url="";
    private int position;
    public DiscussAdapter(List<UserWorkDiscussShow> luwd, Context context){
         setHasStableIds(true);
         this.luwd=luwd;
         this.context=context;
         OssConfig ossConfig=((AppContext) this.context.getApplicationContext()).getOssConfig();
         this.url=ossConfig.getHost() + "/";
         ((AppContext) context.getApplicationContext()).getWindowManager().getDefaultDisplay().getMetrics(metric);
    }
    @Override
    public DiscussViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.imap_discuss_reply_item, viewGroup, false);
         discussViewHolder=new DiscussViewHolder(view);
        return discussViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull DiscussViewHolder holder, int position) {
        this.position=position;
        UserWorkDiscussShow uwd=luwd.get(position);
        holder.itemView.setTag(position);
        holder.tv_reply.setTag(position);
        Glide.with(context)
                .load(url+uwd.getUser_headimg())
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 14))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .crossFade()
                .into(holder.iv_head);
        holder.tv_discuss_text.setText(uwd.getDiscuss_text().trim());
        Long t=luwd.get(position).getDiscuss_time().getTime();
        String timestr=getTimeStr(t);
        holder.tv_time.setText(timestr);
        holder.tv_usernickname.setText(uwd.getUser_nickname());
        //循环填充回复的列表
        tryFillReply(holder, position);
    }

    @Override
    public int getItemCount() {
        return luwd.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    public class DiscussViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_head;
        TextView tv_discuss_text;
        TextView tv_time,tv_reply;
        LinearLayout ll_reply;
        TextView tv_usernickname;
        public DiscussViewHolder(View itemView) {
            super(itemView);
            iv_head=itemView.findViewById(R.id.iv_head);
            tv_discuss_text=itemView.findViewById(R.id.tv_discuss_text);
            tv_time=itemView.findViewById(R.id.tv_time);
            tv_reply=itemView.findViewById(R.id.tv_reply);
            ll_reply=itemView.findViewById(R.id.ll_reply);
            tv_usernickname=itemView.findViewById(R.id.tv_usernickname);
            tv_reply.setOnClickListener(DiscussAdapter.this);
            iv_head.setOnClickListener(DiscussAdapter.this);
            itemView.setOnClickListener(DiscussAdapter.this);
        }
    }
    public String getTimeStr(Long t) {
        Long ct=System.currentTimeMillis();
        Long t1=ct - t; //时间差
        //3个月前的直接显示时间
        if (t1 >= 90 * 24 * 60 * 60 * 1000L) {
            return new SimpleDateFormat("yyyy-MM-dd").format(new Date(t));
            /*return workShow.getWork_time().getYear()+"-"+
                    workShow.getWork_time().getMonth()+"-"+
                    workShow.getWork_time().getDay();*/
        } else if (t1 >= 30 * 24 * 60 * 60 * 1000L) {
            return t1 / (30 * 24 * 60 * 60 * 1000L) + "月前";
        } else if (t1 > 24 * 60 * 60 * 1000L) {
            return t1 / (24 * 60 * 60 * 1000L) + "天前";
        } else if (t1 > 60 * 60 * 1000L) {
            return t1 / (60 * 60 * 1000L) + "小时前";
        } else if (t1 > 60 * 1000L) {
            return t1 / (60 * 1000L) + "分钟前";
        } else if (t1 > 1000L) {
            return t1 / 1000L + "秒前";
        } else {
            return "刚刚";
        }
    }
    //根据discuss_id查询所有回复
    public void requireAllReply(DiscussViewHolder holder,int position){
        UserWorkDiscussShow uwd=luwd.get(position);
        int discuss_id=uwd.getDiscuss_id();
        try {
            NetUtils netUtils=NetUtils.getInstance();
            String token=((AppContext)context.getApplicationContext()).getToken();
            Map map=new HashMap();
            map.put("discuss_id", String.valueOf(discuss_id));
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_WORK_BASE + "/findAllReplyByDiscuss", token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException, JSONException {
                    String ret=response.body().string();
                    JSONObject json=new JSONObject(ret);
                    Gson gson=new Gson();
                    JSONArray listReply=json.getJSONArray("listReply");
                    JsonParser parser=new JsonParser();
                    ((DiscussReplyActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final JsonArray Jarray=parser.parse(listReply.toString()).getAsJsonArray();
                            List<WorkDiscussReply> listwdr=new ArrayList<>();
                            for (JsonElement obj : Jarray) {
                                WorkDiscussReply wdr=gson.fromJson(obj, WorkDiscussReply.class);
                                listwdr.add(wdr);
                            }
                            luwd.get(position).setWorkDiscussReplyList(listwdr);
                            notifyItemChanged(position);
                           // tryFillReply(holder,position);
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

    public void tryFillReply(DiscussViewHolder holder,int position){
        UserWorkDiscussShow uwd=luwd.get(position);
        holder.ll_reply.removeAllViews();
        List  listreply=uwd.getWorkDiscussReplyList();
        //循环填充回复内容
        if(listreply!=null&&listreply.size()>0) {
            for (int i=0; i < listreply.size(); i++) {
                View view=LayoutInflater.from(context).inflate(R.layout.imap_reply_show_item, null);
                WorkDiscussReply reply=(WorkDiscussReply) listreply.get(i);
                String from_headimg=url + String.valueOf(reply.getFrom_headimg());
                String from_nickname=String.valueOf(reply.getFrom_nickname());
                String to_nickname=reply.getTo_nickname() == null ? "" : " @-- " + reply.getTo_nickname();
                String reply_text=String.valueOf(reply.getReply_text());
                Long l=reply.getReply_time().getTime();
                String reply_time=getTimeStr(l);
                //头像
                Glide.with(context)
                        .load(from_headimg)
                        .transform(new CenterCrop(context), new GlideRoundTransform(context, 10))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .crossFade()
                        .into((ImageView) view.findViewById(R.id.iv_reply_from));
                //昵称
                ((TextView) view.findViewById(R.id.tv_reply_nickname)).setText(from_nickname);
                //回复内容
                ((TextView) view.findViewById(R.id.tv_reply_content)).setText(reply_text);
                //回复对象
                ((TextView) view.findViewById(R.id.tv_reply_to)).setText(to_nickname);
                //恢复时间
                ((TextView) view.findViewById(R.id.tv_reply_time)).setText(reply_time);
                //回复按钮点击事件
                TextView tv_reply_reply=(TextView) view.findViewById(R.id.tv_reply_reply);
                //这里每一个点击 要确定的内容 position_i  position记录评论的索引  i记录回复的索引
                String dis_rep_index=position + "_" + i;
                tv_reply_reply.setTag(dis_rep_index);
                tv_reply_reply.setOnClickListener(DiscussAdapter.this);
                holder.ll_reply.addView(view);
            }
        }
        //展开按钮
        if(listreply!=null&&listreply.size()>0&&listreply.size()<uwd.getDiscuss_reply_count()){
            TextView textView=new TextView(context);
            //为全部 按钮绑定索引 注意这里的  最开始的position保留下来
            textView.setTag(position);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0,0,0,10);
            textView.setClickable(true);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag=(int)v.getTag();
                    //查询所有回复
                    requireAllReply(holder,tag);
                }
            });
            textView.setText(uwd.getDiscuss_reply_count()+"条回复");
            textView.setTextColor(Color.parseColor("#999898"));
            holder.ll_reply.addView(textView);
        }
    }
    //item里面有多个控件可以点击（item+item内部控件）
    public enum ViewName {
        ITEM,
        PRACTISE
    }
    //自定义一个回调接口来实现Click和LongClick事件
    public interface OnItemClickListener {
        void onItemClick(View v, ViewName viewName, Object tag);
        void onItemLongClick(View v);
    }
    //定义方法并传给外面的使用者
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener=listener;
    }

    @Override
    public void onClick(View v) {
        Object tag=v.getTag();
        if (mOnItemClickListener != null) {
            switch (v.getId()) {
                case R.id.tv_reply:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, tag);
                    break;
                case R.id.tv_reply_reply:
                    mOnItemClickListener.onItemClick(v, ViewName.PRACTISE, tag);
                    break;
                default:
                    mOnItemClickListener.onItemClick(v, ViewName.ITEM, tag);
                    break;
            }
        }
    }

}
