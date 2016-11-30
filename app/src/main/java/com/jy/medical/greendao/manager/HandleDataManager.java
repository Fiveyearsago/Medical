package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.HandleData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.HandleDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class HandleDataManager extends BaseDao<HandleData> {
    public HandleDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private HandleData loadById(long id) {
        return daoSession.getHandleDataDao().load(id);
    }

    private boolean isExist(HandleData handleData) {
        HandleDataDao handleDataDao = daoSession.getHandleDataDao();
        QueryBuilder<HandleData> qb = handleDataDao.queryBuilder();
        qb.where(HandleDataDao.Properties.TaskNo.eq(handleData.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<HandleData> handleDataList) {
        HandleDataDao handleDataDao = daoSession.getHandleDataDao();
        for (int i = 0; i < handleDataList.size(); i++) {
            if (!isExist(handleDataList.get(i))){
                handleDataDao.insert(handleDataList.get(i));
            }
        }
    }
    public void insertSingleData(HandleData handleData){
        HandleDataDao handleDataDao = daoSession.getHandleDataDao();
        if (!isExist(handleData)){
            handleDataDao.insert(handleData);
        }else {
            HandleData handleData1=getData(handleData.getTaskNo());
            handleData.setId(handleData1.getId());
            handleDataDao.update(handleData);
        }
    }
    public long getID(HandleData handleData) {
        return daoSession.getHandleDataDao().getKey(handleData);
    }
    public List<HandleData> getDataList(String taskNo){
        HandleDataDao handleDataDao = daoSession.getHandleDataDao();
        QueryBuilder<HandleData> qb = handleDataDao.queryBuilder();
        qb.where(HandleDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public HandleData getData(String taskNo){
        HandleDataDao handleDataDao = daoSession.getHandleDataDao();
        QueryBuilder<HandleData> qb = handleDataDao.queryBuilder();
        qb.where(HandleDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
}
