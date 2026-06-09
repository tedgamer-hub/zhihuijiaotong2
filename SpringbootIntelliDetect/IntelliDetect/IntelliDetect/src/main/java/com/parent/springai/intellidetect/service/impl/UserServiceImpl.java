package com.parent.springai.intellidetect.service.impl;

import com.parent.springai.intellidetect.dto.LoginRequest;
import com.parent.springai.intellidetect.dto.RegisterRequest;
import com.parent.springai.intellidetect.dto.UserResponse;
import com.parent.springai.intellidetect.entity.User;
import com.parent.springai.intellidetect.repository.UserRepository;
import com.parent.springai.intellidetect.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserResponse login(LoginRequest loginRequest) {
        // 根据用户名查找用户
        Optional<User> userOptional = userRepository.findByUname(loginRequest.getUname());
        
        if (!userOptional.isPresent()) {
            throw new RuntimeException("用户名不存在");
        }
        
        User user = userOptional.get();
        
        // 验证密码 - 使用passwor字段进行验证
        if (!passwordEncoder.matches(loginRequest.getPasswor(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        
        // 创建UserResponse对象并显式设置每个字段
        UserResponse response = new UserResponse();
        response.setId(user.getUid());
        response.setUname(user.getUname());
        response.setPassword(user.getPassword());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        
        return response;
    }

    @Override
    public UserResponse register(RegisterRequest registerRequest) {
        // 基础非空校验
        if (registerRequest == null
                || !StringUtils.hasText(registerRequest.getUname())
                || !StringUtils.hasText(registerRequest.getPasswor())
                || !StringUtils.hasText(registerRequest.getPhoneNumber())
                || !StringUtils.hasText(registerRequest.getEmail())) {
            throw new RuntimeException("注册信息不完整：用户名/密码/手机号/邮箱不能为空");
        }

        // 检查用户名是否已存在
        if (userRepository.existsByUname(registerRequest.getUname())) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 检查邮箱是否已存在
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException("邮箱已存在");
        }
        
        // 检查手机号是否已存在
        if (userRepository.existsByPhoneNumber(registerRequest.getPhoneNumber())) {
            throw new RuntimeException("手机号已存在");
        }
        
        // 创建新用户
        User user = new User();
        user.setUname(registerRequest.getUname().trim());
        String encodedPassword = passwordEncoder.encode(registerRequest.getPasswor().trim()); // 加密密码
        user.setPassword(encodedPassword);
        user.setPhoneNumber(registerRequest.getPhoneNumber().trim());
        user.setEmail(registerRequest.getEmail().trim());
        user.setRole(2); // 默认为普通用户
        
        User savedUser = userRepository.save(user);
        
        // 创建UserResponse对象并显式设置每个字段
        UserResponse response = new UserResponse();
        response.setId(savedUser.getUid());
        response.setUname(savedUser.getUname());
        response.setPassword(savedUser.getPassword());
        response.setPhoneNumber(savedUser.getPhoneNumber());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());
        
        return response;
    }

    @Override
    public User findByUname(String uname) {
        Optional<User> userOptional = userRepository.findByUname(uname);
        return userOptional.orElse(null);
    }

    @Override
    public UserResponse getUserInfoById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        // 创建UserResponse对象并显式设置每个字段
        UserResponse response = new UserResponse();
        response.setId(user.getUid());
        response.setUname(user.getUname());
        response.setPassword(user.getPassword());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        
        return response;
    }

    @Override
    public UserResponse getUserInfoByUname(String uname) {
        User user = userRepository.findByUname(uname)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        
        // 创建UserResponse对象并显式设置每个字段
        UserResponse response = new UserResponse();
        response.setId(user.getUid());
        response.setUname(user.getUname());
        response.setPassword(user.getPassword());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        
        return response;
    }
}