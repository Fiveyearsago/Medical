package com.jy.medical.activities;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.adapter.PhotoPreAdapter;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.manager.TaskPhotoManager;
import com.jy.medical.greendao.util.DaoUtils;
import com.jy.medical.util.PhotoUtil;
import com.jy.medical.widget.SwipeBackLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 图片预览
 */
public class PhotoPreActivity extends BaseActivity {
    private int index;
    private TaskPhotoManager taskPhotoManager;
    private String taskNo;
    private List<TaskPhoto> pictureList;
    private List<Bitmap> list;
    private PhotoPreAdapter adapter;
    private ViewPager viewPager;
    private TextView textTitle;
    private Boolean first = true;
    private Boolean deleteFlag;
    private TextView deleteText;
    private Handler handler;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_photo_pre;
    }

    @Override
    public void initParams(Bundle parms) {
        index = parms.getInt("index");
        taskNo = parms.getString("taskNo");
        deleteFlag = parms.getBoolean("deleteFlag");
    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        MedicalApplication.getInstance().addActivity(this);
        View view = findViewById(R.id.title_head);
        setTitleState(view, true, "", true, "删除");
        deleteText = (TextView) view.findViewById(R.id.page_head_button);
        if (deleteFlag)
            deleteText.setVisibility(View.VISIBLE);
        else
            deleteText.setVisibility(View.GONE);
        textTitle = (TextView) findViewById(R.id.page_head_text);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        pictureList = new ArrayList<>();
        list = new ArrayList<>();


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (first && positionOffset == 0 && positionOffsetPixels == 0) {
                    onPageSelected(index);
                    first = false;
                }
            }

            @Override
            public void onPageSelected(int position) {
                textTitle.setText((position + 1) + "/" + pictureList.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setPhotoData();
//        viewPager.setCurrentItem(index);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                adapter = new PhotoPreAdapter(PhotoPreActivity.this, list);
                viewPager.setAdapter(adapter);
            }
        };
//        textTitle.setText((index+1) + "/" + pictureList.size());
    }

    private void setPhotoData() {
        Thread thread=new Thread(){
            @Override
            public void run() {
                pictureList.clear();
                list.clear();
                taskPhotoManager = DaoUtils.getTaskPhotoInstance();
                pictureList = taskPhotoManager.selectAllPhoto(taskNo);
                for (int i = 0; i < pictureList.size(); i++) {
                    Bitmap bmp = PhotoUtil.getSDCardImg(pictureList.get(i).getPhotoPath());
                    list.add(bmp);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new PhotoPreAdapter(PhotoPreActivity.this, list);
                        viewPager.setAdapter(adapter);
                        viewPager.setCurrentItem(index);
                    }
                });
            }
        };
        thread.start();
//        pictureList.clear();
//        list.clear();
//        taskPhotoManager = DaoUtils.getTaskPhotoInstance();
//        pictureList = taskPhotoManager.selectAllPhoto(taskNo);
//        for (int i = 0; i < pictureList.size(); i++) {
////            Bitmap bmp = PhotoUtil.convertToBitmap(pictureList.get(i).getPhotoPath(), 480, 640);
//            Bitmap bmp = PhotoUtil.getSDCardImg(pictureList.get(i).getPhotoPath());
////            Bitmap bmp = PhotoUtil.getNativeImage(pictureList.get(i).getPhotoPath());
//            list.add(bmp);
//        }
//        adapter = new PhotoPreAdapter(this, list);
//        viewPager.setAdapter(adapter);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.page_head_button:
                //删除照片
                deletePhoto();

                break;
        }
    }

    private void deletePhoto() {
        int currentIndex = viewPager.getCurrentItem();
        taskPhotoManager.deleteObject(pictureList.get(currentIndex));
//        viewPager.removeViewAt(currentIndex);
        pictureList.remove(currentIndex);
        list.remove(currentIndex);
        adapter.notifyDataSetChanged();
//        setPhotoData();
        if (pictureList.size() == 0) {
            finish();
        } else {
            if (currentIndex > 0) {
                viewPager.setCurrentItem(currentIndex - 1, true);
                textTitle.setText((currentIndex) + "/" + pictureList.size());
            } else {
                textTitle.setText((currentIndex+1) + "/" + pictureList.size());
            }
        }

    }
}
