package com.jy.medical.greendao.entities;

/**
 * Created by songran on 16/10/25.
 */

public class AddressData {
    private String title;
    private String text;

    public AddressData(String title, String text) {
        this.title = title;
        this.text = text;
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
