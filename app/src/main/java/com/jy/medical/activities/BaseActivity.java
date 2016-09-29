package com.jy.medical.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jy.medical.R;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.zip.Inflater;

import cn.smssdk.SMSSDK;

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        int layoutId = getLayoutId();
        if (layoutId != 0) {
            setContentView(layoutId);
   }

//        setContentView(R.layout.activity_base);
        Log.d(TAG, "BaseActivity-->onCreate()");
        Bundle bundle = getIntent().getExtras();
        initParms(bundle);
//        PgyCrashManager.register(this);
//        PgyUpdateManager.register(this);
        SMSSDK.initSDK(this, "1792404991e59", "95ce745b999dc842803436b2d50161b2");
        initView();
        initData();
    }

    /**
     * 布局文件ID
     * @return
     */
    public abstract void initData();
    /**
     * 布局文件ID
     * @return
     */
    public abstract int getLayoutId();
    /**
     * [初始化参数]

     * @param parms
     */
    public abstract void initParms(Bundle parms);
    /**
     * [初始化控件]
     *
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

    public void setEditTextSelection(EditText editText){
        if (editText!=null)
        {
            editText.setSelection(editText.getText().length());
        }
    }
    public void clearEditTextValue(final EditText editText){
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
                        - drawable.getIntrinsicWidth()){
                    editText.setText("");
                }
                return false;
            }
        });

    }
}
