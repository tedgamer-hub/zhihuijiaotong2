package com.parent.springai.intellidetect.entity;

import lombok.Data;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@Entity
@Table(name = "accident_info")
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "video_url", length = 500, nullable = false)
    private String videoUrl;

    @Column(name = "image_url", length = 500, nullable = false)
    private String imageUrl;

    @Column(name = "accident_description", columnDefinition = "TEXT", nullable = false)
    private String accidentDescription;

    @Column(name = "accident_description_text", columnDefinition = "TEXT", nullable = false)
    private String accidentDescriptionText;

    @Column(name = "accident_description_time", columnDefinition = "TEXT", nullable = false)
    private String accidentDescriptionTime;

    @Column(name = "accident_description_state", columnDefinition = "TEXT", nullable = false)
    private String accidentDescriptionState;

    @Column(name = "accident_x")
    @JsonProperty("accidentX")
    private Double accidentX;

    @Column(name = "accident_y")
    @JsonProperty("accidentY")
    private Double accidentY;
}