package com.jy.medical.greendao.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by songran on 16/11/13.
 */

@Entity
public class SearchData {
    @Id
    Long id;
    //kind 搜索类型 1：搜索医院 2：搜索任务
    String kind;
    String searchText;

    public SearchData(String kind, String searchText) {
        this.kind = kind;
        this.searchText = searchText;
    }

    @Generated(hash = 984030608)
    public SearchData(Long id, String kind, String searchText) {
        this.id = id;
        this.kind = kind;
        this.searchText = searchText;
    }

    @Generated(hash = 693570109)
    public SearchData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKind() {
        return this.kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
