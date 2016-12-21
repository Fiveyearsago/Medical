package com.jy.medical.fragment;

import android.content.Context;
import android.content.Intent;
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
import com.jy.medical.activities.MaimHeadActivity;
import com.jy.medical.activities.SelectDiagnoseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by songran on 16/11/17.
 */

public class BodyFragment extends Fragment {
    private static BodyFragment bodyFragment;
    private List<Bitmap> bitmapList = new ArrayList<>();
    private List<Bitmap> bitmapPreList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();
    private ImageView switchBtn;
    private View frontView, headView, backView;
    private List<Bitmap> bitmapBackList = new ArrayList<>();
    private List<Bitmap> bitmapBackPreList = new ArrayList<>();
    private List<ImageView> imageViewBackList = new ArrayList<>();
    private List<Bitmap> bitmapHeadList = new ArrayList<>();
    private List<Bitmap> bitmapHeadPreList = new ArrayList<>();
    private List<ImageView> imageViewHeadList = new ArrayList<>();
    private int positionBack = -1;
    private int position = -1;
    private int positionHead = -1;
    private boolean headFlag = false;
    private boolean frontFlag = true;
    private boolean backFlag = true;
    private Map<String, String> map = new HashMap<String, String>();
    private String taskNo;

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
        taskNo = getArguments().getString("taskNo");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Bundle data = getArguments();//获得从activity中传递过来的值
        initMap();
        final View view = inflater.inflate(R.layout.fragment_body, null);
        frontView = view.findViewById(R.id.front_layout);
        headView = view.findViewById(R.id.head_layout);
        backView = view.findViewById(R.id.back_layout);
        switchBtn = (ImageView) view.findViewById(R.id.switch_btn);
        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //翻转人体
                Log.i("switch", "switch");
                if (frontFlag) {
                    frontView.setVisibility(View.GONE);
                    backView.setVisibility(View.VISIBLE);
                    frontFlag = false;
                    backFlag = true;
                    position = -1;
                    positionBack = -1;
                    positionHead = -1;
                } else if (headFlag) {
                    headView.setVisibility(View.GONE);
                    frontView.setVisibility(View.VISIBLE);
                    headFlag = false;
                    frontFlag = true;
                    position = -1;
                    positionBack = -1;
                    positionHead = -1;
                    switchBtn.setBackground(getContext().getResources().getDrawable(R.drawable.switch_selector));
                } else if (backFlag) {
                    frontView.setVisibility(View.VISIBLE);
                    backView.setVisibility(View.GONE);
                    frontFlag = true;
                    backFlag = false;
                    position = -1;
                    positionBack = -1;
                    positionHead = -1;
                }
            }
        });
        new Thread() {
            @Override
            public void run() {
                ImageView imageView1 = (ImageView) view.findViewById(R.id.imageView1);
                ImageView imageView2 = (ImageView) view.findViewById(R.id.imageView2);
                ImageView imageView3 = (ImageView) view.findViewById(R.id.imageView3);
                ImageView imageView4 = (ImageView) view.findViewById(R.id.imageView4);
                ImageView imageView5 = (ImageView) view.findViewById(R.id.imageView5);
                ImageView imageView6 = (ImageView) view.findViewById(R.id.imageView6);
                ImageView imageView7 = (ImageView) view.findViewById(R.id.imageView7);
                imageViewList.clear();
                imageViewList.add(imageView1);
                imageViewList.add(imageView2);
                imageViewList.add(imageView3);
                imageViewList.add(imageView4);
                imageViewList.add(imageView5);
                imageViewList.add(imageView6);
                imageViewList.add(imageView7);
                Bitmap bitmap = ((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_1)).getBitmap();
                bitmapList.clear();
                bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_1)).getBitmap());
                bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_2)).getBitmap());
                bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_3)).getBitmap());
                bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_4)).getBitmap());
                bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_5)).getBitmap());
                bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_6)).getBitmap());
                bitmapList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_front_7)).getBitmap());
                bitmapPreList.clear();
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
            }
        }.start();
        new Thread() {
            @Override
            public void run() {

                ImageView imageView1 = (ImageView) view.findViewById(R.id.imageViewHead1);
                ImageView imageView2 = (ImageView) view.findViewById(R.id.imageViewHead2);
                ImageView imageView3 = (ImageView) view.findViewById(R.id.imageViewHead3);
                ImageView imageView4 = (ImageView) view.findViewById(R.id.imageViewHead4);
                imageViewHeadList.clear();
                imageViewHeadList.add(imageView1);
                imageViewHeadList.add(imageView2);
                imageViewHeadList.add(imageView3);
                imageViewHeadList.add(imageView4);
                bitmapHeadList.clear();
                bitmapHeadList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.head_ear_normal)).getBitmap());
                bitmapHeadList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.head_eye_normal)).getBitmap());
                bitmapHeadList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.head_mouse_normal)).getBitmap());
                bitmapHeadList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.head_nouse_normal)).getBitmap());
                bitmapHeadPreList.clear();
                bitmapHeadPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.head_ear_pressed)).getBitmap());
                bitmapHeadPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.head_eye_pressed)).getBitmap());
                bitmapHeadPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.head_mouse_pressed)).getBitmap());
                bitmapHeadPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.head_nouse_pressed)).getBitmap());
                for (int i = 0; i < imageViewHeadList.size(); i++) {
                    imageViewHeadList.get(i).setOnTouchListener(new MyOnTouchListener1());
                }
            }
        }.start();
        new Thread() {
            @Override
            public void run() {

                ImageView imageView1 = (ImageView) view.findViewById(R.id.imageViewBack1);
                imageViewBackList.clear();
                imageViewBackList.add(imageView1);
                bitmapBackList.clear();
                bitmapBackList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_back_normal)).getBitmap());
                bitmapBackPreList.clear();
                bitmapBackPreList.add(((BitmapDrawable) getContext().getResources().getDrawable(R.mipmap.body_back_pressed)).getBitmap());
                for (int i = 0; i < imageViewBackList.size(); i++) {
                    imageViewBackList.get(i).setOnTouchListener(new MyOnTouchListener2());
                }
            }
        }.start();
        return view;
    }

    private void initMap() {
        //1：颈部 2：胸部 3：腹部 4：泌尿 5：骨盆 6：四肢 7：背部 8：眼 9：鼻子 10：耳朵 11：嘴巴
        map.put("1", "54860479063D4211A2D616BCE23330EC");
        map.put("2", "C578B4EE3DF0462F84B3A3BD5C1C96E2");
        map.put("3", "A1C96BEF5B584A40B966C9B45B35127D");
        map.put("4", "2B89B7E51BCD431ABF22B588A9BA08F0");
        map.put("5", "DD155DC05A18473DA4F752C97D785B68");
        map.put("6", "A345104D021749108245D74F1B0CD7EE");
        map.put("7", "B6ABE74DA6C04899A6DF2E98C3CC5915");
        map.put("8", "F394E77BE92E4B2187CB055CECA5067B");
        map.put("9", "543A8D57F891496C8A5B7E3B3A9EC15C");
        map.put("10", "205976AF6D134B4FB5D2E8C5FCA3E5F5");
        map.put("11", "1B640DAFBB7A43DDA74F4A7361F55732");

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
                    if (position != -1) {
                        imageViewList.get(position).setImageBitmap(bitmapList.get(position));
                        switch (position) {
                            case 0:
                                headFlag = true;
                                frontFlag = false;
                                position = -1;
                                positionBack = -1;
                                frontView.setVisibility(View.GONE);
                                headView.setVisibility(View.VISIBLE);
                                switchBtn.setBackground(getContext().getResources().getDrawable(R.drawable.body_return_selector));
//                            startActivity(new Intent(getContext(), MaimHeadActivity.class));
                                break;
                            case 1:
                                //颈部
                                selectDiagnose("1","颈部");
                                break;
                            case 2:
                                //胸部
                                selectDiagnose("2","胸部");
                                break;
                            case 3:
                                //腹部
                                selectDiagnose("3","腹部");
                                break;
                            case 4:
                                //骨盆
                                selectDiagnose("5","骨盆");
                                break;
                            case 5:
                                //泌尿
                                selectDiagnose("4","泌尿生殖");
                                break;
                            case 6:
                                //四肢
                                selectDiagnose("6","四肢");
                                break;
                        }
                    }
                    break;
                default:
                    break;

            }
            return false;
        }

    }

    private void selectDiagnose(String code,String name) {
        Log.i("code", map.get(code));
        Intent intent = new Intent(getContext(), SelectDiagnoseActivity.class);
        intent.putExtra("kindCode", map.get(code));
        intent.putExtra("name", name);
        intent.putExtra("taskNo", taskNo);
        getContext().startActivity(intent);
    }

    public class MyOnTouchListener1 implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    for (int i = 0; i < bitmapHeadList.size(); i++) {
                        if (bitmapHeadList.get(i).getPixel((int) (event.getX()), ((int) event.getY())) != 0) {
                            Log.i("imageView", v.getId() + "实体");
                            imageViewHeadList.get(i).setImageBitmap(bitmapHeadPreList.get(i));
                            positionHead = i;
                        } else {
                            imageViewHeadList.get(i).setImageBitmap(bitmapHeadList.get(i));
                        }
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    if (positionHead != -1) {
                        Log.i("imageView", v.getId() + "UP");
                        imageViewHeadList.get(positionHead).setImageBitmap(bitmapHeadList.get(positionHead));
                        switch (positionHead) {
                            case 0:
                                //耳朵
                                selectDiagnose("10","耳朵");
                                break;
                            case 1:
                                //眼睛
                                selectDiagnose("8","眼睛");
                                break;
                            case 2:
                                //口腔
                                selectDiagnose("11","口腔");
                                break;
                            case 3:
                                //鼻子
                                selectDiagnose("9","鼻子");
                                break;

                        }
                    }
                    break;
                default:
                    break;

            }
            return false;
        }

    }

    public class MyOnTouchListener2 implements View.OnTouchListener {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    for (int i = 0; i < bitmapBackList.size(); i++) {
                        if (bitmapBackList.get(i).getPixel((int) (event.getX()), ((int) event.getY())) != 0) {
                            Log.i("imageView", v.getId() + "实体");
                            imageViewBackList.get(i).setImageBitmap(bitmapBackPreList.get(i));
                            positionBack = i;
                        } else {
                            imageViewBackList.get(i).setImageBitmap(bitmapBackList.get(i));
                        }
                    }

                    break;
                case MotionEvent.ACTION_UP:
                    if (positionBack != -1) {
                        Log.i("imageView", v.getId() + "UP");
                        imageViewBackList.get(positionBack).setImageBitmap(bitmapBackList.get(positionBack));
                        switch (positionBack) {
                            case 0:
                                selectDiagnose("7","背部");
                                break;

                        }
                    }
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
