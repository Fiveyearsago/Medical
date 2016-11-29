package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.EarningData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.EarningDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class EarningDataManager extends BaseDao<EarningData> {
    public EarningDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private EarningData loadById(long id) {
        return daoSession.getEarningDataDao().load(id);
    }

    private boolean isExist(EarningData earningData) {
        EarningDataDao earningDataDao = daoSession.getEarningDataDao();
        QueryBuilder<EarningData> qb = earningDataDao.queryBuilder();
        qb.where(EarningDataDao.Properties.TaskNo.eq(earningData.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<EarningData> earningDataList) {
        EarningDataDao earningDataDao = daoSession.getEarningDataDao();
        for (int i = 0; i < earningDataList.size(); i++) {
            if (!isExist(earningDataList.get(i))){
                earningDataDao.insert(earningDataList.get(i));
            }
        }
    }
    public void insertSingleData(EarningData earningData){
        EarningDataDao earningDataDao = daoSession.getEarningDataDao();
        if (!isExist(earningData)){
            earningDataDao.insert(earningData);
        }else {
            EarningData earningData1=getData(earningData.getTaskNo());
            earningData.setId(earningData1.getId());
            earningDataDao.update(earningData);
        }
    }
    public long getID(EarningData earningData) {
        return daoSession.getEarningDataDao().getKey(earningData);
    }
    public List<EarningData> getDataList(String taskNo){
        EarningDataDao earningDataDao = daoSession.getEarningDataDao();
        QueryBuilder<EarningData> qb = earningDataDao.queryBuilder();
        qb.where(EarningDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public EarningData getData(String taskNo){
        EarningDataDao earningDataDao = daoSession.getEarningDataDao();
        QueryBuilder<EarningData> qb = earningDataDao.queryBuilder();
        qb.where(EarningDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
}
