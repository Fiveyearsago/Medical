package com.jy.medical.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.jy.medical.R;
import com.jy.medical.fragment.BodyFragment;

import java.util.ArrayList;
import java.util.List;

public class MaimHeadActivity extends BaseActivity {
    private List<Bitmap> bitmapList = new ArrayList<>();
    private List<Bitmap> bitmapPreList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();
    private int position = 0;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_maim_head;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        new Thread(){
            @Override
            public void run() {
                ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
                ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
                ImageView imageView3 = (ImageView) findViewById(R.id.imageView3);
                ImageView imageView4 = (ImageView) findViewById(R.id.imageView4);
                imageViewList.add(imageView1);
                imageViewList.add(imageView2);
                imageViewList.add(imageView3);
                imageViewList.add(imageView4);
                bitmapList.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.head_ear_normal)).getBitmap());
                bitmapList.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.head_eye_normal)).getBitmap());
                bitmapList.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.head_mouse_normal)).getBitmap());
                bitmapList.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.head_nouse_normal)).getBitmap());
                bitmapPreList.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.head_ear_pressed)).getBitmap());
                bitmapPreList.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.head_eye_pressed)).getBitmap());
                bitmapPreList.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.head_mouse_pressed)).getBitmap());
                bitmapPreList.add(((BitmapDrawable)getResources().getDrawable(R.mipmap.head_nouse_pressed)).getBitmap());
                for (int i = 0; i < imageViewList.size(); i++) {
                    imageViewList.get(i).setOnTouchListener(new MyOnTouchListener());
                }
            }
        }.start();
    }
    public class MyOnTouchListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    for (int i = 0; i < bitmapList.size(); i++) {
                        if (bitmapList.get(i).getPixel((int) (event.getX()), ((int) event.getY())) != 0) {
                            Log.i("imageView", v.getId() + "实体");
                            imageViewList.get(i).setImageBitmap(bitmapPreList.get(i));
                            position = i;
                        } else {
                            imageViewList.get(i).setImageBitmap(bitmapList.get(i));
                        }
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("imageView", v.getId() + "UP");
                    imageViewList.get(position).setImageBitmap(bitmapList.get(position));
                    break;
                default:
                    break;

            }
            return false;
        }

    }
    @Override
    public void widgetClick(View v) {

    }
}
