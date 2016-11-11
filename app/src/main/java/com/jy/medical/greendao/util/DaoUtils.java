package com.jy.medical.greendao.util;

/**
 * Created by songran on 16/11/3.
 */


import android.content.Context;

import com.jy.medical.greendao.manager.CityDataManager;
import com.jy.medical.greendao.manager.ClaimManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.HospitalDataManager;
import com.jy.medical.greendao.manager.MedicalDepartmentManager;
import com.jy.medical.greendao.manager.TaskManager;
import com.jy.medical.greendao.manager.TaskPhotoManager;

/**
 * Created by jinfangmei on 2016/6/16.
 */
public class DaoUtils {
    private static ClaimManager claimManager;
    private static TaskManager taskManager;
    private static TaskPhotoManager taskPhotoManager;
    private static ContactManager contactManager;
    private static CityDataManager cityDataManager;
    private static HospitalDataManager hospitalDataManager;
    private static MedicalDepartmentManager medicalDepartmentManager;
    public static Context context;

    public static void init(Context context) {
        DaoUtils.context = context.getApplicationContext();
    }


    /**
     * 单列模式获取Manager对象
     */
    public static HospitalDataManager getHospitalInstance() {
        if (hospitalDataManager == null) {
            hospitalDataManager = new HospitalDataManager(context);
        }
        return hospitalDataManager;
    }

    public static MedicalDepartmentManager getDepartmentInstance() {
        if (medicalDepartmentManager == null) {
            medicalDepartmentManager = new MedicalDepartmentManager(context);
        }
        return medicalDepartmentManager;
    }

    public static ContactManager getContactInstance() {
        if (contactManager == null) {
            contactManager = new ContactManager(context);
        }
        return contactManager;
    }

    public static CityDataManager getCityDataInstance() {
        if (cityDataManager == null) {
            cityDataManager = new CityDataManager(context);
        }
        return cityDataManager;
    }

    public static ClaimManager getClaimInstance() {
        if (claimManager == null) {
            claimManager = new ClaimManager(context);
        }
        return claimManager;
    }

    public static TaskManager getTaskInstance() {
        if (taskManager == null) {
            taskManager = new TaskManager(context);
        }
        return taskManager;
    }

    public static TaskPhotoManager getTaskPhotoInstance() {
        if (taskPhotoManager == null) {
            taskPhotoManager = new TaskPhotoManager(context);
        }
        return taskPhotoManager;
    }
}