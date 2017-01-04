package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.Inquire;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.InquireDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class InquireManager extends BaseDao<Inquire> {
    public InquireManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private Inquire loadById(long id) {
        return daoSession.getInquireDao().load(id);
    }

    private boolean isExist(Inquire inquire) {
        InquireDao inquireDao = daoSession.getInquireDao();
        QueryBuilder<Inquire> qb = inquireDao.queryBuilder();
        qb.where(qb.and(InquireDao.Properties.TaskNo.eq(inquire.getTaskNo()), InquireDao.Properties.Name.eq(inquire.getName()), InquireDao.Properties.PhoneNum.eq(inquire.getPhoneNum())));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public List<Inquire> selectAllContact(String taskNo) {
        InquireDao inquireDao = daoSession.getInquireDao();
        QueryBuilder<Inquire> qb = inquireDao.queryBuilder();
        qb.where(InquireDao.Properties.TaskNo.eq(taskNo));
        return qb.list();

    }

    public void insertData(@NotNull List<Inquire> inquireList) {
        InquireDao inquireDao = daoSession.getInquireDao();
        for (int i = 0; i < inquireList.size(); i++) {
            if (!isExist(inquireList.get(i))) {
                inquireDao.insert(inquireList.get(i));
            }
        }

    }

    /**
     * 获取某个对象的主键ID
     *
     * @param inquire
     * @return
     */
    public long getID(Inquire inquire) {

        return daoSession.getInquireDao().getKey(inquire);
    }


    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    public void deleteById(long id) {

        daoSession.getInquireDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     *
     * @param ids
     */
    private void deleteByIds(List<Long> ids) {

        daoSession.getInquireDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在次添加一些Inquire特有的数据库操作语句
     ************************************/
    public void deleteSingleData(Inquire inquire) {
        InquireDao inquireDao = daoSession.getInquireDao();
        if (isExist(inquire)) {
            inquireDao.delete(inquire);
        }
    }
}
