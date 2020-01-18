package com.test.hyxc.page.publish;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;
import com.test.hyxc.BaseActivity;
import com.test.hyxc.R;
import com.test.hyxc.adapter.PoiAdapter;

import java.util.ArrayList;
import java.util.List;

import tools.AppContext;
import tools.BaiduMap.service.LocationService;
import tools.LoadingDialog;

/**
 * Created by WWW on 2019/2/8.
 */

public class PoiChooseActivity extends BaseActivity   {
    private Button btn_location_back;
    private Button btn_location_ok;
    private ListView lv_location_nearby;
    public TextView tv_loading;
    private TextView tv_location_ing;
    private List<PoiInfo> nearList=null;
    private List<Boolean> isSelected=new ArrayList<>();
    private PoiAdapter adapter;
    public LocationService locationService=null;
    public LoadingDialog loadingDialog;
    ///
    public BDLocation lastLocation;
    public double mCurrentLat,mCurrentLong;
    public int radius=5000;
    public PoiSearch poiSearch= PoiSearch.newInstance();
    public int poiPageNum=0;

    public int selectPosition=-1;

    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    public BDAbstractLocationListener mListener =new BDAbstractLocationListener(){
        @Override
        public void onReceiveLocation(BDLocation location) {
            if(location==null){
                return;
            }
            if(loadingDialog!=null){
                loadingDialog.dismiss();
            }
            if(lastLocation!=null){
                return;
            }
            //否则
            lastLocation=location;
            mCurrentLat=location.getLatitude();
            mCurrentLong=location.getLongitude();
           /* LatLng lla=new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
            CoordinateConverter converter=new CoordinateConverter();
            converter.coord(lla);
            converter.from(CoordinateConverter.CoordType.COMMON);
            LatLng convertLatLng=converter.convert();
            OverlayOptions ooa=new MarkerOptions().position(convertLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.imap_marker))
                    .zIndex(4).draggable(true);*/
            new Thread(new Runnable() {
                @Override
                public void run() {
                    searchNearBy(poiPageNum++);
                }
            }).start();

        }
        public void onConnectHotSpotMessage(String s, int i) {
        }
    };

    public OnGetPoiSearchResultListener onGetPoiSearchResultListener=new OnGetPoiSearchResultListener() {
        @Override
        public void onGetPoiResult(PoiResult poiResult) {
            if(tv_loading.getVisibility()!=View.GONE){
                tv_loading.setVisibility(View.GONE);
            }
            if (poiResult != null) {
                if (poiResult.getAllPoi()!=null&&poiResult.getAllPoi().size()>0){
                    for(int i=0;i<poiResult.getAllPoi().size();i++){
                        nearList.add(poiResult.getAllPoi().get(i));
                        isSelected.add(false);
                    }
                    tv_location_ing.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                }
            }
        }
        @Override
        public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {}
        @Override
        public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {}
        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {}
    };


    @Override
    protected int getLayoutID() {
        return R.layout.imap_map_poi;
    }

    @Override
    protected void initListener() {
    }
    @Override
    protected void initView() {
        btn_location_back=f(R.id.btn_location_back);
        btn_location_ok=f(R.id.btn_location_ok);
        tv_location_ing=f(R.id.tv_location_ing);
        tv_loading=f(R.id.tv_loading);
        btn_location_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectPoi=-1;
                Intent intent=new Intent();
                intent.putExtra("selectPoi",selectPoi);
                setResult(Activity.RESULT_OK,intent);
                finish();
                overridePendingTransition(0,R.anim.imap_fade_out_right);
            }
        });
        btn_location_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for(int i=0;i<isSelected.size();i++){
                   if(isSelected.get(i)==true){
                     selectPosition=i;
                   }
               }
               if(selectPosition==-1){
                   int selectPoi=0;
                   Intent intent=new Intent();
                   intent.putExtra("selectPoi",selectPoi);
                   setResult(Activity.RESULT_OK,intent);
                   finish();
                   overridePendingTransition(0,R.anim.imap_fade_out_right);
               }else{
                   PoiInfo poiInfo=nearList.get(selectPosition);
                   int selectPoi=1;
                   String name=poiInfo.getName();
                   String address=poiInfo.getAddress();
                   double lat=poiInfo.getLocation().latitude;
                   double lng=poiInfo.getLocation().longitude;
                   Intent intent=new Intent();
                   intent.putExtra("selectPoi",selectPoi);
                   intent.putExtra("name",name);
                   intent.putExtra("address",address);
                   intent.putExtra("lat",lat);
                   intent.putExtra("lng",lng);
                   setResult(Activity.RESULT_OK,intent);
                   finish();
                   overridePendingTransition(0,R.anim.imap_fade_out_right);
               }
            }
        });
        //poi
        nearList= new ArrayList<>();
        //调用百度地图
        locationService = ((AppContext)getApplicationContext()).locationService;
        locationService.registerListener(mListener);
        initLocationConfig();
        locationService.start();
        //
        adapter=new PoiAdapter(this,nearList,isSelected);
        lv_location_nearby=f(R.id.lv_location_nearby);
        lv_location_nearby.setAdapter(adapter);
        lv_location_nearby.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0;i<isSelected.size();i++){
                    if(i==position){
                        isSelected.set(i,true);
                    }else
                        isSelected.set(i,false);
                }
               adapter.notifyDataSetChanged();
            }
        });
        lv_location_nearby.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState== AbsListView.OnScrollListener.SCROLL_STATE_IDLE){
                    //判断滑动到底部
                    if(view.getLastVisiblePosition()==view.getCount()-1){
                        if(tv_loading.getVisibility()==View.GONE){
                            tv_loading.setVisibility(View.VISIBLE);
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                searchNearBy(poiPageNum++);
                            }
                        }).start();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    @Override
    protected void initData() {}


    public void initLocationConfig(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        locationService.setLocationOption(option);
    }
    /**
     * 搜索周边地理位置
     * by hankkin at:2015-11-01 22:54:49
     */
    private void searchNearBy(int pageNum) {
        PoiNearbySearchOption option = new PoiNearbySearchOption();
        option.keyword("生活");
        option.sortType(PoiSortType.distance_from_near_to_far);
        option.location(new LatLng(mCurrentLat, mCurrentLong));
        option.pageNum(pageNum);
        if (radius != 0) {
            option.radius( radius);
        } else {
            option.radius(1000);
        }
        option.pageCapacity(20);
        poiSearch.setOnGetPoiSearchResultListener(onGetPoiSearchResultListener);
        poiSearch.searchNearby(option);
    }
}
