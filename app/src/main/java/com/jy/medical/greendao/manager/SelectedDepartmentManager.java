package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.SelectedDepartment;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.SelectedDepartmentDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class SelectedDepartmentManager extends BaseDao<SelectedDepartment> {
    public SelectedDepartmentManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private SelectedDepartment loadById(long id) {
        return daoSession.getSelectedDepartmentDao().load(id);
    }

    private boolean isExist(SelectedDepartment selectedDepartment) {
        SelectedDepartmentDao selectedDepartmentDao = daoSession.getSelectedDepartmentDao();
        QueryBuilder<SelectedDepartment> qb = selectedDepartmentDao.queryBuilder();
        qb.where(SelectedDepartmentDao.Properties.DepartmentId.eq(selectedDepartment.getDepartmentId()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<SelectedDepartment> selectedDepartmentList) {
        SelectedDepartmentDao selectedDepartmentDao = daoSession.getSelectedDepartmentDao();
        for (int i = 0; i < selectedDepartmentList.size(); i++) {
            if (!isExist(selectedDepartmentList.get(i))){
                selectedDepartmentDao.insert(selectedDepartmentList.get(i));
            }
        }

    }
    public List<SelectedDepartment> getDataList(){
        SelectedDepartmentDao selectedDepartmentDao = daoSession.getSelectedDepartmentDao();
        QueryBuilder<SelectedDepartment> qb = selectedDepartmentDao.queryBuilder();
        qb.where(SelectedDepartmentDao.Properties.Id.isNotNull());
        return qb.list();
    }
}
