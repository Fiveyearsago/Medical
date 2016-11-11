package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.entities.HospitalData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.HospitalDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by jamy on 16/6/16.
 * 在这个类中添加不同的查询条件
 */
public class HospitalDataManager extends BaseDao<HospitalData> {
    public HospitalDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private HospitalData loadById(long id) {
        return daoSession.getHospitalDataDao().load(id);
    }

    private boolean isExist(HospitalData hospitalData) {
        HospitalDataDao hospitalDataDao = daoSession.getHospitalDataDao();
        QueryBuilder<HospitalData> qb = hospitalDataDao.queryBuilder();
        qb.where(HospitalDataDao.Properties.HospitalId.eq(hospitalData.getHospitalId()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<HospitalData> hospitalDataList) {
        HospitalDataDao hospitalDataDao = daoSession.getHospitalDataDao();
        for (int i = 0; i < hospitalDataList.size(); i++) {
            if (!isExist(hospitalDataList.get(i))){
                hospitalDataDao.insert(hospitalDataList.get(i));
            }
        }

    }
}
