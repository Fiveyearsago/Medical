package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.Inquire;
import com.jy.medical.greendao.entities.NursingData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.InquireDao;
import com.jy.medical.greendao.gen.NursingDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class NursingDataManager extends BaseDao<NursingData> {
    public NursingDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private NursingData loadById(long id) {
        return daoSession.getNursingDataDao().load(id);
    }

    public boolean isExist(NursingData nursingData) {
        NursingDataDao nursingDataDao = daoSession.getNursingDataDao();
        QueryBuilder<NursingData> qb = nursingDataDao.queryBuilder();
        qb.where(qb.and(NursingDataDao.Properties.Name.eq(nursingData.getName()),NursingDataDao.Properties.TaskNo.eq(nursingData.getTaskNo())));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public List<NursingData> selectAllNursing(String taskNo){
        NursingDataDao nursingDataDao=daoSession.getNursingDataDao();
        QueryBuilder<NursingData> qb = nursingDataDao.queryBuilder();
        qb.where(NursingDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();

    }

    public void insertSingleData(NursingData nursingData) {
        NursingDataDao nursingDataDao = daoSession.getNursingDataDao();
        if (!isExist(nursingData)) {
            nursingDataDao.insert(nursingData);
        }

    }

    public void updateData(NursingData nursingData) {
        NursingDataDao nursingDataDao = daoSession.getNursingDataDao();
        nursingDataDao.update(nursingData);
    }
    public void insertData(@NotNull List<NursingData> nursingDataList) {
        NursingDataDao nursingDataDao = daoSession.getNursingDataDao();
        for (int i = 0; i < nursingDataList.size(); i++) {
            if (!isExist(nursingDataList.get(i))){
                nursingDataDao.insert(nursingDataList.get(i));
            }else {
                nursingDataDao.update(nursingDataList.get(i));
            }
        }

    }

    public void deleteSingleData(NursingData nursingData) {
        NursingDataDao nursingDataDao = daoSession.getNursingDataDao();
        if (isExist(nursingData)) {
            nursingDataDao.delete(nursingData);
        }
    }
    public List<NursingData> getDataList(String taskNo){
        NursingDataDao nursingDataDao = daoSession.getNursingDataDao();
        QueryBuilder<NursingData> qb = nursingDataDao.queryBuilder();
        qb.where(NursingDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
}
