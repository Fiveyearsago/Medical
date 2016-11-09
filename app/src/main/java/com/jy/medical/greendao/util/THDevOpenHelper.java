package com.jy.medical.greendao.util;

/**
 * Created by songran on 16/11/4.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.gen.ClaimBeanDataDao;
import com.jy.medical.greendao.gen.ContactDataDao;
import com.jy.medical.greendao.gen.DaoMaster;
import com.jy.medical.greendao.gen.TaskBeanDataDao;
import com.jy.medical.greendao.gen.TaskPhotoDao;

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
        Log.i("newVersion",newVersion+"");
        Log.i("oldVersion",oldVersion+"");
        switch (newVersion) {
            case 10:
                //创建新表，注意createTable()是静态方法
                MigrationHelper.migrate(db,TaskPhotoDao.class);
                // TODO
                break;
        }
    }
}
