package com.parent.springai.intellidetect.entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "user_info")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid")
    private Long uid;

    @Column(name = "uname", length = 50, nullable = false, unique = true)
    private String uname;

    @Column(name = "password", length = 300, nullable = false)
    private String password;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "role", nullable = false)
    private Integer role; // 主管理员0，管理员1，普通用户2
}


