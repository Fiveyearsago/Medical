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
    public void insertSingleData(MedicalVisit medicalVisit){
        MedicalVisitDao medicalVisitDao = daoSession.getMedicalVisitDao();
        if (!isExist(medicalVisit)){
            medicalVisitDao.insert(medicalVisit);
        }else {
            MedicalVisit medicalVisit1=getData(medicalVisit.getTaskNo());
            medicalVisit1.setMedicalFee(medicalVisit.getMedicalFee());
            medicalVisit1.setCompleteStatus(medicalVisit.getCompleteStatus());
            medicalVisit1.setRemark(medicalVisit.getRemark());
            medicalVisit1.setCommitFlag(medicalVisit.getCommitFlag());
            medicalVisitDao.update(medicalVisit1);
        }
    }
    public long getID(MedicalVisit medicalVisit) {
        return daoSession.getMedicalVisitDao().getKey(medicalVisit);
    }
    public List<MedicalVisit> getDataList(String taskNo){
        MedicalVisitDao medicalVisitDao = daoSession.getMedicalVisitDao();
        QueryBuilder<MedicalVisit> qb = medicalVisitDao.queryBuilder();
        qb.where(MedicalVisitDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public MedicalVisit getData(String taskNo){
        MedicalVisitDao medicalVisitDao = daoSession.getMedicalVisitDao();
        QueryBuilder<MedicalVisit> qb = medicalVisitDao.queryBuilder();
        qb.where(MedicalVisitDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
}
