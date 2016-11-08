package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by songran on 16/11/8.
 */
@Entity
public class ContactData {
    @Id
    Long id;
    private String title;
    private String name;
    private String phoneNum;
    private String taskNo;
    public ContactData(String title, String name, String phoneNum) {
        this.title = title;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public String getTaskNo() {
        return taskNo;
    }

    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
