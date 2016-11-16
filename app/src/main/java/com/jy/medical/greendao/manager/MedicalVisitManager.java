package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.MedicalVisit;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.MedicalVisitDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class MedicalVisitManager extends BaseDao<MedicalVisit> {
    public MedicalVisitManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private MedicalVisit loadById(long id) {
        return daoSession.getMedicalVisitDao().load(id);
    }

    private boolean isExist(MedicalVisit medicalVisit) {
        MedicalVisitDao medicalVisitDao = daoSession.getMedicalVisitDao();
        QueryBuilder<MedicalVisit> qb = medicalVisitDao.queryBuilder();
        qb.where(MedicalVisitDao.Properties.TaskNo.eq(medicalVisit.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<MedicalVisit> medicalVisitList) {
        MedicalVisitDao medicalVisitDao = daoSession.getMedicalVisitDao();
        for (int i = 0; i < medicalVisitList.size(); i++) {
            if (!isExist(medicalVisitList.get(i))){
                medicalVisitDao.insert(medicalVisitList.get(i));
            }
        }

    }
    public List<MedicalVisit> getDataList(){
        MedicalVisitDao medicalVisitDao = daoSession.getMedicalVisitDao();
        QueryBuilder<MedicalVisit> qb = medicalVisitDao.queryBuilder();
        qb.where(MedicalVisitDao.Properties.Id.isNotNull());
        return qb.list();
    }
}
