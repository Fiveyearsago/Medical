package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.SelectedDiagnose;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.SelectedDiagnoseDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class SelectedDiagnoseManager extends BaseDao<SelectedDiagnose> {
    public SelectedDiagnoseManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private SelectedDiagnose loadById(long id) {
        return daoSession.getSelectedDiagnoseDao().load(id);
    }

    private boolean isExist(SelectedDiagnose selectedDiagnose) {
        SelectedDiagnoseDao selectedDiagnoseDao = daoSession.getSelectedDiagnoseDao();
        QueryBuilder<SelectedDiagnose> qb = selectedDiagnoseDao.queryBuilder();
        qb.where(qb.and(SelectedDiagnoseDao.Properties.TaskNo.eq(selectedDiagnose.getTaskNo()),SelectedDiagnoseDao.Properties.DiagnoseId.eq(selectedDiagnose.getDiagnoseId())));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<SelectedDiagnose> selectedDiagnoseList) {
        SelectedDiagnoseDao selectedDiagnoseDao = daoSession.getSelectedDiagnoseDao();
        for (int i = 0; i < selectedDiagnoseList.size(); i++) {
            if (!isExist(selectedDiagnoseList.get(i))){
                selectedDiagnoseDao.insert(selectedDiagnoseList.get(i));
            }
        }

    }
    public void insertSingleData(@NotNull SelectedDiagnose selectedDiagnose) {
        SelectedDiagnoseDao selectedDiagnoseDao = daoSession.getSelectedDiagnoseDao();
            if (!isExist(selectedDiagnose)){
                selectedDiagnoseDao.insert(selectedDiagnose);
            }
    }
    public List<SelectedDiagnose> getDataList(String taskNo){
        SelectedDiagnoseDao selectedDiagnoseDao = daoSession.getSelectedDiagnoseDao();
        QueryBuilder<SelectedDiagnose> qb = selectedDiagnoseDao.queryBuilder();
        qb.where(SelectedDiagnoseDao.Properties.TaskNo.eq(taskNo));
        return qb.list();
    }
    public void deleteSingleData(SelectedDiagnose selectedDiagnose) {
        SelectedDiagnoseDao selectedDiagnoseDao = daoSession.getSelectedDiagnoseDao();
        if (isExist(selectedDiagnose)) {
            selectedDiagnoseDao.delete(selectedDiagnose);
        }
    }
}
