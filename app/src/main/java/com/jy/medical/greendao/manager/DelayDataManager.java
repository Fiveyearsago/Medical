package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.DelayData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.DelayDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class DelayDataManager extends BaseDao<DelayData> {
    public DelayDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private DelayData loadById(long id) {
        return daoSession.getDelayDataDao().load(id);
    }

    private boolean isExist(DelayData delayData) {
        DelayDataDao delayDataDao = daoSession.getDelayDataDao();
        QueryBuilder<DelayData> qb = delayDataDao.queryBuilder();
        qb.where(DelayDataDao.Properties.TaskNo.eq(delayData.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<DelayData> delayDataList) {
        DelayDataDao delayDataDao = daoSession.getDelayDataDao();
        for (int i = 0; i < delayDataList.size(); i++) {
            if (!isExist(delayDataList.get(i))){
                delayDataDao.insert(delayDataList.get(i));
            }
        }
    }
    public void insertSingleData(DelayData delayData){
        DelayDataDao delayDataDao = daoSession.getDelayDataDao();
        if (!isExist(delayData)){
            delayDataDao.insert(delayData);
        }else {
            DelayData delayData1=getData(delayData.getTaskNo());
            delayData.setId(delayData1.getId());
            delayDataDao.update(delayData);
        }
    }
    public long getID(DelayData delayData) {
        return daoSession.getDelayDataDao().getKey(delayData);
    }
    public List<DelayData> getDataList(String taskNo){
        DelayDataDao delayDataDao = daoSession.getDelayDataDao();
        QueryBuilder<DelayData> qb = delayDataDao.queryBuilder();
        qb.where(DelayDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public DelayData getData(String taskNo){
        DelayDataDao delayDataDao = daoSession.getDelayDataDao();
        QueryBuilder<DelayData> qb = delayDataDao.queryBuilder();
        qb.where(DelayDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
}
