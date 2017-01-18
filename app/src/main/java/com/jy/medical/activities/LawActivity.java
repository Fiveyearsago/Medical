package com.jy.medical.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.R;
import com.jy.medical.adapter.LawFragmentPagerAdapter;
import com.jy.medical.widget.SwipeBackLayout;

public class LawActivity extends BaseActivity {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private LawFragmentPagerAdapter adapter;
    private TextView cityTV;


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_law;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setNavState(findViewById(R.id.title_head_second),"法律法规");
        cityTV= (TextView) findViewById(R.id.page_head_button);
        slidingTabLayout= (SlidingTabLayout) findViewById(R.id.tabLayout_law);
        viewPager= (ViewPager) findViewById(R.id.viewPager_law);
        adapter = new LawFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager,new String[]{"临床鉴定","精神损害","道路交通","民事诉讼","临床鉴定","精神损害"});
        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                slidingTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setCurrentItem(0);
    }


    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_second_image:
                finish();
                break;
            case R.id.nav_layout:
                Intent intent=new Intent(this,SelectCityActivity.class);
                intent.putExtra("currentCity", cityTV.getText());
                startActivityForResult(intent,0x10);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case 0x10:
                    cityTV.setText(data.getStringExtra("province"));
                    break;
            }
        }

    }
}
