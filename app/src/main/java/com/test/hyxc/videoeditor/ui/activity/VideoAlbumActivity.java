package com.test.hyxc.videoeditor.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.test.hyxc.R;
import com.test.hyxc.videoeditor.adapter.VideoGridAdapter;
import com.test.hyxc.videoeditor.base.BaseActivity;
import com.test.hyxc.videoeditor.helper.ToolbarHelper;
import com.test.hyxc.videoeditor.model.LocalVideoModel;
import com.test.hyxc.videoeditor.utils.VideoUtil;
import com.test.hyxc.videoeditor.view.DividerGridItemDecoration;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author LLhon
 * @Project Android-Video-Editor
 * @Package com.marvhong.videoeditor
 * @Date 2018/8/21 15:16
 * @description 视频相册界面
 */
public class VideoAlbumActivity extends BaseActivity implements VideoGridAdapter.OnItemClickListener {
    @BindView(R.id.iv_back)
    ImageView iv_back;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<LocalVideoModel> mLocalVideoModels = new ArrayList<>();
    private VideoGridAdapter mAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, VideoAlbumActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video_album;
    }

    @Override
    protected void initToolbar(ToolbarHelper toolbarHelper) {
        toolbarHelper.setTitle("相册");
    }

    @Override
    protected void initView() {
        getSupportActionBar().hide();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mAdapter = new VideoGridAdapter(this, mLocalVideoModels);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        VideoUtil.getLocalVideoFiles(this)
            .subscribe(new Observer<ArrayList<LocalVideoModel>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    subscribe(d);
                }
                @Override
                public void onNext(ArrayList<LocalVideoModel> localVideoModels) {
                    mLocalVideoModels = localVideoModels;
                    mAdapter.setData(mLocalVideoModels);
                }
                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                }
                @Override
                public void onComplete() {
                }
            });
    }

    @Override
    public void onItemClick(int position, LocalVideoModel model) {
        Intent intent =new Intent();
        intent.setClass(this,TrimVideoActivity.class);
        intent.putExtra("videoPath", model.getVideoPath());
        startActivityForResult(intent, 2);
        finish();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode==2)
        {
            if (resultCode==RESULT_OK)
            {
                Bundle bundle=data.getExtras();
                String str=bundle.getString("outputPath");
                ///然后返回给上面
                if(str!=null&&!"".equals(str)){
                    Intent intent=new Intent();
                    intent.putExtra("outputPath",str);
                    setResult(RESULT_OK,intent); ///完成传0
                    finish();
                }
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocalVideoModels = null;
    }
}
