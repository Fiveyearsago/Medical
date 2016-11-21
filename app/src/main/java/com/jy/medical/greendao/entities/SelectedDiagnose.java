package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/16.
 */
@Entity
public class SelectedDiagnose {
    @Id
    Long id;
    private String taskNo;
    private String diagnoseId;
    private String diagnoseCode;
    private String diagnoseName;
    private String treatmentMode;
    private String treatmentModeName;

    public SelectedDiagnose(String taskNo, String diagnoseId, String diagnoseCode, String diagnoseName, String treatmentMode, String treatmentModeName) {
        this.taskNo = taskNo;
        this.diagnoseId = diagnoseId;
        this.diagnoseCode = diagnoseCode;
        this.diagnoseName = diagnoseName;
        this.treatmentMode = treatmentMode;
        this.treatmentModeName = treatmentModeName;
    }

    @Generated(hash = 1082463947)
    public SelectedDiagnose(Long id, String taskNo, String diagnoseId, String diagnoseCode, String diagnoseName, String treatmentMode,
            String treatmentModeName) {
        this.id = id;
        this.taskNo = taskNo;
        this.diagnoseId = diagnoseId;
        this.diagnoseCode = diagnoseCode;
        this.diagnoseName = diagnoseName;
        this.treatmentMode = treatmentMode;
        this.treatmentModeName = treatmentModeName;
    }

    @Generated(hash = 1561465160)
    public SelectedDiagnose() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskNo() {
        return this.taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getDiagnoseId() {
        return this.diagnoseId;
    }

    public void setDiagnoseId(String diagnoseId) {
        this.diagnoseId = diagnoseId;
    }

    public String getTreatmentMode() {
        return this.treatmentMode;
    }

    public void setTreatmentMode(String treatmentMode) {
        this.treatmentMode = treatmentMode;
    }

    public String getDiagnoseName() {
        return this.diagnoseName;
    }

    public void setDiagnoseName(String diagnoseName) {
        this.diagnoseName = diagnoseName;
    }

    public String getTreatmentModeName() {
        return this.treatmentModeName;
    }

    public void setTreatmentModeName(String treatmentModeName) {
        this.treatmentModeName = treatmentModeName;
    }

    public String getDiagnoseCode() {
        return this.diagnoseCode;
    }

    public void setDiagnoseCode(String diagnoseCode) {
        this.diagnoseCode = diagnoseCode;
    }
}
