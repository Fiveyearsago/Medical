package com.jy.medical.greendao.entities;

/**
 * Created by songran on 16/10/14.
 */

public class PlatformData {
    private String peopleName;
    private String time;
    private String tag;
    private String reportNum;

    public PlatformData(String peopleName, String time, String tag, String reportNum) {
        this.peopleName = peopleName;
        this.time = time;
        this.tag = tag;
        this.reportNum = reportNum;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getReportNum() {
        return reportNum;
    }

    public void setReportNum(String reportNum) {
        this.reportNum = reportNum;
    }
}
