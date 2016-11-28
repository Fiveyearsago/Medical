package com.jy.medical.util;

import android.content.Context;
import android.view.ViewGroup;

import com.jy.medical.R;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;

/**
 * Created by songran on 16/11/21.
 */

public class SwipeMenuUtil {
    private static Context mContext;
    private static SwipeMenuCreator swipeMenuCreator1 = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeLeftMenu, com.yanzhenjie.recyclerview.swipe.SwipeMenu swipeRightMenu, int viewType) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.creator_width);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem editItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(R.drawable.creator_edit_selector)
                    .setImage(R.mipmap.creator_edit)
                    .setWidth(width)
                    .setHeight(height);
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(R.drawable.creator_delete_selector)
                    .setImage(R.mipmap.creator_delete)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(editItem);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };
    private static  SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.creator_width);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(R.drawable.creator_delete_selector)
                    .setImage(R.mipmap.creator_delete)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };
    private static  SwipeMenuCreator swipeMenuCreatorEdit = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = mContext.getResources().getDimensionPixelSize(R.dimen.creator_width_44);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(mContext)
                    .setBackgroundDrawable(R.drawable.creator_delete_selector)
                    .setImage(R.mipmap.creator_delete)
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);
        }
    };
    public static  SwipeMenuCreator getSwipeMenuDelete(Context context){
        mContext=context;
        return swipeMenuCreator1;
    }
    public static  SwipeMenuCreator getSwipeMenuEdit(Context context){
        mContext=context;
        return swipeMenuCreator;
    }
    public static  SwipeMenuCreator getSwipeMenuEdit44(Context context){
        mContext=context;
        return swipeMenuCreatorEdit;
    }
}
