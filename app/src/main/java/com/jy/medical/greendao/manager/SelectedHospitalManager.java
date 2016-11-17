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
        qb.where(qb.and(SelectedHospitalDao.Properties.HospitalId.eq(selectedHospital.getHospitalId()),SelectedHospitalDao.Properties.TaskNo.eq(selectedHospital.getTaskNo())));
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
    public void insertSingleData(@NotNull SelectedHospital selectedHospital) {
        SelectedHospitalDao selectedHospitalDao = daoSession.getSelectedHospitalDao();
            if (!isExist(selectedHospital)){
                selectedHospitalDao.insert(selectedHospital);
        }

    }
    public List<SelectedHospital> getDataList(String taskNo){
        SelectedHospitalDao selectedHospitalDao = daoSession.getSelectedHospitalDao();
        QueryBuilder<SelectedHospital> qb = selectedHospitalDao.queryBuilder();
        qb.where(SelectedHospitalDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public void deleteSingleData(SelectedHospital selectedHospital) {
        SelectedHospitalDao selectedHospitalDao = daoSession.getSelectedHospitalDao();
        if (isExist(selectedHospital)) {
            selectedHospitalDao.delete(selectedHospital);
        }
    }
    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    public void deleteById(long id) {

        daoSession.getSelectedHospitalDao().deleteByKey(id);
    }
}
