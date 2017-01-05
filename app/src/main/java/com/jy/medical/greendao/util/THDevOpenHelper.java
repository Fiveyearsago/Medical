package com.jy.medical.greendao.util;

/**
 * Created by songran on 16/11/4.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jy.medical.greendao.entities.HouseholdData;
import com.jy.medical.greendao.gen.ContactDataDao;
import com.jy.medical.greendao.gen.DaoMaster;
import com.jy.medical.greendao.gen.DeathDataDao;
import com.jy.medical.greendao.gen.DelayDataDao;
import com.jy.medical.greendao.gen.EarningDataDao;
import com.jy.medical.greendao.gen.HandleDataDao;
import com.jy.medical.greendao.gen.HouseholdDataDao;
import com.jy.medical.greendao.gen.InquireDao;
import com.jy.medical.greendao.gen.LawDataDao;
import com.jy.medical.greendao.gen.MaimDataDao;
import com.jy.medical.greendao.gen.MaimGradeDataDao;
import com.jy.medical.greendao.gen.SupporterDataDao;
import com.jy.medical.greendao.gen.SupporterPersonDao;
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
        Log.i("newVersion", newVersion + "");
        Log.i("oldVersion", oldVersion + "");
        switch (newVersion) {
            case 2:
                //创建新表，注意createTable()是静态方法
                MigrationHelper.migrate(db, EarningDataDao.class);
                break;
            case 3:
                MigrationHelper.migrate(db, DelayDataDao.class);
                break;
            case 4:
                MigrationHelper.migrate(db, HandleDataDao.class);
                break;
            case 5:
                MigrationHelper.migrate(db, TaskBeanDataDao.class);
                break;
            case 6:
                MigrationHelper.migrate(db, DeathDataDao.class);
                break;
            case 7:
                MigrationHelper.migrate(db, SupporterDataDao.class);
                break;
            case 8:
                MigrationHelper.migrate(db,  MaimDataDao.class);
                break;
            case 9:
                MigrationHelper.migrate(db,  MaimDataDao.class);
                break;
            case 10:
                MigrationHelper.migrate(db,  MaimGradeDataDao.class);
                break;
            case 11:
                MigrationHelper.migrate(db, TaskBeanDataDao.class);
                break;
            case 12:
                MigrationHelper.migrate(db, SupporterPersonDao.class);
                break;
            case 13:
                MigrationHelper.migrate(db, HouseholdDataDao.class);
                break;
            case 14:
                MigrationHelper.migrate(db, LawDataDao.class);
                break;
            case 15:
                MigrationHelper.migrate(db, InquireDao.class);
                break;
            case 16:
                MigrationHelper.migrate(db, ContactDataDao.class);
                break;
//            case 22:
//                MigrationHelper.migrate(db, SelectedHospitalDao.class);
//                break;
//            case 23:
//                MigrationHelper.migrate(db, SelectedDiagnoseDao.class);
//                break;
//            case 24:
//                MigrationHelper.migrate(db, BaseInfoDataDao.class);
//                break;
        }
    }
}
