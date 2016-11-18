package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/17.
 */

@Entity
public class HumanParts {
    @Id
    Long id;
    private String aid;
    private String code;
    private String name;

    public HumanParts(String aid, String code, String name) {
        this.aid = aid;
        this.code = code;
        this.name = name;
    }

    @Generated(hash = 189791273)
    public HumanParts(Long id, String aid, String code, String name) {
        this.id = id;
        this.aid = aid;
        this.code = code;
        this.name = name;
    }

    @Generated(hash = 1389749072)
    public HumanParts() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAid() {
        return this.aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
