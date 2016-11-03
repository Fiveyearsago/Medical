package com.jy.medical;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.baidu.mapapi.SDKInitializer;
import com.jy.medical.greendao.util.DaoManager;
import com.jy.medical.greendao.gen.DaoSession;
import com.jy.medical.greendao.util.DBUtils;
import com.jy.medical.greendao.util.DaoUtils;
import com.pgyersdk.crash.PgyCrashManager;

import org.xutils.x;

/**
 * Description
 * Created by songran on 16/9/22.
 */
public class MedicalApplication extends Application {

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
