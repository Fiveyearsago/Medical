package com.jy.medical.greendao.entities;

/**
 * Created by songran on 16/10/9.
 */

public class ToolData {
    private String toolTab;
    private String toolText;
    private String toolKind;
    private String toolTime;

    public ToolData(String toolTab, String toolText, String toolKind, String toolTime) {
        this.toolTab = toolTab;
        this.toolText = toolText;
        this.toolKind = toolKind;
        this.toolTime = toolTime;
    }

    public String getToolTab() {
        return toolTab;
    }

    public void setToolTab(String toolTab) {
        this.toolTab = toolTab;
    }

    public String getToolText() {
        return toolText;
    }

    public void setToolText(String toolText) {
        this.toolText = toolText;
    }

    public String getToolKind() {
        return toolKind;
    }

    public void setToolKind(String toolKind) {
        this.toolKind = toolKind;
    }

    public String getToolTime() {
        return toolTime;
    }

    public void setToolTime(String toolTime) {
        this.toolTime = toolTime;
    }
}
