package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;

import com.jy.medical.greendao.entities.ClaimBean;
import com.jy.medical.greendao.entities.ClaimBeanData;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.greendao.entities.TaskBeanData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.ClaimBeanDataDao;
import com.jy.medical.greendao.gen.TaskBeanDataDao;

import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by songran on 16/11/3.
 */
public class ClaimManager extends BaseDao<ClaimBeanData> {
    public ClaimManager(Context context) {
        super(context);
    }

    /***************************数据库查询*************************/

    /**
     * 通过ID查询对象
     *
     * @param id
     * @return
     */
    private ClaimBeanData loadById(long id) {
        return daoSession.getClaimBeanDataDao().load(id);
    }

    private boolean isExist(ClaimBeanData claimBeanData) {
        ClaimBeanDataDao claimBeanDataDao = daoSession.getClaimBeanDataDao();
        QueryBuilder<ClaimBeanData> qb = claimBeanDataDao.queryBuilder();
        qb.where(ClaimBeanDataDao.Properties.ClaimId.eq(claimBeanData.getClaimId()));
        qb.list();
        return qb.list().size() > 0 ? true : false;
    }

    public void insertData(@NotNull List<ClaimBeanData> claimBeanDataList) {
        ClaimBeanDataDao claimBeanDataDao = daoSession.getClaimBeanDataDao();
        for (int i = 0; i < claimBeanDataList.size(); i++) {
            if (!isExist(claimBeanDataList.get(i))) {
                claimBeanDataDao.insert(claimBeanDataList.get(i));
            }
        }

    }

    /**
     * 获取某个对象的主键ID
     *
     * @param claimBeanData
     * @return
     */
    private long getID(ClaimBeanData claimBeanData) {

        return daoSession.getClaimBeanDataDao().getKey(claimBeanData);
    }


    /***************************数据库删除*************************/

    /**
     * 根据ID进行数据库的删除操作
     *
     * @param id
     */
    private void deleteById(long id) {

        daoSession.getClaimBeanDataDao().deleteByKey(id);
    }


    /**
     * 根据ID同步删除数据库操作
     *
     * @param ids
     */
    private void deleteByIds(List<Long> ids) {

        daoSession.getClaimBeanDataDao().deleteByKeyInTx(ids);
    }

    /***********************************
     * 在次添加一些ClaimBeanData特有的数据库操作语句
     ************************************/
    public List<PlatformData> selectAllData() {
        List<PlatformData> list = new ArrayList<>();
        ClaimBeanDataDao claimBeanDataDao = daoSession.getClaimBeanDataDao();
        TaskBeanDataDao taskBeanDataDao = daoSession.getTaskBeanDataDao();
        QueryBuilder<ClaimBeanData> qb = claimBeanDataDao.queryBuilder();
        qb.where(ClaimBeanDataDao.Properties.ClaimId.isNotNull());
        List<ClaimBeanData> datas = qb.list();
        if (datas != null) {
            for (int i = 0; i < datas.size(); i++) {
                String peopleName = datas.get(i).getInsuredName();
                String reportNum = datas.get(i).getReportNo();
                String claimId = datas.get(i).getClaimId();
                String phone=datas.get(i).getMobilePhone();
                QueryBuilder<TaskBeanData> queryBuilder = taskBeanDataDao.queryBuilder();
                queryBuilder.where(TaskBeanDataDao.Properties.ClaimId.eq(claimId));
                List<TaskBeanData> taskBeanDatas= queryBuilder.list();
                for (int j = 0; j < taskBeanDatas.size(); j++) {
                    String time = taskBeanDatas.get(j).getDispatchDate();
                    String tag = taskBeanDatas.get(j).getTaskState();
                    String taskNo = taskBeanDatas.get(j).getTaskNo();
                    list.add(new PlatformData(claimId, taskNo, peopleName, time, tag, reportNum,phone));
                }
            }
        }

        return list;
    }

}
