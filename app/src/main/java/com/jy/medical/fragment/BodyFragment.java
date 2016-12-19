package com.jy.medical.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jy.medical.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by songran on 16/11/17.
 */

public class BodyFragment extends Fragment {
    private static BodyFragment bodyFragment;
    private List<Bitmap> bitmapList = new ArrayList<>();
    private List<Bitmap> bitmapPreList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();
    int position = 0;
    public static BodyFragment newInstance() {
        if (bodyFragment == null)
            bodyFragment = new BodyFragment();
        return bodyFragment;
    }

    public BodyFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Bundle data = getArguments();//获得从activity中传递过来的值
        View view = inflater.inflate(R.layout.fragment_body,null);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.imageView1);
        ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView2);
        ImageView imageView3 = (ImageView) view.findViewById(R.id.imageView3);
        ImageView imageView4 = (ImageView) view.findViewById(R.id.imageView4);
        ImageView imageView5 = (ImageView) view.findViewById(R.id.imageView5);
        ImageView imageView6 = (ImageView) view.findViewById(R.id.imageView6);
        ImageView imageView7 = (ImageView) view.findViewById(R.id.imageView7);
        imageViewList.add(imageView1);
        imageViewList.add(imageView2);
        imageViewList.add(imageView3);
        imageViewList.add(imageView4);
        imageViewList.add(imageView5);
        imageViewList.add(imageView6);
        imageViewList.add(imageView7);
        Bitmap bitmap=((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_1)).getBitmap();
        bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_1)).getBitmap());
        bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_2)).getBitmap());
        bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_3)).getBitmap());
        bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_4)).getBitmap());
        bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_5)).getBitmap());
        bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_6)).getBitmap());
        bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_7)).getBitmap());

        bitmapPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_1_pressed)).getBitmap());
        bitmapPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_2_pressed)).getBitmap());
        bitmapPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_3_pressed)).getBitmap());
        bitmapPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_4_pressed)).getBitmap());
        bitmapPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_5_pressed)).getBitmap());
        bitmapPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_6_pressed)).getBitmap());
        bitmapPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_7_pressed)).getBitmap());
        for (int i = 0; i < imageViewList.size(); i++) {
            imageViewList.get(i).setOnTouchListener(new MyOnTouchListener());
        }
        return view;
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

}
