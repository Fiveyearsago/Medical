package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.MaimData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.MaimDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class MaimDataManager extends BaseDao<MaimData> {
    public MaimDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private MaimData loadById(long id) {
        return daoSession.getMaimDataDao().load(id);
    }

    private boolean isExist(MaimData maimData) {
        MaimDataDao maimDataDao = daoSession.getMaimDataDao();
        QueryBuilder<MaimData> qb = maimDataDao.queryBuilder();
        qb.where(MaimDataDao.Properties.TaskNo.eq(maimData.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<MaimData> maimDataList) {
        MaimDataDao maimDataDao = daoSession.getMaimDataDao();
        for (int i = 0; i < maimDataList.size(); i++) {
            if (!isExist(maimDataList.get(i))){
                maimDataDao.insert(maimDataList.get(i));
            }
        }
    }
    public void insertSingleData(MaimData maimData){
        MaimDataDao maimDataDao = daoSession.getMaimDataDao();
        if (!isExist(maimData)){
            maimDataDao.insert(maimData);
        }else {
            MaimData maimData1=getData(maimData.getTaskNo());
            maimData.setId(maimData1.getId());
            maimDataDao.update(maimData);
        }
    }
    public long getID(MaimData maimData) {
        return daoSession.getMaimDataDao().getKey(maimData);
    }
    public List<MaimData> getDataList(String taskNo){
        MaimDataDao maimDataDao = daoSession.getMaimDataDao();
        QueryBuilder<MaimData> qb = maimDataDao.queryBuilder();
        qb.where(MaimDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public MaimData getData(String taskNo){
        MaimDataDao maimDataDao = daoSession.getMaimDataDao();
        QueryBuilder<MaimData> qb = maimDataDao.queryBuilder();
        qb.where(MaimDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
}
