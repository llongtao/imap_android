package com.test.hyxc.page.workshow;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;
import com.gyf.barlibrary.ImmersionBar;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.ImageShowAdapter;
import com.test.hyxc.adapter.VideoShowAdapter;
import com.test.hyxc.model.WorkShow;
import com.test.hyxc.page.discussreplyshow.DiscussReplyActivity;

import java.util.ArrayList;
import java.util.List;
import tools.AppContext;
public class VideoShowActivity extends BaseActivity {
    double longitude,latitude;
    WorkShow workShow;
    VideoShowAdapter adapter;
    PagerSnapHelper snapHelper;
    List<WorkShow> lws=new ArrayList<>();
    String url="";
    int type=1;
    private RecyclerView rv_workvideo;
    private LinearLayoutManager recyclerViewLayoutManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mImmersionBar = ImmersionBar.with(this)
                .statusBarDarkFont(true);
        mImmersionBar.init();
        super.onCreate(savedInstanceState);
    }
    @Override
    protected int getLayoutID() {
        return R.layout.imap_video_show;
    }

    @Override
    protected void initListener() { }
    @Override
    protected void initData() { }
    @Override
    protected void initView() {
        longitude=((AppContext)getApplicationContext()).getLongitude();
        latitude=((AppContext)getApplicationContext()).getLatitude();
        workShow=(WorkShow) getIntent().getSerializableExtra("workshow");
        url=((AppContext)getApplicationContext()).getOssConfig().getHost()+"/";
        type=workShow.getWork_type();
        lws.add(workShow);
        lws.add(workShow);
        lws.add(workShow);
        lws.add(workShow);
        rv_workvideo=f(R.id.rv_workvideo);
        snapHelper=new PagerSnapHelper();
        snapHelper.attachToRecyclerView(rv_workvideo);
        adapter=new VideoShowAdapter((ArrayList<WorkShow>) lws,VideoShowActivity.this);
        //这里能防止位置错乱
        recyclerViewLayoutManager = new LinearLayoutManager(VideoShowActivity.this,LinearLayoutManager.VERTICAL, false);
        rv_workvideo.setLayoutManager(recyclerViewLayoutManager);
        rv_workvideo.setAdapter(adapter);
        //设置点击事件
        adapter.setOnItemClickListener(MyItemClickListener);

    }
    /**item＋item里的控件点击监听事件 */
    private VideoShowAdapter.OnItemClickListener MyItemClickListener = new VideoShowAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View v, VideoShowAdapter.ViewName viewName, int position) {
            switch (v.getId()){

                default:
                    break;
            }
        }
        @Override
        public void onItemLongClick(View v) {
        }
    };
}
