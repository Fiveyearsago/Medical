package com.jy.medical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeOption;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.jy.medical.R;
import com.jy.medical.adapter.AddressAdapter1;
import com.jy.medical.greendao.entities.AddressData;
import com.jy.medical.util.GetLocation;
import com.jy.medical.util.MyOrientationListener;
import com.jy.medical.util.SPUtils;
import com.jy.medical.widget.SlideBottomPanel;

import java.util.ArrayList;
import java.util.List;

public class SelectAddressActivity extends BaseActivity implements GetLocation.LocationCallBack, OnGetGeoCoderResultListener, AddressAdapter1.ACallBack, SlideBottomPanel.CCallBack {
    private MapView mMapView;
    private RecyclerView recyclerView;
    private List<AddressData> list;
    private AddressAdapter1 adapter;
    private CheckBox checkBox;
    private RelativeLayout relativeLayout;
    private ListView listView;
    private com.jy.medical.widget.SlideBottomPanel slideBottomPanel;
    private BaiduMap mBaiduMap;
    private boolean isFirstLoc = true;
    private MyOrientationListener myOrientationListener;
    private BDLocation bdLocation;
    private GeoCoder mSearch;
    private PoiSearch mPoiSearch;
    private TextView cityText, btnSure;

    private TextView textTitle, textAddress, textLocation;
    private ImageView myLocationImage;


    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_address;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
//        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setLocationSearchState(findViewById(R.id.title_head));
        myLocationImage = (ImageView) findViewById(R.id.my_location_image);
        myLocationImage.setOnClickListener(this);
        cityText = (TextView) findViewById(R.id.page_head_button);
        btnSure = (TextView) findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(this);
        cityText.setText(SPUtils.get(this, "cityName", "北京市").toString());
        mMapView = (MapView) findViewById(R.id.mapView);
        textTitle = (TextView) findViewById(R.id.text_title);
        textAddress = (TextView) findViewById(R.id.text_address);
        textLocation = (TextView) findViewById(R.id.text_location);
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {

            child.setVisibility(View.INVISIBLE);
        }
        //地图上比例尺
        mMapView.showScaleControl(false);
        // 隐藏缩放控件
        mMapView.showZoomControls(false);
//        recyclerView = (RecyclerView) findViewById(R.id.address_recyclerView);
//        recyclerView.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//
        list = new ArrayList<>();
        adapter = new AddressAdapter1(this, list, this);
//        recyclerView.setAdapter(adapter);
        listView = (ListView) findViewById(R.id.address_recyclerView);
        listView.setAdapter(adapter);
        slideBottomPanel = (SlideBottomPanel) findViewById(R.id.sbv);
        checkBox = (CheckBox) findViewById(R.id.checkbox_address);
        checkBox.setChecked(false);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
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

        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(poiListener);

        mBaiduMap.setMyLocationEnabled(true);
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng ptCenter = mBaiduMap.getMapStatus().target; //获取地图中心点坐标
                // 反Geo搜索
                mSearch.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(ptCenter));

            }
        });
        // 初始化搜索模块，注册事件监听
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);
        GetLocation.getLoc(this, this);
//        initOritationListener();

    }

    OnGetPoiSearchResultListener poiListener = new OnGetPoiSearchResultListener() {
        public void onGetPoiResult(PoiResult result) {
            //获取POI检索结果
            List<PoiInfo> poiList = result.getAllPoi();
            if (poiList == null)
                return;
            for (int i = 0; i < poiList.size(); i++) {
                Log.i("poiListName", poiList.get(i).name);
                Log.i("poiListAddress", poiList.get(i).address);
            }
        }

        public void onGetPoiDetailResult(PoiDetailResult result) {
            //获取Place详情页检索结果
            Log.i("result.getAddress()", result.getAddress());
        }

        @Override
        public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
            Log.i("poiIndoorResult", poiIndoorResult.getmArrayPoiInfo().get(0).address);
        }
    };

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.my_location_image:
                GetLocation.getLoc(this, this);
                break;
            case R.id.page_head_button:
                Intent intent = new Intent(this, SelectAreaActivity.class);
                startActivityForResult(intent, 0x11);
                break;
            case R.id.btn_sure:
                //确定
                Intent intent1 = new Intent();
                intent1.putExtra("address", textAddress.getText().toString());
                setResult(RESULT_OK, intent1);
                this.finish();
                break;
            case R.id.page_head_text:
                Intent intent2 = new Intent(this, SearchAddressActivity2.class);
                intent2.putExtra("cityName", cityText.getText().toString());
                startActivityForResult(intent2, 0x14);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        // 开启方向传感器
//        myOrientationListener.start();
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
//        myOrientationListener.stop();
        super.onDestroy();
    }

    @Override
    public void getLocationSuccess(final BDLocation location, String address, String province, String city) {
        if (location == null || mMapView == null) {
            return;
        }
        bdLocation = location;
        cityText.setText(location.getCity());
        LatLng ll = new LatLng(location.getLatitude(),
                location.getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
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
                        if (bdLocation != null) {
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

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
        //设置地图中心点坐标
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(geoCodeResult.getLocation());
        mBaiduMap.animateMapStatus(status);
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
        if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(this, "抱歉，未能找到结果", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        ReverseGeoCodeResult.AddressComponent addressCom = reverseGeoCodeResult.getAddressDetail();

        Log.i("addressCom", addressCom.province + addressCom.city + addressCom.district + addressCom.street + addressCom.streetNumber);
        List<PoiInfo> poiList = reverseGeoCodeResult.getPoiList();
        list.clear();
        for (int i = 0; i < poiList.size(); i++) {
            Log.i("name", poiList.get(i).name);
            Log.i("address", poiList.get(i).address);
            list.add(new AddressData(poiList.get(i).name, poiList.get(i).address, poiList.get(i).location.latitude, poiList.get(i).location.longitude));
        }
        textTitle.setText(list.get(0).getTitle());
        if (addressCom.province.equals(addressCom.city))
            textAddress.setText(addressCom.city + addressCom.district + addressCom.street + addressCom.streetNumber);
        else
            textAddress.setText(addressCom.province + addressCom.city + addressCom.district + addressCom.street + addressCom.streetNumber);
        textTitle.setVisibility(View.VISIBLE);
        textAddress.setVisibility(View.VISIBLE);
        textLocation.setVisibility(View.GONE);
        adapter.setData(list);
        adapter.notifyDataSetChanged();
        mBaiduMap.clear();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(reverseGeoCodeResult
                .getLocation()));//改变地图状态？
        //搜索附近

//        Toast.makeText(this, reverseGeoCodeResult.getAddressDetail().city + "  " +
//                        reverseGeoCodeResult.getAddressDetail().district + "  " + reverseGeoCodeResult.getAddressDetail().street +
//                        reverseGeoCodeResult.getAddressDetail().streetNumber,
//                Toast.LENGTH_LONG).show();

    }

    @Override
    public void changeSearch(int index) {
        if (slideBottomPanel.isPanelShowing()) {
            slideBottomPanel.hide();
            checkBox.setChecked(false);
        }
        LatLng ll = new LatLng(list.get(index).getLatitude(),
                list.get(index).getLongitude());
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(ll).zoom(18.0f);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x11:
                    String cityName = data.getStringExtra("cityName");
                    cityText.setText(cityName);
                    mSearch.geocode(new GeoCodeOption().city(cityName).address(cityName));
                    break;
                case 0x14:
                    LatLng ll = new LatLng(data.getDoubleExtra("lat", 0),
                            data.getDoubleExtra("long", 0));
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                    break;
            }
        }
    }

    @Override
    public void changeState(boolean flag) {
        if (flag) {
            checkBox.setChecked(true);
        } else {
            checkBox.setChecked(false);
        }
    }
}
