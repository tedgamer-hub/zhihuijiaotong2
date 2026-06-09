package com.parent.springai.intellidetect.controller;

import com.parent.springai.intellidetect.dto.ApiResponse;
import com.parent.springai.intellidetect.dto.LoginRequest;
import com.parent.springai.intellidetect.dto.RegisterRequest;
import com.parent.springai.intellidetect.dto.UserResponse;
import com.parent.springai.intellidetect.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    /**
     * 用户登录接口
     * POST /api/v1/users/login
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserResponse>> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserResponse userResponse = userService.login(loginRequest);
            return ResponseEntity.ok(ApiResponse.success("登录成功", userResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(500, "登录失败: " + e.getMessage()));
        }
    }

    /**
     * 用户注册接口
     * POST /api/v1/users/register
     */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@RequestBody RegisterRequest registerRequest) {
        try {
            UserResponse userResponse = userService.register(registerRequest);
            return ResponseEntity.ok(ApiResponse.success("注册成功", userResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(400, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(500, "注册失败: " + e.getMessage()));
        }
    }

    /**
     * 根据用户ID获取用户信息
     * GET /api/v1/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserInfoById(@PathVariable("id") Long userId) {
        try {
            UserResponse userResponse = userService.getUserInfoById(userId);
            return ResponseEntity.ok(ApiResponse.success("获取用户信息成功", userResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(500, "获取用户信息失败: " + e.getMessage()));
        }
    }

    /**
     * 根据用户名获取用户信息
     * GET /api/v1/users/info/{uname}
     */
    @GetMapping("/info/{uname}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserInfoByUname(@PathVariable("uname") String uname) {
        try {
            UserResponse userResponse = userService.getUserInfoByUname(uname);
            return ResponseEntity.ok(ApiResponse.success("获取用户信息成功", userResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.ok(ApiResponse.error(404, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.error(500, "获取用户信息失败: " + e.getMessage()));
        }
    }
}
