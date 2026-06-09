package com.parent.springai.intellidetect.entity;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "accident_info")
public class AccidentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video_url", nullable = false, length = 500)
    private String videoUrl;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "accident_description", nullable = false, columnDefinition = "TEXT")
    private String accidentDescription;

    @Column(name = "accident_description_text", nullable = false, columnDefinition = "TEXT")
    private String accidentDescriptionText;

    @Column(name = "accident_description_time", nullable = false, columnDefinition = "TEXT")
    private String accidentDescriptionTime;

    @Column(name = "accident_description_state", nullable = false, columnDefinition = "TEXT")
    private String accidentDescriptionState;

    @Column(name = "display_info", columnDefinition = "TEXT")
    private String displayInfo;

    @Column(name = "accident_x")
    @JsonProperty("accidentX")
    private Double accidentX;

    @Column(name = "accident_y")
    @JsonProperty("accidentY")
    private Double accidentY;

    // Constructors
    public AccidentInfo() {}

    public AccidentInfo(String videoUrl, String imageUrl, String accidentDescription,
                       String accidentDescriptionText, String accidentDescriptionTime,
                       String accidentDescriptionState, String displayInfo, Double accidentX,
                       Double accidentY) {
        this.videoUrl = videoUrl;
        this.imageUrl = imageUrl;
        this.accidentDescription = accidentDescription;
        this.accidentDescriptionText = accidentDescriptionText;
        this.accidentDescriptionTime = accidentDescriptionTime;
        this.accidentDescriptionState = accidentDescriptionState;
        this.displayInfo = displayInfo;
        this.accidentX = accidentX;
        this.accidentY = accidentY;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getDisplayInfo() {
        return displayInfo;
    }

    public void setDisplayInfo(String displayInfo) {
        this.displayInfo = displayInfo;
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