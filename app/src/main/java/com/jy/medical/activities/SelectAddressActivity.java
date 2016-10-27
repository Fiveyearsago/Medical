package com.jy.medical.activities;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.MapView;
import com.jy.medical.R;
import com.jy.medical.adapter.AddressAdapter;
import com.jy.medical.entities.AddressData;

import java.util.ArrayList;
import java.util.List;

public class SelectAddressActivity extends BaseActivity {
    private MapView mapView;
    private RecyclerView recyclerView;
    private List<AddressData> list;
    private AddressAdapter adapter;
    private CheckBox checkBox;
    private RelativeLayout relativeLayout;

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
        mapView = (MapView) findViewById(R.id.mapView);
        View child = mapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {

            child.setVisibility(View.INVISIBLE);
        }
        //地图上比例尺
        mapView.showScaleControl(false);
        // 隐藏缩放控件
        mapView.showZoomControls(false);
        recyclerView= (RecyclerView) findViewById(R.id.address_recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            list.add(new AddressData("Title"+i,"Address"+i));
        }
        adapter=new AddressAdapter(this,list);
        recyclerView.setAdapter(adapter);
        checkBox= (CheckBox) findViewById(R.id.checkbox_address);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                }else {
                }
            }
        });
        checkBox.setChecked(false);
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
}
