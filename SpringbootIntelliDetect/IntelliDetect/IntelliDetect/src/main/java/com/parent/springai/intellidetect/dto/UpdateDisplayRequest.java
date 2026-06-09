package com.parent.springai.intellidetect.dto;

public class UpdateDisplayRequest {

    private String displayInfo;

    // Constructors
    public UpdateDisplayRequest() {}

    public UpdateDisplayRequest(String displayInfo) {
        this.displayInfo = displayInfo;
    }

    // Getters and Setters
    public String getDisplayInfo() {
        return displayInfo;
    }

    public void setDisplayInfo(String displayInfo) {
        this.displayInfo = displayInfo;
    }
}
