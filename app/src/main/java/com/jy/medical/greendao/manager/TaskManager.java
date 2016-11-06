package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.TaskBeanData;
import com.jy.medical.greendao.entities.TaskBeanData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.TaskBeanDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by jamy on 16/6/16.
 * 在这个类中添加不同的查询条件
 */
public class TaskManager extends BaseDao<TaskBeanData> {
    public TaskManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private TaskBeanData loadById(long id) {
        return daoSession.getTaskBeanDataDao().load(id);
    }

    private boolean isExist(TaskBeanData taskBeanData) {
        TaskBeanDataDao taskBeanDataDao = daoSession.getTaskBeanDataDao();
        QueryBuilder<TaskBeanData> qb = taskBeanDataDao.queryBuilder();
        qb.where(TaskBeanDataDao.Properties.TaskNo.eq(taskBeanData.getTaskNo()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<TaskBeanData> taskBeanDataList) {
        TaskBeanDataDao taskBeanDataDao = daoSession.getTaskBeanDataDao();
        for (int i = 0; i < taskBeanDataList.size(); i++) {
            if (!isExist(taskBeanDataList.get(i))){
                taskBeanDataDao.insert(taskBeanDataList.get(i));
            }
        }

    }

    /**
     * 获取某个对象的主键ID
     *
     * @param taskBeanData
     * @return
     */
    private long getID(TaskBeanData taskBeanData) {

        return daoSession.getTaskBeanDataDao().getKey(taskBeanData);
    }


    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    private void deleteById(long id) {

        daoSession.getTaskBeanDataDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     *
     * @param ids
     */
    private void deleteByIds(List<Long> ids) {

        daoSession.getTaskBeanDataDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在次添加一些TaskBeanData特有的数据库操作语句
     * ************************************/

}
