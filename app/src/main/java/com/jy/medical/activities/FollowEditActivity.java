package com.jy.medical.activities;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jy.medical.R;
import com.jy.medical.adapter.PictureAdapter;

import java.util.ArrayList;
import java.util.List;

public class FollowEditActivity extends BaseActivity {

    private RecyclerView pictureRecyclerView;
    private PictureAdapter pictureAdapter;
    private List<Bitmap> list;

    @Override
    public void initData() {


    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_follow_edit;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "编辑", true, "保存");
        findViewById(R.id.follow_edit_location).setOnClickListener(this);
        pictureRecyclerView= (RecyclerView) findViewById(R.id.picture_recyclerView);
        pictureRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager=new GridLayoutManager(this,4);
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        list=new ArrayList<>();
        Resources res = getResources();
        for (int i = 0; i < 10; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(res, R.mipmap.add_photo);
            list.add(bmp);
        }
        pictureAdapter=new PictureAdapter(this,list);

        pictureRecyclerView.setLayoutManager(layoutManager);
        pictureRecyclerView.setAdapter(pictureAdapter);

    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                break;
            case R.id.follow_edit_location:
                startActivity(SelectAddressActivity.class);
//                startActivity(MapDemoActivity.class);
                break;
            default:
                break;
        }
    }
}
