package com.jy.medical.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jy.medical.R;
import com.jy.medical.adapter.LawFragmentPagerAdapter;
import com.jy.medical.adapter.LoginFragmentPagerAdapter;
import com.jy.medical.widget.XLinearLayout;

public class LoginActivity extends BaseActivity {
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private LoginFragmentPagerAdapter adapter;
    private LinearLayout xLinearLayout;
    private TextInputEditText textInputEditTextAccount;
    @Override
    public void initData() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public void initView() {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        xLinearLayout= (LinearLayout) findViewById(R.id.login_XLinearLayout);

        slidingTabLayout= (SlidingTabLayout) findViewById(R.id.tabLayout_login);
        viewPager= (ViewPager) findViewById(R.id.viewPager_login);
        adapter = new LoginFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(adapter);
        slidingTabLayout.setViewPager(viewPager,new String[]{"账号登陆","手机验证码登陆"});
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
        final LinearLayout scrollView= (LinearLayout) findViewById(R.id.login_layout);
        final ViewTreeObserver vto = scrollView.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            public void onGlobalLayout() {
//                Rect rect = new Rect();
//                scrollView.getWindowVisibleDisplayFrame(rect);
//
//                int rootInvisibleHeight =xLinearLayout.getRootView().getHeight() - rect.bottom;
//
//                if (rootInvisibleHeight<= 100) {
//                    scrollView.scrollTo(0, -400);
//                }else {
//                    scrollView.scrollTo(0, 400);
//
//                }
//            }
//        });
        xLinearLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();

                xLinearLayout.getWindowVisibleDisplayFrame(rect);

                int rootInvisibleHeight =xLinearLayout.getRootView().getHeight() - rect.bottom;
                Log.i("xLinearLayout",rootInvisibleHeight+"");
                if (rootInvisibleHeight<= 100) {
                    Toast.makeText(LoginActivity.this, "keyboard visible", Toast.LENGTH_SHORT).show();
//                    scrollView.scrollTo(0, 200);
                }else {
//                    scrollView.scrollTo(0, 400);
                    Toast.makeText(LoginActivity.this, "keyboard hidden", Toast.LENGTH_SHORT).show();
                }

            }
        });
        textInputEditTextAccount= (TextInputEditText) findViewById(R.id.editText_account);
        if (textInputEditTextAccount!=null){
            textInputEditTextAccount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if (b){
                        Toast.makeText(LoginActivity.this, "获取焦点", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(LoginActivity.this, "失去焦点", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void widgetClick(View v) {

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        // Checks whether a hardware keyboard is available
        if (newConfig.keyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
        } else if (newConfig.keyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }

}
