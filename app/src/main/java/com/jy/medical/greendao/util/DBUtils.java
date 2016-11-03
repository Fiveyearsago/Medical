package com.jy.medical.greendao.util;


import com.jy.medical.greendao.gen.DaoSession;

/**
 * Created on 15/01/16 by kaio.
 */
public class DBUtils {
    private static DBUtils ourInstance = new DBUtils();

    public static DBUtils getInstance() {
        return ourInstance;
    }

    private DaoSession daoSession;

    private DBUtils() {
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public void setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
    }
}
