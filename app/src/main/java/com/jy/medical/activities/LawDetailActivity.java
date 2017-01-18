package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.jy.medical.R;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.SwipeBackLayout;

/**
 * 法律法规详情页
 */
public class LawDetailActivity extends BaseActivity {
    private CheckBox checkBox;


    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_law_detail;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setCheckNavState(findViewById(R.id.title_head_third), "详情", R.drawable.nav_image_selector, R.drawable.collect_image_selector);
        checkBox = (CheckBox) findViewById(R.id.page_third_head_collect);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_third_head_image:
                finish();
                break;
            case R.id.page_third_head_collect:
                if (checkBox.isChecked()) {
                    ToastUtil.showToast(this, "收藏成功");
                } else {
                    ToastUtil.showToast(this, "已取消收藏");
                }
                break;
        }
    }
}
