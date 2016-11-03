package com.jy.medical.greendao.util;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.entities.Student;
import com.jy.medical.greendao.gen.StudentDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jamy on 16/6/16.
 * 在这个类中添加不同的查询条件
 */
public class StudentManager extends BaseDao<Student> {
    public StudentManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private Student loadById(long id) {

        return daoSession.getStudentDao().load(id);
    }

    /**
     * 获取某个对象的主键ID
     *
     * @param student
     * @return
     */
    private long getID(Student student) {

        return daoSession.getStudentDao().getKey(student);
    }

    /**
     * 通过名字获取Customer对象
     *
     * @return
     */
    private List<Student> getStudentByName(String key) {
        QueryBuilder queryBuilder = daoSession.getStudentDao().queryBuilder();
        queryBuilder.where(StudentDao.Properties.Name.eq(key));
        int size = queryBuilder.list().size();
        if (size > 0) {
            return queryBuilder.list();
        } else {
            return null;
        }
    }

    /**
     * 通过名字获取Customer对象
     *
     * @return
     */
    private List<Long> getIdByName(String key) {
        List<Student> students = getStudentByName(key);
        List<Long> ids = new ArrayList<Long>();
        int size = students.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                ids.add(students.get(i).getId());
            }
            return ids;
        } else {
            return null;
        }
    }

    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    private void deleteById(long id) {

        daoSession.getStudentDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     *
     * @param ids
     */
    private void deleteByIds(List<Long> ids) {

        daoSession.getStudentDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在次添加一些Student特有的数据库操作语句
     * ************************************/

}
