package com.jy.medical.controller;

import com.jy.medical.greendao.entities.ClaimBeanData;
import com.jy.medical.greendao.gen.ClaimBeanDataDao;
import com.jy.medical.greendao.gen.DaoSession;
import com.jy.medical.greendao.manager.DaoManager;

import org.greenrobot.greendao.annotation.NotNull;

import java.util.List;

/**
 * Created by songran on 16/11/4.
 */

public class DataController {

    public static void insertClaimBean(@NotNull List<ClaimBeanData> claimBeanDataList){
        DaoManager daoMaster = DaoManager.getInstance();
        DaoSession session = daoMaster.getDaoSession();
        ClaimBeanDataDao claimBeanDataDao = session.getClaimBeanDataDao();
//        claimBeanDataDao.insertInTx(claimBeanDataList);
//        claimBeanDataDao.insertOrReplaceInTx(claimBeanDataList);
        claimBeanDataDao.insertOrReplaceInTx(claimBeanDataList);
    }
}
