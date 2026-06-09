-- 创建数据库
CREATE DATABASE IF NOT EXISTS intellidetect CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE intellidetect;

-- 创建用户信息表
CREATE TABLE IF NOT EXISTS user_info (
    uid BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
    uname VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名，唯一',
    password VARCHAR(300) NOT NULL COMMENT '密码',
    phone_number VARCHAR(20) NOT NULL COMMENT '手机号',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    role TINYINT NOT NULL COMMENT '身份，主管理员0，管理员1，普通用户2',
    PRIMARY KEY (uid)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';

-- 创建事故信息表
CREATE TABLE IF NOT EXISTS accident_info (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
    video_url VARCHAR(500) NOT NULL COMMENT '视频地址',
    image_url VARCHAR(500) NOT NULL COMMENT '图片地址',
    accident_description TEXT NOT NULL COMMENT '事故描述',
    accident_description_text TEXT NOT NULL COMMENT '事故描述text文件',
    accident_description_time TEXT NOT NULL COMMENT '事故描述时间',
    accident_description_state TEXT NOT NULL COMMENT '事故描述严重程度',
    accident_x DOUBLE COMMENT '事故发生地点X坐标',
    accident_y DOUBLE COMMENT '事故发生地点Y坐标',
    display_info TEXT COMMENT '展示信息',
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='事故信息表';

select * from accident_info;