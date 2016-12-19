package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.HouseholdData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.HouseholdDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class HouseholdDataManager extends BaseDao<HouseholdData> {
    public HouseholdDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private HouseholdData loadById(long id) {
        return daoSession.getHouseholdDataDao().load(id);
    }

    private boolean isExist(HouseholdData householdData) {
        HouseholdDataDao householdDataDao = daoSession.getHouseholdDataDao();
        QueryBuilder<HouseholdData> qb = householdDataDao.queryBuilder();
        qb.where(HouseholdDataDao.Properties.TaskNo.eq(householdData.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<HouseholdData> householdDataList) {
        HouseholdDataDao householdDataDao = daoSession.getHouseholdDataDao();
        for (int i = 0; i < householdDataList.size(); i++) {
            if (!isExist(householdDataList.get(i))){
                householdDataDao.insert(householdDataList.get(i));
            }
        }
    }
    public void insertSingleData(HouseholdData householdData){
        HouseholdDataDao householdDataDao = daoSession.getHouseholdDataDao();
        if (!isExist(householdData)){
            householdDataDao.insert(householdData);
        }else {
            HouseholdData householdData1=getData(householdData.getTaskNo());
            householdData.setId(householdData1.getId());
            householdDataDao.update(householdData);
        }
    }
    public long getID(HouseholdData householdData) {
        return daoSession.getHouseholdDataDao().getKey(householdData);
    }
    public List<HouseholdData> getDataList(String taskNo){
        HouseholdDataDao householdDataDao = daoSession.getHouseholdDataDao();
        QueryBuilder<HouseholdData> qb = householdDataDao.queryBuilder();
        qb.where(HouseholdDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public HouseholdData getData(String taskNo){
        HouseholdDataDao householdDataDao = daoSession.getHouseholdDataDao();
        QueryBuilder<HouseholdData> qb = householdDataDao.queryBuilder();
        qb.where(HouseholdDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
}
