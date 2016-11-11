package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.MedicalDepartment;
import com.jy.medical.greendao.entities.MedicalDepartment;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.MedicalDepartmentDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by jamy on 16/6/16.
 * 在这个类中添加不同的查询条件
 */
public class MedicalDepartmentManager extends BaseDao<MedicalDepartment> {
    public MedicalDepartmentManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private MedicalDepartment loadById(long id) {
        return daoSession.getMedicalDepartmentDao().load(id);
    }

    private boolean isExist(MedicalDepartment medicalDepartment) {
        MedicalDepartmentDao medicalDepartmentDao = daoSession.getMedicalDepartmentDao();
        QueryBuilder<MedicalDepartment> qb = medicalDepartmentDao.queryBuilder();
        qb.where(MedicalDepartmentDao.Properties.Key.eq(medicalDepartment.getKey()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<MedicalDepartment> medicalDepartmentList) {
        MedicalDepartmentDao medicalDepartmentDao = daoSession.getMedicalDepartmentDao();
        for (int i = 0; i < medicalDepartmentList.size(); i++) {
            if (!isExist(medicalDepartmentList.get(i))){
                medicalDepartmentDao.insert(medicalDepartmentList.get(i));
            }
        }

    }
}
