package com.jy.medical.dao;


import android.os.Environment;

import org.xutils.DbManager;
import org.xutils.x;

import java.io.File;

public class DbHelper {
    private static DbHelper dbHelper;

    private static DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
            .setDbName("medical.db")
            .setDbDir(new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/jyds/database"))
            .setDbVersion(1)
            .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                @Override
                public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                }
            });

    public static DbHelper getInstance() {
        if (dbHelper == null) {
            dbHelper = new DbHelper();
//            if(SDCardUtils.isMounted()){
//                Logger.e(SDCardUtils.getRootStringPath());
//                daoConfig.setDbDir(new File(SDCardUtils.getRootStringPath()+File.separator+"longding"));
//            }
        }
        return dbHelper;
    }

    public DbManager getDbManger() {
        DbManager db = x.getDb(daoConfig);
        return db;
    }

}
