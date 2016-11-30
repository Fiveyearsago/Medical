package com.jy.medical.greendao.util;

/**
 * Created by songran on 16/11/3.
 */


import android.content.Context;

import com.jy.medical.greendao.entities.SearchData;
import com.jy.medical.greendao.manager.BaseInfoDataManager;
import com.jy.medical.greendao.manager.CategoryDataManager;
import com.jy.medical.greendao.manager.CityDataManager;
import com.jy.medical.greendao.manager.ClaimManager;
import com.jy.medical.greendao.manager.ContactManager;
import com.jy.medical.greendao.manager.DeathDataManager;
import com.jy.medical.greendao.manager.DelayDataManager;
import com.jy.medical.greendao.manager.DiagnoseManager;
import com.jy.medical.greendao.manager.EarningDataManager;
import com.jy.medical.greendao.manager.HandleDataManager;
import com.jy.medical.greendao.manager.HospitalDataManager;
import com.jy.medical.greendao.manager.HumanPartsManager;
import com.jy.medical.greendao.manager.MedicalDepartmentManager;
import com.jy.medical.greendao.manager.MedicalVisitManager;
import com.jy.medical.greendao.manager.NursingDataManager;
import com.jy.medical.greendao.manager.SearchDataManager;
import com.jy.medical.greendao.manager.SelectedDepartmentManager;
import com.jy.medical.greendao.manager.SelectedDiagnoseManager;
import com.jy.medical.greendao.manager.SelectedHospitalManager;
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
    private static SearchDataManager searchDataManager;
    private static MedicalVisitManager medicalVisitManager;
    private static SelectedHospitalManager selectedHospitalManager;
    private static SelectedDepartmentManager selectedDepartmentManager;
    private static DiagnoseManager diagnoseManager;
    private static SelectedDiagnoseManager selectedDiagnoseManager;
    private static HumanPartsManager humanPartsManager;
    private static CategoryDataManager categoryDataManager;
    private static NursingDataManager nursingDataManager;
    private static BaseInfoDataManager baseInfoDataManager;
    private static EarningDataManager earningDataManager;
    private static DelayDataManager delayDataManager;
    private static HandleDataManager handleDataManager;
    private static DeathDataManager deathDataManager;
    public static Context context;

    public static void init(Context context) {
        DaoUtils.context = context.getApplicationContext();
    }


    /**
     * 单列模式获取Manager对象
     */
    public static DeathDataManager getDeathDataInstance() {
        if (deathDataManager == null) {
            deathDataManager = new DeathDataManager(context);
        }
        return deathDataManager;
    }

    public static HandleDataManager getHandleDataInstance() {
        if (handleDataManager == null) {
            handleDataManager = new HandleDataManager(context);
        }
        return handleDataManager;
    }

    public static DelayDataManager getDelayDataInstance() {
        if (delayDataManager == null) {
            delayDataManager = new DelayDataManager(context);
        }
        return delayDataManager;
    }

    public static EarningDataManager getEarningDataInstance() {
        if (earningDataManager == null) {
            earningDataManager = new EarningDataManager(context);
        }
        return earningDataManager;
    }

    public static BaseInfoDataManager getBaseInfoDataInstance() {
        if (baseInfoDataManager == null) {
            baseInfoDataManager = new BaseInfoDataManager(context);
        }
        return baseInfoDataManager;
    }

    public static NursingDataManager getNursingDataInstance() {
        if (nursingDataManager == null) {
            nursingDataManager = new NursingDataManager(context);
        }
        return nursingDataManager;
    }

    public static CategoryDataManager getCategoryDataInstance() {
        if (categoryDataManager == null) {
            categoryDataManager = new CategoryDataManager(context);
        }
        return categoryDataManager;
    }

    public static HumanPartsManager getHumanPartsInstance() {
        if (humanPartsManager == null) {
            humanPartsManager = new HumanPartsManager(context);
        }
        return humanPartsManager;
    }

    public static SelectedDiagnoseManager getSelectedDiagnoseInstance() {
        if (selectedDiagnoseManager == null) {
            selectedDiagnoseManager = new SelectedDiagnoseManager(context);
        }
        return selectedDiagnoseManager;
    }

    public static DiagnoseManager getDiagnoseInstance() {
        if (diagnoseManager == null) {
            diagnoseManager = new DiagnoseManager(context);
        }
        return diagnoseManager;
    }

    public static SelectedDepartmentManager getSelectedDepartmentInstance() {
        if (selectedDepartmentManager == null) {
            selectedDepartmentManager = new SelectedDepartmentManager(context);
        }
        return selectedDepartmentManager;
    }

    public static SelectedHospitalManager getSelectedHospitaInstance() {
        if (selectedHospitalManager == null) {
            selectedHospitalManager = new SelectedHospitalManager(context);
        }
        return selectedHospitalManager;
    }

    public static MedicalVisitManager getMedicalVisitInstance() {
        if (medicalVisitManager == null) {
            medicalVisitManager = new MedicalVisitManager(context);
        }
        return medicalVisitManager;
    }


    public static SearchDataManager getSearchDataInstance() {
        if (searchDataManager == null) {
            searchDataManager = new SearchDataManager(context);
        }
        return searchDataManager;
    }

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