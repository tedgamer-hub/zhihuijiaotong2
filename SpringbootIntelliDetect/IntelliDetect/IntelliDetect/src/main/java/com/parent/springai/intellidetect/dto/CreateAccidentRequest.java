package com.parent.springai.intellidetect.dto;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateAccidentRequest {

    @NotNull(message = "视频地址不能为空")
    private String videoUrl;
    
    @NotNull(message = "图片地址不能为空")
    private String imageUrl;
    
    @NotNull(message = "事故描述不能为空")
    private String accidentDescription;
    
    @NotNull(message = "事故描述文本不能为空")
    private String accidentDescriptionText;
    
    @NotNull(message = "事故描述时间不能为空")
    private String accidentDescriptionTime;
    
    @NotNull(message = "事故描述状态不能为空")
    private String accidentDescriptionState;

    @JsonProperty("accidentX")
    private Double accidentX;

    @JsonProperty("accidentY")
    private Double accidentY;

    // Constructors
    public CreateAccidentRequest() {}

    public CreateAccidentRequest(String videoUrl, String imageUrl, String accidentDescription,
                                String accidentDescriptionText, String accidentDescriptionTime,
                                String accidentDescriptionState, Double accidentX, Double accidentY) {
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this.accidentDescription = accidentDescription;
        this.accidentDescriptionText = accidentDescriptionText;
        this.accidentDescriptionTime = accidentDescriptionTime;
        this.accidentDescriptionState = accidentDescriptionState;
        this.accidentX = accidentX;
        this.accidentY = accidentY;
    }

    // Getters and Setters
    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAccidentDescription() {
        return accidentDescription;
    }

    public void setAccidentDescription(String accidentDescription) {
        this.accidentDescription = accidentDescription;
    }

    public String getAccidentDescriptionText() {
        return accidentDescriptionText;
    }

    public void setAccidentDescriptionText(String accidentDescriptionText) {
        this.accidentDescriptionText = accidentDescriptionText;
    }

    public String getAccidentDescriptionTime() {
        return accidentDescriptionTime;
    }

    public void setAccidentDescriptionTime(String accidentDescriptionTime) {
        this.accidentDescriptionTime = accidentDescriptionTime;
    }

    public String getAccidentDescriptionState() {
        return accidentDescriptionState;
    }

    public void setAccidentDescriptionState(String accidentDescriptionState) {
        this.accidentDescriptionState = accidentDescriptionState;
    }

    public Double getAccidentX() {
        return accidentX;
    }

    public void setAccidentX(Double accidentX) {
        this.accidentX = accidentX;
    }

    public Double getAccidentY() {
        return accidentY;
    }

    public void setAccidentY(Double accidentY) {
        this.accidentY = accidentY;
    }
}