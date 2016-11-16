package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.Diagnose;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.DiagnoseDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class DiagnoseManager extends BaseDao<Diagnose> {
    public DiagnoseManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private Diagnose loadById(long id) {
        return daoSession.getDiagnoseDao().load(id);
    }

    private boolean isExist(Diagnose diagnose) {
        DiagnoseDao diagnoseDao = daoSession.getDiagnoseDao();
        QueryBuilder<Diagnose> qb = diagnoseDao.queryBuilder();
        qb.where(DiagnoseDao.Properties.DiagnoseId.eq(diagnose.getDiagnoseId()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<Diagnose> diagnoseList) {
        DiagnoseDao diagnoseDao = daoSession.getDiagnoseDao();
        for (int i = 0; i < diagnoseList.size(); i++) {
            if (!isExist(diagnoseList.get(i))){
                diagnoseDao.insert(diagnoseList.get(i));
            }
        }

    }
    public List<Diagnose> getDataList(){
        DiagnoseDao diagnoseDao = daoSession.getDiagnoseDao();
        QueryBuilder<Diagnose> qb = diagnoseDao.queryBuilder();
        qb.where(DiagnoseDao.Properties.Id.isNotNull());
        return qb.list();
    }
}
