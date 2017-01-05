package com.jy.medical.greendao.entities;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by songran on 16/12/14.
 */
@Entity
public class SupporterPerson implements Parcelable {


    @Id
    Long id;
    private String taskNo;
    private String name;
    private String bornTime;
    private String houseKindKey;
    private String houseKindValue;
    private String relationKey;
    private String relationValue;
    private String age;
    private String years;
    private String num;
    private String address;

    public SupporterPerson(String taskNo, String name, String bornTime, String houseKindKey, String houseKindValue, String relationKey, String relationValue, String age, String years, String num, String address) {
        this.taskNo = taskNo;
        this.name = name;
        this.bornTime = bornTime;
        this.houseKindKey = houseKindKey;
        this.houseKindValue = houseKindValue;
        this.relationKey = relationKey;
        this.relationValue = relationValue;
        this.age = age;
        this.years = years;
        this.num = num;
        this.address = address;
    }

    @Generated(hash = 1077704132)
    public SupporterPerson(Long id, String taskNo, String name, String bornTime,
                           String houseKindKey, String houseKindValue, String relationKey,
                           String relationValue, String age, String years, String num,
                           String address) {
        this.id = id;
        this.taskNo = taskNo;
        this.name = name;
        this.bornTime = bornTime;
        this.houseKindKey = houseKindKey;
        this.houseKindValue = houseKindValue;
        this.relationKey = relationKey;
        this.relationValue = relationValue;
        this.age = age;
        this.years = years;
        this.num = num;
        this.address = address;
    }

    @Generated(hash = 862058542)
    public SupporterPerson() {
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

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBornTime() {
        return this.bornTime;
    }

    public void setBornTime(String bornTime) {
        this.bornTime = bornTime;
    }

    public String getHouseKindKey() {
        return this.houseKindKey;
    }

    public void setHouseKindKey(String houseKindKey) {
        this.houseKindKey = houseKindKey;
    }

    public String getHouseKindValue() {
        return this.houseKindValue;
    }

    public void setHouseKindValue(String houseKindValue) {
        this.houseKindValue = houseKindValue;
    }

    public String getRelationKey() {
        return this.relationKey;
    }

    public void setRelationKey(String relationKey) {
        this.relationKey = relationKey;
    }

    public String getRelationValue() {
        return this.relationValue;
    }

    public void setRelationValue(String relationValue) {
        this.relationValue = relationValue;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getYears() {
        return this.years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public String getNum() {
        return this.num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(taskNo);
        dest.writeString(name);
        dest.writeString(bornTime);
        dest.writeString(houseKindKey);
        dest.writeString(houseKindValue);
        dest.writeString(relationKey);
        dest.writeString(relationValue);
        dest.writeString(age);
        dest.writeString(years);
        dest.writeString(num);
        dest.writeString(address);
    }

    public static final Parcelable.Creator<SupporterPerson> CREATOR = new Creator<SupporterPerson>() {
        @Override
        public SupporterPerson[] newArray(int size) {
            return new SupporterPerson[size];
        }

        @Override
        public SupporterPerson createFromParcel(Parcel in) {
            return new SupporterPerson(in);
        }
    };

    public SupporterPerson(Parcel in) {
        id = in.readLong();
        taskNo = in.readString();
        name = in.readString();
        bornTime = in.readString();
        houseKindKey = in.readString();
        houseKindValue = in.readString();
        relationKey = in.readString();
        relationValue = in.readString();
        age = in.readString();
        years = in.readString();
        num = in.readString();
        address = in.readString();
    }
}
