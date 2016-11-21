package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.CategoryData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.CategoryDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class CategoryDataManager extends BaseDao<CategoryData> {
    public CategoryDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private CategoryData loadById(long id) {
        return daoSession.getCategoryDataDao().load(id);
    }

    private boolean isExist(CategoryData categoryData) {
        CategoryDataDao categoryDataDao = daoSession.getCategoryDataDao();
        QueryBuilder<CategoryData> qb = categoryDataDao.queryBuilder();
        qb.where(CategoryDataDao.Properties.Key.eq(categoryData.getKey()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<CategoryData> categoryDataList) {
        CategoryDataDao categoryDataDao = daoSession.getCategoryDataDao();
        for (int i = 0; i < categoryDataList.size(); i++) {
            if (!isExist(categoryDataList.get(i))){
                categoryDataDao.insert(categoryDataList.get(i));
            }
        }

    }
    public List<CategoryData> getDataList(String typeCode){
        CategoryDataDao categoryDataDao = daoSession.getCategoryDataDao();
        QueryBuilder<CategoryData> qb = categoryDataDao.queryBuilder();
        qb.where(CategoryDataDao.Properties.TypeCode.eq(typeCode));
        return qb.list();
    }
}
