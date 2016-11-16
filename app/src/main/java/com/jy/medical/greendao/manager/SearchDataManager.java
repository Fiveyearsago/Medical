package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.SearchData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.SearchDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;


/**
 * Created by SongRan on 16/6/16.
 * 在这个类中添加不同的查询条件
 */
public class SearchDataManager extends BaseDao<SearchData> {
    public SearchDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private SearchData loadById(long id) {
        return daoSession.getSearchDataDao().load(id);
    }

    private boolean isExist(SearchData searchData) {
        SearchDataDao searchDataDao = daoSession.getSearchDataDao();
        QueryBuilder<SearchData> qb = searchDataDao.queryBuilder();
        qb.where(qb.and(SearchDataDao.Properties.Kind.eq(searchData.getKind()),SearchDataDao.Properties.SearchText.eq(searchData.getSearchText())));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<SearchData> searchDataList) {
        SearchDataDao searchDataDao = daoSession.getSearchDataDao();
        for (int i = 0; i < searchDataList.size(); i++) {
            if (!isExist(searchDataList.get(i))){
                searchDataDao.insert(searchDataList.get(i));
            }
        }

    }
    public void insertSingleData(@NotNull SearchData searchData) {
        SearchDataDao searchDataDao = daoSession.getSearchDataDao();
            if (!isExist(searchData)){
                searchDataDao.insert(searchData);
            }

    }
    public List<SearchData> getData(String kind){
        SearchDataDao searchDataDao = daoSession.getSearchDataDao();
        QueryBuilder<SearchData> qb = searchDataDao.queryBuilder();
        qb.where(SearchDataDao.Properties.Kind.eq(kind));
        return qb.orderDesc(SearchDataDao.Properties.Id).list();
    }
    public  void deleteData(String kind){
        SearchDataDao searchDataDao = daoSession.getSearchDataDao();
        QueryBuilder<SearchData> qb = searchDataDao.queryBuilder();
        qb.where(SearchDataDao.Properties.Kind.eq(kind));
        List<SearchData> list= qb.orderDesc(SearchDataDao.Properties.Id).list();
        for (int i = 0; i < list.size(); i++) {
            daoSession.getSearchDataDao().delete(list.get(i));
        }
    }
}
