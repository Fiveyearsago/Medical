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
    private String treatmentMode;

    public SelectedDiagnose(String taskNo, String diagnoseId, String treatmentMode) {
        this.taskNo = taskNo;
        this.diagnoseId = diagnoseId;
        this.treatmentMode = treatmentMode;
    }

    @Generated(hash = 127555266)
    public SelectedDiagnose(Long id, String taskNo, String diagnoseId, String treatmentMode) {
        this.id = id;
        this.taskNo = taskNo;
        this.diagnoseId = diagnoseId;
        this.treatmentMode = treatmentMode;
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
}
