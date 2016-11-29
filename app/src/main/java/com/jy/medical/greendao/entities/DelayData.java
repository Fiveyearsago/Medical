package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by songran on 16/11/28.
 */
@Entity
public class DelayData {
    @Id
    Long id;
    private String taskNo;
    private String jobStatusKey;
    private String jobStatusValue;
    private String remark;
    private String completeStatusKey;
    private String completeStatusValue;
    private String industryKey;
    private String industryValue;
    private String companyName;
    private String companyAddress;
    private String entryTime;
    private String leaveTime;
    private String agreementKey;
    private String agreementValue;
    private String socialKey;
    private String socialValue;
    private String incomeFormKey;
    private String incomeFormValue;
    private String monthlyIncome;
    private String commitFlag;
    private String restDays;
    private String moneyReduce;


    public DelayData(String taskNo, String jobStatusKey, String jobStatusValue, String remark, String completeStatusKey, String completeStatusValue, String industryKey, String industryValue, String companyName, String companyAddress, String entryTime, String leaveTime, String agreementKey, String agreementValue, String socialKey, String socialValue, String incomeFormKey, String incomeFormValue, String monthlyIncome, String commitFlag, String restDays, String moneyReduce) {
        this.taskNo = taskNo;
        this.jobStatusKey = jobStatusKey;
        this.jobStatusValue = jobStatusValue;
        this.remark = remark;
        this.completeStatusKey = completeStatusKey;
        this.completeStatusValue = completeStatusValue;
        this.industryKey = industryKey;
        this.industryValue = industryValue;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.entryTime = entryTime;
        this.leaveTime = leaveTime;
        this.agreementKey = agreementKey;
        this.agreementValue = agreementValue;
        this.socialKey = socialKey;
        this.socialValue = socialValue;
        this.incomeFormKey = incomeFormKey;
        this.incomeFormValue = incomeFormValue;
        this.monthlyIncome = monthlyIncome;
        this.commitFlag = commitFlag;
        this.restDays = restDays;
        this.moneyReduce = moneyReduce;
    }



    @Generated(hash = 1593433777)
    public DelayData(Long id, String taskNo, String jobStatusKey, String jobStatusValue, String remark, String completeStatusKey, String completeStatusValue, String industryKey, String industryValue, String companyName, String companyAddress, String entryTime, String leaveTime, String agreementKey, String agreementValue, String socialKey, String socialValue, String incomeFormKey, String incomeFormValue, String monthlyIncome, String commitFlag, String restDays,
            String moneyReduce) {
        this.id = id;
        this.taskNo = taskNo;
        this.jobStatusKey = jobStatusKey;
        this.jobStatusValue = jobStatusValue;
        this.remark = remark;
        this.completeStatusKey = completeStatusKey;
        this.completeStatusValue = completeStatusValue;
        this.industryKey = industryKey;
        this.industryValue = industryValue;
        this.companyName = companyName;
        this.companyAddress = companyAddress;
        this.entryTime = entryTime;
        this.leaveTime = leaveTime;
        this.agreementKey = agreementKey;
        this.agreementValue = agreementValue;
        this.socialKey = socialKey;
        this.socialValue = socialValue;
        this.incomeFormKey = incomeFormKey;
        this.incomeFormValue = incomeFormValue;
        this.monthlyIncome = monthlyIncome;
        this.commitFlag = commitFlag;
        this.restDays = restDays;
        this.moneyReduce = moneyReduce;
    }



    @Generated(hash = 574445421)
    public DelayData() {
    }



    public String getRestDays() {
        return restDays;
    }

    public void setRestDays(String restDays) {
        this.restDays = restDays;
    }

    public String getMoneyReduce() {
        return moneyReduce;
    }

    public void setMoneyReduce(String moneyReduce) {
        this.moneyReduce = moneyReduce;
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

    public String getJobStatusKey() {
        return this.jobStatusKey;
    }

    public void setJobStatusKey(String jobStatusKey) {
        this.jobStatusKey = jobStatusKey;
    }

    public String getJobStatusValue() {
        return this.jobStatusValue;
    }

    public void setJobStatusValue(String jobStatusValue) {
        this.jobStatusValue = jobStatusValue;
    }

    public String getRemark() {
        return this.remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCompleteStatusKey() {
        return this.completeStatusKey;
    }

    public void setCompleteStatusKey(String completeStatusKey) {
        this.completeStatusKey = completeStatusKey;
    }

    public String getCompleteStatusValue() {
        return this.completeStatusValue;
    }

    public void setCompleteStatusValue(String completeStatusValue) {
        this.completeStatusValue = completeStatusValue;
    }

    public String getIndustryKey() {
        return this.industryKey;
    }

    public void setIndustryKey(String industryKey) {
        this.industryKey = industryKey;
    }

    public String getIndustryValue() {
        return this.industryValue;
    }

    public void setIndustryValue(String industryValue) {
        this.industryValue = industryValue;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyAddress() {
        return this.companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getEntryTime() {
        return this.entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getLeaveTime() {
        return this.leaveTime;
    }

    public void setLeaveTime(String leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getAgreementKey() {
        return this.agreementKey;
    }

    public void setAgreementKey(String agreementKey) {
        this.agreementKey = agreementKey;
    }

    public String getAgreementValue() {
        return this.agreementValue;
    }

    public void setAgreementValue(String agreementValue) {
        this.agreementValue = agreementValue;
    }

    public String getSocialKey() {
        return this.socialKey;
    }

    public void setSocialKey(String socialKey) {
        this.socialKey = socialKey;
    }

    public String getSocialValue() {
        return this.socialValue;
    }

    public void setSocialValue(String socialValue) {
        this.socialValue = socialValue;
    }

    public String getIncomeFormKey() {
        return this.incomeFormKey;
    }

    public void setIncomeFormKey(String incomeFormKey) {
        this.incomeFormKey = incomeFormKey;
    }

    public String getIncomeFormValue() {
        return this.incomeFormValue;
    }

    public void setIncomeFormValue(String incomeFormValue) {
        this.incomeFormValue = incomeFormValue;
    }

    public String getMonthlyIncome() {
        return this.monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getCommitFlag() {
        return this.commitFlag;
    }

    public void setCommitFlag(String commitFlag) {
        this.commitFlag = commitFlag;
    }
}
