package com.jy.medical.greendao.util;

/**
 * Created by songran on 16/11/3.
 */


import android.content.Context;

/**
 * Created by jinfangmei on 2016/6/16.
 */
public class DaoUtils {
    private static StudentManager studentManager;
    public static Context context;

    public static void init(Context context) {
        DaoUtils.context = context.getApplicationContext();
    }


    /**
     * 单列模式获取StudentManager对象
     *
     * @return
     */
    public static StudentManager getStudentInstance() {
        if (studentManager == null) {
            studentManager = new StudentManager(context);
        }
        return studentManager;
    }
}