package com.test.hyxc.page.im;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.MainActivity;
import com.test.hyxc.R;
import com.test.hyxc.page.island.IslandSearchActivity;
import com.test.hyxc.page.personal.MeActivity;
import com.test.hyxc.page.activity.PublishActivity;
import com.test.hyxc.ui.GlideRoundTransform;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import cn.jpush.im.android.api.enums.ContentType;
import cn.jpush.im.android.api.enums.ConversationType;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.UserInfo;
import q.rorbin.badgeview.QBadgeView;
import tools.AppContext;
import tools.TimeStrUtil;
import view.SwipeMenu.BaseSwipListAdapter;
import view.SwipeMenu.SwipeMenu;
import view.SwipeMenu.SwipeMenuCreator;
import view.SwipeMenu.SwipeMenuItem;
import view.SwipeMenu.SwipeMenuListView;
public class MessageListActivity extends Activity {

    private List<Conversation> mConversationList;
    private AppAdapter mAdapter;
    private SwipeMenuListView mListView;
    public ImmersionBar mImmersionBar;
    public AppContext ctx;
    QBadgeView qb=null;
    String url="";
    //底部菜单
    private TextView tv_shouye,tv_island,tv_news,tv_me;
    private ImageView iv_publish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this);//.statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
        ctx=((AppContext)getApplicationContext());
        url=ctx.getOssConfig().getHost()+"/";
        setContentView(R.layout.activity_list_message);
        //初始化底部菜单
        initBottomMenu();
        qb=new QBadgeView(this);
        qb.bindTarget(tv_news).setBadgeText(dealUnread(ctx.unReadMsgCnt));
        //mConversationList = JMessageClient.getConversationList();
        if(mConversationList!=null&&mConversationList.size()>0) {
            for (int i = 0; i < 100; i++) {
                mConversationList.add(mConversationList.get(0));
            }
        }
        mListView = findViewById(R.id.listView);
        mAdapter = new AppAdapter();
        mListView.setAdapter(mAdapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                /*SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                openItem.setWidth(dp2px(90));
                openItem.setTitle("置顶");
                openItem.setTitleSize(18);
                openItem.setTitleColor(Color.WHITE);
                menu.addMenuItem(openItem);*/
                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(dp2px(90));
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mListView.setMenuCreator(creator);
        // step 2. listener item click event
        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) throws JSONException {
                Conversation item = mConversationList.get(position);
                switch (index) {
                    case 0:

                        break;
                    case 1:
                        mConversationList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        break;
                }
                return false;
            }
        });
        mListView.setOnSwipeListener(new SwipeMenuListView.OnSwipeListener() {

            @Override
            public void onSwipeStart(int position) {
                // swipe start
            }

            @Override
            public void onSwipeEnd(int position) {
                // swipe end
            }
        });

        // set MenuStateChangeListener
        mListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
            }

            @Override
            public void onMenuClose(int position) {
            }
        });
//		listView.setCloseInterpolator(new BounceInterpolator());
        // test item long click
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), position + " long click", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void delete(ApplicationInfo item) {
        // delete app
        try {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.fromParts("package", item.packageName, null));
            startActivity(intent);
        } catch (Exception e) {
        }
    }

    private void open(ApplicationInfo item) {
        // open app
        Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        resolveIntent.setPackage(item.packageName);
        List<ResolveInfo> resolveInfoList = getPackageManager()
                .queryIntentActivities(resolveIntent, 0);
        if (resolveInfoList != null && resolveInfoList.size() > 0) {
            ResolveInfo resolveInfo = resolveInfoList.get(0);
            String activityPackageName = resolveInfo.activityInfo.packageName;
            String className = resolveInfo.activityInfo.name;

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName componentName = new ComponentName(
                    activityPackageName, className);

            intent.setComponent(componentName);
            startActivity(intent);
        }
    }

    class AppAdapter extends BaseSwipListAdapter {

        @Override
        public int getCount() {
            return mConversationList.size();
        }

        @Override
        public Conversation getItem(int position) {
            return mConversationList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = View.inflate(getApplicationContext(),
                        R.layout.item_list_message_item, null);
                new ViewHolder(convertView);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            Conversation item = getItem(position);
            //右侧未读消息数量
            if(item.getUnReadMsgCnt()>0) {
                holder.qb.bindTarget(holder.tv_qb).setBadgeText(String.valueOf(item.getUnReadMsgCnt()));
            }
            //消息创建时间
            holder.tv_time.setText(TimeStrUtil.getTimeStr(item.getLatestMessage().getCreateTime()));
            ////****获取到的消息列表的展现形式
            if(item.getType()== ConversationType.single){  //个人账户
                Object o =item.getTargetInfo();
                UserInfo userInfo=(UserInfo)o;
                holder.tv_name.setText(userInfo.getNickname());
                //头像处理
                Glide.with(ctx)
                        .load(url+userInfo.getAvatar())
                        .transform(new CenterCrop(ctx), new GlideRoundTransform(ctx, 50))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .crossFade()
                        .into(holder.iv_icon);
            }else if(item.getType()==ConversationType.group){//群聊

            }


            //填充最后的消息
            String json =item.getLatestMessage().getContent().toJson();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //消息类型处理
            if (item.getLatestMessage().getContentType() == ContentType.text) {
                try {
                    String text="";
                    if(jsonObject!=null) {
                        String s=String.valueOf(jsonObject.get("text"));
                        text = s.length()>16?s.substring(0,16)+"..":s;
                    }
                    holder.tv_news.setText(text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else if(item.getLatestMessage().getContentType()==ContentType.image){
                    holder.tv_news.setText("[图片]");
            }else if(item.getLatestMessage().getContentType()==ContentType.video){
                holder.tv_news.setText("[视频]");
            }
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(MessageListActivity.this,"iv_icon_click",Toast.LENGTH_SHORT).show();
                }
            });
            return convertView;
        }

        class ViewHolder {
            ImageView iv_icon;
            TextView tv_name; //昵称
            TextView tv_news;
            TextView tv_time;
            TextView tv_qb;
            QBadgeView qb=new QBadgeView(getApplicationContext());
            public ViewHolder(View view) {
                iv_icon = view.findViewById(R.id.iv_icon);
                tv_name = view.findViewById(R.id.tv_name);
                tv_news=view.findViewById(R.id.tv_news);
                tv_time=view.findViewById(R.id.tv_time);
                tv_qb=view.findViewById(R.id.tv_qb);
                view.setTag(this);
            }
        }

        @Override
        public boolean getSwipEnableByPosition(int position) {
//            if(position % 2 == 0){
//                return false;
//            }
//            return true;
            return true;
        }
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_left) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
            return true;
        }
        if (id == R.id.action_right) {
            mListView.setSwipeDirection(SwipeMenuListView.DIRECTION_RIGHT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mImmersionBar != null)
            mImmersionBar.destroy();  //必须调用该方法，防止内存泄漏，不调用该方法，如果界面bar发生改变，在不关闭app的情况下，退出此界面再进入将记忆最后一次bar改变的状态
    }
    //处理消息数量
    public String dealUnread(Integer news_unread){
        if(news_unread>99){
            return "99+";
        }else {
            return news_unread+"";
        }
    }
    public void initBottomMenu(){
        tv_shouye=findViewById(R.id.tv_shouye);
        tv_shouye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MessageListActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        tv_island=findViewById(R.id.tv_island);
        tv_island.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MessageListActivity.this, IslandSearchActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        tv_me=findViewById(R.id.tv_me);
        tv_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MessageListActivity.this, MeActivity.class);
                intent.putExtra("user_id", ctx.getUserId());
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
        tv_news=findViewById(R.id.tv_news);
        iv_publish=findViewById(R.id.iv_publish);
        iv_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(MessageListActivity.this,PublishActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }

}
