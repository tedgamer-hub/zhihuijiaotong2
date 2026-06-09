package com.parent.springai.intellidetect.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterRequest {
    private String uname;
    private String passwor;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String email;
}
