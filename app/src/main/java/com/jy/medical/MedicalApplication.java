package com.jy.medical;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.pgyersdk.crash.PgyCrashManager;
import com.pgyersdk.update.PgyUpdateManager;

import org.xutils.x;

/**
 * Description
 * Created by songran on 16/9/22.
 */
public class MedicalApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register(this);
        SDKInitializer.initialize(this);
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志, 开启debug会影响性能.
    }
}
