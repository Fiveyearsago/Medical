package com.jy.medical.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * 跟踪详情
 */
public class FollowDetailActivity extends BaseActivity {
    private TextView taskTimeTab;
    private int dayNum=3;

    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_follow_detail;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
        setStatusBarTint();
        setTitleState(findViewById(R.id.title_head), true, "跟踪详情", false, "");
        taskTimeTab= (TextView) findViewById(R.id.task_time_tag);
        setTaskTimeText(true);
    }

    @Override
    public void widgetClick(View v) {
        switch (v.getId()) {
            case R.id.page_head_image:
                finish();
                break;
        }
    }

    public void setTaskTimeText(boolean flag){
        if (flag){
            SpannableString styledText = new SpannableString("已超时"+dayNum+"天");
            styledText.setSpan(new TextAppearanceSpan(this, R.style.textTimeoutTab), 3, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            taskTimeTab.setText(styledText, TextView.BufferType.SPANNABLE);
        }else {
            SpannableString styledText = new SpannableString(dayNum+"天后超时");
            styledText.setSpan(new TextAppearanceSpan(this, R.style.textNormalTab), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            taskTimeTab.setText(styledText, TextView.BufferType.SPANNABLE);
        }
    }
}
