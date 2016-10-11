package com.jy.medical.widget;



import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
/**
 * Created by songran on 16/10/11.
 */
public class XLinearLayout extends LinearLayout {
    private static final String TAG = "XLinearLayout" ;
    private OnKeyboardChangeListener listener ;

    public XLinearLayout(Context context) {
        super(context);
    }

    public XLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (listener == null) {
            return ;
        }
        //横竖屏切换 old width 和 old height都会被系统重置为0
        if (oldh != 0) {
            /**
             * 如果布局改变后高度差达到100,就判断是键盘弹出。需要注意的是,100只是一个大概的值
             * 因为Android的键盘情况比较复杂,没有统一的大小,也没有获取高度的api,只能大概判断
             * 一般情况下,键盘高度会大于100,如果你的布局存在特殊情况,要根据自己的情况设置
             */
            if (oldh - h >= 100) {
                listener.onKeyboardShow();
            } else {
                listener.onKeyboardHide();
            }
        }
    }

    public void setOnKeyboardChangeListener(OnKeyboardChangeListener listener) {
        this.listener = listener ;
    }

    public interface OnKeyboardChangeListener {
        void onKeyboardShow() ;
        void onKeyboardHide() ;
    }
}