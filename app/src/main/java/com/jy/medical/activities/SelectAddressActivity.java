package com.jy.medical.activities;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.jy.medical.R;
import com.jy.medical.adapter.AddressAdapter;
import com.jy.medical.greendao.entities.AddressData;
import com.jy.medical.util.GetLocation;
import com.jy.medical.util.MyOrientationListener;
import com.jy.medical.widget.SlideBottomPanel;

import java.util.ArrayList;
import java.util.List;

public class SelectAddressActivity extends BaseActivity implements GetLocation.LocationCallBack {
    private MapView mMapView;
    private RecyclerView recyclerView;
    private List<AddressData> list;
    private AddressAdapter adapter;
    private CheckBox checkBox;
    private RelativeLayout relativeLayout;
    private ListView listView;
    private ArrayList<String> listDatas = new ArrayList<>();
    private com.jy.medical.widget.SlideBottomPanel slideBottomPanel;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true;
    private MyOrientationListener myOrientationListener;
    private BDLocation bdLocation;

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_address;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setLocationNavState(findViewById(R.id.title_head), true, "确定");
        mMapView = (MapView) findViewById(R.id.mapView);
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {

            child.setVisibility(View.INVISIBLE);
        }
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
//        recyclerView= (RecyclerView) findViewById(R.id.address_recyclerView);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
//        list=new ArrayList<>();
//        for (int i = 0; i < 15; i++) {
//            list.add(new AddressData("Title"+i,"Address"+i));
//        }
//        adapter=new AddressAdapter(this,list);
//        recyclerView.setAdapter(adapter);
        listView = (ListView) findViewById(R.id.address_recyclerView);
        listView.setAdapter(new ArrayAdapter<>(getApplicationContext(), R.layout.list_item, getData()));
        slideBottomPanel = (SlideBottomPanel) findViewById(R.id.sbv);
        checkBox = (CheckBox) findViewById(R.id.checkbox_address);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (slideBottomPanel.isPanelShowing()) {
                        slideBottomPanel.hide();
                    }
                } else {
                    if (!slideBottomPanel.isPanelShowing()) {
                        slideBottomPanel.displayPanel();
                    }
                }
            }
        });
        checkBox.setChecked(false);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        GetLocation.getLoc(this, this);
        initOritationListener();
    }

    private ArrayList<String> getData() {
        for (int i = 0; i < 20; i++) {
            listDatas.add("Item " + i);
        }
        return listDatas;
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        // 开启方向传感器
        myOrientationListener.start();
        super.onStart();
    }

    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        // 关闭方向传感器
        myOrientationListener.stop();
        super.onDestroy();
    }

    @Override
    public void getLocationSuccess(final BDLocation location, String address, String province, String city) {
        if (location == null || mMapView == null) {
            return;
        }
        bdLocation=location;
        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(location.getRadius())
                .accuracy(100)
                // 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(location.getLatitude())
                .longitude(location.getLongitude()).build();
        Log.i("location.getDirection()",location.getDirection()+"");
        mBaiduMap.setMyLocationData(locData);
        if (isFirstLoc) {
            isFirstLoc = false;
            LatLng ll = new LatLng(location.getLatitude(),
                    location.getLongitude());
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(ll).zoom(18.0f);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        }
//
    }

    @Override
    public void getLocationFailed() {

    }

    private void initOritationListener() {
        myOrientationListener = new MyOrientationListener(
                getApplicationContext());
        myOrientationListener
                .setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
                    @Override
                    public void onOrientationChanged(float x) {
//                        mXDirection = (int) x;
                        if (bdLocation!=null) {
                            // 构造定位数据
                            MyLocationData locData = new MyLocationData.Builder()
                                    .accuracy(100)
                                    // 此处设置开发者获取到的方向信息，顺时针0-360
                                    .direction((int) x)
                                    .latitude(bdLocation.getLatitude())
                                    .longitude(bdLocation.getLongitude()).build();
                            // 设置定位数据
                            Log.i("x", x + "");
                            mBaiduMap.setMyLocationData(locData);
                            // 设置自定义图标
                            mBaiduMap.setMyLocationConfigeration((new MyLocationConfiguration(
                                    MyLocationConfiguration.LocationMode.FOLLOWING, true, null)));
                        }

                    }
                });

    }
}
