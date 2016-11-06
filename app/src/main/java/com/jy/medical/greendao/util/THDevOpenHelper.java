package com.jy.medical.greendao.util;

/**
 * Created by songran on 16/11/4.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.jy.medical.greendao.gen.ClaimBeanDataDao;
import com.jy.medical.greendao.gen.DaoMaster;
import com.jy.medical.greendao.gen.TaskBeanDataDao;

/**
 * 封装DaoMaster.OpenHelper方法, 在更新的时候,用来保存原来的数据
 * greenDao默认在更新的时候,会新建表,原来的数据就丢失了
 * Created by cg on 2015/12/28.
 */
public class THDevOpenHelper extends DaoMaster.OpenHelper {

    public THDevOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (newVersion) {
            case 3:
                //创建新表，注意createTable()是静态方法
                MigrationHelper.migrate(db,ClaimBeanDataDao.class,TaskBeanDataDao.class);
                // TODO
                break;
        }
    }
}
