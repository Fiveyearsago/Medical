package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.BaseInfoData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.BaseInfoDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class BaseInfoDataManager extends BaseDao<BaseInfoData> {
    public BaseInfoDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private BaseInfoData loadById(long id) {
        return daoSession.getBaseInfoDataDao().load(id);
    }

    private boolean isExist(BaseInfoData baseInfoData) {
        BaseInfoDataDao baseInfoDataDao = daoSession.getBaseInfoDataDao();
        QueryBuilder<BaseInfoData> qb = baseInfoDataDao.queryBuilder();
        qb.where(BaseInfoDataDao.Properties.TaskNo.eq(baseInfoData.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<BaseInfoData> baseInfoDataList) {
        BaseInfoDataDao baseInfoDataDao = daoSession.getBaseInfoDataDao();
        for (int i = 0; i < baseInfoDataList.size(); i++) {
            if (!isExist(baseInfoDataList.get(i))){
                baseInfoDataDao.insert(baseInfoDataList.get(i));
            }
        }
    }
    public void insertSingleData(BaseInfoData baseInfoData){
        BaseInfoDataDao baseInfoDataDao = daoSession.getBaseInfoDataDao();
        if (!isExist(baseInfoData)){
            baseInfoDataDao.insert(baseInfoData);
        }else {
            BaseInfoData baseInfoData1=getData(baseInfoData.getTaskNo());
            baseInfoData1.setAddress(baseInfoData.getAddress());
            baseInfoData1.setTime(baseInfoData.getTime());
            baseInfoData1.setDetailInfo(baseInfoData.getDetailInfo());
            baseInfoData1.setCompleteStatus(baseInfoData.getCompleteStatus());
            baseInfoData1.setRemark(baseInfoData.getRemark());
            baseInfoData1.setCommitFlag(baseInfoData.getCommitFlag());
            baseInfoDataDao.update(baseInfoData1);
        }
    }
    public long getID(BaseInfoData baseInfoData) {
        return daoSession.getBaseInfoDataDao().getKey(baseInfoData);
    }
    public List<BaseInfoData> getDataList(String taskNo){
        BaseInfoDataDao baseInfoDataDao = daoSession.getBaseInfoDataDao();
        QueryBuilder<BaseInfoData> qb = baseInfoDataDao.queryBuilder();
        qb.where(BaseInfoDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public BaseInfoData getData(String taskNo){
        BaseInfoDataDao baseInfoDataDao = daoSession.getBaseInfoDataDao();
        QueryBuilder<BaseInfoData> qb = baseInfoDataDao.queryBuilder();
        qb.where(BaseInfoDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
}
