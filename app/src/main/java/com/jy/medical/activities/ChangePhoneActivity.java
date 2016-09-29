package com.jy.medical.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jy.medical.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ChangePhoneActivity extends BaseActivity {
    Button btnShow;
    private int i = 0;
    private int TIME = 1000;
    Timer timer;
    TimerTask task;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                btnShow.setText(Integer.toString(60 - (++i)) + "S");
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
            super.handleMessage(msg);
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
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "更改手机号", true, "确定");
        btnShow = (Button) findViewById(R.id.btn_get_code);
        btnShow.setOnClickListener(this);
    }

    @Override
    public void widgetClick(View v) {

        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
            case R.id.btn_get_code:
                if (true) {
                    registerUser("+86", "18612235095");
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
            default:
                break;
        }
    }

    // 提交用户信息
    private void registerUser(String country, String phone) {
        SMSSDK.initSDK(this, "1792404991e59", "95ce745b999dc842803436b2d50161b2");
        SMSSDK.registerEventHandler(eh); //注册短信回调
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "SmsSDK_User_" + uid;
        SMSSDK.submitUserInfo(uid, nickName, null, country, phone);
    }

    EventHandler eh = new EventHandler() {

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.i("code", "提交验证码成功");
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Log.i("code", "获取验证码成功");
//                    SMSSDK.unregisterAllEventHandler();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };

}
