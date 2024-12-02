package org.group4.Edu_Course_Catalog.entity;

import lombok.Data;

@Data
public class RoleDto {
    private Long userId;
    private String roleType;
    private String roleName;

    public RoleDto(Long userId, String roleType, String roleName) {
        this.userId = userId;
        this.roleType = roleType;
        this.roleName = roleName;
    }
}
