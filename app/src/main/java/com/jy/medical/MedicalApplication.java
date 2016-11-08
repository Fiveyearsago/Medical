package com.jy.medical;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.SDKInitializer;
import com.jy.medical.greendao.manager.DaoManager;
import com.jy.medical.greendao.gen.DaoSession;
import com.jy.medical.greendao.util.DBUtils;
import com.jy.medical.greendao.util.DaoUtils;
import com.pgyersdk.crash.PgyCrashManager;

import org.xutils.x;

import java.util.LinkedList;
import java.util.List;

/**
 * Description
 * Created by songran on 16/9/22.
 */
public class MedicalApplication extends Application {
    private List<AppCompatActivity> activityList = new LinkedList<>();
    private static MedicalApplication instance;
//    private MedicalApplication(){ }
    //单例模式中获取唯一的MyApplication实例
    public static MedicalApplication getInstance() {
        if(null == instance) {
            instance = new MedicalApplication();
        }
        return instance;
    }
    //添加Activity到容器中
    public void addActivity(AppCompatActivity activity)  {
        activityList.add(activity);
    }
    //遍历所有Activity并finish
    public void exit(){
        for(AppCompatActivity activity:activityList) {
            activity.finish();
        }
        System.exit(0);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
        SDKInitializer.initialize(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
        DaoUtils.init(getApplicationContext());
        DaoManager.init(getApplicationContext());
        DaoManager daoMaster = DaoManager.getInstance();
        DaoSession session = daoMaster.getDaoSession();
        DBUtils.getInstance().setDaoSession(session);

    }
}
