package com.parent.springai.intellidetect.repository;

import com.parent.springai.intellidetect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // 根据用户名查找用户
    Optional<User> findByUname(String uname);
    
    // 根据邮箱查找用户
    Optional<User> findByEmail(String email);
    
    // 根据手机号查找用户
    Optional<User> findByPhoneNumber(String phoneNumber);
    
    // 检查用户名是否存在
    boolean existsByUname(String uname);
    
    // 检查邮箱是否存在
    boolean existsByEmail(String email);
    
    // 检查手机号是否存在
    boolean existsByPhoneNumber(String phoneNumber);
}
