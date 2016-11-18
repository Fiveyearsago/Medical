package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.HumanParts;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.HumanPartsDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class HumanPartsManager extends BaseDao<HumanParts> {
    public HumanPartsManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private HumanParts loadById(long id) {
        return daoSession.getHumanPartsDao().load(id);
    }

    private boolean isExist(HumanParts humanParts) {
        HumanPartsDao humanPartsDao = daoSession.getHumanPartsDao();
        QueryBuilder<HumanParts> qb = humanPartsDao.queryBuilder();
        qb.where(HumanPartsDao.Properties.Aid.eq(humanParts.getAid()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<HumanParts> humanPartsList) {
        HumanPartsDao humanPartsDao = daoSession.getHumanPartsDao();
        for (int i = 0; i < humanPartsList.size(); i++) {
            if (!isExist(humanPartsList.get(i))){
                humanPartsDao.insert(humanPartsList.get(i));
            }
        }

    }
    public List<HumanParts> getDataList(){
        HumanPartsDao humanPartsDao = daoSession.getHumanPartsDao();
        QueryBuilder<HumanParts> qb = humanPartsDao.queryBuilder();
        qb.where(HumanPartsDao.Properties.Id.isNotNull());
        return qb.list();
    }
}
