package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.MaimGradeData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.MaimGradeDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class MaimGradeDataManager extends BaseDao<MaimGradeData> {
    public MaimGradeDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private MaimGradeData loadById(long id) {
        return daoSession.getMaimGradeDataDao().load(id);
    }

    private boolean isExist(MaimGradeData maimGradeData) {
        MaimGradeDataDao maimGradeDataDao = daoSession.getMaimGradeDataDao();
        QueryBuilder<MaimGradeData> qb = maimGradeDataDao.queryBuilder();
        qb.where(qb.and(MaimGradeDataDao.Properties.TaskNo.eq(maimGradeData.getTaskNo()),MaimGradeDataDao.Properties.DisabilityDescr.eq(maimGradeData.getDisabilityDescr())));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<MaimGradeData> maimGradeDataList) {
        MaimGradeDataDao maimGradeDataDao = daoSession.getMaimGradeDataDao();
        for (int i = 0; i < maimGradeDataList.size(); i++) {
            if (!isExist(maimGradeDataList.get(i))) {
                maimGradeDataDao.insert(maimGradeDataList.get(i));
            }
        }
    }

    public void insertSingleData(MaimGradeData maimGradeData) {
        MaimGradeDataDao maimGradeDataDao = daoSession.getMaimGradeDataDao();
        if (!isExist(maimGradeData)) {
            maimGradeDataDao.insert(maimGradeData);
        }
//        else {
//            MaimGradeData maimGradeData1 = getData(maimGradeData.getTaskNo());
//            maimGradeData.setId(maimGradeData1.getId());
//            maimGradeDataDao.update(maimGradeData);
//        }
    }

    public long getID(MaimGradeData maimGradeData) {
        return daoSession.getMaimGradeDataDao().getKey(maimGradeData);
    }

    public List<MaimGradeData> getDataList(String taskNo) {
        MaimGradeDataDao maimGradeDataDao = daoSession.getMaimGradeDataDao();
        QueryBuilder<MaimGradeData> qb = maimGradeDataDao.queryBuilder();
        qb.where(MaimGradeDataDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }

    public MaimGradeData getData(String taskNo) {
        MaimGradeDataDao maimGradeDataDao = daoSession.getMaimGradeDataDao();
        QueryBuilder<MaimGradeData> qb = maimGradeDataDao.queryBuilder();
        qb.where(MaimGradeDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size() > 0) {
            return qb.list().get(0);
        } else {
            return null;
        }
    }
    public void deleteSingleData(MaimGradeData maimGradeData) {
        MaimGradeDataDao maimGradeDataDao = daoSession.getMaimGradeDataDao();
        if (isExist(maimGradeData)) {
            maimGradeDataDao.delete(maimGradeData);
        }
    }

}
