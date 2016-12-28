package com.jy.medical.greendao.entities;

/**
 * Created by songran on 16/10/25.
 */

public class AddressData {
    private String title;
    private String text;
    private Double latitude;
    private Double longitude;

    public AddressData(String title, String text, Double latitude, Double longitude) {
        this.title = title;
        this.text = text;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
