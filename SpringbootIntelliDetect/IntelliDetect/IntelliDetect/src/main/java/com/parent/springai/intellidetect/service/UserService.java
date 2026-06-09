package com.parent.springai.intellidetect.service;

import com.parent.springai.intellidetect.dto.LoginRequest;
import com.parent.springai.intellidetect.dto.RegisterRequest;
import com.parent.springai.intellidetect.dto.UserResponse;
import com.parent.springai.intellidetect.entity.User;

public interface UserService {
    
    // 用户登录
    UserResponse login(LoginRequest loginRequest);
    
    // 用户注册
    UserResponse register(RegisterRequest registerRequest);
    
    // 根据用户名查找用户
    User findByUname(String uname);
    
    // 根据ID获取用户详细信息
    UserResponse getUserInfoById(Long userId);
    
    // 根据用户名获取用户详细信息
    UserResponse getUserInfoByUname(String uname);
}
