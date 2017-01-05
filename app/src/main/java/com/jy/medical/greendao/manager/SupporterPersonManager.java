package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.SupporterPerson;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.SupporterPersonDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class SupporterPersonManager extends BaseDao<SupporterPerson> {
    public SupporterPersonManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private SupporterPerson loadById(long id) {
        return daoSession.getSupporterPersonDao().load(id);
    }

    public boolean isExist(SupporterPerson supporterPerson) {
        SupporterPersonDao supporterPersonDao = daoSession.getSupporterPersonDao();
        QueryBuilder<SupporterPerson> qb = supporterPersonDao.queryBuilder();
        qb.where(qb.and(SupporterPersonDao.Properties.TaskNo.eq(supporterPerson.getTaskNo()), SupporterPersonDao.Properties.Name.eq(supporterPerson.getName())));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public List<SupporterPerson> selectAllContact(String taskNo){
        SupporterPersonDao supporterPersonDao=daoSession.getSupporterPersonDao();
        QueryBuilder<SupporterPerson> qb = supporterPersonDao.queryBuilder();
        qb.where(SupporterPersonDao.Properties.TaskNo.eq(taskNo));
        return qb.list();

    }

    public void updateData(SupporterPerson supporterPerson) {
        SupporterPersonDao supporterPersonDao = daoSession.getSupporterPersonDao();
        supporterPersonDao.update(supporterPerson);
    }
    public void insertData(@NotNull List<SupporterPerson> supporterPersonList) {
        SupporterPersonDao supporterPersonDao = daoSession.getSupporterPersonDao();
        for (int i = 0; i < supporterPersonList.size(); i++) {
            if (!isExist(supporterPersonList.get(i))){
                supporterPersonDao.insert(supporterPersonList.get(i));
            }
        }

    }

    public void insertSingleData(SupporterPerson supporterPerson) {
        SupporterPersonDao supporterPersonDao = daoSession.getSupporterPersonDao();
        if (!isExist(supporterPerson)) {
            supporterPersonDao.insert(supporterPerson);
        }

    }

    /**
     * 获取某个对象的主键ID
     *
     * @param supporterPerson
     * @return
     */
    public long getID(SupporterPerson supporterPerson) {

        return daoSession.getSupporterPersonDao().getKey(supporterPerson);
    }


    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    public void deleteById(long id) {

        daoSession.getSupporterPersonDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     *
     * @param ids
     */
    private void deleteByIds(List<Long> ids) {

        daoSession.getSupporterPersonDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在次添加一些SupporterPerson特有的数据库操作语句
     * ************************************/
    public void deleteSingleData(SupporterPerson supporterPerson) {
        SupporterPersonDao supporterPersonDao = daoSession.getSupporterPersonDao();
        if (isExist(supporterPerson)) {
            supporterPersonDao.delete(supporterPerson);
        }
    }
}
