package com.jy.medical.greendao.util;

/**
 * Created by songran on 16/11/4.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.entities.CityData;
import com.jy.medical.greendao.entities.ContactData;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.MedicalDepartment;
import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.gen.BaseInfoDataDao;
import com.jy.medical.greendao.gen.CategoryDataDao;
import com.jy.medical.greendao.gen.CityDataDao;
import com.jy.medical.greendao.gen.ClaimBeanDataDao;
import com.jy.medical.greendao.gen.ContactDataDao;
import com.jy.medical.greendao.gen.DaoMaster;
import com.jy.medical.greendao.gen.DelayDataDao;
import com.jy.medical.greendao.gen.DiagnoseDao;
import com.jy.medical.greendao.gen.EarningDataDao;
import com.jy.medical.greendao.gen.HospitalDataDao;
import com.jy.medical.greendao.gen.HumanPartsDao;
import com.jy.medical.greendao.gen.MedicalDepartmentDao;
import com.jy.medical.greendao.gen.MedicalVisitDao;
import com.jy.medical.greendao.gen.NursingDataDao;
import com.jy.medical.greendao.gen.SearchDataDao;
import com.jy.medical.greendao.gen.SelectedDepartmentDao;
import com.jy.medical.greendao.gen.SelectedDiagnoseDao;
import com.jy.medical.greendao.gen.SelectedHospitalDao;
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
//            case 12:
//                MigrationHelper.migrate(db, CityDataDao.class);
//                break;
//            case 13:
//                MigrationHelper.migrate(db, MedicalDepartmentDao.class, HospitalDataDao.class);
//                break;
//            case 14:
//                MigrationHelper.migrate(db, SearchDataDao.class);
//                break;
//            case 15:
//                MigrationHelper.migrate(db, DiagnoseDao.class, MedicalVisitDao.class, SelectedDepartmentDao.class, SelectedDiagnoseDao.class, SelectedHospitalDao.class);
//                break;
//            case 16:
//                MigrationHelper.migrate(db, SelectedHospitalDao.class);
//                break;
//            case 17:
//                MigrationHelper.migrate(db, HumanPartsDao.class, CategoryDataDao.class);
//                break;
//            case 18:
//                MigrationHelper.migrate(db, SelectedDiagnoseDao.class);
//                break;
//            case 19:
//                MigrationHelper.migrate(db, NursingDataDao.class);
//                break;
//            case 20:
//                MigrationHelper.migrate(db, NursingDataDao.class);
//                break;
//            case 21:
//                MigrationHelper.migrate(db, MedicalVisitDao.class);
//                break;
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
