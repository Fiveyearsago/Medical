package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.jy.medical.R;
import com.jy.medical.util.GetLocation;

public class MapDemoActivity extends AppCompatActivity implements GetLocation.LocationCallBack {
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);
        mMapView= (MapView) findViewById(R.id.mapView);
        mBaiduMap = mMapView.getMap();
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        GetLocation.getLoc(this,this);
    }

    @Override
    public void getLocationSuccess(BDLocation bdLocation, String address, String province, String city) {

    }

    @Override
    public void getLocationFailed() {

    }
}
