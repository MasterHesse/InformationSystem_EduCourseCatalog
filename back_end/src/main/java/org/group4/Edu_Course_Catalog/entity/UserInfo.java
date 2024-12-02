package org.group4.Edu_Course_Catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String account;
    private String realName;
    private String gender;
    private String email;
    private String status;
}
