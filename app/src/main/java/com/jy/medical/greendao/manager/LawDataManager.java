package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.LawData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.LawDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class LawDataManager extends BaseDao<LawData> {
    public LawDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private LawData loadById(long id) {
        return daoSession.getLawDataDao().load(id);
    }

    private boolean isExist(LawData lawData) {
        LawDataDao lawDataDao = daoSession.getLawDataDao();
        QueryBuilder<LawData> qb = lawDataDao.queryBuilder();
        qb.where(LawDataDao.Properties.LawId.eq(lawData.getLawId()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<LawData> lawDataList) {
        LawDataDao lawDataDao = daoSession.getLawDataDao();
        for (int i = 0; i < lawDataList.size(); i++) {
            if (!isExist(lawDataList.get(i))) {
                lawDataDao.insert(lawDataList.get(i));
            }
        }
    }

    public void insertSingleData(LawData lawData) {
        LawDataDao lawDataDao = daoSession.getLawDataDao();
        if (!isExist(lawData)) {
            lawDataDao.insert(lawData);
        } else {
            LawData lawData1 = getData(lawData.getLawId());
            lawData.setId(lawData1.getId());
            lawDataDao.update(lawData);
        }
    }

    public long getID(LawData lawData) {
        return daoSession.getLawDataDao().getKey(lawData);
    }

    public List<LawData> getDataList(String lawId) {
        LawDataDao lawDataDao = daoSession.getLawDataDao();
        QueryBuilder<LawData> qb = lawDataDao.queryBuilder();
        qb.where(LawDataDao.Properties.LawId.eq(lawId));
        return qb.list();
    }

    public LawData getData(String lawId) {
        LawDataDao lawDataDao = daoSession.getLawDataDao();
        QueryBuilder<LawData> qb = lawDataDao.queryBuilder();
        qb.where(LawDataDao.Properties.LawId.eq(lawId));
        if (qb.list().size() > 0) {
            return qb.list().get(0);
        } else {
            return null;
        }
    }

}
