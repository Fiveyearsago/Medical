package com.jy.medical.greendao.manager;

/**
 * Created by songran on 16/11/3.
 */

import android.content.Context;
import android.util.Log;

import com.jy.medical.greendao.entities.ClaimBean;
import com.jy.medical.greendao.entities.ClaimBeanData;
import com.jy.medical.greendao.entities.PlatformData;
import com.jy.medical.greendao.entities.TaskBeanData;
import com.jy.medical.greendao.gen.BaseDao;
import com.jy.medical.greendao.gen.ClaimBeanDataDao;
import com.jy.medical.greendao.gen.TaskBeanDataDao;
import com.jy.medical.util.TimeUtil;

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
                String phone = datas.get(i).getMobilePhone();
                QueryBuilder<TaskBeanData> queryBuilder = taskBeanDataDao.queryBuilder();
                queryBuilder.where(TaskBeanDataDao.Properties.ClaimId.eq(claimId));
                List<TaskBeanData> taskBeanDatas = queryBuilder.list();
                for (int j = 0; j < taskBeanDatas.size(); j++) {
                    String time = taskBeanDatas.get(j).getDispatchDate();
                    String tag = taskBeanDatas.get(j).getTaskState();
                    String taskNo = taskBeanDatas.get(j).getTaskNo();
                    String taskType = taskBeanDatas.get(j).getTaskType();
                    String commitFlag = taskBeanDatas.get(j).getCommitFlag();
                    String isDoingFlag = taskBeanDatas.get(j).getIsDoingFlag();
                    list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                }
            }
        }

        return list;
    }

    public List<PlatformData> selectAllData(String taskType1) {
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
                String phone = datas.get(i).getMobilePhone();
                QueryBuilder<TaskBeanData> queryBuilder = taskBeanDataDao.queryBuilder();
                queryBuilder.where(TaskBeanDataDao.Properties.ClaimId.eq(claimId));
                List<TaskBeanData> taskBeanDatas = queryBuilder.list();
                for (int j = 0; j < taskBeanDatas.size(); j++) {
                    String time = taskBeanDatas.get(j).getDispatchDate();
                    String tag = taskBeanDatas.get(j).getTaskState();
                    String taskNo = taskBeanDatas.get(j).getTaskNo();
                    String taskType = taskBeanDatas.get(j).getTaskType();
                    String commitFlag = taskBeanDatas.get(j).getCommitFlag();
                    String isDoingFlag = taskBeanDatas.get(j).getIsDoingFlag();

                    if (taskType1.equals("")) {
                        list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                    } else if (!taskType1.equals("") && taskType.equals(taskType1))
                        list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                }
            }
        }

        return list;
    }

    public List<PlatformData> selectDayData(String taskType1, int days) {
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
                String phone = datas.get(i).getMobilePhone();
                QueryBuilder<TaskBeanData> queryBuilder = taskBeanDataDao.queryBuilder();
                queryBuilder.where(TaskBeanDataDao.Properties.ClaimId.eq(claimId));
                List<TaskBeanData> taskBeanDatas = queryBuilder.list();
                for (int j = 0; j < taskBeanDatas.size(); j++) {
                    String time = taskBeanDatas.get(j).getDispatchDate();
                    String tag = taskBeanDatas.get(j).getTaskState();
                    String taskNo = taskBeanDatas.get(j).getTaskNo();
                    String taskType = taskBeanDatas.get(j).getTaskType();
                    String commitFlag = taskBeanDatas.get(j).getCommitFlag();
                    String isDoingFlag = taskBeanDatas.get(j).getIsDoingFlag();

                    Log.i("days", TimeUtil.getGapCount(time) + "");
                    if (taskType1.equals("") && TimeUtil.getGapCount(time) == days) {
                        list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                    } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) == days)
                        list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                }
            }
        }

        return list;
    }

    public List<PlatformData> selectAllData(String taskType1, int taskFlag) {
        //taskFlag 0：全部，1：未超时 2：已超时 3：已完成
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
                String phone = datas.get(i).getMobilePhone();
                QueryBuilder<TaskBeanData> queryBuilder = taskBeanDataDao.queryBuilder();
                queryBuilder.where(TaskBeanDataDao.Properties.ClaimId.eq(claimId));
                List<TaskBeanData> taskBeanDatas = queryBuilder.list();
                for (int j = 0; j < taskBeanDatas.size(); j++) {
                    String time = taskBeanDatas.get(j).getDispatchDate();
                    String tag = taskBeanDatas.get(j).getTaskState();
                    String taskNo = taskBeanDatas.get(j).getTaskNo();
                    String taskType = taskBeanDatas.get(j).getTaskType();
                    String commitFlag = taskBeanDatas.get(j).getCommitFlag();
                    String isDoingFlag = taskBeanDatas.get(j).getIsDoingFlag();

                    switch (taskFlag) {
                        case 0:
                            //全部任务
                            if (taskType1.equals("")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1)) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 1:
                            //未超时任务
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) >= 0 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) >= 0 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 2:
                            //已超时任务
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) < 0 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) < 0 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 3:
                            //已完成任务
                            if (taskType1.equals("") && commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                    }

                }
            }
        }

        return list;
    }

    public List<PlatformData> selectTaskTypeData2(String taskType1, int taskFlag) {
        //taskFlag 0：全部，1：超时3天 2：超时5天 3：超时7天 4：超时30天
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
                String phone = datas.get(i).getMobilePhone();
                QueryBuilder<TaskBeanData> queryBuilder = taskBeanDataDao.queryBuilder();
                queryBuilder.where(TaskBeanDataDao.Properties.ClaimId.eq(claimId));
                List<TaskBeanData> taskBeanDatas = queryBuilder.list();
                for (int j = 0; j < taskBeanDatas.size(); j++) {
                    String time = taskBeanDatas.get(j).getDispatchDate();
                    String tag = taskBeanDatas.get(j).getTaskState();
                    String taskNo = taskBeanDatas.get(j).getTaskNo();
                    String taskType = taskBeanDatas.get(j).getTaskType();
                    String commitFlag = taskBeanDatas.get(j).getCommitFlag();
                    String isDoingFlag = taskBeanDatas.get(j).getIsDoingFlag();

                    switch (taskFlag) {
                        case 0:
                            //全部超时任务
                            if (taskType1.equals("")&&TimeUtil.getGapCount(time) < 0&& !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1)&&TimeUtil.getGapCount(time) < 0&& !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 1:
                            //超时3天
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) > -5&&TimeUtil.getGapCount(time) <= -3 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) > -5&&TimeUtil.getGapCount(time) <= -3 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 2:
                            //超时5天
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) > -7&&TimeUtil.getGapCount(time) <= -5 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) > -7&&TimeUtil.getGapCount(time) <= -5 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 3:
                            //超时7天
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) > -30&&TimeUtil.getGapCount(time) <= -5 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) > -30&&TimeUtil.getGapCount(time) <= -5 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 4:
                            //超时30天
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) <= -30 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) <= -30&& !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                    }

                }
            }
        }

        return list;
    }
    public List<PlatformData> selectTaskTypeData1(String taskType1, int taskFlag) {
        //taskFlag 0：全部，1：3天后超时 2：5天后超时 3：7天后超时 4：30天后超时
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
                String phone = datas.get(i).getMobilePhone();
                QueryBuilder<TaskBeanData> queryBuilder = taskBeanDataDao.queryBuilder();
                queryBuilder.where(TaskBeanDataDao.Properties.ClaimId.eq(claimId));
                List<TaskBeanData> taskBeanDatas = queryBuilder.list();
                for (int j = 0; j < taskBeanDatas.size(); j++) {
                    String time = taskBeanDatas.get(j).getDispatchDate();
                    String tag = taskBeanDatas.get(j).getTaskState();
                    String taskNo = taskBeanDatas.get(j).getTaskNo();
                    String taskType = taskBeanDatas.get(j).getTaskType();
                    String commitFlag = taskBeanDatas.get(j).getCommitFlag();
                    String isDoingFlag = taskBeanDatas.get(j).getIsDoingFlag();

                    switch (taskFlag) {
                        case 0:
                            //全部超时任务
                            if (taskType1.equals("")&&TimeUtil.getGapCount(time) >= 0&& !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1)&&TimeUtil.getGapCount(time) >= 0&& !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 1:
                            //超时3天
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) >=2&&TimeUtil.getGapCount(time) <4 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) >=2&&TimeUtil.getGapCount(time) <4 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 2:
                            //超时5天
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) >= 4&&TimeUtil.getGapCount(time) < 6 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) >=4&&TimeUtil.getGapCount(time) <= 6 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 3:
                            //超时7天
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) >=6&&TimeUtil.getGapCount(time) <29 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) >= 6&&TimeUtil.getGapCount(time) <=29 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                        case 4:
                            //超时30天
                            if (taskType1.equals("") && TimeUtil.getGapCount(time) >=29 && !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));

                            } else if (!taskType1.equals("") && taskType.equals(taskType1) && TimeUtil.getGapCount(time) >=29&& !commitFlag.equals("1")) {
                                list.add(new PlatformData(claimId, taskNo, taskType, peopleName, time, tag, reportNum, phone, commitFlag, isDoingFlag));
                            }
                            break;
                    }

                }
            }
        }

        return list;
    }

}
