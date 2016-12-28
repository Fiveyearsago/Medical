package com.jy.medical.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jy.medical.R;
import com.jy.medical.util.ToastUtil;
import com.jy.medical.widget.SwipeBackLayout;

import org.json.JSONObject;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ChangePhoneActivity extends BaseActivity {
    private static final int SMSDDK_HANDLER = 3;  //短信回调
    private Button btnShow;
    private int i = 0;
    private int TIME = 1000;
    Timer timer;
    TimerTask task;
    private EventHandler eventHandler;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                btnShow.setText(Integer.toString(60 - (++i)) + "s");
                if (i == 60) {
                    btnShow.setEnabled(true);
                    if (timer != null)
                        timer.cancel();
                    if (task != null)
                        task.cancel();
                    i = 0;
                    btnShow.setText("获取验证码");
                    btnShow.setTextColor(Color.parseColor("#3282F0"));
                }
            }
            if (msg.what == 3) {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                //回调完成
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //验证码验证成功
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        ToastUtil.showToast(ChangePhoneActivity.this, "验证成功");
//                        Toast.makeText(ChangePhoneActivity.this, "验证成功", Toast.LENGTH_LONG).show();
                        if (true)//其他合法性的检测
                        {
//                            SMSSDK.submitVerificationCode("86", "15239475273", "2562");//对验证码进行验证->回调函数
                        }

                    }
                    //已发送验证码
                    else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        ToastUtil.showToast(ChangePhoneActivity.this, "验证码已经发送");
//                        Toast.makeText(getApplicationContext(), "验证码已经发送",
//                                Toast.LENGTH_SHORT).show();
                    } else {
                        ((Throwable) data).printStackTrace();
                    }
                }
                if (result == SMSSDK.RESULT_ERROR) {
                    try {
                        Throwable throwable = (Throwable) data;
                        throwable.printStackTrace();
                        JSONObject object = new JSONObject(throwable.getMessage());
                        String des = object.optString("detail");//错误描述
                        int status = object.optInt("status");//错误代码
                        if (status > 0 && !TextUtils.isEmpty(des)) {
                            ToastUtil.showToast(ChangePhoneActivity.this, des);
//                            Toast.makeText(getApplicationContext(), des, Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } catch (Exception e) {
                        //do something
                    }
                }
                super.handleMessage(msg);
            }
        }
        ;
    };

    @Override
    public void initData() {
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_change_phone;
    }

    @Override
    public void initParams(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
        setTitleState(findViewById(R.id.title_head), true, "更改手机号", true, "确定");
        btnShow = (Button) findViewById(R.id.btn_get_code);
        findViewById(R.id.person_phone_sure).setOnClickListener(this);
        btnShow.setOnClickListener(this);
        initSDK();
        SMSSDK.registerEventHandler(eventHandler);
    }

    @Override
    public void widgetClick(View v) {

        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.btn_get_code:

                if (true) {
                    SMSSDK.getVerificationCode("86", "18612235095");
//                    registerUser("86", "18612235095");
                    btnShow.setEnabled(false);
                    btnShow.setTextColor(Color.parseColor("#BBBBBB"));
                    timer = new Timer();
                    task = new TimerTask() {

                        @Override
                        public void run() {
                            // 需要做的事:发送消息
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    };
                    timer.schedule(task, 200, 1000);
                }
                break;
            case R.id.person_phone_sure:
                EditText editText=(EditText)findViewById(R.id.peron_edit_code);
                SMSSDK.submitVerificationCode("86", "18612235095", editText.getText().toString().trim());//对验证码进行验证->回调函数
                break;
            default:
                break;
        }
    }

    // 提交用户信息
//    private void registerUser(String country, String phone) {
//        SMSSDK.initSDK(this, "1792404991e59", "95ce745b999dc842803436b2d50161b2");
//        SMSSDK.registerEventHandler(eh); //注册短信回调
//        Random rnd = new Random();
//        int id = Math.abs(rnd.nextInt());
//        String uid = String.valueOf(id);
//        String nickName = "SmsSDK_User_" + uid;
//        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
//    }
    private void initSDK() {
        SMSSDK.initSDK(this, "1792404991e59", "95ce745b999dc842803436b2d50161b2");
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                msg.what = SMSDDK_HANDLER;
                handler.sendMessage(msg);
            }
        };
    }

}
