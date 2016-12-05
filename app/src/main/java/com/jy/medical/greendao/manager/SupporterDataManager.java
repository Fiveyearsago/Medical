package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.SupporterData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.SupporterDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class SupporterDataManager extends BaseDao<SupporterData> {
    public SupporterDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private SupporterData loadById(long id) {
        return daoSession.getSupporterDataDao().load(id);
    }

    private boolean isExist(SupporterData supporterData) {
        SupporterDataDao supporterDataDao = daoSession.getSupporterDataDao();
        QueryBuilder<SupporterData> qb = supporterDataDao.queryBuilder();
        qb.where(SupporterDataDao.Properties.TaskNo.eq(supporterData.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<SupporterData> supporterDataList) {
        SupporterDataDao supporterDataDao = daoSession.getSupporterDataDao();
        for (int i = 0; i < supporterDataList.size(); i++) {
            if (!isExist(supporterDataList.get(i))){
                supporterDataDao.insert(supporterDataList.get(i));
            }
        }
    }

    public void insertSingleData(SupporterData supporterData){
        SupporterDataDao supporterDataDao = daoSession.getSupporterDataDao();
        if (!isExist(supporterData)){
            supporterDataDao.insert(supporterData);
        }else {
            SupporterData supporterData1=getData(supporterData.getTaskNo());
            supporterData.setId(supporterData1.getId());
            supporterDataDao.update(supporterData);
        }
    }
    public long getID(SupporterData supporterData) {
        return daoSession.getSupporterDataDao().getKey(supporterData);
    }
    public List<SupporterData> getDataList(String taskNo){
        SupporterDataDao supporterDataDao = daoSession.getSupporterDataDao();
        QueryBuilder<SupporterData> qb = supporterDataDao.queryBuilder();
        qb.where(SupporterDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public SupporterData getData(String taskNo){
        SupporterDataDao supporterDataDao = daoSession.getSupporterDataDao();
        QueryBuilder<SupporterData> qb = supporterDataDao.queryBuilder();
        qb.where(SupporterDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
}
