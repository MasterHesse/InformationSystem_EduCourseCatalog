package org.group4.Edu_Course_Catalog.entity;

import lombok.Data;

// UserUpdateRequest.java
@Data
public class UserUpdateRequest {
    private String realName;
    private String gender;
    private String contact;

    // 学生特有字段
    private Integer semesterId;
    private Integer majorId;

    // 教师特有字段
    private String position;
    private String academicAchievements;
}