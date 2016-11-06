package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * Created by songran on 16/11/2.
 */
@Entity
public class ClaimBeanData {

    /**
     * claimId : 11bb26330a640646666b13634c176211
     * reportNo : 07313608082016000032
     * reportDate : 2016-02-24 13:16:00
     * insuredName : 刘启军
     * plateNo : 鄂H26B59
     * dangerDate : 2016-02-24 12:45:00
     * mobilePhone : 15872977228
     * companyId : 0131363001
     * companyName : 湖北省分公司荆门中支业务一部
     * bPolicyNo : 6313630080820160000143
     * fPolicyNo : 6313630080120160000160
     * createDate : 2016-02-24 13:23:11
     * lockFlag : 0
     * taskList : [{"id":"4028f8be54030eb4015403f827610026","claimId":"11bb26330a640646666b13634c176211","taskType":"05","taskState":"2","taskName":"被扶养人情况核查","injureName":"未知人1","injureId":"8ae486b853151597015315d3f1c00240","dispatchDate":"2016-04-11 14:17:55"}]
     */
    @Id (autoincrement = true)
    private Long id;
    @Unique
    @NotNull
    private String claimId;
    private String reportNo;
    private String reportDate;
    private String insuredName;
    private String plateNo;
    private String dangerDate;
    private String mobilePhone;
    private String companyId;
    private String companyName;
    private String bPolicyNo;
    private String fPolicyNo;
    private String createDate;
    private String lockFlag;
    @Generated(hash = 750019871)
    public ClaimBeanData(Long id, @NotNull String claimId, String reportNo, String reportDate, String insuredName, String plateNo, String dangerDate, String mobilePhone, String companyId, String companyName, String bPolicyNo, String fPolicyNo, String createDate,
            String lockFlag) {
        this.id = id;
        this.claimId = claimId;
        this.reportNo = reportNo;
        this.reportDate = reportDate;
        this.insuredName = insuredName;
        this.plateNo = plateNo;
        this.dangerDate = dangerDate;
        this.mobilePhone = mobilePhone;
        this.companyId = companyId;
        this.companyName = companyName;
        this.bPolicyNo = bPolicyNo;
        this.fPolicyNo = fPolicyNo;
        this.createDate = createDate;
        this.lockFlag = lockFlag;
    }
    public ClaimBeanData( String claimId, String reportNo, String reportDate, String insuredName, String plateNo, String dangerDate, String mobilePhone, String companyId, String companyName, String bPolicyNo, String fPolicyNo, String createDate,
            String lockFlag) {
        this.claimId = claimId;
        this.reportNo = reportNo;
        this.reportDate = reportDate;
        this.insuredName = insuredName;
        this.plateNo = plateNo;
        this.dangerDate = dangerDate;
        this.mobilePhone = mobilePhone;
        this.companyId = companyId;
        this.companyName = companyName;
        this.bPolicyNo = bPolicyNo;
        this.fPolicyNo = fPolicyNo;
        this.createDate = createDate;
        this.lockFlag = lockFlag;
    }

    @Generated(hash = 1445492352)
    public ClaimBeanData() {
    }
    /**
     * id : 4028f8be54030eb4015403f827610026
     * claimId : 11bb26330a640646666b13634c176211
     * taskType : 05
     * taskState : 2
     * taskName : 被扶养人情况核查
     * injureName : 未知人1
     * injureId : 8ae486b853151597015315d3f1c00240
     * dispatchDate : 2016-04-11 14:17:55
     */


    public String getClaimId() {
        return claimId;
    }

    public void setClaimId(String claimId) {
        this.claimId = claimId;
    }

    public String getReportNo() {
        return reportNo;
    }

    public void setReportNo(String reportNo) {
        this.reportNo = reportNo;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getPlateNo() {
        return plateNo;
    }

    public void setPlateNo(String plateNo) {
        this.plateNo = plateNo;
    }

    public String getDangerDate() {
        return dangerDate;
    }

    public void setDangerDate(String dangerDate) {
        this.dangerDate = dangerDate;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBPolicyNo() {
        return bPolicyNo;
    }

    public void setBPolicyNo(String bPolicyNo) {
        this.bPolicyNo = bPolicyNo;
    }

    public String getFPolicyNo() {
        return fPolicyNo;
    }

    public void setFPolicyNo(String fPolicyNo) {
        this.fPolicyNo = fPolicyNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getLockFlag() {
        return lockFlag;
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }



}
