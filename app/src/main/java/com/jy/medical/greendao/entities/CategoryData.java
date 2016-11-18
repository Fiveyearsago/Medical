package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/17.
 */
@Entity
public class CategoryData {
    @Id
    Long id;
    private String key;
    private String value;
    private String typeCode;

    public CategoryData(String key, String value, String typeCode) {
        this.key = key;
        this.value = value;
        this.typeCode = typeCode;
    }

    @Generated(hash = 1735831061)
    public CategoryData(Long id, String key, String value, String typeCode) {
        this.id = id;
        this.key = key;
        this.value = value;
        this.typeCode = typeCode;
    }

    @Generated(hash = 156061166)
    public CategoryData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
}
