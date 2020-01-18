package com.test.hyxc.page.island.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.test.hyxc.R;
import com.test.hyxc.ccb.base.BaseFragment;
import com.test.hyxc.ccb.utils.GlideImageUtils;
import com.test.hyxc.ccb.utils.ToastUtils;
import com.test.hyxc.model.IslandCategory;
import com.test.hyxc.page.island.IslandCreateActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import tools.AppContext;
import static cn.jpush.im.android.api.jmrtc.JMRTCInternalUse.getApplicationContext;

public class ClassifyRightFragment extends BaseFragment {
    private Rv1Adapter rv1Adapter;
    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_classify_right, container, false);
    }
    private RecyclerView rv1;
    private String mTitle;
    private String datas[] = new String[1];
    String url="";
    private List<IslandCategory> categorySecond=new ArrayList<>();
    private List<IslandCategory> categorySecondBelong=new ArrayList<>(); //从categorySecond中筛选出父类是parentId的
    private Integer parentId=-1;
    private String parentName="";
    private int randomPosition;
    @Override
    public void initView(View view) {
       url=((AppContext)getApplicationContext()).getOssConfig().getHost()+"/";
       rv1 = view.findViewById(R.id.rv1);
       rv1.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
       rv1Adapter = new Rv1Adapter(R.layout.item_classify_rv1);
       rv1.setAdapter(rv1Adapter);
    }
    @Override
    public void loadData() {
        mTitle = getArguments().getString("Name");
        categorySecond= (List<IslandCategory>) getArguments().getSerializable("categorySecond");
        parentId=getArguments().getInt("ParentId");
        parentName=getArguments().getString("ParentName");
        datas[0]=mTitle;
        findCategoryBelong();
        rv1Adapter.setNewData(Arrays.asList(datas));
    }

    void findCategoryBelong(){
        categorySecondBelong.clear(); //先清空
        for(int i=0;i<categorySecond.size();i++){
            if(parentId.equals(categorySecond.get(i).getCate_parent())){
                categorySecondBelong.add(categorySecond.get(i));
            }
        }
    }
    @Override
    public void initListener() {
        ClassifyFragment.setonListeners(new ClassifyFragment.Listeners() {
            @Override
            public void onClick() {
                loadData();
                rv1.scrollToPosition(0); //回到顶部 不需要可以注释
            }
        });
    }
    class Rv1Adapter extends BaseQuickAdapter<String,BaseViewHolder> {
        public Rv1Adapter(int layoutResId) {
            super(layoutResId);
        }
        @Override
        protected void convert(final BaseViewHolder helper, String item) {
           helper.setText(R.id.tvTitle,mTitle);
           RecyclerView rv2 = helper.getView(R.id.rv2);
            rv2.setLayoutManager(new GridLayoutManager(mContext,3));
            Rv2Adapter rv2Adapter = new Rv2Adapter(R.layout.item_classify_rv2, categorySecondBelong,helper.getAdapterPosition());
            rv2.setAdapter(rv2Adapter);
            helper.setOnClickListener(R.id.tvTitle, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtils.showToast(mContext,mTitle+helper.getAdapterPosition());
                }
            });
        }
    }
    class Rv2Adapter extends BaseQuickAdapter<IslandCategory,BaseViewHolder> {
        private int rv1Position;
        public Rv2Adapter(int layoutResId, List<IslandCategory> data, int adapterPosition) {
            super(layoutResId, data);
            rv1Position = adapterPosition;
        }
        @Override
        protected void convert(final BaseViewHolder helper, IslandCategory item) {
            helper.setText(R.id.tvTitle,item.getCate_name());
            GlideImageUtils.display(mContext, url+item.getCate_img(),(ImageView)helper.getView(R.id.iv_photo));
            helper.setOnClickListener(R.id.iv_photo, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //ToastUtil.shortToast(getApplicationContext(),item.getCate_img());
                    Intent intent = new Intent();
                    intent.putExtra("parentId", parentId);
                    intent.putExtra("parentName",parentName);
                    intent.putExtra("cateId",item.getCate_id());
                    intent.putExtra("cateName",item.getCate_name());
                    intent.setClass(mContext, IslandCreateActivity.class);
                    startActivity(intent);
                 }
            });
        }
    }
}
