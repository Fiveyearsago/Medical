package com.jy.medical.greendao.util;

/**
 * Created by songran on 16/11/3.
 */


import android.content.Context;

import com.jy.medical.greendao.manager.ClaimManager;
import com.jy.medical.greendao.manager.StudentManager;
import com.jy.medical.greendao.manager.TaskManager;

/**
 * Created by jinfangmei on 2016/6/16.
 */
public class DaoUtils {
    private static StudentManager studentManager;
    private static ClaimManager claimManager;
    private static TaskManager taskManager;
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
    public static ClaimManager getClaimInstance() {
        if (claimManager == null) {
            claimManager = new ClaimManager(context);
        }
        return claimManager;
    }

    public static TaskManager getTaskInstance() {
        if (taskManager == null) {
            taskManager = new TaskManager(context);
        }
        return taskManager;
    }
}