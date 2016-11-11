package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.CityData;
import com.jy.medical.greendao.entities.CityData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.CityDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class CityDataManager extends BaseDao<CityData> {
    public CityDataManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private CityData loadById(long id) {
        return daoSession.getCityDataDao().load(id);
    }

    private boolean isExist(CityData cityData) {
        CityDataDao cityDataDao = daoSession.getCityDataDao();
        QueryBuilder<CityData> qb = cityDataDao.queryBuilder();
        qb.where(CityDataDao.Properties.Aid.eq(cityData.getAid()));
        List<CityData> list=qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public List<CityData> selectAllProvince(){
        CityDataDao cityDataDao=daoSession.getCityDataDao();
        QueryBuilder<CityData> qb = cityDataDao.queryBuilder();
        qb.where(CityDataDao.Properties.Level.eq("2"));
        return qb.list();
    }
    public List<CityData> selectAllCity(String pid){
        CityDataDao cityDataDao=daoSession.getCityDataDao();
        QueryBuilder<CityData> qb = cityDataDao.queryBuilder();
        qb.where(qb.and(CityDataDao.Properties.Level.eq("3"),CityDataDao.Properties.Pid.eq(pid)));
        return qb.list();
    }
    public void insertData(@NotNull List<CityData> cityDataList) {
        CityDataDao cityDataDao = daoSession.getCityDataDao();
        for (int i = 0; i < cityDataList.size(); i++) {
            if (!isExist(cityDataList.get(i))){
                cityDataDao.insert(cityDataList.get(i));
            }else {
                cityDataDao.update(cityDataList.get(i));
            }
        }

    }

    /**
     * 获取某个对象的主键ID
     *
     * @param cityData
     * @return
     */
    public long getID(CityData cityData) {

        return daoSession.getCityDataDao().getKey(cityData);
    }


    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    public void deleteById(long id) {

        daoSession.getCityDataDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     *
     * @param ids
     */
    private void deleteByIds(List<Long> ids) {

        daoSession.getCityDataDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在次添加一些CityData特有的数据库操作语句
     * ************************************/

}
