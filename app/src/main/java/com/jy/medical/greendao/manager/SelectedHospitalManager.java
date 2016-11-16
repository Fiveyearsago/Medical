package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.SelectedHospital;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.SelectedHospitalDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class SelectedHospitalManager extends BaseDao<SelectedHospital> {
    public SelectedHospitalManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private SelectedHospital loadById(long id) {
        return daoSession.getSelectedHospitalDao().load(id);
    }

    private boolean isExist(SelectedHospital selectedHospital) {
        SelectedHospitalDao selectedHospitalDao = daoSession.getSelectedHospitalDao();
        QueryBuilder<SelectedHospital> qb = selectedHospitalDao.queryBuilder();
        qb.where(SelectedHospitalDao.Properties.HospitalId.eq(selectedHospital.getHospitalId()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<SelectedHospital> selectedHospitalList) {
        SelectedHospitalDao selectedHospitalDao = daoSession.getSelectedHospitalDao();
        for (int i = 0; i < selectedHospitalList.size(); i++) {
            if (!isExist(selectedHospitalList.get(i))){
                selectedHospitalDao.insert(selectedHospitalList.get(i));
            }
        }

    }
    public List<SelectedHospital> getDataList(){
        SelectedHospitalDao selectedHospitalDao = daoSession.getSelectedHospitalDao();
        QueryBuilder<SelectedHospital> qb = selectedHospitalDao.queryBuilder();
        qb.where(SelectedHospitalDao.Properties.Id.isNotNull());
        return qb.list();
    }
}
