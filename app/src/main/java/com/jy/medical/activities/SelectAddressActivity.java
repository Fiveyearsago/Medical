package com.jy.medical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.jy.medical.R;
import com.jy.medical.adapter.AddressAdapter1;
import com.jy.medical.greendao.entities.AddressData;
import com.jy.medical.util.LocationUtil;
import com.jy.medical.util.SPUtils;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.SlideBottomPanel;

import java.util.ArrayList;
import java.util.List;

public class SelectAddressActivity extends BaseActivity implements SlideBottomPanel.CCallBack, AddressAdapter1.ACallBack, PoiSearch.OnPoiSearchListener, GeocodeSearch.OnGeocodeSearchListener, LocationUtil.LCallBack {
    private MapView mMapView;
    private AMap aMap;
    private List<AddressData> list;
    private AddressAdapter1 adapter;
    private CheckBox checkBox;
    private ListView listView;
    private com.jy.medical.widget.SlideBottomPanel slideBottomPanel;

    private TextView cityText, btnSure;

    private TextView textTitle, textAddress, textLocation;
    private ImageView myLocationImage;
    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private GeocodeSearch geocoderSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
    }

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
        setLocationSearchState(findViewById(R.id.title_head));
        myLocationImage = (ImageView) findViewById(R.id.my_location_image);
        myLocationImage.setOnClickListener(this);
        cityText = (TextView) findViewById(R.id.page_head_button);
        btnSure = (TextView) findViewById(R.id.btn_sure);
        btnSure.setOnClickListener(this);
        cityText.setText(SPUtils.get(this, "cityName", "").toString());
        mMapView = (MapView) findViewById(R.id.mapView);
//        mMapView.onCreate(getIntent().getExtras());
        aMap = mMapView.getMap();
        //去LOGO
        aMap.getUiSettings().setLogoBottomMargin(-50);
        //去缩放按钮
        aMap.getUiSettings().setZoomControlsEnabled(false);
//        aMap.moveCamera(CameraUpdateFactory.zoomTo(18.0f));

        textTitle = (TextView) findViewById(R.id.text_title);
        textAddress = (TextView) findViewById(R.id.text_address);
        textLocation = (TextView) findViewById(R.id.text_location);

        list = new ArrayList<>();
        adapter = new AddressAdapter1(this, list, this);
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
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                LatLng latLng = cameraPosition.target;
                Log.i("latLng", latLng.latitude + "");
                geocoderSearch = new GeocodeSearch(SelectAddressActivity.this);
                RegeocodeQuery query1 = new RegeocodeQuery(new LatLonPoint(latLng.latitude,
                        latLng.longitude), 1000, GeocodeSearch.AMAP);
                geocoderSearch.setOnGeocodeSearchListener(SelectAddressActivity.this);
                geocoderSearch.getFromLocationAsyn(query1);
            }
        });
        if (cityText.getText().equals("")) {
            LocationUtil.getLocation(this, this);
        }

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.my_location_image:
                LocationUtil.getLocation(this, this);
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
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0x11:
                    String cityName = data.getStringExtra("cityName");
                    cityText.setText(cityName);
                    break;
                case 0x14:
                    aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(data.getDoubleExtra("lat", 0),
                            data.getDoubleExtra("long", 0))));

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

    @Override
    public void changeSearch(int index) {
        if (slideBottomPanel.isPanelShowing()) {
            slideBottomPanel.hide();
            checkBox.setChecked(false);
        }
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(list.get(index).getLatitude(), list.get(index).getLongitude())));

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        if (i == 1000) {
            if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                if (poiResult.getQuery().equals(query)) {// 是否是同一条
                    // 取得搜索到的poiitems有多少页
                    List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始
                    List<SuggestionCity> suggestionCities = poiResult
                            .getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                    if (poiItems.size() > 0)
                        list.clear();
                    for (int j = 0; j < poiItems.size(); j++) {
                        if (list.size() > 10)
                            break;
                        Log.i("poiItemTitle", poiItems.get(j).getTitle());
                        Log.i("poiItemAddress", poiItems.get(j).getProvinceName() + poiItems.get(j).getCityName() + poiItems.get(j).getSnippet());
                        if (!poiItems.get(j).getProvinceName().equals(poiItems.get(j).getCityName())) {
                            if (poiItems.get(j).getSnippet().equals("") || poiItems.get(j).getSnippet() == null)
                                break;
                            list.add(new AddressData(poiItems.get(j).getTitle(), poiItems.get(j).getProvinceName() + poiItems.get(j).getCityName() + poiItems.get(j).getSnippet(), poiItems.get(j).getLatLonPoint().getLatitude(), poiItems.get(j).getLatLonPoint().getLongitude()));
                        } else {
                            if (poiItems.get(j).getSnippet().equals("") || poiItems.get(j).getSnippet() == null)
                                break;
                            list.add(new AddressData(poiItems.get(j).getTitle(), poiItems.get(j).getCityName() + poiItems.get(j).getSnippet(), poiItems.get(j).getLatLonPoint().getLatitude(), poiItems.get(j).getLatLonPoint().getLongitude()));
                        }
                    }
                    textTitle.setText(list.get(0).getTitle());
//                    textAddress.setText(list.get(0).getText());
                    textTitle.setVisibility(View.VISIBLE);
                    textAddress.setVisibility(View.VISIBLE);
                    textLocation.setVisibility(View.GONE);
                    btnSure.setVisibility(View.VISIBLE);
                    adapter.setData(list);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
//                textTitle.setText(result.getRegeocodeAddress().getStreetNumber().getStreet()+result.getRegeocodeAddress().getStreetNumber().getNumber());
                textAddress.setText(result.getRegeocodeAddress().getFormatAddress());
//                textTitle.setVisibility(View.VISIBLE);
//                textAddress.setVisibility(View.VISIBLE);
//                textLocation.setVisibility(View.GONE);
//                btnSure.setVisibility(View.VISIBLE);

                Log.i("getFormatAddress", result.getRegeocodeAddress().getFormatAddress());
                Log.i("getFormatAddress", result.getRegeocodeAddress().getBuilding() + " " + result.getRegeocodeAddress().getBuilding() + "  " + result.getRegeocodeAddress().getDistrict() + "  " + result.getRegeocodeAddress().getStreetNumber().getNumber() + "  " + result.getRegeocodeAddress().getTownship());
                query = new PoiSearch.Query(result.getRegeocodeAddress().getBuilding() == null ? "" : result.getRegeocodeAddress().getBuilding(), "", "");
                query.setPageSize(20);// 设置每页最多返回多少条poiitem
                query.setPageNum(1);//设置查询页码
                poiSearch = new PoiSearch(SelectAddressActivity.this, query);

                poiSearch.setBound(new PoiSearch.SearchBound(result.getRegeocodeQuery().getPoint()
                        , 1000));//设置周边搜索的中心点以及半径
                poiSearch.setOnPoiSearchListener(SelectAddressActivity.this);
                poiSearch.searchPOIAsyn();
            } else {
            }
        } else {
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

        Log.i("geocodeResult", geocodeResult.toString());
    }

    @Override
    public void getLocationSuccess(AMapLocation aMapLocation) {
        cityText.setText(aMapLocation.getCity());
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18.0f));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));

    }

    @Override
    public void getLocationFailed() {
        ToastUtil.showToast(this, "定位失败，请稍后重试");
    }
}
