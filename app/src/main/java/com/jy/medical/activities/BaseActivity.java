package com.jy.medical.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.mapapi.SDKInitializer;
import com.jy.medical.MedicalApplication;
import com.jy.medical.R;
import com.jy.medical.widget.CleanableEditText;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.zip.Inflater;

import cn.smssdk.SMSSDK;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();
    //应用是否销毁标志
    protected boolean isDestroy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        SMSSDK.initSDK(this, "1792404991e59", "95ce745b999dc842803436b2d50161b2");
//        MedicalApplication.getInstance().addActivity(this);
//        SDKInitializer.initialize(getApplicationContext());
        isDestroy=false;
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
        }
        Log.d(TAG, "BaseActivity-->onCreate()");
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
//        PgyCrashManager.register(this);
//        PgyUpdateManager.register(this);
        initView();
        initData();
    }


    /**
     * 布局文件ID
     *
     * @return
     */
    public abstract void initData();

    /**
     * 布局文件ID
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * [初始化参数]
     *
     * @param parms
     */
    public abstract void initParms(Bundle parms);

    /**
     * [初始化控件]
     */
    public abstract void initView();

    /**
     * View点击
     **/
    public abstract void widgetClick(View v);

    @Override
    public void onClick(View v) {
        widgetClick(v);
    }

    /**
     * [页面跳转]
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * [携带数据的页面跳转]
     *
     * @param clz
     * @param bundle
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    public void exit(){
        MedicalApplication.getInstance().exit();
    }
    public void setThirdNavState(View navView,String textTitle,int leftDrawable,int rightDrawable) {
        ImageView navImage = (ImageView) navView.findViewById(R.id.page_third_head_image);
        ImageView collectImage = (ImageView) navView.findViewById(R.id.page_third_head_collect);
        TextView textViewTitle= (TextView) navView.findViewById(R.id.page_third_head_text);
        navImage.setBackground(getResources().getDrawable(leftDrawable));
        collectImage.setBackground(getResources().getDrawable(rightDrawable));
        textViewTitle.setText(textTitle);
        navImage.setOnClickListener(this);
        collectImage.setOnClickListener(this);
    }
    public void setNavState(View navView, String titleText) {
        ImageView navImage = (ImageView) navView.findViewById(R.id.page_head_second_image);
        TextView navText = (TextView) navView.findViewById(R.id.page_head_second_text);
        navText.setText(titleText);
        View view = navView.findViewById(R.id.nav_layout);
        navImage.setOnClickListener(this);
        view.setOnClickListener(this);
    }
    public void setLocationNavState(View navView,boolean flag, String titleText) {
        ImageView navBack = (ImageView) navView.findViewById(R.id.page_head_image);
        if (!flag){
            navBack.setVisibility(View.GONE);
        }
        TextView navText = (TextView) navView.findViewById(R.id.page_head_button);
        navText.setText(titleText);
        navBack.setOnClickListener(this);
        navText.setOnClickListener(this);
    }
    public void setSearchTitle(View navView, String titleText) {
        TextView navText = (TextView) navView.findViewById(R.id.page_head_search_button);
        navText.setText(titleText);
        navText.setOnClickListener(this);
    }
    /**
     * Description 设置Title状态
     * Author songran
     * Created at 16/9/28 下午3:00
     */
    public void setTitleState(View navView, boolean imageFlag, String titleText, boolean buttonFlag, String buttonText) {
        ImageView navImage;
        TextView navText;
        TextView navButton;
        navImage = (ImageView) navView.findViewById(R.id.page_head_image);
        navText = (TextView) navView.findViewById(R.id.page_head_text);
        navButton = (TextView) navView.findViewById(R.id.page_head_button);
        if (imageFlag) {
            navImage.setVisibility(View.VISIBLE);
        } else {
            navImage.setVisibility(View.GONE);
        }
        navText.setText(titleText);
        if (buttonFlag) {
            navButton.setVisibility(View.VISIBLE);
        } else {
            navButton.setVisibility(View.GONE);
        }
        navButton.setText(buttonText);
        navImage.setOnClickListener(this);
        navButton.setOnClickListener(this);
    }


    /**
     * Description 沉淀式状态栏适配KITKAT以上系统
     * Author songran
     * Created at 16/9/28 下午3:01
     */
    public void setStatusBarTint() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window mWindow = getWindow();
            mWindow.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            mWindow.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            SystemBarTintManager mSystemBarTintManager = new SystemBarTintManager(this);
            mSystemBarTintManager.setStatusBarTintEnabled(true);
//            mSystemBarTintManager.setNavigationBarTintEnabled(true);//虚拟键
            mSystemBarTintManager.setTintColor(Color.parseColor("#0F5BC4"));
            ViewGroup rootView = (ViewGroup) ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    public void setEditTextSelection(EditText editText) {
        if (editText != null) {
            editText.setSelection(editText.getText().length());
        }
    }
    public void setCleanableEditTextSelection(CleanableEditText editText) {
        if (editText != null) {
            editText.setSelection(editText.getText().length());
        }
    }
    public void clearEditTextValue(final EditText editText) {
        editText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = editText.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > editText.getWidth()
                        - editText.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    editText.setText("");
                    editText.setCompoundDrawables(null,null,null,null);
                }
                return false;
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroy=true;
    }
    // 获取点击事件
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    // 判定是否需要隐藏
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = { 0, 0 };
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top
                    && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    // 隐藏软键盘
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token,
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
