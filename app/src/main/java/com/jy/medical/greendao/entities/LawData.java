package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/12/19.
 */
@Entity
public class LawData {
    @Id
    Long id;
    private String lawId;
    private String lawShortName;
    private String lawOrder;
    private String lawFullName;
    private String lawFullContent;
    private String createUserId;
    private String createDate;

    public LawData(String lawId, String lawShortName, String lawOrder, String lawFullName, String lawFullContent, String createUserId, String createDate) {
        this.lawId = lawId;
        this.lawShortName = lawShortName;
        this.lawOrder = lawOrder;
        this.lawFullName = lawFullName;
        this.lawFullContent = lawFullContent;
        this.createUserId = createUserId;
        this.createDate = createDate;
    }

    @Generated(hash = 1518709296)
    public LawData(Long id, String lawId, String lawShortName, String lawOrder, String lawFullName, String lawFullContent, String createUserId, String createDate) {
        this.id = id;
        this.lawId = lawId;
        this.lawShortName = lawShortName;
        this.lawOrder = lawOrder;
        this.lawFullName = lawFullName;
        this.lawFullContent = lawFullContent;
        this.createUserId = createUserId;
        this.createDate = createDate;
    }

    @Generated(hash = 385229051)
    public LawData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLawId() {
        return this.lawId;
    }

    public void setLawId(String lawId) {
        this.lawId = lawId;
    }

    public String getLawShortName() {
        return this.lawShortName;
    }

    public void setLawShortName(String lawShortName) {
        this.lawShortName = lawShortName;
    }

    public String getLawOrder() {
        return this.lawOrder;
    }

    public void setLawOrder(String lawOrder) {
        this.lawOrder = lawOrder;
    }

    public String getLawFullName() {
        return this.lawFullName;
    }

    public void setLawFullName(String lawFullName) {
        this.lawFullName = lawFullName;
    }

    public String getLawFullContent() {
        return this.lawFullContent;
    }

    public void setLawFullContent(String lawFullContent) {
        this.lawFullContent = lawFullContent;
    }

    public String getCreateUserId() {
        return this.createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
