package com.jy.medical.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jy.medical.inter.IBaseFragment;

/**
 * Created by songran on 16/11/23.
 */

public class BaseFragment extends Fragment implements IBaseFragment {
    /**
     * Fragment Content view
     */
    private View inflateView;
    /**
     * 所属Activity
     */
    private AppCompatActivity activity;

    private Context context;
    /**
     * 记录是否已经创建了,防止重复创建
     */
    private boolean viewCreated;
    /**
     * 通过ID查找控件
     *
     * @param viewId 控件资源ID
     * @param <VIEW> 泛型参数，查找得到的View
     * @return 找到了返回控件对象，否则返回null
     */
    final public <VIEW extends View> VIEW findViewById(@IdRes int viewId) {
        return (VIEW) inflateView.findViewById(viewId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    final public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == inflateView) {
            // 强制竖屏显示
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            int layoutResId = getCreateViewLayoutId();
            if (layoutResId > 0)
                inflateView = inflater.inflate(getCreateViewLayoutId(), container, false);

            // 解决点击穿透问题
            inflateView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }
            });
        }
        return inflateView;
    }
    @Override
    final public void onViewCreated(View view, Bundle savedInstanceState) {
        if (viewCreated) {
            findView(view, savedInstanceState);
            initView(view, savedInstanceState);
            initDialog();
            initListener();
            viewCreated = false;
        }
    }
    @Override
    public int getCreateViewLayoutId() {
        return 0;
    }

    @Override
    public void initData() {

    }

    @Override
    public void findView(View inflateView, Bundle savedInstanceState) {

    }

    @Override
    public void initView(View inflateView, Bundle savedInstanceState) {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDialog() {

    }

    @Override
    public void initParams(Bundle params) {

    }
}
