package com.jy.medical.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.jy.medical.R;

/**
 * Created by songran on 17/1/6.
 */

public class DialogUtil {
    private static Dialog dialog;

    public static void showDialog(Context context) {
        dialog = new Dialog(context, R.style.progress_dialog);
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        TextView msg = (TextView) dialog.findViewById(R.id.id_tv_loadingmsg);
        msg.setText("正在加载中");
        dialog.show();

    }

    public static void dismissDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
