package com.test.hyxc.page.island.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.test.hyxc.R;
import com.test.hyxc.ccb.base.BaseFragment;
import com.test.hyxc.chat.activity.LoginActivity;
import com.test.hyxc.model.IslandCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;
import tools.AppContext;
import tools.AsyncHttpHelper;
import tools.NetUtils;

public class ClassifyFragment extends BaseFragment {
    private LeftAdapter leftAdapter;
    private ClassifyRightFragment cf;
    public List<IslandCategory> categoryFirst=new ArrayList<>();
    public List<String> categoryFirstStr=new ArrayList<>();
    public List<IslandCategory> categorySecond=new ArrayList<IslandCategory>();
    @Override
    protected View initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order, container, false);
    }
    private RecyclerView rv;
    private FrameLayout fm;
    @Override
    public void initView(View view) {
      rv = view.findViewById(R.id.rvLeft);
      fm = view.findViewById(R.id.fmRight);
      rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false));
        leftAdapter = new LeftAdapter(R.layout.item_classify_left);
        rv.setAdapter(leftAdapter);
        isTitleBar(true,view);
    }
    @Override
    public void loadData() {
        findCategory();
    }
    //填充右侧
    public void fillRight(int index){
        cf = new ClassifyRightFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.fmRight, cf);
        Bundle bundle = new Bundle();
        bundle.putString("Name",categoryFirstStr.get(index));
        bundle.putInt("ParentId",categoryFirst.get(index).getCate_id());
        bundle.putString("ParentName",categoryFirst.get(index).getCate_name());
        bundle.putSerializable("categorySecond", (Serializable) categorySecond);
        cf.setArguments(bundle);
        fragmentTransaction.commit();
    }

    @Override
    public void initListener() {

    }

    class LeftAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

        public LeftAdapter(int layoutResId) {
            super(layoutResId);
        }
        private int mPosition;
        @Override
        protected void convert(final BaseViewHolder helper, String item) {
            TextView tv = helper.getView(R.id.tv_commclass);
           helper.setText(R.id.tv_commclass,item);
           if (mPosition == helper.getAdapterPosition()){
               tv.setTextColor(getResources().getColor(R.color.mainColor));
               tv.setBackgroundColor(getResources().getColor(R.color.colorWhite));
           }else{
               tv.setTextColor(getResources().getColor(R.color.defaultTextview));
               tv.setBackgroundColor(getResources().getColor(R.color.defaultBackground));
           }
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPosition != helper.getAdapterPosition()) {
                            mPosition = helper.getAdapterPosition();
                            notifyDataSetChanged();
                            Bundle bundle = new Bundle();
                            bundle.putString("Name",categoryFirstStr.get(mPosition));
                            bundle.putInt("ParentId",categoryFirst.get(mPosition).getCate_id());
                            bundle.putString("ParentName",categoryFirst.get(mPosition).getCate_name());
                            bundle.putSerializable("categorySecond", (Serializable) categorySecond);
                            cf = new ClassifyRightFragment();
                            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction().replace(R.id.fmRight, cf);
                            cf.setArguments(bundle);
                            fragmentTransaction.commit();
                            onClickListeners.onClick();
                    }
                }
            });
        }
    }

    interface Listeners{
        void onClick();
    }
    private static Listeners onClickListeners;
    public static void setonListeners(Listeners ls){
        onClickListeners = ls;
    }
    public void findCategory(){
        NetUtils netUtils=NetUtils.getInstance();
        String token=((AppContext)getActivity().getApplicationContext()).getToken();
        Map map=new HashMap();
        try {
            netUtils.postDataAsynToNetToken(AsyncHttpHelper.URL_ISLAND_CATEGORY + "/findCategory",token, map, new NetUtils.MyNetCall() {
                @Override
                public void success(Call call, Response response) throws IOException,JSONException {
                    String ret=response.body().string();
                    JSONObject json = new JSONObject(ret);
                    //如果token过期,就重新登陆
                    if(json.has("tokenState")&&json.getString("tokenState").equals("tokenTimeout")){
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                        return;
                    }
                    String state=json.getString("state");
                    if(state.equals("fail")){
                        return;
                    }else if(state.equals("success")){
                        final Gson gson = new Gson();
                        JSONArray firstCate=json.getJSONArray("first");
                        JsonParser parser = new JsonParser();
                        final JsonArray Jarray = parser.parse(firstCate.toString()).getAsJsonArray();
                        if(Jarray!=null&&Jarray.size()>0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (JsonElement obj : Jarray) {
                                        IslandCategory category = gson.fromJson(obj, IslandCategory.class);
                                        categoryFirst.add(category);
                                        categoryFirstStr.add(category.getCate_name());
                                    }
                                    leftAdapter.setNewData(categoryFirstStr);
                                }
                            });
                        }
                        JSONArray secondCate=json.getJSONArray("second");
                        final JsonArray Jarray1 = parser.parse(secondCate.toString()).getAsJsonArray();
                        if(Jarray1!=null&&Jarray1.size()>0) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (JsonElement obj : Jarray1) {
                                        IslandCategory category = gson.fromJson(obj, IslandCategory.class);
                                        categorySecond.add(category);
                                    }
                                    //填充右侧
                                    fillRight(0);
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
}
