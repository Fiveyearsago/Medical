package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/9.
 */

@Entity
public class TaskPhoto {
    @Id
    Long id;
    String taskNo;
    String photoPath;

    public TaskPhoto(String taskNo, String photoPath) {
        this.taskNo = taskNo;
        this.photoPath = photoPath;
    }

    @Generated(hash = 1873169920)
    public TaskPhoto(Long id, String taskNo, String photoPath) {
        this.id = id;
        this.taskNo = taskNo;
        this.photoPath = photoPath;
    }

    @Generated(hash = 2087019496)
    public TaskPhoto() {
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

    public String getPhotoPath() {
        return this.photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
