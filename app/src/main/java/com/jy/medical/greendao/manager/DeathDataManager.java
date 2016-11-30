package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.DeathData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.DeathDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class DeathDataManager extends BaseDao<DeathData> {
    public DeathDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private DeathData loadById(long id) {
        return daoSession.getDeathDataDao().load(id);
    }

    private boolean isExist(DeathData deathData) {
        DeathDataDao deathDataDao = daoSession.getDeathDataDao();
        QueryBuilder<DeathData> qb = deathDataDao.queryBuilder();
        qb.where(DeathDataDao.Properties.TaskNo.eq(deathData.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<DeathData> deathDataList) {
        DeathDataDao deathDataDao = daoSession.getDeathDataDao();
        for (int i = 0; i < deathDataList.size(); i++) {
            if (!isExist(deathDataList.get(i))){
                deathDataDao.insert(deathDataList.get(i));
            }
        }
    }
    public void insertSingleData(DeathData deathData){
        DeathDataDao deathDataDao = daoSession.getDeathDataDao();
        if (!isExist(deathData)){
            deathDataDao.insert(deathData);
        }else {
            DeathData deathData1=getData(deathData.getTaskNo());
            deathData.setId(deathData1.getId());
            deathDataDao.update(deathData);
        }
    }
    public long getID(DeathData deathData) {
        return daoSession.getDeathDataDao().getKey(deathData);
    }
    public List<DeathData> getDataList(String taskNo){
        DeathDataDao deathDataDao = daoSession.getDeathDataDao();
        QueryBuilder<DeathData> qb = deathDataDao.queryBuilder();
        qb.where(DeathDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public DeathData getData(String taskNo){
        DeathDataDao deathDataDao = daoSession.getDeathDataDao();
        QueryBuilder<DeathData> qb = deathDataDao.queryBuilder();
        qb.where(DeathDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
}
