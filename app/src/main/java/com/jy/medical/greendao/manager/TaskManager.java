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
 * Created by songran on 16/11/3.
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

    public TaskBeanData getData(String taskNo){
        TaskBeanDataDao taskBeanDataDao = daoSession.getTaskBeanDataDao();
        QueryBuilder<TaskBeanData> qb = taskBeanDataDao.queryBuilder();
        qb.where(TaskBeanDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0){
            return qb.list().get(0);
        }else {
            return null;
        }
    }
    public void updateCommitFlag(String taskNo,String commitFlag){
        TaskBeanDataDao taskBeanDataDao = daoSession.getTaskBeanDataDao();
        QueryBuilder<TaskBeanData> qb = taskBeanDataDao.queryBuilder();
        qb.where(TaskBeanDataDao.Properties.TaskNo.eq(taskNo));
        if (qb.list().size()>0) {
            TaskBeanData taskBeanData= qb.list().get(0);
            taskBeanData.setCommitFlag(commitFlag);
            taskBeanDataDao.update(taskBeanData);
        }
    }

}
