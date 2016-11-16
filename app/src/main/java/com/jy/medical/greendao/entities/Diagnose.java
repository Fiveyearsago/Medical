package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/16.
 */
@Entity
public class Diagnose {
    @Id
    Long id;
    private String diagnoseId;
    private String itemCode;
    private String itemCnName;

    public Diagnose(String diagnoseId, String itemCode, String itemCnName) {
        this.diagnoseId = diagnoseId;
        this.itemCode = itemCode;
        this.itemCnName = itemCnName;
    }

    @Generated(hash = 1287817269)
    public Diagnose(Long id, String diagnoseId, String itemCode,
            String itemCnName) {
        this.id = id;
        this.diagnoseId = diagnoseId;
        this.itemCode = itemCode;
        this.itemCnName = itemCnName;
    }

    @Generated(hash = 1571269332)
    public Diagnose() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiagnoseId() {
        return this.diagnoseId;
    }

    public void setDiagnoseId(String diagnoseId) {
        this.diagnoseId = diagnoseId;
    }

    public String getItemCode() {
        return this.itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCnName() {
        return this.itemCnName;
    }

    public void setItemCnName(String itemCnName) {
        this.itemCnName = itemCnName;
    }
}
