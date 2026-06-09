package com.parent.springai.intellidetect.dto;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String uname;
    private String password;
    private String phoneNumber;
    private String email;
    private Integer role;
    
    // 无参构造函数，用于创建对象后手动设置字段值
    public UserResponse() {
    }
    
    // 仅包含ID的构造函数，保持向后兼容
    public UserResponse(Long uid) {
        this.id = uid;
    }
    
    // 包含完整用户信息的构造函数
    public UserResponse(Long uid, String uname, String password, String phoneNumber, String email, Integer role) {
        this.id = uid;
        this.uname = uname;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }
}
