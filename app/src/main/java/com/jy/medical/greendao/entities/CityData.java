package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/10.
 */
@Entity
public class CityData {
    @Id
    Long id;
    private String aid;
    private String pid;
    private String code;
    private String name;
    private String level;

    public CityData(String aid,String pid, String code, String name, String level) {
        this.aid = aid;
        this.code = code;
        this.name = name;
        this.level = level;
        this.pid=pid;
    }

    @Generated(hash = 204538064)
    public CityData(Long id, String aid, String pid, String code, String name,
            String level) {
        this.id = id;
        this.aid = aid;
        this.pid = pid;
        this.code = code;
        this.name = name;
        this.level = level;
    }

    @Generated(hash = 1257025435)
    public CityData() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
