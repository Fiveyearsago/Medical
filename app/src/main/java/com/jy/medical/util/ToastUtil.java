package com.jy.medical.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by songran on 16/11/28.
 */

public class ToastUtil {
    public static void showToast(Context context,String info){
        Toast toast= Toast.makeText(context, info, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
