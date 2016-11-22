package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.TaskPhoto;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.TaskPhotoDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class TaskPhotoManager extends BaseDao<TaskPhoto> {
    public TaskPhotoManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private TaskPhoto loadById(long id) {
        return daoSession.getTaskPhotoDao().load(id);
    }

    private boolean isExist(TaskPhoto taskPhoto) {
        TaskPhotoDao taskPhotoDao = daoSession.getTaskPhotoDao();
        QueryBuilder<TaskPhoto> qb = taskPhotoDao.queryBuilder();
        qb.where(qb.and(TaskPhotoDao.Properties.PhotoPath.eq(taskPhoto.getPhotoPath()),TaskPhotoDao.Properties.TaskNo.eq(taskPhoto.getTaskNo())));
        List<TaskPhoto> list=qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public List<TaskPhoto> selectAllPhoto(String taskNo){
        TaskPhotoDao taskPhotoDao=daoSession.getTaskPhotoDao();
        QueryBuilder<TaskPhoto> qb = taskPhotoDao.queryBuilder();
        qb.where(TaskPhotoDao.Properties.TaskNo.eq(taskNo));
        return qb.list();

    }
    public void insertData(@NotNull List<TaskPhoto> taskPhotoList) {
        TaskPhotoDao taskPhotoDao = daoSession.getTaskPhotoDao();
        for (int i = 0; i < taskPhotoList.size(); i++) {
            if (!isExist(taskPhotoList.get(i))){
                taskPhotoDao.insert(taskPhotoList.get(i));
            }
        }

    }
    public void insertSingleData(@NotNull TaskPhoto taskPhoto) {
        TaskPhotoDao taskPhotoDao = daoSession.getTaskPhotoDao();
            if (!isExist(taskPhoto)){
                taskPhotoDao.insert(taskPhoto);
        }

    }

    /**
     * 获取某个对象的主键ID
     *
     * @param taskPhoto
     * @return
     */
    public long getID(TaskPhoto taskPhoto) {

        return daoSession.getTaskPhotoDao().getKey(taskPhoto);
    }


    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    public void deleteById(long id) {

        daoSession.getTaskPhotoDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     *
     * @param ids
     */
    private void deleteByIds(List<Long> ids) {

        daoSession.getTaskPhotoDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在次添加一些TaskPhoto特有的数据库操作语句
     * ************************************/

}
